package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_unclaim_table]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdUnclaimTable extends VoBase
{
	private static final long serialVersionUID = 201205020916490002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_UNCLAIM_TABLE_ID = "sys_rd_unclaim_table_id" ;	/* δ�����ID */
	public static final String ITEM_SYS_RD_DATA_SOURCE_ID = "sys_rd_data_source_id" ;	/* ����ԴID */
	public static final String ITEM_UNCLAIM_TABLE_CODE = "unclaim_table_code" ;	/* δ����� */
	public static final String ITEM_UNCLAIM_TABLE_NAME = "unclaim_table_name" ;	/* δ��������� */
	public static final String ITEM_OBJECT_SCHEMA = "object_schema" ;	/* ����ģʽ */
	public static final String ITEM_TB_INDEX_NAME = "tb_index_name" ;	/* �������� */
	public static final String ITEM_TB_INDEX_COLUMNS = "tb_index_columns" ;	/* �����ֶ� */
	public static final String ITEM_TB_PK_NAME = "tb_pk_name" ;		/* ������ */
	public static final String ITEM_TB_PK_COLUMNS = "tb_pk_columns" ;	/* �����ֶ� */
	public static final String ITEM_CUR_RECORD_COUNT = "cur_record_count" ;	/* ��ǰ��¼���� */
	public static final String ITEM_REMARK = "remark" ;				/* ��ע */
	public static final String ITEM_DATA_OBJECT_TYPE = "data_object_type" ;	/* ���ݶ������� */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* ʱ��� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdUnclaimTable()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdUnclaimTable(DataBus value)
	{
		super(value);
	}
	
	/* δ�����ID : String */
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

	/* �������� : String */
	public String getTb_index_name()
	{
		return getValue( ITEM_TB_INDEX_NAME );
	}

	public void setTb_index_name( String tb_index_name1 )
	{
		setValue( ITEM_TB_INDEX_NAME, tb_index_name1 );
	}

	/* �����ֶ� : String */
	public String getTb_index_columns()
	{
		return getValue( ITEM_TB_INDEX_COLUMNS );
	}

	public void setTb_index_columns( String tb_index_columns1 )
	{
		setValue( ITEM_TB_INDEX_COLUMNS, tb_index_columns1 );
	}

	/* ������ : String */
	public String getTb_pk_name()
	{
		return getValue( ITEM_TB_PK_NAME );
	}

	public void setTb_pk_name( String tb_pk_name1 )
	{
		setValue( ITEM_TB_PK_NAME, tb_pk_name1 );
	}

	/* �����ֶ� : String */
	public String getTb_pk_columns()
	{
		return getValue( ITEM_TB_PK_COLUMNS );
	}

	public void setTb_pk_columns( String tb_pk_columns1 )
	{
		setValue( ITEM_TB_PK_COLUMNS, tb_pk_columns1 );
	}

	/* ��ǰ��¼���� : String */
	public String getCur_record_count()
	{
		return getValue( ITEM_CUR_RECORD_COUNT );
	}

	public void setCur_record_count( String cur_record_count1 )
	{
		setValue( ITEM_CUR_RECORD_COUNT, cur_record_count1 );
	}

	/* ��ע : String */
	public String getRemark()
	{
		return getValue( ITEM_REMARK );
	}

	public void setRemark( String remark1 )
	{
		setValue( ITEM_REMARK, remark1 );
	}

	/* ���ݶ������� : String */
	public String getData_object_type()
	{
		return getValue( ITEM_DATA_OBJECT_TYPE );
	}

	public void setData_object_type( String data_object_type1 )
	{
		setValue( ITEM_DATA_OBJECT_TYPE, data_object_type1 );
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

