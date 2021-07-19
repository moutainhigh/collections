package com.gwssi.log.report.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import cn.gwssi.dw.rd.log.vo.LogReportUseContext;

public class TxnLogReportUse extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnLogReportUse.class, LogReportUseContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "log_report_use";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select log_report_use list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one log_report_use";
	
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one log_report_use";
	
	
	/**
	 * ���캯��
	 */
	public TxnLogReportUse()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	

	/** ��ѯ�����������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn620200201( LogReportUseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoLogReportUseSelectKey selectKey = context.getSelectKey( inputNode );
		//table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		table.executeFunction( "queryLogReportUse", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoLogReportUse result[] = context.getLogReportUses( outputNode );
	}
	
	/** ��ѯ������������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn620200291( LogReportUseContext context ) throws TxnException
	{
		
	}
	/** ��ѯ�����������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn620200292( LogReportUseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoShareServiceSelectKey selectKey = context.getSelectKey( inputNode );
		String create_time = context.getRecord("select-key").getValue("created_time");
		if(StringUtils.isNotBlank(create_time)){
			String [] ctime = com.gwssi.common.util.DateUtil.getDateRegionByDatePicker(create_time, true);
			context.getRecord("select-key").setValue("created_time_start", ctime[0]);
			context.getRecord("select-key").setValue("created_time_end", ctime[1]);
			context.getRecord("select-key").remove("created_time");
		}
	
		table.executeFunction( "queryLogReportUse", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoLogReportUse result[] = context.getLogReportUses( outputNode );
	}
	
	/** ���ӱ�����������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn620200203( LogReportUseContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoLogReportUse log_report_use = context.getLogReportUse( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
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
		LogReportUseContext appContext = new LogReportUseContext( context );
		invoke( method, appContext );
	}
}
