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
import com.yammer.metrics.core.MetricName;

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
    
    initGauges();
  }

  private void initGauges(){
    createRedisInfoMetric("Connections", "connected_clients");
    createRedisInfoMetric("KeysExpired", "expired_keys");
    createRedisInfoMetric("KeysEvicted", "evicted_keys");
    createRedisInfoMetric("CpuUser", "used_cpu_user");
  }
  
  private void createRedisInfoMetric(final String name, final String infoKey){
    Metrics.newGauge(new MetricName("Redis", "Info", name), new Gauge<Double>() {
      @Override
      public Double value() {
        return Double.parseDouble( valueInInfo(infoKey) );
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
