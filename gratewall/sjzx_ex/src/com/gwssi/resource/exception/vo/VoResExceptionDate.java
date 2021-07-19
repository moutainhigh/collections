package com.gwssi.resource.exception.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[res_exception_date]的数据对象类
 * @author Administrator
 *
 */
public class VoResExceptionDate extends VoBase
{
	private static final long serialVersionUID = 201303131312090002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_EXCEPTION_DATE_ID = "exception_date_id" ;	/* 例外日期ID */
	public static final String ITEM_EXCEPTION_DATE_NAME = "exception_date_name" ;	/* 例外名称 */
	public static final String ITEM_EXCEPTION_TYPE = "exception_type" ;	/* 例外类型 */
	public static final String ITEM_EXCEPTION_DESC = "exception_desc" ;	/* 例外描述 */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* 有效标记 */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* 创建人ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* 创建时间 */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* 最后修改人ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* 最后修改时间 */
	public static final String ITEM_EXCEPTION_ID = "exception_id" ;	/* 例外编号 */
	public static final String ITEM_EXCEPTION_DATE = "exception_date" ;	/* 例外日期 */
	
	/**
	 * 构造函数
	 */
	public VoResExceptionDate()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoResExceptionDate(DataBus value)
	{
		super(value);
	}
	
	/* 例外日期ID : String */
	public String getException_date_id()
	{
		return getValue( ITEM_EXCEPTION_DATE_ID );
	}

	public void setException_date_id( String exception_date_id1 )
	{
		setValue( ITEM_EXCEPTION_DATE_ID, exception_date_id1 );
	}

	/* 例外名称 : String */
	public String getException_date_name()
	{
		return getValue( ITEM_EXCEPTION_DATE_NAME );
	}

	public void setException_date_name( String exception_date_name1 )
	{
		setValue( ITEM_EXCEPTION_DATE_NAME, exception_date_name1 );
	}

	/* 例外类型 : String */
	public String getException_type()
	{
		return getValue( ITEM_EXCEPTION_TYPE );
	}

	public void setException_type( String exception_type1 )
	{
		setValue( ITEM_EXCEPTION_TYPE, exception_type1 );
	}

	/* 例外描述 : String */
	public String getException_desc()
	{
		return getValue( ITEM_EXCEPTION_DESC );
	}

	public void setException_desc( String exception_desc1 )
	{
		setValue( ITEM_EXCEPTION_DESC, exception_desc1 );
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

	/* 例外编号 : String */
	public String getException_id()
	{
		return getValue( ITEM_EXCEPTION_ID );
	}

	public void setException_id( String exception_id1 )
	{
		setValue( ITEM_EXCEPTION_ID, exception_id1 );
	}

	/* 例外日期 : String */
	public String getException_date()
	{
		return getValue( ITEM_EXCEPTION_DATE );
	}

	public void setException_date( String exception_date1 )
	{
		setValue( ITEM_EXCEPTION_DATE, exception_date1 );
	}

}

