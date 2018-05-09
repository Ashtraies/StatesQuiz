package org.wus32.group.sq.util;

import android.util.Log;

/**
 * Debug output tool.
 *
 * @author Stanley
 */
public class Debug {

  /**
   * The tag of output information.
   */
  public static String TAG = "StateQuiz";

  /**
   * Log in info level.
   *
   * @param obj
   */
  public static void log(Object obj) {
    if (obj == null) {
      Log.i(TAG,"null");
    } else {
      Log.i(TAG,obj.toString());
    }
  }
}
