package com.github.drashid.locks;

public interface DistributedLockFactory {

  public DistributedLock createLock(String key);

}
