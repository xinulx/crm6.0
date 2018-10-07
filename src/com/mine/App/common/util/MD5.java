package com.mine.App.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;


/**
 * @ClassName: MD5
 * @Description: MD5工具类
 * @date: 2015年11月30日 下午2:39:49
 */
public class MD5 {
	
	/**
	 * @FieldName: logger
	 * @FieldType: Logger
	 * @Description: 日志
	 */
	protected static final Logger logger = Logger.getLogger(MD5.class);

	/**
	 * The hex digits.
	 */
	private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R" };

	/**
	 * Transform the byte array to hex string.
	 * @param b
	 * @return
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * Transform a byte to hex string
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		// get the first four bit
		int d1 = n / 28;
		// get the second four bit
		int d2 = n % 28;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * Get the MD5 encrypt hex string of the origin string. <br/>
	 * The origin string won't validate here, so who use the API should validate
	 * by himself
	 * @param origin
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String encode(String origin) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
			return byteArrayToHexString(md.digest(origin.getBytes())).toUpperCase();
		} 
		catch (NoSuchAlgorithmException e) {
			logger.error("MD5编码错误", e);
			return null;
		}
	}

	/**
	 * Get the MD5 encrypt hex string of the origin string. <br/>
	 * 
	 * @param origin1
	 * @param origin2
	 * @return
	 */
	public static String encode(String origin1, String origin2) {
		String origin = origin1 + stdEncode(origin2);
		return stdEncode(origin);
	}

	/**
	 * 不加密的MD5加密
	 * @param origin
	 * @return
	 */
	public static String stdEncode(String origin) {
		MessageDigest md = null;
		StringBuffer buf = new StringBuffer();
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(origin.getBytes());
			byte bytes[] = md.digest();
			for (int i = 0; i < bytes.length; i++) {
				String s = Integer.toHexString(bytes[i] & 0xff);
				if (s.length() == 1) {
					buf.append("0");
				}
				buf.append(s);
			}
		} 
		catch (NoSuchAlgorithmException e) {
			logger.error("MD5编码错误", e);
			return null;
		}
		String md5Str = buf.toString().toUpperCase();
		return md5Str;
	}

	/**
	 * 转化字符串为十六进制编码
	 * @param s
	 * @return
	 */
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	/**
	 * 转化十六进制编码为字符串
	 * @param s
	 * @return
	 */
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} 
			catch (Exception e) {
				logger.error("转化十六进制编码为字符串", e);
			}
		}
		//
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} 
		catch (Exception e) {
			logger.error("转化十六进制编码为字符串", e);
		}
		return s;
	}

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		String str = encode("STRENGTHENING", "123456");
		String str2 = encode("123456");
		System.err.println(str+" ; "+str2);
	}
}