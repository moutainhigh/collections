package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[opb_subject]�����ݶ�����
 * @author Administrator
 *
 */
public class VoOpbSubjectPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200805300950400004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SUBJ_ID = "subj_id" ;			/* �ļ������� */
	
	/**
	 * ���캯��
	 */
	public VoOpbSubjectPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoOpbSubjectPrimaryKey(DataBus value)
	{
		super(value);
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

