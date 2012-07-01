package module;

import com.github.drashid.service.ServiceModule;
import com.github.drashid.tasks.ScheduledTaskModule;
import com.google.inject.AbstractModule;
import com.yammer.metrics.guice.InstrumentationModule;

import controllers.ControllerModule;

public class ApplicationModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new ControllerModule());
    install(new ScheduledTaskModule());
    install(new ServiceModule());
    install(new InstrumentationModule());
  }

}
