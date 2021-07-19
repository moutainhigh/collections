package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_ent_lic_info]的数据对象类
 * @author Administrator
 *
 */
public class VoEbEntLicInfoSelectKey extends VoBase
{
	private static final long serialVersionUID = 201209241612200011L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_LIC_NAME = "lic_name" ;			/* 许可证名称 */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* 企业ID */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* 主键ID */
	
	/**
	 * 构造函数
	 */
	public VoEbEntLicInfoSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbEntLicInfoSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* 许可证名称 : String */
	public String getLic_name()
	{
		return getValue( ITEM_LIC_NAME );
	}

	public void setLic_name( String lic_name1 )
	{
		setValue( ITEM_LIC_NAME, lic_name1 );
	}

	/* 企业ID : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

	/* 主键ID : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

}

