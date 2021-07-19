package com.gwssi.common.servicejob;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.util.ParamUtil;
import com.gwssi.share.ftp.vo.VoShareSrvScheduling;

public class ServiceTriggerServlet extends HttpServlet
{

	// ������ʱ������
	// 1.ͨ��������������õ�����
	// 2.ʵ��������
	// 3.����������trigger
	// 4.���ô����������ԣ�Ҫִ�е�job,ִ�е�ʱ��Ͳ���
	// 5.����������

	DBOperation	operation	= null;

	public ServiceTriggerServlet()
	{
		operation = DBOperationFactory.createOperation();
	}

	protected static Logger	logger			= TxnLogger
													.getLogger(ServiceTriggerServlet.class
															.getName());	// ��־

	protected ServletConfig	servletConfig	= null;						// ������Ϣ

	protected boolean		ignore			= true;						// �Ƿ���Թ�����

	/**
	 * ��ʼ������
	 */
	public void init(ServletConfig cfg) throws javax.servlet.ServletException
	{
		this.servletConfig = cfg;
		String value = servletConfig.getInitParameter("ignore");
		logger.debug("web.xlm�ļ���������ȵĺ���״̬Ϊ" + value);
		if (value == null) {
			this.ignore = true;
		} else if (value.equalsIgnoreCase("true")) {
			this.ignore = true;
		} else if (value.equalsIgnoreCase("yes")) {
			this.ignore = true;
		} else {
			this.ignore = false;
		}

		// �����Ƿ���Ըù����������ж��Ƿ�ִ�к�������
		if (!ignore) {
			doInit(cfg);
		}
	}

	/**
	 * 
	 * doInit
	 * 
	 * @param cfg
	 *            void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
	 */
	protected void doInit(ServletConfig cfg)
	{
		// ҵ���߼�
		// 1.��ѯ������ȱ�
		// 2.��������Ч��job����

		// �����ݿ�������в�ѯ�����е�����
		StringBuffer sql = new StringBuffer();
		VoShareSrvScheduling vo = new VoShareSrvScheduling();
		sql.append("select * from SHARE_SRV_SCHEDULING t "
				+ "where t.is_markup = 'Y'");
		logger.info("��ѯ������Ƿ���û����trigger��sql: " + sql.toString());

		try {

			List<Map> srvlist = this.query(sql.toString());
			if (null != srvlist && srvlist.size() > 0) {
				logger.info("��[" + srvlist.size() + "]��������Ҫ��ִ��");
				for (int i = 0; i < srvlist.size(); i++) {
					Map m = srvlist.get(i);
					ParamUtil.mapToBean(m, vo, false);
					logger.info("��ѯ�����ķ�����ȶ���Ϊ��" + m);
					try {
						logger.info("��ʼ���ؼƻ�����...");
						SrvTriggerRunner.addToScheduler(vo);
					} catch (Exception e) {
						logger.info("�ƻ��������ʧ��...");
						e.printStackTrace();
					}
				}
			} else {
				logger.info("��ʱû��������Ҫִ��...");
			}
		} catch (DBException e) {
			System.out.println("���ݿ��������");
			e.printStackTrace();
		}
	}

	List query(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}
}
