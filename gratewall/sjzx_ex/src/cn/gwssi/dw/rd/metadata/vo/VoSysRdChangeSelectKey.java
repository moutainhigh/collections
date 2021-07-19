package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_change]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdChangeSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205091047410003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_CHANGE_ID = "sys_rd_change_id" ;	/* ������ */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* ����� */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* �ֶ� */
	public static final String ITEM_CHANGE_ITEM = "change_item" ;	/* ������� */
	public static final String ITEM_CHANGE_RESULT = "change_result" ;	/* ������ */
	
	/**
	 * ���캯��
	 */
	public VoSysRdChangeSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdChangeSelectKey(DataBus value)
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

	/* ����� : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* �ֶ� : String */
	public String getColumn_name()
	{
		return getValue( ITEM_COLUMN_NAME );
	}

	public void setColumn_name( String column_name1 )
	{
		setValue( ITEM_COLUMN_NAME, column_name1 );
	}

	/* ������� : String */
	public String getChange_item()
	{
		return getValue( ITEM_CHANGE_ITEM );
	}

	public void setChange_item( String change_item1 )
	{
		setValue( ITEM_CHANGE_ITEM, change_item1 );
	}

	/* ������ : String */
	public String getChange_result()
	{
		return getValue( ITEM_CHANGE_RESULT );
	}

	public void setChange_result( String change_result1 )
	{
		setValue( ITEM_CHANGE_RESULT, change_result1 );
	}

}

