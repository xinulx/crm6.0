package com.mine.common;

public class DataSourceUtil {

	/**
	 * 默认数据源,不需要根据本地网获取数据源时使用
	 **/
	public static final String DS_DEFAULT = "000";
	/**
	 * 数据源1
	 **/
	public static final String DS_FLAG_ONE = "111";
	/**
	 * 数据源2
	 **/
	public static final String DS_FLAG_TWO = "222";

	/**
	 * 数据源1对应的本地网标识
	 **/
	public static final String DS_ONE_LATN = SystemProperties.getProperty("com.fh.datasource.A");
	/**
	 * 数据源2对应的本地网标识
	 **/
	public static final String DS_TWO_LATN = SystemProperties.getProperty("com.fh.datasource.B");

	/**
	 * 是否使用多数据源标志：TRUE表示多数据源
	 **/
	public static final String DS_MULTI_FLAG = SystemProperties.getProperty("com.fh.datasource.C");
	
	/**
	 * 获取数据源标识的本地网KEY名
	 **/
	public static final String DS_LATN_KEY = "DS_LATN_KEY";
	
	/**
	 * 获取数据源标识
	 * 
	 * @param latnID
	 * @return
	 **/
	public static String getDsFlag(String latnID) {
		if(null != DS_MULTI_FLAG && !"null".equals(DS_MULTI_FLAG) && "TRUE".equals(DS_MULTI_FLAG)){
			if (DS_ONE_LATN.contains(latnID)) {
				return DS_FLAG_ONE;
			}
			if (DS_TWO_LATN.contains(latnID)) {
				return DS_FLAG_TWO;
			}
			throw new IllegalArgumentException("无效的本地网标识[" + latnID + "]");
		}else{
			return DS_DEFAULT;
		}
	}
	
	/**
	 * 获取数据源1
	 * 
	 * @return
	 **/
	public static String getDsFlagOne() {
		if (null != DS_MULTI_FLAG && "TRUE".equals(DS_MULTI_FLAG)) {
			return DS_FLAG_ONE;
		} else {
			return DS_DEFAULT;
		}
	}
	
	/**
	 * 获取数据源2
	 * @return
	 **/
	public static String getDsFlagTwo() {
		if (null != DS_MULTI_FLAG && "TRUE".equals(DS_MULTI_FLAG)) {
			return DS_FLAG_TWO;
		} else {
			return DS_DEFAULT;
		}
	}
}