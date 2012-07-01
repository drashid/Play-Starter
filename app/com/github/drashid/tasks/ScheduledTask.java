package com.github.drashid.tasks;

import akka.util.Duration;

public interface ScheduledTask extends Runnable {

  public Duration getInitialDelay();

  public Duration getFrequency();

}
