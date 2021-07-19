package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_site_relat_type]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbSiteRelatTypeSelectKey extends VoBase
{
	private static final long serialVersionUID = 201209241557420007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_REG_BUS_ENT_ID = "reg_bus_ent_id" ;	/* ��ҵID */
	public static final String ITEM_TYPE = "type" ;					/* 1��վ����/2��Ӫģʽ/3��Ҫ����/4��Ҫ���� */
	public static final String ITEM_OPTION_VALUE = "option_value" ;	/* ѡ��ֵ */
	public static final String ITEM_ENT_WEB_SITE_INFO_ID = "ent_web_site_info_id" ;	/* ��ҵ��վID */
	
	/**
	 * ���캯��
	 */
	public VoEbSiteRelatTypeSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbSiteRelatTypeSelectKey(DataBus value)
	{
		super(value);
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

	/* 1��վ����/2��Ӫģʽ/3��Ҫ����/4��Ҫ���� : String */
	public String getType()
	{
		return getValue( ITEM_TYPE );
	}

	public void setType( String type1 )
	{
		setValue( ITEM_TYPE, type1 );
	}

	/* ѡ��ֵ : String */
	public String getOption_value()
	{
		return getValue( ITEM_OPTION_VALUE );
	}

	public void setOption_value( String option_value1 )
	{
		setValue( ITEM_OPTION_VALUE, option_value1 );
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

