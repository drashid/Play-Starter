import static module.GuiceHolder.injector;

import java.util.List;

import javax.inject.Inject;

import play.Application;
import play.GlobalSettings;

import com.github.drashid.service.Service;
import com.yammer.metrics.HealthChecks;
import com.yammer.metrics.Metrics;
import com.yammer.metrics.core.HealthCheck;

public class Global extends GlobalSettings {
  
  @Inject
  private List<Service> services;
  
  @Inject
  private List<HealthCheck> checks;
  
  @Override
  public void onStart(Application app) {
    injector().injectMembers(this);
    
    startServices();
    registerHealthChecks();
  }

  private void registerHealthChecks() {
    for(HealthCheck check : checks){
      HealthChecks.register(check);
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
    Metrics.shutdown();
  }

  private void stopServices() {
    for (Service service : services) {
      service.stop();
    }
  }
  
}
