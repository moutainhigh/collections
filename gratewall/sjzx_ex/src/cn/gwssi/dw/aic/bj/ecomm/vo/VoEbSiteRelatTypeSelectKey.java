package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_site_relat_type]的数据对象类
 * @author Administrator
 *
 */
public class VoEbSiteRelatTypeSelectKey extends VoBase
{
	private static final long serialVersionUID = 201209241557420007L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* 企业ID */
	public static final String ITEM_TYPE = "type" ;					/* 1网站类型/2经营模式/3主要内容/4主要纠纷 */
	public static final String ITEM_OPTION_VALUE = "option_value" ;	/* 选项值 */
	public static final String ITEM_ENT_WEB_SITE_INFO_ID = "ent_web_site_info_id" ;	/* 企业网站ID */
	
	/**
	 * 构造函数
	 */
	public VoEbSiteRelatTypeSelectKey()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbSiteRelatTypeSelectKey(DataBus value)
	{
		super(value);
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

	/* 1网站类型/2经营模式/3主要内容/4主要纠纷 : String */
	public String getType()
	{
		return getValue( ITEM_TYPE );
	}

	public void setType( String type1 )
	{
		setValue( ITEM_TYPE, type1 );
	}

	/* 选项值 : String */
	public String getOption_value()
	{
		return getValue( ITEM_OPTION_VALUE );
	}

	public void setOption_value( String option_value1 )
	{
		setValue( ITEM_OPTION_VALUE, option_value1 );
	}

	/* 企业网站ID : String */
	public String getEnt_web_site_info_id()
	{
		return getValue( ITEM_ENT_WEB_SITE_INFO_ID );
	}

	public void setEnt_web_site_info_id( String ent_web_site_info_id1 )
	{
		setValue( ITEM_ENT_WEB_SITE_INFO_ID, ent_web_site_info_id1 );
	}

}

