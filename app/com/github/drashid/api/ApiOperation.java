package com.github.drashid.api;

import java.util.concurrent.Callable;

import play.mvc.Controller;
import play.mvc.Result;

public abstract class ApiOperation extends Controller implements Callable<Result> {

}
