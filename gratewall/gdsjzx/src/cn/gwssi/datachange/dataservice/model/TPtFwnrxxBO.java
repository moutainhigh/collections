package cn.gwssi.datachange.dataservice.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_FWNRXX表对应的实体类
 */
@Entity
@Table(name = "T_PT_FWNRXX")
public class TPtFwnrxxBO extends AbsDaoBussinessObject {
	
	public TPtFwnrxxBO(){}

	private String fwnrid;	
	private String servicecontentname;	
	private String servicecontent;	
	private String servicecontentshow;	
	private String servicecontentcondition;	
	private String contenttype;	
	private String columnname;	
	private String columncode;	
	private String tablename;	
	private String tablecode;	
	private String isenabled;	
	private String createperson;	
	private String createtime;	
	private String lastmodifytime;	
	private String lastmodifyperson;	
	
	@Id
	@Column(name = "fwnrId")
	public String getFwnrid(){
		return fwnrid;
	}
	public void setFwnrid(String fwnrid){
		this.fwnrid = fwnrid;
		markChange("fwnrid", fwnrid);
	}
	@Column(name = "serviceContentName")
	public String getServicecontentname(){
		return servicecontentname;
	}
	public void setServicecontentname(String servicecontentname){
		this.servicecontentname = servicecontentname;
		markChange("servicecontentname", servicecontentname);
	}
	@Column(name = "serviceContent")
	public String getServicecontent(){
		return servicecontent;
	}
	public void setServicecontent(String servicecontent){
		this.servicecontent = servicecontent;
		markChange("servicecontent", servicecontent);
	}
	@Column(name = "serviceContentShow")
	public String getServicecontentshow(){
		return servicecontentshow;
	}
	public void setServicecontentshow(String servicecontentshow){
		this.servicecontentshow = servicecontentshow;
		markChange("servicecontentshow", servicecontentshow);
	}
	@Column(name = "serviceContentCondition")
	public String getServicecontentcondition(){
		return servicecontentcondition;
	}
	public void setServicecontentcondition(String servicecontentcondition){
		this.servicecontentcondition = servicecontentcondition;
		markChange("servicecontentcondition", servicecontentcondition);
	}
	@Column(name = "contentType")
	public String getContenttype(){
		return contenttype;
	}
	public void setContenttype(String contenttype){
		this.contenttype = contenttype;
		markChange("contenttype", contenttype);
	}
	@Column(name = "columnName")
	public String getColumnname(){
		return columnname;
	}
	public void setColumnname(String columnname){
		this.columnname = columnname;
		markChange("columnname", columnname);
	}
	@Column(name = "columnCode")
	public String getColumncode(){
		return columncode;
	}
	public void setColumncode(String columncode){
		this.columncode = columncode;
		markChange("columncode", columncode);
	}
	@Column(name = "tableName")
	public String getTablename(){
		return tablename;
	}
	public void setTablename(String tablename){
		this.tablename = tablename;
		markChange("tablename", tablename);
	}
	@Column(name = "tableCode")
	public String getTablecode(){
		return tablecode;
	}
	public void setTablecode(String tablecode){
		this.tablecode = tablecode;
		markChange("tablecode", tablecode);
	}
	@Column(name = "isEnabled")
	public String getIsenabled(){
		return isenabled;
	}
	public void setIsenabled(String isenabled){
		this.isenabled = isenabled;
		markChange("isenabled", isenabled);
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
}