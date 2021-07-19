package com.gwssi.dw.aic.bj.xb.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXbRptConfiGoods extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_XB_RPT_CONFI_GOODS_ID = "xb_rpt_confi_goods_id";			/* �ٱ�����û����ƷID */
	public static final String ITEM_XB_REPORT_RESULT_ID = "xb_report_result_id";			/* �ٱ�������ϢID */
	public static final String ITEM_RPT_FORF_GOODS_NAME = "rpt_forf_goods_name";			/* û����Ʒ���� */
	public static final String ITEM_RPT_FORF_GOODS_NUM = "rpt_forf_goods_num";			/* û����Ʒ���� */
	public static final String ITEM_RPT_FORF_GOODS_UNIT = "rpt_forf_goods_unit";			/* û����Ʒ��λ */
	public static final String ITEM_RPT_FORF_GOODS_VALUE = "rpt_forf_goods_value";			/* û����Ʒ��ֵ */
	public static final String ITEM_ETL_ID = "etl_id";			/* ETL���к� */
	public static final String ITEM_ETL_FLAG = "etl_flag";			/* ETL����״̬ */
	public static final String ITEM_ETL_TIMESTAMP = "etl_timestamp";			/* ETLʱ��� */

	public VoXbRptConfiGoods(DataBus value)
	{
		super(value);
	}

	public VoXbRptConfiGoods()
	{
		super();
	}

	/* �ٱ�����û����ƷID */
	public String getXb_rpt_confi_goods_id()
	{
		return getValue( ITEM_XB_RPT_CONFI_GOODS_ID );
	}

	public void setXb_rpt_confi_goods_id( String xb_rpt_confi_goods_id1 )
	{
		setValue( ITEM_XB_RPT_CONFI_GOODS_ID, xb_rpt_confi_goods_id1 );
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

	/* û����Ʒ���� */
	public String getRpt_forf_goods_name()
	{
		return getValue( ITEM_RPT_FORF_GOODS_NAME );
	}

	public void setRpt_forf_goods_name( String rpt_forf_goods_name1 )
	{
		setValue( ITEM_RPT_FORF_GOODS_NAME, rpt_forf_goods_name1 );
	}

	/* û����Ʒ���� */
	public String getRpt_forf_goods_num()
	{
		return getValue( ITEM_RPT_FORF_GOODS_NUM );
	}

	public void setRpt_forf_goods_num( String rpt_forf_goods_num1 )
	{
		setValue( ITEM_RPT_FORF_GOODS_NUM, rpt_forf_goods_num1 );
	}

	/* û����Ʒ��λ */
	public String getRpt_forf_goods_unit()
	{
		return getValue( ITEM_RPT_FORF_GOODS_UNIT );
	}

	public void setRpt_forf_goods_unit( String rpt_forf_goods_unit1 )
	{
		setValue( ITEM_RPT_FORF_GOODS_UNIT, rpt_forf_goods_unit1 );
	}

	/* û����Ʒ��ֵ */
	public String getRpt_forf_goods_value()
	{
		return getValue( ITEM_RPT_FORF_GOODS_VALUE );
	}

	public void setRpt_forf_goods_value( String rpt_forf_goods_value1 )
	{
		setValue( ITEM_RPT_FORF_GOODS_VALUE, rpt_forf_goods_value1 );
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

