package controllers.api.admin.info;

import javax.inject.Inject;

import play.mvc.Result;

import com.github.drashid.api.ApiOp;
import com.github.drashid.status.ServerStatusGateway;
import com.github.drashid.utils.json.JsonUtils;
import com.yammer.metrics.annotation.Timed;

public class HealthChecks extends ApiOp {

  @Inject
  private ServerStatusGateway gateway;

  @Override
  @Timed
  public Result call() throws Exception {
    return ok(JsonUtils.toJson(gateway.getHealthChecks().values()));
  }

}
