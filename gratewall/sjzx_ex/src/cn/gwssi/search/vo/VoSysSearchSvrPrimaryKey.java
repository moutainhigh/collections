package cn.gwssi.search.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_search_svr]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSearchSvrPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201210091825470004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SEARCH_SVR_ID = "sys_search_svr_id" ;	/* ��������ID */
	
	/**
	 * ���캯��
	 */
	public VoSysSearchSvrPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSearchSvrPrimaryKey(DataBus value)
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

}

