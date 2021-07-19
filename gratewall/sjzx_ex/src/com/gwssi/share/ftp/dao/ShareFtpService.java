package com.gwssi.share.ftp.dao;



import com.gwssi.common.constant.ExConstant;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class ShareFtpService extends BaseTable
{
   public ShareFtpService()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction("queryParamValueById", DaoFunction.SQL_ROWSET, "��ѯ����ֵ�б�");
	   registerSQLFunction("queryFtpService", DaoFunction.SQL_ROWSET, "��ѯftp���������Ϣ");
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
	 * ��ѯ����ֵ�б�
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement queryParamValueById(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		String service_id=request.getRecord("primary-key").getValue("service_id");
		if(service_id==null||"".equals(service_id)){
			service_id=request.getRecord("record1").getValue("service_id");// ����ID
		}
		String sql = "select t1.* from share_ftp_srv_param t1,share_ftp_service t2 where t1.ftp_service_id = t2.ftp_service_id ";
		if(service_id!=null&&!"".equals(service_id)){
			sql += "and t2.service_id = '" + service_id + "' order by showorder";
		}
		System.out.println("��ѯ����ֵ�б�sql�� " + sql);
		stmt.addSqlStmt(sql);
		
		return stmt;
	}
	
	 /**
	 * ��ѯftp���������Ϣ
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement queryFtpService(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		
		String service_id=request.getRecord("primary-key").getValue("service_id");
		if(service_id==null||"".equals(service_id)){
			service_id=request.getRecord("record1").getValue("service_id");// ����ID
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,k.scheduling_type,k.scheduling_week,k.scheduling_day,k.start_time,k.end_time,k.scheduling_day1,k.scheduling_count,k.interval_time from share_ftp_service t left join share_srv_scheduling k ");
		if(service_id!=null){
			sql.append(" on t.service_id = k.service_id where t.service_id = '"+service_id+"'");
		}
		System.out.println("��ѯ����ֵ�б�sql�� " + sql.toString());
		stmt.addSqlStmt(sql.toString());
		stmt.setCountStmt("select count(1) from ("+sql.toString()+")");
		
		return stmt;
	}

}
