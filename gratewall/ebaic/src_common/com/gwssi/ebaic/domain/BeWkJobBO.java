package com.gwssi.ebaic.domain;

import com.gwssi.optimus.core.persistence.annotation.Column;
import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Id;
import com.gwssi.optimus.core.persistence.annotation.Table;

import java.util.Calendar;

import com.gwssi.optimus.core.persistence.annotation.Temporal;

import java.math.BigDecimal;

import com.gwssi.optimus.core.persistence.annotation.TemporalType;
import com.gwssi.optimus.core.persistence.dao.AbsDaoBussinessObject;

/**
 * CP_WK_JOB表对应的实体类
 */
@Entity
@Table(name = "BE_WK_JOB")
public class BeWkJobBO extends AbsDaoBussinessObject {
	

	public BeWkJobBO(){}

	private String psnjobId;	
	private String entmemberId;	
	private String position;	
	private Calendar offHFrom;	
	private Calendar offHTo;	
	private BigDecimal offYears;	
	private String posBrForm;	
	private String appoOrg;	
	private String gid;	
	private Calendar timestamp;	
	private String leRepSign;	
	private String supsType;	
	
	@Id
	@Column(name = "PSNJOB_ID")
	public String getPsnjobId(){
		return psnjobId;
	}
	public void setPsnjobId(String psnjobId){
		this.psnjobId = psnjobId;
		markChange("psnjobId", psnjobId);
	}
	@Column(name = "ENTMEMBER_ID")
	public String getEntmemberId(){
		return entmemberId;
	}
	public void setEntmemberId(String entmemberId){
		this.entmemberId = entmemberId;
		markChange("entmemberId", entmemberId);
	}
	@Column(name = "POSITION")
	public String getPosition(){
		return position;
	}
	public void setPosition(String position){
		this.position = position;
		markChange("position", position);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "OFF_H_FROM")
	public Calendar getOffHFrom(){
		return offHFrom;
	}
	public void setOffHFrom(Calendar offHFrom){
		this.offHFrom = offHFrom;
		markChange("offHFrom", offHFrom);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "OFF_H_TO")
	public Calendar getOffHTo(){
		return offHTo;
	}
	public void setOffHTo(Calendar offHTo){
		this.offHTo = offHTo;
		markChange("offHTo", offHTo);
	}
	@Column(name = "OFF_YEARS")
	public BigDecimal getOffYears(){
		return offYears;
	}
	public void setOffYears(BigDecimal offYears){
		this.offYears = offYears;
		markChange("offYears", offYears);
	}
	@Column(name = "POS_BR_FORM")
	public String getPosBrForm(){
		return posBrForm;
	}
	public void setPosBrForm(String posBrForm){
		this.posBrForm = posBrForm;
		markChange("posBrForm", posBrForm);
	}
	@Column(name = "APPO_ORG")
	public String getAppoOrg(){
		return appoOrg;
	}
	public void setAppoOrg(String appoOrg){
		this.appoOrg = appoOrg;
		markChange("appoOrg", appoOrg);
	}
	@Column(name = "GID")
	public String getGid(){
		return gid;
	}
	public void setGid(String gid){
		this.gid = gid;
		markChange("gid", gid);
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TIMESTAMP")
	public Calendar getTimestamp(){
		return timestamp;
	}
	public void setTimestamp(Calendar timestamp){
		this.timestamp = timestamp;
		markChange("timestamp", timestamp);
	}
	@Column(name = "LE_REP_SIGN")
	public String getLeRepSign(){
		return leRepSign;
	}
	public void setLeRepSign(String leRepSign){
		this.leRepSign = leRepSign;
		markChange("leRepSign", leRepSign);
	}
	
	@Column(name = "SUPS_TYPE")
	public String getSupsType(){
		return supsType;
	}
	public void setSupsType(String supsType){
		this.supsType = supsType;
		markChange("supsType", supsType);
	}
	
	private String positionType;	
	/**
	 * 职务类型，1-董事，2-监事，3-经理。
	 * @return
	 */
	@Column(name = "POSITION_TYPE")
	public String getPositionType(){
		return positionType;
	}
	public void setPositionType(String positionType){
		this.positionType = positionType;
		markChange("positionType", positionType);
	}
	
	/**
	 * @return 变更标记，1-新增，2-编辑，3-删除。
	 */
	@Column(name = "MODIFY_SIGN")
	public String getModifySign(){
		return modifySign;
	}
	public void setModifySign(String modifySign){
		this.modifySign = modifySign;
		markChange("modifySign", modifySign);
	}
	private String modifySign;	
	/**
	 * @return 变更备注。
	 */
	@Column(name = "MODIFY_MEMO")
	public String getModifyMemo(){
		return modifyMemo;
	}
	public void setModifyMemo(String modifyMemo){
		this.modifyMemo = modifyMemo;
		markChange("modifyMemo", modifyMemo);
	}
	private String modifyMemo;	
}