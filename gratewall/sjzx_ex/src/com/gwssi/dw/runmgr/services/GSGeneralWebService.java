package com.gwssi.dw.runmgr.services;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;

import bsh.EvalError;
import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.common.util.StringUtil;
import com.gwssi.common.util.UuidGenerator;
import com.gwssi.dw.runmgr.services.common.Constants;
import com.gwssi.dw.runmgr.services.common.ResultParser;
import com.gwssi.dw.runmgr.services.common.SQLHelper;
import com.gwssi.dw.runmgr.services.common.ServiceBean;
import com.gwssi.dw.runmgr.services.common.ServiceFactory;
import com.gwssi.dw.runmgr.services.common.UserBean;

public class GSGeneralWebService
{
	ServiceDAO	dao	= null;

	public GSGeneralWebService()
	{
		dao = new ServiceDAOImpl();
	}

	public Map query(Map params)
	{
		// 服务开始时间
		Date date = new Date();
		long startTime = date.getTime();
		String is_limit_total = null;
		String limit_total = null;
		// DC2-jufeng-2012-07-07
		// 必须设置JDK启动时的时区参数 -Duser.timezone=GMT+8 否则星期会取错,需要到生产系统中验证！！！
		String today = CalendarUtil.getDayofWeek(date);
		// / System.out.println("当前时间是 "+startTime + " 星期 "+today);
		// System.out.println("{48 line of GSGeneralWebService} 【开始调用服务】 ");
		Map userMap = null;
		UserBean user = null;
		try {
			// 如果用户ID为空，则是调用webservice，需要验证用户名和密码
			if (params.get("SYS_SVR_USER_ID") == null) {
				// 首先检查是否提供了登录参数
				if ((params.get(Constants.SERVICE_IN_PARAM_LOGIN_NAME) == null)
						|| (params
								.get(Constants.SERVICE_IN_PARAM_LOGIN_PASSWORD) == null)) {
					return ResultParser.createLoginFailMap(params);
				}
				try {
					// DC2-jufeng 验证用户账号和密码正确
					String sql = SQLHelper.loginSQL(""+ params.get(Constants.SERVICE_IN_PARAM_LOGIN_NAME),
									""+ params.get(Constants.SERVICE_IN_PARAM_LOGIN_PASSWORD));
					userMap = dao.queryUser(sql);
				} catch (DBException e) {
					e.printStackTrace();
					return ResultParser.createUnknownFailMap(params);
				}
				user = this.login(userMap);
				// 检查是否成功登录
				if (user == null) {
					// 错误！用户名，密码不正确！
					return ResultParser.createLoginFailMap(params);
				} else if (Integer.parseInt(user.getState()) != 0) {
					// 错误！用户状态为停用！
					return ResultParser.createLoginFailMap(params);
				} else if (user.getIs_ip_bind() != null
						&& !user.getIs_ip_bind().equals("")
						&& !user.getIs_ip_bind().equals("0")) {
					// DC2-jufeng-2012-07-07
					// 当用户设置要绑定IP时进行验证
					String ip_bind = null;
					if (user.getIp_bind() != null
							&& !user.getIp_bind().equals("")) {
						// 绑定的IP信息存在
						ip_bind = ";" + user.getIp_bind() + ";";
						String ip = ";" + this.getClientIp() + ";";
						if (ip_bind.indexOf(ip) < 0) {
							// 用户当前IP不在绑定的IP信息中
							return ResultParser.createParamErrorMap(params,
									Constants.SERVICE_FHDM_ERROR_IP);
						}
					} else {
						// 绑定的IP信息不存在
						return ResultParser.createParamErrorMap(params,
								Constants.SERVICE_FHDM_ERROR_IP);
					}
				}

				// 保存用户信息
				params.put("SYS_SVR_USER_ID", user.getId());
				params.put("USER_NAME", user.getName());// 用于记录日志
			}
			// 否则为测试，不需要验证用户密码
			else {
				params.put("IS_TEST", "1");
				userMap = dao
						.queryUser("SELECT sys_svr_user_id, user_name from sys_svr_user WHERE sys_svr_user_id='"
								+ params.get("SYS_SVR_USER_ID") + "'");
				if (userMap != null) {
					params.put("USER_NAME", userMap.get("USER_NAME"));// 用于记录日志
				}
			}
			// -----------真正调用和测试公用的验证开始--------------

			// 检查是否提供了服务代码参数
			if (params.get(Constants.SERVICE_IN_PARAM_SERVICE_CODE) == null) {
				System.out.println("错误！未提供服务编码！");
				return ResultParser.createParamErrorMap(params,
						Constants.SERVICE_FHDM_INPUT_PARAM_ERROR);
			}
			// 根据服务代码获得具体服务信息
			ServiceBean bean = this.queryService(""
					+ params.get(Constants.SERVICE_IN_PARAM_SERVICE_CODE));
			// 检查服务代码是否正确
			if (bean == null) {
				System.out.println("错误！服务代码错误！");
				return ResultParser.createParamErrorMap(params,
						Constants.SERVICE_FHDM_INPUT_PARAM_ERROR);
			}

			// 保存服务信息
			params.put("SYS_SVR_SERVICE_ID", bean.getId());
			params.put("SVR_NAME", bean.getName());// 用于记录日志

			String returnCode = checkRange(bean, params);
			if (!returnCode.equalsIgnoreCase(Constants.SERVICE_FHDM_SUCCESS)) {
				System.out.println("错误！开始结束数错误！");
				Map result = ResultParser.createParamErrorMap(params,
						returnCode);
				this.writeLog(params, result, startTime);
				return result;
			}
			// -----------真正调用和测试公用的验证结束--------------

			// 根据用户ID和服务ID查询配置
			this.queryConfig(params);
			// 如果为webservice
			// DC2-jufeng-2012-07-07
			// 如果用户限制访问日期、时间、次数和总条数中至少有一项设置， 进入如下分支
			// &&user.getIs_limit()!=null&&!user.getIs_limit().equals("")&&user.getIs_limit().equals("1")
			if (user != null && params.get("IS_TEST") == null) {
				System.out.println("-----调用webservice------");
				// 如果服务ID为空，则说明该用户无此服务
				if (params.get("SYS_SVR_CONFIG_ID") == null) {
					System.out.println("错误！服务未配置给用户！");
					Map result = ResultParser.createParamErrorMap(params,
							Constants.SERVICE_FHDM_INPUT_PARAM_ERROR);
					this.writeLog(params, result, startTime);
					return result;
				} else {
					System.out.println("---is_pause----" + params.get("IS_PAUSE"));
					if (params.get("IS_PAUSE").equals("1")) {
						System.out.println("错误！服务已暂停！");
						Map result = ResultParser.createParamErrorMap(params,
								Constants.SERVICE_FHDM_SERVICE_PAUSE);
						this.writeLog(params, result, startTime);
						return result;
					}
				}
				// DC2-jufeng-2012-07-08
				// 判断该用户是否在当日用户服务访问加锁表中，如果在直接返回并记录日志
				String sqlLock = SQLHelper.getLocked(""
						+ params.get("SYS_SVR_USER_ID"), ""
						+ params.get("SYS_SVR_SERVICE_ID"));
				Map mapLock = dao.queryLocked(sqlLock);
				System.out.println("查询是否加锁的 mapLock 是   " + mapLock);
				if (mapLock != null && !mapLock.isEmpty()) {
					System.out.println("当日用户已受限访问！");
					Map result = ResultParser.createParamErrorMap(params,
							Constants.SERVICE_FHDM_LOCKED_TODAY);
					this.writeLog(params, result, startTime);
					return result;
				}

				// 获取用户某个服务的限制信息
				String sql = SQLHelper.getSysLimitSQL(""
						+ params.get("SYS_SVR_USER_ID"), ""
						+ params.get("SYS_SVR_SERVICE_ID"));
				List limitList = dao.querySysLimit(sql);
				String errorCode = null;
				if (limitList != null || limitList.size() > 0) {
					// 目前限制条件只作用于星期几，即每天的限定时间段，限定访问次数和限定总条数都相同，
					// 所以取第一个就可以，但数据库结构支持每天都不同。
					String currentTime = StringUtil.formatDateToString(
							new Date(startTime), "HH:mm");
					for (int j = 0; j < limitList.size(); j++) {
						HashMap map = (HashMap) limitList.get(j);
						String limit_week = (String) map.get("LIMIT_WEEK");
						// System.out.println("map"+j+"的 limit_week is "+
						// limit_week + " today is "+today+" map is " + map);
						if (today != null && !limit_week.equals("")
								&& limit_week.equals(today)) {
							String is_limit_week = ""
									+ map.get("IS_LIMIT_WEEK");
							String is_limit_time = ""
									+ map.get("IS_LIMIT_TIME");

							String limit_start_time = ""
									+ map.get("LIMIT_START_TIME");
							String limit_end_time = ""
									+ map.get("LIMIT_END_TIME");

							String is_limit_number = ""
									+ map.get("IS_LIMIT_NUMBER");
							String limit_number = "" + map.get("LIMIT_NUMBER");

							is_limit_total = "" + map.get("IS_LIMIT_TOTAL");
							limit_total = "" + map.get("LIMIT_TOTAL");

							// (1) 验证当天是否允许访问
							if (is_limit_week.equals("1")) {
								// 当天限制访问
								errorCode = Constants.SERVICE_FHDM_LOCK_WEEK;
								System.out.println("当天访问受限");
								// 该用户访问该服务存放入 当日用户服务加锁表中
								this.writeLock(params,
										Constants.SERVICE_FHDM_LOCK_WEEK,
										"当天访问受限", startTime);
								break;
							}
							// (2)验证当前时间是否允许访问
							if (is_limit_time.equals("1")) {
								// System.out.println("进行时间校验
								// "+"limit_start_time is "+ limit_start_time +
								// " limit_end_time is " + limit_end_time + "
								// currentTime is " + currentTime);
								if (!this.validTime(limit_start_time,
										limit_end_time, currentTime)) {
									// 当前访问时间不允许
									errorCode = Constants.SERVICE_FHDM_LOCK_TIME;
									System.out.println("当天访问时间受限 ");
									// 访问时间错误不放入加锁表中!!!
									// this.writeLock(params,
									// Constants.SERVICE_FHDM_LOCK_TIME,
									// "当天访问时间受限", startTime);
									break;
								}
							}
							// (3)验证访问次数是否超过
							if (is_limit_number.equals("1")) {
								String sqlVisited = SQLHelper.getCountVisited(
										"" + params.get("SYS_SVR_USER_ID"),
										"" + params.get("SYS_SVR_SERVICE_ID"));
								BigDecimal b = dao.visitedCount(sqlVisited);
								long cc = b.longValue();
								long maxc = 0l;
								try {
									maxc = Long.valueOf(limit_number)
											.longValue();
									System.out.println("允许最大访问次数为 " + maxc
											+ " 当前访问次数为 " + cc);
								} catch (Exception e) {
									System.out.println("判断当日访问次数时发生错误！");
									Map result = ResultParser
											.createParamErrorMap(
													params,
													Constants.SERVICE_FHDM_UNKNOWN_ERROR);
									this.writeLog(params, result, startTime);
									return result;
								}
								if (maxc == cc) {
									errorCode = Constants.SERVICE_FHDM_LOCK_NUMBER;
									System.out.println("当天访问次数受限");
									// 该用户访问该服务存放入 当日用户服务加锁表中
									this.writeLock(params,
											Constants.SERVICE_FHDM_LOCK_NUMBER,
											"当天访问次数受限", startTime);
								}

								break;
							}

							break;
						}
					}

				}/*
					 * else{ Map result =
					 * ResultParser.createParamErrorMap(params,
					 * Constants.SERVICE_FHDM_ERROR_LIMIT);
					 * this.writeLog(params, result, startTime); return result; }
					 */
				// System.out.println("errorCode is
				// ------------------------------>>>> "+errorCode);
				if (errorCode != null) {
					Map result = ResultParser.createParamErrorMap(params,
							errorCode);
					this.writeLog(params, result, startTime);
					return result;
				}
			}

			// 设置参数，并执行webservice
			List l = this.queryUserParams(params);
			if (params instanceof HashMap) {
				// 由于检查参数时会改变map的值，故传一个新的map
				HashMap paramsCopy = (HashMap) ((HashMap) params).clone();
				returnCode = checkUserParams(paramsCopy, l);
			} else {
				returnCode = checkUserParams(createNewMap(params), l);
			}
			if (!returnCode.equalsIgnoreCase(Constants.SERVICE_FHDM_SUCCESS)) {
				Map result = ResultParser.createParamErrorMap(params,
						returnCode);
				this.writeLog(params, result, startTime);
				return result;
			}
			// 转换所有日期
			// Iterator it = params.keySet().iterator();
			// while(it.hasNext()){
			// String key =(String) it.next();
			// for(int i = 0; i < l.size(); i++){
			// Map map = (Map) l.get(i);
			// if(((String)map.get("LEFT_COLUMN_NAME")).equalsIgnoreCase(key)){
			// if(((String)map.get("EDIT_TYPE")).trim().equals(Constants.COLUMN_TYPE_DATE)){
			// String[] dataStrs =
			// ((String)params.get(key)).split(Constants.MULTI_COLUMN_CONDITION_SEPARATOR);
			// StringBuffer newDataStr = new StringBuffer();
			// for(int j = 0; j < dataStrs.length; j++){
			// newDataStr.append(StringUtil.format2Date(dataStrs[j],
			// Constants.WS_QUERY_DATE_FORMAT, Constants.DB_DATE_FORMAT));
			// if(j != dataStrs.length - 1){
			// newDataStr.append(Constants.MULTI_COLUMN_CONDITION_SEPARATOR);
			// }
			// }
			// params.put(key, newDataStr.toString());
			// break;
			// }
			// }
			// }
			// }
			// 获得服务
			GeneralService service = ServiceFactory.getService(bean.getType());
			// 返回结果
			Map result = service.query(params);

			// 如果为调用webservice 则要验证当前访问条数是否受限
			if (user != null && params.get("IS_TEST") == null
					&& is_limit_total != null && is_limit_total.equals("1")) {
				if (limit_total != null && !limit_total.equals("")) {
					String sum = result.get("GSDJ_INFO_ARRAY") == null ? "0"
							: ""
									+ ((Map[]) result.get("GSDJ_INFO_ARRAY")).length;
					String sqlSum = SQLHelper.getSumVisited(""
							+ params.get("SYS_SVR_USER_ID"), ""
							+ params.get("SYS_SVR_SERVICE_ID"));
					BigDecimal b = dao.visitedSum(sqlSum);
					long sum_hs = 0l;
					long sumMax = 0l;
					long sum_this = 0l;
					try {
						sumMax = Long.valueOf(limit_total).longValue();
						sum_this = Long.valueOf(sum).longValue();
						sum_hs = b.longValue();
					} catch (Exception e) {
						System.out.println("验证用户服务访问是否超过最大总条数发生错误！");
						Map result2 = ResultParser.createParamErrorMap(params,
								Constants.SERVICE_FHDM_UNKNOWN_ERROR);
						this.writeLog(params, result2, startTime);
						return result2;
					}
					if (sumMax < (sum_this + sum_hs)) {

						System.out.println("用户服务访问总条数超过限制"
								+ " 判断限制的条件是 sumMax<(sum_this+sum_hs) "
								+ (sumMax < (sum_this + sum_hs)) + " sumMax is"
								+ sumMax + " sum_hs is " + sum_hs
								+ " sum_this is " + sum_this);
						Map result2 = ResultParser.createParamErrorMap(params,
								Constants.SERVICE_FHDM_LOCK_TOTAL);
						this.writeLog(params, result2, startTime);
						this.writeLock(params,
								Constants.SERVICE_FHDM_LOCK_TOTAL,
								"用户服务访问总条数超过限制", startTime);
						return result2;
					}
				}
			}
			this.writeLog(params, result, startTime);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Map result = ResultParser.createUnknownFailMap(params);
			this.writeLog(params, result, startTime);
			return result;
		}
	}

	/**
	 * 将用户信息存放在JavaBean中
	 * 
	 * @param result
	 * @return
	 */
	protected UserBean login(Map result)
	{
		UserBean user = null;
		if (result.size() > 0) {
			user = new UserBean();
			user.setId("" + result.get("SYS_SVR_USER_ID"));
			user.setName("" + result.get("USER_NAME"));
			user.setState("" + result.get("STATE"));
			user.setType("" + result.get("USER_TYPE"));
			user.setIs_ip_bind("" + result.get("IS_IP_BIND"));
			if (result.get("IP_BIND") == null) {
				user.setIp_bind("");
			} else {
				user.setIp_bind("" + result.get("IP_BIND"));
			}
			if (result.get("IS_LIMIT") == null) {
				user.setIs_limit("");
			} else {
				user.setIs_limit("" + result.get("IS_LIMIT"));
			}
		}
		return user;
	}

	protected ServiceBean queryService(String svrCode) throws DBException
	{
		String sql = SQLHelper.queryServiceSQL("svr_code", svrCode);
		Map map = dao.queryService(sql);
		if (map.get("SYS_SVR_SERVICE_ID") == null) {
			return null;
		}
		ServiceBean bean = new ServiceBean();
		bean.setId("" + map.get("SYS_SVR_SERVICE_ID"));
		bean.setName("" + map.get("SVR_NAME"));
		bean
				.setMax_records(new Integer("" + map.get("MAX_RECORDS"))
						.intValue());
		bean.setType("" + map.get("SVR_TYPE"));
		return bean;
	}

	protected void queryConfig(Map params) throws DBException
	{
		String sql = SQLHelper.queryConfigSQL(""
				+ params.get("SYS_SVR_USER_ID"), ""
				+ params.get("SYS_SVR_SERVICE_ID"));
		System.out.println("---queryConfig:--"+sql);
		Map map = dao.queryConfig(sql);
		if (map.get("SYS_SVR_CONFIG_ID") == null) {
			return;
		}
		if (map.get("QUERY_SQL")!=null) {
			if (map.get("QUERY_SQL").toString().indexOf("'%")==-1) {
				try {
					params.put("QUERY_SQL", java.net.URLDecoder.decode((String) map
							.get("QUERY_SQL"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					//params.put("QUERY_SQL", map.get("QUERY_SQL"));
				}
			}else{
				params.put("QUERY_SQL", map.get("QUERY_SQL"));
			}
		}
		params.put("SYS_SVR_CONFIG_ID", map.get("SYS_SVR_CONFIG_ID"));
		params.put("IS_PAUSE", map.get("IS_PAUSE"));

	}

	/*
	 * 查询该用户的所有用户参数
	 */
	protected List queryUserParams(Map params) throws DBException
	{
		String configId = "" + params.get("SYS_SVR_CONFIG_ID");
		String sql = SQLHelper.queryConfigParamSQL(configId, "1");
		List l = dao.queryConfigParam(sql);

		return l;
	}

	protected Map createNewMap(Map m)
	{
		HashMap newMap = new HashMap();
		if (m != null) {
			Iterator it = m.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				newMap.put(key, key);
			}
		}
		return newMap;
	}

	/**
	 * 检查用户是否提供了必须的参数
	 * 
	 * @param params
	 * @param userParams
	 * @return String
	 */
	protected String checkUserParams(Map params, List userParams)
	{
		if (userParams != null && userParams.size() > 0) {
			StringBuffer str = new StringBuffer();
			// 循环所有该提供的用户参数
			for (int i = 0; i < userParams.size(); i++) {
				Map m = (Map) userParams.get(i);
				// 条件连接符
				String op1 = m.get("OPERATOR1") == null ? null : ""
						+ m.get("OPERATOR1");
				// 左括号
				String lp = m.get("LEFT_PAREN") == null ? "" : ""
						+ m.get("LEFT_PAREN");
				// 运算符
				String op2 = "" + m.get("OPERATOR2");
				// 字段名
				String colName = "" + m.get("LEFT_COLUMN_NAME");
				System.out.println("应该提供参数-->" + colName);
				Object valueObj = null;
				System.out.println("用户提供了参数-->" + params.get(colName));
				if (params.get(colName) != null) {
					// 根据配置的用户参数字段名获得用户提供的参数值
					String value = String.valueOf(params.get(colName));
					// 查找分隔符，如果能找到，则表示为多个值
					int idx = value
							.indexOf(Constants.MULTI_COLUMN_CONDITION_SEPARATOR);
					// 判断是否为日期型的参数
					if (((String) m.get("EDIT_TYPE")).trim().equals(
							Constants.COLUMN_TYPE_DATE)) {
						if (idx > 0) {// 如果提供了多个日期，
							String[] dates = value
									.split(Constants.MULTI_COLUMN_CONDITION_SEPARATOR);
							// 检查所有值是否为合法日期
							for (int ii = 0; ii < dates.length; ii++) {
								if (!isValidDate(dates[ii])) {
									System.out.println("日期格式非法！");
									return Constants.SERVICE_FHDM_INPUT_PARAM_ERROR;
								}
							}
							// 检查日期顺序是否正确
							if (!isValidOrder(dates[0], dates[1])) {
								System.out.println("日期顺序非法！");
								return Constants.SERVICE_FHDM_INPUT_PARAM_ERROR;
							}

							if (dates.length == 2) {
								// System.out.println("第一个日期操作符："+op2);
								if (isMoreThan(op2)) {// 判断是否为“区间”操作符
									for (int j = 0; j < userParams.size(); j++) {
										if (j == i)
											continue;
										Map temp = (Map) userParams.get(j);
										// System.out.println(temp.get("LEFT_COLUMN_NAME")+"=="+colName+"="+(temp.get("LEFT_COLUMN_NAME").equals(colName)));
										if (temp.get("LEFT_COLUMN_NAME")
												.equals(colName)) {
											String tempOp = ""
													+ temp.get("OPERATOR2");
											// System.out.println("第二个操作符:"+tempOp);
											if (isLessThan(tempOp)) {// 判断是否也是区间操作符
												// 检查日期是否超过限定的查询区间
												if (!isValidRange(dates[0],
														dates[1])) {
													System.out
															.println("超过限定查询范围");
													return Constants.SERVICE_FHDM_OVER_DATE_RANGE;
												}
											} else {
												// System.out.println("第二个参数不是区间操作符！");
												break;
											}
										}
									}
								}
							}
							String tempStr = value.substring(idx + 1);
							valueObj = value.substring(0, idx);
							params.put(colName, tempStr);
						} else {
							// 检查是否为合法日期
							if (!isValidDate(value)) {
								return Constants.SERVICE_FHDM_INPUT_PARAM_ERROR;
							}
							valueObj = params.get(colName);
							params.remove(colName);
						}
					} else {
						if (idx != -1) {
							String tempStr = value.substring(idx + 1);
							valueObj = tempStr;
							params.put(colName, tempStr);
						} else {
							valueObj = params.get(colName);
							params.remove(colName);
						}
					}

				}
				// System.out.println("---->"+valueObj);
				boolean bol = (valueObj != null);
				String rp = m.get("RIGHT_PAREN") == null ? "" : ""
						+ m.get("RIGHT_PAREN");

				if (op1 != null) {
					str.append(parseToLogic(op1));
				}
				str.append(lp).append(bol).append(rp);
			}
			bsh.Interpreter interpreter = new bsh.Interpreter();
			try {
				interpreter.set("boolean", interpreter.eval(str.toString()));
				boolean success = ((Boolean) interpreter.get("boolean"))
						.booleanValue();
				if (success) {
					return Constants.SERVICE_FHDM_SUCCESS;
				}
				return Constants.SERVICE_FHDM_INPUT_PARAM_ERROR;
			} catch (EvalError e) {
				e.printStackTrace();
				return Constants.SERVICE_FHDM_INPUT_PARAM_ERROR;
			}
		} else {
			return Constants.SERVICE_FHDM_SUCCESS;
		}
	}

	public static void main(String[] args)
	{
		Map params = new HashMap();
		params.put("ENT_NAME", "长城|AA|VBB");
		params.put("ENT_DATE", "20080102,20080107");

		List l = new ArrayList();
		Map m = new HashMap();
		// m.put("OPERATOR1", "AND");
		m.put("LEFT_COLUMN_NAME", "ENT_NAME");
		m.put("OPERATOR2", "IN");
		m.put("EDIT_TYPE", "aa");
		l.add(m);
		m = new HashMap();
		m.put("OPERATOR1", "AND");
		m.put("LEFT_COLUMN_NAME", "ENT_DATE");
		m.put("OPERATOR2", ">");
		m.put("EDIT_TYPE", "2");
		l.add(m);
		m = new HashMap();
		m.put("OPERATOR1", "AND");
		m.put("LEFT_COLUMN_NAME", "ENT_DATE");
		m.put("OPERATOR2", ">");
		m.put("EDIT_TYPE", "2");
		l.add(m);

		new GSGeneralWebService().checkUserParams(params, l);
	}

	/**
	 * 校验日期格式是否合法
	 * 
	 * @param date
	 * @return
	 */
	protected boolean isValidDate(String date)
	{
		try {
			// Integer.parseInt(date);
			// 检查日期格式是否为正确的日期格式
			// Pattern p =
			// Pattern.compile("^(\\d{4}-([01][1-9])-([0-3]\\d{1}))$");
			// Matcher m = p.matcher(date);
			// if(!m.find()){
			// return false;
			// }
			DateFormat format = new SimpleDateFormat(Constants.DB_DATE_FORMAT);
			format.parse(date);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 检查开始日期是否在结束日期之前或相等
	 * 
	 * @param from
	 * @param to
	 * @return boolean
	 */
	private boolean isValidOrder(String from, String to)
	{
		if (from.trim().equals("") || to.trim().equals("")) {
			return false;
		}
		if (from.trim().equals(to)) {
			return false;
		}
		try {
			DateFormat format = new SimpleDateFormat(Constants.DB_DATE_FORMAT);
			Date d1 = format.parse(from);
			Date d2 = format.parse(to);

			return d1.before(d2);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean isMoreThan(String operator)
	{
		if (operator.trim().equals(">")) {
			return true;
		} else if (operator.trim().equals(">=")) {
			return true;
		}

		return false;
	}

	private boolean isLessThan(String operator)
	{
		if (operator.trim().equals("<")) {
			return true;
		} else if (operator.trim().equals("<=")) {
			return true;
		} else if (operator.trim().equals("BETWEEN_AND")) {
			return true;
		}

		return false;
	}

	/**
	 * 日期区间不得超过限定天数
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	private boolean isValidRange(String from, String to)
	{
		try {
			DateFormat format = new SimpleDateFormat(Constants.DB_DATE_FORMAT);
			Date d1 = format.parse(from);
			Date d2 = format.parse(to);
			long l1 = d1.getTime();
			long l2 = d2.getTime();
			if (d1.before(d2) || d1.equals(d2)) {// 如果开始日期在结束日期前，则判断是否大于限定天数
				return Constants.SERVICE_IN_PARAM_MAX_QUERY_DATE >= (l2 - l1);
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将字面关系转为对应的逻辑运算符
	 * 
	 * @param str
	 * @return String
	 */
	protected String parseToLogic(String str)
	{
		if (str.trim().equalsIgnoreCase("AND")) {
			return " && ";
		} else if (str.trim().equalsIgnoreCase("OR")) {
			return " || ";
		} else {
			return "";
		}
	}

	protected boolean checkMax(ServiceBean service, Map params)
	{
		// 获得服务配置的最大返回记录数
		int max = service.getMax_records();
		int ksjls = Integer.parseInt(""
				+ params.get(Constants.SERVICE_OUT_PARAM_KSJLS));
		int jsjls = Integer.parseInt(""
				+ params.get(Constants.SERVICE_OUT_PARAM_JSJLS));

		return (jsjls - ksjls) < max;
	}

	/**
	 * 检查“开始记录数”和“结束记录数”是否合法
	 * 
	 * @param service
	 * @param params
	 * @return String 是否验证通过的代码
	 */
	protected String checkRange(ServiceBean service, Map params)
	{
		int ksjls = 1;
		int jsjls = service.getMax_records();
		try {
			Object ksjlsObj = params.get(Constants.SERVICE_OUT_PARAM_KSJLS);
			Object jsjlsObj = params.get(Constants.SERVICE_OUT_PARAM_JSJLS);
			// System.out.println("开始记录数："+ksjlsObj +", 结束记录数："+jsjlsObj);
			if (ksjlsObj == null || jsjlsObj == null) {
				return Constants.SERVICE_FHDM_INPUT_PARAM_ERROR;
			}
			ksjls = Integer.parseInt((String) ksjlsObj);
			jsjls = Integer.parseInt((String) jsjlsObj);
			// 获得服务配置的最大返回记录数
			int max = service.getMax_records();
			// System.out.println("最大记录数："+max);
			// 开始和结束记录数不能为负数，并且结束记录数必须大于等于开始记录数并小于最大记录数
			if ((ksjls <= 0) || (jsjls <= 0) || (jsjls < ksjls)) {
				return Constants.SERVICE_FHDM_INPUT_PARAM_ERROR;
			}
			if ((jsjls - ksjls) >= max) {
				return Constants.SERVICE_FHDM_OVER_MAX;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			return Constants.SERVICE_FHDM_INPUT_PARAM_ERROR;
		}

		return Constants.SERVICE_FHDM_SUCCESS;
	}

	protected void prepare(TxnContext arg0) throws TxnException
	{
	}

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

	protected void writeLog(Map params, Map result, long startTime)
	{
		if (params.get("IS_TEST") == null) {
			String clientIp = getClientIp();
			// INSERT INTO sys_svr_log
			StringBuffer sql = new StringBuffer(
					" sys_svr_user_id,sys_svr_user_name,sys_svr_service_id,sys_svr_service_name,execute_start_time,execute_end_time,state,records_mount,error_msg,client_ip) VALUES (");
			sql.append("'").append(UuidGenerator.getUUID()).append("', ")
					.append("'").append(params.get("SYS_SVR_USER_ID")).append(
							"', ").append("'").append(params.get("USER_NAME"))
					.append("', ").append("'").append(
							params.get("SYS_SVR_SERVICE_ID")).append("', ")
					.append("'").append(params.get("SVR_NAME")).append("', ")
					.append("'").append(
							StringUtil.formatDateToString(new Date(startTime),
									"yyyy-MM-dd HH:mm:ss")).append("', ")
					.append("'").append(
							StringUtil.formatDateToString(new Date(),
									"yyyy-MM-dd HH:mm:ss")).append("', ");
			String fhdm = result.get("FHDM").toString();
			sql
					.append("'")
					.append(
							(fhdm.trim().equalsIgnoreCase(
									Constants.SERVICE_FHDM_SUCCESS) ? "成功"
									: "失败"))
					.append("', ")
					.append("")
					.append(
							result.get("GSDJ_INFO_ARRAY") == null ? "0"
									: ""
											+ ((Map[]) result
													.get("GSDJ_INFO_ARRAY")).length)
					.append(", ").append("'").append(result.get("FHDM"))
					.append("', ").append("'").append(clientIp).append("')");

			try {
				String sql1 = "INSERT INTO sys_svr_log (sys_svr_log_id, " + sql;
				String sql2 = "INSERT INTO sys_svr_visit (sys_svr_visit_id, "
						+ sql;
				dao.writeLog(sql1);
				dao.writeLog(sql2);
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
	}

	protected void writeLock(Map params, String lock_code, String lock_desp,
			long startTime)
	{
		if (params.get("IS_TEST") == null) {
			System.out.println(" 执行writeLock方法   "
					+ params.get("SYS_SVR_USER_ID"));
			// INSERT INTO sys_svr_log
			StringBuffer sql = new StringBuffer(
					"(sys_svr_lock_id,sys_svr_user_ID,sys_svr_service_id,lock_code,lock_desp,lock_time) VALUES (");
			sql.append("'").append(UuidGenerator.getUUID()).append("', ")
					.append("'").append(params.get("SYS_SVR_USER_ID")).append(
							"', ").append("'").append(
							params.get("SYS_SVR_SERVICE_ID")).append("', ")
					.append("'").append(lock_code).append("', ").append("'")
					.append(lock_desp).append("', ").append("'").append(
							StringUtil.formatDateToString(new Date(startTime),
									"yyyy-MM-dd HH:mm:ss")).append("') ");
			try {
				dao.writeLock("  INSERT INTO sys_svr_lock  " + sql.toString());
				dao.writeLock("  INSERT INTO sys_svr_lock_HS  "
						+ sql.toString());
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 验证访问时间是否在规定的时间范围内 时间格式 15:00 24:60
	 * 
	 * @param st
	 *            允许起始时间
	 * @param et
	 *            允许截止时间
	 * @param ct
	 *            当前访问时间
	 * @return
	 * @author jufeng 2012-07-08
	 */
	protected boolean validTime(String st, String et, String ct)
	{
		try {
			long stl = Long.valueOf(st.replaceAll("\\:", "0")).longValue();
			long etl = Long.valueOf(et.replaceAll("\\:", "0")).longValue();
			long ctl = Long.valueOf(ct.replaceAll("\\:", "0")).longValue();
			// System.out.println("转换的时间计算为 : 开始时间"+ stl+ " 结束时间"+ etl +" 当前时间 "
			// + ctl+ " --计算公式为 ： (ctl>=stl&&ctl<=etl) " +
			// (ctl>=stl&&ctl<=etl));
			if (ctl >= stl && ctl <= etl) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
