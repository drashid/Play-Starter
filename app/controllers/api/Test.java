package controllers.api;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.mvc.Result;

import com.github.drashid.api.ApiOp;
import com.github.drashid.api.Async;
import com.yammer.metrics.annotation.Timed;

@Async
public class Test extends ApiOp {

  private static final Logger LOG = LoggerFactory.getLogger(Test.class);
  
  @Inject
  private Date date;

  @Override
  @Timed
  public Result call() {
    LOG.info("Executing Test!");
    
    return ok("I'm a method!  You totally " + request().method() + " me, man! " + date.getTime());
  }

}
