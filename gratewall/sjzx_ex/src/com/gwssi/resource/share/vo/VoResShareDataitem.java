package com.gwssi.resource.share.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_share_dataitem]的数据对象类
 * @author Administrator
 *
 */
public class VoResShareDataitem extends VoBase
{
	private static final long serialVersionUID = 201303191809040006L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SHARE_DATAITEM_ID = "share_dataitem_id" ;	/* 共享数据项ID */
	public static final String ITEM_SHARE_TABLE_ID = "share_table_id" ;	/* 共享表ID */
	public static final String ITEM_DATAITEM_NAME_EN = "dataitem_name_en" ;	/* 数据项名称 */
	public static final String ITEM_DATAITEM_NAME_CN = "dataitem_name_cn" ;	/* 数据项中文名称 */
	public static final String ITEM_DATAITEM_TYPE = "dataitem_type" ;	/* 数据项类型 */
	public static final String ITEM_DATAITEM_LONG = "dataitem_long" ;	/* 数据项长度 */
	public static final String ITEM_CODE_TABLE_NAME = "code_table_name" ;	/* 系统代码名 */
	public static final String ITEM_CODE_TABLE = "code_table" ;		/* 对应代码集 */
	public static final String ITEM_IS_KEY = "is_key" ;				/* 是否主键 */
	public static final String ITEM_DATAITEM_DESC = "dataitem_desc" ;	/* 描述 */
	public static final String ITEM_SHOW_ORDER = "show_order" ;		/* 显示顺序 */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* 有效标记 */
	
	/**
	 * 构造函数
	 */
	public VoResShareDataitem()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResShareDataitem(DataBus value)
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

	/* 共享表ID : String */
	public String getShare_table_id()
	{
		return getValue( ITEM_SHARE_TABLE_ID );
	}

	public void setShare_table_id( String share_table_id1 )
	{
		setValue( ITEM_SHARE_TABLE_ID, share_table_id1 );
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

	/* 系统代码名 : String */
	public String getCode_table_name()
	{
		return getValue( ITEM_CODE_TABLE_NAME );
	}

	public void setCode_table_name( String code_table_name1 )
	{
		setValue( ITEM_CODE_TABLE_NAME, code_table_name1 );
	}

	/* 对应代码集 : String */
	public String getCode_table()
	{
		return getValue( ITEM_CODE_TABLE );
	}

	public void setCode_table( String code_table1 )
	{
		setValue( ITEM_CODE_TABLE, code_table1 );
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

	/* 描述 : String */
	public String getDataitem_desc()
	{
		return getValue( ITEM_DATAITEM_DESC );
	}

	public void setDataitem_desc( String dataitem_desc1 )
	{
		setValue( ITEM_DATAITEM_DESC, dataitem_desc1 );
	}

	/* 显示顺序 : String */
	public String getShow_order()
	{
		return getValue( ITEM_SHOW_ORDER );
	}

	public void setShow_order( String show_order1 )
	{
		setValue( ITEM_SHOW_ORDER, show_order1 );
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

}

