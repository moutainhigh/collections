package com.gwssi.datacenter.model;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;
import java.util.Calendar;
import com.gwssi.optimus.core.persistence.annotation.Temporal;
import com.gwssi.optimus.core.persistence.annotation.Lob;
import java.math.BigDecimal;
import com.gwssi.optimus.core.persistence.annotation.TemporalType;

/**
 * DC_STANDARD_SPEC表对应的实体类
 */
@Entity
@Table(name = "DC_STANDARD_SPEC")
public class DcStandardSpecBO extends AbsDaoBussinessObject {
	
	public DcStandardSpecBO(){}

	private String pkDcStandardSpec;	
	private String standardName;	
	private String standardCom;	
	private Calendar releaseTime;	
	private String fileName;	
	private String fileUrl;	
	private BigDecimal orderNo;	
	private String remarks;	
	private String effectiveMarker;	
	private String createrId;	
	private String createrName;	
	private Calendar createrTime;	
	private String modifierId;	
	private String modifierName;	
	private Calendar modifierTime;	
	
	@Id
	@Column(name = "PK_DC_STANDARD_SPEC")
	public String getPkDcStandardSpec(){
		return pkDcStandardSpec;
	}
	public void setPkDcStandardSpec(String pkDcStandardSpec){
		this.pkDcStandardSpec = pkDcStandardSpec;
		markChange("pkDcStandardSpec", pkDcStandardSpec);
	}
	@Column(name = "STANDARD_NAME")
	public String getStandardName(){
		return standardName;
	}
	public void setStandardName(String standardName){
		this.standardName = standardName;
		markChange("standardName", standardName);
	}
	@Column(name = "STANDARD_COM")
	public String getStandardCom(){
		return standardCom;
	}
	public void setStandardCom(String standardCom){
		this.standardCom = standardCom;
		markChange("standardCom", standardCom);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "RELEASE_TIME")
	public Calendar getReleaseTime(){
		return releaseTime;
	}
	public void setReleaseTime(Calendar releaseTime){
		this.releaseTime = releaseTime;
		markChange("releaseTime", releaseTime);
	}
	@Column(name = "FILE_NAME")
	public String getFileName(){
		return fileName;
	}
	public void setFileName(String fileName){
		this.fileName = fileName;
		markChange("fileName", fileName);
	}
	@Column(name = "FILE_URL")
	public String getFileUrl(){
		return fileUrl;
	}
	public void setFileUrl(String fileUrl){
		this.fileUrl = fileUrl;
		markChange("fileUrl", fileUrl);
	}
	@Column(name = "ORDER_NO")
	public BigDecimal getOrderNo(){
		return orderNo;
	}
	public void setOrderNo(BigDecimal orderNo){
		this.orderNo = orderNo;
		markChange("orderNo", orderNo);
	}
	@Column(name = "REMARKS")
	public String getRemarks(){
		return remarks;
	}
	public void setRemarks(String remarks){
		this.remarks = remarks;
		markChange("remarks", remarks);
	}
	@Column(name = "EFFECTIVE_MARKER")
	public String getEffectiveMarker(){
		return effectiveMarker;
	}
	public void setEffectiveMarker(String effectiveMarker){
		this.effectiveMarker = effectiveMarker;
		markChange("effectiveMarker", effectiveMarker);
	}
	@Column(name = "CREATER_ID")
	public String getCreaterId(){
		return createrId;
	}
	public void setCreaterId(String createrId){
		this.createrId = createrId;
		markChange("createrId", createrId);
	}
	@Column(name = "CREATER_NAME")
	public String getCreaterName(){
		return createrName;
	}
	public void setCreaterName(String createrName){
		this.createrName = createrName;
		markChange("createrName", createrName);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATER_TIME")
	public Calendar getCreaterTime(){
		return createrTime;
	}
	public void setCreaterTime(Calendar createrTime){
		this.createrTime = createrTime;
		markChange("createrTime", createrTime);
	}
	@Column(name = "MODIFIER_ID")
	public String getModifierId(){
		return modifierId;
	}
	public void setModifierId(String modifierId){
		this.modifierId = modifierId;
		markChange("modifierId", modifierId);
	}
	@Column(name = "MODIFIER_NAME")
	public String getModifierName(){
		return modifierName;
	}
	public void setModifierName(String modifierName){
		this.modifierName = modifierName;
		markChange("modifierName", modifierName);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFIER_TIME")
	public Calendar getModifierTime(){
		return modifierTime;
	}
	public void setModifierTime(Calendar modifierTime){
		this.modifierTime = modifierTime;
		markChange("modifierTime", modifierTime);
	}
}