package com.github.drashid.api;

import java.util.concurrent.Callable;

import play.mvc.Controller;
import play.mvc.Result;

public abstract class ApiOp extends Controller implements Callable<Result> {

}
