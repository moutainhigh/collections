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
 * 项目名称：bjgs_exchange 类名称：ExcuteClient 类描述：客户端的执行方法 创建人：lizheng 创建时间：Apr 2, 2013
 * 3:53:30 PM 修改人：lizheng 修改时间：Apr 2, 2013 3:53:30 PM 修改备注：
 * 
 * @version
 * 
 */
public class ServerService
{
	
	
	ServiceDAO	dao	= null; // 操作数据库Dao

	public ServerService()
	{
		dao = new ServiceDAOImpl();
	}
	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(ServerService.class
											.getName());

	String					result	= null;

	/**
	 * 
	 * queryData 根据参数返回XML格式的数据给客户端
	 * 
	 * @param param
	 * @return String
	 * @throws IOException
	 * @throws DBException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	
		public String queryData(String xml,String ip) throws IOException, DBException
		{
			// 业务逻辑
			// 1.获取调用服务的参数
			// 2.解析参数中的服务编号，确定当前要调用哪个服务
			// 3.校验要访问的服务是否能被访问，如果是测试用户不用检验
			// 3a.检验用户名，密码是否正确，ip是否正确
			// 3b.检验服务状态，该服务能否被访问
			// 3c.检验例外时间，是否是工作日
			// 3d.检验服务访问规则，是否符合访问规则
			// 3e.检验参数是否符合服务的标准
			// 4.根据服务编号获取服务详细信息
			// 5.解析服务要执行的SQL
			// 6.执行SQL
			// 7.获取执行SQL的结果集，并封装成xml格式
			// 8.将xml文件存放在服务上
			// 9.记录日志

			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String startTime = sDateFormat.format(new java.util.Date()); // 读取服务器当前年月日时分秒
			logger.debug("当前时间为：" + startTime + "开始调用queryData服务...");
			ShareLogVo shareLogVo = new ShareLogVo();// 共享日志记录

			Long start = System.currentTimeMillis(); // 开始时间用于计算耗时
			shareLogVo.setService_start_time(startTime); // 日志记录服务开始时间
			shareLogVo.setPatameter(xml); // 日志记录传入参数

			try {
				logger.debug("传入的参数为:" + xml);
				Map param = XmlToMapUtil.dom2Map(xml);// 将参数转化成Map

				//String ip = this.getClientInfo(param); // 获取客户端IP
				
				logger.debug("访问的ip为：" + ip);
				shareLogVo.setAccess_ip(ip);// 日志记录客户端IP

				// 判断SVR_CODE是否为空
				if (null != param.get(ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
						&& !"".equals(param.get(
								ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
								.toString())) {

					String oldServiceCode = param.get(
							ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
							.toString(); // 服务编号的机制完全和数据中心二期一样
					logger.debug("老服务编号为:" + oldServiceCode);

					String userName = ""; // 用户名
					String serviceId = ""; // 定位的唯一一条服务的服务ID
					String serviceCode = ""; // 定位的唯一一条服务的服务编号(为真正访问的服务编号)

					if (null != param.get("LOGIN_NAME")) {
						userName = param.get("LOGIN_NAME").toString();
					}
					Map teMap = queryOldSerCode(oldServiceCode, userName); // 查询唯一的一条服务
					if (!teMap.isEmpty()) {
						serviceCode = teMap.get("SERVICE_NO").toString();
						serviceId = teMap.get("SERVICE_ID").toString();
						logger.debug("服务id为：" + serviceId);
						logger.debug("定位的唯一的服务编号为：" + serviceCode);

						shareLogVo.setService_no(serviceCode);
						shareLogVo.setRecord_start(String.valueOf(param
								.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS))); // 日志记录开始记录数
						shareLogVo.setRecord_end(String.valueOf(param
								.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS))); // 日志记录结束记录数
						logger.debug("开始检验服务...");
						// 校验服务是否能被使用
						CheckService checkService = new CheckService();
						Map checkMap = checkService.checkServiceCanBeUsed(
								serviceCode, param, shareLogVo, serviceId);
						logger.debug("检验服务完毕...");
						logger.debug("检验结果为:" + checkMap);
						// 如果检验失败则将返回代码放在服务日志中
						if (checkMap
								.containsKey(ShareConstants.SERVICE_OUT_PARAM_FHDM))
							shareLogVo.setReturn_codes(String.valueOf(checkMap
									.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));
						if ("Y".equals(checkMap.get(
								ShareConstants.SERVICE_CAN_BE_USED).toString())) {
							param = this.queryService(serviceId, param);// 获得服务详细信息
							Long end = System.currentTimeMillis();
							String consumeTime = String
									.valueOf(((end - start) / 1000f));
							logger.debug("准备调用webservices服务操作，共耗时：" + consumeTime
									+ "秒！");
							GeneralService service = ServiceFactory.getService();// 获得服务
							String result = service.queryData(param, shareLogVo); // 获取服务结果
							this.inputFile(result, serviceCode);// 生成文件
							return result;// 返回结果
						} else {
							logger.debug("服务检验失败不能访问...");// 服务不能使用
							checkMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
							String result = XmlToMapUtil.map2Dom(checkMap);
							this.inputFile(result, serviceCode);// 生成文件
							return result;
						}
					} else {
						logger.debug("服务编号不存在未找到服务...");
						Map excpMap = ResultParser.createSvrNotFoundMap();// 未找到服务
						excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
						shareLogVo.setReturn_codes(String.valueOf(excpMap
								.get(ShareConstants.SERVICE_OUT_PARAM_FHDM))); // 日志记录返回代码
						String result = XmlToMapUtil.map2Dom(excpMap);
						this.inputFile(result, "");// 生成文件
						return result;
					}
				} else {
					logger.debug("服务编号不存在未找到服务...");
					Map excpMap = ResultParser.createSvrNotFoundMap();// 未找到服务
					excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
					shareLogVo.setReturn_codes(String.valueOf(excpMap
							.get(ShareConstants.SERVICE_OUT_PARAM_FHDM))); // 日志记录返回代码
					String result = XmlToMapUtil.map2Dom(excpMap);
					this.inputFile(result, "");// 生成文件
					return result;
				}
			} catch (DBException e) {
				logger.debug("系统错误:" + e);
				e.printStackTrace();
				Map excpMap = ResultParser.createSystemErrorMap();// 报错返回的结果
				shareLogVo.setReturn_codes(String.valueOf(excpMap
						.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));// 日志记录返回代码
				String result = XmlToMapUtil.map2Dom(excpMap);
				this.inputFile(result, "");// 生成文件
				return result;
			} finally {
				// 计算服务耗时
				Long end = System.currentTimeMillis(); // 结束时间用于计算耗时
				String consumeTime = String.valueOf(((end - start) / 1000f));
				logger.debug("调用服务操作，总共耗时：" + consumeTime + "秒！");
				// 记录日志
				String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
				shareLogVo.setService_end_time(endTime); // 服务结束时间
				shareLogVo.setConsume_time(consumeTime); // 服务消耗时间
				this.insertLog(shareLogVo);
				logger.debug("记录日志成功...");
			}
		}
		
		/**
		 * 
		 * queryOldSerCode 模仿二期的逻辑通过用户名和sercode定位唯一的一个服务编号
		 * 
		 * @param serviceCode
		 * @param userName
		 * @return Map
		 * @Exception 异常对象
		 * @since CodingExample Ver(编码范例查看) 1.1
		 */
		protected Map queryOldSerCode(String serviceCode, String userName)
		{
			String sql = SQLHelper.queryOldSerCodeSql(serviceCode, userName);
			Map teMap = new HashMap();
			ServiceDAO dao=new ServiceDAOImpl();
			try {
				teMap = dao.queryOldSerCode(sql);
			} catch (DBException e) {
				logger.debug("查询老服务编码报错..." + e);
				e.printStackTrace();
			}
			return teMap;
		}

		/**
		 * 
		 * getClientInfo 获取访问服务的客户端地址
		 * 
		 * @return String
		 * @Exception 异常对象
		 * @since CodingExample Ver(编码范例查看) 1.1
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
			logger.debug("开始解析SQL...");
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
		 * @param dom
		 * @param serviceCode
		 * @throws IOException
		 */
		private void inputFile(String dom, String serviceCode) throws IOException
		{
			java.sql.Timestamp dateForFileName = new java.sql.Timestamp(new Date()
					.getTime());

			logger.debug("开始存储xml文件...");
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
				logger.debug("存储xml文件完毕...");
				logger.debug("xml文件存储路径为：" + path);

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

		/**
		 * 
		 * timeFormat 将系统时间改为特定格式
		 * 
		 * @param time
		 * @return String
		 * @Exception 异常对象
		 * @since CodingExample Ver(编码范例查看) 1.1
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

}
