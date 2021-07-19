package com.gwssi.upload.service;

import java.util.List;

import com.gwssi.upload.pojo.AfficheSpotcheck;
import com.gwssi.upload.pojo.AnBaseinfo;
import com.gwssi.upload.pojo.AoOpaDetail;
import com.gwssi.upload.pojo.AoOpanomalyInv;
import com.gwssi.upload.pojo.CaseCfBaseinfo;
import com.gwssi.upload.pojo.CaseCfIrregpunishinfo;
import com.gwssi.upload.pojo.CaseCfPartyinfo;
import com.gwssi.upload.pojo.CaseCfTrans;
import com.gwssi.upload.pojo.EAlterRecoder;
import com.gwssi.upload.pojo.EBaseinfo;
import com.gwssi.upload.pojo.EContact;
import com.gwssi.upload.pojo.EFiSupl;
import com.gwssi.upload.pojo.EFinLeader;
import com.gwssi.upload.pojo.EImInvestment;
import com.gwssi.upload.pojo.EImInvprodetail;
import com.gwssi.upload.pojo.EImInvsralt;
import com.gwssi.upload.pojo.EImPermit;
import com.gwssi.upload.pojo.EImPrmtalt;
import com.gwssi.upload.pojo.EInvInvestment;
import com.gwssi.upload.pojo.ELicCertifica;
import com.gwssi.upload.pojo.ELpHstleref;
import com.gwssi.upload.pojo.ELpHstname;
import com.gwssi.upload.pojo.EMoveIn;
import com.gwssi.upload.pojo.EMoveOut;
import com.gwssi.upload.pojo.ENameInfo;
import com.gwssi.upload.pojo.EOtCase;
import com.gwssi.upload.pojo.EPriPerson;
import com.gwssi.upload.pojo.ESmBaseinfo;


public interface InsertEntInfoService {

	//根据统一社会信用代码id获取对象
	public EBaseinfo findEBaseInfo(String id);
	
	//企业基本信息E_BASEINFO插入数据
	public void insertEBaseinfo(EBaseinfo eBaseInfo);
	
	//抽查检查公告详情信息
	public void insertAfficSpot(List<AfficheSpotcheck> aspotCheck);
	
	//企业年报基本信息
	public void insertAnBaseInfo(List<AnBaseinfo> anBaseInfo);
	
	//企业异常名录详细信息
	public void insertAoOpaDetail(List<AoOpaDetail> aoOpaDetail);
	
	//企业经营异常名录股东信息
	public void insertAoOpanomalyInv(List<AoOpanomalyInv> aoOpanomalyInv);
	
	//案件当事人信息
	public void insertCaseCfPartyInfo(List<CaseCfPartyinfo> caseCfPartyinfo);
	
	//案件基本信息
	public void insertCaseCfBaseinfo(List<CaseCfBaseinfo> caseCfBaseinfo);

	//违法行为及处罚信息
	public void insertCaseCfIrregpunishinfo(List<CaseCfIrregpunishinfo> caseCfIrregpunishinfo);
	
	//案件移送信息
	public void insertCaseCfTrans(List<CaseCfTrans> caseCfTrans);
	
	//变更备案信息
	public void insertEAlterRecoder(List<EAlterRecoder> alterRecoder);
	
	//联络员信息
	public void insertEContact(List<EContact> contact);
	
	//财务负责人
	public void insertEFinLeader(List<EFinLeader> efinLeader);
	
	//外资补充信息
	public void insertEFiSupl(List<EFiSupl> eFiSupl);
	
	//企业公示-投资人情况信息
	public void insertEImInvestment(List<EImInvestment> objList);
	
	//企业公示-出资人认缴明细
	public void insertEImInvprodetail(List<EImInvprodetail> eImInvprodetail);
	
	//企业公示-股权变更信息
	public void insertEImInvsralt(List<EImInvsralt> eImInvsralt);
	
	//企业公示-许可信息
	public void insertEImPermit(List<EImPermit> eImPermit);
	
	//企业公示-许可变更信息
	public void insertEImPrmtalt(List<EImPrmtalt> eImPrmtalt);
	
	//非自然人投资信息
	public void insertEInvInvestment(List<EInvInvestment> eInvInvestment);
	
	//许可信息
	public void insertELicCertificate(List<ELicCertifica> certifica);
	
	//历史法定代表人信息
	public void insertELpHstleref(List<ELpHstleref> eLpHstleref);
	
	//历史名称信息
	public void insertELpHstname(List<ELpHstname> eLpHstname);
	
	//迁入信息
	public void insertEMoveIn(List<EMoveIn> eMoveIn);
	
	//迁出信息
	public void insertEMoveOut(List<EMoveOut> eMoveOut);
	
	//其他部门行政处罚信息
	public void insertEOtCase(List<EOtCase> eOtCase);
	
	//主要人员信息
	public void insertEPriPerson(List<EPriPerson> ePriPerson);
	
	//小微企业名录信息
	public void insertESmBaseinfo(List<ESmBaseinfo> eSmBaseinfo);
	
	//企业名称信息
	public void insertENameInfo(List<ENameInfo> eNameInfo);
	
}
