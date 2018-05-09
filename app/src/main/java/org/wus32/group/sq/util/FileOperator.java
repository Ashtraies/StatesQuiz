package org.wus32.group.sq.util;

import android.content.Context;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 文件系统操作类
 *
 * @author WuShuang
 * @createtime 2014-2-19 上午11:11:46
 */
public class FileOperator {

  /**
   * 缓冲大小，1k
   */
  private static final int BUFFER_SIZE = 1024;

  /**
   * 输出raw中资源文件至SD卡
   *
   * @param context 上下文
   * @param rawId   资源id
   * @param target  目标路径
   * @return
   * @author WuShuang
   * @createtime 2014-2-19 上午11:34:33
   */
  public static boolean writeRaw2Sdcard(Context context,int rawId,String target) {
    InputStream is = context.getResources().openRawResource(rawId);
    return write2Sdcard(is,target);

  }

  /**
   * 创建文件夹
   *
   * @param name
   * @return
   * @author WuShuang
   * @createtime 2014-2-19 上午11:35:26
   */
  public static boolean createFolder(String name) {
    File folder = new File(name);
    if (folder.exists()) {
      return true;
    } else {
      return folder.mkdirs();
    }
  }

  /**
   * 删除SD卡上指定文件
   *
   * @param target 指定文件路径
   * @return
   * @author WuShuang
   * @createtime 2014-2-19 上午11:33:15
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
   * 检测文件或文件夹是否存在
   *
   * @param name 目标文件路径
   * @return
   * @author WuShuang
   * @createtime 2014-2-19 上午11:35:40
   */
  public static boolean checkExist(String name) {
    File file = new File(name);
    return file.exists();
  }

  /**
   * 将文件输出到SD卡
   *
   * @param is     文件输入流
   * @param target 目标路径
   * @return
   * @author WuShuang
   * @createtime 2014-2-19 上午11:32:00
   */
  public static boolean write2Sdcard(InputStream is,String target) {
    OutputStream os = null;
    BufferedOutputStream bos = null;
    if (is != null) {
      try {
        os = new FileOutputStream(target);
        bos = new BufferedOutputStream(os,1024);
        byte[] buffer = new byte[BUFFER_SIZE];
        int length = -1;
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
   * 以字节流的形式读取文件
   *
   * @param path
   * @return 文件字节流
   * @throws IOException
   * @author WuShuang
   * @createtime 2014-2-19 上午11:24:18
   */
  public static byte[] readFileToByteArray(String path) throws IOException {
    if (new File(path).exists()) {
      InputStream is = new FileInputStream(path);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try {
        byte[] b = new byte[BUFFER_SIZE];
        int n = 0;
        while ((n = is.read(b)) != -1) {
          baos.write(b,0,n);
        }
        baos.flush();
      } finally {
        is.close();
        baos.close();
      }
      return baos.toByteArray();
    }
    return null;
  }

  public static boolean unzip(Context context,int zipId,String target) {
    InputStream in = context.getResources().openRawResource(zipId);
    File targetFile = new File(target);
    if (!targetFile.exists()) {
      boolean s = targetFile.mkdirs();
      if (s == false) {
        return s;
      }
    }
    String zipFile = target + File.separator + "temp";
    ZipFile zip = null;
    if (write2Sdcard(in,zipFile)) {
      try {
        zip = new ZipFile(zipFile);
        Enumeration enumeration = zip.entries();
        ZipEntry ze = null;
        while (enumeration.hasMoreElements()) {
          ze = (ZipEntry)enumeration.nextElement();
          if (ze.isDirectory()) {
            continue;
          }
          String name = ze.getName().split("/")[1];
          boolean s = FileOperator.write2Sdcard(zip.getInputStream(ze),
                  target + File.separator + name);
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
