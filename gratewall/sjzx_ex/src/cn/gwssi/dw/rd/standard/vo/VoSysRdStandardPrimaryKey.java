package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205020221520008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDARD_ID = "sys_rd_standard_id" ;	/* �������к� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �������к� : String */
	public String getSys_rd_standard_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_ID );
	}

	public void setSys_rd_standard_id( String sys_rd_standard_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_ID, sys_rd_standard_id1 );
	}

}

