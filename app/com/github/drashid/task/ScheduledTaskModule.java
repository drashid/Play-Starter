package com.github.drashid.task;

import java.util.List;
import java.util.Set;

import javax.inject.Singleton;

import org.reflections.Reflections;

import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

public class ScheduledTaskModule extends AbstractModule {

  private static final String TASK_PACKAGE_ROOT = "com.github.drashid.task.impl";

  @Override
  protected void configure() {

  }

  @Provides
  @Singleton
  public List<ScheduledTask> getTasks(Injector inject) {
    Reflections refs = new Reflections(TASK_PACKAGE_ROOT);
    Set<Class<? extends ScheduledTask>> taskClsz = refs.getSubTypesOf(ScheduledTask.class);
    List<ScheduledTask> tasks = Lists.newArrayList();
    for (Class<? extends ScheduledTask> taskCls : taskClsz) {
      tasks.add(inject.getInstance(taskCls));
    }
    return tasks;
  }

}
