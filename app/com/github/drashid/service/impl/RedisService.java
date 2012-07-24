package com.github.drashid.service.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import com.github.drashid.config.impl.RedisConfig;
import com.github.drashid.service.AbstractService;

@Singleton
public class RedisService extends AbstractService {

  private JedisPool pool;

  @Inject
  private RedisConfig config;

  @Override
  protected void startService() {
    if (config.getPassword() != null) {
      pool = new JedisPool(new JedisPoolConfig(), config.getHost(), config.getPort(), Protocol.DEFAULT_TIMEOUT,
          config.getPassword());
    } else {
      pool = new JedisPool(new JedisPoolConfig(), config.getHost(), config.getPort(), Protocol.DEFAULT_TIMEOUT);
    }
  }

  @Override
  protected void stopService() {
    pool.destroy();
  }

  public Jedis getConnection() {
    return pool.getResource();
  }

  public void returnConnection(Jedis jedis) {
    pool.returnResource(jedis);
  }

}
