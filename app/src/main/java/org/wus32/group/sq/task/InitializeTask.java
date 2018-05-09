package org.wus32.group.sq.task;

import android.widget.TextView;

import org.wus32.group.sq.activity.BaseActivity;
import org.wus32.group.sq.activity.Portal;

/**
 * StatesQuiz
 * <p>
 * Created by Wu Shuang on 2016/10/25.
 */

public class InitializeTask extends AbstractTask<Void,Integer,Void> {

  private TextView count;

  public InitializeTask(BaseActivity activity) {
    super(activity);
  }

  @Override
  public void onPreExecute() {
    super.onPreExecute();
    activity.showRoundProgress();
  }

  @Override
  protected void doUpdate(Integer o) {
    super.doUpdate(o);

  }

  @Override
  public Void doExecute(Void param) throws Exception {
    Thread.sleep(1000);
    return null;
  }

  @Override
  public void doResult(Void o) throws Exception {
    activity.dismissProgress();
    activity.skip(Portal.class);
  }
}
