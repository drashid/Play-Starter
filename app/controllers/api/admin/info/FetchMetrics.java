package controllers.api.admin.info;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.libs.Json;
import play.mvc.Result;

import com.github.drashid.api.ApiOp;
import com.github.drashid.api.Async;
import com.github.drashid.status.ServerStatusGateway;
import com.yammer.metrics.annotation.Timed;

@Async
public class FetchMetrics extends ApiOp {

  private static final Logger LOG = LoggerFactory.getLogger(FetchMetrics.class);
  
  @Inject
  private ServerStatusGateway metrics;
  
  @Override
  @Timed(durationUnit = TimeUnit.SECONDS)
  public Result call() {
    try {
      return ok(Json.toJson(metrics.getMetrics().values()));
    } catch (Exception e){ 
      LOG.error("Error getting metrics!", e);
      return internalServerError(e.getMessage());
    }
  }

}
