package controllers;

import org.reflections.Reflections;

import play.mvc.Controller;

import com.google.inject.AbstractModule;

public class ControllerModule extends AbstractModule {

  @Override
  protected void configure() {
    // statically inject controllers
    Reflections reflect = new Reflections("controllers");
    requestStaticInjection(reflect.getSubTypesOf(Controller.class).toArray(new Class<?>[] {}));
  }
  
}
