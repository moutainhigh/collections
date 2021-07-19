package com.gwssi.share.ftp.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.TaskSchedulingConstants;
import com.gwssi.common.servicejob.SrvTriggerRunner;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.share.ftp.vo.ShareSrvSchedulingContext;
import com.gwssi.share.ftp.vo.VoShareSrvScheduling;

public class TxnShareSrvScheduling extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnShareSrvScheduling.class, ShareSrvSchedulingContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "share_srv_scheduling";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select share_srv_scheduling list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one share_srv_scheduling";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one share_srv_scheduling";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one share_srv_scheduling";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one share_srv_scheduling";
	
	/**
	 * 构造函数
	 */
	public TxnShareSrvScheduling()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询共享调度列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40400001( ShareSrvSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoShareSrvSchedulingSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoShareSrvScheduling result[] = context.getShareSrvSchedulings( outputNode );
	}
	
	/** 修改共享调度信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40400002( ShareSrvSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoShareSrvScheduling share_srv_scheduling = context.getShareSrvScheduling( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加共享调度信息
	 * @param context 交易上下文
	 * @throws Exception 
	 */
	public void txn40400003( ShareSrvSchedulingContext context ) throws Exception
	{
		  BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		  String srv_scheduling_id = context.getRecord("record").getValue("srv_scheduling_id");
		  String service_id = context.getRecord("record").getValue("service_id");
		  
//		  VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
		  VoShareSrvScheduling vo = new VoShareSrvScheduling();
		  vo.setScheduling_type(context.getRecord("record").getValue("scheduling_type"));//计划任务类型
		  vo.setScheduling_day(context.getRecord("record").getValue("scheduling_day"));//计划任务日期
		  vo.setStart_time(context.getRecord("record").getValue("start_time"));//计划任务开始时间
		  vo.setEnd_time(context.getRecord("record").getValue("end_time"));//计划任务结束时间
		  vo.setScheduling_week(context.getRecord("record").getValue("scheduling_week"));//计划任务周天
		  vo.setScheduling_count(context.getRecord("record").getValue("scheduling_count"));//计划任务执行次数
		  vo.setInterval_time(context.getRecord("record").getValue("interval_time"));//计划任务执行 间隔
		  vo.setJob_class_name(TaskSchedulingConstants.SRV_JOB_CLASS_NAME);//触发调用的类名
		  SrvTriggerRunner trigger = new SrvTriggerRunner();
		 
		  String zq=trigger.getZq(vo);//将时间规则转换成显示的字符串
		  context.getRecord("record").setValue("scheduling_day1",zq);
		  context.getRecord("record").setValue("job_class_name",TaskSchedulingConstants.SRV_JOB_CLASS_NAME);
		  if(srv_scheduling_id==null||"".equals(srv_scheduling_id)){
			  srv_scheduling_id = UuidGenerator.getUUID();
				context.getRecord("record").setValue("srv_scheduling_id", srv_scheduling_id);//调度ID
				
				String sqlTaskSchedule = "update share_ftp_service set srv_scheduling_id = '"+srv_scheduling_id+"' where service_id = '"+service_id+"'";
				System.out.println("sql========"+sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
				
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
		  vo.setSrv_scheduling_id(srv_scheduling_id);
		  vo.setService_id(service_id);
		  trigger.addToScheduler(vo);
	}
	
	/** 查询共享调度用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40400004( ShareSrvSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoShareSrvSchedulingPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoShareSrvScheduling result = context.getShareSrvScheduling( outputNode );
	}
	
	/** 删除共享调度信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn40400005( ShareSrvSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoShareSrvSchedulingPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		ShareSrvSchedulingContext appContext = new ShareSrvSchedulingContext( context );
		invoke( method, appContext );
	}
}
