package cn.gwssi.datachange.msg_push;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;

import cn.gwssi.datachange.msg_push.api.MsgSender;
import cn.gwssi.datachange.msg_push.api.Result;
import cn.gwssi.datachange.msg_push.bus.PushMsgWithFtpJob;
import cn.gwssi.datachange.msg_push.ftpImpl.FtpMsg;
import cn.gwssi.datachange.msg_push.ftpImpl.FtpReceiver;
import cn.gwssi.datachange.msg_push.ftpImpl.FtpSenderFactory;
import cn.gwssi.quartz.SchedFacory;

public class TestFtp {

	public static void main(String[] args) throws SchedulerException, InterruptedException {
//		testFtp();
		Scheduler sched = SchedFacory.getScheduler();    
//		JobDetail job = JobBuilder.newJob(PushMsgWithFtpJob.class).
//				             withIdentity("job-1", "group-1").build();
		JobDetail job = null;
		try {
			TriggerKey tk = new TriggerKey("trigger-1", "trigger-1-1");
			JobKey jk = new JobKey("job-1", "group-1");
			sched.start();
			CronTrigger trigger = new CronTriggerImpl("trigger-1", "trigger-1-1", "0/2 * * * * ?");
			sched.scheduleJob(job, trigger);
			Thread.sleep(8000);
			sched.pauseTrigger(tk);// 停止触发器  
            sched.unscheduleJob(tk);// 移除触发器  
            sched.deleteJob(jk);// 删除任务  
			System.out.println("delete");
			Thread.sleep(6000);
			sched.scheduleJob(job, new CronTriggerImpl("trigger-1", "trigger-1-1", "0/2 * * * * ?"));
			sched.start();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void testFtp() {
		MsgSender sender = new FtpSenderFactory().createMsgSender();
		FtpMsg m = new FtpMsg("/tmp/a.txt");
		FtpReceiver r = new FtpReceiver("localhost", 323, "hello", "world", "/tmp/a/b");
		Result result = null;
		try {
			result = sender.sendMsg(m, r);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(result == null);
	}
}
