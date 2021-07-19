package cn.gwssi.datachange.dataservice.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_FWDXJBXX表对应的实体类
 */
@Entity
@Table(name = "T_PT_FWDXJBXX")
public class TPtFwdxjbxxBO extends AbsDaoBussinessObject {
	
	public TPtFwdxjbxxBO(){}

	private String fwdxjbid;	
	private String serviceobjectname;	
	private String serviceorgname;	
	private String businessname;	
	private String serviceobjectip;	
	private String state;	
	private String createperson;	
	private String createtime;	
	private String lastmodifytime;	
	private String lastmodifyperson;	
	private String reason;	
	private String serviceobjectregion;	
	private String serviceobjectport;	
	private String controlobjectstate;	
	
	@Id
	@Column(name = "fwdxjbId")
	public String getFwdxjbid(){
		return fwdxjbid;
	}
	public void setFwdxjbid(String fwdxjbid){
		this.fwdxjbid = fwdxjbid;
		markChange("fwdxjbid", fwdxjbid);
	}
	@Column(name = "serviceObjectName")
	public String getServiceobjectname(){
		return serviceobjectname;
	}
	public void setServiceobjectname(String serviceobjectname){
		this.serviceobjectname = serviceobjectname;
		markChange("serviceobjectname", serviceobjectname);
	}
	@Column(name = "serviceOrgName")
	public String getServiceorgname(){
		return serviceorgname;
	}
	public void setServiceorgname(String serviceorgname){
		this.serviceorgname = serviceorgname;
		markChange("serviceorgname", serviceorgname);
	}
	@Column(name = "BusinessName")
	public String getBusinessname(){
		return businessname;
	}
	public void setBusinessname(String businessname){
		this.businessname = businessname;
		markChange("businessname", businessname);
	}
	@Column(name = "serviceObjectIP")
	public String getServiceobjectip(){
		return serviceobjectip;
	}
	public void setServiceobjectip(String serviceobjectip){
		this.serviceobjectip = serviceobjectip;
		markChange("serviceobjectip", serviceobjectip);
	}
	@Column(name = "state")
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
		markChange("state", state);
	}
	@Column(name = "createPerson")
	public String getCreateperson(){
		return createperson;
	}
	public void setCreateperson(String createperson){
		this.createperson = createperson;
		markChange("createperson", createperson);
	}
	@Column(name = "createTime")
	public String getCreatetime(){
		return createtime;
	}
	public void setCreatetime(String createtime){
		this.createtime = createtime;
		markChange("createtime", createtime);
	}
	@Column(name = "lastModifyTime")
	public String getLastmodifytime(){
		return lastmodifytime;
	}
	public void setLastmodifytime(String lastmodifytime){
		this.lastmodifytime = lastmodifytime;
		markChange("lastmodifytime", lastmodifytime);
	}
	@Column(name = "lastModifyPerson")
	public String getLastmodifyperson(){
		return lastmodifyperson;
	}
	public void setLastmodifyperson(String lastmodifyperson){
		this.lastmodifyperson = lastmodifyperson;
		markChange("lastmodifyperson", lastmodifyperson);
	}
	@Column(name = "reason")
	public String getReason(){
		return reason;
	}
	public void setReason(String reason){
		this.reason = reason;
		markChange("reason", reason);
	}
	@Column(name = "serviceobjectregion")
	public String getServiceobjectregion(){
		return serviceobjectregion;
	}
	public void setServiceobjectregion(String serviceobjectregion){
		this.serviceobjectregion = serviceobjectregion;
		markChange("serviceobjectregion", serviceobjectregion);
	}
	@Column(name = "serviceObjectPort")
	public String getServiceobjectport(){
		return serviceobjectport;
	}
	public void setServiceobjectport(String serviceobjectport){
		this.serviceobjectport = serviceobjectport;
		markChange("serviceobjectport", serviceobjectport);
	}
	@Column(name = "controlobjectstate")
	public String getControlobjectstate(){
		return controlobjectstate;
	}
	public void setControlobjectstate(String controlobjectstate){
		this.controlobjectstate = controlobjectstate;
		markChange("controlobjectstate", controlobjectstate);
	}
}