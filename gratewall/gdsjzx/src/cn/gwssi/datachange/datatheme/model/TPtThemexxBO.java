package cn.gwssi.datachange.datatheme.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_THEMEXX表对应的实体类
 */
@Entity
@Table(name = "T_PT_THEMEXX")
public class TPtThemexxBO extends AbsDaoBussinessObject {
	
	public TPtThemexxBO(){}
	private String ztid;
	//private String themeid;         
	private String themename;       
	private String createperson;    
	private String isstart;        
	private String fwdxjbid;        
	private String lastuupdatetime; 
	private String createtime;      
	private String modifytime;      
	private String lastmodifyperson;
	private String fwdxname;
	
	@Id
	@Column(name = "ztid")
	public String getZtid() {
		return ztid;
	}
	
	public void setZtid(String ztid) {
		this.ztid = ztid;
		markChange("ztid", ztid);
	}
	@Column(name = "themename")
	public String getThemename() {
		return themename;
	}
	public void setThemename(String themename) {
		this.themename = themename;
		markChange("themename", themename);
	}
	@Column(name = "createperson")
	public String getCreateperson() {
		return createperson;
	}
	public void setCreateperson(String createperson) {
		this.createperson = createperson;
		markChange("createperson", createperson);
	}
	@Column(name = "isstart")
	public String getIsstart() {
		return isstart;
	}
	public void setIsstart(String isstart) {
		this.isstart = isstart;
		markChange("isstart", isstart);
	}
	@Column(name = "fwdxjbid")
	public String getFwdxjbid() {
		return fwdxjbid;
	}
	public void setFwdxjbid(String fwdxjbid) {
		this.fwdxjbid = fwdxjbid;
		markChange("fwdxjbid", fwdxjbid);
	}
	@Column(name = "lastuupdatetime")
	public String getLastuupdatetime() {
		return lastuupdatetime;
	}
	public void setLastuupdatetime(String lastuupdatetime) {
		this.lastuupdatetime = lastuupdatetime;
		markChange("lastuupdatetime", lastuupdatetime);
	}
	@Column(name = "createtime")
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
		markChange("createtime", createtime);
	}
	@Column(name = "modifytime")
	public String getModifytime() {
		return modifytime;
	}
	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
		markChange("modifytime", modifytime);
	}
	@Column(name = "lastmodifyperson")
	public String getLastmodifyperson() {
		return lastmodifyperson;
	}
	public void setLastmodifyperson(String lastmodifyperson) {
		this.lastmodifyperson = lastmodifyperson;
		markChange("lastmodifyperson", lastmodifyperson);
	}
	
	@Column(name = "fwdxname")
	public String getFwdxname() {
		return fwdxname;
	}
	public void setFwdxname(String fwdxname) {
		this.fwdxname = fwdxname;
		markChange("fwdxname", fwdxname);
	}
	
}