package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_ent_check_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbEntCheckTaskSelectKey extends VoBase
{
	private static final long serialVersionUID = 201209241710040019L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* �������ID */
	public static final String ITEM_ENT_WEB_SITE_ID = "ent_web_site_id" ;	/* ������վID */
	
	/**
	 * ���캯��
	 */
	public VoEbEntCheckTaskSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbEntCheckTaskSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* �������ID : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

	/* ������վID : String */
	public String getEnt_web_site_id()
	{
		return getValue( ITEM_ENT_WEB_SITE_ID );
	}

	public void setEnt_web_site_id( String ent_web_site_id1 )
	{
		setValue( ITEM_ENT_WEB_SITE_ID, ent_web_site_id1 );
	}

}

