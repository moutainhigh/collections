package cn.gwssi.template.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoMsWord extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_SYS_MSWORD_ID = "sys_msword_id";			/* wordģ��id */
	public static final String ITEM_SYS_MSWORD_NAME = "sys_msword_name";			/* wordģ������ */
	public static final String ITEM_SYS_MSWORD_BOOKMARKS = "sys_msword_bookmarks";			/* ��ǩ�б� */
	public static final String ITEM_SYS_MSWORD_TEMPLATE = "sys_msword_template";			/* ģ���ļ� */
	public static final String ITEM_SYS_MSWORD_DESP = "sys_msword_desp";			/* ģ������ */

	public VoMsWord(DataBus value)
	{
		super(value);
	}

	public VoMsWord()
	{
		super();
	}

	/* wordģ��id */
	public String getSys_msword_id()
	{
		return getValue( ITEM_SYS_MSWORD_ID );
	}

	public void setSys_msword_id( String sys_msword_id1 )
	{
		setValue( ITEM_SYS_MSWORD_ID, sys_msword_id1 );
	}

	/* wordģ������ */
	public String getSys_msword_name()
	{
		return getValue( ITEM_SYS_MSWORD_NAME );
	}

	public void setSys_msword_name( String sys_msword_name1 )
	{
		setValue( ITEM_SYS_MSWORD_NAME, sys_msword_name1 );
	}

	/* ��ǩ�б� */
	public String getSys_msword_bookmarks()
	{
		return getValue( ITEM_SYS_MSWORD_BOOKMARKS );
	}

	public void setSys_msword_bookmarks( String sys_msword_bookmarks1 )
	{
		setValue( ITEM_SYS_MSWORD_BOOKMARKS, sys_msword_bookmarks1 );
	}

	/* ģ���ļ� */
	public String getSys_msword_template()
	{
		return getValue( ITEM_SYS_MSWORD_TEMPLATE );
	}

	public void setSys_msword_template( String sys_msword_template1 )
	{
		setValue( ITEM_SYS_MSWORD_TEMPLATE, sys_msword_template1 );
	}

	/* ģ������ */
	public String getSys_msword_desp()
	{
		return getValue( ITEM_SYS_MSWORD_DESP );
	}

	public void setSys_msword_desp( String sys_msword_desp1 )
	{
		setValue( ITEM_SYS_MSWORD_DESP, sys_msword_desp1 );
	}

}

