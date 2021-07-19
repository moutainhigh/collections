package com.gwssi.webservice.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.constant.CollectConstants;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.share.ftp.vo.VoShareFtpSrvParam;
import com.gwssi.webservice.client.TaskInfo;

import cn.gwssi.common.component.logger.TxnLogger;

public class ServerInfo
{

	// ��־
	protected static Logger	logger	= TxnLogger.getLogger(ServerInfo.class
											.getName());

	ServiceDAO				dao		= null;

	public ServerInfo()
	{
		dao = new ServiceDAOImpl();
	}

	public Map queryFtpService(String serviceId)
	{
		String sql = SQLHelper.queryFtpServiceSQL(serviceId);
		Map ftpService = new HashMap();
		try {
			ftpService = dao.queryFtpService(sql);
		} catch (DBException e) {
			logger.debug("����queryFtpService��ѯ���ݿ����..." + e);
			e.printStackTrace();
		}
		return ftpService;
	}

	public List queryFtpParam(String ftpServiceId)
	{
		String sql = SQLHelper.queryFtpParamSQL(ftpServiceId);
		List paramList = new ArrayList();
		try {
			paramList = dao.queryFtpParam(sql);
		} catch (DBException e) {
			logger.debug("����queryFtpParam��ѯ���ݿ����..." + e);
			e.printStackTrace();
		}
		return paramList;
	}

	public Map queryFTPDatasource(String datasourceId)
	{
		String sql = SQLHelper.queryFTPDatasource(datasourceId);
		Map datasourceMap = new HashMap();
		try {
			datasourceMap = dao.queryFTPDatasource(sql);
		} catch (DBException e) {
			logger.debug("����queryFTPDatasource��ѯ���ݿ����..." + e);
			e.printStackTrace();
		}
		return datasourceMap;
	}
	
	/**
	 * ���������͵Ĳ�����������õĸ�ʽ
	 * @param paramList
	 * @return
	 */	
	public Map formatParam(List paramList)
	{
		Map paraMap = new HashMap();
		TaskInfo taskInfo = new TaskInfo();
		boolean isMonth = false;
		for (int i = 0; i < paramList.size(); i++) {
			Map tepMap = (Map) paramList.get(i);
			//System.out.println("i="+i+"--Map="+tepMap);
			VoShareFtpSrvParam vo = new VoShareFtpSrvParam();
			ParamUtil.mapToBean(tepMap, vo, false);
			String type = vo.getParam_value_type();
			if (CollectConstants.PARAM_STYLE_00.equals(type)
					|| CollectConstants.PARAM_STYLE_01.equals(type)) {
				//00 �ַ�������  01 �������� ��������
				paraMap.put(vo.getPatameter_name(), vo.getPatameter_value());
			}else if (CollectConstants.PARAM_STYLE_02.equals(type)) {
				//�������� ����
				if (isMonth) {
					System.out.println("isMonth="+isMonth);
				} else {
					String style = vo.getStyle();
					String value = vo.getPatameter_value();
					//System.out.println("����ֵ��"+value+"---�������ͣ�"+style);
					if("TODAY".equals(value)){
						value="TODAY-0";
					}
					int day = 0;
					String time = "";
					if (value.contains("-")) {
						day = Integer.parseInt(value.substring(value
								.lastIndexOf("-") + 1, value.length()));
						if (CollectConstants.DATE_STYLE_01.equals(style)) {
							time = taskInfo.get01Time(day);
						} else if (CollectConstants.DATE_STYLE_02.equals(style)) {
							time = taskInfo.get02Time(day);
						} else if (CollectConstants.DATE_STYLE_03.equals(style)) {
							time = taskInfo.get03Time(day);
						}else{
							logger.info("��֧�ֵ�ʱ���ʽ style="+style);
						}
					}else{
						logger.info("����"+vo.getPatameter_name()+"��������,ֵΪ="+value);
						
					}
					
						
						
					//System.out.println("ת�����ʱ�䣺"+time);
					if (paraMap.containsKey(vo.getPatameter_name())) {
						String tmp = paraMap.get(vo.getPatameter_name())
								.toString();
						paraMap.put(vo.getPatameter_name(), tmp + ","
								+ time);
					} else {
						paraMap.put(vo.getPatameter_name(), time);
					}
					//}
				}
			}
		}
		return paraMap;
	}

	public String getTableId(String serverId)
	{
		StringBuffer sql = new StringBuffer();
		sql
				.append("select i.table_id from share_interface i where i.interface_id =(select t.interface_id from share_service t where t.service_id = '"
						+ serverId + "')");
		String tableIds = null;
		StringBuffer tableId = new StringBuffer();
		tableId.append("(");
		try {
			tableIds = dao.queryTableById(sql.toString());
		} catch (DBException e) {
			logger.debug("����getTableId��ѯ���ݿ����..." + e);
			e.printStackTrace();
		}
		if (null != tableIds) {
			String tids[] = tableIds.split(",");
			for (int i = 0; i < tids.length; i++) {
				tableId.append("'");
				tableId.append(tids[i]);
				tableId.append("'");
				if (i < tids.length - 1) {
					tableId.append(",");
				}
				if (i == tids.length - 1) {
					tableId.append(")");
				}
			}
		} else {
			tableId.append("' ')");
		}
		return tableId.toString();
	}

	public String getTableName(String tableId)
	{
		StringBuffer sql = new StringBuffer();
		sql
				.append("select t.table_name_en from res_share_table t where t.table_no in ");
		sql.append(tableId);

		StringBuffer tableName = new StringBuffer();
		List tbNameList = new ArrayList();
		try {
			tbNameList = dao.queryTableName(sql.toString());
		} catch (DBException e) {
			logger.debug("����getTableName��ѯ���ݿ����..." + e);
			e.printStackTrace();
		}

		for (int i = 0; i < tbNameList.size(); i++) {
			Map tepMap = (Map) tbNameList.get(i);
			tableName.append(tepMap.get("TABLE_NAME_EN"));
			tableName.append(".ROWID");
			if (i < tbNameList.size() - 1) {
				tableName.append(",");
			}
		}
		return tableName.toString();
	}
}
