package com.gwssi.rodimus.sms.service.unicom.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.service.BaseService;

/**
 * 短信表操作类
 * @author lixibo
 * @date 2015年5月11日
 */
@Service(value = "SMSService")
public class SMSService  extends BaseService {

	private final static Logger logger = Logger.getLogger(SMSService.class);
	
	/**
	 * 获取内网审查通过状态的记录信息供发短信通知
	 * @return
	 * @throws OptimusException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getContentList()throws OptimusException{
		/*
		 * 发送短信条件：1、内网内容审查通过；2、该环节还没发过短信；
		 * 3、石景山的业务数据；4、经办人电话不能为空
		 * */
		String sql="select r.requisition_id,r.ent_name,r.linkman," +
				" r.mob_tel,r.state from cp_wk_requisition r " +
				" left join sms_record s on s.gid=r.gid " +
				" and s.req_state=r.state" +
				" where r.app_form='C' and r.state='3' " +
				" and r.operation_type='20' and r.mob_tel is not null" +
				" and s.sms_id is null";
		return getPersistenceDAO().queryForList(sql, null);
	}
	
	/**
	 * 将短信记录插入到短信数据表中
	 * @param list
	 * @param reportMap
	 * @throws OptimusException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void insertSMS(List<Map> list,Map<String,Map<String, String>> reportMap)throws OptimusException{
		if(list==null || list.size()==0){
			return ;
		}
		List<SmsRecordBO> boList = new ArrayList<SmsRecordBO>();
		int len = list.size();
		for(int i=0;i<len;i++){
			SmsRecordBO bo = new SmsRecordBO();
			Map<String,String> tmp = list.get(i);
			String smsId = tmp.get("smsId");
			Map<String, String>report = reportMap.get(smsId);
			if(report==null){
				continue;
			}
			bo.setSmsId(smsId);
			bo.setBid(report.get("bid"));
			bo.setContent("");
			bo.setGid(tmp.get("requisitionId"));
			bo.setRecvNum(tmp.get("mobTel"));
			bo.setRepeat("0");
			bo.setReportTime(report.get("ReportTime"));
			bo.setReqState(tmp.get("state"));
			bo.setSendTime(tmp.get("sendTime"));
			bo.setStatus(report.get("Status"));
			boList.add(bo);
		}
		getPersistenceDAO().insert(boList);
		logger.debug("插入"+len+"条短信记录");
	}
}
