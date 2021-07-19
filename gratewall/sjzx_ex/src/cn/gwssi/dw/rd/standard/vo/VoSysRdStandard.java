package cn.gwssi.dw.rd.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_rd_standard]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysRdStandard extends VoBase
{
	private static final long serialVersionUID = 201205020221520006L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_RD_STANDARD_ID = "sys_rd_standard_id" ;	/* �������к� */
	public static final String ITEM_STANDARD_NAME = "standard_name" ;	/* ���� */
	public static final String ITEM_STANDARD_ISSUED_UNIT = "standard_issued_unit" ;	/* ��ҵע��� */
	public static final String ITEM_STANDARD_CATEGORY_NO = "standard_category_no" ;	/* ���������� */
	public static final String ITEM_STANDARD_ISSUED_TIME = "standard_issued_time" ;	/* ������ס�� */
	public static final String ITEM_FILE_NAME = "file_name" ;		/* ����������(������) */
	public static final String ITEM_FILE_PATH = "file_path" ;		/* ����������(������) */
	public static final String ITEM_STANDARD_RANGE = "standard_range" ;	/* ������λ */
	public static final String ITEM_STANDARD_CATEGORY = "standard_category" ;	/* �ֵ�ַ */
	public static final String ITEM_MEMO = "memo" ;					/* ��ϵ�绰 */
	public static final String ITEM_SORT = "sort" ;					/* ���� */
	public static final String ITEM_TIMESTAMP = "timestamp" ;		/* �����ǼǱ�� */
	
	/**
	 * ���캯��
	 */
	public VoSysRdStandard()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysRdStandard(DataBus value)
	{
		super(value);
	}
	
	/* �������к� : String */
	public String getSys_rd_standard_id()
	{
		return getValue( ITEM_SYS_RD_STANDARD_ID );
	}

	public void setSys_rd_standard_id( String sys_rd_standard_id1 )
	{
		setValue( ITEM_SYS_RD_STANDARD_ID, sys_rd_standard_id1 );
	}

	/* ���� : String */
	public String getStandard_name()
	{
		return getValue( ITEM_STANDARD_NAME );
	}

	public void setStandard_name( String standard_name1 )
	{
		setValue( ITEM_STANDARD_NAME, standard_name1 );
	}

	/* ��ҵע��� : String */
	public String getStandard_issued_unit()
	{
		return getValue( ITEM_STANDARD_ISSUED_UNIT );
	}

	public void setStandard_issued_unit( String standard_issued_unit1 )
	{
		setValue( ITEM_STANDARD_ISSUED_UNIT, standard_issued_unit1 );
	}

	/* ���������� : String */
	public String getStandard_category_no()
	{
		return getValue( ITEM_STANDARD_CATEGORY_NO );
	}

	public void setStandard_category_no( String standard_category_no1 )
	{
		setValue( ITEM_STANDARD_CATEGORY_NO, standard_category_no1 );
	}

	/* ������ס�� : String */
	public String getStandard_issued_time()
	{
		return getValue( ITEM_STANDARD_ISSUED_TIME );
	}

	public void setStandard_issued_time( String standard_issued_time1 )
	{
		setValue( ITEM_STANDARD_ISSUED_TIME, standard_issued_time1 );
	}

	/* ����������(������) : String */
	public String getFile_name()
	{
		return getValue( ITEM_FILE_NAME );
	}

	public void setFile_name( String file_name1 )
	{
		setValue( ITEM_FILE_NAME, file_name1 );
	}

	/* ����������(������) : String */
	public String getFile_path()
	{
		return getValue( ITEM_FILE_PATH );
	}

	public void setFile_path( String file_path1 )
	{
		setValue( ITEM_FILE_PATH, file_path1 );
	}

	/* ������λ : String */
	public String getStandard_range()
	{
		return getValue( ITEM_STANDARD_RANGE );
	}

	public void setStandard_range( String standard_range1 )
	{
		setValue( ITEM_STANDARD_RANGE, standard_range1 );
	}

	/* �ֵ�ַ : String */
	public String getStandard_category()
	{
		return getValue( ITEM_STANDARD_CATEGORY );
	}

	public void setStandard_category( String standard_category1 )
	{
		setValue( ITEM_STANDARD_CATEGORY, standard_category1 );
	}

	/* ��ϵ�绰 : String */
	public String getMemo()
	{
		return getValue( ITEM_MEMO );
	}

	public void setMemo( String memo1 )
	{
		setValue( ITEM_MEMO, memo1 );
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

	/* �����ǼǱ�� : String */
	public String getTimestamp()
	{
		return getValue( ITEM_TIMESTAMP );
	}

	public void setTimestamp( String timestamp1 )
	{
		setValue( ITEM_TIMESTAMP, timestamp1 );
	}

}

