package com.gwssi.common.log;

import org.apache.commons.lang.StringUtils;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.log.collectlog.dao.CollectLogVo;
import com.gwssi.log.sharelog.dao.*;

public class LogUtil
{

	public void saveShareLog(ShareLogVo sharelog)
	{
		String log_id = UuidGenerator.getUUID();
		String service_targets_id =StringUtils.isNotBlank(sharelog.getService_targets_id()) ? sharelog.getService_targets_id() :"" ;
		String service_type = StringUtils.isNotBlank(sharelog.getService_type()) ? sharelog.getService_type() :"" ;
		String service_name = StringUtils.isNotBlank(sharelog.getService_name()) ? sharelog.getService_name() :"" ;
		String service_start_time = StringUtils.isNotBlank(sharelog.getService_start_time()) ? sharelog.getService_start_time() :"" ;
		String service_end_time = StringUtils.isNotBlank(sharelog.getService_end_time()) ? sharelog.getService_end_time() :"";
		String service_id = StringUtils.isNotBlank(sharelog.getService_id()) ? sharelog.getService_id() :"" ;
		String access_ip = StringUtils.isNotBlank(sharelog.getAccess_ip()) ? sharelog.getAccess_ip() : "";
		String consume_time = StringUtils.isNotBlank(sharelog.getConsume_time()) ? sharelog.getConsume_time() :"" ;
		String record_start = StringUtils.isNotBlank(sharelog.getRecord_start()) ? sharelog.getRecord_start() :"" ;
		String record_end =StringUtils.isNotBlank(sharelog.getRecord_end()) ? sharelog.getRecord_end() : "";
		String record_amount = StringUtils.isNotBlank(sharelog.getRecord_amount()) ? sharelog.getRecord_amount() :"" ;
		String patameter = StringUtils.isNotBlank(sharelog.getPatameter()) ? sharelog.getPatameter() : "";
		String service_state = StringUtils.isNotBlank(sharelog.getService_state()) ? sharelog.getService_state() : "";
		String return_codes = StringUtils.isNotBlank(sharelog.getReturn_codes()) ? sharelog.getReturn_codes() : "";
		String sql = "insert into share_log(service_targets_id,"
				+ "log_id,service_type,service_start_time,"
				+ "service_end_time,service_name,service_id,access_ip,"
				+ "consume_time,record_start,record_end,record_amount,"
				+ "patameter,service_state,return_codes) values('"
				+ service_targets_id
				+ "','"
				+ log_id
				+ "','"
				+ service_type
				+ "','"
				+ service_start_time
				+ "','"
				+ service_end_time
				+ "','"
				+ service_name
				+ "','"
				+ service_id
				+ "','"
				+ access_ip
				+ "','"
				+consume_time
				+"','"
				+record_start
				+"','"
				+record_end
				+"','"
				+record_amount
				+"','"
				+ patameter
				+ "','"
				+ service_state
				+ "','"
				+ return_codes
				+ "')";
		DBOperation operation = DBOperationFactory.createTimeOutOperation();
		try {
			operation.execute(sql, true);
			System.out.println("保存成功");
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
//	public void saveCollectLog(CollectLogVo sharelog)
//	{
//		String collect_joumal_id = UuidGenerator.getUUID();
//		String collect_task_id =StringUtils.isNotBlank(sharelog.getCollect_task_id()) ? sharelog.getCollect_task_id() :"" ;
//		String task_name = StringUtils.isNotBlank(sharelog.getTask_name()) ? sharelog.getTask_name() :"" ;
//		String service_targets_id = StringUtils.isNotBlank(sharelog.getService_targets_id()) ? sharelog.getService_targets_id() :"" ;
//		String service_targets_name = StringUtils.isNotBlank(sharelog.getService_targets_name()) ?sharelog.getService_targets_name() :"" ;
//		String collect_type = StringUtils.isNotBlank(sharelog.getCollect_type()) ? sharelog.getCollect_type() :"";
//		String access_ip = StringUtils.isNotBlank(sharelog.getAccess_ip()) ? sharelog.getAccess_ip() :"" ;
//		String task_start_time = StringUtils.isNotBlank(sharelog.getTask_start_time()) ? sharelog.getTask_start_time() : "";
//		String task_end_time = StringUtils.isNotBlank(sharelog.getTask_end_time()) ? sharelog.getTask_end_time() :"" ;
//		String task_consume_time = StringUtils.isNotBlank(sharelog.getTask_consume_time()) ? sharelog.getTask_consume_time() :"" ;
//		String collect_data_amount =StringUtils.isNotBlank(sharelog.getCollect_data_amount()) ? sharelog.getCollect_data_amount() : "";
//		String task_status = StringUtils.isNotBlank(sharelog.getTask_status()) ? sharelog.getTask_status() :"" ;
//		String patameter = StringUtils.isNotBlank(sharelog.getPatameter()) ? sharelog.getPatameter() : "";
//		String error_codes = StringUtils.isNotBlank(sharelog.getError_codes()) ? sharelog.getError_codes() : "";
//		
//		String sql = "insert into collect_joumal(collect_joumal_id,"
//				+ "collect_task_id,task_name,service_targets_id,"
//				+ "service_targets_name,collect_type,access_ip,task_start_time,"
//				+ "task_end_time,task_consume_time,collect_data_amount,task_status,"
//				+ "patameter,error_codes) values('"
//				+ collect_joumal_id
//				+ "','"
//				+ collect_task_id
//				+ "','"
//				+ task_name
//				+ "','"
//				+ service_targets_id
//				+ "','"
//				+ service_targets_name
//				+ "','"
//				+ collect_type
//				+ "','"
//				+ access_ip
//				+ "','"
//				+ task_start_time
//				+ "','"
//				+task_end_time
//				+"','"
//				+task_consume_time
//				+"','"
//				+collect_data_amount
//				+"','"
//				+task_status
//				+"','"
//				+ patameter
//				+ "','"
//				
//				+ error_codes
//				+ "')";
//		DBOperation operation = DBOperationFactory.createTimeOutOperation();
//		try {
//			operation.execute(sql, true);
//			System.out.println("保存成功");
//		} catch (DBException e) {
//			e.printStackTrace();
//		}
//	}
	
}
