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
	 *            ����linqy@szaic
	 * @return
	 * @throws Exception
	 */
	public String getWaitNoByUserid(String userId)  {

		Map<String, List<HashMap<String, Object>>> map = new HashMap<String, List<HashMap<String, Object>>>();

		// ����
		try {
			t.callCaiWuService(userId, map);
		} catch (Exception e) {
			logger.error("��ȡ����������"+e.getMessage());
			e.printStackTrace();
		}

		// OA�����أ�[{"typeName":"���ַ�","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000556","count":0},{"typeName":"���ϲ�","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000559","count":0},{"typeName":"���˸�","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000561","count":0},{"typeName":"������","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000569","count":11},{"typeName":"�쵼������","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000567","count":5},{"typeName":"�������","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000566","count":0},{"typeName":"�Ѻϲ�","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000565","count":0},{"typeName":"������","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000560","count":0},{"typeName":"���Ű�","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000562","count":0},{"typeName":"��У��","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000563","count":0},{"typeName":"��У��","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000564","count":0},{"typeName":"�����","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000588","count":0},{"typeName":"������","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000589","count":0},{"typeName":"��ǩ��","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC","count":0},{"typeName":"���Ķ�","url":"http://wsjgj/oa/jsp/edoc_daiyue_list.jsp?userId=LINQY1@SZAIC","count":17}]

		// OA���졷��������������������������
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
			logger.error("��ȡOA�������"+e1.getMessage());
		} catch (ServiceException e1) {
			logger.error("��ȡOA�������"+e1.getMessage());
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		// ��ȡ���Ŵ���
		try {
			t.callSmsService(userId, map);
		} catch (RemoteException e) {
			logger.error("��ȡ���Ŵ���������"+e.getMessage());
			e.printStackTrace();
		} catch (ServiceException e) {
			logger.error("��ȡ���Ŵ���������"+e.getMessage());
			e.printStackTrace();
		}

		// ��ȡ ����ϵͳ ִ���참���졣�������ϵͳ��
		try {
			t.callCaseService(userId, map);
		} catch (RemoteException e) {
			logger.error("��ȡ����ϵͳ����"+e.getMessage());
			e.printStackTrace();
		} catch (ServiceException e) {
			logger.error("��ȡ����ϵͳ����"+e.getMessage());
			e.printStackTrace();
		}

		try {
			t.callGiapService(userId, map);
		} catch (Exception e) {
			logger.error("��ȡ�˳��������"+e.getMessage());
			e.printStackTrace();
		}

		try {
			t.callWcmService(userId, map);
		} catch (Exception e) {
			logger.error("��ȡWCM����"+e.getMessage());
			e.printStackTrace();
		}

		// �г���ܣ����أ�{"count":"0","szjxjgTaskNum":"0","xbwqNum":"0"}
		try {
			t.callMsService(userId, map);
		} catch (RemoteException e) {
			logger.error("��ȡ�г���ܳ���"+e.getMessage());

			e.printStackTrace();
		} catch (ServiceException e) {
			logger.error("��ȡ�г���ܳ���"+e.getMessage());
			e.printStackTrace();
		}
		
		return JSON.toJSONString(map);
		//System.out.println(JSON.toJSONString(map));

	}

	/**
	 * �������
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
			// 1������UserId��ʽ
			String userIdWithoutDomainName = StringUtils.replace(StringUtils.upperCase(userId), "@SZAIC", "");
			// 2�����ò���ϵͳ�洢����
			List<Map<String, Object>> cwTodoList = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_CW,
					"{call toAuditFromAllDatabase(?)}", userIdWithoutDomainName);
			// 3����װ����ֵ
			System.out.println(JSON.toJSONString(cwTodoList));
			if (cwTodoList != null && cwTodoList.size() > 0) {
				cwmap = cwTodoList.get(0);
				cwmap.put("count", cwmap.get("myAudit"));
				cwmap.remove("myAudit");
				cwmap.put("state", "1");// ����״̬������óɹ�

			}

			// retJsonString = JSON.toJSONString(cwTodoList);
		} catch (Throwable e) {
			e.printStackTrace();
			cwmap = new HashMap<String, Object>();
			cwmap.put("state", "-1");// ����״̬������óɹ�

			// retJsonString = ErrorUtil.getErrorResponse(e.getMessage());

		}

		cwmap.put("id", "caiwu");
		addlist("AM", cwmap, map);

	}

	/**
	 * 
	 * @param pid
	 *            ��NO
	 * @param todomap
	 *            ����map
	 * @param map
	 *            ���켯��
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
	 * ��ȡOA����
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public String callOaService(String userId) throws ServiceException, RemoteException {

		// 1��׼�� Web Service ���ö���
		String result = null;
		String endpointAddress = TodoPropertiesUtil.getProperty("OA_UPCOMING_ADDR"); // ��ַ
		String targetNamespace = TodoPropertiesUtil.getProperty("OA_TARGET_NAME_SPACE"); // targetNamespace
		String operName = TodoPropertiesUtil.getProperty("OA_OPER_NAME"); // operName
		String paramName = TodoPropertiesUtil.getProperty("OA_PARAM_NAME"); // ��������

		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(endpointAddress);
		call.setOperationName(new QName(targetNamespace, operName));
		call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);// �ӿڵĲ���
		call.setReturnType(XMLType.XSD_STRING);// ���÷�������
		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userId);

		// 2���������
		result = (String) call.invoke(new Object[] { userId });
		if (StringUtils.isBlank(result) || "null".equalsIgnoreCase(result)) {
			throw new RuntimeException("OA���췵��ֵ�쳣������ֵ��\"" + result + "\"��");
		}
		logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> " + result);

		// 3�����ؽ��
		return result;
	}

	/**
	 * ���Ŵ��졣 ���ó�Т���ṩ��Web Service��ͨ��OSB��ת��
	 * 
	 * FIXME ���ǻ��� Call ����
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
		// 1������UserId
		String[] userArray = userId.split("@");
		String userIdWithoutDomainName = "";
		if (userArray != null && userArray.length > 0) {
			userIdWithoutDomainName = userArray[0];
		} else {
			throw new RuntimeException("UserId��ʽ����ȷ��UserId��" + userId);
		}
		if (StringUtils.isBlank(userIdWithoutDomainName)) {
			throw new RuntimeException("UserId��ʽ����ȷ��UserId��" + userId);
		}

		// 2��׼�� Web Service ���ö���
		String endpointAddress = TodoPropertiesUtil.getProperty("DX_ADDR"); // ��ַ
		String targetNamespace = TodoPropertiesUtil.getProperty("DX_NAME_SPACE"); // targetNamespace
		String operName = TodoPropertiesUtil.getProperty("DX_OPER_NAME"); // operName
		String paramName = TodoPropertiesUtil.getProperty("DX_PARAM_NAME"); // ��������

		// ֱ������Զ�̵�wsdl�ļ�
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(endpointAddress);
		call.setSOAPActionURI("http://tempuri.org/getSmsCount");
		call.setOperationName(new QName(targetNamespace, operName));
		call.addParameter(new QName(targetNamespace, paramName), XMLType.XSD_STRING, ParameterMode.IN);// �ӿڵĲ���

		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userIdWithoutDomainName);

		// 3���������
		result = (String) call.invoke(new Object[] { userIdWithoutDomainName });
		if (StringUtils.isBlank(result)) {
			throw new RuntimeException("���Ŵ��췵��ֵ�쳣������ֵΪ�ա�");
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
					throw new RuntimeException("���Ŵ��췵��ֵ����" + count);
				}
			} catch (Throwable e) {
				throw new RuntimeException("���Ŵ��췵��ֵ����" + count);
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
		// 4�����ؽ��
		jo.put("count", "0");
		addlist("AM", map, allmap);

	}

	/**
	 * ִ���참���졣�������ϵͳ��
	 * 
	 * ͨ�� Web Service��
	 * 
	 * FIXME ���ǻ��� Call ����
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public void callCaseService(String userId, Map<String, List<HashMap<String, Object>>> allmap)
			throws ServiceException, RemoteException {
		logger.debug("getZFBAWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		// 1��׼�� Web Service ���ö���
		String endpointAddress = TodoPropertiesUtil.getProperty("ZFBA_ADDR"); // ��ַ
		String targetNamespace = TodoPropertiesUtil.getProperty("ZFBA_NAME_SPACE"); // targetNamespace
		String operName = TodoPropertiesUtil.getProperty("ZFBA_OPER_NAME"); // operName
		String paramName = TodoPropertiesUtil.getProperty("ZFBA_PARAM_NAME"); // ��������

		// ֱ������Զ�̵�wsdl�ļ�
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(endpointAddress);
		call.setOperationName(new QName(targetNamespace, operName));
		call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);// �ӿڵĲ���
		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userId);

		// 2���������
		Integer response = (Integer) call.invoke(new Object[] { userId });
		if (response == null || response < 0) {
			return;
			// throw new RuntimeException("�������췵��ֵ�쳣������ֵ��\""+response+"\"��");
		}

		// 3����װ����ֵ
		// FIXME ������ظ�ʽ��ô�򵥣���JSONЧ��̫�ͣ���Ϊ�ַ�������
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("count", response);
		resultMap.put("id", "12b1d7435d6e41b385da165991e4c8fb");
		addlist("LE", resultMap, allmap);
	}

	/***
	 * �Ǽ���ɴ��졣
	 * 
	 * ֱ�Ӳ��˳��Ŀ⡣
	 * 
	 * ����3�����죬�ۼ���͡�
	 * 
	 * @param userId
	 * @return count ������
	 */
	public void callGiapService(String userId, Map<String, List<HashMap<String, Object>>> allmap) throws Exception {
		// 1��������ʱ����
		int totalCnt = 0;
		int singleTodoCnt = 0;

		// 2����ѯ��������
		// 2.1 ��1��
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

		// 2.2 ��2��
		sql = new StringBuffer();
		sql.append(
				" SELECT count(1) FROM GCLOUD_NAME.NAME_ENTERPRISE_INFO EI JOIN GCLOUD_NAME.NAME_TASK_ALLOCATION TA ON EI.ID=TA.INFO_ID WHERE 1=1 AND ( EI.APPROVAL_STATE = \'10\' OR EI.APPROVAL_STATE = \'12\' OR EI.APPROVAL_STATE = \'13\' ) AND TA.ORGAN_ID = ? ORDER BY EI.APPLY_DATE DESC");
		singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK, sql.toString(), userId);
		logger.debug("Single Todo Cnt 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + singleTodoCnt);
		totalCnt += singleTodoCnt;

		// 2.3 ��3��
		sql = new StringBuffer();
		sql.append(
				"SELECT count(1) FROM LS_BPM.WF_DAI_BAN_TASK PROCTABLE WHERE PROCTABLE.IS_VISIBLE = \'1\'   AND PROCTABLE.PROCESS_TYPE = \'HR\' AND ORGAN_ID =? ORDER BY PROCTABLE.CREATE_TIME");
		singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK, sql.toString(), userId);
		logger.debug("Single Todo Cnt 3 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + singleTodoCnt);
		totalCnt += singleTodoCnt;

		logger.debug("Total Todo Cnt >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + totalCnt);

		// 3�� ��װ����ֵ
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", String.valueOf(totalCnt));
		map.put("appId", "8b80a6d286b14a5f8a2f344d64445gh9");
		// map.put("pId", "RP");

		addlist("RP", map, allmap);
	}

	/**
	 * ����Ͷ����죨��壩��
	 * 
	 * @autor chaihw
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public void callWcmService(String userId, Map<String, List<HashMap<String, Object>>> allmap) throws Exception {
		// 0���������
		int count = 0;

		// 1������UserId�����磺LINQY1_ZAIC����ѯWCMϵͳ�� WcmUserId (���ָ�ʽ�����磺9527)
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
		// 3����װ����ֵ
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("appId", "23b1d7435d6e41b385da165991e4c8kE");
		// map.put("pId", "AM");
		map.put("count", String.valueOf(count));
		addlist("AM", map, allmap);

	}

	/**
	 * �г���ܴ��졣
	 * 
	 * ͨ�� Web Service��
	 * 
	 * FIXME ���ǻ��� Call ����
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

		// 1��׼�� Web Service ���ö���
		String endpointAddress = TodoPropertiesUtil.getProperty("SCJG_ADDR"); // ��ַ
		String targetNamespace = TodoPropertiesUtil.getProperty("SCJG_NAME_SPACE"); // targetNamespace
		String operName = TodoPropertiesUtil.getProperty("SCJG_OPER_NAME"); // operName
		String paramName = TodoPropertiesUtil.getProperty("SCJG_PARAM_NAME"); // ��������

		// ֱ������Զ�̵�wsdl�ļ�
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(endpointAddress);
		call.setOperationName(new QName(targetNamespace, operName));
		call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);// �ӿڵĲ���
		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + userId);

		// 2���������
		Map<String, Object> resultMap = (Map<String, Object>) call.invoke(new Object[] { userId });
		if (resultMap == null || resultMap.isEmpty()) {
			throw new RuntimeException("�г���ܴ��췵��ֵ�쳣������ֵΪ�ա�");
		}

		// 3����װ����ֵ
		resultMap.put("id", "8b80a6d286b14a5f8a2f344d64446cf7");
		// resultMap.put("pId","MS");
		addlist("MS", resultMap, allmap);

	}

	public static void main(String[] args) throws Exception {
		WcmTodo wcmto = new WcmTodo();
		wcmto.getWaitNoByUserid("LINQY1@SZAIC");

	}

}
