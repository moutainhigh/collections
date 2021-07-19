package com.gwssi.file.manage.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[xt_ccgl_wjlb]�����ݶ�����
 * @author Administrator
 *
 */
public class VoXtCcglWjlbSelectKey extends VoBase
{
	private static final long serialVersionUID = 201303271601110003L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_CCLBMC = "cclbmc" ;				/* ����������� */
	public static final String ITEM_LBMCBB = "lbmcbb" ;				/* ������ư汾 */
	public static final String ITEM_ZT = "zt" ;						/* ״̬ */
	
	/**
	 * ���캯��
	 */
	public VoXtCcglWjlbSelectKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoXtCcglWjlbSelectKey(DataBus value)
	{
		super(value);
	}
	
	/* ����������� : String */
	public String getCclbmc()
	{
		return getValue( ITEM_CCLBMC );
	}

	public void setCclbmc( String cclbmc1 )
	{
		setValue( ITEM_CCLBMC, cclbmc1 );
	}

	/* ������ư汾 : String */
	public String getLbmcbb()
	{
		return getValue( ITEM_LBMCBB );
	}

	public void setLbmcbb( String lbmcbb1 )
	{
		setValue( ITEM_LBMCBB, lbmcbb1 );
	}

	/* ״̬ : String */
	public String getZt()
	{
		return getValue( ITEM_ZT );
	}

	public void setZt( String zt1 )
	{
		setValue( ITEM_ZT, zt1 );
	}

}

