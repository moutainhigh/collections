package com.gwssi.dw.runmgr.etl.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[opb_subject]�����ݶ�����
 * @author Administrator
 *
 */
public class VoOpbSubjectSelectKey extends VoBase
{
	private static final long serialVersionUID = 200805300950400003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SUBJ_NAME = "subj_name" ;		/* �ļ������� */
	
	/**
	 * ���캯��
	 */
	public VoOpbSubjectSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoOpbSubjectSelectKey(DataBus value)
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

}

