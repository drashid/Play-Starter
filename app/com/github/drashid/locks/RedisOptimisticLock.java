package com.github.drashid.locks;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.commons.lang.Validate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.github.drashid.config.impl.RedisConfig;
import com.github.drashid.service.ServiceModule;
import com.github.drashid.service.impl.RedisService;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
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

  public static void main(String[] args) throws InterruptedException {
    Injector inject = Guice.createInjector(new LockModule(), new ServiceModule(), new AbstractModule() {

      @Override
      protected void configure() {
        RedisConfig config = new RedisConfig();
        config.setHost("localhost");
        config.setPort(6379);
        bind(RedisConfig.class).toInstance(config);
      }
    });
    inject.getInstance(RedisService.class).start();
    DistributedLockFactory lockFactory = inject.getInstance(DistributedLockFactory.class);
    ExecutorService service = Executors.newFixedThreadPool(3);

    Runnable one = buildRunnable(lockFactory);
    Runnable two = buildRunnable(lockFactory);
    Runnable three = buildRunnable(lockFactory);

    service.submit(one);
    service.submit(two);
    service.submit(three);

    service.awaitTermination(10, TimeUnit.SECONDS);
    service.shutdownNow();
  }

  private static Runnable buildRunnable(final DistributedLockFactory lockFactory) {
    return new Runnable() {

      @Override
      public void run() {
        DistributedLock lock = lockFactory.createLock("TEST+LOCK!");
        try {
          String threadName = Thread.currentThread().getName();
          System.out.println("In thread " + threadName);
          boolean success = lock.tryLock(60, TimeUnit.SECONDS);
          System.out.println(String.format("Thread: %s, success: %s", threadName, success));
          try {
            Thread.sleep(3000);
          } catch (InterruptedException e) {
          }
          System.out.println(String.format("Thread: %s, success: %s", threadName, success));
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          lock.releaseLock();
        }
      }
    };
  }

}
