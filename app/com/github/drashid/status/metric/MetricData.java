package com.github.drashid.status.metric;

public interface MetricData {

  public MetricType getType();
  
  public String getMachine();
  
  public String getName();
}
