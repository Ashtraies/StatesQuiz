/*
 * 创建日期 2014-2-17
 * 
 * 成都天和软件公司
 * 电话：028-85425861
 * 传真：028-85425861-8008
 * 邮编：610041
 * 地址：成都市武侯区航空路6号丰德万瑞中心B座1001
 * 版权所有
 */
package org.wus32.group.sq.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 自定义Application，具体项目的Application继承自该类
 *
 * @author WuShuang
 * @createtime 2014-2-17 上午11:36:26
 */
public class MyApplication extends Application {

  /**
   * 当前Activity集合，由Application统一管理
   */
  public Vector<Activity> stack;

  /**
   * 应用包名
   */
  public String packageName;

  /**
   * 应用版本名
   */
  public String versionName;

  /**
   * 版本数字，与Manifest中不同，由versionName生成
   */
  public int versionCode;

  @Override
  public void onCreate() {
    super.onCreate();
    stack = new Vector<Activity>();
  }


  /**
   * 获取当前Activity
   *
   * @return
   * @author WuShuang
   * @createtime 2014-2-17 上午11:48:55
   */
  public Activity currActivity() {
    if (stack != null && stack.size() > 0) {
      Activity a = stack.get(stack.size() - 1);
      return a;
    }
    return null;
  }

  /**
   * Activity创建后，进行注册
   *
   * @param activity
   * @author WuShuang
   * @createtime 2014-2-17 上午11:49:52
   */
  public void registe(Activity activity) {
    this.stack.add(activity);
  }

  /**
   * Activity销毁后，进行移除
   *
   * @param activity
   * @author WuShuang
   * @createtime 2014-2-17 上午11:50:45
   */
  public void remove(Activity activity) {
    this.stack.remove(activity);
  }

  /**
   * 终止所有Activity
   *
   * @author WuShuang
   * @createtime 2014-2-17 下午01:37:52
   */
  public void finishAll() {
    for (Activity a : stack) {
      a.finish();
    }
  }

  /**
   * 完全退出当前程序
   *
   * @author WuShuang
   * @createtime 2014-2-17 下午01:39:58
   */
  public void exit() {
    finishAll();
    System.gc();
    // 跳转至home界面
    Intent home = new Intent(Intent.ACTION_MAIN);
    home.addCategory(Intent.CATEGORY_HOME);
    home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(home);
  }

  /**
   * 全局缓存，缓存可能会被系统回收，取值时应判断。
   *
   * @author WuShuang
   * @createtime 2012-3-24 上午11:47:04
   */
  public static class MemoryCache {

    /**
     * 缓存
     */
    private final static Map<Object, Object> CACHE = new HashMap<Object, Object>();

    /**
     * 存放
     *
     * @param key
     * @param cache
     * @author WuShuang
     * @createtime 2012-5-5 上午10:02:29
     */
    public static void putCache(Object key,Object cache) {
      CACHE.put(key,cache);
    }

    /**
     * 取出
     *
     * @param key
     * @return
     * @author WuShuang
     * @createtime 2012-5-5 上午10:02:37
     */
    public static Object getCache(Object key) {
      return CACHE.get(key);
    }

    /**
     * 清除指定缓存
     *
     * @param key
     * @author WuShuang
     * @createtime 2014-7-29 上午10:07:13
     */
    public static Object remove(Object key) {
      return CACHE.remove(key);
    }
  }
}
