package controllers.api.admin.info;

import javax.inject.Inject;

import play.libs.Json;
import play.mvc.Result;

import com.github.drashid.api.ApiOp;
import com.github.drashid.status.ServerStatusGateway;
import com.yammer.metrics.annotation.Timed;

public class HealthChecks extends ApiOp {

  @Inject
  private ServerStatusGateway gateway;

  @Override
  @Timed
  public Result call() throws Exception {
    return ok(Json.toJson(gateway.getHealthChecks().values()));
  }

}
