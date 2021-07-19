package com.gwssi.standard.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[common_standard]�����ݶ�����
 * @author Administrator
 *
 */
public class VoCommonStandard extends VoBase
{
	private static final long serialVersionUID = 201304121530000002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_STANDARD_ID = "standard_id" ;	/* ��׼ID */
	public static final String ITEM_STANDARD_NAME = "standard_name" ;	/* ��׼���� */
	public static final String ITEM_STANDARD_TYPE = "standard_type" ;	/* ��׼���� */
	public static final String ITEM_SPECIFICATE_TYPE = "specificate_type" ;	/* �������� */
	public static final String ITEM_ISSUING_UNIT = "issuing_unit" ;	/* ������λ */
	public static final String ITEM_SPECIFICATE_NO = "specificate_no" ;	/* ���ͺ� */
	public static final String ITEM_SPECIFICATE_DESC = "specificate_desc" ;	/* �������� */
	public static final String ITEM_SPECIFICATE_STATUS = "specificate_status" ;	/* ����״̬ */
	public static final String ITEM_IS_MARKUP = "is_markup" ;		/* ���뼯 ��Ч ��Ч */
	public static final String ITEM_CREATOR_ID = "creator_id" ;		/* ������ID */
	public static final String ITEM_CREATED_TIME = "created_time" ;	/* ����ʱ�� */
	public static final String ITEM_LAST_MODIFY_ID = "last_modify_id" ;	/* ����޸���ID */
	public static final String ITEM_LAST_MODIFY_TIME = "last_modify_time" ;	/* ����޸�ʱ�� */
	public static final String ITEM_FJ_FK = "fj_fk" ;				/* ����id */
	public static final String ITEM_DELNAMES = "delNAMEs";			/* delNAMEs */
	public static final String ITEM_DELIDS = "delIDs";			/* delIDs */
	public static final String ITEM_FJMC = "fjmc" ;					/* �������� */
	/**
	 * ���캯��
	 */
	public VoCommonStandard()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoCommonStandard(DataBus value)
	{
		super(value);
	}
	
	/* ��׼ID : String */
	public String getStandard_id()
	{
		return getValue( ITEM_STANDARD_ID );
	}

	public void setStandard_id( String standard_id1 )
	{
		setValue( ITEM_STANDARD_ID, standard_id1 );
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

	/* ��׼���� : String */
	public String getStandard_type()
	{
		return getValue( ITEM_STANDARD_TYPE );
	}

	public void setStandard_type( String standard_type1 )
	{
		setValue( ITEM_STANDARD_TYPE, standard_type1 );
	}

	/* �������� : String */
	public String getSpecificate_type()
	{
		return getValue( ITEM_SPECIFICATE_TYPE );
	}

	public void setSpecificate_type( String specificate_type1 )
	{
		setValue( ITEM_SPECIFICATE_TYPE, specificate_type1 );
	}

	/* ������λ : String */
	public String getIssuing_unit()
	{
		return getValue( ITEM_ISSUING_UNIT );
	}

	public void setIssuing_unit( String issuing_unit1 )
	{
		setValue( ITEM_ISSUING_UNIT, issuing_unit1 );
	}

	/* ���ͺ� : String */
	public String getSpecificate_no()
	{
		return getValue( ITEM_SPECIFICATE_NO );
	}

	public void setSpecificate_no( String specificate_no1 )
	{
		setValue( ITEM_SPECIFICATE_NO, specificate_no1 );
	}

	/* �������� : String */
	public String getSpecificate_desc()
	{
		return getValue( ITEM_SPECIFICATE_DESC );
	}

	public void setSpecificate_desc( String specificate_desc1 )
	{
		setValue( ITEM_SPECIFICATE_DESC, specificate_desc1 );
	}

	/* ����״̬ : String */
	public String getSpecificate_status()
	{
		return getValue( ITEM_SPECIFICATE_STATUS );
	}

	public void setSpecificate_status( String specificate_status1 )
	{
		setValue( ITEM_SPECIFICATE_STATUS, specificate_status1 );
	}

	/* ���뼯 ��Ч ��Ч : String */
	public String getIs_markup()
	{
		return getValue( ITEM_IS_MARKUP );
	}

	public void setIs_markup( String is_markup1 )
	{
		setValue( ITEM_IS_MARKUP, is_markup1 );
	}

	/* ������ID : String */
	public String getCreator_id()
	{
		return getValue( ITEM_CREATOR_ID );
	}

	public void setCreator_id( String creator_id1 )
	{
		setValue( ITEM_CREATOR_ID, creator_id1 );
	}

	/* ����ʱ�� : String */
	public String getCreated_time()
	{
		return getValue( ITEM_CREATED_TIME );
	}

	public void setCreated_time( String created_time1 )
	{
		setValue( ITEM_CREATED_TIME, created_time1 );
	}

	/* ����޸���ID : String */
	public String getLast_modify_id()
	{
		return getValue( ITEM_LAST_MODIFY_ID );
	}

	public void setLast_modify_id( String last_modify_id1 )
	{
		setValue( ITEM_LAST_MODIFY_ID, last_modify_id1 );
	}

	/* ����޸�ʱ�� : String */
	public String getLast_modify_time()
	{
		return getValue( ITEM_LAST_MODIFY_TIME );
	}

	public void setLast_modify_time( String last_modify_time1 )
	{
		setValue( ITEM_LAST_MODIFY_TIME, last_modify_time1 );
	}
	/* ����id : String */
	public String getFj_fk()
	{
		return getValue( ITEM_FJ_FK );
	}

	public void setFj_fk( String fj_fk1 )
	{
		setValue( ITEM_FJ_FK, fj_fk1 );
	}

	/* �������� : String */
	public String getFjmc()
	{
		return getValue( ITEM_FJMC );
	}

	public void setFjmc( String fjmc1 )
	{
		setValue( ITEM_FJMC, fjmc1 );
	}

}

