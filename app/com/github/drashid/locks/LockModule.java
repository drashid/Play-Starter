package com.github.drashid.locks;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class LockModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new FactoryModuleBuilder()
      .implement(DistributedLock.class, RedisOptimisticLock.class)
      .build(DistributedLockFactory.class));
  }

}
