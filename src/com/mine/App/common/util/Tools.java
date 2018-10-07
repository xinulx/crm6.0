package com.mine.App.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.Enumeration;
import java.util.Random;

public class Tools
{
  public static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', 
    '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

  public static String getCipherCode(int len)
  {
    String[] randomStr = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
    Random ran = new Random();

    int temp = 0;

    StringBuffer strBuf = new StringBuffer();
    strBuf.delete(0, strBuf.length());
    for (int i = 0; i < len; ++i) {
      temp = Math.abs(ran.nextInt(randomStr.length));
      strBuf.append(randomStr[temp]);
    }
    return strBuf.toString();
  }

  public static byte[] AscToBcd(String source)
  {
    if (source == null) {
      return null;
    }
    int len = source.length();
    len /= 2;
    byte[] dest = new byte[len];

    for (int i = 0; i < len; ++i)
    {
      byte b1;
      byte b2;
      char c1 = source.charAt(i * 2);
      char c2 = source.charAt(i * 2 + 1);

      if ((c1 >= '0') && (c1 <= '9'))
        b1 = (byte)(c1 - '0');
      else if ((c1 >= 'a') && (c1 <= 'z'))
        b1 = (byte)(c1 - 'a' + 10);
      else {
        b1 = (byte)(c1 - 'A' + 10);
      }

      if ((c2 >= '0') && (c2 <= '9'))
        b2 = (byte)(c2 - '0');
      else if ((c2 >= 'a') && (c2 <= 'z'))
        b2 = (byte)(c2 - 'a' + 10);
      else {
        b2 = (byte)(c2 - 'A' + 10);
      }

      dest[i] = (byte)(b1 << 4 | b2);
    }
    return dest;
  }

  public static String BcdToAsc(byte[] bcdstr)
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < bcdstr.length; ++i) {
      byte b = (byte)((bcdstr[i] & 0xF0) >> 4);
      char c = abcd_to_asc(b);
      sb.append(c);

      b = (byte)(bcdstr[i] & 0xF);
      c = abcd_to_asc(b);
      sb.append(c);
    }
    return sb.toString();
  }

  public static char abcd_to_asc(byte abyte)
  {
    char buf;
    if (abyte <= 9)
      buf = (char)(abyte + 48);
    else {
      buf = (char)(abyte + 65 - 10);
    }
    return buf;
  }

  public static String getHash(String fileName)
    throws Exception
  {
    return getHash(new File(fileName));
  }

  public static String getHash(File file)
    throws Exception
  {
    FileInputStream fis = new FileInputStream(file);
    int len = fis.available();
    byte[] buffer = new byte[len];
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    int numRead = 0;
    while ((numRead = fis.read(buffer)) > 0) {
      md5.update(buffer, 0, numRead);
    }
    fis.close();

    return toHexString(md5.digest());
  }

  public static String toHexString(byte[] b)
  {
    StringBuffer strBuf = new StringBuffer();
    for (int i = 0; i < b.length; ++i) {
      strBuf.append(hexChar[((b[i] & 0xF0) >>> 4)]);
      strBuf.append(hexChar[(b[i] & 0xF)]);
    }

    return strBuf.toString();
  }

  public static int compareByte(byte[] b1, byte[] b2)
  {
    if ((b1 == null) || (b2 == null) || (b1.length != b2.length)) {
      return -1;
    }

    int flag = 0;
    int i = 0;
    while (i < b1.length) {
      if (b1[i] != b2[i]) {
        flag = -1;
        break;
      }
      ++i;
    }

    return flag;
  }

  public static PrivateKey getPrivateKey(String fileName, String passWord)
    throws IOException
  {
    InputStream fis = null;
    try
    {
      String p12FileName = fileName;
      String pfxPassword = passWord;
      fis = new FileInputStream(p12FileName);

      KeyStore keyStore = KeyStore.getInstance("PKCS12");

      keyStore.load(fis, pfxPassword.toCharArray());
      fis.close();
      Enumeration aliases = keyStore.aliases();
      String aliaesName = "";
      while (aliases.hasMoreElements()) {
        aliaesName = (String)aliases.nextElement();
      }

      return ((PrivateKey)keyStore.getKey(aliaesName, 
        pfxPassword.toCharArray()));
    } catch (Exception localException) {
    }
    finally {
      if (fis != null)
        fis.close();
    }
    if (fis != null) {
      fis.close();
    }
    return null;
  }

  public static synchronized void renameFile(String pathName)
  {
    File oldfile = new File("history/" + pathName);
    if (oldfile.exists())
      System.out.println(pathName + "已经存在！");
    else
      oldfile.renameTo(oldfile);
  }
}
