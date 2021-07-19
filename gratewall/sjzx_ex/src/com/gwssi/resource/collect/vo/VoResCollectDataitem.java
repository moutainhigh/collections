package com.gwssi.resource.collect.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_collect_dataitem]的数据对象类
 * @author Administrator
 *
 */
public class VoResCollectDataitem extends VoBase
{
	private static final long serialVersionUID = 201303221103430006L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_COLLECT_DATAITEM_ID = "collect_dataitem_id" ;	/* 采集数据项ID */
	public static final String ITEM_COLLECT_TABLE_ID = "collect_table_id" ;	/* 采集数据表ID */
	public static final String ITEM_DATAITEM_NAME_EN = "dataitem_name_en" ;	/* 数据项名称 */
	public static final String ITEM_DATAITEM_NAME_CN = "dataitem_name_cn" ;	/* 数据项中文名称 */
	public static final String ITEM_DATAITEM_TYPE = "dataitem_type" ;	/* 数据项类型 */
	public static final String ITEM_DATAITEM_LONG = "dataitem_long" ;	/* 数据项长度 */
	public static final String ITEM_IS_KEY = "is_key" ;				/* 是否主键 */
	public static final String ITEM_IS_CODE_TABLE = "is_code_table" ;	/* 是否代码表 */
	public static final String ITEM_CODE_TABLE = "code_table" ;		/* 对应代码表 */
	public static final String ITEM_DATAITEM_LONG_DESC = "dataitem_long_desc" ;	/* 数据项描述 */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* 有效标记 */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* 创建人ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* 创建时间 */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* 最后修改人ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* 最后修改时间 */
	public static String ITEM_DATAITEM_STATE = "dataitem_state" ;	/* 物理表是否已生成该数据项 */
	
	/**
	 * 构造函数
	 */
	public VoResCollectDataitem()
	{
		super();
	}
	
	/**
	 * @return the itemDataitemState
	 */
	public static String getItemDataitemState()
	{
		return ITEM_DATAITEM_STATE;
	}

	/**
	 * @param itemDataitemState the itemDataitemState to set
	 */
	public static void setItemDataitemState(String itemDataitemState)
	{
		ITEM_DATAITEM_STATE = itemDataitemState;
	}

	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResCollectDataitem(DataBus value)
	{
		super(value);
	}
	
	/* 采集数据项ID : String */
	public String getCollect_dataitem_id()
	{
		return getValue( ITEM_COLLECT_DATAITEM_ID );
	}

	public void setCollect_dataitem_id( String collect_dataitem_id1 )
	{
		setValue( ITEM_COLLECT_DATAITEM_ID, collect_dataitem_id1 );
	}

	/* 采集数据表ID : String */
	public String getCollect_table_id()
	{
		return getValue( ITEM_COLLECT_TABLE_ID );
	}

	public void setCollect_table_id( String collect_table_id1 )
	{
		setValue( ITEM_COLLECT_TABLE_ID, collect_table_id1 );
	}

	/* 数据项名称 : String */
	public String getDataitem_name_en()
	{
		return getValue( ITEM_DATAITEM_NAME_EN );
	}

	public void setDataitem_name_en( String dataitem_name_en1 )
	{
		setValue( ITEM_DATAITEM_NAME_EN, dataitem_name_en1 );
	}

	/* 数据项中文名称 : String */
	public String getDataitem_name_cn()
	{
		return getValue( ITEM_DATAITEM_NAME_CN );
	}

	public void setDataitem_name_cn( String dataitem_name_cn1 )
	{
		setValue( ITEM_DATAITEM_NAME_CN, dataitem_name_cn1 );
	}

	/* 数据项类型 : String */
	public String getDataitem_type()
	{
		return getValue( ITEM_DATAITEM_TYPE );
	}

	public void setDataitem_type( String dataitem_type1 )
	{
		setValue( ITEM_DATAITEM_TYPE, dataitem_type1 );
	}

	/* 数据项长度 : String */
	public String getDataitem_long()
	{
		return getValue( ITEM_DATAITEM_LONG );
	}

	public void setDataitem_long( String dataitem_long1 )
	{
		setValue( ITEM_DATAITEM_LONG, dataitem_long1 );
	}

	/* 是否主键 : String */
	public String getIs_key()
	{
		return getValue( ITEM_IS_KEY );
	}

	public void setIs_key( String is_key1 )
	{
		setValue( ITEM_IS_KEY, is_key1 );
	}

	/* 是否代码表 : String */
	public String getIs_code_table()
	{
		return getValue( ITEM_IS_CODE_TABLE );
	}

	public void setIs_code_table( String is_code_table1 )
	{
		setValue( ITEM_IS_CODE_TABLE, is_code_table1 );
	}

	/* 对应代码表 : String */
	public String getCode_table()
	{
		return getValue( ITEM_CODE_TABLE );
	}

	public void setCode_table( String code_table1 )
	{
		setValue( ITEM_CODE_TABLE, code_table1 );
	}

	/* 数据项描述 : String */
	public String getDataitem_long_desc()
	{
		return getValue( ITEM_DATAITEM_LONG_DESC );
	}

	public void setDataitem_long_desc( String dataitem_long_desc1 )
	{
		setValue( ITEM_DATAITEM_LONG_DESC, dataitem_long_desc1 );
	}

	/* 有效标记 : String */
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

