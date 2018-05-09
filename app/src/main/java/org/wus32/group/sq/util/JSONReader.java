package org.wus32.group.sq.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.wus32.group.sq.pojo.State;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * StatesQuiz
 * <p>
 * Created by Wu Shuang on 2016/10/25.
 */

public class JSONReader {

  public static List<State> read(InputStream is) {
    try {
      InputStreamReader isr = new InputStreamReader(is,"UTF-8");
      BufferedReader reader = new BufferedReader(isr);
      String line;
      StringBuilder builder = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }
      reader.close();
      isr.close();
      JSONArray array = new JSONArray(builder.toString());
      if (array != null) {
        List<State> list = new ArrayList<>();
        for (int i = 0;i < array.length();i++) {
          JSONObject json = (JSONObject)array.get(i);
          State state = new State();
          //TODO
          list.add(state);
        }
        return list;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
