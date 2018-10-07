package com.mine.App.common.util;


import org.apache.log4j.Logger;

/**
 * 模糊化处理
 * @author shizuliang
 *
 */
public class FuzzyUtil {
	private static final Logger log = Logger.getLogger(FuzzyUtil.class);

	/**
	 * 姓名模糊化处理
	 * @param name
	 * @return
	 */
	public static String pubGetNameFuzzyData(String name){
		log.info("-------------入参:"+name);
		int length=name.length();
		char ch=name.charAt(length-1);
		String str="＊＊"+String.valueOf(ch);
		log.info("-------------出参:"+str);
		return str;
	}
	/**
	 * 证件号码模糊化处理 傻瓜式算法
	 * @param idType
	 * @return
	 */
	public static String pubGetIccidFuzzyData(String idType){
		log.info("-------------入参:"+idType);
		int length=idType.length();
		String str=null;
		if(length<=2){
			str= idType;
		}else if(length<=3){
			str= "＊"+idType.substring(1);
		}else if(length<=4){
			str= "＊＊"+idType.substring(2);
		}else if(length<=5){
			str= "＊＊＊"+idType.substring(3);
		}else if(length<=6){
			str= "＊＊＊＊"+idType.substring(4);
		}else if(length<=7){
			str= "＊＊＊＊＊"+idType.substring(5);
		}else if(length<=8){
			str= idType.substring(0,3)+"＊＊"+idType.substring(length-3);
		}else if(length<=9){
			str= idType.substring(0,3)+"＊＊＊"+idType.substring(length-3);
		}else if(length<=12){
			str= idType.substring(0,4)+"＊＊＊＊"+idType.substring(length-3);
		}else if(length<=17){
			str= idType.substring(0,6)+"＊＊＊＊＊＊"+idType.substring(length-3);
		}else if(length<=18){
			str= idType.substring(0,6)+"＊＊＊＊＊＊＊＊"+idType.substring(length-4,length-1)+"＊";
		}else{
			str= idType.substring(0,6)+"＊＊＊＊＊＊＊＊"+idType.substring(length-4,length-1)+"＊";
		}
		log.info("-------------出参:"+str);
		return str;
	}
	/**
	 * 地址模糊化处理
	 * @param addr
	 * @return
	 */
	public static String pubGetAddrFuzzyData(String addr){
		log.info("-------------出参:"+addr);
		String str="＊＊＊＊＊＊＊＊";
		log.info("-------------出参:"+str);
		return str;
		
	}
	/**
	 * 
	 * @param flag 1代表给姓名模糊化处理 2代表给证件模糊化处理 3代表给地址模糊化处理
	 * @param str
	 * @return
	 */
	public static String getFuzzyData(int flag,String str){
		String s=null;
		if(flag==1){
			s=pubGetNameFuzzyData(str);
		}else if(flag==2){
			s=pubGetIccidFuzzyData(str);
		}else if(flag==3){
			s=pubGetAddrFuzzyData(str);
		}
		return s;
	}
}
