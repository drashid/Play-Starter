package com.github.drashid.task.impl;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.util.Duration;

import com.github.drashid.metric.MetricGateway;
import com.github.drashid.task.ScheduledTask;

public class MetricSyncTask implements ScheduledTask {

  public static Logger LOG = LoggerFactory.getLogger(MetricSyncTask.class);
  
  @Inject
  private MetricGateway metricGateway;

  @Override
  public void run() {
    metricGateway.pingServer();
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
