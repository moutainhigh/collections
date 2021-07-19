package com.gwssi.webservice.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.log4j.Logger;
import org.jfree.data.DataUtilities;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;

import com.ctc.wstx.util.DataUtil;
import com.genersoft.frame.base.database.DBException;
import com.genersoft.frame.base.database.DbUtils;
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
public class GSGeneralWebServiceTest
{

	ServiceDAO	dao	= null; // �������ݿ�Dao

	public GSGeneralWebServiceTest()
	{
		dao = new ServiceDAOImpl();
	}

	protected static Logger	logger	= TxnLogger
											.getLogger(GSGeneralWebServiceTest.class
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
	public String HelloWord(String username,String password)
	{
		return "Hello:" + username+" "+password;
	}
	

	
	/**
	 * 
	 * queryData ���ݲ�������XML��ʽ�����ݸ��ͻ���
	 * 
	 * @param param
	 * @return String
	 * @throws IOException
	 * @throws DBException
	 * @throws SQLException 
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public String queryDataTest(String xml) throws IOException, DBException, SQLException
	{
		System.out.println("--------------1------------");
		Map params = XmlToMapUtil.dom2Map(xml);// ������ת����Map
		int startNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// ��ʼ��¼��
		int endNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// ������¼��
		String dateFrom = params.get("DATE_FROM").toString();//��ʼʱ��
		String dateTo = params.get("DATE_TO").toString();//����ʱ��
		
		Connection conn = DbUtils.getConnection("6"); // ��ʼ��connection
		ResultSet rs = null;
		String querySql ="select * from (select a.*,rownum rn from("
						+"select * from reg_bus_ent r where "
						+" r.update_date>= '"
						+dateFrom
						+"' and r.update_date<= '"
						+dateTo
						+"' ) a ) where rn <= "
						+endNum
						+" and rn >= "
					    +startNum;
		
		String countSql = SQLHelper.getCountSQL(querySql); // ��ȡ��ѯ��������SQL
		rs = conn.createStatement().executeQuery(querySql); // ��ȡ�����
		ResultSetMetaData md = rs.getMetaData(); // ��ȡ��ѯ���ֶ�
		int columnCount = md.getColumnCount(); // ��ȡÿ�е�������
		List DataList = new ArrayList();
		Map rowData;
		while (rs.next()) {
			rowData = new HashMap(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				if (null != rs.getObject(i))
					rowData.put(md.getColumnName(i), rs.getObject(i));
				else
					rowData.put(md.getColumnName(i), "");
			}
			DataList.add(rowData);
		}
		String result = DataList.toString();
		String count = "0";
		rs = conn.createStatement().executeQuery(countSql); // ��ȡ��¼������
		while (rs.next()) {
			count = rs.getString(ShareConstants.SERVICE_OUT_TOTALS);
		}
		
		return result;// ���ؽ��
			
		
		
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
				&& "TEST".equals(param.get("USER_TYPE"))) {
			// ����ǲ��Խӿ������ߴ˷��������û�ȡIP
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
