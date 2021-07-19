package com.gwssi.Service;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gwssi.util.PropertiesUtil;

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
public class PortalGDZCTodoService {
	private static PropertiesUtil TodoPropertiesUtil = PropertiesUtil.getInstance("GwssiPortalTodo");
	
	
	public static void main(String[] args) throws Exception{
		PortalGDZCTodoService t = new PortalGDZCTodoService();
		//String userId = "LINQY1@SZAIC";
		String userId = "LINAN@SZAIC";//@szaic.gov.cn
		//String name = "������";
		String name = "���";
		String ret = "";
		
		//�˳�ƽ̨��{"count":"0"}
		ret = t.callPermanentAssetsService(userId,name);
		System.out.println("GIAP��"+ret);
		
	}
	
	private static final Logger logger = LoggerFactory.getLogger(PortalGDZCTodoService.class);
	
	/***
	 * �Ǽ���ɴ��졣
	 * ֱ�Ӳ��˲�ѯ�̶��ʲ��Ŀ⡣
	 * @return count ������
	 */
/*	public String callPermanentAssetsService(String userId,String name) throws Exception{
		// 1��������ʱ����
		int totalCnt = 0;
		int singleTodoCnt = 0;
		
		// 2����ѯ��������
		// 2.1 ��1��
		StringBuffer sql = new StringBuffer();
		//sql .append("select count(1) from gcloud_assetman.assetman_apply where current_user_id = ? and current_dispose_name = ? ");
		sql .append("select count(1) from gcloud_assetman.assetman_apply where current_user_id = ? and current_dispose_name = ? ");
		//sql .append("select count(1) from assetman_apply where current_user_id = ? and current_dispose_name = ?");
		
		//singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK,sql.toString(), userId,name);
		//singleTodoCnt = SpringJdbcUtil.queryForInt(AppConstants.DATASOURCE_KEY_DJXK,sql.toString(), userId);
		logger.debug("Single Todo Cnt 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+singleTodoCnt);
		System.out.println("������ userId ===>  " + userId + "  name======> " + name);
		totalCnt += singleTodoCnt;

		// 3�� ��װ����ֵ
		Map<String,String> map = new HashMap<String,String>();
		map.put("count", String.valueOf(totalCnt));
		map.put("appId", "pgmId");
		map.put("pId", "pgman");
		String ret =JSON.toJSONString(map);
		
		return ret;
	}*/
	
	
	public String callPermanentAssetsService(String userId,String name) throws Exception{
		logger.debug("�̶��ʲ� ���� start>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
			
			String paramName = TodoPropertiesUtil.getProperty("OAPARA_NAME_GD"); // ��������
			
			try {
				// 1��׼�� Web Service ���ö���
				String endpointAddress = TodoPropertiesUtil.getProperty("OA_LANG_CHAO_ADD"); // ��ַ
				String targetNamespace = TodoPropertiesUtil.getProperty("OA_TARGET_NAME_SPACE_GD"); // targetNamespace
				String operName = TodoPropertiesUtil.getProperty("OA_OPER_NAME_GD"); // operName
				
				//ֱ������Զ�̵�wsdl�ļ�
				Service service = new Service();
				Call call = (Call) service.createCall();
				call.setTargetEndpointAddress(endpointAddress);
				call.setOperationName(new QName(targetNamespace, operName));
				call.addParameter(paramName, XMLType.XSD_STRING, ParameterMode.IN);//�ӿڵĲ���
				call.setTimeout(60000); //���ó�ʱ���
				logger.debug("call webService start>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+userId);
				
				// 2���������
				/*Integer response =(Integer) call.invoke(new Object[]{userId});
				if(response==null || response<0){
					throw new RuntimeException("�̶��ʲ� ���췵��ֵ�쳣������ֵ��\""+response+"\"��");
				}*/
				
				
				String response =(String) call.invoke(new Object[]{userId});
				System.out.println(response);
				
				if(StringUtils.isBlank(response) || "null".equalsIgnoreCase(response)){
					throw new RuntimeException("�̶��ʲ����췵��ֵ�쳣������ֵ��\""+response+"\"��");
				}

				if(result==null) {
					result = "0";
				}
				// 3����װ����ֵ
				// FIXME ������ظ�ʽ��ô�򵥣���JSONЧ��̫�ͣ���Ϊ�ַ�������
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("count", response);
				resultMap.put("appId","12b1d7435d6e41b385da165991e4c8fb");
				resultMap.put("pId","LE");
				result = JSON.toJSONString(resultMap);
				
				logger.debug("result is>>>>>>>>>>>>>>>>>>>>>>>>> "+result);
				logger.info("=================================�̶��ʲ� �������ҵ�񷵻�����===================================" + result);
				logger.info("=================================�̶��ʲ� �������ҵ�����===================================");
				return result;
			} catch (Exception e) {
				logger.debug("�̶��ʲ� ������� ҵ��webserice �����쳣�ˣ��������쳣���ݡ�\n");
				logger.debug(e.getMessage());
				System.out.println(e.getMessage());
				return result;
			}
		
	}
	
	
	
}
