package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_site_addit_info]的数据对象类
 * @author Administrator
 *
 */
public class VoEbSiteAdditInfoPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201209241538470004L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* 企业主键ID */
	
	/**
	 * 构造函数
	 */
	public VoEbSiteAdditInfoPrimaryKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbSiteAdditInfoPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* 企业主键ID : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

}

