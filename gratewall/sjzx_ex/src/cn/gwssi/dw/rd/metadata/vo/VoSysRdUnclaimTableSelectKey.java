package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_unclaim_table]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdUnclaimTableSelectKey extends VoBase
{
	private static final long serialVersionUID = 201205020916490003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_DATA_SOURCE_ID = "sys_rd_data_source_id" ;	/* ����ԴID */
	public static final String ITEM_UNCLAIM_TABLE_CODE = "unclaim_table_code" ;	/* δ����� */
	public static final String ITEM_UNCLAIM_TABLE_NAME = "unclaim_table_name" ;	/* δ��������� */
	public static final String ITEM_OBJECT_SCHEMA = "object_schema" ;	/* ����ģʽ */
	
	/**
	 * ���캯��
	 */
	public VoSysRdUnclaimTableSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdUnclaimTableSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ����ԴID : String */
	public String getSys_rd_data_source_id()
	{
		return getValue( ITEM_SYS_RD_DATA_SOURCE_ID );
	}

	public void setSys_rd_data_source_id( String sys_rd_data_source_id1 )
	{
		setValue( ITEM_SYS_RD_DATA_SOURCE_ID, sys_rd_data_source_id1 );
	}

	/* δ����� : String */
	public String getUnclaim_table_code()
	{
		return getValue( ITEM_UNCLAIM_TABLE_CODE );
	}

	public void setUnclaim_table_code( String unclaim_table_code1 )
	{
		setValue( ITEM_UNCLAIM_TABLE_CODE, unclaim_table_code1 );
	}

	/* δ��������� : String */
	public String getUnclaim_table_name()
	{
		return getValue( ITEM_UNCLAIM_TABLE_NAME );
	}

	public void setUnclaim_table_name( String unclaim_table_name1 )
	{
		setValue( ITEM_UNCLAIM_TABLE_NAME, unclaim_table_name1 );
	}

	/* ����ģʽ : String */
	public String getObject_schema()
	{
		return getValue( ITEM_OBJECT_SCHEMA );
	}

	public void setObject_schema( String object_schema1 )
	{
		setValue( ITEM_OBJECT_SCHEMA, object_schema1 );
	}

}

