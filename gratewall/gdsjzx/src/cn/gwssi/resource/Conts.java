package cn.gwssi.resource;

public class Conts {
	public static final int pageNum = 1000;
	
	/**
	 * 编码 GB2312
	 */
	public static final String GB2312 = "GB2312";
	/**
	 * 默认用户id
	 */
	public static final String DEFAULT_USERID = "sys";
	public static final String DEFAULT_USERNAME = "sys";

	/**
	 * 编码 UTF-8
	 */
	public static final String UTF8 = "UTF-8";
	/**
	 * 主页显示页大小
	 **/
	public static final int pageSize =10;	
	/**
	 * 主体检索库
	 */
	public static final String SELTABREG ="sczt";
	/**
	 * 12315检索库
	 */
	public static final String SELANJIAN ="12315";
	/**
	 * 年度报告库
	 */
	public static final String SELANNUAL ="Annual";
	
	/**
	 * 案件信息库
	 */
	public static final String SELCASEINFO ="caseInfo";
	/**
	 * 主体检索语句
	 */
	//public static final String SELREGINFO ="entname/10,regno/5,enttype,entstate,dom,addr,industryphy,industryco,regorg,opstate,opscope,lerep,postalcode,tel,opscoandform,localadm,insauth,oldregno,inv,cerno";
	//public static final String SELREGINFO ="entname/10,regno/5,uniscid,dom,addr,opscope,industryco,frxx,gjrxx,inv,bgsx,lerep";
	//public static final String SELREGINFO ="entname/10,regno/5,uniscid,industryco,frxx,gjrxx,inv,bgsx,lerep";
	public static final String SELREGINFO ="entname/10,regno/5,uniscid,dom,opscope,industryco,frxx,gjrxx,inv,bgsx,lerep";
	/**
	 * 主体检索结果显示
	 */
	public static final String SELREGSHOW ="entname;regno;uniscid;entstate;enttype;lerep;estdate;industryphy;industryco,regorg;inv;opstate;opscoandform;opscope;dom;bgsx";
	
	/**
	 * 12315检索结果显示
	 * "ajid,regdep,accregper,estDate,regtime,accsce,acctime,keyword,pname,ptel,enttype,marname,enttel,yieldly;vensp,mdsename";
	 */
	public static final String SELAJSHOW ="AJID,applidique,ACCREGPER,ACCSCE,KEYWORD,PNAME,PADDR,ENTADDR,REGDEP,INVOPT,MDSENAME";
	
	/**
	 * 年度报告显示
	 * "ajid,regdep,accregper,estDate,regtime,accsce,acctime,keyword,pname,ptel,enttype,marname,enttel,yieldly;vensp,mdsename";
	 */
	public static final String SELNDSHOW ="entName,regNo,entType,addr,busSt,invName,anCheYear";
	/**
	 * 案件信息显示
	 * "ajid,regdep,accregper,estDate,regtime,accsce,acctime,keyword,pname,ptel,enttype,marname,enttel,yieldly;vensp,mdsename";
	 */
	public static final String CASEINFOSHOW ="caseid,caseno,casesrcid,casetype,casename,casescedistrict,casespot,casetime,casereason,caseval,appprocedure,caseinternetsign,caseforsign,casestate,casefiauth,casefidate,exedate,exesort,unexereasort,caseresult,casedep,clocaserea,clocasedate,sourceflag,entname,entnameurl,url";
	/**
	 * 案件基本信息
	 */
	public static final String CASEINFO ="select a.caseid,a.caseno,a.casesrcid,(case a.casetype when '1' then '一般案件' when '2' then '简易案件' when '3' then '特殊案件' else '其他案件' end) casetype,a.casename,a.casescedistrict,a.casespot,STR_REPLACE(CONVERT(VARCHAR,a.casetime,111) ,'/','-') casetime,a.casereason,a.caseval,(case a.appprocedure when '1' then '一般案件' when '2' then '简易案件' when '3' then '特殊案件' else '其他案件' end) appprocedure,(case a.caseinternetsign when '1' then '是' else '否' end) caseinternetsign,(case a.caseforsign when '1' then '是' else '否' end) caseforsign,(case a.casestate when '0' then '销案' when '1' then '已结案' when '2' then '未结案' else '其他状态' end) casestate,a.casefiauth,STR_REPLACE(CONVERT(VARCHAR,a.casefidate,111),'/','-') casefidate,STR_REPLACE(CONVERT(VARCHAR,a.exedate,111) ,'/','-') exedate,a.exesort,a.unexereasort,(case a.caseresult when '1' then '受理' when '2' then '不予受理' when '3' then '告知' when '9' then '其它' else a.caseresult end) caseresult,a.casedep,a.clocaserea,STR_REPLACE(CONVERT(VARCHAR,a.clocasedate,111),'/','-') clocasedate,a.sourceflag,(case convert(datetime,a.timestamp) when null then convert(datetime,'1900-01-01 00:00:01') else convert(datetime,a.timestamp) end) timestamp,dbo.connactcaseobjname(a.caseid,a.casesrcid) entname,dbo.connactcaseobjurl(a.caseid,a.casesrcid) entnameurl,('caseid=' || a.caseid) url from t_aj_ajjbxx a";
	/**
	 * 市场主体基本信息便签sql
	 */
	//public static final String SCZTJBXXSQL ="select * from SCZTJBXX ";
	public static final String SCZTJBXXSQL="select a.scztjbxxid,a.pripid,a.entname,a.regno,"+
	"(select value from T_DM_QYLXDM where code= a.enttype) enttype,"+
    "(case  when a.regcap>0 then (substring(CONVERT(varchar(18),a.regcap),1,char_length(CONVERT(varchar(18),a.regcap))-2)||'(万元[人民币])') else null end) regcap,"+		
	//"substring(CONVERT(varchar(18),a.regcap),1,char_length(CONVERT(varchar(18),a.regcap))-2)||'(万元[人民币])' regcap,"+
	"(select value from T_DM_HYDM where code=a.IndustryPhy) IndustryPhy,"+
	"(CASE (select value from T_DM_HYDM where ltrim(rtrim(code))= ltrim(rtrim(a.industryco))) WHEN null THEN a.industryco ELSE (select value from T_DM_HYDM where ltrim(rtrim(code))=ltrim(rtrim(a.industryco))) END) industryco ,"+
	"(case when char_length(a.estdate)>10 then substring(a.estdate,1,char_length(a.estdate)-9) else a.estdate end) estdate,"+
	"(select value from T_DM_djgXjGdm where code=a.regorg) regorg,a.postalcode,a.tel,a.email,a.abuitemco,a.cbuitem,a.opscope,"+
	"substring(a.opfrom,1,char_length(a.opfrom)-9)　opfrom,substring(a.opto,1,char_length(a.opto)-9)　opto,"+
	"(select value from T_DM_GSGLJGDM where code=a.localadm) localadm,"+
	"(select value from T_DM_GSGLJGDM where code=a.aicid) aicid,"+
	"(select value from  T_DM_JYZTDM where code=a.opstate) opstate,"+
	"a.apprdate,"+ //substring(a.apprdate,1,char_length(a.apprdate)-9) 
	"a.insauth,a.opscoandform,substring(a.recdate,1,char_length(a.recdate)-9) recdate,a.oldregno,a.depincha,a.memo,a.bliccopynum,a.orgcode,(CASE (select value from T_DM_JJXZDM where ltrim(rtrim(code))=ltrim(rtrim(a.economicproperty))) WHEN null THEN a.economicproperty ELSE (select value from T_DM_JJXZDM where ltrim(rtrim(code))=ltrim(rtrim(a.economicproperty))) END) economicproperty ,a.ecotecdevzone,a.exenum,a.dom,"+
	"(select value from T_DM_ZZXSDM where code=a.domproright) domproright,a.exchangerate,a.fileid,"+
	"(select value from T_DM_WZCYDM where code=a.forcapindcode) forcapindcode,a.midpreindcode,a.foreigncertno,a.industryrangeoption,a.addr,"+
	"(select distinct value from T_DM_ZJYLBDM where code=a.mainFoodstuffLabel) mainFoodstuffLabel,a.lerep,a.timestamp,a.receptionunit,a.receptiontel,a.technicpersonnum,a.unemployment,a.parnum,a.empnum,"+
	"(select value from  T_DM_OLD_JYZTDM where code=a.servicestate) servicestate,"+
	"(case when b.ConGro>0 then ((substring(CONVERT(varchar(18),b.ConGro),1,char_length(CONVERT(varchar(18),b.ConGro))-2) || (select distinct  value from T_DM_BZDM WHERE CODE=b.ConGroCur))||'(万元)') else null end) congro,"+
	//"(substring(CONVERT(varchar(18),b.ConGro),1,char_length(CONVERT(varchar(18),b.ConGro))-2)||(select distinct  value from T_DM_BZDM WHERE CODE=b.ConGroCur))||'(万元)' congro," +
	"(select distinct  value from T_DM_BZDM WHERE CODE=b.ConGroCur) ConGroCur,"+
	"(case when b.RecCap>0 then ((substring(CONVERT(varchar(18),b.RecCap),1,char_length(CONVERT(varchar(18),b.RecCap))-2) || (select distinct  value from T_DM_BZDM WHERE CODE=b.RecCapCur))||'(万元)') else null end) reccap,"+
	//"(substring(CONVERT(varchar(18),b.RecCap),1,char_length(CONVERT(varchar(18),b.RecCap))-2)||(select distinct  value from T_DM_BZDM WHERE CODE=b.RecCapCur))||'(万元)' reccap," +
	"(select distinct  value from T_DM_BZDM WHERE CODE=b.RecCapCur) RecCapCur,"+
	"b.sourceflag,f.signlabel,m.MCJBXXID,m.NameDistrict,m.EntTra,m.NameInd,m.OrgForm,m.TradPiny,m.SavePerTo,m.nameRegHistoryInfoID,m.nameInputStyle from t_sczt_scztjbxx a left join t_sczt_scztbcxx b on (a.pripid=b.pripid AND a.sourceflag=b.sourceflag) left join (select "+ 
	"((CASE isAdCorp WHEN '1' THEN '广告企业;' ELSE null END)||(CASE isBrandPrintCorp WHEN '1' THEN '商标印制企业;' ELSE null END)||"+
	"(CASE isbroker WHEN '1' THEN '经纪人;' ELSE null END)||(CASE isChangeEntityType WHEN '1' THEN '改制;' ELSE null END)||"+
	"(CASE isChangeFromGTH WHEN '1' THEN '个体升级企业;' ELSE null END)||(CASE isCheckLicence WHEN '1' THEN '验照;' ELSE null END)||"+
	"(CASE isInvestCompany WHEN '1' THEN '投资性公司;' ELSE null END)||(CASE isRuralBroker WHEN '1' THEN '农村经纪人;' ELSE null END)||"+
	"(CASE isSecrecy WHEN '1' THEN '保密;' ELSE null END)||(CASE isSmallMicro WHEN '1' THEN '小薇;' ELSE null END)||"+
	"(CASE isStockMerger WHEN '1' THEN '股权并购;' ELSE null END)||(CASE isUrban WHEN '1' THEN '城镇;' ELSE null END)||"+
	"(CASE perilIndustry WHEN '1' THEN '高危行业;' ELSE null END)||(CASE stockPurchase WHEN '1' THEN 'A股并购;' ELSE null END)) signlabel,PRIPID,SOURCEFLAG from t_sczt_scztbjxx) f "+ 
	"on (a.pripid=f.pripid AND a.sourceflag=f.sourceflag) left join T_SCZT_MCJBXX m on "+
	"(a.pripid =m.pripid AND a.sourceflag=m.sourceflag)";
	
	//public static final String SCZTJBXXSQLGT ="select * from SCZTJBXXGT ";
	public static final String SCZTJBXXSQLGT ="select a.scztjbxxid,a.pripid,a.entname,a.regno,"+
	"(select value from T_DM_QYLXDM where code= a.enttype) enttype,"+
	"(case when a.regcap>0 then (substring(CONVERT(varchar(18),a.regcap),1,char_length(CONVERT(varchar(18),a.regcap))-2)||'(万元[人民币])') else null end) regcap,"+				
	//"substring(CONVERT(varchar(18),a.regcap),1,char_length(CONVERT(varchar(18),a.regcap))-2)||'(万元[人民币])'　regcap,"+
	"(select value from T_DM_HYDM where code=a.IndustryPhy) IndustryPhy,"+
	"(CASE (select value from T_DM_HYDM where ltrim(rtrim(code) )= ltrim(rtrim(a.industryco))) WHEN null THEN a.industryco ELSE (select value from T_DM_HYDM where ltrim(rtrim(code))=ltrim(rtrim(a.industryco))) END) industryco ,"+
	"(case when char_length(a.estdate)>10 then substring(a.estdate,1,char_length(a.estdate)-9) else a.estdate end) estdate,"+
	"(select value from T_DM_djgXjGdm where code=a.regorg) regorg,a.postalcode,a.tel,a.email,a.abuitemco,a.cbuitem,a.opscope,"+
	"substring(a.opfrom,1,char_length(a.opfrom)-9)　opfrom,substring(a.opto,1,char_length(a.opto)-9)　opto,"+
	"(select value from T_DM_GSGLJGDM where code=a.localadm) localadm,"+
	"(select value from T_DM_GSGLJGDM where code=a.aicid) aicid,"+
	"  (select value from  T_DM_JYZTDM where code=a.opstate) opstate,"+
	"a.apprdate,"+//substring(a.apprdate,1,char_length(a.apprdate)-9) 
	"a.insauth,a.opscoandform,substring(a.recdate,1,char_length(a.recdate)-9) recdate,a.oldregno,a.depincha,a.memo,a.bliccopynum,a.orgcode,(CASE (select value from T_DM_JJXZDM where ltrim(rtrim(code))=ltrim(rtrim(a.economicproperty))) WHEN null THEN a.economicproperty ELSE (select value from T_DM_JJXZDM where ltrim(rtrim(code))=ltrim(rtrim(a.economicproperty))) END) economicproperty ,a.ecotecdevzone,a.exenum,a.dom,"+
	"(select value from T_DM_ZZXSDM where code=a.domproright) domproright,a.exchangerate,a.fileid,"+
	"(select value from T_DM_WZCYDM where code=a.forcapindcode) forcapindcode,a.midpreindcode,a.foreigncertno,a.industryrangeoption,a.addr,(select distinct value from T_DM_ZJYLBDM where code=a.mainFoodstuffLabel) mainFoodstuffLabel,a.lerep,a.timestamp,a.receptionunit,a.receptiontel,a.technicpersonnum,a.unemployment,a.parnum,a.empnum,"+
	"(select value from  T_DM_OLD_JYZTDM where code=a.servicestate) servicestate,"+
	"(case when b.ConGro>0 then ((substring(CONVERT(varchar(18),b.ConGro),1,char_length(CONVERT(varchar(18),b.ConGro))-2) || (select distinct  value from T_DM_BZDM WHERE CODE=b.ConGroCur))||'(万元)') else null end) congro,"+
	//"(substring(CONVERT(varchar(18),b.ConGro),1,char_length(CONVERT(varchar(18),b.ConGro))-2)||(select distinct  value from T_DM_BZDM WHERE CODE=b.ConGroCur))||'(万元)' congro," +
	"((select distinct  value from T_DM_BZDM WHERE CODE=b.ConGroCur)) ConGroCur,"+
	"(case when b.RecCap>0 then ((substring(CONVERT(varchar(18),b.RecCap),1,char_length(CONVERT(varchar(18),b.RecCap))-2) || (select distinct  value from T_DM_BZDM WHERE CODE=b.RecCapCur))||'(万元)') else null end) reccap,"+
	//"(substring(CONVERT(varchar(18),b.RecCap),1,char_length(CONVERT(varchar(18),b.RecCap))-2) || (select distinct  value from T_DM_BZDM WHERE CODE=b.RecCapCur))||'(万元)' reccap," +
	"(select distinct  value from T_DM_BZDM WHERE CODE=b.RecCapCur) RecCapCur,b.sourceflag,f.signlabel,m.MCJBXXID,m.NameDistrict,m.EntTra,m.NameInd,m.OrgForm,m.TradPiny,m.SavePerTo,m.nameRegHistoryInfoID,m.nameInputStyle from t_sczt_scztjbxx a left join t_sczt_scztbcxx b on (a.pripid=b.pripid AND a.sourceflag=b.sourceflag) left join (select "+ 
	"((CASE isAdCorp WHEN '1' THEN '广告企业;' ELSE null END)||(CASE isBrandPrintCorp WHEN '1' THEN '商标印制企业;' ELSE null END)||"+
	"(CASE isbroker WHEN '1' THEN '经纪人;' ELSE null END)||(CASE isChangeEntityType WHEN '1' THEN '改制;' ELSE null END)||"+
	"(CASE isChangeFromGTH WHEN '1' THEN '个体升级企业;' ELSE null END)||(CASE isCheckLicence WHEN '1' THEN '验照;' ELSE null END)||"+
	"(CASE isInvestCompany WHEN '1' THEN '投资性公司;' ELSE null END)||(CASE isRuralBroker WHEN '1' THEN '农村经纪人;' ELSE null END)||"+
	"(CASE isSecrecy WHEN '1' THEN '保密;' ELSE null END)||(CASE  isSmallMicro WHEN '1' THEN '小薇;' ELSE null END)||"+
	"(CASE isStockMerger WHEN '1' THEN '股权并购;' ELSE null END)||(CASE isUrban WHEN '1' THEN '城镇;' ELSE null END)||"+
	"(CASE perilIndustry WHEN '1' THEN '高危行业;' ELSE null END)||(CASE stockPurchase WHEN '1' THEN 'A股并购;' ELSE null END)) signlabel,PRIPID,SOURCEFLAG from t_sczt_scztbjxx) f "+ 
	"on (a.pripid=f.pripid AND a.sourceflag=f.sourceflag) left join T_SCZT_MCJBXX m on "+
	"(a.pripid =m.pripid AND a.sourceflag=m.sourceflag)";
	
	//public static final String SCZTJBXXSQLWZ ="select * from SCZTJBXXWZ ";
	public static final String SCZTJBXXSQLWZ ="select a.enttype type,a.scztjbxxid,a.pripid,a.entname,a.regno,"+
	"(select value from T_DM_QYLXDM where code= a.enttype) enttype," +
	"(case when a.regcap>0 then (substring(CONVERT(varchar(18),a.regcap),1,char_length(CONVERT(varchar(18),a.regcap))-2)||'(万元[人民币])') else null end) regcap,"+
	//"substring(CONVERT(varchar(18),a.regcap),1,char_length(CONVERT(varchar(18),a.regcap))-2)||'(万元[人民币])' regcap,"+
	"(select value from T_DM_HYDM where code=a.IndustryPhy) IndustryPhy,"+
	"(CASE (select value from T_DM_HYDM where ltrim(rtrim(code))=ltrim(rtrim(a.industryco)))  WHEN null THEN a.industryco ELSE (select value from T_DM_HYDM where ltrim(rtrim(code))=ltrim(rtrim(a.industryco))) END) industryco ,(case when char_length(a.estdate)>10 then substring(a.estdate,1,char_length(a.estdate)-9) else a.estdate end) estdate,"+
	"(select value from T_DM_djgXjGdm where code=a.regorg) regorg,a.postalcode,a.tel,a.email,a.abuitemco,a.cbuitem,a.opscope,a.opfrom,a.opto,"+
	"(select value from T_DM_GSGLJGDM where code=a.localadm) localadm,"+
	"(select value from T_DM_GSGLJGDM where code=a.aicid) aicid,"+
	"(select value from T_DM_JYZTDM where code=a.opstate) opstate,a.apprdate,a.insauth,a.opscoandform,substring(a.recdate,1,char_length(a.recdate)-9) recdate,a.oldregno,a.depincha,a.memo,a.bliccopynum,a.orgcode,(CASE (select value from T_DM_JJXZDM where ltrim(rtrim(code))=ltrim(rtrim(a.economicproperty)))  WHEN null THEN a.economicproperty ELSE (select value from T_DM_JJXZDM where ltrim(rtrim(code))=ltrim(rtrim(a.economicproperty))) END) economicproperty ,a.ecotecdevzone,a.exenum,a.dom,"+//substring(a.apprdate,1,char_length(a.apprdate)-9) 
	"(select value from T_DM_ZZXSDM where code=a.domproright) domproright,a.exchangerate,a.fileid,"+
	"(select value from T_DM_WZCYDM where code=a.forcapindcode) forcapindcode,a.midpreindcode,a.foreigncertno,a.industryrangeoption,a.addr,(select distinct value from T_DM_ZJYLBDM where code=a.mainFoodstuffLabel) mainFoodstuffLabel,a.lerep,a.timestamp,a.receptionunit,a.receptiontel,a.technicpersonnum,a.unemployment,a.parnum,a.empnum,"+
	"(select value from T_DM_OLD_JYZTDM where code=a.servicestate) servicestate," +
	"(case when b.ConGro>0 then ((substring(CONVERT(varchar(18),b.ConGro),1,char_length(CONVERT(varchar(18),b.ConGro))-2) || (select distinct value from T_DM_BZDM WHERE CODE=b.ConGroCur))||'(万元)') else null end) congro,"+
	//"(CONVERT(varchar(18),b.ConGro)||(select distinct  value from T_DM_BZDM WHERE CODE=b.ConGroCur))||'(万元)' congro," +
	"(select distinct  value from T_DM_BZDM WHERE CODE=b.ConGroCur) ConGroCur,b.ConGroUSD,b.RegCapRMB,b.RegCapUSD,b.RecCapUSD,b.RecCapRMB,b.DomeRegCapCur,b.DomeRegCapUSD,b.DomeRegCapInvProp,b.DomeRecCap,b.DomeRecCapCur,b.DomeRecCapUSD,b.DomeRecCapConProp,b.forregcap,b.ForRegCapCur,b.ForRegCapUSD,b.ForRegCapInvProp,b.ForRecCap,b.ForRecCapCur,b.ForRecCapUSD,b.ForRecCapConProp,b.DomeRegCap,b.InsForm,b.ChaMecDate," +
	"(case when b.RecCap>0 then ((substring(CONVERT(varchar(18),b.RecCap),1,char_length(CONVERT(varchar(18),b.RecCap))-2) || (select distinct value from T_DM_BZDM WHERE CODE=b.RecCapCur))||'(万元)') else null end) reccap,"+
	//"(CONVERT(varchar(18),b.RecCap) || (select distinct  value from T_DM_BZDM WHERE CODE=b.RecCapCur))||'(万元)' reccap," +
	"(select distinct  value from T_DM_BZDM WHERE CODE=b.RecCapCur) RecCapCur,d.WGQYSCJYHDJBXXID,d.OpeActType,d.ItemofOporCPro,d.ConOfContrPro,d.FundAm,d.Currency,d.FundAmUSD,d.FundAmRMB,d.ExaAuth,substring(d.SanDate,1,char_length(d.SanDate)-9) SanDate,d.ForEntName,d.Country,d.ForDom,d.ForRegECap,d.ForOpScope,d.lawAcceptPerson,d.operationArea"+
	",d.SOURCEFLAG,f.signlabel,m.MCJBXXID,m.NameDistrict,m.EntTra,m.NameInd,m.OrgForm,m.TradPiny,m.SavePerTo,m.nameRegHistoryInfoID,m.nameInputStyle"+
	" from t_sczt_scztjbxx a left join t_sczt_scztbcxx b on (a.pripid=b.pripid AND a.sourceflag=b.sourceflag) left join (select "+ 
	"((CASE isAdCorp WHEN '1' THEN '广告企业;' ELSE null END)||(CASE isBrandPrintCorp WHEN '1' THEN '商标印制企业;' ELSE null END)||"+
	"(CASE isbroker WHEN '1' THEN '经纪人;' ELSE null END)||(CASE isChangeEntityType WHEN '1' THEN '改制;' ELSE null END)||"+
	"(CASE isChangeFromGTH WHEN '1' THEN '个体升级企业;' ELSE null END)||(CASE isCheckLicence WHEN '1' THEN '验照;' ELSE null END)||"+
	"(CASE isInvestCompany WHEN '1' THEN '投资性公司;' ELSE null END)||(CASE isRuralBroker WHEN '1' THEN '农村经纪人;' ELSE null END)||"+
	"(CASE isSecrecy WHEN '1' THEN '保密;' ELSE null END)||(CASE isSmallMicro WHEN '1' THEN '小薇;' ELSE null END)||"+
	"(CASE isStockMerger WHEN '1' THEN '股权并购;' ELSE null END)||(CASE isUrban WHEN '1' THEN '城镇;' ELSE null END)||"+
	"(CASE perilIndustry WHEN '1' THEN '高危行业;' ELSE null END)||(CASE stockPurchase WHEN '1' THEN 'A股并购;' ELSE null END)) signlabel,PRIPID,SOURCEFLAG from t_sczt_scztbjxx) f on (a.pripid=f.pripid AND a.sourceflag=f.sourceflag) left join t_sczt_wgqyscjyhdjbxx d on (a.pripid=d.WGQYSCJYHDJBXXID AND a.sourceflag=d.sourceflag) left join T_SCZT_MCJBXX m on (a.pripid =m.pripid AND a.sourceflag=m.sourceflag) ";
	
	/**
	 * 市场主体标记信息
	 */
	public static final String T_SCZT_SCZTBJXX ="";
	
	/**
	 * 市场主体隶属信息/市场主体隶属信息补充信息
	 */
	//public static final String T_SCZT_SCZTLSXX ="select * from scztlsxx ";
	public static final String T_SCZT_SCZTLSXX ="select a.scztlsxxid,a.sourceflag,a.entname,a.regno,a.pripid,a.addr,a.domdistrict,a.regorg,a.opscoandform,a.country,a.entitycharacter,a.enterprisetype,a.foreignname,substring(a.estdate,1,char_length(a.estdate)-9) estdate,a.subordinaterelation,(case a.isbranch when '1' then '是' else '否' end) isbranch,(case a.isforeign when '1' then '是' else '否' end) isforeign,a.parcomid,substring(a.operbegindate,1,char_length(a.operbegindate)-9) operbegindate,substring(a.operenddate,1,char_length(a.operenddate)-9) operenddate,a.prilname,a.tel,a.timestamp,b.scztlsbcxxid,b.exchangerate,b.regcap,b.RegCapCur,b.regcapusd,b.regcaprmb,b.reccap from t_sczt_scztlsxx a left join t_sczt_scztlsbcxx b on (a.scztlsxxid=b.scztlsbcxxid and a.pripid=b.pripid and a.sourceflag=b.sourceflag)";
	 
	/**
	 * 迁入迁出信息
	 */
	//public static final String T_SCZT_QRQCXX ="select * from SCZTQRQCXX ";substring(mdate,1,char_length(mdate)-9) 
	public static final String T_SCZT_QRQCXX ="select letnum,area,rea,areregorg,mdate,archremovemode,historyinfoid,memo,status,qrqctype,pripid,sourceflag from (select minletnum as letnum,moutarea as area,minrea as rea,a.moutareregorg as areregorg,substring(mindate,1,char_length(mindate)-9) as mdate,a.archremovemode,historyinfoid,memo,minstatus as status,'迁入' qrqctype,pripid,sourceflag from t_sczt_qrxx a "+ 
			"union all "+
			"select moutletnum as letnum,minarea as area,moutrea as rea,a.minareregorg as areregorg,substring(moutdate,1,char_length(moutdate)-9) as mdate,a.archremovemode,historyinfoid,memo,moutstatus  as status,'迁出' qrqctype,pripid,sourceflag from t_sczt_qcxx a) w "; 
	//E:/MyEclipse10.0/Common/binary/com.sun.java.jdk.win32.x86_64_1.6.0.013/bin/javaw.exe

	/**
	 * 股权冻结信息
	 */
	public static final String T_SCZT_GQDJXX ="select a.GQDJXXID,a.FroAm,a.SharFroProp,a.FroAuth,a.FroFrom,a.FroTo,a.FroDocNO,a.ExeState,a.CorEntName,a.ThawAuth,a.ThawDocNO,substring(a.ThawDate,1,char_length(a.ThawDate)-9) ThawDate,a.frozsign,a.historyInfoID,a.PriPID,a.freezeItem,a.investInfo,a.thawGist,a.TIMESTAMP,a.invest from T_SCZT_GQDJXX a";
	
	/**
	 * 股权出质信息
	 */
	public static final String T_SCZT_GQCZXX ="select b.GQCZXXID,b.Pledgor,b.BLicType,b.BLicNO,b.CerType,b.CerNO,b.PledAmUnit,b.ImpAm,b.ImpOrg,b.ImpOrgTel,b.EntName,b.RegNO,substring(b.applyDate,1,char_length(b.applyDate)-9) applyDate,substring(b.approveDate,1,char_length(b.approveDate)-9) approveDate,b.regStatus,b.PriPID,b.RegOrg,b.stockRegisterNo,b.TIMESTAMP,b.historyInfoID,b.rescindOpinion,b.stockPledgeHostExclusiveID,b.ZQR_BLicType ZQRBLicType,b.ZQR_BLicNO ZQRBLicNO,b.ZQR_CerType ZQRCerType,b.ZQR_CerNO ZQRCerNO from T_SCZT_GQCZXX b ";
	
	/**
	 * 冻结股东信息
	 */
	public static final String T_SCZT_DJGDXX ="select a.FroAm,a.SharFroProp,a.FroAuth,a.FroFrom,a.FroTo,a.FroDocNO,a.ExeState,a.CorEntName,a.ThawAuth,a.ThawDocNO,substring(a.ThawDate,1,char_length(a.ThawDate)-9) ThawDate,a.FrozSign,a.exclusiveID,a.PersonID from T_SCZT_djgdXX a ";
	
	/**
	 * 证照信息
	 */
	public static final String T_SCZT_ZZXX ="select a.zzxxid,a.blicno,a.blictype,a.valfrom,a.valto,(case a.oricopsign when '2' then '副本' else '正本' end) oricopsign,(case a.blicstate when '' then '有效' when null then '有效' else '无效' end) blicstate,a.pripid,a.timestamp from t_sczt_zzxx a";
	
	/**
	 * 清算信息
	 */
	public static final String T_SCZT_QSXX ="select a.qsxxid,a.ligprincipal,a.liqmem,a.addr,a.tel,a.ligst,substring(a.ligenddate,1,char_length(a.ligenddate)-9)  ligenddate,a.debttranee,a.claimtranee,a.pripid,a.timestamp from t_sczt_qsxx a";
	
	/**
	 * 注吊销信息
	 */
	public static final String T_SCZT_ZDXXX ="select a.zdxxxid,a.pripid,a.canrea,a.sanauth,a.sandocno,substring(a.sandate,1,char_length(a.sandate)-9)  sandate,a.cancelbrsign,a.declebrsign,a.affwritno,a.blicrevorinum,a.blicrevorino,a.blicrevdupconum,a.carevst,a.pubnewsname,substring(a.pubdate,1,char_length(a.pubdate)-9) pubdate,a.candate,(case a.devicecancel when '' then '否' when null then '否' else '是' end) devicecancel,(case a.devicechange when '' then '否' when null then '否' else '是' end) devicechange,substring(a.handinsigndate,1,char_length(a.handinsigndate)-9)  handinsigndate,a.debtunit,a.bizsequence,a.handinlicenceperson,a.registercert,substring(a.reopendate,1,char_length(a.reopendate)-9)  reopendate,a.repcarcannum,a.repcert,a.sealdestorydesc,substring(a.shutoutbegindate,1,char_length(a.shutoutbegindate)-9)  shutoutbegindate,substring(a.shutoutenddate,1,char_length(a.shutoutenddate)-9)  shutoutenddate,a.takelicenceperson,a.timestamp from t_sczt_zdxxx a";
	
	/**
	 * 变更信息
	 */
	//public static final String T_SCZT_BGXX ="select a.bgxxid,(select value from T_DM_BGBASX where code = a.altitem) altitem,a.altbe,a.altaf,substring(a.altdate,1,char_length(a.altdate)-9)  altdate,a.alttime,a.content,a.oldhistoryinfoid,a.newhistoryinfoid,a.entityno,substring(a.operatedate,1,char_length(a.operatedate)-9)  operatedate,a.operator,(case a.modifystatus when '1' then '有效' else '无效' end) modifystatus,a.timestamp from t_sczt_bgxx a ";
	public static final String T_SCZT_BGXX ="select a.bgxxid,a.altitem,a.altbe,a.altaf,convert(varchar,a.altdate,111) altdate,a.alttime,a.content,a.oldhistoryinfoid,a.newhistoryinfoid,a.entityno,convert(varchar,a.operatedate,111) operatedate,a.operator,(case a.modifystatus when '1' then '有效' else '无效' end) modifystatus,a.timestamp from t_sczt_bgxx a ";
	
	/**
	 * 归档信息
	 */
	public static final String T_SCZT_GDXX ="select a.gdxxid,a.archno,a.archivist,substring(a.pigdate,1,char_length(a.pigdate)-9) pigdate,a.pripid,a.timestamp from t_sczt_gdxx";
	
	/**
	 * 名称基本信息/证照信息
	 */
	public static final String T_SCZT_MCJBXX ="select a.mcjbxxid,a.pripid,a.entname,a.namedistrict,a.enttra,a.nameind,a.orgform,a.tradpiny,a.saveperto,a.namereghistoryinfoid,a.nameinputstyle,a.timestamp from t_sczt_mcjbxx a";
	
	/**
	 * 高级成员信息(T_SCZT_GJCYXX)/人员基本信息(T_SCZT_RYJBXX)/人员其他证件信息(T_SCZT_RYQTZJXX)
	 */
	//public static final String T_SCZT_RYXX="select * from SCZTRYXX ";
	public static final String T_SCZT_RYXX="select a.SOURCEFLAG,a.gjcyxxid,a.pripid,a.appounit,a.hrdirector,substring(a.hrsigndate,1,char_length(a.hrsigndate)-9) hrsigndate,a.historyinfoid,a.position,a.cnposition,a.personid,"+
	"a.posbrform,a.repcarfrom,a.repcarno,a.repcarto,a.vipsort,b.addr,"+
	"b.sex,substring(b.natdate,1,char_length(b.natdate)-9) natdate,"+
	"b.certype,b.cerno,"+
	"b.country,b.tel,substring(b.cerissdate,1,char_length(b.cerissdate)-9) cerissdate,b.cervalper,substring(b.certapprovedate,1,char_length(b.certapprovedate)-9) certapprovedate,b.certapproveno,b.certapproveorg,b.certdept, b.polstand,b.house,b.postalcode,"+
	"b.litedeg,b.enname,"+
	"b.nation,b.nativeplace,b.name,substring(c.dateofarrvalinchina,1,char_length(c.dateofarrvalinchina)-9) dateofarrvalinchina,c.hkcertid,substring(c.hkcertidsigndate,1,char_length(c.hkcertidsigndate)-9) hkcertidsigndate,substring(c.hkcertidvaliddate,1,char_length(c.hkcertidvaliddate)-9)  hkcertidvaliddate,c.homeplace,c.homepostcode,c.hometelephone,c.passescertid,substring(c.passescertidsigndate,1,char_length(c.passescertidsigndate)-9) passescertidsigndate,substring(c.passescertidvaliddate,1,char_length(c.passescertidvaliddate)-9) passescertidvaliddate,c.rehomecertid,substring(c.rehomecertidsigndate,1,char_length(c.rehomecertidsigndate)-9)  rehomecertidsigndate,substring(c.rehomecertidvaliddate,1,char_length(c.rehomecertidvaliddate)-9) rehomecertidvaliddate,substring(c.residenceperiod,1,char_length(c.residenceperiod)-9) residenceperiod,c.residentlocation,d.entitylegalrepresentative,d.lerep,a.timestamp,(case  d.fddbrid  when null then '否'  else '是' end) islerep from t_sczt_gjcyxx a left join t_sczt_ryjbxx b on (a.personid = b.ryjbxxid and a.SOURCEFLAG=b.SOURCEFLAG) left join t_sczt_ryqtzjxx c on (c.ryqtzjxxid=b.ryjbxxid and c.SOURCEFLAG=b.SOURCEFLAG) left join t_sczt_fddbr d on (d  .personid=b.ryjbxxid and d.pripid= a.pripid and d.SOURCEFLAG=b.SOURCEFLAG) ";
	
	/**
	 * 投资人及出资信息(T_SCZT_TZRJCZXX)/投资人及出资其他信息(T_SCZT_TZRJCZQTXX) pripid
	 */
	//public static final String T_SCZT_TZRJCZXX ="SELECT * from SCZTTZRCZXX ";
	public static final String T_SCZT_TZRJCZXX ="select distinct" +
			" pripid,tzrjczxxid,invid,inv,invtype,(select value from t_dm_zjlxdm where code = w.certype) certype,"+
	"cerno,blictype,blicno,subconam,acconam,conprop,conform,baldelper,conam,regno,grpmemtype,"+
	"(select value from t_dm_bzdm where code = w.currency) currency,"+
	"verifiorg,acconbegdate,sourceflag,tzrjczqtxxid,(case isoriginateperson when '1' then '是' else '否' end) isoriginateperson,isfarmer,dom,biz_historyinfoid,cooperatecondition,"+
	"(select value from t_dm_gjdqdm where code = w.country) country,exchangerate,exclusiveid,(case isexecutor when '1' then '是' else '否' end) isexecutor,excutor,fronum,historyinfoid,beforefronum,subcondata,(case isofficiateaffair when '1' then '是' else '否' end) isofficiateaffair,(case isouter when '1' then '是' else '否' end) isouter,(case isselectsettlemonth when '1' then '是' else '否' end) isselectsettlemonth,lerep,regtype,stocknum,takedutytype,tzrtype,timestamp from"+ 
	"(select a.pripid,a.tzrjczxxid,a.invid,a.inv,a.invtype,a.certype,a.cerno,a.blictype,a.blicno,a.subconam,a.acconam,a.conprop,a.conform,a.baldelper,a.conam,a.regno,a.grpmemtype,a.currency,a.verifiorg,substring(a.acconbegdate,1,char_length(a.acconbegdate)-9) acconbegdate,a.timestamp,a.sourceflag,b.tzrjczqtxxid,b.isoriginateperson,b.isfarmer,b.dom,b.biz_historyinfoid,b.cooperatecondition,b.country,b.exchangerate,b.exclusiveid,b.isexecutor,b.excutor,b.fronum,b.historyinfoid,b.beforefronum,b.subcondata,b.isofficiateaffair,b.isouter,b.isselectsettlemonth,b.lerep,b.regtype,b.stocknum,b.takedutytype,'法人' tzrtype from t_sczt_frtzrjczxx a left join t_sczt_frtzrjczqtxx b on a.tzrjczxxid=b.tzrjczqtxxid "+ 
	"union all "+
	"select c.pripid,c.tzrjczxxid,c.invid,c.inv,c.invtype,c.certype,c.cerno,c.blictype,c.blicno,c.subconam,c.acconam,c.conprop,c.conform,c.baldelper,c.conam,c.regno,c.grpmemtype,c.currency,c.verifiorg,substring(c.acconbegdate,1,char_length(c.acconbegdate)-9) acconbegdate,c.timestamp,c.sourceflag,d.tzrjczqtxxid,d.isoriginateperson,d.isfarmer,d.dom,d.biz_historyinfoid,d.cooperatecondition,d.country,d.exchangerate,d.exclusiveid,d.isexecutor,d.excutor,d.fronum,d.historyinfoid,d.beforefronum,d.subcondata,d.isofficiateaffair,d.isouter,d.isselectsettlemonth,d.lerep,d.regtype,d.stocknum,d.takedutytype,'自然人' tzrtype from t_sczt_zrrtzrjczxx c left   join t_sczt_zrrtzrjczqtxx d on c.tzrjczxxid=d.tzrjczqtxxid) w ";
	
	
	public static final String T_SCZT_FRTZRJCZXX ="select a.pripid,a.tzrjczxxid,a.invid,a.inv,a.invtype,a.certype ,a.cerno,a.blictype,a.blicno,a.subconam,a.acconam,a.conprop,a.conform,a.baldelper,a.conam,a.regno,a.grpmemtype,a.currency,a.verifiorg,substring(a.acconbegdate,1,char_length(a.acconbegdate)-9) acconbegdate,a.timestamp,a.sourceflag,b.tzrjczqtxxid,(case b.isoriginateperson when '1' then '是' else '否' end) isoriginateperson,b.isfarmer,b.dom,b.biz_historyinfoid,b.cooperatecondition,b.country,b.exchangerate,b.exclusiveid,(case b.isexecutor when '1' then '是' else '否' end) isexecutor,b.excutor,b.fronum,b.historyinfoid,b.beforefronum,b.subcondata,(case b.isofficiateaffair when '1' then '是' else '否' end) isofficiateaffair,(case b.isouter when '1' then '是' else '否' end) isouter,(case b.isselectsettlemonth when '1' then '是' else '否' end) isselectsettlemonth,b.lerep,b.regtype,b.stocknum,b.takedutytype,'法人' tzrtype from t_sczt_frtzrjczxx a left join t_sczt_frtzrjczqtxx b on a.tzrjczxxid=b.tzrjczqtxxid "; 
	public static final String T_SCZT_ZRTZRJCZXX ="select c.pripid,c.tzrjczxxid,c.invid,c.inv,c.invtype,c.certype,c.cerno,c.blictype,c.blicno,c.subconam,c.acconam,c.conprop,c.conform,c.baldelper,c.conam,c.regno,c.grpmemtype,c.currency,c.verifiorg,substring(c.acconbegdate,1,char_length(c.acconbegdate)-9) acconbegdate,c.timestamp,c.sourceflag,d.tzrjczqtxxid,(case d.isoriginateperson when '1' then '是' else '否' end) isoriginateperson,d.isfarmer,d.dom,d.biz_historyinfoid,d.cooperatecondition,d.country,d.exchangerate,d.exclusiveid,(case d.isexecutor when '1' then '是' else '否' end) isexecutor,d.excutor,d.fronum,d.historyinfoid,d.beforefronum,d.subcondata,(case d.isofficiateaffair when '1' then '是' else '否' end) isofficiateaffair,(case d.isouter when '1' then '是' else '否' end) isouter,(case d.isselectsettlemonth when '1' then '是' else '否' end) isselectsettlemonth,d.lerep,d.regtype,d.stocknum,d.takedutytype,'自然人' tzrtype from t_sczt_zrrtzrjczxx c left join t_sczt_zrrtzrjczqtxx d on c.tzrjczxxid=d.tzrjczqtxxid "; 

	public static final String T_SCZT_SCZTJBXX ="";
	
	/**
	 * 迁入信息
	 */
	public static final String T_SCZT_QRXX ="select a.qrxxid,a.minletnum,a.moutarea,a.minrea,a.moutareregorg,substring(a.mindate,1,char_length(a.mindate)-9) mindate,a.archremovemode,a.pripid,a.historyinfoid,a.memo,a.minstatus,a.timestamp from t_sczt_qrxx a";
	
	/**
	 * 迁出信息
	 */
	public static final String T_SCZT_QCXX ="select a.qcxxid,a.moutletnum,a.minarea,a.moutrea,a.minareregorg,substring(a.moutdate,1,char_length(a.moutdate)-9) moutdate,a.archremovemode,a.pripid,a.historyinfoid,a.memo,a.moutstatus,a.timestamp from t_sczt_qcxx a";
	
	/**
	 * 待办事宜(T_SCZT_DBSY)/待办附加信息(T_SCZT_DBFJXX)（不显示）
	 */
	public static final String T_SCZT_DBSY ="";
	
	/**
	 * 出资业务信息(不用)
	 */
	public static final String T_SCZT_CZYWXX ="";
	
	/**
	 * 业务环节信息(不用)
	 */
	public static final String T_SCZT_YWHJXX ="";
	
	/**
	 * 法定代表人
	 */
	public static final String T_SCZT_FDDBR ="select a.entityLegalRepresentative,a.AppoUnit,a.historyInfoID,a.personID,a.LeRep from T_SCZT_fddbr a ";
	
	/**
	 * 统计基本信息
	 */
	public static final String T_SCZT_COUNT = "select count(scztjbxxid) countsum from t_sczt_scztjbxx";
	
	/**
	 * 日誌表
	 */
	public static final String T_PT_SYS_LOG="select a.logid,a.logtype,a.operatetime,a.ip,a.sourceplatform,a.userid,a.username,a.orgcode,a.orgname,a.starttime,a.endtime,a.content,a.function,a.url,a.errcode,a.errdesc,a.result,a.req,a.res,a.servicename,a.servicecontent,a.serviceobject,a.servicetype,a.servicestate,a.times,a.isfalg,a.runstate from t_pt_sys_log a";
	
	/**
	 * 共享日誌表
	 */
	public static final String T_PT_FWRZJBXX="select fwrzjbId,callerName,callerTime,executeCase,executeWay,executeTime,callerParameter,executeResult,callerIP,executeType,callerenttime,calleer,fwmc from T_PT_FWRZJBXX ";
	
	/**
	 * 共享日誌详细表
	 */
	public static final String T_PT_FWRZXXXX="select fwrzxxId,fwrzjbId,detail,time,executeContent,startTime,endTime,obj,code from T_PT_FWRZXXXX ";
	
	/**
	 * 共享资源
	 * */
	public static final String T_SHARE_RESOURCE="SELECT a.rsid,a.tablename,a.theme,a.tabletype,a.description,a.chname,a.datasum,a.state,a.TIMESTAMP from t_share_resource a ";
	
	
	/**
	 * 共享资源
	 * */
	public static final String T_SHARE_RESOURCE1="select * from t_pt_gxzytxx a left join t_pt_gxzylxx b on a.gxzyid=b.tablepkid ";
	
	
	
	/**
	 * 共享主题
	 * */
	public static final String T_PT_GXZYTXX_TITLE="SELECT distinct a.subject code,a.subject from T_PT_GXZYTXX a ";

	/**
	 * 共享表
	 * */
	public static final String T_PT_GXZYTXX="SELECT * from T_PT_GXZYTXX a ";

	//public static final String T_PT_GXZYTXX="SELECT a.TableName,a.Tablecode from T_PT_GXZYTXX a ";
	/**
	 * 共享列
	 * */
	public static final String T_PT_GXZYLXX="SELECT a.columnName,a.columncode,a.fieldtype from T_PT_GXZYLXX a ";

	public static final String T_PT_GXZYLXX1="SELECT * from T_PT_GXZYLXX a ";

	/**
	 * 年度报表基本信息
	 * */
	public static final String NDBGJBXX="select a.ancheid,a.pripid,convert(varchar,a.anchedate,111) anchedate,a.ancheyear,a.regno,a.uniscid"+
			",a.entname,(select value from T_DM_QYLXDM where code= a.enttype) enttype,a.oldenttype,a.tel,a.addr,a.postalcode,a.email,(case a.busst when '1' then '开业' when '4' then '歇业' when '5' then '清算 ' end) busst"+
			",a.busst_cn,a.empnum,a.empnumdis,(case a.antype when '10' then '公司-非私营' when '11' then '公司-私营' when '20' then '非公司企业法人、合伙等-非私营' when '21' then '非公司企业法人、合伙等-私营' when '30' then '分支机构、从事生产经营企业-非私营' when '31' then '分支机构、从事生产经营企业-私营' end) antype,a.colgranum,a.colemplnum,a.retsolnum,a.retemplnum"+
			",a.dispernum,a.disemplnum,a.unenum,a.uneemplnum,a.dependententname," +
			"(case  when a.assgro>0 then (substring(CONVERT(varchar(18),a.assgro),1,char_length(CONVERT(varchar(18),a.assgro))-2)||'(万元[人民币])') else null end) assgro,a.assgrodis"+
			",(case  when a.liagro>0 then (substring(CONVERT(varchar(18),a.liagro),1,char_length(CONVERT(varchar(18),a.liagro))-2)||'(万元[人民币])') else null end) liagro,a.liagrodis," +
			"(case  when a.vendinc>0 then (substring(CONVERT(varchar(18),a.vendinc),1,char_length(CONVERT(varchar(18),a.vendinc))-2)||'(万元[人民币])') else null end)  vendinc,a.vendincdis,(case  when a.maibusinc>0 then (substring(CONVERT(varchar(18),a.maibusinc),1,char_length(CONVERT(varchar(18),a.maibusinc))-2)||'(万元[人民币])') else null end) maibusinc,a.maibusincdis," +
			"(case  when a.progro>0 then (substring(CONVERT(varchar(18),a.progro),1,char_length(CONVERT(varchar(18),a.progro))-2)||'(万元[人民币])') else null end) progro,a.progrodis"+
			",(case  when a.netinc>0 then (substring(CONVERT(varchar(18),a.netinc),1,char_length(CONVERT(varchar(18),a.netinc))-2)||'(万元[人民币])') else null end) netinc,a.netincdis," +
			"(case  when a.ratgro>0 then (substring(CONVERT(varchar(18),a.ratgro),1,char_length(CONVERT(varchar(18),a.ratgro))-2)||'(万元[人民币])') else null end) ratgro,a.ratgrodis," +
			"(case  when a.totequ>0 then (substring(CONVERT(varchar(18),a.totequ),1,char_length(CONVERT(varchar(18),a.totequ))-2)||'(万元[人民币])') else null end) totequ,a.totequdis,a.numparm," +
			"(case a.parins when '1' then '党委' when '2' then '党总支' when '3' then '党支部' else '未成立' end) parins,a.parins_cn,a.resparmsign,a.resparsecsign," +
			//转yyyy-mm-dd
			"substring(convert(char(8),getdate(),112),1,4)+'-'+substring(convert(char(8),a.lastupdatetime,112),5,2)+'-'+substring(convert(char(8),a.lastupdatetime,112),7,2) lastupdatetime," +
			"a.s_ext_nodenum,convert(varchar,a.s_ext_timestamp,111) s_ext_timestamp,a.sourceflag,a.timestamp"+
			",b.IndividualID,b.NAME,b.SCOPE,b.GRADUATESISSHOW,b.SOLDIERSISSHOW,b.DISABILITYISSHOW,b.UNEMPLOYMENTISSHOW"+
			",b.ANNREPFORM,b.FUNDAM,b.OUTPUTAMOUNT,b.OUTPUTISSHOW,b.SALESISSHOW,b.RETAILAMOUNT,b.RETAILISSHOW"+
			",b.MARKUPMODE,b.REPORTISSHOW,b.ENTNAMEISSAME,b.USERISSAME,b.MARKUPMODEISSAME,b.SCOPEISSAME,b.ADDRESSISSAME,"+
			"c.ancheid,c.farspeartname,c.anchedate canchedate,c.totalamount,c.memnum,c.annnewmemnum,c.annredmemnum,c.levelf,c.certification,c.priyeasales,c.priyeasalesdis"+
			",c.priyeaprofit,c.priyeaprofitdis,c.priyeasub,c.priyeasubdis,c.priyealoan,c.priyealoandis,c.regcapitalissame,c.farnum" +
			",dbo.fzjgfunc(a.pripid,a.ANCHEID) as fzjg "+
			"from T_NDBG_QYBSJBXX a left join T_NDBG_GTGSNDBB b ON a.priPid=b.pripid and a.ANCHEID=b.ANCHEID LEFT JOIN T_NDBG_NMZYHZSNDBB c ON a.priPid=c.pripid  and a.ANCHEID=c.ANCHEID";


	/**
	 * 年度出资
	 * */
	public static final String NDBGCZ="select a.INVID,a.PRIPID,a.ANCHEID,a.INVNAME,a.LISUBCONAM,convert(varchar,a.SUBCONDATE,111) SUBCONDATE,a.SUBCONFORM,a.SUBCONFORM_CN,a.LIACCONAM,convert(varchar,a.ACCONDATE,111) ACCONDATE,a.ACCONFORM,a.ACCONFORM_CN,a.TIMESTAMP from T_NDBG_GDJCZXX a left join T_NDBG_QYBSJBXX b on a.ANCHEID=b.ANCHEID and a.pripid=b.pripid ";
	

	/**
	 * 年度对外出资
	 * */
	public static final String NDBGDWCZ="select b.entname,b.regno,b.dom,b.lerep,b.tel from T_NDBG_DWTzXX a left join t_sczt_scztjbxx b on a.regno=b.regno ";
	
	/**
	 * 年度-行政许可
	 * */
	public static final String NDBGXZXK="select b.entname,b.regno,b.dom,b.lerep,b.tel from T_NDBG_DWTzXX a left join t_sczt_scztjbxx b on a.regno=b.regno ";

	/**
	 * 年度-隶属信息
	 * */
	public static final String NDBGLSXX="select a.BranchId,a.ANCHEID,a.pripid,a.FarSpeArtRegNO,a.BrName from t_NDBG_FZJG a left join T_NDBG_QYBSJBXX b on a.ANCHEID=b.ANCHEID and a.pripid=b.pripid ";

	/**
	 * 年度-网站信息
	 * */
	public static final String NDBGWZXX="select a.WebID,a.pripid,a.ANCHEID,a.WebType,a.WebSitName,a.Domain,a.webIp,a.webIcp from T_NDBG_QYYYWZHWD a left join T_NDBG_QYBSJBXX b on a.pripid=b.pripid  and a.ANCHEID=b.ANCHEID ";
	
	/**
	 * 年度-对外担保
	 * */
	public static final String NDBGDWDB="select a.GaID,a.entid,a.PRIPID,a.Mortgagor,a.NameIsShow,a.GaType,a.GaTypeIsShow,a.Rage,a.RageIsShow,a.PefPerFrom,a.PefPerTo,a.PefPerIsShow,a.More,a.PriClaSecKind,a.PriClaSecAm,a.Guaranperiod,a.SOURCEFLAG,a.TIMESTAMP from T_NDBG_DWDBXX a left join T_NDBG_QYBSJBXX b on a.PRIPID=b.pripid and  a.ENTID=b.ANCHEID ";

	/**
	 * 年度-变更信息
	 * */
	public static final String NDBGBGXX="select a.ANCHEID,a.PRIPID,a.StockId,a.Inv,a.TransAmPr,a.TransInv,a.TransAmAft,a.AltDate,a.SOURCEFLAG,a.TIMESTAMP from T_NDBG_gqbgxx a  left join T_NDBG_QYBSJBXX b on b.pripid=a.pripid AND a.ANCHEID =b.ANCHEID  ";
	
	/**
	 * 年度-党建信息
	 * */
	public static final String NDBGDJXX="select a.PartyId,a.ANCHEID,a.pripid,a.NumParM,a.NumParMIsShow,(case a.ParIns when '1' then '党委'  when '2' then '党总支'  when '3' then '党支部'  when '4' then '未成立' end) ParIns ,a.ParInsIsShow,(case a.ResParMSign when '1' then '是'  when '2' then '否' end) ResParMSign,a.ResParMSignIsShow,(case a.ResParSecSign when '1' then '是'  when '2' then '否' end) ResParSecSign,a.ResParSecSignIsShow,a.SOURCEFLAG,a.TIMESTAMP from T_NDBG_DJXX a  left join T_NDBG_QYBSJBXX b on b.pripid=a.pripid AND a.ANCHEID =b.ANCHEID";

	/**
	 * 年度-异常名录
	 * */
	public static final String T_JYYCML_QYGTNZ="select a.abnormalqiyeid,a.pripid,a.regno,a.uniscid,a.entname,a.lerep,a.abnormalid,a.specause,convert(varchar,a.abntime,111) abntime,a.remexcpres,convert(varchar,a.remdate,111) remdate,a.decorg,a.auditingfileno,a.reconsiderationorg,a.litigationorg,a.governmentorg,a.area_code,(case a.biaoji when '9100' then '企业' when '9200' then '农专' when '999' then '个体' else a.biaoji end) biaoji,a.sourceflag,a.timestamp from t_jyycml_qygtnz a";
	
	/**
	 * 初始化报表下拉框
	 * */
	public static final String INISEARCH="select  MEASURECDE,MEASURE,TYPE from T_COGNOS_CATEGORY ";

	/**
	 * 初始化 服务对象的区域值
	 * */
	public static final String AREASEARCH="select code,value from T_DM_XZQHDM ";
	/**
	 * 检索表达式
	 * */
	public static final String REGULAR_EXPRESSION="[+-/()<>\\[\\]@!&^=*]";
	
	/**
	 * 守重
	 */
	public static final String T_SCJG_SZJBXXZSB="select a.curcompactcreditid,a.bizsequence,a.bizcompactcreditid,a.comcrelevelid,a.comcreyear,a.corpname,a.registerno,a.entitytypeid,a.principal,a.address,a.postcode,a.phone,a.mobilephone,a.chargedep,a.economicproperty,a.regcapital,a.regcapitalcoin,a.employeenum,a.approvetypeid,a.entityurl,a.corporgcode,a.countrytaxno,a.regiontaxno,a.businessscope,a.issuedeptid,a.aicid,a.firstcomcreyear,a.continuecomcrenum,a.continuecomcreyear,a.approvedeptid,a.acceptdeptid,a.entityno,a.statusid,a.islocation,a.approvedate,a.applydate,a.othercredit,a.recommendunit,a.submitname,a.submitdate,(case a.iswebsubmit when 'y' then '是' else '否' end) iswebsubmit,(case a.isdomedal when '1' then '是' else '否' end) isdomedal,(case a.isrundomedal when '1' then '是' else '否' end) isrundomedal,a.sourceflag,a.timestamp from t_scjg_szjbxxzsb a";
	
	public static final String T_SCJG_HTQDLXQKZSB="select a.curcomexecutiondesid,a.curcompactcreditid,a.pretransfercount,a.pretransfermoney,a.cursigncount,a.cursignmoney,a.alreadyexecutecount,a.alreadyexecutecountpercent,a.alreadyexecutemoney,a.alreadyexecutemoneypercent,a.curexecutecount,a.curexecutecountpercent,a.curexecutemoney,a.curexecutemoneypercent,a.suddencomcount,a.suddencommoney,a.partnerbreakcomcount,a.partnerbreakcommoney,a.selfbreakcomcount,a.selfbreakcommoney,a.invalidcomcount,a.invalidcommoney,a.breakillegalcomcount,a.breakillegalcommoney,a.unchaincomcount,a.unchaincommoney,a.changecomcount,a.changecommoney,a.sourceflag,a.timestamp from t_scjg_htqdlxqkzsb a";
	
	public static final String T_SCJG_HTGLJGZSB="select a.curcommanageorgid,a.curcompactcreditid,a.orgname,a.principal,a.phone,a.mobilephone,a.managercount,a.fulltimecount,a.sidelinecount,a.sourceflag,a.timestamp from t_scjg_htgljgzsb a";
	
	public static final String T_SCJG_DYDJYWZS="select a.pledgeid,a.historyinfoid,a.bizsequence,a.morregcno,a.approvedate,a.status,a.isforeignplace,a.guaname,a.reason,a.totalvalue,a.owner,a.guades,a.priclaseckind,a.pefperto,a.scope,a.applydate,a.foreignregdep,a.supervisedeptid,a.contractno,a.totalnum,a.approvecontent,a.issuedeptid,a.aicid,a.suretymoney,a.bargaintype,a.unit,a.contractnotype,a.totalvaluecointype,a.totalvalueinrmb,a.suretymoneycointype,a.suretymoneyinrmb,a.mortgagor,a.contractname,a.bargainname,a.islimited,a.bargainno,a.expireshowmode,a.pefperform,a.otherbargaintypename,a.pmoney,a.creditbargaintype,a.remark,a.sourceflag,a.timestamp from t_scjg_dydjywzs a";
	public static final String T_SCJG_DYRQKZS="select a.pledgorid,a.pledgeid,a.regno,a.pripid,a.pledgorcodeid,a.mortgagor,a.addr,a.regdep,a.legrep,a.legrepsex,a.legrepcerid,a.legrepcon,a.repadd,a.agent,a.sex,a.agentcertid,a.agentphone,a.agentadd,a.agentcon,a.agentcerttype,a.legrepcerttype,a.sourceflag,a.timestamp from t_scjg_dyrqkzs a";
	public static final String T_SCJG_DYWQDZS="select a.pawnitemid,a.pledgeid,a.pawnname,a.brand,a.modelspec,a.type,a.leavefactorydate,a.workableyear,a.amount,a.pawnvalue,a.place,a.evaluativevalue,a.titledeedid,a.ownexpiredate,a.isforeignplace,a.foreignregdept,a.foreignregdeptid,a.managelimit,a.unit,a.remark,a.sourceflag,a.timestamp from t_scjg_dywqdzs a";
	public static final String T_SCJG_PMXX="select a.historyinfoid,a.bizsequence,a.auc,a.aucname,(CONVERT(varchar(18),a.aucdate,111)) aucdate,a.aucmasname,a.aucspot,CONVERT(varchar(18),a.pubdate,111) pubdate,a.pubme,CONVERT(varchar(18),a.unfdate,111) unfdate,a.itemshowaddress,(case a.isforeign when 'y' then '是' else '否' end) isforeign,a.barnum,a.turnover,a.aucregno,a.status,a.postbizsequence,a.contractsum,a.contractnum,a.supervisedeptid,a.auctioneercertid,a.auctionperiodno,a.postinfonum,CONVERT(varchar(18),a.approvedate,111) approvedate,CONVERT(varchar(18),a.itemshowenddate,111) itemshowenddate,a.issuedeptid,a.aicid,a.postapprovedate,(case a.hasmonitor when 'y' then '是' else '否' end) hasmonitor,a.entitytypeid,a.lastapprovedate,a.industrytype,a.auctioncorpregno,a.item,a.itemsource,(case a.iswebsubmit when 'y' then '是' else '否' end) iswebsubmit,a.requestid,a.aucpripid,a.attn,a.sourceflag,a.timestamp from t_scjg_pmxx a";
	public static final String T_SCJG_PMHBAXX="select a.afterauctionid,a.bizsequence,a.auctionid,a.postapprovedate,a.hasmonitor,a.barnum,a.bargro,a.apprdate,a.status,a.aicid,a.issuedeptid,a.list,a.untrademsg,a.strifenum,a.acceptnum,a.iswebsubmit,a.aucdate,a.aucpripid,a.requestid,a.createddate,a.attn,a.passnum,a.sourceflag,a.timestamp from t_scjg_pmhbaxx a";
	public static final String T_SCJG_PMHBAQQHZJG="select a.id,a.attn,a.consultingnumber,a.date,a.filenumber,a.accper,a.requestid,a.result,a.sector,a.totalfile,a.type,a.sourceflag,a.timestamp from t_scjg_pmhbaqqhzjg a";
	public static final String T_SCJG_PMQBAQQHZJG="select a.id,a.attn,a.consultingnumber,a.date,a.filenumber,a.accper,a.requestid,a.result,a.sector,a.totalfile,a.type,a.sourceflag,a.timestamp from t_scjg_pmqbaqqhzjg a";
	
	public static final String T_SCJG_DYRXX="select a.mortgagorid,a.pledgeid,a.pripid,a.mortgagor,a.regno,a.mortadd,a.mortcodeid,a.mortregdept,a.mortlegrep,a.mortlegrepsex,a.mortlegrepcertid,a.mortlegrepcon,a.mortlegrepadd,a.mortagent,a.mortagentsex,a.mortagentcertid,a.mortagentphone,a.mortagentadd,a.mortagentcon,a.mortlegrepcerttype,a.mortagentcerttype,a.mortentitytype,a.mortregdeptid,a.isforeign,a.mortproperty,a.applicantcername,a.mortcardidtype,a.mortcardid,a.sourceflag,a.timestamp from t_scjg_dyrxx a";
	public static final String T_SCJG_DYQRXX="select a.moreid,a.pledgeid,a.pripid,a.more,a.regno,a.moreadd,a.morecodeid,a.moreregdept,a.morelegrep,a.morelegrepsex,a.morelegrepcertid,a.morelegrepcon,a.morelegrepadd,a.moreagent,a.moreagentsex,a.moreagentcertid,a.moreagentphone,a.moreagentadd,a.moreagentcon,a.morelegrepcerttype,a.moreagentcerttype,a.moreentitytype,a.moreregdeptid,a.isforeign,a.moreproperty,a.applicantcername,a.morecardidtype,a.morecardid,a.sourceflag,a.timestamp from t_scjg_dyqrxx a";
	
	public static final String T_QTBM_XZXK="select a.appperinformationid,a.taskid,a.entno,a.regno,a.entname,a.orgcode,a.apppercardid,a.apppername,a.apppercontent,a.principal,CONVERT(varchar(18),a.appperstarttime,111) appperstarttime,CONVERT(varchar(18),a.appperendtime,111) appperendtime,a.releaseauthorykey,a.releaseauthoryvalue,a.entrypeople,a.entrytime,a.accessory,a.status from t_qtbm_xzxk a";
	public static final String T_QTBM_XZCF="select a.admpeninformationid,a.taskid,a.entno,a.regno,a.entname,a.principal,a.orgcode,a.admpenauthority,CONVERT(varchar(18),a.admpentime,111) admpentime,a.admpendocno,a.admpendecisiontime,a.violationbehvior,a.penalizebasis,a.penalizekind,a.admpenamout,a.admpenresult,a.admpenexecution,a.releaseauthorykey,a.releaseauthoryvalue,a.entrypeople,a.entrytime,a.accessory,a.status,a.appperauthory from t_qtbm_xzcf a";

	public static final String T_SB_DLRXX="select a.code,a.name,a.enname,a.agentaddr,a.agentenaddr,a.agentcontentname,a.agentcontentaddr,a.agentcontentzip,a.agentcontenttel,a.agentmobilenum,a.agentcontentfax,a.agentcontenteml,a.agenteconomytype,a.agenttype,a.agentcername,a.agentcerid,a.agentdistrict,a.agentstate from t_sb_dlrxx a"; 
	public static final String T_SB_GYRXX="select a.regno,a.coowercnname,a.coownercnaddr,a.coownerenname,a.coownerenaddr from t_sb_gyrxx a ";
	public static final String T_SB_SBSQRXX="select a.regno,a.regcnname,a.regcnaddr,a.regenname,a.regenaddr,a.regdistrict,a.intcls,a.country from t_sb_sbsqrxx a ";
	public static final String T_SB_SPXX="select a.regno,a.intcls,a.goodscode,a.similarcode,a.delsign,a.doodsseqnum,a.goodscnname from t_sb_spxx a";

	public static final String T_SB_SBJBXX="select a.regno,a.intcls,CONVERT(varchar(18),a.appdate,111) appdate,a.tmname,a.tmnametranslate,(case a.tmtype when 'P' then '普通' when 'J' then '集体' when 'Z' then '证明' when 'T' then '特殊' else a.tmtype end) tmtype,a.agentid,a.firstanncissue,CONVERT(varchar(18),a.firstanncdate,111) firstanncdate,a.reganncissue,CONVERT(varchar(18),a.rrganncdate,111) rrganncdate,CONVERT(varchar(18),a.propertystrdate,111) propertystrdate,CONVERT(varchar(18),a.propertyenddate,111) propertyenddate,a.tmcolourdesc,a.abandonpropertydesc,(case a.issolidtm when '1' then '是' else '否' end) issolidtm,(case a.issharetm when '1' then '是' else '否' end) issharetm,(case a.tmformtype when '0001' then '文字' when '0010' then '图形' when '0100' then '声音' when '1000' then '气味' else a.tmformtype end) tmformtype,a.landmarkinfo,a.coloursign,(case a.iswellknowntm when '1' then '驰名商标' when '2' then '著名商标' else a.iswellknowntm end) iswellknowntm from t_sb_sbjbxx a";
	public static final String LAOLAI="select a.infoactiontype,a.id,a.case_code casecode,a.iname,a.sex_name sexname,a.age,a.cardnum,a.buesinessentity,a.court_name courtname,a.area_id areaid,a.area_name areaname,a.party_type_name partytypename,a.gist_cid gistcid,a.gist_unit gistunit,a.performance,a.disreput_type_name disreputtypename,CONVERT(varchar(18),a.publish_date,111) publishdate,CONVERT(varchar(18),a.reg_date,111) regdate,a.performed_part performedpart,a.unperform_part unperformpart,a.s_ext_sequence sextsequence,(case a.s_ext_validflag when '1' then '是' else '否' end) sextvalidflag from laolai a ";
	
	public static final String LAOLAIDETAIL="select a.infoactiontype,a.id,a.case_code casecode,a.iname,a.sex_name sexname,a.age,a.cardnum,a.buesinessentity,a.court_name courtname,a.area_id areaid,a.area_name areaname,a.party_type_name partytypename,a.gist_cid gistcid,a.gist_unit gistunit,a.performance,a.disreput_type_name disreputtypename,a.publish_date publishdate,a.reg_date regdate,a.performed_part performedpart,a.unperform_part unperformpart,a.s_ext_sequence sextsequence,a.s_ext_validflag sextvalidflag,d.tp from laolai a"; 

	public static final String T_NZ_NZQYXX="select a.pripid,a.aicid,a.entname,a.regno,a.assureorg,a.opscope,a.enttype,a.country,a.locprov,a.quality_city,a.county,a.localadm,a.addr,a.postalcode,a.email,a.linkman,a.tel,a.fax,a.mobtel,(case a.agrcate when '17301000' then '农用机械、配件' when '17303000' then '肥料' when '17304000' then '农药' when '17305000' then '农用薄膜' else '种子' end) agrcate,a.categoryid,a.estdate,a.opfrom,a.opto,a.busst,a.flag,a.businessmode,a.leveltype,a.areaname,a.isfrombiz,(case a.chatype when '1' then '连锁企业（总部）' when '2' then '连锁店（分支）' when '3' then '加盟店（协议加入）' else '自营店' end) chatype,a.isformbiz,a.isdeleted,a.area_code,a.sourceflag,a.timestamp from t_nz_nzqyxx a";
	public static final String T_NZ_NZGXQYXX="select a.customerid,a.pripid,a.entname,a.customerno,a.corpname,a.farspeartregno,(case a.buyorsell when '1' then '供' when '2' then '销' else a.buyorsell end) buyorsell,a.shoadd,a.postalcode,a.email,a.linkman,a.tel,a.mobtel,(case a.busst when '1' then '开业' when '2' then '筹建' when '3' then '停业' when '4' then '歇业' when '5' then '清算' else '其他' end) busst,a.issync,a.lastupdatetime,a.isdeleted,a.sourceflag,a.timestamp from t_nz_nzgxqyxx a";
	public static final String T_NZ_NZQYSPXX="select a.merchandiseid,a.pripid,a.code,a.categoryid,a.mdsename,a.model,a.manent,a.prono,a.farspeartregno,a.tmname,a.entcode,(case a.busst when '1' then '开业' when '2' then '筹建' when '3' then '停业' when '4' then '歇业' when '5' then '清算' else '其他' end) busst,a.entname,a.regno,a.prospec,a.isbusiness,a.units1,a.units2,a.units3,a.flag,a.isedited,a.productlicenseno,a.issync,a.lastupdatetime,a.basemerchandiseid,a.isdeleted,a.sourceflag,a.timestamp from t_nz_nzqyspxx a";
	public static final String T_NZ_NZSPBZXX="select a.merchandiseid,a.categoryid,a.mdsename,a.model,a.manent,a.prono,a.farspeartregno,a.tmname,(case a.busst when '1' then '开业' when '2' then '筹建' when '3' then '停业' when '4' then '歇业' when '5' then '清算' else '其他' end) busst,(case a.infoori when '1' then '12315中心' when '2' then '一会两站' when '3' then '消费维权服务站' when '4' then '上级工商部门交办' when '5' then '本级工商其他业务部门' when '6' then '消费者协会' when '7' then '12345政府热线' when '8' then '110公安热线' when '9' then '人大' when '10' then '政协' else '其他部门' end) infoori,a.sourseid,a.iskey,a.productlicenseno,a.isdeleted,a.sourceflag,a.timestamp from t_nz_nzspbzxx a";
	public static final String T_NZ_CYGDB="select a.samplebillid,a.execid,a.basemerchandiseid,a.sampleno,a.samdate,a.samplenumber,a.pripid,a.confirmday,a.samplesite,a.sampleplace,(case a.affideg when '1' then '确认' when '2' then '可能' else a.affideg end) affideg,a.noticeday,(case a.noticetype when '1' then '电话' else '其他' end) noticetype,a.comment,a.lastnoticeday,a.linkman,a.tel,a.country,a.locprov,a.sampling_city,a.procomcounty,a.procomtown,a.production_telephone,a.procomfax,a.email,a.addr,a.execstandard,a.qualitygrade,a.production_date,a.productsno,a.categoryid,a.sampling_address,a.remark,a.organ,a.regno,a.production_contacts,(case a.amsreport when '1' then '是' else '否' end) amsreport,a.productmodel,a.product_name,a.brand_name,a.scprojectid,a.fikeyid,a.fsreceiver,a.istocase,a.isdeleted,a.sourceflag,a.timestamp from t_nz_cygdb a";
	public static final String T_NZ_JCBG="select a.checkreportid,a.checktype,a.inspection_time,a.inspection_agency,a.checkrepcode,a.sampleid,a.inspection_conclusion,a.disqualified_item_summary,a.picfile,a.sendingdate,a.signingdate,a.lastnoticeday,a.receiver,a.tel,(case a.confirmresult when '0' then '待确认中' when '1' then '认可' when '2' then '不认可' else a.confirmresult end) confirmresult,(case a.applyrecheck when '1' then '不申请' when '2' then '申请复验' when '3' then '申请复检' else a.applyrecheck end) applyrecheck,a.remark,(case a.busst when '1' then '开业' when '2' then '筹建' when '3' then '停业' when '4' then '歇业' when '5' then '清算' else '其他' end) busst,a.operpriname,a.fsprojectid,a.fcflag,a.completedate,a.standardjudge,a.accforstan,a.quaprojudge,a.determine,a.procomaffirm,a.checomaffirm,a.sampleno,a.sertime,a.checomapprecheck,a.range,a.checkstandard,a.amsreport,a.mssreport,a.organ,a.sourceflag,a.timestamp from t_nz_jcbg a";
	public static final String T_NZ_JCXMJG="select a.resultid,a.projectid,a.checkitemuuid,a.checkreportid,a.standard,a.meaunit,a.result,a.conclusion,a.checkitemname,a.sourceflag,a.timestamp from t_nz_jcxmjg a";
	
	public static final String T_AJ_AJJBXX="select a.caseid,a.caseno,a.casesrcid,(case a.casetype when '1' then '一般案件' when '2' then '简易案件' when '3' then '特殊案件' else '其他案件' end) casetype,a.casename,a.casescedistrict,a.casespot,STR_REPLACE(CONVERT(VARCHAR,a.casetime,111) ,'/','-') casetime,a.casereason,a.caseval,(case a.appprocedure when '1' then '一般案件' when '2' then '简易案件' when '3' then '特殊案件' else '其他案件' end)  appprocedure,(case a.caseinternetsign when '1' then '是' else '否' end) caseinternetsign,(case a.caseforsign when '1' then '是' else '否' end) caseforsign,(case a.casestate when '0' then '销案' when '1' then '已结案' when '2' then '未结案' else '其他状态' end) casestate,a.casefiauth,STR_REPLACE(CONVERT(VARCHAR,a.casefidate,111),'/','-') casefidate,STR_REPLACE(CONVERT(VARCHAR,a.exedate,111) ,'/','-') exedate,a.exesort,a.unexereasort,(case a.caseresult when '1' then '受理' when '2' then '不予受理' when '3' then '告知' when '9' then '其它' else a.caseresult end) caseresult,a.casedep,a.clocaserea,STR_REPLACE(CONVERT(VARCHAR,a.clocasedate,111),'/','-') clocasedate,a.sourceflag,a.timestamp from t_aj_ajjbxx a";
	public static final String T_AJ_WFXWJCFXX="select a.illegactid,a.caseid,a.illegacttype,a.illegact,a.illegincome,a.quabasis,a.penbasis,a.pentype,a.penresult,a.penam,a.forfam,a.apprcuram,a.pendecissdate,a.penexest,(case a.caseigdegree when '10' then '一般违法经营行为' when '20' then '严重违法行为' when '21' then '危害人体健康' when '22' then '存在重大安全隐患' when '23' then '威胁公共安全' when '24' then '破坏环境资源' else '其他' end) caseigdegree,(case a.lenity when '1' then '是' else '否' end) lenity,a.sertime,a.penauth,a.penauth_cn,a.pendecno,a.illegfact,a.sourceflag,a.timestamp from t_aj_wfxwjcfxx a";
	public static final String T_AJ_DSRXX="select a.casepartyid,a.caseid,a.partytype,a.name,a.regno,a.lerep,(case a.sex when '1' then '男' else '女' end) sex,a.age,a.occupation,a.dom,a.tel,a.postalcode,a.workunit,a.litedeg,a.certype,a.cerno,a.nation,a.enqtime,a.enttype,a.pripid,a.uniscid,a.lereppos,a.unitname,a.partytab,a.timestamp,a.sourceflag from t_aj_dsrxx a";
	public static final String T_AJ_XZCFJDS="select a.fijudgmentid,a.pendecno,a.caseno,a.fspartyname,a.lerep,a.postcode,a.tel,a.oploc,a.opscope,a.sex,a.age,a.occupation,a.cerno,a.litedeg,a.nation,a.polstand,a.fsnativeplace,a.casereason,a.illegfact,a.quabasis,a.penbasis,a.fsalarm,a.fschargestoplaw,a.fschargestopintrusion,a.ffseizureillegal,a.ffseizurenonlaw,a.ffseizureprofit,a.ffseizuresale,a.ffseizuredealbrow,a.penam,a.fskeeplicence,a.fsrevokelicence,a.fskeepshopcard,a.fsrevokeshopcard,a.fsstopproduction,a.fsaviso,a.fspricefixing,a.fspreemption,a.fsotherpunish,a.fstellright,a.fdrecorddate,a.fsrecorder,a.fdcreateupdatedate,a.pigsign,a.archno,a.fsdeductproperty,a.fsopencorrect,a.fsstopissue,a.fsseizureillegaltype,a.fsseizuresaletype,a.regno,a.area_code,a.timestamp,a.sourceflag from t_aj_xzcfjds a";
	
	public static final String T_GG_GGJYJBXX="select a.adid,a.pripid,a.adproprietor,(case a.adbuenttype when '1' then '广播电台' when '2' then '电视台' when '3' then '报社' when '4' then '出版社' when '5' then '期刊社' else '其他才' end) adbuenttype,(case a.adbuentchar when '1' then '国有企业' when '2' then '集体企业' when '3' then '私营企业' when '4' then '内资公司（非私营）' when '5' then '外商投资企业' when '6' then '个体工商户' when '7' then '事业单位' else '其他' end) adbuentchar,a.adope,a.tax,(case a.opetype when '1' then '设计' when '2' then '制作' when '3' then '代理' else '发布' end) opetype,a.adbuent,a.adopeorg,a.oploc,a.postalcode,a.adopfrom,a.adopto,a.adlicbu,a.regcap,a.maglicno,a.remark,a.adopeinc,a.forbusiadinc,a.netinc,a.deficit,a.sourceflag,a.timestamp from t_gg_ggjyjbxx a";
	public static final String T_GG_CYRYXX="select a.cyryid,(case a.adpratype when '10' then '管理人员' when '20' then '业务人员' when '30' then '创意设计人员' else '其他人员' end) adpratype,a.name,(case a.sex when '1' then '男性' else '女性' end) sex,a.natdate,a.edubac,a.specialty,a.cername,a.certype,a.cerno,a.graduateno,a.sourceflag,a.timestamp from t_gg_cyryxx a";
	public static final String T_GG_GGSCYGGZYRYXX="select a.id,a.name,(case a.sex when '1' then '男性' else '女性' end) sex,a.natdate,a.certype,a.cerno,a.edubac,a.specialty,a.wopos,a.culdate,a.culauth,a.culresult,a.sourceflag,a.timestamp from t_gg_ggscyggzyryxx a";
	public static final String T_GG_GGFZBSJGSLXX="select a.regno,a.brname,a.regorg,a.estdate,a.prilname,a.sourceflag,a.timestamp from t_gg_ggfzbsjgslxx a";
	public static final String T_GG_GGJGXZCSXX="select a.jgxzid,a.adhost,a.adproprietor,a.adissent,a.adname,a.admedia,a.medianame,a.gostype,a.adisstime,a.adisstimelen,a.adissarea,a.layout,a.spf,a.admmea,a.admonauth,a.setdate,a.sourceflag,a.timestamp from t_gg_ggjgxzcsxx a";
	
	
}											  