package com.gwssi.sysmgr.org.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXt_zzjg_jg extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_JGID_PK = "jgid_pk";			/* 机构ID */
	public static final String ITEM_SJJGID_FK = "sjjgid_fk";			/* 上级机构ID */
	public static final String ITEM_SJJGNAME = "sjjgname";			/* 上级机构name */
	public static final String ITEM_JGBH = "jgbh";			/* 机构编号 */
	public static final String ITEM_JGMC = "jgmc";			/* 机构名称 */
	public static final String ITEM_JGJC = "jgjc";			/* 机构简称 */
	public static final String ITEM_JGLX = "jglx";			/* 机构类型 */
	public static final String ITEM_JGFZR = "jgfzr";			/* 机构负责人 */
	public static final String ITEM_SFYX = "sfyx";			/* 是否有效 */
	public static final String ITEM_PLXH = "plxh";			/* 排列序号 */
	public static final String ITEM_BZ = "bz";			/* 备注 */

	public VoXt_zzjg_jg(DataBus value)
	{
		super(value);
	}

	public VoXt_zzjg_jg()
	{
		super();
	}

	/* 机构ID */
	public String getJgid_pk()
	{
		return getValue( ITEM_JGID_PK );
	}

	public void setJgid_pk( String jgid_pk1 )
	{
		setValue( ITEM_JGID_PK, jgid_pk1 );
	}

	/* 上级机构ID */
	public String getSjjgid_fk()
	{
		return getValue( ITEM_SJJGID_FK );
	}

	public void setSjjgid_fk( String sjjgid_fk1 )
	{
		setValue( ITEM_SJJGID_FK, sjjgid_fk1 );
	}

	/* 上级机构name */
	public String getSjjgname()
	{
		return getValue( ITEM_SJJGNAME );
	}

	public void setSjjgname( String sjjgname1 )
	{
		setValue( ITEM_SJJGNAME, sjjgname1 );
	}

	/* 机构编号 */
	public String getJgbh()
	{
		return getValue( ITEM_JGBH );
	}

	public void setJgbh( String jgbh1 )
	{
		setValue( ITEM_JGBH, jgbh1 );
	}

	/* 机构名称 */
	public String getJgmc()
	{
		return getValue( ITEM_JGMC );
	}

	public void setJgmc( String jgmc1 )
	{
		setValue( ITEM_JGMC, jgmc1 );
	}

	/* 机构简称 */
	public String getJgjc()
	{
		return getValue( ITEM_JGJC );
	}

	public void setJgjc( String jgjc1 )
	{
		setValue( ITEM_JGJC, jgjc1 );
	}

	/* 机构类型 */
	public String getJglx()
	{
		return getValue( ITEM_JGLX );
	}

	public void setJglx( String jglx1 )
	{
		setValue( ITEM_JGLX, jglx1 );
	}

	/* 机构负责人 */
	public String getJgfzr()
	{
		return getValue( ITEM_JGFZR );
	}

	public void setJgfzr( String jgfzr1 )
	{
		setValue( ITEM_JGFZR, jgfzr1 );
	}

	/* 是否有效 */
	public String getSfyx()
	{
		return getValue( ITEM_SFYX );
	}

	public void setSfyx( String sfyx1 )
	{
		setValue( ITEM_SFYX, sfyx1 );
	}

	/* 排列序号 */
	public String getPlxh()
	{
		return getValue( ITEM_PLXH );
	}

	public void setPlxh( String plxh1 )
	{
		setValue( ITEM_PLXH, plxh1 );
	}

	/* 备注 */
	public String getBz()
	{
		return getValue( ITEM_BZ );
	}

	public void setBz( String bz1 )
	{
		setValue( ITEM_BZ, bz1 );
	}

}

