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
 * 项目名称：bjgs_exchange 类名称：GSGeneralWebService 类描述：提供共享服务的方法 创建人：lizheng 创建时间：Mar
 * 27, 2013 3:54:30 PM 修改人：lizheng 修改时间：Mar 27, 2013 3:54:30 PM 修改备注：
 * 
 * @version
 * 
 */
public class GSGeneralWebService
{

	ServiceDAO	dao	= null; // 操作数据库Dao

	public GSGeneralWebService()
	{
		dao = new ServiceDAOImpl();
	}

	protected static Logger	logger	= TxnLogger
											.getLogger(GSGeneralWebService.class
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
	public String HelloWord(String param)
	{
		return "Hello:" + param;
	}

	/**
	 * query 根据参数返回MAP格式的数据给客户端
	 * 
	 * @param param
	 * @return Map
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map query(Map param) throws DBException
	{
		// 业务逻辑
		// 1.获取调用服务的参数，解析参数
		// 2判断服务编号是否存在
		// 2a.若不存在返回对应的错误编码
		// 2b.若存在go on
		// 2.解析参数中的服务编号，确定当前要调用哪个服务
		// 3.校验要访问的服务是否能被访问，如果是测试用户不用检验
		// 3a.检验用户名，密码是否正确，ip是否正确
		// 3b.检验服务状态，该服务能否被访问
		// 3c.检验例外时间，是否是工作日
		// 3d.检验服务访问规则(只检查时间)
		// 3e.检验参数是否符合服务的标准
		// 4.根据服务编号获取服务详细信息
		// 5.解析服务要执行的SQL
		// 6.执行SQL
		// 7.获取执行SQL的结果集，并封装成Map格式
		// 8.检验规则，访问次数，访问条数
		// 9.记录日志
		//System.out.println("--------query begin--------param="+param);
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 读取服务器当前年月日时分秒
		Long start = System.currentTimeMillis(); // 开始时间用于计算耗时
		int batch = (int) Math.round(Math.random() * 1000000); // 每一次访问时建立一个批次号，用来标识同一批的日志
		param.put("batch", batch);
		logger.debug("当前时间为：" + startTime + "开始调用query服务，其日志批次号为：" + batch
				+ "..."); // Batch

		String ip = this.getClientInfo(param);
		logger.debug("访问的ip为：" + ip + " 其日志批次号为：" + batch);

		ShareLogVo shareLogVo = new ShareLogVo();// 共享日志记录
		shareLogVo.setAccess_ip(ip); // 日志记录客户端IP
		shareLogVo.setService_start_time(startTime); // 日志记录服务开始时间

		if (param.isEmpty())
			shareLogVo.setPatameter(""); // 日志记录传入参数
		else
			shareLogVo.setPatameter(param.toString()); // 日志记录传入参数
		logger.debug("传入的参数为:" + param + " 其日志批次号为：" + batch);
		
		try {
			if (null != param.get(ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
					&& !"".equals(param.get(
							ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
							.toString())) {// 判断SVR_CODE是否为空

				String userName = ""; // 用户名
				String serviceCode = ""; // 定位的唯一一条服务的服务编号(为真正访问的服务编号)

				serviceCode = param.get(
						ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
						.toString(); // 服务编号
				logger.debug("服务编号为:" + serviceCode + " 其日志批次号为：" + batch);

				if (null != param.get("LOGIN_NAME")) {
					userName = param.get("LOGIN_NAME").toString();
				}
				Map teMap = queryServiceByCode(serviceCode, userName); // 查询唯一的一条服务
				if (!teMap.isEmpty()) {
					ServiceVo serviceVo = new ServiceVo();
					ParamUtil.mapToBean(teMap, serviceVo, false);

					logger.debug("服务id为：" + serviceVo.getService_id()
							+ " 其日志批次号为：" + batch);
					logger.debug("定位的唯一的服务编号为：" + serviceCode + " 其日志批次号为："
							+ batch);

					shareLogVo.setService_no(serviceCode);
					shareLogVo.setRecord_start(String.valueOf(param
							.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS))); // 开始记录数
					shareLogVo.setRecord_end(String.valueOf(param
							.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS))); // 结束记录数
					logger.debug("开始检验服务" + " 其日志批次号为：" + batch + "...");

					// 校验服务是否能被使用
					CheckService checkService = new CheckService();
					Map checkMap = checkService.checkServiceCanBeUsed(
							serviceVo, param, shareLogVo);
					logger.debug("检验服务完毕" + " 其日志批次号为：" + batch + "...");
					logger.debug("检验结果为:" + checkMap + " 其日志批次号为：" + batch);

					// 如果检验失败则将返回代码放在服务日志中
					if (checkMap
							.containsKey(ShareConstants.SERVICE_OUT_PARAM_FHDM))
						shareLogVo.setReturn_codes(String.valueOf(checkMap
								.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));

					if ("Y".equals(checkMap.get(
							ShareConstants.SERVICE_CAN_BE_USED).toString())) {
						// 服务能使用
						param.put("SERVICE_ID", serviceVo.getService_id());
						param = this.queryService(serviceVo.getService_id(),
								param); // 获得服务详细信息

						Long end = System.currentTimeMillis();
						String consumeTime = String
								.valueOf(((end - start) / 1000f));
						logger.debug("准备调用webservices服务操作，共耗时：" + consumeTime
								+ "秒！" + " 其日志批次号为：" + batch);

						GeneralService service = ServiceFactory.getService();// 获得服务
						Map result = service.query(param, shareLogVo); // 调用服务返回结果
						return result;

					} else {
						// 服务不能使用
						logger.debug("服务检验失败不能访问,其日志批次号为：" + batch + "...");
						checkMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
						return checkMap;
					}
				} else {
					logger.debug("未找到服务！服务编号不存在或用户名不正确 ,其日志批次号为：" + batch + "...");
					Map excpMap = ResultParser.createSvrNotFoundMap();// 未找到服务
					excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
					shareLogVo.setReturn_codes(String.valueOf(excpMap
							.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));// 日志记录返回代码
					return excpMap;
				}
			} else {
				logger.debug("参数错误!未设置服务编号,其日志批次号为：" + batch + "...");
				Map excpMap = ResultParser.createParamErrorMap();// 参数错误
				excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
				shareLogVo.setReturn_codes(String.valueOf(excpMap
						.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));// 日志记录返回代码
				return excpMap;
			}
		} catch (Exception e) {
			logger.debug("其日志批次号为：" + batch + "系统错误,:" + e);
			e.printStackTrace();
			Map excpMap = ResultParser.createSystemErrorMap();// 报错返回的结果
			// 如果报错则将返回代码放在服务日志中
			if (excpMap.containsKey(ShareConstants.SERVICE_OUT_PARAM_FHDM))
				shareLogVo.setReturn_codes(String.valueOf(excpMap
						.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));
			return excpMap;
		} finally {
			// 计算服务耗时
			Long end = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("调用服务操作，共耗时：" + consumeTime + "秒！" + " 其日志批次号为："
					+ batch);
			// 记录日志
			String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
			shareLogVo.setService_end_time(endTime); // 服务结束时间
			shareLogVo.setConsume_time(consumeTime); // 服务消耗时间
			this.insertLog(shareLogVo);
			logger.debug("记录日志成功 其日志批次号为：" + batch + "...");
		}
	}
	
//	/**
//	 * 
//	 * @param param
//	 */
//	private boolean checkVerify(Map param){
//		//System.out.println("--------checkVerify----------");
//		logger.info("进入验证服务是否要特殊的类验证");
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
//				logger.info("校验结果为"+result);
//			}else{
//				logger.info("LOGIN_NAME为空");
//				return true;
//			}
//        } catch (Exception e) {
//			//e.printStackTrace();
//        	//System.out.println("未找到"+userName+"对应的校验处理类");
//        	logger.info("未找到"+userName+"对应的校验处理类---"+e.getMessage());
//			return true;
//		}		
//		return result;
//	}
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
	public String queryData(String xml) throws IOException, DBException
	{
		// 业务逻辑
		// 1.获取调用服务的参数，解析参数
		// 2判断服务编号是否存在
		// 2a.若不存在返回对应的错误编码
		// 2b.若存在go on
		// 2.解析参数中的服务编号，确定当前要调用哪个服务
		// 3.校验要访问的服务是否能被访问，如果是测试用户不用检验
		// 3a.检验用户名，密码是否正确，ip是否正确
		// 3b.检验服务状态，该服务能否被访问
		// 3c.检验例外时间，是否是工作日
		// 3d.检验服务访问规则(只检查时间)
		// 3e.检验参数是否符合服务的标准
		// 4.根据服务编号获取服务详细信息
		// 5.解析服务要执行的SQL
		// 6.执行SQL
		// 7.获取执行SQL的结果集，并封装成XML格式
		// 8.检验规则，访问次数，访问条数
		// 9.记录日志

		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 读取服务器当前年月日时分秒
		Long start = System.currentTimeMillis(); // 开始时间用于计算耗时
		int batch = (int) Math.round(Math.random() * 1000000); // 每一次访问时建立一个批次号，用来标识同一批的日志
		logger.debug("当前时间为：" + startTime + "开始调用queryData服务,其日志批次号为：" + batch
				+ "...");
		ShareLogVo shareLogVo = new ShareLogVo();// 共享日志记录
		shareLogVo.setService_start_time(startTime); // 日志记录服务开始时间
		shareLogVo.setPatameter(xml); // 日志记录传入参数

		try {
			logger.debug("传入的参数为:" + xml + " 其日志批次号为：" + batch);
			Map param = XmlToMapUtil.dom2Map(xml);// 将参数转化成Map
			param.put("batch", batch);

			String ip = this.getClientInfo(param); // 获取客户端IP
			shareLogVo.setAccess_ip(ip);// 日志记录客户端IP
			logger.debug("访问的ip为：" + ip + " 其日志批次号为：" + batch);

			// 判断SVR_CODE是否为空
			if (null != param.get(ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
					&& !"".equals(param.get(
							ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
							.toString())) {

				String userName = ""; // 用户名
				String serviceCode = ""; // 定位的唯一一条服务的服务编号(为真正访问的服务编号)

				serviceCode = param.get(
						ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
						.toString(); // 服务编号
				logger.debug("服务编号为:" + serviceCode + " 其日志批次号为：" + batch);

				if (null != param.get("LOGIN_NAME")) {
					userName = param.get("LOGIN_NAME").toString();					
//					if(!checkVerify(param)){
//						Map rsMap=ResultParser.createVerifyErrorMap();
//						shareLogVo.setReturn_codes(String.valueOf(rsMap
//								.get(ShareConstants.SERVICE_FHDM_VERIFY_ERROR))); // 日志记录返回代码
//						String result = XmlToMapUtil.map2Dom(rsMap);
//						return result;
//					}
					
				}
				
				Map teMap = queryServiceByCode(serviceCode, userName); // 查询唯一的一条服务
				if (!teMap.isEmpty()) {
					ServiceVo serviceVo = new ServiceVo();
					ParamUtil.mapToBean(teMap, serviceVo, false);

					logger.debug("服务id为：" + serviceVo.getService_id()
							+ " 其日志批次号为：" + batch);
					logger.debug("定位的唯一的服务编号为：" + serviceCode + " 其日志批次号为："
							+ batch);

					shareLogVo.setService_no(serviceCode);
					shareLogVo.setRecord_start(String.valueOf(param
							.get(ShareConstants.SERVICE_OUT_PARAM_KSJLS))); // 日志记录开始记录数
					shareLogVo.setRecord_end(String.valueOf(param
							.get(ShareConstants.SERVICE_OUT_PARAM_JSJLS))); // 日志记录结束记录数
					logger.debug("开始检验服务 其日志批次号为：" + batch + "...");
					// 校验服务是否能被使用
					CheckService checkService = new CheckService();
					Map checkMap = checkService.checkServiceCanBeUsed(
							serviceVo, param, shareLogVo);
					logger.debug("检验服务完毕" + " 其日志批次号为：" + batch + "...");
					logger.debug("检验结果为:" + checkMap + " 其日志批次号为：" + batch);
					// 如果检验失败则将返回代码放在服务日志中
					if (checkMap
							.containsKey(ShareConstants.SERVICE_OUT_PARAM_FHDM))
						shareLogVo.setReturn_codes(String.valueOf(checkMap
								.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));
					if ("Y".equals(checkMap.get(
							ShareConstants.SERVICE_CAN_BE_USED).toString())) {
						// 服务能使用
						param.put("SERVICE_ID", serviceVo.getService_id());
						param = this.queryService(serviceVo.getService_id(),
								param); // 获得服务详细信息

						Long end = System.currentTimeMillis();
						String consumeTime = String
								.valueOf(((end - start) / 1000f));
						logger.debug("准备调用webservices服务操作，共耗时：" + consumeTime
								+ "秒！" + " 其日志批次号为：" + batch);
						GeneralService service = ServiceFactory.getService();// 获得服务
						String result = service.queryData(param, shareLogVo); // 获取服务结果
						return result;// 返回结果

					} else {
						logger.debug("服务检验失败不能访问 其日志批次号为：" + batch + "...");// 服务不能使用
						checkMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
						String result = XmlToMapUtil.map2Dom(checkMap);
						return result;
					}
				} else {
					logger.debug("服务编号不存在未找到服务...");
					Map excpMap = ResultParser.createSvrNotFoundMap();// 未找到服务
					excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
					shareLogVo.setReturn_codes(String.valueOf(excpMap
							.get(ShareConstants.SERVICE_OUT_PARAM_FHDM))); // 日志记录返回代码
					String result = XmlToMapUtil.map2Dom(excpMap);
					return result;
				}
			} else {
				logger.debug("服务编号不存在未找到服务 其日志批次号为：" + batch + "...");
				Map excpMap = ResultParser.createSvrNotFoundMap();// 未找到服务
				excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
				shareLogVo.setReturn_codes(String.valueOf(excpMap
						.get(ShareConstants.SERVICE_OUT_PARAM_FHDM))); // 日志记录返回代码
				String result = XmlToMapUtil.map2Dom(excpMap);
				return result;
			}
		} catch (DBException e) {
			logger.debug("日志批次号为：" + batch + "系统错误:" + e);
			e.printStackTrace();
			Map excpMap = ResultParser.createSystemErrorMap();// 报错返回的结果
			shareLogVo.setReturn_codes(String.valueOf(excpMap
					.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));// 日志记录返回代码
			String result = XmlToMapUtil.map2Dom(excpMap);
			return result;
		} finally {
			// 计算服务耗时
			Long end = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("调用服务操作，总共耗时：" + consumeTime + "秒！" + " 其日志批次号为："
					+ batch);
			// 记录日志
			String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
			shareLogVo.setService_end_time(endTime); // 服务结束时间
			shareLogVo.setConsume_time(consumeTime); // 服务消耗时间
			this.insertLog(shareLogVo);
			logger.debug("记录日志成功 其日志批次号为：" + batch + "...");
		}
	}

	/**
	 * 
	 * queryTrsData(trs返回查询结果) 
	 * 
	 * @param xml
	 * @return
	 * @throws IOException
	 * @throws DBException
	 *             String
	 * @throws TxnException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String queryTrsData(String xml) throws IOException, DBException,
			TxnException
	{
		String ip = this.getClientInfo(null); // 获取客户端IP
		return this.queryTrsResult(xml, ip);
	}

	/**
	 * 
	 * Servlet方式调用trs方式的方法
	 * 
	 * @return 返回xml格式结果
	 * @throws TxnException
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public String queryTrsDataByHttp(String xml, String ip) throws IOException,
			DBException, TxnException
	{
		return this.queryTrsResult(xml, ip);
	}

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
	// 5.解析服务要执行的查询字符串
	// 6.查询TRS数据库
	// 7.获取查询结果集，并封装成xml格式
	// 8.将xml文件存放在服务上
	// 9.记录日志
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String queryTrsResult(String xml, String ip) throws IOException
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String startTime = sDateFormat.format(new java.util.Date()); // 读取服务器当前年月日时分秒
		logger.debug("当前时间为：" + startTime + "开始调用queryTrsData服务...");
		ShareLogVo shareLogVo = new ShareLogVo();// 共享日志记录
		Long start = System.currentTimeMillis(); // 开始时间用于计算耗时
		shareLogVo.setService_start_time(startTime); // 日志记录服务开始时间
		shareLogVo.setPatameter(xml); // 日志记录传入参数
		Map param = null;
		try {
			logger.debug("传入的参数为:" + xml);
			// System.out.println("传入的参数为:" + xml);
			param = XmlToMapUtil.dom2Map(xml);// 将参数转化成Map
			logger.debug("访问的ip为：" + ip);
			shareLogVo.setAccess_ip(ip);// 日志记录客户端IP

			// 判断输入参数是否合理
			if (param.get("queryStr") == null
					|| param.get("queryStr").equals("")) {
				Map excpMap = ResultParser.createParamErrorMap();//
				shareLogVo.setReturn_codes(excpMap.get(
						ShareConstants.SERVICE_OUT_PARAM_FHDM).toString());
				String result = XmlToMapUtil.map2Dom(excpMap);
				return result;
			}

			// 判断SVR_CODE是否为空
			if (null != param.get(ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
					&& !"".equals(param.get(
							ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
							.toString())) {

				String oldServiceCode = param.get(
						ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE)
						.toString();
				logger.debug("老服务编号为:" + oldServiceCode);

				String serviceId = ""; // 定位的唯一一条服务的服务ID
				String serviceCode = ""; // 定位的唯一一条服务的服务编号(为真正访问的服务编号)

				Map teMap = queryTrsSerCode(oldServiceCode); // 查询唯一的一条服务

				serviceCode = teMap.get("SERVICE_NO").toString();
				serviceId = teMap.get("SERVICE_ID").toString();
				logger.debug("服务id为：" + serviceId);
				logger.debug("定位的唯一的服务编号为：" + serviceCode);

				shareLogVo.setService_no(serviceCode);

				logger.debug("开始检验服务...");
				// 校验服务是否能被使用
				CheckService checkService = new CheckService();
				// 检验时取出了值，直接用不用再取第二次了
				Map checkMap = checkService.checkTrsServiceCanBeUsed(
						serviceCode, param, shareLogVo, serviceId);
				logger.debug("检验服务完毕...");
				logger.debug("检验结果为:" + checkMap);
				// 如果检验失败则将返回代码放在服务日志中
				if (checkMap.containsKey(ShareConstants.SERVICE_OUT_PARAM_FHDM))
					shareLogVo.setReturn_codes(String.valueOf(checkMap
							.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));
				if ("Y".equals(checkMap.get(ShareConstants.SERVICE_CAN_BE_USED)
						.toString())) {
					ServiceVo serviceVo = new ServiceVo();
					serviceVo.setService_id(serviceId);

					// 验证时间段的规则
					Map dateRuleMap = checkService.checkDateRule(serviceVo);
					if ("Y".equals(dateRuleMap.get(
							ShareConstants.SERVICE_CAN_BE_USED).toString())) {
						// param = this.queryTrsService(serviceId, param);//
						// 获得服务详细信息在上面校验时获取了
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
						logger.debug("准备调用webservices服务操作，共耗时：" + consumeTime
								+ "秒！");
						GeneralService service = ServiceFactory.getService();// 获得服务
						String result = service.queryTrsData(param, shareLogVo); // 获取服务结果
						// this.inputFile(result, serviceCode);// 生成文件
						return result;// 返回结果

					} else {
						shareLogVo.setReturn_codes(dateRuleMap.get(
								ShareConstants.SERVICE_OUT_PARAM_FHDM)
								.toString());
						shareLogVo.setRecord_amount("0");
						logger.debug("服务时间规则检验失败不能访问...");// 服务不能使用
						dateRuleMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
						String result = XmlToMapUtil.map2Dom(dateRuleMap);
						return result;
					}

				} else {
					logger.debug("服务检验失败不能访问...");// 服务不能使用
					checkMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
					String result = XmlToMapUtil.map2Dom(checkMap);
					// this.inputFile(result, serviceCode);// 生成文件
					return result;
				}

			} else {
				logger.debug("服务编号不存在未找到服务...");
				Map excpMap = ResultParser.createSvrNotFoundMap();// 未找到服务
				excpMap.remove(ShareConstants.SERVICE_CAN_BE_USED);
				shareLogVo.setReturn_codes(String.valueOf(excpMap
						.get(ShareConstants.SERVICE_OUT_PARAM_FHDM))); // 日志记录返回代码
				String result = XmlToMapUtil.map2Dom(excpMap);
				// this.inputFile(result, "");// 生成文件
				return result;
			}
		} catch (Exception e) {
			logger.debug("系统错误:" + e.getMessage());
			e.printStackTrace();
			Map excpMap = ResultParser.createSystemErrorMap();// 报错返回的结果
			shareLogVo.setReturn_codes(String.valueOf(excpMap
					.get(ShareConstants.SERVICE_OUT_PARAM_FHDM)));// 日志记录返回代码
			String result = XmlToMapUtil.map2Dom(excpMap);
			// this.inputFile(result, "");// 生成文件
			return result;
		} finally {
			// 计算服务耗时
			Long end = System.currentTimeMillis(); // 结束时间用于计算耗时
			String consumeTime = String.valueOf(((end - start) / 1000f));
			logger.debug("调用服务操作，总共耗时：" + consumeTime + "秒！");
			// 记录日志
			String endTime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
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
			shareLogVo.setService_end_time(endTime); // 服务结束时间
			shareLogVo.setConsume_time(consumeTime); // 服务消耗时间
			//shareLogVo.setLog_type(ExConstant.LOG_TYPE_TEST);
			this.insertLog(shareLogVo);
			logger.debug("记录日志成功...");
		}
	}

	/**
	 * 
	 * queryTrsSerCode 查询服务编码
	 * 
	 * @param serviceCode
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public Map queryTrsSerCode(String serviceCode)
	{
		String sql = SQLHelper.queryTrsSerCodeSql(serviceCode);
		Map teMap = new HashMap();
		try {
			teMap = dao.queryTrsSerCode(sql);
		} catch (DBException e) {
			logger.debug("查询老服务编码报错..." + e);
			e.printStackTrace();
		}
		return teMap;
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
		
		ParamAnalyzer paramAnalyzer = new ParamAnalyzer(param);
		
		// 解析查询的SQL
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
				&& ("TEST".equals(param.get("USER_TYPE"))
				|| "SHARE_FTP".equals(param.get("USER_TYPE")))) {
			// 如果是测试接口或者是共享FTP调用则不用走此方法，不用获取IP
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
