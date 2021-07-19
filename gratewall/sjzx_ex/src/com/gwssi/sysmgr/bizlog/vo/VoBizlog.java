package com.gwssi.sysmgr.bizlog.vo;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.VoBase;

public class VoBizlog extends VoBase
{
	private static final long serialVersionUID = 200511110101002001L;

	/**
	 * 变量列表
	 */
	public static final String ITEM_FLOWNO = "flowno";			/* 流水号 */
	public static final String ITEM_REQFLOWNO = "reqflowno";			/* 请求流水号 */
	public static final String ITEM_USERNAME = "username";			/* 操作员代码 */
	public static final String ITEM_OPERNAME = "opername";			/* 操作员姓名 */
	public static final String ITEM_ORGID = "orgid";			/* 机构代码 */
	public static final String ITEM_ORGNAME = "orgname";			/* 机构名称 */
	public static final String ITEM_REGDATE = "regdate";			/* 操作日期 */
	public static final String ITEM_REGTIME = "regtime";			/* 操作时间 */
	public static final String ITEM_TRDCODE = "trdcode";			/* 交易码 */
	public static final String ITEM_TRDNAME = "trdname";			/* 交易名称 */
	public static final String ITEM_TRDTYPE = "trdtype";			/* 分类名称 */
	public static final String ITEM_ERRCODE = "errcode";			/* 错误代码 */
	public static final String ITEM_ERRDESC = "errdesc";			/* 错误描述 */
	public static final String ITEM_RESULTDATA = "resultdata";			/* 处理结果 */
	public static final String ITEM_OPERFROM = "operfrom";			/* 操作者来源平台 */

	public VoBizlog(DataBus value)
	{
		super(value);
	}

	public VoBizlog()
	{
		super();
	}

	/* 流水号 */
	public String getFlowno()
	{
		return getValue( ITEM_FLOWNO );
	}

	public void setFlowno( String flowno1 )
	{
		setValue( ITEM_FLOWNO, flowno1 );
	}

	/* 请求流水号 */
	public String getReqflowno()
	{
		return getValue( ITEM_REQFLOWNO );
	}

	public void setReqflowno( String reqflowno1 )
	{
		setValue( ITEM_REQFLOWNO, reqflowno1 );
	}

	/* 操作员代码 */
	public String getUsername()
	{
		return getValue( ITEM_USERNAME );
	}

	public void setUsername( String username1 )
	{
		setValue( ITEM_USERNAME, username1 );
	}

	/* 操作员姓名 */
	public String getOpername()
	{
		return getValue( ITEM_OPERNAME );
	}

	public void setOpername( String opername1 )
	{
		setValue( ITEM_OPERNAME, opername1 );
	}

	/* 机构代码 */
	public String getOrgid()
	{
		return getValue( ITEM_ORGID );
	}

	public void setOrgid( String orgid1 )
	{
		setValue( ITEM_ORGID, orgid1 );
	}

	/* 机构名称 */
	public String getOrgname()
	{
		return getValue( ITEM_ORGNAME );
	}

	public void setOrgname( String orgname1 )
	{
		setValue( ITEM_ORGNAME, orgname1 );
	}

	/* 操作日期 */
	public String getRegdate()
	{
		return getValue( ITEM_REGDATE );
	}

	public void setRegdate( String regdate1 )
	{
		setValue( ITEM_REGDATE, regdate1 );
	}

	/* 操作时间 */
	public String getRegtime()
	{
		return getValue( ITEM_REGTIME );
	}

	public void setRegtime( String regtime1 )
	{
		setValue( ITEM_REGTIME, regtime1 );
	}

	/* 交易码 */
	public String getTrdcode()
	{
		return getValue( ITEM_TRDCODE );
	}

	public void setTrdcode( String trdcode1 )
	{
		setValue( ITEM_TRDCODE, trdcode1 );
	}

	/* 交易名称 */
	public String getTrdname()
	{
		return getValue( ITEM_TRDNAME );
	}

	public void setTrdname( String trdname1 )
	{
		setValue( ITEM_TRDNAME, trdname1 );
	}

	/* 分类名称 */
	public String getTrdtype()
	{
		return getValue( ITEM_TRDTYPE );
	}

	public void setTrdtype( String trdtype1 )
	{
		setValue( ITEM_TRDTYPE, trdtype1 );
	}

	/* 错误代码 */
	public String getErrcode()
	{
		return getValue( ITEM_ERRCODE );
	}

	public void setErrcode( String errcode1 )
	{
		setValue( ITEM_ERRCODE, errcode1 );
	}

	/* 错误描述 */
	public String getErrdesc()
	{
		return getValue( ITEM_ERRDESC );
	}

	public void setErrdesc( String errdesc1 )
	{
		setValue( ITEM_ERRDESC, errdesc1 );
	}

	/* 处理结果 */
	public String getResultdata()
	{
		return getValue( ITEM_RESULTDATA );
	}

	public void setResultdata( String resultdata1 )
	{
		setValue( ITEM_RESULTDATA, resultdata1 );
	}
	
	/* 操作者来源平台 */
	public void setOperfrom(String operfrom){
		setValue(ITEM_OPERFROM, operfrom);
	}

}

