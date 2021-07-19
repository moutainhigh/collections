package com.gwssi.resource.collect.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_collect_table]的数据对象类
 * @author Administrator
 *
 */
public class VoResCollectTable extends VoBase
{
	private static final long serialVersionUID = 201303221045510002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_COLLECT_TABLE_ID = "collect_table_id" ;	/* 采集数据表ID */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* 服务对象ID */
	public static final String ITEM_TABLE_NAME_EN = "table_name_en" ;	/* 表名称 */
	public static final String ITEM_TABLE_NAME_CN = "table_name_cn" ;	/* 表中文名称 */
	public static final String ITEM_TABLE_TYPE = "table_type" ;		/* 表类型 */
	public static final String ITEM_TABLE_DESC = "table_desc" ;		/* 表描述 */
	public static final String ITEM_TABLE_STATUS = "table_status" ;	/* 表状态 */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* 有效标记 */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* 创建人ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* 创建时间 */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* 最后修改人ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* 最后修改时间 */
	public static final String ITEM_FJ_FK = "fj_fk" ;				/* 附件id */
	public static final String ITEM_FJMC = "fjmc" ;					/* 附件名称 */
	public static final String ITEM_DELNAMES = "delNAMEs";			/* delNAMEs */
	public static final String ITEM_DELIDS = "delIDs";			/* delIDs */
	public static final String ITEM_CJ_LY = "cj_ly";			/* cj_ly */
	public static final String ITEM_IF_CREAT = "if_creat";			/* if_creat */
	
	/**
	 * 构造函数
	 */
	public VoResCollectTable()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResCollectTable(DataBus value)
	{
		super(value);
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

	/* 服务对象ID : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* 表名称 : String */
	public String getTable_name_en()
	{
		return getValue( ITEM_TABLE_NAME_EN );
	}

	public void setTable_name_en( String table_name_en1 )
	{
		setValue( ITEM_TABLE_NAME_EN, table_name_en1 );
	}

	/* 表中文名称 : String */
	public String getTable_name_cn()
	{
		return getValue( ITEM_TABLE_NAME_CN );
	}

	public void setTable_name_cn( String table_name_cn1 )
	{
		setValue( ITEM_TABLE_NAME_CN, table_name_cn1 );
	}

	/* 表类型 : String */
	public String getTable_type()
	{
		return getValue( ITEM_TABLE_TYPE );
	}

	public void setTable_type( String table_type1 )
	{
		setValue( ITEM_TABLE_TYPE, table_type1 );
	}

	/* 表描述 : String */
	public String getTable_desc()
	{
		return getValue( ITEM_TABLE_DESC );
	}

	public void setTable_desc( String table_desc1 )
	{
		setValue( ITEM_TABLE_DESC, table_desc1 );
	}

	/* 表状态 : String */
	public String getTable_status()
	{
		return getValue( ITEM_TABLE_STATUS );
	}

	public void setTable_status( String table_status1 )
	{
		setValue( ITEM_TABLE_STATUS, table_status1 );
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
	
	/* 采集来源 : String */
	public String getCj_ly()
	{
		return getValue( ITEM_CJ_LY );
	}

	public void setCj_ly(String cj_ly)
	{
		setValue( ITEM_CJ_LY, cj_ly );
	}
	
	/* 是否生成采集表 : String */
	public String getIf_creat()
	{
		return getValue( ITEM_IF_CREAT );
	}

	public void setIf_creat(String if_creat)
	{
		setValue( ITEM_IF_CREAT, if_creat);
	}

}

