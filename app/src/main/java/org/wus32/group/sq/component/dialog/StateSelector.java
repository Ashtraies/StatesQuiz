package org.wus32.group.sq.component.dialog;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.wus32.group.sq.R;
import org.wus32.group.sq.activity.Learn;
import org.wus32.group.sq.component.StatesManager;
import org.wus32.group.sq.pojo.State;

/**
 * A dialog used to select a particular state by name.
 * <p>
 * Created by Wu Shuang on 2016/10/30.
 */
public class StateSelector extends BaseDialog {

  public StateSelector(Learn context) {
    super(context);
  }

  @Override
  protected void fillContent() {
    //Generate the listview from layout file.
    ListView listView = (ListView)context.makeView(R.layout.states_list);
    //Set adapter.
    listView.setAdapter(new SimpleAdapter(context,StatesManager.getStates()));
    //Add item clicked listener.
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent,View view,int position,long id) {
        //Get the state from adapter,and display its value.
        State state = (State)parent.getAdapter().getItem(position);
        ((Learn)context).setCurrentIndex(state.getId());
        ((Learn)context).setViewValue();
        StateSelector.this.dismiss();
      }
    });
    contentContainer.addView(listView);
  }
}
