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
   // ���ݱ�����
   private final String TABLE_NAME = "operrole";

   // ��ѯ�б�
   private final String ROWSET_FUNCTION = "select operrole list";

   // ��ѯ��¼
   private final String SELECT_FUNCTION = "select one operrole";

   // �޸ļ�¼
   private final String UPDATE_FUNCTION = "update one operrole";

   // ���Ӽ�¼
   private final String INSERT_FUNCTION = "insert one operrole";

   // ɾ����¼
   private final String DELETE_FUNCTION = "delete one operrole";

   // �޸ļ�¼״̬
   private final String STATUS_FUNCTION = "update status";
   
   // ���ù����б�
   private final String SET_FUNCLIST_FUNCTION = "set funclist";
   
   // �޸Ľ�ɫ״̬
   private final String UPDATE_STATUS_FUNCTION = "updateStatus";

   public TxnOperrole()
   {
      
   }

   /** ��ʼ������
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
   protected void prepare(TxnContext context) throws TxnException
   {
      
   }

   /** ��ѯ�б�
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980321( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
	}
		 
   /** �޸ļ�¼
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980322( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( UPDATE_FUNCTION, context, inputNode, outputNode );
	}

   /** ���Ӽ�¼
    @param context ����������
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
				throw new TxnDataException("","��ɫ�����Ѵ��ڣ�");
			}else{			
			    table.executeFunction( INSERT_FUNCTION, context, inputNode, outputNode );
			    setBizLog("���ӽ�ɫ��", context,context.getRecord("record").getValue("rolename"));
			}
	}

   /** ��ѯ��¼
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980324( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SELECT_FUNCTION, context, inputNode, outputNode );
	}

   /** ɾ����¼
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980325( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		/**
		 * �û���������������ɫ��Ϊ����ɫ��������ɾ��
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
			throw new TxnDataException("","�û������������˸ý�ɫ���޷�ɾ����ɫ��");
		}else{
			TxnContext queryContext = (TxnContext) context.clone();
			table.executeFunction( SELECT_FUNCTION, queryContext, inputNode, "operrole" );
			String roleId = queryContext.getRecord("operrole").getValue("roleid");
			delRoleFun(roleId);
			table.executeFunction( DELETE_FUNCTION, context, inputNode, outputNode );
		}
		try{
			PrivilegeManager.getInst().init("ͳ���ƶ�");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/** �޸Ľ�ɫ״̬
	 @param context ����������
	 @throws cn.gwssi.common.component.exception.TxnException
	 */
	public void txn980326( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( STATUS_FUNCTION, context, inputNode, outputNode );
		setBizLog("�޸Ľ�ɫ��", context,context.getRecord("record").getValue("rolename"));
	}

	/** �޸Ľ�ɫ�Ĺ����б�
	 @param context ����������
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
		if (funclist != null && !"".equals(funclist)) {//�ж��Ƿ��������Ȩ�ޣ����򲻸��±���Ȩ�� �޸���2010��04��12�� by:��˷��������
			String[] funcs = funclist.split(";");
			for(int i = 0; i < funcs.length; i++){
				if(funcs[i].startsWith("4")){
					StringBuffer groupIds = transFunclistToGroupId(funclist);
					log.debug("�޸Ľ�ɫ������Ȩ��:" + groupIds);
					updateCognosUserByRoleId(table, roleid, groupIds);
					break;
				}
			}
		}
		*/
		table.executeFunction( SET_FUNCLIST_FUNCTION, context, inputNode, outputNode );
		setBizLog("�޸Ľ�ɫȨ�ޣ�", context,context.getRecord("record").getValue("rolename"));	
				
		
//		try{
//			PrivilegeManager.getInst().init("ͳ���ƶ�");
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
			log.debug("û�м�¼");
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
				log.debug("�޸��û�" + yhxm + "�Ĺ���Ȩ��:" + strBuf);
				list.add(new LDAPUser(strBuf.toString(), userid, yhxm));
			}
			boolean flag = addUserToCognos(list);
			if (!flag) {
				throw new TxnDataException("", "���ݽ�ɫ�޸�cognos�û�ʧ��!");
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
			log.debug("û�м�¼");
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
	/** ��ɫ����
	 @param context ����������
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
		doService("txn980324",copyRole);		// ��ѯԴ��ɫ������Ϣ
		copyRole.getRecord(inputNode).setValue("rolename", newRoleName);
		copyRole.getRecord(inputNode).setValue("description", newRoleDesc);
		doService("txn980323", copyRole);		// �������ƽ�ɫ������Ϣ
		// �޸�
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		table.executeFunction( SET_FUNCLIST_FUNCTION, copyRole, inputNode, outputNode );
		setBizLog("���ƽ�ɫ��", context,newRoleName);
		//String newRoleId = copyRole.getRecord(outputNode).getValue("roleid");
		//copyRoleTxn(sourceRoleId, newRoleId);
	}
	
	/** ��ѯ�б�����ҳ
    @param context ����������
    @throws cn.gwssi.common.component.exception.TxnException
    */
	public void txn980329( TxnContext context ) throws TxnException
	{
		BaseTable table = TableFactory.getInstance().getTableObject( this, TABLE_NAME );
		Attribute.setPageRow(context, outputNode, -1);
		table.executeFunction( ROWSET_FUNCTION, context, inputNode, outputNode );
		
	}
	/**
	 * ���ý�ɫ
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
        setBizLog("���ý�ɫ��", context,rolename);
	}
	/**
	 * ���ý�ɫ
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
        setBizLog("���ý�ɫ��", context,rolename);
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
	 * ���ӽ�ɫ���ܵ����ݿ�
	 * @param roleId ��ɫID
	 * @param txnCodeList �����б�
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
	 * �ڽ�ɫ���ܱ���ɾ���ý�ɫ�ļ�¼
	 * @param roleId ��ɫID
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
	 * �ڽ�ɫ���ܱ���ɾ���ý�ɫ�ļ�¼
	 * @param roleId ��ɫID
	 * @param funcList ���������
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
	 * ��ѯ����Ȩ���Ƿ����
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
     * ��¼ʱ��ѯ����ѯ��¼��
     */
    public void queryMaxCount(TxnContext context) throws TxnException {

        try {
    		BaseTable table = TableFactory.getInstance().getTableObject(this,TABLE_NAME);
    		table.executeFunction("queryMaxCount", context,"select-key", "record");    
        } catch (TxnDataException ex) {
            if (ex.getErrCode().compareTo(
            		"SQL000"
            ) == 0) {
                // ���ݿ�������
                context.clear();
            } else {
                throw ex;
            }
        }
    }		
	/**
	 * ��¼��־
	 * @param type
	 * @param context
	 */
    private void setBizLog (String type,TxnContext context,String jgmc){
    	
    	context.getRecord("biz_log").setValue("desc", type + jgmc);
    }	
}
