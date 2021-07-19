package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_ent_web_site_info]的数据对象类
 * @author Administrator
 *
 */
public class VoEbEntWebSiteInfo extends VoBase
{
	private static final long serialVersionUID = 201209241434510002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* 主键chr_id */
	public static final String ITEM_DOM_SUBST = "dom_subst" ;		/* 管辖分局 */
	public static final String ITEM_DOM_LOCAL_ADM = "dom_local_adm" ;	/* 管辖工商所 */
	public static final String ITEM_GRID_ID = "grid_id" ;			/* 网格ID */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* 企业id */
	public static final String ITEM_WEB_SITE = "web_site" ;			/* 网址 */
	public static final String ITEM_IP_AREA = "ip_area" ;			/* IP地域 */
	public static final String ITEM_IP_ADDRE = "ip_addre" ;			/* IP地址 */
	public static final String ITEM_IS_INVAL_LINK = "is_inval_link" ;	/* 是否无效链接 */
	public static final String ITEM_UPDATE_DATE = "update_date" ;	/* 时间戳（搜狐） */
	public static final String ITEM_INTRODUCTION = "introduction" ;	/* 内容简介 */
	public static final String ITEM_NEW_SIGN = "new_sign" ;			/* 新建标识 */
	public static final String ITEM_JUDGE_DETAIL = "judge_detail" ;	/* 判定内容 */
	public static final String ITEM_SITE_TYPE = "site_type" ;		/* 网站类型 */
	public static final String ITEM_CHINE_SITE_NAME = "chine_site_name" ;	/* 中文网站名称 */
	public static final String ITEM_PROXY_AD = "proxy_ad" ;			/* 代理广告 */
	public static final String ITEM_ISSUE_AD = "issue_ad" ;			/* 发布广告 */
	public static final String ITEM_SELF_AD = "self_ad" ;			/* 本公司宣传 */
	public static final String ITEM_IS_ILLEG_CLUES_FOUND = "is_illeg_clues_found" ;	/* 是否发现违法线索（0：否1：是） */
	public static final String ITEM_JUDGE_PERSON = "judge_person" ;	/* 判定人 */
	public static final String ITEM_JUDGE_DATE = "judge_date" ;		/* 判定时间 */
	public static final String ITEM_JUDGE_SIGN = "judge_sign" ;		/* 判定标识（0未初审，1已初审，2内容不匹配，3无效链接，4辖区异议，5退回） */
	public static final String ITEM_VIEW_COUNT = "view_count" ;		/* 浏览量 */
	public static final String ITEM_RISK = "risk" ;					/* 风险度 */
	public static final String ITEM_CASE_COUNT = "case_count" ;		/* 立案数 */
	public static final String ITEM_ILLEG_TRAIL_COUNT = "illeg_trail_count" ;	/* 违法线索量 */
	public static final String ITEM_REG_NO = "reg_no" ;				/* 企业注册号 */
	public static final String ITEM_ENT_NAME = "ent_name" ;			/* 企业名称 */
	public static final String ITEM_CONFI_FLAG = "confi_flag" ;		/* 确认标识（0：未确认，1：已确认） */
	public static final String ITEM_VIEW_FLAG = "view_flag" ;		/* 查看标识（分局：0或1，市局：2） */
	public static final String ITEM_INTER_REAL_NAME_REG = "inter_real_name_reg" ;	/* 网络实名注册制度（点击查看详细） */
	public static final String ITEM_INFO_PUBLIC = "info_public" ;	/* 信息公示制度 */
	public static final String ITEM_CONSU_PROTE = "consu_prote" ;	/* 消费者权益保护制度 */
	public static final String ITEM_USER_INFO_SECUR = "user_info_secur" ;	/* 用户信息保密制度 */
	public static final String ITEM_ONLINE_CREDI_EVALU = "online_credi_evalu" ;	/* 网上信用评价制度 */
	public static final String ITEM_INFO_MONIT = "info_monit" ;		/* 信息监控制度 */
	public static final String ITEM_LIC_GOODS_SERVI = "lic_goods_servi" ;	/* 商品和服务经营许可制度 */
	public static final String ITEM_INTEL_PROPE_PROTE = "intel_prope_prote" ;	/* 知识产权保护制度 */
	public static final String ITEM_TRANS_SECUR = "trans_secur" ;	/* 交易安全制度 */
	public static final String ITEM_EMERG_MANAG = "emerg_manag" ;	/* 突发事件应急处理制度 */
	public static final String ITEM_OTHER_ILLEG_TRAIL = "other_illeg_trail" ;	/* 其它违法线索 */
	public static final String ITEM_OBJEC_RISK = "objec_risk" ;		/* 客体风险度 */
	public static final String ITEM_BEHAV_RISK = "behav_risk" ;		/* 行为风险度 */
	public static final String ITEM_OBJEC_DATE = "objec_date" ;		/* 客体风险度更新时间 */
	public static final String ITEM_BEHAV_DATE = "behav_date" ;		/* 行为风险度更新时间 */
	
	/**
	 * 构造函数
	 */
	public VoEbEntWebSiteInfo()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbEntWebSiteInfo(DataBus value)
	{
		super(value);
	}
	
	/* 主键chr_id : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

	/* 管辖分局 : String */
	public String getDom_subst()
	{
		return getValue( ITEM_DOM_SUBST );
	}

	public void setDom_subst( String dom_subst1 )
	{
		setValue( ITEM_DOM_SUBST, dom_subst1 );
	}

	/* 管辖工商所 : String */
	public String getDom_local_adm()
	{
		return getValue( ITEM_DOM_LOCAL_ADM );
	}

	public void setDom_local_adm( String dom_local_adm1 )
	{
		setValue( ITEM_DOM_LOCAL_ADM, dom_local_adm1 );
	}

	/* 网格ID : String */
	public String getGrid_id()
	{
		return getValue( ITEM_GRID_ID );
	}

	public void setGrid_id( String grid_id1 )
	{
		setValue( ITEM_GRID_ID, grid_id1 );
	}

	/* 企业id : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

	/* 网址 : String */
	public String getWeb_site()
	{
		return getValue( ITEM_WEB_SITE );
	}

	public void setWeb_site( String web_site1 )
	{
		setValue( ITEM_WEB_SITE, web_site1 );
	}

	/* IP地域 : String */
	public String getIp_area()
	{
		return getValue( ITEM_IP_AREA );
	}

	public void setIp_area( String ip_area1 )
	{
		setValue( ITEM_IP_AREA, ip_area1 );
	}

	/* IP地址 : String */
	public String getIp_addre()
	{
		return getValue( ITEM_IP_ADDRE );
	}

	public void setIp_addre( String ip_addre1 )
	{
		setValue( ITEM_IP_ADDRE, ip_addre1 );
	}

	/* 是否无效链接 : String */
	public String getIs_inval_link()
	{
		return getValue( ITEM_IS_INVAL_LINK );
	}

	public void setIs_inval_link( String is_inval_link1 )
	{
		setValue( ITEM_IS_INVAL_LINK, is_inval_link1 );
	}

	/* 时间戳（搜狐） : String */
	public String getUpdate_date()
	{
		return getValue( ITEM_UPDATE_DATE );
	}

	public void setUpdate_date( String update_date1 )
	{
		setValue( ITEM_UPDATE_DATE, update_date1 );
	}

	/* 内容简介 : String */
	public String getIntroduction()
	{
		return getValue( ITEM_INTRODUCTION );
	}

	public void setIntroduction( String introduction1 )
	{
		setValue( ITEM_INTRODUCTION, introduction1 );
	}

	/* 新建标识 : String */
	public String getNew_sign()
	{
		return getValue( ITEM_NEW_SIGN );
	}

	public void setNew_sign( String new_sign1 )
	{
		setValue( ITEM_NEW_SIGN, new_sign1 );
	}

	/* 判定内容 : String */
	public String getJudge_detail()
	{
		return getValue( ITEM_JUDGE_DETAIL );
	}

	public void setJudge_detail( String judge_detail1 )
	{
		setValue( ITEM_JUDGE_DETAIL, judge_detail1 );
	}

	/* 网站类型 : String */
	public String getSite_type()
	{
		return getValue( ITEM_SITE_TYPE );
	}

	public void setSite_type( String site_type1 )
	{
		setValue( ITEM_SITE_TYPE, site_type1 );
	}

	/* 中文网站名称 : String */
	public String getChine_site_name()
	{
		return getValue( ITEM_CHINE_SITE_NAME );
	}

	public void setChine_site_name( String chine_site_name1 )
	{
		setValue( ITEM_CHINE_SITE_NAME, chine_site_name1 );
	}

	/* 代理广告 : String */
	public String getProxy_ad()
	{
		return getValue( ITEM_PROXY_AD );
	}

	public void setProxy_ad( String proxy_ad1 )
	{
		setValue( ITEM_PROXY_AD, proxy_ad1 );
	}

	/* 发布广告 : String */
	public String getIssue_ad()
	{
		return getValue( ITEM_ISSUE_AD );
	}

	public void setIssue_ad( String issue_ad1 )
	{
		setValue( ITEM_ISSUE_AD, issue_ad1 );
	}

	/* 本公司宣传 : String */
	public String getSelf_ad()
	{
		return getValue( ITEM_SELF_AD );
	}

	public void setSelf_ad( String self_ad1 )
	{
		setValue( ITEM_SELF_AD, self_ad1 );
	}

	/* 是否发现违法线索（0：否1：是） : String */
	public String getIs_illeg_clues_found()
	{
		return getValue( ITEM_IS_ILLEG_CLUES_FOUND );
	}

	public void setIs_illeg_clues_found( String is_illeg_clues_found1 )
	{
		setValue( ITEM_IS_ILLEG_CLUES_FOUND, is_illeg_clues_found1 );
	}

	/* 判定人 : String */
	public String getJudge_person()
	{
		return getValue( ITEM_JUDGE_PERSON );
	}

	public void setJudge_person( String judge_person1 )
	{
		setValue( ITEM_JUDGE_PERSON, judge_person1 );
	}

	/* 判定时间 : String */
	public String getJudge_date()
	{
		return getValue( ITEM_JUDGE_DATE );
	}

	public void setJudge_date( String judge_date1 )
	{
		setValue( ITEM_JUDGE_DATE, judge_date1 );
	}

	/* 判定标识（0未初审，1已初审，2内容不匹配，3无效链接，4辖区异议，5退回） : String */
	public String getJudge_sign()
	{
		return getValue( ITEM_JUDGE_SIGN );
	}

	public void setJudge_sign( String judge_sign1 )
	{
		setValue( ITEM_JUDGE_SIGN, judge_sign1 );
	}

	/* 浏览量 : String */
	public String getView_count()
	{
		return getValue( ITEM_VIEW_COUNT );
	}

	public void setView_count( String view_count1 )
	{
		setValue( ITEM_VIEW_COUNT, view_count1 );
	}

	/* 风险度 : String */
	public String getRisk()
	{
		return getValue( ITEM_RISK );
	}

	public void setRisk( String risk1 )
	{
		setValue( ITEM_RISK, risk1 );
	}

	/* 立案数 : String */
	public String getCase_count()
	{
		return getValue( ITEM_CASE_COUNT );
	}

	public void setCase_count( String case_count1 )
	{
		setValue( ITEM_CASE_COUNT, case_count1 );
	}

	/* 违法线索量 : String */
	public String getIlleg_trail_count()
	{
		return getValue( ITEM_ILLEG_TRAIL_COUNT );
	}

	public void setIlleg_trail_count( String illeg_trail_count1 )
	{
		setValue( ITEM_ILLEG_TRAIL_COUNT, illeg_trail_count1 );
	}

	/* 企业注册号 : String */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* 企业名称 : String */
	public String getEnt_name()
	{
		return getValue( ITEM_ENT_NAME );
	}

	public void setEnt_name( String ent_name1 )
	{
		setValue( ITEM_ENT_NAME, ent_name1 );
	}

	/* 确认标识（0：未确认，1：已确认） : String */
	public String getConfi_flag()
	{
		return getValue( ITEM_CONFI_FLAG );
	}

	public void setConfi_flag( String confi_flag1 )
	{
		setValue( ITEM_CONFI_FLAG, confi_flag1 );
	}

	/* 查看标识（分局：0或1，市局：2） : String */
	public String getView_flag()
	{
		return getValue( ITEM_VIEW_FLAG );
	}

	public void setView_flag( String view_flag1 )
	{
		setValue( ITEM_VIEW_FLAG, view_flag1 );
	}

	/* 网络实名注册制度（点击查看详细） : String */
	public String getInter_real_name_reg()
	{
		return getValue( ITEM_INTER_REAL_NAME_REG );
	}

	public void setInter_real_name_reg( String inter_real_name_reg1 )
	{
		setValue( ITEM_INTER_REAL_NAME_REG, inter_real_name_reg1 );
	}

	/* 信息公示制度 : String */
	public String getInfo_public()
	{
		return getValue( ITEM_INFO_PUBLIC );
	}

	public void setInfo_public( String info_public1 )
	{
		setValue( ITEM_INFO_PUBLIC, info_public1 );
	}

	/* 消费者权益保护制度 : String */
	public String getConsu_prote()
	{
		return getValue( ITEM_CONSU_PROTE );
	}

	public void setConsu_prote( String consu_prote1 )
	{
		setValue( ITEM_CONSU_PROTE, consu_prote1 );
	}

	/* 用户信息保密制度 : String */
	public String getUser_info_secur()
	{
		return getValue( ITEM_USER_INFO_SECUR );
	}

	public void setUser_info_secur( String user_info_secur1 )
	{
		setValue( ITEM_USER_INFO_SECUR, user_info_secur1 );
	}

	/* 网上信用评价制度 : String */
	public String getOnline_credi_evalu()
	{
		return getValue( ITEM_ONLINE_CREDI_EVALU );
	}

	public void setOnline_credi_evalu( String online_credi_evalu1 )
	{
		setValue( ITEM_ONLINE_CREDI_EVALU, online_credi_evalu1 );
	}

	/* 信息监控制度 : String */
	public String getInfo_monit()
	{
		return getValue( ITEM_INFO_MONIT );
	}

	public void setInfo_monit( String info_monit1 )
	{
		setValue( ITEM_INFO_MONIT, info_monit1 );
	}

	/* 商品和服务经营许可制度 : String */
	public String getLic_goods_servi()
	{
		return getValue( ITEM_LIC_GOODS_SERVI );
	}

	public void setLic_goods_servi( String lic_goods_servi1 )
	{
		setValue( ITEM_LIC_GOODS_SERVI, lic_goods_servi1 );
	}

	/* 知识产权保护制度 : String */
	public String getIntel_prope_prote()
	{
		return getValue( ITEM_INTEL_PROPE_PROTE );
	}

	public void setIntel_prope_prote( String intel_prope_prote1 )
	{
		setValue( ITEM_INTEL_PROPE_PROTE, intel_prope_prote1 );
	}

	/* 交易安全制度 : String */
	public String getTrans_secur()
	{
		return getValue( ITEM_TRANS_SECUR );
	}

	public void setTrans_secur( String trans_secur1 )
	{
		setValue( ITEM_TRANS_SECUR, trans_secur1 );
	}

	/* 突发事件应急处理制度 : String */
	public String getEmerg_manag()
	{
		return getValue( ITEM_EMERG_MANAG );
	}

	public void setEmerg_manag( String emerg_manag1 )
	{
		setValue( ITEM_EMERG_MANAG, emerg_manag1 );
	}

	/* 其它违法线索 : String */
	public String getOther_illeg_trail()
	{
		return getValue( ITEM_OTHER_ILLEG_TRAIL );
	}

	public void setOther_illeg_trail( String other_illeg_trail1 )
	{
		setValue( ITEM_OTHER_ILLEG_TRAIL, other_illeg_trail1 );
	}

	/* 客体风险度 : String */
	public String getObjec_risk()
	{
		return getValue( ITEM_OBJEC_RISK );
	}

	public void setObjec_risk( String objec_risk1 )
	{
		setValue( ITEM_OBJEC_RISK, objec_risk1 );
	}

	/* 行为风险度 : String */
	public String getBehav_risk()
	{
		return getValue( ITEM_BEHAV_RISK );
	}

	public void setBehav_risk( String behav_risk1 )
	{
		setValue( ITEM_BEHAV_RISK, behav_risk1 );
	}

	/* 客体风险度更新时间 : String */
	public String getObjec_date()
	{
		return getValue( ITEM_OBJEC_DATE );
	}

	public void setObjec_date( String objec_date1 )
	{
		setValue( ITEM_OBJEC_DATE, objec_date1 );
	}

	/* 行为风险度更新时间 : String */
	public String getBehav_date()
	{
		return getValue( ITEM_BEHAV_DATE );
	}

	public void setBehav_date( String behav_date1 )
	{
		setValue( ITEM_BEHAV_DATE, behav_date1 );
	}

}

