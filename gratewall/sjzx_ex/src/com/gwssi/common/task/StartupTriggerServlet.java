package com.gwssi.common.task;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import cn.gwssi.common.component.logger.TxnLogger;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.collect.webservice.vo.VoCollectTaskScheduling;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.util.ParamUtil;

/**
 * 
 * 
 * ��Ŀ���ƣ�bjgs_exchange �����ƣ�StartupTriggerServlet ������������������Ч��������� �����ˣ����� ����ʱ�䣺May
 * 21, 2013 1:43:22 PM �޸��ˣ�lizheng �޸�ʱ�䣺May 21, 2013 1:43:22 PM �޸ı�ע��
 * 
 * @version
 * 
 */
public class StartupTriggerServlet extends HttpServlet
{

	// ������ʱ������
	// 1.ͨ��������������õ�����
	// 2.ʵ��������
	// 3.����������trigger
	// 4.���ô����������ԣ�Ҫִ�е�job,ִ�е�ʱ��Ͳ���
	// 5.����������

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	DBOperation	operation	= null;

	public StartupTriggerServlet()
	{
		operation = DBOperationFactory.createOperation();
	}

	protected static Logger	logger			= TxnLogger
													.getLogger(StartupTriggerServlet.class
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

		doInit(cfg);

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
		
		logger.debug("-----��ʼ��������ҳ��ʱ����-----");
		String job_name = "index";
		String job_class = "com.gwssi.common.task.IndexJob";
		ResourceBundle bundle=ResourceBundle.getBundle("index");
		String time_corn=bundle.getString("time_corn");
		SimpleTriggerRunner.addJob(job_name, job_class, time_corn);
		logger.info("-----quartz��������ҳ���ݶ�ʱ����-----");

		// �����Ƿ���Ըù����������ж��Ƿ�ִ�к�������
		if (ignore) {
			return;
		}
		// ҵ���߼�
		// 1.��ѯ������ȱ�
		// 2.��������Ч��job����
		// �����ݿ�������в�ѯ�����е�����
		StringBuffer sql = new StringBuffer();
		VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
		sql.append("select * from collect_task_scheduling t "
				+ "where t.is_markup = 'Y'");
		logger.debug("��ѯ������Ƿ���û����trigger��sql: " + sql.toString());
		
		try {

			List<Map> rwlist = this.query(sql.toString());
			if (null != rwlist && rwlist.size() > 0) {
				logger.debug("��[" + rwlist.size() + "]��������Ҫ��ִ��");
				for (int i = 0; i < rwlist.size(); i++) {
					Map m = rwlist.get(i);
					ParamUtil.mapToBean(m, vo, false);
					logger.debug("��ѯ������������ȶ���Ϊ��" + m);

					if (null != m.get("SCHEDULING_TYPE"))
						vo.setJhrw_lx(m.get("SCHEDULING_TYPE").toString()); // ��������
					else
						vo.setJhrw_lx("");

					if (null != m.get("END_TIME"))
						vo.setJhrw_end_sj(m.get("END_TIME").toString()); // �ƻ��������ʱ��
					else
						vo.setJhrw_end_sj("");

					if (null != m.get("START_TIME"))
						vo.setJhrw_start_sj(m.get("START_TIME").toString()); // �ƻ�����ʼʱ��
					else
						vo.setJhrw_start_sj("");

					if (null != m.get("SCHEDULING_COUNT"))
						vo.setJhrwzx_cs(m.get("SCHEDULING_COUNT").toString()); // �ƻ�����ִ�д���
					else
						vo.setJhrwzx_cs("");

					if (null != m.get("INTERVAL_TIME"))
						vo.setJhrwzx_jg(m.get("INTERVAL_TIME").toString()); // ÿ�μ��ʱ��
					else
						vo.setJhrwzx_jg("");

					if (null != m.get("SCHEDULING_DAY"))
						vo.setJhrw_rq(m.get("SCHEDULING_DAY").toString());
					else
						vo.setJhrw_rq("");

					if (null != m.get("SCHEDULING_WEEK"))
						vo.setJhrw_zt(m.get("SCHEDULING_WEEK").toString());
					else
						vo.setJhrw_zt("");

					if (null != m.get("JOB_CLASS_NAME"))
						vo
								.setJob_class_name(m.get("JOB_CLASS_NAME")
										.toString());
					else
						vo.setJob_class_name("");

					try {
						logger.debug("��ʼ���ؼƻ�����...");
						SimpleTriggerRunner.addToScheduler(vo);
					} catch (Exception e) {
						logger.debug("�ƻ��������ʧ��...");
						e.printStackTrace();
					}
				}
			} else {
				logger.debug("��ʱû��������Ҫִ��...");
			}
		} catch (DBException e) {
			logger.debug("���ݿ��������");
			e.printStackTrace();
		}
	}

	List query(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}
}
