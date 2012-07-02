package controllers;

import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.With;
import views.html.index;

import com.github.drashid.action.InjectedAction;


public class Index extends Controller {

  @With(IndexAction.class)
  public static Result index() {
    return notFound();
  }

  public static class IndexAction extends InjectedAction {
    @Override
    public Result _call(Context context) {
      return ok(index.render("I'm Injected!"));
    }
  }
}