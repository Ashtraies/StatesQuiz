package org.wus32.group.sq.pojo;

import java.util.List;

/**
 * StatesQuiz
 * <p>
 * Created by Wu Shuang on 2016/10/31.
 */
public class Question {

  private int id;

  private List<State> states;

  private int correctIndex;

  private Type type;



  enum Type {
    L2N,F2N,N2L,N2F
  }
}
