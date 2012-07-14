package com.github.drashid.config.impl;

import com.github.drashid.config.Config;

@Config("redis")
public class RedisConfig {

  public String host;

  public int port;

  RedisConfig() {
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

}
