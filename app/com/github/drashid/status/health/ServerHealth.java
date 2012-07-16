package com.github.drashid.status.health;

import java.util.Map;

import com.yammer.metrics.core.HealthCheck.Result;

public class ServerHealth {

  private Map<String, Result> checkResults;

  private long timestamp;

  private String machine;

  ServerHealth() {

  }

  public ServerHealth(String machine, Map<String, Result> results) {
    this.checkResults = results;
    this.timestamp = System.currentTimeMillis();
    this.machine = machine;
  }

  public Map<String, Result> getCheckResults() {
    return checkResults;
  }

  public void setCheckResults(Map<String, Result> checkResults) {
    this.checkResults = checkResults;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public void setMachine(String machine) {
    this.machine = machine;
  }

  public String getMachine() {
    return machine;
  }

}
