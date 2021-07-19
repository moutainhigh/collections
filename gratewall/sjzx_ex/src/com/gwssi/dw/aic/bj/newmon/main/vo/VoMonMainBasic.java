package com.gwssi.dw.aic.bj.newmon.main.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoMonMainBasic extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_MON_MAIN_BASIC_ID = "mon_main_basic_id";			/* ���������Ϣ��ID */
	public static final String ITEM_MAIN_ID = "main_id";			/* ����ID */
	public static final String ITEM_GRID_ID = "grid_id";			/* ����״̬(dm) */
	public static final String ITEM_EXIST_STATE = "exist_state";			/* ����ʱ�� */
	public static final String ITEM_CLA_TIME = "cla_time";			/* ����ʱ�� */
	public static final String ITEM_GA_RISK_RANK = "ga_risk_rank";			/* �оַַ��նȼ��� */
	public static final String ITEM_SUB_RISK_RANK = "sub_risk_rank";			/* �־ַ��նȼ���(dm) */
	public static final String ITEM_ICI_RISK_RANK = "ici_risk_rank";			/* ���������նȼ���(dm) */
	public static final String ITEM_NEED_EARLY_CHE = "need_early_che";			/* �Ƿ���Ҫ���ڼ��(dm) */
	public static final String ITEM_ENT_RANK = "ent_rank";			/* ���弶��(dm) */
	public static final String ITEM_MONI_RANK = "moni_rank";			/* Ѳ�鼶��(dm) */
	public static final String ITEM_IMP_IND_AREA = "imp_ind_area";			/* �Ƿ������ص���ҵ�ص����(dm) */
	public static final String ITEM_ENT_TYPE = "ent_type";			/* ��ҵ���� */
	public static final String ITEM_INSPE_DEPT_CODE = "inspe_dept_code";			/* ��첿�Ŵ��� */
	public static final String ITEM_CHECK_FLAG = "check_flag";			/* �˶���Ϣ��־ */
	public static final String ITEM_ACCOU_FIRM_FLAG = "accou_firm_flag";			/* ���ʦ��������־ */
	public static final String ITEM_LIVE_STATE = "live_state";			/* ����״̬��¼ */
	public static final String ITEM_EXPEL_DATE = "expel_date";			/* �������� */
	public static final String ITEM_INSPE_FALSE = "inspe_false";			/* �Ƿ���� */
	public static final String ITEM_OUT_FLAG = "out_flag";			/* �Ƿ�Ƿ�Ǩ�� */
	public static final String ITEM_INSPE_FLAG = "inspe_flag";			/* ����־ */
	public static final String ITEM_LINK_MAN_ADD = "link_man_add";			/* ������ϵ�� */
	public static final String ITEM_LINK_PHONE_ADD = "link_phone_add";			/* ������ϵ�绰 */
	public static final String ITEM_REG_RE = "reg_re";			/* �Ǽ����� */
	public static final String ITEM_ADM_RE = "adm_re";			/* ��Ͻ���� */
	public static final String ITEM_ADM_ORG = "adm_org";			/* ��Ͻ�־� */
	public static final String ITEM_ADM_ORG_DEP = "adm_org_dep";			/* ��Ͻ������ */
	public static final String ITEM_OP_SCOPE = "op_scope";			/* ��Ӫ��Χ */
	public static final String ITEM_REG_NO = "reg_no";			/* ע��� */
	public static final String ITEM_ENT_TITLE = "ent_title";			/* �������� */
	public static final String ITEM_LAW_MAN = "law_man";			/* ���������� */
	public static final String ITEM_WORD_NO = "word_no";			/* �ֺ� */
	public static final String ITEM_INDUSTRY_II_CO = "industry_ii_co";			/* ��ҵС����� */
	public static final String ITEM_INDUSTRY_I_CO = "industry_i_co";			/* ��ҵ������� */
	public static final String ITEM_ENT_ATTRI = "ent_attri";			/* ��ҵ���� */
	public static final String ITEM_ADDR = "addr";			/* ס�� */
	public static final String ITEM_REG_CAP_REMA = "reg_cap_rema";			/* ע���ʱ�˵�� */
	public static final String ITEM_REG_CAP = "reg_cap";			/* ע���ʱ� */
	public static final String ITEM_MONEY_TYPE = "money_type";			/* ���� */
	public static final String ITEM_CAP_TOTAL_DOLLAR = "cap_total_dollar";			/* Ͷ���ܶ�������Ԫ */
	public static final String ITEM_FOR_CAP_DOLLAR = "for_cap_dollar";			/* �ⷽͶ��������Ԫ */
	public static final String ITEM_D_OR_F = "d_or_f";			/* ����(dm) */
	public static final String ITEM_MOR_EXC_DATE = "mor_exc_date";			/* ������� */
	public static final String ITEM_LAST_OP_DATE = "last_op_date";			/* ���������� */
	public static final String ITEM_FOUND_DATE = "found_date";			/* �������� */
	public static final String ITEM_LICEN_TIME = "licen_time";			/* ִ����Ч�� */
	public static final String ITEM_MAIN_TYPE = "main_type";			/* �������� */
	public static final String ITEM_L_CARD_NO = "l_card_no";			/* �������������֤�� */
	public static final String ITEM_INDUSTRY_CLASS = "industry_class";			/* ��ҵ���� */
	public static final String ITEM_CRED_RATI = "cred_rati";			/* ���õȼ� */
	public static final String ITEM_REG_DATE = "reg_date";			/* �Ǽ�ʱ�� */
	public static final String ITEM_LINK_MAN = "link_man";			/* ��ϵ�� */
	public static final String ITEM_LINK_TELE = "link_tele";			/* ��ϵ�绰 */
	public static final String ITEM_MAIN_CLASS = "main_class";			/* ������� */
	public static final String ITEM_ECON_TYPE = "econ_type";			/* �������� */
	public static final String ITEM_REG_CAP_DOLLAR = "reg_cap_dollar";			/* ע���ʱ�����Ԫ */
	public static final String ITEM_REG_DEP = "reg_dep";			/* �Ǽǲ��� */
	public static final String ITEM_TEMP_REG_NO = "temp_reg_no";			/* ��ʱע��� */
	public static final String ITEM_OP_TO_TIME = "op_to_time";			/* ��Ӫ������ */
	public static final String ITEM_EXIST_STATE_CHA = "exist_state_cha";			/* ����״̬��� */
	public static final String ITEM_CHA_NEW_FLAG = "cha_new_flag";			/* �����������־ */
	public static final String ITEM_EXAM_FLAG = "exam_flag";			/* �μ��־ */
	public static final String ITEM_REVOK_DATE_FLAG = "revok_date_flag";			/* �������ݱ�� */
	public static final String ITEM_LINK_PHONE_BACKUP = "link_phone_backup";			/* ��ϵ�绰backup */
	public static final String ITEM_EXAM_FLAG_OLD = "exam_flag_old";			/* �μ��־odl */
	public static final String ITEM_NSPE_FLAGBACKUP = "nspe_flagbackup";			/* ����־BACKUP */

	public VoMonMainBasic(DataBus value)
	{
		super(value);
	}

	public VoMonMainBasic()
	{
		super();
	}

	/* ���������Ϣ��ID */
	public String getMon_main_basic_id()
	{
		return getValue( ITEM_MON_MAIN_BASIC_ID );
	}

	public void setMon_main_basic_id( String mon_main_basic_id1 )
	{
		setValue( ITEM_MON_MAIN_BASIC_ID, mon_main_basic_id1 );
	}

	/* ����ID */
	public String getMain_id()
	{
		return getValue( ITEM_MAIN_ID );
	}

	public void setMain_id( String main_id1 )
	{
		setValue( ITEM_MAIN_ID, main_id1 );
	}

	/* ����״̬(dm) */
	public String getGrid_id()
	{
		return getValue( ITEM_GRID_ID );
	}

	public void setGrid_id( String grid_id1 )
	{
		setValue( ITEM_GRID_ID, grid_id1 );
	}

	/* ����ʱ�� */
	public String getExist_state()
	{
		return getValue( ITEM_EXIST_STATE );
	}

	public void setExist_state( String exist_state1 )
	{
		setValue( ITEM_EXIST_STATE, exist_state1 );
	}

	/* ����ʱ�� */
	public String getCla_time()
	{
		return getValue( ITEM_CLA_TIME );
	}

	public void setCla_time( String cla_time1 )
	{
		setValue( ITEM_CLA_TIME, cla_time1 );
	}

	/* �оַַ��նȼ��� */
	public String getGa_risk_rank()
	{
		return getValue( ITEM_GA_RISK_RANK );
	}

	public void setGa_risk_rank( String ga_risk_rank1 )
	{
		setValue( ITEM_GA_RISK_RANK, ga_risk_rank1 );
	}

	/* �־ַ��նȼ���(dm) */
	public String getSub_risk_rank()
	{
		return getValue( ITEM_SUB_RISK_RANK );
	}

	public void setSub_risk_rank( String sub_risk_rank1 )
	{
		setValue( ITEM_SUB_RISK_RANK, sub_risk_rank1 );
	}

	/* ���������նȼ���(dm) */
	public String getIci_risk_rank()
	{
		return getValue( ITEM_ICI_RISK_RANK );
	}

	public void setIci_risk_rank( String ici_risk_rank1 )
	{
		setValue( ITEM_ICI_RISK_RANK, ici_risk_rank1 );
	}

	/* �Ƿ���Ҫ���ڼ��(dm) */
	public String getNeed_early_che()
	{
		return getValue( ITEM_NEED_EARLY_CHE );
	}

	public void setNeed_early_che( String need_early_che1 )
	{
		setValue( ITEM_NEED_EARLY_CHE, need_early_che1 );
	}

	/* ���弶��(dm) */
	public String getEnt_rank()
	{
		return getValue( ITEM_ENT_RANK );
	}

	public void setEnt_rank( String ent_rank1 )
	{
		setValue( ITEM_ENT_RANK, ent_rank1 );
	}

	/* Ѳ�鼶��(dm) */
	public String getMoni_rank()
	{
		return getValue( ITEM_MONI_RANK );
	}

	public void setMoni_rank( String moni_rank1 )
	{
		setValue( ITEM_MONI_RANK, moni_rank1 );
	}

	/* �Ƿ������ص���ҵ�ص����(dm) */
	public String getImp_ind_area()
	{
		return getValue( ITEM_IMP_IND_AREA );
	}

	public void setImp_ind_area( String imp_ind_area1 )
	{
		setValue( ITEM_IMP_IND_AREA, imp_ind_area1 );
	}

	/* ��ҵ���� */
	public String getEnt_type()
	{
		return getValue( ITEM_ENT_TYPE );
	}

	public void setEnt_type( String ent_type1 )
	{
		setValue( ITEM_ENT_TYPE, ent_type1 );
	}

	/* ��첿�Ŵ��� */
	public String getInspe_dept_code()
	{
		return getValue( ITEM_INSPE_DEPT_CODE );
	}

	public void setInspe_dept_code( String inspe_dept_code1 )
	{
		setValue( ITEM_INSPE_DEPT_CODE, inspe_dept_code1 );
	}

	/* �˶���Ϣ��־ */
	public String getCheck_flag()
	{
		return getValue( ITEM_CHECK_FLAG );
	}

	public void setCheck_flag( String check_flag1 )
	{
		setValue( ITEM_CHECK_FLAG, check_flag1 );
	}

	/* ���ʦ��������־ */
	public String getAccou_firm_flag()
	{
		return getValue( ITEM_ACCOU_FIRM_FLAG );
	}

	public void setAccou_firm_flag( String accou_firm_flag1 )
	{
		setValue( ITEM_ACCOU_FIRM_FLAG, accou_firm_flag1 );
	}

	/* ����״̬��¼ */
	public String getLive_state()
	{
		return getValue( ITEM_LIVE_STATE );
	}

	public void setLive_state( String live_state1 )
	{
		setValue( ITEM_LIVE_STATE, live_state1 );
	}

	/* �������� */
	public String getExpel_date()
	{
		return getValue( ITEM_EXPEL_DATE );
	}

	public void setExpel_date( String expel_date1 )
	{
		setValue( ITEM_EXPEL_DATE, expel_date1 );
	}

	/* �Ƿ���� */
	public String getInspe_false()
	{
		return getValue( ITEM_INSPE_FALSE );
	}

	public void setInspe_false( String inspe_false1 )
	{
		setValue( ITEM_INSPE_FALSE, inspe_false1 );
	}

	/* �Ƿ�Ƿ�Ǩ�� */
	public String getOut_flag()
	{
		return getValue( ITEM_OUT_FLAG );
	}

	public void setOut_flag( String out_flag1 )
	{
		setValue( ITEM_OUT_FLAG, out_flag1 );
	}

	/* ����־ */
	public String getInspe_flag()
	{
		return getValue( ITEM_INSPE_FLAG );
	}

	public void setInspe_flag( String inspe_flag1 )
	{
		setValue( ITEM_INSPE_FLAG, inspe_flag1 );
	}

	/* ������ϵ�� */
	public String getLink_man_add()
	{
		return getValue( ITEM_LINK_MAN_ADD );
	}

	public void setLink_man_add( String link_man_add1 )
	{
		setValue( ITEM_LINK_MAN_ADD, link_man_add1 );
	}

	/* ������ϵ�绰 */
	public String getLink_phone_add()
	{
		return getValue( ITEM_LINK_PHONE_ADD );
	}

	public void setLink_phone_add( String link_phone_add1 )
	{
		setValue( ITEM_LINK_PHONE_ADD, link_phone_add1 );
	}

	/* �Ǽ����� */
	public String getReg_re()
	{
		return getValue( ITEM_REG_RE );
	}

	public void setReg_re( String reg_re1 )
	{
		setValue( ITEM_REG_RE, reg_re1 );
	}

	/* ��Ͻ���� */
	public String getAdm_re()
	{
		return getValue( ITEM_ADM_RE );
	}

	public void setAdm_re( String adm_re1 )
	{
		setValue( ITEM_ADM_RE, adm_re1 );
	}

	/* ��Ͻ�־� */
	public String getAdm_org()
	{
		return getValue( ITEM_ADM_ORG );
	}

	public void setAdm_org( String adm_org1 )
	{
		setValue( ITEM_ADM_ORG, adm_org1 );
	}

	/* ��Ͻ������ */
	public String getAdm_org_dep()
	{
		return getValue( ITEM_ADM_ORG_DEP );
	}

	public void setAdm_org_dep( String adm_org_dep1 )
	{
		setValue( ITEM_ADM_ORG_DEP, adm_org_dep1 );
	}

	/* ��Ӫ��Χ */
	public String getOp_scope()
	{
		return getValue( ITEM_OP_SCOPE );
	}

	public void setOp_scope( String op_scope1 )
	{
		setValue( ITEM_OP_SCOPE, op_scope1 );
	}

	/* ע��� */
	public String getReg_no()
	{
		return getValue( ITEM_REG_NO );
	}

	public void setReg_no( String reg_no1 )
	{
		setValue( ITEM_REG_NO, reg_no1 );
	}

	/* �������� */
	public String getEnt_title()
	{
		return getValue( ITEM_ENT_TITLE );
	}

	public void setEnt_title( String ent_title1 )
	{
		setValue( ITEM_ENT_TITLE, ent_title1 );
	}

	/* ���������� */
	public String getLaw_man()
	{
		return getValue( ITEM_LAW_MAN );
	}

	public void setLaw_man( String law_man1 )
	{
		setValue( ITEM_LAW_MAN, law_man1 );
	}

	/* �ֺ� */
	public String getWord_no()
	{
		return getValue( ITEM_WORD_NO );
	}

	public void setWord_no( String word_no1 )
	{
		setValue( ITEM_WORD_NO, word_no1 );
	}

	/* ��ҵС����� */
	public String getIndustry_ii_co()
	{
		return getValue( ITEM_INDUSTRY_II_CO );
	}

	public void setIndustry_ii_co( String industry_ii_co1 )
	{
		setValue( ITEM_INDUSTRY_II_CO, industry_ii_co1 );
	}

	/* ��ҵ������� */
	public String getIndustry_i_co()
	{
		return getValue( ITEM_INDUSTRY_I_CO );
	}

	public void setIndustry_i_co( String industry_i_co1 )
	{
		setValue( ITEM_INDUSTRY_I_CO, industry_i_co1 );
	}

	/* ��ҵ���� */
	public String getEnt_attri()
	{
		return getValue( ITEM_ENT_ATTRI );
	}

	public void setEnt_attri( String ent_attri1 )
	{
		setValue( ITEM_ENT_ATTRI, ent_attri1 );
	}

	/* ס�� */
	public String getAddr()
	{
		return getValue( ITEM_ADDR );
	}

	public void setAddr( String addr1 )
	{
		setValue( ITEM_ADDR, addr1 );
	}

	/* ע���ʱ�˵�� */
	public String getReg_cap_rema()
	{
		return getValue( ITEM_REG_CAP_REMA );
	}

	public void setReg_cap_rema( String reg_cap_rema1 )
	{
		setValue( ITEM_REG_CAP_REMA, reg_cap_rema1 );
	}

	/* ע���ʱ� */
	public String getReg_cap()
	{
		return getValue( ITEM_REG_CAP );
	}

	public void setReg_cap( String reg_cap1 )
	{
		setValue( ITEM_REG_CAP, reg_cap1 );
	}

	/* ���� */
	public String getMoney_type()
	{
		return getValue( ITEM_MONEY_TYPE );
	}

	public void setMoney_type( String money_type1 )
	{
		setValue( ITEM_MONEY_TYPE, money_type1 );
	}

	/* Ͷ���ܶ�������Ԫ */
	public String getCap_total_dollar()
	{
		return getValue( ITEM_CAP_TOTAL_DOLLAR );
	}

	public void setCap_total_dollar( String cap_total_dollar1 )
	{
		setValue( ITEM_CAP_TOTAL_DOLLAR, cap_total_dollar1 );
	}

	/* �ⷽͶ��������Ԫ */
	public String getFor_cap_dollar()
	{
		return getValue( ITEM_FOR_CAP_DOLLAR );
	}

	public void setFor_cap_dollar( String for_cap_dollar1 )
	{
		setValue( ITEM_FOR_CAP_DOLLAR, for_cap_dollar1 );
	}

	/* ����(dm) */
	public String getD_or_f()
	{
		return getValue( ITEM_D_OR_F );
	}

	public void setD_or_f( String d_or_f1 )
	{
		setValue( ITEM_D_OR_F, d_or_f1 );
	}

	/* ������� */
	public String getMor_exc_date()
	{
		return getValue( ITEM_MOR_EXC_DATE );
	}

	public void setMor_exc_date( String mor_exc_date1 )
	{
		setValue( ITEM_MOR_EXC_DATE, mor_exc_date1 );
	}

	/* ���������� */
	public String getLast_op_date()
	{
		return getValue( ITEM_LAST_OP_DATE );
	}

	public void setLast_op_date( String last_op_date1 )
	{
		setValue( ITEM_LAST_OP_DATE, last_op_date1 );
	}

	/* �������� */
	public String getFound_date()
	{
		return getValue( ITEM_FOUND_DATE );
	}

	public void setFound_date( String found_date1 )
	{
		setValue( ITEM_FOUND_DATE, found_date1 );
	}

	/* ִ����Ч�� */
	public String getLicen_time()
	{
		return getValue( ITEM_LICEN_TIME );
	}

	public void setLicen_time( String licen_time1 )
	{
		setValue( ITEM_LICEN_TIME, licen_time1 );
	}

	/* �������� */
	public String getMain_type()
	{
		return getValue( ITEM_MAIN_TYPE );
	}

	public void setMain_type( String main_type1 )
	{
		setValue( ITEM_MAIN_TYPE, main_type1 );
	}

	/* �������������֤�� */
	public String getL_card_no()
	{
		return getValue( ITEM_L_CARD_NO );
	}

	public void setL_card_no( String l_card_no1 )
	{
		setValue( ITEM_L_CARD_NO, l_card_no1 );
	}

	/* ��ҵ���� */
	public String getIndustry_class()
	{
		return getValue( ITEM_INDUSTRY_CLASS );
	}

	public void setIndustry_class( String industry_class1 )
	{
		setValue( ITEM_INDUSTRY_CLASS, industry_class1 );
	}

	/* ���õȼ� */
	public String getCred_rati()
	{
		return getValue( ITEM_CRED_RATI );
	}

	public void setCred_rati( String cred_rati1 )
	{
		setValue( ITEM_CRED_RATI, cred_rati1 );
	}

	/* �Ǽ�ʱ�� */
	public String getReg_date()
	{
		return getValue( ITEM_REG_DATE );
	}

	public void setReg_date( String reg_date1 )
	{
		setValue( ITEM_REG_DATE, reg_date1 );
	}

	/* ��ϵ�� */
	public String getLink_man()
	{
		return getValue( ITEM_LINK_MAN );
	}

	public void setLink_man( String link_man1 )
	{
		setValue( ITEM_LINK_MAN, link_man1 );
	}

	/* ��ϵ�绰 */
	public String getLink_tele()
	{
		return getValue( ITEM_LINK_TELE );
	}

	public void setLink_tele( String link_tele1 )
	{
		setValue( ITEM_LINK_TELE, link_tele1 );
	}

	/* ������� */
	public String getMain_class()
	{
		return getValue( ITEM_MAIN_CLASS );
	}

	public void setMain_class( String main_class1 )
	{
		setValue( ITEM_MAIN_CLASS, main_class1 );
	}

	/* �������� */
	public String getEcon_type()
	{
		return getValue( ITEM_ECON_TYPE );
	}

	public void setEcon_type( String econ_type1 )
	{
		setValue( ITEM_ECON_TYPE, econ_type1 );
	}

	/* ע���ʱ�����Ԫ */
	public String getReg_cap_dollar()
	{
		return getValue( ITEM_REG_CAP_DOLLAR );
	}

	public void setReg_cap_dollar( String reg_cap_dollar1 )
	{
		setValue( ITEM_REG_CAP_DOLLAR, reg_cap_dollar1 );
	}

	/* �Ǽǲ��� */
	public String getReg_dep()
	{
		return getValue( ITEM_REG_DEP );
	}

	public void setReg_dep( String reg_dep1 )
	{
		setValue( ITEM_REG_DEP, reg_dep1 );
	}

	/* ��ʱע��� */
	public String getTemp_reg_no()
	{
		return getValue( ITEM_TEMP_REG_NO );
	}

	public void setTemp_reg_no( String temp_reg_no1 )
	{
		setValue( ITEM_TEMP_REG_NO, temp_reg_no1 );
	}

	/* ��Ӫ������ */
	public String getOp_to_time()
	{
		return getValue( ITEM_OP_TO_TIME );
	}

	public void setOp_to_time( String op_to_time1 )
	{
		setValue( ITEM_OP_TO_TIME, op_to_time1 );
	}

	/* ����״̬��� */
	public String getExist_state_cha()
	{
		return getValue( ITEM_EXIST_STATE_CHA );
	}

	public void setExist_state_cha( String exist_state_cha1 )
	{
		setValue( ITEM_EXIST_STATE_CHA, exist_state_cha1 );
	}

	/* �����������־ */
	public String getCha_new_flag()
	{
		return getValue( ITEM_CHA_NEW_FLAG );
	}

	public void setCha_new_flag( String cha_new_flag1 )
	{
		setValue( ITEM_CHA_NEW_FLAG, cha_new_flag1 );
	}

	/* �μ��־ */
	public String getExam_flag()
	{
		return getValue( ITEM_EXAM_FLAG );
	}

	public void setExam_flag( String exam_flag1 )
	{
		setValue( ITEM_EXAM_FLAG, exam_flag1 );
	}

	/* �������ݱ�� */
	public String getRevok_date_flag()
	{
		return getValue( ITEM_REVOK_DATE_FLAG );
	}

	public void setRevok_date_flag( String revok_date_flag1 )
	{
		setValue( ITEM_REVOK_DATE_FLAG, revok_date_flag1 );
	}

	/* ��ϵ�绰backup */
	public String getLink_phone_backup()
	{
		return getValue( ITEM_LINK_PHONE_BACKUP );
	}

	public void setLink_phone_backup( String link_phone_backup1 )
	{
		setValue( ITEM_LINK_PHONE_BACKUP, link_phone_backup1 );
	}

	/* �μ��־odl */
	public String getExam_flag_old()
	{
		return getValue( ITEM_EXAM_FLAG_OLD );
	}

	public void setExam_flag_old( String exam_flag_old1 )
	{
		setValue( ITEM_EXAM_FLAG_OLD, exam_flag_old1 );
	}

	/* ����־BACKUP */
	public String getNspe_flagbackup()
	{
		return getValue( ITEM_NSPE_FLAGBACKUP );
	}

	public void setNspe_flagbackup( String nspe_flagbackup1 )
	{
		setValue( ITEM_NSPE_FLAGBACKUP, nspe_flagbackup1 );
	}

}

