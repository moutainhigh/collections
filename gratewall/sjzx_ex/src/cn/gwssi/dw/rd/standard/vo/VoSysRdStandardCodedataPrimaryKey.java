package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_codedata]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardCodedataPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205031401170004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_ID = "id" ;						/* 代码值ID */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardCodedataPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardCodedataPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 代码值ID : String */
	public String getId()
	{
		return getValue( ITEM_ID );
	}

	public void setId( String id1 )
	{
		setValue( ITEM_ID, id1 );
	}

}

