package com.github.drashid.config.impl;

import com.github.drashid.config.Config;

@Config("redis")
public class RedisConfig {

  private String host;

  private int port;

  private String password;

  public RedisConfig() {
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

}
