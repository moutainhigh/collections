package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_site_addit_info]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbSiteAdditInfo extends VoBase
{
	private static final long serialVersionUID = 201209241538470002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* ��ҵ����ID */
	public static final String ITEM_IS_ECOMME = "is_ecomme" ;		/* ���µ������� */
	public static final String ITEM_SITE_CONST_OPERA_TYPE = "site_const_opera_type" ;	/* ����������վ�Ľ�����Ӫ���� */
	public static final String ITEM_INTER_SERVI_PROVI = "inter_servi_provi" ;	/* �����������ṩ�� */
	public static final String ITEM_SERVE_SERVI_PROVI = "serve_servi_provi" ;	/* ����������йܷ����ṩ�� */
	public static final String ITEM_DOMAIN_ADDRE = "domain_addre" ;	/* ����������վ������ַ */
	public static final String ITEM_ECOMME_TYPE = "ecomme_type" ;	/* ����������ҵ���� */
	public static final String ITEM_ENTRY_PERSON = "entry_person" ;	/* ¼���� */
	public static final String ITEM_ENTRY_DATE = "entry_date" ;		/* ¼��ʱ�� */
	
	/**
	 * ���캯��
	 */
	public VoEbSiteAdditInfo()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbSiteAdditInfo(DataBus value)
	{
		super(value);
	}
	
	/* ��ҵ����ID : String */
	public String getReg_bus_ent_id()
	{
		return getValue( ITEM_REG_BUS_ENT_ID );
	}

	public void setReg_bus_ent_id( String reg_bus_ent_id1 )
	{
		setValue( ITEM_REG_BUS_ENT_ID, reg_bus_ent_id1 );
	}

	/* ���µ������� : String */
	public String getIs_ecomme()
	{
		return getValue( ITEM_IS_ECOMME );
	}

	public void setIs_ecomme( String is_ecomme1 )
	{
		setValue( ITEM_IS_ECOMME, is_ecomme1 );
	}

	/* ����������վ�Ľ�����Ӫ���� : String */
	public String getSite_const_opera_type()
	{
		return getValue( ITEM_SITE_CONST_OPERA_TYPE );
	}

	public void setSite_const_opera_type( String site_const_opera_type1 )
	{
		setValue( ITEM_SITE_CONST_OPERA_TYPE, site_const_opera_type1 );
	}

	/* �����������ṩ�� : String */
	public String getInter_servi_provi()
	{
		return getValue( ITEM_INTER_SERVI_PROVI );
	}

	public void setInter_servi_provi( String inter_servi_provi1 )
	{
		setValue( ITEM_INTER_SERVI_PROVI, inter_servi_provi1 );
	}

	/* ����������йܷ����ṩ�� : String */
	public String getServe_servi_provi()
	{
		return getValue( ITEM_SERVE_SERVI_PROVI );
	}

	public void setServe_servi_provi( String serve_servi_provi1 )
	{
		setValue( ITEM_SERVE_SERVI_PROVI, serve_servi_provi1 );
	}

	/* ����������վ������ַ : String */
	public String getDomain_addre()
	{
		return getValue( ITEM_DOMAIN_ADDRE );
	}

	public void setDomain_addre( String domain_addre1 )
	{
		setValue( ITEM_DOMAIN_ADDRE, domain_addre1 );
	}

	/* ����������ҵ���� : String */
	public String getEcomme_type()
	{
		return getValue( ITEM_ECOMME_TYPE );
	}

	public void setEcomme_type( String ecomme_type1 )
	{
		setValue( ITEM_ECOMME_TYPE, ecomme_type1 );
	}

	/* ¼���� : String */
	public String getEntry_person()
	{
		return getValue( ITEM_ENTRY_PERSON );
	}

	public void setEntry_person( String entry_person1 )
	{
		setValue( ITEM_ENTRY_PERSON, entry_person1 );
	}

	/* ¼��ʱ�� : String */
	public String getEntry_date()
	{
		return getValue( ITEM_ENTRY_DATE );
	}

	public void setEntry_date( String entry_date1 )
	{
		setValue( ITEM_ENTRY_DATE, entry_date1 );
	}

}

