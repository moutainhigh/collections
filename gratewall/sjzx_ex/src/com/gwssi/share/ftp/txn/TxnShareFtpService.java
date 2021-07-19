package com.gwssi.share.ftp.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.UuidGenerator;
import com.gwssi.share.ftp.vo.ShareFtpServiceContext;

public class TxnShareFtpService extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnShareFtpService.class,
														ShareFtpServiceContext.class);

	// 数据表名称
	private static final String	TABLE_NAME		= "share_ftp_service";

	// 查询列表
	private static final String	ROWSET_FUNCTION	= "select share_ftp_service list";

	// 查询记录
	private static final String	SELECT_FUNCTION	= "select one share_ftp_service";

	// 修改记录
	private static final String	UPDATE_FUNCTION	= "update one share_ftp_service";

	// 增加记录
	private static final String	INSERT_FUNCTION	= "insert one share_ftp_service";

	// 删除记录
	private static final String	DELETE_FUNCTION	= "delete one share_ftp_service";

	/**
	 * 构造函数
	 */
	public TxnShareFtpService()
	{

	}

	/**
	 * 初始化函数
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{

	}

	/**
	 * 查询共享ftp服务列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn40401001(ShareFtpServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoShareFtpServiceSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction(ROWSET_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录集 VoShareFtpService result[] = context.getShareFtpServices(
		// outputNode );
	}

	/**
	 * 修改共享ftp服务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn40401002(ShareFtpServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

	
		String ftp_service_id = context.getRecord(inputNode).getValue("ftp_service_id");
		String service_id=context.getRecord(inputNode).getValue("service_id");
		if(service_id!=null&&!"".equals(service_id)){
		
			context.getRecord("record1").setValue("service_id", service_id);// ftp服务ID
			if (ftp_service_id == null || "".equals(ftp_service_id)) {
				String id = UuidGenerator.getUUID();
				context.getRecord("record1").setValue("ftp_service_id", id);// ftp服务ID
				table.executeFunction(INSERT_FUNCTION, context, inputNode,outputNode);
			}// 新增
			else {
				table.executeFunction(UPDATE_FUNCTION, context, inputNode,outputNode);
			}
			}
			this.callService("40401004", context);
		
	}
	
	/**
	 * 增加共享ftp服务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn40401003(ShareFtpServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 增加记录的内容 VoShareFtpService share_ftp_service =
		// context.getShareFtpService( inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 查询共享ftp服务用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn40401004(ShareFtpServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		ShareFtpServiceContext context1= new ShareFtpServiceContext();
		// 查询记录的主键 VoShareFtpServicePrimaryKey primaryKey =
		// context.getPrimaryKey( inputNode );
		String service_id = context.getRecord("primary-key").getValue("service_id");// 服务ID
		if(service_id==null||"".equals(service_id)){
			service_id=context.getRecord("record1").getValue("service_id");// 服务ID
		}
		String service_no = context.getRecord("primary-key").getValue("service_no");// 服务编号
		if(service_no==null||"".equals(service_no)){
			service_no=context.getRecord("record1").getValue("service_no");// 服务编号
		}
		String service_name = context.getRecord("primary-key").getValue("service_name");// 服务名称
		if(service_name==null||"".equals(service_name)){
			service_name=context.getRecord("record1").getValue("service_name");// 服务名称
		}
		String service_targets_id = context.getRecord("primary-key").getValue("service_targets_id");// 服务对象ID
		if(service_targets_id==null||"".equals(service_targets_id)){
			service_targets_id=context.getRecord("record1").getValue("service_targets_id");// 服务对象ID
		}
		
		String sql = "select count(*) as num from share_ftp_service  where 1=1 ";
		if (service_id != null && !"".equals(service_id)) {
			sql += "and service_id = '" + service_id + "' ";
		}
		table.executeSelect(sql, context1, outputNode);
		String num = context1.getRecord(outputNode).getValue("num");
		if (num != null && !"0".equals(num)) {
			table.executeFunction("queryFtpService", context, inputNode,"record1");
			
			sql = "select count(*) as num from share_ftp_srv_param t1,share_ftp_service t2 where t1.ftp_service_id = t2.ftp_service_id ";
			if(service_id!=null&&!"".equals(service_id)){
				sql += "and t2.service_id = '" + service_id + "' ";
			}
			table.executeSelect(sql, context1, outputNode);
			num = context1.getRecord(outputNode).getValue("num");
			System.out.print("num===" + num);
			if (num != null && !"0".equals(num)) {
				// 查询当前任务的修改后的这个参数的参数值列表
				table.executeFunction("queryParamValueById", context, inputNode,outputNode);
				DataBus db = new DataBus();
				// db.setProperty("patameter_style", param_style);
				context.addRecord("param", db);
			}
		}
		context.getRecord("record1").setValue("service_id", service_id);// 服务ID
		context.getRecord("record1").setValue("service_no", service_no);// 服务编号
		context.getRecord("record1").setValue("service_name", service_name);// 服务名称
		context.getRecord("record1").setValue("service_targets_id",service_targets_id);// 服务对象ID
	}

	/**
	 * 删除共享ftp服务信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn40401005(ShareFtpServiceContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 删除记录的主键列表 VoShareFtpServicePrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 重载父类的方法，用于替换交易接口的输入变量 调用函数
	 * 
	 * @param funcName
	 *            方法名称
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	protected void doService(String funcName, TxnContext context)
			throws TxnException
	{
		Method method = (Method) txnMethods.get(funcName);
		if (method == null) {
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException(ErrorConstant.JAVA_METHOD_NOTFOUND,
					"没有找到交易码[" + txnCode + ":" + funcName + "]的处理函数");
		}

		// 执行
		ShareFtpServiceContext appContext = new ShareFtpServiceContext(context);
		invoke(method, appContext);
	}
}
