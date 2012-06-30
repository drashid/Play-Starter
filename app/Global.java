import module.ApplicationModule;
import play.Application;
import play.GlobalSettings;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Global extends GlobalSettings {

  private static Injector inject;
  
  public static Injector getInjector() {
    return inject;
  }
  
  @Override
  public void onStart(Application app) {
    inject = Guice.createInjector(new ApplicationModule());
  }
  
}
