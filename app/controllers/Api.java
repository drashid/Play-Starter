package controllers;

import static module.GuiceHolder.injector;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.libs.Akka;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Result;

import com.github.drashid.api.ApiOp;
import com.github.drashid.api.Async;

import controllers.api.Test;

public class Api extends Controller {

  private static final Logger LOG = LoggerFactory.getLogger(Test.class);
  
  private static final String API_PATH = "controllers.api";

  public static Result api(String path) throws Exception {
    // Find class name
    StringBuffer sb = capitalizeClassName(new StringBuffer(API_PATH).append(".")
        .append(path.replace('/', '.').toLowerCase()));

    try {
      ApiOp op = (ApiOp)injector().getInstance(Class.forName(sb.toString()));
      LOG.info("Executing API call [{}]", op.getClass().getCanonicalName()); 
      
      if(op.getClass().isAnnotationPresent(Async.class)){
        return async(apiAsyncCall(Context.current(), op));
      }else{
        return op.call();
      }
    } catch (Exception e) {
      LOG.error("Could not execute API call", e);
      return badRequest();
    }
  }

  private static Promise<Result> apiAsyncCall(final Context current, final ApiOp op) {
    return Akka.future(new Callable<Result>() {
      @Override
      public Result call() throws Exception {
        Context.current.set(current);
        return op.call();
      }
    });
  }

  private static StringBuffer capitalizeClassName(StringBuffer sb) {
    int index = sb.lastIndexOf(".");
    if (index >= 0) {
      char classNameStart = sb.charAt(index + 1);
      String upperCaseChar = String.valueOf(Character.toUpperCase(classNameStart));
      return sb.replace(index + 1, index + 2, upperCaseChar);
    }
    return sb;
  }

}
