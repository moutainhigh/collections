/**
 * 
 */
package com.gwssi.dw.component.quartz;
import java.text.ParseException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;

import org.quartz.Trigger;
import org.quartz.JobDetail;


import cn.gwssi.common.context.DataBus;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.common.database.DBOperation;
import com.gwssi.common.database.DBOperationFactory;
import com.gwssi.common.util.CalendarUtil;
import com.gwssi.dw.runmgr.services.vo.VoSysCltUser;

/**
 * @author caiqian
 *
 */
public class JobFactory {
	private static Log log = LogFactory.getLog(JobFactory.class);

	public static final String DEFAULT_SQL="select * from sys_clt_user where state = '0'";
	private static final String APPTYPE = java.util.ResourceBundle.getBundle("app").getString("appType");
	public static List getDefaultJobs()
	{
		String sql = DEFAULT_SQL;
		if("NEIWANG".equals(APPTYPE)){
			sql += " and user_type='0'";
		}else if("ZHUANWANG".equals(APPTYPE)){
			sql += " and user_type='1'";
		}
		DBOperation operation = DBOperationFactory.createOperation();
		List jobList = new ArrayList();
		List list = null;
		try {
			log.info("初始化调度Sql语句="+sql);
			list = operation.select(sql);
			for(int i=0;list!=null&&i<list.size();i++){
				Map map = (Map)list.get(i);
				JobDetail detail = createJobDetail(
						(String) map.get("JOBNAME"),
						(String) map.get("GROUPNAME"),
						(String) map.get("CLASSNAME"));
				Trigger trigger = createTriggle(
						(String) map.get("HOURS"),
						(String) map.get("MINUTES"), 
						(String) map.get("SECONDS"), 
						(String) map.get("STRATEGY"),
						(String) map.get("STRATEGYDESC"), 
						(String) map.get("STARTDATE"), 
						(String) map.get("ENDDATE"));
				jobList.add(new JobContainer(detail,trigger));
			}
		} catch (DBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobList;
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static JobContainer getJobById(DataBus db)
	{
		JobDetail detail = createJobDetail(
					db.getValue(VoSysCltUser.ITEM_JOBNAME),
					db.getValue(VoSysCltUser.ITEM_GROUPNAME),
					db.getValue(VoSysCltUser.ITEM_CLASSNAME));
		Trigger trigger = createTriggle(
					db.getValue(VoSysCltUser.ITEM_HOURS),
					db.getValue(VoSysCltUser.ITEM_MINUTES),
					db.getValue(VoSysCltUser.ITEM_SECONDS),
					db.getValue(VoSysCltUser.ITEM_STRATEGY),
					db.getValue(VoSysCltUser.ITEM_STRATEGYDESC),
					db.getValue(VoSysCltUser.ITEM_STARTDATE),
					db.getValue(VoSysCltUser.ITEM_ENDDATE));
		JobContainer job = new JobContainer(detail, trigger);
		return job;
	}
	
	private static JobDetail createJobDetail(String jobName,String groupName,String className){
		JobDetail jobDetail = new JobDetail();
		try {
			jobDetail.setName(jobName);
			jobDetail.setGroup(groupName);
			jobDetail.setJobClass(Class.forName(className));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			log.error("jobclass not found!");
		}
		return jobDetail;
	}
	
	private static Trigger createTriggle(String hour, String minutes,
			String seconds, String strategy, String strategyDesc, String start,
			String end)
	{
		StringBuffer time = new StringBuffer();

		if ("0".equals(strategy)) {
			time.append(seconds).append(" ").append(minutes).append(" ")
					.append(hour).append(" * * ? *");
		} else {
			time.append(seconds).append(" ").append(minutes).append(" ")
					.append(hour).append(" ? * ").append(strategyDesc).append(
							" *");
		}
		log.info("the trigger time is " + time.toString());
		try {
			CronTrigger trigger = new CronTrigger("myTrigger",
					"myTriggerGroup", time.toString());
			if (start != null && !"".equals(start))
				trigger.setStartTime(CalendarUtil.parseStringToDate(start));
			if (end != null && !"".equals(end))
				trigger.setEndTime(CalendarUtil.parseStringToDate(end));
			return trigger;
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("myTrigger can not be created!");
			return null;
		}
	}
}
