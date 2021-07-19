package cn.gwssi.template.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[sys_msword]�����ݶ�����
 * @author Administrator
 *
 */
public class VoSysMswordPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 201207301446060004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_MSWORD_ID = "sys_msword_id" ;	/* wordģ��id */
	
	/**
	 * ���캯��
	 */
	public VoSysMswordPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoSysMswordPrimaryKey(DataBus value)
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

}

