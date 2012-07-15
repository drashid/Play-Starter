package module;

import com.github.drashid.status.health.HealthModule;
import com.google.inject.AbstractModule;
import com.yammer.metrics.guice.InstrumentationModule;


public class AdminModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new InstrumentationModule());
    install(new HealthModule());
  }

}
