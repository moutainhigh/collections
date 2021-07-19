package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_table_relation]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdTableRelationPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205260127140004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_TABLE_RELATION_ID = "sys_rd_table_relation_id" ;	/* 关联ID */
	
	/**
	 * 构造函数
	 */
	public VoSysRdTableRelationPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdTableRelationPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 关联ID : String */
	public String getSys_rd_table_relation_id()
	{
		return getValue( ITEM_SYS_RD_TABLE_RELATION_ID );
	}

	public void setSys_rd_table_relation_id( String sys_rd_table_relation_id1 )
	{
		setValue( ITEM_SYS_RD_TABLE_RELATION_ID, sys_rd_table_relation_id1 );
	}

}

