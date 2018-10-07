/**
 * 系统消息、常量
 */
package com.mine.App.common.util;

/**
 * @author db2admin
 *
 */
public interface Constant {
	/**登陆模块*/
	public static final String LOGIN_SUCCESS="登陆成功";

	public static final String LOGIN_FAIL="登陆失败!";

	public static final String PASSWORD_ERROR="密码错误!";

	public static final String USERNAME_ERROR="用户名错误!";

	public static final String EMPTY_USERNAME="用户名为空!";

	public static final String MORE_LOGINNUM="登陆次数过多，账户已锁定!";

	public static final String LOADING_INFO="登陆成功，马上转到系统...";

	/**首页*/
	public static final String MENU_INDEX="INDEX";//首页

	public static final String MENU_MESSAGE="MESSAGE";//我的消息

	public static final String MENU_PERSONAL="PERSONAL";//个人信息

	public static final String MENU_SYSTEM="SYSTEM";//系统设置

	public static final String MENU_LOGOUT="LOGOUT";//退出登陆

	public static final String MENU_PARAMS="101";//首页菜单渠道

	public static final String MENU_ERROR="参数异常，请检查http请求路径是否正确！";

	public static final String MENU_MSG="获取菜单信息成功！";

	public static final String MENU_QUERYERROR="查询菜单信息异常，请检查服务器sql配置！";

	/**强化加密*/
	public static final String PWD_STH="STRENGTHENING";

	/**修改密码*/
	public static final String EMPTY_USERID="用户编号为空！";

	public static final String EMPTY_OLD_PASSWORD="原密码为空！";

	public static final String EMPTY_NEW_PASSWORD="新密码不能为空！";

	public static final String EMPTY_FINAL_PASSWORD="确认密码不能为空！";

	public static final String EMPTY_CODE="验证码为空！";

	public static final String ERROR_USERID="用户编号错误！";

	public static final String ERROR_OLD_PASSWORD="原密码错误！";

	public static final String SAME_PWD="新密码不能与原密码相同！";

	public static final String NOSAME_PWD="确认密码与新密码不一致！";

	public static final String ERROR_CODE="验证码错误！";

	public static final String TIMEOUT_SESSIONCODE="验证码已过期！";

	public static final String PWDMODI_SUCCESS="密码修改成功！！";

	public static final String USER_AUTHEN="用户权限不足，不能登录！";

	/** 2003及以前版本 */
	public static final String EXCEL_EX_2003 = "xls";

	/**2007版本*/
	public static final String EXCEL_EX_2007 = "xlsx";

	/**视图参数*/
	public static final String PAGE_TYPE = "PAGE_TYPE";

	/**视图解析JSP*/
	public static final String JSP_PAGE="jsp";

	/**视图解析HTML*/
	public static final String HTML_PAGE="html";

	/**版本控制*/
	public static final int VERSION = 261;
}
