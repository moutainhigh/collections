package com.gwssi.sysmgr.priv.func.dao;

import java.util.Arrays;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.Attribute;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.Recordset;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.dao.impl.TableFactory;

public class Funcinfo extends BaseTable
{
   public Funcinfo()
   {
      
   }
   
	/**
	 * 注册用户自定义的SQL语句
	 */
	protected void register( )
	{
		registerSQLFunction( "getFirstNode", DaoFunction.SQL_ROWSET, "读取第一层节点" );
		registerSQLFunction( "getSecondNodes", DaoFunction.SQL_ROWSET, "读取第二层节点" );
		registerSQLFunction( "getLastNode", DaoFunction.SQL_ROWSET, "读取底层节点" );
		registerSQLFunction( "valTxn", DaoFunction.SQL_ROWSET, "检查节点" );
		registerSQLFunction( "getLastNodeByFunCode", DaoFunction.SQL_ROWSET, "过滤节点" );
		registerSQLFunction( "getFunList", DaoFunction.SQL_ROWSET, "查询交易" );
		registerSQLFunction( "getTxnByFun", DaoFunction.SQL_ROWSET, "查询功能下的交易" );		
		registerSQLFunction( "getRoleAccId", DaoFunction.SQL_ROWSET, "查询角色权限代码" );		
		registerSQLFunction( "getSjqx", DaoFunction.SQL_ROWSET, "查询数据权限配置" );
		registerSQLFunction( "getGnqxInUse", DaoFunction.SQL_ROWSET, "查询可用的功能权限" );
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


	/**
	 * 根据功能获取交易列表
	 * @param funcCode
	 * @return
	 */
	protected String loadFuncTxnList( BaseTable funcTxn, String funcCode ) throws TxnException
	{
		// 生成请求信息
		DataBus result = new DataBus();
		DataBus data = new DataBus();
		data.setValue( "funccode", funcCode );
		data.setValue( "func-info", result );
		TxnContext context = new TxnContext( data );
		
		// 取功能信息
		try{
			int rc = this.executeFunction( "select one funcinfo", 
					context, (String)null, "func-info"
			);
			
			if( rc == 0 ){
				return "";
			}
		}
		catch( TxnException e ){
			if( sqlNotFound(e) ){
				return "";
			}
			else{
				throw e;
			}
		}
		
		// 状态
		String status = (String)result.getValue( "status" );
		if( status == null || status.compareTo("0") == 0 ){
			return "";
		}
		
		// 取交易列表
		try{
			int rc = funcTxn.executeFunction( "select functxn list",
					context, "func-info", "func-txn"
			);
			
			if( rc == 0 ){
				return "";
			}
		}
		catch( TxnException e ){
			if( sqlNotFound(e) ){
				return "";
			}
			else{
				throw e;
			}
		}
		
		// 生成交易列表
		String txnCode;
		DataBus txn;
		StringBuffer txnList = new StringBuffer( );
		Recordset list = data.getRecordset( "func-txn" );
		for( int ii=0; ii<list.size(); ii++ ){
			txn = list.get(ii);
			status = txn.getValue("status");
			if( status == null || status.compareTo("0") != 0 ){
				txnCode = txn.getValue("txncode");
				txnList.append( txnCode );
				txnList.append( ";" );
			}
		}
		
		// 删除最后一个[;]
		if( txnList.length() > 0 ){
			txnList.setLength( txnList.length() - 1 );
		}
		
		return txnList.toString();
	}
	
	/**
	 * 根据功能列表获取交易列表,过滤重复的功能代码，但不过滤重复的交易
	 * @param funcList
	 * @return
	 */
	protected String loadFuncsTxnList( String funcList ) throws TxnException
	{
		// 功能交易表
		BaseTable funcTxn = TableFactory.getInstance().getTableObject( this, "work_status" );
		
		funcList = funcList.trim();
		if (funcList.endsWith(";")){
			funcList = funcList.substring(0, funcList.length() - 1);
		}
		if ( funcList.startsWith(";")){
			funcList = funcList.substring(1);
		}
		
		funcList = funcList.replaceAll(";", "','");
		funcList = "'" + funcList + "'";
		
		TxnContext txnContext = new TxnContext();
		txnContext.getRecord("select-key").setValue("funcList", funcList);
		Attribute.setPageRow(txnContext, "record", -1);
		funcTxn.executeFunction("getFuncList", txnContext, "select-key", "record");
		
		StringBuffer sb_txnCode = new StringBuffer();
		
		Recordset rs = txnContext.getRecordset("record");
		for(int i = 0 ; i < rs.size(); i++ ){
			DataBus db = rs.get(i);
			sb_txnCode.append(db.getValue("txncode"));
			if (i != rs.size() - 1){
				sb_txnCode.append(";");
			}
		}
		
		return sb_txnCode.toString();
	}
	
	/**
	 * 加载功能的授权交易列表
	 * @param request
	 * @param inputNode
	 * @param outputNode
	 * @return
	 * @throws TxnException
	 */
	public int loadTxnList( TxnContext request, String inputNode, 
			String outputNode ) throws TxnException {
		
		DataBus inputData = request.getRecord( inputNode );
		String funcList = inputData.getString( "funclist" );
//		System.out.println( "用户授权的功能列表：" + funcList );
		String txnList = loadFuncsTxnList( funcList );
		
		// 返回权限列表
		//log.info( "用户授权的交易列表：" + txnList );
		
		VoUser user = request.getOperData();
		user.setValue("txnList", txnList );
		request.getRecord("role-info").setValue("txnList",txnList);
		return 1;
	}
	/**
	 * 查询第一层节点
	 * @param request
	 * @param inputData
	 * @return
	 */	
	public SqlStatement getFirstNode( TxnContext request, DataBus inputData ){
		
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("select-key");
		String roleid = data.getValue("roleid");
		String sql = "Select distinct A.funccode, A.funcname, A.parentcode , A.regname, " +
				" B.roleid From funcinfo_new A ,operrole_new B, OperRoleFun C, functxn_new D Where " +
				" ( A.parentcode is null or A.parentcode='') And B.roleid=C.roleid And" +
				" C.txncode=D.funccode||'_'||D.txncode And  B.roleid=" + roleid ;
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	/**
	 * 查询第一层后节点
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getSecondNodes(TxnContext request,DataBus inputData){
		
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String parentcode=dataBus.getValue("parentcode"); 
		String sql = "select A.funccode , A.funcname , A.parentcode from funcinfo_new A " +
		" where A.parentcode='" + parentcode + "'" ;	
		stmt.addSqlStmt(sql);
		return stmt;
	}
	/**
	 * 查询底层节点
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getLastNode(TxnContext request,DataBus inputData){		
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String funccode=dataBus.getValue("funccode"); 
		String roleid=dataBus.getValue("roleid");
		String sql = "select  A.roleaccid  , B.txnname   from  OperRoleFun A,functxn_new B  " +
		" where B.funccode='" + funccode + "' and A.roleid=" + roleid +" and A.txncode=B.funccode||'_'||B.txncode ";
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	/**
	 * 
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getLastNodeByFunCode(TxnContext request,DataBus inputData)
	{		
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String parentcode=dataBus.getValue("parentcode"); 
		String roleid=dataBus.getValue("roleid");
		String sql = " select distinct A.funccode, A.funcname, A.parentcode, A.regname, B.roleid from funcinfo_new A ,operrole_new B, OperRoleFun C,functxn_new D " +
				" where B.roleid=" + roleid +" and B.roleid=C.roleid and C.txncode=D.txncode " +
						" and D.funccode=A.funccode and A.parentcode='" + parentcode + "'";
		//System.out.println(sql);
		stmt.addSqlStmt(sql);
		return stmt;
	}	
		
	/**
	 * 检查功能是否有子节点
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement valTxn(TxnContext request,DataBus inputData){
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String funccode=dataBus.getValue("funccode"); 	
		String sql = "select funccode  from funcinfo_new  " +
		" where parentcode='" + funccode + "'" ;
		//System.out.println("sql====   "+sql);
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	/**
	 * 查询一个角色的交易
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getFunList(TxnContext request,DataBus inputData){
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String roleid=dataBus.getValue("roleid"); 	
		String sql = "select funclist  from operrole_new  " +
		" where roleid=" + roleid  ;
		stmt.addSqlStmt(sql);
		return stmt;
	}	
	
	/**
	 * 查询功能下的交易
	 * @param request
	 * @param inputData
	 * @return
	 */
	public SqlStatement getTxnByFun(TxnContext request,DataBus inputData){
		
		SqlStatement stmt=new SqlStatement();
		DataBus dataBus=request.getRecord("select-key");
		String funccode=dataBus.getValue("funccode"); 	
		String sql = "select txncode  from functxn_new  " +
		" where funccode='" + funccode + "'"  ;
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	/**
	 * 查询角色权限代码
	 * @param request
	 * @param inputData
	 * @return
	 */	
	public SqlStatement getRoleAccId(TxnContext request,DataBus inputData){
		
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");
		String roleid = dataBus.getValue("roleid");		
		String funccode = dataBus.getValue("funccode"); 
		String txncode = dataBus.getValue("txncode");		
		txncode = funccode + "_" + txncode;		
		String sql = "select RoleAccId  from OperRoleFun  " +
		" where roleid=" + roleid + " and txncode ='" + txncode + "'"  ;
		stmt.addSqlStmt(sql);
		return stmt;
	}
	
	/**
	 * 查询数据权限配置
	 * @param request
	 * @param inputData
	 * @return
	 */	
	public SqlStatement getSjqx(TxnContext request,DataBus inputData){
		
		SqlStatement stmt = new SqlStatement();
		DataBus dataBus = request.getRecord("select-key");	
		String funccode = dataBus.getValue("funccode"); 		
		String sql = "select SF_SJQX  from DATACONFIG  " +
		" where funcode ='" + funccode + "'"  ;
		stmt.addSqlStmt(sql);
		return stmt;
	}		
	
	/**
	 * 查询可用的功能权限
	 * @param request
	 * @param inputData
	 * @return
	 */	
	public SqlStatement getGnqxInUse(TxnContext request,DataBus inputData){
		
		SqlStatement stmt = new SqlStatement();		
		String sql = "select funccode,funcname,parentcode,groupname,status,memo  from funcinfo_new where status='1'  order by regdate " ;
		System.out.println(sql);
		stmt.addSqlStmt(sql);
		return stmt;
	}
}

