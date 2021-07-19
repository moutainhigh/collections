package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[view_func_name_log]的数据对象类
 * @author Administrator
 *
 */
public class VoViewFuncNameLog extends VoBase
{
	private static final long serialVersionUID = 201208221639540002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_FIRST_FUNC_NAME = "first_func_name" ;	/* 功能大类 */
	public static final String ITEM_SECOND_FUNC_NAME = "second_func_name" ;	/* 功能小类 */
	
	/**
	 * 构造函数
	 */
	public VoViewFuncNameLog()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoViewFuncNameLog(DataBus value)
	{
		super(value);
	}
	
	/* 功能大类 : String */
	public String getFirst_func_name()
	{
		return getValue( ITEM_FIRST_FUNC_NAME );
	}

	public void setFirst_func_name( String first_func_name1 )
	{
		setValue( ITEM_FIRST_FUNC_NAME, first_func_name1 );
	}

	/* 功能小类 : String */
	public String getSecond_func_name()
	{
		return getValue( ITEM_SECOND_FUNC_NAME );
	}

	public void setSecond_func_name( String second_func_name1 )
	{
		setValue( ITEM_SECOND_FUNC_NAME, second_func_name1 );
	}

}

