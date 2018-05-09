package org.wus32.group.sq.pojo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.wus32.group.sq.Constant;
import org.wus32.group.sq.app.MyApplication;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * State object.
 * <p>
 * Created by Wu Shuang on 2016/10/25.
 */
public class State {

  /**
   * Id.
   */
  private int id;

  /**
   * State name.
   */
  private String name;

  /**
   * The abbreviation of the state.
   */
  private String abbreviation;

  /**
   * The population of the state.
   */
  private String population;

  /**
   * The captial name of the state.
   */
  private String capital;

  /**
   * The path in file system of the state's location image.
   */
  private String loc;

  /**
   * The path in file system of the state's flag image.
   */
  private String flag;

  /**
   * Flag image.
   */
  private Bitmap flagImage;

  /**
   * Location image.
   */
  private Bitmap locImage;

  /**
   * Get the image of the state's flag.
   * In order to reduce IO time-consuming operation,using memory cache.
   *
   * @return A bitmap of flag.
   */
  public Bitmap getFlagImage() {
    //If already has a flag image,just return.
    if (flagImage != null) {
      return flagImage;
    }
    //Combine the state name and a suffix to make the key is unique.
    String key = name + "flag";
    //If the bitmap is in cache,just return.
    flagImage = getFromCache(key);
    if (flagImage != null) {
      return flagImage;
    }
    //Load the image from internal storage of users' phone.
    if (flag != null) {
      InputStream is = null;
      try {
        is = new FileInputStream(Constant.FLAG + flag);
        flagImage = BitmapFactory.decodeStream(is);
        //Once get the bitmap,save to cache.
        putInCache(key,flagImage);
        return flagImage;
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } finally {
        if (is != null) {
          try {
            is.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return null;
  }

  /**
   * Get the image of the state's location map.
   * In order to reduce IO time-consuming operation,using memory cache.
   *
   * @return A bitmap of location map.
   */
  public Bitmap getLocImage() {
    //If already has a flag image,just return.
    if (locImage != null) {
      return locImage;
    }
    //Combine the state name and a suffix to make the key is unique.
    String key = name + "loc";
    //If the bitmap is in cache,just return.
    locImage = getFromCache(key);
    if (locImage != null) {
      return locImage;
    }
    //Load the image from internal storage of users' phone.
    if (loc != null) {
      InputStream is = null;
      try {
        is = new FileInputStream(Constant.LOC + loc);
        locImage = BitmapFactory.decodeStream(is);
        putInCache(key,locImage);
        return locImage;
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } finally {
        if (is != null) {
          try {
            is.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return null;
  }

  /**
   * Get bitmap from global memory cache.
   *
   * @param key The cache key.
   * @return Bitmap.
   */
  private Bitmap getFromCache(String key) {
    return (Bitmap)MyApplication.MemoryCache.getCache(key);
  }

  /**
   * Store the bitmap to global memory cache.
   *
   * @param key    The cache key.
   * @param bitmap Bitmap to store.
   */
  private void putInCache(String key,Bitmap bitmap) {
    MyApplication.MemoryCache.putCache(key,bitmap);
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCapital() {
    return capital;
  }

  public void setCapital(String capital) {
    this.capital = capital;
  }

  public String getAbbreviation() {
    return abbreviation;
  }

  public void setAbbreviation(String abbreviation) {
    this.abbreviation = abbreviation;
  }

  public String getPopulation() {
    return population;
  }

  public void setPopulation(String population) {
    this.population = population;
  }

  public String getLoc() {
    return loc;
  }

  public void setLoc(String loc) {
    this.loc = loc;
  }

  public String getFlag() {
    return flag;
  }

  public void setFlag(String flag) {
    this.flag = flag;
  }

  @Override
  public String toString() {
    return "State{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", abbreviation='" + abbreviation + '\'' +
            ", capital='" + capital + '\'' +
            ", loc='" + loc + '\'' +
            ", flag='" + flag + '\'' +
            '}';
  }
}
