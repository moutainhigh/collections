package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXbAppealResult extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_XB_APPEAL_RESULT_ID = "xb_appeal_result_id";			/* ���ߴ���ID */
	public static final String ITEM_XB_APPEAL_BASE_ID = "xb_appeal_base_id";			/* ���߻�����ϢID */
	public static final String ITEM_APL_CAUS = "apl_caus";			/* ���� */
	public static final String ITEM_APL_IS_ACCEPT = "apl_is_accept";			/* �Ƿ����� */
	public static final String ITEM_APL_REFU_CAUS = "apl_refu_caus";			/* ������ԭ�� */
	public static final String ITEM_APL_LAST_RESU = "apl_last_resu";			/* ������ */
	public static final String ITEM_APL_LAST_RESU_DETA = "apl_last_resu_deta";			/* ������ϸ�� */
	public static final String ITEM_APL_IS_TO_CASE = "apl_is_to_case";			/* �Ƿ�ת���� */
	public static final String ITEM_APL_TRANSF_CASE_UNIT = "apl_transf_case_unit";			/* ת������λ */
	public static final String ITEM_APL_IS_SHIFT_OTHE_DEPT = "apl_is_shift_othe_dept";			/* �Ƿ���ת�������� */
	public static final String ITEM_ACCP_TRANF_AUTH = "accp_tranf_auth";			/* �������� */
	public static final String ITEM_APL_INT_FROM_DATE = "apl_int_from_date";			/* ���������� */
	public static final String ITEM_INT_END_DATE = "int_end_date";			/* ��������ֹ */
	public static final String ITEM_APL_INT_WAY = "apl_int_way";			/* ���ⷽʽ */
	public static final String ITEM_APL_INT_SITUA = "apl_int_situa";			/* ������� */
	public static final String ITEM_APL_INT_BASIS = "apl_int_basis";			/* �������� */
	public static final String ITEM_APL_MEDIA_PER_NAME = "apl_media_per_name";			/* ���������� */
	public static final String ITEM_APL_MEDIA_UNIT = "apl_media_unit";			/* ���ⵥλ */
	public static final String ITEM_DIS_AM = "dis_am";			/* ������ */
	public static final String ITEM_APL_AME_SUM = "apl_ame_sum";			/* �⳥��� */
	public static final String ITEM_DOU_AME_AM = "dou_ame_am";			/* ����һ���⳥��� */
	public static final String ITEM_SPI_AME_AM = "spi_ame_am";			/* �����⳥��� */
	public static final String ITEM_APL_OTHE_AME_AM = "apl_othe_ame_am";			/* �����⳥��� */
	public static final String ITEM_APL_RETURN_GOOD_AM = "apl_return_good_am";			/* �˻������� */
	public static final String ITEM_RED_ECO_LOS = "red_eco_los";			/* ��ؾ�����ʧ���(��) */
	public static final String ITEM_APL_IS_SUPP_LAWS = "apl_is_supp_laws";			/* �Ƿ�֧������ */
	public static final String ITEM_APL_SUPP_LAWS = "apl_supp_laws";			/* ֧������ */
	public static final String ITEM_APL_LAWS_IS_SUCC = "apl_laws_is_succ";			/* �����Ƿ�ɹ� */
	public static final String ITEM_APL_LAWS_RST = "apl_laws_rst";			/* ���Ͻ�� */
	public static final String ITEM_APL_LAWS_SUCC = "apl_laws_succ";			/* ���ϳɹ� */
	public static final String ITEM_APL_LAWS_RED_ECO_LOS_AM = "apl_laws_red_eco_los_am";			/* ������ؾ�����ʧ��� */
	public static final String ITEM_APL_MED_NAME = "apl_med_name";			/* ý������ */
	public static final String ITEM_APL_LAST_RECORD_DATE = "apl_last_record_date";			/* ���ʱ�� */
	public static final String ITEM_ETL_ID = "etl_id";			/* ETL���к� */
	public static final String ITEM_ETL_FLAG = "etl_flag";			/* ETL����״̬ */
	public static final String ITEM_ETL_TIMESTAMP = "etl_timestamp";			/* ETLʱ��� */

	public VoXbAppealResult(DataBus value)
	{
		super(value);
	}

	public VoXbAppealResult()
	{
		super();
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

	/* ���߻�����ϢID */
	public String getXb_appeal_base_id()
	{
		return getValue( ITEM_XB_APPEAL_BASE_ID );
	}

	public void setXb_appeal_base_id( String xb_appeal_base_id1 )
	{
		setValue( ITEM_XB_APPEAL_BASE_ID, xb_appeal_base_id1 );
	}

	/* ���� */
	public String getApl_caus()
	{
		return getValue( ITEM_APL_CAUS );
	}

	public void setApl_caus( String apl_caus1 )
	{
		setValue( ITEM_APL_CAUS, apl_caus1 );
	}

	/* �Ƿ����� */
	public String getApl_is_accept()
	{
		return getValue( ITEM_APL_IS_ACCEPT );
	}

	public void setApl_is_accept( String apl_is_accept1 )
	{
		setValue( ITEM_APL_IS_ACCEPT, apl_is_accept1 );
	}

	/* ������ԭ�� */
	public String getApl_refu_caus()
	{
		return getValue( ITEM_APL_REFU_CAUS );
	}

	public void setApl_refu_caus( String apl_refu_caus1 )
	{
		setValue( ITEM_APL_REFU_CAUS, apl_refu_caus1 );
	}

	/* ������ */
	public String getApl_last_resu()
	{
		return getValue( ITEM_APL_LAST_RESU );
	}

	public void setApl_last_resu( String apl_last_resu1 )
	{
		setValue( ITEM_APL_LAST_RESU, apl_last_resu1 );
	}

	/* ������ϸ�� */
	public String getApl_last_resu_deta()
	{
		return getValue( ITEM_APL_LAST_RESU_DETA );
	}

	public void setApl_last_resu_deta( String apl_last_resu_deta1 )
	{
		setValue( ITEM_APL_LAST_RESU_DETA, apl_last_resu_deta1 );
	}

	/* �Ƿ�ת���� */
	public String getApl_is_to_case()
	{
		return getValue( ITEM_APL_IS_TO_CASE );
	}

	public void setApl_is_to_case( String apl_is_to_case1 )
	{
		setValue( ITEM_APL_IS_TO_CASE, apl_is_to_case1 );
	}

	/* ת������λ */
	public String getApl_transf_case_unit()
	{
		return getValue( ITEM_APL_TRANSF_CASE_UNIT );
	}

	public void setApl_transf_case_unit( String apl_transf_case_unit1 )
	{
		setValue( ITEM_APL_TRANSF_CASE_UNIT, apl_transf_case_unit1 );
	}

	/* �Ƿ���ת�������� */
	public String getApl_is_shift_othe_dept()
	{
		return getValue( ITEM_APL_IS_SHIFT_OTHE_DEPT );
	}

	public void setApl_is_shift_othe_dept( String apl_is_shift_othe_dept1 )
	{
		setValue( ITEM_APL_IS_SHIFT_OTHE_DEPT, apl_is_shift_othe_dept1 );
	}

	/* �������� */
	public String getAccp_tranf_auth()
	{
		return getValue( ITEM_ACCP_TRANF_AUTH );
	}

	public void setAccp_tranf_auth( String accp_tranf_auth1 )
	{
		setValue( ITEM_ACCP_TRANF_AUTH, accp_tranf_auth1 );
	}

	/* ���������� */
	public String getApl_int_from_date()
	{
		return getValue( ITEM_APL_INT_FROM_DATE );
	}

	public void setApl_int_from_date( String apl_int_from_date1 )
	{
		setValue( ITEM_APL_INT_FROM_DATE, apl_int_from_date1 );
	}

	/* ��������ֹ */
	public String getInt_end_date()
	{
		return getValue( ITEM_INT_END_DATE );
	}

	public void setInt_end_date( String int_end_date1 )
	{
		setValue( ITEM_INT_END_DATE, int_end_date1 );
	}

	/* ���ⷽʽ */
	public String getApl_int_way()
	{
		return getValue( ITEM_APL_INT_WAY );
	}

	public void setApl_int_way( String apl_int_way1 )
	{
		setValue( ITEM_APL_INT_WAY, apl_int_way1 );
	}

	/* ������� */
	public String getApl_int_situa()
	{
		return getValue( ITEM_APL_INT_SITUA );
	}

	public void setApl_int_situa( String apl_int_situa1 )
	{
		setValue( ITEM_APL_INT_SITUA, apl_int_situa1 );
	}

	/* �������� */
	public String getApl_int_basis()
	{
		return getValue( ITEM_APL_INT_BASIS );
	}

	public void setApl_int_basis( String apl_int_basis1 )
	{
		setValue( ITEM_APL_INT_BASIS, apl_int_basis1 );
	}

	/* ���������� */
	public String getApl_media_per_name()
	{
		return getValue( ITEM_APL_MEDIA_PER_NAME );
	}

	public void setApl_media_per_name( String apl_media_per_name1 )
	{
		setValue( ITEM_APL_MEDIA_PER_NAME, apl_media_per_name1 );
	}

	/* ���ⵥλ */
	public String getApl_media_unit()
	{
		return getValue( ITEM_APL_MEDIA_UNIT );
	}

	public void setApl_media_unit( String apl_media_unit1 )
	{
		setValue( ITEM_APL_MEDIA_UNIT, apl_media_unit1 );
	}

	/* ������ */
	public String getDis_am()
	{
		return getValue( ITEM_DIS_AM );
	}

	public void setDis_am( String dis_am1 )
	{
		setValue( ITEM_DIS_AM, dis_am1 );
	}

	/* �⳥��� */
	public String getApl_ame_sum()
	{
		return getValue( ITEM_APL_AME_SUM );
	}

	public void setApl_ame_sum( String apl_ame_sum1 )
	{
		setValue( ITEM_APL_AME_SUM, apl_ame_sum1 );
	}

	/* ����һ���⳥��� */
	public String getDou_ame_am()
	{
		return getValue( ITEM_DOU_AME_AM );
	}

	public void setDou_ame_am( String dou_ame_am1 )
	{
		setValue( ITEM_DOU_AME_AM, dou_ame_am1 );
	}

	/* �����⳥��� */
	public String getSpi_ame_am()
	{
		return getValue( ITEM_SPI_AME_AM );
	}

	public void setSpi_ame_am( String spi_ame_am1 )
	{
		setValue( ITEM_SPI_AME_AM, spi_ame_am1 );
	}

	/* �����⳥��� */
	public String getApl_othe_ame_am()
	{
		return getValue( ITEM_APL_OTHE_AME_AM );
	}

	public void setApl_othe_ame_am( String apl_othe_ame_am1 )
	{
		setValue( ITEM_APL_OTHE_AME_AM, apl_othe_ame_am1 );
	}

	/* �˻������� */
	public String getApl_return_good_am()
	{
		return getValue( ITEM_APL_RETURN_GOOD_AM );
	}

	public void setApl_return_good_am( String apl_return_good_am1 )
	{
		setValue( ITEM_APL_RETURN_GOOD_AM, apl_return_good_am1 );
	}

	/* ��ؾ�����ʧ���(��) */
	public String getRed_eco_los()
	{
		return getValue( ITEM_RED_ECO_LOS );
	}

	public void setRed_eco_los( String red_eco_los1 )
	{
		setValue( ITEM_RED_ECO_LOS, red_eco_los1 );
	}

	/* �Ƿ�֧������ */
	public String getApl_is_supp_laws()
	{
		return getValue( ITEM_APL_IS_SUPP_LAWS );
	}

	public void setApl_is_supp_laws( String apl_is_supp_laws1 )
	{
		setValue( ITEM_APL_IS_SUPP_LAWS, apl_is_supp_laws1 );
	}

	/* ֧������ */
	public String getApl_supp_laws()
	{
		return getValue( ITEM_APL_SUPP_LAWS );
	}

	public void setApl_supp_laws( String apl_supp_laws1 )
	{
		setValue( ITEM_APL_SUPP_LAWS, apl_supp_laws1 );
	}

	/* �����Ƿ�ɹ� */
	public String getApl_laws_is_succ()
	{
		return getValue( ITEM_APL_LAWS_IS_SUCC );
	}

	public void setApl_laws_is_succ( String apl_laws_is_succ1 )
	{
		setValue( ITEM_APL_LAWS_IS_SUCC, apl_laws_is_succ1 );
	}

	/* ���Ͻ�� */
	public String getApl_laws_rst()
	{
		return getValue( ITEM_APL_LAWS_RST );
	}

	public void setApl_laws_rst( String apl_laws_rst1 )
	{
		setValue( ITEM_APL_LAWS_RST, apl_laws_rst1 );
	}

	/* ���ϳɹ� */
	public String getApl_laws_succ()
	{
		return getValue( ITEM_APL_LAWS_SUCC );
	}

	public void setApl_laws_succ( String apl_laws_succ1 )
	{
		setValue( ITEM_APL_LAWS_SUCC, apl_laws_succ1 );
	}

	/* ������ؾ�����ʧ��� */
	public String getApl_laws_red_eco_los_am()
	{
		return getValue( ITEM_APL_LAWS_RED_ECO_LOS_AM );
	}

	public void setApl_laws_red_eco_los_am( String apl_laws_red_eco_los_am1 )
	{
		setValue( ITEM_APL_LAWS_RED_ECO_LOS_AM, apl_laws_red_eco_los_am1 );
	}

	/* ý������ */
	public String getApl_med_name()
	{
		return getValue( ITEM_APL_MED_NAME );
	}

	public void setApl_med_name( String apl_med_name1 )
	{
		setValue( ITEM_APL_MED_NAME, apl_med_name1 );
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

