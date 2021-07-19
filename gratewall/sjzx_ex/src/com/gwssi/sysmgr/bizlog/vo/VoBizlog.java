package com.gwssi.sysmgr.bizlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoBizlog extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * �����б�
	 */
	public static final String ITEM_FLOWNO = "flowno";			/* ��ˮ�� */
	public static final String ITEM_REQFLOWNO = "reqflowno";			/* ������ˮ�� */
	public static final String ITEM_USERNAME = "username";			/* ����Ա���� */
	public static final String ITEM_OPERNAME = "opername";			/* ����Ա���� */
	public static final String ITEM_ORGID = "orgid";			/* �������� */
	public static final String ITEM_ORGNAME = "orgname";			/* �������� */
	public static final String ITEM_REGDATE = "regdate";			/* �������� */
	public static final String ITEM_REGTIME = "regtime";			/* ����ʱ�� */
	public static final String ITEM_TRDCODE = "trdcode";			/* ������ */
	public static final String ITEM_TRDNAME = "trdname";			/* �������� */
	public static final String ITEM_TRDTYPE = "trdtype";			/* �������� */
	public static final String ITEM_ERRCODE = "errcode";			/* ������� */
	public static final String ITEM_ERRDESC = "errdesc";			/* �������� */
	public static final String ITEM_RESULTDATA = "resultdata";			/* ������ */
	public static final String ITEM_OPERFROM = "operfrom";			/* ��������Դƽ̨ */

	public VoBizlog(DataBus value)
	{
		super(value);
	}

	public VoBizlog()
	{
		super();
	}

	/* ��ˮ�� */
	public String getFlowno()
	{
		return getValue( ITEM_FLOWNO );
	}

	public void setFlowno( String flowno1 )
	{
		setValue( ITEM_FLOWNO, flowno1 );
	}

	/* ������ˮ�� */
	public String getReqflowno()
	{
		return getValue( ITEM_REQFLOWNO );
	}

	public void setReqflowno( String reqflowno1 )
	{
		setValue( ITEM_REQFLOWNO, reqflowno1 );
	}

	/* ����Ա���� */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

	/* ����Ա���� */
	public String getOpername()
	{
		return getValue( ITEM_OPERNAME );
	}

	public void setOpername( String opername1 )
	{
		setValue( ITEM_OPERNAME, opername1 );
	}

	/* �������� */
	public String getOrgid()
	{
		return getValue( ITEM_ORGID );
	}

	public void setOrgid( String orgid1 )
	{
		setValue( ITEM_ORGID, orgid1 );
	}

	/* �������� */
	public String getOrgname()
	{
		return getValue( ITEM_ORGNAME );
	}

	public void setOrgname( String orgname1 )
	{
		setValue( ITEM_ORGNAME, orgname1 );
	}

	/* �������� */
	public String getRegdate()
	{
		return getValue( ITEM_REGDATE );
	}

	public void setRegdate( String regdate1 )
	{
		setValue( ITEM_REGDATE, regdate1 );
	}

	/* ����ʱ�� */
	public String getRegtime()
	{
		return getValue( ITEM_REGTIME );
	}

	public void setRegtime( String regtime1 )
	{
		setValue( ITEM_REGTIME, regtime1 );
	}

	/* ������ */
	public String getTrdcode()
	{
		return getValue( ITEM_TRDCODE );
	}

	public void setTrdcode( String trdcode1 )
	{
		setValue( ITEM_TRDCODE, trdcode1 );
	}

	/* �������� */
	public String getTrdname()
	{
		return getValue( ITEM_TRDNAME );
	}

	public void setTrdname( String trdname1 )
	{
		setValue( ITEM_TRDNAME, trdname1 );
	}

	/* �������� */
	public String getTrdtype()
	{
		return getValue( ITEM_TRDTYPE );
	}

	public void setTrdtype( String trdtype1 )
	{
		setValue( ITEM_TRDTYPE, trdtype1 );
	}

	/* ������� */
	public String getErrcode()
	{
		return getValue( ITEM_ERRCODE );
	}

	public void setErrcode( String errcode1 )
	{
		setValue( ITEM_ERRCODE, errcode1 );
	}

	/* �������� */
	public String getErrdesc()
	{
		return getValue( ITEM_ERRDESC );
	}

	public void setErrdesc( String errdesc1 )
	{
		setValue( ITEM_ERRDESC, errdesc1 );
	}

	/* ������ */
	public String getResultdata()
	{
		return getValue( ITEM_RESULTDATA );
	}

	public void setResultdata( String resultdata1 )
	{
		setValue( ITEM_RESULTDATA, resultdata1 );
	}
	
	/* ��������Դƽ̨ */
	public void setOperfrom(String operfrom){
		setValue(ITEM_OPERFROM, operfrom);
	}

}

