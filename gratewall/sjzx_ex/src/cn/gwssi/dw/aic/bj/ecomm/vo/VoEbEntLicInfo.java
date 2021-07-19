package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_ent_lic_info]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbEntLicInfo extends VoBase
{
	private static final long serialVersionUID = 201209241612200010L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* ����ID */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* ��ҵID */
	public static final String ITEM_LIC_NAME = "lic_name" ;			/* ���֤���� */
	public static final String ITEM_LIC_NO = "lic_no" ;				/* ���֤�� */
	public static final String ITEM_CERTI_DATE = "certi_date" ;		/* ��֤ʱ�� */
	public static final String ITEM_END_DATE = "end_date" ;			/* ��Ч���� */
	public static final String ITEM_ENT_NAME = "ent_name" ;			/* ��ҵ���� */
	public static final String ITEM_IS_ECOMME = "is_ecomme" ;		/* �Ƿ�����������֤��0����1���ǣ� */
	public static final String ITEM_LIC_UNIT = "lic_unit" ;			/* ��֤��λ */
	public static final String ITEM_LIC_CODE = "lic_code" ;			/* ���֤���� */
	public static final String ITEM_ENT_WEB_SITE_INFO_ID = "ent_web_site_info_id" ;	/* ��ҵ��վID */
	
	/**
	 * ���캯��
	 */
	public VoEbEntLicInfo()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbEntLicInfo(DataBus value)
	{
		super(value);
	}
	
	/* ����ID : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

	/* ��ҵID : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

	/* ���֤���� : String */
	public String getLic_name()
	{
		return getValue( ITEM_LIC_NAME );
	}

	public void setLic_name( String lic_name1 )
	{
		setValue( ITEM_LIC_NAME, lic_name1 );
	}

	/* ���֤�� : String */
	public String getLic_no()
	{
		return getValue( ITEM_LIC_NO );
	}

	public void setLic_no( String lic_no1 )
	{
		setValue( ITEM_LIC_NO, lic_no1 );
	}

	/* ��֤ʱ�� : String */
	public String getCerti_date()
	{
		return getValue( ITEM_CERTI_DATE );
	}

	public void setCerti_date( String certi_date1 )
	{
		setValue( ITEM_CERTI_DATE, certi_date1 );
	}

	/* ��Ч���� : String */
	public String getEnd_date()
	{
		return getValue( ITEM_END_DATE );
	}

	public void setEnd_date( String end_date1 )
	{
		setValue( ITEM_END_DATE, end_date1 );
	}

	/* ��ҵ���� : String */
	public String getEnt_name()
	{
		return getValue( ITEM_ENT_NAME );
	}

	public void setEnt_name( String ent_name1 )
	{
		setValue( ITEM_ENT_NAME, ent_name1 );
	}

	/* �Ƿ�����������֤��0����1���ǣ� : String */
	public String getIs_ecomme()
	{
		return getValue( ITEM_IS_ECOMME );
	}

	public void setIs_ecomme( String is_ecomme1 )
	{
		setValue( ITEM_IS_ECOMME, is_ecomme1 );
	}

	/* ��֤��λ : String */
	public String getLic_unit()
	{
		return getValue( ITEM_LIC_UNIT );
	}

	public void setLic_unit( String lic_unit1 )
	{
		setValue( ITEM_LIC_UNIT, lic_unit1 );
	}

	/* ���֤���� : String */
	public String getLic_code()
	{
		return getValue( ITEM_LIC_CODE );
	}

	public void setLic_code( String lic_code1 )
	{
		setValue( ITEM_LIC_CODE, lic_code1 );
	}

	/* ��ҵ��վID : String */
	public String getEnt_web_site_info_id()
	{
		return getValue( ITEM_ENT_WEB_SITE_INFO_ID );
	}

	public void setEnt_web_site_info_id( String ent_web_site_info_id1 )
	{
		setValue( ITEM_ENT_WEB_SITE_INFO_ID, ent_web_site_info_id1 );
	}

}

