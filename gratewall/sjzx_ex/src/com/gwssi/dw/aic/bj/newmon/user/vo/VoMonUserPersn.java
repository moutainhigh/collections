package com.gwssi.dw.aic.bj.newmon.user.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoMonUserPersn extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_MON_USER_PERSN_ID = "mon_user_persn_id";			/* 用户人员表ID */
	public static final String ITEM_USER_PERSN_ID = "user_persn_id";			/* ID */
	public static final String ITEM_DEP_NO = "dep_no";			/* 部门代码(dm) */
	public static final String ITEM_USER_NAME = "user_name";			/* 用户名称 */
	public static final String ITEM_LOGIN_NAME = "login_name";			/* 用户登录名 */
	public static final String ITEM_PASS_WORD = "pass_word";			/* 口令 */
	public static final String ITEM_SEX = "sex";			/* 性别 */
	public static final String ITEM_BIRTH_DAY = "birth_day";			/* 出生日期 */
	public static final String ITEM_PROF_TITLE = "prof_title";			/* 职务 */
	public static final String ITEM_SHOW_ODER = "show_oder";			/* 显示顺序 */
	public static final String ITEM_IS_LINK_MAN = "is_link_man";			/* 是否联系人 */
	public static final String ITEM_UNIT_LEVEL = "unit_level";			/* 单位层级 */
	public static final String ITEM_ADM_ORG = "adm_org";			/* 管辖分局 */
	public static final String ITEM_USER_TYPE = "user_type";			/* 用户类型 */
	public static final String ITEM_POLI_SCAPE = "poli_scape";			/* 政治面貌 */
	public static final String ITEM_START_DATE = "start_date";			/* 参加工作时间 */

	public VoMonUserPersn(DataBus value)
	{
		super(value);
	}

	public VoMonUserPersn()
	{
		super();
	}

	/* 用户人员表ID */
	public String getMon_user_persn_id()
	{
		return getValue( ITEM_MON_USER_PERSN_ID );
	}

	public void setMon_user_persn_id( String mon_user_persn_id1 )
	{
		setValue( ITEM_MON_USER_PERSN_ID, mon_user_persn_id1 );
	}

	/* ID */
	public String getUser_persn_id()
	{
		return getValue( ITEM_USER_PERSN_ID );
	}

	public void setUser_persn_id( String user_persn_id1 )
	{
		setValue( ITEM_USER_PERSN_ID, user_persn_id1 );
	}

	/* 部门代码(dm) */
	public String getDep_no()
	{
		return getValue( ITEM_DEP_NO );
	}

	public void setDep_no( String dep_no1 )
	{
		setValue( ITEM_DEP_NO, dep_no1 );
	}

	/* 用户名称 */
	public String getUser_name()
	{
		return getValue( ITEM_USER_NAME );
	}

	public void setUser_name( String user_name1 )
	{
		setValue( ITEM_USER_NAME, user_name1 );
	}

	/* 用户登录名 */
	public String getLogin_name()
	{
		return getValue( ITEM_LOGIN_NAME );
	}

	public void setLogin_name( String login_name1 )
	{
		setValue( ITEM_LOGIN_NAME, login_name1 );
	}

	/* 口令 */
	public String getPass_word()
	{
		return getValue( ITEM_PASS_WORD );
	}

	public void setPass_word( String pass_word1 )
	{
		setValue( ITEM_PASS_WORD, pass_word1 );
	}

	/* 性别 */
	public String getSex()
	{
		return getValue( ITEM_SEX );
	}

	public void setSex( String sex1 )
	{
		setValue( ITEM_SEX, sex1 );
	}

	/* 出生日期 */
	public String getBirth_day()
	{
		return getValue( ITEM_BIRTH_DAY );
	}

	public void setBirth_day( String birth_day1 )
	{
		setValue( ITEM_BIRTH_DAY, birth_day1 );
	}

	/* 职务 */
	public String getProf_title()
	{
		return getValue( ITEM_PROF_TITLE );
	}

	public void setProf_title( String prof_title1 )
	{
		setValue( ITEM_PROF_TITLE, prof_title1 );
	}

	/* 显示顺序 */
	public String getShow_oder()
	{
		return getValue( ITEM_SHOW_ODER );
	}

	public void setShow_oder( String show_oder1 )
	{
		setValue( ITEM_SHOW_ODER, show_oder1 );
	}

	/* 是否联系人 */
	public String getIs_link_man()
	{
		return getValue( ITEM_IS_LINK_MAN );
	}

	public void setIs_link_man( String is_link_man1 )
	{
		setValue( ITEM_IS_LINK_MAN, is_link_man1 );
	}

	/* 单位层级 */
	public String getUnit_level()
	{
		return getValue( ITEM_UNIT_LEVEL );
	}

	public void setUnit_level( String unit_level1 )
	{
		setValue( ITEM_UNIT_LEVEL, unit_level1 );
	}

	/* 管辖分局 */
	public String getAdm_org()
	{
		return getValue( ITEM_ADM_ORG );
	}

	public void setAdm_org( String adm_org1 )
	{
		setValue( ITEM_ADM_ORG, adm_org1 );
	}

	/* 用户类型 */
	public String getUser_type()
	{
		return getValue( ITEM_USER_TYPE );
	}

	public void setUser_type( String user_type1 )
	{
		setValue( ITEM_USER_TYPE, user_type1 );
	}

	/* 政治面貌 */
	public String getPoli_scape()
	{
		return getValue( ITEM_POLI_SCAPE );
	}

	public void setPoli_scape( String poli_scape1 )
	{
		setValue( ITEM_POLI_SCAPE, poli_scape1 );
	}

	/* 参加工作时间 */
	public String getStart_date()
	{
		return getValue( ITEM_START_DATE );
	}

	public void setStart_date( String start_date1 )
	{
		setValue( ITEM_START_DATE, start_date1 );
	}

}

