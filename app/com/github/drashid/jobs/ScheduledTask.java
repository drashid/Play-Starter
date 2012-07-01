package com.github.drashid.jobs;

import akka.util.Duration;

public interface ScheduledTask extends Runnable {

  public Duration getInitialDelay();

  public Duration getFrequency();

}
