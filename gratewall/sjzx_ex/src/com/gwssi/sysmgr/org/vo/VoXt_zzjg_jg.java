package com.gwssi.sysmgr.org.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXt_zzjg_jg extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_JGID_PK = "jgid_pk";			/* ����ID */
	public static final String ITEM_SJJGID_FK = "sjjgid_fk";			/* �ϼ�����ID */
	public static final String ITEM_SJJGNAME = "sjjgname";			/* �ϼ�����name */
	public static final String ITEM_JGBH = "jgbh";			/* ������� */
	public static final String ITEM_JGMC = "jgmc";			/* �������� */
	public static final String ITEM_JGJC = "jgjc";			/* ������� */
	public static final String ITEM_JGLX = "jglx";			/* �������� */
	public static final String ITEM_JGFZR = "jgfzr";			/* ���������� */
	public static final String ITEM_SFYX = "sfyx";			/* �Ƿ���Ч */
	public static final String ITEM_PLXH = "plxh";			/* ������� */
	public static final String ITEM_BZ = "bz";			/* ��ע */

	public VoXt_zzjg_jg(DataBus value)
	{
		super(value);
	}

	public VoXt_zzjg_jg()
	{
		super();
	}

	/* ����ID */
	public String getJgid_pk()
	{
		return getValue( ITEM_JGID_PK );
	}

	public void setJgid_pk( String jgid_pk1 )
	{
		setValue( ITEM_JGID_PK, jgid_pk1 );
	}

	/* �ϼ�����ID */
	public String getSjjgid_fk()
	{
		return getValue( ITEM_SJJGID_FK );
	}

	public void setSjjgid_fk( String sjjgid_fk1 )
	{
		setValue( ITEM_SJJGID_FK, sjjgid_fk1 );
	}

	/* �ϼ�����name */
	public String getSjjgname()
	{
		return getValue( ITEM_SJJGNAME );
	}

	public void setSjjgname( String sjjgname1 )
	{
		setValue( ITEM_SJJGNAME, sjjgname1 );
	}

	/* ������� */
	public String getJgbh()
	{
		return getValue( ITEM_JGBH );
	}

	public void setJgbh( String jgbh1 )
	{
		setValue( ITEM_JGBH, jgbh1 );
	}

	/* �������� */
	public String getJgmc()
	{
		return getValue( ITEM_JGMC );
	}

	public void setJgmc( String jgmc1 )
	{
		setValue( ITEM_JGMC, jgmc1 );
	}

	/* ������� */
	public String getJgjc()
	{
		return getValue( ITEM_JGJC );
	}

	public void setJgjc( String jgjc1 )
	{
		setValue( ITEM_JGJC, jgjc1 );
	}

	/* �������� */
	public String getJglx()
	{
		return getValue( ITEM_JGLX );
	}

	public void setJglx( String jglx1 )
	{
		setValue( ITEM_JGLX, jglx1 );
	}

	/* ���������� */
	public String getJgfzr()
	{
		return getValue( ITEM_JGFZR );
	}

	public void setJgfzr( String jgfzr1 )
	{
		setValue( ITEM_JGFZR, jgfzr1 );
	}

	/* �Ƿ���Ч */
	public String getSfyx()
	{
		return getValue( ITEM_SFYX );
	}

	public void setSfyx( String sfyx1 )
	{
		setValue( ITEM_SFYX, sfyx1 );
	}

	/* ������� */
	public String getPlxh()
	{
		return getValue( ITEM_PLXH );
	}

	public void setPlxh( String plxh1 )
	{
		setValue( ITEM_PLXH, plxh1 );
	}

	/* ��ע */
	public String getBz()
	{
		return getValue( ITEM_BZ );
	}

	public void setBz( String bz1 )
	{
		setValue( ITEM_BZ, bz1 );
	}

}

