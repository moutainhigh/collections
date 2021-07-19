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

	// 创建定时任务步骤
	// 1.通过调度器工厂获得调度器
	// 2.实例化任务
	// 3.创建触发器trigger
	// 4.设置触发器的属性：要执行的job,执行的时间和参数
	// 5.启动调度器

	DBOperation	operation	= null;

	public ServiceTriggerServlet()
	{
		operation = DBOperationFactory.createOperation();
	}

	protected static Logger	logger			= TxnLogger
													.getLogger(ServiceTriggerServlet.class
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

		// 根据是否忽略该过滤器参数判断是否执行后续操作
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
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	protected void doInit(ServletConfig cfg)
	{
		// 业务逻辑
		// 1.查询任务调度表
		// 2.将所有有效的job启动

		// 从数据库任务表中查询出所有的任务
		StringBuffer sql = new StringBuffer();
		VoShareSrvScheduling vo = new VoShareSrvScheduling();
		sql.append("select * from SHARE_SRV_SCHEDULING t "
				+ "where t.is_markup = 'Y'");
		logger.info("查询服务表是否有没触发trigger的sql: " + sql.toString());

		try {

			List<Map> srvlist = this.query(sql.toString());
			if (null != srvlist && srvlist.size() > 0) {
				logger.info("有[" + srvlist.size() + "]个服务需要被执行");
				for (int i = 0; i < srvlist.size(); i++) {
					Map m = srvlist.get(i);
					ParamUtil.mapToBean(m, vo, false);
					logger.info("查询出来的服务调度对象为：" + m);
					try {
						logger.info("开始加载计划服务...");
						SrvTriggerRunner.addToScheduler(vo);
					} catch (Exception e) {
						logger.info("计划任务添加失败...");
						e.printStackTrace();
					}
				}
			} else {
				logger.info("暂时没有任务需要执行...");
			}
		} catch (DBException e) {
			System.out.println("数据库操作报错");
			e.printStackTrace();
		}
	}

	List query(String sql) throws DBException
	{
		return operation.selectInOrder(sql);
	}
}
