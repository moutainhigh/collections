package com.gwssi.dw.aic.bj.exc.que.txn;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnMasterTable;

import com.gwssi.common.util.PlainSequence;
import com.gwssi.common.util.StringUtil;
import com.gwssi.dw.aic.bj.exc.que.vo.ExcQueRegContext;

public class TxnExcQueReg extends TxnMasterTable
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnExcQueReg.class, ExcQueRegContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "exc_que_reg";
	


	// 明细表
	private static final String detailTables[][] = {
		{ "exc_que_auth", "60114011" },
		{ "exc_que_civ", "60114021" },
		
		// 仅仅是为了处理最后一个 [,]；可以删除
		{ null, null }
	};
	
	/**
	 * 构造函数
	 */
	public TxnExcQueReg()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询非企业列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn60114001( ExcQueRegContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "viewExcQueReg_Detail", context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		for(int i=0;i<rs.size();i++){
			DataBus db = rs.get(i);
			formatDate("est_date","yyyy-MM-dd", db);
		}
	}

	/**
	 * 查询编办基本信息
	 * @creator caiwd
	 * @createtime 2008-8-29
	 *             下午02:29:52
	 * @param context
	 * @throws TxnException
	 *
	 */
	public void txn60114002( ExcQueRegContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, "exc_que_auth" );
		table.executeFunction( "queryExcQueAuthBase_detail", context, inputNode, outputNode );
	}

	/**
	 * 框架页面(查询传递条件Exc_Que_Reg_ID、)
	 * @creator caiwd
	 * @createtime 2008-8-31
	 *             上午10:11:49
	 * @param context
	 * @throws TxnException
	 *
	 */
	public void txn60114003( ExcQueRegContext context ) throws TxnException
	{
		/*
		 * select
		 * t.exc_que_reg_id,t.ent_name,t.organ_code,t.civ_id,t.corp_rpt,t.data_sou
		 * from exc_que_reg t where
		 * t.exc_que_reg_id='F0000000000000100000000004719273'
		 */
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "viewExcQueReg_DetailAndParameters", context, inputNode, outputNode );    
		context.getRecord("select-key").setValue("nodes", "FQY");
		this.callService("60110108", context);
	}

	/**
	 * 编办企业年检信息
	 * @creator caiwd
	 * @createtime 2008-8-31
	 *             上午09:26:19
	 * @param context
	 * @throws TxnException
	 *
	 */
	public void txn60114004( ExcQueRegContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, "exc_que_auth_ann" );
		table.executeFunction( "queryExcQueAuthAnn_list", context, inputNode, outputNode );
		PlainSequence.addIndex(context, outputNode);
	}

	/**
	 * 民政事业单位基本信息
	 * @creator caiwd
	 * @createtime 2008-8-31
	 *             上午11:35:45
	 * @param context
	 * @throws TxnException
	 *
	 */
	public void txn60114005( ExcQueRegContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, "exc_que_civ" );
		table.executeFunction( "queryExcQueCivBase_detail", context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		for(int i=0;i<rs.size();i++){
			DataBus db = rs.get(i);
			formatDate("est_date","yyyy-MM-dd", db);
			formatDate("chan_date","yyyy-MM-dd", db);
			formatDate("revok_date","yyyy-MM-dd", db);
		}
	}
	
	/**
	 * 民政事业单位年检信息
	 * @creator caiwd
	 * @createtime 2008-8-31
	 *             下午12:01:36
	 * @param context
	 * @throws TxnException
	 *
	 */
	public void txn60114006( ExcQueRegContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, "exc_que_civ_ann" );
		//queryExcQueCivAnn_list
		table.executeFunction( "queryExcQueCivAnn_list", context, inputNode, outputNode );
		PlainSequence.addIndex(context, outputNode);
	}
	
	
	/**
	 * 重载父类的方法，用于替换交易接口的输入变量
	 * 调用函数
	 * @param funcName 方法名称
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数"
			);
		}
		
		// 执行
		ExcQueRegContext appContext = new ExcQueRegContext( context );
		invoke( method, appContext );
	}
	
	/**
	 * 日期格式化
	 * @creator caiwd
	 * @createtime 2008-9-17
	 *             下午01:36:35
	 * @param record
	 * @param fromPattern
	 * @param context
	 *
	 */
	protected void formatDate(String record,String fromPattern, DataBus db){
		String date2Format = db.getValue(record);
		try {
			db.setValue(record,StringUtil.format2Date(date2Format,fromPattern, "yyyy年MM月dd日"));
		} catch (ParseException e) {
			//System.out.println("日期转换失败："+e.getMessage());
		}
	}
}
