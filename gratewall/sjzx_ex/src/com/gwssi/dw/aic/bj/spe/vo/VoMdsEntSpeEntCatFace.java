package com.gwssi.dw.aic.bj.spe.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoMdsEntSpeEntCatFace extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_MDS_ENT_SPE_ENT_CAT_FACE_ID = "mds_ent_spe_ent_cat_face_id";			/* 特殊企业分类对应表ID */
	public static final String ITEM_SPECI_ENT_TYPE_ID = "speci_ent_type_id";			/* 特殊企业分类代码 */
	public static final String ITEM_MDS_ENT_ENT_INF_ID = "mds_ent_ent_inf_id";			/* 企业ID */

	public VoMdsEntSpeEntCatFace(DataBus value)
	{
		super(value);
	}

	public VoMdsEntSpeEntCatFace()
	{
		super();
	}

	/* 特殊企业分类对应表ID */
	public String getMds_ent_spe_ent_cat_face_id()
	{
		return getValue( ITEM_MDS_ENT_SPE_ENT_CAT_FACE_ID );
	}

	public void setMds_ent_spe_ent_cat_face_id( String mds_ent_spe_ent_cat_face_id1 )
	{
		setValue( ITEM_MDS_ENT_SPE_ENT_CAT_FACE_ID, mds_ent_spe_ent_cat_face_id1 );
	}

	/* 特殊企业分类代码 */
	public String getSpeci_ent_type_id()
	{
		return getValue( ITEM_SPECI_ENT_TYPE_ID );
	}

	public void setSpeci_ent_type_id( String speci_ent_type_id1 )
	{
		setValue( ITEM_SPECI_ENT_TYPE_ID, speci_ent_type_id1 );
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

}

