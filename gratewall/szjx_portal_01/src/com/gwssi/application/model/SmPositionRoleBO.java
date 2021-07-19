package com.gwssi.application.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * SM_POSITION_ROLE表对应的实体类
 */
@Entity
@Table(name = "SM_POSITION_ROLE")
public class SmPositionRoleBO extends AbsDaoBussinessObject {

	public SmPositionRoleBO() {
	}

	private String roleCode;
	private String pkPosition;

	@Column(name = "ROLE_CODE")
	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
		markChange("roleCode", roleCode);
	}

	@Column(name = "PK_POSITION")
	public String getPkPosition() {
		return pkPosition;
	}

	public void setPkPosition(String pkPosition) {
		this.pkPosition = pkPosition;
		markChange("pkPosition", pkPosition);
	}
}