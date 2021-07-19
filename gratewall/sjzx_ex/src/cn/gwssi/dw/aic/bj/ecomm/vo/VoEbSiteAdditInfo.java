package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_site_addit_info]的数据对象类
 * @author Administrator
 *
 */
public class VoEbSiteAdditInfo extends VoBase
{
	private static final long serialVersionUID = 201209241538470002L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* 企业主键ID */
	public static final String ITEM_IS_ECOMME = "is_ecomme" ;		/* 从事电子商务活动 */
	public static final String ITEM_SITE_CONST_OPERA_TYPE = "site_const_opera_type" ;	/* 电子商务网站的建设运营属于 */
	public static final String ITEM_INTER_SERVI_PROVI = "inter_servi_provi" ;	/* 网络接入服务提供商 */
	public static final String ITEM_SERVE_SERVI_PROVI = "serve_servi_provi" ;	/* 网络服务器托管服务提供商 */
	public static final String ITEM_DOMAIN_ADDRE = "domain_addre" ;	/* 电子商务网站域名地址 */
	public static final String ITEM_ECOMME_TYPE = "ecomme_type" ;	/* 电子商务企业类型 */
	public static final String ITEM_ENTRY_PERSON = "entry_person" ;	/* 录入人 */
	public static final String ITEM_ENTRY_DATE = "entry_date" ;		/* 录入时间 */
	
	/**
	 * 构造函数
	 */
	public VoEbSiteAdditInfo()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbSiteAdditInfo(DataBus value)
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

	/* 从事电子商务活动 : String */
	public String getIs_ecomme()
	{
		return getValue( ITEM_IS_ECOMME );
	}

	public void setIs_ecomme( String is_ecomme1 )
	{
		setValue( ITEM_IS_ECOMME, is_ecomme1 );
	}

	/* 电子商务网站的建设运营属于 : String */
	public String getSite_const_opera_type()
	{
		return getValue( ITEM_SITE_CONST_OPERA_TYPE );
	}

	public void setSite_const_opera_type( String site_const_opera_type1 )
	{
		setValue( ITEM_SITE_CONST_OPERA_TYPE, site_const_opera_type1 );
	}

	/* 网络接入服务提供商 : String */
	public String getInter_servi_provi()
	{
		return getValue( ITEM_INTER_SERVI_PROVI );
	}

	public void setInter_servi_provi( String inter_servi_provi1 )
	{
		setValue( ITEM_INTER_SERVI_PROVI, inter_servi_provi1 );
	}

	/* 网络服务器托管服务提供商 : String */
	public String getServe_servi_provi()
	{
		return getValue( ITEM_SERVE_SERVI_PROVI );
	}

	public void setServe_servi_provi( String serve_servi_provi1 )
	{
		setValue( ITEM_SERVE_SERVI_PROVI, serve_servi_provi1 );
	}

	/* 电子商务网站域名地址 : String */
	public String getDomain_addre()
	{
		return getValue( ITEM_DOMAIN_ADDRE );
	}

	public void setDomain_addre( String domain_addre1 )
	{
		setValue( ITEM_DOMAIN_ADDRE, domain_addre1 );
	}

	/* 电子商务企业类型 : String */
	public String getEcomme_type()
	{
		return getValue( ITEM_ECOMME_TYPE );
	}

	public void setEcomme_type( String ecomme_type1 )
	{
		setValue( ITEM_ECOMME_TYPE, ecomme_type1 );
	}

	/* 录入人 : String */
	public String getEntry_person()
	{
		return getValue( ITEM_ENTRY_PERSON );
	}

	public void setEntry_person( String entry_person1 )
	{
		setValue( ITEM_ENTRY_PERSON, entry_person1 );
	}

	/* 录入时间 : String */
	public String getEntry_date()
	{
		return getValue( ITEM_ENTRY_DATE );
	}

	public void setEntry_date( String entry_date1 )
	{
		setValue( ITEM_ENTRY_DATE, entry_date1 );
	}

}

