package org.wus32.group.sq.activity;

import android.os.Bundle;
import android.view.View;

import org.wus32.group.sq.R;

/**
 * The portal of this app,in this page users will choose to learn or
 * take a quiz.
 * <p>
 * Created by Mickey on 2016/10/25.
 */
public class Portal extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_portal);
  }

  /**
   * Equals to set onClickListener.
   * Set the actions when the view is clciked.
   *
   * @param v The view which is clicked.
   */
  public void skip(View v) {
    //Get the id of clciked view.
    int id = v.getId();
    switch (id) {
      case R.id.portal_learn:
        //Jumping to learn page.
        skip(Learn.class);
        break;
      case R.id.portal_quiz:
        //Jumping to quiz page.
        skip(Quiz.class);
        break;
//      case R.id.portal_about:
//        //Jumping to about page.
//        skip(About.class);
//        break;
    }
  }
}
