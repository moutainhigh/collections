package com.gwssi.dw.runmgr.services.txn;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.StringUtil;
import com.gwssi.dw.runmgr.services.common.Constants;
import com.gwssi.dw.runmgr.services.vo.SysSvrLogContext;

public class TxnSysSvrLog extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysSvrLog.class, SysSvrLogContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "sys_svr_log";
	
	private static final Map STATE_MAP = new HashMap();
	
	static{
		STATE_MAP.put("BAIC0000", "");
		STATE_MAP.put("BAIC0010", "无符合条件的记录");
		STATE_MAP.put("BAIC0020", "超过最大记录数限制");
		STATE_MAP.put("BAIC0030", "用户提供的参数错误");
		STATE_MAP.put("BAIC0040", "超过最大日期查询限制");
		STATE_MAP.put("BAIC0050", "系统错误");
		STATE_MAP.put("BAIC0060", "连接测试");
		STATE_MAP.put("BAIC0070", "登录失败");
		STATE_MAP.put("BAIC9999", "未知错误");
	}
	
	/**
	 * 构造函数
	 */
	public TxnSysSvrLog()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询服务日志列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50207001( SysSvrLogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysSvrLogSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryLogList", context, inputNode, outputNode );
		// 查询到的记录集 VoSysSvrLog result[] = context.getSysSvrLogs( outputNode );
		Recordset rs = context.getRecordset(outputNode);
		try{
			while(rs.hasNext()){
				DataBus db = (DataBus) rs.next();
				db.setValue("execute_start_time", StringUtil.format2Date(db.getValue("execute_start_time"), "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日 HH:mm:ss"));
				db.setValue("execute_end_time", StringUtil.format2Date(db.getValue("execute_end_time"), "yyyy-MM-dd HH:mm:ss", "yyyy年MM月dd日 HH:mm:ss"));
				db.setValue("error_msg", getFGDMCN(db.getValue("error_msg")));
			}
		}catch(ParseException pe){
			
		}
	}
	
	/** 查询交换日志
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50207002( SysSvrLogContext context ) throws TxnException
	{
//		Attribute.setPageRow(context, outputNode, -1);
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysSvrLogSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryLogTongji", context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		while(rs.hasNext()){
			DataBus db = (DataBus)rs.next();
			db.setValue("error_msg", STATE_MAP.get(db.getValue("error_msg")) == null ? "" : ""+STATE_MAP.get(db.getValue("error_msg")));
		}
	}
	
	/** 查询所有服务对象
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn50207003( SysSvrLogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		// 查询记录的条件 VoSysSvrLogSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryAllUsers", context, inputNode, outputNode );
		// 查询到的记录集 VoSysSvrLog result[] = context.getSysSvrLogs( outputNode );
	}

	/**
	 * 查询交换统计
	 * @param context
	 * @throws TxnException
	 */
	public void txn50207004( SysSvrLogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoSysSvrLogSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryChangeTongji", context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		while(rs.hasNext()){
			DataBus db = (DataBus)rs.next();
			db.setValue("error_msg", STATE_MAP.get(db.getValue("error_msg")) == null ? "" : ""+STATE_MAP.get(db.getValue("error_msg")));
		}
	}
	
	private String getFGDMCN(String fhdm){
		if(fhdm.equals(Constants.SERVICE_FHDM_OVER_MAX)){
			return "超过最大记录数限制";
		}else if(fhdm.equals(Constants.SERVICE_FHDM_NO_RESULT)){
			return "无符合条件的记录";
		}else if(fhdm.equals(Constants.SERVICE_FHDM_INPUT_PARAM_ERROR)){
			return "用户提供的参数错误";
		}else if(fhdm.equals(Constants.SERVICE_FHDM_OVER_DATE_RANGE)){
			return "超过最大日期查询限制";
		}else if(fhdm.equals(Constants.SERVICE_FHDM_SYSTEM_ERROR)){
			return "系统错误";
		}else if(fhdm.equals(Constants.SERVICE_FHDM_LOGIN_FAIL)){
			return "登录失败";
		}else if(fhdm.equals(Constants.SERVICE_FHDM_UNKNOWN_ERROR)){
			return "未知错误";
		}
		return "";
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
		SysSvrLogContext appContext = new SysSvrLogContext( context );
		invoke( method, appContext );
	}
}
