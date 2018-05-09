package org.wus32.group.sq.pojo;

import org.wus32.group.sq.component.StatesManager;
import org.wus32.group.sq.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The object of question.
 * <p>
 * Created by Mickey on 2016/10/31.
 */
public class Question {

  /**
   * Id
   */
  private int id;

  /**
   * Four options,they are state object.
   * One of them is the right answer.
   */
  private List<State> states;

  /**
   * The index of the right answer.
   */
  private int correctIndex;

  /**
   * The question type.
   */
  private Type type;

  public Question(int id,Type type) {
    this.id = id;
    this.type = type;
    //Select the four states as options randomly.
    int seed = StatesManager.getStatesNumber();
    states = new ArrayList<>(4);
    //Set the correctIndex randomly.
    correctIndex = RandomUtil.getCorrectIndex();
    int[] indexs = RandomUtil.getQuizIndex(seed);
    states.add(StatesManager.getStateByIndex(indexs[0]));
    states.add(StatesManager.getStateByIndex(indexs[1]));
    states.add(StatesManager.getStateByIndex(indexs[2]));
    states.add(StatesManager.getStateByIndex(indexs[3]));
  }

  public int getId() {
    return id;
  }

  public List<State> getStates() {
    return states;
  }

  public int getCorrectIndex() {
    return correctIndex;
  }

  public Type getType() {
    return type;
  }

  /**
   * An enum defines the type of questions.
   */
  public enum Type {
    /**
     * Choose state name by location picture.
     */
    L2N,
    /**
     * Choose state name by flag picture.
     */
    F2N,
    /**
     * Choose location picture by state name.
     */
    N2L,
    /**
     * Choose flag picture by state name.
     */
    N2F
  }

  /**
   * Get a type randomly.
   *
   * @return Question type.
   */
  public static Type randomType() {
    switch (new Random().nextInt(4)) {
      case 0:
        return Type.L2N;
      case 1:
        return Type.F2N;
      case 2:
        return Type.N2L;
      case 3:
        return Type.N2F;
    }
    return Type.L2N;
  }
}
