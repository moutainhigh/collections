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
	 * @param param
	 */
	private boolean checkVerify(Map param){
		//System.out.println("--------checkVerify----------");
		logger.info("������֤�����Ƿ�Ҫ���������֤");
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
				logger.info("У����Ϊ"+result);
			}else{
				logger.info("LOGIN_NAMEΪ��");
				return true;
			}
        } catch (Exception e) {
			//e.printStackTrace();
        	//System.out.println("δ�ҵ�"+userName+"��Ӧ��У�鴦����");
        	logger.info("δ�ҵ�"+userName+"��Ӧ��У�鴦����---"+e.getMessage());
			return true;
		}		
		return result;
	}
	
	/**
	 * 
	 * checkServiceCanBeUsed �������Ƿ���ʹ��
	 * 
	 * @param serviceVo
	 * @param param
	 * @param shareLogVo
	 * @return Map
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected Map checkServiceCanBeUsed(ServiceVo serviceVo, Map param,
			ShareLogVo shareLogVo) throws DBException
	{
		// �ж��Ƿ��ǲ����û�
		if (param.containsKey("USER_TYPE")) {
			if("TEST".equals(param.get("USER_TYPE"))){
				logger.debug("���û�Ϊ�����û����ü���...");
				// ���ü�¼��־��Ϣ
				shareLogVo.setLog_type(ExConstant.LOG_TYPE_TEST);
			}else if("SHARE_FTP".equals(param.get("USER_TYPE"))){
				logger.debug("����FTP�����ü���...");
				// ���ü�¼��־��Ϣ
				shareLogVo.setLog_type(ExConstant.LOG_TYPE_USER);
			}else{
				logger.error("�������󣬴�����û�����:"+param.get("USER_TYPE"));
				Map result =ResultParser.createParamErrorMap();
				result.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
				return result;
			}
			
			this.getServiceInfo(serviceVo, shareLogVo);
			return ResultParser.createSuccuseMap();
		} else {
			logger.debug("���û�Ҫ����...");
			// ���ü�¼��־��Ϣ
			shareLogVo.setLog_type(ExConstant.LOG_TYPE_USER);
			
			
			//����У�� ���ھ�
			if (null != param.get("LOGIN_NAME")) {					
				if(!checkVerify(param)){
					Map rsMap=ResultParser.createVerifyErrorMap();
					shareLogVo.setReturn_codes(String.valueOf(rsMap
							.get(ShareConstants.SERVICE_FHDM_VERIFY_ERROR))); // ��־��¼���ش���
					return rsMap;
				}
				
			}		
			
			
			
			// 20140311 add ����÷����Ƿ���
			String serviceId = serviceVo.getService_id();
			Map lockedMap = this.queyrServiceLocked(serviceId);
			System.out.println(lockedMap);
			if ("Y".equals(lockedMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				// 1.����������¼��Ϣ�Ƿ���ȷ,ip���Ƿ���ȷ
				Map loginMap = this
						.queryLoginInfo(serviceVo, param, shareLogVo);
				if ("Y".equals(loginMap.get(ShareConstants.SERVICE_CAN_BE_USED)
						.toString())) {
					// 2.������,����״̬�Ƿ���ȷ
					Map serStateMap = this.queryServiceState(serviceVo,
							shareLogVo);
					if ("Y".equals(serStateMap.get(
							ShareConstants.SERVICE_CAN_BE_USED).toString())) {
						// 3.�������ʱ���Ƿ���ȷ
						Map serDateMap = this.querySvrDate(DateUtil.getToday());
						if ("Y".equals(serDateMap.get(
								ShareConstants.SERVICE_CAN_BE_USED).toString())) {
							// 4.������ʱ�����
							Map dateRuleMap = this.checkDateRule(serviceVo);
							if ("Y".equals(dateRuleMap.get(
									ShareConstants.SERVICE_CAN_BE_USED)
									.toString())) {
								// 5.�������Ƿ���ȷ
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
	 * getServiceInfo ���Է���ʱ��ȡ�������ϸ��Ϣ
	 * 
	 * @param serviceVo
	 * @param shareLogVo
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected void getServiceInfo(ServiceVo serviceVo, ShareLogVo shareLogVo)
	{
		shareLogVo.setService_targets_id(serviceVo.getService_targets_id()); // �������ID
		shareLogVo.setService_targets_name(serviceVo.getService_targets_name()); // �����������
		shareLogVo.setTargets_type(serviceVo.getService_targets_type()); // �����������
		shareLogVo.setIs_formal(serviceVo.getIs_formal());// �Ƿ���ʽ����
		//shareLogVo.setIs_formal("N");
		shareLogVo.setService_id(serviceVo.getService_id()); // ����ID
		shareLogVo.setService_name(serviceVo.getService_name()); // ��������
		shareLogVo.setService_type(serviceVo.getService_type()); // ��������
	}

	/**
	 * 
	 * queryLoginInfo ����������¼��Ϣ�Ƿ���ȷ
	 * 
	 * @param serviceVo
	 * @param param
	 * @param shareLogVo
	 * @return Map
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected Map queryLoginInfo(ServiceVo serviceVo, Map param,
			ShareLogVo shareLogVo) throws DBException
	{
		logger.debug("��ʼ�����û���Ϣ...");
		Map resultMap = new HashMap();
		resultMap.put(ShareConstants.SERVICE_CAN_BE_USED, "Y");

		if (null == serviceVo) {
			logger.debug("�û���Ч...");
			resultMap = ResultParser.createUserFailMap();
			return ResultParser.createUserFailMap();
		} else {
			String svrName = param.get("LOGIN_NAME").toString();
			String svrPwd = param.get("PASSWORD").toString();
			String svrStatus = serviceVo.getService_status();
			logger.debug("�û���Ϊ:" + svrName);
			logger.debug("����Ϊ:" + svrPwd);
			logger.debug("�û���Ч״̬Ϊ:" + svrStatus);

			// ���ü�¼��־��Ϣ
			shareLogVo.setIs_formal(serviceVo.getIs_formal()); // �Ƿ���ʽ����
			shareLogVo.setService_targets_id(serviceVo.getService_targets_id()); // �������ID
			shareLogVo.setService_targets_name(serviceVo
					.getService_targets_name()); // �����������
			shareLogVo.setTargets_type(serviceVo.getService_targets_type()); // �����������
			String serviceStatus = serviceVo.getService_status();

			if (ExConstant.IS_MARKUP_Y.equals(serviceStatus)) {
				// �ж��û���Ϣ
				if (ExConstant.IS_MARKUP_Y.equals(svrStatus)) {
					if (StringUtils.isBlank(svrName)
							|| !svrName.equals(serviceVo
									.getService_targets_no())) {
						logger.debug("�û�������...");
						resultMap = ResultParser.createUserErrorlMap();
						return ResultParser.createUserErrorlMap();
					}
					if (StringUtils.isBlank(svrPwd)
							|| !serviceVo.getService_password().equals(svrPwd)) {
						logger.debug("�������...");
						resultMap = ResultParser.createPwdErrorlMap();
						return ResultParser.createPwdErrorlMap();
					}
					if (!queryServiceIPInfo(serviceVo, shareLogVo)) {
						logger.debug("IP����...");
						resultMap = ResultParser.createServiceIPFailMap();
						return ResultParser.createServiceIPFailMap();
					}
					if ("Y".equals(resultMap
							.get(ShareConstants.SERVICE_CAN_BE_USED))) {
						if (svrName.equals(serviceVo.getService_targets_no())
								&& svrPwd.equals(serviceVo
										.getService_password())) {
							logger.debug("�û����������ɹ�...");
							return ResultParser.createSuccuseMap();
						} else {
							return resultMap;
						}
					} else {
						return resultMap;
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
	 * queryServiceIPInfo ����ip���Ƿ���ȷ
	 * 
	 * @param serviceVo
	 * @param shareLogVo
	 * @return boolean
	 * @throws DBException
	 *             boolean
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected boolean queryServiceIPInfo(ServiceVo serviceVo,
			ShareLogVo shareLogVo) throws DBException
	{
		logger.debug("��ʼ����IP��Ϣ...");
		boolean ipFlag = false;
		String isBindIP = serviceVo.getIs_bind_ip();
		if (StringUtils.isNotBlank(isBindIP) // �����ip
				&& ExConstant.IS_BIND_IP_Y.equals(isBindIP)) {
			String clientIP = getClientIp();
			if (null != serviceVo.getIp() && !"".equals(serviceVo.getIp())) {
				String[] ips = serviceVo.getIp().split(",");
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
		} else {
			logger.debug("û�а�ip,����IP���...");
			ipFlag = true; // û�а�ip
		}
		return ipFlag;
	}

	/**
	 * 
	 * queryServiceIPInfo ����ip���Ƿ���ȷforTRS
	 * 
	 * @param v
	 * @param shareLogVo
	 * @return boolean
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
	 * @param serviceVo
	 * @param shareLogVo
	 * @return Map
	 * @throws DBException
	 *             Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected Map queryServiceState(ServiceVo serviceVo, ShareLogVo shareLogVo)
			throws DBException
	{
		logger.debug("��ʼ�������״̬...");

		if (null == serviceVo) {
			logger.debug("û���ҵ��÷���...");
			return ResultParser.createSvrNotFoundMap();
		} else {
			// ���ü�¼��־��Ϣ
			shareLogVo.setService_id(serviceVo.getService_id()); // ����ID
			shareLogVo.setService_name(serviceVo.getService_name()); // ��������
			shareLogVo.setService_type(serviceVo.getService_type()); // ��������
			if (ExConstant.IS_MARKUP_Y.equals(serviceVo.getIs_markup())
					&& ExConstant.SERVICE_STATE_Y.equals(serviceVo
							.getService_state())) {
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
	 * querySvrDate �������ʱ��
	 * 
	 * @param svrDate
	 * @return Map
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
	 * checkDateRule �������ʱ���Ƿ����Ҫ��
	 * 
	 * @param serviceVo
	 * @return Map Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected Map checkDateRule(ServiceVo serviceVo)
	{
		SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss");// ֻ��ʱ����
		String nowtime = sDateFormat.format(new java.util.Date());

		// ��ҳ�жϵ����Ƿ������ù���
		String sql = SQLHelper.queryCountDataRule(serviceVo.getService_id());

		int count = 0;
		int timeCount = 0;
		try {
			String countStr = dao.queryDateRule(sql);
			// countΪ0ʱ������ǰδ���ù���
			count = Integer.parseInt(countStr == null ? "0" : countStr);

			if (count > 0) {
				// ��ǰ������ʱ����ʹ���
				sql = SQLHelper.queryDateRule(serviceVo.getService_id(),
						nowtime);
				String timeStr = dao.queryDateRule(sql);
				timeCount = Integer.parseInt(timeStr == null ? "0" : timeStr);
			}
		} catch (DBException e) {
			logger.debug("��ѯ������򱨴�..." + e);
			e.printStackTrace();
		}
		if (count == 0) {
			logger.debug(" countΪ0ʱ������ǰδ���ù���");
			return ResultParser.createSuccuseMap();
		} else {
			if (timeCount == 0) {
				logger.debug("��ǰʱ�䲻�ڷ����������ʱ���ڣ�" + nowtime);
				return ResultParser.createSvrTimeErrorMap();
			} else {
				logger.debug("timeCount>0ʱ������ǰʱ����Ϲ���");
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
		logger.debug("����һ��ȡ��" + currentCount + " ��");
		ShareLockVo shareLockVo = new ShareLockVo();
		shareLockVo.setService_id(serviceId);
		shareLockVo.setLock_time(nowTime);
		Map resultMap = new HashMap();
		// ��ѯ����ķ��ʹ���
		String ruleSql = SQLHelper.queryRule(serviceId);
		// ��ѯ��������˶��ٴ�
		String logSql = SQLHelper.getServiceLogSQL(serviceId);
		// Map logMap = new HashMap();
		Map ruleMap = new HashMap();
		VoShareServiceRule serviceRule = new VoShareServiceRule();
		try {
			ruleMap = dao.queryServiceRule(ruleSql);// ��ѯ����ķ��ʹ���
			// ����÷��������˹���,Ҫ��Թ������У��
			if (null != ruleMap.get("TIMES_DAY")) {
				ParamUtil.mapToBean(ruleMap, serviceRule, false);
				Map logMap = dao.queryServiceLog(logSql);// ��ѯ������ʼ�¼
				// ��ȡÿ����ʶ��ٴ�
				int times_day = Integer
						.parseInt(serviceRule.getTimes_day() == null ? "0"
								: serviceRule.getTimes_day());
				// ��ȡÿ�η��ʶ�����
				int count_times = Integer
						.parseInt(serviceRule.getCount_dat() == null ? "0"
								: serviceRule.getCount_dat());
				// ��ȡ��������ʶ�����
				int amount_day = Integer.parseInt(serviceRule
						.getTotal_count_day() == null ? "0" : serviceRule
						.getTotal_count_day());
				// ��ȡ�Ѿ����ʵĴ���,+1������һ�ε�
				int times = Integer.parseInt(logMap.get("TIMES").toString()) + 1;
				logger.debug("�÷�����챻���ʹ�[" + times + "]��...");
				// ��ȡ�Ѿ����ʵ�����,���η��ʵ�����
				int amount = Integer.parseInt(logMap.get("AMOUNT").toString())
						+ currentCount;
				logger.debug("�÷�����칲��ȡ����[" + amount + "]��...");

				if (times_day != 0 && times > times_day) {
					logger.debug("��������������ʴ���...");
					resultMap = ResultParser.createSvrNumErrorMap();
					shareLockVo
							.setLock_code(ShareConstants.SERVICE_FHDM_LOCK_NUMBER);
					this.insertLock(shareLockVo);
				} else if (count_times != 0 && currentCount > count_times) {
					logger.debug("�������������������...");
					resultMap = ResultParser.createSvrTimeCountErrorMap();
				} else if (amount_day != 0 && amount > amount_day) {
					logger.debug("�������������������...");
					resultMap = ResultParser.createSvrTotalErrorMap();
					shareLockVo
							.setLock_code(ShareConstants.SERVICE_FHDM_LOCK_TOTAL);
					this.insertLock(shareLockVo);
				} else {
					logger.debug("������ʹ���ͨ��...");
					resultMap = ResultParser.createSuccuseMap();
				}
			} else {
				logger.debug("�÷�����δ���÷��ʹ���...");
				resultMap = ResultParser.createSuccuseMap();
			}
		} catch (DBException e) {
			logger.debug("������������...");
			resultMap = ResultParser.createSqlErrorMap();
			e.printStackTrace();
		} finally {
			return resultMap;
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
	 * isInDatesForJob �ж�ʱ���Ƿ���һ��ʱ��������
	 * 
	 * @param strDate
	 * @param strDateBegin
	 * @param strDateEnd
	 * @return boolean
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	public boolean isInDatesForJob(String strDate, String strDateBegin,
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
		if ((intCompareBegin > 0 || intCompareBegin == 0) && intCompareEnd < 0) {
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
	private Map checkParam(ServiceVo serviceVo, Map paraMap)
	{
		// 1.У�����еĲ����Ƿ���
		// 2.У��ʱ������Ƿ��ʽ����ȷ
		// 3.У���Ƿ���ֻȡ������������
		// 4.�����Ƿ���ʱ��������
		logger.debug("У�����...");
		boolean flag = true;

		flag = paramIsLost(paraMap);
		Map tepMap = new HashMap();
		if (flag) {
			tepMap = dateFormatIsRight(serviceVo, paraMap);
			return tepMap;
		} else {
			logger.debug("��������...");
			tepMap = ResultParser.createParamErrorMap();
			tepMap.put(ShareConstants.SERVICE_CAN_BE_USED, "N");
			return tepMap;
		}
	}

	/**
	 * 
	 * paramIsLost У�����еĲ����Ƿ񶼴���
	 * 
	 * @param paraMap
	 * @return boolean
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
		return flag;
	}

	/**
	 * 
	 * dateFormatIsRight У��ʱ������Ƿ��ʽ����ȷ
	 * 
	 * @param serviceVo
	 * @param paraMap
	 * @return boolean
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
				logger.debug("ʱ��param is" + param);
				int split = param.indexOf(",");
				if (split > 0) {
					// �ж�ʱ���ʽ�Ƿ���ȷ
					String startTime = param.substring(0, split);
					isRightFormat = matcherTime(startTime);
					String endTime = param.substring(split + 1, param.length());
					isRightFormat = matcherTime(endTime);
					logger.debug("ʱ��startTime :" + startTime);
					logger.debug("ʱ��endTime :" + endTime);

					// �ж��Ƿ���
					if ("Y".equals(serviceVo.getIs_month_data())) {
						isInMonth = isInTheMonth(startTime);
						isInMonth = isInTheMonth(endTime);
					}

					// �ж�ʱ�����Ƿ���ȷ
					if (null != serviceVo.getVisit_period()
							&& Integer.parseInt(serviceVo.getVisit_period()) >= 1) {
						int dayCount = daysBetween(startTime, endTime);
						logger.debug("����dayCount" + dayCount);
						logger.debug("����visit_period"
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
			logger.debug("��������...");
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
	 * queryParam ��ѯ����Ĳ���
	 * 
	 * @param serviceId
	 * @return List
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
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

	/**
	 * 
	 * daysBetween ����ĳ��ʱ���Ƿ���һ��ʱ����
	 * 
	 * @param startTime
	 * @param endTime
	 * @return int
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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

	/**
	 * 
	 * isInTheMonth �ж�ʱ���Ƿ��ڱ�����
	 * 
	 * @param time
	 * @return boolean
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private boolean isInTheMonth(String time)
	{
		boolean flag = false;
		Calendar cal = Calendar.getInstance();
		int yearInt = cal.get(Calendar.YEAR);
		String theYear = String.valueOf(yearInt);
		logger.debug("ϵͳ��ǰ���Ϊ " + theYear);
		int monthInt = cal.get(Calendar.MONTH) + 1;
		String theMonth = String.valueOf(monthInt);
		if (theMonth.length() == 1) {
			theMonth = "0" + theMonth;
		}
		logger.debug("ϵͳ��ǰ�·�Ϊ " + theMonth);
		String temp1[] = time.split("-");
		String year = temp1[0];
		String month = temp1[1];
		logger.debug("����yearΪ��" + year);
		logger.debug("����monthΪ��" + month);
		if (theYear.equals(year) && theMonth.equals(month)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 
	 * matcherTimeʱ���ʽУ��
	 * 
	 * @param time
	 * @return boolean
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	private boolean matcherTime(String time)
	{
		String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		Pattern pattern = Pattern.compile(eL);
		Matcher matcher = pattern.matcher(time);
		boolean b = matcher.matches();
		// ����������ʱ��������true�����򷵻�false
		return b;
	}

	// TRS������
	protected Map checkTrsServiceCanBeUsed(String serviceCode, Map param,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		Long start = System.currentTimeMillis();
		// �ж��Ƿ��ǲ����û�
		if (param.containsKey("USER_TYPE")
				&& "TEST".equals(param.get("USER_TYPE"))) {
			logger.debug("���û�Ϊ�����û����ü���...");

			// ���ü�¼��־��Ϣ
			shareLogVo.setLog_type(ExConstant.LOG_TYPE_TEST);
			this.queryTrsLoginInfoTest(serviceCode, param, shareLogVo,
					serviceId);
			this.queryTrsServiceStateTest(serviceCode, shareLogVo, serviceId);
			Map serStateMap = this.queryTrsServiceState(serviceCode,
					shareLogVo, serviceId);
			return serStateMap;
			// return ResultParser.createSuccuseMap();
		} else {
			logger.debug("���û�Ҫ����...");
			// ���ü�¼��־��Ϣ
			shareLogVo.setLog_type(ExConstant.LOG_TYPE_TEST);
			Map lockedMap = this.queyrServiceLocked(serviceId);
			if ("Y".equals(lockedMap.get(ShareConstants.SERVICE_CAN_BE_USED)
					.toString())) {
				// 1.����������¼��Ϣ�Ƿ���ȷ,ip���Ƿ���ȷ
				Map loginMap = this.queryTrsLoginInfo(serviceCode, param,
						shareLogVo, serviceId);
				// 2.������,����״̬�Ƿ���ȷ
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
		logger.debug("��ʼ�����û���Ϣ...");
		// ��ȡ���ʷ�����û���Ϣ
		String sql = SQLHelper.getTrsLoginSQL(serviceId);
		Map loginMap = dao.queryLoginInfo(sql);
		logger.debug("loginMap is " + loginMap);
		logger.debug("param is " + param);

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

	protected void queryTrsLoginInfoTest(String serviceCode, Map param,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		logger.debug("��ʼ�����û���Ϣ...");
		// ��ȡ���ʷ�����û���Ϣ
		String sql = SQLHelper.getTrsLoginSQL(serviceCode);
		Map loginMap = dao.queryLoginInfo(sql);

		VoResServiceTargets v = new VoResServiceTargets();
		ParamUtil.mapToBean(loginMap, v, false);

		// ���ü�¼��־��Ϣ
		shareLogVo.setService_targets_id(v.getService_targets_id()); // �������ID
		shareLogVo.setService_targets_name(v.getService_targets_name()); // �����������
		shareLogVo.setTargets_type(v.getService_targets_type()); // �����������

	}

	protected Map queryTrsServiceState(String serviceCode,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		logger.debug("��ʼ�������״̬...");
		// ��ȡ����״̬
		String sql = SQLHelper.queryTrsServiceById(serviceId);
		Map svrStateMap = dao.queryServiceState(sql);

		if (svrStateMap.isEmpty()) {
			logger.debug("û���ҵ��÷���...");
			return ResultParser.createSvrNotFoundMap();
		} else {
			VoTrsShareService v = new VoTrsShareService();
			ParamUtil.mapToBean(svrStateMap, v, false);
			// ���ü�¼��־��Ϣ
			shareLogVo.setService_id(v.getTrs_service_id()); // ����ID
			shareLogVo.setService_name(v.getTrs_service_name()); // ��������
			shareLogVo.setService_type("99"); // ��������TRS�趨Ϊ99
			if (ExConstant.IS_MARKUP_Y.equals(v.getIs_markup())
					&& ExConstant.SERVICE_STATE_Y.equals(v.getService_state())) {
				// ˵���˷����ܹ��ṩ����
				logger.debug("�÷�������...");

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
				logger.debug("�÷���״̬Ϊ���ɷ���...");
				return ResultParser.createServiceStateFailMap();
			}
		}
	}

	protected void queryTrsServiceStateTest(String serviceCode,
			ShareLogVo shareLogVo, String serviceId) throws DBException
	{
		// ��ȡ����״̬
		String sql = SQLHelper.queryTrsServiceById(serviceId);
		Map svrStateMap = dao.queryServiceState(sql);

		VoTrsShareService v = new VoTrsShareService();
		ParamUtil.mapToBean(svrStateMap, v, false);

		// ���ü�¼��־��Ϣ
		shareLogVo.setService_id(v.getService_targets_id()); // ����ID
		shareLogVo.setService_name(v.getTrs_service_name()); // ��������
		shareLogVo.setService_type("99"); // ��������

	}

	/**
	 * 
	 * queyrServiceLocked ��ѯ��������Ϣ
	 * 
	 * @param serviceId
	 * @return Map
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
				// û�б���
				dataMap = ResultParser.createSuccuseMap();
			}
		} catch (DBException e) {
			dataMap = ResultParser.createSqlErrorMap();
			logger.debug("����queyrServiceLocked��ѯ����");
			e.printStackTrace();
		}
		return dataMap;
	}

	private int insertLock(ShareLockVo shareLockVo)
	{
		logger.debug("��¼��־...");
		String sql = SQLHelper.insertLock(shareLockVo);
		int count = 0;
		try {
			count = dao.insertShareLog(sql);
		} catch (DBException e) {
			logger.debug("��¼��־����..." + e);
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
		// ֻ��ʱ����
		// String calNowtime = sDateFormat.format(new java.util.Date());
		// System.out.println(calNowtime);
	}

}
