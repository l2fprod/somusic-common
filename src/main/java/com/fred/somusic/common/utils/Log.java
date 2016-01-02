package com.fred.somusic.common.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {

  private static Logger logger = Logger.getLogger("somusic");

  public static void info(String tag, Object o) {
    System.out.println("[" + tag + "] " + String.valueOf(o)); //logger.info(String.valueOf(o));
  }

  public static void severe(String tag, Object o) {
    System.err.println("[" + tag + "] " + String.valueOf(o));
  }

  public static void fine(Object o) {
    //System.out.println(String.valueOf(o));
  }
  
  public static boolean isFine() {
    return logger.isLoggable(Level.FINE);
  }

  public static void warning(String tag, Object o) {
    logger.warning("[" + tag + "] " + String.valueOf(o));
  }

}
