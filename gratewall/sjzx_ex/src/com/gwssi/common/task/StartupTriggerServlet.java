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
 * 项目名称：bjgs_exchange 类名称：StartupTriggerServlet 类描述：启动所有有效的任务调度 创建人：李毅 创建时间：May
 * 21, 2013 1:43:22 PM 修改人：lizheng 修改时间：May 21, 2013 1:43:22 PM 修改备注：
 * 
 * @version
 * 
 */
public class StartupTriggerServlet extends HttpServlet
{

	// 创建定时任务步骤
	// 1.通过调度器工厂获得调度器
	// 2.实例化任务
	// 3.创建触发器trigger
	// 4.设置触发器的属性：要执行的job,执行的时间和参数
	// 5.启动调度器

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
															.getName());	// 日志

	protected ServletConfig	servletConfig	= null;						// 配置信息

	protected boolean		ignore			= true;						// 是否忽略过滤器

	/**
	 * 初始化方法
	 */
	public void init(ServletConfig cfg) throws javax.servlet.ServletException
	{
		this.servletConfig = cfg;
		String value = servletConfig.getInitParameter("ignore");
		logger.debug("web.xlm文件里任务调度的忽略状态为" + value);
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
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected void doInit(ServletConfig cfg)
	{
		
		logger.debug("-----开始加载了首页定时任务-----");
		String job_name = "index";
		String job_class = "com.gwssi.common.task.IndexJob";
		ResourceBundle bundle=ResourceBundle.getBundle("index");
		String time_corn=bundle.getString("time_corn");
		SimpleTriggerRunner.addJob(job_name, job_class, time_corn);
		logger.info("-----quartz加载了首页数据定时任务-----");

		// 根据是否忽略该过滤器参数判断是否执行后续操作
		if (ignore) {
			return;
		}
		// 业务逻辑
		// 1.查询任务调度表
		// 2.将所有有效的job启动
		// 从数据库任务表中查询出所有的任务
		StringBuffer sql = new StringBuffer();
		VoCollectTaskScheduling vo = new VoCollectTaskScheduling();
		sql.append("select * from collect_task_scheduling t "
				+ "where t.is_markup = 'Y'");
		logger.debug("查询任务表是否有没触发trigger的sql: " + sql.toString());
		
		try {

			List<Map> rwlist = this.query(sql.toString());
			if (null != rwlist && rwlist.size() > 0) {
				logger.debug("有[" + rwlist.size() + "]个任务需要被执行");
				for (int i = 0; i < rwlist.size(); i++) {
					Map m = rwlist.get(i);
					ParamUtil.mapToBean(m, vo, false);
					logger.debug("查询出来的任务调度对象为：" + m);

					if (null != m.get("SCHEDULING_TYPE"))
						vo.setJhrw_lx(m.get("SCHEDULING_TYPE").toString()); // 任务类型
					else
						vo.setJhrw_lx("");

					if (null != m.get("END_TIME"))
						vo.setJhrw_end_sj(m.get("END_TIME").toString()); // 计划任务结束时间
					else
						vo.setJhrw_end_sj("");

					if (null != m.get("START_TIME"))
						vo.setJhrw_start_sj(m.get("START_TIME").toString()); // 计划任务开始时间
					else
						vo.setJhrw_start_sj("");

					if (null != m.get("SCHEDULING_COUNT"))
						vo.setJhrwzx_cs(m.get("SCHEDULING_COUNT").toString()); // 计划任务执行次数
					else
						vo.setJhrwzx_cs("");

					if (null != m.get("INTERVAL_TIME"))
						vo.setJhrwzx_jg(m.get("INTERVAL_TIME").toString()); // 每次间隔时间
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
						logger.debug("开始加载计划任务...");
						SimpleTriggerRunner.addToScheduler(vo);
					} catch (Exception e) {
						logger.debug("计划任务添加失败...");
						e.printStackTrace();
					}
				}
			} else {
				logger.debug("暂时没有任务需要执行...");
			}
		} catch (DBException e) {
			logger.debug("数据库操作报错");
			e.printStackTrace();
		}
	}

	List query(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}
}
