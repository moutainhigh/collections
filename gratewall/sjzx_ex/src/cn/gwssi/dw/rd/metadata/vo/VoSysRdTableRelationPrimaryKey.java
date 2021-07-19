package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_table_relation]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdTableRelationPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201205260127140004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_TABLE_RELATION_ID = "sys_rd_table_relation_id" ;	/* ����ID */
	
	/**
	 * ���캯��
	 */
	public VoSysRdTableRelationPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdTableRelationPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ID : String */
	public String getSys_rd_table_relation_id()
	{
		return getValue( ITEM_SYS_RD_TABLE_RELATION_ID );
	}

	public void setSys_rd_table_relation_id( String sys_rd_table_relation_id1 )
	{
		setValue( ITEM_SYS_RD_TABLE_RELATION_ID, sys_rd_table_relation_id1 );
	}

}

