import java.util.List;

import javax.inject.Inject;

import module.ApplicationModule;
import play.Application;
import play.GlobalSettings;

import com.github.drashid.service.Service;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Global extends GlobalSettings {
  
  private static Injector inject;

  @Inject
  private List<Service> services;

  public static Injector getInjector() {
    return inject;
  }

  @Override
  public void onStart(Application app) {
    inject = Guice.createInjector(new ApplicationModule());
    inject.injectMembers(this);

    startServices();
  }

  private void startServices() {
    for (Service s : services) {
      s.start();
    }
  }

  @Override
  public void onStop(Application app) {
    stopServices();
  }

  private void stopServices() {
    for (Service s : services) {
      s.stop();
    }
  }

}
