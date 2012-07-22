import static module.GuiceHolder.injector;

import java.util.List;

import javax.inject.Inject;

import play.Application;
import play.GlobalSettings;

import com.github.drashid.config.InvalidConfigurationException;
import com.github.drashid.service.Service;
import com.yammer.metrics.HealthChecks;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.HealthCheck;

public class Global extends GlobalSettings {

  private static final String CONFIG_ENV = "env";
  
  @Inject
  private List<Service> services;
  
  @Inject
  private List<HealthCheck> checks;
  
  @Override
  public void onStart(Application app) {
    validateAppConfig(app);
    injector().injectMembers(this);
    
    startServices();
    registerHealthChecks();
  }

  private void registerHealthChecks() {
    for(HealthCheck check : checks){
      HealthChecks.register(check);
    }    
  }

  private static void validateAppConfig(Application app) {
    //TODO put back
//    String env = app.configuration().getString(CONFIG_ENV);
//    if( (app.isDev() && !env.equals("dev")) || (app.isTest() && !env.equals("test")) || (app.isProd() && !env.equals("prod"))){
//      throw new InvalidConfigurationException("Environment does not match!");
//    }
  }

  private void startServices() {
    for (Service service : services) {
      service.start();
    }
  }

  @Override
  public void onStop(Application app) {
    stopServices();    
    Metrics.shutdown();
  }

  private void stopServices() {
    for (Service service : services) {
      service.stop();
    }
  }
  
}
