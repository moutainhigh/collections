package com.gwssi.log.systemlog.txn;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.JsonDataUtil;
import com.gwssi.log.sharelog.vo.ShareLogContext;
import com.gwssi.log.systemlog.vo.FirstPageQuerySystemlogContext;

public class TxnFirstPageQuery extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnFirstPageQuery.class, FirstPageQuerySystemlogContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "first_page_query_sl";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select first_page_query_sl list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one first_page_query_sl";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one first_page_query_sl";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one first_page_query_sl";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one first_page_query_sl";
	
	/**
	 * ���캯��
	 */
	public TxnFirstPageQuery()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	/** ��ѯϵͳ��־�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn601011( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		System.out.println("txn601011");
		String opera_time = context.getRecord("select-key").getValue("created_time");
		
		FirstPageQuerySystemlogContext usercontext = new FirstPageQuerySystemlogContext();
		Attribute.setPageRow(usercontext, outputNode, -1);
		table.executeFunction("getUserInfo", usercontext, inputNode,
				outputNode);
		Recordset userRs = usercontext.getRecordset("record");
		context.setValue("username", JsonDataUtil.getJsonByRecordSet(userRs));
		
		
		// ��ѯ��¼������ VoFirstPageQuerySystemlogSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "querySystemLogList", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoFirstPageQuerySystemlog result[] = context.getFirstPageQuerySystemlogs( outputNode );
	}
	
	
	
	
	/** ��ѯϵͳ��־�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn601091( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		
		
	}
	
	
	
	public void txn601092( FirstPageQuerySystemlogContext context ) throws TxnException
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
	
		table.executeFunction( "querySystemLogList", context, inputNode, outputNode );
		
	}
	/** �޸�ϵͳ��־��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn601012( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoFirstPageQuerySystemlog first_page_query = context.getFirstPageQuerySystemlog( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ����ϵͳ��־��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn601013( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoFirstPageQuerySystemlog first_page_query = context.getFirstPageQuerySystemlog( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯϵͳ��־�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn601014( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoFirstPageQuerySystemlogPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoFirstPageQuerySystemlog result = context.getFirstPageQuerySystemlog( outputNode );
	}
	
	/** ɾ��ϵͳ��־��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn601015( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoFirstPageQuerySystemlogPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	/** ��תϵͳ��־��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn601016( FirstPageQuerySystemlogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoFirstPageQuerySystemlogPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( "querySystemLog", context, inputNode, outputNode );
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
		FirstPageQuerySystemlogContext appContext = new FirstPageQuerySystemlogContext( context );
		invoke( method, appContext );
	}
}
