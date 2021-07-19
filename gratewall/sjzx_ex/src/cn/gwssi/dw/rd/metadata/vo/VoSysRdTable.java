package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_table]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdTable extends VoBase
{
	private static final long serialVersionUID = 201205031231580002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_TABLE_ID = "sys_rd_table_id" ;	/* ���ݱ�ID */
	public static final String ITEM_SYS_RD_SYSTEM_ID = "sys_rd_system_id" ;	/* ҵ������ID */
	public static final String ITEM_SYS_NO = "sys_no" ;				/* ҵ�������� */
	public static final String ITEM_SYS_NAME = "sys_name" ;			/* �������� */
	public static final String ITEM_SYS_RD_DATA_SOURCE_ID = "sys_rd_data_source_id" ;	/* ����ԴID */
	public static final String ITEM_TABLE_CODE = "table_code" ;		/* ���ݱ��� */
	public static final String ITEM_TABLE_NAME = "table_name" ;		/* ���ݱ������� */
	public static final String ITEM_TABLE_NO = "table_no" ;			/* ���ݱ��� */
	public static final String ITEM_TABLE_SQL = "table_sql" ;		/* ���ݱ�sql */
	public static final String ITEM_TABLE_SORT = "table_sort" ;		/* �����ֶ� */
	public static final String ITEM_TABLE_DIST = "table_dist" ;		/* �����ֶ� */
	public static final String ITEM_TABLE_TIME = "table_time" ;		/* ʱ���ֶ� */
	public static final String ITEM_PARENT_TABLE = "parent_table" ;	/* ������ */
	public static final String ITEM_PARENT_PK = "parent_pk" ;		/* ���������� */
	public static final String ITEM_TABLE_FK = "table_fk" ;			/* �븸��������� */
	public static final String ITEM_FIRST_RECORD_COUNT = "first_record_count" ;	/* ���������� */
	public static final String ITEM_LAST_RECORD_COUNT = "last_record_count" ;	/* ���һ��ͬ�������� */
	public static final String ITEM_TABLE_TYPE = "table_type" ;		/* ������ */
	public static final String ITEM_TABLE_PRIMARY_KEY = "table_primary_key" ;	/* ������ */
	public static final String ITEM_TABLE_INDEX = "table_index" ;	/* ������ */
	public static final String ITEM_TABLE_USE = "table_use" ;		/* ��; */
	public static final String ITEM_GEN_CODE_COLUMN = "gen_code_column" ;	/* �ִܾ����ֶ� */
	public static final String ITEM_PROV_CODE_COLUMN = "prov_code_column" ;	/* ʡ�ִ����ֶ� */
	public static final String ITEM_CITY_CODE_COLUMN = "city_code_column" ;	/* �оִ����ֶ� */
	public static final String ITEM_CONTENT = "content" ;			/* �����ֶ����� */
	public static final String ITEM_CLAIM_OPERATOR = "claim_operator" ;	/* ������ */
	public static final String ITEM_CLAIM_DATE = "claim_date" ;		/* �������� */
	public static final String ITEM_CHANGED_STATUS = "changed_status" ;	/* �仯״̬ */
	public static final String ITEM_OBJECT_SCHEMA = "object_schema" ;	/* ��ģʽ */
	public static final String ITEM_MEMO = "memo" ;					/* ��ע */
	public static final String ITEM_IS_QUERY = "is_query" ;			/* �Ƿ�ɲ�ѯ */
	public static final String ITEM_IS_TRANS = "is_trans" ;			/* �Ƿ�ɹ��� */
	public static final String ITEM_IS_DOWNLOAD = "is_download" ;	/* �Ƿ������ */
	public static final String ITEM_SORT = "sort" ;					/* ���� */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* ʱ��� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdTable()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdTable(DataBus value)
	{
		super(value);
	}
	
	/* ���ݱ�ID : String */
	public String getSys_rd_table_id()
	{
		return getValue( ITEM_SYS_RD_TABLE_ID );
	}

	public void setSys_rd_table_id( String sys_rd_table_id1 )
	{
		setValue( ITEM_SYS_RD_TABLE_ID, sys_rd_table_id1 );
	}

	/* ҵ������ID : String */
	public String getSys_rd_system_id()
	{
		return getValue( ITEM_SYS_RD_SYSTEM_ID );
	}

	public void setSys_rd_system_id( String sys_rd_system_id1 )
	{
		setValue( ITEM_SYS_RD_SYSTEM_ID, sys_rd_system_id1 );
	}

	/* ҵ�������� : String */
	public String getSys_no()
	{
		return getValue( ITEM_SYS_NO );
	}

	public void setSys_no( String sys_no1 )
	{
		setValue( ITEM_SYS_NO, sys_no1 );
	}

	/* �������� : String */
	public String getSys_name()
	{
		return getValue( ITEM_SYS_NAME );
	}

	public void setSys_name( String sys_name1 )
	{
		setValue( ITEM_SYS_NAME, sys_name1 );
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

	/* ���ݱ��� : String */
	public String getTable_code()
	{
		return getValue( ITEM_TABLE_CODE );
	}

	public void setTable_code( String table_code1 )
	{
		setValue( ITEM_TABLE_CODE, table_code1 );
	}

	/* ���ݱ������� : String */
	public String getTable_name()
	{
		return getValue( ITEM_TABLE_NAME );
	}

	public void setTable_name( String table_name1 )
	{
		setValue( ITEM_TABLE_NAME, table_name1 );
	}

	/* ���ݱ��� : String */
	public String getTable_no()
	{
		return getValue( ITEM_TABLE_NO );
	}

	public void setTable_no( String table_no1 )
	{
		setValue( ITEM_TABLE_NO, table_no1 );
	}

	/* ���ݱ�sql : String */
	public String getTable_sql()
	{
		return getValue( ITEM_TABLE_SQL );
	}

	public void setTable_sql( String table_sql1 )
	{
		setValue( ITEM_TABLE_SQL, table_sql1 );
	}

	/* �����ֶ� : String */
	public String getTable_sort()
	{
		return getValue( ITEM_TABLE_SORT );
	}

	public void setTable_sort( String table_sort1 )
	{
		setValue( ITEM_TABLE_SORT, table_sort1 );
	}

	/* �����ֶ� : String */
	public String getTable_dist()
	{
		return getValue( ITEM_TABLE_DIST );
	}

	public void setTable_dist( String table_dist1 )
	{
		setValue( ITEM_TABLE_DIST, table_dist1 );
	}

	/* ʱ���ֶ� : String */
	public String getTable_time()
	{
		return getValue( ITEM_TABLE_TIME );
	}

	public void setTable_time( String table_time1 )
	{
		setValue( ITEM_TABLE_TIME, table_time1 );
	}

	/* ������ : String */
	public String getParent_table()
	{
		return getValue( ITEM_PARENT_TABLE );
	}

	public void setParent_table( String parent_table1 )
	{
		setValue( ITEM_PARENT_TABLE, parent_table1 );
	}

	/* ���������� : String */
	public String getParent_pk()
	{
		return getValue( ITEM_PARENT_PK );
	}

	public void setParent_pk( String parent_pk1 )
	{
		setValue( ITEM_PARENT_PK, parent_pk1 );
	}

	/* �븸��������� : String */
	public String getTable_fk()
	{
		return getValue( ITEM_TABLE_FK );
	}

	public void setTable_fk( String table_fk1 )
	{
		setValue( ITEM_TABLE_FK, table_fk1 );
	}

	/* ���������� : String */
	public String getFirst_record_count()
	{
		return getValue( ITEM_FIRST_RECORD_COUNT );
	}

	public void setFirst_record_count( String first_record_count1 )
	{
		setValue( ITEM_FIRST_RECORD_COUNT, first_record_count1 );
	}

	/* ���һ��ͬ�������� : String */
	public String getLast_record_count()
	{
		return getValue( ITEM_LAST_RECORD_COUNT );
	}

	public void setLast_record_count( String last_record_count1 )
	{
		setValue( ITEM_LAST_RECORD_COUNT, last_record_count1 );
	}

	/* ������ : String */
	public String getTable_type()
	{
		return getValue( ITEM_TABLE_TYPE );
	}

	public void setTable_type( String table_type1 )
	{
		setValue( ITEM_TABLE_TYPE, table_type1 );
	}

	/* ������ : String */
	public String getTable_primary_key()
	{
		return getValue( ITEM_TABLE_PRIMARY_KEY );
	}

	public void setTable_primary_key( String table_primary_key1 )
	{
		setValue( ITEM_TABLE_PRIMARY_KEY, table_primary_key1 );
	}

	/* ������ : String */
	public String getTable_index()
	{
		return getValue( ITEM_TABLE_INDEX );
	}

	public void setTable_index( String table_index1 )
	{
		setValue( ITEM_TABLE_INDEX, table_index1 );
	}

	/* ��; : String */
	public String getTable_use()
	{
		return getValue( ITEM_TABLE_USE );
	}

	public void setTable_use( String table_use1 )
	{
		setValue( ITEM_TABLE_USE, table_use1 );
	}

	/* �ִܾ����ֶ� : String */
	public String getGen_code_column()
	{
		return getValue( ITEM_GEN_CODE_COLUMN );
	}

	public void setGen_code_column( String gen_code_column1 )
	{
		setValue( ITEM_GEN_CODE_COLUMN, gen_code_column1 );
	}

	/* ʡ�ִ����ֶ� : String */
	public String getProv_code_column()
	{
		return getValue( ITEM_PROV_CODE_COLUMN );
	}

	public void setProv_code_column( String prov_code_column1 )
	{
		setValue( ITEM_PROV_CODE_COLUMN, prov_code_column1 );
	}

	/* �оִ����ֶ� : String */
	public String getCity_code_column()
	{
		return getValue( ITEM_CITY_CODE_COLUMN );
	}

	public void setCity_code_column( String city_code_column1 )
	{
		setValue( ITEM_CITY_CODE_COLUMN, city_code_column1 );
	}

	/* �����ֶ����� : String */
	public String getContent()
	{
		return getValue( ITEM_CONTENT );
	}

	public void setContent( String content1 )
	{
		setValue( ITEM_CONTENT, content1 );
	}

	/* ������ : String */
	public String getClaim_operator()
	{
		return getValue( ITEM_CLAIM_OPERATOR );
	}

	public void setClaim_operator( String claim_operator1 )
	{
		setValue( ITEM_CLAIM_OPERATOR, claim_operator1 );
	}

	/* �������� : String */
	public String getClaim_date()
	{
		return getValue( ITEM_CLAIM_DATE );
	}

	public void setClaim_date( String claim_date1 )
	{
		setValue( ITEM_CLAIM_DATE, claim_date1 );
	}

	/* �仯״̬ : String */
	public String getChanged_status()
	{
		return getValue( ITEM_CHANGED_STATUS );
	}

	public void setChanged_status( String changed_status1 )
	{
		setValue( ITEM_CHANGED_STATUS, changed_status1 );
	}

	/* ��ģʽ : String */
	public String getObject_schema()
	{
		return getValue( ITEM_OBJECT_SCHEMA );
	}

	public void setObject_schema( String object_schema1 )
	{
		setValue( ITEM_OBJECT_SCHEMA, object_schema1 );
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

	/* �Ƿ�ɲ�ѯ : String */
	public String getIs_query()
	{
		return getValue( ITEM_IS_QUERY );
	}

	public void setIs_query( String is_query1 )
	{
		setValue( ITEM_IS_QUERY, is_query1 );
	}

	/* �Ƿ�ɹ��� : String */
	public String getIs_trans()
	{
		return getValue( ITEM_IS_TRANS );
	}

	public void setIs_trans( String is_trans1 )
	{
		setValue( ITEM_IS_TRANS, is_trans1 );
	}

	/* �Ƿ������ : String */
	public String getIs_download()
	{
		return getValue( ITEM_IS_DOWNLOAD );
	}

	public void setIs_download( String is_download1 )
	{
		setValue( ITEM_IS_DOWNLOAD, is_download1 );
	}

	/* ���� : String */
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

