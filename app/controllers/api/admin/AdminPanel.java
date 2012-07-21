package controllers.api.admin;

import play.mvc.Result;

import com.github.drashid.api.ApiOp;
import com.yammer.metrics.annotation.Metered;

public class AdminPanel extends ApiOp {

  @Override
  @Metered
  public Result call() throws Exception {
    return ok(views.html.admin.render(env));
  }

}
