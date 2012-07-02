package controllers.api;

import play.mvc.Result;

import com.yammer.metrics.annotation.Timed;

import controllers.ApiOperation;

public class Test extends ApiOperation {

  @Override
  @Timed
  public Result call() {
    return ok("I'm a method!  You totally " + request().method() + " me!");
  }

}
