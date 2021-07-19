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
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnShareSrvScheduling.class, ShareSrvSchedulingContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "share_srv_scheduling";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select share_srv_scheduling list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one share_srv_scheduling";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one share_srv_scheduling";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one share_srv_scheduling";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one share_srv_scheduling";
	
	/**
	 * ���캯��
	 */
	public TxnShareSrvScheduling()
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
	public void txn40400001( ShareSrvSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoShareSrvSchedulingSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoShareSrvScheduling result[] = context.getShareSrvSchedulings( outputNode );
	}
	
	/** �޸Ĺ��������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40400002( ShareSrvSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoShareSrvScheduling share_srv_scheduling = context.getShareSrvScheduling( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���ӹ��������Ϣ
	 * @param context ����������
	 * @throws Exception 
	 */
	public void txn40400003( ShareSrvSchedulingContext context ) throws Exception
	{
		  BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		  String srv_scheduling_id = context.getRecord("record").getValue("srv_scheduling_id");
		  String service_id = context.getRecord("record").getValue("service_id");
		  
//		  VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
		  VoShareSrvScheduling vo = new VoShareSrvScheduling();
		  vo.setScheduling_type(context.getRecord("record").getValue("scheduling_type"));//�ƻ���������
		  vo.setScheduling_day(context.getRecord("record").getValue("scheduling_day"));//�ƻ���������
		  vo.setStart_time(context.getRecord("record").getValue("start_time"));//�ƻ�����ʼʱ��
		  vo.setEnd_time(context.getRecord("record").getValue("end_time"));//�ƻ��������ʱ��
		  vo.setScheduling_week(context.getRecord("record").getValue("scheduling_week"));//�ƻ���������
		  vo.setScheduling_count(context.getRecord("record").getValue("scheduling_count"));//�ƻ�����ִ�д���
		  vo.setInterval_time(context.getRecord("record").getValue("interval_time"));//�ƻ�����ִ�� ���
		  vo.setJob_class_name(TaskSchedulingConstants.SRV_JOB_CLASS_NAME);//�������õ�����
		  SrvTriggerRunner trigger = new SrvTriggerRunner();
		 
		  String zq=trigger.getZq(vo);//��ʱ�����ת������ʾ���ַ���
		  context.getRecord("record").setValue("scheduling_day1",zq);
		  context.getRecord("record").setValue("job_class_name",TaskSchedulingConstants.SRV_JOB_CLASS_NAME);
		  if(srv_scheduling_id==null||"".equals(srv_scheduling_id)){
			  srv_scheduling_id = UuidGenerator.getUUID();
				context.getRecord("record").setValue("srv_scheduling_id", srv_scheduling_id);//����ID
				
				String sqlTaskSchedule = "update share_ftp_service set srv_scheduling_id = '"+srv_scheduling_id+"' where service_id = '"+service_id+"'";
				System.out.println("sql========"+sqlTaskSchedule);
				table.executeUpdate(sqlTaskSchedule);
				
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
		  vo.setSrv_scheduling_id(srv_scheduling_id);
		  vo.setService_id(service_id);
		  trigger.addToScheduler(vo);
	}
	
	/** ��ѯ������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40400004( ShareSrvSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoShareSrvSchedulingPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoShareSrvScheduling result = context.getShareSrvScheduling( outputNode );
	}
	
	/** ɾ�����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn40400005( ShareSrvSchedulingContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoShareSrvSchedulingPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
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
		ShareSrvSchedulingContext appContext = new ShareSrvSchedulingContext( context );
		invoke( method, appContext );
	}
}
