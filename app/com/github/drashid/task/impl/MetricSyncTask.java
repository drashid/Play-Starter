package com.github.drashid.task.impl;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.util.Duration;

import com.github.drashid.task.ScheduledTask;

public class MetricSyncTask implements ScheduledTask {

  public static Logger LOG = LoggerFactory.getLogger(MetricSyncTask.class);

  @Override
  public void run() {
//    LOG.info("Ping!"); //TODO
  }

  @Override
  public Duration getInitialDelay() {
    return Duration.Zero();
  }

  @Override
  public Duration getFrequency() {
    return Duration.create(1, TimeUnit.SECONDS);
  }

}
