package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_dataelement]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardDataelementSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205022055520007L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDARD_DATAELEMENT_ID = "sys_rd_standard_dataelement_id" ;	/* ��������ԪID */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardDataelementSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardDataelementSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ��������ԪID : String */
	public String getSys_rd_standard_dataelement_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_DATAELEMENT_ID );
	}

	public void setSys_rd_standard_dataelement_id( String sys_rd_standard_dataelement_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_DATAELEMENT_ID, sys_rd_standard_dataelement_id1 );
	}

}

