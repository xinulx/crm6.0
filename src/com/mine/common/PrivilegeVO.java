package com.mine.common;

import java.util.Date;

public class PrivilegeVO {
	private Long privilegeId; // 权限标识
	private String privilegeCode; // 权限编码
	private String privilegeName; // 权限名称
	private String description; // 权限描述
	private String superCode; // 父权限编码
	private String privilegeType; // 权限类型
	private String url; // 操作路径
	private String state; // 状态
	private Integer position;
	private Integer layer;
	private String menuTarget;
	private Date createDate; // 创建时间
	private Integer appid;
	private Integer parentprivilegeid;
	private String groups; // 菜单组
	private Integer isleaf; // 是否为叶子节点

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return Returns the privilegeCode.
	 */
	public String getPrivilegeCode() {
		return privilegeCode;
	}

	/**
	 * @return Returns the privilegeId.
	 */
	public Long getPrivilegeId() {
		return privilegeId;
	}

	/**
	 * @return Returns the privilegeName.
	 */
	public String getPrivilegeName() {
		return privilegeName;
	}

	/**
	 * @return Returns the privilegeType.
	 */
	public String getPrivilegeType() {
		return privilegeType;
	}

	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return Returns the superCode.
	 */
	public String getSuperCode() {
		return superCode;
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param privilegeCode
	 *            The privilegeCode to set.
	 */
	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}

	/**
	 * @param privilegeId
	 *            The privilegeId to set.
	 */
	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	/**
	 * @param privilegeName
	 *            The privilegeName to set.
	 */
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	/**
	 * @param privilegeType
	 *            The privilegeType to set.
	 */
	public void setPrivilegeType(String privilegeType) {
		this.privilegeType = privilegeType;
	}

	/**
	 * @param state
	 *            The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @param superCode
	 *            The superCode to set.
	 */
	public void setSuperCode(String superCode) {
		this.superCode = superCode;
	}

	/**
	 * @param url
	 *            The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public String getMenuTarget() {
		return menuTarget;
	}

	public void setMenuTarget(String menuTarget) {
		this.menuTarget = menuTarget;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getAppid() {
		return appid;
	}

	public void setAppid(Integer appid) {
		this.appid = appid;
	}

	public Integer getParentprivilegeid() {
		return parentprivilegeid;
	}

	public void setParentprivilegeid(Integer parentprivilegeid) {
		this.parentprivilegeid = parentprivilegeid;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public Integer getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Integer isleaf) {
		this.isleaf = isleaf;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
