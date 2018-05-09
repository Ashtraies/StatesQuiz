package org.wus32.group.sq.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * A tool used to generate random numbers.
 * <p>
 * Created by Mickey on 2016/10/31.
 */
public class RandomUtil {

  /**
   * Use 4 as the seed,and generate the inedex of correct answer.
   *
   * @return An integer from 0 to 3.
   */
  public static int getCorrectIndex() {
    Random random = new Random();
    return random.nextInt(4);
  }

  /**
   * Get four different random integer.
   *
   * @param seed In this app,it is the number of states.
   * @return An array of integer.
   */
  public static int[] getQuizIndex(int seed) {
    int[] indexs = new int[4];
    //Using Set to make sure every integer is different.
    Set<Integer> set = new HashSet<>();
    Random random = new Random();
    while (set.size() < 4) {
      set.add(random.nextInt(seed));
    }
    int index = 0;
    for (Integer i : set) {
      indexs[index] = i;
      index++;
    }
    return indexs;
  }

  public static void main(String[] s) {
    int[] x = getQuizIndex(50);
    for (int i = 0;i < 4;i++) {
      System.out.println(x[i]);
    }
  }
}
