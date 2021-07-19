package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_ent_web_site_info]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbEntWebSiteInfoPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201209241434510004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* ����chr_id */
	
	/**
	 * ���캯��
	 */
	public VoEbEntWebSiteInfoPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbEntWebSiteInfoPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����chr_id : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

}

