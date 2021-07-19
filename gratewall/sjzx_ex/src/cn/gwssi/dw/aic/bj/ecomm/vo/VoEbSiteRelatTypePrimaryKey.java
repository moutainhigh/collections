package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_site_relat_type]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbSiteRelatTypePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201209241557420008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* ����id */
	
	/**
	 * ���캯��
	 */
	public VoEbSiteRelatTypePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbSiteRelatTypePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����id : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

}

