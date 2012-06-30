package controllers;

import javax.inject.Inject;

import play.mvc.Controller;
import play.mvc.Result;
import service.TestService;
import views.html.index;

public class Application extends Controller {
  
  @Inject static TestService serv;
  
  public static Result index() {
    String str = "" + serv.hashCode();
    return ok(index.render("Your new application is ready !!!! " + str));
  }
  
}