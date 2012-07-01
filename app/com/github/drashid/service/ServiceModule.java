package com.github.drashid.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;

public class ServiceModule extends AbstractModule {

  @Override
  protected void configure() {

  }

  @Provides
  List<Service> getAllServices(Injector inject) {
    Reflections reflections = new Reflections("com.github.drashid.service");
    Set<Class<? extends AbstractService>> services = reflections.getSubTypesOf(AbstractService.class);

    List<Service> servInsts = new ArrayList<Service>();
    for (Class<? extends Service> servCls : services) {
      Service serv = inject.getInstance(servCls);
      servInsts.add(serv);
    }
    return servInsts;
  }
}
