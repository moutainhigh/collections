package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_codedata]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardCodedataPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205031401170004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_ID = "id" ;						/* ����ֵID */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardCodedataPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardCodedataPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ֵID : String */
	public String getId()
	{
		return getValue( ITEM_ID );
	}

	public void setId( String id1 )
	{
		setValue( ITEM_ID, id1 );
	}

}

