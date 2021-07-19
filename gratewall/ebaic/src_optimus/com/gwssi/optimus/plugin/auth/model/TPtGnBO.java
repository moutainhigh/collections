package com.gwssi.optimus.plugin.auth.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_GN表对应的实体类
 */
@Entity
@Table(name = "T_PT_GN")
public class TPtGnBO extends AbsDaoBussinessObject {
	
	public TPtGnBO(){}

	private String gnId;	
	private String xtId;	
	private String gnMc;	
	private String sjgnId;	
	private String sfcd;	
	private String url;	
	private String xh;	
	
	@Id
	@Column(name = "GN_ID")
	public String getGnId(){
		return gnId;
	}
	public void setGnId(String gnId){
		this.gnId = gnId;
		markChange("gnId", gnId);
	}
	@Column(name = "XT_ID")
	public String getXtId(){
		return xtId;
	}
	public void setXtId(String xtId){
		this.xtId = xtId;
		markChange("xtId", xtId);
	}
	@Column(name = "GN_MC")
	public String getGnMc(){
		return gnMc;
	}
	public void setGnMc(String gnMc){
		this.gnMc = gnMc;
		markChange("gnMc", gnMc);
	}
	@Column(name = "SJGN_ID")
	public String getSjgnId(){
		return sjgnId;
	}
	public void setSjgnId(String sjgnId){
		this.sjgnId = sjgnId;
		markChange("sjgnId", sjgnId);
	}
	@Column(name = "SFCD")
	public String getSfcd(){
		return sfcd;
	}
	public void setSfcd(String sfcd){
		this.sfcd = sfcd;
		markChange("sfcd", sfcd);
	}
	@Column(name = "URL")
	public String getUrl(){
		return url;
	}
	public void setUrl(String url){
		this.url = url;
		markChange("url", url);
	}
	@Column(name = "XH")
	public String getXh(){
		return xh;
	}
	public void setXh(String xh){
		this.xh = xh;
		markChange("xh", xh);
	}
}