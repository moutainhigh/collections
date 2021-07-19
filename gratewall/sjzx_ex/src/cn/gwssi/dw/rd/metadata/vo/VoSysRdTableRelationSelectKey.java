package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_table_relation]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdTableRelationSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205260127140003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* 主表中文名称 */
	public static final String ITEM_RELATION_TABLE_NAME = "relation_table_name" ;	/* 关联表中文名称 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdTableRelationSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdTableRelationSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 主表中文名称 : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* 关联表中文名称 : String */
	public String getRelation_table_name()
	{
		return getValue( ITEM_RELATION_TABLE_NAME );
	}

	public void setRelation_table_name( String relation_table_name1 )
	{
		setValue( ITEM_RELATION_TABLE_NAME, relation_table_name1 );
	}

}

