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
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnDataObject.class, DataobjectContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "dataobject";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select dataobject list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one dataobject";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one dataobject";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one dataobject";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one dataobject";
	
	/**
	 * 构造函数
	 */
	public TxnDataObject()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询数据权限类型管理列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103011( DataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDataobjectSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		Recordset rs = context.getRecordset(outputNode);
		DataBus dataBus;
		String txncode;
		String functionIds;
		
		String objectid;//数据对象代码
		StringBuffer objectidBuf;
		
		for(int i=0;i<rs.size();i++){
			dataBus = rs.get(i);
			objectid = dataBus.getValue("objectid");
			//查询对应的功能
			FuncdataobjectContext funObContext = new FuncdataobjectContext();//新建查询上下文对象（FuncdataobjectContext）
			DataBus funObDataBus = new DataBus();
			funObDataBus.setValue("objectid", objectid);//设置查询条件值
			funObContext.addRecord(inputNode,funObDataBus);// 设置查询条件
			this.callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnFuncdataobject","txn103021", funObContext);//调用服务
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
		// 查询到的记录集 VoDataobject result[] = context.getDataobjects( outputNode );
	}
	
	/** 修改数据权限类型管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103012( DataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoDataobject dataobject = context.getDataobject( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 增加数据权限类型管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103013( DataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoDataobject dataobject = context.getDataobject( inputNode );
		table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询数据权限类型管理用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103014( DataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoDataobjectPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoDataobject result = context.getDataobject( outputNode );
	}
	
	/** 删除数据权限类型管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn103015( DataobjectContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 删除记录的主键列表 VoDataobjectPrimaryKey primaryKey[] = context.getPrimaryKeys( inputNode );
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
	}
	/**
	 * 保存功能配置关系
	 * 保存数据到 Funcdataobject
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
			throw new TxnDataException("999999","请选择功能关系！");
		}
		String[] functionIdArray = functionIds.split(",");
		Set set = new HashSet();
		
		FuncdataobjectContext funObContext = new FuncdataobjectContext();
		for(int i=0;i<functionIdArray.length;i++){
			funcode = functionIdArray[i];
			if(set.contains(funcode)){
				//剔除重复的交易
			}else{
				DataBus funObDataBus = new DataBus();
				funObDataBus.setValue("objectid", objectid);
				funObDataBus.setValue("funcode", funcode);
				funObContext.addRecord(inputNode,funObDataBus);// 设置
				set.add(funcode);
			}
			funcode = null;
		}
		//保存配置
		//先删除
		try {
			this.callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnFuncdataobject","txn103025", funObContext);
		} catch (TxnException e) {
			System.out.println("删除。。。");
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		//再保存
		this.callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnFuncdataobject","txn103023", funObContext);

	}
	
	/** 查询数据权限类型管理用于修改
	 * @param context 交易上下文
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
		
		// 根据自定义数据权限组获得已经选择数据权限项目
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
			// 将databus转成XML对象
			Document doc = DocumentHelper.parseText(dagic.toString());
			
			List list = PrivilegeManager.getInst().getPrivilegeItem(objectSource, parentCode);
			for(int i = 0; i < list.size(); i++){
				Map item = (Map) list.get(i);
				DataBus db = new DataBus();
				// 如果当前数据权限项已经存在于数据权限组中
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
	
	/** 查询数据权限类型管理用于修改
	 * @param context 交易上下文
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
		
		// 根据自定义数据权限组获得已经选择数据权限项目
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
			// 将databus转成XML对象
			Document doc = DocumentHelper.parseText(dagic.toString());
			
			List list = PrivilegeManager.getInst().getPrivilegeItem(objectSource, parentCode);
			for(int i = 0; i < list.size(); i++){
				Map item = (Map) list.get(i);
				DataBus db = new DataBus();
				// 如果当前数据权限项已经存在于数据权限组中
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
	 * 删除dataObject的功能配置关系
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
		funObContext.addRecord(inputNode,funObDataBus);// 设置
		try {
			this.callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnFuncdataobject","txn103025", funObContext);
		} catch (TxnException e) {
			System.out.println("删除失败！");
		}
//		for(int i=0;i<functionIdArray.length;i++){
//			funcode = functionIdArray[i];
//			DataBus funObDataBus = new DataBus();
//			funObDataBus.setValue("objectid", objectid);
//			funObDataBus.setValue("funcode", funcode);
//			funObContext.addRecord(inputNode,funObDataBus);// 设置
//			
//			funcode = null;
//		}
		//保存配置
		//先删除
		
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
		DataobjectContext appContext = new DataobjectContext( context );
		invoke( method, appContext );
	}
}
