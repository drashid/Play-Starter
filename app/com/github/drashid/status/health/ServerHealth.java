package com.github.drashid.status.health;

import java.util.Map;

import com.yammer.metrics.core.HealthCheck.Result;

public class ServerHealth {

  private Map<String, Result> checkResults;

  private long timestamp;

  ServerHealth() {

  }

  public ServerHealth(Map<String, Result> results) {
    this.checkResults = results;
    this.timestamp = System.currentTimeMillis();
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

}
