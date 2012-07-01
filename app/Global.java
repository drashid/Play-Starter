import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import module.ApplicationModule;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import akka.actor.Cancellable;

import com.github.drashid.jobs.ScheduledTask;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Global extends GlobalSettings {

  private static Injector inject;

  private List<Cancellable> scheduledJobs = new ArrayList<Cancellable>();

  @Inject
  private List<? extends ScheduledTask> tasks;

  public static Injector getInjector() {
    return inject;
  }

  @Override
  public void onStart(Application app) {
    inject = Guice.createInjector(new ApplicationModule());
    inject.injectMembers(this);

    startServices();
    startJobs();
  }

  private void startServices() {

  }

  private void startJobs() {
    for (ScheduledTask task : tasks) {
      scheduledJobs.add(Akka.system().scheduler().schedule(task.getInitialDelay(), task.getFrequency(), task));
    }
  }

  @Override
  public void onStop(Application app) {
    cancelJobs();
    stopServices();
  }

  private void stopServices() {

  }

  private void cancelJobs() {
    for (Cancellable running : scheduledJobs) {
      running.cancel(); //Warning: does not shut down the running task, just future schedules
    }
  }

}
