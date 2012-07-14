import static module.GuiceHolder.injector;

import java.util.List;

import javax.inject.Inject;

import play.Application;
import play.GlobalSettings;

import com.github.drashid.config.InvalidConfigurationException;
import com.github.drashid.service.Service;

public class Global extends GlobalSettings {

  private static final String CONFIG_ENV = "env";
  
  @Inject
  private List<Service> services;
  
  @Override
  public void onStart(Application app) {
    validateAppConfig(app);
    injector().injectMembers(this);
    
    startServices();
  }

  private static void validateAppConfig(Application app) {
    String env = app.configuration().getString(CONFIG_ENV);
    if( (app.isDev() && !env.equals("dev")) || (app.isTest() && !env.equals("test")) || (app.isProd() && !env.equals("prod"))){
      throw new InvalidConfigurationException("Environment does not match!");
    }
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
