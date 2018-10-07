package com.mine.App.model;

import java.util.List;

/**
 * @author db2admin
 *
 */
public class Menu {
	/**菜单信息*/
	private Integer rid; // 标识ID
	private String  id; // 节点ID
	private String  name; // 显示文本
	private String  pid; // 父节点ID
	private boolean expanded; // 展开
	private List    data ; // 节点数据对象，默认{}
	private String  url ; //数据加载地址,默认null
	private String  vlaue ; // 选中的节点值,默认null
	private String  idField ; // 值字段，默认"id"
	private String  text ; //默认文本字段，"text"
	private String  icon ;// 图标字段，默认"iconCls"
	private String  parentField ;// 父节点字段，默认"pid"
	private String  imgField ; // 节点图片字段,默认"img"
	private String  imgPath ; // 图片路径字段，默认""
	private boolean resultAsTree ;// url数据是否列表，默认true
	private String  dataField ; // 数据列表字段，默认null
	private String  checkedField; // checked字段 checked 
	private boolean checkRecursive ;// 是否联动选择父子节点。比如选中父节点，自动全选子节点。 false √ √ √ 
	private boolean autoCheckParent ;// 是否自动选择父节点。比如选中子节点，将父节点也自动选中。 false √ √ √ 
	private Object  expandOnLoad ;// 加载后是否展开。比如：true展开所有节点；0展开第一级节点。以此类推。 false √ √ √ 
	private boolean showTreeIcon ;// 显示节点图标 true √ √ √ 
	private boolean showTreeLines ;// 显示树形线条 true √ √ √ 
	private boolean allowSelect ;// 允许选择节点 true √ √ √ 
	private boolean showCheckBox ;// 允许Check模式选中节点 false √ √ √ 
	private boolean showFolderCheckBox ;// 当showCheckBox为true时，是否显示父节点CheckBox true √ √ √ 
	private boolean showExpandButtons ;// 显示折叠展开图标 true √ √ √ 
	private boolean enableHotTrack ;// 移动节点上时高亮显示 true √ √ √ 
	private boolean expandOnDblClick ;// 双击节点展开收缩 true √ √ √ 
	private boolean expandOnNodeClick ;// 单击节点展开收缩 true √ √ √ 
	private boolean allowLeafDropIn ;// 是否允许拖拽投放到子节点内 false √ √ √ 
	private boolean allowDrag ;// 是否允许拖拽节点 false √ √ √ 
	private boolean allowDrop ;// 是否允许投放节点 false 

	public Menu(){
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean isExpanded() {
		return expanded;
	}

	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVlaue() {
		return vlaue;
	}

	public void setVlaue(String vlaue) {
		this.vlaue = vlaue;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getParentField() {
		return parentField;
	}

	public void setParentField(String parentField) {
		this.parentField = parentField;
	}

	public String getImgField() {
		return imgField;
	}

	public void setImgField(String imgField) {
		this.imgField = imgField;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public boolean isResultAsTree() {
		return resultAsTree;
	}

	public void setResultAsTree(boolean resultAsTree) {
		this.resultAsTree = resultAsTree;
	}

	public String getDataField() {
		return dataField;
	}

	public void setDataField(String dataField) {
		this.dataField = dataField;
	}

	public String getCheckedField() {
		return checkedField;
	}

	public void setCheckedField(String checkedField) {
		this.checkedField = checkedField;
	}

	public boolean isCheckRecursive() {
		return checkRecursive;
	}

	public void setCheckRecursive(boolean checkRecursive) {
		this.checkRecursive = checkRecursive;
	}

	public boolean isAutoCheckParent() {
		return autoCheckParent;
	}

	public void setAutoCheckParent(boolean autoCheckParent) {
		this.autoCheckParent = autoCheckParent;
	}

	public Object getExpandOnLoad() {
		return expandOnLoad;
	}

	public void setExpandOnLoad(Object expandOnLoad) {
		this.expandOnLoad = expandOnLoad;
	}

	public boolean isShowTreeIcon() {
		return showTreeIcon;
	}

	public void setShowTreeIcon(boolean showTreeIcon) {
		this.showTreeIcon = showTreeIcon;
	}

	public boolean isShowTreeLines() {
		return showTreeLines;
	}

	public void setShowTreeLines(boolean showTreeLines) {
		this.showTreeLines = showTreeLines;
	}

	public boolean isAllowSelect() {
		return allowSelect;
	}

	public void setAllowSelect(boolean allowSelect) {
		this.allowSelect = allowSelect;
	}

	public boolean isShowCheckBox() {
		return showCheckBox;
	}

	public void setShowCheckBox(boolean showCheckBox) {
		this.showCheckBox = showCheckBox;
	}

	public boolean isShowFolderCheckBox() {
		return showFolderCheckBox;
	}

	public void setShowFolderCheckBox(boolean showFolderCheckBox) {
		this.showFolderCheckBox = showFolderCheckBox;
	}

	public boolean isShowExpandButtons() {
		return showExpandButtons;
	}

	public void setShowExpandButtons(boolean showExpandButtons) {
		this.showExpandButtons = showExpandButtons;
	}

	public boolean isEnableHotTrack() {
		return enableHotTrack;
	}

	public void setEnableHotTrack(boolean enableHotTrack) {
		this.enableHotTrack = enableHotTrack;
	}

	public boolean isExpandOnDblClick() {
		return expandOnDblClick;
	}

	public void setExpandOnDblClick(boolean expandOnDblClick) {
		this.expandOnDblClick = expandOnDblClick;
	}

	public boolean isExpandOnNodeClick() {
		return expandOnNodeClick;
	}

	public void setExpandOnNodeClick(boolean expandOnNodeClick) {
		this.expandOnNodeClick = expandOnNodeClick;
	}

	public boolean isAllowLeafDropIn() {
		return allowLeafDropIn;
	}

	public void setAllowLeafDropIn(boolean allowLeafDropIn) {
		this.allowLeafDropIn = allowLeafDropIn;
	}

	public boolean isAllowDrag() {
		return allowDrag;
	}

	public void setAllowDrag(boolean allowDrag) {
		this.allowDrag = allowDrag;
	}

	public boolean isAllowDrop() {
		return allowDrop;
	}

	public void setAllowDrop(boolean allowDrop) {
		this.allowDrop = allowDrop;
	}
	
}
