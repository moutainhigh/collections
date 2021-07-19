package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXbRptTreaFakeGoods extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_ID = "rpt_trea_fake_goods_id";			/* 查处假冒物资ID */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_DATE = "rpt_trea_fake_goods_date";			/* 查处时间 */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_NAME = "rpt_trea_fake_goods_name";			/* 物资名称 */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_NUM = "rpt_trea_fake_goods_num";			/* 物资数量 */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_UNIT = "rpt_trea_fake_goods_unit";			/* 物资单位 */
	public static final String ITEM_RPT_TREA_FAKE_GOODS_VALUE = "rpt_trea_fake_goods_value";			/* 物资价值 */
	public static final String ITEM_ETL_ID = "etl_id";			/* ETL序列号 */
	public static final String ITEM_ETL_FLAG = "etl_flag";			/* ETL数据状态 */
	public static final String ITEM_ETL_TIMESTAMP = "etl_timestamp";			/* ETL时间戳 */
	public static final String ITEM_XB_REPORT_RESULT_ID = "xb_report_result_id";			/* 举报处理ID */

	public VoXbRptTreaFakeGoods(DataBus value)
	{
		super(value);
	}

	public VoXbRptTreaFakeGoods()
	{
		super();
	}

	/* 查处假冒物资ID */
	public String getRpt_trea_fake_goods_id()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_ID );
	}

	public void setRpt_trea_fake_goods_id( String rpt_trea_fake_goods_id1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_ID, rpt_trea_fake_goods_id1 );
	}

	/* 查处时间 */
	public String getRpt_trea_fake_goods_date()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_DATE );
	}

	public void setRpt_trea_fake_goods_date( String rpt_trea_fake_goods_date1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_DATE, rpt_trea_fake_goods_date1 );
	}

	/* 物资名称 */
	public String getRpt_trea_fake_goods_name()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_NAME );
	}

	public void setRpt_trea_fake_goods_name( String rpt_trea_fake_goods_name1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_NAME, rpt_trea_fake_goods_name1 );
	}

	/* 物资数量 */
	public String getRpt_trea_fake_goods_num()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_NUM );
	}

	public void setRpt_trea_fake_goods_num( String rpt_trea_fake_goods_num1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_NUM, rpt_trea_fake_goods_num1 );
	}

	/* 物资单位 */
	public String getRpt_trea_fake_goods_unit()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_UNIT );
	}

	public void setRpt_trea_fake_goods_unit( String rpt_trea_fake_goods_unit1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_UNIT, rpt_trea_fake_goods_unit1 );
	}

	/* 物资价值 */
	public String getRpt_trea_fake_goods_value()
	{
		return getValue( ITEM_RPT_TREA_FAKE_GOODS_VALUE );
	}

	public void setRpt_trea_fake_goods_value( String rpt_trea_fake_goods_value1 )
	{
		setValue( ITEM_RPT_TREA_FAKE_GOODS_VALUE, rpt_trea_fake_goods_value1 );
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

	/* 举报处理ID */
	public String getXb_report_result_id()
	{
		return getValue( ITEM_XB_REPORT_RESULT_ID );
	}

	public void setXb_report_result_id( String xb_report_result_id1 )
	{
		setValue( ITEM_XB_REPORT_RESULT_ID, xb_report_result_id1 );
	}

}

