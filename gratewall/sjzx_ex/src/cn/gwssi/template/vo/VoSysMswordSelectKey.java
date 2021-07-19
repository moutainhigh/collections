package cn.gwssi.template.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_msword]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysMswordSelectKey extends VoBase
{
	private static final long serialVersionUID = 201207301446060003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_MSWORD_ID = "sys_msword_id" ;	/* wordģ��id */
	public static final String ITEM_SYS_MSWORD_NAME = "sys_msword_name" ;	/* wordģ������ */
	
	/**
	 * ���캯��
	 */
	public VoSysMswordSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysMswordSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* wordģ��id : String */
	public String getSys_msword_id()
	{
		return getValue( ITEM_SYS_MSWORD_ID );
	}

	public void setSys_msword_id( String sys_msword_id1 )
	{
		setValue( ITEM_SYS_MSWORD_ID, sys_msword_id1 );
	}

	/* wordģ������ : String */
	public String getSys_msword_name()
	{
		return getValue( ITEM_SYS_MSWORD_NAME );
	}

	public void setSys_msword_name( String sys_msword_name1 )
	{
		setValue( ITEM_SYS_MSWORD_NAME, sys_msword_name1 );
	}

}

