package cn.gwssi.quartz;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.eclipse.jetty.util.log.Log;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerMetaData;
import org.quartz.TriggerKey;
import org.quartz.impl.calendar.CronCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwssi.optimus.core.exception.OptimusException;

import cn.gwssi.datachange.msg_push.bus.PushMsgWithFtpJob;
import cn.gwssi.quartz.inter.JobManagerInterface;
import cn.gwssi.quartz.model.TPtTaskBO;
import cn.gwssi.quartz.service.JobDataService;

@Component
public class CronTriggerExecut {
	
	@Autowired
	public JobDataService jobService;
	
	@Autowired
	private JobManagerInterface jobManager;

	public void addJob(String intervalTime,Date executDate,String Method,String jobId,JobManagerInterface jobManager,String calzz,String paramers) throws Exception {
		Logger log = LoggerFactory.getLogger(CronTriggerExecut.class);
		CronTrigger trigger = null;
		JobDetail job = null;
		Date ft = null;
		Scheduler sched = SchedFacory.getScheduler();    
		SimpleDateFormat sdf = new SimpleDateFormat(intervalTime);
		String cron=sdf.format(executDate);
		//  String cron="0 40 15 17 03 ? 2016";
		//  String jobID=UUID.randomUUID().toString();
		
		job = newJob(jobManager.getClass()).withIdentity("job-"+jobId, "group-"+jobId).build();
		job.getJobDataMap().put("method", Method);
		job.getJobDataMap().put("clazz", calzz);
		job.getJobDataMap().put("taskId", jobId);
		job.getJobDataMap().put("JobDataService", jobService);
		job.getJobDataMap().put("paramers", paramers);

		//读取数据库定时任务配置
		
		trigger = newTrigger()
				.withIdentity("trigger-"+jobId, "group-"+jobId)
				.withSchedule(cronSchedule(cron))
				.build();       

		System.out.println(job.getJobDataMap().getString("className")+"是否有参数");
		ft = sched.scheduleJob(job, trigger);

		log.info(job.getKey() + "时间：" + sdf.format(ft)
				+ " 时间："
				+ trigger.getCronExpression());

//     	sched.start();

		SchedulerMetaData metaData = sched.getMetaData();
		log.debug("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
	}
	
	//insert job to DB
	public void storeJob(String intervalTime,Date executDate,String Method,String jobId ,String calzz,String paramers, String status, String starttime) {
//		cron.addJob(config.getFrequency(), new Date(), "", config.getServiceid(), 
//				new PushMsgWithFtpJob(), "cn.gwssi.datachange.msg_push.bus.PushMsgJob",
//				config.getServiceid());
		TPtTaskBO bo = new TPtTaskBO();
		if(intervalTime == null)
			return ;
		//可直接接受crontab表达式，或者按照频率来计算（天） 则实际频率不精准 @TODO 
		intervalTime = formatCron(intervalTime);
		bo.setTimeparamer(intervalTime);
		bo.setClassname(calzz);
		bo.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		bo.setExcutestate("");
		bo.setMethodname("");
		bo.setTaskid(jobId);
		bo.setStarttime(starttime);
		bo.setParamers(jobId);
		bo.setState("0".equals(status) ? "1" : "0");
		try {
			//任务列表中是否已存在
			if(jobService.getJobById(jobId).getTaskid() == null){
				jobService.getPersistenceDAO().insert(bo);
			}else{
				List<String> params = new ArrayList<String>();
			    params.add(jobId);
			    jobService
			    .getPersistenceDAO()
			    .execute("update T_PT_TASK set State='" +("0".equals(status) ? "1'," : "0',")+ 
			    		 " timeParamer='" + intervalTime + "',starttime='" + starttime +  
			    		"' where taskid=?", params);
			}
			if("0".equals(status)){ //acceptWay为启动才添加
				this.addJob(intervalTime, executDate, Method, jobId, jobManager, calzz, paramers);
	//			cron.addJob(config.getFrequency(), new Date(), "", config.getServiceid(), 
	//					new PushMsgWithFtpJob(), "cn.gwssi.datachange.msg_push.bus.PushMsgJob",
	//					config.getServiceid());
			}
		} catch (OptimusException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String formatCron(String intervalTime) {
		if(!CronExpression.isValidExpression(intervalTime)){
			int c = Integer.parseInt(intervalTime);
			//按频率折算成cron表达式(计算如果有小数位，则实际频率不精准 @TODO)
			if(c <= 24){
				c = 24 / c;
				intervalTime = "* * 0/" + c + " * * ?";
			}else if(c < 24 * 60){
				c = 24 * 60 / c;
				intervalTime = "* 0/" + c + " * * * ?";
			}else{
				c = 86400 / c;
				intervalTime = "0/" + c + " * * * * ?";
			}
		}
		return intervalTime;
	}

	//修改定时任务触发器,copy|||| update by @wuyincheng 07-20
	public void updateJob(String jobTriId, String cron) throws Exception {
		Logger log = LoggerFactory.getLogger(CronTriggerExecut.class);
		try {  
			Scheduler sched = SchedFacory.getScheduler(); 
			TriggerKey triggerKey = TriggerKey.triggerKey(
					"trigger-"+jobTriId, "group-"+jobTriId);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey); 
			new Thread(new SchedEventLister(triggerKey)).start();
			if (trigger == null) {  
				return;  
			}  
			Date ft = null;
			// trigger已存在，则更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(cron); 
			String oldTime = trigger.getCronExpression();  
			if (!oldTime.equalsIgnoreCase(cron)) {  
				// 按新的cronExpression表达式重新构建trigger
				trigger = (CronTrigger) trigger.getTriggerBuilder()
						.withSchedule(scheduleBuilder).build();
				// 按新的trigger重新设置job执行
				sched.rescheduleJob(triggerKey, trigger);  
				sched.start();
			}  
		} catch (Exception e) {  
			throw new RuntimeException(e);  
		}  
	}

	//修改定时任务
	public void updateJob(String jobTriId,Date time) throws Exception {
		Logger log = LoggerFactory.getLogger(CronTriggerExecut.class);
		try {  
			Scheduler sched = SchedFacory.getScheduler(); 
			TriggerKey triggerKey = TriggerKey.triggerKey(
					"trigger-"+jobTriId, "group-"+jobTriId);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey); 
			new Thread(new SchedEventLister(triggerKey)).start();
			if (trigger == null) {  
				return;  
			}  
			Date ft = null;
			SimpleDateFormat sdf = new SimpleDateFormat("*/20 * HH dd MM ? yyyy");
			String cron=sdf.format(time);
			// trigger已存在，则更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
					.cronSchedule(cron); 

			String oldTime = trigger.getCronExpression();  
			if (!oldTime.equalsIgnoreCase(cron)) {  

				// 按新的cronExpression表达式重新构建trigger
				trigger = (CronTrigger) trigger.getTriggerBuilder()
						.withSchedule(scheduleBuilder).build();
				// 按新的trigger重新设置job执行
				sched.rescheduleJob(triggerKey, trigger);  
				sched.start();
			}  
		} catch (Exception e) {  
			throw new RuntimeException(e);  
		}  
	} 


	//删除定时任务
	public void deleteJob(String jobTriId) throws Exception {
		Scheduler sched = SchedFacory.getScheduler();   
		JobKey jobKey = JobKey.jobKey(
				"job-"  + jobTriId,  "group-"  + jobTriId); 
		TriggerKey triggerKey = TriggerKey.triggerKey(
				"trigger-"  + jobTriId,  "group-"  + jobTriId); 

		sched.resumeTrigger(triggerKey);//停止触发器
		sched.deleteJob(jobKey);
	} 
	
	
	
	//删除所有定时任务
	//删除定时任务
//	public void deleteAllJob(String jobTriId) throws Exception {
//		Scheduler sched = SchedFacory.getScheduler();   
//		JobKey jobKey = JobKey.jobKey(
//				"job-"  + jobTriId,  "group-"  + jobTriId); 
//		TriggerKey triggerKey = TriggerKey.triggerKey(
//				"trigger-"  + jobTriId,  "group-"  + jobTriId); 
//		sched.resumeAll();
//		sched.
//		sched.resumeTrigger();//停止触发器
//		sched.deleteJob(jobKey);
//	} 
	
	
	//暂停定时任务
	public void pauseJob(String jobTriId) throws Exception {
		Scheduler sched = SchedFacory.getScheduler();
		JobKey jobKey = JobKey.jobKey(
				"job-"  + jobTriId,  "group-"  + jobTriId); 
		sched.pauseJob(jobKey);

	} 
	//恢复定时任务
	public void resumeJob(String jobTriId) throws Exception {
		Scheduler sched = SchedFacory.getScheduler();
		JobKey jobKey = JobKey.jobKey(
				"job-"  + jobTriId,  "group-"  + jobTriId); 
		sched.resumeJob(jobKey);
	} 

	@PostConstruct
	public void initJob() throws Exception{
		java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		List<TPtTaskBO> jobList=jobService.getJobList();
		for( TPtTaskBO bo : jobList){
			this.deleteJob( bo.getTaskid());
			jobService.releaseALlTaskLock();
			//去除所有定时任务绑定，进行重新自动分配
			this.addJob(bo.getTimeparamer(), 
					formatter.parse(bo.getCreatetime()),
					bo.getMethodname(), bo.getTaskid(), jobManager , bo.getClassname(),bo.getParamers());
		}
		//执行任务初始化，查询任务时间详情，需要做逻辑判断，当前任务是否执行，如果没有执行，怎么办？
//		//初始化查询数据库，放置内存中，查看已有调度
//		this.addJob("*/10 * HH dd MM ? yyyy",new Date(), "", "","111",jobManager,"dsadoabodboasd");
//		this.addJob("*/5 * HH dd MM ? yyyy",new Date(), "", "","222",jobManager,"cdcdcdcdcdcdcdcd");
//		sched.start();
	}
}