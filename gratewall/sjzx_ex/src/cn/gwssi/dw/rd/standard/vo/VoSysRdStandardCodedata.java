package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[sys_rd_standard_codedata]的数据对象类
 * @author Administrator
 *
 */
public class VoSysRdStandardCodedata extends VoBase
{
	private static final long serialVersionUID = 201205031401170002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_ID = "id" ;						/* 代码值ID */
	public static final String ITEM_SYS_RD_STANDAR_CODEINDEX = "sys_rd_standar_codeindex" ;	/* 代码标识符 */
	public static final String ITEM_SYS_RD_STANDARD_CODEVALUE = "sys_rd_standard_codevalue" ;	/* 代码值 */
	public static final String ITEM_SYS_RD_STANDARD_CODENAME = "sys_rd_standard_codename" ;	/* 代码内容 */
	public static final String ITEM_DESCRIPTION = "description" ;	/* 说明 */
	
	/**
	 * 构造函数
	 */
	public VoSysRdStandardCodedata()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoSysRdStandardCodedata(DataBus value)
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

	/* 代码标识符 : String */
	public String getSys_rd_standar_codeindex()
	{
		return getValue( ITEM_SYS_RD_STANDAR_CODEINDEX );
	}

	public void setSys_rd_standar_codeindex( String sys_rd_standar_codeindex1 )
	{
		setValue( ITEM_SYS_RD_STANDAR_CODEINDEX, sys_rd_standar_codeindex1 );
	}

	/* 代码值 : String */
	public String getSys_rd_standard_codevalue()
	{
		return getValue( ITEM_SYS_RD_STANDARD_CODEVALUE );
	}

	public void setSys_rd_standard_codevalue( String sys_rd_standard_codevalue1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_CODEVALUE, sys_rd_standard_codevalue1 );
	}

	/* 代码内容 : String */
	public String getSys_rd_standard_codename()
	{
		return getValue( ITEM_SYS_RD_STANDARD_CODENAME );
	}

	public void setSys_rd_standard_codename( String sys_rd_standard_codename1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_CODENAME, sys_rd_standard_codename1 );
	}

	/* 说明 : String */
	public String getDescription()
	{
		return getValue( ITEM_DESCRIPTION );
	}

	public void setDescription( String description1 )
	{
		setValue( ITEM_DESCRIPTION, description1 );
	}

}

