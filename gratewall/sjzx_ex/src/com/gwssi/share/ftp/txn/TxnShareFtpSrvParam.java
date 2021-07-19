package com.gwssi.share.ftp.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.UuidGenerator;
import com.gwssi.share.ftp.vo.ShareFtpSrvParamContext;

public class TxnShareFtpSrvParam extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnShareFtpSrvParam.class, ShareFtpSrvParamContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "share_ftp_srv_param";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select share_ftp_srv_param list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one share_ftp_srv_param";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one share_ftp_srv_param";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one share_ftp_srv_param";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one share_ftp_srv_param";
	
	/**
	 * 构造函数
	 */
	public TxnShareFtpSrvParam()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询ftp服务参数列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40402001( ShareFtpSrvParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoShareFtpSrvParamSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoShareFtpSrvParam result[] = context.getShareFtpSrvParams( outputNode );
	}
	
	/** 修改ftp服务参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40402002( ShareFtpSrvParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoShareFtpSrvParam share_ftp_srv_param = context.getShareFtpSrvParam( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		String service_id=context.getRecord(inputNode).getValue("service_id");
		System.out.println("service_id======="+service_id);
		String service_targets_id=context.getRecord(inputNode).getValue("service_targets_id");
		System.out.println("service_targets_id======="+service_targets_id);
		context.getRecord("record1").setValue("service_id",service_id);
		context.getRecord("record1").setValue("service_targets_id",service_targets_id);
		//this.callService("40401004", context);
	}
	
	/** 增加ftp服务参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40402003( ShareFtpSrvParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoShareFtpSrvParam share_ftp_srv_param = context.getShareFtpSrvParam( inputNode );
		String srv_param_id = context.getRecord("record").getValue("srv_param_id");
			if (srv_param_id == null || "".equals(srv_param_id)) {
				String id = UuidGenerator.getUUID();
				context.getRecord("record").setValue("srv_param_id", id);// 参数值ID
				table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
			}else{
				table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
			}
			
	}
	
	/** 查询ftp服务参数用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40402004( ShareFtpSrvParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoShareFtpSrvParamPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoShareFtpSrvParam result = context.getShareFtpSrvParam( outputNode );
	}
	
	/** 删除ftp服务参数信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40402005( ShareFtpSrvParamContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoShareFtpSrvParamPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
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
		ShareFtpSrvParamContext appContext = new ShareFtpSrvParamContext( context );
		invoke( method, appContext );
	}
}
