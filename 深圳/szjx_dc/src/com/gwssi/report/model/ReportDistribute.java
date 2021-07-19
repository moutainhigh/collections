package com.gwssi.report.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * 报表分发 与 分局|所，映射关系表
 * @author wuyincheng ,Oct 15, 2016
 */
@Entity
@Table(name = "DB_CSDB.T_REPORT_DISTRIBUTE")
public class ReportDistribute  extends AbsDaoBussinessObject {

	private String id;
	private String reportId;
	private String deptCode;
	private String deptName;
	private String upperDept;
	
	public ReportDistribute() {
		// TODO Auto-generated constructor stub
	}
	
	public ReportDistribute(String id, String reportId,String deptCode){
		this.id = id;
		this.reportId = reportId;
		this.deptCode = deptCode;
	}

	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		markChange("id", id);
	}

	@Column(name = "reportId")
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
		markChange("reportId", reportId);
	}

	@Column(name = "deptCode")
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
		markChange("deptCode", deptCode);
	}

	@Column(name = "deptName")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
		markChange("deptName", deptName);
	}

	@Column(name = "upperDept")
	public String getUpperDept() {
		return upperDept;
	}

	public void setUpperDept(String upperDept) {
		this.upperDept = upperDept;
		markChange("upperDept", upperDept);
	}

	
	
}
