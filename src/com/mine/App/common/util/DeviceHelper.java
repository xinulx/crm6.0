package com.mine.App.common.util;

import org.apache.log4j.Logger;


/**
 * @ClassName: DeviceHelper
 * @Description: 访问设备工具类
 * @date: 2014年12月19日 上午11:14:18
 */
public class DeviceHelper {

	/**
	 * @FieldName: logger
	 * @FieldType: Logger
	 * @Description: 日志
	 */
	protected static final Logger logger = Logger.getLogger(DeviceHelper.class);

	/**
	 * @FieldName: mobileAgents
	 * @FieldType: String[]
	 * @Description: mobile agents
	 */
	private static String[] mobileAgents = { 
		"iphone", "android", "ipod", "ipad", "windows phone", "mqqbrowser", "phone", "mobile",
		"wap", "netfront", "java", "opera mobi", "opera mini", "ucweb",
		"windows ce", "symbian", "series", "webos", "sony", "blackberry",
		"dopod", "nokia", "samsung", "palmsource", "xda", "pieplus",
		"meizu", "midp", "cldc", "motorola", "foma", "docomo",
		"up.browser", "up.link", "blazer", "helio", "hosin", "huawei",
		"novarra", "coolpad", "webos", "techfaith", "palmsource",
		"alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips",
		"sagem", "wellcom", "bunjalloo", "maui", "smartphone", "iemobile",
		"spice", "bird", "zte-", "longcos", "pantech", "gionee",
		"portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct",
		"320x320", "240x320", "176x220", "w3c ", "acs-", "alav", "alca",
		"amoi", "audi", "avan", "benq", "bird", "blac", "blaz", "brew",
		"cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno",
		"ipaq", "java", "jigs", "kddi", "keji", "leno", "lg-c", "lg-d",
		"lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
		"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm",
		"pana", "pant", "phil", "play", "port", "prox", "qwap", "sage",
		"sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar",
		"sie-", "siem", "smal", "smar", "sony", "sph-", "symb", "t-mo",
		"teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v", "voda",
		"wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw",
		"xda", "xda-", "Googlebot-Mobile"
	};

    /**
     * @Title: isMobile
     * @Description: 是否是手机浏览器
     * @param agent
     * @return
     */
    public static boolean isMobile(String agent) {
    	boolean isMoblie = false;
		if (agent != null) {
			agent = agent.toLowerCase();
			//排除Windows桌面
			if (!agent.contains("windows nt") || (agent.contains("windows nt") && agent.contains("compatible; msie 9.0;"))){
				//排除OSX
	            if (!agent.contains("windows nt") && !agent.contains("macintosh")){
	    			for (String mobileAgent : mobileAgents) {
	    				if (agent.contains(mobileAgent)) {
	    					isMoblie = true;
	    					break;
	    				}
	    			}
	            }
			}
		}
		return isMoblie;
    }

}
