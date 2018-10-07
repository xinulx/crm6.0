package com.mine.common;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Authorization implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long empeeID;
	private Long staffID; // 员工账号ID
	private String empeeName;
	private String empeeCode;
	private String empeeAcct; // 员工帐号
	private String empeePwd; // 员工密码
	private String ipAddress; // 用户IP地址
	private Long empeelevel; // 用户用户等级
	private Long latn;
	private String LatnCode;
	private List<?> channels;
	private List<?> locs;
	private Date lastLoginDate; // 员工上次登陆时间
	private List<?> inOrgs; // 员工orgid对象，主要是与老的系统保持兼容
	private List<?> staffOrgVOs; // 员工与部门之间的关系对象列表
	private List<?> saledOffers; // 员工所能销售的商品列表
	private List<?> notSaledOffers; // 员工不能销售的商品列表
	private List<?> privileges;
	private List<?> roles;
	private List<?> staffSegments; // 用户数据范围
	private Integer loginNum; // 上次员工登陆时，以有的错误登陆次数
	private String urlMapping;

	private String staffType; // 正式还是临时
	private String mac; // 客户端MAC地址
	private String clientName; // 客户端机器名
	private List<?> allLatns; // 所有的本地网
	private List<?> authLatns; // 有权限的本地网
	private Long selChnlID;
	private String selChnlName;
	private String selChnlTypeCd;
	private String selChnlTypeName;
	private String userType;// 用户类型：agent，empee

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getEmpeeID() {
		return empeeID;
	}

	public void setEmpeeID(Long empeeID) {
		this.empeeID = empeeID;
	}

	public String getEmpeeName() {
		return empeeName;
	}

	public void setEmpeeName(String empeeName) {
		this.empeeName = empeeName;
	}

	public String getEmpeeCode() {
		return empeeCode;
	}

	public void setEmpeeCode(String empeeCode) {
		this.empeeCode = empeeCode;
	}

	public String getEmpeeAcct() {
		return empeeAcct;
	}

	public void setEmpeeAcct(String empeeAcct) {
		this.empeeAcct = empeeAcct;
	}

	public String getEmpeePwd() {
		return empeePwd;
	}

	public void setEmpeePwd(String empeePwd) {
		this.empeePwd = empeePwd;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Long getEmpeelevel() {
		return empeelevel;
	}

	public void setEmpeelevel(Long empeelevel) {
		this.empeelevel = empeelevel;
	}

	public Long getLatn() {
		return latn;
	}

	public void setLatn(Long latn) {
		this.latn = latn;
	}

	public String getLatnCode() {
		return LatnCode;
	}

	public void setLatnCode(String latnCode) {
		LatnCode = latnCode;
	}

	public List<?> getChannels() {
		return channels;
	}

	public void setChannels(List<?> channels) {
		this.channels = channels;
	}

	public List<?> getLocs() {
		return locs;
	}

	public void setLocs(List<?> locs) {
		this.locs = locs;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public List<?> getInOrgs() {
		return inOrgs;
	}

	public void setInOrgs(List<?> inOrgs) {
		this.inOrgs = inOrgs;
	}

	public List<?> getSaledOffers() {
		return saledOffers;
	}

	public void setSaledOffers(List<?> saledOffers) {
		this.saledOffers = saledOffers;
	}

	public List<?> getNotSaledOffers() {
		return notSaledOffers;
	}

	public void setNotSaledOffers(List<?> notSaledOffers) {
		this.notSaledOffers = notSaledOffers;
	}

	public List<?> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<?> privileges) {
		this.privileges = privileges;
	}

	public List<?> getRoles() {
		return roles;
	}

	public void setRoles(List<?> roles) {
		this.roles = roles;
	}

	public Integer getLoginNum() {
		return loginNum;
	}

	public void setLoginNum(Integer loginNum) {
		this.loginNum = loginNum;
	}

	public String getStaffType() {
		return staffType;
	}

	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getUrlMapping() {
		return urlMapping;
	}

	public void setUrlMapping(String urlMapping) {
		this.urlMapping = urlMapping;
	}

	public List<?> getStaffSegments() {
		return staffSegments;
	}

	public void setStaffSegments(List<?> staffSegments) {
		this.staffSegments = staffSegments;
	}

	public List<?> getAllLatns() {
		return allLatns;
	}

	public void setAllLatns(List<?> allLatns) {
		this.allLatns = allLatns;
	}

	public List<?> getAuthLatns() {
		return authLatns;
	}

	public void setAuthLatns(List<?> authLatns) {
		this.authLatns = authLatns;
	}

	public List<?> getStaffOrgVOs() {
		return staffOrgVOs;
	}

	public void setStaffOrgVOs(List<?> staffOrgVOs) {
		this.staffOrgVOs = staffOrgVOs;
	}

	public Long getSelChnlID() {
		return selChnlID;
	}

	public void setSelChnlID(Long selChnlID) {
		this.selChnlID = selChnlID;
	}

	public String getSelChnlName() {
		return selChnlName;
	}

	public void setSelChnlName(String selChnlName) {
		this.selChnlName = selChnlName;
	}

	public String getSelChnlTypeCd() {
		return selChnlTypeCd;
	}

	public void setSelChnlTypeCd(String selChnlTypeCd) {
		this.selChnlTypeCd = selChnlTypeCd;
	}

	public String getSelChnlTypeName() {
		return selChnlTypeName;
	}

	public void setSelChnlTypeName(String selChnlTypeName) {
		this.selChnlTypeName = selChnlTypeName;
	}

	public Long getStaffID() {
		return staffID;
	}

	public void setStaffID(Long staffID) {
		this.staffID = staffID;
	}

}
