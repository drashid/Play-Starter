package controllers;

import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.With;

import com.github.drashid.action.InjectedAction;

public class Index extends Controller {

  @With(IndexAction.class)
  public static Result index() {
    return notFound();
  }

  public static Result main() {
//    return ok(views.html.main.render("Titley", Html.empty()));
    return notFound();
  }
  
  public static class IndexAction extends InjectedAction {

    @Override
    public Result _call(Context context) {
      return notFound();
//      return ok(index.render("I'm Injected!"));
    }
  }
}