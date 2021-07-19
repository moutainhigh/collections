package com.gwssi.cxf.feedback.inter;

import javax.jws.WebParam;
import javax.jws.WebResult;

public interface FeedBackInfoServiceInterface {
	
	/**
	 * 反馈信息
	 * @param updateStartTime 时间戳开始时间
	 * @param updateEndTime 时间戳结束时间
	 * @return 
	 */
	public String getFeedbackInfo(String updateStartTime, String updateEndTime);
	
	/**
	 * 根据企业业务编码号获取信息
	 * @param id 企业业务编号
	 * @return
	 */
	public String getFeedbackInfoById(String id);

}
