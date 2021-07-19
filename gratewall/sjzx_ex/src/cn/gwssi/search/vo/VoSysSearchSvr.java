package cn.gwssi.search.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_search_svr]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSearchSvr extends VoBase
{
	private static final long serialVersionUID = 201210091825470002L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SEARCH_SVR_ID = "sys_search_svr_id" ;	/* ��������ID */
	public static final String ITEM_SVR_NAME = "svr_name" ;			/* �������� */
	public static final String ITEM_SVR_DB = "svr_db" ;				/* �������ݿ� */
	public static final String ITEM_SVR_QUERY = "svr_query" ;		/* ������ѯ�� */
	public static final String ITEM_SVR_TEMPLATE = "svr_template" ;	/* ��������ģ�� */
	
	/**
	 * ���캯��
	 */
	public VoSysSearchSvr()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSearchSvr(DataBus value)
	{
		super(value);
	}
	
	/* ��������ID : String */
	public String getSys_search_svr_id()
	{
		return getValue( ITEM_SYS_SEARCH_SVR_ID );
	}

	public void setSys_search_svr_id( String sys_search_svr_id1 )
	{
		setValue( ITEM_SYS_SEARCH_SVR_ID, sys_search_svr_id1 );
	}

	/* �������� : String */
	public String getSvr_name()
	{
		return getValue( ITEM_SVR_NAME );
	}

	public void setSvr_name( String svr_name1 )
	{
		setValue( ITEM_SVR_NAME, svr_name1 );
	}

	/* �������ݿ� : String */
	public String getSvr_db()
	{
		return getValue( ITEM_SVR_DB );
	}

	public void setSvr_db( String svr_db1 )
	{
		setValue( ITEM_SVR_DB, svr_db1 );
	}

	/* ������ѯ�� : String */
	public String getSvr_query()
	{
		return getValue( ITEM_SVR_QUERY );
	}

	public void setSvr_query( String svr_query1 )
	{
		setValue( ITEM_SVR_QUERY, svr_query1 );
	}

	/* ��������ģ�� : String */
	public String getSvr_template()
	{
		return getValue( ITEM_SVR_TEMPLATE );
	}

	public void setSvr_template( String svr_template1 )
	{
		setValue( ITEM_SVR_TEMPLATE, svr_template1 );
	}

}

