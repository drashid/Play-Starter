package controllers.api;

import java.util.Date;

import javax.inject.Inject;

import play.mvc.Result;

import com.github.drashid.api.ApiOperation;
import com.github.drashid.api.Async;
import com.yammer.metrics.annotation.Timed;

@Async
public class Test extends ApiOperation {

  @Inject
  private Date date;

  @Override
  @Timed
  public Result call() {
    return ok("I'm a method!  You totally " + request().method() + " me, man! " + date.getTime());
  }

}
