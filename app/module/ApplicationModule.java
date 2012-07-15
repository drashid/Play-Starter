package module;

import com.github.drashid.config.ConfigModule;
import com.github.drashid.service.ServiceModule;
import com.github.drashid.task.ScheduledTaskModule;
import com.google.inject.AbstractModule;


public class ApplicationModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new ControllerModule());
    install(new ScheduledTaskModule());
    install(new ServiceModule());
    install(new ConfigModule());
    install(new AdminModule());
  }

}
