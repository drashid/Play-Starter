package com.github.drashid.status.metric;

import java.util.concurrent.TimeUnit;

import com.yammer.metrics.core.Meter;

public class MeterInfo implements MetricData {

  private MetricType type = MetricType.METER;

  private String machine;

  private String name;

  private long count;

  private double meanRate;

  private TimeUnit unit;

  private double oneMinRate;

  private double fiveMinRate;

  private double fifteenMinRate;

  public MeterInfo(String machineCode, String metricName, Meter value) {
    this.name = metricName;
    this.machine = machineCode;
    this.count = value.count();
    this.meanRate = value.meanRate();
    this.unit = value.rateUnit();
    this.oneMinRate = value.oneMinuteRate();
    this.fiveMinRate = value.fiveMinuteRate();
    this.fifteenMinRate = value.fifteenMinuteRate();
  }
  
  MeterInfo(){  
  }

  @Override
  public MetricType getType() {
    return type;
  }

  public void setType(MetricType type) {
    this.type = type;
  }

  public String getMachine() {
    return machine;
  }

  public void setMachine(String machine) {
    this.machine = machine;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getCount() {
    return count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public double getMeanRate() {
    return meanRate;
  }

  public void setMeanRate(double meanRate) {
    this.meanRate = meanRate;
  }

  public TimeUnit getUnit() {
    return unit;
  }

  public void setUnit(TimeUnit unit) {
    this.unit = unit;
  }

  public double getOneMinRate() {
    return oneMinRate;
  }

  public void setOneMinRate(double oneMinRate) {
    this.oneMinRate = oneMinRate;
  }

  public double getFiveMinRate() {
    return fiveMinRate;
  }

  public void setFiveMinRate(double fiveMinRate) {
    this.fiveMinRate = fiveMinRate;
  }

  public double getFifteenMinRate() {
    return fifteenMinRate;
  }

  public void setFifteenMinRate(double fifteenMinRate) {
    this.fifteenMinRate = fifteenMinRate;
  }

}
