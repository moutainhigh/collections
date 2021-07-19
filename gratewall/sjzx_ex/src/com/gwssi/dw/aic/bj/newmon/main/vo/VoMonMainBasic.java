package com.gwssi.dw.aic.bj.newmon.main.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoMonMainBasic extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_MON_MAIN_BASIC_ID = "mon_main_basic_id";			/* 主体基本信息表ID */
	public static final String ITEM_MAIN_ID = "main_id";			/* 主体ID */
	public static final String ITEM_GRID_ID = "grid_id";			/* 生存状态(dm) */
	public static final String ITEM_EXIST_STATE = "exist_state";			/* 认领时间 */
	public static final String ITEM_CLA_TIME = "cla_time";			/* 认领时间 */
	public static final String ITEM_GA_RISK_RANK = "ga_risk_rank";			/* 市局分风险度级别 */
	public static final String ITEM_SUB_RISK_RANK = "sub_risk_rank";			/* 分局风险度级别(dm) */
	public static final String ITEM_ICI_RISK_RANK = "ici_risk_rank";			/* 工商所风险度级别(dm) */
	public static final String ITEM_NEED_EARLY_CHE = "need_early_che";			/* 是否需要初期检查(dm) */
	public static final String ITEM_ENT_RANK = "ent_rank";			/* 主体级别(dm) */
	public static final String ITEM_MONI_RANK = "moni_rank";			/* 巡查级别(dm) */
	public static final String ITEM_IMP_IND_AREA = "imp_ind_area";			/* 是否被设置重点行业重点地域(dm) */
	public static final String ITEM_ENT_TYPE = "ent_type";			/* 企业类型 */
	public static final String ITEM_INSPE_DEPT_CODE = "inspe_dept_code";			/* 年检部门代码 */
	public static final String ITEM_CHECK_FLAG = "check_flag";			/* 核对信息标志 */
	public static final String ITEM_ACCOU_FIRM_FLAG = "accou_firm_flag";			/* 会计师事务所标志 */
	public static final String ITEM_LIVE_STATE = "live_state";			/* 生存状态记录 */
	public static final String ITEM_EXPEL_DATE = "expel_date";			/* 吊销日期 */
	public static final String ITEM_INSPE_FALSE = "inspe_false";			/* 是否查无 */
	public static final String ITEM_OUT_FLAG = "out_flag";			/* 是否非法迁出 */
	public static final String ITEM_INSPE_FLAG = "inspe_flag";			/* 年检标志 */
	public static final String ITEM_LINK_MAN_ADD = "link_man_add";			/* 附加联系人 */
	public static final String ITEM_LINK_PHONE_ADD = "link_phone_add";			/* 附加联系电话 */
	public static final String ITEM_REG_RE = "reg_re";			/* 登记区县 */
	public static final String ITEM_ADM_RE = "adm_re";			/* 管辖区县 */
	public static final String ITEM_ADM_ORG = "adm_org";			/* 管辖分局 */
	public static final String ITEM_ADM_ORG_DEP = "adm_org_dep";			/* 管辖工商所 */
	public static final String ITEM_OP_SCOPE = "op_scope";			/* 经营范围 */
	public static final String ITEM_REG_NO = "reg_no";			/* 注册号 */
	public static final String ITEM_ENT_TITLE = "ent_title";			/* 主体名称 */
	public static final String ITEM_LAW_MAN = "law_man";			/* 法定代表人 */
	public static final String ITEM_WORD_NO = "word_no";			/* 字号 */
	public static final String ITEM_INDUSTRY_II_CO = "industry_ii_co";			/* 行业小类代码 */
	public static final String ITEM_INDUSTRY_I_CO = "industry_i_co";			/* 行业大类代码 */
	public static final String ITEM_ENT_ATTRI = "ent_attri";			/* 企业性质 */
	public static final String ITEM_ADDR = "addr";			/* 住所 */
	public static final String ITEM_REG_CAP_REMA = "reg_cap_rema";			/* 注册资本说明 */
	public static final String ITEM_REG_CAP = "reg_cap";			/* 注册资本 */
	public static final String ITEM_MONEY_TYPE = "money_type";			/* 币种 */
	public static final String ITEM_CAP_TOTAL_DOLLAR = "cap_total_dollar";			/* 投资总额折万美元 */
	public static final String ITEM_FOR_CAP_DOLLAR = "for_cap_dollar";			/* 外方投资折万美元 */
	public static final String ITEM_D_OR_F = "d_or_f";			/* 国别(dm) */
	public static final String ITEM_MOR_EXC_DATE = "mor_exc_date";			/* 变更日期 */
	public static final String ITEM_LAST_OP_DATE = "last_op_date";			/* 最后操作日期 */
	public static final String ITEM_FOUND_DATE = "found_date";			/* 成立日期 */
	public static final String ITEM_LICEN_TIME = "licen_time";			/* 执照有效期 */
	public static final String ITEM_MAIN_TYPE = "main_type";			/* 主体类型 */
	public static final String ITEM_L_CARD_NO = "l_card_no";			/* 法定代表人身份证号 */
	public static final String ITEM_INDUSTRY_CLASS = "industry_class";			/* 行业门类 */
	public static final String ITEM_CRED_RATI = "cred_rati";			/* 信用等级 */
	public static final String ITEM_REG_DATE = "reg_date";			/* 登记时间 */
	public static final String ITEM_LINK_MAN = "link_man";			/* 联系人 */
	public static final String ITEM_LINK_TELE = "link_tele";			/* 联系电话 */
	public static final String ITEM_MAIN_CLASS = "main_class";			/* 主体类别 */
	public static final String ITEM_ECON_TYPE = "econ_type";			/* 经济性质 */
	public static final String ITEM_REG_CAP_DOLLAR = "reg_cap_dollar";			/* 注册资本折美元 */
	public static final String ITEM_REG_DEP = "reg_dep";			/* 登记部门 */
	public static final String ITEM_TEMP_REG_NO = "temp_reg_no";			/* 临时注册号 */
	public static final String ITEM_OP_TO_TIME = "op_to_time";			/* 经营期限至 */
	public static final String ITEM_EXIST_STATE_CHA = "exist_state_cha";			/* 生存状态变更 */
	public static final String ITEM_CHA_NEW_FLAG = "cha_new_flag";			/* 变更与新增标志 */
	public static final String ITEM_EXAM_FLAG = "exam_flag";			/* 参检标志 */
	public static final String ITEM_REVOK_DATE_FLAG = "revok_date_flag";			/* 吊销数据标记 */
	public static final String ITEM_LINK_PHONE_BACKUP = "link_phone_backup";			/* 联系电话backup */
	public static final String ITEM_EXAM_FLAG_OLD = "exam_flag_old";			/* 参检标志odl */
	public static final String ITEM_NSPE_FLAGBACKUP = "nspe_flagbackup";			/* 年检标志BACKUP */

	public VoMonMainBasic(DataBus value)
	{
		super(value);
	}

	public VoMonMainBasic()
	{
		super();
	}

	/* 主体基本信息表ID */
	public String getMon_main_basic_id()
	{
		return getValue( ITEM_MON_MAIN_BASIC_ID );
	}

	public void setMon_main_basic_id( String mon_main_basic_id1 )
	{
		setValue( ITEM_MON_MAIN_BASIC_ID, mon_main_basic_id1 );
	}

	/* 主体ID */
	public String getMain_id()
	{
		return getValue( ITEM_MAIN_ID );
	}

	public void setMain_id( String main_id1 )
	{
		setValue( ITEM_MAIN_ID, main_id1 );
	}

	/* 生存状态(dm) */
	public String getGrid_id()
	{
		return getValue( ITEM_GRID_ID );
	}

	public void setGrid_id( String grid_id1 )
	{
		setValue( ITEM_GRID_ID, grid_id1 );
	}

	/* 认领时间 */
	public String getExist_state()
	{
		return getValue( ITEM_EXIST_STATE );
	}

	public void setExist_state( String exist_state1 )
	{
		setValue( ITEM_EXIST_STATE, exist_state1 );
	}

	/* 认领时间 */
	public String getCla_time()
	{
		return getValue( ITEM_CLA_TIME );
	}

	public void setCla_time( String cla_time1 )
	{
		setValue( ITEM_CLA_TIME, cla_time1 );
	}

	/* 市局分风险度级别 */
	public String getGa_risk_rank()
	{
		return getValue( ITEM_GA_RISK_RANK );
	}

	public void setGa_risk_rank( String ga_risk_rank1 )
	{
		setValue( ITEM_GA_RISK_RANK, ga_risk_rank1 );
	}

	/* 分局风险度级别(dm) */
	public String getSub_risk_rank()
	{
		return getValue( ITEM_SUB_RISK_RANK );
	}

	public void setSub_risk_rank( String sub_risk_rank1 )
	{
		setValue( ITEM_SUB_RISK_RANK, sub_risk_rank1 );
	}

	/* 工商所风险度级别(dm) */
	public String getIci_risk_rank()
	{
		return getValue( ITEM_ICI_RISK_RANK );
	}

	public void setIci_risk_rank( String ici_risk_rank1 )
	{
		setValue( ITEM_ICI_RISK_RANK, ici_risk_rank1 );
	}

	/* 是否需要初期检查(dm) */
	public String getNeed_early_che()
	{
		return getValue( ITEM_NEED_EARLY_CHE );
	}

	public void setNeed_early_che( String need_early_che1 )
	{
		setValue( ITEM_NEED_EARLY_CHE, need_early_che1 );
	}

	/* 主体级别(dm) */
	public String getEnt_rank()
	{
		return getValue( ITEM_ENT_RANK );
	}

	public void setEnt_rank( String ent_rank1 )
	{
		setValue( ITEM_ENT_RANK, ent_rank1 );
	}

	/* 巡查级别(dm) */
	public String getMoni_rank()
	{
		return getValue( ITEM_MONI_RANK );
	}

	public void setMoni_rank( String moni_rank1 )
	{
		setValue( ITEM_MONI_RANK, moni_rank1 );
	}

	/* 是否被设置重点行业重点地域(dm) */
	public String getImp_ind_area()
	{
		return getValue( ITEM_IMP_IND_AREA );
	}

	public void setImp_ind_area( String imp_ind_area1 )
	{
		setValue( ITEM_IMP_IND_AREA, imp_ind_area1 );
	}

	/* 企业类型 */
	public String getEnt_type()
	{
		return getValue( ITEM_ENT_TYPE );
	}

	public void setEnt_type( String ent_type1 )
	{
		setValue( ITEM_ENT_TYPE, ent_type1 );
	}

	/* 年检部门代码 */
	public String getInspe_dept_code()
	{
		return getValue( ITEM_INSPE_DEPT_CODE );
	}

	public void setInspe_dept_code( String inspe_dept_code1 )
	{
		setValue( ITEM_INSPE_DEPT_CODE, inspe_dept_code1 );
	}

	/* 核对信息标志 */
	public String getCheck_flag()
	{
		return getValue( ITEM_CHECK_FLAG );
	}

	public void setCheck_flag( String check_flag1 )
	{
		setValue( ITEM_CHECK_FLAG, check_flag1 );
	}

	/* 会计师事务所标志 */
	public String getAccou_firm_flag()
	{
		return getValue( ITEM_ACCOU_FIRM_FLAG );
	}

	public void setAccou_firm_flag( String accou_firm_flag1 )
	{
		setValue( ITEM_ACCOU_FIRM_FLAG, accou_firm_flag1 );
	}

	/* 生存状态记录 */
	public String getLive_state()
	{
		return getValue( ITEM_LIVE_STATE );
	}

	public void setLive_state( String live_state1 )
	{
		setValue( ITEM_LIVE_STATE, live_state1 );
	}

	/* 吊销日期 */
	public String getExpel_date()
	{
		return getValue( ITEM_EXPEL_DATE );
	}

	public void setExpel_date( String expel_date1 )
	{
		setValue( ITEM_EXPEL_DATE, expel_date1 );
	}

	/* 是否查无 */
	public String getInspe_false()
	{
		return getValue( ITEM_INSPE_FALSE );
	}

	public void setInspe_false( String inspe_false1 )
	{
		setValue( ITEM_INSPE_FALSE, inspe_false1 );
	}

	/* 是否非法迁出 */
	public String getOut_flag()
	{
		return getValue( ITEM_OUT_FLAG );
	}

	public void setOut_flag( String out_flag1 )
	{
		setValue( ITEM_OUT_FLAG, out_flag1 );
	}

	/* 年检标志 */
	public String getInspe_flag()
	{
		return getValue( ITEM_INSPE_FLAG );
	}

	public void setInspe_flag( String inspe_flag1 )
	{
		setValue( ITEM_INSPE_FLAG, inspe_flag1 );
	}

	/* 附加联系人 */
	public String getLink_man_add()
	{
		return getValue( ITEM_LINK_MAN_ADD );
	}

	public void setLink_man_add( String link_man_add1 )
	{
		setValue( ITEM_LINK_MAN_ADD, link_man_add1 );
	}

	/* 附加联系电话 */
	public String getLink_phone_add()
	{
		return getValue( ITEM_LINK_PHONE_ADD );
	}

	public void setLink_phone_add( String link_phone_add1 )
	{
		setValue( ITEM_LINK_PHONE_ADD, link_phone_add1 );
	}

	/* 登记区县 */
	public String getReg_re()
	{
		return getValue( ITEM_REG_RE );
	}

	public void setReg_re( String reg_re1 )
	{
		setValue( ITEM_REG_RE, reg_re1 );
	}

	/* 管辖区县 */
	public String getAdm_re()
	{
		return getValue( ITEM_ADM_RE );
	}

	public void setAdm_re( String adm_re1 )
	{
		setValue( ITEM_ADM_RE, adm_re1 );
	}

	/* 管辖分局 */
	public String getAdm_org()
	{
		return getValue( ITEM_ADM_ORG );
	}

	public void setAdm_org( String adm_org1 )
	{
		setValue( ITEM_ADM_ORG, adm_org1 );
	}

	/* 管辖工商所 */
	public String getAdm_org_dep()
	{
		return getValue( ITEM_ADM_ORG_DEP );
	}

	public void setAdm_org_dep( String adm_org_dep1 )
	{
		setValue( ITEM_ADM_ORG_DEP, adm_org_dep1 );
	}

	/* 经营范围 */
	public String getOp_scope()
	{
		return getValue( ITEM_OP_SCOPE );
	}

	public void setOp_scope( String op_scope1 )
	{
		setValue( ITEM_OP_SCOPE, op_scope1 );
	}

	/* 注册号 */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* 主体名称 */
	public String getEnt_title()
	{
		return getValue( ITEM_ENT_TITLE );
	}

	public void setEnt_title( String ent_title1 )
	{
		setValue( ITEM_ENT_TITLE, ent_title1 );
	}

	/* 法定代表人 */
	public String getLaw_man()
	{
		return getValue( ITEM_LAW_MAN );
	}

	public void setLaw_man( String law_man1 )
	{
		setValue( ITEM_LAW_MAN, law_man1 );
	}

	/* 字号 */
	public String getWord_no()
	{
		return getValue( ITEM_WORD_NO );
	}

	public void setWord_no( String word_no1 )
	{
		setValue( ITEM_WORD_NO, word_no1 );
	}

	/* 行业小类代码 */
	public String getIndustry_ii_co()
	{
		return getValue( ITEM_INDUSTRY_II_CO );
	}

	public void setIndustry_ii_co( String industry_ii_co1 )
	{
		setValue( ITEM_INDUSTRY_II_CO, industry_ii_co1 );
	}

	/* 行业大类代码 */
	public String getIndustry_i_co()
	{
		return getValue( ITEM_INDUSTRY_I_CO );
	}

	public void setIndustry_i_co( String industry_i_co1 )
	{
		setValue( ITEM_INDUSTRY_I_CO, industry_i_co1 );
	}

	/* 企业性质 */
	public String getEnt_attri()
	{
		return getValue( ITEM_ENT_ATTRI );
	}

	public void setEnt_attri( String ent_attri1 )
	{
		setValue( ITEM_ENT_ATTRI, ent_attri1 );
	}

	/* 住所 */
	public String getAddr()
	{
		return getValue( ITEM_ADDR );
	}

	public void setAddr( String addr1 )
	{
		setValue( ITEM_ADDR, addr1 );
	}

	/* 注册资本说明 */
	public String getReg_cap_rema()
	{
		return getValue( ITEM_REG_CAP_REMA );
	}

	public void setReg_cap_rema( String reg_cap_rema1 )
	{
		setValue( ITEM_REG_CAP_REMA, reg_cap_rema1 );
	}

	/* 注册资本 */
	public String getReg_cap()
	{
		return getValue( ITEM_REG_CAP );
	}

	public void setReg_cap( String reg_cap1 )
	{
		setValue( ITEM_REG_CAP, reg_cap1 );
	}

	/* 币种 */
	public String getMoney_type()
	{
		return getValue( ITEM_MONEY_TYPE );
	}

	public void setMoney_type( String money_type1 )
	{
		setValue( ITEM_MONEY_TYPE, money_type1 );
	}

	/* 投资总额折万美元 */
	public String getCap_total_dollar()
	{
		return getValue( ITEM_CAP_TOTAL_DOLLAR );
	}

	public void setCap_total_dollar( String cap_total_dollar1 )
	{
		setValue( ITEM_CAP_TOTAL_DOLLAR, cap_total_dollar1 );
	}

	/* 外方投资折万美元 */
	public String getFor_cap_dollar()
	{
		return getValue( ITEM_FOR_CAP_DOLLAR );
	}

	public void setFor_cap_dollar( String for_cap_dollar1 )
	{
		setValue( ITEM_FOR_CAP_DOLLAR, for_cap_dollar1 );
	}

	/* 国别(dm) */
	public String getD_or_f()
	{
		return getValue( ITEM_D_OR_F );
	}

	public void setD_or_f( String d_or_f1 )
	{
		setValue( ITEM_D_OR_F, d_or_f1 );
	}

	/* 变更日期 */
	public String getMor_exc_date()
	{
		return getValue( ITEM_MOR_EXC_DATE );
	}

	public void setMor_exc_date( String mor_exc_date1 )
	{
		setValue( ITEM_MOR_EXC_DATE, mor_exc_date1 );
	}

	/* 最后操作日期 */
	public String getLast_op_date()
	{
		return getValue( ITEM_LAST_OP_DATE );
	}

	public void setLast_op_date( String last_op_date1 )
	{
		setValue( ITEM_LAST_OP_DATE, last_op_date1 );
	}

	/* 成立日期 */
	public String getFound_date()
	{
		return getValue( ITEM_FOUND_DATE );
	}

	public void setFound_date( String found_date1 )
	{
		setValue( ITEM_FOUND_DATE, found_date1 );
	}

	/* 执照有效期 */
	public String getLicen_time()
	{
		return getValue( ITEM_LICEN_TIME );
	}

	public void setLicen_time( String licen_time1 )
	{
		setValue( ITEM_LICEN_TIME, licen_time1 );
	}

	/* 主体类型 */
	public String getMain_type()
	{
		return getValue( ITEM_MAIN_TYPE );
	}

	public void setMain_type( String main_type1 )
	{
		setValue( ITEM_MAIN_TYPE, main_type1 );
	}

	/* 法定代表人身份证号 */
	public String getL_card_no()
	{
		return getValue( ITEM_L_CARD_NO );
	}

	public void setL_card_no( String l_card_no1 )
	{
		setValue( ITEM_L_CARD_NO, l_card_no1 );
	}

	/* 行业门类 */
	public String getIndustry_class()
	{
		return getValue( ITEM_INDUSTRY_CLASS );
	}

	public void setIndustry_class( String industry_class1 )
	{
		setValue( ITEM_INDUSTRY_CLASS, industry_class1 );
	}

	/* 信用等级 */
	public String getCred_rati()
	{
		return getValue( ITEM_CRED_RATI );
	}

	public void setCred_rati( String cred_rati1 )
	{
		setValue( ITEM_CRED_RATI, cred_rati1 );
	}

	/* 登记时间 */
	public String getReg_date()
	{
		return getValue( ITEM_REG_DATE );
	}

	public void setReg_date( String reg_date1 )
	{
		setValue( ITEM_REG_DATE, reg_date1 );
	}

	/* 联系人 */
	public String getLink_man()
	{
		return getValue( ITEM_LINK_MAN );
	}

	public void setLink_man( String link_man1 )
	{
		setValue( ITEM_LINK_MAN, link_man1 );
	}

	/* 联系电话 */
	public String getLink_tele()
	{
		return getValue( ITEM_LINK_TELE );
	}

	public void setLink_tele( String link_tele1 )
	{
		setValue( ITEM_LINK_TELE, link_tele1 );
	}

	/* 主体类别 */
	public String getMain_class()
	{
		return getValue( ITEM_MAIN_CLASS );
	}

	public void setMain_class( String main_class1 )
	{
		setValue( ITEM_MAIN_CLASS, main_class1 );
	}

	/* 经济性质 */
	public String getEcon_type()
	{
		return getValue( ITEM_ECON_TYPE );
	}

	public void setEcon_type( String econ_type1 )
	{
		setValue( ITEM_ECON_TYPE, econ_type1 );
	}

	/* 注册资本折美元 */
	public String getReg_cap_dollar()
	{
		return getValue( ITEM_REG_CAP_DOLLAR );
	}

	public void setReg_cap_dollar( String reg_cap_dollar1 )
	{
		setValue( ITEM_REG_CAP_DOLLAR, reg_cap_dollar1 );
	}

	/* 登记部门 */
	public String getReg_dep()
	{
		return getValue( ITEM_REG_DEP );
	}

	public void setReg_dep( String reg_dep1 )
	{
		setValue( ITEM_REG_DEP, reg_dep1 );
	}

	/* 临时注册号 */
	public String getTemp_reg_no()
	{
		return getValue( ITEM_TEMP_REG_NO );
	}

	public void setTemp_reg_no( String temp_reg_no1 )
	{
		setValue( ITEM_TEMP_REG_NO, temp_reg_no1 );
	}

	/* 经营期限至 */
	public String getOp_to_time()
	{
		return getValue( ITEM_OP_TO_TIME );
	}

	public void setOp_to_time( String op_to_time1 )
	{
		setValue( ITEM_OP_TO_TIME, op_to_time1 );
	}

	/* 生存状态变更 */
	public String getExist_state_cha()
	{
		return getValue( ITEM_EXIST_STATE_CHA );
	}

	public void setExist_state_cha( String exist_state_cha1 )
	{
		setValue( ITEM_EXIST_STATE_CHA, exist_state_cha1 );
	}

	/* 变更与新增标志 */
	public String getCha_new_flag()
	{
		return getValue( ITEM_CHA_NEW_FLAG );
	}

	public void setCha_new_flag( String cha_new_flag1 )
	{
		setValue( ITEM_CHA_NEW_FLAG, cha_new_flag1 );
	}

	/* 参检标志 */
	public String getExam_flag()
	{
		return getValue( ITEM_EXAM_FLAG );
	}

	public void setExam_flag( String exam_flag1 )
	{
		setValue( ITEM_EXAM_FLAG, exam_flag1 );
	}

	/* 吊销数据标记 */
	public String getRevok_date_flag()
	{
		return getValue( ITEM_REVOK_DATE_FLAG );
	}

	public void setRevok_date_flag( String revok_date_flag1 )
	{
		setValue( ITEM_REVOK_DATE_FLAG, revok_date_flag1 );
	}

	/* 联系电话backup */
	public String getLink_phone_backup()
	{
		return getValue( ITEM_LINK_PHONE_BACKUP );
	}

	public void setLink_phone_backup( String link_phone_backup1 )
	{
		setValue( ITEM_LINK_PHONE_BACKUP, link_phone_backup1 );
	}

	/* 参检标志odl */
	public String getExam_flag_old()
	{
		return getValue( ITEM_EXAM_FLAG_OLD );
	}

	public void setExam_flag_old( String exam_flag_old1 )
	{
		setValue( ITEM_EXAM_FLAG_OLD, exam_flag_old1 );
	}

	/* 年检标志BACKUP */
	public String getNspe_flagbackup()
	{
		return getValue( ITEM_NSPE_FLAGBACKUP );
	}

	public void setNspe_flagbackup( String nspe_flagbackup1 )
	{
		setValue( ITEM_NSPE_FLAGBACKUP, nspe_flagbackup1 );
	}

}

