package org.wus32.group.sq.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import org.wus32.group.sq.R;
import org.wus32.group.sq.task.InitializeTask;

/**
 * The first page when the app is been lauching.
 * In this activity,a initialize task will run to do initial work.
 *
 * @author Stanley
 */
public class SrceenSplash extends BaseActivity {
  
  private static final int WRITE_EXTERNAL_STORAGE = 1;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_srceen_splash);
    checkPermission();
  }
  
  private void checkPermission() {
    int permission = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
    if (permission != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this,
              new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
              WRITE_EXTERNAL_STORAGE);
    } else {
      //Start initialize task.
      new InitializeTask(this).execute();
    }
  }
  
  @Override
  public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults) {
    switch (requestCode) {
      case WRITE_EXTERNAL_STORAGE:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          //Start initialize task.
          new InitializeTask(this).execute();
        } else {
          toast("States flags and location map cannot be loaded,please try again.",1);
          finish();
        }
        return;
    }
  }
}
