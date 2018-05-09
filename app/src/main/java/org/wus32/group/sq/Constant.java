package org.wus32.group.sq;

import android.os.Environment;

import java.io.File;

/**
 * StatesQuiz
 * <p>
 * Created by Wu Shuang on 2016/10/25.
 */

public final class Constant {

  public static String ROOT = Environment.getExternalStorageDirectory() +
          File.separator + "sq";

  public static String FLAG = ROOT + File.separator + "flag";

  public static String LOC = ROOT + File.separator + "loc";
}
