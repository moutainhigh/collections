package com.gwssi.socket.share.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.util.XmlToMapUtil;
import com.gwssi.log.sharelog.dao.ShareLogVo;
import com.gwssi.webservice.server.ParamAnalyzer;
import com.gwssi.webservice.server.ResultParser;
import com.gwssi.webservice.server.SQLHelper;
import com.gwssi.webservice.server.ServiceDAO;
import com.gwssi.webservice.server.ServiceDAOImpl;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�ExcuteClient ���������ͻ��˵�ִ�з��� �����ˣ�lizheng ����ʱ�䣺Apr 2, 2013
 * 3:53:30 PM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 2, 2013 3:53:30 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class ServerService
{
	
	
	ServiceDAO	dao	= null; // �������ݿ�Dao

	public ServerService()
	{
		dao = new ServiceDAOImpl();
	}
	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(ServerService.class
											.getName());

	String					result	= null;

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
	
		public String queryData(String xml,String ip) throws IOException, DBException
		{
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
			// 5.��������Ҫִ�е�SQL
			// 6.ִ��SQL
			// 7.��ȡִ��SQL�Ľ����������װ��xml��ʽ
			// 8.��xml�ļ�����ڷ�����
			// 9.��¼��־

			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String startTime = sDateFormat.format(new java.util.Date()); // ��ȡ��������ǰ������ʱ����
			logger.debug("��ǰʱ��Ϊ��" + startTime + "��ʼ����queryData����...");
			ShareLogVo shareLogVo = new ShareLogVo();// ������־��¼

			Long start = System.currentTimeMillis(); // ��ʼʱ�����ڼ����ʱ
			shareLogVo.setService_start_time(startTime); // ��־��¼����ʼʱ��
			shareLogVo.setPatameter(xml); // ��־��¼�������

			try {
				logger.debug("����Ĳ���Ϊ:" + xml);
				Map param = XmlToMapUtil.dom2Map(xml);// ������ת����Map

				//String ip = this.getClientInfo(param); // ��ȡ�ͻ���IP
				
				logger.debug("���ʵ�ipΪ��" + ip);
				shareLogVo.setAccess_ip(ip);// ��־��¼�ͻ���IP

				// �ж�SVR_CODE�Ƿ�Ϊ��
				if (null != param.get(ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
						&& !"".equals(param.get(
								ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
								.toString())) {

					String oldServiceCode = param.get(
							ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
							.toString(); // �����ŵĻ�����ȫ���������Ķ���һ��
					logger.debug("�Ϸ�����Ϊ:" + oldServiceCode);

					String userName = ""; // �û���
					String serviceId = ""; // ��λ��Ψһһ������ķ���ID
					String serviceCode = ""; // ��λ��Ψһһ������ķ�����(Ϊ�������ʵķ�����)

					if (null != param.get("LOGIN_NAME")) {
						userName = param.get("LOGIN_NAME").toString();
					}
					Map teMap = queryOldSerCode(oldServiceCode, userName); // ��ѯΨһ��һ������
					if (!teMap.isEmpty()) {
						serviceCode = teMap.get("SERVICE_NO").toString();
						serviceId = teMap.get("SERVICE_ID").toString();
						logger.debug("����idΪ��" + serviceId);
						logger.debug("��λ��Ψһ�ķ�����Ϊ��" + serviceCode);

						shareLogVo.setService_no(serviceCode);
						shareLogVo.setRecord_start(String.valueOf(param
								.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS))); // ��־��¼��ʼ��¼��
						shareLogVo.setRecord_end(String.valueOf(param
								.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS))); // ��־��¼������¼��
						logger.debug("��ʼ�������...");
						// У������Ƿ��ܱ�ʹ��
						CheckService checkService = new CheckService();
						Map checkMap = checkService.checkServiceCanBeUsed(
								serviceCode, param, shareLogVo, serviceId);
						logger.debug("����������...");
						logger.debug("������Ϊ:" + checkMap);
						// �������ʧ���򽫷��ش�����ڷ�����־��
						if (checkMap
								.containsKey(ShareConstants.SERVICE_OUT_PARAM_FHDM))
							shareLogVo.setReturn_codes(String.valueOf(checkMap
									.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));
						if ("Y".equals(checkMap.get(
								ShareConstants.SERVICE_CAN_BE_USED).toString())) {
							param = this.queryService(serviceId, param);// ��÷�����ϸ��Ϣ
							Long end = System.currentTimeMillis();
							String consumeTime = String
									.valueOf(((end - start) / 1000f));
							logger.debug("׼������webservices�������������ʱ��" + consumeTime
									+ "�룡");
							GeneralService service = ServiceFactory.getService();// ��÷���
							String result = service.queryData(param, shareLogVo); // ��ȡ������
							this.inputFile(result, serviceCode);// �����ļ�
							return result;// ���ؽ��
						} else {
							logger.debug("�������ʧ�ܲ��ܷ���...");// ������ʹ��
							checkMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
							String result = XmlToMapUtil.map2Dom(checkMap);
							this.inputFile(result, serviceCode);// �����ļ�
							return result;
						}
					} else {
						logger.debug("�����Ų�����δ�ҵ�����...");
						Map excpMap = ResultParser.createSvrNotFoundMap();// δ�ҵ�����
						excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
						shareLogVo.setReturn_codes(String.valueOf(excpMap
								.get(ShareConstants.SERVICE_OUT_PARAM_FHDM))); // ��־��¼���ش���
						String result = XmlToMapUtil.map2Dom(excpMap);
						this.inputFile(result, "");// �����ļ�
						return result;
					}
				} else {
					logger.debug("�����Ų�����δ�ҵ�����...");
					Map excpMap = ResultParser.createSvrNotFoundMap();// δ�ҵ�����
					excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
					shareLogVo.setReturn_codes(String.valueOf(excpMap
							.get(ShareConstants.SERVICE_OUT_PARAM_FHDM))); // ��־��¼���ش���
					String result = XmlToMapUtil.map2Dom(excpMap);
					this.inputFile(result, "");// �����ļ�
					return result;
				}
			} catch (DBException e) {
				logger.debug("ϵͳ����:" + e);
				e.printStackTrace();
				Map excpMap = ResultParser.createSystemErrorMap();// �����صĽ��
				shareLogVo.setReturn_codes(String.valueOf(excpMap
						.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));// ��־��¼���ش���
				String result = XmlToMapUtil.map2Dom(excpMap);
				this.inputFile(result, "");// �����ļ�
				return result;
			} finally {
				// ��������ʱ
				Long end = System.currentTimeMillis(); // ����ʱ�����ڼ����ʱ
				String consumeTime = String.valueOf(((end - start) / 1000f));
				logger.debug("���÷���������ܹ���ʱ��" + consumeTime + "�룡");
				// ��¼��־
				String endTime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
				shareLogVo.setService_end_time(endTime); // �������ʱ��
				shareLogVo.setConsume_time(consumeTime); // ��������ʱ��
				this.insertLog(shareLogVo);
				logger.debug("��¼��־�ɹ�...");
			}
		}
		
		/**
		 * 
		 * queryOldSerCode ģ�¶��ڵ��߼�ͨ���û�����sercode��λΨһ��һ��������
		 * 
		 * @param serviceCode
		 * @param userName
		 * @return Map
		 * @Exception �쳣����
		 * @since CodingExample Ver(���뷶���鿴) 1.1
		 */
		protected Map queryOldSerCode(String serviceCode, String userName)
		{
			String sql = SQLHelper.queryOldSerCodeSql(serviceCode, userName);
			Map teMap = new HashMap();
			ServiceDAO dao=new ServiceDAOImpl();
			try {
				teMap = dao.queryOldSerCode(sql);
			} catch (DBException e) {
				logger.debug("��ѯ�Ϸ�����뱨��..." + e);
				e.printStackTrace();
			}
			return teMap;
		}

		/**
		 * 
		 * getClientInfo ��ȡ���ʷ���Ŀͻ��˵�ַ
		 * 
		 * @return String
		 * @Exception �쳣����
		 * @since CodingExample Ver(���뷶���鿴) 1.1
		 */
		private String getClientInfo(Map param)
		{
			String clientIP = "";
			if (param.containsKey("USER_TYPE")
					&& "TEST".equals(param.get("USER_TYPE"))) {
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
			logger.debug("��ʼ����SQL...");
			Map tmpMap = new HashMap();
			tmpMap = getOhterColumn(serviceId);
			String querySql = String.valueOf(tmpMap.get("sql"));
			logger.debug("����ǰ��SQLΪ��" + querySql);
			param.put(ShareConstants.SERVICE_QUERY_SQL, querySql);
			// ������ѯ��SQL
			ParamAnalyzer paramAnalyzer = new ParamAnalyzer(param);
			String anayzerQuerySql = paramAnalyzer.createSQL();
			param.put(ShareConstants.SERVICE_QUERY_SQL, anayzerQuerySql);
			logger.debug("�������SQLΪ��" + anayzerQuerySql);
			logger.debug("����SQL���...");
			return param;
		}
		/**
		 * 
		 * @param dom
		 * @param serviceCode
		 * @throws IOException
		 */
		private void inputFile(String dom, String serviceCode) throws IOException
		{
			java.sql.Timestamp dateForFileName = new java.sql.Timestamp(new Date()
					.getTime());

			logger.debug("��ʼ�洢xml�ļ�...");
			StringBuffer path = new StringBuffer();
			path.append(ExConstant.SHARE_XML);
			path.append(File.separator);
			path.append(serviceCode);
			path.append("_");
			path.append(timeFormat(dateForFileName.toString()));
			path.append(".xml");

			OutputFormat format = OutputFormat.createPrettyPrint();
			XMLWriter output = null;
			try {
				Document doc = DocumentHelper.parseText(dom);
				output = new XMLWriter(new FileOutputStream(new File(path
						.toString())), format);
				output.write(doc);
				logger.debug("�洢xml�ļ����...");
				logger.debug("xml�ļ��洢·��Ϊ��" + path);

			} catch (DocumentException e) {
				if (null != output)
					output.close();
				logger.debug("DocumentException:" + e);
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				if (null != output)
					output.close();
				logger.debug("UnsupportedEncodingException:" + e);
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				if (null != output)
					output.close();
				logger.debug("FileNotFoundException:" + e);
				e.printStackTrace();
			} catch (IOException e1) {
				if (null != output)
					output.close();
				logger.debug("IOException:" + e1);
				e1.printStackTrace();
			} finally {
				if (null != output)
					output.close();
			}
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

		/**
		 * 
		 * timeFormat ��ϵͳʱ���Ϊ�ض���ʽ
		 * 
		 * @param time
		 * @return String
		 * @Exception �쳣����
		 * @since CodingExample Ver(���뷶���鿴) 1.1
		 */
		private String timeFormat(String time)
		{
			String currenttime = "";
			if (time != null && !"".equals(time)) {
				String temp[] = time.split(" ");
				String temp1[] = temp[0].split("-");
				String temp2[] = temp[1].split(":");
				for (int i = 0; i < temp1.length; i++) {
					currenttime = currenttime + temp1[i];
				}
				for (int j = 0; j < temp2.length - 1; j++) {
					currenttime = currenttime + temp2[j];
				}
			}
			return currenttime;
		}
		/**
		 * 
		 * @param svrNo
		 * @return
		 */
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

}
