package controllers.api;

import play.mvc.Result;

import com.github.drashid.api.ApiOp;

public class AdminPanel extends ApiOp {

  @Override
  public Result call() throws Exception {
    return ok(views.html.admin.render());
  }

}
