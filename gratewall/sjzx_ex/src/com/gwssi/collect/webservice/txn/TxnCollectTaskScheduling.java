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
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnCollectTaskScheduling.class, CollectTaskSchedulingContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "collect_task_scheduling";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select collect_task_scheduling list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one collect_task_scheduling";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one collect_task_scheduling";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one collect_task_scheduling";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one collect_task_scheduling";
	
	/**
	 * ���캯��
	 */
	public TxnCollectTaskScheduling()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ��������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30801001( CollectTaskSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction("queryTaskScheduleList", context, inputNode, outputNode );
	}
	
	/** �޸����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30801002( CollectTaskSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoCollectTaskScheduling collect_task_scheduling = context.getCollectTaskScheduling( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** �������������Ϣ
	 * @param context ����������
	 * @throws Exception 
	 */
	public void txn30801003( CollectTaskSchedulingContext context ) throws Exception
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		  String task_scheduling_id = context.getRecord("record").getValue("task_scheduling_id");
		 
		  VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
		  vo.setJhrw_lx(context.getRecord("record").getValue("scheduling_type"));//�ƻ���������
		  vo.setJhrw_rq(context.getRecord("record").getValue("scheduling_day"));//�ƻ���������
		  vo.setJhrw_start_sj(context.getRecord("record").getValue("start_time"));//�ƻ�����ʼʱ��
		  vo.setJhrw_end_sj(context.getRecord("record").getValue("end_time"));//�ƻ��������ʱ��
		  vo.setJhrw_zt(context.getRecord("record").getValue("scheduling_week"));//�ƻ���������
		  vo.setJhrwzx_cs(context.getRecord("record").getValue("scheduling_count"));//�ƻ�����ִ�д���
		  vo.setJhrwzx_jg(context.getRecord("record").getValue("interval_time"));//�ƻ�����ִ�� ���
		  vo.setJob_class_name(TaskSchedulingConstants.JOB_CLASS_NAME);//�������õ�����
		  SimpleTriggerRunner trigger = new SimpleTriggerRunner();
		 
		  String zq=trigger.getZq(vo);
		  context.getRecord("record").setValue("scheduling_day1",zq);
		  context.getRecord("record").setValue("job_class_name",TaskSchedulingConstants.JOB_CLASS_NAME);
		  if(task_scheduling_id==null||"".equals(task_scheduling_id)){
			   task_scheduling_id = UuidGenerator.getUUID();
				context.getRecord("record").setValue("task_scheduling_id", task_scheduling_id);//���ݱ�ID
				
				context.getRecord("record").setValue("created_time",CalendarUtil.getCurrentDateTime());//����ʱ��
				context.getRecord("record").setValue("creator_id",context.getRecord("oper-data").getValue("userID"));//������ID
				context.getRecord("record").setValue("is_markup",ExConstant.IS_MARKUP_Y);// ���볣��  ��Ч���
				
				table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		  }else{
			    context.getRecord("record").setValue("is_markup",ExConstant.IS_MARKUP_Y);// ���볣��  ��Ч���
				context.getRecord("record").setValue("last_modify_time",CalendarUtil.getCurrentDateTime());//����޸�ʱ��
				context.getRecord("record").setValue("last_modify_id",context.getRecord("oper-data").getValue("userID"));//����޸���ID
				table.executeFunction(UPDATE_FUNCTION, context, inputNode, outputNode);
			}
		  vo.settask_scheduling_id(task_scheduling_id);
		  vo.setcollect_task_id(context.getRecord("record").getValue("collect_task_id"));
		  SimpleTriggerRunner.addToScheduler(vo);
		
	}
	
	/** ��ѯ������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30801004( CollectTaskSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCollectTaskSchedulingPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoCollectTaskScheduling result = context.getCollectTaskScheduling( outputNode );
	}
	
	/** ɾ�����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30801005( CollectTaskSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoCollectTaskSchedulingPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
		
	/**
	 * ��ҳ�����б��ѯ
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
	 * ���ظ���ķ����������滻���׽ӿڵ��������
	 * ���ú���
	 * @param funcName ��������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void doService( String funcName,
			TxnContext context ) throws TxnException
	{
		Method method = (Method)txnMethods.get( funcName );
		if( method == null ){
			funcName = this.getClass().getName() + "#" + funcName;
			throw new TxnErrorException( ErrorConstant.JAVA_METHOD_NOTFOUND,
					"û���ҵ�������[" + txnCode + ":" + funcName + "]�Ĵ�����"
			);
		}
		
		// ִ��
		CollectTaskSchedulingContext appContext = new CollectTaskSchedulingContext( context );
		invoke( method, appContext );
	}
}
