package cn.gwssi.search.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_search_config]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSearchConfigSelectKey extends VoBase
{
	private static final long serialVersionUID = 201208211741000003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SEARCH_CONFIG_ID = "sys_search_config_id" ;	/* ��������ID */
	public static final String ITEM_SYS_SVR_USER_ID = "sys_svr_user_id" ;	/* �û�ID */
	
	/**
	 * ���캯��
	 */
	public VoSysSearchConfigSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSearchConfigSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ��������ID : String */
	public String getSys_search_config_id()
	{
		return getValue( ITEM_SYS_SEARCH_CONFIG_ID );
	}

	public void setSys_search_config_id( String sys_search_config_id1 )
	{
		setValue( ITEM_SYS_SEARCH_CONFIG_ID, sys_search_config_id1 );
	}

	/* �û�ID : String */
	public String getSys_svr_user_id()
	{
		return getValue( ITEM_SYS_SVR_USER_ID );
	}

	public void setSys_svr_user_id( String sys_svr_user_id1 )
	{
		setValue( ITEM_SYS_SVR_USER_ID, sys_svr_user_id1 );
	}

}

