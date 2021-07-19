package com.gwssi.ebaic.domain;

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
 * 工作库-企业主表。
 * 
 * @author sgz
 */
@Entity
@Table(name = "BE_WK_ENT")
public class BeWkEntBO  extends AbsDaoBussinessObject{
    private String entId;   
    private String priPId; 
    private String nameAppId;
    private String uniScid;  
    private String regNo;  
    private String oldRegNo;   
    private String industryCo; 
    private String rsEntId;   
    private String entName;  
    private String entType;   
    private String industryPhy; 
    private String opLoc;    
    private String domDistrict;  
    private String domDetail; 
    private String domStreet;
    private String domVillage;    
    private String domNo;  
    private String domBuilding; 
    private String domOther; 
    private String domProRight;
    private String domOwner;
    private String businessScope;    
    private String opScope;  
    private String ptBusScope;    
    private String opSuffix;    
    private String regCap;   
    private String recCap;   
    private Calendar opFrom;
    private Calendar opTo;
    private BigDecimal tradeTerm; 
    private String insForm;   
    private String entState;   
    private String localAdm; 
    private String enterType;    
    private String entMemo;
    private String organCode;
    private String regOrg;
    private String entTypeMemo;
    private Calendar enterDate; 
    private Calendar estDate; 
    private Calendar approveDate; 
    private Calendar regEndDate; 
    private Calendar cancelDate; 
    private Calendar repValPer;
    private BigDecimal domAcreage;
    private String proLoc;   
    private String proLocProv; 
    private String proLocCity;
    private String proLocOther;
    private String zgcFlag;
    private String busiPlace;
    private String scopeSign;
    private String mainScope;
    
    private String catId ; // 没有数据库字段
    
    private String gid; 
    private Calendar timestamp;
    
    private String opCustomScope;
    
    @Column(name = "CAT_ID")
    public String getCatId() {
		return catId;
	}
	public void setCatId(String catId) {
		this.catId = catId;
	}
	@Id
    @Column(name = "ENT_ID")
    public String getEntId() {
        return entId;
    }
    public void setEntId(String entId) {
        this.entId = entId;
        markChange("entId", entId);//拼接update sql语句使用，如果加了markChange，在保存bo的时候拼接该字段，否则不拼接
    }
    @Column(name = "PRI_P_ID")
    public String getPriPId() {
        return priPId;
    }
    public void setPriPId(String priPId) {
        this.priPId = priPId;
        markChange("priPId", priPId);
    }
    @Column(name = "NAME_APP_ID")
    public String getNameAppId() {
        return nameAppId;
    }
    public void setNameAppId(String nameAppId) {
        this.nameAppId = nameAppId;
        markChange("nameAppId", nameAppId);
    }
    @Column(name = "UNI_SCID")
    public String getUniScid() {
        return uniScid;
    }
    public void setUniScid(String uniScid) {
        this.uniScid = uniScid;
        markChange("uniScid", uniScid);
    }
    @Column(name = "REG_NO")
    public String getRegNo() {
        return regNo;
    }
    public void setRegNo(String regNo) {
        this.regNo = regNo;
        markChange("regNo", regNo);
    }
    @Column(name = "OLD_REG_NO")
    public String getOldRegNo() {
        return oldRegNo;
    }
    public void setOldRegNo(String oldRegNo) {
        this.oldRegNo = oldRegNo;
        markChange("oldRegNo", oldRegNo);
    }
    @Column(name = "INDUSTRY_CO")
    public String getIndustryCo() {
        return industryCo;
    }
    public void setIndustryCo(String industryCo) {
        this.industryCo = industryCo;
        markChange("oldRegNo", oldRegNo);
    }
    @Column(name = "RS_ENT_ID")
    public String getRsEntId() {
        return rsEntId;
    }
    public void setRsEntId(String rsEntId) {
        this.rsEntId = rsEntId;
        markChange("rsEntId", rsEntId);
    }
    @Column(name = "ENT_NAME")
    public String getEntName() {
        return entName;
    }
    public void setEntName(String entName) {
        this.entName = entName;
        markChange("entName", entName);
    }
    @Column(name = "ENT_TYPE")
    public String getEntType() {
        return entType;
    }
    public void setEntType(String entType) {
        this.entType = entType;
        markChange("entType", entType);
    }
    @Column(name = "INDUSTRY_PHY")
    public String getIndustryPhy() {
        return industryPhy;
    }
    public void setIndustryPhy(String industryPhy) {
        this.industryPhy = industryPhy;
        markChange("industryPhy", industryPhy);
    }
    @Column(name = "OP_LOC")
    public String getOpLoc() {
        return opLoc;
    }
    public void setOpLoc(String opLoc) {
        this.opLoc = opLoc;
        markChange("opLoc", opLoc);
    }
    @Column(name = "DOM_DISTRICT")
    public String getDomDistrict() {
        return domDistrict;
    }
    public void setDomDistrict(String domDistrict) {
        this.domDistrict = domDistrict;
        markChange("domDistrict", domDistrict);
    }
    @Column(name = "DOM_DETAIL")
    public String getDomDetail() {
        return domDetail;
    }
    public void setDomDetail(String domDetail) {
        this.domDetail = domDetail;
        markChange("domDetail", domDetail);
    }
    @Column(name = "DOM_STREET")
    public String getDomStreet() {
        return domStreet;
    }
    public void setDomStreet(String domStreet) {
        this.domStreet = domStreet;
        markChange("domStreet", domStreet);
    }
    @Column(name = "DOM_VILLAGE")
    public String getDomVillage() {
        return domVillage;
    }
    public void setDomVillage(String domVillage) {
        this.domVillage = domVillage;
        markChange("domVillage", domVillage);
    }
    @Column(name = "DOM_NO")
    public String getDomNo() {
        return domNo;
    }
    public void setDomNo(String domNo) {
        this.domNo = domNo;
        markChange("domNo", domNo);
    }
    @Column(name = "DOM_BUILDING")
    public String getDomBuilding() {
        return domBuilding;
    }
    public void setDomBuilding(String domBuilding) {
        this.domBuilding = domBuilding;
        markChange("domBuilding", domBuilding);
    }
    @Column(name = "DOM_OTHER")
    public String getDomOther() {
        return domOther;
    }
    public void setDomOther(String domOther) {
        this.domOther = domOther;
        markChange("domOther", domOther);
    }
    @Column(name = "DOM_PRO_RIGHT")
    public String getDomProRight() {
        return domProRight;
    }
    public void setDomProRight(String domProRight) {
        this.domProRight = domProRight;
        markChange("domProRight", domProRight);
    }
    @Column(name = "DOM_OWNER")
    public String getDomOwner() {
        return domOwner;
    }
    public void setDomOwner(String domOwner) {
        this.domOwner = domOwner;
        markChange("domOwner", domOwner);
    }
    @Column(name = "BUSINESS_SCOPE")
    public String getBusinessScope() {
        return businessScope;
    }
    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
        markChange("businessScope", businessScope);
    }
    @Column(name = "OP_SCOPE")
    public String getOpScope() {
        return opScope;
    }
    public void setOpScope(String opScope) {
        this.opScope = opScope;
        markChange("opScope", opScope);
    }
    @Column(name = "PT_BUS_SCOPE")
    public String getPtBusScope() {
        return ptBusScope;
    }
    public void setPtBusScope(String ptBusScope) {
        this.ptBusScope = ptBusScope;
        markChange("ptBusScope", ptBusScope);
    }
    @Column(name = "OP_SUFFIX")
    public String getOpSuffix() {
        return opSuffix;
    }
    public void setOpSuffix(String opSuffix) {
        this.opSuffix = opSuffix;
        markChange("opSuffix", opSuffix);
    }
    @Column(name = "REG_CAP")
    public String getRegCap() {
        return regCap;
    }
    public void setRegCap(String regCap) {
        this.regCap = regCap;
        markChange("regCap", regCap);
    }
    @Column(name = "REC_CAP")
    public String getRecCap() {
        return recCap;
    }
    public void setRecCap(String recCap) {
        this.recCap = recCap;
        markChange("recCap", recCap);
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "OP_FROM")
    public Calendar getOpFrom() {
        return opFrom;
    }
    public void setOpFrom(Calendar opFrom) {
        this.opFrom = opFrom;
        markChange("opFrom", opFrom);
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "OP_TO")
    public Calendar getOpTo() {
        return opTo;
    }
    public void setOpTo(Calendar opTo) {
        this.opTo = opTo;
        markChange("opTo", opTo);
    }
    @Column(name = "TRADE_TERM")
    public BigDecimal getTradeTerm() {
        return tradeTerm;
    }
    public void setTradeTerm(BigDecimal tradeTerm) {
        this.tradeTerm = tradeTerm;
        markChange("tradeTerm", tradeTerm);
    }
    @Column(name = "INS_FORM")
    public String getInsForm() {
        return insForm;
    }
    public void setInsForm(String insForm) {
        this.insForm = insForm;
        markChange("insForm", insForm);
    }
    @Column(name = "ENT_STATE")
    public String getEntState() {
        return entState;
    }
    public void setEntState(String entState) {
        this.entState = entState;
        markChange("entState", entState);
    }
    @Column(name = "LOCAL_ADM")
    public String getLocalAdm() {
        return localAdm;
    }
    public void setLocalAdm(String localAdm) {
        this.localAdm = localAdm;
        markChange("localAdm", localAdm);
    }
    @Column(name = "ENTER_TYPE")
    public String getEnterType() {
        return enterType;
    }
    public void setEnterType(String enterType) {
        this.enterType = enterType;
        markChange("enterType", enterType);
    }
    @Column(name = "ENT_MEMO")
    public String getEntMemo() {
        return entMemo;
    }
    public void setEntMemo(String entMemo) {
        this.entMemo = entMemo;
        markChange("entMemo", entMemo);
    }
    @Column(name = "ORGAN_CODE")
    public String getOrganCode() {
        return organCode;
    }
    public void setOrganCode(String organCode) {
        this.organCode = organCode;
        markChange("organCode", organCode);
    }
    @Column(name = "REG_ORG")
    public String getRegOrg() {
        return regOrg;
    }
    public void setRegOrg(String regOrg) {
        this.regOrg = regOrg;
        markChange("regOrg", regOrg);
    }
    @Column(name = "ENT_TYPE_MEMO")
    public String getEntTypeMemo() {
        return entTypeMemo;
    }
    public void setEntTypeMemo(String entTypeMemo) {
        this.entTypeMemo = entTypeMemo;
        markChange("entTypeMemo", entTypeMemo);
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "ENTER_DATE")
    public Calendar getEnterDate() {
        return enterDate;
    }
    public void setEnterDate(Calendar enterDate) {
        this.enterDate = enterDate;
        markChange("enterDate", enterDate);
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "EST_DATE")
    public Calendar getEstDate() {
        return estDate;
    }
    public void setEstDate(Calendar estDate) {
        this.estDate = estDate;
        markChange("estDate", estDate);
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "APPROVE_DATE")
    public Calendar getApproveDate() {
        return approveDate;
    }
    public void setApproveDate(Calendar approveDate) {
        this.approveDate = approveDate;
        markChange("approveDate", approveDate);
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "REG_END_DATE")
    public Calendar getRegEndDate() {
        return regEndDate;
    }
    public void setRegEndDate(Calendar regEndDate) {
        this.regEndDate = regEndDate;
        markChange("regEndDate", regEndDate);
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "CANCEL_DATE")
    public Calendar getCancelDate() {
        return cancelDate;
    }
    public void setCancelDate(Calendar cancelDate) {
        this.cancelDate = cancelDate;
        markChange("cancelDate", cancelDate);
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "REP_VAL_PER")
    public Calendar getRepValPer() {
        return repValPer;
    }
    public void setRepValPer(Calendar repValPer) {
        this.repValPer = repValPer;
        markChange("repValPer", repValPer);
    }
    @Column(name = "DOM_ACREAGE")
    public BigDecimal getDomAcreage() {
        return domAcreage;
    }
    public void setDomAcreage(BigDecimal domAcreage) {
        this.domAcreage = domAcreage;
        markChange("domAcreage", domAcreage);
    }
    @Column(name = "PRO_LOC")
    public String getProLoc() {
        return proLoc;
    }
    public void setProLoc(String proLoc) {
        this.proLoc = proLoc;
        markChange("proLoc", proLoc);
    }
    @Column(name = "PRO_LOC_PROV")
    public String getProLocProv() {
        return proLocProv;
    }
    public void setProLocProv(String proLocProv) {
        this.proLocProv = proLocProv;
        markChange("proLocProv", proLocProv);
    }
    @Column(name = "PRO_LOC_CITY")
    public String getProLocCity() {
        return proLocCity;
    }
    public void setProLocCity(String proLocCity) {
        this.proLocCity = proLocCity;
        markChange("proLocCity", proLocCity);
    }
    @Column(name = "PRO_LOC_OTHER")
    public String getProLocOther() {
        return proLocOther;
    }
    public void setProLocOther(String proLocOther) {
        this.proLocOther = proLocOther;
        markChange("proLocOther", proLocOther);
    }
    @Column(name = "ZGC_FLAG")
    public String getZgcFlag() {
        return zgcFlag;
    }
    public void setZgcFlag(String zgcFlag) {
        this.zgcFlag = zgcFlag;
        markChange("zgcFlag", zgcFlag);
    }
    @Column(name = "BUSI_PLACE")
    public String getBusiPlace() {
        return busiPlace;
    }
    public void setBusiPlace(String busiPlace) {
        this.busiPlace = busiPlace;
        markChange("busiPlace", busiPlace);
    }
    @Column(name = "GID")
    public String getGid() {
        return gid;
    }
    public void setGid(String gid) {
        this.gid = gid;
        markChange("gid", gid);
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TIMESTAMP")
    public Calendar getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
        markChange("timestamp", timestamp);
    }
    @Column(name="OP_CUSTOM_SCOPE")
	public String getOpCustomScope() {
		return opCustomScope;
	}
	public void setOpCustomScope(String opCustomScope) {
		this.opCustomScope = opCustomScope;
		markChange("opCustomScope", opCustomScope);
	}  
	@Column(name="SCOPE_SIGN")
	public String getScopeSign() {
		return scopeSign;
	}
	public void setScopeSign(String scopeSign) {
		this.scopeSign = scopeSign;
		markChange("scopeSign", scopeSign);
	}
	@Column(name="MAIN_SCOPE")
    public String getMainScope() {
        return mainScope;
    }
    public void setMainScope(String mainScope) {
        this.mainScope = mainScope;
        markChange("mainScope", mainScope);
    }
	
}
