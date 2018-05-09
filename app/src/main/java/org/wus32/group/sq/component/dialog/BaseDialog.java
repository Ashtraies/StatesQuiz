package org.wus32.group.sq.component.dialog;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wus32.group.sq.R;
import org.wus32.group.sq.activity.BaseActivity;

/**
 * Base custom dialog，
 *
 * @author WuShuang
 */
public abstract class BaseDialog extends Dialog {

  /**
   * The context.
   */
  protected BaseActivity context;

  /**
   * The content view of the dialog.
   */
  private View allView;

  /**
   * Dialog title.
   */
  private TextView title;

  /**
   * Divider.
   */
  private View titleDivider, contentDivider, btnDivider;

  /**
   * Container,which holds custom content.
   */
  protected ViewGroup contentContainer;

  /**
   * Default buttons.
   */
  private TextView btnOne, btnTwo;

  public BaseDialog(BaseActivity context) {
    super(context,R.style.fw_style_dialog_base);
    this.context = context;
    //Set framework view.
    setContentView();
    //Get the view elements.
    initView();
    setCanceledOnTouchOutside(true);
    //Adding custom views.
    fillContent();
  }

  /**
   * Setting the theme color.
   *
   * @param titleColor   The color of title text.
   * @param dividerColor The color of divider.
   * @author WuShuang
   */
  public void setMainColor(int titleColor,int dividerColor,int themeColor) {
    if (titleColor != 0) {
      title.setTextColor(titleColor);
    }
    if (dividerColor != 0) {
      titleDivider.setBackgroundColor(dividerColor);
      contentDivider.setBackgroundColor(dividerColor);
      btnDivider.setBackgroundColor(dividerColor);
    }
    if (themeColor != 0) {
      allView.setBackgroundColor(themeColor);
    }
  }

  /**
   * Initialize the view elements.
   *
   * @author WuShuang
   */
  private void initView() {
    title = (TextView)findViewById(R.id.fw_dialog_title);
    contentContainer = (ViewGroup)findViewById(R.id.fw_dialog_content_view);
    btnOne = (TextView)findViewById(R.id.fw_dialog_btn_one);
    btnTwo = (TextView)findViewById(R.id.fw_dialog_btn_two);
    titleDivider = findViewById(R.id.fw_dialog_title_line);
    contentDivider = findViewById(R.id.fw_dialog_btns_line);
    btnDivider = findViewById(R.id.fw_dialog_btn_line);
    allView = findViewById(R.id.fw_dialog_base_view);
  }

  /**
   * Setting the layout of dialog.
   *
   * @author Kyle
   */
  public final void setContentView() {
    setContentView(R.layout.fw_layout_componet_dialog_base);
  }

  /**
   * Subclasses must implement this method to add the view which it needs.
   *
   * @author WuShuang
   */
  protected abstract void fillContent();
}
