package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_notice_info]的数据对象类
 * @author Administrator
 *
 */
public class VoSysNoticeInfoSelectKey extends VoBase
{
	private static final long serialVersionUID = 200810151621200003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_NOTICE_MATTER = "sys_notice_matter" ;	/* 内容 */
	public static final String ITEM_SYS_NOTICE_DATE = "sys_notice_date" ;	/* 发布时间 */
	public static final String ITEM_SYS_NOTICE_ORG = "sys_notice_org" ;	/* 发布单位 */
	public static final String ITEM_SYS_NOTICE_PROMULGATOR = "sys_notice_promulgator" ;	/* 发布人 */
	public static final String ITEM_SYS_NOTICE_STATE = "sys_notice_state" ;	/* 发布状态 */
	
	/**
	 * 构造函数
	 */
	public VoSysNoticeInfoSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysNoticeInfoSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 内容 : String */
	public String getSys_notice_matter()
	{
		return getValue( ITEM_SYS_NOTICE_MATTER );
	}

	public void setSys_notice_matter( String sys_notice_matter1 )
	{
		setValue( ITEM_SYS_NOTICE_MATTER, sys_notice_matter1 );
	}

	/* 发布时间 : String */
	public String getSys_notice_date()
	{
		return getValue( ITEM_SYS_NOTICE_DATE );
	}

	public void setSys_notice_date( String sys_notice_date1 )
	{
		setValue( ITEM_SYS_NOTICE_DATE, sys_notice_date1 );
	}

	/* 发布单位 : String */
	public String getSys_notice_org()
	{
		return getValue( ITEM_SYS_NOTICE_ORG );
	}

	public void setSys_notice_org( String sys_notice_org1 )
	{
		setValue( ITEM_SYS_NOTICE_ORG, sys_notice_org1 );
	}

	/* 发布人 : String */
	public String getSys_notice_promulgator()
	{
		return getValue( ITEM_SYS_NOTICE_PROMULGATOR );
	}

	public void setSys_notice_promulgator( String sys_notice_promulgator1 )
	{
		setValue( ITEM_SYS_NOTICE_PROMULGATOR, sys_notice_promulgator1 );
	}

	/* 发布状态 : String */
	public String getSys_notice_state()
	{
		return getValue( ITEM_SYS_NOTICE_STATE );
	}

	public void setSys_notice_state( String sys_notice_state1 )
	{
		setValue( ITEM_SYS_NOTICE_STATE, sys_notice_state1 );
	}

}

