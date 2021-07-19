package com.gwssi.dw.runmgr.services.txn;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.common.util.StringUtil;
import com.gwssi.dw.runmgr.services.common.Constants;
import com.gwssi.dw.runmgr.services.vo.SysSvrLogContext;

public class TxnSysSvrLog extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnSysSvrLog.class, SysSvrLogContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "sys_svr_log";
	
	private static final Map STATE_MAP = new HashMap();
	
	static{
		STATE_MAP.put("BAIC0000", "");
		STATE_MAP.put("BAIC0010", "�޷��������ļ�¼");
		STATE_MAP.put("BAIC0020", "��������¼������");
		STATE_MAP.put("BAIC0030", "�û��ṩ�Ĳ�������");
		STATE_MAP.put("BAIC0040", "����������ڲ�ѯ����");
		STATE_MAP.put("BAIC0050", "ϵͳ����");
		STATE_MAP.put("BAIC0060", "���Ӳ���");
		STATE_MAP.put("BAIC0070", "��¼ʧ��");
		STATE_MAP.put("BAIC9999", "δ֪����");
	}
	
	/**
	 * ���캯��
	 */
	public TxnSysSvrLog()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ������־�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50207001( SysSvrLogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysSvrLogSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryLogList", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysSvrLog result[] = context.getSysSvrLogs( outputNode );
		Recordset rs = context.getRecordset(outputNode);
		try{
			while(rs.hasNext()){
				DataBus db = (DataBus) rs.next();
				db.setValue("execute_start_time", StringUtil.format2Date(db.getValue("execute_start_time"), "yyyy-MM-dd HH:mm:ss", "yyyy��MM��dd�� HH:mm:ss"));
				db.setValue("execute_end_time", StringUtil.format2Date(db.getValue("execute_end_time"), "yyyy-MM-dd HH:mm:ss", "yyyy��MM��dd�� HH:mm:ss"));
				db.setValue("error_msg", getFGDMCN(db.getValue("error_msg")));
			}
		}catch(ParseException pe){
			
		}
	}
	
	/** ��ѯ������־
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50207002( SysSvrLogContext context ) throws TxnException
	{
//		Attribute.setPageRow(context, outputNode, -1);
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysSvrLogSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryLogTongji", context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		while(rs.hasNext()){
			DataBus db = (DataBus)rs.next();
			db.setValue("error_msg", STATE_MAP.get(db.getValue("error_msg")) == null ? "" : ""+STATE_MAP.get(db.getValue("error_msg")));
		}
	}
	
	/** ��ѯ���з������
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn50207003( SysSvrLogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		// ��ѯ��¼������ VoSysSvrLogSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryAllUsers", context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoSysSvrLog result[] = context.getSysSvrLogs( outputNode );
	}

	/**
	 * ��ѯ����ͳ��
	 * @param context
	 * @throws TxnException
	 */
	public void txn50207004( SysSvrLogContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoSysSvrLogSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( "queryChangeTongji", context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		while(rs.hasNext()){
			DataBus db = (DataBus)rs.next();
			db.setValue("error_msg", STATE_MAP.get(db.getValue("error_msg")) == null ? "" : ""+STATE_MAP.get(db.getValue("error_msg")));
		}
	}
	
	private String getFGDMCN(String fhdm){
		if(fhdm.equals(Constants.SERVICE_FHDM_OVER_MAX)){
			return "��������¼������";
		}else if(fhdm.equals(Constants.SERVICE_FHDM_NO_RESULT)){
			return "�޷��������ļ�¼";
		}else if(fhdm.equals(Constants.SERVICE_FHDM_INPUT_PARAM_ERROR)){
			return "�û��ṩ�Ĳ�������";
		}else if(fhdm.equals(Constants.SERVICE_FHDM_OVER_DATE_RANGE)){
			return "����������ڲ�ѯ����";
		}else if(fhdm.equals(Constants.SERVICE_FHDM_SYSTEM_ERROR)){
			return "ϵͳ����";
		}else if(fhdm.equals(Constants.SERVICE_FHDM_LOGIN_FAIL)){
			return "��¼ʧ��";
		}else if(fhdm.equals(Constants.SERVICE_FHDM_UNKNOWN_ERROR)){
			return "δ֪����";
		}
		return "";
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
		SysSvrLogContext appContext = new SysSvrLogContext( context );
		invoke( method, appContext );
	}
}
