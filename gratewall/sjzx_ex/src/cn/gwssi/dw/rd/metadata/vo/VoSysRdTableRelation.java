package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoSysRdTableRelation extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_TABLE_RELATION_ID = "sys_rd_table_relation_id";			/* 关联ID */
	public static final String ITEM_SYS_RD_TABLE_ID = "sys_rd_table_id";			/* 主表ID */
	public static final String ITEM_TABLE_CODE = "table_code";			/* 主表代码 */
	public static final String ITEM_TABLE_NAME = "table_name";			/* 主表中文名称 */
	public static final String ITEM_TABLE_FK = "table_fk";			/* 主表字段 */
	public static final String ITEM_TABLE_FK_NAME = "table_fk_name";			/* 主表字段名称 */
	public static final String ITEM_RELATION_TABLE_CODE = "relation_table_code";			/* 关联表代码 */
	public static final String ITEM_RELATION_TABLE_NAME = "relation_table_name";			/* 关联表中文名称 */
	public static final String ITEM_RELATION_TABLE_FK = "relation_table_fk";			/* 关联表字段 */
	public static final String ITEM_RALATION_TABLE_FK_NAME = "ralation_table_fk_name";			/* 关联表字段中文名称 */
	public static final String ITEM_SYS_RD_SYSTEM_ID = "sys_rd_system_id";			/* 主表所属主题ID */
	public static final String ITEM_SYS_RD_DATA_SOURCE_ID = "sys_rd_data_source_id";			/* 主表所属数据源ID */
	public static final String ITEM_REF_SYS_RD_SYSTEM_ID = "ref_sys_rd_system_id";			/* 关联表所属主题ID */
	public static final String ITEM_REF_SYS_RD_DATA_SOURCE_ID = "ref_sys_rd_data_source_id";			/* 关联表所属数据源ID */
	public static final String ITEM_TABLE_RELATION_TYPE = "table_relation_type";			/* 关联关系类型 */
	public static final String ITEM_REMARKS = "remarks";			/* 注释 */
	public static final String ITEM_TIMESTAMP = "timestamp";			/* 时间戳 */

	public VoSysRdTableRelation(DataBus value)
	{
		super(value);
	}

	public VoSysRdTableRelation()
	{
		super();
	}

	/* 关联ID */
	public String getSys_rd_table_relation_id()
	{
		return getValue( ITEM_SYS_RD_TABLE_RELATION_ID );
	}

	public void setSys_rd_table_relation_id( String sys_rd_table_relation_id1 )
	{
		setValue( ITEM_SYS_RD_TABLE_RELATION_ID, sys_rd_table_relation_id1 );
	}

	/* 主表ID */
	public String getSys_rd_table_id()
	{
		return getValue( ITEM_SYS_RD_TABLE_ID );
	}

	public void setSys_rd_table_id( String sys_rd_table_id1 )
	{
		setValue( ITEM_SYS_RD_TABLE_ID, sys_rd_table_id1 );
	}

	/* 主表代码 */
	public String getTable_code()
	{
		return getValue( ITEM_TABLE_CODE );
	}

	public void setTable_code( String table_code1 )
	{
		setValue( ITEM_TABLE_CODE, table_code1 );
	}

	/* 主表中文名称 */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* 主表字段 */
	public String getTable_fk()
	{
		return getValue( ITEM_TABLE_FK );
	}

	public void setTable_fk( String table_fk1 )
	{
		setValue( ITEM_TABLE_FK, table_fk1 );
	}

	/* 主表字段名称 */
	public String getTable_fk_name()
	{
		return getValue( ITEM_TABLE_FK_NAME );
	}

	public void setTable_fk_name( String table_fk_name1 )
	{
		setValue( ITEM_TABLE_FK_NAME, table_fk_name1 );
	}

	/* 关联表代码 */
	public String getRelation_table_code()
	{
		return getValue( ITEM_RELATION_TABLE_CODE );
	}

	public void setRelation_table_code( String relation_table_code1 )
	{
		setValue( ITEM_RELATION_TABLE_CODE, relation_table_code1 );
	}

	/* 关联表中文名称 */
	public String getRelation_table_name()
	{
		return getValue( ITEM_RELATION_TABLE_NAME );
	}

	public void setRelation_table_name( String relation_table_name1 )
	{
		setValue( ITEM_RELATION_TABLE_NAME, relation_table_name1 );
	}

	/* 关联表字段 */
	public String getRelation_table_fk()
	{
		return getValue( ITEM_RELATION_TABLE_FK );
	}

	public void setRelation_table_fk( String relation_table_fk1 )
	{
		setValue( ITEM_RELATION_TABLE_FK, relation_table_fk1 );
	}

	/* 关联表字段中文名称 */
	public String getRalation_table_fk_name()
	{
		return getValue( ITEM_RALATION_TABLE_FK_NAME );
	}

	public void setRalation_table_fk_name( String ralation_table_fk_name1 )
	{
		setValue( ITEM_RALATION_TABLE_FK_NAME, ralation_table_fk_name1 );
	}

	/* 主表所属主题ID */
	public String getSys_rd_system_id()
	{
		return getValue( ITEM_SYS_RD_SYSTEM_ID );
	}

	public void setSys_rd_system_id( String sys_rd_system_id1 )
	{
		setValue( ITEM_SYS_RD_SYSTEM_ID, sys_rd_system_id1 );
	}

	/* 主表所属数据源ID */
	public String getSys_rd_data_source_id()
	{
		return getValue( ITEM_SYS_RD_DATA_SOURCE_ID );
	}

	public void setSys_rd_data_source_id( String sys_rd_data_source_id1 )
	{
		setValue( ITEM_SYS_RD_DATA_SOURCE_ID, sys_rd_data_source_id1 );
	}

	/* 关联表所属主题ID */
	public String getRef_sys_rd_system_id()
	{
		return getValue( ITEM_REF_SYS_RD_SYSTEM_ID );
	}

	public void setRef_sys_rd_system_id( String ref_sys_rd_system_id1 )
	{
		setValue( ITEM_REF_SYS_RD_SYSTEM_ID, ref_sys_rd_system_id1 );
	}

	/* 关联表所属数据源ID */
	public String getRef_sys_rd_data_source_id()
	{
		return getValue( ITEM_REF_SYS_RD_DATA_SOURCE_ID );
	}

	public void setRef_sys_rd_data_source_id( String ref_sys_rd_data_source_id1 )
	{
		setValue( ITEM_REF_SYS_RD_DATA_SOURCE_ID, ref_sys_rd_data_source_id1 );
	}

	/* 关联关系类型 */
	public String getTable_relation_type()
	{
		return getValue( ITEM_TABLE_RELATION_TYPE );
	}

	public void setTable_relation_type( String table_relation_type1 )
	{
		setValue( ITEM_TABLE_RELATION_TYPE, table_relation_type1 );
	}

	/* 注释 */
	public String getRemarks()
	{
		return getValue( ITEM_REMARKS );
	}

	public void setRemarks( String remarks1 )
	{
		setValue( ITEM_REMARKS, remarks1 );
	}

	/* 时间戳 */
	public String getTimestamp()
	{
		return getValue( ITEM_TIMESTAMP );
	}

	public void setTimestamp( String timestamp1 )
	{
		setValue( ITEM_TIMESTAMP, timestamp1 );
	}

}

