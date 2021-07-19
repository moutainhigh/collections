package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_column]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardColumnPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205031749460008L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDARD_COLUMN_ID = "sys_rd_standard_column_id" ;	/* ָ����ID */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardColumnPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardColumnPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ָ����ID : String */
	public String getSys_rd_standard_column_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_COLUMN_ID );
	}

	public void setSys_rd_standard_column_id( String sys_rd_standard_column_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_COLUMN_ID, sys_rd_standard_column_id1 );
	}

}

