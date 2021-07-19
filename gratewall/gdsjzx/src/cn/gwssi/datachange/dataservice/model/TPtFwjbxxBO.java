package cn.gwssi.datachange.dataservice.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_FWJBXX表对应的实体类
 */
@Entity
@Table(name = "T_PT_FWJBXX")
public class TPtFwjbxxBO extends AbsDaoBussinessObject {
	
	public TPtFwjbxxBO(){}

	private String serviceid;	
	private String servicename;	
	private String region;	
	private String serviceorgname;	
	private String businessname;	
	private String interfacename;	
	private String versionnumber;	
	private String serviceurl;	
	private String servicestate;	
	private String servicetype;	
	private String createtime;	
	private String createperson;	
	private String serviceconentshow;	
	private String serviceconentid;	
	private String lastmodifytime;	
	private String lastmodifyperson;	
	private String invokeclass;	
	private String defaulttime;	
	private String createtype;	
	private String executetype;	
	private String description;	
	private String serviceobjectid;	
	private String servicerunstate;	
	private String alias;	
	
	@Id
	@Column(name = "serviceId")
	public String getServiceid(){
		return serviceid;
	}
	public void setServiceid(String serviceid){
		this.serviceid = serviceid;
		markChange("serviceid", serviceid);
	}
	@Column(name = "serviceName")
	public String getServicename(){
		return servicename;
	}
	public void setServicename(String servicename){
		this.servicename = servicename;
		markChange("servicename", servicename);
	}
	@Column(name = "region")
	public String getRegion(){
		return region;
	}
	public void setRegion(String region){
		this.region = region;
		markChange("region", region);
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
	@Column(name = "InterfaceName")
	public String getInterfacename(){
		return interfacename;
	}
	public void setInterfacename(String interfacename){
		this.interfacename = interfacename;
		markChange("interfacename", interfacename);
	}
	@Column(name = "versionNumber")
	public String getVersionnumber(){
		return versionnumber;
	}
	public void setVersionnumber(String versionnumber){
		this.versionnumber = versionnumber;
		markChange("versionnumber", versionnumber);
	}
	@Column(name = "serviceUrl")
	public String getServiceurl(){
		return serviceurl;
	}
	public void setServiceurl(String serviceurl){
		this.serviceurl = serviceurl;
		markChange("serviceurl", serviceurl);
	}
	@Column(name = "serviceState")
	public String getServicestate(){
		return servicestate;
	}
	public void setServicestate(String servicestate){
		this.servicestate = servicestate;
		markChange("servicestate", servicestate);
	}
	@Column(name = "serviceType")
	public String getServicetype(){
		return servicetype;
	}
	public void setServicetype(String servicetype){
		this.servicetype = servicetype;
		markChange("servicetype", servicetype);
	}
	@Column(name = "createTime")
	public String getCreatetime(){
		return createtime;
	}
	public void setCreatetime(String createtime){
		this.createtime = createtime;
		markChange("createtime", createtime);
	}
	@Column(name = "createPerson")
	public String getCreateperson(){
		return createperson;
	}
	public void setCreateperson(String createperson){
		this.createperson = createperson;
		markChange("createperson", createperson);
	}
	@Column(name = "serviceConentShow")
	public String getServiceconentshow(){
		return serviceconentshow;
	}
	public void setServiceconentshow(String serviceconentshow){
		this.serviceconentshow = serviceconentshow;
		markChange("serviceconentshow", serviceconentshow);
	}
	@Column(name = "serviceConentId")
	public String getServiceconentid(){
		return serviceconentid;
	}
	public void setServiceconentid(String serviceconentid){
		this.serviceconentid = serviceconentid;
		markChange("serviceconentid", serviceconentid);
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
	@Column(name = "invokeClass")
	public String getInvokeclass(){
		return invokeclass;
	}
	public void setInvokeclass(String invokeclass){
		this.invokeclass = invokeclass;
		markChange("invokeclass", invokeclass);
	}
	@Column(name = "defaulttime")
	public String getDefaulttime(){
		return defaulttime;
	}
	public void setDefaulttime(String defaulttime){
		this.defaulttime = defaulttime;
		markChange("defaulttime", defaulttime);
	}
	@Column(name = "createType")
	public String getCreatetype(){
		return createtype;
	}
	public void setCreatetype(String createtype){
		this.createtype = createtype;
		markChange("createtype", createtype);
	}
	@Column(name = "executeType")
	public String getExecutetype(){
		return executetype;
	}
	public void setExecutetype(String executetype){
		this.executetype = executetype;
		markChange("executetype", executetype);
	}
	@Column(name = "description")
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
		markChange("description", description);
	}
	@Column(name = "serviceobjectid")
	public String getServiceobjectid(){
		return serviceobjectid;
	}
	public void setServiceobjectid(String serviceobjectid){
		this.serviceobjectid = serviceobjectid;
		markChange("serviceobjectid", serviceobjectid);
	}
	@Column(name = "serviceRunState")
	public String getServicerunstate(){
		return servicerunstate;
	}
	public void setServicerunstate(String servicerunstate){
		this.servicerunstate = servicerunstate;
		markChange("servicerunstate", servicerunstate);
	}
	@Column(name = "alias")
	public String getAlias(){
		return alias;
	}
	public void setAlias(String alias){
		this.alias = alias;
		markChange("alias", alias);
	}
}