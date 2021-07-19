package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_notice_info]的数据对象类
 * @author Administrator
 *
 */
public class VoSysNoticeInfo extends VoBase
{
	private static final long serialVersionUID = 200810151621200002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_NOTICE_ID = "sys_notice_id" ;	/* 通知通告ID */
	public static final String ITEM_SYS_NOTICE_TITLE = "sys_notice_title" ;	/* 标题 */
	public static final String ITEM_SYS_NOTICE_MATTER = "sys_notice_matter" ;	/* 内容 */
	public static final String ITEM_SYS_NOTICE_PROMULGATOR = "sys_notice_promulgator" ;	/* 发布人 */
	public static final String ITEM_SYS_NOTICE_ORG = "sys_notice_org" ;	/* 发布单位 */
	public static final String ITEM_SYS_NOTICE_DATE = "sys_notice_date" ;	/* 发布时间 */
	public static final String ITEM_SYS_NOTICE_STATE = "sys_notice_state" ;	/* 发布状态 */
	public static final String ITEM_SYS_NOTICE_FILEPATH = "sys_notice_filepath" ;	/* 附件 */
	
	/**
	 * 构造函数
	 */
	public VoSysNoticeInfo()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysNoticeInfo(DataBus value)
	{
		super(value);
	}
	
	/* 通知通告ID : String */
	public String getSys_notice_id()
	{
		return getValue( ITEM_SYS_NOTICE_ID );
	}

	public void setSys_notice_id( String sys_notice_id1 )
	{
		setValue( ITEM_SYS_NOTICE_ID, sys_notice_id1 );
	}

	/* 标题 : String */
	public String getSys_notice_title()
	{
		return getValue( ITEM_SYS_NOTICE_TITLE );
	}

	public void setSys_notice_title( String sys_notice_title1 )
	{
		setValue( ITEM_SYS_NOTICE_TITLE, sys_notice_title1 );
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

	/* 发布人 : String */
	public String getSys_notice_promulgator()
	{
		return getValue( ITEM_SYS_NOTICE_PROMULGATOR );
	}

	public void setSys_notice_promulgator( String sys_notice_promulgator1 )
	{
		setValue( ITEM_SYS_NOTICE_PROMULGATOR, sys_notice_promulgator1 );
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

	/* 发布时间 : String */
	public String getSys_notice_date()
	{
		return getValue( ITEM_SYS_NOTICE_DATE );
	}

	public void setSys_notice_date( String sys_notice_date1 )
	{
		setValue( ITEM_SYS_NOTICE_DATE, sys_notice_date1 );
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

	/* 附件 : String */
	public String getSys_notice_filepath()
	{
		return getValue( ITEM_SYS_NOTICE_FILEPATH );
	}

	public void setSys_notice_filepath( String sys_notice_filepath1 )
	{
		setValue( ITEM_SYS_NOTICE_FILEPATH, sys_notice_filepath1 );
	}

}

