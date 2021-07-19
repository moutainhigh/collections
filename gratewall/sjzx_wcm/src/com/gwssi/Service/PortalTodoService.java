package com.gwssi.Service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.gwssi.util.ErrorUtil;
import com.gwssi.util.PropertiesUtil;
import com.gwssi.util.SpringJdbcUtil;

/**
 * <h3>��ѯ����</h3>
 * <ol>
 * 	<li>������졣</li>
 * 	<li>���Ŵ��졣</li>
 * 	<li>OA���졣</li>
 * 	<li>������ִ���참�����졣</li>
 * 	<li>�г���ܣ�������12315�����졣</li>
 * 	<li>�˳�ƽ̨����ʳƷ�����¡��г���ܵȣ����졣</li>
 * </ol>
 * 
 * TODO ͳһ����ֵ��ʽ
 */
public class PortalTodoService {
	
	public static void main(String[] args) throws Exception{
		PortalTodoService t = new PortalTodoService();
		String userId = "LINQY1@SZAIC";
		String ret = "";
		// ���������Ͽ�
/*		ret = t.callCaiWuService(userId);
		System.out.println("����"+ret);*/
		

		//OA�����أ�[{"typeName":"���ַ�","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000556","count":0},{"typeName":"���ϲ�","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000559","count":0},{"typeName":"���˸�","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000561","count":0},{"typeName":"������","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000569","count":11},{"typeName":"�쵼������","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000567","count":5},{"typeName":"�������","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000566","count":0},{"typeName":"�Ѻϲ�","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000565","count":0},{"typeName":"������","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000560","count":0},{"typeName":"���Ű�","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000562","count":0},{"typeName":"��У��","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000563","count":0},{"typeName":"��У��","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000564","count":0},{"typeName":"�����","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000588","count":0},{"typeName":"������","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC&menuId=000000000000000000000000000589","count":0},{"typeName":"��ǩ��","url":"http://wsjgj/oa/jsp/edoc_daiban_list.jsp?userId=LINQY1@SZAIC","count":0},{"typeName":"���Ķ�","url":"http://wsjgj/oa/jsp/edoc_daiyue_list.jsp?userId=LINQY1@SZAIC","count":17}]
/*		ret = t.callOaService(userId);
		System.out.println("OA��"+ret);
		
		// ���ţ����أ�{"code":"0","message":"3"}
		ret = t.callSmsService(userId);
		System.out.println("���ţ�"+ret);*/
		
		// ���������أ�{"count":0}
		ret = t.callCaseService(userId);
		System.out.println("������"+ret);
		
		//�˳�ƽ̨��{"count":"0"}
		ret = t.callGiapService(userId);
		System.out.println("GIAP��"+ret);
		
		//WCM�����أ�{"count":"0"}
/*		ret = t.callWcmService(userId);
		System.out.println("WCM��"+ret);*/
		
		// �г���ܣ����أ�{"count":"0","szjxjgTaskNum":"0","xbwqNum":"0"}
/*		ret = t.callMsService(userId);
		System.out.println("�г���ܣ�"+ret);*/
	}
	
	private static final Logger logger = LoggerFactory.getLogger(PortalTodoService.class);
	
	private static PropertiesUtil TodoPropertiesUtil = PropertiesUtil.getInstance("GwssiPortalTodo");

	/**
	 * ������졣
	 * ֱ�ӵ��ò���ϵͳ�洢���̡�
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String callCaiWuService(String userId)throws Exception {
		String retJsonString = "";
		try{
			// 1������UserId��ʽ
			String userIdWithoutDomainName =  StringUtils.replace(StringUtils.upperCase(userId), "@SZAIC", "");
			// 2�����ò���ϵͳ�洢����
			List<Map<String, Object>> cwTodoList = SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_CW, 
					"{call toAuditFromAllDatabase(?)}", userIdWithoutDomainName);
			// 3����װ����ֵ
			retJsonString = JSON.toJSONString(cwTodoList);
		}catch(Throwable e){
			e.printStackTrace();
			retJsonString = ErrorUtil.getErrorResponse(e.getMessage());
		}
		return retJsonString;
	}
	/**
	 * ���Ŵ��졣
	 * ���ó�Т���ṩ��Web Service��ͨ��OSB��ת��
	 * 
	 * FIXME ���ǻ��� Call ����
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	public String callSmsService(String userId) throws ServiceException, RemoteException {
		logger.debug("getMessageWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		String result = null;
		// 1������UserId
		String[] userArray = userId.split("@");
		String userIdWithoutDomainName = "";
		if(userArray!=null && userArray.length>0){
			userIdWithoutDomainName = userArray[0];
		}else{
			throw new RuntimeException("UserId��ʽ����ȷ��UserId��"+userId);
		}
		if(StringUtils.isBlank(userIdWithoutDomainName)){
			throw new RuntimeException("UserId��ʽ����ȷ��UserId��"+userId);
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
		call.addParameter(new QName(targetNamespace, paramName),XMLType.XSD_STRING, ParameterMode.IN);// �ӿڵĲ���
		
		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ userIdWithoutDomainName);
		
		// 3���������
		result = (String) call.invoke(new Object[] { userIdWithoutDomainName });
		if(StringUtils.isBlank(result)){
			throw new RuntimeException("���Ŵ��췵��ֵ�쳣������ֵΪ�ա�");
		}
		JSONObject jo = JSON.parseObject(result);
		String code = jo.getString("code");
		if("0".equals(code)){
			jo.remove("code");
			String count = jo.getString("message");
			try{
				int cnt = Integer.parseInt(count);
				if(cnt<0){
					throw new RuntimeException("���Ŵ��췵��ֵ����"+count);
				}
			}catch(Throwable e){
				throw new RuntimeException("���Ŵ��췵��ֵ����"+count);
			}
			jo.remove("message");
			jo.put("count", count);
			jo.put("appId", "8b80a6d286b14a5f8a2f344d66666po2");
			jo.put("pId", "AM");
			result = jo.toJSONString();

			logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> " + result);
			return result ;
		}
		if("1".equals(code)){
			// ��ǰ�û���Ȩ�޴�������
			jo.put("count", "0");
			jo.remove("code");
			result = jo.toJSONString();
			
			logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> " + result);
			return result ;
		}
		if("2".equals(code)){
			String message = jo.getString("message");
			throw new RuntimeException("��ȡ���Ŵ��������"+message);
		}
		// 4�����ؽ��
		jo.put("count", "0");
		return jo.toJSONString();
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
	public String callCaseService(String userId) throws ServiceException, RemoteException {
		logger.debug("getZFBAWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		// 1��׼�� Web Service ���ö���
		String endpointAddress = TodoPropertiesUtil.getProperty("ZFBA_ADDR"); // ��ַ
		String targetNamespace = TodoPropertiesUtil.getProperty("ZFBA_NAME_SPACE"); // targetNamespace
		String operName = TodoPropertiesUtil.getProperty("ZFBA_OPER_NAME"); // operName
		String paramName = TodoPropertiesUtil.getProperty("ZFBA_PARAM_NAME"); // ��������
		
		//ֱ������Զ�̵�wsdl�ļ�
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(endpointAddress);
		call.setOperationName(new QName(targetNamespace, operName));
		call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//�ӿڵĲ���
		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
		
		// 2���������
		Integer response =(Integer) call.invoke(new Object[]{userId});
		if(response==null || response<0){
			throw new RuntimeException("�������췵��ֵ�쳣������ֵ��\""+response+"\"��");
		}
		
		// 3����װ����ֵ
		// FIXME ������ظ�ʽ��ô�򵥣���JSONЧ��̫�ͣ���Ϊ�ַ���������
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("count", response);
		resultMap.put("appId","12b1d7435d6e41b385da165991e4c8fb");
		resultMap.put("pId","LE");
		String result = JSON.toJSONString(resultMap);
		
		logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+result);
		
		return result;
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
	public String callMsService(String userId) throws ServiceException, RemoteException {
		logger.debug("getSCJGWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		// 1��׼�� Web Service ���ö���
		String endpointAddress = TodoPropertiesUtil.getProperty("SCJG_ADDR"); // ��ַ
		String targetNamespace = TodoPropertiesUtil.getProperty("SCJG_NAME_SPACE"); // targetNamespace
		String operName = TodoPropertiesUtil.getProperty("SCJG_OPER_NAME"); // operName
		String paramName = TodoPropertiesUtil.getProperty("SCJG_PARAM_NAME"); // ��������
		
		//ֱ������Զ�̵�wsdl�ļ�
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(endpointAddress);
		call.setOperationName(new QName(targetNamespace, operName));
		call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//�ӿڵĲ���
		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
		
		// 2���������
		Map<String,Object> resultMap = (Map<String, Object>) call.invoke(new Object[]{userId});
		if(resultMap==null || resultMap.isEmpty()){
			throw new RuntimeException("�г���ܴ��췵��ֵ�쳣������ֵΪ�ա�");
		}
		
		// 3����װ����ֵ
		resultMap.put("appId","8b80a6d286b14a5f8a2f344d64446cf7");
		resultMap.put("pId","MS");
		String result =JSON.toJSONString(resultMap);
		logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+resultMap);
			
		return result;
	}
	/**
	 * OA���졣
	 * 
	 * @param userId
	 * @return
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	public String callOaService(String userId) throws ServiceException, RemoteException {
		logger.debug("getOaWaitByWebService start>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
		call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//�ӿڵĲ���
		call.setReturnType(XMLType.XSD_STRING);//���÷������� 
		logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
		
		// 2���������
		result = (String)call.invoke(new Object[]{userId});
		if(StringUtils.isBlank(result) || "null".equalsIgnoreCase(result)){
			throw new RuntimeException("OA���췵��ֵ�쳣������ֵ��\""+result+"\"��");
		}
		logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+result);
		
		// 3�����ؽ��
		return result;
	}
	
	
	/***
	 * �Ǽ����ɴ��졣
	 * 
	 * ֱ�Ӳ��˳��Ŀ⡣
	 * 
	 * ����3�����죬�ۼ���͡�
	 * 
	 * @param userId
	 * @return count ������
	 */
	public String callGiapService(String userId) throws Exception{
		// 1��������ʱ����
		int totalCnt = 0;
		int singleTodoCnt = 0;
		
		// 2����ѯ��������
		// 2.1 ��1��
		StringBuffer sql = new StringBuffer();
		sql .append("SELECT ")
			.append("	count(1) ")
			.append("	FROM GCLOUD_GIAP_aicmer.GIAP_APPLY_BASE BASE")
			.append("	LEFT JOIN  GCLOUD_GIAP_aicmer.GIAP_APPLY_WORKFLOW WF    ")
			.append("	ON BASE.SERIAL_NO = WF.SERIAL_NO ")
			.append("	WHERE EXISTS ")
			.append("	(SELECT 1  FROM  GCLOUD_GIAP_aicmer.GIAP_APPLY_DAI_BAN DB WHERE BASE.SERIAL_NO = DB.SERIAL_NO AND DB.ORGAN_ID = ?)")
			.append("	AND BASE.IS_FINISH = \'0\'")
			.append("	AND WF.IS_FINISH = \'0\'")
			.append("	AND BASE.TASK_STATE = \'TODO\'")
			.append("	ORDER BY WF.TASK_CREATE_TIME DESC");
		singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK,sql.toString(), userId);
		logger.debug("Single Todo Cnt 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+singleTodoCnt);
		totalCnt += singleTodoCnt;

/*		// 2.2 ��2��
		sql = new StringBuffer();
		sql.append(" SELECT count(1) FROM GCLOUD_NAME.NAME_ENTERPRISE_INFO EI JOIN GCLOUD_NAME.NAME_TASK_ALLOCATION TA ON EI.ID=TA.INFO_ID WHERE 1=1 AND ( EI.APPROVAL_STATE = \'10\' OR EI.APPROVAL_STATE = \'12\' OR EI.APPROVAL_STATE = \'13\' ) AND TA.ORGAN_ID = ? ORDER BY EI.APPLY_DATE DESC");
		singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK,sql.toString(), userId);
		logger.debug("Single Todo Cnt 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+singleTodoCnt);
		totalCnt += singleTodoCnt;

		// 2.3  ��3��
		sql = new StringBuffer();
		sql.append("SELECT count(1) FROM LS_BPM.WF_DAI_BAN_TASK PROCTABLE WHERE PROCTABLE.IS_VISIBLE = \'1\'   AND PROCTABLE.PROCESS_TYPE = \'HR\' AND ORGAN_ID =? ORDER BY PROCTABLE.CREATE_TIME");
		singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK,sql.toString(), userId);
		logger.debug("Single Todo Cnt 3 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+singleTodoCnt);
		totalCnt += singleTodoCnt;
		
		logger.debug("Total Todo Cnt >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+totalCnt);
		*/
		// 3�� ��װ����ֵ
		Map<String,String> map = new HashMap<String,String>();
		map.put("count", String.valueOf(totalCnt));
		map.put("appId", "8b80a6d286b14a5f8a2f344d64445gh9");
		map.put("pId", "RP");
		String ret =JSON.toJSONString(map);
		
		return ret;
	}
	
	
	
	/**
	 * ���ش�������ͨ��oracle���ݿ��ȡ����sql
	 * 
	 *   todoConfig['HR'] = todoPath + "GetWaitNo&pkid=dabf14ddda2e4bf9b2f639f686350b88";
	 * @param userId
	 * @param request
	 * @return
	 */
	public String CallNoByDataBase(String userId, HttpServletRequest request){
		String ret="";
		String sql	="select * from V_TODO_SQL t where t.pk_sys_integration = ?";
		String pkid= request.getParameter("pkid");
		
		if(StringUtils.isEmpty(pkid)){
			return null;
		}
		List<Map<String,Object>> list= SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_YYJC,sql.toString(), pkid);
		if(list!=null&&list.size()>0){
			Map<String,Object> map1 =list.get(0);
			
			String sql2=(String) map1.get("WAIT_SQL");
			String datasourekey =(String) map1.get("DATASOURCEKEY");
			String pid =(String) map1.get("PID");
			int singleTodoCnt = SpringJdbcUtil.queryForInt(datasourekey,sql2.toString(), userId);
			
			Map<String,String> map = new HashMap<String,String>();
			map.put("count", String.valueOf(singleTodoCnt));
			map.put("appId", pkid);
			map.put("pId", pid);
			 ret =JSON.toJSONString(map);
		}
		
		
		


		
		return ret;
	}
	
	
/*	*//**
	 * ����Ͷ����죨��壩��
	 * 
	 * ֱ�Ӳ�⡣
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 *//*
	public String callWcmService(String userId) throws Exception {
		// 0���������
		int count = 0;
		
		// 1������UserId�����磺LINQY1_ZAIC����ѯWCMϵͳ�� WcmUserId (���ָ�ʽ�����磺9527)
		userId =StringUtils.replace(StringUtils.lowerCase(userId), "@", "_");
		
		String sql = "select w.USERID from WCMUSER w where w.USERNAME= ?";
		int wcmUserId =SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_WCM, sql, userId);
		logger.debug("WCM User Id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+wcmUserId);
		
		// 2����ѯ��������
		sql = "select count(1) from  WCMFLOWDOC w , wcmdocument du where w.worked = 0 and du.docid =w.objid and du.docstatus > 0 and w.worktime is null  and w.tousers is not null and( w.tousers =? or  w.tousers like concat(concat(?,\',\'),\'%\') or  w.tousers like concat(concat(\'%\',\',\'),?)  or   w.tousers like concat(concat(\'%\',\',\'),concat(?,concat(\',\',\'%\'))) )";
		count = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_WCM,sql.toString(), userId,userId,userId,userId);
		logger.debug("wcm wait>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+count);
		
		// 3����װ����ֵ
		Map<String,String> map = new HashMap<String,String>();
		map.put("appId", "23b1d7435d6e41b385da165991e4c8kE");
		map.put("pId", "AM");
		map.put("count", String.valueOf(count));
		String result =JSON.toJSONString(map);
		
		return result;
	}*/
	
	/**
	 * ����Ͷ����죨��壩�� 
	 * @autor chaihw 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String callWcmService(String userId) throws Exception {
		// 0���������
		int count = 0;
		
		// 1������UserId�����磺LINQY1_ZAIC����ѯWCMϵͳ�� WcmUserId (���ָ�ʽ�����磺9527)
		userId =StringUtils.replace(StringUtils.lowerCase(userId), "@", "_");
		
		String sql = "select w.USERID from WCMUSER w where w.USERNAME= ?";
		int wcmUserId =SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_WCM, sql, userId);
		logger.debug("WCM User Id>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+wcmUserId);
		
		
		StringBuffer strsql = new StringBuffer();
		strsql.append("select w.tousers from WCMFLOWDOC w, wcmdocument du  where w.worked = 0 and du.docid = w.objid and du.docstatus > 0  and w.worktime is null  and w.tousers is not null");
		strsql.append("   and w.tousers like '%"+wcmUserId+"%'");
		
		List<Map<String,Object>> list= SpringJdbcUtil.query(AppConstants.DATASOURCE_KEY_WCM,strsql.toString());
		if(list!=null &&list.size()>0){
			for(Map<String,Object> map:list){
				String userids = (String) map.get("TOUSERS");
				List<String> userList = new ArrayList<String>();
				Collections.addAll(userList, userids.split(","));
				
				if(userList.contains(String.valueOf(wcmUserId))){
					count=count+1;
				}else{
					
				}
				
				
			}
			
		}
		
		
		
		logger.debug("wcm wait>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+count);
		

		
		// 3����װ����ֵ
		Map<String,String> map = new HashMap<String,String>();
		map.put("appId", "23b1d7435d6e41b385da165991e4c8kE");
		map.put("pId", "AM");
		map.put("count", String.valueOf(count));
		String result =JSON.toJSONString(map);
		
		return result;
	}	
	
	
}