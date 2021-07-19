package cn.gwssi.dw.rd.metadata.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_column]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdColumn extends VoBase
{
	private static final long serialVersionUID = 201205071133200010L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_COLUMN_ID = "sys_rd_column_id" ;	/* �ֶ�ID */
	public static final String ITEM_SYS_RD_TABLE_ID = "sys_rd_table_id" ;	/* �����ID */
	public static final String ITEM_SYS_RD_DATA_SOURCE_ID = "sys_rd_data_source_id" ;	/* ����ԴID */
	public static final String ITEM_TABLE_CODE = "table_code" ;		/* ������� */
	public static final String ITEM_COLUMN_CODE = "column_code" ;	/* �ֶ��� */
	public static final String ITEM_COLUMN_NAME = "column_name" ;	/* �ֶ������� */
	public static final String ITEM_COLUMN_TYPE = "column_type" ;	/* �ֶ����� */
	public static final String ITEM_COLUMN_LENGTH = "column_length" ;	/* �ֶγ��� */
	public static final String ITEM_ALIA_NAME = "alia_name" ;		/* �ֶα��� */
	public static final String ITEM_CODE_NAME = "code_name" ;		/* ϵͳ������ */
	public static final String ITEM_STANDARD_STATUS = "standard_status" ;	/* �ϱ�״̬ */
	public static final String ITEM_COLUMN_CODEINDEX = "column_codeindex" ;	/* ϵͳ���뼯���� */
	public static final String ITEM_IS_EXTRA_COL = "is_extra_col" ;	/* �Ƿ�Ϊ�ز��ֶ� */
	public static final String ITEM_SYS_COLUMN_TYPE = "sys_column_type" ;	/* �ֶ����� */
	public static final String ITEM_JC_DATA_ELEMENT = "jc_data_element" ;	/* ��������Ԫ */
	public static final String ITEM_JC_DATA_INDEX = "jc_data_index" ;	/* �������뼯 */
	public static final String ITEM_DOMAIN_VALUE = "domain_value" ;	/* ֵ�� */
	public static final String ITEM_UNIT = "unit" ;					/* ������λ */
	public static final String ITEM_CLAIM_OPERATOR = "claim_operator" ;	/* ������ */
	public static final String ITEM_CLAIM_DATE = "claim_date" ;		/* ����ʱ�� */
	public static final String ITEM_DEFAULT_VALUE = "default_value" ;	/* ȱʡֵ */
	public static final String ITEM_IS_NULL = "is_null" ;			/* �Ƿ�Ϊ�� */
	public static final String ITEM_IS_PRIMARY_KEY = "is_primary_key" ;	/* �Ƿ����� */
	public static final String ITEM_IS_INDEX = "is_index" ;			/* �Ƿ����� */
	public static final String ITEM_CHANGED_STATUS = "changed_status" ;	/* �ֶα仯״̬ */
	public static final String ITEM_SYNC_SIGN = "sync_sign" ;		/* ͬ����ʶ�� */
	public static final String ITEM_DESCRIPTION = "description" ;	/* ˵�� */
	public static final String ITEM_COLUMN_LEVEL = "column_level" ;	/* �ֶμ��� */
	public static final String ITEM_BASIC_FLAG = "basic_flag" ;		/* �����ֶα�ʶ */
	public static final String ITEM_DATA_FROM = "data_from" ;		/* ������Դ */
	public static final String ITEM_DATA_FROM_COLUMN = "data_from_column" ;	/* ������Դ�ֶ� */
	public static final String ITEM_TRANSFORM_DESP = "transform_desp" ;	/* ����ת������ */
	public static final String ITEM_SORT = "sort" ;					/* ���� */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* ʱ��� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdColumn()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdColumn(DataBus value)
	{
		super(value);
	}
	
	/* �ֶ�ID : String */
	public String getSys_rd_column_id()
	{
		return getValue( ITEM_SYS_RD_COLUMN_ID );
	}

	public void setSys_rd_column_id( String sys_rd_column_id1 )
	{
		setValue( ITEM_SYS_RD_COLUMN_ID, sys_rd_column_id1 );
	}

	/* �����ID : String */
	public String getSys_rd_table_id()
	{
		return getValue( ITEM_SYS_RD_TABLE_ID );
	}

	public void setSys_rd_table_id( String sys_rd_table_id1 )
	{
		setValue( ITEM_SYS_RD_TABLE_ID, sys_rd_table_id1 );
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

	/* ������� : String */
	public String getTable_code()
	{
		return getValue( ITEM_TABLE_CODE );
	}

	public void setTable_code( String table_code1 )
	{
		setValue( ITEM_TABLE_CODE, table_code1 );
	}

	/* �ֶ��� : String */
	public String getColumn_code()
	{
		return getValue( ITEM_COLUMN_CODE );
	}

	public void setColumn_code( String column_code1 )
	{
		setValue( ITEM_COLUMN_CODE, column_code1 );
	}

	/* �ֶ������� : String */
	public String getColumn_name()
	{
		return getValue( ITEM_COLUMN_NAME );
	}

	public void setColumn_name( String column_name1 )
	{
		setValue( ITEM_COLUMN_NAME, column_name1 );
	}

	/* �ֶ����� : String */
	public String getColumn_type()
	{
		return getValue( ITEM_COLUMN_TYPE );
	}

	public void setColumn_type( String column_type1 )
	{
		setValue( ITEM_COLUMN_TYPE, column_type1 );
	}

	/* �ֶγ��� : String */
	public String getColumn_length()
	{
		return getValue( ITEM_COLUMN_LENGTH );
	}

	public void setColumn_length( String column_length1 )
	{
		setValue( ITEM_COLUMN_LENGTH, column_length1 );
	}

	/* �ֶα��� : String */
	public String getAlia_name()
	{
		return getValue( ITEM_ALIA_NAME );
	}

	public void setAlia_name( String alia_name1 )
	{
		setValue( ITEM_ALIA_NAME, alia_name1 );
	}

	/* ϵͳ������ : String */
	public String getCode_name()
	{
		return getValue( ITEM_CODE_NAME );
	}

	public void setCode_name( String code_name1 )
	{
		setValue( ITEM_CODE_NAME, code_name1 );
	}

	/* �ϱ�״̬ : String */
	public String getStandard_status()
	{
		return getValue( ITEM_STANDARD_STATUS );
	}

	public void setStandard_status( String standard_status1 )
	{
		setValue( ITEM_STANDARD_STATUS, standard_status1 );
	}

	/* ϵͳ���뼯���� : String */
	public String getColumn_codeindex()
	{
		return getValue( ITEM_COLUMN_CODEINDEX );
	}

	public void setColumn_codeindex( String column_codeindex1 )
	{
		setValue( ITEM_COLUMN_CODEINDEX, column_codeindex1 );
	}

	/* �Ƿ�Ϊ�ز��ֶ� : String */
	public String getIs_extra_col()
	{
		return getValue( ITEM_IS_EXTRA_COL );
	}

	public void setIs_extra_col( String is_extra_col1 )
	{
		setValue( ITEM_IS_EXTRA_COL, is_extra_col1 );
	}

	/* �ֶ����� : String */
	public String getSys_column_type()
	{
		return getValue( ITEM_SYS_COLUMN_TYPE );
	}

	public void setSys_column_type( String sys_column_type1 )
	{
		setValue( ITEM_SYS_COLUMN_TYPE, sys_column_type1 );
	}

	/* ��������Ԫ : String */
	public String getJc_data_element()
	{
		return getValue( ITEM_JC_DATA_ELEMENT );
	}

	public void setJc_data_element( String jc_data_element1 )
	{
		setValue( ITEM_JC_DATA_ELEMENT, jc_data_element1 );
	}

	/* �������뼯 : String */
	public String getJc_data_index()
	{
		return getValue( ITEM_JC_DATA_INDEX );
	}

	public void setJc_data_index( String jc_data_index1 )
	{
		setValue( ITEM_JC_DATA_INDEX, jc_data_index1 );
	}

	/* ֵ�� : String */
	public String getDomain_value()
	{
		return getValue( ITEM_DOMAIN_VALUE );
	}

	public void setDomain_value( String domain_value1 )
	{
		setValue( ITEM_DOMAIN_VALUE, domain_value1 );
	}

	/* ������λ : String */
	public String getUnit()
	{
		return getValue( ITEM_UNIT );
	}

	public void setUnit( String unit1 )
	{
		setValue( ITEM_UNIT, unit1 );
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

	/* ����ʱ�� : String */
	public String getClaim_date()
	{
		return getValue( ITEM_CLAIM_DATE );
	}

	public void setClaim_date( String claim_date1 )
	{
		setValue( ITEM_CLAIM_DATE, claim_date1 );
	}

	/* ȱʡֵ : String */
	public String getDefault_value()
	{
		return getValue( ITEM_DEFAULT_VALUE );
	}

	public void setDefault_value( String default_value1 )
	{
		setValue( ITEM_DEFAULT_VALUE, default_value1 );
	}

	/* �Ƿ�Ϊ�� : String */
	public String getIs_null()
	{
		return getValue( ITEM_IS_NULL );
	}

	public void setIs_null( String is_null1 )
	{
		setValue( ITEM_IS_NULL, is_null1 );
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

	/* �ֶα仯״̬ : String */
	public String getChanged_status()
	{
		return getValue( ITEM_CHANGED_STATUS );
	}

	public void setChanged_status( String changed_status1 )
	{
		setValue( ITEM_CHANGED_STATUS, changed_status1 );
	}

	/* ͬ����ʶ�� : String */
	public String getSync_sign()
	{
		return getValue( ITEM_SYNC_SIGN );
	}

	public void setSync_sign( String sync_sign1 )
	{
		setValue( ITEM_SYNC_SIGN, sync_sign1 );
	}

	/* ˵�� : String */
	public String getDescription()
	{
		return getValue( ITEM_DESCRIPTION );
	}

	public void setDescription( String description1 )
	{
		setValue( ITEM_DESCRIPTION, description1 );
	}

	/* �ֶμ��� : String */
	public String getColumn_level()
	{
		return getValue( ITEM_COLUMN_LEVEL );
	}

	public void setColumn_level( String column_level1 )
	{
		setValue( ITEM_COLUMN_LEVEL, column_level1 );
	}

	/* �����ֶα�ʶ : String */
	public String getBasic_flag()
	{
		return getValue( ITEM_BASIC_FLAG );
	}

	public void setBasic_flag( String basic_flag1 )
	{
		setValue( ITEM_BASIC_FLAG, basic_flag1 );
	}

	/* ������Դ : String */
	public String getData_from()
	{
		return getValue( ITEM_DATA_FROM );
	}

	public void setData_from( String data_from1 )
	{
		setValue( ITEM_DATA_FROM, data_from1 );
	}

	/* ������Դ�ֶ� : String */
	public String getData_from_column()
	{
		return getValue( ITEM_DATA_FROM_COLUMN );
	}

	public void setData_from_column( String data_from_column1 )
	{
		setValue( ITEM_DATA_FROM_COLUMN, data_from_column1 );
	}

	/* ����ת������ : String */
	public String getTransform_desp()
	{
		return getValue( ITEM_TRANSFORM_DESP );
	}

	public void setTransform_desp( String transform_desp1 )
	{
		setValue( ITEM_TRANSFORM_DESP, transform_desp1 );
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

