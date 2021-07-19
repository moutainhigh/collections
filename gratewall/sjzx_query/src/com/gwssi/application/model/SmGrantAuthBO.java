package com.gwssi.application.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * SM_GRANT_AUTH表对应的实体类
 */
@Entity
@Table(name = "SM_GRANT_AUTH")
public class SmGrantAuthBO extends AbsDaoBussinessObject {

	public SmGrantAuthBO() {
	}

	private String pkSmServices;
	private String pkSysIntegration;

	@Column(name = "PK_SM_SERVICES")
	public String getPkSmServices() {
		return pkSmServices;
	}

	public void setPkSmServices(String pkSmServices) {
		this.pkSmServices = pkSmServices;
		markChange("pkSmServices", pkSmServices);
	}

	@Column(name = "PK_SYS_INTEGRATION")
	public String getPkSysIntegration() {
		return pkSysIntegration;
	}

	public void setPkSysIntegration(String pkSysIntegration) {
		this.pkSysIntegration = pkSysIntegration;
		markChange("pkSysIntegration", pkSysIntegration);
	}
}