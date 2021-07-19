package com.gwssi.sysmgr.user.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoXt_zzjg_yh extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_YHID_PK = "yhid_pk";			/* 用户ID */
	public static final String ITEM_JGID_FK = "jgid_fk";			/* 所属机构ID */
	public static final String ITEM_YHZH = "yhzh";			/* 用户帐号 */
	public static final String ITEM_YHMM = "yhmm";			/* 用户密码 */
	public static final String ITEM_YHXM = "yhxm";			/* 用户姓名 */
	public static final String ITEM_SFYX = "sfyx";			/* 是否有效 */
	public static final String ITEM_PLXH = "plxh";			/* 排列序号 */
	public static final String ITEM_SFZ = "sfz";			/* 身份证号 */
	public static final String ITEM_ZW = "zw";			/* 职位 */
	public static final String ITEM_ZYZZ = "zyzz";			/* 主要职责 */
	public static final String ITEM_GZDH = "gzdh";			/* 工作电话 */
	public static final String ITEM_LXDH = "lxdh";			/* 联系电话 */
	public static final String ITEM_QTLXFS = "qtlxfs";			/* 其他联系方式 */
	public static final String ITEM_DZYX = "dzyx";			/* 电子邮件 */
	public static final String ITEM_BZ = "bz";			/* 备注 */
	public static final String ITEM_JZJGID = "jzjgid";			/* 兼职机构ID */
	public static final String ITEM_ROLEIDS = "roleids";			/* 角色ID列表 */
	public static final String ITEM_MAINROLE = "mainrole";			/* 主角色 */
	public static final String ITEM_YHBH = "yhbh";			/* 用户编号 */
	public static final String ITEM_JGNAME = "jgname";			/* 所属机构名称 */
	public static final String ITEM_JZJGNAME = "jzjgname";			/* 兼职机构名称 */
	public static final String ITEM_ROLENAMES = "rolenames";			/* 所属角色名称 */
	public static final String ITEM_MAINROLENAME = "mainrolename";			/* 主角色名称 */
	public static final String ITEM_ROLETYPE = "roletype";			/* 用户类型 */

	public VoXt_zzjg_yh(DataBus value)
	{
		super(value);
	}

	public VoXt_zzjg_yh()
	{
		super();
	}

	/* 用户ID */
	public String getYhid_pk()
	{
		return getValue( ITEM_YHID_PK );
	}

	public void setYhid_pk( String yhid_pk1 )
	{
		setValue( ITEM_YHID_PK, yhid_pk1 );
	}

	/* 所属机构ID */
	public String getJgid_fk()
	{
		return getValue( ITEM_JGID_FK );
	}

	public void setJgid_fk( String jgid_fk1 )
	{
		setValue( ITEM_JGID_FK, jgid_fk1 );
	}

	/* 用户帐号 */
	public String getYhzh()
	{
		return getValue( ITEM_YHZH );
	}

	public void setYhzh( String yhzh1 )
	{
		setValue( ITEM_YHZH, yhzh1 );
	}

	/* 用户密码 */
	public String getYhmm()
	{
		return getValue( ITEM_YHMM );
	}

	public void setYhmm( String yhmm1 )
	{
		setValue( ITEM_YHMM, yhmm1 );
	}

	/* 用户姓名 */
	public String getYhxm()
	{
		return getValue( ITEM_YHXM );
	}

	public void setYhxm( String yhxm1 )
	{
		setValue( ITEM_YHXM, yhxm1 );
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

	/* 身份证号 */
	public String getSfz()
	{
		return getValue( ITEM_SFZ );
	}

	public void setSfz( String sfz1 )
	{
		setValue( ITEM_SFZ, sfz1 );
	}

	/* 职位 */
	public String getZw()
	{
		return getValue( ITEM_ZW );
	}

	public void setZw( String zw1 )
	{
		setValue( ITEM_ZW, zw1 );
	}

	/* 主要职责 */
	public String getZyzz()
	{
		return getValue( ITEM_ZYZZ );
	}

	public void setZyzz( String zyzz1 )
	{
		setValue( ITEM_ZYZZ, zyzz1 );
	}

	/* 工作电话 */
	public String getGzdh()
	{
		return getValue( ITEM_GZDH );
	}

	public void setGzdh( String gzdh1 )
	{
		setValue( ITEM_GZDH, gzdh1 );
	}

	/* 联系电话 */
	public String getLxdh()
	{
		return getValue( ITEM_LXDH );
	}

	public void setLxdh( String lxdh1 )
	{
		setValue( ITEM_LXDH, lxdh1 );
	}

	/* 其他联系方式 */
	public String getQtlxfs()
	{
		return getValue( ITEM_QTLXFS );
	}

	public void setQtlxfs( String qtlxfs1 )
	{
		setValue( ITEM_QTLXFS, qtlxfs1 );
	}

	/* 电子邮件 */
	public String getDzyx()
	{
		return getValue( ITEM_DZYX );
	}

	public void setDzyx( String dzyx1 )
	{
		setValue( ITEM_DZYX, dzyx1 );
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

	/* 兼职机构ID */
	public String getJzjgid()
	{
		return getValue( ITEM_JZJGID );
	}

	public void setJzjgid( String jzjgid1 )
	{
		setValue( ITEM_JZJGID, jzjgid1 );
	}

	/* 角色ID列表 */
	public String getRoleids()
	{
		return getValue( ITEM_ROLEIDS );
	}

	public void setRoleids( String roleids1 )
	{
		setValue( ITEM_ROLEIDS, roleids1 );
	}

	/* 主角色 */
	public String getMainrole()
	{
		return getValue( ITEM_MAINROLE );
	}

	public void setMainrole( String mainrole1 )
	{
		setValue( ITEM_MAINROLE, mainrole1 );
	}

	/* 用户编号 */
	public String getYhbh()
	{
		return getValue( ITEM_YHBH );
	}

	public void setYhbh( String yhbh1 )
	{
		setValue( ITEM_YHBH, yhbh1 );
	}

	/* 所属机构名称 */
	public String getJgname()
	{
		return getValue( ITEM_JGNAME );
	}

	public void setJgname( String jgname1 )
	{
		setValue( ITEM_JGNAME, jgname1 );
	}

	/* 兼职机构名称 */
	public String getJzjgname()
	{
		return getValue( ITEM_JZJGNAME );
	}

	public void setJzjgname( String jzjgname1 )
	{
		setValue( ITEM_JZJGNAME, jzjgname1 );
	}

	/* 所属角色名称 */
	public String getRolenames()
	{
		return getValue( ITEM_ROLENAMES );
	}

	public void setRolenames( String rolenames1 )
	{
		setValue( ITEM_ROLENAMES, rolenames1 );
	}

	/* 主角色名称 */
	public String getMainrolename()
	{
		return getValue( ITEM_MAINROLENAME );
	}

	public void setMainrolename( String mainrolename1 )
	{
		setValue( ITEM_MAINROLENAME, mainrolename1 );
	}

	/* 用户类型 */
	public String getRoletype()
	{
		return getValue( ITEM_ROLETYPE );
	}

	public void setRoletype( String roletype1 )
	{
		setValue( ITEM_ROLETYPE, roletype1 );
	}

}

