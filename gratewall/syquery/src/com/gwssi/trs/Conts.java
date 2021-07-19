package com.gwssi.trs;

public class Conts {
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
	//登记主题的检索
	//public static final String SELTABREG ="SZREG";
	public static final String SELTABREG ="reg";
	//案件主题的检索
	public static final String SELTCASE="caseTheme";
	//消保主题的检索
	public static final String SELTXIAO="XiaoBao";
	/**
	 * 主体检索语句
	 */
	//public static final String SELREGINFO ="entname/10,regno/5,enttype,entstate,dom,addr,industryphy,industryco,regorg,opstate,opscope,lerep,postalcode,tel,opscoandform,localadm,insauth,oldregno,inv,cerno";
	//登记主题
	public static final String SELREGINFO ="a/10,b/5,c,d,e,f,g";
	//案件主题
	public static final String SELRCASE="a/10,b/5,r";
	//消保主题
	public static final String SELRXIAO="a/10,b/5,c,d";
	/**
	 * 主体检索结果显示
	 */
	//登记主题
	public static final String SELREGSHOW ="a;b;c;d;e;f;g;h;i,j;k;l;m;n;r";
	//案件主题
	public static final String SELRCASESHOW="a;b;b_;c;d;d_;e;f;g;h;i;j;k";
	//消保主题
	public static final String SELRXIAOSHOW="a;b;c;d;e;f;g;h;i;j;k;l";
	/**
	 * 市场主体基本信息便签sql
	 */
	public static final String SCZTJBXXSQL ="select * from V_QY_BASIC " ;//内资视图 
	public static final String SCZTJBXXSQLGT ="select * from  V_QY_GT_BASIC ";//个体
	public static final String SCZTJBXXSQLWZ ="select * from V_QY_BASIC ";//外资视图
	public static final String SCZTJBXXSSQLJT ="select * from V_QY_JITUAN_BASIC ";//集团
	
	
	/**
	 * 个体信息查询
	 */
	public static final String V_GETI_JINYIN ="select  * from v_qy_gt_jinyin ";//个体经营者信息
	public static final String V_GETI_XUKE ="select  * from v_qy_geti_xuke_xx ";//个体许可信息
	public static final String V_GETI_ZHUXIAO ="select  * from v_qy_geti_zhuxiao_xx ";//个体注销信息	
	public static final String V_GETI_DIAOXIAO ="select  * from v_qy_geti_diaoxiao_xx ";//个体吊销信息	
	public static final String V_GETI_BIANGENG ="select  * from v_qy_gt_biangeng_xx ";//个体变更信息	
	
	/**
	 * 内资外资信息查询
	 */
	public static final String V_NZWZ_RENYUANXX="select * from v_qy_nzwz_renyuan_xinxi";//内资外资信息查询
	public static final String V_NZWZ_GUQUAN_DONGJIE="select * from v_qy_nzwz_guquan_dongjie";//内资外资股权冻结
	public static final String V_NZWZ_GUQUAN_CHUZHI="select * from v_qy_nzwz_guquan_chuzhi";//内资外资股权出质
	public static final String V_NZWZ_ZHUXIAOXX="select * from v_qy_nzwz_zhuxiaoxx";//注销信息
	public static final String V_NZWZ_DiaoXiaoXX="select * from v_qy_nzwz_diaoxiaoxx";//吊销信息
	public static final String V_NZWZ_XuKeXX="select * from v_qy_nzwz_xukexx";//许可信息
	public static final String V_NZWZ_QianYiXX="select * from v_qy_nzwz_qianru_qinachu_xx";//迁移信息
	public static final String V_NZWZ_QingsuanXX="select * from v_qy_nzwz_qingsuanxx";//清算信息
	public static final String V_NZWZ_QingsuanChengYuanXX="select * from v_qy_nzwz_qingsuanchengyuanxx";//清算信息
	public static final String V_NZWZ_CaiWuFuZeXX="select * from v_qy_nzwz_caiwu_fuzexx";//财务负责人信息
	public static final String V_NZWZ_LianLuoYuanXX="select * from v_qy_nzwz_lianluo_yuanxx";//联络员信息
	public static final String V_NZWZ_BianGengXX="select * from v_qy_nzwz_biangeng_xx";//内资外资变更信息	
	
	/**
	 * 集团成员信息 
	 */
	public static final String V_JITUAN_CHENGYUANXX="select * from v_qy_jituan_chengyuan_xx";//集团成员信息	
	public static final String V_JITUAN_ZhuXiaoXX="select * from v_qy_jituan_zhuxiao_xx";//集团注销信息	
	public static final String V_JITUAN_BianGengXX="select * from v_qy_jituan_biangeng_xx";//集团变更信息	
	
	/**
	 * 市场主体标记信息
	 */
	public static final String T_SCZT_SCZTBJXX ="";
	
	/**
	 * 市场主体隶属信息/市场主体隶属信息补充信息
	 */
	public static final String T_SCZT_SCZTLSXX ="select *  from v_qy_nzwz_lishu_xx t ";
	 
	/**
	 * 迁入迁出信息
	 */
	public static final String T_SCZT_QRQCXX ="select * from SCZTQRQCXX ";

	/**
	 * 股权冻结信息
	 */
	public static final String T_SCZT_GQDJXX ="select a.GQDJXXID,a.FroAm,a.SharFroProp,a.FroAuth,a.FroFrom,a.FroTo,a.FroDocNO,a.ExeState,a.CorEntName,a.ThawAuth,a.ThawDocNO,a.ThawDate,(select value from T_DM_DJZTDM where code=a.FrozSign) frozsign,a.historyInfoID,a.PriPID,a.freezeItem,a.investInfo,a.thawGist,a.TIMESTAMP,a.invest from T_SCZT_GQDJXX a";
	
	/**
	 * 股权出质信息
	 */
	public static final String T_SCZT_GQCZXX ="select b.GQCZXXID,b.Pledgor,b.BLicType,b.BLicNO,b.CerType,b.CerNO,b.PledAmUnit,b.ImpAm,b.ImpOrg,b.ImpOrgTel,b.EntName,b.RegNO,b.applyDate,b.approveDate,(select value from T_DM_OLD_JYZTDM where code=b.regStatus) regStatus,b.PriPID,(select value from T_DM_GSGLJDM where code=b.RegOrg) RegOrg,b.stockRegisterNo,b.TIMESTAMP,b.historyInfoID,b.rescindOpinion,b.stockPledgeHostExclusiveID,b.ZQR_BLicType ZQRBLicType,b.ZQR_BLicNO ZQRBLicNO,b.ZQR_CerType ZQRCerType,b.ZQR_CerNO ZQRCerNO from T_SCZT_GQCZXX b ";
	
	/**
	 * 冻结股东信息
	 */
	public static final String T_SCZT_DJGDXX ="select a.FroAm,a.SharFroProp,a.FroAuth,a.FroFrom,a.FroTo,a.FroDocNO,a.ExeState,a.CorEntName,a.ThawAuth,a.ThawDocNO,a.ThawDate,a.FrozSign,a.exclusiveID,a.PersonID from T_SCZT_djgdXX a ";
	
	/**
	 * 证照信息
	 */
	public static final String T_SCZT_ZZXX ="select a.zzxxid,a.blicno,(select value from T_DM_ZZLXDM where code=a.blictype) blictype,a.valfrom,a.valto,(case a.oricopsign when '2' then '副本' else '正本' end) oricopsign,(case a.blicstate when '' then '有效' when null then '有效' else '无效' end) blicstate,a.pripid,a.timestamp from t_sczt_zzxx a";
	
	/**
	 * 清算信息
	 */
	public static final String T_SCZT_QSXX ="select a.qsxxid,a.ligprincipal,a.liqmem,a.addr,a.tel,a.ligst,a.ligenddate,a.debttranee,a.claimtranee,a.pripid,a.timestamp from t_sczt_qsxx a";
	
	/**
	 * 注吊销信息
	 */
	public static final String T_SCZT_ZDXXX ="select a.zdxxxid,a.pripid,(select value from T_DM_ZXYYDM where code=a.canrea) canrea,a.sanauth,a.sandocno,a.sandate,a.cancelbrsign,a.declebrsign,a.affwritno,a.blicrevorinum,a.blicrevorino,a.blicrevdupconum,a.carevst,a.pubnewsname,a.pubdate,a.candate,(case a.devicecancel when '' then '否' when null then '否' else '是' end) devicecancel,(case a.devicechange when '' then '否' when null then '否' else '是' end) devicechange,a.handinsigndate,a.debtunit,a.bizsequence,a.handinlicenceperson,a.registercert,a.reopendate,a.repcarcannum,a.repcert,a.sealdestorydesc,a.shutoutbegindate,a.shutoutenddate,a.takelicenceperson,a.timestamp from t_sczt_zdxxx a";
	
	/**
	 * 变更信息
	 */
	public static final String T_SCZT_BGXX ="select a.bgxxid,(select value from T_DM_BGBASX where code = a.altitem) altitem,a.altbe,a.altaf,a.altdate,a.alttime,a.content,a.oldhistoryinfoid,a.newhistoryinfoid,a.entityno,a.operatedate,a.operator,(case a.modifystatus when '1' then '有效' else '无效' end) modifystatus,a.timestamp from t_sczt_bgxx a ";
	
	/**
	 * 归档信息
	 */
	public static final String T_SCZT_GDXX ="select a.gdxxid,a.archno,a.archivist,a.pigdate,a.pripid,a.timestamp from t_sczt_gdxx";
	
	/**
	 * 名称基本信息/证照信息
	 */
	public static final String T_SCZT_MCJBXX ="select a.mcjbxxid,a.pripid,a.entname,a.namedistrict,a.enttra,a.nameind,a.orgform,a.tradpiny,a.saveperto,a.namereghistoryinfoid,a.nameinputstyle,a.timestamp from t_sczt_mcjbxx a";
	
	/**
	 * 人员信息表
	 */
	public static final String T_SCZT_RYXX="select * from E_PRI_PERSON ";
	
	/**
	 * 企业出资信息
	 */
	public static final String T_SCZT_TZRJCZXX ="SELECT * from v_qy_chuzi_xinxi ";
	/**
	 * 迁入信息
	 */
	public static final String T_SCZT_QRXX ="select a.qrxxid,a.minletnum,a.moutarea,a.minrea,a.moutareregorg,a.mindate,a.archremovemode,a.pripid,a.historyinfoid,a.memo,a.minstatus,a.timestamp from t_sczt_qrxx a";
	
	/**
	 * 迁出信息
	 */
	public static final String T_SCZT_QCXX ="select a.qcxxid,a.moutletnum,a.minarea,a.moutrea,a.minareregorg,a.moutdate,a.archremovemode,a.pripid,a.historyinfoid,a.memo,a.moutstatus,a.timestamp from t_sczt_qcxx a";
	
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
}






