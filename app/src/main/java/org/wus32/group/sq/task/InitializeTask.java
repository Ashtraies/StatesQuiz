package org.wus32.group.sq.task;

import org.wus32.group.sq.Constant;
import org.wus32.group.sq.R;
import org.wus32.group.sq.activity.BaseActivity;
import org.wus32.group.sq.activity.Portal;
import org.wus32.group.sq.app.MyApplication;
import org.wus32.group.sq.pojo.State;
import org.wus32.group.sq.util.FileOperator;
import org.wus32.group.sq.util.JSONReader;

import java.io.File;
import java.util.List;

/**
 * When the app is lauched,
 * start this task to do time-consuming initial operation.
 * <p>
 * Created by Wu Shuang on 2016/10/25.
 */
public class InitializeTask extends AbstractTask<Void, Integer, Void> {

  public InitializeTask(BaseActivity activity) {
    super(activity);
  }

  @Override
  public void onPreExecute() {
    super.onPreExecute();
    //Show a round progress.
    activity.showRoundProgress();
  }

  @Override
  public Void doExecute(Void param) throws Exception {
    createFileSystem();
    generateStates();
    Thread.sleep(1000);
    //No need to return a result.
    return null;
  }

  /**
   * Create folders,
   * and unzip resouces(flag and location images) on external storage.
   */
  private void createFileSystem() {
    unzip(R.raw.flag,Constant.FLAG);
    unzip(R.raw.loc,Constant.LOC);
  }

  /**
   * Unzip a zipped file from raw directory.
   *
   * @param id     Resource id.
   * @param target Where to unzip.
   */
  private void unzip(int id,String target) {
    File flag = new File(target);
    if (!flag.exists()) {
      flag.mkdirs();
      FileOperator.unzip(activity,id,target);
    }
  }

  /**
   * Read a JSON file in raw directory to generate the state object.
   */
  private void generateStates() {
    List<State> states = JSONReader.getStateList(activity,R.raw.states);
    //Add the states list into gloabal cache.
    MyApplication.MemoryCache.putCache(Constant.STATES_CACHE,states);
  }

  @Override
  public void doResult(Void o) throws Exception {
    activity.dismissProgress();
    //To portal page.
    activity.skip(Portal.class);
    activity.finish();
  }
}
