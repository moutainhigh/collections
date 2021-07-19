package com.gwssi.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[common_standard]的数据对象类
 * @author Administrator
 *
 */
public class VoCommonStandardSelectKey extends VoBase
{
	private static final long serialVersionUID = 201304121530000003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_STANDARD_NAME = "standard_name" ;	/* 标准名称 */
	public static final String ITEM_SPECIFICATE_NO = "specificate_no" ;	/* 类型号 */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* 创建时间 */
	
	/**
	 * 构造函数
	 */
	public VoCommonStandardSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoCommonStandardSelectKey(DataBus value)
	{
		super(value);
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

	/* 类型号 : String */
	public String getSpecificate_no()
	{
		return getValue( ITEM_SPECIFICATE_NO );
	}

	public void setSpecificate_no( String specificate_no1 )
	{
		setValue( ITEM_SPECIFICATE_NO, specificate_no1 );
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

}

