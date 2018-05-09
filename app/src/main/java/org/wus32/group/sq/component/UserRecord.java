package org.wus32.group.sq.component;

import android.content.Context;
import android.content.SharedPreferences;

import org.wus32.group.sq.Constant;

/**
 * Record users' data.
 * In this stage,just record which state users are learning.
 * <p>
 * Created by Kyle on 2016/10/30.
 */
public class UserRecord {

  /**
   * An Android componet used to storage application data.
   */
  private SharedPreferences sp;

  /**
   * The key of the content which will be stored.
   * The content is index of current state.
   */
  public static final String CURRENT_INDEX = "current_index";

  public UserRecord(Context context) {
    //Generate a SharedPreferences,if it exsits,then just open it.
    sp = context.getSharedPreferences(Constant.SP_NAME,Context.MODE_PRIVATE);
  }

  /**
   * Get current index.
   *
   * @return Current index.
   */
  public int getCurrentIndex() {
    //If there is no value,return the default value 0.
    return sp.getInt(CURRENT_INDEX,0);
  }

  /**
   * Store current index.
   *
   * @param value Current index.
   */
  public void saveCurrentIndex(int value) {
    SharedPreferences.Editor editor = sp.edit();
    //Commit the storage.
    editor.putInt(CURRENT_INDEX,value).commit();
  }
}
