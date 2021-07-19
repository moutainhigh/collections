package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_ent_web_site_info]的数据对象类
 * @author Administrator
 *
 */
public class VoEbEntWebSiteInfoSelectKey extends VoBase
{
	private static final long serialVersionUID = 201209241434510003L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* 主键chr_id */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* 企业id */
	
	/**
	 * 构造函数
	 */
	public VoEbEntWebSiteInfoSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbEntWebSiteInfoSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 主键chr_id : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

	/* 企业id : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

}

