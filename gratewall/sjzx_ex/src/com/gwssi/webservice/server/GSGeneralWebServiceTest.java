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
 * 项目名称：bjgs_exchange 类名称：GSGeneralWebService 类描述：提供共享服务的方法 创建人：lizheng 创建时间：Mar
 * 27, 2013 3:54:30 PM 修改人：lizheng 修改时间：Mar 27, 2013 3:54:30 PM 修改备注：
 * 
 * @version
 * 
 */
public class GSGeneralWebServiceTest
{

	ServiceDAO	dao	= null; // 操作数据库Dao

	public GSGeneralWebServiceTest()
	{
		dao = new ServiceDAOImpl();
	}

	protected static Logger	logger	= TxnLogger
											.getLogger(GSGeneralWebServiceTest.class
													.getName());	// 日志

	/**
	 * 
	 * HelloWord 测试webservice方法
	 * 
	 * @param param
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String HelloWord(String username,String password)
	{
		return "Hello:" + username+" "+password;
	}
	

	
	/**
	 * 
	 * queryData 根据参数返回XML格式的数据给客户端
	 * 
	 * @param param
	 * @return String
	 * @throws IOException
	 * @throws DBException
	 * @throws SQLException 
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String queryDataTest(String xml) throws IOException, DBException, SQLException
	{
		System.out.println("--------------1------------");
		Map params = XmlToMapUtil.dom2Map(xml);// 将参数转化成Map
		int startNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_KSJLS).toString());// 开始记录数
		int endNum = Integer.parseInt(params.get(
				ShareConstants.SERVICE_OUT_PARAM_JSJLS).toString());// 结束记录数
		String dateFrom = params.get("DATE_FROM").toString();//开始时间
		String dateTo = params.get("DATE_TO").toString();//结束时间
		
		Connection conn = DbUtils.getConnection("6"); // 初始化connection
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
		
		String countSql = SQLHelper.getCountSQL(querySql); // 获取查询总条数的SQL
		rs = conn.createStatement().executeQuery(querySql); // 获取结果集
		ResultSetMetaData md = rs.getMetaData(); // 获取查询的字段
		int columnCount = md.getColumnCount(); // 获取每行的总列数
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
		rs = conn.createStatement().executeQuery(countSql); // 获取记录总条数
		while (rs.next()) {
			count = rs.getString(ShareConstants.SERVICE_OUT_TOTALS);
		}
		
		return result;// 返回结果
			
		
		
	}

	/**
	 * 
	 * queryServiceByCode 根据用户名，服务编号
	 * 
	 * @param serviceCode
	 * @param userName
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected Map queryServiceByCode(String serviceCode, String userName)
	{
		String sql = SQLHelper.queryService(serviceCode, userName);
		Map serviceMap = new HashMap();
		try {
			serviceMap = dao.queryService(sql);
		} catch (DBException e) {
			logger.debug("查询参数报错..." + e);
			e.printStackTrace();
		}
		return serviceMap;
	}

	/**
	 * 
	 * queryService 根据参数获取当前被访问的服务详细信息
	 * 
	 * @param serviceId
	 * @param param
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected Map queryService(String serviceId, Map param)
	{
		Map tmpMap = new HashMap();
		tmpMap = getOhterColumn(serviceId);
		String querySql = String.valueOf(tmpMap.get("sql"));
		logger.debug("解析前的SQL为：" + querySql);
		param.put(ShareConstants.SERVICE_QUERY_SQL, querySql);
		// 解析查询的SQL
		ParamAnalyzer paramAnalyzer = new ParamAnalyzer(param);
		String anayzerQuerySql = paramAnalyzer.createSQL();
		param.put(ShareConstants.SERVICE_QUERY_SQL, anayzerQuerySql);
		logger.debug("解析后的SQL为：" + anayzerQuerySql);
		logger.debug("解析SQL完毕...");
		return param;
	}

	/**
	 * 
	 * insertLog 插入日志
	 * 
	 * @param shareLogVo
	 * @return
	 * @throws DBException
	 *             int
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private int insertLog(ShareLogVo shareLogVo)
	{
		logger.debug("记录日志...");
		String sql = SQLHelper.insertLog(shareLogVo);
		int count = 0;
		try {
			count = dao.insertShareLog(sql);
		} catch (DBException e) {
			logger.debug("记录日志报错..." + e);
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("finally")
	public Map<String, String> getOhterColumn(String svrNo)
	{
		// 开始读文件
		String svrPath = ExConstant.SHARE_CONFIG;
		File file = new File(svrPath + File.separator + svrNo + ".dat");
		Map<String, String> tmpMap = new HashMap<String, String>();
		// 判断文件是否存在
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
				logger.debug("字符集报错...");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				logger.debug("没有找到文件" + svrNo);
				e.printStackTrace();
			} catch (IOException e) {
				logger.debug("读取文件报错...");
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
	 * getClientInfo 获取访问服务的客户端地址
	 * 
	 * @param param
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private String getClientInfo(Map param)
	{
		String clientIP = "";
		if (param.containsKey("USER_TYPE")
				&& "TEST".equals(param.get("USER_TYPE"))) {
			// 如果是测试接口则不用走此方法，不用获取IP
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
