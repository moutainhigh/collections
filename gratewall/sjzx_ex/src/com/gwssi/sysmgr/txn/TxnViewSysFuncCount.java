package com.gwssi.sysmgr.txn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import com.gwssi.common.util.ExcelWrite;
import com.gwssi.sysmgr.vo.ViewSysFuncCountContext;

import javax.servlet.http.HttpServletResponse;

public class TxnViewSysFuncCount extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnViewSysFuncCount.class, ViewSysFuncCountContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "view_sys_func_count";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select view_sys_func_count list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one view_sys_func_count";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one view_sys_func_count";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one view_sys_func_count";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one view_sys_func_count";
	
	private static final String[] FUNCNAMES = {"�򵥲�ѯ","�߼���ѯ","ͳ�Ʊ���","ȫ�ļ���"};
	private static final String[] QUXIAN = {"�����й��̾��оֻ���","���Ƿ־�","���Ƿ־�","���ķ־�","����־�","�����־�","��̨�־�","ʯ��ɽ�־�","����־�","��ͷ���־�","��ɽ�־�","ͨ�ݷ־�","˳��־�","��ƽ�־�","���˷־�","����־�","ƽ�ȷ־�","���Ʒ־�","����־�","��ɽ�־�"};
	/**
	 * ���캯��
	 */
	public TxnViewSysFuncCount()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ����ʹ��ͳ���б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60900001( ViewSysFuncCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "queryDataByDate", context, inputNode, outputNode );
		
		for(int i = 0; i < FUNCNAMES.length; i++){
			boolean found = false;
			Recordset rs = context.getRecordset(outputNode);
			while(rs.hasNext()){
				DataBus db = (DataBus)rs.next();
				String func_name = db.getValue("func_name");
				if(func_name.trim().equals(FUNCNAMES[i])){
					found = true;
					break;
				}
			}
			if(!found){
				DataBus db = new DataBus();
				db.setValue("func_name", FUNCNAMES[i]);
				db.setValue("querytimes", "0");
				db.setValue("primary-key", "func_name="+FUNCNAMES[i]+" sjjgid_fk=");
				context.addRecord(outputNode, db);
			}
		}
	}
	
	/** �ӹ���ʹ��ͳ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60900002( ViewSysFuncCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "querySub1DataByDate", context, inputNode, outputNode );
	}
	
	/** ���ӹ���ʹ��ͳ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60900003( ViewSysFuncCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "querySub2DataByDate", context, inputNode, outputNode );
		List busList = new ArrayList();
		for(int i = 0; i < QUXIAN.length; i++){
			boolean found = false;
			Recordset rs = context.getRecordset(outputNode);
			while(rs.hasNext()){
				DataBus db = (DataBus)rs.next();
				String sjjgid_fk = db.getValue("sjjgname");
				if(sjjgid_fk.trim().equals(QUXIAN[i])){
					busList.add(db);
					found = true;
				}
			}
			if(!found){
				DataBus db = new DataBus();
				db.setValue("sjjgname", QUXIAN[i]);
				db.setValue("querytimes", "0");
				db.setValue("primary-key", "func_name= sjjgid_fk="+QUXIAN[i]);
				busList.add(db);
			}
		}
		context.remove(outputNode);
		for(int i = 0; i < busList.size(); i++){
			DataBus db = (DataBus)busList.get(i);
			context.addRecord(outputNode, db);
		}
	}
	
	/** ���ڵ���
	 * @param context ����������
	 * @throws TxnException
	 * @throws IOException 
	 */
	public void txn620100208( ViewSysFuncCountContext context ) throws TxnException
	{
		
//		com.gwssi.common.util.ExcelHelper.writeExcel(out, db.toString());
//		response.setContentType("aplication/vnd.ms-excel");
//	    response.addHeader("Content-Disposition","inline; filename=" + new String(title.getBytes("GBK"),"ISO8859_1") + ".xls"); 
//		while(rs.hasNext()){
//			DataBus db = (DataBus)rs.next();
//			
//		}
	    BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "queryLogStatistics", context, inputNode, outputNode );
		
//		String[] Titles = {"���ܴ���","����С��","�����־�","ʹ�ô���"} ;
//		String[][] Contents;
//		Recordset rs = context.getRecordset(outputNode);
//        File tempFile=new File("d:/temp/output23.xls");
//		OutputStream os=null;
//		try {
//			os = new FileOutputStream(tempFile);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Contents=new String[rs.size()][5];
//		
//		for(int i=0;i<rs.size();i++)
//		{
//			DataBus db = (DataBus)rs.get(i);
//			Contents[i][0]=db.getValue("first_func_name");
//			Contents[i][1]=db.getValue("func_name");
//			Contents[i][2]=db.getValue("sjjgname");
//			Contents[i][3]=db.getValue("querytimes");
//		}
//		ExcelWrite ew = new ExcelWrite();
//		try {
//			ew.expordExcel(os, "��һҳ", Titles, Contents);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	/** �û�ʹ����־ͳ��
	 * @param context ����������
	 * @throws TxnException
	 * @throws IOException 
	 */
	public void txn620100201( ViewSysFuncCountContext context) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		//Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( "queryLogStatistics", context, inputNode, outputNode );
		


		/*List busList = new ArrayList();
		for(int i = 0; i < QUXIAN.length; i++){
			boolean found = false;
			Recordset rs = context.getRecordset(outputNode);
			while(rs.hasNext()){
				DataBus db = (DataBus)rs.next();
				String sjjgid_fk = db.getValue("sjjgname");
				if(sjjgid_fk.trim().equals(QUXIAN[i])){
					busList.add(db);
					found = true;
				}
			}
			if(!found){
				DataBus db = new DataBus();
				db.setValue("sjjgname", QUXIAN[i]);
				db.setValue("querytimes", "0");
				db.setValue("primary-key", "func_name= sjjgid_fk="+QUXIAN[i]);
				busList.add(db);
			}
		}
		context.remove(outputNode);
		for(int i = 0; i < busList.size(); i++){
			DataBus db = (DataBus)busList.get(i);
			context.addRecord(outputNode, db);
		}*/
	}
	
	/** ��ѯ����ʹ��ͳ�������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60900004( ViewSysFuncCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoViewSysFuncCountPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoViewSysFuncCount result = context.getViewSysFuncCount( outputNode );
	}
	
	/** ɾ������ʹ��ͳ����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn60900005( ViewSysFuncCountContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoViewSysFuncCountPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
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
		ViewSysFuncCountContext appContext = new ViewSysFuncCountContext( context );
		invoke( method, appContext );
	}
}
