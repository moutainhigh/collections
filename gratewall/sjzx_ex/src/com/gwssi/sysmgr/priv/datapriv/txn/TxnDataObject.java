package com.gwssi.sysmgr.priv.datapriv.txn;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import cn.gwssi.common.component.exception.ErrorConstant;
import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.sysmgr.priv.datapriv.PrivilegeManager;
import com.gwssi.sysmgr.priv.datapriv.vo.DataaccdispContext;
import com.gwssi.sysmgr.priv.datapriv.vo.DataaccgroupitemContext;
import com.gwssi.sysmgr.priv.datapriv.vo.DataobjectContext;
import com.gwssi.sysmgr.priv.datapriv.vo.FuncdataobjectContext;

public class TxnDataObject extends TxnService
{
	/**
	 * ҵ�����ṩ�����з���
	 */
	private static HashMap txnMethods = getAllMethod( TxnDataObject.class, DataobjectContext.class );
	
	// ���ݱ�����
	private static final String TABLE_NAME = "dataobject";
	
	// ��ѯ�б�
	private static final String ROWSET_FUNCTION = "select dataobject list";
	
	// ��ѯ��¼
	private static final String SELECT_FUNCTION = "select one dataobject";
	
	// �޸ļ�¼
	private static final String UPDATE_FUNCTION = "update one dataobject";
	
	// ���Ӽ�¼
	private static final String INSERT_FUNCTION = "insert one dataobject";
	
	// ɾ����¼
	private static final String DELETE_FUNCTION = "delete one dataobject";
	
	/**
	 * ���캯��
	 */
	public TxnDataObject()
	{
		
	}
	
	/** ��ʼ������
	 * @param context ����������
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** ��ѯ����Ȩ�����͹����б�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103011( DataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDataobjectSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		DataBus dataBus;
		String txncode;
		String functionIds;
		
		String objectid;//���ݶ������
		StringBuffer objectidBuf;
		
		for(int i=0;i<rs.size();i++){
			dataBus = rs.get(i);
			objectid = dataBus.getValue("objectid");
			//��ѯ��Ӧ�Ĺ���
			FuncdataobjectContext funObContext = new FuncdataobjectContext();//�½���ѯ�����Ķ���FuncdataobjectContext��
			DataBus funObDataBus = new DataBus();
			funObDataBus.setValue("objectid", objectid);//���ò�ѯ����ֵ
			funObContext.addRecord(inputNode,funObDataBus);// ���ò�ѯ����
			this.callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnFuncdataobject","txn103021", funObContext);//���÷���
			objectidBuf = new StringBuffer();
			for(int j=0;j<funObContext.getRecordset(outputNode).size();j++){
				txncode = funObContext.getRecordset(outputNode).get(j).getValue("funcode");
				//System.out.println("txncode:..."+txncode);
				objectidBuf.append(",");
				objectidBuf.append(txncode);
				txncode = null;
			}
			if(objectidBuf.length()!=0){
				functionIds = objectidBuf.substring(1);
				dataBus.setValue("functionIds", functionIds);
			}
			
		}
		System.out.println("");
		// ��ѯ���ļ�¼�� VoDataobject result[] = context.getDataobjects( outputNode );
	}
	
	/** �޸�����Ȩ�����͹�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103012( DataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// �޸ļ�¼������ VoDataobject dataobject = context.getDataobject( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��������Ȩ�����͹�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103013( DataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ���Ӽ�¼������ VoDataobject dataobject = context.getDataobject( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** ��ѯ����Ȩ�����͹��������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103014( DataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ��ѯ��¼������ VoDataobjectPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// ��ѯ���ļ�¼���� VoDataobject result = context.getDataobject( outputNode );
	}
	
	/** ɾ������Ȩ�����͹�����Ϣ
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103015( DataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// ɾ����¼�������б� VoDataobjectPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	/**
	 * ���湦�����ù�ϵ
	 * �������ݵ� Funcdataobject
	 * @param context
	 * @throws TxnException
	 */
	public void txn103016( DataobjectContext context ) throws TxnException
	{
		String objectid;
		String funcode;
		String functionIds;
		
		objectid = context.getRecord(inputNode).getValue("objectid");
		functionIds = context.getRecord(inputNode).getValue("functionIds");
		if(functionIds==null||functionIds.equals("")){
			throw new TxnDataException("999999","��ѡ���ܹ�ϵ��");
		}
		String[] functionIdArray = functionIds.split(",");
		Set set = new HashSet();
		
		FuncdataobjectContext funObContext = new FuncdataobjectContext();
		for(int i=0;i<functionIdArray.length;i++){
			funcode = functionIdArray[i];
			if(set.contains(funcode)){
				//�޳��ظ��Ľ���
			}else{
				DataBus funObDataBus = new DataBus();
				funObDataBus.setValue("objectid", objectid);
				funObDataBus.setValue("funcode", funcode);
				funObContext.addRecord(inputNode,funObDataBus);// ����
				set.add(funcode);
			}
			funcode = null;
		}
		//��������
		//��ɾ��
		try {
			this.callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnFuncdataobject","txn103025", funObContext);
		} catch (TxnException e) {
			System.out.println("ɾ��������");
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		//�ٱ���
		this.callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnFuncdataobject","txn103023", funObContext);

	}
	
	/** ��ѯ����Ȩ�����͹��������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103017( DataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String parentCode = context.getRecord(inputNode).getValue("parentcode");
		String rolefunids = context.getRecord(inputNode).getValue("rolefunids");
		String objectId = context.getRecord(inputNode).getValue("objectid");
		String dataaccdispobj = context.getRecord(inputNode).getValue("dataaccdispobj");
		
		String objectSource = null;
		TxnContext dataContext = new TxnContext();
		DataBus dataBus = new DataBus();
		dataBus.setValue("objectid", objectId);
		dataContext.addRecord(inputNode, dataBus);
		table.executeFunction("loadDataObjectById", dataContext, inputNode, outputNode);
		objectSource = dataContext.getRecord(outputNode).getString("objectsource");
		
		if(dataaccdispobj == null || dataaccdispobj.length() == 0) dataaccdispobj = "2";
		
		// �����Զ�������Ȩ�������Ѿ�ѡ������Ȩ����Ŀ
		DataaccdispContext customAccGrp = new DataaccdispContext();
		DataBus dbCustomGrp = new DataBus();
		dbCustomGrp.setValue("objectids", rolefunids);
		dbCustomGrp.setValue("dataaccdispobj", dataaccdispobj);
		customAccGrp.addRecord("select-key", dbCustomGrp);
		callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp", 
				"txn103049", customAccGrp);
		String dataAccGrpIds = "";
		Recordset rs = customAccGrp.getRecordset(outputNode);
		for(int j = 0; j < rs.size(); j++){
			if(j > 0) dataAccGrpIds += ",";
			dataAccGrpIds += (rs.get(j).getValue("dataaccgrpid"));
		}
		if(dataAccGrpIds.length() == 0) dataAccGrpIds = "-1";
		DataaccgroupitemContext dagic = new DataaccgroupitemContext();
		DataBus selectDb = new DataBus();
		selectDb.setValue("dataaccgrpid", dataAccGrpIds);
		dagic.addRecord(inputNode, selectDb);
		try{
			callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccgroupitem","txn103061",
				dagic);
		}catch(TxnException e){
			e.printStackTrace();
		}
		
		try{
			// ��databusת��XML����
			Document doc = DocumentHelper.parseText(dagic.toString());
			
			List list = PrivilegeManager.getInst().getPrivilegeItem(objectSource, parentCode);
			for(int i = 0; i < list.size(); i++){
				Map item = (Map) list.get(i);
				DataBus db = new DataBus();
				// �����ǰ����Ȩ�����Ѿ�����������Ȩ������
				try{
					if(doc.getRootElement().selectNodes(outputNode + 
							"[dataaccid=\"" + item.get("ID").toString().trim() + "\" and objectid=\"" +
							objectId + "\"]").size() > 0){
						db.setValue("checked", "true");
					}
					else{
						db.setValue("checked", "false");
					}
				}catch(Error e){
					e.printStackTrace();
				}
				db.setValue("id", item.get("ID").toString());
				db.setValue("objectsource", objectId);
				db.setValue("objectid", objectId);
				db.setValue("name", item.get("NAME").toString());
				db.setValue("code", item.get("CODE").toString());
				db.setValue("expand", item.get("BRANCHNUM").toString().equals("0") ? "true" : "false");
				context.addRecord(outputNode, db);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new TxnErrorException("txn103017",e.getMessage(),e.getCause()); 
		}
	}
	
	/** ��ѯ����Ȩ�����͹��������޸�
	 * @param context ����������
	 * @throws TxnException
	 */
	public void txn103018( DataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String objectSource = null;
		String parentCode = context.getRecord(inputNode).getValue("parentcode");
		String dataaccgrpid = context.getRecord(inputNode).getValue("dataaccgrpid");
		String objectId = context.getRecord(inputNode).getValue("objectid");
		
		TxnContext dataContext = new TxnContext();
		DataBus dataBus = new DataBus();
		dataBus.setValue("objectid", objectId);
		dataContext.addRecord(inputNode, dataBus);
		table.executeFunction("loadDataObjectById", dataContext, inputNode, outputNode);
		objectSource = dataContext.getRecord(outputNode).getString("objectsource");
		
		// �����Զ�������Ȩ�������Ѿ�ѡ������Ȩ����Ŀ
		if(dataaccgrpid == null || dataaccgrpid.length() == 0) dataaccgrpid = "-1";
		DataaccgroupitemContext dagic = new DataaccgroupitemContext();
		DataBus selectDb = new DataBus();
		selectDb.setValue("dataaccgrpid", dataaccgrpid);
		dagic.addRecord(inputNode, selectDb);
		try{
			callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccgroupitem","txn103061",
				dagic);
		}catch(TxnException e){
			e.printStackTrace();
		}
		
		try{
			// ��databusת��XML����
			Document doc = DocumentHelper.parseText(dagic.toString());
			
			List list = PrivilegeManager.getInst().getPrivilegeItem(objectSource, parentCode);
			for(int i = 0; i < list.size(); i++){
				Map item = (Map) list.get(i);
				DataBus db = new DataBus();
				// �����ǰ����Ȩ�����Ѿ�����������Ȩ������
				try{
					if(doc.getRootElement().selectNodes(outputNode + 
							"[dataaccid=\"" + item.get("ID").toString().trim() + "\" and objectid=\"" +
							objectId + "\"]").size() > 0){
						db.setValue("checked", "true");
					}
					else{
						db.setValue("checked", "false");
					}
				}catch(Error e){
					e.printStackTrace();
				}
				db.setValue("id", item.get("ID").toString());
				db.setValue("objectsource", objectId);
				db.setValue("objectid", objectId);
				db.setValue("name", item.get("NAME").toString());
				db.setValue("code", item.get("CODE").toString());
				db.setValue("expand", item.get("BRANCHNUM").toString().equals("0") ? "true" : "false");
				context.addRecord(outputNode, db);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new TxnErrorException("txn103017",e.getMessage(),e.getCause()); 
		}
	}
	/**
	 * ɾ��dataObject�Ĺ������ù�ϵ
	 * @param context
	 * @throws TxnException
	 */
	public void txn103019( DataobjectContext context ) throws TxnException
	{
		String objectid;
		
		objectid = context.getRecord(inputNode).getValue("objectid");
		FuncdataobjectContext funObContext = new FuncdataobjectContext();
		DataBus funObDataBus = new DataBus();
		funObDataBus.setValue("objectid", objectid);
		funObContext.addRecord(inputNode,funObDataBus);// ����
		try {
			this.callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnFuncdataobject","txn103025", funObContext);
		} catch (TxnException e) {
			System.out.println("ɾ��ʧ�ܣ�");
		}
//		for(int i=0;i<functionIdArray.length;i++){
//			funcode = functionIdArray[i];
//			DataBus funObDataBus = new DataBus();
//			funObDataBus.setValue("objectid", objectid);
//			funObDataBus.setValue("funcode", funcode);
//			funObContext.addRecord(inputNode,funObDataBus);// ����
//			
//			funcode = null;
//		}
		//��������
		//��ɾ��
		
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
		DataobjectContext appContext = new DataobjectContext( context );
		invoke( method, appContext );
	}
}
