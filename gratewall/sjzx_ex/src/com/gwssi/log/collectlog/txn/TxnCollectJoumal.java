package com.gwssi.log.collectlog.txn;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.dao.resource.PublicResource;
import cn.gwssi.common.dao.resource.code.CodeMap;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.log.LogUtil;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.common.util.ValueSetCodeUtil;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.log.collectlog.vo.CollectJoumalContext;
import com.gwssi.log.sharelog.dao.ShareLogVo;
import com.gwssi.log.sharelog.vo.ShareLogContext;
import com.gwssi.resource.svrobj.vo.ResDataSourceContext;
import com.gwssi.share.service.vo.ShareServiceContext;

public class TxnCollectJoumal extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap		txnMethods		= getAllMethod(
														TxnCollectJoumal.class,
														CollectJoumalContext.class);

	// 数据表名称
	private static final String	TABLE_NAME		= "collect_joumal";

	// 查询列表
	private static final String	ROWSET_FUNCTION	= "select collect_joumal list";

	// 查询记录
	private static final String	SELECT_FUNCTION	= "${mod.function.select}";

	// 修改记录
	private static final String	UPDATE_FUNCTION	= "${mod.function.update}";

	// 增加记录
	private static final String	INSERT_FUNCTION	= "${mod.function.insert}";

	// 删除记录
	private static final String	DELETE_FUNCTION	= "${mod.function.delete}";

	/**
	 * 构造函数
	 */
	public TxnCollectJoumal()
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
	 * 查询采集日志列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6011001(CollectJoumalContext context) throws TxnException
	{

		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoCollectJoumalSelectKey selectKey = context.getSelectKey(
		// inputNode );
		table.executeFunction("queryCollectLogList", context, inputNode,
				outputNode);
		// 查询到的记录集 VoCollectJoumal result[] = context.getCollectJoumals(
		// outputNode );
		ResourceBundle code = ResourceBundle.getBundle("share_error");
		// System.out.println("context="+context);
		Recordset resultList = context.getRecordset(outputNode);
		for (int i = 0; i < resultList.size(); i++) {
			String val = ValueSetCodeUtil.getPropertiesByKey("share_error",
					resultList.get(i).getValue("return_codes"));
			resultList.get(i).setValue("return_codes", val);
		}
	}

	/**
	 * 修改采集日志信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6011002(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 修改记录的内容 VoCollectJoumal collect_joumal = context.getCollectJoumal(
		// inputNode );
		table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 增加采集日志信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6011003(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 增加记录的内容 VoCollectJoumal collect_joumal = context.getCollectJoumal(
		// inputNode );
		table.executeFunction(INSERT_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 查询采集日志用于修改
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6011004(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的主键 VoCollectJoumalPrimaryKey primaryKey = context.getPrimaryKey(
		// inputNode );
		table.executeFunction(SELECT_FUNCTION, context, inputNode, outputNode);
		// 查询到的记录内容 VoCollectJoumal result = context.getCollectJoumal(
		// outputNode );
	}

	/**
	 * 删除采集日志信息
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6011005(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 删除记录的主键列表 VoCollectJoumalPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table.executeFunction(DELETE_FUNCTION, context, inputNode, outputNode);
	}

	/**
	 * 跳转到采集日志信息页面
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6011006(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 删除记录的主键列表 VoCollectJoumalPrimaryKey primaryKey[] =
		// context.getPrimaryKeys( inputNode );
		table
				.executeFunction("queryCollectLog", context, inputNode,
						outputNode);

		ResourceBundle code = ResourceBundle.getBundle("share_error");
		//System.out.println("context=" + context);
		Recordset resultList = context.getRecordset(outputNode);
		for (int i = 0; i < resultList.size(); i++) {
			String val = ValueSetCodeUtil.getPropertiesByKey("share_error",
					resultList.get(i).getValue("return_codes"));
			resultList.get(i).setValue("return_codes", val);
		}
	}

	/**
	 * 查询列表
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 * @throws IOException
	 */
	public void txn6011010(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);

		CollectJoumalContext targetContext = new CollectJoumalContext();
		Attribute.setPageRow(targetContext, outputNode, -1);
		table.executeFunction("getInfoByTarget", targetContext, inputNode,
				outputNode);
		System.out.println(targetContext);
		Recordset targetRs = targetContext.getRecordset("record");
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(targetContext, "资源管理_服务对象类型");
		if (!rs.isEmpty()) {
			String[] keys = new String[rs.size()];
			String[] values = new String[rs.size()];
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				keys[i] = db.getValue("codename");
				values[i] = db.getValue("codevalue");
			}
			String groupValue = JsonDataUtil.getJsonGroupByRecordSet(targetRs,
					"service_targets_type", keys, values);
			context.setValue("svrTarget", groupValue);
		}

	}

	public void txn6011011(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoShareServiceSelectKey selectKey = context.getSelectKey(
		// inputNode );
		String create_time = context.getRecord("select-key").getValue(
				"created_time");

		// System.out.println("来自TxnCollectjoumal.java的值为"+create_time);

		if (StringUtils.isNotBlank(create_time)) {
			String[] ctime = com.gwssi.common.util.DateUtil
					.getDateRegionByDatePicker(create_time, true);
			context.getRecord("select-key").setValue("created_time_start",
					ctime[0]);
			context.getRecord("select-key").setValue("created_time_end",
					ctime[1]);
			context.getRecord("select-key").remove("created_time");
		}
		table.executeFunction("queryShareServiceListOrder", context, inputNode,
				outputNode);
		// 查询到的记录集 VoShareService result[] = context.getShareServices(
		// outputNode );
	}

	/**
	 * 
	 * txn6011013采集日志归档
	 * 
	 * @param context
	 * @throws TxnException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public void txn6011013(CollectJoumalContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		// 查询记录的条件 VoShareServiceSelectKey selectKey = context.getSelectKey(
		// inputNode );
		String time_start = context.getRecord("select-key").getValue(
				"created_time_start");
		String time_end = context.getRecord("select-key").getValue(
				"created_time_end");


		int count = table.executeFunction("updateCollectLog", context,
				inputNode, outputNode);
		context.getRecord("record").setValue("count", String.valueOf(count));

	}
	/**
	 * 查询日志列表(采集+共享)  add by LXB 2014-01-24
	 * 
	 * @param context
	 *            交易上下文
	 * @throws TxnException
	 */
	public void txn6011020(CollectJoumalContext context) throws TxnException
	{
		//System.out.println("begin6011020:"+context);
		String start_time = context.getRecord("select-key").getValue("created_time");
		
		BaseTable table = TableFactory.getInstance().getTableObject(this,
				TABLE_NAME);
		
		//构造分类 json数据
		List typelist=new ArrayList();
		Map map =new HashMap();
		map.put("title", "采集日志");
		map.put("key", "c");		
		typelist.add(map);
		Map map1 =new HashMap();
		map1.put("title", "共享日志");
		map1.put("key", "s");		
		typelist.add(map1);
		context.setValue("svrType", JsonDataUtil.toJSONString(typelist));
		
		//构造状态 json数据
		List statuslist=new ArrayList();
		Map map2 =new HashMap();
		map2.put("title", "成功");
		map2.put("key", "1");		
		statuslist.add(map2);
		Map map3 =new HashMap();
		map3.put("title", "失败");
		map3.put("key", "0");		
		statuslist.add(map3);
		context.setValue("svrStatus", JsonDataUtil.toJSONString(statuslist));
		
		// 构造服务对象 json数据
		CollectJoumalContext svrTarcontext = new CollectJoumalContext();
		svrTarcontext.getRecord(inputNode).setValue("table_name",
				"res_service_targets");
		svrTarcontext.getRecord(inputNode).setValue("col_name",
				"service_targets_id");
		svrTarcontext.getRecord(inputNode).setValue("col_title",
				"service_targets_name");
		Attribute.setPageRow(svrTarcontext, outputNode, -1);
		table.executeFunction("getTargetInfo", svrTarcontext, inputNode,
				outputNode);
		Recordset targetRs = svrTarcontext.getRecordset("record");
		CodeMap codeMap = PublicResource.getCodeFactory();
		Recordset rs = codeMap.lookup(svrTarcontext, "资源管理_服务对象类型");
		if (!rs.isEmpty()) {
			String[] keys = new String[rs.size()];
			String[] values = new String[rs.size()];
			for (int i = 0; i < rs.size(); i++) {
				DataBus db = rs.get(i);
				keys[i] = db.getValue("codename");
				values[i] = db.getValue("codevalue");
			}
			String groupValue = JsonDataUtil.getJsonGroupByRecordSet(targetRs,
					"service_targets_type", keys, values);
			//System.out.println("group----"+groupValue);
			context.setValue("svrTarget", groupValue);
		}
		
		if(StringUtils.isBlank(start_time)){
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal=Calendar.getInstance();
			Date date=cal.getTime();
			String to= df.format(date)+" 00:00";
			cal.add(Calendar.DATE, -1); 
			date=cal.getTime();
			String from= df.format(date)+" 00:00";
			
			start_time=from+" 至 "+to;
			context.getRecord("select-key").setValue("created_time",
					start_time);
			//System.out.println(start_time);
		}
		

		
		table.executeFunction("queryLogData", context, inputNode, outputNode);
		//System.out.println("end6011020:"+context);
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
		CollectJoumalContext appContext = new CollectJoumalContext(context);
		invoke(method, appContext);
	}

}
