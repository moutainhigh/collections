package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[opb_subject]�����ݶ�����
 * @author Administrator
 *
 */
public class VoOpbSubject extends VoBase
{
	private static final long serialVersionUID = 200805300950390002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SUBJ_NAME = "subj_name" ;		/* �ļ������� */
	public static final String ITEM_SUBJ_DESC = "subj_desc" ;		/* �ļ������� */
	public static final String ITEM_SUBJ_ID = "subj_id" ;			/* �ļ������� */
	
	/**
	 * ���캯��
	 */
	public VoOpbSubject()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoOpbSubject(DataBus value)
	{
		super(value);
	}
	
	/* �ļ������� : String */
	public String getSubj_name()
	{
		return getValue( ITEM_SUBJ_NAME );
	}

	public void setSubj_name( String subj_name1 )
	{
		setValue( ITEM_SUBJ_NAME, subj_name1 );
	}

	/* �ļ������� : String */
	public String getSubj_desc()
	{
		return getValue( ITEM_SUBJ_DESC );
	}

	public void setSubj_desc( String subj_desc1 )
	{
		setValue( ITEM_SUBJ_DESC, subj_desc1 );
	}

	/* �ļ������� : String */
	public String getSubj_id()
	{
		return getValue( ITEM_SUBJ_ID );
	}

	public void setSubj_id( String subj_id1 )
	{
		setValue( ITEM_SUBJ_ID, subj_id1 );
	}

}

