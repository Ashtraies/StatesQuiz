package org.wus32.group.sq.component.dialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;

import org.wus32.group.sq.R;
import org.wus32.group.sq.activity.BaseActivity;

/**
 * A round progress dialog.
 * Used to tell users something are in progress.
 *
 * @author Tom
 */
public class ProgressRoundDialog extends BaseDialog implements OnDismissListener {

  public ProgressRoundDialog(BaseActivity activity) {
    super(activity);
    setLightweight();
    setCanceledOnTouchOutside(false);
    setOnDismissListener(this);
  }

  @Override
  protected void fillContent() {
    View view = context.makeView(R.layout.fw_layout_componet_dialog_progress_round);
    contentContainer.addView(view);
  }

  /**
   * Setting progress dialog style.
   */
  private void setLightweight() {
    setCanceledOnTouchOutside(false);
    //Background is transparent.
    setMainColor(0,0,Color.parseColor("#00ffffff"));
    WindowManager.LayoutParams lp = getWindow().getAttributes();
    lp.dimAmount = 0f;
    getWindow().setAttributes(lp);
  }

  @Override
  public void onDismiss(DialogInterface dialog) {
    //No need to do anything.
  }
}
