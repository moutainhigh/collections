package com.gwssi.dw.aic.bj.ent.lis.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoMdsEntLisInf extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_MDS_ENT_LIS_INF_ID = "mds_ent_lis_inf_id";			/* ��֤id */
	public static final String ITEM_MDS_ENT_ENT_INF_ID = "mds_ent_ent_inf_id";			/* ��ҵID */
	public static final String ITEM_AUTH_RES = "auth_res";			/* ��֤��� */
	public static final String ITEM_LIS_NO = "lis_no";			/* ֤���� */
	public static final String ITEM_AUTH_ENT = "auth_ent";			/* ��֤/�϶���λ */
	public static final String ITEM_ISS_DATE = "iss_date";			/* ��֤���� */
	public static final String ITEM_FILE_NAME = "file_name";			/* �ļ����� */
	public static final String ITEM_EFF_DATE = "eff_date";			/* ��Ч���� */

	public VoMdsEntLisInf(DataBus value)
	{
		super(value);
	}

	public VoMdsEntLisInf()
	{
		super();
	}

	/* ��֤id */
	public String getMds_ent_lis_inf_id()
	{
		return getValue( ITEM_MDS_ENT_LIS_INF_ID );
	}

	public void setMds_ent_lis_inf_id( String mds_ent_lis_inf_id1 )
	{
		setValue( ITEM_MDS_ENT_LIS_INF_ID, mds_ent_lis_inf_id1 );
	}

	/* ��ҵID */
	public String getMds_ent_ent_inf_id()
	{
		return getValue( ITEM_MDS_ENT_ENT_INF_ID );
	}

	public void setMds_ent_ent_inf_id( String mds_ent_ent_inf_id1 )
	{
		setValue( ITEM_MDS_ENT_ENT_INF_ID, mds_ent_ent_inf_id1 );
	}

	/* ��֤��� */
	public String getAuth_res()
	{
		return getValue( ITEM_AUTH_RES );
	}

	public void setAuth_res( String auth_res1 )
	{
		setValue( ITEM_AUTH_RES, auth_res1 );
	}

	/* ֤���� */
	public String getLis_no()
	{
		return getValue( ITEM_LIS_NO );
	}

	public void setLis_no( String lis_no1 )
	{
		setValue( ITEM_LIS_NO, lis_no1 );
	}

	/* ��֤/�϶���λ */
	public String getAuth_ent()
	{
		return getValue( ITEM_AUTH_ENT );
	}

	public void setAuth_ent( String auth_ent1 )
	{
		setValue( ITEM_AUTH_ENT, auth_ent1 );
	}

	/* ��֤���� */
	public String getIss_date()
	{
		return getValue( ITEM_ISS_DATE );
	}

	public void setIss_date( String iss_date1 )
	{
		setValue( ITEM_ISS_DATE, iss_date1 );
	}

	/* �ļ����� */
	public String getFile_name()
	{
		return getValue( ITEM_FILE_NAME );
	}

	public void setFile_name( String file_name1 )
	{
		setValue( ITEM_FILE_NAME, file_name1 );
	}

	/* ��Ч���� */
	public String getEff_date()
	{
		return getValue( ITEM_EFF_DATE );
	}

	public void setEff_date( String eff_date1 )
	{
		setValue( ITEM_EFF_DATE, eff_date1 );
	}

}

