package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205020221520007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_STANDARD_NAME = "standard_name" ;	/* 姓名 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 姓名 : String */
	public String getStandard_name()
	{
		return getValue( ITEM_STANDARD_NAME );
	}

	public void setStandard_name( String standard_name1 )
	{
		setValue( ITEM_STANDARD_NAME, standard_name1 );
	}

}

