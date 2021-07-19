package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_site_illeg_trail]的数据对象类
 * @author Administrator
 *
 */
public class VoEbSiteIllegTrail extends VoBase
{
	private static final long serialVersionUID = 201209241647280014L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* 主键ID(网站ID) */
	public static final String ITEM_DOM_SUBST = "dom_subst" ;		/* 管辖分局 */
	public static final String ITEM_DOM_LOCAL_ADM = "dom_local_adm" ;	/* 管辖工商所 */
	public static final String ITEM_IMAGE_PATH = "image_path" ;		/* 图片路径 */
	public static final String ITEM_EVIDE_TYPE = "evide_type" ;		/* EVIDE_TYPE */
	public static final String ITEM_ENTRY_PERSON = "entry_person" ;	/* 录入人 */
	public static final String ITEM_ENTRY_DATE = "entry_date" ;		/* 录入时间 */
	public static final String ITEM_INFO_LEVEL = "info_level" ;		/* 信息等级 */
	public static final String ITEM_ATTEN_LEVEL = "atten_level" ;	/* 关注等级 */
	public static final String ITEM_INFO_CATEG_BIG = "info_categ_big" ;	/* 信息分类大类 */
	public static final String ITEM_INFO_CATEG_MID = "info_categ_mid" ;	/* 信息分类中类 */
	public static final String ITEM_INFO_CATEG_SMALL = "info_categ_small" ;	/* 信息分类小类 */
	public static final String ITEM_OVERVIEW = "overview" ;			/* 概要说明 */
	public static final String ITEM_IS_RECORD_CASE = "is_record_case" ;	/* 建议立案 */
	public static final String ITEM_APPRO_LEADER = "appro_leader" ;	/* 审批领导 */
	public static final String ITEM_APPRO_STATUS = "appro_status" ;	/* 审批状态(0 未审批 1 已审批) */
	public static final String ITEM_APPRO_COMME = "appro_comme" ;	/* 审批备注 */
	public static final String ITEM_APPRO_DATE = "appro_date" ;		/* 审批时间 */
	public static final String ITEM_IS_CONTR_MEASU = "is_contr_measu" ;	/* 采取控制措施 */
	public static final String ITEM_IS_NO_TREAT = "is_no_treat" ;	/* 无需处理 */
	public static final String ITEM_IS_PUBLI_OPINI = "is_publi_opini" ;	/* 舆情 */
	public static final String ITEM_INFO_TYPE = "info_type" ;		/* 来源（0：网页判定1：重点监控3客体 4行为 ） */
	public static final String ITEM_IS_JUDGE_MECHA = "is_judge_mecha" ;	/* 送入研判机制（0：否1：是2：已研判） */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* 主体id */
	public static final String ITEM_CASE_NO = "case_no" ;			/* 案件编号 */
	public static final String ITEM_ADMIN_TIPS = "admin_tips" ;		/* 行政提示 */
	public static final String ITEM_ORDER_CORRE = "order_corre" ;	/* 责令改正 */
	public static final String ITEM_BACK_SEARC_CYCLE = "back_searc_cycle" ;	/* 回查周期 */
	public static final String ITEM_PROPO_TRANS = "propo_trans" ;	/* 建议移转 */
	public static final String ITEM_EXECU_APPOI = "execu_appoi" ;	/* 建议行政约见 */
	public static final String ITEM_ADMIN_WARNI = "admin_warni" ;	/* 建议行政告诫 */
	public static final String ITEM_LABEL_DEAL = "label_deal" ;		/* 建议年检时标注处理 */
	public static final String ITEM_APPRO_RESULT = "appro_result" ;	/* 审批意见(0 不同意 1 同意) */
	public static final String ITEM_REMARK = "remark" ;				/* 初审人意见备注 */
	public static final String ITEM_HANDLE_DESC = "handle_desc" ;	/* 处理描述 */
	public static final String ITEM_HANDLE_STATUS = "handle_status" ;	/* 处理状态(0未处理 1已处理) */
	
	/**
	 * 构造函数
	 */
	public VoEbSiteIllegTrail()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbSiteIllegTrail(DataBus value)
	{
		super(value);
	}
	
	/* 主键ID(网站ID) : String */
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

	/* 图片路径 : String */
	public String getImage_path()
	{
		return getValue( ITEM_IMAGE_PATH );
	}

	public void setImage_path( String image_path1 )
	{
		setValue( ITEM_IMAGE_PATH, image_path1 );
	}

	/* EVIDE_TYPE : String */
	public String getEvide_type()
	{
		return getValue( ITEM_EVIDE_TYPE );
	}

	public void setEvide_type( String evide_type1 )
	{
		setValue( ITEM_EVIDE_TYPE, evide_type1 );
	}

	/* 录入人 : String */
	public String getEntry_person()
	{
		return getValue( ITEM_ENTRY_PERSON );
	}

	public void setEntry_person( String entry_person1 )
	{
		setValue( ITEM_ENTRY_PERSON, entry_person1 );
	}

	/* 录入时间 : String */
	public String getEntry_date()
	{
		return getValue( ITEM_ENTRY_DATE );
	}

	public void setEntry_date( String entry_date1 )
	{
		setValue( ITEM_ENTRY_DATE, entry_date1 );
	}

	/* 信息等级 : String */
	public String getInfo_level()
	{
		return getValue( ITEM_INFO_LEVEL );
	}

	public void setInfo_level( String info_level1 )
	{
		setValue( ITEM_INFO_LEVEL, info_level1 );
	}

	/* 关注等级 : String */
	public String getAtten_level()
	{
		return getValue( ITEM_ATTEN_LEVEL );
	}

	public void setAtten_level( String atten_level1 )
	{
		setValue( ITEM_ATTEN_LEVEL, atten_level1 );
	}

	/* 信息分类大类 : String */
	public String getInfo_categ_big()
	{
		return getValue( ITEM_INFO_CATEG_BIG );
	}

	public void setInfo_categ_big( String info_categ_big1 )
	{
		setValue( ITEM_INFO_CATEG_BIG, info_categ_big1 );
	}

	/* 信息分类中类 : String */
	public String getInfo_categ_mid()
	{
		return getValue( ITEM_INFO_CATEG_MID );
	}

	public void setInfo_categ_mid( String info_categ_mid1 )
	{
		setValue( ITEM_INFO_CATEG_MID, info_categ_mid1 );
	}

	/* 信息分类小类 : String */
	public String getInfo_categ_small()
	{
		return getValue( ITEM_INFO_CATEG_SMALL );
	}

	public void setInfo_categ_small( String info_categ_small1 )
	{
		setValue( ITEM_INFO_CATEG_SMALL, info_categ_small1 );
	}

	/* 概要说明 : String */
	public String getOverview()
	{
		return getValue( ITEM_OVERVIEW );
	}

	public void setOverview( String overview1 )
	{
		setValue( ITEM_OVERVIEW, overview1 );
	}

	/* 建议立案 : String */
	public String getIs_record_case()
	{
		return getValue( ITEM_IS_RECORD_CASE );
	}

	public void setIs_record_case( String is_record_case1 )
	{
		setValue( ITEM_IS_RECORD_CASE, is_record_case1 );
	}

	/* 审批领导 : String */
	public String getAppro_leader()
	{
		return getValue( ITEM_APPRO_LEADER );
	}

	public void setAppro_leader( String appro_leader1 )
	{
		setValue( ITEM_APPRO_LEADER, appro_leader1 );
	}

	/* 审批状态(0 未审批 1 已审批) : String */
	public String getAppro_status()
	{
		return getValue( ITEM_APPRO_STATUS );
	}

	public void setAppro_status( String appro_status1 )
	{
		setValue( ITEM_APPRO_STATUS, appro_status1 );
	}

	/* 审批备注 : String */
	public String getAppro_comme()
	{
		return getValue( ITEM_APPRO_COMME );
	}

	public void setAppro_comme( String appro_comme1 )
	{
		setValue( ITEM_APPRO_COMME, appro_comme1 );
	}

	/* 审批时间 : String */
	public String getAppro_date()
	{
		return getValue( ITEM_APPRO_DATE );
	}

	public void setAppro_date( String appro_date1 )
	{
		setValue( ITEM_APPRO_DATE, appro_date1 );
	}

	/* 采取控制措施 : String */
	public String getIs_contr_measu()
	{
		return getValue( ITEM_IS_CONTR_MEASU );
	}

	public void setIs_contr_measu( String is_contr_measu1 )
	{
		setValue( ITEM_IS_CONTR_MEASU, is_contr_measu1 );
	}

	/* 无需处理 : String */
	public String getIs_no_treat()
	{
		return getValue( ITEM_IS_NO_TREAT );
	}

	public void setIs_no_treat( String is_no_treat1 )
	{
		setValue( ITEM_IS_NO_TREAT, is_no_treat1 );
	}

	/* 舆情 : String */
	public String getIs_publi_opini()
	{
		return getValue( ITEM_IS_PUBLI_OPINI );
	}

	public void setIs_publi_opini( String is_publi_opini1 )
	{
		setValue( ITEM_IS_PUBLI_OPINI, is_publi_opini1 );
	}

	/* 来源（0：网页判定1：重点监控3客体 4行为 ） : String */
	public String getInfo_type()
	{
		return getValue( ITEM_INFO_TYPE );
	}

	public void setInfo_type( String info_type1 )
	{
		setValue( ITEM_INFO_TYPE, info_type1 );
	}

	/* 送入研判机制（0：否1：是2：已研判） : String */
	public String getIs_judge_mecha()
	{
		return getValue( ITEM_IS_JUDGE_MECHA );
	}

	public void setIs_judge_mecha( String is_judge_mecha1 )
	{
		setValue( ITEM_IS_JUDGE_MECHA, is_judge_mecha1 );
	}

	/* 主体id : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

	/* 案件编号 : String */
	public String getCase_no()
	{
		return getValue( ITEM_CASE_NO );
	}

	public void setCase_no( String case_no1 )
	{
		setValue( ITEM_CASE_NO, case_no1 );
	}

	/* 行政提示 : String */
	public String getAdmin_tips()
	{
		return getValue( ITEM_ADMIN_TIPS );
	}

	public void setAdmin_tips( String admin_tips1 )
	{
		setValue( ITEM_ADMIN_TIPS, admin_tips1 );
	}

	/* 责令改正 : String */
	public String getOrder_corre()
	{
		return getValue( ITEM_ORDER_CORRE );
	}

	public void setOrder_corre( String order_corre1 )
	{
		setValue( ITEM_ORDER_CORRE, order_corre1 );
	}

	/* 回查周期 : String */
	public String getBack_searc_cycle()
	{
		return getValue( ITEM_BACK_SEARC_CYCLE );
	}

	public void setBack_searc_cycle( String back_searc_cycle1 )
	{
		setValue( ITEM_BACK_SEARC_CYCLE, back_searc_cycle1 );
	}

	/* 建议移转 : String */
	public String getPropo_trans()
	{
		return getValue( ITEM_PROPO_TRANS );
	}

	public void setPropo_trans( String propo_trans1 )
	{
		setValue( ITEM_PROPO_TRANS, propo_trans1 );
	}

	/* 建议行政约见 : String */
	public String getExecu_appoi()
	{
		return getValue( ITEM_EXECU_APPOI );
	}

	public void setExecu_appoi( String execu_appoi1 )
	{
		setValue( ITEM_EXECU_APPOI, execu_appoi1 );
	}

	/* 建议行政告诫 : String */
	public String getAdmin_warni()
	{
		return getValue( ITEM_ADMIN_WARNI );
	}

	public void setAdmin_warni( String admin_warni1 )
	{
		setValue( ITEM_ADMIN_WARNI, admin_warni1 );
	}

	/* 建议年检时标注处理 : String */
	public String getLabel_deal()
	{
		return getValue( ITEM_LABEL_DEAL );
	}

	public void setLabel_deal( String label_deal1 )
	{
		setValue( ITEM_LABEL_DEAL, label_deal1 );
	}

	/* 审批意见(0 不同意 1 同意) : String */
	public String getAppro_result()
	{
		return getValue( ITEM_APPRO_RESULT );
	}

	public void setAppro_result( String appro_result1 )
	{
		setValue( ITEM_APPRO_RESULT, appro_result1 );
	}

	/* 初审人意见备注 : String */
	public String getRemark()
	{
		return getValue( ITEM_REMARK );
	}

	public void setRemark( String remark1 )
	{
		setValue( ITEM_REMARK, remark1 );
	}

	/* 处理描述 : String */
	public String getHandle_desc()
	{
		return getValue( ITEM_HANDLE_DESC );
	}

	public void setHandle_desc( String handle_desc1 )
	{
		setValue( ITEM_HANDLE_DESC, handle_desc1 );
	}

	/* 处理状态(0未处理 1已处理) : String */
	public String getHandle_status()
	{
		return getValue( ITEM_HANDLE_STATUS );
	}

	public void setHandle_status( String handle_status1 )
	{
		setValue( ITEM_HANDLE_STATUS, handle_status1 );
	}

}

