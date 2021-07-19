package com.gwssi.optimus.plugin.auth.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_JSGN表对应的实体类
 */
@Entity
@Table(name = "T_PT_JSGN")
public class TPtJsgnBO extends AbsDaoBussinessObject {
	
	public TPtJsgnBO(){}

	private String id;	
	private String gnId;	
	private String jsId;	
	
	@Id
	@Column(name = "ID")
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
		markChange("id", id);
	}
	@Column(name = "GN_ID")
	public String getGnId(){
		return gnId;
	}
	public void setGnId(String gnId){
		this.gnId = gnId;
		markChange("gnId", gnId);
	}
	@Column(name = "JS_ID")
	public String getJsId(){
		return jsId;
	}
	public void setJsId(String jsId){
		this.jsId = jsId;
		markChange("jsId", jsId);
	}
}