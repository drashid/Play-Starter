package com.github.drashid.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class MachineUtils {

  public static String getMachineCode() {
    try {
      InetAddress addr = InetAddress.getLocalHost();
      byte[] mac = NetworkInterface.getByInetAddress(addr).getHardwareAddress();
      
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < mac.length; i++) {
        sb.append(String.format("%02X", mac[i]));
      }
      return sb.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
