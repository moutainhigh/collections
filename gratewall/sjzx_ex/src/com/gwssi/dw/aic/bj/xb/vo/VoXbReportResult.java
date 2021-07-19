package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXbReportResult extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_XB_REPORT_RESULT_ID = "xb_report_result_id";			/* 举报处理信息ID */
	public static final String ITEM_XB_REPORT_BASE_ID = "xb_report_base_id";			/* 举报基本信息ID */
	public static final String ITEM_RPT_SURV_IS_TRUE = "rpt_surv_is_true";			/* 属实情况 */
	public static final String ITEM_RPT_SURV_DATE = "rpt_surv_date";			/* 调查时间 */
	public static final String ITEM_RPT_SURV_PER = "rpt_surv_per";			/* 调查人 */
	public static final String ITEM_RPT_SURV_SITU = "rpt_surv_situ";			/* 调查情况 */
	public static final String ITEM_RPT_IS_CASE_FILE = "rpt_is_case_file";			/* 是否立案 */
	public static final String ITEM_APP_PROCEDURE = "app_procedure";			/* 适应程序 */
	public static final String ITEM_CLO_CASE_REA = "clo_case_rea";			/* 销案原因 */
	public static final String ITEM_CASE_FI_DATE = "case_fi_date";			/* 办案日期 */
	public static final String ITEM_PEN_DEC_NO = "pen_dec_no";			/* 处罚决定书号 */
	public static final String ITEM_CLO_CASE_DATE = "clo_case_date";			/* 销案时间 */
	public static final String ITEM_RPT_CLO_CASE_NO = "rpt_clo_case_no";			/* 销案号 */
	public static final String ITEM_RPT_CLO_CASE_APPR_PER = "rpt_clo_case_appr_per";			/* 销案批准人 */
	public static final String ITEM_CASE_CHR = "case_chr";			/* 案件性质 */
	public static final String ITEM_RPT_CASE_TYPE = "rpt_case_type";			/* 案件类型 */
	public static final String ITEM_INFO_ORI = "info_ori";			/* 申诉信息来源 */
	public static final String ITEM_PEN_BASIS = "pen_basis";			/* 处罚依据 */
	public static final String ITEM_PEN_TYPE = "pen_type";			/* 处罚方式 */
	public static final String ITEM_CASE_VAL = "case_val";			/* 案值 */
	public static final String ITEM_PEN_AM = "pen_am";			/* 罚款金额 */
	public static final String ITEM_FORF_AM = "forf_am";			/* 没收金额 */
	public static final String ITEM_RPT_PEN_FORF_SUM = "rpt_pen_forf_sum";			/* 罚没总金额 */
	public static final String ITEM_RPT_DEST_SUM = "rpt_dest_sum";			/* 捣毁窝点个数 */
	public static final String ITEM_SP_ZON_TYPE = "sp_zon_type";			/* 典型案件 */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_SUM = "rpt_trea_fake_goods_sum";			/* 查处假冒伪劣物资总值 */
	public static final String ITEM_RPT_DEST_FAKE_GOODS_SUM = "rpt_dest_fake_goods_sum";			/* 销毁假冒伪劣物资总值 */
	public static final String ITEM_RPT_TREA_FAKE_AGRI_GOODS_SUM = "rpt_trea_fake_agri_goods_sum";			/* 查处假冒伪劣农资总值 */
	public static final String ITEM_RPT_DEST_FAKE_AGRI_GOODS_SUM = "rpt_dest_fake_agri_goods_sum";			/* 销毁假冒伪劣农资总值 */
	public static final String ITEM_SAC_NUM = "sac_num";			/* 执法死亡人数 */
	public static final String ITEM_INJ_NUM = "inj_num";			/* 执法受伤人数 */
	public static final String ITEM_RPT_ATTACK_SUM = "rpt_attack_sum";			/* 被打人数 */
	public static final String ITEM_RPT_BLOCK_CASE = "rpt_block_case";			/* 受阻案件 */
	public static final String ITEM_RPT_CASE_BLOCK = "rpt_case_block";			/* 被困受阻 */
	public static final String ITEM_RPT_IS_AGRI_RES_CASE = "rpt_is_agri_res_case";			/* 农资案件 */
	public static final String ITEM_EXE_SORT = "exe_sort";			/* 案件执行情况 */
	public static final String ITEM_UNEXE_REA_SORT = "unexe_rea_sort";			/* 案件难以结案执行原因 */
	public static final String ITEM_RPT_UNEXE_REA_AM = "rpt_unexe_rea_am";			/* 难以结案涉及金额 */
	public static final String ITEM_RPT_UNEXE_REA_PEN_AM = "rpt_unexe_rea_pen_am";			/* 难以执行案件涉及罚没金额 */
	public static final String ITEM_RPT_AWARD_SUM = "rpt_award_sum";			/* 举报奖励费用金额 */
	public static final String ITEM_RPT_DETECT_COST = "rpt_detect_cost";			/* 检测费用 */
	public static final String ITEM_RPT_ADDI_MATERIAL = "rpt_addi_material";			/* 补充资料 */
	public static final String ITEM_RPT_ADDI_MATERIAL_DETAIL = "rpt_addi_material_detail";			/* 补充资料细分 */
	public static final String ITEM_HAND_DEP = "hand_dep";			/* 办案单位 */
	public static final String ITEM_TRAN = "tran";			/* 案件承办人 */
	public static final String ITEM_END_CAS_DATE = "end_cas_date";			/* 结案日期 */
	public static final String ITEM_RPT_LAST_RECORD_DATE = "rpt_last_record_date";			/* 入库时间 */
	public static final String ITEM_RPT_OTHE_CASE = "rpt_othe_case";			/* 其它案件 */
	public static final String ITEM_RPT_OTHE_CASE_CONT = "rpt_othe_case_cont";			/* 其它案件内容 */
	public static final String ITEM_ETL_ID = "etl_id";			/* ETL序列号 */
	public static final String ITEM_ETL_FLAG = "etl_flag";			/* ETL数据状态 */
	public static final String ITEM_ETL_TIMESTAMP = "etl_timestamp";			/* ETL时间戳 */

	public VoXbReportResult(DataBus value)
	{
		super(value);
	}

	public VoXbReportResult()
	{
		super();
	}

	/* 举报处理信息ID */
	public String getXb_report_result_id()
	{
		return getValue( ITEM_XB_REPORT_RESULT_ID );
	}

	public void setXb_report_result_id( String xb_report_result_id1 )
	{
		setValue( ITEM_XB_REPORT_RESULT_ID, xb_report_result_id1 );
	}

	/* 举报基本信息ID */
	public String getXb_report_base_id()
	{
		return getValue( ITEM_XB_REPORT_BASE_ID );
	}

	public void setXb_report_base_id( String xb_report_base_id1 )
	{
		setValue( ITEM_XB_REPORT_BASE_ID, xb_report_base_id1 );
	}

	/* 属实情况 */
	public String getRpt_surv_is_true()
	{
		return getValue( ITEM_RPT_SURV_IS_TRUE );
	}

	public void setRpt_surv_is_true( String rpt_surv_is_true1 )
	{
		setValue( ITEM_RPT_SURV_IS_TRUE, rpt_surv_is_true1 );
	}

	/* 调查时间 */
	public String getRpt_surv_date()
	{
		return getValue( ITEM_RPT_SURV_DATE );
	}

	public void setRpt_surv_date( String rpt_surv_date1 )
	{
		setValue( ITEM_RPT_SURV_DATE, rpt_surv_date1 );
	}

	/* 调查人 */
	public String getRpt_surv_per()
	{
		return getValue( ITEM_RPT_SURV_PER );
	}

	public void setRpt_surv_per( String rpt_surv_per1 )
	{
		setValue( ITEM_RPT_SURV_PER, rpt_surv_per1 );
	}

	/* 调查情况 */
	public String getRpt_surv_situ()
	{
		return getValue( ITEM_RPT_SURV_SITU );
	}

	public void setRpt_surv_situ( String rpt_surv_situ1 )
	{
		setValue( ITEM_RPT_SURV_SITU, rpt_surv_situ1 );
	}

	/* 是否立案 */
	public String getRpt_is_case_file()
	{
		return getValue( ITEM_RPT_IS_CASE_FILE );
	}

	public void setRpt_is_case_file( String rpt_is_case_file1 )
	{
		setValue( ITEM_RPT_IS_CASE_FILE, rpt_is_case_file1 );
	}

	/* 适应程序 */
	public String getApp_procedure()
	{
		return getValue( ITEM_APP_PROCEDURE );
	}

	public void setApp_procedure( String app_procedure1 )
	{
		setValue( ITEM_APP_PROCEDURE, app_procedure1 );
	}

	/* 销案原因 */
	public String getClo_case_rea()
	{
		return getValue( ITEM_CLO_CASE_REA );
	}

	public void setClo_case_rea( String clo_case_rea1 )
	{
		setValue( ITEM_CLO_CASE_REA, clo_case_rea1 );
	}

	/* 办案日期 */
	public String getCase_fi_date()
	{
		return getValue( ITEM_CASE_FI_DATE );
	}

	public void setCase_fi_date( String case_fi_date1 )
	{
		setValue( ITEM_CASE_FI_DATE, case_fi_date1 );
	}

	/* 处罚决定书号 */
	public String getPen_dec_no()
	{
		return getValue( ITEM_PEN_DEC_NO );
	}

	public void setPen_dec_no( String pen_dec_no1 )
	{
		setValue( ITEM_PEN_DEC_NO, pen_dec_no1 );
	}

	/* 销案时间 */
	public String getClo_case_date()
	{
		return getValue( ITEM_CLO_CASE_DATE );
	}

	public void setClo_case_date( String clo_case_date1 )
	{
		setValue( ITEM_CLO_CASE_DATE, clo_case_date1 );
	}

	/* 销案号 */
	public String getRpt_clo_case_no()
	{
		return getValue( ITEM_RPT_CLO_CASE_NO );
	}

	public void setRpt_clo_case_no( String rpt_clo_case_no1 )
	{
		setValue( ITEM_RPT_CLO_CASE_NO, rpt_clo_case_no1 );
	}

	/* 销案批准人 */
	public String getRpt_clo_case_appr_per()
	{
		return getValue( ITEM_RPT_CLO_CASE_APPR_PER );
	}

	public void setRpt_clo_case_appr_per( String rpt_clo_case_appr_per1 )
	{
		setValue( ITEM_RPT_CLO_CASE_APPR_PER, rpt_clo_case_appr_per1 );
	}

	/* 案件性质 */
	public String getCase_chr()
	{
		return getValue( ITEM_CASE_CHR );
	}

	public void setCase_chr( String case_chr1 )
	{
		setValue( ITEM_CASE_CHR, case_chr1 );
	}

	/* 案件类型 */
	public String getRpt_case_type()
	{
		return getValue( ITEM_RPT_CASE_TYPE );
	}

	public void setRpt_case_type( String rpt_case_type1 )
	{
		setValue( ITEM_RPT_CASE_TYPE, rpt_case_type1 );
	}

	/* 申诉信息来源 */
	public String getInfo_ori()
	{
		return getValue( ITEM_INFO_ORI );
	}

	public void setInfo_ori( String info_ori1 )
	{
		setValue( ITEM_INFO_ORI, info_ori1 );
	}

	/* 处罚依据 */
	public String getPen_basis()
	{
		return getValue( ITEM_PEN_BASIS );
	}

	public void setPen_basis( String pen_basis1 )
	{
		setValue( ITEM_PEN_BASIS, pen_basis1 );
	}

	/* 处罚方式 */
	public String getPen_type()
	{
		return getValue( ITEM_PEN_TYPE );
	}

	public void setPen_type( String pen_type1 )
	{
		setValue( ITEM_PEN_TYPE, pen_type1 );
	}

	/* 案值 */
	public String getCase_val()
	{
		return getValue( ITEM_CASE_VAL );
	}

	public void setCase_val( String case_val1 )
	{
		setValue( ITEM_CASE_VAL, case_val1 );
	}

	/* 罚款金额 */
	public String getPen_am()
	{
		return getValue( ITEM_PEN_AM );
	}

	public void setPen_am( String pen_am1 )
	{
		setValue( ITEM_PEN_AM, pen_am1 );
	}

	/* 没收金额 */
	public String getForf_am()
	{
		return getValue( ITEM_FORF_AM );
	}

	public void setForf_am( String forf_am1 )
	{
		setValue( ITEM_FORF_AM, forf_am1 );
	}

	/* 罚没总金额 */
	public String getRpt_pen_forf_sum()
	{
		return getValue( ITEM_RPT_PEN_FORF_SUM );
	}

	public void setRpt_pen_forf_sum( String rpt_pen_forf_sum1 )
	{
		setValue( ITEM_RPT_PEN_FORF_SUM, rpt_pen_forf_sum1 );
	}

	/* 捣毁窝点个数 */
	public String getRpt_dest_sum()
	{
		return getValue( ITEM_RPT_DEST_SUM );
	}

	public void setRpt_dest_sum( String rpt_dest_sum1 )
	{
		setValue( ITEM_RPT_DEST_SUM, rpt_dest_sum1 );
	}

	/* 典型案件 */
	public String getSp_zon_type()
	{
		return getValue( ITEM_SP_ZON_TYPE );
	}

	public void setSp_zon_type( String sp_zon_type1 )
	{
		setValue( ITEM_SP_ZON_TYPE, sp_zon_type1 );
	}

	/* 查处假冒伪劣物资总值 */
	public String getRpt_trea_fake_goods_sum()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_SUM );
	}

	public void setRpt_trea_fake_goods_sum( String rpt_trea_fake_goods_sum1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_SUM, rpt_trea_fake_goods_sum1 );
	}

	/* 销毁假冒伪劣物资总值 */
	public String getRpt_dest_fake_goods_sum()
	{
		return getValue( ITEM_RPT_DEST_FAKE_GOODS_SUM );
	}

	public void setRpt_dest_fake_goods_sum( String rpt_dest_fake_goods_sum1 )
	{
		setValue( ITEM_RPT_DEST_FAKE_GOODS_SUM, rpt_dest_fake_goods_sum1 );
	}

	/* 查处假冒伪劣农资总值 */
	public String getRpt_trea_fake_agri_goods_sum()
	{
		return getValue( ITEM_RPT_TREA_FAKE_AGRI_GOODS_SUM );
	}

	public void setRpt_trea_fake_agri_goods_sum( String rpt_trea_fake_agri_goods_sum1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_AGRI_GOODS_SUM, rpt_trea_fake_agri_goods_sum1 );
	}

	/* 销毁假冒伪劣农资总值 */
	public String getRpt_dest_fake_agri_goods_sum()
	{
		return getValue( ITEM_RPT_DEST_FAKE_AGRI_GOODS_SUM );
	}

	public void setRpt_dest_fake_agri_goods_sum( String rpt_dest_fake_agri_goods_sum1 )
	{
		setValue( ITEM_RPT_DEST_FAKE_AGRI_GOODS_SUM, rpt_dest_fake_agri_goods_sum1 );
	}

	/* 执法死亡人数 */
	public String getSac_num()
	{
		return getValue( ITEM_SAC_NUM );
	}

	public void setSac_num( String sac_num1 )
	{
		setValue( ITEM_SAC_NUM, sac_num1 );
	}

	/* 执法受伤人数 */
	public String getInj_num()
	{
		return getValue( ITEM_INJ_NUM );
	}

	public void setInj_num( String inj_num1 )
	{
		setValue( ITEM_INJ_NUM, inj_num1 );
	}

	/* 被打人数 */
	public String getRpt_attack_sum()
	{
		return getValue( ITEM_RPT_ATTACK_SUM );
	}

	public void setRpt_attack_sum( String rpt_attack_sum1 )
	{
		setValue( ITEM_RPT_ATTACK_SUM, rpt_attack_sum1 );
	}

	/* 受阻案件 */
	public String getRpt_block_case()
	{
		return getValue( ITEM_RPT_BLOCK_CASE );
	}

	public void setRpt_block_case( String rpt_block_case1 )
	{
		setValue( ITEM_RPT_BLOCK_CASE, rpt_block_case1 );
	}

	/* 被困受阻 */
	public String getRpt_case_block()
	{
		return getValue( ITEM_RPT_CASE_BLOCK );
	}

	public void setRpt_case_block( String rpt_case_block1 )
	{
		setValue( ITEM_RPT_CASE_BLOCK, rpt_case_block1 );
	}

	/* 农资案件 */
	public String getRpt_is_agri_res_case()
	{
		return getValue( ITEM_RPT_IS_AGRI_RES_CASE );
	}

	public void setRpt_is_agri_res_case( String rpt_is_agri_res_case1 )
	{
		setValue( ITEM_RPT_IS_AGRI_RES_CASE, rpt_is_agri_res_case1 );
	}

	/* 案件执行情况 */
	public String getExe_sort()
	{
		return getValue( ITEM_EXE_SORT );
	}

	public void setExe_sort( String exe_sort1 )
	{
		setValue( ITEM_EXE_SORT, exe_sort1 );
	}

	/* 案件难以结案执行原因 */
	public String getUnexe_rea_sort()
	{
		return getValue( ITEM_UNEXE_REA_SORT );
	}

	public void setUnexe_rea_sort( String unexe_rea_sort1 )
	{
		setValue( ITEM_UNEXE_REA_SORT, unexe_rea_sort1 );
	}

	/* 难以结案涉及金额 */
	public String getRpt_unexe_rea_am()
	{
		return getValue( ITEM_RPT_UNEXE_REA_AM );
	}

	public void setRpt_unexe_rea_am( String rpt_unexe_rea_am1 )
	{
		setValue( ITEM_RPT_UNEXE_REA_AM, rpt_unexe_rea_am1 );
	}

	/* 难以执行案件涉及罚没金额 */
	public String getRpt_unexe_rea_pen_am()
	{
		return getValue( ITEM_RPT_UNEXE_REA_PEN_AM );
	}

	public void setRpt_unexe_rea_pen_am( String rpt_unexe_rea_pen_am1 )
	{
		setValue( ITEM_RPT_UNEXE_REA_PEN_AM, rpt_unexe_rea_pen_am1 );
	}

	/* 举报奖励费用金额 */
	public String getRpt_award_sum()
	{
		return getValue( ITEM_RPT_AWARD_SUM );
	}

	public void setRpt_award_sum( String rpt_award_sum1 )
	{
		setValue( ITEM_RPT_AWARD_SUM, rpt_award_sum1 );
	}

	/* 检测费用 */
	public String getRpt_detect_cost()
	{
		return getValue( ITEM_RPT_DETECT_COST );
	}

	public void setRpt_detect_cost( String rpt_detect_cost1 )
	{
		setValue( ITEM_RPT_DETECT_COST, rpt_detect_cost1 );
	}

	/* 补充资料 */
	public String getRpt_addi_material()
	{
		return getValue( ITEM_RPT_ADDI_MATERIAL );
	}

	public void setRpt_addi_material( String rpt_addi_material1 )
	{
		setValue( ITEM_RPT_ADDI_MATERIAL, rpt_addi_material1 );
	}

	/* 补充资料细分 */
	public String getRpt_addi_material_detail()
	{
		return getValue( ITEM_RPT_ADDI_MATERIAL_DETAIL );
	}

	public void setRpt_addi_material_detail( String rpt_addi_material_detail1 )
	{
		setValue( ITEM_RPT_ADDI_MATERIAL_DETAIL, rpt_addi_material_detail1 );
	}

	/* 办案单位 */
	public String getHand_dep()
	{
		return getValue( ITEM_HAND_DEP );
	}

	public void setHand_dep( String hand_dep1 )
	{
		setValue( ITEM_HAND_DEP, hand_dep1 );
	}

	/* 案件承办人 */
	public String getTran()
	{
		return getValue( ITEM_TRAN );
	}

	public void setTran( String tran1 )
	{
		setValue( ITEM_TRAN, tran1 );
	}

	/* 结案日期 */
	public String getEnd_cas_date()
	{
		return getValue( ITEM_END_CAS_DATE );
	}

	public void setEnd_cas_date( String end_cas_date1 )
	{
		setValue( ITEM_END_CAS_DATE, end_cas_date1 );
	}

	/* 入库时间 */
	public String getRpt_last_record_date()
	{
		return getValue( ITEM_RPT_LAST_RECORD_DATE );
	}

	public void setRpt_last_record_date( String rpt_last_record_date1 )
	{
		setValue( ITEM_RPT_LAST_RECORD_DATE, rpt_last_record_date1 );
	}

	/* 其它案件 */
	public String getRpt_othe_case()
	{
		return getValue( ITEM_RPT_OTHE_CASE );
	}

	public void setRpt_othe_case( String rpt_othe_case1 )
	{
		setValue( ITEM_RPT_OTHE_CASE, rpt_othe_case1 );
	}

	/* 其它案件内容 */
	public String getRpt_othe_case_cont()
	{
		return getValue( ITEM_RPT_OTHE_CASE_CONT );
	}

	public void setRpt_othe_case_cont( String rpt_othe_case_cont1 )
	{
		setValue( ITEM_RPT_OTHE_CASE_CONT, rpt_othe_case_cont1 );
	}

	/* ETL序列号 */
	public String getEtl_id()
	{
		return getValue( ITEM_ETL_ID );
	}

	public void setEtl_id( String etl_id1 )
	{
		setValue( ITEM_ETL_ID, etl_id1 );
	}

	/* ETL数据状态 */
	public String getEtl_flag()
	{
		return getValue( ITEM_ETL_FLAG );
	}

	public void setEtl_flag( String etl_flag1 )
	{
		setValue( ITEM_ETL_FLAG, etl_flag1 );
	}

	/* ETL时间戳 */
	public String getEtl_timestamp()
	{
		return getValue( ITEM_ETL_TIMESTAMP );
	}

	public void setEtl_timestamp( String etl_timestamp1 )
	{
		setValue( ITEM_ETL_TIMESTAMP, etl_timestamp1 );
	}

}

