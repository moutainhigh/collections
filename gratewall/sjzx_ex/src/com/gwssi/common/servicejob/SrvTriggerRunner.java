package com.gwssi.common.servicejob;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import cn.gwssi.common.component.exception.TxnException;
import cn.gwssi.common.component.logger.TxnLogger;

import com.gwssi.common.constant.TaskSchedulingConstants;
import com.gwssi.common.task.SimpleJobListener;
import com.gwssi.common.task.SimpleTriggerRunner;
import com.gwssi.share.ftp.vo.VoShareSrvScheduling;

public class SrvTriggerRunner
{

	private static SchedulerFactory	gSchedulerFactory	= new StdSchedulerFactory();

	protected static Logger			logger				= TxnLogger
																.getLogger(SrvTriggerRunner.class
																		.getName());	// 日志

	private static String			JOB_GROUP_NAME		= "SRV_JOBGROUP_NAME";

	private static String			TRIGGER_GROUP_NAME	= "SRV_TRIGGERGROUP_NAME";

	private static String			SRV_NAME			= "SRV_NAME";

	/**
	 * 将计划任务添加到Scheduler中 （如果该任务已存在，先删除旧的，再重新添加）
	 * 将计划ID作为JobDetail的name属性值和trigger的name属性值
	 * 
	 * @param jhrwvo
	 * @throws Exception
	 */
	public static void addToScheduler(VoShareSrvScheduling srvVo)
			throws Exception
	{
		logger.info("添加计划任务");
		//System.out.println("添加计划任务");
		try {
			Scheduler scheduler = gSchedulerFactory.getScheduler();
			// srvvo.getJob_class_name();
			String srvSchedulingId = srvVo.getSrv_scheduling_id();
			String srvId = srvVo.getService_id();
			String srvType = srvVo.getScheduling_type();
			String startTime = srvVo.getStart_time();
			String endTime = srvVo.getEnd_time();
			String className = srvVo.getJob_class_name();

			logger.info("job_class_name is " + className);
			logger.info("计划服务ID为： " + srvId);

			if (className != null && !"".equals(className)) {
				JobDetail jobDetail = new JobDetail(srvId, scheduler.DEFAULT_GROUP,
						Class.forName(className));
				Trigger trigger = genTrigger(srvVo);
				if (trigger != null) {
					jobDetail.getJobDataMap().put("srvId", srvId);
					jobDetail.getJobDataMap().put("srvSchedulingId",
							srvSchedulingId);
					jobDetail.getJobDataMap().put("srvName", SRV_NAME);
					jobDetail.getJobDataMap().put("srvType", srvType);
					jobDetail.getJobDataMap().put("startTime", startTime);
					jobDetail.getJobDataMap().put("endTime", endTime);
					// 如果该任务已存在，先删除旧的，再重新添加
					removeFromScheduler(srvVo);
					scheduler.addJobListener(new SimpleJobListener());
					jobDetail.addJobListener("SimpleJobListener");
					scheduler.scheduleJob(jobDetail, trigger);
				}
				if (!scheduler.isShutdown()) {
					scheduler.start();
					logger.info("任务启动了...");
				}
			}
		} catch (Throwable e) {
			logger.info("启动任务报错" + e);
			e.printStackTrace();
			throw new Exception("将计划任务添加到执行队列异常 ", e);
		}
	}

	/**
	 * 
	 * removeFromScheduler 将计划从Scheduler中删除
	 * 
	 * @param srvVo
	 * @throws TxnException
	 *             void
	 * @Exception 异常对象
	 * @since CodingExample Ver(编码范例查看) 1.1
	 */
	public static void removeFromScheduler(VoShareSrvScheduling srvVo)
			throws TxnException
	{
		
		try {
			if (srvVo != null) {
				String srvId = srvVo.getService_id();
				
				Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
				
				JobDetail jobDetail=scheduler.getJobDetail(srvId, null);
				
				if (jobDetail != null) {
					
					if (scheduler.getTrigger(srvId, Scheduler.DEFAULT_GROUP) != null) {
						scheduler.pauseTrigger(srvId, null);
					}
					boolean unrs=false,delrs=false;
					unrs=scheduler.unscheduleJob(srvId, null);
					delrs=scheduler.deleteJob(srvId, null);
					logger.info("删除计划任务 id="+srvId);
					if(!(unrs&&delrs)){
						logger.info("unscheduleJob执行结果："+unrs+"---deleteJob执行结果："+delrs);
					}					
				}else {
					logger.info("未找到service_id为"+srvId+"的计划任务，删除失败！");
				}
			}
		} catch (Throwable e) {
			logger.info("删除计划任务报错" + e);
			e.printStackTrace();
		}
	}

	/**
	 * 生成quartz的trigger （将计划的ID作为trigger的name属性值）
	 * 
	 * @param tGkJhrwBO
	 * @return
	 * @throws Exception
	 */
	private static Trigger genTrigger(VoShareSrvScheduling srvVo)
			throws Exception
	{
		System.out.println("开始计算任务表达式");
		Trigger trigger = null;
		System.out.println("服务计划类型=" + srvVo.getScheduling_type());
		if (srvVo != null
				&& (srvVo.getScheduling_type() != null && !"".equals(srvVo
						.getScheduling_type()))) {
			String srvId = srvVo.getService_id();
			String srvType = srvVo.getScheduling_type();
			String SchedulingDay = srvVo.getScheduling_day();
			String startTime = srvVo.getStart_time();
			String endTime = srvVo.getEnd_time();
			String intervalTime = srvVo.getInterval_time();
			String schedulingWeek = srvVo.getScheduling_week();

			System.out.println("srvId is " + srvId);
			System.out.println("srvType is " + srvType);
			System.out.println("SchedulingDay is " + SchedulingDay);
			System.out.println("startTime is " + startTime);
			System.out.println("endTime is " + endTime);
			System.out.println("intervalTime is " + intervalTime);
			System.out.println("schedulingWeek is " + schedulingWeek);

			String jhrwZts[] = null;
			if (schedulingWeek != null && !"".equals(schedulingWeek)) {
				jhrwZts = schedulingWeek.split(",");
			}
			String jhrwkssj_hm[] = startTime.split(":");
			String jhrwjssj_hm[] = endTime.split(":");

			StringBuffer expSbf = new StringBuffer();
			if (TaskSchedulingConstants.JHRWLX_YC.equals(srvType)) {
				// 一次
				Calendar cfsj = Calendar.getInstance();
				cfsj.set(Calendar.SECOND, 0);
				cfsj.set(Calendar.MINUTE, Integer.parseInt(jhrwkssj_hm[1]));
				cfsj
						.set(Calendar.HOUR_OF_DAY, Integer
								.parseInt(jhrwkssj_hm[0]));

				expSbf.append(cfsj.get(Calendar.SECOND)).append(" ").append(
						cfsj.get(Calendar.MINUTE)).append(" ").append(
						cfsj.get(Calendar.HOUR_OF_DAY)).append(" ").append(
						SchedulingDay).append(" ? ");
			} else if (TaskSchedulingConstants.JHRWLX_MR.equals(srvType)) {
				// 每日0 0 12 * * ? 0 0/1 14-16 * * ?
				expSbf.append("0 ").append(Integer.parseInt(jhrwkssj_hm[1]))
						.append("/").append(intervalTime).append(" ").append(
								Integer.parseInt(jhrwkssj_hm[0])).append("-")
						.append(jhrwjssj_hm[0]).append(" * * ?");
			} else if (TaskSchedulingConstants.JHRWLX_MZ.equals(srvType)) {
				// 每周
				//字串样例 ：秒 分 时 日 月 周 "0 15 10 ? * MON-FRI" 0 0/30 0-24 ? * MON-FRI
				expSbf.append("0 ").append(Integer.parseInt(jhrwkssj_hm[1]))
						.append("/").append(intervalTime).append(" ").append(
								Integer.parseInt(jhrwkssj_hm[0])).append("-")
						.append(jhrwjssj_hm[0]).append(" ? * ");
				Map map = new HashMap();
				map.put("1", TaskSchedulingConstants.JHRW_ZT[0]);
				map.put("2", TaskSchedulingConstants.JHRW_ZT[1]);
				map.put("3", TaskSchedulingConstants.JHRW_ZT[2]);
				map.put("4", TaskSchedulingConstants.JHRW_ZT[3]);
				map.put("5", TaskSchedulingConstants.JHRW_ZT[4]);
				map.put("6", TaskSchedulingConstants.JHRW_ZT[5]);
				map.put("7", TaskSchedulingConstants.JHRW_ZT[6]);
				
				//拼接周字段
				int len=jhrwZts.length;
				for (int i = 0; i < len; i++) {
					System.out.println("" + jhrwZts[i]);
					if(i==(len-1)){
						expSbf.append(map.get(jhrwZts[i]));
					}else{//逗号分割
						expSbf.append(map.get(jhrwZts[i])).append(",");
					}
					
				}
			} else if (TaskSchedulingConstants.JHRWLX_MY.equals(srvType)) {
				// 每月
				expSbf.append("0 ").append(Integer.parseInt(jhrwkssj_hm[1]))
						.append("/").append(intervalTime).append(" ").append(
								Integer.parseInt(jhrwkssj_hm[0])).append("-")
						.append(jhrwjssj_hm[0]).append(" ").append(
								SchedulingDay).append(" * ?");
			}
			if (!CronExpression.isValidExpression(expSbf.toString()))
				throw new Exception("genTrigger表达式不合法：" + expSbf.toString());
			CronTrigger cronTrigger = new CronTrigger();
			cronTrigger.setName(srvId);
			try {
				cronTrigger.setCronExpression(expSbf.toString());
			} catch (ParseException e) {
				throw new Exception("genTrigger解析表达式时异常：" + expSbf.toString(),
						e);
			}
			System.out.println("expSbf.toString()=" + expSbf.toString());
			trigger = cronTrigger;
		}
		return trigger;
	}

	/**
	 * 返回一个字符串，说明任务执行规则
	 * 
	 * @param jhrwvo
	 * @return
	 */
	public static String getZq(VoShareSrvScheduling srvVo)
	{
		StringBuffer expSbf = new StringBuffer();
		String srvType = srvVo.getScheduling_type();
		String SchedulingDay = srvVo.getScheduling_day();
		String startTime = srvVo.getStart_time();
		String endTime = srvVo.getEnd_time();
		String schedulingWeek = srvVo.getScheduling_week();
		String schedulingCount = "";
		String intervalTime = "";
		if (srvVo.getScheduling_count() != null
				&& !"".equals(srvVo.getScheduling_count())) {
			schedulingCount = srvVo.getScheduling_count();
		}
		if (srvVo.getInterval_time() != null
				&& !"".equals(srvVo.getInterval_time())) {
			intervalTime = srvVo.getInterval_time();
		}

		// System.out.println("srvId is " + srvId);
		System.out.println("srvType is " + srvType);
		System.out.println("SchedulingDay is " + SchedulingDay);
		System.out.println("startTime is " + startTime);
		System.out.println("endTime is " + endTime);
		System.out.println("intervalTime is " + intervalTime);
		System.out.println("schedulingWeek is " + schedulingWeek);

		String jhrwZts[] = schedulingWeek.split(",");
		String jhrwkssj_hm[] = startTime.split(":");
		String jhrwjssj_hm[] = endTime.split(":");
		int hour = 24;
		int minute = 0;
		int time;
		if (schedulingCount == null || "".equals(schedulingCount)) {
			schedulingCount = "1";
		}
		if (TaskSchedulingConstants.JHRWLX_MR.equals(srvType)) {
			// 每日
			expSbf.append("每天");
		} else if (TaskSchedulingConstants.JHRWLX_MZ.equals(srvType)) {
			// 每周
			// 根据JHRW_ZT字段设定星期几
			if (jhrwZts.length == 7) {
				expSbf.append("每天 ");
			} else if (schedulingWeek.equals("1,2,3,4,5")) {
				expSbf.append("每周工作日 ");
			} else {
				expSbf.append("每周");
				for (int i = 0; i < jhrwZts.length; i++) {
					if (i == 0) {
						expSbf.append(""
								+ TaskSchedulingConstants.JHRW_EXP[Integer
										.parseInt(jhrwZts[i]) - 1] + " ");
					} else {
						System.out.println("jhrwZts[i]====" + jhrwZts[i]);
						expSbf.append(","
								+ TaskSchedulingConstants.JHRW_EXP[Integer
										.parseInt(jhrwZts[i]) - 1] + " ");
					}
				}
			}
		} else if (TaskSchedulingConstants.JHRWLX_MY.equals(srvType)) {
			// 每月
			expSbf.append("每月第 ").append(SchedulingDay).append(" 天");
		}
		expSbf.append(jhrwkssj_hm[0]).append("点").append(jhrwkssj_hm[1])
				.append("分到 ");
		expSbf.append(jhrwjssj_hm[0]).append("点").append(jhrwjssj_hm[1])
				.append("分 ");
		time = hour - Integer.parseInt(jhrwkssj_hm[0]);
		hour = Integer.parseInt(jhrwjssj_hm[0])
				- Integer.parseInt(jhrwkssj_hm[0]);
		minute = Integer.parseInt(jhrwjssj_hm[1])
				- Integer.parseInt(jhrwkssj_hm[1]);
		if (intervalTime != null && !"".equals(intervalTime)) {
			expSbf.append("每" + intervalTime + "分钟执行一次");
		} else {
			expSbf.append("每" + (hour * 60 + minute)
					/ Integer.parseInt(schedulingCount) + "分钟执行一次");
		}
		return expSbf.toString();
	}

	/**
	 * 获取计划上一次执行时间
	 * 
	 * @param jhrwId
	 *            计划任务ID
	 * @return
	 */
	// public static Date getFinalFireTime(String jhrwId)
	// {
	// Date nextFireTime = null;
	// try {
	// if (jhrwId != null && !"".equals(jhrwId)) {
	// Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
	// Trigger trigger = scheduler.getTrigger(jhrwId, null);
	// if (trigger != null)
	// nextFireTime = trigger.getPreviousFireTime();
	// }
	// } catch (Throwable e) {
	// System.out.println("获取计划上一次执行时间异常，jhrwId:" + jhrwId);
	// }
	// return nextFireTime;
	// }
	// private void jdbcJobStoreRunner()
	// {
	// try {
	// SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	// Scheduler scheduler = schedulerFactory.getScheduler();
	// // 获取调度器中所有的触发器组
	// String[] triggerGroups = scheduler.getTriggerGroupNames();
	// // 重新恢复在tgroup1组中，名为trigger1_1触发器的运行
	// for (int i = 0; i < triggerGroups.length; i++) {
	// String[] triggers = scheduler.getTriggerNames(triggerGroups[i]);
	// for (int j = 0; j < triggers.length; j++) {
	// Trigger tg = scheduler.getTrigger(triggers[j],
	// triggerGroups[i]);
	// if (tg instanceof SimpleTrigger
	// && tg.getFullName().equals("tgroup1.trigger1_1")) {
	// // 根据名称判断
	// // 恢复运行
	// scheduler.rescheduleJob(triggers[j], triggerGroups[i],
	// tg);
	// }
	// }
	// }
	// scheduler.start();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		/*
		 * SimpleTriggerRunner str = new SimpleTriggerRunner(); VoJhrw jhrwvo =
		 * new VoJhrw(); jhrwvo.setJhrwid("1111111111"); jhrwvo.setJhrwlx("03");
		 * jhrwvo.setJhrwmc("测试"); jhrwvo.setJhrwsj("01:00");
		 * jhrwvo.setJhrwrq(""); // jhrwvo.setJhrwZq(3); jhrwvo.setJhrwZt("1");
		 * jhrwvo.setJhrwzx_zt("0");
		 * jhrwvo.setjob_class_name(TaskSchedulingConstants.JOB_CLASS_NAME); try {
		 * SimpleTriggerRunner.addToScheduler(jhrwvo); } catch (Exception e) {
		 * e.printStackTrace(); }
		 * 
		 * Date d = SimpleTriggerRunner.getFinalFireTime("1111111111");
		 * System.out.println("获取计划上一次执行时间:" + d);
		 */
		// str.simpTrigger();
		// VoCollectTaskScheduling jhrwvo = new VoCollectTaskScheduling();
		// jhrwvo.settask_scheduling_id("123456");
		// jhrwvo.setJhrw_lx("01");
		// jhrwvo.setJhrw_start_sj("2:00");
		// jhrwvo.setJhrw_end_sj("3:00");
		// jhrwvo.setJhrw_zt("1,3,4");
		// jhrwvo.setJhrwzx_cs("20");
		// String s = SimpleTriggerRunner.getZq(jhrwvo);
		// System.out.println("执行规则为：" + s);
	}

}
