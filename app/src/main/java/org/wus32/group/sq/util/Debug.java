package org.wus32.group.sq.util;

import android.util.Log;

/**
 * 调试信息输出类
 *
 * @author WuShuang
 * @createtime 2012-4-28 下午04:10:55
 */
public class Debug {

  public static String TAG = "StateQuiz";

  /**
   * 应用初始化时调用，设置TAG
   *
   * @param tag
   * @author WuShuang
   * @createtime 2014-2-18 上午09:36:18
   */
  public static void init(String tag) {
    TAG = tag;
  }

  /**
   * 以默认info级别打印日志，
   * 发布时需注释方法体
   *
   * @param obj
   * @author WuShuang
   * @createtime 2012-3-13 上午11:02:27
   */
  public static void log(Object obj) {
    if (obj == null) {
      Log.i(TAG,"null");
    } else {
      Log.i(TAG,obj.toString());
    }
  }
}
