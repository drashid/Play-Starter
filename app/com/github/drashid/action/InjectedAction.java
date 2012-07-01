package com.github.drashid.action;

import module.GuiceHolder;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

public abstract class InjectedAction extends Action.Simple {

  @Override
  public Result call(Http.Context context) throws Throwable {
    GuiceHolder.injector().injectMembers(this);
    return _call(context);
  }

  protected abstract Result _call(Http.Context context);

}
