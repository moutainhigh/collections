package com.gwssi.datacenter.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import com.gwssi.optimus.core.persistence.annotation.Lob;
import java.math.BigDecimal;

/**
 * DC_DM_JCDM_FX表对应的实体类
 */
@Entity
@Table(name = "DC_DM_JCDM_FX")
public class DcDmJcdmFxBO extends AbsDaoBussinessObject {
	
	public DcDmJcdmFxBO(){}

	private String dcSjfxId;	
	private String dcDmId;	
	private String dcSjfxDm;	
	private String dcSjfxMc;	
	private String dcSjfxCjm;	
	private String dcSjfxFjd;	
	private String szcc;	
	private BigDecimal xssx;	
	private String sfmx;	
	private String fxMs;	
	private String syZt;	
	private String modname;	
	private String modtime;	
	private String regname;	
	private String regtime;	
	private String zjDm;	
	private String sjDm;	
	private String description;	
	
	@Id
	@Column(name = "DC_SJFX_ID")
	public String getDcSjfxId(){
		return dcSjfxId;
	}
	public void setDcSjfxId(String dcSjfxId){
		this.dcSjfxId = dcSjfxId;
		markChange("dcSjfxId", dcSjfxId);
	}
	@Column(name = "DC_DM_ID")
	public String getDcDmId(){
		return dcDmId;
	}
	public void setDcDmId(String dcDmId){
		this.dcDmId = dcDmId;
		markChange("dcDmId", dcDmId);
	}
	@Column(name = "DC_SJFX_DM")
	public String getDcSjfxDm(){
		return dcSjfxDm;
	}
	public void setDcSjfxDm(String dcSjfxDm){
		this.dcSjfxDm = dcSjfxDm;
		markChange("dcSjfxDm", dcSjfxDm);
	}
	@Column(name = "DC_SJFX_MC")
	public String getDcSjfxMc(){
		return dcSjfxMc;
	}
	public void setDcSjfxMc(String dcSjfxMc){
		this.dcSjfxMc = dcSjfxMc;
		markChange("dcSjfxMc", dcSjfxMc);
	}
	@Column(name = "DC_SJFX_CJM")
	public String getDcSjfxCjm(){
		return dcSjfxCjm;
	}
	public void setDcSjfxCjm(String dcSjfxCjm){
		this.dcSjfxCjm = dcSjfxCjm;
		markChange("dcSjfxCjm", dcSjfxCjm);
	}
	@Column(name = "DC_SJFX_FJD")
	public String getDcSjfxFjd(){
		return dcSjfxFjd;
	}
	public void setDcSjfxFjd(String dcSjfxFjd){
		this.dcSjfxFjd = dcSjfxFjd;
		markChange("dcSjfxFjd", dcSjfxFjd);
	}
	@Column(name = "SZCC")
	public String getSzcc(){
		return szcc;
	}
	public void setSzcc(String szcc){
		this.szcc = szcc;
		markChange("szcc", szcc);
	}
	@Column(name = "XSSX")
	public BigDecimal getXssx(){
		return xssx;
	}
	public void setXssx(BigDecimal xssx){
		this.xssx = xssx;
		markChange("xssx", xssx);
	}
	@Column(name = "SFMX")
	public String getSfmx(){
		return sfmx;
	}
	public void setSfmx(String sfmx){
		this.sfmx = sfmx;
		markChange("sfmx", sfmx);
	}
	@Column(name = "FX_MS")
	public String getFxMs(){
		return fxMs;
	}
	public void setFxMs(String fxMs){
		this.fxMs = fxMs;
		markChange("fxMs", fxMs);
	}
	@Column(name = "SY_ZT")
	public String getSyZt(){
		return syZt;
	}
	public void setSyZt(String syZt){
		this.syZt = syZt;
		markChange("syZt", syZt);
	}
	@Column(name = "MODNAME")
	public String getModname(){
		return modname;
	}
	public void setModname(String modname){
		this.modname = modname;
		markChange("modname", modname);
	}
	@Column(name = "MODTIME")
	public String getModtime(){
		return modtime;
	}
	public void setModtime(String modtime){
		this.modtime = modtime;
		markChange("modtime", modtime);
	}
	@Column(name = "REGNAME")
	public String getRegname(){
		return regname;
	}
	public void setRegname(String regname){
		this.regname = regname;
		markChange("regname", regname);
	}
	@Column(name = "REGTIME")
	public String getRegtime(){
		return regtime;
	}
	public void setRegtime(String regtime){
		this.regtime = regtime;
		markChange("regtime", regtime);
	}
	@Column(name = "ZJ_DM")
	public String getZjDm(){
		return zjDm;
	}
	public void setZjDm(String zjDm){
		this.zjDm = zjDm;
		markChange("zjDm", zjDm);
	}
	@Column(name = "SJ_DM")
	public String getSjDm(){
		return sjDm;
	}
	public void setSjDm(String sjDm){
		this.sjDm = sjDm;
		markChange("sjDm", sjDm);
	}
	@Column(name = "DESCRIPTION")
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
		markChange("description", description);
	}
}