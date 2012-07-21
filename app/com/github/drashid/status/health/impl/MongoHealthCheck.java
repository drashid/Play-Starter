package com.github.drashid.status.health.impl;

import com.yammer.metrics.core.HealthCheck;

public class MongoHealthCheck extends HealthCheck {

  protected MongoHealthCheck() {
    super("MongoCheck");
  }

  @Override
  protected Result check() throws Exception {
    return Result.unhealthy("Not hooked up yet.  Adjust your expectations accordingly, otherwise you will be disappointed.");
  }

}
