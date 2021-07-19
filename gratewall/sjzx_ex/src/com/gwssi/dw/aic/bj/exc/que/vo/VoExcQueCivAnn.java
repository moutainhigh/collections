package com.gwssi.dw.aic.bj.exc.que.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoExcQueCivAnn extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_EXC_QUE_CIV_ANN_ID = "exc_que_civ_ann_id";			/* �����Ϣ(������)ID */
	public static final String ITEM_ORGAN_CODE = "organ_code";			/* ��֯��������(��) */
	public static final String ITEM_AN_CHE_YEAR = "an_che_year";			/* ������ */
	public static final String ITEM_AN_CHE_DATE = "an_che_date";			/* ������� */
	public static final String ITEM_AN_CHE_RES = "an_che_res";			/* �����(Code) */

	public VoExcQueCivAnn(DataBus value)
	{
		super(value);
	}

	public VoExcQueCivAnn()
	{
		super();
	}

	/* �����Ϣ(������)ID */
	public String getExc_que_civ_ann_id()
	{
		return getValue( ITEM_EXC_QUE_CIV_ANN_ID );
	}

	public void setExc_que_civ_ann_id( String exc_que_civ_ann_id1 )
	{
		setValue( ITEM_EXC_QUE_CIV_ANN_ID, exc_que_civ_ann_id1 );
	}

	/* ��֯��������(��) */
	public String getOrgan_code()
	{
		return getValue( ITEM_ORGAN_CODE );
	}

	public void setOrgan_code( String organ_code1 )
	{
		setValue( ITEM_ORGAN_CODE, organ_code1 );
	}

	/* ������ */
	public String getAn_che_year()
	{
		return getValue( ITEM_AN_CHE_YEAR );
	}

	public void setAn_che_year( String an_che_year1 )
	{
		setValue( ITEM_AN_CHE_YEAR, an_che_year1 );
	}

	/* ������� */
	public String getAn_che_date()
	{
		return getValue( ITEM_AN_CHE_DATE );
	}

	public void setAn_che_date( String an_che_date1 )
	{
		setValue( ITEM_AN_CHE_DATE, an_che_date1 );
	}

	/* �����(Code) */
	public String getAn_che_res()
	{
		return getValue( ITEM_AN_CHE_RES );
	}

	public void setAn_che_res( String an_che_res1 )
	{
		setValue( ITEM_AN_CHE_RES, an_che_res1 );
	}

}

