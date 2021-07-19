package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXbRptTreaFakeGoods extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_ID = "rpt_trea_fake_goods_id";			/* �鴦��ð����ID */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_DATE = "rpt_trea_fake_goods_date";			/* �鴦ʱ�� */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_NAME = "rpt_trea_fake_goods_name";			/* �������� */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_NUM = "rpt_trea_fake_goods_num";			/* �������� */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_UNIT = "rpt_trea_fake_goods_unit";			/* ���ʵ�λ */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_VALUE = "rpt_trea_fake_goods_value";			/* ���ʼ�ֵ */
	public static final String ITEM_ETL_ID = "etl_id";			/* ETL���к� */
	public static final String ITEM_ETL_FLAG = "etl_flag";			/* ETL����״̬ */
	public static final String ITEM_ETL_TIMESTAMP = "etl_timestamp";			/* ETLʱ��� */
	public static final String ITEM_XB_REPORT_RESULT_ID = "xb_report_result_id";			/* �ٱ�����ID */

	public VoXbRptTreaFakeGoods(DataBus value)
	{
		super(value);
	}

	public VoXbRptTreaFakeGoods()
	{
		super();
	}

	/* �鴦��ð����ID */
	public String getRpt_trea_fake_goods_id()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_ID );
	}

	public void setRpt_trea_fake_goods_id( String rpt_trea_fake_goods_id1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_ID, rpt_trea_fake_goods_id1 );
	}

	/* �鴦ʱ�� */
	public String getRpt_trea_fake_goods_date()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_DATE );
	}

	public void setRpt_trea_fake_goods_date( String rpt_trea_fake_goods_date1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_DATE, rpt_trea_fake_goods_date1 );
	}

	/* �������� */
	public String getRpt_trea_fake_goods_name()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_NAME );
	}

	public void setRpt_trea_fake_goods_name( String rpt_trea_fake_goods_name1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_NAME, rpt_trea_fake_goods_name1 );
	}

	/* �������� */
	public String getRpt_trea_fake_goods_num()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_NUM );
	}

	public void setRpt_trea_fake_goods_num( String rpt_trea_fake_goods_num1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_NUM, rpt_trea_fake_goods_num1 );
	}

	/* ���ʵ�λ */
	public String getRpt_trea_fake_goods_unit()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_UNIT );
	}

	public void setRpt_trea_fake_goods_unit( String rpt_trea_fake_goods_unit1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_UNIT, rpt_trea_fake_goods_unit1 );
	}

	/* ���ʼ�ֵ */
	public String getRpt_trea_fake_goods_value()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_VALUE );
	}

	public void setRpt_trea_fake_goods_value( String rpt_trea_fake_goods_value1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_VALUE, rpt_trea_fake_goods_value1 );
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

	/* �ٱ�����ID */
	public String getXb_report_result_id()
	{
		return getValue( ITEM_XB_REPORT_RESULT_ID );
	}

	public void setXb_report_result_id( String xb_report_result_id1 )
	{
		setValue( ITEM_XB_REPORT_RESULT_ID, xb_report_result_id1 );
	}

}

