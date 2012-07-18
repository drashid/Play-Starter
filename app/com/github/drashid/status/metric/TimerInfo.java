package com.github.drashid.status.metric;

import java.util.concurrent.TimeUnit;

import com.yammer.metrics.core.Timer;

public class TimerInfo extends MeterInfo {

  protected double mean;

  protected double min;

  protected double max;

  protected double median;

  protected double nfPercent; // 95 percentile

  protected TimeUnit durationUnit;

  public TimerInfo(String machineCode, String metricName, Timer timer) {
    super(machineCode, metricName, timer);
    this.mean = timer.mean();
    this.min = timer.min();
    this.max = timer.max();
    this.median = timer.getSnapshot().getMedian();
    this.nfPercent = timer.getSnapshot().get95thPercentile();
    this.durationUnit = timer.durationUnit();
    this.type = MetricType.TIMER;
  }

  TimerInfo() {
  }

  public double getMedian() {
    return median;
  }

  public void setMedian(double median) {
    this.median = median;
  }

  public double getNfPercent() {
    return nfPercent;
  }

  public void setNfPercent(double nfPercent) {
    this.nfPercent = nfPercent;
  }

  public double getMax() {
    return max;
  }

  public double getMean() {
    return mean;
  }

  public double getMin() {
    return min;
  }

  public void setMax(double max) {
    this.max = max;
  }

  public void setMean(double mean) {
    this.mean = mean;
  }

  public void setMin(double min) {
    this.min = min;
  }

  public void setMachine(String machine) {
    this.machine = machine;
  }

  public String getMachine() {
    return machine;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MetricType getType() {
    return type;
  }

  public TimeUnit getDurationUnit() {
    return durationUnit;
  }

  public void setDurationUnit(TimeUnit durationUnit) {
    this.durationUnit = durationUnit;
  }

}
