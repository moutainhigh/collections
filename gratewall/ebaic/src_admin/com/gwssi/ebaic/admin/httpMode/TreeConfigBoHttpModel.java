package com.gwssi.ebaic.admin.httpMode;

import java.util.Map;

public class TreeConfigBoHttpModel implements java.io.Serializable {
	
	
	private static final long serialVersionUID = 1222876728402071210L;
	
	/* 
	* 主键
	 */
	private String id;
	/**
	 * 父级ID
	 */
	private String pid;
	/**
	 * 节点名称
	 */
	private String text;
	
	/**
	 * 序号
	 */
	private String treeIndex;
	
	private String state = "open"; //是否展开(open,closed)
	private boolean checked = false;// 是否勾选状态
	private Map<String, Object> attributes;// 其他参数
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTreeIndex() {
		return treeIndex;
	}
	public void setTreeIndex(String treeIndex) {
		this.treeIndex = treeIndex;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
