import static module.GuiceHolder.injector;

import java.util.List;

import javax.inject.Inject;

import play.Application;
import play.GlobalSettings;

import com.github.drashid.service.Service;

public class Global extends GlobalSettings {

  @Inject
  private List<Service> services;

  @Override
  public void onStart(Application app) {
    injector().injectMembers(this);

    startServices();
  }

  private void startServices() {
    for (Service service : services) {
      service.start();
    }
  }

  @Override
  public void onStop(Application app) {
    stopServices();
  }

  private void stopServices() {
    for (Service service : services) {
      service.stop();
    }
  }

}
