package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[view_sys_count_log]的数据对象类
 * @author Administrator
 *
 */
public class VoViewSysCountLogPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201208031540190004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TABLE_CLASS_ID = "table_class_id" ;	/* 分类主键id */
	
	/**
	 * 构造函数
	 */
	public VoViewSysCountLogPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoViewSysCountLogPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 分类主键id : String */
	public String getTable_class_id()
	{
		return getValue( ITEM_TABLE_CLASS_ID );
	}

	public void setTable_class_id( String table_class_id1 )
	{
		setValue( ITEM_TABLE_CLASS_ID, table_class_id1 );
	}

}

