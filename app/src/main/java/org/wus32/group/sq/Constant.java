package org.wus32.group.sq;

import android.os.Environment;

import java.io.File;

/**
 * System constants.
 * <p>
 * Created by Wu Shuang on 2016/10/25.
 */
public final class Constant {

  /**
   * The root of file system.
   */
  public static String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() +
          File.separator + "sq";

  /**
   * The path where all the flag images stored.
   */
  public static String FLAG = ROOT + File.separator + "flag" + File.separator;

  /**
   * The path where all the location map images stored.
   */
  public static String LOC = ROOT + File.separator + "loc" + File.separator;

  /**
   * The cache key of the states list.
   */
  public static String STATES_CACHE = "states";

  /**
   * The cache key of the wrong answer list.
   */
  public static String WRONGS_CACHE = "wrongs";

  /**
   * The name of shared preference.
   */
  public static String SP_NAME = "sq";
  
  public static String APP_ID = "ca-app-pub-9267819544938806~3015730178";
  
//public static String APP_ID = "ca-app-pub-3940256099942544~3347511713";
}
