package cn.gwssi.datachange.dataservice.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_GXZYTXX表对应的实体类
 */
@Entity
@Table(name = "T_PT_GXZYTXX")
public class TPtGxzytxxBO extends AbsDaoBussinessObject {
	
	public TPtGxzytxxBO(){}

	private String gxzyid;	
	private String subject;	
	private String tablename;	
	private String tablecode;	
	private String isstart;	
	private Long dataquantity;	
	private String lastuupdatetime;	
	private String createperson;	
	private String createtime;	
	private String modifytime;	
	private String lastmodifyperson;	
	
	@Id
	@Column(name = "gxzyId")
	public String getGxzyid(){
		return gxzyid;
	}
	public void setGxzyid(String gxzyid){
		this.gxzyid = gxzyid;
		markChange("gxzyid", gxzyid);
	}
	@Column(name = "subject")
	public String getSubject(){
		return subject;
	}
	public void setSubject(String subject){
		this.subject = subject;
		markChange("subject", subject);
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
	@Column(name = "isStart")
	public String getIsstart(){
		return isstart;
	}
	public void setIsstart(String isstart){
		this.isstart = isstart;
		markChange("isstart", isstart);
	}
	@Column(name = "dataQuantity")
	public Long getDataquantity(){
		return dataquantity;
	}
	public void setDataquantity(Long dataquantity){
		this.dataquantity = dataquantity;
		markChange("dataquantity", dataquantity);
	}
	@Column(name = "lastUupdateTime")
	public String getLastuupdatetime(){
		return lastuupdatetime;
	}
	public void setLastuupdatetime(String lastuupdatetime){
		this.lastuupdatetime = lastuupdatetime;
		markChange("lastuupdatetime", lastuupdatetime);
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
	@Column(name = "modifyTime")
	public String getModifytime(){
		return modifytime;
	}
	public void setModifytime(String modifytime){
		this.modifytime = modifytime;
		markChange("modifytime", modifytime);
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