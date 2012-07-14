package com.github.drashid.config;

import play.Application;


public enum Environment {
  DEV, STAGING, PROD, TEST;
  
  public static Environment valueOf(Application app){
    if(app.isDev()){
      return DEV;
    }else if(app.isTest()){
      return TEST;
    }else if(app.isProd()){
      return PROD; //eventually Staging?
    }
    
    return DEV;
  }
}
