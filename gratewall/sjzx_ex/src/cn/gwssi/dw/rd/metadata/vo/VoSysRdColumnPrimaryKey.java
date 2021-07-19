package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_column]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdColumnPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205071133200012L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_COLUMN_ID = "sys_rd_column_id" ;	/* �ֶ�ID */
	
	/**
	 * ���캯��
	 */
	public VoSysRdColumnPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdColumnPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �ֶ�ID : String */
	public String getSys_rd_column_id()
	{
		return getValue( ITEM_SYS_RD_COLUMN_ID );
	}

	public void setSys_rd_column_id( String sys_rd_column_id1 )
	{
		setValue( ITEM_SYS_RD_COLUMN_ID, sys_rd_column_id1 );
	}

}

