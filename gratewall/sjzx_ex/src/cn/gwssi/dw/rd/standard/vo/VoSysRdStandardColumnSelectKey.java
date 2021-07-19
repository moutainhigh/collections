package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_column]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardColumnSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205031749450007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CN_NAME = "cn_name" ;			/* 指标项名称 */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* 字段名称 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardColumnSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardColumnSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 指标项名称 : String */
	public String getCn_name()
	{
		return getValue( ITEM_CN_NAME );
	}

	public void setCn_name( String cn_name1 )
	{
		setValue( ITEM_CN_NAME, cn_name1 );
	}

	/* 字段名称 : String */
	public String getColumn_name()
	{
		return getValue( ITEM_COLUMN_NAME );
	}

	public void setColumn_name( String column_name1 )
	{
		setValue( ITEM_COLUMN_NAME, column_name1 );
	}

}

