package cn.gwssi.dw.aic.bj.ecomm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[eb_ent_check_task]�����ݶ�����
 * @author Administrator
 *
 */
public class VoEbEntCheckTaskPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201209241710040020L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CHR_ID = "chr_id" ;				/* �������ID */
	
	/**
	 * ���캯��
	 */
	public VoEbEntCheckTaskPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoEbEntCheckTaskPrimaryKey(DataBus value)
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

}

