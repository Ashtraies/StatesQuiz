package org.wus32.group.sq.util;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;
import org.wus32.group.sq.pojo.State;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A tool used to read JSON string from a textual file,
 * and convert it to a object.
 * <p>
 * Created by Wu Shuang on 2016/10/25.
 */
public class JSONReader {

  /**
   * Derive JDON string from a JSON file in raw directory.
   *
   * @param context Context,to get Resources object.
   * @param id      The id of the JSON file.
   * @return JSON string.
   */
  public static String readJSONString(Context context,int id) {
    StringBuilder builder = new StringBuilder();
    try {
      //Open file and get the input stream.
      InputStream is = context.getResources().openRawResource(id);
      //Convert the input stream to reader with buffer area.
      InputStreamReader isr = new InputStreamReader(is,"UTF-8");
      BufferedReader reader = new BufferedReader(isr);
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }
      //Close all the streams.
      reader.close();
      isr.close();
      is.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return builder.toString();
  }

  /**
   * Covert a JSON array string to a state list.
   *
   * @param context Context,to get Resources object.
   * @param id      The id of the JSON file.
   * @return The states list.
   */
  public static List<State> getStateList(Context context,int id) {
    try {
      JSONArray array = new JSONArray(readJSONString(context,id));
      List<State> list = new ArrayList<>();
      if (array != null) {
        for (int i = 0;i < array.length();i++) {
          JSONObject json = (JSONObject)array.get(i);
          State state = new State();
          int index = json.getInt("id");
          state.setId(index);
          state.setName(json.getString("name"));
          state.setCapital(json.getString("capital"));
          state.setAbbreviation(json.getString("abbreviation"));
          state.setLoc(json.getString("loc"));
          state.setFlag(json.getString("flag"));
          state.setPopulation(json.getString("population"));
          list.add(i,state);
        }
        return list;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
