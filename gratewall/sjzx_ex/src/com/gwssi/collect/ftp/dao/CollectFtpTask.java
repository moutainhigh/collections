package com.gwssi.collect.ftp.dao;

import com.gwssi.common.constant.CollectConstants;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class CollectFtpTask extends BaseTable
{
   public CollectFtpTask()
   {
      
   }

   /**
    * 注册SQL语句
    */
   protected void register()
   {
	   registerSQLFunction("deleteFile", DaoFunction.SQL_DELETE, "删除文件" );
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
	 * deleteFile
	 * 删除文件
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public SqlStatement deleteFile(TxnContext request, DataBus inputData)
	{
		String ftp_task_id = request.getRecord("primary-key").getValue("ftp_task_id");//方法ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		SqlStatement stmt = new SqlStatement();
		if(ftp_task_id!=null&&!"".equals(ftp_task_id)){
			sqlBuffer.append("update collect_ftp_task set file_status ='"+CollectConstants.TYPE_TY+"'");
			sqlBuffer.append(" where ftp_task_id  = '"+ftp_task_id+"'");//更新文件状态为停用
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}

}
