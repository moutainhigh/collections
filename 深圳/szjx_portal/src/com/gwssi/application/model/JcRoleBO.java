package com.gwssi.application.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.Lob;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * JC_ROLE表对应的实体类
 */
@Entity
@Table(name = "JC_ROLE")
public class JcRoleBO extends AbsDaoBussinessObject {
	
	public JcRoleBO(){}

	private String roleCode;	
	private String roleName;	
	private String appCode;	
	private String flag;	
	private Calendar timestamp;	
	private String remark;	
	
	@Column(name = "ROLE_CODE")
	public String getRoleCode(){
		return roleCode;
	}
	public void setRoleCode(String roleCode){
		this.roleCode = roleCode;
		markChange("roleCode", roleCode);
	}
	@Column(name = "ROLE_NAME")
	public String getRoleName(){
		return roleName;
	}
	public void setRoleName(String roleName){
		this.roleName = roleName;
		markChange("roleName", roleName);
	}
	@Id
	@Column(name = "APP_CODE")
	public String getAppCode(){
		return appCode;
	}
	public void setAppCode(String appCode){
		this.appCode = appCode;
		markChange("appCode", appCode);
	}
	@Column(name = "FLAG")
	public String getFlag(){
		return flag;
	}
	public void setFlag(String flag){
		this.flag = flag;
		markChange("flag", flag);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIMESTAMP")
	public Calendar getTimestamp(){
		return timestamp;
	}
	public void setTimestamp(Calendar timestamp){
		this.timestamp = timestamp;
		markChange("timestamp", timestamp);
	}
	@Column(name = "REMARK")
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
		markChange("remark", remark);
	}
}