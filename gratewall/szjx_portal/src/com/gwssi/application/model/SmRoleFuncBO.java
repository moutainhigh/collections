package com.gwssi.application.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * SM_ROLE_FUNC表对应的实体类
 */
@Entity
@Table(name = "SM_ROLE_FUNC")
public class SmRoleFuncBO extends AbsDaoBussinessObject {
	
	public SmRoleFuncBO(){}

	private String roleCode;	
	private String functionCode;	
	
	@Column(name = "ROLE_CODE")
	public String getRoleCode(){
		return roleCode;
	}
	public void setRoleCode(String roleCode){
		this.roleCode = roleCode;
		markChange("roleCode", roleCode);
	}
	@Column(name = "FUNCTION_CODE")
	public String getFunctionCode(){
		return functionCode;
	}
	public void setFunctionCode(String functionCode){
		this.functionCode = functionCode;
		markChange("functionCode", functionCode);
	}
}