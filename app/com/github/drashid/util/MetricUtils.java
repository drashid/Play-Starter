package com.github.drashid.util;

import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.Timer;

public class MetricUtils {

  public static Timer createTimer(Class<?> cls) {
    return Metrics.newTimer(cls, cls.getName());
  }
  
}
