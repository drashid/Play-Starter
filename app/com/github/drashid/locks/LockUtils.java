package com.github.drashid.locks;

import java.util.UUID;

import com.github.drashid.utils.MachineUtils;

class LockUtils {

  public static String createMachineUUID() {
    return MachineUtils.getMachineName() + "::" + UUID.randomUUID().toString();
  }

  public static String getMachineFromMUUID(String machineUUID) {
    return machineUUID.substring(0, machineUUID.indexOf("::"));
  }

}
