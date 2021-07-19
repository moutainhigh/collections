package com.gwssi.webservice.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.sharelog.dao.ShareLogVo;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�GSGeneralWebService ���������ṩ�������ķ��� �����ˣ�lizheng ����ʱ�䣺Mar
 * 27, 2013 3:54:30 PM �޸��ˣ�lizheng �޸�ʱ�䣺Mar 27, 2013 3:54:30 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class GSGeneralWebService
{

	ServiceDAO	dao	= null; // �������ݿ�Dao

	public GSGeneralWebService()
	{
		dao = new ServiceDAOImpl();
	}

	protected static Logger	logger	= TxnLogger
											.getLogger(GSGeneralWebService.class
													.getName());	// ��־

	/**
	 * 
	 * HelloWord ����webservice����
	 * 
	 * @param param
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String HelloWord(String param)
	{
		return "Hello:" + param;
	}

	/**
	 * query ���ݲ�������MAP��ʽ�����ݸ��ͻ���
	 * 
	 * @param param
	 * @return Map
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map query(Map param) throws DBException
	{
		// ҵ���߼�
		// 1.��ȡ���÷���Ĳ�������������
		// 2�жϷ������Ƿ����
		// 2a.�������ڷ��ض�Ӧ�Ĵ������
		// 2b.������go on
		// 2.���������еķ����ţ�ȷ����ǰҪ�����ĸ�����
		// 3.У��Ҫ���ʵķ����Ƿ��ܱ����ʣ�����ǲ����û����ü���
		// 3a.�����û����������Ƿ���ȷ��ip�Ƿ���ȷ
		// 3b.�������״̬���÷����ܷ񱻷���
		// 3c.��������ʱ�䣬�Ƿ��ǹ�����
		// 3d.���������ʹ���(ֻ���ʱ��)
		// 3e.��������Ƿ���Ϸ���ı�׼
		// 4.���ݷ����Ż�ȡ������ϸ��Ϣ
		// 5.��������Ҫִ�е�SQL
		// 6.ִ��SQL
		// 7.��ȡִ��SQL�Ľ����������װ��Map��ʽ
		// 8.������򣬷��ʴ�������������
		// 9.��¼��־
		//System.out.println("--------query begin--------param="+param);
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ��ȡ��������ǰ������ʱ����
		Long start = System.currentTimeMillis(); // ��ʼʱ�����ڼ����ʱ
		int batch = (int) Math.round(Math.random() * 1000000); // ÿһ�η���ʱ����һ�����κţ�������ʶͬһ������־
		param.put("batch", batch);
		logger.debug("��ǰʱ��Ϊ��" + startTime + "��ʼ����query��������־���κ�Ϊ��" + batch
				+ "..."); // Batch

		String ip = this.getClientInfo(param);
		logger.debug("���ʵ�ipΪ��" + ip + " ����־���κ�Ϊ��" + batch);

		ShareLogVo shareLogVo = new ShareLogVo();// ������־��¼
		shareLogVo.setAccess_ip(ip); // ��־��¼�ͻ���IP
		shareLogVo.setService_start_time(startTime); // ��־��¼����ʼʱ��

		if (param.isEmpty())
			shareLogVo.setPatameter(""); // ��־��¼�������
		else
			shareLogVo.setPatameter(param.toString()); // ��־��¼�������
		logger.debug("����Ĳ���Ϊ:" + param + " ����־���κ�Ϊ��" + batch);
		
		try {
			if (null != param.get(ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
					&& !"".equals(param.get(
							ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
							.toString())) {// �ж�SVR_CODE�Ƿ�Ϊ��

				String userName = ""; // �û���
				String serviceCode = ""; // ��λ��Ψһһ������ķ�����(Ϊ�������ʵķ�����)

				serviceCode = param.get(
						ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
						.toString(); // ������
				logger.debug("������Ϊ:" + serviceCode + " ����־���κ�Ϊ��" + batch);

				if (null != param.get("LOGIN_NAME")) {
					userName = param.get("LOGIN_NAME").toString();
				}
				Map teMap = queryServiceByCode(serviceCode, userName); // ��ѯΨһ��һ������
				if (!teMap.isEmpty()) {
					ServiceVo serviceVo = new ServiceVo();
					ParamUtil.mapToBean(teMap, serviceVo, false);

					logger.debug("����idΪ��" + serviceVo.getService_id()
							+ " ����־���κ�Ϊ��" + batch);
					logger.debug("��λ��Ψһ�ķ�����Ϊ��" + serviceCode + " ����־���κ�Ϊ��"
							+ batch);

					shareLogVo.setService_no(serviceCode);
					shareLogVo.setRecord_start(String.valueOf(param
							.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS))); // ��ʼ��¼��
					shareLogVo.setRecord_end(String.valueOf(param
							.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS))); // ������¼��
					logger.debug("��ʼ�������" + " ����־���κ�Ϊ��" + batch + "...");

					// У������Ƿ��ܱ�ʹ��
					CheckService checkService = new CheckService();
					Map checkMap = checkService.checkServiceCanBeUsed(
							serviceVo, param, shareLogVo);
					logger.debug("����������" + " ����־���κ�Ϊ��" + batch + "...");
					logger.debug("������Ϊ:" + checkMap + " ����־���κ�Ϊ��" + batch);

					// �������ʧ���򽫷��ش�����ڷ�����־��
					if (checkMap
							.containsKey(ShareConstants.SERVICE_OUT_PARAM_FHDM))
						shareLogVo.setReturn_codes(String.valueOf(checkMap
								.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));

					if ("Y".equals(checkMap.get(
							ShareConstants.SERVICE_CAN_BE_USED).toString())) {
						// ������ʹ��
						param.put("SERVICE_ID", serviceVo.getService_id());
						param = this.queryService(serviceVo.getService_id(),
								param); // ��÷�����ϸ��Ϣ

						Long end = System.currentTimeMillis();
						String consumeTime = String
								.valueOf(((end - start) / 1000f));
						logger.debug("׼������webservices�������������ʱ��" + consumeTime
								+ "�룡" + " ����־���κ�Ϊ��" + batch);

						GeneralService service = ServiceFactory.getService();// ��÷���
						Map result = service.query(param, shareLogVo); // ���÷��񷵻ؽ��
						return result;

					} else {
						// ������ʹ��
						logger.debug("�������ʧ�ܲ��ܷ���,����־���κ�Ϊ��" + batch + "...");
						checkMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
						return checkMap;
					}
				} else {
					logger.debug("δ�ҵ����񣡷����Ų����ڻ��û�������ȷ ,����־���κ�Ϊ��" + batch + "...");
					Map excpMap = ResultParser.createSvrNotFoundMap();// δ�ҵ�����
					excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
					shareLogVo.setReturn_codes(String.valueOf(excpMap
							.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));// ��־��¼���ش���
					return excpMap;
				}
			} else {
				logger.debug("��������!δ���÷�����,����־���κ�Ϊ��" + batch + "...");
				Map excpMap = ResultParser.createParamErrorMap();// ��������
				excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
				shareLogVo.setReturn_codes(String.valueOf(excpMap
						.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));// ��־��¼���ش���
				return excpMap;
			}
		} catch (Exception e) {
			logger.debug("����־���κ�Ϊ��" + batch + "ϵͳ����,:" + e);
			e.printStackTrace();
			Map excpMap = ResultParser.createSystemErrorMap();// �����صĽ��
			// ��������򽫷��ش�����ڷ�����־��
			if (excpMap.containsKey(ShareConstants.SERVICE_OUT_PARAM_FHDM))
				shareLogVo.setReturn_codes(String.valueOf(excpMap
						.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));
			return excpMap;
		} finally {
			// ��������ʱ
			Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("���÷������������ʱ��" + consumeTime + "�룡" + " ����־���κ�Ϊ��"
					+ batch);
			// ��¼��־
			String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
			shareLogVo.setService_end_time(endTime); // �������ʱ��
			shareLogVo.setConsume_time(consumeTime); // ��������ʱ��
			this.insertLog(shareLogVo);
			logger.debug("��¼��־�ɹ� ����־���κ�Ϊ��" + batch + "...");
		}
	}
	
//	/**
//	 * 
//	 * @param param
//	 */
//	private boolean checkVerify(Map param){
//		//System.out.println("--------checkVerify----------");
//		logger.info("������֤�����Ƿ�Ҫ���������֤");
//		String className = "cn.gwssi.webservice.check.Check_"; 
//		String userName="";
//        boolean result=false;
//        try {
//        	if (null != param.get("LOGIN_NAME")) {
//        		userName = param.get("LOGIN_NAME").toString();
//        		className=className+userName;
//				Class c = Class.forName(className);
//				ICheckRule iTest=(ICheckRule)c.newInstance();
//				result=iTest.checkRule(param);
//				logger.info("У����Ϊ"+result);
//			}else{
//				logger.info("LOGIN_NAMEΪ��");
//				return true;
//			}
//        } catch (Exception e) {
//			//e.printStackTrace();
//        	//System.out.println("δ�ҵ�"+userName+"��Ӧ��У�鴦����");
//        	logger.info("δ�ҵ�"+userName+"��Ӧ��У�鴦����---"+e.getMessage());
//			return true;
//		}		
//		return result;
//	}
	/**
	 * 
	 * queryData ���ݲ�������XML��ʽ�����ݸ��ͻ���
	 * 
	 * @param param
	 * @return String
	 * @throws IOException
	 * @throws DBException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String queryData(String xml) throws IOException, DBException
	{
		// ҵ���߼�
		// 1.��ȡ���÷���Ĳ�������������
		// 2�жϷ������Ƿ����
		// 2a.�������ڷ��ض�Ӧ�Ĵ������
		// 2b.������go on
		// 2.���������еķ����ţ�ȷ����ǰҪ�����ĸ�����
		// 3.У��Ҫ���ʵķ����Ƿ��ܱ����ʣ�����ǲ����û����ü���
		// 3a.�����û����������Ƿ���ȷ��ip�Ƿ���ȷ
		// 3b.�������״̬���÷����ܷ񱻷���
		// 3c.��������ʱ�䣬�Ƿ��ǹ�����
		// 3d.���������ʹ���(ֻ���ʱ��)
		// 3e.��������Ƿ���Ϸ���ı�׼
		// 4.���ݷ����Ż�ȡ������ϸ��Ϣ
		// 5.��������Ҫִ�е�SQL
		// 6.ִ��SQL
		// 7.��ȡִ��SQL�Ľ����������װ��XML��ʽ
		// 8.������򣬷��ʴ�������������
		// 9.��¼��־

		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ��ȡ��������ǰ������ʱ����
		Long start = System.currentTimeMillis(); // ��ʼʱ�����ڼ����ʱ
		int batch = (int) Math.round(Math.random() * 1000000); // ÿһ�η���ʱ����һ�����κţ�������ʶͬһ������־
		logger.debug("��ǰʱ��Ϊ��" + startTime + "��ʼ����queryData����,����־���κ�Ϊ��" + batch
				+ "...");
		ShareLogVo shareLogVo = new ShareLogVo();// ������־��¼
		shareLogVo.setService_start_time(startTime); // ��־��¼����ʼʱ��
		shareLogVo.setPatameter(xml); // ��־��¼�������

		try {
			logger.debug("����Ĳ���Ϊ:" + xml + " ����־���κ�Ϊ��" + batch);
			Map param = XmlToMapUtil.dom2Map(xml);// ������ת����Map
			param.put("batch", batch);

			String ip = this.getClientInfo(param); // ��ȡ�ͻ���IP
			shareLogVo.setAccess_ip(ip);// ��־��¼�ͻ���IP
			logger.debug("���ʵ�ipΪ��" + ip + " ����־���κ�Ϊ��" + batch);

			// �ж�SVR_CODE�Ƿ�Ϊ��
			if (null != param.get(ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
					&& !"".equals(param.get(
							ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
							.toString())) {

				String userName = ""; // �û���
				String serviceCode = ""; // ��λ��Ψһһ������ķ�����(Ϊ�������ʵķ�����)

				serviceCode = param.get(
						ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
						.toString(); // ������
				logger.debug("������Ϊ:" + serviceCode + " ����־���κ�Ϊ��" + batch);

				if (null != param.get("LOGIN_NAME")) {
					userName = param.get("LOGIN_NAME").toString();					
//					if(!checkVerify(param)){
//						Map rsMap=ResultParser.createVerifyErrorMap();
//						shareLogVo.setReturn_codes(String.valueOf(rsMap
//								.get(ShareConstants.SERVICE_FHDM_VERIFY_ERROR))); // ��־��¼���ش���
//						String result = XmlToMapUtil.map2Dom(rsMap);
//						return result;
//					}
					
				}
				
				Map teMap = queryServiceByCode(serviceCode, userName); // ��ѯΨһ��һ������
				if (!teMap.isEmpty()) {
					ServiceVo serviceVo = new ServiceVo();
					ParamUtil.mapToBean(teMap, serviceVo, false);

					logger.debug("����idΪ��" + serviceVo.getService_id()
							+ " ����־���κ�Ϊ��" + batch);
					logger.debug("��λ��Ψһ�ķ�����Ϊ��" + serviceCode + " ����־���κ�Ϊ��"
							+ batch);

					shareLogVo.setService_no(serviceCode);
					shareLogVo.setRecord_start(String.valueOf(param
							.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS))); // ��־��¼��ʼ��¼��
					shareLogVo.setRecord_end(String.valueOf(param
							.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS))); // ��־��¼������¼��
					logger.debug("��ʼ������� ����־���κ�Ϊ��" + batch + "...");
					// У������Ƿ��ܱ�ʹ��
					CheckService checkService = new CheckService();
					Map checkMap = checkService.checkServiceCanBeUsed(
							serviceVo, param, shareLogVo);
					logger.debug("����������" + " ����־���κ�Ϊ��" + batch + "...");
					logger.debug("������Ϊ:" + checkMap + " ����־���κ�Ϊ��" + batch);
					// �������ʧ���򽫷��ش�����ڷ�����־��
					if (checkMap
							.containsKey(ShareConstants.SERVICE_OUT_PARAM_FHDM))
						shareLogVo.setReturn_codes(String.valueOf(checkMap
								.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));
					if ("Y".equals(checkMap.get(
							ShareConstants.SERVICE_CAN_BE_USED).toString())) {
						// ������ʹ��
						param.put("SERVICE_ID", serviceVo.getService_id());
						param = this.queryService(serviceVo.getService_id(),
								param); // ��÷�����ϸ��Ϣ

						Long end = System.currentTimeMillis();
						String consumeTime = String
								.valueOf(((end - start) / 1000f));
						logger.debug("׼������webservices�������������ʱ��" + consumeTime
								+ "�룡" + " ����־���κ�Ϊ��" + batch);
						GeneralService service = ServiceFactory.getService();// ��÷���
						String result = service.queryData(param, shareLogVo); // ��ȡ������
						return result;// ���ؽ��

					} else {
						logger.debug("�������ʧ�ܲ��ܷ��� ����־���κ�Ϊ��" + batch + "...");// ������ʹ��
						checkMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
						String result = XmlToMapUtil.map2Dom(checkMap);
						return result;
					}
				} else {
					logger.debug("�����Ų�����δ�ҵ�����...");
					Map excpMap = ResultParser.createSvrNotFoundMap();// δ�ҵ�����
					excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
					shareLogVo.setReturn_codes(String.valueOf(excpMap
							.get(ShareConstants.SERVICE_OUT_PARAM_FHDM))); // ��־��¼���ش���
					String result = XmlToMapUtil.map2Dom(excpMap);
					return result;
				}
			} else {
				logger.debug("�����Ų�����δ�ҵ����� ����־���κ�Ϊ��" + batch + "...");
				Map excpMap = ResultParser.createSvrNotFoundMap();// δ�ҵ�����
				excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
				shareLogVo.setReturn_codes(String.valueOf(excpMap
						.get(ShareConstants.SERVICE_OUT_PARAM_FHDM))); // ��־��¼���ش���
				String result = XmlToMapUtil.map2Dom(excpMap);
				return result;
			}
		} catch (DBException e) {
			logger.debug("��־���κ�Ϊ��" + batch + "ϵͳ����:" + e);
			e.printStackTrace();
			Map excpMap = ResultParser.createSystemErrorMap();// �����صĽ��
			shareLogVo.setReturn_codes(String.valueOf(excpMap
					.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));// ��־��¼���ش���
			String result = XmlToMapUtil.map2Dom(excpMap);
			return result;
		} finally {
			// ��������ʱ
			Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("���÷���������ܹ���ʱ��" + consumeTime + "�룡" + " ����־���κ�Ϊ��"
					+ batch);
			// ��¼��־
			String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
			shareLogVo.setService_end_time(endTime); // �������ʱ��
			shareLogVo.setConsume_time(consumeTime); // ��������ʱ��
			this.insertLog(shareLogVo);
			logger.debug("��¼��־�ɹ� ����־���κ�Ϊ��" + batch + "...");
		}
	}

	/**
	 * 
	 * queryTrsData(trs���ز�ѯ���) 
	 * 
	 * @param xml
	 * @return
	 * @throws IOException
	 * @throws DBException
	 *             String
	 * @throws TxnException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String queryTrsData(String xml) throws IOException, DBException,
			TxnException
	{
		String ip = this.getClientInfo(null); // ��ȡ�ͻ���IP
		return this.queryTrsResult(xml, ip);
	}

	/**
	 * 
	 * Servlet��ʽ����trs��ʽ�ķ���
	 * 
	 * @return ����xml��ʽ���
	 * @throws TxnException
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String queryTrsDataByHttp(String xml, String ip) throws IOException,
			DBException, TxnException
	{
		return this.queryTrsResult(xml, ip);
	}

	// ҵ���߼�
	// 1.��ȡ���÷���Ĳ���
	// 2.���������еķ����ţ�ȷ����ǰҪ�����ĸ�����
	// 3.У��Ҫ���ʵķ����Ƿ��ܱ����ʣ�����ǲ����û����ü���
	// 3a.�����û����������Ƿ���ȷ��ip�Ƿ���ȷ
	// 3b.�������״̬���÷����ܷ񱻷���
	// 3c.��������ʱ�䣬�Ƿ��ǹ�����
	// 3d.���������ʹ����Ƿ���Ϸ��ʹ���
	// 3e.��������Ƿ���Ϸ���ı�׼
	// 4.���ݷ����Ż�ȡ������ϸ��Ϣ
	// 5.��������Ҫִ�еĲ�ѯ�ַ���
	// 6.��ѯTRS���ݿ�
	// 7.��ȡ��ѯ�����������װ��xml��ʽ
	// 8.��xml�ļ�����ڷ�����
	// 9.��¼��־
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String queryTrsResult(String xml, String ip) throws IOException
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // ��ȡ��������ǰ������ʱ����
		logger.debug("��ǰʱ��Ϊ��" + startTime + "��ʼ����queryTrsData����...");
		ShareLogVo shareLogVo = new ShareLogVo();// ������־��¼
		Long start = System.currentTimeMillis(); // ��ʼʱ�����ڼ����ʱ
		shareLogVo.setService_start_time(startTime); // ��־��¼����ʼʱ��
		shareLogVo.setPatameter(xml); // ��־��¼�������
		Map param = null;
		try {
			logger.debug("����Ĳ���Ϊ:" + xml);
			// System.out.println("����Ĳ���Ϊ:" + xml);
			param = XmlToMapUtil.dom2Map(xml);// ������ת����Map
			logger.debug("���ʵ�ipΪ��" + ip);
			shareLogVo.setAccess_ip(ip);// ��־��¼�ͻ���IP

			// �ж���������Ƿ����
			if (param.get("queryStr") == null
					|| param.get("queryStr").equals("")) {
				Map excpMap = ResultParser.createParamErrorMap();//
				shareLogVo.setReturn_codes(excpMap.get(
						ShareConstants.SERVICE_OUT_PARAM_FHDM).toString());
				String result = XmlToMapUtil.map2Dom(excpMap);
				return result;
			}

			// �ж�SVR_CODE�Ƿ�Ϊ��
			if (null != param.get(ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
					&& !"".equals(param.get(
							ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
							.toString())) {

				String oldServiceCode = param.get(
						ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
						.toString();
				logger.debug("�Ϸ�����Ϊ:" + oldServiceCode);

				String serviceId = ""; // ��λ��Ψһһ������ķ���ID
				String serviceCode = ""; // ��λ��Ψһһ������ķ�����(Ϊ�������ʵķ�����)

				Map teMap = queryTrsSerCode(oldServiceCode); // ��ѯΨһ��һ������

				serviceCode = teMap.get("SERVICE_NO").toString();
				serviceId = teMap.get("SERVICE_ID").toString();
				logger.debug("����idΪ��" + serviceId);
				logger.debug("��λ��Ψһ�ķ�����Ϊ��" + serviceCode);

				shareLogVo.setService_no(serviceCode);

				logger.debug("��ʼ�������...");
				// У������Ƿ��ܱ�ʹ��
				CheckService checkService = new CheckService();
				// ����ʱȡ����ֵ��ֱ���ò�����ȡ�ڶ�����
				Map checkMap = checkService.checkTrsServiceCanBeUsed(
						serviceCode, param, shareLogVo, serviceId);
				logger.debug("����������...");
				logger.debug("������Ϊ:" + checkMap);
				// �������ʧ���򽫷��ش�����ڷ�����־��
				if (checkMap.containsKey(ShareConstants.SERVICE_OUT_PARAM_FHDM))
					shareLogVo.setReturn_codes(String.valueOf(checkMap
							.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));
				if ("Y".equals(checkMap.get(ShareConstants.SERVICE_CAN_BE_USED)
						.toString())) {
					ServiceVo serviceVo = new ServiceVo();
					serviceVo.setService_id(serviceId);

					// ��֤ʱ��εĹ���
					Map dateRuleMap = checkService.checkDateRule(serviceVo);
					if ("Y".equals(dateRuleMap.get(
							ShareConstants.SERVICE_CAN_BE_USED).toString())) {
						// param = this.queryTrsService(serviceId, param);//
						// ��÷�����ϸ��Ϣ������У��ʱ��ȡ��
						param.put("search_db", checkMap.get("search_db")
								.toString());
						param.put("search_column", checkMap
								.get("search_column").toString());
						param.put("show_column", checkMap.get("show_column")
								.toString());
						param.put("trs_service_id",
								checkMap.get("trs_service_id").toString());
						param.put("trs_template", checkMap.get("trs_template")
								.toString());
						Long end = System.currentTimeMillis();
						String consumeTime = String
								.valueOf(((end - start) / 1000f));
						logger.debug("׼������webservices�������������ʱ��" + consumeTime
								+ "�룡");
						GeneralService service = ServiceFactory.getService();// ��÷���
						String result = service.queryTrsData(param, shareLogVo); // ��ȡ������
						// this.inputFile(result, serviceCode);// �����ļ�
						return result;// ���ؽ��

					} else {
						shareLogVo.setReturn_codes(dateRuleMap.get(
								ShareConstants.SERVICE_OUT_PARAM_FHDM)
								.toString());
						shareLogVo.setRecord_amount("0");
						logger.debug("����ʱ��������ʧ�ܲ��ܷ���...");// ������ʹ��
						dateRuleMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
						String result = XmlToMapUtil.map2Dom(dateRuleMap);
						return result;
					}

				} else {
					logger.debug("�������ʧ�ܲ��ܷ���...");// ������ʹ��
					checkMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
					String result = XmlToMapUtil.map2Dom(checkMap);
					// this.inputFile(result, serviceCode);// �����ļ�
					return result;
				}

			} else {
				logger.debug("�����Ų�����δ�ҵ�����...");
				Map excpMap = ResultParser.createSvrNotFoundMap();// δ�ҵ�����
				excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
				shareLogVo.setReturn_codes(String.valueOf(excpMap
						.get(ShareConstants.SERVICE_OUT_PARAM_FHDM))); // ��־��¼���ش���
				String result = XmlToMapUtil.map2Dom(excpMap);
				// this.inputFile(result, "");// �����ļ�
				return result;
			}
		} catch (Exception e) {
			logger.debug("ϵͳ����:" + e.getMessage());
			e.printStackTrace();
			Map excpMap = ResultParser.createSystemErrorMap();// �����صĽ��
			shareLogVo.setReturn_codes(String.valueOf(excpMap
					.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));// ��־��¼���ش���
			String result = XmlToMapUtil.map2Dom(excpMap);
			// this.inputFile(result, "");// �����ļ�
			return result;
		} finally {
			// ��������ʱ
			Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("���÷���������ܹ���ʱ��" + consumeTime + "�룡");
			// ��¼��־
			String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
			String log_type = "02";
			if (null == param) {
				log_type = "01";
			} else {
				if (null == param.get("USER_TYPE")) {
					log_type = "02";
				} else {
					log_type = "01";
				}
			}
			shareLogVo.setLog_type(log_type);
			shareLogVo.setService_type("99");
			shareLogVo.setService_end_time(endTime); // �������ʱ��
			shareLogVo.setConsume_time(consumeTime); // ��������ʱ��
			//shareLogVo.setLog_type(ExConstant.LOG_TYPE_TEST);
			this.insertLog(shareLogVo);
			logger.debug("��¼��־�ɹ�...");
		}
	}

	/**
	 * 
	 * queryTrsSerCode ��ѯ�������
	 * 
	 * @param serviceCode
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public Map queryTrsSerCode(String serviceCode)
	{
		String sql = SQLHelper.queryTrsSerCodeSql(serviceCode);
		Map teMap = new HashMap();
		try {
			teMap = dao.queryTrsSerCode(sql);
		} catch (DBException e) {
			logger.debug("��ѯ�Ϸ�����뱨��..." + e);
			e.printStackTrace();
		}
		return teMap;
	}

	/**
	 * 
	 * queryServiceByCode �����û�����������
	 * 
	 * @param serviceCode
	 * @param userName
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected Map queryServiceByCode(String serviceCode, String userName)
	{
		String sql = SQLHelper.queryService(serviceCode, userName);
		Map serviceMap = new HashMap();
		try {
			serviceMap = dao.queryService(sql);
		} catch (DBException e) {
			logger.debug("��ѯ��������..." + e);
			e.printStackTrace();
		}
		return serviceMap;
	}

	/**
	 * 
	 * queryService ���ݲ�����ȡ��ǰ�����ʵķ�����ϸ��Ϣ
	 * 
	 * @param serviceId
	 * @param param
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected Map queryService(String serviceId, Map param)
	{
		Map tmpMap = new HashMap();
		tmpMap = getOhterColumn(serviceId);
		String querySql = String.valueOf(tmpMap.get("sql"));
		logger.debug("����ǰ��SQLΪ��" + querySql);
		param.put(ShareConstants.SERVICE_QUERY_SQL, querySql);
		
		ParamAnalyzer paramAnalyzer = new ParamAnalyzer(param);
		
		// ������ѯ��SQL
		String anayzerQuerySql = paramAnalyzer.createSQL();
		
		param.put(ShareConstants.SERVICE_QUERY_SQL, anayzerQuerySql);
		logger.debug("�������SQLΪ��" + anayzerQuerySql);
		logger.debug("����SQL���...");
		return param;
	}

	/**
	 * 
	 * insertLog ������־
	 * 
	 * @param shareLogVo
	 * @return
	 * @throws DBException
	 *             int
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private int insertLog(ShareLogVo shareLogVo)
	{
		logger.debug("��¼��־...");
		String sql = SQLHelper.insertLog(shareLogVo);
		int count = 0;
		try {
			count = dao.insertShareLog(sql);
		} catch (DBException e) {
			logger.debug("��¼��־����..." + e);
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("finally")
	public Map<String, String> getOhterColumn(String svrNo)
	{
		// ��ʼ���ļ�
		String svrPath = ExConstant.SHARE_CONFIG;
		File file = new File(svrPath + File.separator + svrNo + ".dat");
		Map<String, String> tmpMap = new HashMap<String, String>();
		// �ж��ļ��Ƿ����
		if (file.exists()) {
			InputStreamReader read = null;
			BufferedReader reader = null;
			try {
				read = new InputStreamReader(new FileInputStream(file), "UTF-8");
				reader = new BufferedReader(read);
				String line = reader.readLine();
				while (line != null) {
					String[] cols = line.split("###");
					tmpMap.put("service_no", cols[0]);
					tmpMap.put("column_no", cols[1]);
					tmpMap.put("column_name_cn", cols[2]);
					tmpMap.put("sql", cols[3]);
					tmpMap.put("column_name_en", cols[4]);
					tmpMap.put("jsoncolumns", cols[5]);
					line = reader.readLine();
				}
			} catch (UnsupportedEncodingException e) {
				logger.debug("�ַ�������...");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				logger.debug("û���ҵ��ļ�" + svrNo);
				e.printStackTrace();
			} catch (IOException e) {
				logger.debug("��ȡ�ļ�����...");
				e.printStackTrace();
			} finally {
				if (null != read) {
					try {
						read.close();
					} catch (final IOException e) {
						e.printStackTrace();
					}
				}
				if (null != reader) {
					try {
						reader.close();
					} catch (final IOException e) {
						e.printStackTrace();
					}
				}
				return tmpMap;
			}
		} else {
			return tmpMap;
		}
	}

	/**
	 * 
	 * getClientInfo ��ȡ���ʷ���Ŀͻ��˵�ַ
	 * 
	 * @param param
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private String getClientInfo(Map param)
	{
		String clientIP = "";
		if (param.containsKey("USER_TYPE")
				&& ("TEST".equals(param.get("USER_TYPE"))
				|| "SHARE_FTP".equals(param.get("USER_TYPE")))) {
			// ����ǲ��Խӿڻ����ǹ���FTP���������ߴ˷��������û�ȡIP
		} else {
			MessageContext mc = MessageContext.getCurrentContext();
			HttpServletRequest request;
			request = (HttpServletRequest) mc
					.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
			clientIP = request.getHeader("x-forwarded-for");
			if (clientIP == null || clientIP.length() == 0
					|| "unknown".equalsIgnoreCase(clientIP)) {
				clientIP = request.getHeader("Proxy-Client-IP");
			}
			if (clientIP == null || clientIP.length() == 0
					|| "unknown".equalsIgnoreCase(clientIP)) {
				clientIP = request.getHeader("WL-Proxy-Client-IP");
			}
			if (clientIP == null || clientIP.length() == 0
					|| "unknown".equalsIgnoreCase(clientIP)) {
				clientIP = request.getRemoteAddr();
			}
		}
		return clientIP;
	}

}
