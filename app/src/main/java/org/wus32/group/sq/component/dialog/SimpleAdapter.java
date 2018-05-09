package org.wus32.group.sq.component.dialog;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.wus32.group.sq.activity.BaseActivity;
import org.wus32.group.sq.pojo.State;

import java.util.List;

/**
 * A simple listview adapter.
 * Implementing all the methods required.
 *
 * @author WuShuang
 */
public class SimpleAdapter extends BaseAdapter {

  /**
   * Context.
   */
  private BaseActivity activity;

  /**
   * The collection of source data.
   */
  private List<State> data;

  public SimpleAdapter(BaseActivity activity,List<State> data) {
    this.activity = activity;
    this.data = data;
  }

  @Override
  public int getCount() {
    return data.size();
  }

  @Override
  public Object getItem(int position) {
    return data.get(position);
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }

  @Override
  @SuppressWarnings("unchecked")
  public View getView(int position,View convertView,ViewGroup parent) {
    //Dynamically create textview.
    TextView name = new TextView(activity);
    name.setTextSize(30);
    name.setDrawingCacheEnabled(false);
    //Get the name of state.
    State item = (State)getItem(position);
    name.setText(item.getName());
    return name;
  }
}
