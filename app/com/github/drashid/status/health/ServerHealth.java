package com.github.drashid.status.health;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.yammer.metrics.core.HealthCheck.Result;

public class ServerHealth {

  private Map<String, ServerResult> checkResults;

  private long timestamp;

  private String machine;

  ServerHealth() {

  }

  public ServerHealth(String machine, Map<String, Result> results) {
    this.checkResults = new HashMap<String, ServerResult>();
    for (Entry<String, Result> resultEntry : results.entrySet()) {
      checkResults.put(resultEntry.getKey(), new ServerResult(resultEntry.getValue()));
    }

    this.timestamp = System.currentTimeMillis();
    this.machine = machine;
  }

  public Map<String, ServerResult> getCheckResults() {
    return checkResults;
  }

  public void setCheckResults(Map<String, ServerResult> checkResults) {
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

  public static class ServerResult {

    private boolean healthy;

    private Throwable error;

    private String message;

    public ServerResult(Result value) {
      this.message = value.getMessage();
      this.error = value.getError();
      this.healthy = value.isHealthy();
    }

    ServerResult() {

    }

    public boolean isHealthy() {
      return healthy;
    }

    public void setHealthy(boolean healthy) {
      this.healthy = healthy;
    }

    public Throwable getError() {
      return error;
    }

    public void setError(Throwable error) {
      this.error = error;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

  }

}
