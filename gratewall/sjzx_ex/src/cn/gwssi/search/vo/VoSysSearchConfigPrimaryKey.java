package cn.gwssi.search.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_search_config]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysSearchConfigPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201208211741000004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_SEARCH_CONFIG_ID = "sys_search_config_id" ;	/* ��������ID */
	
	/**
	 * ���캯��
	 */
	public VoSysSearchConfigPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysSearchConfigPrimaryKey(DataBus value)
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

}

