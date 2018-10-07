package com.mine.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookiesUtils {	
	//public static final String COOKIE_DOMAIN = "";
	public static final String COOKIE_PATH = "/";

/**
 * 获取COOKIE
 * 
 * @param request
 * @param name
 */
public static Cookie getCookie(HttpServletRequest request, String name) {
	Cookie[] cookies = request.getCookies();
	if (cookies == null)
		return null;
	for (int i = 0; i < cookies.length; i++) {
		if (name.equals(cookies[i].getName())) {
			return cookies[i];
		}
	}
	return null;
}

/**
 * 设置COOKIE
 * 
 * @param response
 * @param name
 * @param value
 * @param maxAge
 *            cookie在客户端最大的有效时间，以秒为单位。默认情况下为-1，表示cookie将一直有效直到浏览器关闭
 */
public static void setCookie(HttpServletResponse response, String name,
		String value, int maxAge) {
	setCookie(response, name, value, maxAge, COOKIE_PATH);
}

public static void setCookie(HttpServletResponse response, String name,
		String value, int maxAge,String path) {
	Cookie cookie = new Cookie(name, value);
	if (maxAge > -1)
		cookie.setMaxAge(maxAge);
	/*
	 * if (COOKIE_DOMAIN != null && COOKIE_DOMAIN.indexOf('.') != -1) {
	 * cookie.setDomain('.' + COOKIE_DOMAIN); }
	 */
	// cookie.setDomain(COOKIE_DOMAIN);
	cookie.setPath(path);
	response.addCookie(cookie);
}

/**
 * 设置COOKIE 为无效状态
 * @param request
 */
public static void invalidAllCookies(HttpServletRequest request,HttpServletResponse response) {
	Cookie[] cookies = request.getCookies();
	if (cookies != null){
		for (int i = 0; i < cookies.length; i++) {
			//清除掉浏览器所有的Cookie,但保留样式选择的Cookie，以便下次选择时能正确获取上次换肤后的样式
			if(!"SSOSKIN".equals(cookies[i].getName())){
				cookies[i].setPath(COOKIE_PATH);
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
			}
		}
	}
}

}
