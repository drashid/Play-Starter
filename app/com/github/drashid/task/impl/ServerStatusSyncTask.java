package com.github.drashid.task.impl;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.util.Duration;

import com.github.drashid.status.ServerStatusGateway;
import com.github.drashid.task.ScheduledTask;

public class ServerStatusSyncTask implements ScheduledTask {

  public static Logger LOG = LoggerFactory.getLogger(ServerStatusSyncTask.class);
  
  @Inject
  private ServerStatusGateway metricGateway;

  @Override
  public void run() {
    metricGateway.pushHealth();
    metricGateway.pushMetrics();
  }

  @Override
  public Duration getInitialDelay() {
    return Duration.Zero();
  }

  @Override
  public Duration getFrequency() {
    return Duration.create(5, TimeUnit.SECONDS);
  }

}
