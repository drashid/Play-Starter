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
    GuiceHolder.injector().injectMembers(this);

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
