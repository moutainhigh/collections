package com.gwssi.dw.metadata.datadict.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_column_semantic]的数据对象类
 * @author Administrator
 *
 */
public class VoSysColumnSemantic extends VoBase
{
	private static final long serialVersionUID = 200804181524160010L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* 业务表编码 */
	public static final String ITEM_COLUMN_NO = "column_no" ;		/* 业务字段编码 */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* 业务字段名 */
	public static final String ITEM_COLUMN_NAME_CN = "column_name_cn" ;	/* 业务字段中文名 */
	public static final String ITEM_COLUMN_ORDER = "column_order" ;	/* 检索顺序 */
	public static final String ITEM_EDIT_TYPE = "edit_type" ;		/* 编辑类型 */
	public static final String ITEM_EDIT_CONTENT = "edit_content" ;	/* 编辑内容 */
	public static final String ITEM_DEMO = "demo" ;					/* 备注 */
	
	/**
	 * 构造函数
	 */
	public VoSysColumnSemantic()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysColumnSemantic(DataBus value)
	{
		super(value);
	}
	
	/* 业务表编码 : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

	/* 业务字段编码 : String */
	public String getColumn_no()
	{
		return getValue( ITEM_COLUMN_NO );
	}

	public void setColumn_no( String column_no1 )
	{
		setValue( ITEM_COLUMN_NO, column_no1 );
	}

	/* 业务字段名 : String */
	public String getColumn_name()
	{
		return getValue( ITEM_COLUMN_NAME );
	}

	public void setColumn_name( String column_name1 )
	{
		setValue( ITEM_COLUMN_NAME, column_name1 );
	}

	/* 业务字段中文名 : String */
	public String getColumn_name_cn()
	{
		return getValue( ITEM_COLUMN_NAME_CN );
	}

	public void setColumn_name_cn( String column_name_cn1 )
	{
		setValue( ITEM_COLUMN_NAME_CN, column_name_cn1 );
	}

	/* 检索顺序 : String */
	public String getColumn_order()
	{
		return getValue( ITEM_COLUMN_ORDER );
	}

	public void setColumn_order( String column_order1 )
	{
		setValue( ITEM_COLUMN_ORDER, column_order1 );
	}

	/* 编辑类型 : String */
	public String getEdit_type()
	{
		return getValue( ITEM_EDIT_TYPE );
	}

	public void setEdit_type( String edit_type1 )
	{
		setValue( ITEM_EDIT_TYPE, edit_type1 );
	}

	/* 编辑内容 : String */
	public String getEdit_content()
	{
		return getValue( ITEM_EDIT_CONTENT );
	}

	public void setEdit_content( String edit_content1 )
	{
		setValue( ITEM_EDIT_CONTENT, edit_content1 );
	}

	/* 备注 : String */
	public String getDemo()
	{
		return getValue( ITEM_DEMO );
	}

	public void setDemo( String demo1 )
	{
		setValue( ITEM_DEMO, demo1 );
	}

}

