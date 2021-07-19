package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXbAppealResult extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_XB_APPEAL_RESULT_ID = "xb_appeal_result_id";			/* 申诉处理ID */
	public static final String ITEM_XB_APPEAL_BASE_ID = "xb_appeal_base_id";			/* 申诉基本信息ID */
	public static final String ITEM_APL_CAUS = "apl_caus";			/* 案由 */
	public static final String ITEM_APL_IS_ACCEPT = "apl_is_accept";			/* 是否受理 */
	public static final String ITEM_APL_REFU_CAUS = "apl_refu_caus";			/* 不受理原因 */
	public static final String ITEM_APL_LAST_RESU = "apl_last_resu";			/* 办理结果 */
	public static final String ITEM_APL_LAST_RESU_DETA = "apl_last_resu_deta";			/* 办理结果细分 */
	public static final String ITEM_APL_IS_TO_CASE = "apl_is_to_case";			/* 是否转立案 */
	public static final String ITEM_APL_TRANSF_CASE_UNIT = "apl_transf_case_unit";			/* 转立案单位 */
	public static final String ITEM_APL_IS_SHIFT_OTHE_DEPT = "apl_is_shift_othe_dept";			/* 是否移转其他部门 */
	public static final String ITEM_ACCP_TRANF_AUTH = "accp_tranf_auth";			/* 其他部门 */
	public static final String ITEM_APL_INT_FROM_DATE = "apl_int_from_date";			/* 调解日期起 */
	public static final String ITEM_INT_END_DATE = "int_end_date";			/* 调解日期止 */
	public static final String ITEM_APL_INT_WAY = "apl_int_way";			/* 调解方式 */
	public static final String ITEM_APL_INT_SITUA = "apl_int_situa";			/* 调解情况 */
	public static final String ITEM_APL_INT_BASIS = "apl_int_basis";			/* 调解依据 */
	public static final String ITEM_APL_MEDIA_PER_NAME = "apl_media_per_name";			/* 调解人姓名 */
	public static final String ITEM_APL_MEDIA_UNIT = "apl_media_unit";			/* 调解单位 */
	public static final String ITEM_DIS_AM = "dis_am";			/* 争议金额 */
	public static final String ITEM_APL_AME_SUM = "apl_ame_sum";			/* 赔偿金额 */
	public static final String ITEM_DOU_AME_AM = "dou_ame_am";			/* 增加一倍赔偿金额 */
	public static final String ITEM_SPI_AME_AM = "spi_ame_am";			/* 精神赔偿金额 */
	public static final String ITEM_APL_OTHE_AME_AM = "apl_othe_ame_am";			/* 其他赔偿金额 */
	public static final String ITEM_APL_RETURN_GOOD_AM = "apl_return_good_am";			/* 退还货款金额 */
	public static final String ITEM_RED_ECO_LOS = "red_eco_los";			/* 挽回经济损失金额(总) */
	public static final String ITEM_APL_IS_SUPP_LAWS = "apl_is_supp_laws";			/* 是否支持诉讼 */
	public static final String ITEM_APL_SUPP_LAWS = "apl_supp_laws";			/* 支持诉讼 */
	public static final String ITEM_APL_LAWS_IS_SUCC = "apl_laws_is_succ";			/* 诉讼是否成功 */
	public static final String ITEM_APL_LAWS_RST = "apl_laws_rst";			/* 诉讼结果 */
	public static final String ITEM_APL_LAWS_SUCC = "apl_laws_succ";			/* 诉讼成功 */
	public static final String ITEM_APL_LAWS_RED_ECO_LOS_AM = "apl_laws_red_eco_los_am";			/* 诉讼挽回经济损失金额 */
	public static final String ITEM_APL_MED_NAME = "apl_med_name";			/* 媒体名称 */
	public static final String ITEM_APL_LAST_RECORD_DATE = "apl_last_record_date";			/* 入库时间 */
	public static final String ITEM_ETL_ID = "etl_id";			/* ETL序列号 */
	public static final String ITEM_ETL_FLAG = "etl_flag";			/* ETL数据状态 */
	public static final String ITEM_ETL_TIMESTAMP = "etl_timestamp";			/* ETL时间戳 */

	public VoXbAppealResult(DataBus value)
	{
		super(value);
	}

	public VoXbAppealResult()
	{
		super();
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

	/* 申诉基本信息ID */
	public String getXb_appeal_base_id()
	{
		return getValue( ITEM_XB_APPEAL_BASE_ID );
	}

	public void setXb_appeal_base_id( String xb_appeal_base_id1 )
	{
		setValue( ITEM_XB_APPEAL_BASE_ID, xb_appeal_base_id1 );
	}

	/* 案由 */
	public String getApl_caus()
	{
		return getValue( ITEM_APL_CAUS );
	}

	public void setApl_caus( String apl_caus1 )
	{
		setValue( ITEM_APL_CAUS, apl_caus1 );
	}

	/* 是否受理 */
	public String getApl_is_accept()
	{
		return getValue( ITEM_APL_IS_ACCEPT );
	}

	public void setApl_is_accept( String apl_is_accept1 )
	{
		setValue( ITEM_APL_IS_ACCEPT, apl_is_accept1 );
	}

	/* 不受理原因 */
	public String getApl_refu_caus()
	{
		return getValue( ITEM_APL_REFU_CAUS );
	}

	public void setApl_refu_caus( String apl_refu_caus1 )
	{
		setValue( ITEM_APL_REFU_CAUS, apl_refu_caus1 );
	}

	/* 办理结果 */
	public String getApl_last_resu()
	{
		return getValue( ITEM_APL_LAST_RESU );
	}

	public void setApl_last_resu( String apl_last_resu1 )
	{
		setValue( ITEM_APL_LAST_RESU, apl_last_resu1 );
	}

	/* 办理结果细分 */
	public String getApl_last_resu_deta()
	{
		return getValue( ITEM_APL_LAST_RESU_DETA );
	}

	public void setApl_last_resu_deta( String apl_last_resu_deta1 )
	{
		setValue( ITEM_APL_LAST_RESU_DETA, apl_last_resu_deta1 );
	}

	/* 是否转立案 */
	public String getApl_is_to_case()
	{
		return getValue( ITEM_APL_IS_TO_CASE );
	}

	public void setApl_is_to_case( String apl_is_to_case1 )
	{
		setValue( ITEM_APL_IS_TO_CASE, apl_is_to_case1 );
	}

	/* 转立案单位 */
	public String getApl_transf_case_unit()
	{
		return getValue( ITEM_APL_TRANSF_CASE_UNIT );
	}

	public void setApl_transf_case_unit( String apl_transf_case_unit1 )
	{
		setValue( ITEM_APL_TRANSF_CASE_UNIT, apl_transf_case_unit1 );
	}

	/* 是否移转其他部门 */
	public String getApl_is_shift_othe_dept()
	{
		return getValue( ITEM_APL_IS_SHIFT_OTHE_DEPT );
	}

	public void setApl_is_shift_othe_dept( String apl_is_shift_othe_dept1 )
	{
		setValue( ITEM_APL_IS_SHIFT_OTHE_DEPT, apl_is_shift_othe_dept1 );
	}

	/* 其他部门 */
	public String getAccp_tranf_auth()
	{
		return getValue( ITEM_ACCP_TRANF_AUTH );
	}

	public void setAccp_tranf_auth( String accp_tranf_auth1 )
	{
		setValue( ITEM_ACCP_TRANF_AUTH, accp_tranf_auth1 );
	}

	/* 调解日期起 */
	public String getApl_int_from_date()
	{
		return getValue( ITEM_APL_INT_FROM_DATE );
	}

	public void setApl_int_from_date( String apl_int_from_date1 )
	{
		setValue( ITEM_APL_INT_FROM_DATE, apl_int_from_date1 );
	}

	/* 调解日期止 */
	public String getInt_end_date()
	{
		return getValue( ITEM_INT_END_DATE );
	}

	public void setInt_end_date( String int_end_date1 )
	{
		setValue( ITEM_INT_END_DATE, int_end_date1 );
	}

	/* 调解方式 */
	public String getApl_int_way()
	{
		return getValue( ITEM_APL_INT_WAY );
	}

	public void setApl_int_way( String apl_int_way1 )
	{
		setValue( ITEM_APL_INT_WAY, apl_int_way1 );
	}

	/* 调解情况 */
	public String getApl_int_situa()
	{
		return getValue( ITEM_APL_INT_SITUA );
	}

	public void setApl_int_situa( String apl_int_situa1 )
	{
		setValue( ITEM_APL_INT_SITUA, apl_int_situa1 );
	}

	/* 调解依据 */
	public String getApl_int_basis()
	{
		return getValue( ITEM_APL_INT_BASIS );
	}

	public void setApl_int_basis( String apl_int_basis1 )
	{
		setValue( ITEM_APL_INT_BASIS, apl_int_basis1 );
	}

	/* 调解人姓名 */
	public String getApl_media_per_name()
	{
		return getValue( ITEM_APL_MEDIA_PER_NAME );
	}

	public void setApl_media_per_name( String apl_media_per_name1 )
	{
		setValue( ITEM_APL_MEDIA_PER_NAME, apl_media_per_name1 );
	}

	/* 调解单位 */
	public String getApl_media_unit()
	{
		return getValue( ITEM_APL_MEDIA_UNIT );
	}

	public void setApl_media_unit( String apl_media_unit1 )
	{
		setValue( ITEM_APL_MEDIA_UNIT, apl_media_unit1 );
	}

	/* 争议金额 */
	public String getDis_am()
	{
		return getValue( ITEM_DIS_AM );
	}

	public void setDis_am( String dis_am1 )
	{
		setValue( ITEM_DIS_AM, dis_am1 );
	}

	/* 赔偿金额 */
	public String getApl_ame_sum()
	{
		return getValue( ITEM_APL_AME_SUM );
	}

	public void setApl_ame_sum( String apl_ame_sum1 )
	{
		setValue( ITEM_APL_AME_SUM, apl_ame_sum1 );
	}

	/* 增加一倍赔偿金额 */
	public String getDou_ame_am()
	{
		return getValue( ITEM_DOU_AME_AM );
	}

	public void setDou_ame_am( String dou_ame_am1 )
	{
		setValue( ITEM_DOU_AME_AM, dou_ame_am1 );
	}

	/* 精神赔偿金额 */
	public String getSpi_ame_am()
	{
		return getValue( ITEM_SPI_AME_AM );
	}

	public void setSpi_ame_am( String spi_ame_am1 )
	{
		setValue( ITEM_SPI_AME_AM, spi_ame_am1 );
	}

	/* 其他赔偿金额 */
	public String getApl_othe_ame_am()
	{
		return getValue( ITEM_APL_OTHE_AME_AM );
	}

	public void setApl_othe_ame_am( String apl_othe_ame_am1 )
	{
		setValue( ITEM_APL_OTHE_AME_AM, apl_othe_ame_am1 );
	}

	/* 退还货款金额 */
	public String getApl_return_good_am()
	{
		return getValue( ITEM_APL_RETURN_GOOD_AM );
	}

	public void setApl_return_good_am( String apl_return_good_am1 )
	{
		setValue( ITEM_APL_RETURN_GOOD_AM, apl_return_good_am1 );
	}

	/* 挽回经济损失金额(总) */
	public String getRed_eco_los()
	{
		return getValue( ITEM_RED_ECO_LOS );
	}

	public void setRed_eco_los( String red_eco_los1 )
	{
		setValue( ITEM_RED_ECO_LOS, red_eco_los1 );
	}

	/* 是否支持诉讼 */
	public String getApl_is_supp_laws()
	{
		return getValue( ITEM_APL_IS_SUPP_LAWS );
	}

	public void setApl_is_supp_laws( String apl_is_supp_laws1 )
	{
		setValue( ITEM_APL_IS_SUPP_LAWS, apl_is_supp_laws1 );
	}

	/* 支持诉讼 */
	public String getApl_supp_laws()
	{
		return getValue( ITEM_APL_SUPP_LAWS );
	}

	public void setApl_supp_laws( String apl_supp_laws1 )
	{
		setValue( ITEM_APL_SUPP_LAWS, apl_supp_laws1 );
	}

	/* 诉讼是否成功 */
	public String getApl_laws_is_succ()
	{
		return getValue( ITEM_APL_LAWS_IS_SUCC );
	}

	public void setApl_laws_is_succ( String apl_laws_is_succ1 )
	{
		setValue( ITEM_APL_LAWS_IS_SUCC, apl_laws_is_succ1 );
	}

	/* 诉讼结果 */
	public String getApl_laws_rst()
	{
		return getValue( ITEM_APL_LAWS_RST );
	}

	public void setApl_laws_rst( String apl_laws_rst1 )
	{
		setValue( ITEM_APL_LAWS_RST, apl_laws_rst1 );
	}

	/* 诉讼成功 */
	public String getApl_laws_succ()
	{
		return getValue( ITEM_APL_LAWS_SUCC );
	}

	public void setApl_laws_succ( String apl_laws_succ1 )
	{
		setValue( ITEM_APL_LAWS_SUCC, apl_laws_succ1 );
	}

	/* 诉讼挽回经济损失金额 */
	public String getApl_laws_red_eco_los_am()
	{
		return getValue( ITEM_APL_LAWS_RED_ECO_LOS_AM );
	}

	public void setApl_laws_red_eco_los_am( String apl_laws_red_eco_los_am1 )
	{
		setValue( ITEM_APL_LAWS_RED_ECO_LOS_AM, apl_laws_red_eco_los_am1 );
	}

	/* 媒体名称 */
	public String getApl_med_name()
	{
		return getValue( ITEM_APL_MED_NAME );
	}

	public void setApl_med_name( String apl_med_name1 )
	{
		setValue( ITEM_APL_MED_NAME, apl_med_name1 );
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

