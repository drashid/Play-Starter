package com.github.drashid.action;

import module.GuiceHolder;
import play.mvc.Http.Context;
import play.mvc.Result;

public class InjectedActionRunner extends play.mvc.Action<Action> {

  @Override
  public Result call(Context httpContext) throws Throwable {
    InjectedAction action = GuiceHolder.injector().getInstance(configuration.value());
    action.setContext(httpContext);
    action.setDelegate(delegate);
    return action.call();
  }

}
