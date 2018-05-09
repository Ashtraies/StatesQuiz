package org.wus32.group.sq.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.android.gms.ads.AdView;

import org.wus32.group.sq.R;
import org.wus32.group.sq.component.StatesManager;
import org.wus32.group.sq.component.UserRecord;
import org.wus32.group.sq.component.dialog.StateSelector;
import org.wus32.group.sq.pojo.State;
import org.wus32.group.sq.util.AdHelper;

/**
 * The page of learning the knowledge of states.
 * <p>
 * Created by Wu Shuang on 2016/10/26.
 */
public class Learn extends BaseActivity {

  /**
   * To show the textual information.
   */
  private TextView stateIndex, stateName, stateCapital, stateAbbreviation, statePopulation;

  /**
   * Arrow icon.
   */
  private View leftArrow, rightArrow;
  
  private AdView adView;

  /**
   * The container which holds the view of states information.
   */
  private ViewFlipper mainContent;

  /**
   * The view hold the flag and location of a state.
   */
  private ImageView flagView, locView;

  /**
   * The index of currently showing state in states list.
   */
  private int currentIndex;

  /**
   * A tool to record where the user stop learning.
   * And when the user start to learn again,it will load the location.
   */
  private UserRecord record;

  /**
   * The X coordinates of touch down event and touch up event.
   */
  private float touchDownX, touchUpX;

  /**
   * A dialog which offers the shortcut of selecting a state to learn.
   */
  private StateSelector selector;

  /**
   * The threshold of sliding when greater than this value,
   * the sliding event will be handled.
   */
  public static final int THRESHOLD = 100;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_learn);
    initializeViews();
    bindingListener();
    //Load user record
    record = new UserRecord(this);
    currentIndex = record.getCurrentIndex();
    setViewValue();
  }

  /**
   * Set the current index.
   *
   * @param currentIndex Current index of state in states list.
   */
  public void setCurrentIndex(int currentIndex) {
    this.currentIndex = currentIndex;
  }

  /**
   * Filling the views with object's value.
   * When a new state is loaded,this method should be called.
   */
  public void setViewValue() {
    //Get the current state to show.
    State currentState = StatesManager.getStateByIndex(currentIndex);
    //Get the information and show.
    stateIndex.setText((currentIndex + 1) + "/" + StatesManager.getStatesNumber());
    stateName.setText(currentState.getName());
    stateCapital.setText(currentState.getCapital());
    stateAbbreviation.setText(currentState.getAbbreviation());
    statePopulation.setText(currentState.getPopulation());
    //Load the images of flag and location.
    flagView.setImageBitmap(currentState.getFlagImage());
    locView.setImageBitmap(currentState.getLocImage());
    AdHelper.LoadAd(adView);
  }

  /**
   * Getting all the view elements from content view.
   */
  private void initializeViews() {
    stateIndex = (TextView)findViewById(R.id.state_index);
    stateName = (TextView)findViewById(R.id.state_name);
    stateCapital = (TextView)findViewById(R.id.state_capital);
    stateAbbreviation = (TextView)findViewById(R.id.state_abbreviation);
    statePopulation = (TextView)findViewById(R.id.state_population);
    flagView = (ImageView)findViewById(R.id.learn_flag);
    locView = (ImageView)findViewById(R.id.learn_loc);
    leftArrow = findViewById(R.id.left_arrow);
    rightArrow = findViewById(R.id.right_arrow);
    mainContent = (ViewFlipper)findViewById(R.id.main_content);
    adView = (AdView)findViewById(R.id.adView);
  }

  /**
   * Setting the listener to the view elements which is needed.
   */
  private void bindingListener() {
    /*
     * When click the textview which shows the index,
     * a state selector will show.
     */
    stateIndex.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        selector = new StateSelector(Learn.this);
        selector.show();
      }
    });
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
    mainContent.setOnTouchListener(new View.OnTouchListener() {
      
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

  /**
   * Show next state.
   */
  private void next() {
    //Get the next index.
    currentIndex++;
    //If it is the last one,return to the first one.
    if (currentIndex == StatesManager.getStatesNumber()) {
      currentIndex = 0;
    }
    //Setting the animation of changing view.
    mainContent.setInAnimation(AnimationUtils.loadAnimation(Learn.this,
            R.anim.left_in));
    mainContent.setOutAnimation(AnimationUtils.loadAnimation(Learn.this,
            R.anim.left_out));
    //Show the next view.
    mainContent.showNext();
    //Show the new data.
    setViewValue();
  }

  /**
   * Show previous state.
   */
  private void previous() {
    //Get the next index.
    currentIndex--;
    if (currentIndex == -1) {
      //If it is the first one,return to the last one.
      currentIndex = StatesManager.getStatesNumber() - 1;
    }
    //Setting the animation of changing view.
    mainContent.setInAnimation(AnimationUtils.loadAnimation(Learn.this,
            R.anim.right_in));
    mainContent.setOutAnimation(AnimationUtils.loadAnimation(Learn.this,
            R.anim.right_out));
    //Show the previous view.
    mainContent.showPrevious();
    //Show the new data.
    setViewValue();
  }

  @Override
  protected void onPause() {
    super.onPause();
    //Record the current index.
    record.saveCurrentIndex(currentIndex);
  }
}
