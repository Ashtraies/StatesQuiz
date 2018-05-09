package org.wus32.group.sq.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.ads.AdView;

import org.wus32.group.sq.Constant;
import org.wus32.group.sq.R;
import org.wus32.group.sq.app.MyApplication;
import org.wus32.group.sq.pojo.Question;
import org.wus32.group.sq.util.AdHelper;

import java.util.List;

import static org.wus32.group.sq.activity.Learn.THRESHOLD;

/**
 * In this page,users could check the questions they gave the wrong answers.
 * <p>
 * <p>
 * Created by Tom on 2016/11/10.
 */
public class Wrong extends BaseActivity {

  /**
   * To show the textual information.
   */
  private TextView quizIndex;

  /**
   * The two types of quiz view.
   * i2n;choosing state name by image.
   * n2i;choosing image by state name.
   */
  private View i2n, n2i;

  /**
   * To show the flag or location map.
   */
  private ImageView quizImage;

  /**
   * Quiz title and state name.
   */
  private TextView title, name;

  /**
   * Four images of potential answer.
   */
  private ImageView n2ia, n2ib, n2ic, n2id;

  /**
   * Four textview of potential answer.
   */
  private TextView i2na, i2nb, i2nc, i2nd;

  /**
   * The container of quiz view.
   */
  private ViewFlipper main;

  /**
   * Arrow icon.
   */
  private View leftArrow, rightArrow;
  
  private AdView adView;

  /**
   * The index of the correct answer of the four potential answers.
   */
  private int correctIndex = -1;

  /**
   * The index of current questions.
   */
  private int currentIndex;

  /**
   * The X coordinates of touch down event and touch up event.
   */
  private float touchDownX, touchUpX;

  /**
   * A list of questions which users answered uncorrectly.
   */
  private List<Question> wrongs;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wrong);
    wrongs = (List<Question>)MyApplication.MemoryCache.getCache(Constant.WRONGS_CACHE);
    initializeViews();
    bindingListener();
    setViewValue();
  }

  /**
   * Show next state.
   */
  private void next() {
    //Get the next index.
    currentIndex++;
    //If it is the last one,return to the first one.
    if (currentIndex == wrongs.size()) {
      currentIndex = 0;
    }
    //Show the new data.
    setViewValue();
    //Setting the animation of changing view.
    main.setInAnimation(AnimationUtils.loadAnimation(Wrong.this,
            R.anim.left_in));
    main.setOutAnimation(AnimationUtils.loadAnimation(Wrong.this,
            R.anim.left_out));
    //Show the next view.
    main.showNext();
  }

  /**
   * Show previous state.
   */
  private void previous() {
    //Get the next index.
    currentIndex--;
    if (currentIndex == -1) {
      //If it is the first one,return to the last one.
      currentIndex = wrongs.size() - 1;
    }
    //Show the new data.
    setViewValue();
    //Setting the animation of changing view.
    main.setInAnimation(AnimationUtils.loadAnimation(Wrong.this,
            R.anim.right_in));
    main.setOutAnimation(AnimationUtils.loadAnimation(Wrong.this,
            R.anim.right_out));
    //Show the previous view.
    main.showPrevious();
  }

  /**
   * Load quiz view.
   *
   * @param v Which quiz view to load.
   */
  private void loadQuizView(View v) {
    //Must remove all the child views from the container.
    main.removeAllViews();
    //Adding view.
    main.addView(v);
  }

  /**
   * Filling the views with object's value.
   * When a new question is loaded,this method should be called.
   */
  public void setViewValue() {
    AdHelper.LoadAd(adView);
    //Get the current question from quesitons list.
    Question question = wrongs.get(currentIndex);
    correctIndex = question.getCorrectIndex();
    quizIndex.setText((currentIndex + 1) + "/" + wrongs.size());
    switch (question.getType()) {
      case N2F:
        //Load the quiz view first.
        loadQuizView(n2i);
        title.setText(R.string.quiz_title_n2f);
        name.setText(question.getStates().get(correctIndex).getName());
        //Every answer has a index,as tag setted to the view.
        n2ia.setImageBitmap(question.getStates().get(0).getFlagImage());
        n2ia.setTag(0);
        n2ib.setImageBitmap(question.getStates().get(1).getFlagImage());
        n2ib.setTag(1);
        n2ic.setImageBitmap(question.getStates().get(2).getFlagImage());
        n2ic.setTag(2);
        n2id.setImageBitmap(question.getStates().get(3).getFlagImage());
        n2id.setTag(3);
        break;
      case N2L:
        //Load the quiz view first.
        loadQuizView(n2i);
        title.setText(R.string.quiz_title_n2l);
        name.setText(question.getStates().get(correctIndex).getName());
        //Every answer has a index,as tag setted to the view.
        n2ia.setImageBitmap(question.getStates().get(0).getLocImage());
        n2ia.setTag(0);
        n2ib.setImageBitmap(question.getStates().get(1).getLocImage());
        n2ib.setTag(1);
        n2ic.setImageBitmap(question.getStates().get(2).getLocImage());
        n2ic.setTag(2);
        n2id.setImageBitmap(question.getStates().get(3).getLocImage());
        n2id.setTag(3);
        break;
      case L2N:
        //Load the quiz view first.
        loadQuizView(i2n);
        quizImage.setImageBitmap(question.getStates().get(correctIndex).getLocImage());
        //Every answer has a index,as tag setted to the view.
        i2na.setText(question.getStates().get(0).getName());
        i2na.setTag(0);
        i2nb.setText(question.getStates().get(1).getName());
        i2nb.setTag(1);
        i2nc.setText(question.getStates().get(2).getName());
        i2nc.setTag(2);
        i2nd.setText(question.getStates().get(3).getName());
        i2nd.setTag(3);
        break;
      case F2N:
        //Load the quiz view first.
        loadQuizView(i2n);
        quizImage.setImageBitmap(question.getStates().get(correctIndex).getFlagImage());
        //Every answer has a index,as tag setted to the view.
        i2na.setText(question.getStates().get(0).getName());
        i2na.setTag(0);
        i2nb.setText(question.getStates().get(1).getName());
        i2nb.setTag(1);
        i2nc.setText(question.getStates().get(2).getName());
        i2nc.setTag(2);
        i2nd.setText(question.getStates().get(3).getName());
        i2nd.setTag(3);
        break;
    }
    //Indentify the correct answer.
    highlightCorrectAnswer();
  }

  /**
   * High light the correct answer.
   */
  private void highlightCorrectAnswer() {
    switch (correctIndex) {
      case 0:
        i2na.setBackgroundColor(Color.LTGRAY);
        n2ia.setBackgroundColor(Color.LTGRAY);
        i2nb.setBackgroundColor(Color.TRANSPARENT);
        n2ib.setBackgroundColor(Color.TRANSPARENT);
        i2nc.setBackgroundColor(Color.TRANSPARENT);
        n2ic.setBackgroundColor(Color.TRANSPARENT);
        i2nd.setBackgroundColor(Color.TRANSPARENT);
        n2id.setBackgroundColor(Color.TRANSPARENT);
        break;
      case 1:
        i2nb.setBackgroundColor(Color.LTGRAY);
        n2ib.setBackgroundColor(Color.LTGRAY);
        i2na.setBackgroundColor(Color.TRANSPARENT);
        n2ia.setBackgroundColor(Color.TRANSPARENT);
        i2nc.setBackgroundColor(Color.TRANSPARENT);
        n2ic.setBackgroundColor(Color.TRANSPARENT);
        i2nd.setBackgroundColor(Color.TRANSPARENT);
        n2id.setBackgroundColor(Color.TRANSPARENT);
        break;
      case 2:
        i2nc.setBackgroundColor(Color.LTGRAY);
        n2ic.setBackgroundColor(Color.LTGRAY);
        i2na.setBackgroundColor(Color.TRANSPARENT);
        n2ia.setBackgroundColor(Color.TRANSPARENT);
        i2nb.setBackgroundColor(Color.TRANSPARENT);
        n2ib.setBackgroundColor(Color.TRANSPARENT);
        i2nd.setBackgroundColor(Color.TRANSPARENT);
        n2id.setBackgroundColor(Color.TRANSPARENT);
        break;
      case 3:
        i2nd.setBackgroundColor(Color.LTGRAY);
        n2id.setBackgroundColor(Color.LTGRAY);
        i2na.setBackgroundColor(Color.TRANSPARENT);
        n2ia.setBackgroundColor(Color.TRANSPARENT);
        i2nb.setBackgroundColor(Color.TRANSPARENT);
        n2ib.setBackgroundColor(Color.TRANSPARENT);
        i2nc.setBackgroundColor(Color.TRANSPARENT);
        n2ic.setBackgroundColor(Color.TRANSPARENT);
        break;
    }
  }

  /**
   * A placehold method.
   * But it's necessary,beacuse the view elements binding this method.
   *
   * @param v
   */
  public void choose(View v) {
    //No need to do anything.
  }

  /**
   * Getting all the view elements from content view.
   */
  private void initializeViews() {
    //Get the common views.
    leftArrow = findViewById(R.id.left_arrow);
    rightArrow = findViewById(R.id.right_arrow);
    i2n = makeView(R.layout.quiz_image2name_fragment);
    n2i = makeView(R.layout.quiz_name2image_fragment);
    main = (ViewFlipper)findViewById(R.id.main_content);
    quizIndex = (TextView)findViewById(R.id.state_index);
    adView = (AdView)findViewById(R.id.adView);
    //Get the views in i2n.
    i2na = (TextView)i2n.findViewById(R.id.i2na);
    i2nb = (TextView)i2n.findViewById(R.id.i2nb);
    i2nc = (TextView)i2n.findViewById(R.id.i2nc);
    i2nd = (TextView)i2n.findViewById(R.id.i2nd);
    quizImage = (ImageView)i2n.findViewById(R.id.quiz_image);
    //Get the views in n2i.
    n2ia = (ImageView)n2i.findViewById(R.id.n2ia);
    n2ib = (ImageView)n2i.findViewById(R.id.n2ib);
    n2ic = (ImageView)n2i.findViewById(R.id.n2ic);
    n2id = (ImageView)n2i.findViewById(R.id.n2id);
    title = (TextView)n2i.findViewById(R.id.n2i_quiz_title);
    name = (TextView)n2i.findViewById(R.id.state_name);
  }

  /**
   * Setting the listener to the view elements which is needed.
   */
  private void bindingListener() {
    //When clicking to show the previous state.
    leftArrow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        previous();
      }
    });
    //When clicking to show the next state.
    rightArrow.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        next();
      }
    });
    //Implementing the sliding effect.
    main.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v,MotionEvent event) {
        //When the event is touch down.
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
          // Get and record the X coordinate of touch down.
          touchDownX = event.getX();
          return true;
        }
        //When finger release.
        else if (event.getAction() == MotionEvent.ACTION_UP) {
          // Get and record the X coordinate of touch up.
          touchUpX = event.getX();
          // From left to right.
          if (touchUpX - touchDownX > THRESHOLD) {
            //Show previous state.
            previous();
          }
          // From right to left.
          else if (touchDownX - touchUpX > THRESHOLD) {
            //Show next state.
            next();
          }
          return true;
        }
        return false;
      }
    });
  }
}
