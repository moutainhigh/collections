package com.gwssi.dw.aic.bj.newmon.tm.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

/**
 * ���ݱ�[mon_tm_bas_info]�����ݶ�����
 * @author Administrator
 *
 */
public class VoMonTmBasInfoPrimaryKey extends VoBase
{
	private static final long serialVersionUID = 200811171515250004L;
	
	/**
	 * �����б�
	 */
	public static final String ITEM_MON_TM_BAS_INFO_ID = "mon_tm_bas_info_id" ;	/* mon_tm_bas_info_id */
	
	/**
	 * ���캯��
	 */
	public VoMonTmBasInfoPrimaryKey()
	{
		super();
	}
	
	/**
	 * ���캯��
	 * @param value ���ݽڵ�
	 */
	public VoMonTmBasInfoPrimaryKey(DataBus value)
	{
		super(value);
	}
	
	/* mon_tm_bas_info_id : String */
	public String getMon_tm_bas_info_id()
	{
		return getValue( ITEM_MON_TM_BAS_INFO_ID );
	}

	public void setMon_tm_bas_info_id( String mon_tm_bas_info_id1 )
	{
		setValue( ITEM_MON_TM_BAS_INFO_ID, mon_tm_bas_info_id1 );
	}

}

