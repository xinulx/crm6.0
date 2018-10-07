package com.mine.App.common.util;

import java.util.HashMap;
import java.util.Map;

import com.mine.App.model.User;

public class LoginHelper {
	public User user;
	
	public String userId;
	
	public String userName;
	
	public String orgId;
	
	public String deptNo;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getLoginUser(){
		LoginHelper LHP=new LoginHelper();
		Map paramsMap=new HashMap();
		paramsMap.put("user", LHP.getUser());
		paramsMap.put("userId", LHP.getUserId());
		paramsMap.put("userName", LHP.getUserName());
		paramsMap.put("orgId", LHP.getOrgId());
		paramsMap.put("deptNo", LHP.getDeptNo());
		return paramsMap;
	}
}