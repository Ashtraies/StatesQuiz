package org.wus32.group.sq.component;

import org.wus32.group.sq.Constant;
import org.wus32.group.sq.app.MyApplication;
import org.wus32.group.sq.pojo.State;

import java.util.List;

/**
 * Manager used to manage all the states.
 * <p>
 *
 * @author Kyle
 */
public class StatesManager {

  /**
   * Get all the states from the global cache.
   *
   * @return States list.
   */
  public static List<State> getStates() {
    return ((List<State>)MyApplication.MemoryCache.getCache(Constant.STATES_CACHE));
  }

  /**
   * Get a state from states list by index.
   *
   * @param index The index.
   * @return A state with the index.
   */
  public static State getStateByIndex(int index) {
    return ((List<State>)MyApplication.MemoryCache.getCache(Constant.STATES_CACHE)).get(index);
  }

  /**
   * Get the total number of states list.
   *
   * @return The number of states.
   */
  public static int getStatesNumber() {
    return ((List<State>)MyApplication.MemoryCache.getCache(Constant.STATES_CACHE)).size();
  }
}
