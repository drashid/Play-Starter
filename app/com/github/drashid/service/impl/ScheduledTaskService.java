package com.github.drashid.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import play.libs.Akka;
import akka.actor.Cancellable;

import com.github.drashid.service.AbstractService;
import com.github.drashid.tasks.ScheduledTask;

@Singleton
public class ScheduledTaskService extends AbstractService {
  
  private List<Cancellable> scheduledTasks = new ArrayList<Cancellable>();

  @Inject
  private List<ScheduledTask> tasks;

  @Override
  protected void startService() {
    startTasks();
  }
  
  @Override
  protected void stopService() {
    stopTasks();
  }
  
  private void startTasks() {
    for (ScheduledTask task : tasks) {
      scheduledTasks.add(Akka.system().scheduler().schedule(task.getInitialDelay(), task.getFrequency(), task));
    }
  }

  private void stopTasks() {
    for (Cancellable running : scheduledTasks) {
      running.cancel(); // Warning: does not shut down the running task, just future schedules
    }
    scheduledTasks.clear();
  }

}
