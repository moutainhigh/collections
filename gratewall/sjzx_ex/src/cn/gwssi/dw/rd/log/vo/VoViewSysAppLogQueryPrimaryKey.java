package cn.gwssi.dw.rd.log.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[view_sys_app_log_query]�����ݶ�����
 * @author Administrator
 *
 */
public class VoViewSysAppLogQueryPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201208010743400004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_USERNAME = "username" ;			/* �û����� */
	
	/**
	 * ���캯��
	 */
	public VoViewSysAppLogQueryPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoViewSysAppLogQueryPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* �û����� : String */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

}

