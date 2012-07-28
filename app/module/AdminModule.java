package module;

import com.github.drashid.status.health.HealthModule;
import com.google.inject.AbstractModule;


public class AdminModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new HealthModule());
  }

}
