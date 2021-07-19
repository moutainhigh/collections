package com.gwssi.collect.database.dao;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class CollectDatabaseTask extends BaseTable
{
   public CollectDatabaseTask()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction("queryCollectTaskInfo", DaoFunction.SQL_ROWSET, "��ѯ�ɼ������" );
	   registerSQLFunction("queryCollectDataBaseTaskInfo", DaoFunction.SQL_ROWSET, "��ѯ���ݿ��" );
	   registerSQLFunction("queryCollectDataBaseTaskInfoForStep", DaoFunction.SQL_ROWSET, "��ѯ���ݿ��" );
	   registerSQLFunction("queryTasknum", DaoFunction.SQL_ROWSET, "��ѯ�ظ�������" );
	   registerSQLFunction("queryCollectDatabseTreeInfo", DaoFunction.SQL_ROWSET, "��ѯ���ݿ�ɼ�������չʾ��Ϣ" );
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
    * 
    * queryCollectDatabseTreeInfo(��ѯ���ݿ�ɼ������չʾ��Ϣ)    
    * TODO(����������������������� �C ��ѡ)    
    * TODO(�����������������ִ������ �C ��ѡ)    
    * TODO(�����������������ʹ�÷��� �C ��ѡ)    
    * TODO(�����������������ע������ �C ��ѡ)    
    * @param request
    * @param inputData
    * @return        
    * SqlStatement       
    * @Exception �쳣����    
    * @since  CodingExample��Ver(���뷶���鿴) 1.1
    */
   public SqlStatement queryCollectDatabseTreeInfo(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("select-key").getValue("collect_task_id");//���ݱ�ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.*,t1.table_name_en,t1.table_name_cn from collect_database_task t,res_collect_table t1 where t1.collect_table_id(+) = t.collect_table  ");
		if(!"".equals(collect_task_id)){
			sqlBuffer.append(" and t.collect_task_id = '"+collect_task_id+"' ");
		}
		System.out.println("��ѯ�ɼ��������Ϣsql============"+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}
   
   /**
    * ��ѯ�ɼ��������Ϣ
    * @param request
    * @param inputData
    * @return
    */
   
   public SqlStatement queryCollectTaskInfo(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//���ݱ�ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from collect_task t ");
		if(!"".equals(collect_task_id)){
			sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"' ");
		}
		System.out.println("��ѯ�ɼ��������Ϣsql============"+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}
   
   /**
    * ��ѯ�ظ�������
    * @param request
    * @param inputData
    * @return
    */
   
   public SqlStatement queryTasknum(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("record").getValue("collect_task_id");//���ݱ�ID
		String source_collect_table = request.getRecord("record").getValue("source_collect_table");//Դ���ݱ�
		String collect_table = request.getRecord("record").getValue("collect_table");//Ŀ�����ݱ�
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select count(*) as task_num from collect_database_task  m where 1=1 ");
		if(!"".equals(collect_task_id)){
			sqlBuffer.append(" and m.collect_task_id = '"+collect_task_id+"' ");
		}
		if(!"".equals(source_collect_table)){
			sqlBuffer.append(" and m.source_collect_table= '"+source_collect_table+"' ");
		}
		if(!"".equals(collect_table)){
			sqlBuffer.append(" and m.collect_table = '"+collect_table+"' ");
		}
		System.out.println("��ѯ���ݿ�������sql============"+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}
   
   /**
    * ��ѯ�ɼ����ݿ���ϸ��Ϣ
    * @param request
    * @param inputData
    * @return
    */
   
   public SqlStatement queryCollectDataBaseTaskInfo(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//���ݱ�ID
		String database_task_id = request.getRecord("primary-key").getValue("database_task_id");//���ݱ�ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.*,t1.database_task_id,t1.source_collect_table,t1.source_collect_column,t1.collect_mode,t1.collect_table,t1.description from collect_task t, collect_database_task t1 where t1.collect_task_id = t.collect_task_id ");
		if(!"".equals(collect_task_id)){
			sqlBuffer.append(" and t.collect_task_id = '"+collect_task_id+"' ");
		}
		if(!"".equals(database_task_id)){
			sqlBuffer.append(" and t1.database_task_id = '"+database_task_id+"' ");
		}
		System.out.println("��ѯ���ݿ�ɼ��������Ϣsql============"+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
		return stmt;
	}
   
   /**
    * 
    * queryCollectDataBaseTaskInfoForStep(������һ�仰�����������������)    
    * TODO(����������������������� �C ��ѡ)    
    * TODO(�����������������ִ������ �C ��ѡ)    
    * TODO(�����������������ʹ�÷��� �C ��ѡ)    
    * TODO(�����������������ע������ �C ��ѡ)    
    * @param request
    * @param inputData
    * @return        
    * SqlStatement       
    * @Exception �쳣����    
    * @since  CodingExample��Ver(���뷶���鿴) 1.1
    */
   public SqlStatement queryCollectDataBaseTaskInfoForStep(TxnContext request, DataBus inputData)
	{
		//String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//���ݱ�ID
		//String database_task_id = request.getRecord("primary-key").getValue("database_task_id");//���ݱ�ID
		
		String collect_task_id = request.getRecord("select-key").getValue("collect_task_id");//���ݱ�ID
		String database_task_id = request.getRecord("select-key").getValue("database_task_id");//���ݱ�ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.*,t1.database_task_id,t1.source_collect_table,t1.source_collect_column,t1.collect_mode,t1.collect_table,t1.description from collect_task t, collect_database_task t1 where t1.collect_task_id = t.collect_task_id ");
		if(!"".equals(collect_task_id)){
			sqlBuffer.append(" and t.collect_task_id = '"+collect_task_id+"' ");
		}
		if(!"".equals(database_task_id)){
			sqlBuffer.append(" and t1.database_task_id = '"+database_task_id+"' ");
		}
		System.out.println("��ѯ���ݿ�ɼ��������Ϣsql============"+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt(sqlBuffer.toString());
		return stmt;
	}

}
