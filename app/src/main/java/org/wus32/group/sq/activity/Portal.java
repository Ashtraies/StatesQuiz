package org.wus32.group.sq.activity;

import android.os.Bundle;
import android.view.View;

import org.wus32.group.sq.R;

/**
 * StatesQuiz
 * <p>
 * Created by Wu Shuang on 2016/10/25.
 */
public class Portal extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_portal);
  }

  public void skip(View v) {
    int id = v.getId();
    switch (id) {
      case R.id.portal_learn:
        skip(Learn.class);
        break;
      case R.id.portal_quiz:
        skip(Quiz.class);
        break;
      case R.id.portal_about:
        skip(About.class);
        break;
    }
  }
}
