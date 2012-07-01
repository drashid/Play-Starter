package module;

import java.util.List;

import javax.inject.Singleton;

import com.github.drashid.jobs.ScheduledTask;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class ScheduledTaskModule extends AbstractModule {

  @Override
  protected void configure() {
    
  }

  @Provides
  @Singleton
  public List<? extends ScheduledTask> getTasks() {
    return Lists.newArrayList();
  }
  
}
