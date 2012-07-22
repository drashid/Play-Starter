package com.github.drashid.service.impl;

import java.net.URI;

import javax.inject.Inject;
import javax.inject.Singleton;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import com.github.drashid.config.Environment;
import com.github.drashid.config.InvalidConfigurationException;
import com.github.drashid.config.impl.RedisConfig;
import com.github.drashid.service.AbstractService;

@Singleton
public class RedisService extends AbstractService {

  private JedisPool pool;

  @Inject
  private RedisConfig config;

  @Inject
  private Environment env;
  
  @Override
  protected void startService() {
    if(env == Environment.PRODUCTION){
      try{ //TODO generalize -- this is heroku specific right now! 
        URI redisURI = new URI(System.getenv("REDISTOGO_URL"));
        pool = new JedisPool(new JedisPoolConfig(),
            redisURI.getHost(),
            redisURI.getPort(),
            Protocol.DEFAULT_TIMEOUT,
            redisURI.getUserInfo().split(":",2)[1]);
      }catch(Exception e){
        throw new InvalidConfigurationException("Could not set up Redis from system ENV!"); 
      }
    }else{
      pool = new JedisPool(config.getHost(), config.getPort());
    }
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
