package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[view_sys_count_log]�����ݶ�����
 * @author Administrator
 *
 */
public class VoViewSysCountLogPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201208031540190004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_TABLE_CLASS_ID = "table_class_id" ;	/* ��������id */
	
	/**
	 * ���캯��
	 */
	public VoViewSysCountLogPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoViewSysCountLogPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* ��������id : String */
	public String getTable_class_id()
	{
		return getValue( ITEM_TABLE_CLASS_ID );
	}

	public void setTable_class_id( String table_class_id1 )
	{
		setValue( ITEM_TABLE_CLASS_ID, table_class_id1 );
	}

}

