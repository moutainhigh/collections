package com.gwssi.collect.etl.dao;

import org.apache.commons.lang.StringUtils;

import com.genersoft.frame.base.database.DBException;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.func.SqlStatement;
import cn.gwssi.common.dao.iface.DaoFunction;
import cn.gwssi.common.component.exception.TxnException;

public class EtlSubject extends BaseTable
{
	public EtlSubject()
	{

	}

	/**
	 * ע��SQL���
	 */
	protected void register()
	{

		registerSQLFunction("getEtlTaskList", DaoFunction.SQL_ROWSET,
				"��ȡETL���������");
		registerSQLFunction("getEtlMinWorkRunId", DaoFunction.SQL_SELECT,
				"��ȡETL������С����ID");
		registerSQLFunction("getEtlWorkletTree", DaoFunction.SQL_ROWSET,
				"��ȡETL�������ṹ");
		registerSQLFunction("getEtlTaskDetail", DaoFunction.SQL_ROWSET,
				"��ȡETL�������б�");
		registerSQLFunction("getCollectTableDetail", DaoFunction.SQL_ROWSET,
		"��ȡETL�ɼ��������Ϣ");
		registerSQLFunction("getSubjectNum", DaoFunction.SQL_ROWSET,
		"��ȡETL�ɼ�������ÿ�������Ӧ�Ĳɼ���ĸ���");
		registerSQLFunction("getEtlInfo", DaoFunction.SQL_SELECT,
		"��ȡEtl�ɼ�������Ϣ");
	}

	/**
	 * getEtlTaskList
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement getEtlTaskList(TxnContext request, DataBus inputData)
			throws DBException
	{
		StringBuffer sqlBuffer = new StringBuffer(
				"select * from etl_subject t where t.is_show=0 ");
		sqlBuffer.append(" order by t.subj_sort,t.start_time");
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}

	/**
	 * getEtlTaskList
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement getEtlMinWorkRunId(TxnContext request, DataBus inputData)
			throws DBException
	{
		String workflow_id = request.getRecord("select-key").getValue(
				"workflow_id");//
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append(" select workflow_run_id from( select t.workflow_run_id,row_number()");
		sqlBuffer
				.append(" over(order by workflow_run_id desc)rn FROM OPB_TASK_INST_RUN@ETL t ");
		sqlBuffer.append("where t.workflow_id = ").append(workflow_id)
				.append(") where rn=3");
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}

	/**
	 * getEtlTaskList
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement getEtlWorkletTree(TxnContext request, DataBus inputData)
			throws DBException
	{
		String run_id = request.getRecord("minId").getValue("workflow_run_id");//
		String workflow_id = request.getRecord("select-key").getValue(
				"workflow_id");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("select a.*,nvl(e.work_desc,a.instance_name) work_desc from( select t.workflow_id,t.workflow_run_id");
		sqlBuffer.append(",t.worklet_run_id,t.child_run_id,t.instance_name");
		sqlBuffer
				.append(" FROM OPB_TASK_INST_RUN@ETL t where t.workflow_id = ")
				.append(workflow_id);
		sqlBuffer.append(" and t.workflow_run_id=").append(run_id);
		sqlBuffer.append(" and child_run_id > 0)a,etl_worklet_compare e");
		sqlBuffer.append(" where a.instance_name=e.worklet_name(+)");
		sqlBuffer.append(" and a.workflow_id=e.workfolw_id(+)");
		System.out.println(sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}

	/**
	 * getEtlTaskList
	 * 
	 * @param request
	 * @param inputData
	 * @return SqlStatement
	 * @throws DBException
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement getEtlTaskDetail(TxnContext request, DataBus inputData)
			throws DBException
	{
		String workflow_id = request.getRecord("select-key").getValue(
				"workflow_id");//
		String workflow_run_id = request.getRecord("select-key").getValue(
				"workflow_run_id");
		String worklet_run_id = request.getRecord("select-key").getValue(
				"worklet_run_id");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select rownum tno,instance_name,remark from(");
		sqlBuffer.append("select t.instance_name,k.comments remark FROM OPB_TASK_INST_RUN@ETL t");
		sqlBuffer.append(",opb_task@ETL k where t.workflow_id =").append(workflow_id);
		sqlBuffer.append(" and t.workflow_run_id =").append(workflow_run_id);
		if (StringUtils.isNotBlank(worklet_run_id)) {
			sqlBuffer.append(" and t.worklet_run_id =").append(worklet_run_id);
		}else {
			sqlBuffer.append(" and t.child_run_id=0 and t.worklet_run_id=0");
		}
		sqlBuffer.append(" and t.instance_name!='Start' and t.task_id=k.task_id order by t.task_id)");
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}
	
	/**
	 * 
	 * getCollectTableDetail(��ȡETL�ɼ��������Ϣ)    
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
	public SqlStatement getCollectTableDetail(TxnContext request, DataBus inputData)
		throws DBException
	{
		String etl_id = request.getRecord("primary-key").getValue(
				"etl_id");//
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select t1.*, t2.sys_name,t2.table_name_cn,t2.table_name_en,t2.task_name from etl_subject t1,etl_db_statistics t2 where t1.etl_id = t2.task_id and t2.table_name_en is not null and t2.table_name_cn is not null  ");
		
		if(etl_id!=null&&!"".equals(etl_id)){
			sqlBuffer.append(" and t1.etl_id ='"+etl_id+"' ");
		}
		
		sqlBuffer.append(" order by t2.sys_name");
		
		//System.out.println("�ɼ����ѯsql="+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}
	
	
	/**
	 * 
	 * getSubjectNum(������һ�仰�����������������)       
	 * @param request
	 * @param inputData
	 * @return
	 * @throws DBException        
	 * SqlStatement       
	 * @Exception �쳣����    
	 * @since  CodingExample��Ver(���뷶���鿴) 1.1
	 */
	public SqlStatement getSubjectNum(TxnContext request, DataBus inputData)
	throws DBException
	{
		String etl_id = request.getRecord("primary-key").getValue(
				"etl_id");//
		
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select t1.*,t.show_order from res_business_topics t,(select sys_name,count(*) as total from etl_subject t1,etl_db_statistics t2 where t1.etl_id = t2.task_id and t2.table_name_en is not null and t2.table_name_cn is not null  ");
		
		if(etl_id!=null&&!"".equals(etl_id)){
			sqlBuffer.append(" and t1.etl_id ='"+etl_id+"' ");
		}
		
		sqlBuffer.append(" group by sys_name ) t1 where t.topics_name = t1.sys_name  order by t.show_order ");
		
		//System.out.println("�ɼ��������sql="+sqlBuffer.toString());
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
	}
	
	public SqlStatement getEtlInfo(TxnContext request, DataBus inputData)
	throws DBException
	{
		String etl_id = request.getRecord("primary-key").getValue(
				"etl_id");//
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append(" select t.*,t1.service_targets_name from etl_subject t,res_service_targets t1 where t.res_target_id = t1.service_targets_id ");
		if(etl_id!=null&&!"".equals(etl_id)){
			sqlBuffer.append(" and t.etl_id = '"+etl_id+"' ");
		}
		
		SqlStatement stmt = new SqlStatement();
		stmt.addSqlStmt(sqlBuffer.toString());
		return stmt;
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

}
