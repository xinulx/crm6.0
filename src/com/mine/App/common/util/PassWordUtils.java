package com.mine.App.common.util;

import java.util.Random;

/**
 * @Description 弱密码校验类
 * @createTime: 2014-10-08 下午13:44:34
 */
public class PassWordUtils {

	/**
	 * 
	 * @param length
	 *            随机密码长度
	 * @param flag
	 *            随机密码组合方式 0 -- 0 ~ 9 1 -- a ~ z 2 -- A ~ Z 3 -- 0 ~ 9 + a ~ z 4
	 *            -- 0 ~ 9 + A ~ Z 5 -- a ~ z + A ~ Z 6 -- 0 ~ 9 + a ~ z + A ~ Z
	 * @return 随机密码
	 * 
	 */
	public static String getRandPwd(int length, int flag) {
		int head = 0, distance = 0, len = 0;
		int i = 0, randNum = 0;
		StringBuffer buffer = new StringBuffer();
		String sCombinationPwd = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		switch (flag) {
		case 0:
			head = 0;
			distance = 0;
			len = 10;
			break;
		case 1:
			head = 10;
			distance = 0;
			len = 26;
			break;
		case 2:
			head = 36;
			distance = 0;
			len = 26;
			break;
		case 3:
			head = 0;
			distance = 0;
			len = 36;
			break;
		case 4:
			head = 0;
			distance = 26;
			len = 36;
			break;
		case 5:
			head = 10;
			distance = 0;
			len = 52;
			break;
		case 6:
			head = 0;
			distance = 0;
			len = 62;
			break;
		default:
			throw new RuntimeException("传入的密码组合方式错误[0 ~ 6]: " + flag);
		}

		Random random = new Random();

		for (i = 0; i < length; i++) {
			randNum = random.nextInt(len) + head;
			if (distance > 0) {
				if (randNum > 9) {
					randNum = randNum + distance;
				}
				buffer.append(sCombinationPwd.charAt(randNum));
			} else {
				buffer.append(sCombinationPwd.charAt(randNum));
			}
		}
		return buffer.toString();
	}

	/**
	 * 该方法为IMS获取随机密码特有，在基础密码基础上 将字母（包含大小写）I、L、U、V、O，数字1、0去掉
	 * 
	 * @param length
	 *            随机密码长度
	 * @param flag
	 *            随机密码组合方式 0 -- 0 ~ 9 1 -- a ~ z 2 -- A ~ Z 3 -- 0 ~ 9 + a ~ z 4
	 *            -- 0 ~ 9 + A ~ Z 5 -- a ~ z + A ~ Z 6 -- 0 ~ 9 + a ~ z + A ~ Z
	 * 
	 * 
	 * @return 随机密码
	 * 
	 */
	public static String getIMSRandPwd(int length, int flag) {
		int head = 0, distance = 0, len = 0;
		int i = 0, randNum = 0;
		StringBuffer buffer = new StringBuffer();
		String sCombinationPwd = "23456789abcdefghjkmnpqrstwxyzABCDEFGHJKMNPQRSTWXYZ";
		switch (flag) {
		case 0:
			head = 0;
			distance = 0;
			len = 8;
			break;
		case 1:
			head = 8;
			distance = 0;
			len = 21;
			break;
		case 2:
			head = 29;
			distance = 0;
			len = 21;
			break;
		case 3:
			head = 0;
			distance = 0;
			len = 29;
			break;
		case 4:
			head = 0;
			distance = 21;
			len = 29;
			break;
		case 5:
			head = 8;
			distance = 0;
			len = 42;
			break;
		case 6:
			head = 0;
			distance = 0;
			len = 50;
			break;
		default:
			throw new RuntimeException("传入的密码组合方式错误[0 ~ 6]: " + flag);
		}

		Random random = new Random();

		for (i = 0; i < length; i++) {
			randNum = random.nextInt(len) + head;
			if (distance > 0) {
				if (randNum > 7) {
					randNum = randNum + distance;
				}
				buffer.append(sCombinationPwd.charAt(randNum));
			} else {
				buffer.append(sCombinationPwd.charAt(randNum));
			}
		}
		return buffer.toString();
	}

	/**
	 * @Description 弱密码校验公共方法
	 * @author: zhaopf
	 * @createTime: 2014-10-08 下午13:44:34
	 */
	public static String checkPubWeakPwd(final String pwd) {
		String erroReson = "";
		if (StringUtils.isEmptyOrNull(pwd)) {
			erroReson = "您设置的密码不能为空！";
		} else if (pwd.length() < 6) {
			erroReson = "您设置的密码过于简单，密码不能少于六位！";
		} else if (!StringUtils.isLongNumber(pwd)) {
			erroReson = "您设置的密码受限，密码必须是数字！";
		} else if (valideRepeatNum(pwd)) {
			erroReson = "你设置的密码过于简单,请勿使用重号作为密码！";
		} else if (validFormatNum(pwd)) {
			erroReson = "你设置的密码过于简单,密码至少含有2个以上的不同数字组成！";
		} else if (validIncreasing(pwd)) {
			erroReson = "你设置的密码过于简单，密码连续5位及以上不能是升序数字组合！";
		} else if (validDecreasing(pwd)) {
			erroReson = "你设置的密码过于简单，密码连续5位及以上不能是倒序数字组合！";
		} else if (validContinuous(pwd.substring(0, 3))
				&& validContinuous(pwd.substring(pwd.length() - 3, pwd.length()))) {
			erroReson = "你设置的密码过于简单，前三位后三位不能均为连续数字，如密码不能是123321、456123、987123、987321!";
		}

		return erroReson;
	}

	/**
	 * @Description 弱密码校验公共方法
	 * @author: licc_bj
	 * @createTime: 2016-10-08 下午13:44:34
	 */
	public static String checkPubWeakPwdMin(final String pwd) {
		String erroReson = "";
		if (StringUtils.isEmptyOrNull(pwd)) {
			erroReson = "您设置的密码不能为空！";
		} else if (pwd.length() < 6) {
			erroReson = "您设置的密码过于简单，密码不能少于六位！";
		} else if (!StringUtils.isLongNumber(pwd)) {
			erroReson = "您设置的密码受限，密码必须是数字！";
		} else if (valideRepeatNum(pwd)) {
			erroReson = "你设置的密码过于简单,请勿使用重号作为密码！";
		} else if (validIncreasingMin(pwd)) {
			erroReson = "你设置的密码过于简单，密码连续6位不能是升序数字组合！";
		} else if (validDecreasingMin(pwd)) {
			erroReson = "你设置的密码过于简单，密码连续6位不能是倒序数字组合！";
		}

		return erroReson;
	}

	/**
	 * @Description 弱密码校验公共方法，包含校验证件及手机号码及其他校验项
	 * @author: guanlp
	 * @createTime: 2014-10-08 下午13:44:34
	 */
	public static String checkNewWeakPwd(final String pwd, final String idIccid, final String phoneNo) {
		String erroReson = checkWeakPwd(pwd);
		if ("".equals(erroReson)) {
			if (StringUtils.isNotEmptyOrNull(idIccid) && idIccid.indexOf(pwd) != -1) {
				// erroReson = "您设置的密码过于简单，请勿使用证件的连续"+pwd.length()+"位作为服务密码！";
				erroReson = "0001";
			} else if (phoneNo.indexOf(pwd) != -1) {
				// erroReson = "您设置的密码过于简单，请勿使用手机号的连续"+pwd.length()+"位作为服务密码！";
				erroReson = "0002";
			}
		}

		return erroReson;
	}

	/**
	 * @Description 密码修改密码校验公共方法
	 * @author: guanlp
	 * @createTime: 2014-10-08 下午13:44:34
	 */
	// TODO
	public static String checkWeakPwd(final String pwd) {
		String erroReson = "";
		if (StringUtils.isEmptyOrNull(pwd)) {
			// erroReson = "您设置的密码不能为空！";
			erroReson = "0003";
		} else if (pwd.length() < 6) {
			// erroReson = "您设置的密码过于简单，密码不能少于六位！";
			erroReson = "0004";
		} else if (!StringUtils.isLongNumber(pwd)) {
			// erroReson = "您设置的密码受限，密码必须是数字！";
			erroReson = "0005";
		} else if (valideRepeatNum(pwd)) {
			// erroReson = "你设置的密码过于简单,请勿使用重号作为密码！";
			erroReson = "0006";
		} else if (validFormatNum(pwd)) {
			// erroReson = "你设置的密码过于简单,密码至少含有2个以上的不同数字组成！";
			erroReson = "0007";
		} else if (validIncreasing(pwd)) {
			// erroReson = "你设置的密码过于简单，密码连续5位及以上不能是升序数字组合！";
			erroReson = "0008";
		} else if (validDecreasing(pwd)) {
			// erroReson = "你设置的密码过于简单，密码连续5位及以上不能是倒序数字组合！";
			erroReson = "0009";
		} else if (validContinuous(pwd.substring(0, 3))
				&& validContinuous(pwd.substring(pwd.length() - 3, pwd.length()))) {
			// erroReson =
			// "你设置的密码过于简单，前三位后三位不能均为连续数字，如密码不能是123321、456123、987123、987321!";
			erroReson = "0010";
		}

		return erroReson;
	}

	/**
	 * @Description 弱密码校验公共方法,校验不通过，直接抛出异常信息
	 * @author: songTao
	 * @createTime: 2014-10-08 下午13:44:34
	 */
	public static void checkPubWeakPwdThrow(final String pwd) {
		if (StringUtils.isEmptyOrNull(pwd)) {
			throw new RuntimeException("您设置的密码不能为空！");
		} else if (pwd.length() < 6) {
			throw new RuntimeException("您设置的密码过于简单，密码不能少于六位！");
		} else if (!StringUtils.isLongNumber(pwd)) {
			throw new RuntimeException("您设置的密码受限，密码必须是数字！");
		} else if (valideRepeatNum(pwd)) {
			throw new RuntimeException("你设置的密码过于简单,请勿使用重号作为密码！");
		} else if (validFormatNum(pwd)) {
			throw new RuntimeException("你设置的密码过于简单,密码至少含有2个以上的不同数字组成！");
		} else if (validIncreasing(pwd)) {
			throw new RuntimeException("你设置的密码过于简单，密码连续5位及以上不能是升序数字组合！");
		} else if (validDecreasing(pwd)) {
			throw new RuntimeException("你设置的密码过于简单，密码连续5位及以上不能是倒序数字组合！");
		} else if (validContinuous(pwd.substring(0, 3))
				&& validContinuous(pwd.substring(pwd.length() - 3, pwd.length()))) {
			throw new RuntimeException("你设置的密码过于简单，前三位后三位不能均为连续数字，如密码不能是123321、456123、987123、987321!");
		}

	}

	/**
	 * @Description 弱密码校验公共方法，包含校验证件及手机号码及其他校验项
	 * @author: zhaopf
	 * @createTime: 2014-10-08 下午13:44:34
	 */
	public static String checkSpeclWeakPwd(final String pwd, final String idIccid, final String phoneNo) {
		String erroReson = checkPubWeakPwd(pwd);
		if ("".equals(erroReson)) {
			if (StringUtils.isNotEmptyOrNull(idIccid) && idIccid.indexOf(pwd) != -1) {
				erroReson = "您设置的密码过于简单，请勿使用证件的连续" + pwd.length() + "位作为服务密码！";
			} else if (phoneNo.indexOf(pwd) != -1) {
				erroReson = "您设置的密码过于简单，请勿使用手机号的连续" + pwd.length() + "位作为服务密码！";
			}
		}

		return erroReson;
	}

	/**
	 * @Description 弱密码校验公共方法，包含校验证件及手机号码及其他校验项
	 * @author: licc_bj
	 * @createTime: 2016-10-08 下午13:44:34
	 */
	public static String checkSpeclWeakPwdMin(final String pwd, final String idIccid, final String phoneNo) {
		String erroReson = checkPubWeakPwdMin(pwd);
		if ("".equals(erroReson)) {
			if (StringUtils.isNotEmptyOrNull(idIccid) && idIccid.indexOf(pwd) != -1) {
				erroReson = "您设置的密码过于简单，请勿使用证件的连续" + pwd.length() + "位作为服务密码！";
			} else if (phoneNo.indexOf(pwd) != -1) {
				erroReson = "您设置的密码过于简单，请勿使用手机号的连续" + pwd.length() + "位作为服务密码！";
			}
		}

		return erroReson;
	}

	/**
	 * @Description 弱密码校验公共方法，包含校验证件及手机号码及其他校验项 校验不通过，直接抛出异常信息
	 * @author: songtao
	 * @createTime: 2014-10-08 下午13:44:34
	 */
	public static void checkSpeclWeakPwdThrow(final String pwd, final String idIccid, final String phoneNo) {
		checkPubWeakPwdThrow(pwd);
		if (StringUtils.isNotEmptyOrNull(idIccid) && idIccid.indexOf(pwd) != -1) {
			throw new RuntimeException("您设置的密码过于简单，请勿使用证件的连续" + pwd.length() + "位作为服务密码！！");
		} else if (phoneNo.indexOf(pwd) != -1) {
			throw new RuntimeException("您设置的密码过于简单，请勿使用手机号的连续" + pwd.length() + "位作为服务密码！");
		}
	}

	/**
	 * @Description 弱密码校验黑龙江方法，包含校验证件及手机号码及其他校验项 校验不通过，直接抛出异常信息
	 * @author: liujian
	 * @createTime: 2016-11-08 下午13:44:34
	 */
	public static void checkWeakPwdThrow(final String pwd, final String idIccid, final String phoneNo) {
		if (StringUtils.isEmptyOrNull(pwd)) {
			throw new RuntimeException("您设置的密码不能为空！");
		} else if (pwd.length() < 6) {
			throw new RuntimeException("您设置的密码过于简单，密码不能少于六位！");
		} else if (!StringUtils.isLongNumber(pwd)) {
			throw new RuntimeException("您设置的密码受限，密码必须是数字！");
		} else if (valideRepeatNum(pwd)) {
			throw new RuntimeException("你设置的密码过于简单,请勿使用重号作为密码！");
		} else if (validIncreasing(pwd)) {
			throw new RuntimeException("你设置的密码过于简单，密码连续5位及以上不能是升序数字组合！");
		} else if (validDecreasing(pwd)) {
			throw new RuntimeException("你设置的密码过于简单，密码连续5位及以上不能是倒序数字组合！");
		} else if (validContinuous(pwd.substring(0, 3))
				&& validContinuous(pwd.substring(pwd.length() - 3, pwd.length()))) {
			throw new RuntimeException("你设置的密码过于简单，前三位后三位不能均为连续数字，如密码不能是123321、456123、987123、987321!");
		}
		if (StringUtils.isNotEmptyOrNull(idIccid) && idIccid.indexOf(pwd) != -1) {
			throw new RuntimeException("您设置的密码过于简单，请勿使用证件的连续" + pwd.length() + "位作为服务密码！！");
		} else if (phoneNo.indexOf(pwd) != -1) {
			throw new RuntimeException("您设置的密码过于简单，请勿使用手机号的连续" + pwd.length() + "位作为服务密码！");
		}
	}

	/**
	 * @Description: 获取已验证的随机密码
	 * @author: zhaopf
	 * @createTime: 2014年10月30日 下午5:26:59
	 */
	public static String getPwd(final String phone, final String idiccid) {
		String pwd = "";
		String checkFlag = "";
		boolean flag = false;
		do {
			pwd = PassWordUtils.getRandPwd(6);
			checkFlag = PassWordUtils.checkPubWeakPwd(pwd);
			if ("".equals(checkFlag)) {
				flag = true;
				break;
			}
		} while (flag);
		return pwd;
	}

	/**
	 * @Description: 获取已验证的随机密码 包含服务号码和身份证号码校验
	 * @author: zhaopf
	 * @createTime: 2014年10月30日 下午5:26:59
	 */
	public static String getPwdSpecl(final String phone, final String idiccid) {
		String pwd = "";
		String checkFlag = "";
		boolean flag = false;
		do {
			pwd = PassWordUtils.getRandPwd(6);
			checkFlag = PassWordUtils.checkSpeclWeakPwd(pwd, idiccid, phone);
			if ("".equals(checkFlag)) {
				flag = true;
				break;
			}
		} while (flag);
		return pwd;
	}

	/**
	 * @Description: 获取已验证的随机密码 包含服务号码和身份证号码校验
	 * @author: licc_bj
	 * @createTime: 2016年10月30日 下午5:26:59
	 */
	public static String getPwdSpeclMin(final String phone, final String idiccid) {
		String pwd = "";
		String checkFlag = "";
		boolean flag = false;
		do {
			pwd = PassWordUtils.getRandPwd(6);
			checkFlag = PassWordUtils.checkSpeclWeakPwdMin(pwd, idiccid, phone);
			if ("".equals(checkFlag)) {
				flag = true;
				break;
			}
		} while (flag);
		return pwd;
	}

	/**
	 * @Description: 不能为重号
	 * @author: zhaopf
	 * @createTime: 2014年10月30日 下午5:26:59
	 */
	public static boolean valideRepeatNum(String numStr) {
		boolean flag = true;
		char str = numStr.charAt(0);
		for (int i = 1; i < numStr.length(); i++) {
			if (str != numStr.charAt(i)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * @Description: 不能包含5位以上的连续的数字--递增（如：123456、3456789）连续数字返回true
	 * @author: zhaopf
	 * @createTime: 2014年10月30日 下午5:26:59
	 */
	public static boolean validIncreasing(String numStr) {
		boolean flag = false;
		String allNum = "01234567890";
		for (int i = 0; i < numStr.length() - 5; i++) {
			String str = numStr.substring(i, i + 5);
			if (allNum.indexOf(str) > 0) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * @Description: 不能包含6位连续的数字--递增（如：123456、345678）连续数字返回true
	 * @author: licc_bj
	 * @createTime: 2016年10月30日 下午5:26:59
	 */
	public static boolean validIncreasingMin(String numStr) {
		boolean flag = false;
		String allNum = "0123456789";
		if (allNum.indexOf(numStr) > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * @Description: 不能包含5位以上的连续的数字--递减（如：987654、876543）连续数字返回true
	 * @author: zhaopf
	 * @createTime: 2014年10月30日 下午5:26:59
	 */
	public static boolean validDecreasing(String numStr) {
		boolean flag = false;
		String allNum = "09876543210";
		for (int i = 0; i < numStr.length() - 5; i++) {
			String str = numStr.substring(i, i + 5);
			if (allNum.indexOf(str) > 0) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * @Description: 不能包含6位连续的数字--递减（如：987654、876543）连续数字返回true
	 * @author: licc_bj
	 * @createTime: 2014年10月30日 下午5:26:59
	 */
	public static boolean validDecreasingMin(String numStr) {
		boolean flag = false;
		String allNum = "9876543210";
		if (allNum.indexOf(numStr) > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * @Description: 不能为连续的数字--递减（如：987654、876543）连续数字返回true
	 * @author: songTao
	 * @createTime: 2014年10月30日 下午5:26:59
	 */
	public static boolean validContinuous(String numStr) {
		boolean flag = false;
		String allInNum = "01234567890";
		String allDeNum = "09876543210";
		if (allInNum.indexOf(numStr) > 0 || allDeNum.indexOf(numStr) > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * @Description: 获取字符串中至少含有3个或三个以上的不同字符（如：121221、121112） 返回true
	 * @author: songTao
	 * @createTime: 2014年10月30日 下午5:26:59
	 */
	public static boolean validFormatNum(String numStr) {
		boolean flag = true;
		char str = numStr.charAt(0);
		String str2 = "";
		for (int i = 1; i < numStr.length(); i++) {
			if (str != numStr.charAt(i) && "".equals(str2)) {
				str2 = "" + numStr.charAt(i);
				continue;
			} else if (str != numStr.charAt(i) && str2.indexOf(numStr.charAt(i)) == -1) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * @Description: 获取随机密码
	 * @author: zhaopf
	 * @createTime: 2014年10月30日 下午5:26:59
	 */
	public static String getRandPwd(int k) {
		String allChar = "0123456789";
		StringBuffer sb = new StringBuffer();
		Random random = new Random();

		for (int i = 0; i < k; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}

	/**
	 * @Description: 后台加密
	 * @author: wangcp
	 * @createTime: 2017年4月25日 上午9:46:59 src需要加密的字符，opType为0或1，0代表解密，1代表加密
	 */
	public static String pubEncryptPasswd(String src, String opType) {

		// 加一个校验，此加密函数不支持字母加密，紧适用于数字加密
		for (int i = 0; i < src.length(); i++) {
			if (src.charAt(i) < '0' || src.charAt(i) > '9') {
				return "密码为数字密码";
			}
		}
		int i = 0;
		String dst = "";
		int[] TemStr = new int[6];
		String ptsr = "PLMOKNIJBUHYGVTFCRDXESZAQWqazwsxedcrfvtgbyhnujmikolp1234567890JBUHYGVTFC-*&^$#@!;]";
		int len = src.length();
		src = src.trim();
		if ("1".equals(opType)) {
			for (i = 0; i < src.length(); ++i) {
				dst = dst + ptsr.charAt((src.charAt(i) - '0' + i + 1) * (len / 2 + 1));
			}
		} else {
			for (int j = 0; j < len; ++j) {

				int c = ptsr.indexOf(src.charAt(j));
				if (c == -1) {
					TemStr[j] = 0;
				} else {
					// System.out.println((c - 188) / (len / 2 + 1) + "****");
					TemStr[j] = (((c - 192) / (len / 2 + 1) - j - 1) + '0');
				}

			}
			String StrOut = "";
			for (int j = 0; j < TemStr.length; j++) {
				StrOut = StrOut + TemStr[j];
			}
			return StrOut.trim();
		}

		return dst;
	}
}
