package com.gwssi.optimus.plugin.auth.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_ZJZY表对应的实体类
 */
@Entity
@Table(name = "T_PT_ZJZY")
public class TPtZjzyBO extends AbsDaoBussinessObject {
	
	public TPtZjzyBO(){}

	private String zjId;	
	private String gnId;	
	private String zjMc;	
	private String zjCode;	
	
	@Id
	@Column(name = "ZJ_ID")
	public String getZjId(){
		return zjId;
	}
	public void setZjId(String zjId){
		this.zjId = zjId;
		markChange("zjId", zjId);
	}
	@Column(name = "GN_ID")
	public String getGnId(){
		return gnId;
	}
	public void setGnId(String gnId){
		this.gnId = gnId;
		markChange("gnId", gnId);
	}
	@Column(name = "ZJ_MC")
	public String getZjMc(){
		return zjMc;
	}
	public void setZjMc(String zjMc){
		this.zjMc = zjMc;
		markChange("zjMc", zjMc);
	}
	@Column(name = "ZJ_CODE")
	public String getZjCode(){
		return zjCode;
	}
	public void setZjCode(String zjCode){
		this.zjCode = zjCode;
		markChange("zjCode", zjCode);
	}
}