package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_site_illeg_trail]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbSiteIllegTrailPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201209241647280016L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* ����ID(��վID) */
	
	/**
	 * ���캯��
	 */
	public VoEbSiteIllegTrailPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbSiteIllegTrailPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ID(��վID) : String */
	public String getChr_id()
	{
		return getValue( ITEM_CHR_ID );
	}

	public void setChr_id( String chr_id1 )
	{
		setValue( ITEM_CHR_ID, chr_id1 );
	}

}

