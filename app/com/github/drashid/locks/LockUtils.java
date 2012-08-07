package com.github.drashid.locks;

import java.util.UUID;

import com.github.drashid.utils.MachineUtils;

class LockUtils {

  public static String genMachineMarkedUUID() {
    return MachineUtils.getMachineName() + "::" + UUID.randomUUID().toString();
  }

}
