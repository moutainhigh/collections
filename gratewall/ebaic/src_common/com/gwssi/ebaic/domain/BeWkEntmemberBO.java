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
 * CP_WK_ENTMEMBER表对应的实体类
 */
@Entity
@Table(name = "BE_WK_ENTMEMBER")
public class BeWkEntmemberBO extends AbsDaoBussinessObject {
	

	public BeWkEntmemberBO(){}

	private String entmemberId;	
	private String entId;	
	private String name;	
	private String sex;	
	private String country;	
	private String cerType;	
	private String cerNo;	
	private String position;	
	private String posBrForm;	
	private String leRepSign;	
	private String isConcurrentMgr;	
	private BigDecimal offYears;	
	private String liteDeg;	
	private Calendar natDate;	
	private String polStand;	
	private String nation;	
	private String houseAdd;	
	private String houseAddProv;	
	private String houseAddCity;	
	private String houseAddOther;	
	private String unionSign;	
	private String offSign;	
	private String nameEng;	
	private String accdSide;	
	private BigDecimal repTo;	
	private String repType;	
	private String repNo;	
	private String repState;	
	private Calendar repValPer;	
	private Calendar repIssDate;	
	private Calendar enterDate;	
	private String rsEntmemberId;	
	private String addInChina;	
	private Calendar cerValPer;	
	private Calendar cerIssDate;	
	private String gid;	
	private Calendar timestamp;	
	private String accdInvestorId;	
	private String supsType;	
	
	@Id
	@Column(name = "ENTMEMBER_ID")
	public String getEntmemberId(){
		return entmemberId;
	}
	public void setEntmemberId(String entmemberId){
		this.entmemberId = entmemberId;
		markChange("entmemberId", entmemberId);
	}
	@Column(name = "ENT_ID")
	public String getEntId(){
		return entId;
	}
	public void setEntId(String entId){
		this.entId = entId;
		markChange("entId", entId);
	}
	@Column(name = "NAME")
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
		markChange("name", name);
	}
	@Column(name = "SEX")
	public String getSex(){
		return sex;
	}
	public void setSex(String sex){
		this.sex = sex;
		markChange("sex", sex);
	}
	@Column(name = "COUNTRY")
	public String getCountry(){
		return country;
	}
	public void setCountry(String country){
		this.country = country;
		markChange("country", country);
	}
	@Column(name = "CER_TYPE")
	public String getCerType(){
		return cerType;
	}
	public void setCerType(String cerType){
		this.cerType = cerType;
		markChange("cerType", cerType);
	}
	@Column(name = "CER_NO")
	public String getCerNo(){
		return cerNo;
	}
	public void setCerNo(String cerNo){
		this.cerNo = cerNo;
		markChange("cerNo", cerNo);
	}
	@Column(name = "POSITION")
	public String getPosition(){
		return position;
	}
	public void setPosition(String position){
		this.position = position;
		markChange("position", position);
	}
	@Column(name = "POS_BR_FORM")
	public String getPosBrForm(){
		return posBrForm;
	}
	public void setPosBrForm(String posBrForm){
		this.posBrForm = posBrForm;
		markChange("posBrForm", posBrForm);
	}
	@Column(name = "LE_REP_SIGN")
	public String getLeRepSign(){
		return leRepSign;
	}
	public void setLeRepSign(String leRepSign){
		this.leRepSign = leRepSign;
		markChange("leRepSign", leRepSign);
	}
	@Column(name = "IS_CONCURRENT_MGR")
	public String getIsConcurrentMgr(){
		return isConcurrentMgr;
	}
	public void setIsConcurrentMgr(String isConcurrentMgr){
		this.isConcurrentMgr = isConcurrentMgr;
		markChange("isConcurrentMgr", isConcurrentMgr);
	}
	@Column(name = "OFF_YEARS")
	public BigDecimal getOffYears(){
		return offYears;
	}
	public void setOffYears(BigDecimal offYears){
		this.offYears = offYears;
		markChange("offYears", offYears);
	}
	
	@Column(name = "LITE_DEG")
	public String getLiteDeg(){
		return liteDeg;
	}
	public void setLiteDeg(String liteDeg){
		this.liteDeg = liteDeg;
		markChange("liteDeg", liteDeg);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "NAT_DATE")
	public Calendar getNatDate(){
		return natDate;
	}
	public void setNatDate(Calendar natDate){
		this.natDate = natDate;
		markChange("natDate", natDate);
	}
	@Column(name = "POL_STAND")
	public String getPolStand(){
		return polStand;
	}
	public void setPolStand(String polStand){
		this.polStand = polStand;
		markChange("polStand", polStand);
	}
	@Column(name = "NATION")
	public String getNation(){
		return nation;
	}
	public void setNation(String nation){
		this.nation = nation;
		markChange("nation", nation);
	}
	@Column(name = "HOUSE_ADD")
	public String getHouseAdd(){
		return houseAdd;
	}
	public void setHouseAdd(String houseAdd){
		this.houseAdd = houseAdd;
		markChange("houseAdd", houseAdd);
	}
	@Column(name = "HOUSE_ADD_PROV")
	public String getHouseAddProv(){
		return houseAddProv;
	}
	public void setHouseAddProv(String houseAddProv){
		this.houseAddProv = houseAddProv;
		markChange("houseAddProv", houseAddProv);
	}
	@Column(name = "HOUSE_ADD_CITY")
	public String getHouseAddCity(){
		return houseAddCity;
	}
	public void setHouseAddCity(String houseAddCity){
		this.houseAddCity = houseAddCity;
		markChange("houseAddCity", houseAddCity);
	}
	@Column(name = "HOUSE_ADD_OTHER")
	public String getHouseAddOther(){
		return houseAddOther;
	}
	public void setHouseAddOther(String houseAddOther){
		this.houseAddOther = houseAddOther;
		markChange("houseAddOther", houseAddOther);
	}
	@Column(name = "UNION_SIGN")
	public String getUnionSign(){
		return unionSign;
	}
	public void setUnionSign(String unionSign){
		this.unionSign = unionSign;
		markChange("unionSign", unionSign);
	}
	@Column(name = "OFF_SIGN")
	public String getOffSign(){
		return offSign;
	}
	public void setOffSign(String offSign){
		this.offSign = offSign;
		markChange("offSign", offSign);
	}
	@Column(name = "NAME_ENG")
	public String getNameEng(){
		return nameEng;
	}
	public void setNameEng(String nameEng){
		this.nameEng = nameEng;
		markChange("nameEng", nameEng);
	}
	@Column(name = "ACCD_SIDE")
	public String getAccdSide(){
		return accdSide;
	}
	public void setAccdSide(String accdSide){
		this.accdSide = accdSide;
		markChange("accdSide", accdSide);
	}
	@Column(name = "REP_TO")
	public BigDecimal getRepTo(){
		return repTo;
	}
	public void setRepTo(BigDecimal repTo){
		this.repTo = repTo;
		markChange("repTo", repTo);
	}
	@Column(name = "REP_TYPE")
	public String getRepType(){
		return repType;
	}
	public void setRepType(String repType){
		this.repType = repType;
		markChange("repType", repType);
	}
	@Column(name = "REP_NO")
	public String getRepNo(){
		return repNo;
	}
	public void setRepNo(String repNo){
		this.repNo = repNo;
		markChange("repNo", repNo);
	}
	@Column(name = "REP_STATE")
	public String getRepState(){
		return repState;
	}
	public void setRepState(String repState){
		this.repState = repState;
		markChange("repState", repState);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "REP_VAL_PER")
	public Calendar getRepValPer(){
		return repValPer;
	}
	public void setRepValPer(Calendar repValPer){
		this.repValPer = repValPer;
		markChange("repValPer", repValPer);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "REP_ISS_DATE")
	public Calendar getRepIssDate(){
		return repIssDate;
	}
	public void setRepIssDate(Calendar repIssDate){
		this.repIssDate = repIssDate;
		markChange("repIssDate", repIssDate);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "ENTER_DATE")
	public Calendar getEnterDate(){
		return enterDate;
	}
	public void setEnterDate(Calendar enterDate){
		this.enterDate = enterDate;
		markChange("enterDate", enterDate);
	}
	@Column(name = "RS_ENTMEMBER_ID")
	public String getRsEntmemberId(){
		return rsEntmemberId;
	}
	public void setRsEntmemberId(String rsEntmemberId){
		this.rsEntmemberId = rsEntmemberId;
		markChange("rsEntmemberId", rsEntmemberId);
	}
	@Column(name = "ADD_IN_CHINA")
	public String getAddInChina(){
		return addInChina;
	}
	public void setAddInChina(String addInChina){
		this.addInChina = addInChina;
		markChange("addInChina", addInChina);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "CER_VAL_PER")
	public Calendar getCerValPer(){
		return cerValPer;
	}
	public void setCerValPer(Calendar cerValPer){
		this.cerValPer = cerValPer;
		markChange("cerValPer", cerValPer);
	}
	@Temporal(TemporalType.DATE)
	@Column(name = "CER_ISS_DATE")
	public Calendar getCerIssDate(){
		return cerIssDate;
	}
	public void setCerIssDate(Calendar cerIssDate){
		this.cerIssDate = cerIssDate;
		markChange("cerIssDate", cerIssDate);
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
	@Column(name = "ACCD_INVESTOR_ID")
	public String getAccdInvestorId(){
		return accdInvestorId;
	}
	public void setAccdInvestorId(String accdInvestorId){
		this.accdInvestorId = accdInvestorId;
		markChange("accdInvestorId", accdInvestorId);
	}
	@Column(name = "SUPS_TYPE")
	public String getSupsType(){
		return supsType;
	}
	public void setSupsType(String supsType){
		this.supsType = supsType;
		markChange("supsType", supsType);
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
	
	
	private String countryEng;
	@Column(name = "COUNTRY_ENG")
	public String getCountryEng(){
		return countryEng;
	}
	public void setCountryEng(String countryEng){
		this.countryEng = countryEng;
		markChange("countryEng", countryEng);
	}
	
	
	private String tel;	
	@Column(name = "TEL")
	public String getTel(){
		return tel;
	}
	public void setTel(String tel){
		this.tel = tel;
		markChange("tel", tel);
	}
	
	private String mobTel;	
	@Column(name = "MOB_TEL")
	public String getMobTel(){
		return mobTel;
	}
	public void setMobTel(String mobTel){
		this.mobTel = mobTel;
		markChange("mobTel", mobTel);
	}
	
	private String email;	
	@Column(name = "EMAIL")
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
		markChange("email", email);
	}
}