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
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction("deleteFile", DaoFunction.SQL_DELETE, "ɾ���ļ�" );
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
   
   /**
	 * deleteFile
	 * ɾ���ļ�
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement deleteFile(TxnContext request, DataBus inputData)
	{
		String ftp_task_id = request.getRecord("primary-key").getValue("ftp_task_id");//����ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		SqlStatement stmt = new SqlStatement();
		if(ftp_task_id!=null&&!"".equals(ftp_task_id)){
			sqlBuffer.append("update collect_ftp_task set file_status ='"+CollectConstants.TYPE_TY+"'");
			sqlBuffer.append(" where ftp_task_id  = '"+ftp_task_id+"'");//�����ļ�״̬Ϊͣ��
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}

}
