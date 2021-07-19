package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_codedata]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardCodedataSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205031401170003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_SYS_RD_STANDAR_CODEINDEX = "sys_rd_standar_codeindex" ;	/* 代码标识符 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardCodedataSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardCodedataSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 代码标识符 : String */
	public String getSys_rd_standar_codeindex()
	{
		return getValue( ITEM_SYS_RD_STANDAR_CODEINDEX );
	}

	public void setSys_rd_standar_codeindex( String sys_rd_standar_codeindex1 )
	{
		setValue( ITEM_SYS_RD_STANDAR_CODEINDEX, sys_rd_standar_codeindex1 );
	}

}

