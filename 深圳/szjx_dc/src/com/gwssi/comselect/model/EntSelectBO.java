package com.gwssi.comselect.model;
import java.math.BigDecimal;
import java.util.Calendar;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * ENT_SELECT表对应的实体类
 */
@Entity
@Table(name = "ENT_SELECT")
public class EntSelectBO extends AbsDaoBussinessObject {
	
	public EntSelectBO(){}

	private String pripid;	
	private String regno;	
	private Calendar estdate;	
	private Calendar apprdate;	
	private BigDecimal reccap;	
	private String regcapcur;	
	private String industryphy;	
	private String entname;	
	private String opscope;	
	private String dom;	
	private String regorg;	
	private String adminbrancode;	
	private String workgrid;	
	private String unitgrid;	
	private String enttype;	
	private String regstate;	
	private String year;	
	private String industryco;	
	
	@Id
	@Column(name = "PRIPID")
	public String getPripid(){
		return pripid;
	}
	public void setPripid(String pripid){
		this.pripid = pripid;
		markChange("pripid", pripid);
	}
	@Column(name = "REGNO")
	public String getRegno(){
		return regno;
	}
	public void setRegno(String regno){
		this.regno = regno;
		markChange("regno", regno);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "ESTDATE")
	public Calendar getEstdate(){
		return estdate;
	}
	public void setEstdate(Calendar estdate){
		this.estdate = estdate;
		markChange("estdate", estdate);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "APPRDATE")
	public Calendar getApprdate(){
		return apprdate;
	}
	public void setApprdate(Calendar apprdate){
		this.apprdate = apprdate;
		markChange("apprdate", apprdate);
	}
	@Column(name = "RECCAP")
	public BigDecimal getReccap(){
		return reccap;
	}
	public void setReccap(BigDecimal reccap){
		this.reccap = reccap;
		markChange("reccap", reccap);
	}
	@Column(name = "REGCAPCUR")
	public String getRegcapcur(){
		return regcapcur;
	}
	public void setRegcapcur(String regcapcur){
		this.regcapcur = regcapcur;
		markChange("regcapcur", regcapcur);
	}
	@Column(name = "INDUSTRYPHY")
	public String getIndustryphy(){
		return industryphy;
	}
	public void setIndustryphy(String industryphy){
		this.industryphy = industryphy;
		markChange("industryphy", industryphy);
	}
	@Column(name = "ENTNAME")
	public String getEntname(){
		return entname;
	}
	public void setEntname(String entname){
		this.entname = entname;
		markChange("entname", entname);
	}
	@Column(name = "OPSCOPE")
	public String getOpscope(){
		return opscope;
	}
	public void setOpscope(String opscope){
		this.opscope = opscope;
		markChange("opscope", opscope);
	}
	@Column(name = "DOM")
	public String getDom(){
		return dom;
	}
	public void setDom(String dom){
		this.dom = dom;
		markChange("dom", dom);
	}
	@Column(name = "REGORG")
	public String getRegorg(){
		return regorg;
	}
	public void setRegorg(String regorg){
		this.regorg = regorg;
		markChange("regorg", regorg);
	}
	@Column(name = "ADMINBRANCODE")
	public String getAdminbrancode(){
		return adminbrancode;
	}
	public void setAdminbrancode(String adminbrancode){
		this.adminbrancode = adminbrancode;
		markChange("adminbrancode", adminbrancode);
	}
	@Column(name = "WORKGRID")
	public String getWorkgrid(){
		return workgrid;
	}
	public void setWorkgrid(String workgrid){
		this.workgrid = workgrid;
		markChange("workgrid", workgrid);
	}
	@Column(name = "UNITGRID")
	public String getUnitgrid(){
		return unitgrid;
	}
	public void setUnitgrid(String unitgrid){
		this.unitgrid = unitgrid;
		markChange("unitgrid", unitgrid);
	}
	@Column(name = "ENTTYPE")
	public String getEnttype(){
		return enttype;
	}
	public void setEnttype(String enttype){
		this.enttype = enttype;
		markChange("enttype", enttype);
	}
	@Column(name = "REGSTATE")
	public String getRegstate(){
		return regstate;
	}
	public void setRegstate(String regstate){
		this.regstate = regstate;
		markChange("regstate", regstate);
	}
	@Column(name = "YEAR")
	public String getYear(){
		return year;
	}
	public void setYear(String year){
		this.year = year;
		markChange("year", year);
	}
	@Column(name = "INDUSTRYCO")
	public String getIndustryco(){
		return industryco;
	}
	public void setIndustryco(String industryco){
		this.industryco = industryco;
		markChange("industryco", industryco);
	}
}