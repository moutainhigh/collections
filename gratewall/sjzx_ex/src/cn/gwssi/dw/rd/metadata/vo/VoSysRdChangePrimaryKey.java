package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_change]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdChangePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205091047410004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_CHANGE_ID = "sys_rd_change_id" ;	/* ������ */
	
	/**
	 * ���캯��
	 */
	public VoSysRdChangePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdChangePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ������ : String */
	public String getSys_rd_change_id()
	{
		return getValue( ITEM_SYS_RD_CHANGE_ID );
	}

	public void setSys_rd_change_id( String sys_rd_change_id1 )
	{
		setValue( ITEM_SYS_RD_CHANGE_ID, sys_rd_change_id1 );
	}

}

