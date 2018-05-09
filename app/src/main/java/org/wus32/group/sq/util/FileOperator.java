package org.wus32.group.sq.util;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * File system operation tool.
 * Using this tool to create folders,delete,copy and unzip files.
 *
 * @author WuShuang
 */
public class FileOperator {

  /**
   * The size of buffer area.
   */
  public static final int BUFFER_SIZE = 1024;


  /**
   * Delete a file with specific path in external storage.
   *
   * @param target The path of the file.
   * @return Whether the operation is successful.
   * @author WuShuang
   */
  public static boolean delSdcardFile(String target) {
    File file = new File(target);
    if (file.exists()) {
      if (!file.isDirectory()) {
        return file.delete();
      }
      return false;
    } else {
      return true;
    }
  }

  /**
   * Output a file stream to external storage.
   *
   * @param is     File stream.
   * @param target The target path in external storage.
   * @return Whether the operation is successful.
   * @author WuShuang
   */
  public static boolean write2Sdcard(InputStream is,String target) {
    OutputStream os = null;
    BufferedOutputStream bos = null;
    if (is != null) {
      try {
        Debug.log(target);
        os = new FileOutputStream(target);
        //Wrap a output stream with buffer area.
        bos = new BufferedOutputStream(os,BUFFER_SIZE);
        byte[] buffer = new byte[BUFFER_SIZE];
        int length;
        while ((length = is.read(buffer)) != -1) {
          bos.write(buffer,0,length);
        }
        return true;
      } catch (IOException e) {
        e.printStackTrace();
        delSdcardFile(target);
      } finally {
        try {
          if (is != null) {
            is.close();
          }
          if (bos != null) {
            bos.close();
          }
          if (os != null) {
            os.close();
          }
        } catch (IOException e) {
          return false;
        }
      }
    }
    return false;
  }

  /**
   * Unzip a zipped a file,and output the outcome to external storage.
   *
   * @param context Context,used to get Resources object.
   * @param zipId   The source id of the zipped file in raw directory.
   * @param target  The target path in external storage.
   * @return
   */
  public static boolean unzip(Context context,int zipId,String target) {
    //Get the input stream of zipped file.
    InputStream in = context.getResources().openRawResource(zipId);
    String zipFile = target + "temp";
    ZipFile zip = null;
    //First,copy the zipped file to external storage.
    if (write2Sdcard(in,zipFile)) {
      try {
        zip = new ZipFile(zipFile);
        Enumeration enumeration = zip.entries();
        ZipEntry ze;
        //Ierate the items in the zipped file.
        while (enumeration.hasMoreElements()) {
          ze = (ZipEntry)enumeration.nextElement();
          //Ignore the folder.
          if (ze.isDirectory()) {
            continue;
          }
          String name = ze.getName();
          boolean s = FileOperator.write2Sdcard(zip.getInputStream(ze),
                  target + name);
          if (s == false) {
            return s;
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (zip != null) {
          try {
            zip.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return true;
  }
}
