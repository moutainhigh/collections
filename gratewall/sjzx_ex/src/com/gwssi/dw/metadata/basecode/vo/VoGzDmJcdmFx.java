package com.gwssi.dw.metadata.basecode.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[gz_dm_jcdm_fx]的数据对象类
 * @author Administrator
 *
 */
public class VoGzDmJcdmFx extends VoBase
{
	private static final long serialVersionUID = 200708271316210001L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_JCSJFX_ID = "jcsjfx_id" ;		/* 基础数据分项ID */
	public static final String ITEM_JC_DM_ID = "jc_dm_id" ;			/* 基础代码ID */
	public static final String ITEM_JCSJFX_DM = "jcsjfx_dm" ;		/* 基础数据分项代码 */
	public static final String ITEM_JCSJFX_MC = "jcsjfx_mc" ;		/* 基础数据分项名称 */
	public static final String ITEM_JCSJFX_CJM = "jcsjfx_cjm" ;		/* 基础数据分项层级码 */
	public static final String ITEM_JCSJFX_FJD = "jcsjfx_fjd" ;		/* 基础数据分项父节点代码 */
	public static final String ITEM_SZCC = "szcc" ;					/* 所在层次 */
	public static final String ITEM_XSSX = "xssx" ;					/* 显示顺序 */
	public static final String ITEM_SFMX = "sfmx" ;					/* 是否明细 */
	public static final String ITEM_FX_MS = "fx_ms" ;				/* 分项描述 */
	public static final String ITEM_SY_ZT = "sy_zt" ;				/* 使用状态 */
	
	/**
	 * 构造函数
	 */
	public VoGzDmJcdmFx()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoGzDmJcdmFx(DataBus value)
	{
		super(value);
	}
	
	/* 基础数据分项ID : String */
	public String getJcsjfx_id()
	{
		return getValue( ITEM_JCSJFX_ID );
	}

	public void setJcsjfx_id( String jcsjfx_id1 )
	{
		setValue( ITEM_JCSJFX_ID, jcsjfx_id1 );
	}

	/* 基础代码ID : String */
	public String getJc_dm_id()
	{
		return getValue( ITEM_JC_DM_ID );
	}

	public void setJc_dm_id( String jc_dm_id1 )
	{
		setValue( ITEM_JC_DM_ID, jc_dm_id1 );
	}

	/* 基础数据分项代码 : String */
	public String getJcsjfx_dm()
	{
		return getValue( ITEM_JCSJFX_DM );
	}

	public void setJcsjfx_dm( String jcsjfx_dm1 )
	{
		setValue( ITEM_JCSJFX_DM, jcsjfx_dm1 );
	}

	/* 基础数据分项名称 : String */
	public String getJcsjfx_mc()
	{
		return getValue( ITEM_JCSJFX_MC );
	}

	public void setJcsjfx_mc( String jcsjfx_mc1 )
	{
		setValue( ITEM_JCSJFX_MC, jcsjfx_mc1 );
	}

	/* 基础数据分项层级码 : String */
	public String getJcsjfx_cjm()
	{
		return getValue( ITEM_JCSJFX_CJM );
	}

	public void setJcsjfx_cjm( String jcsjfx_cjm1 )
	{
		setValue( ITEM_JCSJFX_CJM, jcsjfx_cjm1 );
	}

	/* 基础数据分项父节点代码 : String */
	public String getJcsjfx_fjd()
	{
		return getValue( ITEM_JCSJFX_FJD );
	}

	public void setJcsjfx_fjd( String jcsjfx_fjd1 )
	{
		setValue( ITEM_JCSJFX_FJD, jcsjfx_fjd1 );
	}

	/* 所在层次 : String */
	public String getSzcc()
	{
		return getValue( ITEM_SZCC );
	}

	public void setSzcc( String szcc1 )
	{
		setValue( ITEM_SZCC, szcc1 );
	}

	/* 显示顺序 : String */
	public String getXssx()
	{
		return getValue( ITEM_XSSX );
	}

	public void setXssx( String xssx1 )
	{
		setValue( ITEM_XSSX, xssx1 );
	}

	/* 是否明细 : String */
	public String getSfmx()
	{
		return getValue( ITEM_SFMX );
	}

	public void setSfmx( String sfmx1 )
	{
		setValue( ITEM_SFMX, sfmx1 );
	}

	/* 分项描述 : String */
	public String getFx_ms()
	{
		return getValue( ITEM_FX_MS );
	}

	public void setFx_ms( String fx_ms1 )
	{
		setValue( ITEM_FX_MS, fx_ms1 );
	}

	/* 使用状态 : String */
	public String getSy_zt()
	{
		return getValue( ITEM_SY_ZT );
	}

	public void setSy_zt( String sy_zt1 )
	{
		setValue( ITEM_SY_ZT, sy_zt1 );
	}

}

