package org.wus32.group.sq.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.wus32.group.sq.R;

/**
 * In this page users will the the result of quiz.
 * <p>
 * Created by Kyle on 2016/10/31.
 */
public class Result extends BaseActivity {

  /**
   * The percentage of correct answers.
   */
  private float per;

  /**
   * View to display information.
   */
  private TextView title, right, wrong;

  /**
   * If the percentage of correctness is greater than this number,
   * the result is good.
   */
  public static final float GOOD = 0.5f;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_result);
    //Get the data from previous activity.
    int right = Integer.valueOf(getIntent().getExtras().get("0").toString());
    int wrong = Integer.valueOf(getIntent().getExtras().get("1").toString());
    //Calculating the percentage.
    per = (float)right / (float)Quiz.QUESTION_NUMBER;
    initializeViews();
    setViewValue(right,wrong);
  }

  /**
   * Filling the views with object's value.
   */
  private void setViewValue(int right,int wrong) {
    if (per > GOOD) {
      title.setText(R.string.result_good);
    } else {
      title.setText(R.string.result_bad);
    }
    this.right.setText(right + "");
    this.wrong.setText(wrong + "");
  }

  /**
   * Get view from content view.
   */
  private void initializeViews() {
    title = (TextView)findViewById(R.id.result_title);
    right = (TextView)findViewById(R.id.result_correct);
    wrong = (TextView)findViewById(R.id.result_uncorrect);
    //To view which question with the wrong answers.
    wrong.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        skip(Wrong.class);
      }
    });
  }

  /**
   * Jump to other activity.
   *
   * @param v Which view is clicked.
   */
  public void skip(View v) {
    int id = v.getId();
    switch (id) {
      //Relearn.
      case R.id.portal_learn:
        skip(Learn.class);
        break;
      //Restart a quiz.
      case R.id.portal_quiz:
        skip(Quiz.class);
        break;
    }
    finish();
  }
}
