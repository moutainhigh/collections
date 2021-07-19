package com.gwssi.sysmgr.user.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXt_zzjg_yh extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_YHID_PK = "yhid_pk";			/* �û�ID */
	public static final String ITEM_JGID_FK = "jgid_fk";			/* ��������ID */
	public static final String ITEM_YHZH = "yhzh";			/* �û��ʺ� */
	public static final String ITEM_YHMM = "yhmm";			/* �û����� */
	public static final String ITEM_YHXM = "yhxm";			/* �û����� */
	public static final String ITEM_SFYX = "sfyx";			/* �Ƿ���Ч */
	public static final String ITEM_PLXH = "plxh";			/* ������� */
	public static final String ITEM_SFZ = "sfz";			/* ���֤�� */
	public static final String ITEM_ZW = "zw";			/* ְλ */
	public static final String ITEM_ZYZZ = "zyzz";			/* ��Ҫְ�� */
	public static final String ITEM_GZDH = "gzdh";			/* �����绰 */
	public static final String ITEM_LXDH = "lxdh";			/* ��ϵ�绰 */
	public static final String ITEM_QTLXFS = "qtlxfs";			/* ������ϵ��ʽ */
	public static final String ITEM_DZYX = "dzyx";			/* �����ʼ� */
	public static final String ITEM_BZ = "bz";			/* ��ע */
	public static final String ITEM_JZJGID = "jzjgid";			/* ��ְ����ID */
	public static final String ITEM_ROLEIDS = "roleids";			/* ��ɫID�б� */
	public static final String ITEM_MAINROLE = "mainrole";			/* ����ɫ */
	public static final String ITEM_YHBH = "yhbh";			/* �û���� */
	public static final String ITEM_JGNAME = "jgname";			/* ������������ */
	public static final String ITEM_JZJGNAME = "jzjgname";			/* ��ְ�������� */
	public static final String ITEM_ROLENAMES = "rolenames";			/* ������ɫ���� */
	public static final String ITEM_MAINROLENAME = "mainrolename";			/* ����ɫ���� */
	public static final String ITEM_ROLETYPE = "roletype";			/* �û����� */

	public VoXt_zzjg_yh(DataBus value)
	{
		super(value);
	}

	public VoXt_zzjg_yh()
	{
		super();
	}

	/* �û�ID */
	public String getYhid_pk()
	{
		return getValue( ITEM_YHID_PK );
	}

	public void setYhid_pk( String yhid_pk1 )
	{
		setValue( ITEM_YHID_PK, yhid_pk1 );
	}

	/* ��������ID */
	public String getJgid_fk()
	{
		return getValue( ITEM_JGID_FK );
	}

	public void setJgid_fk( String jgid_fk1 )
	{
		setValue( ITEM_JGID_FK, jgid_fk1 );
	}

	/* �û��ʺ� */
	public String getYhzh()
	{
		return getValue( ITEM_YHZH );
	}

	public void setYhzh( String yhzh1 )
	{
		setValue( ITEM_YHZH, yhzh1 );
	}

	/* �û����� */
	public String getYhmm()
	{
		return getValue( ITEM_YHMM );
	}

	public void setYhmm( String yhmm1 )
	{
		setValue( ITEM_YHMM, yhmm1 );
	}

	/* �û����� */
	public String getYhxm()
	{
		return getValue( ITEM_YHXM );
	}

	public void setYhxm( String yhxm1 )
	{
		setValue( ITEM_YHXM, yhxm1 );
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

	/* ���֤�� */
	public String getSfz()
	{
		return getValue( ITEM_SFZ );
	}

	public void setSfz( String sfz1 )
	{
		setValue( ITEM_SFZ, sfz1 );
	}

	/* ְλ */
	public String getZw()
	{
		return getValue( ITEM_ZW );
	}

	public void setZw( String zw1 )
	{
		setValue( ITEM_ZW, zw1 );
	}

	/* ��Ҫְ�� */
	public String getZyzz()
	{
		return getValue( ITEM_ZYZZ );
	}

	public void setZyzz( String zyzz1 )
	{
		setValue( ITEM_ZYZZ, zyzz1 );
	}

	/* �����绰 */
	public String getGzdh()
	{
		return getValue( ITEM_GZDH );
	}

	public void setGzdh( String gzdh1 )
	{
		setValue( ITEM_GZDH, gzdh1 );
	}

	/* ��ϵ�绰 */
	public String getLxdh()
	{
		return getValue( ITEM_LXDH );
	}

	public void setLxdh( String lxdh1 )
	{
		setValue( ITEM_LXDH, lxdh1 );
	}

	/* ������ϵ��ʽ */
	public String getQtlxfs()
	{
		return getValue( ITEM_QTLXFS );
	}

	public void setQtlxfs( String qtlxfs1 )
	{
		setValue( ITEM_QTLXFS, qtlxfs1 );
	}

	/* �����ʼ� */
	public String getDzyx()
	{
		return getValue( ITEM_DZYX );
	}

	public void setDzyx( String dzyx1 )
	{
		setValue( ITEM_DZYX, dzyx1 );
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

	/* ��ְ����ID */
	public String getJzjgid()
	{
		return getValue( ITEM_JZJGID );
	}

	public void setJzjgid( String jzjgid1 )
	{
		setValue( ITEM_JZJGID, jzjgid1 );
	}

	/* ��ɫID�б� */
	public String getRoleids()
	{
		return getValue( ITEM_ROLEIDS );
	}

	public void setRoleids( String roleids1 )
	{
		setValue( ITEM_ROLEIDS, roleids1 );
	}

	/* ����ɫ */
	public String getMainrole()
	{
		return getValue( ITEM_MAINROLE );
	}

	public void setMainrole( String mainrole1 )
	{
		setValue( ITEM_MAINROLE, mainrole1 );
	}

	/* �û���� */
	public String getYhbh()
	{
		return getValue( ITEM_YHBH );
	}

	public void setYhbh( String yhbh1 )
	{
		setValue( ITEM_YHBH, yhbh1 );
	}

	/* ������������ */
	public String getJgname()
	{
		return getValue( ITEM_JGNAME );
	}

	public void setJgname( String jgname1 )
	{
		setValue( ITEM_JGNAME, jgname1 );
	}

	/* ��ְ�������� */
	public String getJzjgname()
	{
		return getValue( ITEM_JZJGNAME );
	}

	public void setJzjgname( String jzjgname1 )
	{
		setValue( ITEM_JZJGNAME, jzjgname1 );
	}

	/* ������ɫ���� */
	public String getRolenames()
	{
		return getValue( ITEM_ROLENAMES );
	}

	public void setRolenames( String rolenames1 )
	{
		setValue( ITEM_ROLENAMES, rolenames1 );
	}

	/* ����ɫ���� */
	public String getMainrolename()
	{
		return getValue( ITEM_MAINROLENAME );
	}

	public void setMainrolename( String mainrolename1 )
	{
		setValue( ITEM_MAINROLENAME, mainrolename1 );
	}

	/* �û����� */
	public String getRoletype()
	{
		return getValue( ITEM_ROLETYPE );
	}

	public void setRoletype( String roletype1 )
	{
		setValue( ITEM_ROLETYPE, roletype1 );
	}

}

