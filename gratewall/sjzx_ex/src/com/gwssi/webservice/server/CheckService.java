package com.gwssi.webservice.server;

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
import cn.gwssi.webservice.check.ICheckRule;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.ExConstant;
import com.gwssi.common.constant.ShareConstants;
import com.gwssi.common.util.DateUtil;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.log.sharelog.dao.ShareLogVo;
import com.gwssi.resource.svrobj.vo.VoResServiceTargets;
import com.gwssi.share.rule.vo.VoShareServiceRule;
import com.gwssi.share.service.vo.ShareLockVo;
import com.gwssi.share.service.vo.VoShareServiceCondition;
import com.gwssi.share.trs.vo.VoTrsShareService;

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
	 * @param param
	 */
	private boolean checkVerify(Map param){
		//System.out.println("--------checkVerify----------");
		logger.info("进入验证服务是否要特殊的类验证");
		String className = "cn.gwssi.webservice.check.Check_"; 
		String userName="";
        boolean result=false;
        try {
        	if (null != param.get("LOGIN_NAME")) {
        		userName = param.get("LOGIN_NAME").toString();
        		className=className+userName;
				Class c = Class.forName(className);
				ICheckRule iTest=(ICheckRule)c.newInstance();
				result=iTest.checkRule(param);
				logger.info("校验结果为"+result);
			}else{
				logger.info("LOGIN_NAME为空");
				return true;
			}
        } catch (Exception e) {
			//e.printStackTrace();
        	//System.out.println("未找到"+userName+"对应的校验处理类");
        	logger.info("未找到"+userName+"对应的校验处理类---"+e.getMessage());
			return true;
		}		
		return result;
	}
	
	/**
	 * 
	 * checkServiceCanBeUsed 检查服务是否能使用
	 * 
	 * @param serviceVo
	 * @param param
	 * @param shareLogVo
	 * @return Map
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected Map checkServiceCanBeUsed(ServiceVo serviceVo, Map param,
			ShareLogVo shareLogVo) throws DBException
	{
		// 判断是否是测试用户
		if (param.containsKey("USER_TYPE")) {
			if("TEST".equals(param.get("USER_TYPE"))){
				logger.debug("该用户为测试用户不用检验...");
				// 设置记录日志信息
				shareLogVo.setLog_type(ExConstant.LOG_TYPE_TEST);
			}else if("SHARE_FTP".equals(param.get("USER_TYPE"))){
				logger.debug("共享FTP任务不用检验...");
				// 设置记录日志信息
				shareLogVo.setLog_type(ExConstant.LOG_TYPE_USER);
			}else{
				logger.error("参数有误，错误的用户类型:"+param.get("USER_TYPE"));
				Map result =ResultParser.createParamErrorMap();
				result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
				return result;
			}
			
			this.getServiceInfo(serviceVo, shareLogVo);
			return ResultParser.createSuccuseMap();
		} else {
			logger.debug("该用户要检验...");
			// 设置记录日志信息
			shareLogVo.setLog_type(ExConstant.LOG_TYPE_USER);
			
			
			//特殊校验 金融局
			if (null != param.get("LOGIN_NAME")) {					
				if(!checkVerify(param)){
					Map rsMap=ResultParser.createVerifyErrorMap();
					shareLogVo.setReturn_codes(String.valueOf(rsMap
							.get(ShareConstants.SERVICE_FHDM_VERIFY_ERROR))); // 日志记录返回代码
					return rsMap;
				}
				
			}		
			
			
			
			// 20140311 add 检验该服务是否被锁
			String serviceId = serviceVo.getService_id();
			Map lockedMap = this.queyrServiceLocked(serviceId);
			System.out.println(lockedMap);
			if ("Y".equals(lockedMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				// 1.检查服务对象登录信息是否正确,ip绑定是否正确
				Map loginMap = this
						.queryLoginInfo(serviceVo, param, shareLogVo);
				if ("Y".equals(loginMap.get(ShareConstants.SERVICE_CAN_BE_USED)
						.toString())) {
					// 2.检查服务,服务状态是否正确
					Map serStateMap = this.queryServiceState(serviceVo,
							shareLogVo);
					if ("Y".equals(serStateMap.get(
							ShareConstants.SERVICE_CAN_BE_USED).toString())) {
						// 3.检查例外时间是否正确
						Map serDateMap = this.querySvrDate(DateUtil.getToday());
						if ("Y".equals(serDateMap.get(
								ShareConstants.SERVICE_CAN_BE_USED).toString())) {
							// 4.检查访问时间规则
							Map dateRuleMap = this.checkDateRule(serviceVo);
							if ("Y".equals(dateRuleMap.get(
									ShareConstants.SERVICE_CAN_BE_USED)
									.toString())) {
								// 5.检查参数是否正确
								Map paraMap = this.checkParam(serviceVo, param);
								return paraMap;
							} else {
								return dateRuleMap;
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
			} else {
				return lockedMap;
			}
		}
	}

	/**
	 * 
	 * getServiceInfo 测试服务时获取服务的详细信息
	 * 
	 * @param serviceVo
	 * @param shareLogVo
	 *            void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected void getServiceInfo(ServiceVo serviceVo, ShareLogVo shareLogVo)
	{
		shareLogVo.setService_targets_id(serviceVo.getService_targets_id()); // 服务对象ID
		shareLogVo.setService_targets_name(serviceVo.getService_targets_name()); // 服务对象名称
		shareLogVo.setTargets_type(serviceVo.getService_targets_type()); // 服务对象类型
		shareLogVo.setIs_formal(serviceVo.getIs_formal());// 是否正式环境
		//shareLogVo.setIs_formal("N");
		shareLogVo.setService_id(serviceVo.getService_id()); // 服务ID
		shareLogVo.setService_name(serviceVo.getService_name()); // 服务名称
		shareLogVo.setService_type(serviceVo.getService_type()); // 服务类型
	}

	/**
	 * 
	 * queryLoginInfo 检查服务对象登录信息是否正确
	 * 
	 * @param serviceVo
	 * @param param
	 * @param shareLogVo
	 * @return Map
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected Map queryLoginInfo(ServiceVo serviceVo, Map param,
			ShareLogVo shareLogVo) throws DBException
	{
		logger.debug("开始检验用户信息...");
		Map resultMap = new HashMap();
		resultMap.put(ShareConstants.SERVICE_CAN_BE_USED, "Y");

		if (null == serviceVo) {
			logger.debug("用户无效...");
			resultMap = ResultParser.createUserFailMap();
			return ResultParser.createUserFailMap();
		} else {
			String svrName = param.get("LOGIN_NAME").toString();
			String svrPwd = param.get("PASSWORD").toString();
			String svrStatus = serviceVo.getService_status();
			logger.debug("用户名为:" + svrName);
			logger.debug("密码为:" + svrPwd);
			logger.debug("用户有效状态为:" + svrStatus);

			// 设置记录日志信息
			shareLogVo.setIs_formal(serviceVo.getIs_formal()); // 是否正式环境
			shareLogVo.setService_targets_id(serviceVo.getService_targets_id()); // 服务对象ID
			shareLogVo.setService_targets_name(serviceVo
					.getService_targets_name()); // 服务对象名称
			shareLogVo.setTargets_type(serviceVo.getService_targets_type()); // 服务对象类型
			String serviceStatus = serviceVo.getService_status();

			if (ExConstant.IS_MARKUP_Y.equals(serviceStatus)) {
				// 判断用户信息
				if (ExConstant.IS_MARKUP_Y.equals(svrStatus)) {
					if (StringUtils.isBlank(svrName)
							|| !svrName.equals(serviceVo
									.getService_targets_no())) {
						logger.debug("用户名错误...");
						resultMap = ResultParser.createUserErrorlMap();
						return ResultParser.createUserErrorlMap();
					}
					if (StringUtils.isBlank(svrPwd)
							|| !serviceVo.getService_password().equals(svrPwd)) {
						logger.debug("密码错误...");
						resultMap = ResultParser.createPwdErrorlMap();
						return ResultParser.createPwdErrorlMap();
					}
					if (!queryServiceIPInfo(serviceVo, shareLogVo)) {
						logger.debug("IP错误...");
						resultMap = ResultParser.createServiceIPFailMap();
						return ResultParser.createServiceIPFailMap();
					}
					if ("Y".equals(resultMap
							.get(ShareConstants.SERVICE_CAN_BE_USED))) {
						if (svrName.equals(serviceVo.getService_targets_no())
								&& svrPwd.equals(serviceVo
										.getService_password())) {
							logger.debug("用户名密码检验成功...");
							return ResultParser.createSuccuseMap();
						} else {
							return resultMap;
						}
					} else {
						return resultMap;
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
	 * queryServiceIPInfo 检验ip绑定是否正确
	 * 
	 * @param serviceVo
	 * @param shareLogVo
	 * @return boolean
	 * @throws DBException
	 *             boolean
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected boolean queryServiceIPInfo(ServiceVo serviceVo,
			ShareLogVo shareLogVo) throws DBException
	{
		logger.debug("开始检验IP信息...");
		boolean ipFlag = false;
		String isBindIP = serviceVo.getIs_bind_ip();
		if (StringUtils.isNotBlank(isBindIP) // 如果绑定ip
				&& ExConstant.IS_BIND_IP_Y.equals(isBindIP)) {
			String clientIP = getClientIp();
			if (null != serviceVo.getIp() && !"".equals(serviceVo.getIp())) {
				String[] ips = serviceVo.getIp().split(",");
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
		} else {
			logger.debug("没有绑定ip,检验IP完毕...");
			ipFlag = true; // 没有绑定ip
		}
		return ipFlag;
	}

	/**
	 * 
	 * queryServiceIPInfo 检验ip绑定是否正确forTRS
	 * 
	 * @param v
	 * @param shareLogVo
	 * @return boolean
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
	 * @param serviceVo
	 * @param shareLogVo
	 * @return Map
	 * @throws DBException
	 *             Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected Map queryServiceState(ServiceVo serviceVo, ShareLogVo shareLogVo)
			throws DBException
	{
		logger.debug("开始检验服务状态...");

		if (null == serviceVo) {
			logger.debug("没有找到该服务...");
			return ResultParser.createSvrNotFoundMap();
		} else {
			// 设置记录日志信息
			shareLogVo.setService_id(serviceVo.getService_id()); // 服务ID
			shareLogVo.setService_name(serviceVo.getService_name()); // 服务名称
			shareLogVo.setService_type(serviceVo.getService_type()); // 服务类型
			if (ExConstant.IS_MARKUP_Y.equals(serviceVo.getIs_markup())
					&& ExConstant.SERVICE_STATE_Y.equals(serviceVo
							.getService_state())) {
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
	 * querySvrDate 检查例外时间
	 * 
	 * @param svrDate
	 * @return Map
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
	 * checkDateRule 检验访问时间是否符合要求
	 * 
	 * @param serviceVo
	 * @return Map Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected Map checkDateRule(ServiceVo serviceVo)
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss");// 只有时分秒
		String nowtime = sDateFormat.format(new java.util.Date());

		// 首页判断当天是否有配置规则
		String sql = SQLHelper.queryCountDataRule(serviceVo.getService_id());

		int count = 0;
		int timeCount = 0;
		try {
			String countStr = dao.queryDateRule(sql);
			// count为0时表明当前未配置规则
			count = Integer.parseInt(countStr == null ? "0" : countStr);

			if (count > 0) {
				// 当前有配置时间访问规则
				sql = SQLHelper.queryDateRule(serviceVo.getService_id(),
						nowtime);
				String timeStr = dao.queryDateRule(sql);
				timeCount = Integer.parseInt(timeStr == null ? "0" : timeStr);
			}
		} catch (DBException e) {
			logger.debug("查询服务规则报错..." + e);
			e.printStackTrace();
		}
		if (count == 0) {
			logger.debug(" count为0时表明当前未配置规则");
			return ResultParser.createSuccuseMap();
		} else {
			if (timeCount == 0) {
				logger.debug("当前时间不在服务允许访问时间内：" + nowtime);
				return ResultParser.createSvrTimeErrorMap();
			} else {
				logger.debug("timeCount>0时表明当前时间符合规则");
				return ResultParser.createSuccuseMap();
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "finally" })
	protected Map checkRules(String serviceId, int currentCount)
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String nowTime = sDateFormat.format(new java.util.Date());
		logger.debug("本次一共取：" + currentCount + " 条");
		ShareLockVo shareLockVo = new ShareLockVo();
		shareLockVo.setService_id(serviceId);
		shareLockVo.setLock_time(nowTime);
		Map resultMap = new HashMap();
		// 查询当天的访问规则
		String ruleSql = SQLHelper.queryRule(serviceId);
		// 查询当天访问了多少次
		String logSql = SQLHelper.getServiceLogSQL(serviceId);
		// Map logMap = new HashMap();
		Map ruleMap = new HashMap();
		VoShareServiceRule serviceRule = new VoShareServiceRule();
		try {
			ruleMap = dao.queryServiceRule(ruleSql);// 查询当天的访问规则
			// 如果该服务配置了规则,要针对规则进行校验
			if (null != ruleMap.get("TIMES_DAY")) {
				ParamUtil.mapToBean(ruleMap, serviceRule, false);
				Map logMap = dao.queryServiceLog(logSql);// 查询当天访问记录
				// 获取每天访问多少次
				int times_day = Integer
						.parseInt(serviceRule.getTimes_day() == null ? "0"
								: serviceRule.getTimes_day());
				// 获取每次访问多少条
				int count_times = Integer
						.parseInt(serviceRule.getCount_dat() == null ? "0"
								: serviceRule.getCount_dat());
				// 获取当允许访问多少条
				int amount_day = Integer.parseInt(serviceRule
						.getTotal_count_day() == null ? "0" : serviceRule
						.getTotal_count_day());
				// 获取已经访问的次数,+1算上这一次的
				int times = Integer.parseInt(logMap.get("TIMES").toString()) + 1;
				logger.debug("该服务今天被访问过[" + times + "]次...");
				// 获取已经访问的条数,本次访问的条数
				int amount = Integer.parseInt(logMap.get("AMOUNT").toString())
						+ currentCount;
				logger.debug("该服务今天共获取数据[" + amount + "]条...");

				if (times_day != 0 && times > times_day) {
					logger.debug("超过今天允许访问次数...");
					resultMap = ResultParser.createSvrNumErrorMap();
					shareLockVo
							.setLock_code(ShareConstants.SERVICE_FHDM_LOCK_NUMBER);
					this.insertLock(shareLockVo);
				} else if (count_times != 0 && currentCount > count_times) {
					logger.debug("超过单次允许访问条数...");
					resultMap = ResultParser.createSvrTimeCountErrorMap();
				} else if (amount_day != 0 && amount > amount_day) {
					logger.debug("超过今天允许访问条数...");
					resultMap = ResultParser.createSvrTotalErrorMap();
					shareLockVo
							.setLock_code(ShareConstants.SERVICE_FHDM_LOCK_TOTAL);
					this.insertLock(shareLockVo);
				} else {
					logger.debug("检验访问规则通过...");
					resultMap = ResultParser.createSuccuseMap();
				}
			} else {
				logger.debug("该服务当天未配置访问规则...");
				resultMap = ResultParser.createSuccuseMap();
			}
		} catch (DBException e) {
			logger.debug("检查服务规则出错...");
			resultMap = ResultParser.createSqlErrorMap();
			e.printStackTrace();
		} finally {
			return resultMap;
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
	 * isInDatesForJob 判断时间是否在一个时间区域内
	 * 
	 * @param strDate
	 * @param strDateBegin
	 * @param strDateEnd
	 * @return boolean
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public boolean isInDatesForJob(String strDate, String strDateBegin,
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
		if ((intCompareBegin > 0 || intCompareBegin == 0) && intCompareEnd < 0) {
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
	private Map checkParam(ServiceVo serviceVo, Map paraMap)
	{
		// 1.校验所有的参数是否都有
		// 2.校验时间参数是否格式都正确
		// 3.校验是否有只取本月数据限制
		// 4.检验是否有时间跨度限制
		logger.debug("校验参数...");
		boolean flag = true;

		flag = paramIsLost(paraMap);
		Map tepMap = new HashMap();
		if (flag) {
			tepMap = dateFormatIsRight(serviceVo, paraMap);
			return tepMap;
		} else {
			logger.debug("参数错误...");
			tepMap = ResultParser.createParamErrorMap();
			tepMap.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
			return tepMap;
		}
	}

	/**
	 * 
	 * paramIsLost 校验所有的参数是否都存在
	 * 
	 * @param paraMap
	 * @return boolean
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private boolean paramIsLost(Map paraMap)
	{
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
		return flag;
	}

	/**
	 * 
	 * dateFormatIsRight 校验时间参数是否格式都正确
	 * 
	 * @param serviceVo
	 * @param paraMap
	 * @return boolean
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private Map dateFormatIsRight(ServiceVo serviceVo, Map paraMap)
	{
		boolean flag = false;
		boolean isRightFormat = true;
		boolean isInPeriod = true;
		boolean isInMonth = true;
		List timeParamList = new ArrayList();
		List temList = this.queryParam(serviceVo.getService_id());
		logger.debug("temList is" + temList);
		for (int i = 0; i < temList.size(); i++) {
			Map m = (Map) temList.get(i);
			VoShareServiceCondition v = new VoShareServiceCondition();
			ParamUtil.mapToBean(m, v, false);
			if ("2".equals(v.getParam_type())) {
				if (!timeParamList.contains(v.getColumn_name_en()))
					timeParamList.add(v.getColumn_name_en());
			}
		}
		logger.debug("timeParamList is" + timeParamList);
		for (int i = 0; i < timeParamList.size(); i++) {
			String param = "";
			if (null != paraMap.get(timeParamList.get(i))) {
				param = paraMap.get(timeParamList.get(i)).toString();
				logger.debug("时间param is" + param);
				int split = param.indexOf(",");
				if (split > 0) {
					// 判断时间格式是否正确
					String startTime = param.substring(0, split);
					isRightFormat = matcherTime(startTime);
					String endTime = param.substring(split + 1, param.length());
					isRightFormat = matcherTime(endTime);
					logger.debug("时间startTime :" + startTime);
					logger.debug("时间endTime :" + endTime);

					// 判断是否当月
					if ("Y".equals(serviceVo.getIs_month_data())) {
						isInMonth = isInTheMonth(startTime);
						isInMonth = isInTheMonth(endTime);
					}

					// 判断时间间隔是否正确
					if (null != serviceVo.getVisit_period()
							&& Integer.parseInt(serviceVo.getVisit_period()) >= 1) {
						int dayCount = daysBetween(startTime, endTime);
						logger.debug("天数dayCount" + dayCount);
						logger.debug("天数visit_period"
								+ serviceVo.getVisit_period());
						if (0 <= dayCount
								&& dayCount <= Integer.parseInt(serviceVo
										.getVisit_period())) {
							isInPeriod = true;
						} else {
							isInPeriod = false;
						}
					}

				} else {
					String time = param;
					isRightFormat = matcherTime(time);
				}
			}
		}
		Map tepMap = new HashMap();
		if (isRightFormat && isInPeriod && isInMonth) {
			flag = true;
			tepMap = ResultParser.createSuccuseMap();
		}
		if (!isRightFormat) {
			logger.debug("参数错误...");
			tepMap = ResultParser.createParamErrorMap();
			tepMap.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		}
		if (!isInPeriod) {
			tepMap = ResultParser.createTimeOutRuleErrorMap();
			tepMap.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		}
		if (!isInMonth) {
			tepMap = ResultParser.createTimeNotInMonthErrorMap();
			tepMap.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
		}
		return tepMap;
	}

	/**
	 * 
	 * queryParam 查询服务的参数
	 * 
	 * @param serviceId
	 * @return List
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
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

	/**
	 * 
	 * daysBetween 检验某个时间是否在一段时间内
	 * 
	 * @param startTime
	 * @param endTime
	 * @return int
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private int daysBetween(String startTime, String endTime)
	{
		Date earlydate = new Date();
		Date latedate = new Date();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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

	/**
	 * 
	 * isInTheMonth 判断时间是否在本月内
	 * 
	 * @param time
	 * @return boolean
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private boolean isInTheMonth(String time)
	{
		boolean flag = false;
		Calendar cal = Calendar.getInstance();
		int yearInt = cal.get(Calendar.YEAR);
		String theYear = String.valueOf(yearInt);
		logger.debug("系统当前年份为 " + theYear);
		int monthInt = cal.get(Calendar.MONTH) + 1;
		String theMonth = String.valueOf(monthInt);
		if (theMonth.length() == 1) {
			theMonth = "0" + theMonth;
		}
		logger.debug("系统当前月份为 " + theMonth);
		String temp1[] = time.split("-");
		String year = temp1[0];
		String month = temp1[1];
		logger.debug("参数year为：" + year);
		logger.debug("参数month为：" + month);
		if (theYear.equals(year) && theMonth.equals(month)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * matcherTime时间格式校验
	 * 
	 * @param time
	 * @return boolean
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	private boolean matcherTime(String time)
	{
		String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern pattern = Pattern.compile(eL);
		Matcher matcher = pattern.matcher(time);
		boolean b = matcher.matches();
		// 当条件满足时，将返回true，否则返回false
		return b;
	}

	// TRS方法区
	protected Map checkTrsServiceCanBeUsed(String serviceCode, Map param,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		Long start = System.currentTimeMillis();
		// 判断是否是测试用户
		if (param.containsKey("USER_TYPE")
				&& "TEST".equals(param.get("USER_TYPE"))) {
			logger.debug("该用户为测试用户不用检验...");

			// 设置记录日志信息
			shareLogVo.setLog_type(ExConstant.LOG_TYPE_TEST);
			this.queryTrsLoginInfoTest(serviceCode, param, shareLogVo,
					serviceId);
			this.queryTrsServiceStateTest(serviceCode, shareLogVo, serviceId);
			Map serStateMap = this.queryTrsServiceState(serviceCode,
					shareLogVo, serviceId);
			return serStateMap;
			// return ResultParser.createSuccuseMap();
		} else {
			logger.debug("该用户要检验...");
			// 设置记录日志信息
			shareLogVo.setLog_type(ExConstant.LOG_TYPE_TEST);
			Map lockedMap = this.queyrServiceLocked(serviceId);
			if ("Y".equals(lockedMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				// 1.检查服务对象登录信息是否正确,ip绑定是否正确
				Map loginMap = this.queryTrsLoginInfo(serviceCode, param,
						shareLogVo, serviceId);
				// 2.检查服务,服务状态是否正确
				Map serStateMap = this.queryTrsServiceState(serviceCode,
						shareLogVo, serviceId);
				if ("Y".equals(loginMap.get(ShareConstants.SERVICE_CAN_BE_USED)
						.toString())) {
					if ("Y".equals(serStateMap.get(
							ShareConstants.SERVICE_CAN_BE_USED).toString())) {

						if (param != null && !param.equals(""))
							// return ResultParser.createSuccuseMap();
							return serStateMap;
						else
							return ResultParser.createParamErrorMap();
					} else {
						return serStateMap;
					}
				} else {
					return loginMap;
				}
			} else {
				return lockedMap;
			}
		}
	}

	protected Map queryTrsLoginInfo(String serviceCode, Map param,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		logger.debug("开始检验用户信息...");
		// 获取访问服务的用户信息
		String sql = SQLHelper.getTrsLoginSQL(serviceId);
		Map loginMap = dao.queryLoginInfo(sql);
		logger.debug("loginMap is " + loginMap);
		logger.debug("param is " + param);

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

	protected void queryTrsLoginInfoTest(String serviceCode, Map param,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		logger.debug("开始检验用户信息...");
		// 获取访问服务的用户信息
		String sql = SQLHelper.getTrsLoginSQL(serviceCode);
		Map loginMap = dao.queryLoginInfo(sql);

		VoResServiceTargets v = new VoResServiceTargets();
		ParamUtil.mapToBean(loginMap, v, false);

		// 设置记录日志信息
		shareLogVo.setService_targets_id(v.getService_targets_id()); // 服务对象ID
		shareLogVo.setService_targets_name(v.getService_targets_name()); // 服务对象名称
		shareLogVo.setTargets_type(v.getService_targets_type()); // 服务对象类型

	}

	protected Map queryTrsServiceState(String serviceCode,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		logger.debug("开始检验服务状态...");
		// 获取服务状态
		String sql = SQLHelper.queryTrsServiceById(serviceId);
		Map svrStateMap = dao.queryServiceState(sql);

		if (svrStateMap.isEmpty()) {
			logger.debug("没有找到该服务...");
			return ResultParser.createSvrNotFoundMap();
		} else {
			VoTrsShareService v = new VoTrsShareService();
			ParamUtil.mapToBean(svrStateMap, v, false);
			// 设置记录日志信息
			shareLogVo.setService_id(v.getTrs_service_id()); // 服务ID
			shareLogVo.setService_name(v.getTrs_service_name()); // 服务名称
			shareLogVo.setService_type("99"); // 服务类型TRS设定为99
			if (ExConstant.IS_MARKUP_Y.equals(v.getIs_markup())
					&& ExConstant.SERVICE_STATE_Y.equals(v.getService_state())) {
				// 说明此服务能够提供服务
				logger.debug("该服务正常...");

				Map result = new HashMap();
				result.put(ShareConstants.SERVICE_CAN_BE_USED, "Y");

				result.put("search_db", v.getTrs_data_base());
				result.put("show_column", v.getTrs_column());
				result.put("search_column", v.getTrs_search_column());
				result.put("trs_service_id", v.getTrs_service_id());
				if (StringUtils.isNotBlank(v.getTrs_template())) {
					String template = v.getTrs_template().toString()
							+ (StringUtils.isNotBlank(v.getTrs_template_ex()) ? v
									.getTrs_template_ex().toString() : "");
					result.put("trs_template", template);
				} else {
					result.put("trs_template", "");
				}
				return result;
			} else {
				logger.debug("该服务状态为不可访问...");
				return ResultParser.createServiceStateFailMap();
			}
		}
	}

	protected void queryTrsServiceStateTest(String serviceCode,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		// 获取服务状态
		String sql = SQLHelper.queryTrsServiceById(serviceId);
		Map svrStateMap = dao.queryServiceState(sql);

		VoTrsShareService v = new VoTrsShareService();
		ParamUtil.mapToBean(svrStateMap, v, false);

		// 设置记录日志信息
		shareLogVo.setService_id(v.getService_targets_id()); // 服务ID
		shareLogVo.setService_name(v.getTrs_service_name()); // 服务名称
		shareLogVo.setService_type("99"); // 服务类型

	}

	/**
	 * 
	 * queyrServiceLocked 查询服务被锁信息
	 * 
	 * @param serviceId
	 * @return Map
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public Map queyrServiceLocked(String serviceId)
	{
		String sql = SQLHelper.queryServiceLokedSQL(serviceId);
		Map dataMap = new HashMap();
		try {
			List list = dao.queryServiceLoked(sql);
			ShareLockVo vo = new ShareLockVo();
			if (list.size() > 0) {
				dataMap = (Map) list.get(0);
				ParamUtil.mapToBean(dataMap, vo, false);
				dataMap.clear();
				dataMap.put(ShareConstants.SERVICE_OUT_PARAM_FHDM,
						vo.getLock_code());
				dataMap.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
			} else {
				// 没有被锁
				dataMap = ResultParser.createSuccuseMap();
			}
		} catch (DBException e) {
			dataMap = ResultParser.createSqlErrorMap();
			logger.debug("方法queyrServiceLocked查询报错");
			e.printStackTrace();
		}
		return dataMap;
	}

	private int insertLock(ShareLockVo shareLockVo)
	{
		logger.debug("记录日志...");
		String sql = SQLHelper.insertLock(shareLockVo);
		int count = 0;
		try {
			count = dao.insertShareLog(sql);
		} catch (DBException e) {
			logger.debug("记录日志报错..." + e);
			e.printStackTrace();
		}
		return count;
	}

	public static void main(String[] args)
	{
		CheckService c = new CheckService();
		System.out.println(c.daysBetween("2012-01-24", "2012-01-27"));
		// boolean a = c.isInDates("2013-12-14 13:36:04", "2013-12-14 02:00:00",
		// "2013-12-14 05:00:00");
		// logger.debug(a);
		// SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss");//
		// 只有时分秒
		// String calNowtime = sDateFormat.format(new java.util.Date());
		// System.out.println(calNowtime);
	}

}
