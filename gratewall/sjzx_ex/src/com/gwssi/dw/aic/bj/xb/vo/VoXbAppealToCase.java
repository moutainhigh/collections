package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXbAppealToCase extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_XB_APPEAL_TO_CASE_ID = "xb_appeal_to_case_id";			/* ����ת����ID */
	public static final String ITEM_XB_APPEAL_RESULT_ID = "xb_appeal_result_id";			/* ���ߴ���ID */
	public static final String ITEM_APL_IS_CLO_CASE = "apl_is_clo_case";			/* �Ƿ����� */
	public static final String ITEM_CLO_CASE_REA = "clo_case_rea";			/* ����ԭ�� */
	public static final String ITEM_CLO_CASE_DATE = "clo_case_date";			/* ����ʱ�� */
	public static final String ITEM_APL_QUA_BASIS = "apl_qua_basis";			/* Υ������ */
	public static final String ITEM_APL_CASE_TYPE = "apl_case_type";			/* �������� */
	public static final String ITEM_CASE_CHR = "case_chr";			/* �������� */
	public static final String ITEM_APP_PROCEDURE = "app_procedure";			/* ��Ӧ���� */
	public static final String ITEM_PEN_TYPE = "pen_type";			/* ������ʽ */
	public static final String ITEM_CASE_VAL = "case_val";			/* ��ֵ */
	public static final String ITEM_APL_FOFF_SUM = "apl_foff_sum";			/* û�շǷ����� */
	public static final String ITEM_PEN_AM = "pen_am";			/* ������ */
	public static final String ITEM_FORF_AM = "forf_am";			/* û�ս�� */
	public static final String ITEM_APL_FORF_GOODS_NUM = "apl_forf_goods_num";			/* û����Ʒ���� */
	public static final String ITEM_APL_FORF_GOODS_UNIT = "apl_forf_goods_unit";			/* û����Ʒ��λ */
	public static final String ITEM_APL_FORF_GOODS_VALUE = "apl_forf_goods_value";			/* û����Ʒ��ֵ */
	public static final String ITEM_APL_IS_DESTROY = "apl_is_destroy";			/* �Ƿ����� */
	public static final String ITEM_APL_DEST_SUM = "apl_dest_sum";			/* �����ѵ���� */
	public static final String ITEM_SAC_NUM = "sac_num";			/* ִ���������� */
	public static final String ITEM_INJ_NUM = "inj_num";			/* ִ���������� */
	public static final String ITEM_APL_ATTACK_SUM = "apl_attack_sum";			/* �������� */
	public static final String ITEM_APL_BLOCK_CASE = "apl_block_case";			/* ���谸�� */
	public static final String ITEM_APL_CASE_BLOCKED = "apl_case_blocked";			/* �������� */
	public static final String ITEM_EXE_SORT = "exe_sort";			/* ����ִ����� */
	public static final String ITEM_UNEXE_REA_SORT = "unexe_rea_sort";			/* �������Խ᰸ִ��ԭ�� */
	public static final String ITEM_APL_UNEXE_REA_AM = "apl_unexe_rea_am";			/* ���Խ᰸�漰��� */
	public static final String ITEM_APL_UNEXE_REA_PEN_AM = "apl_unexe_rea_pen_am";			/* ���Խ᰸�漰��û��� */
	public static final String ITEM_APL_DETECT_COST = "apl_detect_cost";			/* ������ */
	public static final String ITEM_APL_ADDI_MATERIAL = "apl_addi_material";			/* �������� */
	public static final String ITEM_APL_ADDI_MATERIAL_DETAIL = "apl_addi_material_detail";			/* ��������ϸ�� */
	public static final String ITEM_HAND_DEP = "hand_dep";			/* �참��λ */
	public static final String ITEM_CASE_FI_DATE = "case_fi_date";			/* �참���� */
	public static final String ITEM_END_CAS_DATE = "end_cas_date";			/* �᰸���� */
	public static final String ITEM_TRAN = "tran";			/* �����а��� */
	public static final String ITEM_APL_LAST_RECORD_DATE = "apl_last_record_date";			/* ���ʱ�� */
	public static final String ITEM_APL_OTHE_CASE = "apl_othe_case";			/* �������� */
	public static final String ITEM_APL_OTHE_CASE_CONT = "apl_othe_case_cont";			/* ������������ */
	public static final String ITEM_ETL_ID = "etl_id";			/* ETL���к� */
	public static final String ITEM_ETL_FLAG = "etl_flag";			/* ETL����״̬ */
	public static final String ITEM_ETL_TIMESTAMP = "etl_timestamp";			/* ETLʱ��� */

	public VoXbAppealToCase(DataBus value)
	{
		super(value);
	}

	public VoXbAppealToCase()
	{
		super();
	}

	/* ����ת����ID */
	public String getXb_appeal_to_case_id()
	{
		return getValue( ITEM_XB_APPEAL_TO_CASE_ID );
	}

	public void setXb_appeal_to_case_id( String xb_appeal_to_case_id1 )
	{
		setValue( ITEM_XB_APPEAL_TO_CASE_ID, xb_appeal_to_case_id1 );
	}

	/* ���ߴ���ID */
	public String getXb_appeal_result_id()
	{
		return getValue( ITEM_XB_APPEAL_RESULT_ID );
	}

	public void setXb_appeal_result_id( String xb_appeal_result_id1 )
	{
		setValue( ITEM_XB_APPEAL_RESULT_ID, xb_appeal_result_id1 );
	}

	/* �Ƿ����� */
	public String getApl_is_clo_case()
	{
		return getValue( ITEM_APL_IS_CLO_CASE );
	}

	public void setApl_is_clo_case( String apl_is_clo_case1 )
	{
		setValue( ITEM_APL_IS_CLO_CASE, apl_is_clo_case1 );
	}

	/* ����ԭ�� */
	public String getClo_case_rea()
	{
		return getValue( ITEM_CLO_CASE_REA );
	}

	public void setClo_case_rea( String clo_case_rea1 )
	{
		setValue( ITEM_CLO_CASE_REA, clo_case_rea1 );
	}

	/* ����ʱ�� */
	public String getClo_case_date()
	{
		return getValue( ITEM_CLO_CASE_DATE );
	}

	public void setClo_case_date( String clo_case_date1 )
	{
		setValue( ITEM_CLO_CASE_DATE, clo_case_date1 );
	}

	/* Υ������ */
	public String getApl_qua_basis()
	{
		return getValue( ITEM_APL_QUA_BASIS );
	}

	public void setApl_qua_basis( String apl_qua_basis1 )
	{
		setValue( ITEM_APL_QUA_BASIS, apl_qua_basis1 );
	}

	/* �������� */
	public String getApl_case_type()
	{
		return getValue( ITEM_APL_CASE_TYPE );
	}

	public void setApl_case_type( String apl_case_type1 )
	{
		setValue( ITEM_APL_CASE_TYPE, apl_case_type1 );
	}

	/* �������� */
	public String getCase_chr()
	{
		return getValue( ITEM_CASE_CHR );
	}

	public void setCase_chr( String case_chr1 )
	{
		setValue( ITEM_CASE_CHR, case_chr1 );
	}

	/* ��Ӧ���� */
	public String getApp_procedure()
	{
		return getValue( ITEM_APP_PROCEDURE );
	}

	public void setApp_procedure( String app_procedure1 )
	{
		setValue( ITEM_APP_PROCEDURE, app_procedure1 );
	}

	/* ������ʽ */
	public String getPen_type()
	{
		return getValue( ITEM_PEN_TYPE );
	}

	public void setPen_type( String pen_type1 )
	{
		setValue( ITEM_PEN_TYPE, pen_type1 );
	}

	/* ��ֵ */
	public String getCase_val()
	{
		return getValue( ITEM_CASE_VAL );
	}

	public void setCase_val( String case_val1 )
	{
		setValue( ITEM_CASE_VAL, case_val1 );
	}

	/* û�շǷ����� */
	public String getApl_foff_sum()
	{
		return getValue( ITEM_APL_FOFF_SUM );
	}

	public void setApl_foff_sum( String apl_foff_sum1 )
	{
		setValue( ITEM_APL_FOFF_SUM, apl_foff_sum1 );
	}

	/* ������ */
	public String getPen_am()
	{
		return getValue( ITEM_PEN_AM );
	}

	public void setPen_am( String pen_am1 )
	{
		setValue( ITEM_PEN_AM, pen_am1 );
	}

	/* û�ս�� */
	public String getForf_am()
	{
		return getValue( ITEM_FORF_AM );
	}

	public void setForf_am( String forf_am1 )
	{
		setValue( ITEM_FORF_AM, forf_am1 );
	}

	/* û����Ʒ���� */
	public String getApl_forf_goods_num()
	{
		return getValue( ITEM_APL_FORF_GOODS_NUM );
	}

	public void setApl_forf_goods_num( String apl_forf_goods_num1 )
	{
		setValue( ITEM_APL_FORF_GOODS_NUM, apl_forf_goods_num1 );
	}

	/* û����Ʒ��λ */
	public String getApl_forf_goods_unit()
	{
		return getValue( ITEM_APL_FORF_GOODS_UNIT );
	}

	public void setApl_forf_goods_unit( String apl_forf_goods_unit1 )
	{
		setValue( ITEM_APL_FORF_GOODS_UNIT, apl_forf_goods_unit1 );
	}

	/* û����Ʒ��ֵ */
	public String getApl_forf_goods_value()
	{
		return getValue( ITEM_APL_FORF_GOODS_VALUE );
	}

	public void setApl_forf_goods_value( String apl_forf_goods_value1 )
	{
		setValue( ITEM_APL_FORF_GOODS_VALUE, apl_forf_goods_value1 );
	}

	/* �Ƿ����� */
	public String getApl_is_destroy()
	{
		return getValue( ITEM_APL_IS_DESTROY );
	}

	public void setApl_is_destroy( String apl_is_destroy1 )
	{
		setValue( ITEM_APL_IS_DESTROY, apl_is_destroy1 );
	}

	/* �����ѵ���� */
	public String getApl_dest_sum()
	{
		return getValue( ITEM_APL_DEST_SUM );
	}

	public void setApl_dest_sum( String apl_dest_sum1 )
	{
		setValue( ITEM_APL_DEST_SUM, apl_dest_sum1 );
	}

	/* ִ���������� */
	public String getSac_num()
	{
		return getValue( ITEM_SAC_NUM );
	}

	public void setSac_num( String sac_num1 )
	{
		setValue( ITEM_SAC_NUM, sac_num1 );
	}

	/* ִ���������� */
	public String getInj_num()
	{
		return getValue( ITEM_INJ_NUM );
	}

	public void setInj_num( String inj_num1 )
	{
		setValue( ITEM_INJ_NUM, inj_num1 );
	}

	/* �������� */
	public String getApl_attack_sum()
	{
		return getValue( ITEM_APL_ATTACK_SUM );
	}

	public void setApl_attack_sum( String apl_attack_sum1 )
	{
		setValue( ITEM_APL_ATTACK_SUM, apl_attack_sum1 );
	}

	/* ���谸�� */
	public String getApl_block_case()
	{
		return getValue( ITEM_APL_BLOCK_CASE );
	}

	public void setApl_block_case( String apl_block_case1 )
	{
		setValue( ITEM_APL_BLOCK_CASE, apl_block_case1 );
	}

	/* �������� */
	public String getApl_case_blocked()
	{
		return getValue( ITEM_APL_CASE_BLOCKED );
	}

	public void setApl_case_blocked( String apl_case_blocked1 )
	{
		setValue( ITEM_APL_CASE_BLOCKED, apl_case_blocked1 );
	}

	/* ����ִ����� */
	public String getExe_sort()
	{
		return getValue( ITEM_EXE_SORT );
	}

	public void setExe_sort( String exe_sort1 )
	{
		setValue( ITEM_EXE_SORT, exe_sort1 );
	}

	/* �������Խ᰸ִ��ԭ�� */
	public String getUnexe_rea_sort()
	{
		return getValue( ITEM_UNEXE_REA_SORT );
	}

	public void setUnexe_rea_sort( String unexe_rea_sort1 )
	{
		setValue( ITEM_UNEXE_REA_SORT, unexe_rea_sort1 );
	}

	/* ���Խ᰸�漰��� */
	public String getApl_unexe_rea_am()
	{
		return getValue( ITEM_APL_UNEXE_REA_AM );
	}

	public void setApl_unexe_rea_am( String apl_unexe_rea_am1 )
	{
		setValue( ITEM_APL_UNEXE_REA_AM, apl_unexe_rea_am1 );
	}

	/* ���Խ᰸�漰��û��� */
	public String getApl_unexe_rea_pen_am()
	{
		return getValue( ITEM_APL_UNEXE_REA_PEN_AM );
	}

	public void setApl_unexe_rea_pen_am( String apl_unexe_rea_pen_am1 )
	{
		setValue( ITEM_APL_UNEXE_REA_PEN_AM, apl_unexe_rea_pen_am1 );
	}

	/* ������ */
	public String getApl_detect_cost()
	{
		return getValue( ITEM_APL_DETECT_COST );
	}

	public void setApl_detect_cost( String apl_detect_cost1 )
	{
		setValue( ITEM_APL_DETECT_COST, apl_detect_cost1 );
	}

	/* �������� */
	public String getApl_addi_material()
	{
		return getValue( ITEM_APL_ADDI_MATERIAL );
	}

	public void setApl_addi_material( String apl_addi_material1 )
	{
		setValue( ITEM_APL_ADDI_MATERIAL, apl_addi_material1 );
	}

	/* ��������ϸ�� */
	public String getApl_addi_material_detail()
	{
		return getValue( ITEM_APL_ADDI_MATERIAL_DETAIL );
	}

	public void setApl_addi_material_detail( String apl_addi_material_detail1 )
	{
		setValue( ITEM_APL_ADDI_MATERIAL_DETAIL, apl_addi_material_detail1 );
	}

	/* �참��λ */
	public String getHand_dep()
	{
		return getValue( ITEM_HAND_DEP );
	}

	public void setHand_dep( String hand_dep1 )
	{
		setValue( ITEM_HAND_DEP, hand_dep1 );
	}

	/* �참���� */
	public String getCase_fi_date()
	{
		return getValue( ITEM_CASE_FI_DATE );
	}

	public void setCase_fi_date( String case_fi_date1 )
	{
		setValue( ITEM_CASE_FI_DATE, case_fi_date1 );
	}

	/* �᰸���� */
	public String getEnd_cas_date()
	{
		return getValue( ITEM_END_CAS_DATE );
	}

	public void setEnd_cas_date( String end_cas_date1 )
	{
		setValue( ITEM_END_CAS_DATE, end_cas_date1 );
	}

	/* �����а��� */
	public String getTran()
	{
		return getValue( ITEM_TRAN );
	}

	public void setTran( String tran1 )
	{
		setValue( ITEM_TRAN, tran1 );
	}

	/* ���ʱ�� */
	public String getApl_last_record_date()
	{
		return getValue( ITEM_APL_LAST_RECORD_DATE );
	}

	public void setApl_last_record_date( String apl_last_record_date1 )
	{
		setValue( ITEM_APL_LAST_RECORD_DATE, apl_last_record_date1 );
	}

	/* �������� */
	public String getApl_othe_case()
	{
		return getValue( ITEM_APL_OTHE_CASE );
	}

	public void setApl_othe_case( String apl_othe_case1 )
	{
		setValue( ITEM_APL_OTHE_CASE, apl_othe_case1 );
	}

	/* ������������ */
	public String getApl_othe_case_cont()
	{
		return getValue( ITEM_APL_OTHE_CASE_CONT );
	}

	public void setApl_othe_case_cont( String apl_othe_case_cont1 )
	{
		setValue( ITEM_APL_OTHE_CASE_CONT, apl_othe_case_cont1 );
	}

	/* ETL���к� */
	public String getEtl_id()
	{
		return getValue( ITEM_ETL_ID );
	}

	public void setEtl_id( String etl_id1 )
	{
		setValue( ITEM_ETL_ID, etl_id1 );
	}

	/* ETL����״̬ */
	public String getEtl_flag()
	{
		return getValue( ITEM_ETL_FLAG );
	}

	public void setEtl_flag( String etl_flag1 )
	{
		setValue( ITEM_ETL_FLAG, etl_flag1 );
	}

	/* ETLʱ��� */
	public String getEtl_timestamp()
	{
		return getValue( ITEM_ETL_TIMESTAMP );
	}

	public void setEtl_timestamp( String etl_timestamp1 )
	{
		setValue( ITEM_ETL_TIMESTAMP, etl_timestamp1 );
	}

}

