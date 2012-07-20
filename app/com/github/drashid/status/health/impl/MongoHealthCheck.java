package com.github.drashid.status.health.impl;

import com.yammer.metrics.core.HealthCheck;

public class MongoHealthCheck extends HealthCheck {

  protected MongoHealthCheck() {
    super("MongoCheck");
  }

  @Override
  protected Result check() throws Exception {
    return Result.unhealthy("Not yet implemented!");
  }

}
