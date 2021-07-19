package com.gwssi.share.interfaces.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[share_interface]的数据对象类
 * @author Administrator
 *
 */
public class VoShareInterface extends VoBase
{
	private static final long serialVersionUID = 201303121022120002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_INTERFACE_ID = "interface_id" ;	/* 接口ID */
	public static final String ITEM_INTERFACE_NAME = "interface_name" ;	/* 接口名称 */
	public static final String ITEM_TABLE_ID = "table_id" ;			/* 表代码串 */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* 表中文名串 */
	public static final String ITEM_SQL = "sql" ;					/* sql语句 */
	public static final String ITEM_INTERFACE_DESCRIPTION = "interface_description" ;	/* 接口描述 */
	public static final String ITEM_INTERFACE_STATE = "interface_state" ;	/* 接口状态 */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* 代码表 */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* 创建人ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* 创建时间 */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* 最后修改人ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* 最后修改时间 */
	
	/**
	 * 构造函数
	 */
	public VoShareInterface()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoShareInterface(DataBus value)
	{
		super(value);
	}
	
	/* 接口ID : String */
	public String getInterface_id()
	{
		return getValue( ITEM_INTERFACE_ID );
	}

	public void setInterface_id( String interface_id1 )
	{
		setValue( ITEM_INTERFACE_ID, interface_id1 );
	}

	/* 接口名称 : String */
	public String getInterface_name()
	{
		return getValue( ITEM_INTERFACE_NAME );
	}

	public void setInterface_name( String interface_name1 )
	{
		setValue( ITEM_INTERFACE_NAME, interface_name1 );
	}

	/* 表代码串 : String */
	public String getTable_id()
	{
		return getValue( ITEM_TABLE_ID );
	}

	public void setTable_id( String table_id1 )
	{
		setValue( ITEM_TABLE_ID, table_id1 );
	}

	/* 表中文名串 : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

	/* sql语句 : String */
	public String getSql()
	{
		return getValue( ITEM_SQL );
	}

	public void setSql( String sql1 )
	{
		setValue( ITEM_SQL, sql1 );
	}

	/* 接口描述 : String */
	public String getInterface_description()
	{
		return getValue( ITEM_INTERFACE_DESCRIPTION );
	}

	public void setInterface_description( String interface_description1 )
	{
		setValue( ITEM_INTERFACE_DESCRIPTION, interface_description1 );
	}

	/* 接口状态 : String */
	public String getInterface_state()
	{
		return getValue( ITEM_INTERFACE_STATE );
	}

	public void setInterface_state( String interface_state1 )
	{
		setValue( ITEM_INTERFACE_STATE, interface_state1 );
	}

	/* 代码表 : String */
	public String getIs_markup()
	{
		return getValue( ITEM_IS_MARKUP );
	}

	public void setIs_markup( String is_markup1 )
	{
		setValue( ITEM_IS_MARKUP, is_markup1 );
	}

	/* 创建人ID : String */
	public String getCreator_id()
	{
		return getValue( ITEM_CREATOR_ID );
	}

	public void setCreator_id( String creator_id1 )
	{
		setValue( ITEM_CREATOR_ID, creator_id1 );
	}

	/* 创建时间 : String */
	public String getCreated_time()
	{
		return getValue( ITEM_CREATED_TIME );
	}

	public void setCreated_time( String created_time1 )
	{
		setValue( ITEM_CREATED_TIME, created_time1 );
	}

	/* 最后修改人ID : String */
	public String getLast_modify_id()
	{
		return getValue( ITEM_LAST_MODIFY_ID );
	}

	public void setLast_modify_id( String last_modify_id1 )
	{
		setValue( ITEM_LAST_MODIFY_ID, last_modify_id1 );
	}

	/* 最后修改时间 : String */
	public String getLast_modify_time()
	{
		return getValue( ITEM_LAST_MODIFY_TIME );
	}

	public void setLast_modify_time( String last_modify_time1 )
	{
		setValue( ITEM_LAST_MODIFY_TIME, last_modify_time1 );
	}

}

