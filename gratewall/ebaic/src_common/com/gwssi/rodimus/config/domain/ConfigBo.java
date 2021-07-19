package com.gwssi.rodimus.config.domain;

/**
 * table sys_config.
 * 
 * @author liuhailong(liuhailong2008#foxmail.com)
 */
public class ConfigBo {
	
	private String id;
	private String key ;
	private String value;
	private String flag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
