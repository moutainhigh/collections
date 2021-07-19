package com.gwssi.sysmgr.bizlog.plugin;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.context.TxnContext;
import cn.gwssi.common.context.vo.VoCtrl;
import cn.gwssi.common.context.vo.VoUser;
import cn.gwssi.common.dao.BaseTable;
import cn.gwssi.common.dao.impl.TableFactory;
import cn.gwssi.common.proxy.config.TxnDefine;
import cn.gwssi.common.txn.logger.LoggerService;

import com.gwssi.common.util.CalendarUtil;
import com.gwssi.dw.runmgr.etl.vo.VoFirstPageQuery;
import com.gwssi.sysmgr.bizlog.vo.VoBizlog;

public class BizLogger extends LoggerService
{
	// ���ݱ�����
	private static final String	QUERY_TABLE_NAME	= "first_page_query";

	// ���ݱ�����
	private static final String	BIZLOG_TABLE_NAME	= "biz_log";

	private static final String	QUERY_TXNCODE		= java.util.ResourceBundle
															.getBundle("app")
															.getString(
																	"QUERY_TXNCODE");

	/**
	 * д��־��Ϣ
	 */
	public void writeLogger(TxnDefine txnDefine, TxnContext context,
			String errCode, String errDesc) throws TxnException
	{
		// ������Ϣ
		VoCtrl ctrl = context.getConttrolData();
		VoUser user = context.getOperData();
		// ��¼���ݲ�ѯ������
		BaseTable table = null;
		String txnCode = txnDefine.getTxnCode();
		if (QUERY_TXNCODE.indexOf(txnCode) != -1) {
			VoFirstPageQuery queryData = new VoFirstPageQuery();
			// �������������� ���繲�����-�ӿڹ���
			queryData.setFirst_cls(txnDefine.getMenuName());
			// �����Ӳ������� ���繲�����-�ӿڹ���
			queryData.setSecond_cls(ctrl.getTxnName());
			queryData.setCount("1");
			queryData.setNum("0");
			String date = CalendarUtil
					.getCalendarByFormat(CalendarUtil.FORMAT11);
			String times = CalendarUtil
					.getCalendarByFormat(CalendarUtil.FORMAT7);
			queryData.setQuery_date(date);
			queryData.setQuery_time(times);
			queryData.setUsername(user.getUserName());
			queryData.setOpername(user.getOperName());
			queryData.setOrgid(user.getOrgCode());
			queryData.setOrgname(user.getOrgName());
			//��ȡ���ص�ַ��ʱ�� ��ʱ�����ַ����������⴦����һ��
			queryData.setIpaddress(user.getValue("ipaddress").equals(
					"0:0:0:0:0:0:0:1") ? "127.0.0.1" : user
					.getValue("ipaddress"));
			queryData.setUserId(user.get("userID").toString());
			// ���Ӽ�¼
			table = TableFactory.getInstance().getTableObject(this,QUERY_TABLE_NAME);
			table.executeFunction("insert one first_page_query", context, queryData, "queryData");
		}

		String bizLogDesc = context.getRecord("biz_log").getValue("desc");

		if (bizLogDesc != null && !bizLogDesc.equals("")) {

			// û����ˮ�ŵĽ��ײ���¼��־
			String flowid = ctrl.getFlowid();
			if (flowid == null) {
				flowid = String.valueOf(System.currentTimeMillis());
				// return;
			}

			// ������־����
			VoBizlog logData = new VoBizlog();
			logData.setReqflowno(flowid);

			// ��������
			logData.setTrdtype(txnDefine.getSortName());

			// ������
			logData.setErrcode(errCode);
			logData.setErrdesc(errDesc);

			// �������

			// logData.setResultdata( context.toString() );
			logData.setResultdata(bizLogDesc);
			logData.setOperfrom("1");

			// ���Ӽ�¼
			table = TableFactory.getInstance().getTableObject(this,
					BIZLOG_TABLE_NAME);
			table.executeUpdate("insert one biz_log", context, logData);
		}
		context.getRecord("biz_log").clear();
	}

}
