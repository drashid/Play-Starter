package com.github.drashid.status.metric;

import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface MetricData {

  public MetricType getType();

  public String getMachine();

  public String getName();
  
}
