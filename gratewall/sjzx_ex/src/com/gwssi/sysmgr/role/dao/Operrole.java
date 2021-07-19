package com.gwssi.sysmgr.role.dao;

import java.util.HashMap;

import cn.gwssi.common.component.array.StringArray;
import cn.gwssi.common.component.exception.TxnErrorException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.txn.util.ActionErrorConstants;

public class Operrole extends BaseTable
{
   public Operrole()
   {
      
   }
	/**
	 * ע���û��Զ����SQL���
	 */
	protected void register( )
	{
		registerSQLFunction( "checkName", DaoFunction.SQL_ROWSET, "����ɫ����" );	
		registerSQLFunction( "updateStatus", DaoFunction.SQL_UPDATE, "�޸Ľ�ɫ״̬" );			
		registerSQLFunction( "queryMaxCount", DaoFunction.SQL_ROWSET, "��ѯ����ѯ��¼��" );	
	}
   /**
    * ִ��SQL���ǰ�Ĵ���
    */
   public void prepareExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

   /**
    * ִ����SQL����Ĵ���
    */
   public void afterExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }
   
	//����ɫ����	
	public SqlStatement checkName(TxnContext request,DataBus inputData)
	{				
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus=request.getRecord("select-key");
		String rolename = fzx_idDataBus.getValue("rolename");
		stmt.addSqlStmt("select count(*) as count from operrole_new where rolename='"+rolename+"'");		
		return stmt;
	}
	//�޸Ľ�ɫ״̬	
	public SqlStatement updateStatus(TxnContext request,DataBus inputData)
	{				
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus=request.getRecord("record");
		String roleid = fzx_idDataBus.getValue("roleid");
		String status = fzx_idDataBus.getValue("status");
		stmt.addSqlStmt(" update operrole_new set status='"+status+"' where roleid in ("+roleid+")");		
		return stmt;
	}
	//��ѯ����ѯ��¼��
	public SqlStatement queryMaxCount(TxnContext request,DataBus inputData)
	{				
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus = request.getRecord("select-key");
		String roles = fzx_idDataBus.getValue("roles");	
		stmt.addSqlStmt("select * from (select maxcount from operrole_new where roleid in ('"+roles.replaceAll(",", "','")+"') order by maxcount desc ) where Rownum<2");		
		return stmt;
	}	
   
   /**
    * ȡ��ɫ����Ϣ
    * @param roleName
    * @return
    * @throws TxnException
    */
   protected DataBus getRoleInfo( String roleName ) throws TxnException
   {
	   // ����������Ϣ
	   DataBus result = new DataBus();
	   DataBus data = new DataBus();
	   data.setValue( "rolename", roleName );
	   data.setValue( "role-info", result );
	   TxnContext context = new TxnContext( data );
		
	   // ȡ������Ϣ
	   int rc = this.executeFunction( "select one operrole", 
			   context, (String)null, "role-info"
	   );
	   
	   if( rc == 0 ){
		   return null;
	   }
	   
	   return result;
   }
   
   /**
    * ȡ��ɫ����Ϣ����Ȩ�����б�
    * @param roleName �û�������ɫ
    * @param roleList �û���������ɫ�б�
    * @param request
    * @param outputNode
    * @throws TxnException
    */
   protected int getRoleInfo( String roleName, String[] roleList, 
		   TxnContext request, String outputNode) throws TxnException
   {
	   // �����б�
	   DataBus role;
	   String funcs;
	   int flag = 0;
	   String status;
	   StringBuffer funcList = new StringBuffer();
	   
	   // ȡ����ɫ����Ϣ
	   role = getRoleInfo( roleName );
	   if( role == null ){
		   throw new TxnErrorException( ActionErrorConstants.ACTION_ROLE_INVALID,
				   "�û���Ȩ�Ľ�ɫ������"
		   );
	   }
	   
	   // ��ɫ״̬
	   status = role.getValue("status");
	   if( status != null && status.compareTo("0") == 0 ){
		   throw new TxnErrorException( ActionErrorConstants.ACTION_ROLE_DISABLE,
				   "�û���Ȩ�Ľ�ɫ�Ѿ�����ֹ������ǩ��"
		   );
	   }
	   
	   request.put( outputNode, role );
	   funcs = role.getValue( "funclist" );
	   if( funcs != null ){
		   flag = 1;
		   funcList.append( funcs );
	   }
	   
	   // ȡ������ɫ����Ϣ
	   for( int ii=0; ii<roleList.length; ii++ ){
		   roleName = roleList[ii];
		   if( roleName.length() != 0 ){
			   DataBus role1 = getRoleInfo( roleName );
			   if( role1 == null ){
				   continue;
			   }
			   
			   // ��ɫ״̬
			   status = role1.getValue("status");
			   if( status != null && status.compareTo("0") == 0 ){
				   continue;
			   }
			   
			   // �����б�
			   funcs = role1.getValue( "funclist" );
			   if( funcs != null ){
				   if( flag != 0 ){
					   funcList.append( ";" );
				   }

				   flag = 2;
				   funcList.append( funcs );
			   }
		   }
	   }
	   
	   // ���ù����б�
	   if( flag == 2 ){
		   role.setValue( "funclist", funcList.toString() );
	   }
	   
	   // ȡ���ܵĽ����б�
	   BaseTable funcInfo = TableFactory.getInstance().getTableObject( this, "funcinfo" );
	   funcInfo.executeFunction( "loadTxnList", request, outputNode, null );
	   
	   return 0;
   }

   /**
    * ȡ��ɫ����Ϣ����Ȩ�����б�
    * @param request
    * @param inputNode
    * @param outputNode
    * @return
    * @throws TxnException
    */
   public int loadRoleInfo(TxnContext request, String inputNode, 
			String outputNode) throws TxnException
   {
	   // ȡ��ɫ��Ϣ
	   DataBus inputData = request.getRecord( inputNode );
	   String roleName = inputData.getString( "rolename" );
	   String roleGroup = inputData.getValue( "rolegroup" );
	   StringArray roleList = new StringArray();
	   
	   // �����û��Ľ�ɫ�б�
	   VoUser user = request.getOperData();
	   if( roleGroup == null || roleGroup.length() == 0 ){
		   user.setRoleList( roleName );
	   }
	   else{
		   // ɾ���ظ��Ľ�ɫ����
		   HashMap r = new HashMap();
		   String roles[] = roleGroup.split( ";" );
		   
		   r.put( roleName, "1" );
		   for( int ii=0; ii<roles.length; ii++ ){
			   String name = roles[ii].trim();
			   if( r.get(name) == null ){
				   roleList.add( name );
				   r.put( name, "2" );
			   }
		   }

		   StringBuffer s = new StringBuffer( roleGroup.length() + 16 );
		   
		   s.append( roleName );
		   for( int ii=0; ii<roleList.size(); ii++ ){
			   s.append( ";" );
			   s.append( roleList.get(ii) );
		   }
		   
		   user.setRoleList( s.toString() );
	   }
	   
	   // ����Ȩ����Ϣ
	   return getRoleInfo( roleName, roleList.toArray(), request, outputNode );
   }
}
