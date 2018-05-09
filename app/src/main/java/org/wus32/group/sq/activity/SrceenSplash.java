package org.wus32.group.sq.activity;

import android.os.Bundle;

import org.wus32.group.sq.R;
import org.wus32.group.sq.task.InitializeTask;

public class SrceenSplash extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_srcenn_splash);
    new InitializeTask(this).execute();
  }
}
