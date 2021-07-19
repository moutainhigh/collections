package com.gwssi.collect.webservice.param.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.collect.webservice.param.vo.CollectWsParamValueContext;

public class TxnCollectWsParamValue extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnCollectWsParamValue.class, CollectWsParamValueContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "collect_ws_param_value";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select collect_ws_param_value list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one collect_ws_param_value";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one collect_ws_param_value";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one collect_ws_param_value";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one collect_ws_param_value";
	
	/**
	 * 构造函数
	 */
	public TxnCollectWsParamValue()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询采集参数值列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30107001( CollectWsParamValueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoCollectWsParamValueSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoCollectWsParamValue result[] = context.getCollectWsParamValues( outputNode );
	}
	
	/** 修改采集参数值信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30107002( CollectWsParamValueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoCollectWsParamValue collect_ws_param_value = context.getCollectWsParamValue( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加采集参数值信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30107003( CollectWsParamValueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoCollectWsParamValue collect_ws_param_value = context.getCollectWsParamValue( inputNode );
		//System.out.println("txn30107003:"+context);
		String webservice_patameter_id=context.getValue("webservice_patameter_id");
		if(StringUtils.isNotBlank(webservice_patameter_id)){
			
			//删除该参数的所有参数值
			String delSql="delete from collect_ws_param_value where webservice_patameter_id='"+webservice_patameter_id+"'";
			int rsnum=table.executeUpdate(delSql);
			//System.out.println("rsnum="+rsnum);
			Recordset rs=context.getRecordset("record");
			
			//插入新值
			if(rs!=null && rs.size()>0){
				int num=rs.size();
				for(int i=0;i<num;i++){
					TxnContext txnContext=new TxnContext();
					txnContext.addRecord("record", rs.get(i));
					//System.out.println("i="+i+"--txnContext="+txnContext);
					table.executeFunction( INSERT_FUNCTION, txnContext, inputNode, outputNode );
				}
			}
			
			
			
		}
		
		
	}
	
	/** 查询采集参数值用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30107004( CollectWsParamValueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoCollectWsParamValuePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoCollectWsParamValue result = context.getCollectWsParamValue( outputNode );
	}
	
	/** 删除采集参数值信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30107005( CollectWsParamValueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoCollectWsParamValuePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		//System.out.println("inputNode="+inputNode);
		//System.out.println(context);
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );

		//callService("30103033", context);
		
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
		CollectWsParamValueContext appContext = new CollectWsParamValueContext( context );
		invoke( method, appContext );
	}
}
