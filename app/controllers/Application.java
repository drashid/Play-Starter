package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.github.drashid.service.AbstractService;

public class Application extends Controller {
  
  private static final Logger LOG = LoggerFactory.getLogger(AbstractService.class);
  
  public static Result index() {
    LOG.info("index() request");
    return ok(index.render("Your new application is ready!  Sick..."));
  }
  
}