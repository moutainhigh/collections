package com.gwssi.resource.svrobj.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_service_targets]的数据对象类
 * @author Administrator
 *
 */
public class VoResServiceTargets extends VoBase
{
	private static final long serialVersionUID = 201303131040540002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SERVICE_TARGETS_ID = "service_targets_id" ;	/* 服务对象ID */
	public static final String ITEM_SERVICE_TARGETS_NO = "service_targets_no" ;	/* 服务对象代码 */
	public static final String ITEM_SERVICE_TARGETS_NAME = "service_targets_name" ;	/* 服务对象名称 */
	public static final String ITEM_SERVICE_TARGETS_TYPE = "service_targets_type" ;	/* 服务对象类型 */
	public static final String ITEM_IS_BIND_IP = "is_bind_ip" ;		/* 是否绑定IP */
	public static final String ITEM_IP = "ip" ;						/* IP */
	public static final String ITEM_SERVICE_PASSWORD = "service_password" ;	/* 服务口令 */
	public static final String ITEM_SERVICE_STATUS = "service_status" ;	/* 服务状态 */
	public static final String ITEM_SERVICE_DESC = "service_desc" ;	/* 服务对象描述 */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* 有效标记 */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* 创建人ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* 创建时间 */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* 最后修改人ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* 最后修改时间 */
	public static final String ITEM_IS_FORMAL = "is_formal" ;	/* 是否试运行 */
	/**
	 * 构造函数
	 */
	public VoResServiceTargets()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResServiceTargets(DataBus value)
	{
		super(value);
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

	/* 服务对象代码 : String */
	public String getService_targets_no()
	{
		return getValue( ITEM_SERVICE_TARGETS_NO );
	}

	public void setService_targets_no( String service_targets_no1 )
	{
		setValue( ITEM_SERVICE_TARGETS_NO, service_targets_no1 );
	}

	/* 服务对象名称 : String */
	public String getService_targets_name()
	{
		return getValue( ITEM_SERVICE_TARGETS_NAME );
	}

	public void setService_targets_name( String service_targets_name1 )
	{
		setValue( ITEM_SERVICE_TARGETS_NAME, service_targets_name1 );
	}

	/* 服务对象类型 : String */
	public String getService_targets_type()
	{
		return getValue( ITEM_SERVICE_TARGETS_TYPE );
	}

	public void setService_targets_type( String service_targets_type1 )
	{
		setValue( ITEM_SERVICE_TARGETS_TYPE, service_targets_type1 );
	}

	/* 是否绑定IP : String */
	public String getIs_bind_ip()
	{
		return getValue( ITEM_IS_BIND_IP );
	}

	public void setIs_bind_ip( String is_bind_ip1 )
	{
		setValue( ITEM_IS_BIND_IP, is_bind_ip1 );
	}

	/* IP : String */
	public String getIp()
	{
		return getValue( ITEM_IP );
	}

	public void setIp( String ip1 )
	{
		setValue( ITEM_IP, ip1 );
	}

	/* 服务口令 : String */
	public String getService_password()
	{
		return getValue( ITEM_SERVICE_PASSWORD );
	}

	public void setService_password( String service_password1 )
	{
		setValue( ITEM_SERVICE_PASSWORD, service_password1 );
	}

	/* 服务状态 : String */
	public String getService_status()
	{
		return getValue( ITEM_SERVICE_STATUS );
	}

	public void setService_status( String service_status1 )
	{
		setValue( ITEM_SERVICE_STATUS, service_status1 );
	}

	/* 服务对象描述 : String */
	public String getService_desc()
	{
		return getValue( ITEM_SERVICE_DESC );
	}

	public void setService_desc( String service_desc1 )
	{
		setValue( ITEM_SERVICE_DESC, service_desc1 );
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
	/* 是否试运行 : String */
	public String getIs_formal()
	{
		return getValue( ITEM_IS_FORMAL );
	}

	public void setIs_formal( String is_foraml1 )
	{
		setValue( ITEM_IS_FORMAL, is_foraml1 );
	}
}

