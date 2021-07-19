package com.gwssi.collect.webservice.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.collect.webservice.vo.CollectTaskSchedulingContext;
import com.gwssi.collect.webservice.vo.VoCollectTaskScheduling;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.TaskSchedulingConstants;
import com.gwssi.common.task.SimpleTriggerRunner;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;

public class TxnCollectTaskScheduling extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnCollectTaskScheduling.class, CollectTaskSchedulingContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "collect_task_scheduling";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select collect_task_scheduling list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one collect_task_scheduling";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one collect_task_scheduling";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one collect_task_scheduling";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one collect_task_scheduling";
	
	/**
	 * 构造函数
	 */
	public TxnCollectTaskScheduling()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询任务调度列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30801001( CollectTaskSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction("queryTaskScheduleList", context, inputNode, outputNode );
	}
	
	/** 修改任务调度信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30801002( CollectTaskSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoCollectTaskScheduling collect_task_scheduling = context.getCollectTaskScheduling( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加任务调度信息
	 * @param context 交易上下文
	 * @throws Exception 
	 */
	public void txn30801003( CollectTaskSchedulingContext context ) throws Exception
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		  String task_scheduling_id = context.getRecord("record").getValue("task_scheduling_id");
		 
		  VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
		  vo.setJhrw_lx(context.getRecord("record").getValue("scheduling_type"));//计划任务类型
		  vo.setJhrw_rq(context.getRecord("record").getValue("scheduling_day"));//计划任务日期
		  vo.setJhrw_start_sj(context.getRecord("record").getValue("start_time"));//计划任务开始时间
		  vo.setJhrw_end_sj(context.getRecord("record").getValue("end_time"));//计划任务结束时间
		  vo.setJhrw_zt(context.getRecord("record").getValue("scheduling_week"));//计划任务周天
		  vo.setJhrwzx_cs(context.getRecord("record").getValue("scheduling_count"));//计划任务执行次数
		  vo.setJhrwzx_jg(context.getRecord("record").getValue("interval_time"));//计划任务执行 间隔
		  vo.setJob_class_name(TaskSchedulingConstants.JOB_CLASS_NAME);//触发调用的类名
		  SimpleTriggerRunner trigger = new SimpleTriggerRunner();
		 
		  String zq=trigger.getZq(vo);
		  context.getRecord("record").setValue("scheduling_day1",zq);
		  context.getRecord("record").setValue("job_class_name",TaskSchedulingConstants.JOB_CLASS_NAME);
		  if(task_scheduling_id==null||"".equals(task_scheduling_id)){
			   task_scheduling_id = UuidGenerator.getUUID();
				context.getRecord("record").setValue("task_scheduling_id", task_scheduling_id);//数据表ID
				
				context.getRecord("record").setValue("created_time",CalendarUtil.getCurrentDateTime());//创建时间
				context.getRecord("record").setValue("creator_id",context.getRecord("oper-data").getValue("userID"));//创建人ID
				context.getRecord("record").setValue("is_markup",ExConstant.IS_MARKUP_Y);// 引入常量  有效标记
				
				table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		  }else{
			    context.getRecord("record").setValue("is_markup",ExConstant.IS_MARKUP_Y);// 引入常量  有效标记
				context.getRecord("record").setValue("last_modify_time",CalendarUtil.getCurrentDateTime());//最后修改时间
				context.getRecord("record").setValue("last_modify_id",context.getRecord("oper-data").getValue("userID"));//最后修改人ID
				table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
			}
		  vo.settask_scheduling_id(task_scheduling_id);
		  vo.setcollect_task_id(context.getRecord("record").getValue("collect_task_id"));
		  SimpleTriggerRunner.addToScheduler(vo);
		
	}
	
	/** 查询任务调度用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30801004( CollectTaskSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoCollectTaskSchedulingPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoCollectTaskScheduling result = context.getCollectTaskScheduling( outputNode );
	}
	
	/** 删除任务调度信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn30801005( CollectTaskSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoCollectTaskSchedulingPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	/**
	 * 首页任务列表查询
	 * @param context
	 * @throws TxnException
	 */
	public void txn30801006( CollectTaskSchedulingContext context ) throws TxnException{
		BaseTable table = TableFactory.getInstance().getTableObject( this, "collect_task");
		//System.out.println(context);
		table.executeFunction("queryTaskScheduleListForIndex", context, inputNode, outputNode);
		//System.out.println(context);
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
		CollectTaskSchedulingContext appContext = new CollectTaskSchedulingContext( context );
		invoke( method, appContext );
	}
}
