package com.gwssi.report.model;


import java.util.HashMap;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_COGNOS_Report表对应的实体类
 */
@Entity
@Table(name = "DB_CSDB.T_COGNOS_Report")
public class TCognosReportBO extends AbsDaoBussinessObject {
	
	public TCognosReportBO(){}

	private String id;	
	private String regcode;	
	private String reporttype;	
	private String reporttime;	
	private String reportparamters;	
	private String reportname;
	@SuppressWarnings("rawtypes") private HashMap mapHelper;
	private String mouth;	
	private String year;	
	
	
	@SuppressWarnings("rawtypes") public HashMap getMapHelper() {
		return mapHelper;
	}
	@SuppressWarnings("rawtypes") public void setMapHelper(HashMap mapHelper) {
		this.mapHelper = mapHelper;
	}
	@Id
	@Column(name = "id")
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
		markChange("id", id);
	}
	@Column(name = "regcode")
	public String getRegcode(){
		return regcode;
	}
	public void setRegcode(String regcode){
		this.regcode = regcode;
		markChange("regcode", regcode);
	}

	@Column(name = "reportType")
	public String getReporttype(){
		return reporttype;
	}
	public void setReporttype(String reporttype){
		this.reporttype = reporttype;
		markChange("reporttype", reporttype);
	}
	@Column(name = "reportTime")
	public String getReporttime(){
		return reporttime;
	}
	public void setReporttime(String reporttime){
		this.reporttime = reporttime;
		markChange("reporttime", reporttime);
	}
	@Column(name = "reportParamters")
	public String getReportparamters(){
		return reportparamters;
	}
	public void setReportparamters(String reportparamters){
		this.reportparamters = reportparamters;
		markChange("reportparamters", reportparamters);
	}
	@Column(name = "ReportName")
	public String getReportname(){
		return reportname;
	}
	public void setReportname(String reportname){
		this.reportname = reportname;
		markChange("reportname", reportname);
	}
	@Column(name = "mouth")
	public String getMouth(){
		return mouth;
	}
	public void setMouth(String mouth){
		this.mouth = mouth;
		markChange("mouth", mouth);
	}
	@Column(name = "year")
	public String getYear(){
		return year;
	}
	public void setYear(String year){
		this.year = year;
		markChange("year", year);
	}
	
	@Override
	public int hashCode() {
		return id != null ? id.hashCode() * 31 : 
			   (regcode == null ? 0 : regcode.hashCode() * 7 +
				reportname == null ? 0 : reportname.hashCode() * 23 + 
				reporttype == null ? 0 : reporttype.hashCode() * 11 + 	
				reportparamters == null ? 0 : reportparamters.hashCode() * 13 + 	
				mouth == null ? 0 : mouth.hashCode() * 17 + 
				year == null ? 0 : year.hashCode() * 31);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof TCognosReportBO){
			TCognosReportBO tc = (TCognosReportBO) obj;
			return id != null && tc.id != null ? id.equals(tc.id) : 
				((reportname == null ? tc.reportname == null : reportname.equals(tc.reportname)) &&
				 (regcode == null ? tc.regcode == null : regcode.equals(tc.regcode)) &&
				 (reporttype == null ? tc.reporttype == null : reporttype.equals(tc.reporttype)) && 	
				 (reportparamters == null ? tc.reportparamters == null : reportparamters.equals(tc.reportparamters)) &&
				 (mouth == null ? tc.mouth == null : mouth.equals(tc.mouth)) &&
				 (year == null ? tc.year == null : year.equals(tc.year)));
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.id + "-" + this.reportname + "-" + this.reporttype + "-" + this.year + "-" + this.reportparamters;
	}
	
	//copy 
	public TCognosReportBO clone() {
		TCognosReportBO bo = new TCognosReportBO();
		//bo.setId(this.getId());
		bo.setMouth(this.getMouth());
		bo.setRegcode(this.getRegcode());
		bo.setReportname(this.getReportname());
		bo.setReportparamters(this.getReportparamters());
		bo.setReporttime(this.getReporttime());
		bo.setReporttype(this.getReporttype());
		bo.setYear(this.getYear());
		return bo;
	}
	
	public static void main(String[] args) {
		TCognosReportBO b = new TCognosReportBO();
		TCognosReportBO b1 = new TCognosReportBO();
		b.setReportname("综合5表");
		b.setYear("2014");
		b1.setReportname("综合5表");
		b1.setYear("2015");
		System.out.println(b.equals(b1));
	}
	
}