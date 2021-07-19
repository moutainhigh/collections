package com.gwssi.dw.aic.bj.code;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.param.ParamHelp;

public class JCDMCode extends ParamHelp
{
	public Recordset getEntStateList(TxnContext context) throws TxnException{
		String sql = "select JCSJFX_DM,JCSJFX_MC FROM GZ_DM_JCDM_FX T WHERE T.JC_DM_ID='200901150062' ORDER BY T.XSSX";
		BaseTable table = TableFactory.getInstance().getTableObject(this, "gz_dm_jcdm_fx");
		table.executeRowset(sql, context, "entState");
		Recordset rs = context.getRecordset("entState");
		DataBus db = new DataBus();
		db.setValue("jcsjfx_dm", "7");
		db.setValue("jcsjfx_mc", "个体转企业");
		rs.add(db);
		return this.getParamList(rs, "jcsjfx_mc", "jcsjfx_dm");
	}
	
	public Recordset getCaseChr(TxnContext context) throws TxnException{
		String sql = "select JCSJFX_DM,JCSJFX_MC FROM GZ_DM_JCDM_FX T WHERE T.JC_DM_ID='100114' ORDER BY T.JCSJFX_MC";
		BaseTable table = TableFactory.getInstance().getTableObject(this, "gz_dm_jcdm_fx");
		table.executeRowset(sql, context, "caseChr");
		return this.getParamList(context.getRecordset("caseChr"), "jcsjfx_mc", "jcsjfx_dm");
	}
	
	/**
	 * 
	 * @creator caiwd
	 * @createtime 2008-8-19
	 *             上午10:11:00
	 * @param context
	 * @return
	 * @throws TxnException
	 *
	 */
	public Recordset getEntType(TxnContext context) throws TxnException{
		String sql = "select JCSJFX_DM,JCSJFX_MC FROM GZ_DM_JCDM_FX T WHERE T.JC_DM_ID='100011' and T.JCSJFX_MC is not null ORDER BY T.JCSJFX_MC";
		BaseTable table = TableFactory.getInstance().getTableObject(this, "gz_dm_jcdm_fx");
		table.executeRowset(sql, context, "entType");
		return this.getParamList(context.getRecordset("entType"), "jcsjfx_mc", "jcsjfx_dm");
	}
	
	
	/**
	 * Ind_Type_Code
	 * @creator caiwd
	 * @createtime 2008-8-19
	 *             上午10:32:56
	 * @param context
	 * @return
	 * @throws TxnException
	 *
	 */
	public Recordset getIndTypeCode(TxnContext context) throws TxnException{
		String sql = "select JCSJFX_DM,JCSJFX_MC FROM GZ_DM_JCDM_FX T WHERE T.JC_DM_ID='16072502' and T.JCSJFX_MC is not null ORDER BY T.JCSJFX_MC";
		BaseTable table = TableFactory.getInstance().getTableObject(this, "gz_dm_jcdm_fx");
		table.executeRowset(sql, context, "IndTypeCode");
		return this.getParamList(context.getRecordset("IndTypeCode"), "jcsjfx_mc", "jcsjfx_dm");
	}
	
	public Recordset getDomDistrict(TxnContext context) throws TxnException{
		String sql = "select JCSJFX_DM, JCSJFX_MC FROM GZ_DM_JCDM_FX T WHERE T.JC_DM_ID = '200904220001' " +
				" and T.JCSJFX_MC is not null and t.jcsjfx_mc!='北京市' ORDER BY T.Jcsjfx_Dm";
		BaseTable table = TableFactory.getInstance().getTableObject(this, "gz_dm_jcdm_fx");
		table.executeRowset(sql, context, "DomDistrict");
		return this.getParamList(context.getRecordset("DomDistrict"), "jcsjfx_mc", "jcsjfx_dm");
	}
}
