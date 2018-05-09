/*
 * 创建日期 2012-10-26
 * 
 * 成都天和软件公司
 * 电话：028-85425861
 * 传真：028-85425861-8008
 * 邮编：610041
 * 地址：成都市武侯区航空路6号丰德万瑞中心B座1001
 * 版权所有
 */
package org.wus32.group.sq.component.dialog;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.th.mobile.framework.activity.BaseActivity;
import com.th.mobile.framework.util.DateUtil;
import com.th.mobile.framework.util.Debug;
import com.th.mobile.framework.util.RUtil;
import com.th.mobile.framework.util.StringUtil;

/**
 * 简易数据适配器，
 * 实现getView方法
 * 
 * @author WuShuang
 * @createtime 2012-10-26 下午01:38:08
 * 最后修改时间 : 
 * 更新记录:
 */
public class SimpleAdapter<E, H> extends SpecificAdapter<E> {

	/**ViewHolder的类型，由子类定义，在子类构造方法中赋值*/
	protected Class<H> hCls;

	/**
	 * ViewHolder，父类不用关注其具体类型，只需创建其实例。
	 * 子类中定义的ViewHolder的属性名，必须与对应Item的属性名相同。
	 */
	protected H holder;

	/**
	 * 构造
	 * 
	 * @param activity
	 * @param data
	 * @param layoutId
	 * @param hCls ViewHolder类模板
	 */
	public SimpleAdapter(BaseActivity activity,List<E> data,int layoutId,Class<H> hCls) {
		super(activity, data, layoutId);
		this.hCls = hCls;
	}

	@Override
	@SuppressWarnings("unchecked")
	public View getView(int position,View convertView,ViewGroup parent) {
		if (convertView == null) {
			convertView = activity.makeView(layoutId);
			holder = generateViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (H) convertView.getTag();
		}
		//避免Item在滚动中出现黑色背景
		convertView.setDrawingCacheEnabled(false);
		E item = getItem(position);
		displayText(item);
		doExtra(convertView, item);
		return convertView;
	}

	/**
	 * 初始化ViewHolder，
	 * 为ViewHolder中的界面控件赋值。
	 * 确保ViewHolder的属性名，与控件id的名称相同
	 * 
	 * @param convertView
	 * @return
	 * @author WuShuang
	 * @createtime 2012-10-26 下午02:01:41
	 */
	protected final H generateViewHolder(View convertView) {
		try {
			holder = hCls.newInstance();
			for (Field f : hCls.getDeclaredFields()) {
				String name = f.getName();
				f.setAccessible(true);
				//ViewHolder的属性，不论类型都初始化赋值
				f.set(holder, convertView.findViewById(RUtil.getId(name)));
			}
		} catch (Exception e) {
			Debug.e(e);
		}
		return holder;
	}

	/**
	 * 将Item的值显示在对应控件上，
	 * 确保Item的属性名与ViewHolder的属性名相同
	 * 
	 * @param item 装载数据
	 * @author WuShuang
	 * @createtime 2012-10-26 下午02:30:23
	 */
	@SuppressWarnings("unchecked")
	protected void displayText(E item) {
		if (item != null) {
			Class<E> cls = (Class<E>) item.getClass();
			try {
				for (Field f : hCls.getDeclaredFields()) {
					f.setAccessible(true);
					//只关注文字型显示信息
					View view = (View) f.get(holder);
					/*
					 * 	属性名相同，建立Viewholder与Item的联系
					 * 从Item中取得对应的值
					 */
					Field itemF = cls.getDeclaredField(f.getName());
					String text = null;
					if (itemF != null) {
						itemF.setAccessible(true);
						text = valueToString(itemF.get(item));
					}
					setTextView(view, text);
					setSpinner(view, text);
				}
			} catch (Exception e) {
				Debug.e(e);
			}
		}
	}

	/**
	 * 设置TextView显示内容
	 * 
	 * @param v
	 * @param text
	 * @author WuShuang
	 * @createtime 2014-2-28 下午03:41:21
	 */
	public void setTextView(View v,String text) {
		if (v instanceof TextView) {
			TextView tv = (TextView) v;
			tv.setText(text);
		}
	}

	/**
	 * 设置Spinner显示内容，由子类实现其逻辑
	 * 
	 * @param v
	 * @param text
	 * @author WuShuang
	 * @createtime 2014-2-28 下午03:40:50
	 */
	public void setSpinner(View v,String text) {
		//父类未实现
	}

	/**
	 * 将属性值转换为字符串，默认实现，需要时子类可覆写
	 * 
	 * @param value
	 * @return
	 * @author WuShuang
	 * @createtime 2014-2-28 下午03:32:45
	 */
	public String valueToString(Object value) {
		if (value == null) {
			return "暂无";
		}
		if (value instanceof Boolean || value.getClass() == boolean.class) {
			return Boolean.valueOf(value.toString()) ? "是" : "否";
		}
		if (value instanceof Date) {
			return DateUtil.toString((Date) value, "yyyy-MM-dd");
		}
		return StringUtil.toString(value);
	}

	/**
	 * 由子类在需要时重写，
	 * 实现额外的界面展示逻辑。
	 * 
	 * @author WuShuang
	 * @createtime 2012-10-26 下午02:26:04
	 */
	protected void doExtra(View convertView,E item) {
		// subclass to do if necessary
	}
}
