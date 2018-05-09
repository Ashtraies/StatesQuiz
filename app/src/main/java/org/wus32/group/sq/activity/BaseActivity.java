package org.wus32.group.sq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import org.wus32.group.sq.app.MyApplication;
import org.wus32.group.sq.component.TitleBar;
import org.wus32.group.sq.component.dialog.ProgressRoundDialog;
import org.wus32.group.sq.task.AbstractTask;

import java.io.Serializable;

/**
 * 基础类Activity，其中的抽象方法建立了一种规范。 主要提供两方面的支持：
 * 1、为子类提供了一些便利的方法。
 * 2、建立一种规范。
 * 3、封装了Actvity中常用的操作。 如Toast的展示，对话框的展示,Activity之间跳转。
 *
 * @author WuShuang
 * @createtime 2014-2-17 下午03:06:56
 */
public class BaseActivity extends Activity {

  /**
   * 对自身的引用
   */
  protected BaseActivity activity = this;

  /**
   * 对自定义Application的引用
   */
  protected MyApplication app;

  /**
   * 圆形进度对话框
   */
  private ProgressRoundDialog roundProgress;

  protected TitleBar title;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    app = (MyApplication)getApplication();
    super.onCreate(savedInstanceState);
    //注册到Application
    app.registe(this);
    // 实例化进度对话框
    roundProgress = new ProgressRoundDialog(this);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    dismissProgress();
    app.remove(this);
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    dismissProgress();
  }

  /**
   * 通过布局文件，生成View
   *
   * @param resId
   * @return
   * @author WuShuang
   * @createtime 2014-2-28 上午11:37:39
   */
  public View makeView(int resId) {
    LayoutInflater inflater = LayoutInflater.from(this);
    return inflater.inflate(resId,null);
  }

  /**
   * 显示浮现信息
   *
   * @param obj
   * @author WuShuang
   * @createtime 2012-3-13 上午11:01:52
   */
  public void toast(Object obj) {
    toast(obj,0);
  }

  /**
   * 显示浮现信息
   *
   * @param obj
   * @param dur 显示时间
   * @author WuShuang
   * @createtime 2012-3-13 上午11:02:07
   */
  public void toast(Object obj,int dur) {
    Toast.makeText(this,(obj == null) ? "null" : obj.toString(),dur).show();
  }

  /**
   * 简易跳转Activity的方法
   *
   * @param cls 目标Activity
   * @author WuShuang
   * @createtime 2012-3-19 上午10:35:17
   */
  public void skip(Class<? extends Activity> cls) {
    startActivity(new Intent(this,cls));
  }

  /**
   * 简易跳转Activity的方法
   *
   * @param action 动作，在intent-filter中定义
   * @author WuShuang
   * @createtime 2012-7-16 下午06:35:01
   */
  public void skip(String action) {
    startActivity(new Intent(action));
  }

  /**
   * 简易跳转Activity的方法
   *
   * @param action   动作，在intent-filter中定义
   * @param serializ 携带对象
   * @author WuShuang
   * @createtime 2012-7-16 下午06:35:01
   */
  public void skip(String action,Serializable... serializ) {
    Intent intent = new Intent(action);
    Bundle extras = new Bundle();
    for (int i = 0;i < serializ.length;i++) {
      Serializable s = serializ[i];
      //放对象的规则，以顺序为键
      extras.putSerializable(i + "",s);
    }
    intent.putExtras(extras);
    startActivity(intent);
  }

  /**
   * 携带对象，跳转activity
   *
   * @param cls      目标类
   * @param serializ 对象
   * @author WuShuang
   * @createtime 2012-4-16 下午02:37:39
   */
  public void skip(Class<? extends Activity> cls,Serializable... serializ) {
    Intent intent = new Intent(this,cls);
    Bundle extras = new Bundle();
    for (int i = 0;i < serializ.length;i++) {
      Serializable s = serializ[i];
      //放对象的规则，以顺序为键
      extras.putSerializable(i + "",s);
    }
    intent.putExtras(extras);
    startActivity(intent);
  }


  @Override
  public void setTitle(CharSequence title) {
    if (this.title != null) {
      this.title.setTitle(title.toString());
    }
  }

  /**
   * 显示圆形进度条对话框
   *
   * @author WangLong
   * @createtime 2014-5-29 下午3:14:10
   */
  public void showRoundProgress() {
    if (!roundProgress.isShowing()) {
      roundProgress.show();
    }
  }

  /**
   * 隐藏进度对话框
   *
   * @author WuShuang
   * @createtime 2012-4-16 下午02:49:26
   * 最后修改时间 :
   * 更新记录:
   */
  public void dismissProgress() {
    if (roundProgress.isShowing()) {
      roundProgress.dismiss();
    }
  }

  public void registerTask(AbstractTask<?, ?, ?>... task) {
    roundProgress.registerTask(task);
  }
}
