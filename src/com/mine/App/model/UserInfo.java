package com.mine.App.model;

import java.sql.Timestamp;

public class UserInfo {
	private Integer rid;
	
	private String userId;
	
	private String userName;

	private String orgId;
	
	private String password;

	private Integer pwdErrorNum;
	
	private Integer loginNum;
	
	private Timestamp createTime;
	
	private String vFlag;

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getPwdErrorNum() {
		return pwdErrorNum;
	}

	public void setPwdErrorNum(Integer pwdErrorNum) {
		this.pwdErrorNum = pwdErrorNum;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getvFlag() {
		return vFlag;
	}

	public void setvFlag(String string) {
		this.vFlag = string;
	}

	@Override
	public String toString() {
		return "UserInfo [createTime=" + createTime + ", loginNum=" + loginNum
				+ ", orgId=" + orgId + ", password=" + password
				+ ", pwdErrorNum=" + pwdErrorNum + ", rid=" + rid + ", userId="
				+ userId + ", userName=" + userName + ", vFlag=" + vFlag + "]";
	}
}
