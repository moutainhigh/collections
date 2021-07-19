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
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		registerSQLFunction( "checkName", DaoFunction.SQL_ROWSET, "检查角色名称" );	
		registerSQLFunction( "updateStatus", DaoFunction.SQL_UPDATE, "修改角色状态" );			
		registerSQLFunction( "queryMaxCount", DaoFunction.SQL_ROWSET, "查询最大查询记录数" );	
	}
   /**
    * 执行SQL语句前的处理
    */
   public void prepareExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

   /**
    * 执行完SQL语句后的处理
    */
   public void afterExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }
   
	//检查角色名称	
	public SqlStatement checkName(TxnContext request,DataBus inputData)
	{				
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus=request.getRecord("select-key");
		String rolename = fzx_idDataBus.getValue("rolename");
		stmt.addSqlStmt("select count(*) as count from operrole_new where rolename='"+rolename+"'");		
		return stmt;
	}
	//修改角色状态	
	public SqlStatement updateStatus(TxnContext request,DataBus inputData)
	{				
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus=request.getRecord("record");
		String roleid = fzx_idDataBus.getValue("roleid");
		String status = fzx_idDataBus.getValue("status");
		stmt.addSqlStmt(" update operrole_new set status='"+status+"' where roleid in ("+roleid+")");		
		return stmt;
	}
	//查询最大查询记录数
	public SqlStatement queryMaxCount(TxnContext request,DataBus inputData)
	{				
		SqlStatement stmt=new SqlStatement();
		DataBus fzx_idDataBus = request.getRecord("select-key");
		String roles = fzx_idDataBus.getValue("roles");	
		stmt.addSqlStmt("select * from (select maxcount from operrole_new where roleid in ('"+roles.replaceAll(",", "','")+"') order by maxcount desc ) where Rownum<2");		
		return stmt;
	}	
   
   /**
    * 取角色的信息
    * @param roleName
    * @return
    * @throws TxnException
    */
   protected DataBus getRoleInfo( String roleName ) throws TxnException
   {
	   // 生成请求信息
	   DataBus result = new DataBus();
	   DataBus data = new DataBus();
	   data.setValue( "rolename", roleName );
	   data.setValue( "role-info", result );
	   TxnContext context = new TxnContext( data );
		
	   // 取功能信息
	   int rc = this.executeFunction( "select one operrole", 
			   context, (String)null, "role-info"
	   );
	   
	   if( rc == 0 ){
		   return null;
	   }
	   
	   return result;
   }
   
   /**
    * 取角色的信息和授权功能列表
    * @param roleName 用户的主角色
    * @param roleList 用户的其它角色列表
    * @param request
    * @param outputNode
    * @throws TxnException
    */
   protected int getRoleInfo( String roleName, String[] roleList, 
		   TxnContext request, String outputNode) throws TxnException
   {
	   // 功能列表
	   DataBus role;
	   String funcs;
	   int flag = 0;
	   String status;
	   StringBuffer funcList = new StringBuffer();
	   
	   // 取主角色的信息
	   role = getRoleInfo( roleName );
	   if( role == null ){
		   throw new TxnErrorException( ActionErrorConstants.ACTION_ROLE_INVALID,
				   "用户授权的角色不存在"
		   );
	   }
	   
	   // 角色状态
	   status = role.getValue("status");
	   if( status != null && status.compareTo("0") == 0 ){
		   throw new TxnErrorException( ActionErrorConstants.ACTION_ROLE_DISABLE,
				   "用户授权的角色已经被禁止，不能签到"
		   );
	   }
	   
	   request.put( outputNode, role );
	   funcs = role.getValue( "funclist" );
	   if( funcs != null ){
		   flag = 1;
		   funcList.append( funcs );
	   }
	   
	   // 取其它角色的信息
	   for( int ii=0; ii<roleList.length; ii++ ){
		   roleName = roleList[ii];
		   if( roleName.length() != 0 ){
			   DataBus role1 = getRoleInfo( roleName );
			   if( role1 == null ){
				   continue;
			   }
			   
			   // 角色状态
			   status = role1.getValue("status");
			   if( status != null && status.compareTo("0") == 0 ){
				   continue;
			   }
			   
			   // 功能列表
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
	   
	   // 设置功能列表
	   if( flag == 2 ){
		   role.setValue( "funclist", funcList.toString() );
	   }
	   
	   // 取功能的交易列表
	   BaseTable funcInfo = TableFactory.getInstance().getTableObject( this, "funcinfo" );
	   funcInfo.executeFunction( "loadTxnList", request, outputNode, null );
	   
	   return 0;
   }

   /**
    * 取角色的信息和授权功能列表
    * @param request
    * @param inputNode
    * @param outputNode
    * @return
    * @throws TxnException
    */
   public int loadRoleInfo(TxnContext request, String inputNode, 
			String outputNode) throws TxnException
   {
	   // 取角色信息
	   DataBus inputData = request.getRecord( inputNode );
	   String roleName = inputData.getString( "rolename" );
	   String roleGroup = inputData.getValue( "rolegroup" );
	   StringArray roleList = new StringArray();
	   
	   // 设置用户的角色列表
	   VoUser user = request.getOperData();
	   if( roleGroup == null || roleGroup.length() == 0 ){
		   user.setRoleList( roleName );
	   }
	   else{
		   // 删除重复的角色名称
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
	   
	   // 加载权限信息
	   return getRoleInfo( roleName, roleList.toArray(), request, outputNode );
   }
}
