package org.wus32.group.sq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import org.wus32.group.sq.app.MyApplication;
import org.wus32.group.sq.component.dialog.ProgressRoundDialog;

import java.io.Serializable;

/**
 * Base Activity,build up standard for subclass.And support methods
 * as follows:
 * 1,Offering convenient method for subclasses.
 * 2,Building up a standard.
 * 3,Wrapping ofen-using methods of Activity.
 *
 * @author WuShuang
 */
public class BaseActivity extends Activity {

  /**
   * A reference of itself.
   */
  protected BaseActivity activity = this;

  /**
   * A reference of customer Application.
   */
  protected MyApplication app;

  /**
   * Round progress
   */
  private ProgressRoundDialog roundProgress;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    app = (MyApplication)getApplication();
    super.onCreate(savedInstanceState);
    //Regist to Application
    app.registe(this);
    roundProgress = new ProgressRoundDialog(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    dismissProgress();
    //Unregister this activity from appliaction.
    app.remove(this);
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    dismissProgress();
  }

  /**
   * Using layout XML to generate layout View.
   *
   * @param resId Id of the XML file.
   * @return View
   * @author WuShuang
   */
  public View makeView(int resId) {
    LayoutInflater inflater = LayoutInflater.from(this);
    return inflater.inflate(resId,null);
  }

  /**
   * Show information by Toast.
   *
   * @param obj What to show.
   * @author WuShuang
   */
  public void toast(Object obj) {
    toast(obj,0);
  }

  /**
   * Show information by Toast.
   *
   * @param obj What to show.
   * @param dur How long to show.
   * @author WuShuang
   */
  public void toast(Object obj,int dur) {
    Toast.makeText(this,(obj == null) ? "null" : obj.toString(),dur).show();
  }

  /**
   * A warpped method for jumping to another activity.
   *
   * @param cls The class of target Activity.
   * @author WuShuang
   */
  public void skip(Class<? extends Activity> cls) {
    startActivity(new Intent(this,cls));
  }

  /**
   * A warpped method for jumping to another activity.
   * And can carry data to the target activity.
   *
   * @param cls      The class of target Activity.
   * @param serializ The data wiil be sent to target activity.
   * @author WuShuang
   */
  public void skip(Class<? extends Activity> cls,Serializable... serializ) {
    Intent intent = new Intent(this,cls);
    Bundle extras = new Bundle();
    for (int i = 0;i < serializ.length;i++) {
      Serializable s = serializ[i];
      //The rule of putting data,using the order as the key.
      extras.putSerializable(Integer.toString(i),s);
    }
    intent.putExtras(extras);
    startActivity(intent);
  }

  /**
   * Show the round progress.
   *
   * @author WuShuang
   */
  public void showRoundProgress() {
    if (!roundProgress.isShowing()) {
      roundProgress.show();
    }
  }

  /**
   * Dismiss the round progress.
   *
   * @author WuShuang
   */
  public void dismissProgress() {
    if (roundProgress.isShowing()) {
      roundProgress.dismiss();
    }
  }
}
