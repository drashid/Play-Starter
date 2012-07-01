package com.github.drashid.action;

import java.util.concurrent.Callable;

import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;

public abstract class InjectedAction implements Callable<Result> {

  protected Context context;

  protected Action<?> delegate;

  public void setContext(Context httpContext) {
    this.context = httpContext;
  }

  public void setDelegate(Action<?> delegate) {
    this.delegate = delegate;
  }

}
