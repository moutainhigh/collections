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
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�CheckService �����������������Ϣ �����ˣ�lizheng ����ʱ�䣺Apr 27, 2013
 * 4:44:47 PM �޸��ˣ�lizheng �޸�ʱ�䣺Apr 27, 2013 4:44:47 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class CheckService
{
	ServiceDAO	dao	= null; // �������ݿ�Dao

	public CheckService()
	{
		dao = new ServiceDAOImpl();
	}

	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(CheckService.class
											.getName());

	/**
	 * 
	 * checkServiceCanBeUsed �������Ƿ���ʹ��
	 * 
	 * @param serviceCode
	 * @param param
	 * @param shareLogVo
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected Map checkServiceCanBeUsed(String serviceCode, Map param,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		Long start = System.currentTimeMillis();
		// �ж��Ƿ��ǲ����û�
		if (param.containsKey("USER_TYPE")
				&& "TEST".equals(param.get("USER_TYPE"))) {
			logger.debug("���û�Ϊ�����û����ü���...");

			// ���ü�¼��־��Ϣ
			shareLogVo.setLog_type(ExConstant.LOG_TYPE_TEST);
			this.queryLoginInfoTest(serviceCode, param, shareLogVo, serviceId);
			this.queryServiceStateTest(serviceCode, shareLogVo);
			return ResultParser.createSuccuseMap();
		} else {
			logger.debug("���û�Ҫ����...");
			// ���ü�¼��־��Ϣ
			shareLogVo.setLog_type(ExConstant.LOG_TYPE_USER);
			// 1.����������¼��Ϣ�Ƿ���ȷ,ip���Ƿ���ȷ
			Map loginMap = this.queryLoginInfo(serviceCode, param, shareLogVo,
					serviceId);
			// 2.������,����״̬�Ƿ���ȷ
			Map serStateMap = this.queryServiceState(serviceCode, shareLogVo);
			if ("Y".equals(loginMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				if ("Y".equals(serStateMap.get(
						ShareConstants.SERVICE_CAN_BE_USED).toString())) {
					// 3.�������ʱ���Ƿ���ȷ
					Map serDateMap = this.querySvrDate(DateUtil.getToday());
					if ("Y".equals(serDateMap.get(
							ShareConstants.SERVICE_CAN_BE_USED).toString())) {
						// 4.����������Ƿ���ȷ
						try {
							Map serRuleMap = this.queryServiceRule(serviceCode);
							if ("Y".equals(serDateMap.get(
									ShareConstants.SERVICE_CAN_BE_USED)
									.toString())) {
								// 5.�������Ƿ���ȷ
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
	 * queryLoginInfo ����������¼��Ϣ�Ƿ���ȷ
	 * 
	 * @param serviceCode
	 * @param param
	 * @param shareLogVo
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected Map queryLoginInfo(String serviceCode, Map param,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		logger.debug("��ʼ�����û���Ϣ...");
		// ��ȡ���ʷ�����û���Ϣ
		String sql = SQLHelper.getLoginSQL(serviceId);
		Map loginMap = dao.queryLoginInfo(sql);

		if (loginMap.isEmpty()) {
			logger.debug("�û���Ч...");
			return ResultParser.createUserFailMap();
		} else {
			VoResServiceTargets v = new VoResServiceTargets();
			ParamUtil.mapToBean(loginMap, v, false);

			String svrName = param.get("LOGIN_NAME").toString();
			String svrPwd = param.get("PASSWORD").toString();
			String svrStatus = v.getService_status();
			logger.debug("�û���Ϊ:" + svrName);
			logger.debug("����Ϊ:" + svrPwd);
			logger.debug("�û���Ч״̬Ϊ:" + svrStatus);

			// ���ü�¼��־��Ϣ
			shareLogVo.setService_targets_id(v.getService_targets_id()); // �������ID
			shareLogVo.setService_targets_name(v.getService_targets_name()); // �����������
			shareLogVo.setTargets_type(v.getService_targets_type()); // �����������
			String serviceStatus = v.getService_status();

			if (ExConstant.IS_MARKUP_Y.equals(serviceStatus)) {
				// �ж��û���Ϣ
				if (ExConstant.IS_MARKUP_Y.equals(svrStatus)) {
					if (StringUtils.isBlank(svrName)
							|| !svrName.equals(v.getService_targets_no())) {
						logger.debug("�û�������...");
						return ResultParser.createUserErrorlMap();
					} else if (StringUtils.isBlank(svrPwd)
							|| !v.getService_password().equals(svrPwd)) {
						logger.debug("�������...");
						return ResultParser.createPwdErrorlMap();
					} else if (svrName.equals(v.getService_targets_no())
							&& svrPwd.equals(v.getService_password())) {
						logger.debug("�û����������ɹ�...");
						return ResultParser.createSuccuseMap();
					} else if (!queryServiceIPInfo(v, shareLogVo)) {
						logger.debug("IP����...");
						return ResultParser.createServiceIPFailMap();
					} else {
						logger.debug("�û�����...");
						return ResultParser.createLoginFailMap();
					}
				} else {
					logger.debug("�û���Ч...");
					return ResultParser.createUserFailMap();
				}
			} else {
				logger.debug("�û���Ч...");
				return ResultParser.createUserFailMap();
			}

		}
	}

	/**
	 * 
	 * queryLoginInfoTest ��������¼��Ϣ
	 * 
	 * @param serviceCode
	 * @param param
	 * @param shareLogVo
	 * @throws DBException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected void queryLoginInfoTest(String serviceCode, Map param,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		logger.debug("��ʼ�����û���Ϣ...");
		// ��ȡ���ʷ�����û���Ϣ
		String sql = SQLHelper.getLoginSQL(serviceCode);
		Map loginMap = dao.queryLoginInfo(sql);

		VoResServiceTargets v = new VoResServiceTargets();
		ParamUtil.mapToBean(loginMap, v, false);

		// ���ü�¼��־��Ϣ
		shareLogVo.setService_targets_id(v.getService_targets_id()); // �������ID
		shareLogVo.setService_targets_name(v.getService_targets_name()); // �����������
		shareLogVo.setTargets_type(v.getService_targets_type()); // �����������

	}

	/**
	 * 
	 * queryServiceIPInfo ����ip���Ƿ���ȷ
	 * 
	 * @param v
	 * @param shareLogVo
	 * @return
	 * @throws DBException
	 *             boolean
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected boolean queryServiceIPInfo(VoResServiceTargets v,
			ShareLogVo shareLogVo) throws DBException
	{
		logger.debug("��ʼ����IP��Ϣ...");
		boolean ipFlag = false;
		String isBindIP = v.getIs_bind_ip();
		if (StringUtils.isNotBlank(isBindIP) // �����ip
				&& ExConstant.IS_BIND_IP_Y.equals(isBindIP)) {
			String clientIP = getClientIp();
			String[] ips = v.getIp().split(",");
			for (int ii = 0; ii < ips.length; ii++) {
				if (clientIP.equals(ips[ii])) {
					ipFlag = true;
					logger.debug("����IP���...");
					break; // ip���ͨ��
				}
			}
		} else {
			logger.debug("û�а�ip,����IP���...");
			ipFlag = true; // û�а�ip
		}
		return ipFlag;
	}

	/**
	 * 
	 * queryServiceState �������״̬
	 * 
	 * @param serviceCode
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected Map queryServiceState(String serviceCode, ShareLogVo shareLogVo)
			throws DBException
	{
		logger.debug("��ʼ�������״̬...");
		// ��ȡ����״̬
		String sql = SQLHelper.queryServiceSQL(serviceCode);
		Map svrStateMap = dao.queryServiceState(sql);

		if (svrStateMap.isEmpty()) {
			logger.debug("û���ҵ��÷���...");
			return ResultParser.createSvrNotFoundMap();
		} else {
			VoShareService v = new VoShareService();
			ParamUtil.mapToBean(svrStateMap, v, false);
			// ���ü�¼��־��Ϣ
			shareLogVo.setService_id(v.getService_id()); // ����ID
			shareLogVo.setService_name(v.getService_name()); // ��������
			shareLogVo.setService_type(v.getService_type()); // ��������
			if (ExConstant.IS_MARKUP_Y.equals(v.getIs_markup())
					&& ExConstant.SERVICE_STATE_Y.equals(v.getService_state())) {
				// ˵���˷����ܹ��ṩ����
				logger.debug("�÷�������...");
				return ResultParser.createSuccuseMap();
			} else {
				logger.debug("�÷���״̬Ϊ���ɷ���...");
				return ResultParser.createServiceStateFailMap();
			}
		}
	}

	/**
	 * 
	 * queryServiceStateTest ����״̬
	 * 
	 * @param serviceCode
	 * @param shareLogVo
	 * @throws DBException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected void queryServiceStateTest(String serviceCode,
			ShareLogVo shareLogVo) throws DBException
	{
		// ��ȡ����״̬
		String sql = SQLHelper.queryServiceSQL(serviceCode);
		Map svrStateMap = dao.queryServiceState(sql);

		VoShareService v = new VoShareService();
		ParamUtil.mapToBean(svrStateMap, v, false);

		// ���ü�¼��־��Ϣ
		shareLogVo.setService_id(v.getService_id()); // ����ID
		shareLogVo.setService_name(v.getService_name()); // ��������
		shareLogVo.setService_type(v.getService_type()); // ��������

	}

	/**
	 * 
	 * querySvrDate �������ʱ��
	 * 
	 * @param svrDate
	 * @return
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected Map querySvrDate(String svrDate) throws DBException
	{
		logger.debug("��ʼ�������ʱ��...");
		if (StringUtils.isNotEmpty(svrDate)) {
			String sql = SQLHelper.getServiceDateSQL(svrDate);
			String dateString = dao.queryServiceDate(sql);

			if (StringUtils.isNotEmpty(dateString) && "0".equals(dateString)) {
				// ���������ڱ���û�в鵽���ռ�¼,˵�����շ������������
				logger.debug("����Ϊ���������������ʷ���...");
				return ResultParser.createSuccuseMap();
			} else {
				logger.debug("����Ϊ�żٷ�ֹͣ����...");
				return ResultParser.createServiceDateFailMap();
			}
		} else {
			logger.debug("����ʱ����BUG����δ��������ʱ��...");
			return ResultParser.createServiceDateFailMap();
		}

	}

	/**
	 * 
	 * queryServiceRule ��ѯ������ʹ���
	 * 
	 * @param serviceCode
	 * @return
	 * @throws DBException
	 * @throws ParseException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected Map queryServiceRule(String serviceCode) throws DBException,
			ParseException
	{
		logger.debug("��ʼ������ʹ���...");
		String sql = SQLHelper.getServiceRuleSQL(serviceCode);
		Map svrRuleMap = dao.queryServiceRule(sql);

		if (svrRuleMap.isEmpty()) {
			logger.debug("�÷���δ���÷��ʹ��򣬿�������...");
			return ResultParser.createSuccuseMap();
		} else {
			// �����˷��ʹ��򣬼����ʹ���
			VoShareServiceRule v = new VoShareServiceRule();
			ParamUtil.mapToBean(svrRuleMap, v, false);

			String startTime = v.getStart_time(); // ������ʿ�ʼʱ��
			String endTime = v.getEnd_time(); // ������ʽ���ʱ��

			if (StringUtils.isNotEmpty(startTime)
					&& StringUtils.isNotEmpty(endTime)) {

				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				String calNow = sd.format(new java.util.Date()); // ϵͳ��ǰ������
				startTime = calNow + " " + startTime + ":00";
				endTime = calNow + " " + endTime + ":00";

				SimpleDateFormat sDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String calNowtime = sDateFormat.format(new java.util.Date()); // ϵͳ��ǰ������ʱ����
				boolean isIn = isInDates(calNowtime.toString(), startTime,
						endTime);// �Ƚ�ϵͳ��ǰʱ��͹������ʼʱ��

				logger.debug("����������ʿ�ʼʱ��Ϊ��" + startTime);
				logger.debug("��ǰϵͳʱ��Ϊ��" + calNowtime);
				logger.debug("����������ʽ���ʱ��Ϊ��" + endTime);

				if (isIn) {
					// ��������ʵ�ʱ����
					logger.debug("��������ʵ�ʱ����...");
					sql = SQLHelper.getServiceLogSQL(serviceCode);
					Map logMap = dao.queryServiceLog(sql); // ��ѯ��������˶��ٴ�

					if (logMap.isEmpty()) {
						logger.debug("�÷�����컹û�б����ʹ�...");
						return ResultParser.createSuccuseMap();
					} else {
						int times = Integer.parseInt(logMap.get("TIMES")
								.toString());
						logger.debug("�÷�����챻���ʹ�[" + times + "]��...");

						int amount = Integer.parseInt(logMap.get("AMOUNT")
								.toString());
						logger.debug("�÷�����칲��ȡ����[" + amount + "]��...");

						int times_day = Integer
								.parseInt(v.getTimes_day() == null ? "0" : v
										.getTimes_day());
						logger.debug("�÷�������������[" + times_day
								+ "]��,�����0��˵��û�����ô�������...");
						if (times < times_day || times_day == 0) {

							int amount_day = Integer.parseInt(v
									.getTotal_count_day() == null ? "0" : v
									.getTotal_count_day());
							logger.debug("�÷�����칲��ȡ����[" + amount
									+ "]��,�����0��˵��û����������������...");

							if (amount < amount_day || amount_day == 0) {
								// ����С�����õĹ�������
								logger.debug("������ʹ���ͨ��...");
								return ResultParser.createSuccuseMap();
							} else {
								logger.debug("�������������������...");
								return ResultParser.createSvrTotalErrorMap();
							}
						} else {
							logger.debug("��������������ʴ���...");
							return ResultParser.createSvrNumErrorMap();
						}
					}
				} else {
					logger.debug("��ǰʱ�䲻�ڷ����������ʱ���ڣ�" + startTime);
					return ResultParser.createSvrTimeErrorMap();
				}
			} else {
				logger.debug("ϵͳ����...");
				return ResultParser.createSystemErrorMap();
			}
		}
	}

	/**
	 * 
	 * getClientIp ��ȡ�ͻ���IP
	 * 
	 * @return String
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
	 * isInDates �ж�ʱ���Ƿ���һ��ʱ��������
	 * 
	 * @param strDate
	 * @param strDateBegin
	 * @param strDateEnd
	 * @return boolean
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private boolean isInDates(String strDate, String strDateBegin,
			String strDateEnd)
	{
		// ���ڻ��ߵ��ڿ�ʼʱ��
		// ����
		// С�ڻ��ߵ��ڽ���ʱ��
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
			logger.debug("ʱ���ʽ����ȷ��");
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
	 * checkParam У�����
	 * 
	 * @param paraMap
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private Map checkParam(String serviceId, Map paraMap)
	{
		logger.debug("У�����...");
		// 1.У�����еĲ����Ƿ���
		// 2.У��ʱ������Ƿ��ʽ����ȷ
		// 3.У���Ƿ���ֻȡ������������
		// 4.�����Ƿ���ʱ��������
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
				logger.debug("��������...");
				break;
			} else {
				if (null == paraMap.get(paraList.get(i))) {
					flag = false;
					logger.debug("��������...");
					break;
				}
			}
		}
		if (flag) {
			Map tepMap = checkTime(serviceId, timeParamList, paraMap);
			return tepMap;
		} else {
			logger.debug("��������...");
			Map tepMap = ResultParser.createParamErrorMap();
			tepMap.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
			return tepMap;
		}
	}

	private Map checkTime(String serviceId, List timeParamList, Map paraMap)
	{
		boolean flag = true; // �жϲ���
		boolean flag1 = true; // �жϵ���
		Map srvMap = queryServiceById(serviceId);
		VoShareService v = new VoShareService();
		ParamUtil.mapToBean(srvMap, v, false);
		logger.debug("v.getIs_month_data() is " + v.getIs_month_data());
		// �ж������Ƿ���ϱ�׼
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
			logger.debug("��������...");
			Map tepMap = ResultParser.createParamErrorMap();
			tepMap.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
			return tepMap;
		}
		// ��ʱ���ж�ʱ�䳤������
		// int dayCount = daysBetween(startTime, endTime);
		// System.out.println("dayCount is " + dayCount);
		// if (0 <= dayCount && dayCount <= 7) {
		// System.out.println("��ȷ");
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
			logger.debug("��ѯ���񱨴�..." + e);
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
			logger.debug("��ѯ��������..." + e);
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
		// ����ʱ��Ϊ0ʱ
		calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
		calst.set(java.util.Calendar.MINUTE, 0);
		calst.set(java.util.Calendar.SECOND, 0);
		caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
		caled.set(java.util.Calendar.MINUTE, 0);
		caled.set(java.util.Calendar.SECOND, 0);
		// �õ�����������������
		int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
				.getTime().getTime() / 1000)) / 3600 / 24;
		return days;
	}

	private boolean isInTheMonth(String time)
	{
		boolean flag = false;
		Calendar cal = Calendar.getInstance();
		int monthInt = cal.get(Calendar.MONTH) + 1;
		logger.debug("ϵͳ��ǰ�·�Ϊ " + monthInt);
		String theMonth = String.valueOf(monthInt);
		if (theMonth.length() == 1) {
			theMonth = "0" + theMonth;
		}
		String temp1[] = time.split("-");
		String month = temp1[1];
		logger.debug("����monthΪ��" + month);
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
		// ����������ʱ��������true�����򷵻�false
		return b;
	}

}
