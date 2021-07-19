package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXbReportResult extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_XB_REPORT_RESULT_ID = "xb_report_result_id";			/* �ٱ�������ϢID */
	public static final String ITEM_XB_REPORT_BASE_ID = "xb_report_base_id";			/* �ٱ�������ϢID */
	public static final String ITEM_RPT_SURV_IS_TRUE = "rpt_surv_is_true";			/* ��ʵ��� */
	public static final String ITEM_RPT_SURV_DATE = "rpt_surv_date";			/* ����ʱ�� */
	public static final String ITEM_RPT_SURV_PER = "rpt_surv_per";			/* ������ */
	public static final String ITEM_RPT_SURV_SITU = "rpt_surv_situ";			/* ������� */
	public static final String ITEM_RPT_IS_CASE_FILE = "rpt_is_case_file";			/* �Ƿ����� */
	public static final String ITEM_APP_PROCEDURE = "app_procedure";			/* ��Ӧ���� */
	public static final String ITEM_CLO_CASE_REA = "clo_case_rea";			/* ����ԭ�� */
	public static final String ITEM_CASE_FI_DATE = "case_fi_date";			/* �참���� */
	public static final String ITEM_PEN_DEC_NO = "pen_dec_no";			/* ����������� */
	public static final String ITEM_CLO_CASE_DATE = "clo_case_date";			/* ����ʱ�� */
	public static final String ITEM_RPT_CLO_CASE_NO = "rpt_clo_case_no";			/* ������ */
	public static final String ITEM_RPT_CLO_CASE_APPR_PER = "rpt_clo_case_appr_per";			/* ������׼�� */
	public static final String ITEM_CASE_CHR = "case_chr";			/* �������� */
	public static final String ITEM_RPT_CASE_TYPE = "rpt_case_type";			/* �������� */
	public static final String ITEM_INFO_ORI = "info_ori";			/* ������Ϣ��Դ */
	public static final String ITEM_PEN_BASIS = "pen_basis";			/* �������� */
	public static final String ITEM_PEN_TYPE = "pen_type";			/* ������ʽ */
	public static final String ITEM_CASE_VAL = "case_val";			/* ��ֵ */
	public static final String ITEM_PEN_AM = "pen_am";			/* ������ */
	public static final String ITEM_FORF_AM = "forf_am";			/* û�ս�� */
	public static final String ITEM_RPT_PEN_FORF_SUM = "rpt_pen_forf_sum";			/* ��û�ܽ�� */
	public static final String ITEM_RPT_DEST_SUM = "rpt_dest_sum";			/* �����ѵ���� */
	public static final String ITEM_SP_ZON_TYPE = "sp_zon_type";			/* ���Ͱ��� */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_SUM = "rpt_trea_fake_goods_sum";			/* �鴦��ðα��������ֵ */
	public static final String ITEM_RPT_DEST_FAKE_GOODS_SUM = "rpt_dest_fake_goods_sum";			/* ���ټ�ðα��������ֵ */
	public static final String ITEM_RPT_TREA_FAKE_AGRI_GOODS_SUM = "rpt_trea_fake_agri_goods_sum";			/* �鴦��ðα��ũ����ֵ */
	public static final String ITEM_RPT_DEST_FAKE_AGRI_GOODS_SUM = "rpt_dest_fake_agri_goods_sum";			/* ���ټ�ðα��ũ����ֵ */
	public static final String ITEM_SAC_NUM = "sac_num";			/* ִ���������� */
	public static final String ITEM_INJ_NUM = "inj_num";			/* ִ���������� */
	public static final String ITEM_RPT_ATTACK_SUM = "rpt_attack_sum";			/* �������� */
	public static final String ITEM_RPT_BLOCK_CASE = "rpt_block_case";			/* ���谸�� */
	public static final String ITEM_RPT_CASE_BLOCK = "rpt_case_block";			/* �������� */
	public static final String ITEM_RPT_IS_AGRI_RES_CASE = "rpt_is_agri_res_case";			/* ũ�ʰ��� */
	public static final String ITEM_EXE_SORT = "exe_sort";			/* ����ִ����� */
	public static final String ITEM_UNEXE_REA_SORT = "unexe_rea_sort";			/* �������Խ᰸ִ��ԭ�� */
	public static final String ITEM_RPT_UNEXE_REA_AM = "rpt_unexe_rea_am";			/* ���Խ᰸�漰��� */
	public static final String ITEM_RPT_UNEXE_REA_PEN_AM = "rpt_unexe_rea_pen_am";			/* ����ִ�а����漰��û��� */
	public static final String ITEM_RPT_AWARD_SUM = "rpt_award_sum";			/* �ٱ��������ý�� */
	public static final String ITEM_RPT_DETECT_COST = "rpt_detect_cost";			/* ������ */
	public static final String ITEM_RPT_ADDI_MATERIAL = "rpt_addi_material";			/* �������� */
	public static final String ITEM_RPT_ADDI_MATERIAL_DETAIL = "rpt_addi_material_detail";			/* ��������ϸ�� */
	public static final String ITEM_HAND_DEP = "hand_dep";			/* �참��λ */
	public static final String ITEM_TRAN = "tran";			/* �����а��� */
	public static final String ITEM_END_CAS_DATE = "end_cas_date";			/* �᰸���� */
	public static final String ITEM_RPT_LAST_RECORD_DATE = "rpt_last_record_date";			/* ���ʱ�� */
	public static final String ITEM_RPT_OTHE_CASE = "rpt_othe_case";			/* �������� */
	public static final String ITEM_RPT_OTHE_CASE_CONT = "rpt_othe_case_cont";			/* ������������ */
	public static final String ITEM_ETL_ID = "etl_id";			/* ETL���к� */
	public static final String ITEM_ETL_FLAG = "etl_flag";			/* ETL����״̬ */
	public static final String ITEM_ETL_TIMESTAMP = "etl_timestamp";			/* ETLʱ��� */

	public VoXbReportResult(DataBus value)
	{
		super(value);
	}

	public VoXbReportResult()
	{
		super();
	}

	/* �ٱ�������ϢID */
	public String getXb_report_result_id()
	{
		return getValue( ITEM_XB_REPORT_RESULT_ID );
	}

	public void setXb_report_result_id( String xb_report_result_id1 )
	{
		setValue( ITEM_XB_REPORT_RESULT_ID, xb_report_result_id1 );
	}

	/* �ٱ�������ϢID */
	public String getXb_report_base_id()
	{
		return getValue( ITEM_XB_REPORT_BASE_ID );
	}

	public void setXb_report_base_id( String xb_report_base_id1 )
	{
		setValue( ITEM_XB_REPORT_BASE_ID, xb_report_base_id1 );
	}

	/* ��ʵ��� */
	public String getRpt_surv_is_true()
	{
		return getValue( ITEM_RPT_SURV_IS_TRUE );
	}

	public void setRpt_surv_is_true( String rpt_surv_is_true1 )
	{
		setValue( ITEM_RPT_SURV_IS_TRUE, rpt_surv_is_true1 );
	}

	/* ����ʱ�� */
	public String getRpt_surv_date()
	{
		return getValue( ITEM_RPT_SURV_DATE );
	}

	public void setRpt_surv_date( String rpt_surv_date1 )
	{
		setValue( ITEM_RPT_SURV_DATE, rpt_surv_date1 );
	}

	/* ������ */
	public String getRpt_surv_per()
	{
		return getValue( ITEM_RPT_SURV_PER );
	}

	public void setRpt_surv_per( String rpt_surv_per1 )
	{
		setValue( ITEM_RPT_SURV_PER, rpt_surv_per1 );
	}

	/* ������� */
	public String getRpt_surv_situ()
	{
		return getValue( ITEM_RPT_SURV_SITU );
	}

	public void setRpt_surv_situ( String rpt_surv_situ1 )
	{
		setValue( ITEM_RPT_SURV_SITU, rpt_surv_situ1 );
	}

	/* �Ƿ����� */
	public String getRpt_is_case_file()
	{
		return getValue( ITEM_RPT_IS_CASE_FILE );
	}

	public void setRpt_is_case_file( String rpt_is_case_file1 )
	{
		setValue( ITEM_RPT_IS_CASE_FILE, rpt_is_case_file1 );
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

	/* ����ԭ�� */
	public String getClo_case_rea()
	{
		return getValue( ITEM_CLO_CASE_REA );
	}

	public void setClo_case_rea( String clo_case_rea1 )
	{
		setValue( ITEM_CLO_CASE_REA, clo_case_rea1 );
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

	/* ����������� */
	public String getPen_dec_no()
	{
		return getValue( ITEM_PEN_DEC_NO );
	}

	public void setPen_dec_no( String pen_dec_no1 )
	{
		setValue( ITEM_PEN_DEC_NO, pen_dec_no1 );
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

	/* ������ */
	public String getRpt_clo_case_no()
	{
		return getValue( ITEM_RPT_CLO_CASE_NO );
	}

	public void setRpt_clo_case_no( String rpt_clo_case_no1 )
	{
		setValue( ITEM_RPT_CLO_CASE_NO, rpt_clo_case_no1 );
	}

	/* ������׼�� */
	public String getRpt_clo_case_appr_per()
	{
		return getValue( ITEM_RPT_CLO_CASE_APPR_PER );
	}

	public void setRpt_clo_case_appr_per( String rpt_clo_case_appr_per1 )
	{
		setValue( ITEM_RPT_CLO_CASE_APPR_PER, rpt_clo_case_appr_per1 );
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

	/* �������� */
	public String getRpt_case_type()
	{
		return getValue( ITEM_RPT_CASE_TYPE );
	}

	public void setRpt_case_type( String rpt_case_type1 )
	{
		setValue( ITEM_RPT_CASE_TYPE, rpt_case_type1 );
	}

	/* ������Ϣ��Դ */
	public String getInfo_ori()
	{
		return getValue( ITEM_INFO_ORI );
	}

	public void setInfo_ori( String info_ori1 )
	{
		setValue( ITEM_INFO_ORI, info_ori1 );
	}

	/* �������� */
	public String getPen_basis()
	{
		return getValue( ITEM_PEN_BASIS );
	}

	public void setPen_basis( String pen_basis1 )
	{
		setValue( ITEM_PEN_BASIS, pen_basis1 );
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

	/* ��û�ܽ�� */
	public String getRpt_pen_forf_sum()
	{
		return getValue( ITEM_RPT_PEN_FORF_SUM );
	}

	public void setRpt_pen_forf_sum( String rpt_pen_forf_sum1 )
	{
		setValue( ITEM_RPT_PEN_FORF_SUM, rpt_pen_forf_sum1 );
	}

	/* �����ѵ���� */
	public String getRpt_dest_sum()
	{
		return getValue( ITEM_RPT_DEST_SUM );
	}

	public void setRpt_dest_sum( String rpt_dest_sum1 )
	{
		setValue( ITEM_RPT_DEST_SUM, rpt_dest_sum1 );
	}

	/* ���Ͱ��� */
	public String getSp_zon_type()
	{
		return getValue( ITEM_SP_ZON_TYPE );
	}

	public void setSp_zon_type( String sp_zon_type1 )
	{
		setValue( ITEM_SP_ZON_TYPE, sp_zon_type1 );
	}

	/* �鴦��ðα��������ֵ */
	public String getRpt_trea_fake_goods_sum()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_SUM );
	}

	public void setRpt_trea_fake_goods_sum( String rpt_trea_fake_goods_sum1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_SUM, rpt_trea_fake_goods_sum1 );
	}

	/* ���ټ�ðα��������ֵ */
	public String getRpt_dest_fake_goods_sum()
	{
		return getValue( ITEM_RPT_DEST_FAKE_GOODS_SUM );
	}

	public void setRpt_dest_fake_goods_sum( String rpt_dest_fake_goods_sum1 )
	{
		setValue( ITEM_RPT_DEST_FAKE_GOODS_SUM, rpt_dest_fake_goods_sum1 );
	}

	/* �鴦��ðα��ũ����ֵ */
	public String getRpt_trea_fake_agri_goods_sum()
	{
		return getValue( ITEM_RPT_TREA_FAKE_AGRI_GOODS_SUM );
	}

	public void setRpt_trea_fake_agri_goods_sum( String rpt_trea_fake_agri_goods_sum1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_AGRI_GOODS_SUM, rpt_trea_fake_agri_goods_sum1 );
	}

	/* ���ټ�ðα��ũ����ֵ */
	public String getRpt_dest_fake_agri_goods_sum()
	{
		return getValue( ITEM_RPT_DEST_FAKE_AGRI_GOODS_SUM );
	}

	public void setRpt_dest_fake_agri_goods_sum( String rpt_dest_fake_agri_goods_sum1 )
	{
		setValue( ITEM_RPT_DEST_FAKE_AGRI_GOODS_SUM, rpt_dest_fake_agri_goods_sum1 );
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
	public String getRpt_attack_sum()
	{
		return getValue( ITEM_RPT_ATTACK_SUM );
	}

	public void setRpt_attack_sum( String rpt_attack_sum1 )
	{
		setValue( ITEM_RPT_ATTACK_SUM, rpt_attack_sum1 );
	}

	/* ���谸�� */
	public String getRpt_block_case()
	{
		return getValue( ITEM_RPT_BLOCK_CASE );
	}

	public void setRpt_block_case( String rpt_block_case1 )
	{
		setValue( ITEM_RPT_BLOCK_CASE, rpt_block_case1 );
	}

	/* �������� */
	public String getRpt_case_block()
	{
		return getValue( ITEM_RPT_CASE_BLOCK );
	}

	public void setRpt_case_block( String rpt_case_block1 )
	{
		setValue( ITEM_RPT_CASE_BLOCK, rpt_case_block1 );
	}

	/* ũ�ʰ��� */
	public String getRpt_is_agri_res_case()
	{
		return getValue( ITEM_RPT_IS_AGRI_RES_CASE );
	}

	public void setRpt_is_agri_res_case( String rpt_is_agri_res_case1 )
	{
		setValue( ITEM_RPT_IS_AGRI_RES_CASE, rpt_is_agri_res_case1 );
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
	public String getRpt_unexe_rea_am()
	{
		return getValue( ITEM_RPT_UNEXE_REA_AM );
	}

	public void setRpt_unexe_rea_am( String rpt_unexe_rea_am1 )
	{
		setValue( ITEM_RPT_UNEXE_REA_AM, rpt_unexe_rea_am1 );
	}

	/* ����ִ�а����漰��û��� */
	public String getRpt_unexe_rea_pen_am()
	{
		return getValue( ITEM_RPT_UNEXE_REA_PEN_AM );
	}

	public void setRpt_unexe_rea_pen_am( String rpt_unexe_rea_pen_am1 )
	{
		setValue( ITEM_RPT_UNEXE_REA_PEN_AM, rpt_unexe_rea_pen_am1 );
	}

	/* �ٱ��������ý�� */
	public String getRpt_award_sum()
	{
		return getValue( ITEM_RPT_AWARD_SUM );
	}

	public void setRpt_award_sum( String rpt_award_sum1 )
	{
		setValue( ITEM_RPT_AWARD_SUM, rpt_award_sum1 );
	}

	/* ������ */
	public String getRpt_detect_cost()
	{
		return getValue( ITEM_RPT_DETECT_COST );
	}

	public void setRpt_detect_cost( String rpt_detect_cost1 )
	{
		setValue( ITEM_RPT_DETECT_COST, rpt_detect_cost1 );
	}

	/* �������� */
	public String getRpt_addi_material()
	{
		return getValue( ITEM_RPT_ADDI_MATERIAL );
	}

	public void setRpt_addi_material( String rpt_addi_material1 )
	{
		setValue( ITEM_RPT_ADDI_MATERIAL, rpt_addi_material1 );
	}

	/* ��������ϸ�� */
	public String getRpt_addi_material_detail()
	{
		return getValue( ITEM_RPT_ADDI_MATERIAL_DETAIL );
	}

	public void setRpt_addi_material_detail( String rpt_addi_material_detail1 )
	{
		setValue( ITEM_RPT_ADDI_MATERIAL_DETAIL, rpt_addi_material_detail1 );
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

	/* �����а��� */
	public String getTran()
	{
		return getValue( ITEM_TRAN );
	}

	public void setTran( String tran1 )
	{
		setValue( ITEM_TRAN, tran1 );
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

	/* ���ʱ�� */
	public String getRpt_last_record_date()
	{
		return getValue( ITEM_RPT_LAST_RECORD_DATE );
	}

	public void setRpt_last_record_date( String rpt_last_record_date1 )
	{
		setValue( ITEM_RPT_LAST_RECORD_DATE, rpt_last_record_date1 );
	}

	/* �������� */
	public String getRpt_othe_case()
	{
		return getValue( ITEM_RPT_OTHE_CASE );
	}

	public void setRpt_othe_case( String rpt_othe_case1 )
	{
		setValue( ITEM_RPT_OTHE_CASE, rpt_othe_case1 );
	}

	/* ������������ */
	public String getRpt_othe_case_cont()
	{
		return getValue( ITEM_RPT_OTHE_CASE_CONT );
	}

	public void setRpt_othe_case_cont( String rpt_othe_case_cont1 )
	{
		setValue( ITEM_RPT_OTHE_CASE_CONT, rpt_othe_case_cont1 );
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

