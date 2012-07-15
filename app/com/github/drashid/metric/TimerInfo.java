package com.github.drashid.metric;

import com.yammer.metrics.core.Timer;

public class TimerInfo {

  private String machine;

  private double mean;

  private double min;

  private double max;

  private double median;

  private double nfPercent; // 95 percentile

  public TimerInfo(String machineCode, Timer timer) {
    this.mean = timer.mean();
    this.min = timer.min();
    this.max = timer.max();
    this.median = timer.getSnapshot().getMedian();
    this.nfPercent = timer.getSnapshot().get95thPercentile();
    this.machine = machineCode;
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
}
