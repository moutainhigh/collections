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
																		.getName());	// ��־

	private static String			JOB_GROUP_NAME		= "SRV_JOBGROUP_NAME";

	private static String			TRIGGER_GROUP_NAME	= "SRV_TRIGGERGROUP_NAME";

	private static String			SRV_NAME			= "SRV_NAME";

	/**
	 * ���ƻ�������ӵ�Scheduler�� ������������Ѵ��ڣ���ɾ���ɵģ���������ӣ�
	 * ���ƻ�ID��ΪJobDetail��name����ֵ��trigger��name����ֵ
	 * 
	 * @param jhrwvo
	 * @throws Exception
	 */
	public static void addToScheduler(VoShareSrvScheduling srvVo)
			throws Exception
	{
		logger.info("��Ӽƻ�����");
		//System.out.println("��Ӽƻ�����");
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
			logger.info("�ƻ�����IDΪ�� " + srvId);

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
					// ����������Ѵ��ڣ���ɾ���ɵģ����������
					removeFromScheduler(srvVo);
					scheduler.addJobListener(new SimpleJobListener());
					jobDetail.addJobListener("SimpleJobListener");
					scheduler.scheduleJob(jobDetail, trigger);
				}
				if (!scheduler.isShutdown()) {
					scheduler.start();
					logger.info("����������...");
				}
			}
		} catch (Throwable e) {
			logger.info("�������񱨴�" + e);
			e.printStackTrace();
			throw new Exception("���ƻ�������ӵ�ִ�ж����쳣 ", e);
		}
	}

	/**
	 * 
	 * removeFromScheduler ���ƻ���Scheduler��ɾ��
	 * 
	 * @param srvVo
	 * @throws TxnException
	 *             void
	 * @Exception �쳣����
	 * @since CodingExample Ver(���뷶���鿴) 1.1
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
					logger.info("ɾ���ƻ����� id="+srvId);
					if(!(unrs&&delrs)){
						logger.info("unscheduleJobִ�н����"+unrs+"---deleteJobִ�н����"+delrs);
					}					
				}else {
					logger.info("δ�ҵ�service_idΪ"+srvId+"�ļƻ�����ɾ��ʧ�ܣ�");
				}
			}
		} catch (Throwable e) {
			logger.info("ɾ���ƻ����񱨴�" + e);
			e.printStackTrace();
		}
	}

	/**
	 * ����quartz��trigger �����ƻ���ID��Ϊtrigger��name����ֵ��
	 * 
	 * @param tGkJhrwBO
	 * @return
	 * @throws Exception
	 */
	private static Trigger genTrigger(VoShareSrvScheduling srvVo)
			throws Exception
	{
		System.out.println("��ʼ����������ʽ");
		Trigger trigger = null;
		System.out.println("����ƻ�����=" + srvVo.getScheduling_type());
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
				// һ��
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
				// ÿ��0 0 12 * * ? 0 0/1 14-16 * * ?
				expSbf.append("0 ").append(Integer.parseInt(jhrwkssj_hm[1]))
						.append("/").append(intervalTime).append(" ").append(
								Integer.parseInt(jhrwkssj_hm[0])).append("-")
						.append(jhrwjssj_hm[0]).append(" * * ?");
			} else if (TaskSchedulingConstants.JHRWLX_MZ.equals(srvType)) {
				// ÿ��
				//�ִ����� ���� �� ʱ �� �� �� "0 15 10 ? * MON-FRI" 0 0/30 0-24 ? * MON-FRI
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
				
				//ƴ�����ֶ�
				int len=jhrwZts.length;
				for (int i = 0; i < len; i++) {
					System.out.println("" + jhrwZts[i]);
					if(i==(len-1)){
						expSbf.append(map.get(jhrwZts[i]));
					}else{//���ŷָ�
						expSbf.append(map.get(jhrwZts[i])).append(",");
					}
					
				}
			} else if (TaskSchedulingConstants.JHRWLX_MY.equals(srvType)) {
				// ÿ��
				expSbf.append("0 ").append(Integer.parseInt(jhrwkssj_hm[1]))
						.append("/").append(intervalTime).append(" ").append(
								Integer.parseInt(jhrwkssj_hm[0])).append("-")
						.append(jhrwjssj_hm[0]).append(" ").append(
								SchedulingDay).append(" * ?");
			}
			if (!CronExpression.isValidExpression(expSbf.toString()))
				throw new Exception("genTrigger���ʽ���Ϸ���" + expSbf.toString());
			CronTrigger cronTrigger = new CronTrigger();
			cronTrigger.setName(srvId);
			try {
				cronTrigger.setCronExpression(expSbf.toString());
			} catch (ParseException e) {
				throw new Exception("genTrigger�������ʽʱ�쳣��" + expSbf.toString(),
						e);
			}
			System.out.println("expSbf.toString()=" + expSbf.toString());
			trigger = cronTrigger;
		}
		return trigger;
	}

	/**
	 * ����һ���ַ�����˵������ִ�й���
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
			// ÿ��
			expSbf.append("ÿ��");
		} else if (TaskSchedulingConstants.JHRWLX_MZ.equals(srvType)) {
			// ÿ��
			// ����JHRW_ZT�ֶ��趨���ڼ�
			if (jhrwZts.length == 7) {
				expSbf.append("ÿ�� ");
			} else if (schedulingWeek.equals("1,2,3,4,5")) {
				expSbf.append("ÿ�ܹ����� ");
			} else {
				expSbf.append("ÿ��");
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
			// ÿ��
			expSbf.append("ÿ�µ� ").append(SchedulingDay).append(" ��");
		}
		expSbf.append(jhrwkssj_hm[0]).append("��").append(jhrwkssj_hm[1])
				.append("�ֵ� ");
		expSbf.append(jhrwjssj_hm[0]).append("��").append(jhrwjssj_hm[1])
				.append("�� ");
		time = hour - Integer.parseInt(jhrwkssj_hm[0]);
		hour = Integer.parseInt(jhrwjssj_hm[0])
				- Integer.parseInt(jhrwkssj_hm[0]);
		minute = Integer.parseInt(jhrwjssj_hm[1])
				- Integer.parseInt(jhrwkssj_hm[1]);
		if (intervalTime != null && !"".equals(intervalTime)) {
			expSbf.append("ÿ" + intervalTime + "����ִ��һ��");
		} else {
			expSbf.append("ÿ" + (hour * 60 + minute)
					/ Integer.parseInt(schedulingCount) + "����ִ��һ��");
		}
		return expSbf.toString();
	}

	/**
	 * ��ȡ�ƻ���һ��ִ��ʱ��
	 * 
	 * @param jhrwId
	 *            �ƻ�����ID
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
	// System.out.println("��ȡ�ƻ���һ��ִ��ʱ���쳣��jhrwId:" + jhrwId);
	// }
	// return nextFireTime;
	// }
	// private void jdbcJobStoreRunner()
	// {
	// try {
	// SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	// Scheduler scheduler = schedulerFactory.getScheduler();
	// // ��ȡ�����������еĴ�������
	// String[] triggerGroups = scheduler.getTriggerGroupNames();
	// // ���»ָ���tgroup1���У���Ϊtrigger1_1������������
	// for (int i = 0; i < triggerGroups.length; i++) {
	// String[] triggers = scheduler.getTriggerNames(triggerGroups[i]);
	// for (int j = 0; j < triggers.length; j++) {
	// Trigger tg = scheduler.getTrigger(triggers[j],
	// triggerGroups[i]);
	// if (tg instanceof SimpleTrigger
	// && tg.getFullName().equals("tgroup1.trigger1_1")) {
	// // ���������ж�
	// // �ָ�����
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
		 * jhrwvo.setJhrwmc("����"); jhrwvo.setJhrwsj("01:00");
		 * jhrwvo.setJhrwrq(""); // jhrwvo.setJhrwZq(3); jhrwvo.setJhrwZt("1");
		 * jhrwvo.setJhrwzx_zt("0");
		 * jhrwvo.setjob_class_name(TaskSchedulingConstants.JOB_CLASS_NAME); try {
		 * SimpleTriggerRunner.addToScheduler(jhrwvo); } catch (Exception e) {
		 * e.printStackTrace(); }
		 * 
		 * Date d = SimpleTriggerRunner.getFinalFireTime("1111111111");
		 * System.out.println("��ȡ�ƻ���һ��ִ��ʱ��:" + d);
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
		// System.out.println("ִ�й���Ϊ��" + s);
	}

}
