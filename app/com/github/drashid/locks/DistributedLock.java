package com.github.drashid.locks;

import java.util.concurrent.TimeUnit;


public interface DistributedLock {

  public boolean tryLock(long ttl, TimeUnit unit);
  
  public boolean releaseLock();
  
}
