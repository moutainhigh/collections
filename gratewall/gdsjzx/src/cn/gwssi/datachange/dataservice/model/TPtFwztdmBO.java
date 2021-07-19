package cn.gwssi.datachange.dataservice.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_PT_FWZTDM表对应的实体类
 */
@Entity
@Table(name = "T_PT_FWZTDM")
public class TPtFwztdmBO extends AbsDaoBussinessObject {
	
	public TPtFwztdmBO(){}

	private String code;	
	private String codename;	
	private String jc;	
	private String createperson;	
	private String createtime;	
	private String isenabled;	
	
	@Id
	@Column(name = "code")
	public String getCode(){
		return code;
	}
	public void setCode(String code){
		this.code = code;
		markChange("code", code);
	}
	@Column(name = "codeName")
	public String getCodename(){
		return codename;
	}
	public void setCodename(String codename){
		this.codename = codename;
		markChange("codename", codename);
	}
	@Column(name = "jc")
	public String getJc(){
		return jc;
	}
	public void setJc(String jc){
		this.jc = jc;
		markChange("jc", jc);
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
	@Column(name = "isEnabled")
	public String getIsenabled(){
		return isenabled;
	}
	public void setIsenabled(String isenabled){
		this.isenabled = isenabled;
		markChange("isenabled", isenabled);
	}
}