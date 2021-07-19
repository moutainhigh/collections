package com.gwssi.sysmgr.priv.datapriv.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[dataaccdisp]的数据对象类
 * @author Administrator
 *
 */
public class VoDataaccdisp extends VoBase
{
	private static final long serialVersionUID = 200709101705430022L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_OBJECTID = "objectid" ;			/* 对象内码 */
	public static final String ITEM_DATAACCGRPID = "dataaccgrpid" ;	/* 数据权限分组内码 */
	public static final String ITEM_DATAACCDISPOBJ = "dataaccdispobj" ;	/* 分配对象 */
	
	/**
	 * 构造函数
	 */
	public VoDataaccdisp()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoDataaccdisp(DataBus value)
	{
		super(value);
	}
	
	/* 对象内码 : String */
	public String getObjectid()
	{
		return getValue( ITEM_OBJECTID );
	}

	public void setObjectid( String objectid1 )
	{
		setValue( ITEM_OBJECTID, objectid1 );
	}

	/* 数据权限分组内码 : String */
	public String getDataaccgrpid()
	{
		return getValue( ITEM_DATAACCGRPID );
	}

	public void setDataaccgrpid( String dataaccgrpid1 )
	{
		setValue( ITEM_DATAACCGRPID, dataaccgrpid1 );
	}

	/* 分配对象 : String */
	public String getDataaccdispobj()
	{
		return getValue( ITEM_DATAACCDISPOBJ );
	}

	public void setDataaccdispobj( String dataaccdispobj1 )
	{
		setValue( ITEM_DATAACCDISPOBJ, dataaccdispobj1 );
	}

}

