package com.gwssi.application.log.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.persistence.dao.IPersistenceDAO;
import com.gwssi.optimus.core.service.BaseService;
import com.gwssi.optimus.util.StringUtil;

@Service(value = "logService")
public class LogService extends BaseService{
	
	/**
	 * 通过操作人和操作时间，获取操作日志结果集
	 * @return List 操作日志集合
	 * @throws OptimusException 
	 */
	public List queryOperationLog(Map params) throws OptimusException{
	
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取填写的数据
		String createrName = StringUtil.getMapStr(params, "createrName").trim();
		String operationTime = StringUtil.getMapStr(params, "operationTime").trim();
		
		//编写sql语句
		String sql = "select d.CREATER_NAME,d.ORGAN_NAME,c.SYSTEM_NAME,b.FUNCTION_NAME,d.OPERATION_TYPE,"
				+ "d.OPERATION_TIME,d.OPERATION_STATE from SM_FUNCTION b,SM_SYS_INTEGRATION c,"
				+ "SM_OPERATION_LOG d where b.FUNCTION_CODE=d.FUNCTION_CODE "
				+ "and c.SYSTEM_CODE=d.SYSTEM_CODE ";
		
		//判断操作人是否为空
		if(StringUtils.isNotEmpty(createrName)){
			sql+="and d.CREATER_NAME like ?";
			listParam.add("%"+createrName+"%");
		}	
				
		//判断操作时间是否为空
		if(StringUtils.isNotEmpty(operationTime)){
			sql+=" and to_char(d.OPERATION_TIME,'yyyy-mm-dd' )=?";
			listParam.add(operationTime);
				
		}
		//封装结果集
		return dao.pageQueryForList(sql.toString(), listParam);
	}

	/**
	 * 通过系统集成表主键，获取所有的系统名称
	 * @return 系统名称集合
	 * @throws OptimusException 
	 */
	public List querySystemList() throws OptimusException {
		
		IPersistenceDAO dao = getPersistenceDAO();
		
		//编写sql语句
        String sql = "select PK_SYS_INTEGRATION as value, SYSTEM_NAME as text from SM_SYS_INTEGRATION";
       
        //封装结果集
        List systemList = dao.queryForList(sql, null);
        
        return systemList;
	}
	
	/**
	 * 通过参与者和工作项名称，获取业务流程日志结果集
	 * @return 业务流程日志集合
	 * @throws OptimusException 
	 */
	public List queryFlowLog(Map params) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取参与者和工作项名称
		String participantName = StringUtil.getMapStr(params, "participantName").trim();
		String businessName = StringUtil.getMapStr(params, "businessName").trim();
		
		//编写sql
		String sql = "select BUSINESS_NAME,PARTICIPANT_NAME,PROCESS_NAME,ACTIVITY_NAME,START_TIME,"
				+ "END_TIME,TIME_LIMIT,ACTIVITY_STATE FROM SM_FLOW_LOG where 1=1";
		
		//判断参与者是否为空
		if(StringUtils.isNotEmpty(participantName)){
			sql+=" and PARTICIPANT_NAME like ?";
			listParam.add("%"+participantName+"%");
		}
			
		//判断工作项名称是否为空
		if(StringUtils.isNotEmpty(businessName)){
			sql+=" and BUSINESS_NAME like ?";
			listParam.add("%"+businessName+"%");
		}
		
		
		//封装结果集
		return dao.pageQueryForList(sql.toString(), listParam);
	}
	
	/**
	 * 通过请求系统和目标系统，获取接口日志结果集
	 * @return 接口日志集合
	 * @throws OptimusException 
	 */
	public List queryInterfaceLog(Map params) throws OptimusException{
		
		IPersistenceDAO dao = getPersistenceDAO();
		List listParam = new ArrayList();
		
		//获取请求系统和目标系统的主键
		String reqPkSys = StringUtil.getMapStr(params, "reqPkSys").trim();
		String resPkSys = StringUtil.getMapStr(params, "resPkSys").trim();
		
		//编写sql
		String sql = "select SERVICE_NAME,REQ_SYS_NAME,RES_SYS_NAME,INTERFACE_TYPE,INTERFACE_STATE"
				+ " from SM_INTERFACE_LOG where 1=1";
		
		//判断请求系统主键是否为空
		if(StringUtils.isNotEmpty(reqPkSys)){
			sql+=" and REQ_PK_SYS like ?";
			listParam.add("%"+reqPkSys+"%");
		}	
		
		//判断目标系统主键是否为空？
		if(StringUtils.isNotEmpty(resPkSys)){
			sql+=" and RES_PK_SYS like ?";
			listParam.add("%"+resPkSys+"%");
		}
		
		
		//封装结果集
		return dao.pageQueryForList(sql.toString(), listParam);
	}


	

}
