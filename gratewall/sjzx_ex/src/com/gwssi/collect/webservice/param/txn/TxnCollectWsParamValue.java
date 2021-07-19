package com.gwssi.collect.webservice.param.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.txn.TxnService;
import com.gwssi.collect.webservice.param.vo.CollectWsParamValueContext;

public class TxnCollectWsParamValue extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnCollectWsParamValue.class, CollectWsParamValueContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "collect_ws_param_value";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select collect_ws_param_value list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one collect_ws_param_value";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one collect_ws_param_value";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one collect_ws_param_value";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one collect_ws_param_value";
	
	/**
	 * ���캯��
	 */
	public TxnCollectWsParamValue()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ�ɼ�����ֵ�б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30107001( CollectWsParamValueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCollectWsParamValueSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoCollectWsParamValue result[] = context.getCollectWsParamValues( outputNode );
	}
	
	/** �޸Ĳɼ�����ֵ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30107002( CollectWsParamValueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoCollectWsParamValue collect_ws_param_value = context.getCollectWsParamValue( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ���Ӳɼ�����ֵ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30107003( CollectWsParamValueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoCollectWsParamValue collect_ws_param_value = context.getCollectWsParamValue( inputNode );
		//System.out.println("txn30107003:"+context);
		String webservice_patameter_id=context.getValue("webservice_patameter_id");
		if(StringUtils.isNotBlank(webservice_patameter_id)){
			
			//ɾ���ò��������в���ֵ
			String delSql="delete from collect_ws_param_value where webservice_patameter_id='"+webservice_patameter_id+"'";
			int rsnum=table.executeUpdate(delSql);
			//System.out.println("rsnum="+rsnum);
			Recordset rs=context.getRecordset("record");
			
			//������ֵ
			if(rs!=null && rs.size()>0){
				int num=rs.size();
				for(int i=0;i<num;i++){
					TxnContext txnContext=new TxnContext();
					txnContext.addRecord("record", rs.get(i));
					//System.out.println("i="+i+"--txnContext="+txnContext);
					table.executeFunction( INSERT_FUNCTION, txnContext, inputNode, outputNode );
				}
			}
			
			
			
		}
		
		
	}
	
	/** ��ѯ�ɼ�����ֵ�����޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30107004( CollectWsParamValueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoCollectWsParamValuePrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoCollectWsParamValue result = context.getCollectWsParamValue( outputNode );
	}
	
	/** ɾ���ɼ�����ֵ��Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn30107005( CollectWsParamValueContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoCollectWsParamValuePrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		//System.out.println("inputNode="+inputNode);
		//System.out.println(context);
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );

		//callService("30103033", context);
		
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
		CollectWsParamValueContext appContext = new CollectWsParamValueContext( context );
		invoke( method, appContext );
	}
}
