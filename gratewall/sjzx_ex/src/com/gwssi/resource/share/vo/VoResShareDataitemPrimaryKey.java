package com.gwssi.resource.share.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_share_dataitem]的数据对象类
 * @author Administrator
 *
 */
public class VoResShareDataitemPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201303191809040008L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SHARE_DATAITEM_ID = "share_dataitem_id" ;	/* 共享数据项ID */
	
	/**
	 * 构造函数
	 */
	public VoResShareDataitemPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResShareDataitemPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 共享数据项ID : String */
	public String getShare_dataitem_id()
	{
		return getValue( ITEM_SHARE_DATAITEM_ID );
	}

	public void setShare_dataitem_id( String share_dataitem_id1 )
	{
		setValue( ITEM_SHARE_DATAITEM_ID, share_dataitem_id1 );
	}

}

