package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_unclaim_column]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdUnclaimColumn extends VoBase
{
	private static final long serialVersionUID = 201205071126260002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_UNCLAIM_COLUMN_ID = "sys_rd_unclaim_column_id" ;	/* ������ */
	public static final String ITEM_SYS_RD_UNCLAIM_TABLE_ID = "sys_rd_unclaim_table_id" ;	/* �����ID */
	public static final String ITEM_SYS_RD_DATA_SOURCE_ID = "sys_rd_data_source_id" ;	/* ����ԴID */
	public static final String ITEM_UNCLAIM_TAB_CODE = "unclaim_tab_code" ;	/* δ������� */
	public static final String ITEM_UNCLAIM_COLUMN_CODE = "unclaim_column_code" ;	/* �ֶδ��� */
	public static final String ITEM_UNCLAIM_COLUMN_NAME = "unclaim_column_name" ;	/* �ֶ����� */
	public static final String ITEM_UNCLAIM_COLUMN_TYPE = "unclaim_column_type" ;	/* �ֶ����� */
	public static final String ITEM_UNCLAIM_COLUMN_LENGTH = "unclaim_column_length" ;	/* �ֶγ��� */
	public static final String ITEM_IS_PRIMARY_KEY = "is_primary_key" ;	/* �Ƿ����� */
	public static final String ITEM_IS_INDEX = "is_index" ;			/* �Ƿ����� */
	public static final String ITEM_IS_NULL = "is_null" ;			/* �Ƿ�����Ϊ�� */
	public static final String ITEM_DEFAULT_VALUE = "default_value" ;	/* Ĭ��ֵ */
	public static final String ITEM_REMARKS = "remarks" ;			/* ��ע */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* ʱ��� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdUnclaimColumn()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdUnclaimColumn(DataBus value)
	{
		super(value);
	}
	
	/* ������ : String */
	public String getSys_rd_unclaim_column_id()
	{
		return getValue( ITEM_SYS_RD_UNCLAIM_COLUMN_ID );
	}

	public void setSys_rd_unclaim_column_id( String sys_rd_unclaim_column_id1 )
	{
		setValue( ITEM_SYS_RD_UNCLAIM_COLUMN_ID, sys_rd_unclaim_column_id1 );
	}

	/* �����ID : String */
	public String getSys_rd_unclaim_table_id()
	{
		return getValue( ITEM_SYS_RD_UNCLAIM_TABLE_ID );
	}

	public void setSys_rd_unclaim_table_id( String sys_rd_unclaim_table_id1 )
	{
		setValue( ITEM_SYS_RD_UNCLAIM_TABLE_ID, sys_rd_unclaim_table_id1 );
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

	/* δ������� : String */
	public String getUnclaim_tab_code()
	{
		return getValue( ITEM_UNCLAIM_TAB_CODE );
	}

	public void setUnclaim_tab_code( String unclaim_tab_code1 )
	{
		setValue( ITEM_UNCLAIM_TAB_CODE, unclaim_tab_code1 );
	}

	/* �ֶδ��� : String */
	public String getUnclaim_column_code()
	{
		return getValue( ITEM_UNCLAIM_COLUMN_CODE );
	}

	public void setUnclaim_column_code( String unclaim_column_code1 )
	{
		setValue( ITEM_UNCLAIM_COLUMN_CODE, unclaim_column_code1 );
	}

	/* �ֶ����� : String */
	public String getUnclaim_column_name()
	{
		return getValue( ITEM_UNCLAIM_COLUMN_NAME );
	}

	public void setUnclaim_column_name( String unclaim_column_name1 )
	{
		setValue( ITEM_UNCLAIM_COLUMN_NAME, unclaim_column_name1 );
	}

	/* �ֶ����� : String */
	public String getUnclaim_column_type()
	{
		return getValue( ITEM_UNCLAIM_COLUMN_TYPE );
	}

	public void setUnclaim_column_type( String unclaim_column_type1 )
	{
		setValue( ITEM_UNCLAIM_COLUMN_TYPE, unclaim_column_type1 );
	}

	/* �ֶγ��� : String */
	public String getUnclaim_column_length()
	{
		return getValue( ITEM_UNCLAIM_COLUMN_LENGTH );
	}

	public void setUnclaim_column_length( String unclaim_column_length1 )
	{
		setValue( ITEM_UNCLAIM_COLUMN_LENGTH, unclaim_column_length1 );
	}

	/* �Ƿ����� : String */
	public String getIs_primary_key()
	{
		return getValue( ITEM_IS_PRIMARY_KEY );
	}

	public void setIs_primary_key( String is_primary_key1 )
	{
		setValue( ITEM_IS_PRIMARY_KEY, is_primary_key1 );
	}

	/* �Ƿ����� : String */
	public String getIs_index()
	{
		return getValue( ITEM_IS_INDEX );
	}

	public void setIs_index( String is_index1 )
	{
		setValue( ITEM_IS_INDEX, is_index1 );
	}

	/* �Ƿ�����Ϊ�� : String */
	public String getIs_null()
	{
		return getValue( ITEM_IS_NULL );
	}

	public void setIs_null( String is_null1 )
	{
		setValue( ITEM_IS_NULL, is_null1 );
	}

	/* Ĭ��ֵ : String */
	public String getDefault_value()
	{
		return getValue( ITEM_DEFAULT_VALUE );
	}

	public void setDefault_value( String default_value1 )
	{
		setValue( ITEM_DEFAULT_VALUE, default_value1 );
	}

	/* ��ע : String */
	public String getRemarks()
	{
		return getValue( ITEM_REMARKS );
	}

	public void setRemarks( String remarks1 )
	{
		setValue( ITEM_REMARKS, remarks1 );
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

