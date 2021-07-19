package com.gwssi.datacenter.model;

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
 * DC_DM_JCDM表对应的实体类
 */
@Entity
@Table(name = "DC_DM_JCDM")
public class DcDmJcdmBO extends AbsDaoBussinessObject {
	
	public DcDmJcdmBO(){}

	private String dcDmId;	
	private String dcDmDm;	
	private String dcDmMc;	
	private String dcDmBzly;	
	private String dcDmMs;
	private String effectiveMarker;	
	private String modname;	
	private Calendar modtime;	
	private String regname;	
	private Calendar regtime;	
	private String busiObjectCode;	
	private String pkDcBusiObject;	
	private String dcDmType;	
	private String representation;	
	private String description;	
	private String codingMethods;	
	private String dataFrom;	
	private String timestamp;	
	private String modid;	
	private String regid;	
	
	@Id
	@Column(name = "DC_DM_ID")
	public String getDcDmId(){
		return dcDmId;
	}
	public void setDcDmId(String dcDmId){
		this.dcDmId = dcDmId;
		markChange("dcDmId", dcDmId);
	}
	@Column(name = "DC_DM_DM")
	public String getDcDmDm(){
		return dcDmDm;
	}
	public void setDcDmDm(String dcDmDm){
		this.dcDmDm = dcDmDm;
		markChange("dcDmDm", dcDmDm);
	}
	@Column(name = "DC_DM_MC")
	public String getDcDmMc(){
		return dcDmMc;
	}
	public void setDcDmMc(String dcDmMc){
		this.dcDmMc = dcDmMc;
		markChange("dcDmMc", dcDmMc);
	}
	@Column(name = "DC_DM_BZLY")
	public String getDcDmBzly(){
		return dcDmBzly;
	}
	public void setDcDmBzly(String dcDmBzly){
		this.dcDmBzly = dcDmBzly;
		markChange("dcDmBzly", dcDmBzly);
	}
	@Column(name = "DC_DM_MS")
	public String getDcDmMs(){
		return dcDmMs;
	}
	public void setDcDmMs(String dcDmMs){
		this.dcDmMs = dcDmMs;
		markChange("dcDmMs", dcDmMs);
	}
	@Column(name = "EFFECTIVE_MARKER")
	public String getEffectiveMarker(){
		return effectiveMarker;
	}
	public void setEffectiveMarker(String effectiveMarker){
		this.effectiveMarker = effectiveMarker;
		markChange("effectiveMarker", effectiveMarker);
	}
	@Column(name = "MODNAME")
	public String getModname(){
		return modname;
	}
	public void setModname(String modname){
		this.modname = modname;
		markChange("modname", modname);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODTIME")
	public Calendar getModtime(){
		return modtime;
	}
	public void setModtime(Calendar modtime){
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REGTIME")
	public Calendar getRegtime(){
		return regtime;
	}
	public void setRegtime(Calendar regtime){
		this.regtime = regtime;
		markChange("regtime", regtime);
	}
	@Column(name = "BUSI_OBJECT_CODE")
	public String getBusiObjectCode(){
		return busiObjectCode;
	}
	public void setBusiObjectCode(String busiObjectCode){
		this.busiObjectCode = busiObjectCode;
		markChange("busiObjectCode", busiObjectCode);
	}
	@Column(name = "PK_DC_BUSI_OBJECT")
	public String getPkDcBusiObject(){
		return pkDcBusiObject;
	}
	public void setPkDcBusiObject(String pkDcBusiObject){
		this.pkDcBusiObject = pkDcBusiObject;
		markChange("pkDcBusiObject", pkDcBusiObject);
	}
	@Column(name = "DC_DM_TYPE")
	public String getDcDmType(){
		return dcDmType;
	}
	public void setDcDmType(String dcDmType){
		this.dcDmType = dcDmType;
		markChange("dcDmType", dcDmType);
	}
	@Column(name = "REPRESENTATION")
	public String getRepresentation(){
		return representation;
	}
	public void setRepresentation(String representation){
		this.representation = representation;
		markChange("representation", representation);
	}
	@Column(name = "DESCRIPTION")
	public String getDescription(){
		return description;
	}
	public void setDescription(String description){
		this.description = description;
		markChange("description", description);
	}
	@Column(name = "CODING_METHODS")
	public String getCodingMethods(){
		return codingMethods;
	}
	public void setCodingMethods(String codingMethods){
		this.codingMethods = codingMethods;
		markChange("codingMethods", codingMethods);
	}
	@Column(name = "DATA_FROM")
	public String getDataFrom(){
		return dataFrom;
	}
	public void setDataFrom(String dataFrom){
		this.dataFrom = dataFrom;
		markChange("dataFrom", dataFrom);
	}
	@Column(name = "TIMESTAMP")
	public String getTimestamp(){
		return timestamp;
	}
	public void setTimestamp(String timestamp){
		this.timestamp = timestamp;
		markChange("timestamp", timestamp);
	}
	@Column(name = "MODID")
	public String getModid(){
		return modid;
	}
	public void setModid(String modid){
		this.modid = modid;
		markChange("modid", modid);
	}
	@Column(name = "REGID")
	public String getRegid(){
		return regid;
	}
	public void setRegid(String regid){
		this.regid = regid;
		markChange("regid", regid);
	}
}