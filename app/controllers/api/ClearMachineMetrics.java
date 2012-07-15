package controllers.api;

import javax.inject.Inject;

import org.codehaus.jackson.JsonNode;

import play.mvc.Result;

import com.github.drashid.api.ApiOp;
import com.github.drashid.api.Async;
import com.github.drashid.metric.MetricGateway;
import com.yammer.metrics.annotation.Timed;

@Async
public class ClearMachineMetrics extends ApiOp {

  @Inject
  private MetricGateway metrics;
  
  @Override
  @Timed
  public Result call() {
    try {
      JsonNode body = request().body().asJson();
      String machine = body.get("machine").asText();
      
      metrics.clearMetrics(machine);
      return ok();
    } catch (Exception e){
      return internalServerError(e.getMessage());
    }
  }

}
