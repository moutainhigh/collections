package com.gwssi.application.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import com.gwssi.optimus.core.persistence.annotation.Lob;

/**
 * SM_INTERFACE_LOG表对应的实体类
 */
@Entity
@Table(name = "SM_INTERFACE_LOG")
public class SmInterfaceLogBO extends AbsDaoBussinessObject {
	
	public SmInterfaceLogBO(){}

	private String pkInterfaceLog;	
	private String serviceNo;	
	private String serviceName;	
	private String reqPkSys;	
	private String reqSysName;	
	private String resPkSys;	
	private String resSysName;	
	private String interfaceType;	
	private String interfaceState;	
	
	@Id
	@Column(name = "PK_INTERFACE_LOG")
	public String getPkInterfaceLog(){
		return pkInterfaceLog;
	}
	public void setPkInterfaceLog(String pkInterfaceLog){
		this.pkInterfaceLog = pkInterfaceLog;
		markChange("pkInterfaceLog", pkInterfaceLog);
	}
	@Column(name = "SERVICE_NO")
	public String getServiceNo(){
		return serviceNo;
	}
	public void setServiceNo(String serviceNo){
		this.serviceNo = serviceNo;
		markChange("serviceNo", serviceNo);
	}
	@Column(name = "SERVICE_NAME")
	public String getServiceName(){
		return serviceName;
	}
	public void setServiceName(String serviceName){
		this.serviceName = serviceName;
		markChange("serviceName", serviceName);
	}
	@Column(name = "REQ_PK_SYS")
	public String getReqPkSys(){
		return reqPkSys;
	}
	public void setReqPkSys(String reqPkSys){
		this.reqPkSys = reqPkSys;
		markChange("reqPkSys", reqPkSys);
	}
	@Column(name = "REQ_SYS_NAME")
	public String getReqSysName(){
		return reqSysName;
	}
	public void setReqSysName(String reqSysName){
		this.reqSysName = reqSysName;
		markChange("reqSysName", reqSysName);
	}
	@Column(name = "RES_PK_SYS")
	public String getResPkSys(){
		return resPkSys;
	}
	public void setResPkSys(String resPkSys){
		this.resPkSys = resPkSys;
		markChange("resPkSys", resPkSys);
	}
	@Column(name = "RES_SYS_NAME")
	public String getResSysName(){
		return resSysName;
	}
	public void setResSysName(String resSysName){
		this.resSysName = resSysName;
		markChange("resSysName", resSysName);
	}
	@Column(name = "INTERFACE_TYPE")
	public String getInterfaceType(){
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType){
		this.interfaceType = interfaceType;
		markChange("interfaceType", interfaceType);
	}
	@Column(name = "INTERFACE_STATE")
	public String getInterfaceState(){
		return interfaceState;
	}
	public void setInterfaceState(String interfaceState){
		this.interfaceState = interfaceState;
		markChange("interfaceState", interfaceState);
	}
}