package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard_dataelement]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandardDataelement extends VoBase
{
	private static final long serialVersionUID = 201205022055520006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDARD_DATAELEMENT_ID = "sys_rd_standard_dataelement_id" ;	/* ��������ԪID */
	public static final String ITEM_STANDARD_CATEGORY = "standard_category" ;	/* �淶���� */
	public static final String ITEM_IDENTIFIER = "identifier" ;		/* ��ʶ�� */
	public static final String ITEM_CN_NAME = "cn_name" ;			/* �������� */
	public static final String ITEM_EN_NAME = "en_name" ;			/* Ӣ������ */
	public static final String ITEM_COLUMN_NANE = "column_nane" ;	/* �ֶ����� */
	public static final String ITEM_DATA_TYPE = "data_type" ;		/* �������� */
	public static final String ITEM_DATA_LENGTH = "data_length" ;	/* ���ݳ��� */
	public static final String ITEM_DATA_FORMAT = "data_format" ;	/* ���ݸ�ʽ */
	public static final String ITEM_VALUE_DOMAIN = "value_domain" ;	/* ֵ�� */
	public static final String ITEM_JC_STANDAR_CODEINDEX = "jc_standar_codeindex" ;	/* �������뼯��ʶ�� */
	public static final String ITEM_REPRESENTATION = "representation" ;	/* ��ʾ */
	public static final String ITEM_UNIT = "unit" ;					/* ������λ */
	public static final String ITEM_SYNONYMS = "synonyms" ;			/* ͬ��� */
	public static final String ITEM_VERSION = "version" ;			/* �汾 */
	public static final String ITEM_MEMO = "memo" ;					/* ��ע */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandardDataelement()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandardDataelement(DataBus value)
	{
		super(value);
	}
	
	/* ��������ԪID : String */
	public String getSys_rd_standard_dataelement_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_DATAELEMENT_ID );
	}

	public void setSys_rd_standard_dataelement_id( String sys_rd_standard_dataelement_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_DATAELEMENT_ID, sys_rd_standard_dataelement_id1 );
	}

	/* �淶���� : String */
	public String getStandard_category()
	{
		return getValue( ITEM_STANDARD_CATEGORY );
	}

	public void setStandard_category( String standard_category1 )
	{
		setValue( ITEM_STANDARD_CATEGORY, standard_category1 );
	}

	/* ��ʶ�� : String */
	public String getIdentifier()
	{
		return getValue( ITEM_IDENTIFIER );
	}

	public void setIdentifier( String identifier1 )
	{
		setValue( ITEM_IDENTIFIER, identifier1 );
	}

	/* �������� : String */
	public String getCn_name()
	{
		return getValue( ITEM_CN_NAME );
	}

	public void setCn_name( String cn_name1 )
	{
		setValue( ITEM_CN_NAME, cn_name1 );
	}

	/* Ӣ������ : String */
	public String getEn_name()
	{
		return getValue( ITEM_EN_NAME );
	}

	public void setEn_name( String en_name1 )
	{
		setValue( ITEM_EN_NAME, en_name1 );
	}

	/* �ֶ����� : String */
	public String getColumn_nane()
	{
		return getValue( ITEM_COLUMN_NANE );
	}

	public void setColumn_nane( String column_nane1 )
	{
		setValue( ITEM_COLUMN_NANE, column_nane1 );
	}

	/* �������� : String */
	public String getData_type()
	{
		return getValue( ITEM_DATA_TYPE );
	}

	public void setData_type( String data_type1 )
	{
		setValue( ITEM_DATA_TYPE, data_type1 );
	}

	/* ���ݳ��� : String */
	public String getData_length()
	{
		return getValue( ITEM_DATA_LENGTH );
	}

	public void setData_length( String data_length1 )
	{
		setValue( ITEM_DATA_LENGTH, data_length1 );
	}

	/* ���ݸ�ʽ : String */
	public String getData_format()
	{
		return getValue( ITEM_DATA_FORMAT );
	}

	public void setData_format( String data_format1 )
	{
		setValue( ITEM_DATA_FORMAT, data_format1 );
	}

	/* ֵ�� : String */
	public String getValue_domain()
	{
		return getValue( ITEM_VALUE_DOMAIN );
	}

	public void setValue_domain( String value_domain1 )
	{
		setValue( ITEM_VALUE_DOMAIN, value_domain1 );
	}

	/* �������뼯��ʶ�� : String */
	public String getJc_standar_codeindex()
	{
		return getValue( ITEM_JC_STANDAR_CODEINDEX );
	}

	public void setJc_standar_codeindex( String jc_standar_codeindex1 )
	{
		setValue( ITEM_JC_STANDAR_CODEINDEX, jc_standar_codeindex1 );
	}

	/* ��ʾ : String */
	public String getRepresentation()
	{
		return getValue( ITEM_REPRESENTATION );
	}

	public void setRepresentation( String representation1 )
	{
		setValue( ITEM_REPRESENTATION, representation1 );
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

	/* ͬ��� : String */
	public String getSynonyms()
	{
		return getValue( ITEM_SYNONYMS );
	}

	public void setSynonyms( String synonyms1 )
	{
		setValue( ITEM_SYNONYMS, synonyms1 );
	}

	/* �汾 : String */
	public String getVersion()
	{
		return getValue( ITEM_VERSION );
	}

	public void setVersion( String version1 )
	{
		setValue( ITEM_VERSION, version1 );
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

}

