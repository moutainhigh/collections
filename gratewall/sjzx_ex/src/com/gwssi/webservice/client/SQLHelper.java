package com.gwssi.webservice.client;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.gwssi.common.util.StringUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.log.collectlog.dao.CollectLogVo;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�SQLHelper ����������֯Ҫִ�е�SQL�ķ��� �����ˣ�lizheng ����ʱ�䣺Apr 10,
 * 2013 10:30:14 AM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 10, 2013 10:30:14 AM �޸ı�ע��
 * 
 * @version
 * 
 */
public class SQLHelper
{
	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(SQLHelper.class
											.getName());

	/**
	 * 
	 * queryTargetNo ��ѯ�ɼ������������
	 * 
	 * @param taskId
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String queryTargetNo(String taskId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select tar.service_targets_no as TARGETNO "
				+ "from res_service_targets tar,collect_task task "
				+ "where tar.service_targets_id = task.service_targets_id "
				+ "and task.collect_task_id = ");
		sql.append("'");
		sql.append(taskId);
		sql.append("'");
		logger.debug("����queryTargetNoִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryCollectType ��ѯ��������
	 * 
	 * @param taskId
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String queryCollectType(String taskId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select t.collect_type from collect_task t "
				+ "where t.collect_task_id = ");
		sql.append("'");
		sql.append(taskId);
		sql.append("'");
		logger.debug("����queryCollectTypeִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryTask ��������ID��ѯ������Ϣ
	 * 
	 * @param taskId
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String queryTask(String taskId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from collect_task t "
				+ "where t.collect_task_id = ");
		sql.append("'");
		sql.append(taskId);
		sql.append("'");
		logger.debug("����queryTaskִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * querySrvTager ���ݷ������ID��ѯ���������Ϣ
	 * 
	 * @param taskId
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String querySrvTager(String srvTargetId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from res_service_targets t "
				+ "where t.service_targets_id = ");
		sql.append("'");
		sql.append(srvTargetId);
		sql.append("'");
		logger.debug("����querySrvTagerִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryDataSource ����taskId��ѯ����Դ��Ϣ
	 * 
	 * @param taskId
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String queryDataSource(String taskId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from res_data_source t "
				+ "where t.data_source_id = " + "(select task.data_source_id "
				+ "from collect_task task " + "where task.collect_task_id = ");
		sql.append("'");
		sql.append(taskId);
		sql.append("')");
		logger.debug("����queryDataSourceִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryMethodListSQL ����taskID��ѯ���������ж��ٷ���
	 * 
	 * @param taskId
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String queryMethodListSQL(String taskId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from collect_webservice_task t "
				+ "where t.collect_task_id =  ");
		sql.append("'");
		sql.append(taskId);
		sql.append("'");
		sql.append(" and t.METHOD_STATUS = '1'");
		logger.debug("����queryMethodListSQLִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryParam ����webserviceTaskId��ѯ����
	 * 
	 * @param wsTaskId
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String queryParam(String wsTaskId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from collect_webservice_patameter t "
				+ "where t.webservice_task_id =  ");
		sql.append("'");
		sql.append(wsTaskId);
		sql.append("'");
		logger.debug("����queryParamִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryParamValue ����paramId��ѯ����ֵ
	 * 
	 * @param paramId
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String queryParamValue(String paramId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from collect_ws_param_value t "
				+ "where t.webservice_patameter_id = ");
		sql.append("'");
		sql.append(paramId);
		sql.append("' ");
		sql.append("order by t.showorder");
		logger.debug("����queryParamValueִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryTable ����tableId��ѯ����Ϣ
	 * 
	 * @param tableId
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String queryTable(String tableId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select * from res_collect_table t "
				+ "where t.collect_table_id = ");
		sql.append("'");
		sql.append(tableId);
		sql.append("'");
		logger.debug("����queryTableִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * queryDataitem ���ݱ�ID��ѯ�ñ�����ֶ�
	 * 
	 * @param tableId
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static String queryDataitem(String tableId)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select t.dataitem_name_en from res_collect_dataitem t "
				+ "where t.collect_table_id = ");
		sql.append("'");
		sql.append(tableId);
		sql.append("'");
		logger.debug("����queryDataitemִ�е�SQLΪ��" + sql);
		return sql.toString();
	}

	/**
	 * 
	 * insertCollectData ���ݱ������ֶ����ƺͷ��ص����ݷ�װinsert���
	 * 
	 * @param tableName
	 * @param columnList
	 * @param dataMap
	 * @return StringBuffer
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static StringBuffer insertCollectData(
			StringBuffer insertSql, List columnList, Map dataMap)
	{
		// ���ݸ���ʱ��
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String update_date = sDateFormat.format(new java.util.Date());

		StringBuffer valuesSql = new StringBuffer();
		valuesSql.append(insertSql);
		for (int j = 0; j < columnList.size(); j++) {
			if ("UPDATE_DATE".equals(columnList.get(j))) {
				valuesSql.append("'");
				valuesSql.append(update_date);
				valuesSql.append("'");
			} else {
				valuesSql.append("'");
				if (null != dataMap.get(columnList.get(j))
						&& !""
								.equals(dataMap.get(columnList.get(j))
										.toString()))
					valuesSql.append(dataMap.get(columnList.get(j)));
				else
					valuesSql.append("");
				valuesSql.append("'");
			}
			if (j < columnList.size() - 1)
				valuesSql.append(",");
			else if (j == columnList.size() - 1)
				valuesSql.append(")");
		}
		return valuesSql;
	}

	/**
	 * 
	 * deleteCollectData ɾ���ɼ�����ԭ�е�����
	 * 
	 * @param tableName
	 * @return StringBuffer
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static StringBuffer deleteCollectData(String tableName)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ");
		sql.append(tableName);
		return sql;
	}

	/**
	 * 
	 * insertCollectLog ������־
	 * 
	 * @param collectLogVo
	 * @return StringBuffer
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public synchronized static StringBuffer insertCollectLog(CollectLogVo vo)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into collect_joumal (collect_joumal_id,"
				+ "collect_task_id,task_name,service_targets_id,"
				+ "service_targets_name,collect_type,task_id,"
				+ "service_no,task_start_time,task_end_time,"
				+ "task_consume_time,collect_data_amount,task_status,"
				+ "patameter,return_codes,collect_mode,"
				+ "collect_table,collect_table_name,"
				+ "method_name_cn,method_name_en,"
				+ "collect_column_name,invoke_consume_time,"
				+ "insert_consume_time,batch_num,is_formal) values (");
		sql.append("'" + UuidGenerator.getUUID() + "'");
		sql.append(",");
		sql
				.append("'" + StringUtil.nullToEmpty(vo.getCollect_task_id())
						+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getTask_name()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getService_targets_id())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getService_targets_name())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getCollect_type()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getTask_id()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getService_no()) + "',");
		sql
				.append("'" + StringUtil.nullToEmpty(vo.getTask_start_time())
						+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getTask_end_time()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getTask_consume_time())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getCollect_data_amount())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getTask_status()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getPatameter()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getReturn_codes()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getCollect_mode()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getCollect_table()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getCollect_table_name())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getMethod_name_cn()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getMethod_name_en()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getCollect_column_name())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getInvoke_consume_time())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getInsert_consume_time())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getBatch_num()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getIs_formal()) + "'");
		sql.append(")");
		logger.debug("����insertCollectLogִ�е�SQLΪ��" + sql);
		return sql;
	}

	public synchronized static StringBuffer insertCollectLog(String id,
			CollectLogVo vo)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into collect_joumal (collect_joumal_id,"
				+ "collect_task_id,task_name,service_targets_id,"
				+ "service_targets_name,collect_type,task_id,"
				+ "service_no,task_start_time,task_end_time,"
				+ "task_consume_time,collect_data_amount,task_status,"
				+ "patameter,return_codes,collect_mode,"
				+ "collect_table,collect_table_name,method_name_cn,method_name_en,batch_num,is_formal ) values (");
		sql.append("'" + id + "'");
		sql.append(",");
		sql
				.append("'" + StringUtil.nullToEmpty(vo.getCollect_task_id())
						+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getTask_name()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getService_targets_id())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getService_targets_name())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getCollect_type()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getTask_id()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getService_no()) + "',");
		sql
				.append("'" + StringUtil.nullToEmpty(vo.getTask_start_time())
						+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getTask_end_time()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getTask_consume_time())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getCollect_data_amount())
				+ "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getTask_status()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getPatameter()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getReturn_codes()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getCollect_mode()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getCollect_table()) + "',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getCollect_table_name())+"',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getMethod_name_cn())+"',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getMethod_name_en())+"',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getBatch_num())+"',");
		sql.append("'" + StringUtil.nullToEmpty(vo.getIs_formal())
				+ "'");
		sql.append(")");
		logger.debug("����insertCollectLogִ�е�SQLΪ��" + sql);
		return sql;
	}
	
	public synchronized static StringBuffer getFilesInfo(String collect_task_id){
		StringBuffer sql= new StringBuffer();
		sql.append("select a.data_source_ip,a.access_port,a.access_url,")
			.append(" a.db_username,a.db_password,b.service_targets_id,b.task_name,b.collect_type,b.creator_id,b.last_modify_id,b.log_file_path,c.* ")
			.append(" ,d.table_name_en,e.service_targets_name")
			.append(" from res_data_source a, collect_task b, collect_ftp_task c ,res_collect_table d ,res_service_targets e")   
			.append(" where a.data_source_id = b.data_source_id")
			.append(" and c.collect_table = d.collect_table_id")
			.append(" and b.collect_task_id=c.collect_task_id")
			.append(" and d.service_targets_id=e.service_targets_id")
			.append(" and c.collect_task_id='")
			.append(collect_task_id).append("'");
		System.out.println("getFilesInfosql===" + sql);
		return sql;
	}
	public synchronized static StringBuffer getDataItems(String tableId){
		StringBuffer sql= new StringBuffer();
		sql.append("select a.* from res_collect_dataitem a ");
		sql.append("where a.collect_table_id = '" + tableId + "'");
		System.out.println("getDataItemsql===" + sql);
		return sql;
	}
	
	public synchronized static StringBuffer getManualSource(String data_source_id,String collect_table_id ){
		StringBuffer sql= new StringBuffer();
		sql.append("select a.data_source_ip,a.access_port,a.access_url,")
			.append("a.db_username,a.db_password,b.table_name_en")
			.append(" from res_data_source a,res_collect_table b")
			.append(" where a.service_targets_id=b.service_targets_id ")
			.append(" and a.data_source_id = '")
			.append(data_source_id)
			.append("' and b.collect_table_id='")
			.append(collect_table_id)
			.append("'");
 
		System.out.println("getManualSource===" + sql);
		return sql;
	}
	
}
