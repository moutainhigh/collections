package com.gwssi.sysmgr.role.txn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.TxnService;

import com.gwssi.dw.dq.ldap.LDAPControl;
import com.gwssi.dw.dq.ldap.LDAPUser;
import com.gwssi.sysmgr.priv.datapriv.PrivilegeManager;
import com.gwssi.sysmgr.role.vo.OperrolefunContext;

public class TxnOperrole extends TxnService
{
   // 数据表名称
   private final String TABLE_NAME = "operrole";

   // 查询列表
   private final String ROWSET_FUNCTION = "select operrole list";

   // 查询记录
   private final String SELECT_FUNCTION = "select one operrole";

   // 修改记录
   private final String UPDATE_FUNCTION = "update one operrole";

   // 增加记录
   private final String INSERT_FUNCTION = "insert one operrole";

   // 删除记录
   private final String DELETE_FUNCTION = "delete one operrole";

   // 修改记录状态
   private final String STATUS_FUNCTION = "update status";
   
   // 设置功能列表
   private final String SET_FUNCLIST_FUNCTION = "set funclist";
   
   // 修改角色状态
   private final String UPDATE_STATUS_FUNCTION = "updateStatus";

   public TxnOperrole()
   {
      
   }

   /** 初始化函数
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }

   /** 查询列表
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980321( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
	}
		 
   /** 修改记录
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980322( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}

   /** 增加记录
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980323( TxnContext context ) throws TxnException
	{		
			BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
			
			TxnContext checkContext = new TxnContext();
			DataBus dataBus = new DataBus();
			String rolename = context.getRecord(inputNode).getValue("rolename");
			checkContext.addRecord("select-key", dataBus);
			dataBus.setValue("rolename", rolename);
			table.executeFunction( "checkName", checkContext, "record", "record" );			
			DataBus countBus = checkContext.getRecord("record");
			String count = countBus.getValue("count");
			
			if(count!=null&&!count.equals("0")){
				throw new TxnDataException("","角色名称已存在！");
			}else{			
			    table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
			    setBizLog("增加角色：", context,context.getRecord("record").getValue("rolename"));
			}
	}

   /** 查询记录
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980324( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}

   /** 删除记录
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980325( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		/**
		 * 用户如果引用了这个角色作为主角色，不允许删除
		 */
		String roleid = context.getRecord(inputNode).getValue("roleid");
		BaseTable table1 = TableFactory.getInstance().getTableObject(this,"XT_ZZJG_YH");
		String sql = "select count(0) as countyh from xt_zzjg_yh_new where roleids like '%"+ roleid +"%' and SFYX='0'";
		log.debug("sql:"+sql);
		table1.executeRowset(sql, context, "result");
		DataBus db_yh = context.getRecord("result");
		String countyh = db_yh.getValue("countyh");
		
		log.debug("countyh:"+countyh);
		if(!countyh.equals("0")){
			throw new TxnDataException("","用户管理中引用了该角色，无法删除角色！");
		}else{
			TxnContext queryContext = (TxnContext) context.clone();
			table.executeFunction( SELECT_FUNCTION, queryContext, inputNode, "operrole" );
			String roleId = queryContext.getRecord("operrole").getValue("roleid");
			delRoleFun(roleId);
			table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		}
		try{
			PrivilegeManager.getInst().init("统计制度");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/** 修改角色状态
	 @param context 交易上下文
	 @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn980326( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( STATUS_FUNCTION, context, inputNode, outputNode );
		setBizLog("修改角色：", context,context.getRecord("record").getValue("rolename"));
	}

	/** 修改角色的功能列表
	 @param context 交易上下文
	 @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn980327( TxnContext context ) throws TxnException
	{
//		DataBus dataBus = context.getRecord("record");
//		String roleId = dataBus.getValue("roleid");
//		String txnCodeList = dataBus.getValue("funclist");
//		delRoleFun(roleId,txnCodeList);
//		if(txnCodeList!=null&&!txnCodeList.equals("")){
//			delRoleFun(roleId,txnCodeList);
//		    addRoleFun(roleId,txnCodeList);
//		}else{
//			delRoleFun(roleId);
//		}		
		
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		String roleid = context.getRecord(inputNode).getValue("roleid");
		//String status = context.getRecord(inputNode).getValue("status");
		String funclist = context.getRecord(inputNode).getValue("funclist");
		
		/* jufneg 20120428
		if (funclist != null && !"".equals(funclist)) {//判断是否包含报表权限，否则不更新报表权限 修改于2010年04月12日 by:刘朔、王燕新
			String[] funcs = funclist.split(";");
			for(int i = 0; i < funcs.length; i++){
				if(funcs[i].startsWith("4")){
					StringBuffer groupIds = transFunclistToGroupId(funclist);
					log.debug("修改角色报表功能权限:" + groupIds);
					updateCognosUserByRoleId(table, roleid, groupIds);
					break;
				}
			}
		}
		*/
		table.executeFunction( SET_FUNCLIST_FUNCTION, context, inputNode, outputNode );
		setBizLog("修改角色权限：", context,context.getRecord("record").getValue("rolename"));	
				
		
//		try{
//			PrivilegeManager.getInst().init("统计制度");
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
	}

	private StringBuffer transFunclistToGroupId(String funclist)
	{
		StringBuffer groupIds = new StringBuffer();
		if (funclist == null || "".equals(funclist)) {
			return groupIds;
		}
		String[] funcinfo = funclist.split(";");
		for (int j = 0; funcinfo != null && j < funcinfo.length; j++) {
			String func = funcinfo[j];
			if (func.startsWith("413")) {
				if (groupIds.length() > 0) {
					groupIds.append(",");
				}
				groupIds.append(Integer.parseInt(func.substring(func
						.length() - 2, func.length())));
			}
		}
		return groupIds;
	}

	private void updateCognosUserByRoleId(BaseTable table, String roleid, StringBuffer groupIds) throws TxnException
	{
		int count = 0;
		TxnContext ctx = new TxnContext();
		try {
			StringBuffer sql = new StringBuffer("select yhid_pk,yhxm,roleids from xt_zzjg_yh_new where ");
			log.debug("roleid="+roleid);
			if(roleid.indexOf(",")>-1){
				String[] r_id = roleid.split(",");
				StringBuffer condition = new StringBuffer();
				for(int i=0;i<r_id.length;i++){
					if(condition.length()>0){
						condition.append(" or ");
					}
					condition.append( " roleids like '%").append(r_id[i]).append("%'");
				}
				sql.append(condition);
			}else{
				sql.append( " roleids like '%").append(roleid).append("%'");
			}
			count = table.executeRowset(sql.toString(), ctx, "userlist");
		} catch (TxnException e) {
			log.debug("没有记录");
		}
		if (count > 0) {
			Recordset userRs = ctx.getRecordset("userlist");
			List list = new ArrayList();
			for (int idx = 0; idx < userRs.size(); idx++) {
				StringBuffer strBuf = new StringBuffer();
				DataBus tmp = userRs.get(idx);
				String userid = tmp.getValue("yhid_pk");
				String yhxm = tmp.getValue("yhxm");
				String userroleids = tmp.getValue("roleids");
				setGroupIds(table, userroleids, roleid, strBuf);
				if (strBuf.length() > 0) {
					if (groupIds.length() > 0) {
						strBuf.append(",").append(groupIds);
					}
				} else {
					if (groupIds.length() > 0) {
						strBuf.append(groupIds);
					}
				}
				log.debug("修改用户" + yhxm + "的功能权限:" + strBuf);
				list.add(new LDAPUser(strBuf.toString(), userid, yhxm));
			}
			boolean flag = addUserToCognos(list);
			if (!flag) {
				throw new TxnDataException("", "根据角色修改cognos用户失败!");
			}

		}
	}
    private boolean addUserToCognos(List list){
		LDAPControl control = new LDAPControl();
		return control.insertOrUpdateUserWithGroups(list);
    }	
	private void setGroupIds(BaseTable table, String roleIds, String roleid, StringBuffer groupIds) throws TxnException
	{
		log.debug("setGroupIds-roleIds"+roleIds);
		log.debug("setGroupIds-roleid"+roleid);
		if(roleIds==null||"".equals(roleIds)){
			return;
		}
		StringBuffer roleIdBuf = new StringBuffer();
		String[] arr = roleIds.split(",");
		
		for(int i=0;arr!=null&&i<arr.length;i++){
			if(arr[i]==null||"".equals(arr[i])){
				continue;
			}
			if(roleid.indexOf(arr[i])==-1){
				if(roleIdBuf.length()>0){
					roleIdBuf.append(",");
				}			
				roleIdBuf.append(arr[i]);	
			}
		}
		
		if(roleIdBuf.length()==0){
			return;
		}
		
		int count = 0;
		TxnContext context = new TxnContext();
		try {
			String sql = "select funclist  from operrole_new  " +
			" where roleid in (" + roleIdBuf +")" ;
			count = table.executeRowset(sql, context, "funclists");
		} catch (TxnException e) {
			log.debug("没有记录");
		}
		if(count>0){
			Recordset rs = context.getRecordset("funclists");
			for(int i=0;i<rs.size();i++){
				DataBus db = rs.get(i);
				String funclist = db.getValue("funclist");
				StringBuffer g_id = transFunclistToGroupId(funclist);
				if(g_id.length()>0){
					if(groupIds.length()>0){
						groupIds.append(",");
					}
					groupIds.append(g_id);
				}	
			}
		}
	}
	/** 角色复制
	 @param context 交易上下文
	 @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn980328( TxnContext context ) throws TxnException
	{
		//String sourceRoleId = context.getRecord(inputNode).getValue("roleid");
		String sourceRoleName = context.getRecord(inputNode).getValue("rolename");
		String newRoleName = context.getRecord(inputNode).getValue("rolename_target");
		String newRoleDesc = context.getRecord(inputNode).getValue("description_target");
		TxnContext copyRole = new TxnContext();
		DataBus db = new DataBus();
		db.setValue("rolename", sourceRoleName);
		copyRole.addRecord(inputNode,db);
		doService("txn980324",copyRole);		// 查询源角色基本信息
		copyRole.getRecord(inputNode).setValue("rolename", newRoleName);
		copyRole.getRecord(inputNode).setValue("description", newRoleDesc);
		doService("txn980323", copyRole);		// 新增复制角色基本信息
		// 修改
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SET_FUNCLIST_FUNCTION, copyRole, inputNode, outputNode );
		setBizLog("复制角色：", context,newRoleName);
		//String newRoleId = copyRole.getRecord(outputNode).getValue("roleid");
		//copyRoleTxn(sourceRoleId, newRoleId);
	}
	
	/** 查询列表，不分页
    @param context 交易上下文
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980329( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		
	}
	/**
	 * 禁用角色
	 * @param context
	 * @throws TxnException
	 */
	
	public void txn980330( TxnContext context ) throws TxnException{
		
        BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);   
        Recordset rs = context.getRecordset(inputNode);
        String roleid = "";
        String rolename = "";
        if (rs != null && !rs.isEmpty()) {
        	StringBuffer roleIdBuf = new StringBuffer();
            for (int i = 0; i < rs.size(); i++) {
            	DataBus db = rs.get(i);
            	String roleids = db.getValue("roleid");
            	String funclist = db.getValue("funclist");
            	log.debug("funclist="+funclist);
            	StringBuffer groupIds = transFunclistToGroupId(funclist);
            	if(groupIds.length()>0){
            		if(roleIdBuf.length()>0){
            			roleIdBuf.append(",");
            		}
            		roleIdBuf.append(roleids);
            	}
            	roleid+=","+roleids;
            	rolename+=","+db.getValue("rolename");
            }
            if(roleIdBuf.length()>0){
            	updateCognosUserByRoleId(table,roleIdBuf.toString(),new StringBuffer());
            }
        } 
        if(!roleid.equals("")) roleid = roleid.substring(1);
        if(!rolename.equals("")) rolename = rolename.substring(1);
        TxnContext queryCon = new TxnContext();
        queryCon.getRecord("record").setValue("roleid", roleid);
        queryCon.getRecord("record").setValue("status", "0");
        table.executeFunction(UPDATE_STATUS_FUNCTION, queryCon, inputNode,outputNode);	
        setBizLog("禁用角色：", context,rolename);
	}
	/**
	 * 启用角色
	 * @param context
	 * @throws TxnException
	 */
	public void txn980331( TxnContext context ) throws TxnException{
		
        BaseTable table = TableFactory.getInstance().getTableObject(this, TABLE_NAME);   
        Recordset rs = context.getRecordset(inputNode);
        String roleid = "";
        String rolename = "";
        if (rs != null && !rs.isEmpty()) {
            for (int i = 0; i < rs.size(); i++) {
            	DataBus db = rs.get(i);
            	String roleids = db.getValue("roleid");
            	String funclist = db.getValue("funclist");
            	StringBuffer groupIds = transFunclistToGroupId(funclist);
            	if(groupIds.length()>0){
                	updateCognosUserByRoleId(table,roleids,groupIds);
            	}            	
            	roleid+=","+roleids;
            	rolename+=","+db.getValue("rolename");
            }
        } 
        if(!roleid.equals("")) roleid = roleid.substring(1);
        if(!rolename.equals("")) rolename = rolename.substring(1);
        TxnContext queryCon = new TxnContext();
        queryCon.getRecord("record").setValue("roleid", roleid);
        queryCon.getRecord("record").setValue("status", "1");
        table.executeFunction(UPDATE_STATUS_FUNCTION, queryCon, inputNode,outputNode);
        setBizLog("启用角色：", context,rolename);
	}	
	
	private void copyRoleTxn(String sourceRoleId, String newRoleId) throws TxnException{
		TxnContext copyRole = new TxnContext();
		DataBus db = new DataBus();
		db.setValue("roleid", sourceRoleId);
		copyRole.addRecord(inputNode,db);
		callService("com.gwssi.sysmgr.priv.func.txn.TxnOperrolefun", "txn103031", copyRole);
		Recordset rs = copyRole.getRecordset(outputNode);
		for(int i = 0; i < rs.size(); i++){
			TxnContext newTxn = new TxnContext();
			db = new DataBus();
			db.setValue("roleid", newRoleId);
			db.setValue("txncode",rs.get(i).getValue("txncode"));
			db.setValue("dataaccrule", "1");
			newTxn.addRecord(inputNode,db);
			callService("com.gwssi.sysmgr.priv.func.txn.TxnOperrolefun", "txn103033", newTxn);
			
			copyRoleTxnAcc(rs.get(i).getValue("roleaccid"),
					newTxn.getRecord(outputNode).getValue("roleaccid"));
		}
//		System.out.println(copyRole);
	}
	 
	private void copyRoleTxnAcc(String sourceTxn,String newTxn) throws TxnException{
		TxnContext copyRole = new TxnContext();
		DataBus db = new DataBus();
		db.setValue("objectid", sourceTxn);
		copyRole.addRecord(inputNode,db);
		callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp", "txn103041", copyRole);
		Recordset rs = copyRole.getRecordset(outputNode);
		for(int i = 0 ; i < rs.size(); i++){
			String dataaccgrpid = rs.get(i).getValue("dataaccgrpid");
			String dataaccdispobj = rs.get(i).getValue("dataaccdispobj");
			
			TxnContext newAcc = new TxnContext();
			db = new DataBus();
			db.setValue("dataaccdispobj", dataaccdispobj);
			db.setValue("dataaccgrpid", dataaccgrpid);
			db.setValue("objectid", newTxn);
			newAcc.addRecord(inputNode, db);
			callService("com.gwssi.sysmgr.priv.datapriv.txn.TxnDataaccdisp", "txn103043", newAcc);
		}
	}
	
	/**
	 * 增加角色功能到数据库
	 * @param roleId 角色ID
	 * @param txnCodeList 交易列表串
	 * @throws TxnException
	 */
	private void addRoleFun(String roleId,String funcList) throws TxnException{
		OperrolefunContext roleFunContext = new OperrolefunContext();
		String[] funcCodes = funcList.split(";");
		HashSet txnCodes = new HashSet();
		int index = 0;
		for(int i = 0; i < funcCodes.length; i++){
			DataBus dataBus = new DataBus();
			TxnContext funcTxnContext = new TxnContext();
			dataBus.setValue("funccode", funcCodes[i]);
			funcTxnContext.addRecord(inputNode, dataBus);
			callService("com.gwssi.sysmgr.priv.func.txn.TxnFunctxn","txn980311",
					funcTxnContext);
			Recordset txnRs = funcTxnContext.getRecordset(outputNode);
			for(int j = 0; j < txnRs.size(); j++){
				String txnCode = txnRs.get(j).getValue("funccode") +"_"+ txnRs.get(j).getValue("txncode");
				if(txnCodes.contains(txnCode)) continue;
				txnCodes.add(txnCode);
				if(!isExistByCode(roleId,txnCode)){
					dataBus = new DataBus();
					dataBus.setValue("roleid", roleId);
					dataBus.setValue("txncode", txnCode);
					dataBus.setValue("dataaccrule", "1");
					roleFunContext.addRecord("record", dataBus);
					index ++;
				}
			}
		}
		if(index>0){
		    callService("com.gwssi.sysmgr.priv.func.txn.TxnOperrolefun", "txn103033",
				roleFunContext);
		}
	}
	/**
	 * 在角色功能表中删除该角色的记录
	 * @param roleId 角色ID
	 * @throws TxnException 
	 */
	private void delRoleFun(String roleId) throws TxnException{
		OperrolefunContext roleFunContext = new OperrolefunContext();
		DataBus dataBus = new DataBus();
		dataBus.setValue("roleid", roleId);
		roleFunContext.addRecord(inputNode, dataBus);
		callService("com.gwssi.sysmgr.priv.func.txn.TxnOperrolefun", "txn103036",
				roleFunContext);
	}	
	/**
	 * 在角色功能表中删除该角色的记录
	 * @param roleId 角色ID
	 * @param funcList 功能组代码
	 * @throws TxnException
	 */
	private void delRoleFun(String roleId,String funcList) throws TxnException{
		OperrolefunContext roleFunContext = new OperrolefunContext();
		DataBus dataBus = new DataBus();
		dataBus.setValue("roleid", roleId);
		dataBus.setValue("funcList", funcList);
		roleFunContext.addRecord(inputNode, dataBus);
		callService("com.gwssi.sysmgr.priv.func.txn.TxnOperrolefun", "txn103036",
				roleFunContext);
	}
	/**
	 * 查询数据权限是否存在
	 * @param roleId
	 * @param txnCode
	 * @return
	 * @throws TxnException
	 */	
	private boolean isExistByCode(String roleId,String txnCode)throws TxnException{
		
		boolean bl = false;
		OperrolefunContext roleFunContext = new OperrolefunContext();
		DataBus dataBus = new DataBus();
		dataBus.setValue("roleid", roleId);
		dataBus.setValue("txncode", txnCode);
		roleFunContext.addRecord(inputNode, dataBus);
		callService("com.gwssi.sysmgr.priv.func.txn.TxnOperrolefun", "txn103031",
				roleFunContext);		
		if(roleFunContext.getRecordset(outputNode).size()>0){
			bl = true;
		}
		return bl;
	}
	
    /**
     * 登录时查询最大查询记录数
     */
    public void queryMaxCount(TxnContext context) throws TxnException {

        try {
    		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
    		table.executeFunction("queryMaxCount", context,"select-key", "record");    
        } catch (TxnDataException ex) {
            if (ex.getErrCode().compareTo(
            		"SQL000"
            ) == 0) {
                // 数据库无数据
                context.clear();
            } else {
                throw ex;
            }
        }
    }		
	/**
	 * 记录日志
	 * @param type
	 * @param context
	 */
    private void setBizLog (String type,TxnContext context,String jgmc){
    	
    	context.getRecord("biz_log").setValue("desc", type + jgmc);
    }	
}
