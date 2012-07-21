package com.github.drashid.api;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import play.mvc.Controller;
import play.mvc.Result;

import com.github.drashid.config.Environment;

public abstract class ApiOp extends Controller implements Callable<Result> {

  @Inject
  protected Environment env;
  
}
