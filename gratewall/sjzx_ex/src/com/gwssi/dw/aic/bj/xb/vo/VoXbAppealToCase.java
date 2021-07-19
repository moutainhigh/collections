package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXbAppealToCase extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_XB_APPEAL_TO_CASE_ID = "xb_appeal_to_case_id";			/* 调解转立案ID */
	public static final String ITEM_XB_APPEAL_RESULT_ID = "xb_appeal_result_id";			/* 申诉处理ID */
	public static final String ITEM_APL_IS_CLO_CASE = "apl_is_clo_case";			/* 是否销案 */
	public static final String ITEM_CLO_CASE_REA = "clo_case_rea";			/* 销案原因 */
	public static final String ITEM_CLO_CASE_DATE = "clo_case_date";			/* 销案时间 */
	public static final String ITEM_APL_QUA_BASIS = "apl_qua_basis";			/* 违反法规 */
	public static final String ITEM_APL_CASE_TYPE = "apl_case_type";			/* 案件类型 */
	public static final String ITEM_CASE_CHR = "case_chr";			/* 案件性质 */
	public static final String ITEM_APP_PROCEDURE = "app_procedure";			/* 适应程序 */
	public static final String ITEM_PEN_TYPE = "pen_type";			/* 处罚方式 */
	public static final String ITEM_CASE_VAL = "case_val";			/* 案值 */
	public static final String ITEM_APL_FOFF_SUM = "apl_foff_sum";			/* 没收非法所得 */
	public static final String ITEM_PEN_AM = "pen_am";			/* 罚款金额 */
	public static final String ITEM_FORF_AM = "forf_am";			/* 没收金额 */
	public static final String ITEM_APL_FORF_GOODS_NUM = "apl_forf_goods_num";			/* 没收物品数量 */
	public static final String ITEM_APL_FORF_GOODS_UNIT = "apl_forf_goods_unit";			/* 没收物品单位 */
	public static final String ITEM_APL_FORF_GOODS_VALUE = "apl_forf_goods_value";			/* 没收物品价值 */
	public static final String ITEM_APL_IS_DESTROY = "apl_is_destroy";			/* 是否销毁 */
	public static final String ITEM_APL_DEST_SUM = "apl_dest_sum";			/* 捣毁窝点个数 */
	public static final String ITEM_SAC_NUM = "sac_num";			/* 执法死亡人数 */
	public static final String ITEM_INJ_NUM = "inj_num";			/* 执法受伤人数 */
	public static final String ITEM_APL_ATTACK_SUM = "apl_attack_sum";			/* 被打人数 */
	public static final String ITEM_APL_BLOCK_CASE = "apl_block_case";			/* 受阻案件 */
	public static final String ITEM_APL_CASE_BLOCKED = "apl_case_blocked";			/* 被困受阻 */
	public static final String ITEM_EXE_SORT = "exe_sort";			/* 案件执行情况 */
	public static final String ITEM_UNEXE_REA_SORT = "unexe_rea_sort";			/* 案件难以结案执行原因 */
	public static final String ITEM_APL_UNEXE_REA_AM = "apl_unexe_rea_am";			/* 难以结案涉及金额 */
	public static final String ITEM_APL_UNEXE_REA_PEN_AM = "apl_unexe_rea_pen_am";			/* 难以结案涉及罚没金额 */
	public static final String ITEM_APL_DETECT_COST = "apl_detect_cost";			/* 检测费用 */
	public static final String ITEM_APL_ADDI_MATERIAL = "apl_addi_material";			/* 补充资料 */
	public static final String ITEM_APL_ADDI_MATERIAL_DETAIL = "apl_addi_material_detail";			/* 补充资料细分 */
	public static final String ITEM_HAND_DEP = "hand_dep";			/* 办案单位 */
	public static final String ITEM_CASE_FI_DATE = "case_fi_date";			/* 办案日期 */
	public static final String ITEM_END_CAS_DATE = "end_cas_date";			/* 结案日期 */
	public static final String ITEM_TRAN = "tran";			/* 案件承办人 */
	public static final String ITEM_APL_LAST_RECORD_DATE = "apl_last_record_date";			/* 入库时间 */
	public static final String ITEM_APL_OTHE_CASE = "apl_othe_case";			/* 其它案件 */
	public static final String ITEM_APL_OTHE_CASE_CONT = "apl_othe_case_cont";			/* 其它案件内容 */
	public static final String ITEM_ETL_ID = "etl_id";			/* ETL序列号 */
	public static final String ITEM_ETL_FLAG = "etl_flag";			/* ETL数据状态 */
	public static final String ITEM_ETL_TIMESTAMP = "etl_timestamp";			/* ETL时间戳 */

	public VoXbAppealToCase(DataBus value)
	{
		super(value);
	}

	public VoXbAppealToCase()
	{
		super();
	}

	/* 调解转立案ID */
	public String getXb_appeal_to_case_id()
	{
		return getValue( ITEM_XB_APPEAL_TO_CASE_ID );
	}

	public void setXb_appeal_to_case_id( String xb_appeal_to_case_id1 )
	{
		setValue( ITEM_XB_APPEAL_TO_CASE_ID, xb_appeal_to_case_id1 );
	}

	/* 申诉处理ID */
	public String getXb_appeal_result_id()
	{
		return getValue( ITEM_XB_APPEAL_RESULT_ID );
	}

	public void setXb_appeal_result_id( String xb_appeal_result_id1 )
	{
		setValue( ITEM_XB_APPEAL_RESULT_ID, xb_appeal_result_id1 );
	}

	/* 是否销案 */
	public String getApl_is_clo_case()
	{
		return getValue( ITEM_APL_IS_CLO_CASE );
	}

	public void setApl_is_clo_case( String apl_is_clo_case1 )
	{
		setValue( ITEM_APL_IS_CLO_CASE, apl_is_clo_case1 );
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

	/* 销案时间 */
	public String getClo_case_date()
	{
		return getValue( ITEM_CLO_CASE_DATE );
	}

	public void setClo_case_date( String clo_case_date1 )
	{
		setValue( ITEM_CLO_CASE_DATE, clo_case_date1 );
	}

	/* 违反法规 */
	public String getApl_qua_basis()
	{
		return getValue( ITEM_APL_QUA_BASIS );
	}

	public void setApl_qua_basis( String apl_qua_basis1 )
	{
		setValue( ITEM_APL_QUA_BASIS, apl_qua_basis1 );
	}

	/* 案件类型 */
	public String getApl_case_type()
	{
		return getValue( ITEM_APL_CASE_TYPE );
	}

	public void setApl_case_type( String apl_case_type1 )
	{
		setValue( ITEM_APL_CASE_TYPE, apl_case_type1 );
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

	/* 适应程序 */
	public String getApp_procedure()
	{
		return getValue( ITEM_APP_PROCEDURE );
	}

	public void setApp_procedure( String app_procedure1 )
	{
		setValue( ITEM_APP_PROCEDURE, app_procedure1 );
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

	/* 没收非法所得 */
	public String getApl_foff_sum()
	{
		return getValue( ITEM_APL_FOFF_SUM );
	}

	public void setApl_foff_sum( String apl_foff_sum1 )
	{
		setValue( ITEM_APL_FOFF_SUM, apl_foff_sum1 );
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

	/* 没收物品数量 */
	public String getApl_forf_goods_num()
	{
		return getValue( ITEM_APL_FORF_GOODS_NUM );
	}

	public void setApl_forf_goods_num( String apl_forf_goods_num1 )
	{
		setValue( ITEM_APL_FORF_GOODS_NUM, apl_forf_goods_num1 );
	}

	/* 没收物品单位 */
	public String getApl_forf_goods_unit()
	{
		return getValue( ITEM_APL_FORF_GOODS_UNIT );
	}

	public void setApl_forf_goods_unit( String apl_forf_goods_unit1 )
	{
		setValue( ITEM_APL_FORF_GOODS_UNIT, apl_forf_goods_unit1 );
	}

	/* 没收物品价值 */
	public String getApl_forf_goods_value()
	{
		return getValue( ITEM_APL_FORF_GOODS_VALUE );
	}

	public void setApl_forf_goods_value( String apl_forf_goods_value1 )
	{
		setValue( ITEM_APL_FORF_GOODS_VALUE, apl_forf_goods_value1 );
	}

	/* 是否销毁 */
	public String getApl_is_destroy()
	{
		return getValue( ITEM_APL_IS_DESTROY );
	}

	public void setApl_is_destroy( String apl_is_destroy1 )
	{
		setValue( ITEM_APL_IS_DESTROY, apl_is_destroy1 );
	}

	/* 捣毁窝点个数 */
	public String getApl_dest_sum()
	{
		return getValue( ITEM_APL_DEST_SUM );
	}

	public void setApl_dest_sum( String apl_dest_sum1 )
	{
		setValue( ITEM_APL_DEST_SUM, apl_dest_sum1 );
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
	public String getApl_attack_sum()
	{
		return getValue( ITEM_APL_ATTACK_SUM );
	}

	public void setApl_attack_sum( String apl_attack_sum1 )
	{
		setValue( ITEM_APL_ATTACK_SUM, apl_attack_sum1 );
	}

	/* 受阻案件 */
	public String getApl_block_case()
	{
		return getValue( ITEM_APL_BLOCK_CASE );
	}

	public void setApl_block_case( String apl_block_case1 )
	{
		setValue( ITEM_APL_BLOCK_CASE, apl_block_case1 );
	}

	/* 被困受阻 */
	public String getApl_case_blocked()
	{
		return getValue( ITEM_APL_CASE_BLOCKED );
	}

	public void setApl_case_blocked( String apl_case_blocked1 )
	{
		setValue( ITEM_APL_CASE_BLOCKED, apl_case_blocked1 );
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
	public String getApl_unexe_rea_am()
	{
		return getValue( ITEM_APL_UNEXE_REA_AM );
	}

	public void setApl_unexe_rea_am( String apl_unexe_rea_am1 )
	{
		setValue( ITEM_APL_UNEXE_REA_AM, apl_unexe_rea_am1 );
	}

	/* 难以结案涉及罚没金额 */
	public String getApl_unexe_rea_pen_am()
	{
		return getValue( ITEM_APL_UNEXE_REA_PEN_AM );
	}

	public void setApl_unexe_rea_pen_am( String apl_unexe_rea_pen_am1 )
	{
		setValue( ITEM_APL_UNEXE_REA_PEN_AM, apl_unexe_rea_pen_am1 );
	}

	/* 检测费用 */
	public String getApl_detect_cost()
	{
		return getValue( ITEM_APL_DETECT_COST );
	}

	public void setApl_detect_cost( String apl_detect_cost1 )
	{
		setValue( ITEM_APL_DETECT_COST, apl_detect_cost1 );
	}

	/* 补充资料 */
	public String getApl_addi_material()
	{
		return getValue( ITEM_APL_ADDI_MATERIAL );
	}

	public void setApl_addi_material( String apl_addi_material1 )
	{
		setValue( ITEM_APL_ADDI_MATERIAL, apl_addi_material1 );
	}

	/* 补充资料细分 */
	public String getApl_addi_material_detail()
	{
		return getValue( ITEM_APL_ADDI_MATERIAL_DETAIL );
	}

	public void setApl_addi_material_detail( String apl_addi_material_detail1 )
	{
		setValue( ITEM_APL_ADDI_MATERIAL_DETAIL, apl_addi_material_detail1 );
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

	/* 办案日期 */
	public String getCase_fi_date()
	{
		return getValue( ITEM_CASE_FI_DATE );
	}

	public void setCase_fi_date( String case_fi_date1 )
	{
		setValue( ITEM_CASE_FI_DATE, case_fi_date1 );
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

	/* 案件承办人 */
	public String getTran()
	{
		return getValue( ITEM_TRAN );
	}

	public void setTran( String tran1 )
	{
		setValue( ITEM_TRAN, tran1 );
	}

	/* 入库时间 */
	public String getApl_last_record_date()
	{
		return getValue( ITEM_APL_LAST_RECORD_DATE );
	}

	public void setApl_last_record_date( String apl_last_record_date1 )
	{
		setValue( ITEM_APL_LAST_RECORD_DATE, apl_last_record_date1 );
	}

	/* 其它案件 */
	public String getApl_othe_case()
	{
		return getValue( ITEM_APL_OTHE_CASE );
	}

	public void setApl_othe_case( String apl_othe_case1 )
	{
		setValue( ITEM_APL_OTHE_CASE, apl_othe_case1 );
	}

	/* 其它案件内容 */
	public String getApl_othe_case_cont()
	{
		return getValue( ITEM_APL_OTHE_CASE_CONT );
	}

	public void setApl_othe_case_cont( String apl_othe_case_cont1 )
	{
		setValue( ITEM_APL_OTHE_CASE_CONT, apl_othe_case_cont1 );
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

