package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[view_func_name_log]�����ݶ�����
 * @author Administrator
 *
 */
public class VoViewFuncNameLog extends VoBase
{
	private static final long serialVersionUID = 201208221639540002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_FIRST_FUNC_NAME = "first_func_name" ;	/* ���ܴ��� */
	public static final String ITEM_SECOND_FUNC_NAME = "second_func_name" ;	/* ����С�� */
	
	/**
	 * ���캯��
	 */
	public VoViewFuncNameLog()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoViewFuncNameLog(DataBus value)
	{
		super(value);
	}
	
	/* ���ܴ��� : String */
	public String getFirst_func_name()
	{
		return getValue( ITEM_FIRST_FUNC_NAME );
	}

	public void setFirst_func_name( String first_func_name1 )
	{
		setValue( ITEM_FIRST_FUNC_NAME, first_func_name1 );
	}

	/* ����С�� : String */
	public String getSecond_func_name()
	{
		return getValue( ITEM_SECOND_FUNC_NAME );
	}

	public void setSecond_func_name( String second_func_name1 )
	{
		setValue( ITEM_SECOND_FUNC_NAME, second_func_name1 );
	}

}

