package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_unclaim_table]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdUnclaimTablePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205020916490004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_UNCLAIM_TABLE_ID = "sys_rd_unclaim_table_id" ;	/* δ�����ID */
	
	/**
	 * ���캯��
	 */
	public VoSysRdUnclaimTablePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdUnclaimTablePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* δ�����ID : String */
	public String getSys_rd_unclaim_table_id()
	{
		return getValue( ITEM_SYS_RD_UNCLAIM_TABLE_ID );
	}

	public void setSys_rd_unclaim_table_id( String sys_rd_unclaim_table_id1 )
	{
		setValue( ITEM_SYS_RD_UNCLAIM_TABLE_ID, sys_rd_unclaim_table_id1 );
	}

}

