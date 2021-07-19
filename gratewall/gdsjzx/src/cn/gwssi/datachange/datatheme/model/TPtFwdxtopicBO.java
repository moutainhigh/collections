package cn.gwssi.datachange.datatheme.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_FWDXTOPIC表对应的实体类
 */
@Entity
@Table(name = "T_PT_FWDXTOPIC")
public class TPtFwdxtopicBO extends AbsDaoBussinessObject {
	
	public TPtFwdxtopicBO(){}

	private String pkid;	
	private String serviceobjectid;	
	private String themeid;	
	
	@Id
	@Column(name = "pkId")
	public String getPkid(){
		return pkid;
	}
	public void setPkid(String pkid){
		this.pkid = pkid;
		markChange("pkid", pkid);
	}
	@Column(name = "serviceObjectId")
	public String getServiceobjectid(){
		return serviceobjectid;
	}
	public void setServiceobjectid(String serviceobjectid){
		this.serviceobjectid = serviceobjectid;
		markChange("serviceobjectid", serviceobjectid);
	}
	@Column(name = "themeid")
	public String getThemeid(){
		return themeid;
	}
	public void setThemeid(String themeid){
		this.themeid = themeid;
		markChange("themeid", themeid);
	}
}