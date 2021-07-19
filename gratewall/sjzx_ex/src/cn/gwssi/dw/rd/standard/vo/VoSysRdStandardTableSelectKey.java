package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_table]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardTableSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205031723560003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_STANDARD_ID = "sys_rd_standard_id" ;	/* 标准ID */
	public static final String ITEM_STANDARD_NAME = "standard_name" ;	/* 标准名称 */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* 实体信息名称 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardTableSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardTableSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 标准ID : String */
	public String getSys_rd_standard_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_ID );
	}

	public void setSys_rd_standard_id( String sys_rd_standard_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_ID, sys_rd_standard_id1 );
	}

	/* 标准名称 : String */
	public String getStandard_name()
	{
		return getValue( ITEM_STANDARD_NAME );
	}

	public void setStandard_name( String standard_name1 )
	{
		setValue( ITEM_STANDARD_NAME, standard_name1 );
	}

	/* 实体信息名称 : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

}

