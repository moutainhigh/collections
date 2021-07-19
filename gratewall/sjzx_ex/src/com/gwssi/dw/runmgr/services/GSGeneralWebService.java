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
		// ����ʼʱ��
		Date date = new Date();
		long startTime = date.getTime();
		String is_limit_total = null;
		String limit_total = null;
		// DC2-jufeng-2012-07-07
		// ��������JDK����ʱ��ʱ������ -Duser.timezone=GMT+8 �������ڻ�ȡ��,��Ҫ������ϵͳ����֤������
		String today = CalendarUtil.getDayofWeek(date);
		// / System.out.println("��ǰʱ���� "+startTime + " ���� "+today);
		// System.out.println("{48 line of GSGeneralWebService} ����ʼ���÷��� ");
		Map userMap = null;
		UserBean user = null;
		try {
			// ����û�IDΪ�գ����ǵ���webservice����Ҫ��֤�û���������
			if (params.get("SYS_SVR_USER_ID") == null) {
				// ���ȼ���Ƿ��ṩ�˵�¼����
				if ((params.get(Constants.SERVICE_IN_PARAM_LOGIN_NAME) == null)
						|| (params
								.get(Constants.SERVICE_IN_PARAM_LOGIN_PASSWORD) == null)) {
					return ResultParser.createLoginFailMap(params);
				}
				try {
					// DC2-jufeng ��֤�û��˺ź�������ȷ
					String sql = SQLHelper.loginSQL(""+ params.get(Constants.SERVICE_IN_PARAM_LOGIN_NAME),
									""+ params.get(Constants.SERVICE_IN_PARAM_LOGIN_PASSWORD));
					userMap = dao.queryUser(sql);
				} catch (DBException e) {
					e.printStackTrace();
					return ResultParser.createUnknownFailMap(params);
				}
				user = this.login(userMap);
				// ����Ƿ�ɹ���¼
				if (user == null) {
					// �����û��������벻��ȷ��
					return ResultParser.createLoginFailMap(params);
				} else if (Integer.parseInt(user.getState()) != 0) {
					// �����û�״̬Ϊͣ�ã�
					return ResultParser.createLoginFailMap(params);
				} else if (user.getIs_ip_bind() != null
						&& !user.getIs_ip_bind().equals("")
						&& !user.getIs_ip_bind().equals("0")) {
					// DC2-jufeng-2012-07-07
					// ���û�����Ҫ��IPʱ������֤
					String ip_bind = null;
					if (user.getIp_bind() != null
							&& !user.getIp_bind().equals("")) {
						// �󶨵�IP��Ϣ����
						ip_bind = ";" + user.getIp_bind() + ";";
						String ip = ";" + this.getClientIp() + ";";
						if (ip_bind.indexOf(ip) < 0) {
							// �û���ǰIP���ڰ󶨵�IP��Ϣ��
							return ResultParser.createParamErrorMap(params,
									Constants.SERVICE_FHDM_ERROR_IP);
						}
					} else {
						// �󶨵�IP��Ϣ������
						return ResultParser.createParamErrorMap(params,
								Constants.SERVICE_FHDM_ERROR_IP);
					}
				}

				// �����û���Ϣ
				params.put("SYS_SVR_USER_ID", user.getId());
				params.put("USER_NAME", user.getName());// ���ڼ�¼��־
			}
			// ����Ϊ���ԣ�����Ҫ��֤�û�����
			else {
				params.put("IS_TEST", "1");
				userMap = dao
						.queryUser("SELECT sys_svr_user_id, user_name from sys_svr_user WHERE sys_svr_user_id='"
								+ params.get("SYS_SVR_USER_ID") + "'");
				if (userMap != null) {
					params.put("USER_NAME", userMap.get("USER_NAME"));// ���ڼ�¼��־
				}
			}
			// -----------�������úͲ��Թ��õ���֤��ʼ--------------

			// ����Ƿ��ṩ�˷���������
			if (params.get(Constants.SERVICE_IN_PARAM_SERVICE_CODE) == null) {
				System.out.println("����δ�ṩ������룡");
				return ResultParser.createParamErrorMap(params,
						Constants.SERVICE_FHDM_INPUT_PARAM_ERROR);
			}
			// ���ݷ�������þ��������Ϣ
			ServiceBean bean = this.queryService(""
					+ params.get(Constants.SERVICE_IN_PARAM_SERVICE_CODE));
			// ����������Ƿ���ȷ
			if (bean == null) {
				System.out.println("���󣡷���������");
				return ResultParser.createParamErrorMap(params,
						Constants.SERVICE_FHDM_INPUT_PARAM_ERROR);
			}

			// ���������Ϣ
			params.put("SYS_SVR_SERVICE_ID", bean.getId());
			params.put("SVR_NAME", bean.getName());// ���ڼ�¼��־

			String returnCode = checkRange(bean, params);
			if (!returnCode.equalsIgnoreCase(Constants.SERVICE_FHDM_SUCCESS)) {
				System.out.println("���󣡿�ʼ����������");
				Map result = ResultParser.createParamErrorMap(params,
						returnCode);
				this.writeLog(params, result, startTime);
				return result;
			}
			// -----------�������úͲ��Թ��õ���֤����--------------

			// �����û�ID�ͷ���ID��ѯ����
			this.queryConfig(params);
			// ���Ϊwebservice
			// DC2-jufeng-2012-07-07
			// ����û����Ʒ������ڡ�ʱ�䡢��������������������һ�����ã� �������·�֧
			// &&user.getIs_limit()!=null&&!user.getIs_limit().equals("")&&user.getIs_limit().equals("1")
			if (user != null && params.get("IS_TEST") == null) {
				System.out.println("-----����webservice------");
				// �������IDΪ�գ���˵�����û��޴˷���
				if (params.get("SYS_SVR_CONFIG_ID") == null) {
					System.out.println("���󣡷���δ���ø��û���");
					Map result = ResultParser.createParamErrorMap(params,
							Constants.SERVICE_FHDM_INPUT_PARAM_ERROR);
					this.writeLog(params, result, startTime);
					return result;
				} else {
					System.out.println("---is_pause----" + params.get("IS_PAUSE"));
					if (params.get("IS_PAUSE").equals("1")) {
						System.out.println("���󣡷�������ͣ��");
						Map result = ResultParser.createParamErrorMap(params,
								Constants.SERVICE_FHDM_SERVICE_PAUSE);
						this.writeLog(params, result, startTime);
						return result;
					}
				}
				// DC2-jufeng-2012-07-08
				// �жϸ��û��Ƿ��ڵ����û�������ʼ������У������ֱ�ӷ��ز���¼��־
				String sqlLock = SQLHelper.getLocked(""
						+ params.get("SYS_SVR_USER_ID"), ""
						+ params.get("SYS_SVR_SERVICE_ID"));
				Map mapLock = dao.queryLocked(sqlLock);
				System.out.println("��ѯ�Ƿ������ mapLock ��   " + mapLock);
				if (mapLock != null && !mapLock.isEmpty()) {
					System.out.println("�����û������޷��ʣ�");
					Map result = ResultParser.createParamErrorMap(params,
							Constants.SERVICE_FHDM_LOCKED_TODAY);
					this.writeLog(params, result, startTime);
					return result;
				}

				// ��ȡ�û�ĳ�������������Ϣ
				String sql = SQLHelper.getSysLimitSQL(""
						+ params.get("SYS_SVR_USER_ID"), ""
						+ params.get("SYS_SVR_SERVICE_ID"));
				List limitList = dao.querySysLimit(sql);
				String errorCode = null;
				if (limitList != null || limitList.size() > 0) {
					// Ŀǰ��������ֻ���������ڼ�����ÿ����޶�ʱ��Σ��޶����ʴ������޶�����������ͬ��
					// ����ȡ��һ���Ϳ��ԣ������ݿ�ṹ֧��ÿ�춼��ͬ��
					String currentTime = StringUtil.formatDateToString(
							new Date(startTime), "HH:mm");
					for (int j = 0; j < limitList.size(); j++) {
						HashMap map = (HashMap) limitList.get(j);
						String limit_week = (String) map.get("LIMIT_WEEK");
						// System.out.println("map"+j+"�� limit_week is "+
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

							// (1) ��֤�����Ƿ��������
							if (is_limit_week.equals("1")) {
								// �������Ʒ���
								errorCode = Constants.SERVICE_FHDM_LOCK_WEEK;
								System.out.println("�����������");
								// ���û����ʸ÷������� �����û������������
								this.writeLock(params,
										Constants.SERVICE_FHDM_LOCK_WEEK,
										"�����������", startTime);
								break;
							}
							// (2)��֤��ǰʱ���Ƿ��������
							if (is_limit_time.equals("1")) {
								// System.out.println("����ʱ��У��
								// "+"limit_start_time is "+ limit_start_time +
								// " limit_end_time is " + limit_end_time + "
								// currentTime is " + currentTime);
								if (!this.validTime(limit_start_time,
										limit_end_time, currentTime)) {
									// ��ǰ����ʱ�䲻����
									errorCode = Constants.SERVICE_FHDM_LOCK_TIME;
									System.out.println("�������ʱ������ ");
									// ����ʱ����󲻷����������!!!
									// this.writeLock(params,
									// Constants.SERVICE_FHDM_LOCK_TIME,
									// "�������ʱ������", startTime);
									break;
								}
							}
							// (3)��֤���ʴ����Ƿ񳬹�
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
									System.out.println("���������ʴ���Ϊ " + maxc
											+ " ��ǰ���ʴ���Ϊ " + cc);
								} catch (Exception e) {
									System.out.println("�жϵ��շ��ʴ���ʱ��������");
									Map result = ResultParser
											.createParamErrorMap(
													params,
													Constants.SERVICE_FHDM_UNKNOWN_ERROR);
									this.writeLog(params, result, startTime);
									return result;
								}
								if (maxc == cc) {
									errorCode = Constants.SERVICE_FHDM_LOCK_NUMBER;
									System.out.println("������ʴ�������");
									// ���û����ʸ÷������� �����û������������
									this.writeLock(params,
											Constants.SERVICE_FHDM_LOCK_NUMBER,
											"������ʴ�������", startTime);
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

			// ���ò�������ִ��webservice
			List l = this.queryUserParams(params);
			if (params instanceof HashMap) {
				// ���ڼ�����ʱ��ı�map��ֵ���ʴ�һ���µ�map
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
			// ת����������
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
			// ��÷���
			GeneralService service = ServiceFactory.getService(bean.getType());
			// ���ؽ��
			Map result = service.query(params);

			// ���Ϊ����webservice ��Ҫ��֤��ǰ���������Ƿ�����
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
						System.out.println("��֤�û���������Ƿ񳬹������������������");
						Map result2 = ResultParser.createParamErrorMap(params,
								Constants.SERVICE_FHDM_UNKNOWN_ERROR);
						this.writeLog(params, result2, startTime);
						return result2;
					}
					if (sumMax < (sum_this + sum_hs)) {

						System.out.println("�û����������������������"
								+ " �ж����Ƶ������� sumMax<(sum_this+sum_hs) "
								+ (sumMax < (sum_this + sum_hs)) + " sumMax is"
								+ sumMax + " sum_hs is " + sum_hs
								+ " sum_this is " + sum_this);
						Map result2 = ResultParser.createParamErrorMap(params,
								Constants.SERVICE_FHDM_LOCK_TOTAL);
						this.writeLog(params, result2, startTime);
						this.writeLock(params,
								Constants.SERVICE_FHDM_LOCK_TOTAL,
								"�û����������������������", startTime);
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
	 * ���û���Ϣ�����JavaBean��
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
	 * ��ѯ���û��������û�����
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
	 * ����û��Ƿ��ṩ�˱���Ĳ���
	 * 
	 * @param params
	 * @param userParams
	 * @return String
	 */
	protected String checkUserParams(Map params, List userParams)
	{
		if (userParams != null && userParams.size() > 0) {
			StringBuffer str = new StringBuffer();
			// ѭ�����и��ṩ���û�����
			for (int i = 0; i < userParams.size(); i++) {
				Map m = (Map) userParams.get(i);
				// �������ӷ�
				String op1 = m.get("OPERATOR1") == null ? null : ""
						+ m.get("OPERATOR1");
				// ������
				String lp = m.get("LEFT_PAREN") == null ? "" : ""
						+ m.get("LEFT_PAREN");
				// �����
				String op2 = "" + m.get("OPERATOR2");
				// �ֶ���
				String colName = "" + m.get("LEFT_COLUMN_NAME");
				System.out.println("Ӧ���ṩ����-->" + colName);
				Object valueObj = null;
				System.out.println("�û��ṩ�˲���-->" + params.get(colName));
				if (params.get(colName) != null) {
					// �������õ��û������ֶ�������û��ṩ�Ĳ���ֵ
					String value = String.valueOf(params.get(colName));
					// ���ҷָ�����������ҵ������ʾΪ���ֵ
					int idx = value
							.indexOf(Constants.MULTI_COLUMN_CONDITION_SEPARATOR);
					// �ж��Ƿ�Ϊ�����͵Ĳ���
					if (((String) m.get("EDIT_TYPE")).trim().equals(
							Constants.COLUMN_TYPE_DATE)) {
						if (idx > 0) {// ����ṩ�˶�����ڣ�
							String[] dates = value
									.split(Constants.MULTI_COLUMN_CONDITION_SEPARATOR);
							// �������ֵ�Ƿ�Ϊ�Ϸ�����
							for (int ii = 0; ii < dates.length; ii++) {
								if (!isValidDate(dates[ii])) {
									System.out.println("���ڸ�ʽ�Ƿ���");
									return Constants.SERVICE_FHDM_INPUT_PARAM_ERROR;
								}
							}
							// �������˳���Ƿ���ȷ
							if (!isValidOrder(dates[0], dates[1])) {
								System.out.println("����˳��Ƿ���");
								return Constants.SERVICE_FHDM_INPUT_PARAM_ERROR;
							}

							if (dates.length == 2) {
								// System.out.println("��һ�����ڲ�������"+op2);
								if (isMoreThan(op2)) {// �ж��Ƿ�Ϊ�����䡱������
									for (int j = 0; j < userParams.size(); j++) {
										if (j == i)
											continue;
										Map temp = (Map) userParams.get(j);
										// System.out.println(temp.get("LEFT_COLUMN_NAME")+"=="+colName+"="+(temp.get("LEFT_COLUMN_NAME").equals(colName)));
										if (temp.get("LEFT_COLUMN_NAME")
												.equals(colName)) {
											String tempOp = ""
													+ temp.get("OPERATOR2");
											// System.out.println("�ڶ���������:"+tempOp);
											if (isLessThan(tempOp)) {// �ж��Ƿ�Ҳ�����������
												// ��������Ƿ񳬹��޶��Ĳ�ѯ����
												if (!isValidRange(dates[0],
														dates[1])) {
													System.out
															.println("�����޶���ѯ��Χ");
													return Constants.SERVICE_FHDM_OVER_DATE_RANGE;
												}
											} else {
												// System.out.println("�ڶ����������������������");
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
							// ����Ƿ�Ϊ�Ϸ�����
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
		params.put("ENT_NAME", "����|AA|VBB");
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
	 * У�����ڸ�ʽ�Ƿ�Ϸ�
	 * 
	 * @param date
	 * @return
	 */
	protected boolean isValidDate(String date)
	{
		try {
			// Integer.parseInt(date);
			// ������ڸ�ʽ�Ƿ�Ϊ��ȷ�����ڸ�ʽ
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
	 * ��鿪ʼ�����Ƿ��ڽ�������֮ǰ�����
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
	 * �������䲻�ó����޶�����
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
			if (d1.before(d2) || d1.equals(d2)) {// �����ʼ�����ڽ�������ǰ�����ж��Ƿ�����޶�����
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
	 * �������ϵתΪ��Ӧ���߼������
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
		// ��÷������õ���󷵻ؼ�¼��
		int max = service.getMax_records();
		int ksjls = Integer.parseInt(""
				+ params.get(Constants.SERVICE_OUT_PARAM_KSJLS));
		int jsjls = Integer.parseInt(""
				+ params.get(Constants.SERVICE_OUT_PARAM_JSJLS));

		return (jsjls - ksjls) < max;
	}

	/**
	 * ��顰��ʼ��¼�����͡�������¼�����Ƿ�Ϸ�
	 * 
	 * @param service
	 * @param params
	 * @return String �Ƿ���֤ͨ���Ĵ���
	 */
	protected String checkRange(ServiceBean service, Map params)
	{
		int ksjls = 1;
		int jsjls = service.getMax_records();
		try {
			Object ksjlsObj = params.get(Constants.SERVICE_OUT_PARAM_KSJLS);
			Object jsjlsObj = params.get(Constants.SERVICE_OUT_PARAM_JSJLS);
			// System.out.println("��ʼ��¼����"+ksjlsObj +", ������¼����"+jsjlsObj);
			if (ksjlsObj == null || jsjlsObj == null) {
				return Constants.SERVICE_FHDM_INPUT_PARAM_ERROR;
			}
			ksjls = Integer.parseInt((String) ksjlsObj);
			jsjls = Integer.parseInt((String) jsjlsObj);
			// ��÷������õ���󷵻ؼ�¼��
			int max = service.getMax_records();
			// System.out.println("����¼����"+max);
			// ��ʼ�ͽ�����¼������Ϊ���������ҽ�����¼��������ڵ��ڿ�ʼ��¼����С������¼��
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
									Constants.SERVICE_FHDM_SUCCESS) ? "�ɹ�"
									: "ʧ��"))
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
			System.out.println(" ִ��writeLock����   "
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
	 * ��֤����ʱ���Ƿ��ڹ涨��ʱ�䷶Χ�� ʱ���ʽ 15:00 24:60
	 * 
	 * @param st
	 *            ������ʼʱ��
	 * @param et
	 *            �����ֹʱ��
	 * @param ct
	 *            ��ǰ����ʱ��
	 * @return
	 * @author jufeng 2012-07-08
	 */
	protected boolean validTime(String st, String et, String ct)
	{
		try {
			long stl = Long.valueOf(st.replaceAll("\\:", "0")).longValue();
			long etl = Long.valueOf(et.replaceAll("\\:", "0")).longValue();
			long ctl = Long.valueOf(ct.replaceAll("\\:", "0")).longValue();
			// System.out.println("ת����ʱ�����Ϊ : ��ʼʱ��"+ stl+ " ����ʱ��"+ etl +" ��ǰʱ�� "
			// + ctl+ " --���㹫ʽΪ �� (ctl>=stl&&ctl<=etl) " +
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
