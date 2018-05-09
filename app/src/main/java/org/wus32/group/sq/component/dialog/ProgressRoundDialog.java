package org.wus32.group.sq.component.dialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;

import org.wus32.group.sq.R;
import org.wus32.group.sq.activity.BaseActivity;
import org.wus32.group.sq.task.AbstractTask;
import org.wus32.group.sq.util.CollectionUtil;

/**
 * 
 * @author WangLong
 * @createtime 2014-5-29 上午10:21:23
 */
public class ProgressRoundDialog extends BaseDialog implements OnDismissListener {

	private AbstractTask<?, ?, ?>[] task;

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

	public void registerTask(AbstractTask<?, ?, ?>... task) {
		this.task = task;
	}

	private void setLightweight() {
		setCanceledOnTouchOutside(false);
		//对话框背景完全透明
		setMainColor(0, 0, Color.parseColor("#00ffffff"));

		//对话框外面区域完全透明
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.dimAmount = 0f;
		getWindow().setAttributes(lp);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		if (!CollectionUtil.isEmpty(task)) {
			for (AbstractTask<?, ?, ?> t : task) {
				t.cancel(true);
			}
		}
	}
}
