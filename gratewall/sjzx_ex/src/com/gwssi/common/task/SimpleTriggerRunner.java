package com.gwssi.common.task;

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

import com.gwssi.collect.webservice.vo.VoCollectTaskScheduling;
import com.gwssi.common.constant.TaskSchedulingConstants;

public class SimpleTriggerRunner
{

	private static SchedulerFactory	gSchedulerFactory	= new StdSchedulerFactory();

	protected static Logger			logger				= TxnLogger
																.getLogger(StartupTriggerServlet.class
																		.getName());	// ��־

	private static String			JOB_GROUP_NAME		= "EXTJWEB_JOBGROUP_NAME";

	private static String			TRIGGER_GROUP_NAME	= "EXTJWEB_TRIGGERGROUP_NAME";

	public static void addJob(String jobName, String jobClass, String time)
	{
		try {
			// ��õ�����
			Scheduler sched = gSchedulerFactory.getScheduler();
			// ʵ��������
			JobDetail jobDetail = new JobDetail(jobName, "index_job", Class
					.forName(jobClass));// �������������飬����ִ����
			CronTrigger cronTrigger1 = new CronTrigger("trigger1_1", "tGroup1");
			CronExpression cexp1 = new CronExpression(time);// ÿ��15��10:15�����С�
			cronTrigger1.setCronExpression(cexp1);
			// CronTrigger trigger = new CronTrigger(jobName);// ��������,��������
			// trigger.setCronExpression(time);// ������ʱ���趨
			sched.scheduleJob(jobDetail, cronTrigger1);

			// ������ trigger��������������ִ��Job��
			// ������һ��jobʱ������ʵ��һ��������Ȼ�������������������jobִ�е�������
			// ���������Ҫ�ڸ���ʱ��ִ��һ��job�����ڸ���ʱ�̴���job�����һ��ʱ�䲻ͣ��ִ�еĻ���
			// SimpleTrigger�Ǹ��򵥵Ľ���취�������������������������ȵĴ���job�Ļ���
			// ����˵����ÿ������������������ÿ���µ�10���10��15����jobʱ��CronTrigger�Ǻ����õġ�

			// JobDetail������Job�ڱ��ӵ���������ʱ��������

			// ����
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * ���ƻ�������ӵ�Scheduler�� ������������Ѵ��ڣ���ɾ���ɵģ���������ӣ�
	 * ���ƻ�ID��ΪJobDetail��name����ֵ��trigger��name����ֵ
	 * 
	 * @param jhrwvo
	 * @throws Exception
	 */
	public static void addToScheduler(VoCollectTaskScheduling jhrwvo)
			throws Exception
	{
		logger.debug("��Ӽƻ�����");
		try {
			Scheduler scheduler = gSchedulerFactory.getScheduler();
			String job_class_name = jhrwvo.getJob_class_name();
			String jhrwId = jhrwvo.getcollect_task_id();
			String rwlx = jhrwvo.getJhrw_lx();
			String start = jhrwvo.getJhrw_start_sj();
			String end = jhrwvo.getJhrw_end_sj();
			logger.debug("job_class_name is " + job_class_name);
			logger.debug("�ƻ�����IDΪ�� " + jhrwId);
			if (job_class_name != null && !"".equals(job_class_name)) {
				JobDetail jobDetail = new JobDetail(jhrwId,
						Scheduler.DEFAULT_GROUP, Class.forName(job_class_name));
				Trigger trigger = genTrigger(jhrwvo);
				if (trigger != null) {
					jobDetail.getJobDataMap().put("jhrwId", jhrwId);
					jobDetail.getJobDataMap().put("rwId",
							jhrwvo.gettask_scheduling_id());
					jobDetail.getJobDataMap().put("jhrwMc", "123");
					jobDetail.getJobDataMap().put("rwlx", rwlx);
					jobDetail.getJobDataMap().put("start", start);
					jobDetail.getJobDataMap().put("end", end);
					// ����������Ѵ��ڣ���ɾ���ɵģ����������
					removeFromScheduler(jhrwvo);
					scheduler.addJobListener(new SimpleJobListener());
					jobDetail.addJobListener("SimpleJobListener");
					scheduler.scheduleJob(jobDetail, trigger);
				}
				if (!scheduler.isShutdown()) {
					scheduler.start();
					logger.debug("����������...");
				}
			}
		} catch (Throwable e) {
			logger.debug("�������񱨴�" + e);
			e.printStackTrace();
			throw new Exception("���ƻ�������ӵ�ִ�ж����쳣 ", e);
		}
	}

	/**
	 * ���ƻ���Scheduler��ɾ��
	 * 
	 * @param jhrwvo
	 * @throws TxnException
	 */
	public static void removeFromScheduler(VoCollectTaskScheduling jhrwvo)
			throws TxnException
	{
		logger.debug("ɾ���ƻ�����");
		try {
			if (jhrwvo != null) {
				String jhrwId = jhrwvo.getcollect_task_id();
				Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
				if (scheduler.getJobDetail(jhrwId, null) != null) {
					if (scheduler.getTrigger(jhrwId, null) != null) {
						scheduler.pauseTrigger(jhrwId, null);
					}
					scheduler.unscheduleJob(jhrwId, null);
					scheduler.deleteJob(jhrwId, null);
				}
			}
		} catch (Throwable e) {
			logger.debug("ɾ���ƻ����񱨴�");
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
	private static Trigger genTrigger(VoCollectTaskScheduling jhrwvo)
			throws Exception
	{
		logger.debug("��ʼ����������ʽ");
		Trigger trigger = null;
		logger.debug("jhrwvo.getJhrw_lx()=" + jhrwvo.getJhrw_lx());
		if (jhrwvo != null
				&& (jhrwvo.getJhrw_lx() != null && !"".equals(jhrwvo
						.getJhrw_lx()))) {
			String jhrwLx = jhrwvo.getJhrw_lx();
			String jhrwRq = jhrwvo.getJhrw_rq();
			String jhrwSj = jhrwvo.getJhrw_start_sj();
			String jhrwjsSj = jhrwvo.getJhrw_end_sj();
			String jhrwsjjg = jhrwvo.getJhrwzx_jg();
			String jhrwZt = jhrwvo.getJhrw_zt();

			System.out.println("jhrwLx is " + jhrwLx);
			System.out.println("jhrwRq is " + jhrwRq);
			System.out.println("jhrwSj is " + jhrwSj);
			System.out.println("jhrwjsSj is " + jhrwjsSj);
			System.out.println("jhrwsjjg is " + jhrwsjjg);
			System.out.println("jhrwZt is " + jhrwZt);

			String jhrwZts[] = null;
			if (jhrwZt != null && !"".equals(jhrwZt)) {
				jhrwZts = jhrwZt.split(",");
			}
			String jhrwkssj_hm[] = jhrwSj.split(":");
			String jhrwjssj_hm[] = jhrwjsSj.split(":");

			StringBuffer expSbf = new StringBuffer();
			if (TaskSchedulingConstants.JHRWLX_YC.equals(jhrwLx)) {
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
						jhrwRq).append(" ? ");
			} else if (TaskSchedulingConstants.JHRWLX_MR.equals(jhrwLx)) {
				// ÿ��0 0 12 * * ? 0 0/1 14-16 * * ?
				expSbf.append("0 "); //��
				if(Integer.parseInt(jhrwsjjg)<60){
					expSbf.append(Integer.parseInt(jhrwkssj_hm[1]))
						.append("/").append(jhrwsjjg).append(" ")//��
						.append(Integer.parseInt(jhrwkssj_hm[0]))//ʱ
						.append("-")
						.append(jhrwjssj_hm[0]);
						
				}else{//ʱ��������һСʱ������ת��
					int jg = Integer.parseInt(jhrwsjjg);
					jg=jg/60;//ת����Сʱ��
					expSbf.append(Integer.parseInt(jhrwkssj_hm[1]))//��
						.append(" ")
						.append(Integer.parseInt(jhrwkssj_hm[0]))
						.append("/")
						.append(jg);						
				}
				expSbf.append(" * * ?");//������
			} else if (TaskSchedulingConstants.JHRWLX_MZ.equals(jhrwLx)) {
				// ÿ��"0 15 10 ? * MON-FRI" 0 0/30 0-24 ? * MON-FRI
				expSbf.append("0 ").append(Integer.parseInt(jhrwkssj_hm[1]))
						.append("/").append(jhrwsjjg).append(" ").append(
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
				int len=jhrwZts.length;
				for (int i = 0; i < len; i++) {
//					System.out.println("" + jhrwZts[i]);
			
					if(i==(len-1)){
						expSbf.append(map.get(jhrwZts[i]));
					}else{
						expSbf.append(map.get(jhrwZts[i])).append(",");
					}
				}
			} else if (TaskSchedulingConstants.JHRWLX_MY.equals(jhrwLx)) {
				// ÿ��
				expSbf.append("0 ").append(Integer.parseInt(jhrwkssj_hm[1]))
						.append("/").append(jhrwsjjg).append(" ").append(
								Integer.parseInt(jhrwkssj_hm[0])).append("-")
						.append(jhrwjssj_hm[0]).append(" ").append(jhrwRq)
						.append(" * ?");
			}
			if (!CronExpression.isValidExpression(expSbf.toString()))
				throw new Exception("genTrigger���ʽ���Ϸ���" + expSbf.toString());
			CronTrigger cronTrigger = new CronTrigger();
			cronTrigger.setName(jhrwvo.getcollect_task_id());
			try {
				cronTrigger.setCronExpression(expSbf.toString());
			} catch (ParseException e) {
				throw new Exception("genTrigger�������ʽʱ�쳣��" + expSbf.toString(),
						e);
			}
			logger.debug("expSbf.toString()=" + expSbf.toString());
			trigger = cronTrigger;
		}
		return trigger;
	}

	/**
	 * ��ȡ�ƻ���һ��ִ��ʱ��
	 * 
	 * @param jhrwId
	 *            �ƻ�����ID
	 * @return
	 */
	public static Date getFinalFireTime(String jhrwId)
	{
		Date nextFireTime = null;
		try {
			if (jhrwId != null && !"".equals(jhrwId)) {
				Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
				Trigger trigger = scheduler.getTrigger(jhrwId, null);
				if (trigger != null)
					nextFireTime = trigger.getPreviousFireTime();
			}
		} catch (Throwable e) {
			logger.debug("��ȡ�ƻ���һ��ִ��ʱ���쳣��jhrwId:" + jhrwId);
		}
		return nextFireTime;
	}

	/**
	 * ����һ���ַ�����˵������ִ�й���
	 * 
	 * @param jhrwvo
	 * @return
	 */
	public static String getZq(VoCollectTaskScheduling jhrwvo)
	{
		StringBuffer expSbf = new StringBuffer();
		String jhrwLx = jhrwvo.getJhrw_lx();
		logger.debug("jhrwLx====" + jhrwLx);
		String jhrwRq = jhrwvo.getJhrw_rq();
		String jhrwksSj = jhrwvo.getJhrw_start_sj();
		String jhrwjsSj = jhrwvo.getJhrw_end_sj();
		String jhrwZt = jhrwvo.getJhrw_zt();
		logger.debug("jhrwZt====" + jhrwZt);
		String jhrwzxCs = "";
		if (jhrwvo.getJhrwzx_cs() != null && !"".equals(jhrwvo.getJhrwzx_cs())) {
			jhrwzxCs = jhrwvo.getJhrwzx_cs();
		}
		logger.debug("jhrwzxCs====" + jhrwzxCs);
		String interval_time = jhrwvo.getJhrwzx_jg();
		if (jhrwvo.getJhrwzx_jg() != null && !"".equals(jhrwvo.getJhrwzx_jg())) {
			interval_time = jhrwvo.getJhrwzx_jg();
		}
		String jhrwZts[] = jhrwZt.split(",");
		String jhrwkssj_hm[] = jhrwksSj.split(":");
		String jhrwjssj_hm[] = jhrwjsSj.split(":");
		int hour = 24;
		int minute = 0;
		int time;
		if (jhrwzxCs == null || "".equals(jhrwzxCs)) {
			jhrwzxCs = "1";
		}
		if (TaskSchedulingConstants.JHRWLX_MR.equals(jhrwLx)) {
			// ÿ��
			expSbf.append("��һ�����գ�");
//			System.out.println("ssss"+jhrwvo.getJhrwzx_cs() );
			if (jhrwvo.getJhrwzx_cs().equals("1")) {
				expSbf.append("ÿ��һ��");
			} else {
				int a = Integer.parseInt(jhrwvo.getJhrwzx_jg());
				int b = 60;
				int c = a / b;
				if (c >= 1) {
					expSbf.append("ÿ");
					expSbf.append(c);
					expSbf.append("Сʱһ��");
				} else {
					expSbf.append("ÿ");
					expSbf.append(jhrwvo.getJhrwzx_jg());
					expSbf.append("����ִ��һ��");
				}
			}
		} else if (TaskSchedulingConstants.JHRWLX_MZ.equals(jhrwLx)) {
			// ÿ��
			// ����JHRW_ZT�ֶ��趨���ڼ�
			if (jhrwZts.length == 7) {
				expSbf.append("��һ�����գ�");
			} else if (jhrwZt.equals("1,2,3,4,5")) {
				expSbf.append("��һ�����壬");
			} else {
				expSbf.append("��");
				for (int i = 0; i < jhrwZts.length; i++) {
					if (i == 0) {
						expSbf.append(""
								+ TaskSchedulingConstants.JHRW_EXP[Integer
										.parseInt(jhrwZts[i]) - 1] + " ");
					} else {
//						System.out.println("jhrwZts[i]====" + jhrwZts[i]);
						expSbf.append(","
								+ TaskSchedulingConstants.JHRW_EXP[Integer
										.parseInt(jhrwZts[i]) - 1] + " ");
					}
				}
			}
			if (jhrwvo.getJhrwzx_cs().equals("1")) {
				expSbf.append("ÿ��һ��");
			} else {
				int a = Integer.parseInt(jhrwvo.getJhrwzx_jg());
				int b = 60;
				int c = a / b;
				if (c >= 1) {
					expSbf.append("ÿ");
					expSbf.append(c);
					expSbf.append("Сʱһ��");
				} else {
					expSbf.append("ÿ");
					expSbf.append(jhrwvo.getJhrwzx_jg());
					expSbf.append("����ִ��һ��");
				}
			}
		} else if (TaskSchedulingConstants.JHRWLX_MY.equals(jhrwLx)) {
			// ÿ��
			expSbf.append("ÿ�µ�").append(jhrwRq).append("�죬");
			if (jhrwvo.getJhrwzx_cs().equals("1")) {
				expSbf.append("ִ��һ��");
			} else {
				int a = Integer.parseInt(jhrwvo.getJhrwzx_jg());
				int b = 60;
				int c = a / b;
				if (c >= 1) {
					expSbf.append("ÿ");
					expSbf.append(c);
					expSbf.append("Сʱһ��");
				} else {
					expSbf.append("ÿ");
					expSbf.append(jhrwvo.getJhrwzx_jg());
					expSbf.append("����ִ��һ��");
				}
			}
		}
		
		return expSbf.toString();
	}

	private void jdbcJobStoreRunner()
	{
		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
			// ��ȡ�����������еĴ�������
			String[] triggerGroups = scheduler.getTriggerGroupNames();
			// ���»ָ���tgroup1���У���Ϊtrigger1_1������������
			for (int i = 0; i < triggerGroups.length; i++) {
				String[] triggers = scheduler.getTriggerNames(triggerGroups[i]);
				for (int j = 0; j < triggers.length; j++) {
					Trigger tg = scheduler.getTrigger(triggers[j],
							triggerGroups[i]);
					if (tg instanceof SimpleTrigger
							&& tg.getFullName().equals("tgroup1.trigger1_1")) {
						// ���������ж�
						// �ָ�����
						scheduler.rescheduleJob(triggers[j], triggerGroups[i],
								tg);
					}
				}
			}
			scheduler.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		VoCollectTaskScheduling jhrwvo = new VoCollectTaskScheduling();
		jhrwvo.settask_scheduling_id("123456");
		jhrwvo.setJhrw_lx("01");
		jhrwvo.setJhrw_start_sj("2:00");
		jhrwvo.setJhrw_end_sj("3:00");
		jhrwvo.setJhrw_zt("1,3,4");
		jhrwvo.setJhrwzx_cs("20");
		String s = SimpleTriggerRunner.getZq(jhrwvo);
		System.out.println("ִ�й���Ϊ��" + s);
	}

}
