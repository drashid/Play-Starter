package controllers.api;

import javax.inject.Inject;

import play.libs.Json;
import play.mvc.Result;

import com.github.drashid.api.ApiOp;
import com.github.drashid.api.Async;
import com.github.drashid.metric.MetricGateway;
import com.yammer.metrics.annotation.Timed;

@Async
public class AdminMetrics extends ApiOp {

  @Inject
  private MetricGateway metrics;
  
  @Override
  @Timed
  public Result call() {
    try {
      return ok(Json.toJson(metrics.getMetrics()));
    } catch (Exception e){
      return internalServerError(e.getMessage());
    }
  }

}
