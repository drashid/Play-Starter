package com.github.drashid.service.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import com.github.drashid.config.impl.RedisConfig;
import com.github.drashid.service.AbstractService;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Gauge;

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
    
    //Info gauges    
    Metrics.newGauge(RedisService.class, "RedisConnections", new Gauge<Integer>() {
      @Override
      public Integer value() {
        return getNumConnections();
      }
    });
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
  
  private int getNumConnections() {
    String numConnectionKey = "connected_clients";
    String value = valueInInfo(numConnectionKey);
    return Integer.parseInt(value);
  }
  
  private String valueInInfo(String key){
    Jedis conn = getConnection();
    try{
      String info = conn.info();
      return valueInInfo(key, info);
    }finally{
      returnConnection(conn);
    }
  }
  
  private String valueInInfo(String key, String info){
    key = key.trim();    
    int indexKey = info.indexOf(key);
    int start = indexKey + key.length() + (key.endsWith(":") ? 0 : 1);
    int end = info.indexOf("\r\n", start);
    
    return info.substring(start, end);
  }

}
