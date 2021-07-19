package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_table]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardTable extends VoBase
{
	private static final long serialVersionUID = 201205031723560002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDARD_TABLE_ID = "sys_rd_standard_table_id" ;	/* ʵ����ϢID */
	public static final String ITEM_SYS_RD_STANDARD_ID = "sys_rd_standard_id" ;	/* ��׼ID */
	public static final String ITEM_STANDARD_NAME = "standard_name" ;	/* ��׼���� */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* ʵ����Ϣ���� */
	public static final String ITEM_TABLE_BELONGS = "table_belongs" ;	/* ������ϵ */
	public static final String ITEM_MEMO = "memo" ;					/* ��ע */
	public static final String ITEM_SORT = "sort" ;					/* ����� */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* ʱ��� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardTable()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardTable(DataBus value)
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

	/* ��׼ID : String */
	public String getSys_rd_standard_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_ID );
	}

	public void setSys_rd_standard_id( String sys_rd_standard_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_ID, sys_rd_standard_id1 );
	}

	/* ��׼���� : String */
	public String getStandard_name()
	{
		return getValue( ITEM_STANDARD_NAME );
	}

	public void setStandard_name( String standard_name1 )
	{
		setValue( ITEM_STANDARD_NAME, standard_name1 );
	}

	/* ʵ����Ϣ���� : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* ������ϵ : String */
	public String getTable_belongs()
	{
		return getValue( ITEM_TABLE_BELONGS );
	}

	public void setTable_belongs( String table_belongs1 )
	{
		setValue( ITEM_TABLE_BELONGS, table_belongs1 );
	}

	/* ��ע : String */
	public String getMemo()
	{
		return getValue( ITEM_MEMO );
	}

	public void setMemo( String memo1 )
	{
		setValue( ITEM_MEMO, memo1 );
	}

	/* ����� : String */
	public String getSort()
	{
		return getValue( ITEM_SORT );
	}

	public void setSort( String sort1 )
	{
		setValue( ITEM_SORT, sort1 );
	}

	/* ʱ��� : String */
	public String getTimestamp()
	{
		return getValue( ITEM_TIMESTAMP );
	}

	public void setTimestamp( String timestamp1 )
	{
		setValue( ITEM_TIMESTAMP, timestamp1 );
	}

}

