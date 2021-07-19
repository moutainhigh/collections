package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_table]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardTableSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205031723560003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDARD_ID = "sys_rd_standard_id" ;	/* ��׼ID */
	public static final String ITEM_STANDARD_NAME = "standard_name" ;	/* ��׼���� */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* ʵ����Ϣ���� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardTableSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardTableSelectKey(DataBus value)
	{
		super(value);
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

}

