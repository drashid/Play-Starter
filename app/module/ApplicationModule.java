package module;

import com.google.inject.AbstractModule;

public class ApplicationModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new ControllerModule());
    install(new ScheduledTaskModule());
  }

}
