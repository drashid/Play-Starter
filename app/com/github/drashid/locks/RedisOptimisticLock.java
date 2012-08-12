package com.github.drashid.locks;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.commons.lang.Validate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.github.drashid.service.impl.RedisService;
import com.google.inject.assistedinject.Assisted;

public class RedisOptimisticLock implements DistributedLock {

  private static final String SUCCESS = "OK";

  @Inject
  private RedisService redis;

  private final String value;

  private final String key;

  @Inject
  RedisOptimisticLock(@Assisted String key) {
    this.key = key;
    this.value = LockUtils.createMachineUUID();
  }

  @Override
  public boolean tryLock(long ttl, TimeUnit unit) {
    int expireInSecs = (int)unit.toSeconds(ttl);
    Validate.isTrue(expireInSecs > 0, "Expire time must be >= 1 second!");

    Jedis conn = redis.getConnection();
    try {
      conn.watch(key);
      String initialValue = conn.get(key);
      // if no one holds this lock, or we already do, continue
      if (initialValue == null || initialValue.equals(value)) {
        Transaction trans = conn.multi();
        trans.set(key, value);
        trans.expire(key, expireInSecs);
        List<Object> res = trans.exec();

        return res != null && res.get(0).equals(SUCCESS);
      } else {
        // someone already holds it
        return false;
      }
    } finally {
      redis.returnConnection(conn);
    }
  }

  @Override
  public boolean releaseLock() {
    Jedis conn = redis.getConnection();
    try {
      conn.watch(key);
      String initialValue = conn.get(key);
      // if no one holds this lock, or we already do, continue
      if (initialValue == null || initialValue.equals(value)) {
        Transaction trans = conn.multi();
        trans.del(key);
        List<Object> res = trans.exec();

        return res != null && res.get(0).equals(SUCCESS);
      } else {
        // someone already holds it
        return false;
      }
    } finally {
      redis.returnConnection(conn);
    }
  }

}
