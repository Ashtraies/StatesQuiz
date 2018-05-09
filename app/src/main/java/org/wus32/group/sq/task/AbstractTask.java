/*
 * 创建日期 2014-3-3
 * 
 * 成都天和软件公司
 * 电话：028-85425861
 * 传真：028-85425861-8008
 * 邮编：610041
 * 地址：成都市武侯区航空路6号丰德万瑞中心B座1001
 * 版权所有
 */
package org.wus32.group.sq.task;

import android.os.AsyncTask;

import org.wus32.group.sq.activity.BaseActivity;
import org.wus32.group.sq.util.CollectionUtil;

/**
 * 抽象异步任务，定义公共方法供子类实现及调用流程，禁止覆写原生命周期方法
 * 
 * @param <Params> 参数类型
 * @param <Progress> 进度
 * @param <Result> 返回值
 * @author WuShuang
 * @createtime 2014-3-3 下午03:23:35
 */
public abstract class AbstractTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

	protected BaseActivity activity;

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
		if (!CollectionUtil.isEmpty(values)) {
			doUpdate(values[0]);
		} else {
			doUpdate(null);
		}
	}

	@Override
	protected Result doInBackground(Params... params) {
		try {
			if (!CollectionUtil.isEmpty(params)) {
				return doExecute(params[0]);
			} else {
				return doExecute(null);
			}
		} catch (Exception e) {
			this.e = e;
			//因为异常，task已经终止，此处参数需要传入false
			cancel(false);
			return null;
		}
	}

	@Override
	protected final void onPostExecute(Result result) {
		// 需要执行后续操作
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
	 * 具体异步执行的方法，由子类实现
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 * @author WuShuang
	 * @createtime 2014-3-3 下午04:45:10
	 */
	public abstract Result doExecute(Params param) throws Exception;

	/**
	 * 根据返回结果执行后续操作
	 * 
	 * @param result
	 * @author WuShuang
	 * @createtime 2012-12-26 上午10:41:27
	 */
	public abstract void doResult(Result result) throws Exception;

	/**
	 * 进度更新时操作，根据需要由子类实现
	 * 
	 * @param progress
	 * @author WuShuang
	 * @createtime 2014-3-3 下午04:53:20
	 */
	protected void doUpdate(Progress progress) {
		//subclass todo if necessary
	}

	/**
	 * 异常处理，由子类扩展
	 * 
	 * @author WuShuang
	 * @createtime 2012-12-26 上午10:44:21
	 * 最后修改时间 : 
	 * 更新记录:
	 */
	protected void doException(Throwable e) {
		e.printStackTrace();
	}
}
