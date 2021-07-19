package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_column]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardColumn extends VoBase
{
	private static final long serialVersionUID = 201205031749450006L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_STANDARD_COLUMN_ID = "sys_rd_standard_column_id" ;	/* 指标项ID */
	public static final String ITEM_SYS_RD_STANDARD_TABLE_ID = "sys_rd_standard_table_id" ;	/* 信息实体ID */
	public static final String ITEM_DATA_ELEMENT_IDENTIFIER = "data_element_identifier" ;	/* 数据元标识符 */
	public static final String ITEM_CN_NAME = "cn_name" ;			/* 指标项名称 */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* 字段名称 */
	public static final String ITEM_COLUMN_TYPE = "column_type" ;	/* 数据类型 */
	public static final String ITEM_COLUMN_FORMAT = "column_format" ;	/* 格式 */
	public static final String ITEM_CODE_IDENTIFIER = "code_identifier" ;	/* 代码标识符 */
	public static final String ITEM_MEMO = "memo" ;					/* 备注 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardColumn()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardColumn(DataBus value)
	{
		super(value);
	}
	
	/* 指标项ID : String */
	public String getSys_rd_standard_column_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_COLUMN_ID );
	}

	public void setSys_rd_standard_column_id( String sys_rd_standard_column_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_COLUMN_ID, sys_rd_standard_column_id1 );
	}

	/* 信息实体ID : String */
	public String getSys_rd_standard_table_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_TABLE_ID );
	}

	public void setSys_rd_standard_table_id( String sys_rd_standard_table_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_TABLE_ID, sys_rd_standard_table_id1 );
	}

	/* 数据元标识符 : String */
	public String getData_element_identifier()
	{
		return getValue( ITEM_DATA_ELEMENT_IDENTIFIER );
	}

	public void setData_element_identifier( String data_element_identifier1 )
	{
		setValue( ITEM_DATA_ELEMENT_IDENTIFIER, data_element_identifier1 );
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

	/* 数据类型 : String */
	public String getColumn_type()
	{
		return getValue( ITEM_COLUMN_TYPE );
	}

	public void setColumn_type( String column_type1 )
	{
		setValue( ITEM_COLUMN_TYPE, column_type1 );
	}

	/* 格式 : String */
	public String getColumn_format()
	{
		return getValue( ITEM_COLUMN_FORMAT );
	}

	public void setColumn_format( String column_format1 )
	{
		setValue( ITEM_COLUMN_FORMAT, column_format1 );
	}

	/* 代码标识符 : String */
	public String getCode_identifier()
	{
		return getValue( ITEM_CODE_IDENTIFIER );
	}

	public void setCode_identifier( String code_identifier1 )
	{
		setValue( ITEM_CODE_IDENTIFIER, code_identifier1 );
	}

	/* 备注 : String */
	public String getMemo()
	{
		return getValue( ITEM_MEMO );
	}

	public void setMemo( String memo1 )
	{
		setValue( ITEM_MEMO, memo1 );
	}

}

