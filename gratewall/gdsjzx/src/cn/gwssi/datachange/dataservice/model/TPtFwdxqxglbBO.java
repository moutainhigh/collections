package cn.gwssi.datachange.dataservice.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_FWDXQXGLB表对应的实体类
 */
@Entity
@Table(name = "T_PT_FWDXQXGLB")
public class TPtFwdxqxglbBO extends AbsDaoBussinessObject {
	
	public TPtFwdxqxglbBO(){}

	private String pkid;	
	private String serviceobjectid;	
	private String serviceid;	
	
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
	@Column(name = "serviceID")
	public String getServiceid(){
		return serviceid;
	}
	public void setServiceid(String serviceid){
		this.serviceid = serviceid;
		markChange("serviceid", serviceid);
	}
}