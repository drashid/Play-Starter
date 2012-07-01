package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.github.drashid.action.Action;
import com.github.drashid.action.InjectedAction;
import com.github.drashid.service.AbstractService;
import com.yammer.metrics.annotation.Timed;

public class Application extends Controller {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractService.class);

  @Action(IndexAction.class)
  public static Result index() {
    return ok(index.render("Your new application is ready!  Sick...!"));
  }

  public static class IndexAction extends InjectedAction {
    @Override
    @Timed
    public Result call() throws Exception {
      return ok(index.render("I'm Injected!, Parent:" + this.delegate.getClass().getName()));
    }
  }
}