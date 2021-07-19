package cn.gwssi.report.model;


import java.util.HashMap;
import java.util.Map;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * T_COGNOS_Report表对应的实体类
 */
@Entity
@Table(name = "T_COGNOS_Report")
public class TCognosReportBO extends AbsDaoBussinessObject {
	
	public TCognosReportBO(){}

	private String id;	
	private String regcode;	
	private String reporttype;	
	private String reporttime;	
	private String reportparamters;	
	private String reportname;
	private HashMap mapHelper;
	private String mouth;	
	private String year;	
	
	
	public HashMap getMapHelper() {
		return mapHelper;
	}
	public void setMapHelper(HashMap mapHelper) {
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
	
	
}