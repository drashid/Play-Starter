package com.github.drashid.config;

import play.Application;

public enum Environment {
  DEVELOPMENT,
  STAGING,
  PRODUCTION,
  TEST;

  public static Environment valueOf(Application app) {
    if (app.isDev()) {
      return DEVELOPMENT;
    } else if (app.isTest()) {
      return TEST;
    } else if (app.isProd()) {
      return PRODUCTION; // eventually Staging?
    }

    return DEVELOPMENT;
  }
}
