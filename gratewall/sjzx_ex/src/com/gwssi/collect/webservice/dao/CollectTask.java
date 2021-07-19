package com.gwssi.collect.webservice.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.gwssi.common.component.exception.TxnDataException;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.util.AnalyCollectFile;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.ftp.download.DownloadFile;
import com.gwssi.webservice.client.AnalyzeWsdl;
import com.gwssi.webservice.client.wsdl.OperationInfo;
import com.gwssi.webservice.client.wsdl.ParameterInfo;
import com.gwssi.webservice.client.wsdl.ServiceInfo;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

public class CollectTask extends BaseTable
{
   public CollectTask()
   {
      
   }

   /**
    * ע��SQL���
    */
   protected void register()
   {
	   registerSQLFunction("queryCollectTaskList", DaoFunction.SQL_ROWSET,"��ѯ�ɼ�����");
	   registerSQLFunction("queryCollectTaskNewList", DaoFunction.SQL_ROWSET,"��ѯ�ɼ�����");
	   registerSQLFunction("getCollectTask", DaoFunction.SQL_ROWSET, "��ȡ�ɼ�������Ϣ" );
	   registerSQLFunction("getCollectTask2", DaoFunction.SQL_ROWSET, "��ȡ�ɼ�������Ϣ2" );
	   registerSQLFunction("getFunctionByTask", DaoFunction.SQL_ROWSET, "��ȡwebservice�ɼ������Ӧ����" );
	   registerSQLFunction("getFtpByTask", DaoFunction.SQL_ROWSET, "��ȡftp�ɼ������Ӧ�ļ�" );
	   registerSQLFunction("deleteTaskItem", DaoFunction.SQL_DELETE, "ɾ��������Ӧ����������" );
	   registerSQLFunction("getFuncAndParam", DaoFunction.SQL_DELETE,"��ȡ����������");
	   registerSQLFunction("getFtpFile", DaoFunction.SQL_DELETE, "��ȡftp�ļ�" );
	   registerSQLFunction("downLoadFtpFile", DaoFunction.SQL_UPDATE, "����FTP�ļ�����ȡ�������" );
	   registerSQLFunction("queryFilePath", DaoFunction.SQL_ROWSET,"��ѯ����·����Ϣ");
	   registerSQLFunction("queryCollectTableInfo", DaoFunction.SQL_ROWSET,"��ѯ�ɼ�����Ϣ");
	   registerSQLFunction("getFileUploadInfo", DaoFunction.SQL_ROWSET,"��ȡ�ļ��ϴ��ɼ���Ϣ");
	   registerSQLFunction("deleteFileUpload", DaoFunction.SQL_DELETE, "ɾ���ļ��ϴ�����" );
	   registerSQLFunction("queryKeyColumns", DaoFunction.SQL_ROWSET,"��ѯ�����ֶ���Ϣ");
	   registerSQLFunction("querySerTarName", DaoFunction.SQL_ROWSET,"��ѯ�����������");
	   registerSQLFunction("getCollectFileResult", DaoFunction.SQL_ROWSET,"��ѯ�ɼ��ļ������Ϣ");
	   registerSQLFunction("queryTaskScheduleListForIndex", DaoFunction.SQL_ROWSET,"��ѯ���������ҳ��");
	   registerSQLFunction("getDBByTask", DaoFunction.SQL_ROWSET, "��ȡ���ݿ�ɼ������Ӧ�Ĳɼ����ݱ�" );
	   registerSQLFunction("deleteTaskDatabaseItem", DaoFunction.SQL_DELETE, "ɾ��������Ӧ������" );
	   registerSQLFunction("getInfoByType", DaoFunction.SQL_ROWSET,"����ָ�����뼯��÷���ͳ����Ϣ");
	   registerSQLFunction("getInfoByTarget", DaoFunction.SQL_ROWSET,"���ݷ�������÷���ͳ����Ϣ");
	   registerSQLFunction("getCollectTaskTableAndDataitems", DaoFunction.SQL_ROWSET,"���ݷ�������÷���ͳ����Ϣ");
	   registerSQLFunction("getCollectTaskInfo", DaoFunction.SQL_ROWSET,"���ݲɼ�����ID���ͳ����Ϣ");
	   registerSQLFunction("getInfoByTaskStatus", DaoFunction.SQL_ROWSET,"����ָ�����뼶��ȡ�ɼ�������Ϣ");
	   registerSQLFunction("getInfoByCollectType", DaoFunction.SQL_ROWSET,"����ָ�����뼶��ȡ�ɼ�������Ϣ");
	   registerSQLFunction("getInfoByTargetType", DaoFunction.SQL_ROWSET,"����ָ�����뼶��ȡ�ɼ�������Ϣ");
	   registerSQLFunction("getFuncTest", DaoFunction.SQL_ROWSET,"��ѯ������Ϣ");
	   registerSQLFunction("getFileInfoTree", DaoFunction.SQL_ROWSET,"����FTP�ɼ�����ID��ȡ�ļ���Ϣ");
	   registerSQLFunction("getFileInfo", DaoFunction.SQL_ROWSET,"�����ļ�ID��ȡ�ļ���Ϣ");
	   registerSQLFunction("getFTPTaskInfo", DaoFunction.SQL_ROWSET,"��ȡ������Ϣ");
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
    * getInfoByTargetType(���ݷ���������ͻ�ȡ�ɼ�����ͳ����Ϣ)
    * @param request
    * @param inputData
    * @return        
    * SqlStatement       
    * @Exception �쳣����    
    * @since  CodingExample��Ver(���뷶���鿴) 1.1
    */
   public SqlStatement getInfoByTargetType(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String table = inputData.getValue("table_name");
		String column = inputData.getValue("col_name");
		String title = inputData.getValue("col_title");
		if (StringUtils.isNotBlank(table) && StringUtils.isNotBlank(column)
				&& StringUtils.isNotBlank(title)) {
			sqlBuffer
				.append("with a as (select tar." + column + " as key, tar." + title
						+ " as title, NVL(t.mt, 0) as amount")
				.append(",tar.service_targets_type ")
				.append(" ,tar.show_order, tar.last_modify_time ")
				.append("from " + table + " tar, ")
				.append(" (select count(v.collect_task_id) mt, s.service_targets_type,s.service_targets_id")
				.append(" from VIEW_COLLECT_TASK v, res_service_targets s")   
				.append(" where v.service_targets_id = s.service_targets_id")      
				.append(" group by s.service_targets_type,s.service_targets_id) t")
				.append(" where tar.is_markup = 'Y' and tar." + column
						+ " = t." + column + "(+)) ")
				.append(" select * from (select * from a")
				.append(" where service_targets_type = '000'")			
				.append(" order by show_order) union all ")
				.append(" select * from(select * from a")
				.append(" where service_targets_type <> '000'")
				.append(" order by service_targets_type,title)");
		
		}
		stmt.addSqlStmt(sqlBuffer.toString());
		
		System.out.println("�������ͣ�sqlBuffer="+sqlBuffer.toString());
		return stmt;
	}
   /**
    * 
    * getInfoByCollectType(���ݲɼ��������ͻ�ȡ�ɼ�����ͳ����Ϣ)    
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
   public SqlStatement getInfoByCollectType(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append("select cd.codename as title, cd.codevalue as key, NVL(t.mt, 0) as amount ")
					.append("from codedata cd, ")
					.append("(select svr.")
					.append(column)
					.append(", count(svr.collect_task_id) as mt ")
					.append("from view_collect_task svr  group by svr.")
					.append(column).append(") t where cd.codetype = '")
					.append(codeType).append("' and cd.codevalue = t.")
					.append(column).append("(+) order by key");
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		
		//System.out.println("�ɼ����ͣ�sqlBuffer="+sqlBuffer.toString());
		return stmt;
	}
   /**
    * 
    * getInfoByTaskStatus(����ָ�����뼶��ȡ�ɼ�������Ϣ)    
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
   public SqlStatement getInfoByTaskStatus(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append("select cd.codename as title, cd.codevalue as key, NVL(t.mt, 0) as amount ")
					.append("from codedata cd, ")
					.append("(select itf.")
					.append(column)
					.append(", count(itf.collect_task_id) as mt ")
					.append("from view_collect_task itf  group by itf.")
					.append(column).append(") t where cd.codetype = '")
					.append(codeType).append("'  and cd.codevalue = t.")
					.append(column).append("(+) order by amount desc");
			stmt.addSqlStmt(sqlBuffer.toString());
			// stmt.setCountStmt("select count(*) from (" +
			// sqlBuffer.toString()+ ")");
		}
		//System.out.println("����״̬��sqlBuffer="+sqlBuffer.toString());
		return stmt;
	}
   /**
    * ���ݲɼ����ͺ�����״̬��ѯ�ɼ�����
    * @param request
    * @param inputData
    * @return
    */
   public SqlStatement getInfoByType(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String codeType = inputData.getValue("codetype");
		String column = inputData.getValue("column");
		if (StringUtils.isNotBlank(codeType) && StringUtils.isNotBlank(column)) {
			sqlBuffer
					.append("select cd.codename as title, cd.codevalue as key, NVL(t.mt, 0) as amount ")
					.append("from codedata cd, ")
					.append("(select svr.")
					.append(column)
					.append(", count(svr.collect_task_id) as mt ")
					.append("from collect_task svr where svr.is_markup = 'Y' group by svr.")
					.append(column).append(") t where cd.codetype = '")
					.append(codeType).append("' and cd.codevalue = t.")
					.append(column).append("(+) order by amount desc");
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}
   /**
    * ����������������ѯ�ɼ�����
    * @param request
    * @param inputData
    * @return
    */
	public SqlStatement getInfoByTarget(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String table = inputData.getValue("table_name");
		String column = inputData.getValue("col_name");
		String title = inputData.getValue("col_title");
		if (StringUtils.isNotBlank(table) && StringUtils.isNotBlank(column)
				&& StringUtils.isNotBlank(title)) {
			sqlBuffer
					.append("select tar." + column + " as key, tar." + title
							+ " as title, NVL(t.mt, 0) as amount, tar.service_targets_type ")
					.append("from " + table + " tar, ")
					.append("(select s." + column
							+ ", count(s.collect_task_id) as mt ")
					.append("from collect_task s where s.is_markup = 'Y' ")
					.append("group by s." + column + ") t ")
					.append("where tar.is_markup = 'Y' and mt!='0' and t." + column
							+ " = tar." + column + "(+) ")
					.append("order by amount desc, tar.last_modify_time ");
			stmt.addSqlStmt(sqlBuffer.toString());
		}

		return stmt;
	}
   /**
    * 
    * queryCollectTaskList(��ѯ�ɼ������б�)   
    * @param request
    * @param inputData
    * @return
    * @throws TxnException        
    * SqlStatement       
    * @Exception �쳣����    
    * @since  CodingExample��Ver(���뷶���鿴) 1.1
    */
 	public SqlStatement queryCollectTaskList(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String service_targets_id = request.getRecord("select-key").getValue("service_targets_id");//�������ID
 		String task_name = request.getRecord("select-key").getValue("task_name");//��������
 		String collect_type = request.getRecord("select-key").getValue("collect_type");//�ɼ�����
 		String collect_status = request.getRecord("select-key").getValue("collect_status");//�ɼ�״̬
 		String task_status = request.getRecord("select-key").getValue("task_status");//����״̬
 		String created_time_start = request.getRecord("select-key").getValue("created_time_start");//����ʱ�俪ʼ
        String created_time_end = request.getRecord("select-key").getValue("created_time_end");//����ʱ�����
 		
 		/*DataBus db = request.getRecord("select-key");
		String service_targets_id = db.getValue("service_targets_id");
		String service_targets_type = db.getValue("service_targets_type");
		String collect_type = db.getValue("collect_type");
		String task_status = db.getValue("task_status");
		String created_time_start = db.getValue("created_time_start");
		String created_time_end = db.getValue("created_time_end");*/
		
		
		
 		StringBuffer querySql = new StringBuffer();
 		querySql.append("select t.collect_task_id,service_targets_id,data_source_id,t.task_name,collect_type,task_description,record,task_status,t.is_markup,t.creator_id,substr(t.created_time, 0, 10) created_time,t.last_modify_id,t.last_modify_time,log_file_path,collect_status,c.scheduling_day1,c.start_time from collect_task t , collect_task_scheduling c where t.collect_task_id = c.collect_task_id and t.is_markup = 'Y' and c.is_markup = 'Y'");
 		if (service_targets_id != null && !"".equals(service_targets_id)) {//�������ID
 			querySql.append(" and service_targets_id = '" + service_targets_id + "'");
 		}
 		if (collect_type != null && !"".equals(collect_type)) {//�ɼ�����
 			querySql.append(" and collect_type = '" + collect_type + "'");
 		}
 		if (collect_status != null && !"".equals(collect_status)) {//�ɼ�״̬
 			querySql.append(" and collect_status = '" + collect_status + "'");
 		}
 		if (task_status != null && !"".equals(task_status)) {//����״̬
 			querySql.append(" and task_status = '" + task_status + "'");
 		}
 		if (task_name != null && !"".equals(task_name)) {//��������
 			querySql
 					.append(" and task_name like '%" + task_name + "%'");
 		}
 		if (created_time_start != null && !"".equals(created_time_start)) {//����ʱ��
			querySql.append(" and created_time >= '" + created_time_start
					+ "'");
		}
		if (created_time_end != null && !"".equals(created_time_end)) {
			querySql.append(" and created_time <= '" + created_time_end + " 24:00:00'");
		}
 		querySql.append(" order by t.created_time desc");
 		//System.out.println("��ѯ�ɼ������б� sql======"+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
 	
 	/**
     * 
     * queryCollectTaskNewList(��ѯ�ɼ������б�)   
     * @param request
     * @param inputData
     * @return
     * @throws TxnException        
     * SqlStatement       
     * @Exception �쳣����    
     * @since  CodingExample��Ver(���뷶���鿴) 1.1
     */
  	public SqlStatement queryCollectTaskNewList(TxnContext request,
  			DataBus inputData) throws TxnException
  	{
  		SqlStatement stmt = new SqlStatement();
  		//System.out.println(request.getRecord("select-key"));
  		String service_targets_id = request.getRecord("select-key").getValue("service_targets_id");//�������ID
  		String task_name = request.getRecord("select-key").getValue("task_name");//��������
  		String collect_type = request.getRecord("select-key").getValue("collect_type");//�ɼ�����
  		String collect_status = request.getRecord("select-key").getValue("collect_status");//�ɼ�״̬
  		String task_status = request.getRecord("select-key").getValue("task_status");//����״̬
  		String service_targets_type =request.getRecord("select-key").getValue("service_targets_type"); 
		// String created_time_start =
		// request.getRecord("select-key").getValue("created_time_start");//����ʱ�俪ʼ
		// String created_time_end =
		// request.getRecord("select-key").getValue("created_time_end");//����ʱ�����
		//
  		StringBuffer querySql = new StringBuffer();
  		querySql.append("select v.collect_task_id,v.service_targets_id,v.service_targets_id as service_targets_id1," +
  				"v.task_name,v.collect_type,v.scheduling_day1,v.start_time,v.collect_status,v.task_status,v.log_file_path," +
  				"nvl(substr(t.last_modify_time,0,10),substr(t.created_time,0,10))  time,nvl(y2.yhxm,y1.yhxm) name " +
  				"from view_collect_task v,collect_task t,xt_zzjg_yh_new y1,xt_zzjg_yh_new y2,res_service_targets r where 1=1");
  		if (service_targets_id != null && !"".equals(service_targets_id)) {//�������ID
  			querySql.append(" and v.service_targets_id = '" + service_targets_id + "'");
  		}
  		if (collect_type != null && !"".equals(collect_type)) {//�ɼ�����
  			querySql.append(" and v.collect_type = '" + collect_type + "'");
  		}
  		if (collect_status != null && !"".equals(collect_status)) {//�ɼ�״̬
  			querySql.append(" and v.collect_status = '" + collect_status + "'");
  		}
  		if (task_status != null && !"".equals(task_status)) {//����״̬
  			querySql.append(" and v.task_status = '" + task_status + "'");
  		}
  		if (task_name != null && !"".equals(task_name)) {//��������
  			querySql.append(" and v.task_name like '%" + task_name + "%'");
  		}
  		if (service_targets_type != null && !"".equals(service_targets_type)) {//��������
  			querySql.append(" and r.service_targets_type ='")
  				.append(service_targets_type)
  				.append("'");
  				
  		}
  		querySql.append(" and r.service_targets_id=v.service_targets_id and v.collect_task_id = t.collect_task_id(+) and t.creator_id=y1.yhid_pk(+) and t.last_modify_id=y2.yhid_pk(+)");
//  		if (created_time_start != null && !"".equals(created_time_start)) {//����ʱ��
// 			querySql.append(" and created_time >= '" + created_time_start
// 					+ "'");
// 		}
// 		if (created_time_end != null && !"".equals(created_time_end)) {
// 			querySql.append(" and created_time <= '" + created_time_end + " 24:00:00'");
// 		}
  		querySql.append(" order by nvl(time,'1900-01-01') desc,collect_type, to_number(replace(v.start_time,':','')) ");
  		//System.out.println("��ѯ�ɼ������б� sql======"+querySql.toString());
  		stmt.addSqlStmt(querySql.toString());
  		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
  		return stmt;

  	}
 	
 	 /**
	 * getCollectTask 
	 * ���ݲɼ�����ID��ѯ��Ӧ���з���
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
   
	public SqlStatement getCollectTask(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//�ɼ�����ID
		//System.out.println(" collect_task_id========="+collect_task_id);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t.*,k.task_scheduling_id,k.scheduling_type,k.scheduling_week,k.scheduling_day,k.start_time,k.end_time,k.scheduling_day1,k.scheduling_count,k.interval_time from collect_task t left join collect_task_scheduling k ");
		if(collect_task_id!=null){
			sqlBuffer.append(" on t.collect_task_id = k.collect_task_id where t.collect_task_id = '"+collect_task_id+"' and t.is_markup = '"+ExConstant.IS_MARKUP_Y+"'");
		}
		//System.out.println("���ݲɼ�����ID��ѯ�ɼ�������Ϣ sql==="+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
		return stmt;
	}
	
	 /**
		 * getCollectTask2 
		 * ���ݲɼ�����ID��ѯ��Ӧ���з���
		 * 
		 * @param request
		 * @param inputData
		 * @return SqlStatement
		 * @since CodingExample Ver(���뷶���鿴) 1.1
		 */
	   
		public SqlStatement getCollectTask2(TxnContext request, DataBus inputData)
		{
			String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//�ɼ�����ID
			//System.out.println(" collect_task_id========="+collect_task_id);
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("select t1.*,t1.created_time as cretime,nvl(t1.last_modify_time,t1.created_time)as modtime, " +
					"yh1.yhxm as crename,nvl(yh2.yhxm,yh1.yhxm) as modname from collect_task t1,xt_zzjg_yh_new yh1,xt_zzjg_yh_new yh2  ");
			if(collect_task_id!=null){
				sqlBuffer.append("  where t1.collect_task_id = '"+collect_task_id+"' and t1.creator_id = yh1.yhid_pk(+)  and t1.last_modify_id = yh2.yhid_pk(+) and t1.is_markup = '"+ExConstant.IS_MARKUP_Y+"'");
			}
			//System.out.println("���ݲɼ�����ID��ѯ�ɼ�������Ϣ sql==="+sqlBuffer.toString());
			SqlStatement stmt = new SqlStatement();
			stmt.addSqlStmt(sqlBuffer.toString());
			stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
			return stmt;
		}
	
 	 /**
	 * getFunctionByTask s
	 * ���ݲɼ�����ID��ѯ��Ӧ���з���
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
   
	public SqlStatement getFunctionByTask(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//�ɼ�����ID
		//System.out.println(" collect_task_id========="+collect_task_id);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from collect_webservice_task t ");
		if(collect_task_id!=null){
			sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"' and t.method_status = '"+CollectConstants.TYPE_QY+"' order by service_no");
		}
		//System.out.println("���ݲɼ�����ID��ѯ��Ӧ���з���       sql==="+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
		return stmt;
	}
	
	/**
	 * getDBByTask 
	 * ���ݲɼ�����ID��ѯ��Ӧ���ݱ�
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
   
	public SqlStatement getDBByTask(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//�ɼ�����ID
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from collect_database_task t ");
		if(collect_task_id!=null){
			sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"' order by created_time");
		}
		
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
		return stmt;
	}
	 /**
	 * getFtpByTask 
	 * ����ftp�ɼ�����ID��ѯ��Ӧ�����ļ�
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
   
	public SqlStatement getFtpByTask(TxnContext request, DataBus inputData)
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//�ɼ�����ID
		//System.out.println(" collect_task_id========="+collect_task_id);
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from collect_ftp_task t ");
		if(collect_task_id!=null){
			sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"' and t.file_status = '"+CollectConstants.TYPE_QY+"' order by service_no");
		}
		//System.out.println("����ftp�ɼ�����ID��ѯ��Ӧ�����ļ�       sql==="+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		stmt.setCountStmt("select count(1) from ("+sqlBuffer.toString()+")");
		return stmt;
	}
	
	 /**
	 * deleteTaskItem 
	 * ���ݷ����IDɾ����Ӧ���з���������
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException 
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement deleteTaskItem(TxnContext request, DataBus inputData) throws DBException
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//����ID
		if(collect_task_id==null||"".equals(collect_task_id)){
			collect_task_id = request.getRecord("record").getValue("collect_task_id");
		}
		StringBuffer sqlBuffer = new StringBuffer();
		SqlStatement stmt = new SqlStatement();
		if(collect_task_id!=null&&!"".equals(collect_task_id)){
			sqlBuffer.append("select * from collect_task t where t.collect_task_id = '"+collect_task_id+"'");
			ServiceDAO	daoTable	= new ServiceDAOImpl();; // �������ݱ�Dao
			// ��ȡ���ݱ�����
			Map tablepMap = daoTable.queryService(sqlBuffer.toString());
			String type=null;//�ɼ�����
			if(tablepMap!=null&&!tablepMap.isEmpty()){
				type=(String) tablepMap.get("COLLECT_TYPE");
			}
			sqlBuffer = new StringBuffer();
			if(type!=null&&(type.equals(CollectConstants.TYPE_CJLX_WEBSERVICE)
					||type.equals(CollectConstants.TYPE_CJLX_SOCKET)||type.equals(CollectConstants.TYPE_CJLX_JMS))){//webservice socket jms
				sqlBuffer.append("delete from  collect_webservice_patameter where webservice_task_id in ( ");
				sqlBuffer.append("select webservice_task_id from collect_webservice_task a where a.collect_task_id = '"+collect_task_id+"')");//ɾ��������
				stmt.addSqlStmt(sqlBuffer.toString());
				
				
				
				sqlBuffer = new StringBuffer();
				sqlBuffer.append("delete from collect_webservice_task t ");
				if(collect_task_id!=null){
					sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"'");//ɾ��������
				}
			}else if(type!=null&&type.equals(CollectConstants.TYPE_CJLX_FTP)){//ftp
				sqlBuffer.append("delete from collect_ftp_task t ");
				if(collect_task_id!=null){
					sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"'");//ɾ��ftp�ļ���
				}
			}
			//System.out.println("ɾ���ɼ����� sql==="+sqlBuffer.toString());
			stmt.addSqlStmt(sqlBuffer.toString());
			
			sqlBuffer = new StringBuffer();
			sqlBuffer.append("delete from collect_task_scheduling t ");
			if(collect_task_id!=null){
				sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"'");//ɾ��������ȱ�
			}
			stmt.addSqlStmt(sqlBuffer.toString());
		}
		return stmt;
	}
	
	public SqlStatement getFuncTest(TxnContext request, DataBus inputData) {
		SqlStatement stmt = new SqlStatement();
		String data_source_id=request.getRecord("record").getValue("data_source_id");//����Դ��ID
		String collect_task_id=request.getRecord("record").getValue("collect_task_id");//���ݱ�ID
		String sql="select access_url from res_data_source where data_source_id = '"+data_source_id+"'";
	
		System.out.println("data_source_id is "+data_source_id);
		System.out.println("collect_task_id is "+collect_task_id);
		System.out.println("sql is "+sql);
		
		stmt.addSqlStmt(sql);
		stmt.setCountStmt("select count(1) from ("+sql+")");
		return stmt;
		
	}
	
	
	 /**
	 * getFuncAndParam 
	 * ���ݷ����IDɾ����Ӧ���з���������
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException 
	 * @throws TxnDataException 
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement getFuncAndParam(TxnContext request, DataBus inputData) throws DBException, TxnDataException
	{
		SqlStatement stmt = new SqlStatement();
		String data_source_id=request.getRecord("record").getValue("data_source_id");//����Դ��ID
		String collect_task_id=request.getRecord("record").getValue("collect_task_id");//���ݱ�ID
		String sql="select access_url from res_data_source where data_source_id = '"+data_source_id+"'";
	
		try {
			ServiceDAO	daoTable	= new ServiceDAOImpl(); // �������ݱ�Dao
			Map tablepMap = daoTable.queryService(sql);
			String access_url=(String) tablepMap.get("ACCESS_URL");//����URL
			
			ServiceInfo serviceInfo=AnalyzeWsdl.getService(access_url);
			String idParam=null;
			String idFunc=null;
			String service_no=null;
			String methodName=null;
			String nameParam=null;
			String typeParam=null;
			String methodNameTemp=null;
			Iterator iter = serviceInfo.getOperations();
			String web_name_space=serviceInfo.getTargetnamespace();
			if(web_name_space==null){
				web_name_space="";
			}
			int i = 0, j = 0;
			int count=-1;
			count=this.getNum(CollectConstants.TYPE_WEBSERVICE_TABLE,CollectConstants.TYPE_CJLX_WEBSERVICE, daoTable);
			StringBuffer insertSql=new StringBuffer();
			insertSql= new StringBuffer();
			
			StringBuffer selectSqlBuffer= new StringBuffer();
			selectSqlBuffer.append("select *  from  collect_webservice_patameter where webservice_task_id in ( ");
			selectSqlBuffer.append("select webservice_task_id from collect_webservice_task a where a.collect_task_id = '"+collect_task_id+"')");//��ѯ������
			List paramList=daoTable.query(selectSqlBuffer.toString());
			
			selectSqlBuffer= new StringBuffer();
			selectSqlBuffer.append("select * from  collect_webservice_task where collect_task_id = '"+collect_task_id+"'");//��ѯ������
			
			List methodList=daoTable.query(selectSqlBuffer.toString());
			
			insertSql.append("delete from  collect_webservice_patameter where webservice_task_id in ( ");
			insertSql.append("select webservice_task_id from collect_webservice_task a where a.collect_task_id = '"+collect_task_id+"')");//ɾ��������
			//System.out.println("ɾ��������sql==="+insertSql);
			stmt.addSqlStmt(insertSql.toString());
			
			insertSql= new StringBuffer();
			insertSql.append("delete from  collect_webservice_task where collect_task_id = '"+collect_task_id+"'");//ɾ��������
			stmt.addSqlStmt(insertSql.toString());
			//System.out.println("ɾ��������sql==="+insertSql);
			
			while (iter.hasNext()) {
				i++;
				OperationInfo oper = (OperationInfo) iter.next();
					
					count=count+1;
					//System.out.println("count==="+count);
					if(count<10){
						service_no="SERVICE_00"+count;
					}else if(count>=10&&count<=99){
						service_no="SERVICE_0"+count;
					}else{
						service_no="SERVICE_"+count;
					}
				
					
				methodName=oper.getTargetMethodName();//������
				
				if(methodName!=null&&!"".equals(methodName)){
					
					if(methodList!=null&&methodList.size()>0){
						Map mapTemp = new HashMap();
						String fanfaName=null;
						for(int k=0;k<methodList.size();k++){
							mapTemp = (Map) methodList.get(k);
							fanfaName=(String)mapTemp.get("METHOD_NAME_EN");
							//System.out.println("������=="+fanfaName);
							//System.out.println("methodName=="+methodName);
							if(fanfaName!=null&&fanfaName.equals(methodName)){
								idFunc=(String)mapTemp.get("WEBSERVICE_TASK_ID");
								break;
							}else if(k==methodList.size()){
								idFunc = UuidGenerator.getUUID();
							}
						}
						
					}else{
						idFunc = UuidGenerator.getUUID();
					}	
							insertSql= new StringBuffer();
							insertSql.append("insert into collect_webservice_task(webservice_task_id,collect_task_id,service_no,method_name_en,method_status,web_name_space) values('");
							insertSql.append(idFunc+"','"+collect_task_id+"','"+service_no+"','"+methodName+"','"+CollectConstants.TYPE_QY+"','"+web_name_space+"')");
							//System.out.println("insertSql���뷽����==="+insertSql);
							stmt.addSqlStmt(insertSql.toString());
				}
				List inps = oper.getInparameters();
				if (inps.size() == 0) {
					System.out.println("�˲���������������Ϊ:");
					System.out.println("ִ�д˲�������Ҫ�����κβ���!");
				} else {
					
					for (Iterator iterator1 = inps.iterator(); iterator1
							.hasNext();) {
						ParameterInfo element = (ParameterInfo) iterator1.next();
						nameParam=element.getName();
						typeParam=element.getKind();
						idParam = UuidGenerator.getUUID();
						insertSql= new StringBuffer();
						insertSql.append("insert into collect_webservice_patameter(webservice_patameter_id,webservice_task_id,patameter_type,patameter_name) values('");
						insertSql.append(idParam+"','"+idFunc+"','"+typeParam+"','"+nameParam+"')");
						//System.out.println("insertSql���������==="+insertSql);
						stmt.addSqlStmt(insertSql.toString());
						
					}
				}
				//System.out.println("����ɹ�!===");
			}//���»�ȡ����������
			updateFun(methodList,stmt,daoTable);//���·���
			updateParam(paramList,stmt,daoTable);//���²���
		
		}catch (Exception e) {
			e.printStackTrace();
			throw new TxnDataException("error", "��������Դʧ��!");
		}
		return stmt;
	}
	/**
	 * ���·���
	 * @param methodList
	 * @param stmt
	 * @throws DBException 
	 */
	public void updateFun(List methodList,SqlStatement stmt,ServiceDAO	daoTable) throws DBException{
		
		//��ԭ��������ֵ���µ��·�����
		Map methodMap = new HashMap();
		String sql=null;
		Map tablepMap = new HashMap();
		String methodName=null;
		String method_name_en=null;
		String method_name_cn=null;//����������
		String service_no=null;//������
		String collect_table=null;//��Ӧ�ɼ���
		String collect_mode=null;//���������ȫ��
		String is_encryption=null;//������Ƿ�
		String encrypt_mode=null;//�����DES3DSAES
		String method_description=null;//��������
		String  webservice_task_id = null;//����ID
		String web_name_space=null;//�����ռ�
		StringBuffer updateSql= new StringBuffer();
		if(methodList!=null&&methodList.size()>0){
			for(int i=0;i<methodList.size();i++){
			updateSql= new StringBuffer();
			methodMap = (Map) methodList.get(i);
			System.out.println("����en��======"+methodMap.get("METHOD_NAME_EN"));//����EN��
			method_name_en=(String)methodMap.get("METHOD_NAME_EN");
			System.out.println("����cn��======"+methodMap.get("METHOD_NAME_CN"));//����CN��
			method_name_cn=(String)methodMap.get("METHOD_NAME_CN");
			System.out.println("SERVICE_NO======"+methodMap.get("SERVICE_NO"));//service_no
			service_no=(String)methodMap.get("SERVICE_NO");
			System.out.println("��Ӧ�ɼ���======"+methodMap.get("COLLECT_TABLE"));//��Ӧ�ɼ���
			collect_table=(String)methodMap.get("COLLECT_TABLE");
			System.out.println("���������ȫ��======"+methodMap.get("COLLECT_MODE"));//���������ȫ��
			collect_mode=(String)methodMap.get("COLLECT_MODE");
			System.out.println("������Ƿ�======"+methodMap.get("IS_ENCRYPTION"));//������Ƿ�
			is_encryption=(String)methodMap.get("IS_ENCRYPTION");
			System.out.println("�����DES3DSAES======"+methodMap.get("ENCRYPT_MODE"));//�����DES3DSAES
			encrypt_mode=(String)methodMap.get("ENCRYPT_MODE");
			System.out.println("��������======"+methodMap.get("METHOD_DESCRIPTION"));//��������
			method_description=(String)methodMap.get("METHOD_DESCRIPTION");
			System.out.println("����ID======"+methodMap.get("WEBSERVICE_TASK_ID"));//����ID
			webservice_task_id=(String)methodMap.get("WEBSERVICE_TASK_ID");//����ID
			System.out.println("�����ռ�======"+methodMap.get("WEB_NAME_SPACE"));//�����ռ�
			web_name_space=(String)methodMap.get("WEB_NAME_SPACE");//�����ռ�
			sql="select method_name_en from collect_webservice_task where webservice_task_id = '"+webservice_task_id+"'";
			tablepMap= new HashMap();
			tablepMap = daoTable.queryService(sql);
			updateSql.append(" update collect_webservice_task set");
			if(tablepMap!=null&&!"".equals(tablepMap)){
				methodName=(String)tablepMap.get("METHOD_NAME_EN");
				if(methodName!=null&&!"".equals(methodName)){//�д˷���
					if(method_name_cn!=null&&!"".equals(method_name_cn)){//����������
					updateSql.append(" method_name_cn = '"+method_name_cn+"',");
					}
					if(service_no!=null&&!"".equals(service_no)){//������
						updateSql.append(" service_no = '"+service_no+"',");
					}
					if(collect_table!=null&&!"".equals(collect_table)){//��Ӧ�ɼ���
						updateSql.append(" collect_table = '"+collect_table+"',");
					}
					if(collect_mode!=null&&!"".equals(collect_mode)){//���������ȫ��
						updateSql.append(" collect_mode = '"+collect_mode+"',");
					}
					if(is_encryption!=null&&!"".equals(is_encryption)){//������Ƿ�
						updateSql.append(" is_encryption = '"+is_encryption+"',");
					}
					if(encrypt_mode!=null&&!"".equals(encrypt_mode)){//�����DES3DSAES
						updateSql.append(" encrypt_mode = '"+encrypt_mode+"',");
					}
					if(web_name_space!=null&&!"".equals(web_name_space)){//�����ռ�
						updateSql.append(" web_name_space = '"+web_name_space+"',");
					}
					if(method_description!=null&&!"".equals(method_description)){//��������
						updateSql.append(" method_description = '"+method_description+"',");
					}
					if(updateSql.toString().endsWith(",")){//���·���
						String updateMethodSql=updateSql.toString();
						updateMethodSql=updateMethodSql.substring(0,updateMethodSql.length()-1);
						updateMethodSql+=" where webservice_task_id = '"+webservice_task_id+"'";
						System.out.println("���·�����sql======"+updateMethodSql);//����ID
						stmt.addSqlStmt(updateMethodSql);
					}
			 }//�д˷���
		 }//�д˷���
	  }//����ѭ��
    }//�жϷ���List�Ƿ�Ϊ��
  }
	
	/**
	 * ���²���
	 * @param methodList
	 * @param stmt
	 * @throws DBException 
	 */
	public void updateParam(List paramList,SqlStatement stmt,ServiceDAO	daoTable) throws DBException{
		
		//��ԭ��������ֵ���µ��²�����
		Map paramMap = new HashMap();
		StringBuffer sql=new StringBuffer();
		Map tablepMap = new HashMap();
		String paramName=null;
		String patameter_name=null;//������
		String patameter_type=null;//��������
		String patameter_value=null;//����ֵ
		String  webservice_task_id = null;//��Ӧ����ID
		
		StringBuffer updateSql= new StringBuffer();
		
		if(paramList!=null&&paramList.size()>0){
			for(int i=0;i<paramList.size();i++){
			updateSql= new StringBuffer();
			sql=new StringBuffer();
			paramMap = (Map) paramList.get(i);
			System.out.println("������======"+paramMap.get("PATAMETER_NAME"));//������
			patameter_name=(String)paramMap.get("PATAMETER_NAME");
			System.out.println("��������======"+paramMap.get("PATAMETER_TYPE"));//�������ʹ����StringINTBOOLEAN
			patameter_type=(String)paramMap.get("PATAMETER_TYPE");//��������
			System.out.println("����ֵ======"+paramMap.get("PATAMETER_VALUE"));//����ֵ
			patameter_value=(String)paramMap.get("PATAMETER_VALUE");//����ֵ
			System.out.println("��Ӧ����ID======"+paramMap.get("WEBSERVICE_TASK_ID"));//��Ӧ����ID
			webservice_task_id=(String)paramMap.get("WEBSERVICE_TASK_ID");//��Ӧ����ID
			
			
			sql.append("select patameter_name from collect_webservice_patameter a ");
			sql.append("where a.patameter_name = '"+patameter_name+"' and a.patameter_type = '"+patameter_type+"'");
			sql.append(" and a.webservice_task_id = '"+webservice_task_id+"'");
			System.out.println("��ѯ����======"+sql.toString());//��ѯ����
			tablepMap= new HashMap();
			tablepMap = daoTable.queryService(sql.toString());
			
			if(tablepMap!=null&&!"".equals(tablepMap)){
				paramName=(String)tablepMap.get("PATAMETER_NAME");
				if(paramName!=null&&!"".equals(paramName)){//�д˲���
					if(patameter_value!=null&&!"".equals(patameter_value)){//����ֵ
						updateSql.append(" update collect_webservice_patameter a set");
						updateSql.append(" a.patameter_value = '"+patameter_value+"'");
						updateSql.append(" where  a.patameter_name = '"+patameter_name+"' and a.patameter_type = '"+patameter_type+"'");
						updateSql.append(" and a.webservice_task_id = '"+webservice_task_id+"'");
						System.out.println("���²�����======"+updateSql.toString());//updateSql
						stmt.addSqlStmt(updateSql.toString());
					}
			 }//�д˲���
		 }//�д˲���
	  }//����ѭ��
    }//�жϷ���List�Ƿ�Ϊ��
  }
	
	 /**
	 * getFtpFile 
	 * ���ݷ����IDɾ����Ӧ����ftp�ļ�
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException 
	 * @throws TxnDataException 
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement getFtpFile(TxnContext request, DataBus inputData) throws DBException, TxnDataException
	{
		SqlStatement stmt = new SqlStatement();
		String data_source_id=request.getRecord("record").getValue("data_source_id");//����Դ��ID
		String collect_task_id=request.getRecord("record").getValue("collect_task_id");//���ݱ�ID
		String sql="select * from res_data_source where data_source_id = '"+data_source_id+"'";
	
		try {
			ServiceDAO	daoTable	= new ServiceDAOImpl(); // �������ݱ�Dao
			Map tablepMap = daoTable.queryService(sql);
			List fileList=DownloadFile.downLoadFile(tablepMap);////����ftp������
			
			String idFile=null;
			String service_no=null;
			String fileName=null;
			String nameParam=null;
			String typeParam=null;
			String methodNameTemp=null;
			
			
			int count=-1;
			count=this.getNum(CollectConstants.TYPE_FTP_TABLE,CollectConstants.TYPE_CJLX_FTP, daoTable);
			StringBuffer insertSql=new StringBuffer();
			
			StringBuffer selectSqlBuffer= new StringBuffer();
			selectSqlBuffer.append("select * from  collect_ftp_task where collect_task_id = '"+collect_task_id+"'");//��ѯftp�ļ���
			
			List ftpList=daoTable.query(selectSqlBuffer.toString());
			insertSql.append("delete from  collect_ftp_task where collect_task_id = '"+collect_task_id+"'");//ɾ��ftp�ļ���
			stmt.addSqlStmt(insertSql.toString());
			System.out.println("ɾ��ftp�ļ���  sql==="+insertSql);
			Map fileMap= new HashMap();
			if(fileList!=null&&fileList.size()>0){
				for(int i=0;i<fileList.size();i++) {
					fileMap=(Map)fileList.get(i);
					count=count+1;
					System.out.println("count==="+count);
					if(count<10){
						service_no="SERVICE_00"+count;
					}else if(count>=10&&count<=99){
						service_no="SERVICE_0"+count;
					}else{
						service_no="SERVICE_"+count;
					}
				
				fileName=(String)fileMap.get("filename");//�ļ���
				if(fileName!=null&&!"".equals(fileName)){
					if(ftpList!=null&&ftpList.size()>0){
						Map mapTemp = new HashMap();
						String wjName=null;
						for(int k=0;k<ftpList.size();k++){
							mapTemp = (Map) ftpList.get(k);
							wjName=(String)mapTemp.get("FILE_NAME_EN");
							if(wjName!=null&&wjName.equals(fileName)){
								idFile=(String)mapTemp.get("FTP_TASK_ID");
								System.out.println("id3333333333333333333333333333=========="+idFile);
								break;
							}else if(k==ftpList.size()){
								idFile = UuidGenerator.getUUID();
							}
						}
						
					}else{
						idFile = UuidGenerator.getUUID();
					}	
						insertSql= new StringBuffer();
						insertSql.append("insert into collect_ftp_task(ftp_task_id,collect_task_id,service_no,file_name_en,file_status) values('");
						insertSql.append(idFile+"','"+collect_task_id+"','"+service_no+"','"+fileName+"','"+CollectConstants.TYPE_QY+"')");
						System.out.println("insertSql����ftp�ļ���==="+insertSql);
						stmt.addSqlStmt(insertSql.toString());
				}
				
				
			}
			}//���»�ȡ����������
			System.out.println("����ɹ�!===");
			updateFile(ftpList,stmt,daoTable);//���·���
		
		}catch (Exception e) {
			//e.printStackTrace();
			throw new TxnDataException("error", "����ftp����Դʧ��!");
		}
		return stmt;
	}
	
	/**
	 * ����Ftp�ļ�
	 * @param methodList
	 * @param stmt
	 * @throws DBException 
	 */
	public void updateFile(List fileList,SqlStatement stmt,ServiceDAO	daoTable) throws DBException{
		
		//��ԭ�ļ�����ֵ���µ����ļ���
		Map fileMap = new HashMap();
		String sql=null;
		Map tablepMap = new HashMap();
		String fileName=null;
		String file_name_en=null;
		String file_name_cn=null;//����������
		String service_no=null;//������
		String collect_table=null;//��Ӧ�ɼ���
		String collect_mode=null;//���������ȫ��
		String file_description=null;//��������
		String  collect_task_id = null;//����ID
		StringBuffer updateSql= new StringBuffer();
		if(fileList!=null&&fileList.size()>0){
			for(int i=0;i<fileList.size();i++){
			updateSql= new StringBuffer();
			fileMap = (Map) fileList.get(i);
			System.out.println("�ļ�en��======"+fileMap.get("FILE_NAME_EN"));//�ļ�Ӣ������
			file_name_en=(String)fileMap.get("FILE_NAME_EN");
			System.out.println("�ļ�cn��======"+fileMap.get("FILE_NAME_CN"));//�ļ���������
			file_name_cn=(String)fileMap.get("FILE_NAME_CN");
			System.out.println("SERVICE_NO======"+fileMap.get("SERVICE_NO"));//service_no
			service_no=(String)fileMap.get("SERVICE_NO");
			System.out.println("��Ӧ�ɼ���======"+fileMap.get("COLLECT_TABLE"));//��Ӧ�ɼ���
			collect_table=(String)fileMap.get("COLLECT_TABLE");
			System.out.println("���������ȫ��======"+fileMap.get("COLLECT_MODE"));//���������ȫ��
			collect_mode=(String)fileMap.get("COLLECT_MODE");
			System.out.println("�ļ�����======"+fileMap.get("FILE_DESCRIPTION"));//�ļ�����
			file_description=(String)fileMap.get("FILE_DESCRIPTION");
			System.out.println("�ļ�ID======"+fileMap.get("FTP_TASK_ID"));//�ļ�ID
			collect_task_id=(String)fileMap.get("COLLECT_TASK_ID");//����ID
			sql="select file_name_en from collect_ftp_task where collect_task_id = '"+collect_task_id+"' and file_name_en = '"+file_name_en+"'";
			System.out.println("��ѯftp�ļ�sql==="+sql);
			tablepMap= new HashMap();
			tablepMap = daoTable.queryService(sql);
			updateSql.append(" update collect_ftp_task set");
			if(tablepMap!=null&&!tablepMap.isEmpty()){
					if(file_name_cn!=null&&!"".equals(file_name_cn)){//�ļ�������
					updateSql.append(" file_name_cn = '"+file_name_cn+"',");
					}
					if(service_no!=null&&!"".equals(service_no)){//������
						updateSql.append(" service_no = '"+service_no+"',");
					}
					if(collect_table!=null&&!"".equals(collect_table)){//��Ӧ�ɼ���
						updateSql.append(" collect_table = '"+collect_table+"',");
					}
					if(collect_mode!=null&&!"".equals(collect_mode)){//���������ȫ��
						updateSql.append(" collect_mode = '"+collect_mode+"',");
					}
					
					if(file_description!=null&&!"".equals(file_description)){//�ļ�����
						updateSql.append(" file_description = '"+file_description+"',");
					}
					if(updateSql.toString().endsWith(",")){//���·���
						String updateMethodSql=updateSql.toString();
						updateMethodSql=updateMethodSql.substring(0,updateMethodSql.length()-1);
						updateMethodSql+=" where collect_task_id = '"+collect_task_id+"' and file_name_en = '"+file_name_en+"'";
						System.out.println("����ftp�ļ���sql==="+updateMethodSql);
						stmt.addSqlStmt(updateMethodSql);
					}
			
		 }//�д��ļ�
	  }//����ѭ��
    }//�жϷ���List�Ƿ�Ϊ��
  }
	
	 /**
	 * downLoadFtpFile 
	 * ����Ftp�ļ�����ȡ�������
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException 
	 * @throws TxnDataException 
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement downLoadFtpFile(TxnContext request, DataBus inputData) throws DBException, TxnDataException
	{
		SqlStatement stmt = new SqlStatement();
		String collect_task_id=request.getRecord("primary-key").getValue("collect_task_id");//����ID
		StringBuffer sql = new StringBuffer();
		
		sql.append("select a.* from ");
		sql.append("res_data_source a,collect_task b ");
		sql.append("where a.data_source_id = b.data_source_id  and b.collect_task_id = '"+collect_task_id+"'");
	    System.out.println("sql==="+sql);
	    List fileList=null;
	    Map tablepMap=null;
	    ServiceDAO	daoTable	= new ServiceDAOImpl(); // �������ݱ�Dao
		try {
			
			tablepMap = daoTable.queryService(sql.toString());
			fileList=DownloadFile.downFtpFile(tablepMap,ExConstant.FILE_FTP);////��ȡftp�ļ������ص���������
		}catch (Exception e) {
			//e.printStackTrace();
			throw new TxnDataException("error", "����ftp����Դʧ��!");
		}
			Map map= new HashMap();
			String filename=null;
			String ftpFileName=null;
			String filetype=null;
			String filePath=null;
			String resultFile=null;
			String tableName=null;
			String tableId=null;
			String collect_mode=null;
			if(fileList!=null&&fileList.size()>0){
				for(int i=0;i<fileList.size();i++){
					map= (Map)fileList.get(i);
					filename=(String) map.get("filename");
					
					filePath=ExConstant.FILE_FTP+File.separator+filename;
					
					if(filename!=null&&!"".equals(filename)){
						tablepMap= new HashMap();
						filetype=filename.substring(filename.indexOf(".")+1);
						resultFile=ExConstant.FILE_FTP+File.separator+filename.substring(0,filename.indexOf("."))+"_result.txt";
						ftpFileName=filename.substring(filename.indexOf("_")+1);
						System.out.println("type=="+filetype);
						
							AnalyCollectFile file = new AnalyCollectFile();
							sql = new StringBuffer();
							sql.append("select a.*,b.table_name_en from ");
							sql.append("collect_ftp_task a,res_collect_table b ");
							sql.append("where a.collect_table = b.collect_table_id and a.file_name_en = '"+ftpFileName+"'  and a.collect_task_id = '"+collect_task_id+"'");
							System.out.println("��ѯftp�ļ���sql=="+sql);
							tablepMap = daoTable.queryService(sql.toString());
							tableName=(String)tablepMap.get("TABLE_NAME_EN");//�ɼ���
							tableId=(String)tablepMap.get("COLLECT_TABLE");//�ɼ���ID
							System.out.println("tableId=="+tableId);
							System.out.println("tableName=="+tableName);
							collect_mode=(String)tablepMap.get("COLLECT_MODE");//�ɼ���ʽ
							
							//����ftp�ļ��� �ļ�·��
							sql = new StringBuffer();
							sql.append("update collect_ftp_task set fj_path = '"+filePath+"'");
							sql.append(" where file_name_en = '"+ftpFileName+"'  and collect_task_id = '"+collect_task_id+"'");
							stmt.addSqlStmt(sql.toString());
							
							sql = new StringBuffer();
							sql.append("select a.* from ");
							sql.append("res_collect_dataitem a ");
							sql.append("where a.collect_table_id = '"+ tableId+"'");
							System.out.println("sql===="+sql);
							List list = daoTable.query(sql.toString());
							Map tableMap= new HashMap();
							HashMap resultMap=new HashMap();
							if(list!=null&&list.size()>0){
								for(int j=0;j<list.size();j++){
									tableMap=(Map)list.get(j);
									resultMap.put(j, tableMap.get("DATAITEM_NAME_EN").toString().toUpperCase());
								}
							}
						if(filetype!=null&&(filetype.equals("xls"))||filetype.equals("xlsx")){//excel
							//file.analyExcelData(request,filePath,tableName,resultMap,collect_mode,resultFile);
							
							
						}else if(filetype!=null&&filetype.equals("txt")){
							//file.analyTxtData(request,filePath,tableName,resultMap,collect_mode,",",resultFile);
							
						}else if(filetype!=null&&filetype.equals("mdb")){
							
						}
					}
					
				}
			
		
		
		}
		return stmt;
	}
	
	/**
	 * 
	 * queryFilePath(��ѯ����ȫ·��)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException        
	 * SqlStatement       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement queryFilePath(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String fj_fk = request.getRecord("record").getValue("fj_fk");

 		//StringBuffer querySql = new StringBuffer("select a.CCGML||'/'||b.cclj||'/'||b.wybs as file_path from xt_ccgl_wjlb a,xt_ccgl_wjys b where a.cclbbh_pk = b.cclbbh_pk ");
 		StringBuffer querySql = new StringBuffer("select b.cclj||'/'||b.wybs as file_path,a.cclbbh_pk from xt_ccgl_wjlb a,xt_ccgl_wjys b where a.cclbbh_pk = b.cclbbh_pk ");
 		if (fj_fk != null && !"".equals(fj_fk)) {
 			querySql.append(" and  b.ysbh_pk ='" + fj_fk + "'");
 		}
 		
 		System.out.println("��ѯ����Դʹ�������"+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
	
	/**
	 * 
	 * queryCollectTableInfo(��ѯ�ɼ����ֶ���)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException        
	 * SqlStatement       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement queryCollectTableInfo(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String collect_table = request.getRecord("record").getValue("collect_table");

 		StringBuffer querySql = new StringBuffer("select t1.table_name_en,t2.dataitem_name_en,t2.dataitem_name_cn from res_collect_table t1,res_collect_dataitem t2 where t1.collect_table_id= t2.collect_table_id ");
 		if (collect_table != null && !"".equals(collect_table)) {
 			querySql.append(" and  t1.collect_table_id ='" + collect_table + "'");
 		}
 		
 		System.out.println("��ѯ�ɼ����ֶ�������"+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
	
	/**
	 * 
	 * querySerTarName(���ݷ���id��ѯ�����������)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException        
	 * SqlStatement       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement querySerTarName(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String service_targets_id = request.getRecord("record").getValue("service_targets_id");

 		StringBuffer querySql = new StringBuffer("select distinct t2.service_targets_name from collect_task t,res_service_targets t2 where t.service_targets_id = t2.service_targets_id  ");
 		if (service_targets_id != null && !"".equals(service_targets_id)) {
 			querySql.append(" and  t.service_targets_id ='" + service_targets_id + "'");
 		}
 		
 		System.out.println("��ѯ�������"+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
	
	/**
	 * 
	 * getCollectFileResult(��ѯ�ļ��ɼ������Ϣ)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException        
	 * SqlStatement       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement getCollectFileResult(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String collect_joumal_id = request.getRecord("record").getValue("collect_joumal_id");

 		StringBuffer querySql = new StringBuffer("select collect_table_name,collect_data_amount,task_consume_time,return_codes from collect_joumal where 1=1  ");
 		if (collect_joumal_id != null && !"".equals(collect_joumal_id)) {
 			querySql.append(" and  collect_joumal_id ='" + collect_joumal_id + "'");
 		}
 		
 		System.out.println("��ѯ�ɼ��ļ������Ϣ��"+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
	
	/**
	 * 
	 * getFileUploadInfo(��ȡ�ļ��ϴ��ɼ�����Ϣ)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException        
	 * SqlStatement       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement getFileUploadInfo(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");

 		StringBuffer querySql = new StringBuffer("select t.collect_task_id,t.service_targets_id,t.data_source_id,t.task_name,t.collect_type,t.task_description,t.record,t.task_status,t.fj_fk,t.fjmc,t.log_file_path,t.collect_status,t1.file_upload_task_id,t1.collect_table,t1.collect_mode,t1.check_result_file_name from collect_task t,collect_file_upload_task t1 where t.collect_task_id=t1.collect_task_id ");
 		if (collect_task_id != null && !"".equals(collect_task_id)) {
 			querySql.append(" and  t.collect_task_id ='" + collect_task_id + "'");
 		}
 		
 		System.out.println("��ѯ�ļ��ϴ��ɼ���Ϣ��"+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
	/**
	 * ��ȡ������
	 * @param tableName
	 * @param daoTable
	 * @return
	 * @throws DBException
	 */
	public int getNum(String tableName,String type,ServiceDAO daoTable) throws DBException{
		int num=0;
		String sql="select service_no from "+tableName+" a,collect_task b where a.collect_task_id = b.collect_task_id "
			+" and b.collect_type = '"+type+"' order by service_no desc";
		System.out.println("��ȡ������sql=========="+sql);
		List list = daoTable.query(sql);
		Map map=new HashMap();
		if(list!=null&&list.size()>0){
			map=(HashMap)list.get(0);
			String service_no = (String)map.get("SERVICE_NO");//���ֵ
			if(service_no!=null&&!"".equals(service_no)){
				service_no=service_no.substring(service_no.indexOf("_")+1);
				num =Integer.parseInt(service_no);
			}
		}
		System.out.println("num==="+num);
		return num;
	}
	
	/**
	 * 
	 * deleteFileUpload(ɾ���ļ��ϴ��ɼ�����)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws DBException        
	 * SqlStatement       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement deleteFileUpload(TxnContext request, DataBus inputData) throws DBException
	{
		SqlStatement stmt = new SqlStatement();
		DataBus data = request.getRecord("primary-key");
		String collect_task_id = data.getValue("collect_task_id");
	
		StringBuffer updateSql =new StringBuffer("update collect_task t set is_markup='N' " +
																		"  where t.collect_task_id='"+collect_task_id+"'");
		System.out.println("ɾ���ļ��ϴ��ɼ�����:::"+updateSql.toString());
		stmt.addSqlStmt(updateSql.toString());
		return stmt;
	}
	
	/**
	 * ��ѯÿ�������б�
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException
	 */
	public SqlStatement queryTaskScheduleListForIndex(TxnContext request,
			DataBus inputData) throws TxnException
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sqlBuffer = new StringBuffer();
		String year = request.getRecord("select-key").getValue("year");
		String month = request.getRecord("select-key").getValue("month");
		String day = request.getRecord("select-key").getValue("day");
		if (StringUtils.isNotBlank(year) && StringUtils.isNotBlank(month)
				&& StringUtils.isNotBlank(day)) {
			sqlBuffer
					.append("with t1 as (select ct.collect_task_id,")
					.append(" ct.task_name, cts.start_time, cts.end_time,")
					.append(" cts.scheduling_type, cts.scheduling_week, cts.scheduling_day")
					.append(" from collect_task_scheduling cts, collect_task ct")
					.append(" where cts.is_markup = 'Y' and ct.is_markup = 'Y'")
					.append(" and cts.collect_task_id = ct.collect_task_id)");
			sqlBuffer
				.append("select * from (select t1.collect_task_id, t1.task_name, t1.start_time, t1.end_time ")
				.append("from t1 ")
				.append("where t1.scheduling_type = '01' ")
				.append("union all ")
				.append("select t1.collect_task_id, t1.task_name, t1.start_time, t1.end_time ")
				.append("from t1 ")
				.append("where t1.scheduling_type = '02' ")
				.append("and t1.scheduling_week like ")
				.append("'%' || (select to_char(to_date('"+year+"-"+((month.length()>1) ? month : "0"+month)+"-"+((day.length()>1) ? day : "0"+day)+"', 'yyyy-mm-dd') - 1, 'd') ")
				.append("from dual) || '%' ")
				.append("union all ")
				.append("select t1.collect_task_id, t1.task_name, t1.start_time, t1.end_time ")
				.append("from t1 ")
				.append("where t1.scheduling_type = '03' ")
				.append("and t1.scheduling_day = '"+day+"' ")
				.append(") t order by t.start_time ");
		}
		//System.out.println("taskSql = "+sqlBuffer.toString());
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}
	
	/**
	 * 
	 * queryKeyColumns(��ѯ�ɼ����������Ϣ)    
	 * TODO(����������������������� �C ��ѡ)    
	 * TODO(�����������������ִ������ �C ��ѡ)    
	 * TODO(�����������������ʹ�÷��� �C ��ѡ)    
	 * TODO(�����������������ע������ �C ��ѡ)    
	 * @param request
	 * @param inputData
	 * @return
	 * @throws TxnException        
	 * SqlStatement       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement queryKeyColumns(TxnContext request,
 			DataBus inputData) throws TxnException
 	{
 		SqlStatement stmt = new SqlStatement();

 		String collect_table = request.getRecord("record").getValue("collect_table");

 		StringBuffer querySql = new StringBuffer("select t1.dataitem_name_en from res_collect_dataitem t1 where 1=1 ");
 		if (collect_table != null && !"".equals(collect_table)) {
 			querySql.append(" and  t1.collect_table_id ='" + collect_table + "'");
 		}
 		
 		querySql.append(" and t1.is_key='1' ");
 		System.out.println("��ѯ�ɼ����ֶ�������"+querySql.toString());
 		stmt.addSqlStmt(querySql.toString());
 		stmt.setCountStmt("select count(1) from (" + querySql.toString() + ")");
 		return stmt;

 	}
	
	/**
	 * deleteTaskItem 
	 * ���ݷ����IDɾ����Ӧ���з���������
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException 
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement deleteTaskDatabaseItem(TxnContext request, DataBus inputData) throws DBException
	{
		String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//����ID
		if(collect_task_id==null||"".equals(collect_task_id)){
			collect_task_id = request.getRecord("record").getValue("collect_task_id");
		}
		StringBuffer sqlBuffer = new StringBuffer("delete from collect_database_task t ");
		SqlStatement stmt = new SqlStatement();
		if(collect_task_id!=null&&!"".equals(collect_task_id)){
			sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"'");
			stmt.addSqlStmt(sqlBuffer.toString());
			
			sqlBuffer = new StringBuffer();
			sqlBuffer.append("delete from collect_task_scheduling t ");
			if(collect_task_id!=null){
				sqlBuffer.append(" where t.collect_task_id = '"+collect_task_id+"'");//ɾ��������ȱ�
			}
			stmt.addSqlStmt(sqlBuffer.toString());
			
		}
		
		return stmt;
	}
	
	
	   /**
	    * ���ݲɼ�����ID��ѯ��Ӧ�ɼ�����ֶ�
	    * @param request
	    * @param inputData
	    * @return
	    */
		public SqlStatement getCollectTaskTableAndDataitems(TxnContext request, DataBus inputData)
		{
			
			String collect_task_id = request.getRecord("primary-key").getValue("collect_task_id");//����ID
			SqlStatement stmt = new SqlStatement();
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append("select distinct * from view_collect_task_table t where collect_task_id='");
			sqlBuffer.append(collect_task_id);
			sqlBuffer.append("'");
			stmt.addSqlStmt(sqlBuffer.toString());
			//System.out.println(sqlBuffer.toString());
			return stmt;
		}
	/**
    * ���ݲɼ�����ID��ѯ��Ӧͳ������
    * @param request
    * @param inputData
    * @return
    */
	public SqlStatement getCollectTaskInfo(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		DataBus db= request.getRecord("select-key");
		String collect_task_id = db.getValue("collect_task_id");//����ID
		if(collect_task_id!=null && !"".equals(collect_task_id)){
			String start_time = db.getValue("start_time");
			String end_time = db.getValue("end_time");
			String task_name = db.getValue("task_name");
			if((start_time==null || "".equals(start_time)) && (end_time==null || "".equals(end_time))){
				start_time ="to_char(add_months(sysdate,-2),'yyyy-mm-dd')";
				end_time ="to_char(sysdate,'yyyy-mm-dd')";
			}else{
				if(start_time==null || "".equals(start_time)){
					start_time="to_char(add_months(sysdate,-12),'yyyy-mm-dd')";
				}else{
					start_time="'"+start_time+"'";
				}
				if(end_time==null || "".equals(end_time)){
					end_time="to_char(sysdate,'yyyy-mm-dd')";
					
				}else{
					end_time="'"+end_time+"'";
				}
				
				
			}
			
			
			sql.append("with a as (select d.log_date from (select")
				.append(" to_char(to_date("+start_time+",'YYYY-mm-dd')+ rownum - 1,'YYYY-mm-dd')")
				.append(" as log_date from dual connect by rownum <=")
				.append(" (to_date("+end_time+", 'yyyy-mm-dd') -")
				.append(" to_date("+start_time+", 'yyyy-mm-dd')+1)) d")
			    .append(" where d.log_date not in")              
			    .append(" (select exception_Date log_date from exception_date))")
			    .append(" select a.log_date,nvl(l.sum_num, 0) sum_num,nvl(l.sum_count, 0) sum_count,")     
			    .append(" nvl(l.avg_time, 0) avg_time,nvl(l.error_num, 0) error_num from a,")
			    .append(" (select log_date,sum(t.sum_record_amount) sum_num,sum(t.exec_count) sum_count,")   
			    .append(" TO_CHAR(sum(t.sum_consume_time) / sum(t.exec_count),'fm999999990.99') avg_time,")   
			    .append(" sum(t.error_num) error_num from collect_log_statistics t where 1 = 1")     
			    .append(" and t.log_date >= "+start_time+" and t.log_date <= "+end_time+"")                   
			    .append(" and t.task_id = '"+collect_task_id+"'")           
			    .append(" group by log_date) l where a.log_date = l.log_date(+) order by log_date"); 
			/*
			sql.append("select log_date,sum(t.sum_record_amount) sum_num,")
				.append(" sum(t.exec_count) sum_count,")
				.append(" TO_CHAR(sum(t.sum_consume_time) / sum(t.exec_count),")
				.append(" 'fm999999990.99') avg_time,0 as  error_num")
				.append(" from collect_log_statistics t where 1 = 1");
		    if(start_time==null && end_time==null){//Ĭ�ϵ���
		    	sql.append(" and log_date>=to_char(add_months(sysdate, -2), 'yyyy-MM') || '-01'")
					.append(" and log_date < to_char(add_months(sysdate, 1), 'yyyy-MM') || '-01'");
		    }
		    if(start_time!=null){
		    	sql.append(" and t.log_date >= '"+start_time+"'");
		    }
		    if(end_time!=null){
		    	sql.append(" and t.log_date <= '"+end_time+"'");
		    }
		    
		    sql.append(" and t.task_id = '"+collect_task_id+"'")
		    	.append(" and t.log_date not in (select exception_Date from exception_date)")
		    	.append(" group by log_date")
		    	.append(" order by log_date");
		   */
			stmt.addSqlStmt(sql.toString());
		}else{
			stmt.addSqlStmt("");
		}
		
		System.out.println(sql.toString());
		return stmt;
	}
	
	/**
	    * ����FTP�ɼ�����ID��ȡ�ļ���Ϣ
	    * @param request
	    * @param inputData
	    * @return
	    */
	public SqlStatement getFileInfoTree(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		String collect_task_id = request.getRecord("select-key").getValue(
				"collect_task_id");
		//System.out.println(request);
		sql.append("select * from collect_ftp_task where collect_task_id='")
				.append(collect_task_id).append("'");
		stmt.addSqlStmt(sql.toString());
		
		
		stmt.setCountStmt("select count(1) from ("+sql.toString()+")");
		System.out.println("getFileInfoTree="+sql.toString());
		return stmt;
	}
	/**
	    * �����ļ�ID��ȡ�ļ���Ϣ
	    * @param request
	    * @param inputData
	    * @return
	    */
	public SqlStatement getFileInfo(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		String ftp_task_id = request.getRecord("select-key").getValue(
				"ftp_task_id");
		sql.append("select * from collect_ftp_task where ftp_task_id='")
				.append(ftp_task_id).append("'");
		System.out.println("getFileInfo---"+sql.toString());
		stmt.addSqlStmt(sql.toString());
		
		return stmt;
	}
	/**
	    * ��ȡ������Ϣ
	    * @param request
	    * @param inputData
	    * @return
	    */
	public SqlStatement getFTPTaskInfo(TxnContext request, DataBus inputData)
	{
		SqlStatement stmt = new SqlStatement();
		StringBuffer sql = new StringBuffer();
		//System.out.println(request);
		//System.out.println(inputData);
		String collect_task_id = request.getRecord("select-key").getValue(
				"collect_task_id");
		
			sql.append("select c.*,k.task_scheduling_id,k.scheduling_type,k.scheduling_week,k.scheduling_day,k.start_time,k.end_time,k.scheduling_day1,k.scheduling_count,k.interval_time from (")
			.append("select * from collect_task t where")
			.append(" t.collect_task_id = '")
			.append(collect_task_id).append("') c,")
            .append("(select * from collect_task_scheduling where is_markup = 'Y' ) k" )   
            .append(" where c.collect_task_id = k.collect_task_id(+)"); 
			stmt.addSqlStmt(sql.toString());
			
		System.out.println("getFTPTaskInfo="+sql.toString());
		return stmt;
	}
			
		



}