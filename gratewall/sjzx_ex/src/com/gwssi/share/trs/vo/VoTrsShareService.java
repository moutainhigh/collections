package com.gwssi.share.trs.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[trs_share_service]的数据对象类
 * @author Administrator
 *
 */
public class VoTrsShareService extends VoBase
{
	private static final long serialVersionUID = 201308051642360002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_TRS_SERVICE_ID = "trs_service_id" ;	/* 服务ID */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* 所属服务对象ID */
	public static final String ITEM_TRS_SERVICE_NAME = "trs_service_name" ;	/* 服务名称 */
	public static final String ITEM_TRS_SERVICE_NO = "trs_service_no" ;	/* 服务编号 */
	public static final String ITEM_TRS_DATA_BASE = "trs_data_base" ;	/* 服务库 */
	public static final String ITEM_TRS_COLUMN = "trs_column" ;		/* 展示字段 */
	public static final String ITEM_SERVICE_STATE = "service_state" ;	/* 是否启用 */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* 是否有效 */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* 创建人ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* 创建时间 */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* 最后修改人ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* 最后修改时间 */
	public static final String ITEM_SERVICE_DESCRIPTION = "service_description" ;	/* 服务说明 */
	public static final String ITEM_TRS_SEARCH_COLUMN = "trs_search_column" ;		/* 检索字段 */
	public static final String ITEM_USE_TEMPLATE = "use_template" ;		/* 是否使用模板 */
	public static final String ITEM_TRS_TEMPLATE = "trs_template" ;		/* 模板文件 */
	public static final String ITEM_TRS_TEMPLATE_EX = "trs_template_ex" ;		/* 超长模板文件扩展 */
	
	/**
	 * 构造函数
	 */
	public VoTrsShareService()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoTrsShareService(DataBus value)
	{
		super(value);
	}
	
	/* 服务ID : String */
	public String getTrs_service_id()
	{
		return getValue( ITEM_TRS_SERVICE_ID );
	}

	public void setTrs_service_id( String trs_service_id1 )
	{
		setValue( ITEM_TRS_SERVICE_ID, trs_service_id1 );
	}

	/* 所属服务对象ID : String */
	public String getService_targets_id()
	{
		return getValue( ITEM_SERVICE_TARGETS_ID );
	}

	public void setService_targets_id( String service_targets_id1 )
	{
		setValue( ITEM_SERVICE_TARGETS_ID, service_targets_id1 );
	}

	/* 服务名称 : String */
	public String getTrs_service_name()
	{
		return getValue( ITEM_TRS_SERVICE_NAME );
	}

	public void setTrs_service_name( String trs_service_name1 )
	{
		setValue( ITEM_TRS_SERVICE_NAME, trs_service_name1 );
	}

	/* 服务编号 : String */
	public String getTrs_service_no()
	{
		return getValue( ITEM_TRS_SERVICE_NO );
	}

	public void setTrs_service_no( String trs_service_no1 )
	{
		setValue( ITEM_TRS_SERVICE_NO, trs_service_no1 );
	}

	/* 服务库 : String */
	public String getTrs_data_base()
	{
		return getValue( ITEM_TRS_DATA_BASE );
	}

	public void setTrs_data_base( String trs_data_base1 )
	{
		setValue( ITEM_TRS_DATA_BASE, trs_data_base1 );
	}

	/* 展示字段 : String */
	public String getTrs_column()
	{
		return getValue( ITEM_TRS_COLUMN );
	}

	public void setTrs_column( String trs_column1 )
	{
		setValue( ITEM_TRS_COLUMN, trs_column1 );
	}

	/* 是否启用 : String */
	public String getService_state()
	{
		return getValue( ITEM_SERVICE_STATE );
	}

	public void setService_state( String service_state1 )
	{
		setValue( ITEM_SERVICE_STATE, service_state1 );
	}

	/* 是否有效 : String */
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

	/* 服务说明 : String */
	public String getService_description()
	{
		return getValue( ITEM_SERVICE_DESCRIPTION );
	}

	public void setService_description( String service_description1 )
	{
		setValue( ITEM_SERVICE_DESCRIPTION, service_description1 );
	}
	
	/* 检索字段 : String */
	public String getTrs_search_column()
	{
		return getValue( ITEM_TRS_SEARCH_COLUMN );
	}

	public void setTrs_search_column( String trs_search_column1 )
	{
		setValue( ITEM_TRS_SEARCH_COLUMN, trs_search_column1 );
	}

	/* 是否使用模板 : String */
	public String getUse_template()
	{
		return getValue( ITEM_USE_TEMPLATE );
	}

	public void setUse_template( String use_template )
	{
		setValue( ITEM_USE_TEMPLATE, use_template );
	}

	
	/* 模板文件 : String */
	public String getTrs_template()
	{
		return getValue( ITEM_TRS_TEMPLATE );
	}

	public void setTrs_template( String trs_template )
	{
		setValue( ITEM_TRS_TEMPLATE, trs_template );
	}
	
	
	/* 模板文件扩展 : String */
	public String getTrs_template_ex()
	{
		return getValue( ITEM_TRS_TEMPLATE_EX );
	}

	public void setTrs_template_ex( String trs_template_ex )
	{
		setValue( ITEM_TRS_TEMPLATE_EX, trs_template_ex );
	}
}

