package com.gwssi.dw.aic.bj.ent.lis.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoMdsEntLisInf extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_MDS_ENT_LIS_INF_ID = "mds_ent_lis_inf_id";			/* 认证id */
	public static final String ITEM_MDS_ENT_ENT_INF_ID = "mds_ent_ent_inf_id";			/* 企业ID */
	public static final String ITEM_AUTH_RES = "auth_res";			/* 认证结果 */
	public static final String ITEM_LIS_NO = "lis_no";			/* 证书编号 */
	public static final String ITEM_AUTH_ENT = "auth_ent";			/* 认证/认定单位 */
	public static final String ITEM_ISS_DATE = "iss_date";			/* 发证日期 */
	public static final String ITEM_FILE_NAME = "file_name";			/* 文件名称 */
	public static final String ITEM_EFF_DATE = "eff_date";			/* 有效日期 */

	public VoMdsEntLisInf(DataBus value)
	{
		super(value);
	}

	public VoMdsEntLisInf()
	{
		super();
	}

	/* 认证id */
	public String getMds_ent_lis_inf_id()
	{
		return getValue( ITEM_MDS_ENT_LIS_INF_ID );
	}

	public void setMds_ent_lis_inf_id( String mds_ent_lis_inf_id1 )
	{
		setValue( ITEM_MDS_ENT_LIS_INF_ID, mds_ent_lis_inf_id1 );
	}

	/* 企业ID */
	public String getMds_ent_ent_inf_id()
	{
		return getValue( ITEM_MDS_ENT_ENT_INF_ID );
	}

	public void setMds_ent_ent_inf_id( String mds_ent_ent_inf_id1 )
	{
		setValue( ITEM_MDS_ENT_ENT_INF_ID, mds_ent_ent_inf_id1 );
	}

	/* 认证结果 */
	public String getAuth_res()
	{
		return getValue( ITEM_AUTH_RES );
	}

	public void setAuth_res( String auth_res1 )
	{
		setValue( ITEM_AUTH_RES, auth_res1 );
	}

	/* 证书编号 */
	public String getLis_no()
	{
		return getValue( ITEM_LIS_NO );
	}

	public void setLis_no( String lis_no1 )
	{
		setValue( ITEM_LIS_NO, lis_no1 );
	}

	/* 认证/认定单位 */
	public String getAuth_ent()
	{
		return getValue( ITEM_AUTH_ENT );
	}

	public void setAuth_ent( String auth_ent1 )
	{
		setValue( ITEM_AUTH_ENT, auth_ent1 );
	}

	/* 发证日期 */
	public String getIss_date()
	{
		return getValue( ITEM_ISS_DATE );
	}

	public void setIss_date( String iss_date1 )
	{
		setValue( ITEM_ISS_DATE, iss_date1 );
	}

	/* 文件名称 */
	public String getFile_name()
	{
		return getValue( ITEM_FILE_NAME );
	}

	public void setFile_name( String file_name1 )
	{
		setValue( ITEM_FILE_NAME, file_name1 );
	}

	/* 有效日期 */
	public String getEff_date()
	{
		return getValue( ITEM_EFF_DATE );
	}

	public void setEff_date( String eff_date1 )
	{
		setValue( ITEM_EFF_DATE, eff_date1 );
	}

}

