package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_codeindex]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardCodeindexSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205031030510007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CODEINDEX_NAME = "codeindex_name" ;	/* 代码集名称 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardCodeindexSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardCodeindexSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 代码集名称 : String */
	public String getCodeindex_name()
	{
		return getValue( ITEM_CODEINDEX_NAME );
	}

	public void setCodeindex_name( String codeindex_name1 )
	{
		setValue( ITEM_CODEINDEX_NAME, codeindex_name1 );
	}

}

