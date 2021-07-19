package com.gwssi.WebService.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwssi.AppConstants;
import com.gwssi.Service.PortalTodoService;
import com.gwssi.util.PropertiesUtil;
import com.gwssi.util.SpringJdbcUtil;

public class WcmTodo {
	private static final Logger logger = LoggerFactory.getLogger(WcmTodo.class);

	private static PropertiesUtil TodoPropertiesUtil = PropertiesUtil.getInstance("GwssiPortalTodo");
	private static WcmTodo t = new WcmTodo();

	/**
	 * 
	 * @param userid
	 *            例如linqy@szaic
	 * @return
	 * @throws Exception
	 */
	public String getWaitNoByUserid(String userId)  {

		Map<String, List<HashMap<String, Object>>> map = new HashMap<String, List<HashMap<String, Object>>>();

		// 财务
		try {
			t.callCaiWuService(userId, map);
		} catch (Exception e) {
			logger.error("获取财物代办出错："+e.getMessage());
			e.printStackTrace();
		}

		// OA，返回：[{"typeName":"待分发","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000556","count":0},{"typeName":"待合并","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000559","count":0},{"typeName":"待核稿","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000561","count":0},{"typeName":"待办理","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000569","count":11},{"typeName":"领导新审批","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000567","count":5},{"typeName":"主任拟办","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000566","count":0},{"typeName":"已合并","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000565","count":0},{"typeName":"待盖章","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000560","count":0},{"typeName":"待排版","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000562","count":0},{"typeName":"待校对","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000563","count":0},{"typeName":"已校对","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000564","count":0},{"typeName":"待拟办","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000588","count":0},{"typeName":"待审批","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000589","count":0},{"typeName":"待签收","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC","count":0},{"typeName":"待阅读","url":"http://wsjgj/oa/jsp/edoc_daiyue_list.jsp?userId=LINQY1@SZAIC","count":17}]

		// OA代办》》》》》》》》》》》》》》
		List<Map> oalist = new ArrayList<Map>();
		Map<String, Object> oamap = new HashMap<String, Object>();
		try {
			oalist = JSONObject.parseArray(t.callOaService(userId), Map.class);
			oamap.put("id", "OA");
			oamap.put("data", oalist);
			addlist("AM", oamap, map);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			logger.error("获取OA代办出错："+e1.getMessage());
		} catch (ServiceException e1) {
			logger.error("获取OA代办出错："+e1.getMessage());
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		// 获取短信代办
		try {
			t.callSmsService(userId, map);
		} catch (RemoteException e) {
			logger.error("获取短信代办代办出错："+e.getMessage());
			e.printStackTrace();
		} catch (ServiceException e) {
			logger.error("获取短信代办代办出错："+e.getMessage());
			e.printStackTrace();
		}

		// 获取 案件系统 执法办案待办。（华宇案件系统）
		try {
			t.callCaseService(userId, map);
		} catch (RemoteException e) {
			logger.error("获取案件系统出错："+e.getMessage());
			e.printStackTrace();
		} catch (ServiceException e) {
			logger.error("获取案件系统出错："+e.getMessage());
			e.printStackTrace();
		}

		try {
			t.callGiapService(userId, map);
		} catch (Exception e) {
			logger.error("获取浪潮代办出错："+e.getMessage());
			e.printStackTrace();
		}

		try {
			t.callWcmService(userId, map);
		} catch (Exception e) {
			logger.error("获取WCM出错："+e.getMessage());
			e.printStackTrace();
		}

		// 市场监管，返回：{"count":"0","szjxjgTaskNum":"0","xbwqNum":"0"}
		try {
			t.callMsService(userId, map);
		} catch (RemoteException e) {
			logger.error("获取市场监管出错："+e.getMessage());

			e.printStackTrace();
		} catch (ServiceException e) {
			logger.error("获取市场监管出错："+e.getMessage());
			e.printStackTrace();
		}
		
		return JSON.toJSONString(map);
		//System.out.println(JSON.toJSONString(map));

	}

	/**
	 * 财务代办
	 * 
	 * @param userId
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void callCaiWuService(String userId, Map<String, List<HashMap<String, Object>>> map) throws Exception {
		String retJsonString = "";
		Map<String, Object> cwmap = null;
		try {
			// 1、处理UserId格式
			String userIdWithoutDomainName = StringUtils.replace(StringUtils.upperCase(userId), "@SZAIC", "");
			// 2、调用财务系统存储过程
			List<Map<String, Object>> cwTodoList = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_CW,
					"{call toAuditFromAllDatabase(?)}", userIdWithoutDomainName);
			// 3、封装返回值
			System.out.println(JSON.toJSONString(cwTodoList));
			if (cwTodoList != null && cwTodoList.size() > 0) {
				cwmap = cwTodoList.get(0);
				cwmap.put("count", cwmap.get("myAudit"));
				cwmap.remove("myAudit");
				cwmap.put("state", "1");// 加入状态代表调用成功

			}

			// retJsonString = JSON.toJSONString(cwTodoList);
		} catch (Throwable e) {
			e.printStackTrace();
			cwmap = new HashMap<String, Object>();
			cwmap.put("state", "-1");// 加入状态代表调用成功

			// retJsonString = ErrorUtil.getErrorResponse(e.getMessage());

		}

		cwmap.put("id", "caiwu");
		addlist("AM", cwmap, map);

	}

	/**
	 * 
	 * @param pid
	 *            域NO
	 * @param todomap
	 *            代办map
	 * @param map
	 *            代办集合
	 */
	public void addlist(String pid, Map<String, Object> todomap, Map<String, List<HashMap<String, Object>>> map) {

		List<HashMap<String, Object>> list = map.get(pid);
		if (list == null) {
			list = new ArrayList<HashMap<String, Object>>();
			list.add((HashMap<String, Object>) todomap);
			map.put(pid, list);
		} else {
			list.add((HashMap<String, Object>) todomap);
		}
	}

	/**
	 * 获取OA代办
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public String callOaService(String userId) throws ServiceException, RemoteException {

		// 1、准备 Web Service 调用对象
		String result = null;
		String endpointAddress = TodoPropertiesUtil.getProperty("OA_UPCOMING_ADDR"); // 地址
		String targetNamespace = TodoPropertiesUtil.getProperty("OA_TARGET_NAME_SPACE"); // targetNamespace
		String operName = TodoPropertiesUtil.getProperty("OA_OPER_NAME"); // operName
		String paramName = TodoPropertiesUtil.getProperty("OA_PARAM_NAME"); // 参数名称

		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(endpointAddress);
		call.setOperationName(new QName(targetNamespace, operName));
		call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userId);

		// 2、发起调用
		result = (String) call.invoke(new Object[] { userId });
		if (StringUtils.isBlank(result) || "null".equalsIgnoreCase(result)) {
			throw new RuntimeException("OA待办返回值异常，返回值：\"" + result + "\"。");
		}
		logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> " + result);

		// 3、返回结果
		return result;
	}

	/**
	 * 短信待办。 调用陈孝华提供的Web Service，通过OSB中转。
	 * 
	 * FIXME 考虑缓存 Call 对象。
	 * 
	 * @param userId
	 * @param allmap
	 * @return
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public void callSmsService(String userId, Map<String, List<HashMap<String, Object>>> allmap)
			throws ServiceException, RemoteException {
		Map<String, Object> map = new HashMap<String, Object>();
		logger.debug("getMessageWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String result = null;
		// 1、处理UserId
		String[] userArray = userId.split("@");
		String userIdWithoutDomainName = "";
		if (userArray != null && userArray.length > 0) {
			userIdWithoutDomainName = userArray[0];
		} else {
			throw new RuntimeException("UserId格式不正确，UserId：" + userId);
		}
		if (StringUtils.isBlank(userIdWithoutDomainName)) {
			throw new RuntimeException("UserId格式不正确，UserId：" + userId);
		}

		// 2、准备 Web Service 调用对象
		String endpointAddress = TodoPropertiesUtil.getProperty("DX_ADDR"); // 地址
		String targetNamespace = TodoPropertiesUtil.getProperty("DX_NAME_SPACE"); // targetNamespace
		String operName = TodoPropertiesUtil.getProperty("DX_OPER_NAME"); // operName
		String paramName = TodoPropertiesUtil.getProperty("DX_PARAM_NAME"); // 参数名称

		// 直接引用远程的wsdl文件
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(endpointAddress);
		call.setSOAPActionURI("http://tempuri.org/getSmsCount");
		call.setOperationName(new QName(targetNamespace, operName));
		call.addParameter(new QName(targetNamespace, paramName), XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数

		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userIdWithoutDomainName);

		// 3、发起调用
		result = (String) call.invoke(new Object[] { userIdWithoutDomainName });
		if (StringUtils.isBlank(result)) {
			throw new RuntimeException("短信待办返回值异常，返回值为空。");
		}
		JSONObject jo = JSON.parseObject(result);
		String code = jo.getString("code");
		if ("0".equals(code)) {
			jo.remove("code");
			String count = jo.getString("message");
			try {
				int cnt = Integer.parseInt(count);
				if (cnt < 0) {
					map.put("state", "-1");
					throw new RuntimeException("短信待办返回值错误：" + count);
				}
			} catch (Throwable e) {
				throw new RuntimeException("短信待办返回值错误：" + count);
			}
			map.put("state", "1");
			map.put("count", count);
			map.put("id", "8b80a6d286b14a5f8a2f344d66666po2");

		}
		if ("1".equals(code)) {
			map.put("state", "1");
			map.put("count", 0);
			map.put("id", "8b80a6d286b14a5f8a2f344d66666po2");
		}
		if ("2".equals(code)) {
			map.put("state", "-1");
			map.put("count", 0);
			map.put("id", "8b80a6d286b14a5f8a2f344d66666po2");
			map.put("message", jo.getString("message"));

		}
		// 4、返回结果
		jo.put("count", "0");
		addlist("AM", map, allmap);

	}

	/**
	 * 执法办案待办。（华宇案件系统）
	 * 
	 * 通过 Web Service。
	 * 
	 * FIXME 考虑缓存 Call 对象。
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public void callCaseService(String userId, Map<String, List<HashMap<String, Object>>> allmap)
			throws ServiceException, RemoteException {
		logger.debug("getZFBAWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		// 1、准备 Web Service 调用对象
		String endpointAddress = TodoPropertiesUtil.getProperty("ZFBA_ADDR"); // 地址
		String targetNamespace = TodoPropertiesUtil.getProperty("ZFBA_NAME_SPACE"); // targetNamespace
		String operName = TodoPropertiesUtil.getProperty("ZFBA_OPER_NAME"); // operName
		String paramName = TodoPropertiesUtil.getProperty("ZFBA_PARAM_NAME"); // 参数名称

		// 直接引用远程的wsdl文件
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(endpointAddress);
		call.setOperationName(new QName(targetNamespace, operName));
		call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userId);

		// 2、发起调用
		Integer response = (Integer) call.invoke(new Object[] { userId });
		if (response == null || response < 0) {
			return;
			// throw new RuntimeException("案件待办返回值异常，返回值：\""+response+"\"。");
		}

		// 3、封装返回值
		// FIXME 如果返回格式这么简单，用JSON效率太低，改为字符串处理。
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("count", response);
		resultMap.put("id", "12b1d7435d6e41b385da165991e4c8fb");
		addlist("LE", resultMap, allmap);
	}

	/***
	 * 登记许可待办。
	 * 
	 * 直接查浪潮的库。
	 * 
	 * 共有3个待办，累加求和。
	 * 
	 * @param userId
	 * @return count 待办数
	 */
	public void callGiapService(String userId, Map<String, List<HashMap<String, Object>>> allmap) throws Exception {
		// 1、定义临时变量
		int totalCnt = 0;
		int singleTodoCnt = 0;

		// 2、查询待办数量
		// 2.1 第1个
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append("	count(1) ").append("	FROM GCLOUD_GIAP_aicmer.GIAP_APPLY_BASE BASE")
				.append("	LEFT JOIN  GCLOUD_GIAP_aicmer.GIAP_APPLY_WORKFLOW WF    ")
				.append("	ON BASE.SERIAL_NO = WF.SERIAL_NO ").append("	WHERE EXISTS ")
				.append("	(SELECT 1  FROM  GCLOUD_GIAP_aicmer.GIAP_APPLY_DAI_BAN DB WHERE BASE.SERIAL_NO = DB.SERIAL_NO AND DB.ORGAN_ID = ?)")
				.append("	AND BASE.IS_FINISH = \'0\'").append("	AND WF.IS_FINISH = \'0\'")
				.append("	AND BASE.TASK_STATE = \'TODO\'").append("	ORDER BY WF.TASK_CREATE_TIME DESC");
		singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK, sql.toString(), userId);
		logger.debug("Single Todo Cnt 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + singleTodoCnt);
		totalCnt += singleTodoCnt;

		// 2.2 第2个
		sql = new StringBuffer();
		sql.append(
				" SELECT count(1) FROM GCLOUD_NAME.NAME_ENTERPRISE_INFO EI JOIN GCLOUD_NAME.NAME_TASK_ALLOCATION TA ON EI.ID=TA.INFO_ID WHERE 1=1 AND ( EI.APPROVAL_STATE = \'10\' OR EI.APPROVAL_STATE = \'12\' OR EI.APPROVAL_STATE = \'13\' ) AND TA.ORGAN_ID = ? ORDER BY EI.APPLY_DATE DESC");
		singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK, sql.toString(), userId);
		logger.debug("Single Todo Cnt 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + singleTodoCnt);
		totalCnt += singleTodoCnt;

		// 2.3 第3个
		sql = new StringBuffer();
		sql.append(
				"SELECT count(1) FROM LS_BPM.WF_DAI_BAN_TASK PROCTABLE WHERE PROCTABLE.IS_VISIBLE = \'1\'   AND PROCTABLE.PROCESS_TYPE = \'HR\' AND ORGAN_ID =? ORDER BY PROCTABLE.CREATE_TIME");
		singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK, sql.toString(), userId);
		logger.debug("Single Todo Cnt 3 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + singleTodoCnt);
		totalCnt += singleTodoCnt;

		logger.debug("Total Todo Cnt >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + totalCnt);

		// 3、 封装返回值
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", String.valueOf(totalCnt));
		map.put("appId", "8b80a6d286b14a5f8a2f344d64445gh9");
		// map.put("pId", "RP");

		addlist("RP", map, allmap);
	}

	/**
	 * 新闻投稿待办（审稿）。
	 * 
	 * @autor chaihw
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public void callWcmService(String userId, Map<String, List<HashMap<String, Object>>> allmap) throws Exception {
		// 0、定义变量
		int count = 0;

		// 1、根据UserId（形如：LINQY1_ZAIC）查询WCM系统中 WcmUserId (数字格式，形如：9527)
		userId = StringUtils.replace(StringUtils.lowerCase(userId), "@", "_");

		String sql = "select w.USERID from WCMUSER w where w.USERNAME= ?";
		int wcmUserId = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_WCM, sql, userId);
		logger.debug("WCM User Id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + wcmUserId);

		StringBuffer strsql = new StringBuffer();
		strsql.append(
				"select w.tousers from WCMFLOWDOC w, wcmdocument du  where w.worked = 0 and du.docid = w.objid and du.docstatus > 0  and w.worktime is null  and w.tousers is not null");
		strsql.append("   and w.tousers like '%" + wcmUserId + "%'");

		List<Map<String, Object>> list = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCM, strsql.toString());
		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				String userids = (String) map.get("TOUSERS");
				List<String> userList = new ArrayList<String>();
				Collections.addAll(userList, userids.split(","));

				if (userList.contains(String.valueOf(wcmUserId))) {
					count = count + 1;
				} else {

				}
			}

		}
		logger.debug("wcm wait>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + count);
		// 3、封装返回值
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", "23b1d7435d6e41b385da165991e4c8kE");
		// map.put("pId", "AM");
		map.put("count", String.valueOf(count));
		addlist("AM", map, allmap);

	}

	/**
	 * 市场监管待办。
	 * 
	 * 通过 Web Service。
	 * 
	 * FIXME 考虑缓存 Call 对象。
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	@SuppressWarnings("unchecked")
	public void callMsService(String userId, Map<String, List<HashMap<String, Object>>> allmap)
			throws ServiceException, RemoteException {
		logger.debug("getSCJGWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");

		// 1、准备 Web Service 调用对象
		String endpointAddress = TodoPropertiesUtil.getProperty("SCJG_ADDR"); // 地址
		String targetNamespace = TodoPropertiesUtil.getProperty("SCJG_NAME_SPACE"); // targetNamespace
		String operName = TodoPropertiesUtil.getProperty("SCJG_OPER_NAME"); // operName
		String paramName = TodoPropertiesUtil.getProperty("SCJG_PARAM_NAME"); // 参数名称

		// 直接引用远程的wsdl文件
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(endpointAddress);
		call.setOperationName(new QName(targetNamespace, operName));
		call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userId);

		// 2、发起调用
		Map<String, Object> resultMap = (Map<String, Object>) call.invoke(new Object[] { userId });
		if (resultMap == null || resultMap.isEmpty()) {
			throw new RuntimeException("市场监管待办返回值异常，返回值为空。");
		}

		// 3、封装返回值
		resultMap.put("id", "8b80a6d286b14a5f8a2f344d64446cf7");
		// resultMap.put("pId","MS");
		addlist("MS", resultMap, allmap);

	}

	public static void main(String[] args) throws Exception {
		WcmTodo wcmto = new WcmTodo();
		wcmto.getWaitNoByUserid("LINQY1@SZAIC");

	}

}
