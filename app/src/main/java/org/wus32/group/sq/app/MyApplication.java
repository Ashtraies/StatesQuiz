package org.wus32.group.sq.app;

import android.app.Activity;
import android.app.Application;
import android.util.LruCache;

import com.google.android.gms.ads.MobileAds;

import org.wus32.group.sq.Constant;

import java.util.Vector;

/**
 * Custom application,should be defined in Manifest file.
 *
 * @author WuShuang
 */
public class MyApplication extends Application {

  /**
   * The collection of activities.
   */
  public Vector<Activity> stack;

  @Override
  public void onCreate() {
    super.onCreate();
    stack = new Vector<>();
    MobileAds.initialize(this,Constant.APP_ID);
  }


  /**
   * Get current Activity.
   *
   * @return Current activity which shows.
   * @author WuShuang
   */
  public Activity currActivity() {
    if (stack != null && stack.size() > 0) {
      Activity a = stack.get(stack.size() - 1);
      return a;
    }
    return null;
  }

  /**
   * After a activity has been created,regist it.
   *
   * @param activity The new activity.
   * @author WuShuang
   */
  public void registe(Activity activity) {
    stack.add(activity);
  }

  /**
   * When a activity is destoryed,remove it.
   *
   * @param activity
   * @author WuShuang
   */
  public void remove(Activity activity) {
    stack.remove(activity);
  }

  /**
   * Finish all the activities.
   *
   * @author WuShuang
   */
  public void finishAll() {
    for (Activity a : stack) {
      a.finish();
    }
  }

  /**
   * Global memory cache.
   * Using strings as cache key which are defined in Constant.
   *
   * @author WuShuang
   */
  public static class MemoryCache {

    /**
     * LruCache.
     */
    private final static LruCache<Object, Object> CACHE = new LruCache(1024);

    /**
     * Storage.
     *
     * @param key   The cache key.
     * @param cache The content,could be any object.
     * @author WuShuang
     */
    public static void putCache(Object key,Object cache) {
      CACHE.put(key,cache);
    }

    /**
     * Get.
     *
     * @param key The cache key.
     * @return The cache content.
     * @author WuShuang
     */
    public static Object getCache(Object key) {
      return CACHE.get(key);
    }

    /**
     * Remove a spectific cache.
     *
     * @param key The cache key.
     * @author WuShuang
     */
    public static Object remove(Object key) {
      return CACHE.remove(key);
    }
  }
}
