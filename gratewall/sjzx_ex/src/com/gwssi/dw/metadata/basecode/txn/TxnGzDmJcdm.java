package com.gwssi.dw.metadata.basecode.txn;

import java.lang.reflect.Method;
import java.util.HashMap;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnMasterTable;

import com.gwssi.dw.metadata.basecode.vo.GzDmJcdmContext;

public class TxnGzDmJcdm extends TxnMasterTable
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnGzDmJcdm.class, GzDmJcdmContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "gz_dm_jcdm";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select gz_dm_jcdm list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one gz_dm_jcdm";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one gz_dm_jcdm";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one gz_dm_jcdm";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one gz_dm_jcdm";

	// ��ϸ��
	private static final String detailTables[][] = {
		
		// ������Ϊ�˴������һ�� [,]������ɾ��
		{ null, null }
	};
	
	/**
	 * ���캯��
	 */
	public TxnGzDmJcdm()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ���������б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn301011( GzDmJcdmContext context ) throws TxnException
	{
		DataBus db = context.getRecord(inputNode);
		String jc_dm_dm = db.getValue("jc_dm_dm");
		if(jc_dm_dm != null && !jc_dm_dm.equals("")){
			jc_dm_dm = jc_dm_dm.toUpperCase();
			db.setValue("jc_dm_dm", jc_dm_dm);
		}
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoGzDmJcdmSelectKey selectKey = context.getSelectKey( inputNode );
		//loadMasterTable( table, ROWSET_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼�� VoGzDmJcdm result[] = context.getGzDmJcdms( outputNode );
		//table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		table.executeFunction( "selectSystemCodeTable", context, inputNode, outputNode );
		// �ӱ�
//		for( int ii=0; ii<detailTables.length; ii++ ){
//			String detail[] = detailTables[ii];
//			if( detail[0] == null || detail[0].length() == 0 ){
//				continue;
//			}
//			
//			try{
//				loadDetailTable( detail[1], context, inputNode, detail[0] );
//			}
//			catch( Exception e ){
//				log.warn( "������ϸ��[" + detail[0] + "]����", e );
//			}
//		}
	}

	
	/** ��ѯ���������б�_����
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn301016( GzDmJcdmContext context ) throws TxnException
	{
		Attribute.setPageRow(context, outputNode, 5);
	    callService("301011", context);
	}
	/** �޸Ļ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn301012( GzDmJcdmContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		
		DataBus dataBus = context.getRecord("record");
		String jc_dm_id = dataBus.getValue("jc_dm_id");
		String dm = dataBus.getValue("jc_dm_dm");
		String jc_dm_mc = dataBus.getValue("jc_dm_mc");
		
		GzDmJcdmContext oldContext  = new GzDmJcdmContext();
		DataBus oldDb = oldContext.getRecord("primary-key");
		oldDb.setValue("jc_dm_id", jc_dm_id);	
		table.executeFunction( SELECT_FUNCTION, oldContext, "primary-key", "record" );
		String oldDm = oldContext.getRecord("record").getValue("jc_dm_dm");
		if(!dm.equals(oldDm)){
			table.executeFunction( "verifyJcdmdm", context, "record", "record" );
			DataBus queryDataBus = context.getRecord("record");
			String count = queryDataBus.getValue("count");
			if(count!=null&&!count.equals("0")){
				throw new TxnDataException("","����������룺"+dm+"�Ѵ��ڣ��������������룡");
			}
		}
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		setBizLog("�޸Ļ������룺", context,dm + "-" + jc_dm_mc);
	}

	/** ���ӻ���������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn301013( GzDmJcdmContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( "verifyJcdmdm", context, "record", "record" );
		DataBus dataBus = context.getRecord("record");		
		String dm = dataBus.getValue("jc_dm_dm");
		
		if(dm != null && !dm.equals("")){
			dm = dm.toUpperCase();
			dataBus.setValue("jc_dm_dm", dm);
		}
		String jc_dm_mc = dataBus.getValue("jc_dm_mc");
		String count = dataBus.getValue("count");
		if(count!=null&&!count.equals("0")){
			throw new TxnDataException("","����������룺"+dm+"�Ѵ��ڣ��������ӣ�");
		}
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		setBizLog("���ӻ������룺", context,dm + "-" + jc_dm_mc);
	}

	/** ��ѯ�������������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn301014( GzDmJcdmContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}

	/** ɾ������������Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn301015( GzDmJcdmContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );	
		Recordset rs = context.getRecordset("select-key");
		DataBus dataBus = null;
		DataBus countBus = null;
		GzDmJcdmContext gzDmJcdmContext = null;
		String jc_dm_mc = "";
		String jc_dm_dm = "";
		String count = "";
		String errorString  = "";	
		String log_desc = "";
		for(int i=0; i<rs.size();i++){
			dataBus = rs.get(i);
			jc_dm_mc = dataBus.getValue("jc_dm_mc");
			jc_dm_dm = dataBus.getValue("jc_dm_dm");
			gzDmJcdmContext = new GzDmJcdmContext();
			gzDmJcdmContext.addRecord("select-key", dataBus);
			table.executeFunction( "selectSjfx", gzDmJcdmContext, "record", "record" );
			countBus = gzDmJcdmContext.getRecord("record");
			count = countBus.getValue("count");
			if(count!=null&&!count.equals("0")){
				errorString+=jc_dm_mc+";";
			}	
			log_desc+="," + jc_dm_dm + "-" +jc_dm_mc;
		}
		if(!errorString.equals("")){
			errorString = errorString.substring(0,errorString.length()-1);
			throw new TxnDataException("","��������:" + errorString + " �ѱ�ʹ�ã�����ɾ��;");
		}
		if(!log_desc.equals("")) log_desc = log_desc.substring(1);
		// ɾ����¼�������б� VoGzDmJcdmPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		 table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		 setBizLog("ɾ���������룺", context,log_desc);
	}
	/**
	 * ��¼��־
	 * @param type
	 * @param context
	 */
    private void setBizLog (String type,TxnContext context,String jgmc){
    	
    	context.getRecord("biz_log").setValue("desc", type + jgmc);
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
		GzDmJcdmContext appContext = new GzDmJcdmContext( context );
		invoke( method, appContext );
	}
}
