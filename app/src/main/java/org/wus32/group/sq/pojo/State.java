package org.wus32.group.sq.pojo;

import java.io.Serializable;

/**
 * StatesQuiz
 * <p>
 * Created by Wu Shuang on 2016/10/25.
 */

public class State implements Serializable, Cloneable {

  private int id;

  private String name;

  private String capital;

  private String location;

  private String flag;

  private String size;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getCapital() {
    return capital;
  }

  public String getLocation() {
    return location;
  }

  public String getFlag() {
    return flag;
  }

  public String getSize() {
    return size;
  }

  @Override
  public String toString() {
    return "State{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", capital='" + capital + '\'' +
            ", location='" + location + '\'' +
            ", flag='" + flag + '\'' +
            ", size='" + size + '\'' +
            '}';
  }
}
