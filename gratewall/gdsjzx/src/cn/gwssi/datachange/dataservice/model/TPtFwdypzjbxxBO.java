package cn.gwssi.datachange.dataservice.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_FWDYPZJBXX表对应的实体类
 */
@Entity
@Table(name = "T_PT_FWDYPZJBXX")
public class TPtFwdypzjbxxBO extends AbsDaoBussinessObject {
	
	public TPtFwdypzjbxxBO(){}

	private String fwdypzjbxxid;	
	private String subscriptionobject;	
	private String startsubscribetime;	
	private String frequency;	
	private String acceptway;	
	private String path;	
	private String serviceid;	
	private String modifytime;	
	private String modifyperson;	
	private String reason;	
	private String serviceobjectid;	
	
	@Id
	@Column(name = "fwdypzjbxxId")
	public String getFwdypzjbxxid(){
		return fwdypzjbxxid;
	}
	public void setFwdypzjbxxid(String fwdypzjbxxid){
		this.fwdypzjbxxid = fwdypzjbxxid;
		markChange("fwdypzjbxxid", fwdypzjbxxid);
	}
	@Column(name = "subscriptionObject")
	public String getSubscriptionobject(){
		return subscriptionobject;
	}
	public void setSubscriptionobject(String subscriptionobject){
		this.subscriptionobject = subscriptionobject;
		markChange("subscriptionobject", subscriptionobject);
	}
	@Column(name = "startSubscribeTime")
	public String getStartsubscribetime(){
		return startsubscribetime;
	}
	public void setStartsubscribetime(String startsubscribetime){
		this.startsubscribetime = startsubscribetime;
		markChange("startsubscribetime", startsubscribetime);
	}
	@Column(name = "frequency")
	public String getFrequency(){
		return frequency;
	}
	public void setFrequency(String frequency){
		this.frequency = frequency;
		markChange("frequency", frequency);
	}
	@Column(name = "acceptWay")
	public String getAcceptway(){
		return acceptway;
	}
	public void setAcceptway(String acceptway){
		this.acceptway = acceptway;
		markChange("acceptway", acceptway);
	}
	@Column(name = "path")
	public String getPath(){
		return path;
	}
	public void setPath(String path){
		this.path = path;
		markChange("path", path);
	}
	@Column(name = "serviceId")
	public String getServiceid(){
		return serviceid;
	}
	public void setServiceid(String serviceid){
		this.serviceid = serviceid;
		markChange("serviceid", serviceid);
	}
	@Column(name = "modifyTime")
	public String getModifytime(){
		return modifytime;
	}
	public void setModifytime(String modifytime){
		this.modifytime = modifytime;
		markChange("modifytime", modifytime);
	}
	@Column(name = "modifyperson")
	public String getModifyperson(){
		return modifyperson;
	}
	public void setModifyperson(String modifyperson){
		this.modifyperson = modifyperson;
		markChange("modifyperson", modifyperson);
	}
	@Column(name = "reason")
	public String getReason(){
		return reason;
	}
	public void setReason(String reason){
		this.reason = reason;
		markChange("reason", reason);
	}
	@Column(name = "serviceObjectId")
	public String getServiceobjectid(){
		return serviceobjectid;
	}
	public void setServiceobjectid(String serviceobjectid){
		this.serviceobjectid = serviceobjectid;
		markChange("serviceobjectid", serviceobjectid);
	}
}