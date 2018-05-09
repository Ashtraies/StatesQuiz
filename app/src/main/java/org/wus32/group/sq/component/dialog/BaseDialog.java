package org.wus32.group.sq.component.dialog;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.wus32.group.sq.R;
import org.wus32.group.sq.activity.BaseActivity;

/**
 * 基础自定义对话框，
 * 1、确定style
 * 2、确定布局框架，最大面积，最小面积，边距。
 * 3、提供标题管理
 * 4、提供按钮管理
 *
 * @author WuShuang
 * @createtime 2014-4-28 上午11:03:06
 */
public abstract class BaseDialog extends Dialog {

  protected BaseActivity context;

  /**
   * 整个视图
   */
  private View allView;

  /**
   * 标题
   */
  private TextView title;

  /**
   * 分割线
   */
  private View titleDivider, contentDivider, btnDivider;

  /**
   * 内容容器
   */
  protected ViewGroup contentContainer;

  /**
   * 按钮
   */
  private TextView btnOne, btnTwo;

  /**
   * 标题与分割线颜色
   */
  protected int titleColor, dividerColor;

  public BaseDialog(BaseActivity context) {
    super(context,R.style.fw_style_dialog_base);
    this.context = context;
    //设置框架视图
    setContentView();
    //初始化视图组件
    initView();
    //设置主题颜色
    setMainColor(
            context.getResources().getColor(R.color.dilog_mian_title),
            context.getResources().getColor(R.color.dilog_mian_divider),
            0);
    setCanceledOnTouchOutside(true);
    //子类加入自定义内容
    fillContent();
  }

  /**
   * 初始化对话框框架内必要视图组件
   *
   * @author WuShuang
   * @createtime 2014-4-28 下午02:53:29
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
   * 设置主题颜色
   *
   * @param titleColor
   * @param dividerColor
   * @author WuShuang
   * @createtime 2014-4-28 下午02:51:14
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
   * 设置对话框的主布局
   *
   * @author WangLong
   * @createtime 2014-4-29 下午4:00:36
   */
  public final void setContentView() {
    setContentView(R.layout.fw_layout_componet_dialog_base);
  }

  /**
   * 设置标题栏显示的内容
   *
   * @param text 要显示的内容
   * @author WangLong
   * @createtime 2014-4-29 下午3:59:54
   */
  public void setTitle(String text) {
    title.setVisibility(View.VISIBLE);
    titleDivider.setVisibility(View.VISIBLE);
    title.setText(text);
  }

  /**
   * 子类实现，加入自己想要加入的内容
   * 使用方法：contentContainer.addView(contentView)，contentView为想要加入的内容布局
   *
   * @author WangLong
   * @createtime 2014-4-29 下午3:56:12
   */
  protected abstract void fillContent();

  /**
   * 显示确定按钮
   *
   * @param text     按钮显示名称
   * @param listener 按钮的监听事件,为空则表示不做任何处理直接dismiss掉对话框
   * @author WangLong
   * @createtime 2014-4-29 上午10:38:02
   */
  public void showPositiveButton(String text,final ButtonOnClickListener listener) {
    btnOne.setVisibility(View.VISIBLE);
    btnOne.setText(text);
    contentDivider.setVisibility(View.VISIBLE);
    btnOne.setOnClickListener(new android.view.View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener == null || listener.onClick()) {
          dismiss();
        }
      }
    });
  }

  /**
   * 显示取消按钮
   *
   * @param text     按钮显示名称
   * @param listener 按钮的监听事件,为空则表示不做任何处理直接dismiss掉对话框
   * @author WangLong
   * @createtime 2014-4-29 上午10:38:02
   */
  public void showNegativeButton(String text,final ButtonOnClickListener listener) {
    btnTwo.setVisibility(View.VISIBLE);
    contentDivider.setVisibility(View.VISIBLE);
    btnTwo.setText(text);
    btnTwo.setOnClickListener(new android.view.View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (listener == null || listener.onClick()) {
          dismiss();
        }
      }
    });
  }

  /**
   * 同时显示两个按钮
   *
   * @param posiText     确定按钮显示名称
   * @param negaText     取消按钮显示名称
   * @param posiListener 确定按钮的监听事件,为空则表示不做任何处理直接dismiss掉对话框
   * @param negaListener 取消按钮的监听事件,为空则表示不做任何处理直接dismiss掉对话框
   * @author WangLong
   * @createtime 2014-4-29 上午10:40:39
   */
  public void showTwoButton(String posiText,String negaText,ButtonOnClickListener posiListener,
                            ButtonOnClickListener negaListener) {
    showPositiveButton(posiText,posiListener);
    showNegativeButton(negaText,negaListener);
    btnDivider.setVisibility(View.VISIBLE);
  }

  public interface ButtonOnClickListener {
    /**
     * 点击按钮需要处理的操作
     *
     * @return 如果返回ture则表示处理完之后将隐藏对话框，如果返回false则不隐藏对话框
     * @author WangLong
     * @createtime 2014-5-29 下午3:45:17
     */
    boolean onClick();
  }
}
