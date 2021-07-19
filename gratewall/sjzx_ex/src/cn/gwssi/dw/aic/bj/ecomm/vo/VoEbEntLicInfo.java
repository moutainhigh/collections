package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * 数据表[eb_ent_lic_info]的数据对象类
 * @author Administrator
 *
 */
public class VoEbEntLicInfo extends VoBase
{
	private static final long serialVersionUID = 201209241612200010L;
	
	/**
	 * 变量列表
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* 主键ID */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* 企业ID */
	public static final String ITEM_LIC_NAME = "lic_name" ;			/* 许可证名称 */
	public static final String ITEM_LIC_NO = "lic_no" ;				/* 许可证号 */
	public static final String ITEM_CERTI_DATE = "certi_date" ;		/* 发证时间 */
	public static final String ITEM_END_DATE = "end_date" ;			/* 有效期至 */
	public static final String ITEM_ENT_NAME = "ent_name" ;			/* 企业名称 */
	public static final String ITEM_IS_ECOMME = "is_ecomme" ;		/* 是否电子商务活动许可证（0：否1：是） */
	public static final String ITEM_LIC_UNIT = "lic_unit" ;			/* 发证单位 */
	public static final String ITEM_LIC_CODE = "lic_code" ;			/* 许可证代码 */
	public static final String ITEM_ENT_WEB_SITE_INFO_ID = "ent_web_site_info_id" ;	/* 企业网站ID */
	
	/**
	 * 构造函数
	 */
	public VoEbEntLicInfo()
	{
		super();
	}
	
	/**
	 * 构造函数
	 * @param value 数据节点
	 */
	public VoEbEntLicInfo(DataBus value)
	{
		super(value);
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

	/* 企业ID : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
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

	/* 许可证号 : String */
	public String getLic_no()
	{
		return getValue( ITEM_LIC_NO );
	}

	public void setLic_no( String lic_no1 )
	{
		setValue( ITEM_LIC_NO, lic_no1 );
	}

	/* 发证时间 : String */
	public String getCerti_date()
	{
		return getValue( ITEM_CERTI_DATE );
	}

	public void setCerti_date( String certi_date1 )
	{
		setValue( ITEM_CERTI_DATE, certi_date1 );
	}

	/* 有效期至 : String */
	public String getEnd_date()
	{
		return getValue( ITEM_END_DATE );
	}

	public void setEnd_date( String end_date1 )
	{
		setValue( ITEM_END_DATE, end_date1 );
	}

	/* 企业名称 : String */
	public String getEnt_name()
	{
		return getValue( ITEM_ENT_NAME );
	}

	public void setEnt_name( String ent_name1 )
	{
		setValue( ITEM_ENT_NAME, ent_name1 );
	}

	/* 是否电子商务活动许可证（0：否1：是） : String */
	public String getIs_ecomme()
	{
		return getValue( ITEM_IS_ECOMME );
	}

	public void setIs_ecomme( String is_ecomme1 )
	{
		setValue( ITEM_IS_ECOMME, is_ecomme1 );
	}

	/* 发证单位 : String */
	public String getLic_unit()
	{
		return getValue( ITEM_LIC_UNIT );
	}

	public void setLic_unit( String lic_unit1 )
	{
		setValue( ITEM_LIC_UNIT, lic_unit1 );
	}

	/* 许可证代码 : String */
	public String getLic_code()
	{
		return getValue( ITEM_LIC_CODE );
	}

	public void setLic_code( String lic_code1 )
	{
		setValue( ITEM_LIC_CODE, lic_code1 );
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

