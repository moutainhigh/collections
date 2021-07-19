package com.gwssi.share.rule.dao;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

public class ShareServiceRule extends BaseTable
{
   public ShareServiceRule()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction("getRuleByServIDAndWeek", DaoFunction.SQL_SELECT, "��ѯ�б�");
	   registerSQLFunction("getServInfoByLog", DaoFunction.SQL_SELECT, "��ѯ���������ʴ����ͼ�¼����");
	   registerSQLFunction("getServState", DaoFunction.SQL_SELECT, "��ѯ��������״̬");
	   registerSQLFunction("isExistServ", DaoFunction.SQL_SELECT, "��ѯ�Ƿ���ڹ������");
	   registerSQLFunction("getServiceIP", DaoFunction.SQL_SELECT, "��ѯ��������IP");
	   registerSQLFunction("delByService_ID", DaoFunction.SQL_DELETE, "���ݷ���IDɾ��");
   }

   /**
    * ִ��SQL���ǰ�Ĵ���
    */
   public void prepareExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }
   
   /**
    * 
    * getServInfoByLog ��ѯ�����������ʵ��ܴ������ܼ�¼�� 
    * @param request
    * @param inputData
    * @return        
    * SqlStatement       
    * @Exception �쳣����    
    * @since  CodingExample��Ver(���뷶���鿴) 1.1
    */
   public SqlStatement getServInfoByLog(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String servID = request.getRecord("select-key").getValue("service_id");
		sqlBuffer.append("select count(1) times, sum(s.record_amount) amount")
			.append(" from (select t.* from share_log t ")
			.append(" where t.service_id = '").append(servID).append("' ")
			.append(" and t.service_start_time > to_char(sysdate, 'yyyy-mm-dd')")
			.append(" and t.service_end_time < to_char(sysdate+1, 'yyyy-mm-dd'))")
			.append(" s");
		
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}
   
   /**
    * 
    * getRuleByServIDAndWeek ���ݹ������Id�����ڼ���ѯ����ĸù���������������
    * @param request
    * @param inputData
    * @return        
    * SqlStatement       
    * @Exception �쳣����    
    * @since  CodingExample��Ver(���뷶���鿴) 1.1
    */
   public SqlStatement getRuleByServIDAndWeek(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String servID = request.getRecord("select-key").getValue("service_id");
		String week = request.getRecord("select-key").getValue("week");
		StringBuffer sqlstr = new StringBuffer(); 
		sqlstr.append("select r.service_id, r.start_time, r.end_time, r.times_day, r.total_count_day ")
			.append(" from share_service_rule r ")
			.append(" where r.service_id = '").append(servID).append("' ")
			.append(" and r.week = '").append(week).append("'");
		stmt.addSqlStmt(sqlstr.toString());
		//stmt.setCountStmt("SELECT COUNT(1) FROM ("+sql+")");
		return stmt;
	}
   
   /**
    * getServState ���ݷ����Ż�ȡ����״̬   
    * @param request
    * @param inputData
    * @return        
    * SqlStatement       
    * @Exception �쳣����    
    * @since  CodingExample��Ver(���뷶���鿴) 1.1
    */
   public SqlStatement getServState(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String servID = request.getRecord("primary-key").getValue("service_id");
		String sql="SELECT t.service_id, t.service_state FROM share_service t ";
		if(StringUtils.isNotEmpty(servID)){
			sql += " where t.service_id = '" + servID + "'";
		}
		stmt.addSqlStmt(sql);
		//stmt.setCountStmt("SELECT COUNT(1) FROM ("+sql+")");
		return stmt;
	}
   
   public SqlStatement getServiceIP(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String servID = request.getRecord("primary-key").getValue("service_id");
		StringBuffer sql = new StringBuffer();
		sql.append("select t.is_bind_ip, t.ip ")
			.append(" from res_service_targets t, share_service s ");
		if(StringUtils.isNotEmpty(servID)){
			sql.append(" where s.service_id = '").append(servID)
				.append("' and s.service_targets_id = t.service_targets_id");
		}
		
		stmt.addSqlStmt(sql.toString());
		//stmt.setCountStmt("SELECT COUNT(1) FROM ("+sql+")");
		return stmt;
	}
   
   public SqlStatement isExistServ(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		String servID = request.getRecord("primary-key").getValue("service_id");
		String sql="SELECT count(1) num FROM share_service t ";
		if(StringUtils.isNotEmpty(servID)){
			sql += " where t.service_id = '" + servID + "'";
		}
		stmt.addSqlStmt(sql);
		//stmt.setCountStmt("SELECT COUNT(1) FROM ("+sql+")");
		return stmt;
	}
   
   
   /**
    * 
    * delByService_ID(���ݷ���IDɾ��)    
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
	public SqlStatement delByService_ID( TxnContext request, DataBus inputData )
	{
		SqlStatement stmt = new SqlStatement( );
		String service_id = request.getRecord("primary-key").getValue("service_id");
		stmt.addSqlStmt( "delete from share_service_rule where service_id='"+service_id+"'" );
		return stmt;
	}
	
   /*
    * 

    */
   
   /**
    * ִ����SQL����Ĵ���
    */
   public void afterExecuteStmt(DaoFunction func, TxnContext request, 
		   DataBus[] inputData, String outputNode) throws TxnException
   {
      
   }

}
