package com.gwssi.upload.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gwssi.upload.mapper.AfficheSpotcheckMapper;
import com.gwssi.upload.mapper.AnBaseinfoMapper;
import com.gwssi.upload.mapper.AoOpaDetailMapper;
import com.gwssi.upload.mapper.AoOpanomalyInvMapper;
import com.gwssi.upload.mapper.CaseCfBaseinfoMapper;
import com.gwssi.upload.mapper.CaseCfIrregpunishinfoMapper;
import com.gwssi.upload.mapper.CaseCfPartyinfoMapper;
import com.gwssi.upload.mapper.CaseCfTransMapper;
import com.gwssi.upload.mapper.EAlterRecoderMapper;
import com.gwssi.upload.mapper.EBaseinfoMapper;
import com.gwssi.upload.mapper.EContactMapper;
import com.gwssi.upload.mapper.EFiSuplMapper;
import com.gwssi.upload.mapper.EFinLeaderMapper;
import com.gwssi.upload.mapper.EImInvestmentMapper;
import com.gwssi.upload.mapper.EImInvprodetailMapper;
import com.gwssi.upload.mapper.EImInvsraltMapper;
import com.gwssi.upload.mapper.EImPermitMapper;
import com.gwssi.upload.mapper.EImPrmtaltMapper;
import com.gwssi.upload.mapper.EInvInvestmentMapper;
import com.gwssi.upload.mapper.ELicCertificateMapper;
import com.gwssi.upload.mapper.ELpHstlerefMapper;
import com.gwssi.upload.mapper.ELpHstnameMapper;
import com.gwssi.upload.mapper.ENameInfoMapper;
import com.gwssi.upload.mapper.EOtCaseMapper;
import com.gwssi.upload.mapper.EPriPersonMapper;
import com.gwssi.upload.mapper.ESmBaseinfoMapper;
import com.gwssi.upload.mapper.EmoveInMapper;
import com.gwssi.upload.mapper.EmoveOutMapper;
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
import com.gwssi.upload.utils.DateRegexUtil;

@Service
public class InsertEntInfoServiceImpl implements InsertEntInfoService {

	@Autowired
	private EBaseinfoMapper eBaseinfoMapper;
	@Autowired
	private EFiSuplMapper eFiSuplMapper;
	@Autowired 
	private ELpHstnameMapper eLpHstnameMapper;
	@Autowired
	private ELpHstlerefMapper eLpHstlerefMapper;
	@Autowired
	private EInvInvestmentMapper eInvInvestmentMapper;
	@Autowired
	private EPriPersonMapper ePriPersonMapper;
	@Autowired
	private ELicCertificateMapper eLicCertificateMapper;
	@Autowired
	private EAlterRecoderMapper eAlterRecoderMapper;
	@Autowired
	private EmoveInMapper emoveInMapper;
	@Autowired
	private EmoveOutMapper emoveOutMapper;
	@Autowired
	private EFinLeaderMapper eFinLeaderMapper;
	@Autowired
	private EContactMapper eContactMapper;
	@Autowired
	private AoOpaDetailMapper aoOpaDetailMapper;
	@Autowired
	private AoOpanomalyInvMapper aoOpanomalyInvMapperl;
	@Autowired
	private AnBaseinfoMapper anBaseinfoMapper;
	@Autowired
	private EImPermitMapper eImPermitMapper;
	@Autowired
	private EImPrmtaltMapper eImPrmtaltMapper;
	@Autowired
	private EImInvestmentMapper eImInvestmentMapper;
	@Autowired
	private EImInvsraltMapper eImInvsraltMapper;
	@Autowired
	private EImInvprodetailMapper eImInvprodetailMapper;
	@Autowired
	private ENameInfoMapper eNameInfoMapper;
	@Autowired
	private EOtCaseMapper eOtCaseMapper;
	@Autowired
	private ESmBaseinfoMapper eSmBaseinfoMapper;
	@Autowired
	private AfficheSpotcheckMapper afficheSpotcheckMapper;
	@Autowired
	private CaseCfBaseinfoMapper caseCfBaseinfoMapper;
	@Autowired
	private CaseCfIrregpunishinfoMapper caseCfIrregpunishinfoMapper;
	@Autowired
	private CaseCfPartyinfoMapper caseCfPartyinfoMapper;
	@Autowired
	private CaseCfTransMapper caseCfTransMapper;

	////根据统一社会信用代码id获取对象
	@Override
	public EBaseinfo findEBaseInfo(String id) {
		EBaseinfo findEBaseInfo = eBaseinfoMapper.findEBaseInfoById(id);
		return findEBaseInfo;
	}
	
	//基本信息
	@Override
	public void insertEBaseinfo(EBaseinfo eBaseInfo) {
		
		String estdate = eBaseInfo.getEstdate();
		String opfrom = eBaseInfo.getOpfrom();
		String opto = eBaseInfo.getOpto();
		String apprdate = eBaseInfo.getApprdate();
		
		if(!StringUtils.isEmpty(estdate)) {
			estdate = DateRegexUtil.dateType(estdate);
			eBaseInfo.setEstdate(estdate);
		}
		
		if(!StringUtils.isEmpty(opfrom)) {
			opfrom = DateRegexUtil.dateType(opfrom);
			eBaseInfo.setOpfrom(opfrom);
		}
		
		if (!StringUtils.isEmpty(opto)) {
			opto = DateRegexUtil.dateType(opto);
			eBaseInfo.setOpto(opto);
		}
		
		if(!StringUtils.isEmpty(apprdate)) {
			apprdate = DateRegexUtil.dateType(apprdate);
			eBaseInfo.setApprdate(apprdate);
		}
		
		eBaseinfoMapper.insertEntDate(eBaseInfo);
	}
	
	//抽查检查公告详情信息
	@Override
	public void insertAfficSpot(List<AfficheSpotcheck> aspotCheck) {
		for (AfficheSpotcheck afficheSpotcheck : aspotCheck) {
			if(afficheSpotcheck != null) {
				String insdate = afficheSpotcheck.getInsdate();
				if(!StringUtils.isEmpty(insdate)) {
					insdate = DateRegexUtil.dateType(insdate);
					afficheSpotcheck.setInsdate(insdate);
				}
				afficheSpotcheckMapper.insertAfficheSpotcheck(afficheSpotcheck);
			}
		}
	}

	//企业年报基本信息
	@Override
	public void insertAnBaseInfo(List<AnBaseinfo> anBaseInfo) {
		for (AnBaseinfo anBase : anBaseInfo) {
			if(anBase != null) {
				String lastupdatetime = anBase.getLastupdatetime();
				String anchedate = anBase.getAnchedate();
				
				if(!StringUtils.isEmpty(lastupdatetime)) {
			    	lastupdatetime = DateRegexUtil.dateType(lastupdatetime);
					anBase.setLastupdatetime(lastupdatetime);
				}
				if(!StringUtils.isEmpty(anchedate)) {
					anchedate = DateRegexUtil.dateType(anchedate);
					anBase.setAnchedate(anchedate);
				}
				
				anBaseinfoMapper.insertAnBaseinfo(anBase);
			}
		}
	}

	//企业异常名录详细信息
	@Override
	public void insertAoOpaDetail(List<AoOpaDetail> aoOpaDetail) {
		for (AoOpaDetail aoOpaD : aoOpaDetail) {
			if(aoOpaD != null) {
				
				String abntime = aoOpaD.getAbntime();
				String remdate = aoOpaD.getRemdate();
				
				if(!StringUtils.isEmpty(abntime)) {
					abntime = DateRegexUtil.dateType(abntime);
					aoOpaD.setAbntime(abntime);
				}
				
				if(!StringUtils.isEmpty(remdate)) {
					remdate = DateRegexUtil.dateType(remdate);
					aoOpaD.setRemdate(remdate);
				}
				
				aoOpaDetailMapper.insertAoOpaDetail(aoOpaD);
			}
		}
	}

	//企业经营异常名录股东信息
	@Override
	public void insertAoOpanomalyInv(List<AoOpanomalyInv> aoOpanomalyInv) {
		for (AoOpanomalyInv aoOpan : aoOpanomalyInv) {
			if(aoOpan != null) {
				aoOpanomalyInvMapperl.insertAoOpanomalyInv(aoOpan);
			}
		}
	}

	//案件当事人信息
	@Override
	public void insertCaseCfPartyInfo(List<CaseCfPartyinfo> caseCfPartyinfo) {
		for (CaseCfPartyinfo caseCfPa : caseCfPartyinfo) {
			if(caseCfPa != null) {
				
				String age = caseCfPa.getAge();
				if(" ".equals(age)) {
					age = null;
					caseCfPa.setAge(age);
				}
				caseCfPartyinfoMapper.insertCaseCfPartyinfo(caseCfPa);
			}
		}
	}
	
	//案件基本信息
	@Override
	public void insertCaseCfBaseinfo(List<CaseCfBaseinfo> caseCfBaseinfo) {
		for (CaseCfBaseinfo caseCfBa : caseCfBaseinfo) {
			if(caseCfBa != null) {
		
				String casetime = caseCfBa.getCasetime();
				String casefidate = caseCfBa.getCasefidate();
				String exedate = caseCfBa.getExedate();
				String clocasedate = caseCfBa.getClocasedate();
				
				if(!StringUtils.isEmpty(casetime)) {
					casetime = DateRegexUtil.dateType(casetime);
					caseCfBa.setCasetime(casetime);
				}
				
				if(!StringUtils.isEmpty(casefidate)) {
					casefidate = DateRegexUtil.dateType(casefidate);
					caseCfBa.setCasefidate(casefidate);
				}
				
				if(!StringUtils.isEmpty(exedate)) {
					exedate = DateRegexUtil.dateType(exedate);
					caseCfBa.setExedate(exedate);
				}
				
				if(!StringUtils.isEmpty(clocasedate)) {
					clocasedate = DateRegexUtil.dateType(clocasedate);
					caseCfBa.setClocasedate(clocasedate);
				}
				
				caseCfBaseinfoMapper.insertCaseCfBaseinfo(caseCfBa);
			}
		}
	}

	//违法行为及处罚信息
	@Override
	public void insertCaseCfIrregpunishinfo(
			List<CaseCfIrregpunishinfo> caseCfIrregpunishinfo) {
		for (CaseCfIrregpunishinfo caseCfIrre : caseCfIrregpunishinfo) {
			if(caseCfIrre != null) {
				
				String pendecissdate = caseCfIrre.getPendecissdate();
				String sertime = caseCfIrre.getSertime();
				
				if(!StringUtils.isEmpty(pendecissdate)) {
					pendecissdate = DateRegexUtil.dateType(pendecissdate);
					caseCfIrre.setPendecissdate(pendecissdate);
				}
				
				if(!StringUtils.isEmpty(sertime)) {
					sertime = DateRegexUtil.dateType(sertime);
					caseCfIrre.setSertime(sertime);
				}
				
				caseCfIrregpunishinfoMapper.insertCaseCfIrregpunishinfo(caseCfIrre);
			}
		}
	}

	//案件移除信息
	@Override
	public void insertCaseCfTrans(List<CaseCfTrans> caseCfTrans) {
		for (CaseCfTrans caseCfTr : caseCfTrans) {
			if(caseCfTr != null) {
				String tranfdate = caseCfTr.getTranfdate();
				if(!StringUtils.isEmpty(tranfdate)) {
					tranfdate = DateRegexUtil.dateType(tranfdate);
					caseCfTr.setTranfdate(tranfdate);
				}
				caseCfTransMapper.insertCaseCfTrans(caseCfTr);
			}
		}
	}

	//变更备案信息
	@Override
	public void insertEAlterRecoder(List<EAlterRecoder> alterRecoder) {
		for (EAlterRecoder eAlterR : alterRecoder) {
			if(eAlterR != null) {
				
				String altdate = eAlterR.getAltdate();
				if(!StringUtils.isEmpty(altdate)) {
					altdate = DateRegexUtil.dateType(altdate);
					eAlterR.setAltdate(altdate);
				}
				
				eAlterRecoderMapper.insertEAlterRecoder(eAlterR);
			}
		}
	}

	//联络员信息
	@Override
	public void insertEContact(List<EContact> contact) {
		for (EContact eContact : contact) {
			if(eContact != null) {
				eContactMapper.insertEContact(eContact);
			}
		}
	}

	//外资补充信息
	@Override
	public void insertEFiSupl(List<EFiSupl> eFiSupl) {
		for (EFiSupl eFiS : eFiSupl) {
			if(eFiS != null) {
				
				String chamecdate = eFiS.getChamecdate();
				String candate = eFiS.getCandate();
				String forentopfrom = eFiS.getForentopfrom();
				String forentopto = eFiS.getForentopto();
				
				if(!StringUtils.isEmpty(chamecdate)) {
					chamecdate = DateRegexUtil.dateType(chamecdate);
					eFiS.setChamecdate(chamecdate);
				}
				if(!StringUtils.isEmpty(candate)) {
					candate = DateRegexUtil.dateType(candate);
					eFiS.setCandate(candate);
				}
				if(!StringUtils.isEmpty(forentopfrom)) {
					forentopfrom = DateRegexUtil.dateType(forentopfrom);
					eFiS.setForentopfrom(forentopfrom);
				}
				if(!StringUtils.isEmpty(forentopto)) {
					forentopto = DateRegexUtil.dateType(forentopto);
					eFiS.setForentopto(forentopto);
				}
				
				eFiSuplMapper.insertEFiSupl(eFiS);
			}
		}
	}

	//企业公示_出资人认缴明细
	@Override
	public void insertEImInvprodetail(List<EImInvprodetail> eImInvprodetail) {
		for (EImInvprodetail eImInvp : eImInvprodetail) {
			if(eImInvp != null) {
				
				String condate = eImInvp.getCondate();
				String publicdate = eImInvp.getPublicdate();
				
				if(!StringUtils.isEmpty(condate)) {
					condate = DateRegexUtil.dateType(condate);
					eImInvp.setCondate(condate);
				}
				if(!StringUtils.isEmpty(publicdate)) {
					publicdate = DateRegexUtil.dateType(publicdate);
					eImInvp.setPublicdate(publicdate);
				}
				
				
				eImInvprodetailMapper.insertEImInvprodetail(eImInvp);
			}
		}
	}

	//企业公示_股权变更信息
	@Override
	public void insertEImInvsralt(List<EImInvsralt> eImInvsralt) {
		for (EImInvsralt eImInvsra : eImInvsralt) {
			if(eImInvsra != null && (eImInvsra.getAlitem().indexOf("股权变更") >= 0)) {
				
				String altdate = eImInvsra.getAltdate();
				String publicdate = eImInvsra.getPublicdate();
				
				if(!StringUtils.isEmpty(altdate)) {
					altdate = DateRegexUtil.dateType(altdate);
					eImInvsra.setAltdate(altdate);
				}
				if(!StringUtils.isEmpty(publicdate)) {
					publicdate = DateRegexUtil.dateType(publicdate);
					eImInvsra.setPublicdate(publicdate);
				}
				
				eImInvsraltMapper.insertEImInvsralt(eImInvsra);
			}
		}
	}

	//企业公示_许可信息
	@Override
	public void insertEImPermit(List<EImPermit> eImPermit) {
		for (EImPermit eImPer : eImPermit) {
			if(eImPer != null) {
				
				String valfrom = eImPer.getValfrom();
				String valto = eImPer.getValto();
				String candate = eImPer.getCandate();
				String revdate = eImPer.getRevdate();
				String invaliddate = eImPer.getInvaliddate();
				String publicdate = eImPer.getPublicdate();
				
				if(!StringUtils.isEmpty(valfrom)) {
					valfrom = DateRegexUtil.dateType(valfrom);
					eImPer.setValfrom(valfrom);
				}
				
				if(!StringUtils.isEmpty(valto)) {
					valto = DateRegexUtil.dateType(valto);
					eImPer.setValto(valto);
				}
				
				if(!StringUtils.isEmpty(candate)) {
					candate = DateRegexUtil.dateType(candate);
					eImPer.setCandate(candate);
				}
				
				if(!StringUtils.isEmpty(revdate)) {
					revdate = DateRegexUtil.dateType(revdate);
					eImPer.setRevdate(revdate);
				}
				
				if(!StringUtils.isEmpty(invaliddate)) {
					invaliddate = DateRegexUtil.dateType(invaliddate);
					eImPer.setInvaliddate(invaliddate);
				}
				
				if(!StringUtils.isEmpty(publicdate)) {
					publicdate = DateRegexUtil.dateType(publicdate);
					eImPer.setPublicdate(publicdate);
				}
				
				eImPermitMapper.insertEImPermit(eImPer);
			}
		}
	}

	//企业公示_许可变更信息
	@Override
	public void insertEImPrmtalt(List<EImPrmtalt> eImPrmtalt) {
		for (EImPrmtalt eImPrmta : eImPrmtalt) {
			if(eImPrmta != null) {
				
				String altdate = eImPrmta.getAltdate();
				
				if(!StringUtils.isEmpty(altdate)) {
					altdate = DateRegexUtil.dateType(altdate);
					eImPrmta.setAltdate(altdate);
				}
				
				eImPrmtaltMapper.insertEImPrmtalt(eImPrmta);
			}
		}
	}

	//非自然人出资信息
	@Override
	public void insertEInvInvestment(List<EInvInvestment> eInvInvestment) {
		for (EInvInvestment eInvInvest : eInvInvestment) {
			if(eInvInvest != null) {
				
				String condate = eInvInvest.getCondate();
				
				if(!StringUtils.isEmpty(condate)) {
					condate = DateRegexUtil.dateType(condate);
					eInvInvest.setCondate(condate);
				}
				
				eInvInvestmentMapper.insertEInvInvestment(eInvInvest);
			}
		}
	}

	//许可信息
	@Override
	public void insertELicCertificate(List<ELicCertifica> certifica) {
		for (ELicCertifica eLicCertifica : certifica) {
			if(eLicCertifica != null) {
				
				String valfrom = eLicCertifica.getValfrom();
				String valto = eLicCertifica.getValto();
				
				if(!StringUtils.isEmpty(valfrom)) {
					valfrom = DateRegexUtil.dateType(valfrom);
					eLicCertifica.setValfrom(valfrom);
				}
				
				if(!StringUtils.isEmpty(valto)) {
					valto = DateRegexUtil.dateType(valto);
					eLicCertifica.setValto(valto);
				}
				
				eLicCertificateMapper.insertELicCertificate(eLicCertifica);
			}
		}
	}

	//历史法定代表人信息
	@Override
	public void insertELpHstleref(List<ELpHstleref> eLpHstleref) {
		for (ELpHstleref eLpHstle : eLpHstleref) {
			if(eLpHstle != null) {
				eLpHstlerefMapper.insertELpHstleref(eLpHstle);
			}
		}
	}

	//历史名称信息
	@Override
	public void insertELpHstname(List<ELpHstname> eLpHstname) {
		for (ELpHstname eLpHstna : eLpHstname) {
			if(eLpHstna != null) {
				eLpHstnameMapper.insertELpHstname(eLpHstna);
			}
		}
	}

	//迁入信息
	@Override
	public void insertEMoveIn(List<EMoveIn> eMoveIn) {
		for (EMoveIn eMoveI : eMoveIn) {
			if(eMoveI != null) {
				
				String mindate = eMoveI.getMindate();
				if(!StringUtils.isEmpty(mindate)) {
					mindate = DateRegexUtil.dateType(mindate);
					eMoveI.setMindate(mindate);
				}
				
				emoveInMapper.insertEmoveIn(eMoveI);
			}
		}
	}

	//迁出信息
	@Override
	public void insertEMoveOut(List<EMoveOut> eMoveOut) {
		for (EMoveOut eMoveO : eMoveOut) {
			if(eMoveO != null) {
				
				String moutdate = eMoveO.getMoutdate();
				
				if(!StringUtils.isEmpty(moutdate)) {
					moutdate = DateRegexUtil.dateType(moutdate);
					eMoveO.setMoutdate(moutdate);
				}
				
				emoveOutMapper.insertEmoveOut(eMoveO);
			}
		}
	}

	//其他部门公示_行政处罚信息
	@Override
	public void insertEOtCase(List<EOtCase> eOtCase) {
		for (EOtCase eOtCa : eOtCase) {
			if(eOtCa != null) {
				
				String pendecissdate = eOtCa.getPendecissdate();
				String publicdate = eOtCa.getPublicdate();
				
				if(!StringUtils.isEmpty(pendecissdate)) {
					pendecissdate = DateRegexUtil.dateType(pendecissdate);
					eOtCa.setPendecissdate(pendecissdate);
				}
				
				if(!StringUtils.isEmpty(publicdate)) {
					publicdate = DateRegexUtil.dateType(publicdate);
					eOtCa.setPublicdate(publicdate);
				}
				
				eOtCaseMapper.insertEOtCase(eOtCa);
			}
		}
	}

	//主要人员信息
	@Override
	public void insertEPriPerson(List<EPriPerson> ePriPerson) {
		for (EPriPerson ePriPer : ePriPerson) {
			if(ePriPer != null) {
				
				String natdate = ePriPer.getNatdate();
				String arrchdate = ePriPer.getArrchdate();
				String repcarfrom = ePriPer.getRepcarfrom();
				String repcarto = ePriPer.getRepcarto();
				

				if(!StringUtils.isEmpty(natdate)) {
					natdate = DateRegexUtil.dateType(natdate);
					ePriPer.setNatdate(natdate);
				}
				
				if(!StringUtils.isEmpty(arrchdate)) {
					arrchdate = DateRegexUtil.dateType(arrchdate);
					ePriPer.setArrchdate(arrchdate);
				}
				
				if(!StringUtils.isEmpty(repcarfrom)) {
					repcarfrom = DateRegexUtil.dateType(repcarfrom);
					ePriPer.setRepcarfrom(repcarfrom);
				}
				
				if(!StringUtils.isEmpty(repcarto)) {
					repcarto = DateRegexUtil.dateType(repcarto);
					ePriPer.setRepcarto(repcarto);
				}
				
				ePriPersonMapper.insertEPriPerson(ePriPer);
			}
		}
	}

	//小微企业名录信息
	@Override
	public void insertESmBaseinfo(List<ESmBaseinfo> eSmBaseinfo) {
		for (ESmBaseinfo eSmBaseinf : eSmBaseinfo) {
			if(eSmBaseinf != null) {
				
				String estdate = eSmBaseinf.getEstdate();
				if(!StringUtils.isEmpty(estdate)) {
					estdate = DateRegexUtil.dateType(estdate);
					eSmBaseinf.setEstdate(estdate);
				}
				
				eSmBaseinfoMapper.insertESmBaseinfo(eSmBaseinf);
			}
		}
	}

	//企业名称信息
	@Override
	public void insertENameInfo(List<ENameInfo> eNameInfo) {
		for (ENameInfo eNameInf : eNameInfo) {
			if(eNameInf != null) {
				
				String savedperto = eNameInf.getSavedperto();
				if(!StringUtils.isEmpty(savedperto)) {
					savedperto = DateRegexUtil.dateType(savedperto);
					eNameInf.setSavedperto(savedperto);
				}
				
				//apprdate返回的是数字字符串，所以需要转换
				String apprdate = eNameInf.getApprdate();
				
				if(!StringUtils.isEmpty(apprdate)) {
			    	apprdate = DateRegexUtil.dateType(apprdate);
					eNameInf.setApprdate(apprdate);
				}
				
				eNameInfoMapper.insertENameInfo(eNameInf);
			}
		}
	}

	//财务负责人信息
	@Override
	public void insertEFinLeader(List<EFinLeader> efinLeader) {
		for (EFinLeader eFinLead : efinLeader) {
			if(eFinLead != null) {
				eFinLeaderMapper.insertEfinLeader(eFinLead);
			}
		}
	}

	//投资人情况信息 
	@Override
	public void insertEImInvestment(List<EImInvestment> objList) {
		for (EImInvestment eImInvestment : objList) {
			if(eImInvestment != null) {
				eImInvestmentMapper.insertEImInvestment(eImInvestment);
			}
		}
	}

}
