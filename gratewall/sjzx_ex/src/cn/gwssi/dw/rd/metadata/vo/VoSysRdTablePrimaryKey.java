package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_table]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdTablePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205031231580004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_TABLE_ID = "sys_rd_table_id" ;	/* ���ݱ�ID */
	
	/**
	 * ���캯��
	 */
	public VoSysRdTablePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdTablePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ���ݱ�ID : String */
	public String getSys_rd_table_id()
	{
		return getValue( ITEM_SYS_RD_TABLE_ID );
	}

	public void setSys_rd_table_id( String sys_rd_table_id1 )
	{
		setValue( ITEM_SYS_RD_TABLE_ID, sys_rd_table_id1 );
	}

}

