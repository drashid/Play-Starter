package com.github.drashid.service;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractService implements Service {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractService.class);

  private AtomicBoolean started = new AtomicBoolean(false);

  @Override
  public boolean isStarted() {
    return started.get();
  }

  @Override
  public void start() {
    synchronized (started) {
      if (!isStarted()) {
        LOG.info("Starting Service {}", getClass().getSimpleName());

        startService();
        started.set(true);
      }
    }
  }

  @Override
  public void stop() {
    synchronized (started) {
      if (isStarted()) {
        LOG.info("Stopping Service {}", getClass().getSimpleName());

        stopService();
        started.set(false);
      }
    }
  }

  protected abstract void startService();

  protected abstract void stopService();
}
