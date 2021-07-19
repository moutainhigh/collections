package com.gwssi.sysmgr.priv.datapriv.txn;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

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
import cn.gwssi.common.txn.TxnService;

import com.gwssi.sysmgr.priv.datapriv.PrivilegeManager;
import com.gwssi.sysmgr.priv.datapriv.vo.DataaccdispContext;
import com.gwssi.sysmgr.priv.datapriv.vo.DataaccgroupContext;
import com.gwssi.sysmgr.priv.datapriv.vo.DataaccgroupitemContext;

public class TxnDataaccgroup extends TxnService
{
	/**
	 * 业务类提供的所有方法
	 */
	private static HashMap txnMethods = getAllMethod( TxnDataaccgroup.class, DataaccgroupContext.class );
	
	// 数据表名称
	private static final String TABLE_NAME = "dataaccgroup";
	
	// 查询列表
	private static final String ROWSET_FUNCTION = "select dataaccgroup list";
	
	// 查询记录
	private static final String SELECT_FUNCTION = "select one dataaccgroup";
	
	// 修改记录
	private static final String UPDATE_FUNCTION = "update one dataaccgroup";
	
	// 增加记录
	private static final String INSERT_FUNCTION = "insert one dataaccgroup";
	
	// 删除记录
	private static final String DELETE_FUNCTION = "delete one dataaccgroup";
	
	/**
	 * 构造函数
	 */
	public TxnDataaccgroup()
	{
		
	}
	
	/** 初始化函数
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	protected void prepare(TxnContext context) throws TxnException
	{
		
	}
	
	/** 查询数据权限组管理列表
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn1030041( DataaccgroupContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDataaccgroupSelectKey selectKey = context.getSelectKey( inputNode );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		System.out.println(Attribute.getTotalRow(context, "record"));
		// 查询到的记录集 VoDataaccgroup result[] = context.getDataaccgroups( outputNode );
	}
	
	/** 修改数据权限组管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn1030042( DataaccgroupContext context ) throws TxnException
	{
		String dataaccgrpid = null;
		String itemXml = context.getRecord(inputNode).getValue("saveXml");
		
		try {
			dataaccgrpid = context.getRecord(inputNode).getValue("dataaccgrpid");
		} catch (RuntimeException e) {
			dataaccgrpid = null;
		}
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 修改记录的内容 VoDataaccgroup dataaccgroup = context.getDataaccgroup( inputNode );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		this.saveGroupItem(dataaccgrpid, itemXml);
	}
	
	/** 增加数据权限组管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn1030043( DataaccgroupContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String dataaccgrpid = null;
		String itemXml = context.getRecord(inputNode).getValue("saveXml");
		
		try {
			dataaccgrpid = context.getRecord(inputNode).getValue("dataaccgrpid");
		} catch (RuntimeException e) {
			dataaccgrpid = null;
		}
		if(dataaccgrpid==null||dataaccgrpid.equals("")){
			table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
		}else{
			table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
		}
		dataaccgrpid = context.getRecord(outputNode).getValue("dataaccgrpid");
		this.saveGroupItem(dataaccgrpid, itemXml);
		// 增加记录的内容 VoDataaccgroup dataaccgroup = context.getDataaccgroup( inputNode );
		//table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
	}
	
	/** 查询数据权限组管理用于修改
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn1030044( DataaccgroupContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的主键 VoDataaccgroupPrimaryKey primaryKey = context.getPrimaryKey( inputNode );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录内容 VoDataaccgroup result = context.getDataaccgroup( outputNode );
	}
	
	/** 删除数据权限组管理信息
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn1030045( DataaccgroupContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 增加记录的内容 VoDataaccgroup dataaccgroup = context.getDataaccgroup( inputNode );
		//首先查询关联，有关联数据权限的权限组的不能删除
		String dataaccgrpid = context.getRecord(inputNode).getValue("dataaccgrpid");
		TxnContext checkContext = new TxnContext();
		DataBus checkDb = new DataBus();
		checkDb.setValue("dataaccgrpid", dataaccgrpid);
		checkContext.addRecord(inputNode, checkDb);
		
		table.executeFunction( "dataaccgroupFpCheck", checkContext, inputNode, outputNode );
		
		if(checkContext.getRecordset(outputNode).size()>0){
			throw new TxnDataException("999999","此权限组已经被引用，不能删除！");
		}
		
		table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		TxnContext delete = new TxnContext();
		DataBus db = new DataBus();
		db.setValue("dataaccgrpid", dataaccgrpid);
		delete.addRecord(inputNode, db);
		// 删除数据权限组项
		callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccgroupitem", "txn103065", delete);
		// 删除分配给角色或功能的分组项
		callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp", "txn1030410", delete);
		
	}
	
	/** 保存分配给角色功能的数据权限组
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn1030046( DataaccgroupContext context ) throws TxnException
	{
		String itemXml = context.getRecord(inputNode).getValue("saveXml");
		String dataaccrule = context.getRecord(inputNode).getValue("dataaccrule");
		String selectGroups = context.getRecord(inputNode).getValue("selectGroups");
		String rolefunids = context.getRecord(inputNode).getValue("rolefunids");
		
		String[] rolefunid = rolefunids.split(",");
		for(int i = 0; i < rolefunid.length; i++){
			TxnContext customAccGrp = new TxnContext();
			DataBus db = new DataBus();
			db.setValue("objectids", rolefunid[i]);
			db.setValue("dataaccdispobj", "2");
			customAccGrp.addRecord("select-key", db);
			callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp", 
					"txn103049", customAccGrp);
			Recordset rs = customAccGrp.getRecordset(outputNode);
			String dataaccgrpid = "";
			if(rs.size() > 0) dataaccgrpid = rs.get(0).getValue("dataaccgrpid");
			dataaccgrpid = saveCustomGroup(dataaccgrpid,dataaccrule,itemXml);
			//saveGroupItem(dataaccgrpid,itemXml);
			String selectGroupList = dataaccgrpid;
			if(selectGroups  != null && selectGroups.length() > 0)
				selectGroupList += "," + selectGroups;
			saveDataAccDisp(rolefunid[i],selectGroupList,"2");
		}
		try{
			PrivilegeManager.getInst().init("统计制度");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void saveDataAccDisp(String rolefunid,String selectGroups,String dataaccdispobj) throws TxnException{
		String[] groupids = selectGroups.split(",");
		DataaccdispContext deleteContext = new DataaccdispContext();
		DataBus db = new DataBus();
		deleteContext.addRecord("record", db);
		db.setValue("objectid", rolefunid);
		db.setValue("dataaccdispobj", dataaccdispobj);
		callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp",
					"txn103047",deleteContext);
			
		DataaccdispContext saveContext = new DataaccdispContext();
		for(int i = 0; i < groupids.length; i++){
			db = new DataBus();
			db.setValue("objectid", rolefunid);
			db.setValue("dataaccgrpid", groupids[i]);
			db.setValue("dataaccdispobj", dataaccdispobj);
			saveContext.addRecord("record", db);
		}
		callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp",
					"txn103043",saveContext);
	}
	
	private void saveGroupItem(String dataaccgrpid,String itemXml) throws TxnException{
		try{
			Document doc = DocumentHelper.parseText(itemXml);
			boolean  exist = false;
			System.out.println(doc.asXML());
			List item = doc.getRootElement().selectNodes("object");
			for(int i = 0 ; i < item.size(); i++){
				Element object = (Element) item.get(i);
				String objectid = object.attributeValue("objectid");
				String dataaccid = object.attributeValue("dataaccid");
				String checked = object.attributeValue("checked");
				DataaccgroupitemContext itemContext = new DataaccgroupitemContext();
				DataBus db = new DataBus();
								
				itemContext.addRecord(inputNode, db);
				db.setValue("dataaccgrpid", dataaccgrpid);
				db.setValue("objectid", objectid);
				db.setValue("dataaccid", dataaccid);
				
				exist = isExistByCode(dataaccgrpid,objectid,dataaccid);
				
				if(checked.equals("true")){
					// 新增
					if(!exist){
						callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccgroupitem",
								"txn103063",itemContext);
					}
				}
				else{
					if(exist){
						// 删除
						try {
							if(isExistByCode(dataaccgrpid,objectid,dataaccid))
							callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccgroupitem",
									"txn103066",itemContext);
						} catch (TxnException e) {
							
						}
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			throw new TxnErrorException("txn103017",e.getMessage(),e.getCause()); 
		}
	}
	
	/**
	 * 查询数据权限分组项是否存在
	 * @param roleId
	 * @param txnCode
	 * @return
	 * @throws TxnException
	 */	
	private boolean isExistByCode(String dataaccgrpid,String objectid,String dataaccid)throws TxnException{
		
		boolean bl = false;
		DataaccgroupitemContext itemContext = new DataaccgroupitemContext();
		BaseTable table = TableFactory.getInstance().getTableObject( this, "dataaccgroupitem" );
		DataBus db = new DataBus();		
		itemContext.addRecord(inputNode, db);
		db.setValue("dataaccgrpid", dataaccgrpid);
		db.setValue("objectid", objectid);
		db.setValue("dataaccid", dataaccid);
		table.executeFunction( "findByPramaryKey", itemContext, inputNode, outputNode );
		if(itemContext.getRecordset(outputNode).size()>0){
			bl = true;
		}		
		return bl;
	}	
	
	private String saveCustomGroup(String dataaccgrpid,String dataaccrule,String itemXml) throws TxnException{
		DataaccgroupContext customGroup = new DataaccgroupContext();
		DataBus db = new DataBus();
		customGroup.addRecord(inputNode, db);
		db.setValue("dataaccgrpname", "自定义数据权限组");
		db.setValue("dataaccrule", dataaccrule);
		db.setValue("dataacctype", "0");
		db.setValue("dataaccgrpdesc", "");
		db.setValue("saveXml", itemXml);
		DataBus dbKey = new DataBus();
		customGroup.addRecord("primary-key", dbKey);
		if(dataaccgrpid == null || dataaccgrpid.length() == 0){	// ID为空
			doService("txn1030043",customGroup);
		}
		else{
			db.setValue("dataaccgrpid", dataaccgrpid);
			dbKey.setValue("dataaccgrpid", dataaccgrpid);
			doService("txn1030042",customGroup);
		}
		return db.getValue("dataaccgrpid");
	}

	/**
	 * 查询记录不分页
	 * @param context
	 * @throws TxnException
	 */
	public void txn1030047( DataaccgroupContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		// 查询记录的条件 VoDataaccgroupSelectKey selectKey = context.getSelectKey( inputNode );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		// 查询到的记录集 VoDataaccgroup result[] = context.getDataaccgroups( outputNode );
	}
	
	/** 保存分配给角色的数据权限组
	 * @param context 交易上下文
	 * @throws TxnException
	 */
	public void txn1030048( DataaccgroupContext context ) throws TxnException
	{
		String itemXml = context.getRecord(inputNode).getValue("saveXml");
		String dataaccrule = context.getRecord(inputNode).getValue("dataaccrule");
		String selectGroups = context.getRecord(inputNode).getValue("selectGroups");
		String roleids = context.getRecord(inputNode).getValue("roleids");
		
		String[] roleid = roleids.split(",");
		for(int i = 0; i < roleid.length; i++){
			TxnContext customAccGrp = new TxnContext();
			DataBus db = new DataBus();
			db.setValue("objectids", roleid[i]);
			db.setValue("dataaccdispobj", "1");
			customAccGrp.addRecord("select-key", db);
			callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp", 
					"txn103049", customAccGrp);
			Recordset rs = customAccGrp.getRecordset(outputNode);
			String dataaccgrpid = "";
			if(rs.size() > 0) dataaccgrpid = rs.get(0).getValue("dataaccgrpid");
			dataaccgrpid = saveCustomGroup(dataaccgrpid,dataaccrule,itemXml);
			//saveGroupItem(dataaccgrpid,itemXml);
			String selectGroupList = dataaccgrpid;
			if(selectGroups  != null && selectGroups.length() > 0)
				selectGroupList += "," + selectGroups;
			saveDataAccDisp(roleid[i],selectGroupList,"1");
		}
		try{
			PrivilegeManager.getInst().init("统计制度");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 根据角色查询角色列表查询权限组
	 * @param context
	 * @throws TxnException
	 */
	public void txn1030049( DataaccgroupContext context ) throws TxnException
	{
		String roleids = context.getRecord(inputNode).getValue("roleids");
		
		// 查询自定义权限组
		TxnContext customAccGrp = new TxnContext();
		DataBus db = new DataBus();
		db.setValue("objectids", roleids);
		db.setValue("dataaccdispobj", "1");
		customAccGrp.addRecord("select-key", db);
		callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp", 
				"txn103049", customAccGrp);
		String accRule = customAccGrp.getRecord(outputNode).getValue("dataaccrule");

		
		//根据角色查询角色对应的权限分组
		TxnContext qTxnContext = new TxnContext();
		DataBus qDataBus = new DataBus();
		qDataBus.setValue("roleids", roleids);
		qTxnContext.addRecord("select-key", qDataBus);
		this.callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp", "txn1030411", qTxnContext);
		Recordset rs = qTxnContext.getRecordset(outputNode);
		StringBuffer selectGroupsBuf = new StringBuffer();
		//DATAACCGRPID dataccgrpid
		String selectGroups = "";
		String dataaccgrpid;
		String dataacctype;
		DataBus tempBus;
		for(int i=0;i<rs.size();i++){
			tempBus = rs.get(i);
			dataaccgrpid = tempBus.getValue("dataaccgrpid");
			dataacctype = tempBus.getValue("dataacctype");
			if(!dataacctype.equals("0")){//跳过自定义项
				selectGroupsBuf.append(",");
				selectGroupsBuf.append(dataaccgrpid);
			}
			dataaccgrpid = null;
			tempBus = null;
		}
		if(selectGroupsBuf.length()>0){
			selectGroups = selectGroupsBuf.substring(1);
		}
		context.getRecord(inputNode).setProperty("selectGroups", selectGroups);
		context.getRecord(inputNode).setProperty("dataaccrule", accRule);
		
		System.out.println("context:"+context);
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
		DataaccgroupContext appContext = new DataaccgroupContext( context );
		invoke( method, appContext );
	}
}
