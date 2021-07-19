package com.gwssi.collect.webservice.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[collect_webservice_patameter]的数据对象类
 * @author Administrator
 *
 */
public class VoCollectWebservicePatameterPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201304101416380004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_WEBSERVICE_PATAMETER_ID = "webservice_patameter_id" ;	/* 参数ID */
	
	/**
	 * 构造函数
	 */
	public VoCollectWebservicePatameterPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCollectWebservicePatameterPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 参数ID : String */
	public String getWebservice_patameter_id()
	{
		return getValue( ITEM_WEBSERVICE_PATAMETER_ID );
	}

	public void setWebservice_patameter_id( String webservice_patameter_id1 )
	{
		setValue( ITEM_WEBSERVICE_PATAMETER_ID, webservice_patameter_id1 );
	}

}

