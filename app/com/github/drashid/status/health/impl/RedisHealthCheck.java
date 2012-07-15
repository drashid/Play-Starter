package com.github.drashid.status.health.impl;

import javax.inject.Inject;

import redis.clients.jedis.Jedis;

import com.github.drashid.service.impl.RedisService;
import com.yammer.metrics.core.HealthCheck;

public class RedisHealthCheck extends HealthCheck {

  @Inject
  private RedisService redis;

  public RedisHealthCheck() {
    super("RedisCheck");
  }

  @Override
  protected Result check() throws Exception {
    Jedis jedis = redis.getConnection();
    try {
      String pong = jedis.ping();
      if (pong.equalsIgnoreCase("pong")) {
        return Result.healthy();
      } else {
        return Result.unhealthy(pong);
      }
    } finally {
      redis.returnConnection(jedis);
    }
  }

}
