package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_table]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardTablePrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205031723560004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDARD_TABLE_ID = "sys_rd_standard_table_id" ;	/* ʵ����ϢID */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardTablePrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardTablePrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ʵ����ϢID : String */
	public String getSys_rd_standard_table_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_TABLE_ID );
	}

	public void setSys_rd_standard_table_id( String sys_rd_standard_table_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_TABLE_ID, sys_rd_standard_table_id1 );
	}

}

