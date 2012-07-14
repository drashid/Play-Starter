package com.github.drashid.service.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.github.drashid.config.impl.RedisConfig;
import com.github.drashid.service.AbstractService;

@Singleton
public class RedisService extends AbstractService {

  private JedisPool pool;

  @Inject
  private RedisConfig config;

  @Override
  protected void startService() {
    pool = new JedisPool(config.getHost(), config.getPort());
  }

  @Override
  protected void stopService() {
    pool.destroy();
  }

  public Jedis getConnection(){
    return pool.getResource();
  }
  
  public void returnConnection(Jedis jedis){
    pool.returnResource(jedis);
  }
  
}
