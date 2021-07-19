package com.gwssi.cxf.feedback.impl;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.context.ContextLoaderListener;

import com.gwssi.cxf.feedback.common.SysResult;
import com.gwssi.cxf.feedback.common.Utils;
import com.gwssi.cxf.feedback.inter.FeedBackInfoServiceInterface;
import com.gwssi.cxf.feedback.service.FeedBackInfoService;

/**
 * 房屋编码反馈信息接口
 * @author lokn
 * 2017/12/01
 */
@WebService(serviceName="FeedBackInfoService")
public class FeedBackInfoServiceInterfaceImpl implements
		FeedBackInfoServiceInterface {

	private FeedBackInfoService feedBackInfoService = ContextLoaderListener.
													getCurrentWebApplicationContext().
														getBean(FeedBackInfoService.class);
	
	private static final ObjectMapper Mapper = new ObjectMapper();
	
	/**
	 * 根据时间戳查询信息
	 */
	@Override
	@WebResult(name="String")
	public String getFeedbackInfo(@WebParam(name="updateStartTime", targetNamespace="http://impl.feedback.cxf.gwssi.com/") String updateStartTime, 
								@WebParam(name="updateEndTime", targetNamespace="http://impl.feedback.cxf.gwssi.com/") String updateEndTime) {
		/**
		 * 返回参数说明：
		 * 	code: 0-获取数据成功；1-暂无数据；2-传入参数都为空；9-其他；
		 *  msg:消息说明
		 *  data:返回数据
		 */
		updateStartTime = Utils.checkTimeFormat(updateStartTime);
		updateEndTime = Utils.checkTimeFormat(updateEndTime);
		if (StringUtils.isNotEmpty(updateStartTime) || StringUtils.isNotEmpty(updateEndTime)) {
			List<Map> listInfo;
			try {
				listInfo = feedBackInfoService.getHouseInfo(updateStartTime, updateEndTime);
				if (listInfo != null && listInfo.size() > 0) {
					//转换日期类型格式
					List<Map> typechageList = Utils.typechage(listInfo);
					String jsonStr = Mapper.writeValueAsString(typechageList);
					for (Map map : listInfo) {
						String id = (String) map.get("id");
						feedBackInfoService.updateFeedFlag(id);
					}
					return SysResult.codeStatus_0(jsonStr);
				}
			} catch (Exception e) {
				e.printStackTrace();
				//其他
				return SysResult.codeStatus_9();
			}
		}
		//没有查询到对应数据或传入参数不正确
		return SysResult.codeStatus_1();
	}

	/**
	 * 根据id精确查询信息
	 */
	@Override
	@WebResult(name="String")
	public String getFeedbackInfoById(@WebParam(name="id", targetNamespace="http://impl.feedback.cxf.gwssi.com/") String id) {
		if (!StringUtils.isEmpty(id)) {
			List<Map> list;
			try {
				list = feedBackInfoService.getHouseInfoById(id);
				if (list != null && list.size() > 0) {
					//转换日期类型格式
					List<Map> typechageList = Utils.typechage(list);
					String jsonStr = Mapper.writeValueAsString(typechageList);
					return SysResult.codeStatus_0(jsonStr);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
				//其他情况返回数据
				return SysResult.codeStatus_9();
			}
		}
		//没有查询到对应数据或传入参数不正确
		return SysResult.codeStatus_1();
	}

}
