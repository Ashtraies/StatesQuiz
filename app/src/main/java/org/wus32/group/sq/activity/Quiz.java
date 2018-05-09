package org.wus32.group.sq.activity;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

/**
 * The page users can take a quiz to evaluate their study.
 * The quizs are all multiple choice including four types;
 * 1.Choosing the state name by flag.
 * 2.Choosing the state name by location map.
 * 3.Choosing the flag by state name.
 * 3.Choosing the location map by state name.
 * <p>
 * Created by Mickey on 2016/10/26.
 */
public class Quiz extends BaseActivity {

  /**
   * The view shows the index of quiz.
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
  
  private AdView adView;

  /**
   * The container of quiz view.
   */
  private ViewFlipper main;

  /**
   * The list which holds the questions which users gave wrong answers.
   */
  private List<Question> wrongs;

  /**
   * The list which holds the questions.
   */
  private List<Question> questions;

  /**
   * The index of current questions.
   */
  private int currentIndex;

  /**
   * The count of right and wrong answer.
   */
  private int right, wrong;

  /**
   * The index of the correct answer of the four potential answers.
   */
  private int correctIndex = -1;

  /**
   * How many questions will be in this quiz.
   */
  public static final int QUESTION_NUMBER = 10;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);
    initializeViews();
    initializeQuestions();
    setViewValue();
  }

  /**
   * Getting all the view elements from content view.
   */
  private void initializeViews() {
    //Get the common views.
    i2n = makeView(R.layout.quiz_image2name_fragment);
    n2i = makeView(R.layout.quiz_name2image_fragment);
    main = (ViewFlipper)findViewById(R.id.main_content);
    quizIndex = (TextView)findViewById(R.id.quiz_index);
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
   * Filling the views with object's value.
   * When a new question is loaded,this method should be called.
   */
  public void setViewValue() {
    AdHelper.LoadAd(adView);
    //Get the current question from quesitons list.
    Question question = questions.get(currentIndex);
    correctIndex = question.getCorrectIndex();
    quizIndex.setText((currentIndex + 1) + "/" + QUESTION_NUMBER);
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
  }

  /**
   * Generating questions and filling into question list.
   */
  private void initializeQuestions() {
    questions = new ArrayList<>(QUESTION_NUMBER);
    wrongs = new ArrayList<>();
    for (int i = 1;i <= QUESTION_NUMBER;i++) {
      //The type of question is random.
      questions.add(new Question(i,Question.randomType()));
    }
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
    //Setting the animation effect.
    main.setInAnimation(AnimationUtils.loadAnimation(this,
            R.anim.left_in));
    main.setOutAnimation(AnimationUtils.loadAnimation(this,
            R.anim.left_out));
    main.showNext();
  }

  /**
   * Choose the answers.
   *
   * @param v Which answer is chosen(clicked).
   */
  public void choose(View v) {
    //Get the index of the answer.
    int index = Integer.valueOf(v.getTag().toString());
    //Comparing it to the index of correct answer.
    if (index == correctIndex) {
      right++;
    } else {
      wrong++;
      //Adding current question to wrong list then putting it into cache.
      wrongs.add(questions.get(currentIndex));
      MyApplication.MemoryCache.putCache(Constant.WRONGS_CACHE,wrongs);
    }
    //Moving to the next question.
    currentIndex++;
    if (currentIndex < QUESTION_NUMBER) {
      //Show next question.
      setViewValue();
    } else {
      //Jump to result page.
      skip(Result.class,right,wrong);
      finish();
    }
  }
}
