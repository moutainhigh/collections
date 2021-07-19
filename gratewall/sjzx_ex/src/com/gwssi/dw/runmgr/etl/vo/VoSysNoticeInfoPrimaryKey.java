package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_notice_info]的数据对象类
 * @author Administrator
 *
 */
public class VoSysNoticeInfoPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200810151621200004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_NOTICE_ID = "sys_notice_id" ;	/* 通知通告ID */
	
	/**
	 * 构造函数
	 */
	public VoSysNoticeInfoPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysNoticeInfoPrimaryKey(DataBus value)
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

}

