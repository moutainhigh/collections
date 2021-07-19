package com.gwssi.socket.share.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.log.sharelog.dao.ShareLogVo;
import com.gwssi.resource.svrobj.vo.VoResServiceTargets;
import com.gwssi.share.rule.vo.VoShareServiceRule;
import com.gwssi.share.service.vo.VoShareService;
import com.gwssi.share.service.vo.VoShareServiceCondition;

/**
 * 
 * 
 * 项目名称：bjgs_exchange 类名称：CheckService 类描述：检验服务信息 创建人：lizheng 创建时间：Apr 27, 2013
 * 4:44:47 PM 修改人：lizheng 修改时间：Apr 27, 2013 4:44:47 PM 修改备注：
 * 
 * @version
 * 
 */
public class CheckService
{
	ServiceDAO	dao	= null; // 操作数据库Dao

	public CheckService()
	{
		dao = new ServiceDAOImpl();
	}

	// 日志
	protected static Logger	logger	= TxnLogger.getLogger(CheckService.class
											.getName());

	/**
	 * 
	 * checkServiceCanBeUsed 检查服务是否能使用
	 * 
	 * @param serviceCode
	 * @param param
	 * @param shareLogVo
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected Map checkServiceCanBeUsed(String serviceCode, Map param,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		Long start = System.currentTimeMillis();
		// 判断是否是测试用户
		if (param.containsKey("USER_TYPE")
				&& "TEST".equals(param.get("USER_TYPE"))) {
			logger.debug("该用户为测试用户不用检验...");

			// 设置记录日志信息
			shareLogVo.setLog_type(ExConstant.LOG_TYPE_TEST);
			this.queryLoginInfoTest(serviceCode, param, shareLogVo, serviceId);
			this.queryServiceStateTest(serviceCode, shareLogVo);
			return ResultParser.createSuccuseMap();
		} else {
			logger.debug("该用户要检验...");
			// 设置记录日志信息
			shareLogVo.setLog_type(ExConstant.LOG_TYPE_USER);
			// 1.检查服务对象登录信息是否正确,ip绑定是否正确
			Map loginMap = this.queryLoginInfo(serviceCode, param, shareLogVo,
					serviceId);
			// 2.检查服务,服务状态是否正确
			Map serStateMap = this.queryServiceState(serviceCode, shareLogVo);
			if ("Y".equals(loginMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				if ("Y".equals(serStateMap.get(
						ShareConstants.SERVICE_CAN_BE_USED).toString())) {
					// 3.检查例外时间是否正确
					Map serDateMap = this.querySvrDate(DateUtil.getToday());
					if ("Y".equals(serDateMap.get(
							ShareConstants.SERVICE_CAN_BE_USED).toString())) {
						// 4.检查服务规则是否正确
						try {
							Map serRuleMap = this.queryServiceRule(serviceCode);
							if ("Y".equals(serDateMap.get(
									ShareConstants.SERVICE_CAN_BE_USED)
									.toString())) {
								// 5.检查参数是否正确
								Map paraMap = this.checkParam(serviceId, param);
								return paraMap;
							} else {
								return serRuleMap;
							}
						} catch (ParseException e) {
							e.printStackTrace();
							return ResultParser.createSystemErrorMap();
						}
					} else {
						return serDateMap;
					}
				} else {
					return serStateMap;
				}
			} else {
				return loginMap;
			}
		}
	}

	/**
	 * 
	 * queryLoginInfo 检查服务对象登录信息是否正确
	 * 
	 * @param serviceCode
	 * @param param
	 * @param shareLogVo
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected Map queryLoginInfo(String serviceCode, Map param,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		logger.debug("开始检验用户信息...");
		// 获取访问服务的用户信息
		String sql = SQLHelper.getLoginSQL(serviceId);
		Map loginMap = dao.queryLoginInfo(sql);

		if (loginMap.isEmpty()) {
			logger.debug("用户无效...");
			return ResultParser.createUserFailMap();
		} else {
			VoResServiceTargets v = new VoResServiceTargets();
			ParamUtil.mapToBean(loginMap, v, false);

			String svrName = param.get("LOGIN_NAME").toString();
			String svrPwd = param.get("PASSWORD").toString();
			String svrStatus = v.getService_status();
			logger.debug("用户名为:" + svrName);
			logger.debug("密码为:" + svrPwd);
			logger.debug("用户有效状态为:" + svrStatus);

			// 设置记录日志信息
			shareLogVo.setService_targets_id(v.getService_targets_id()); // 服务对象ID
			shareLogVo.setService_targets_name(v.getService_targets_name()); // 服务对象名称
			shareLogVo.setTargets_type(v.getService_targets_type()); // 服务对象类型
			String serviceStatus = v.getService_status();

			if (ExConstant.IS_MARKUP_Y.equals(serviceStatus)) {
				// 判断用户信息
				if (ExConstant.IS_MARKUP_Y.equals(svrStatus)) {
					if (StringUtils.isBlank(svrName)
							|| !svrName.equals(v.getService_targets_no())) {
						logger.debug("用户名错误...");
						return ResultParser.createUserErrorlMap();
					} else if (StringUtils.isBlank(svrPwd)
							|| !v.getService_password().equals(svrPwd)) {
						logger.debug("密码错误...");
						return ResultParser.createPwdErrorlMap();
					} else if (svrName.equals(v.getService_targets_no())
							&& svrPwd.equals(v.getService_password())) {
						logger.debug("用户名密码检验成功...");
						return ResultParser.createSuccuseMap();
					} else if (!queryServiceIPInfo(v, shareLogVo)) {
						logger.debug("IP错误...");
						return ResultParser.createServiceIPFailMap();
					} else {
						logger.debug("用户错误...");
						return ResultParser.createLoginFailMap();
					}
				} else {
					logger.debug("用户无效...");
					return ResultParser.createUserFailMap();
				}
			} else {
				logger.debug("用户无效...");
				return ResultParser.createUserFailMap();
			}

		}
	}

	/**
	 * 
	 * queryLoginInfoTest 服务对象登录信息
	 * 
	 * @param serviceCode
	 * @param param
	 * @param shareLogVo
	 * @throws DBException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected void queryLoginInfoTest(String serviceCode, Map param,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		logger.debug("开始检验用户信息...");
		// 获取访问服务的用户信息
		String sql = SQLHelper.getLoginSQL(serviceCode);
		Map loginMap = dao.queryLoginInfo(sql);

		VoResServiceTargets v = new VoResServiceTargets();
		ParamUtil.mapToBean(loginMap, v, false);

		// 设置记录日志信息
		shareLogVo.setService_targets_id(v.getService_targets_id()); // 服务对象ID
		shareLogVo.setService_targets_name(v.getService_targets_name()); // 服务对象名称
		shareLogVo.setTargets_type(v.getService_targets_type()); // 服务对象类型

	}

	/**
	 * 
	 * queryServiceIPInfo 检验ip绑定是否正确
	 * 
	 * @param v
	 * @param shareLogVo
	 * @return
	 * @throws DBException
	 *             boolean
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected boolean queryServiceIPInfo(VoResServiceTargets v,
			ShareLogVo shareLogVo) throws DBException
	{
		logger.debug("开始检验IP信息...");
		boolean ipFlag = false;
		String isBindIP = v.getIs_bind_ip();
		if (StringUtils.isNotBlank(isBindIP) // 如果绑定ip
				&& ExConstant.IS_BIND_IP_Y.equals(isBindIP)) {
			String clientIP = getClientIp();
			String[] ips = v.getIp().split(",");
			for (int ii = 0; ii < ips.length; ii++) {
				if (clientIP.equals(ips[ii])) {
					ipFlag = true;
					logger.debug("检验IP完毕...");
					break; // ip检查通过
				}
			}
		} else {
			logger.debug("没有绑定ip,检验IP完毕...");
			ipFlag = true; // 没有绑定ip
		}
		return ipFlag;
	}

	/**
	 * 
	 * queryServiceState 检验服务状态
	 * 
	 * @param serviceCode
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected Map queryServiceState(String serviceCode, ShareLogVo shareLogVo)
			throws DBException
	{
		logger.debug("开始检验服务状态...");
		// 获取服务状态
		String sql = SQLHelper.queryServiceSQL(serviceCode);
		Map svrStateMap = dao.queryServiceState(sql);

		if (svrStateMap.isEmpty()) {
			logger.debug("没有找到该服务...");
			return ResultParser.createSvrNotFoundMap();
		} else {
			VoShareService v = new VoShareService();
			ParamUtil.mapToBean(svrStateMap, v, false);
			// 设置记录日志信息
			shareLogVo.setService_id(v.getService_id()); // 服务ID
			shareLogVo.setService_name(v.getService_name()); // 服务名称
			shareLogVo.setService_type(v.getService_type()); // 服务类型
			if (ExConstant.IS_MARKUP_Y.equals(v.getIs_markup())
					&& ExConstant.SERVICE_STATE_Y.equals(v.getService_state())) {
				// 说明此服务能够提供服务
				logger.debug("该服务正常...");
				return ResultParser.createSuccuseMap();
			} else {
				logger.debug("该服务状态为不可访问...");
				return ResultParser.createServiceStateFailMap();
			}
		}
	}

	/**
	 * 
	 * queryServiceStateTest 服务状态
	 * 
	 * @param serviceCode
	 * @param shareLogVo
	 * @throws DBException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected void queryServiceStateTest(String serviceCode,
			ShareLogVo shareLogVo) throws DBException
	{
		// 获取服务状态
		String sql = SQLHelper.queryServiceSQL(serviceCode);
		Map svrStateMap = dao.queryServiceState(sql);

		VoShareService v = new VoShareService();
		ParamUtil.mapToBean(svrStateMap, v, false);

		// 设置记录日志信息
		shareLogVo.setService_id(v.getService_id()); // 服务ID
		shareLogVo.setService_name(v.getService_name()); // 服务名称
		shareLogVo.setService_type(v.getService_type()); // 服务类型

	}

	/**
	 * 
	 * querySvrDate 检查例外时间
	 * 
	 * @param svrDate
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected Map querySvrDate(String svrDate) throws DBException
	{
		logger.debug("开始检查例外时间...");
		if (StringUtils.isNotEmpty(svrDate)) {
			String sql = SQLHelper.getServiceDateSQL(svrDate);
			String dateString = dao.queryServiceDate(sql);

			if (StringUtils.isNotEmpty(dateString) && "0".equals(dateString)) {
				// 在例外日期表中没有查到当日记录,说明该日服务可正常访问
				logger.debug("今天为工作日能正常访问服务...");
				return ResultParser.createSuccuseMap();
			} else {
				logger.debug("今天为放假服停止服务...");
				return ResultParser.createServiceDateFailMap();
			}
		} else {
			logger.debug("例外时间有BUG可能未设置例外时间...");
			return ResultParser.createServiceDateFailMap();
		}

	}

	/**
	 * 
	 * queryServiceRule 查询服务访问规则
	 * 
	 * @param serviceCode
	 * @return
	 * @throws DBException
	 * @throws ParseException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected Map queryServiceRule(String serviceCode) throws DBException,
			ParseException
	{
		logger.debug("开始服务访问规则...");
		String sql = SQLHelper.getServiceRuleSQL(serviceCode);
		Map svrRuleMap = dao.queryServiceRule(sql);

		if (svrRuleMap.isEmpty()) {
			logger.debug("该服务未配置访问规则，可随便访问...");
			return ResultParser.createSuccuseMap();
		} else {
			// 配置了访问规则，检查访问规则
			VoShareServiceRule v = new VoShareServiceRule();
			ParamUtil.mapToBean(svrRuleMap, v, false);

			String startTime = v.getStart_time(); // 服务访问开始时间
			String endTime = v.getEnd_time(); // 服务访问结束时间

			if (StringUtils.isNotEmpty(startTime)
					&& StringUtils.isNotEmpty(endTime)) {

				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				String calNow = sd.format(new java.util.Date()); // 系统当前年月日
				startTime = calNow + " " + startTime + ":00";
				endTime = calNow + " " + endTime + ":00";

				SimpleDateFormat sDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String calNowtime = sDateFormat.format(new java.util.Date()); // 系统当前年月日时分秒
				boolean isIn = isInDates(calNowtime.toString(), startTime,
						endTime);// 比较系统当前时间和共享服务开始时间

				logger.debug("服务允许访问开始时间为：" + startTime);
				logger.debug("当前系统时间为：" + calNowtime);
				logger.debug("服务允许访问结束时间为：" + endTime);

				if (isIn) {
					// 在允许访问的时间内
					logger.debug("在允许访问的时间内...");
					sql = SQLHelper.getServiceLogSQL(serviceCode);
					Map logMap = dao.queryServiceLog(sql); // 查询当天访问了多少次

					if (logMap.isEmpty()) {
						logger.debug("该服务今天还没有被访问过...");
						return ResultParser.createSuccuseMap();
					} else {
						int times = Integer.parseInt(logMap.get("TIMES")
								.toString());
						logger.debug("该服务今天被访问过[" + times + "]次...");

						int amount = Integer.parseInt(logMap.get("AMOUNT")
								.toString());
						logger.debug("该服务今天共获取数据[" + amount + "]条...");

						int times_day = Integer
								.parseInt(v.getTimes_day() == null ? "0" : v
										.getTimes_day());
						logger.debug("该服务今天允许访问[" + times_day
								+ "]次,如果是0则说明没有配置次数规则...");
						if (times < times_day || times_day == 0) {

							int amount_day = Integer.parseInt(v
									.getTotal_count_day() == null ? "0" : v
									.getTotal_count_day());
							logger.debug("该服务今天共获取数据[" + amount
									+ "]条,如果是0则说明没有配置条数数规则...");

							if (amount < amount_day || amount_day == 0) {
								// 总数小于配置的规则总数
								logger.debug("检验访问规则通过...");
								return ResultParser.createSuccuseMap();
							} else {
								logger.debug("超过今天允许访问条数...");
								return ResultParser.createSvrTotalErrorMap();
							}
						} else {
							logger.debug("超过今天允许访问次数...");
							return ResultParser.createSvrNumErrorMap();
						}
					}
				} else {
					logger.debug("当前时间不在服务允许访问时间内：" + startTime);
					return ResultParser.createSvrTimeErrorMap();
				}
			} else {
				logger.debug("系统错误...");
				return ResultParser.createSystemErrorMap();
			}
		}
	}

	/**
	 * 
	 * getClientIp 获取客户端IP
	 * 
	 * @return String
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private String getClientIp()
	{
		MessageContext mc = MessageContext.getCurrentContext();
		HttpServletRequest request = (HttpServletRequest) mc
				.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
		String clientIP = request.getHeader("x-forwarded-for");
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
		return clientIP;
	}

	/**
	 * 
	 * isInDates 判断时间是否在一个时间区域内
	 * 
	 * @param strDate
	 * @param strDateBegin
	 * @param strDateEnd
	 * @return boolean
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private boolean isInDates(String strDate, String strDateBegin,
			String strDateEnd)
	{
		// 大于或者等于开始时间
		// 并且
		// 小于或者等于结束时间
		java.text.DateFormat df = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		java.util.Calendar cBegin = java.util.Calendar.getInstance();
		java.util.Calendar cDate = java.util.Calendar.getInstance();
		java.util.Calendar cEnd = java.util.Calendar.getInstance();
		try {
			cBegin.setTime(df.parse(strDateBegin));
			cDate.setTime(df.parse(strDate));
			cEnd.setTime(df.parse(strDateEnd));
		} catch (java.text.ParseException e) {
			logger.debug("时间格式不正确！");
		}
		int intCompareBegin = cDate.compareTo(cBegin);
		int intCompareEnd = cDate.compareTo(cEnd);
		if ((intCompareBegin > 0 || intCompareBegin == 0)
				&& (intCompareEnd < 0 || intCompareEnd == 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * checkParam 校验参数
	 * 
	 * @param paraMap
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private Map checkParam(String serviceId, Map paraMap)
	{
		logger.debug("校验参数...");
		// 1.校验所有的参数是否都有
		// 2.校验时间参数是否格式都正确
		// 3.校验是否有只取本月数据限制
		// 4.检验是否有时间跨度限制
		boolean flag = true;
		if (!paraMap.containsKey(ShareConstants.SERVICE_OUT_PARAM_KSJLS)) {
			paraMap.put(ShareConstants.SERVICE_OUT_PARAM_KSJLS,
					ShareConstants.SERVICE_DEFAULT_STAR_RECORDS);
		}
		if (!paraMap.containsKey(ShareConstants.SERVICE_OUT_PARAM_JSJLS)) {
			paraMap.put(ShareConstants.SERVICE_OUT_PARAM_JSJLS,
					ShareConstants.SERVICE_DEFAULT_MAX_RECORDS);
		}
		List paraList = new ArrayList();
		paraList.add(ShareConstants.SERVICE_IN_PARAM_SERVICE_CODE);
		paraList.add(ShareConstants.SERVICE_IN_PARAM_LOGIN_NAME);
		paraList.add(ShareConstants.SERVICE_IN_PARAM_LOGIN_PASSWORD);
		List temList = this.queryParam(serviceId);
		List timeParamList = new ArrayList();
		for (int i = 0; i < temList.size(); i++) {
			Map m = (Map) temList.get(i);
			VoShareServiceCondition v = new VoShareServiceCondition();
			ParamUtil.mapToBean(m, v, false);
			if ("2".equals(v.getParam_type())) {
				if (!timeParamList.contains(v.getColumn_name_en()))
					timeParamList.add(v.getColumn_name_en());
			}
		}
		logger.debug("timeParamList is " + timeParamList);
		for (int i = 0; i < paraList.size(); i++) {
			if (!paraMap.containsKey(paraList.get(i))) {
				flag = false;
				logger.debug("参数错误...");
				break;
			} else {
				if (null == paraMap.get(paraList.get(i))) {
					flag = false;
					logger.debug("参数错误...");
					break;
				}
			}
		}
		if (flag) {
			Map tepMap = checkTime(serviceId, timeParamList, paraMap);
			return tepMap;
		} else {
			logger.debug("参数错误...");
			Map tepMap = ResultParser.createParamErrorMap();
			tepMap.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
			return tepMap;
		}
	}

	private Map checkTime(String serviceId, List timeParamList, Map paraMap)
	{
		boolean flag = true; // 判断参数
		boolean flag1 = true; // 判断当月
		Map srvMap = queryServiceById(serviceId);
		VoShareService v = new VoShareService();
		ParamUtil.mapToBean(srvMap, v, false);
		logger.debug("v.getIs_month_data() is " + v.getIs_month_data());
		// 判断日期是否符合标准
		for (int i = 0; i < timeParamList.size(); i++) {
			String param = "";
			if (null != paraMap.get(timeParamList.get(i))) {
				param = paraMap.get(timeParamList.get(i)).toString();
				int split = param.indexOf(",");
				if (split > 0) {
					String startTime = param.substring(0, split);
					flag = matcherTime(startTime);
					String endTime = param.substring(split + 1, param.length());
					flag = matcherTime(endTime);
					if (flag) {
						if ("Y".equals(v.getIs_month_data())) {
							flag1 = isInTheMonth(startTime);
							if (flag1) {
								flag1 = isInTheMonth(endTime);
							} else {
								break;
							}
						}
					}
				} else {
					String time = param;
					flag = matcherTime(time);
					if (flag) {
						if ("Y".equals(v.getIs_month_data())) {
							flag1 = isInTheMonth(time);
						}
					} else {
						flag1 = false;
						break;
					}
				}
			}
		}
		if (flag) {
			if (flag1) {
				return ResultParser.createSuccuseMap();
			} else {
				Map tepMap = ResultParser.createTimeNotInMonthErrorMap();
				tepMap.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
				return tepMap;
			}
		} else {
			logger.debug("参数错误...");
			Map tepMap = ResultParser.createParamErrorMap();
			tepMap.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
			return tepMap;
		}
		// 暂时不判断时间长度限制
		// int dayCount = daysBetween(startTime, endTime);
		// System.out.println("dayCount is " + dayCount);
		// if (0 <= dayCount && dayCount <= 7) {
		// System.out.println("正确");
		// } else {
		// flag = false;
		// break;
		// }
	}

	private Map queryServiceById(String serviceId)
	{
		String sql = SQLHelper.queryServiceById(serviceId);
		Map teMap = new HashMap();
		try {
			teMap = dao.queryServiceById(sql);
		} catch (DBException e) {
			logger.debug("查询服务报错..." + e);
			e.printStackTrace();
		}
		return teMap;
	}

	private List queryParam(String serviceId)
	{
		String sql = SQLHelper.queryParamSQL(serviceId);
		List temList = new ArrayList();
		try {
			temList = dao.queryParam(sql);
		} catch (DBException e) {
			logger.debug("查询参数报错..." + e);
			e.printStackTrace();
		}
		return temList;
	}

	private int daysBetween(String startTime, String endTime)
	{
		Date earlydate = new Date();
		Date latedate = new Date();
		DateFormat df = DateFormat.getDateInstance();
		try {
			earlydate = df.parse(startTime);
			latedate = df.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.util.Calendar calst = java.util.Calendar.getInstance();
		java.util.Calendar caled = java.util.Calendar.getInstance();
		calst.setTime(earlydate);
		caled.setTime(latedate);
		// 设置时间为0时
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// 得到两个日期相差的天数
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;
		return days;
	}

	private boolean isInTheMonth(String time)
	{
		boolean flag = false;
		Calendar cal = Calendar.getInstance();
		int monthInt = cal.get(Calendar.MONTH) + 1;
		logger.debug("系统当前月份为 " + monthInt);
		String theMonth = String.valueOf(monthInt);
		if (theMonth.length() == 1) {
			theMonth = "0" + theMonth;
		}
		String temp1[] = time.split("-");
		String month = temp1[1];
		logger.debug("参数month为：" + month);
		if (theMonth.equals(month)) {
			flag = true;
		}
		return flag;
	}

	private boolean matcherTime(String time)
	{
		String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern pattern = Pattern.compile(eL);
		Matcher matcher = pattern.matcher(time);
		boolean b = matcher.matches();
		// 当条件满足时，将返回true，否则返回false
		return b;
	}

}
