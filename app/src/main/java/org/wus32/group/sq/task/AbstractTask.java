package org.wus32.group.sq.task;

import android.os.AsyncTask;

import org.wus32.group.sq.activity.BaseActivity;

/**
 * A abstract async task define the progess,
 * and implement the abstract method which subclass cannot override.
 *
 * @param <Params>   Params' type.
 * @param <Progress> Progress type.
 * @param <Result>   Return value type.
 * @author WuShuang
 */
public abstract class AbstractTask<Params,Progress,Result> extends AsyncTask<Params, Progress, Result> {

  /**
   * Context.
   */
  protected BaseActivity activity;

  /**
   * The exceptions may be occur in the progress.
   */
  private Throwable e;

  public AbstractTask(BaseActivity activity) {
    this.activity = activity;
  }

  @Override
  public void onPreExecute() {
    //subclass todo if necessary
  }

  @Override
  protected final void onProgressUpdate(Progress... values) {
    if (values != null && values.length != 0) {
      doUpdate(values[0]);
    } else {
      doUpdate(null);
    }
  }

  @Override
  protected final Result doInBackground(Params... params) {
    try {
      if (params != null && params.length != 0) {
        return doExecute(params[0]);
      } else {
        return doExecute(null);
      }
    } catch (Exception e) {
      this.e = e;
      cancel(false);
      return null;
    }
  }

  @Override
  protected final void onPostExecute(Result result) {
    try {
      doResult(result);
    } catch (Exception e) {
      doException(e);
    }
  }

  @Override
  protected void onCancelled() {
    if (e != null) {
      doException(e);
    }
  }

  /**
   * The async operation.
   * Subclass must implement this method.
   *
   * @param param
   * @return
   * @throws Exception
   */
  public abstract Result doExecute(Params param) throws Exception;

  /**
   * Do something when get the result.
   *
   * @param result
   */
  public abstract void doResult(Result result) throws Exception;

  /**
   * Update the progress.
   *
   * @param progress
   */
  protected void doUpdate(Progress progress) {
    //subclass todo if necessary
  }

  /**
   * Handle the exception.
   *
   * @author WuShuang
   */
  protected void doException(Throwable e) {
    e.printStackTrace();
  }
}
