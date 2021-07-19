package com.gwssi.dw.component.quartz;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import cn.gwssi.common.context.DataBus;

import com.gwssi.common.util.Constants;


public class QuartzInitService {
	private static Log log = LogFactory.getLog(QuartzInitService.class);

	private static Scheduler scheduler = null;
	
	private static QuartzInitService factory;
	
	private static final String CONFIGFILE = "quartz.properties";
	
	private QuartzInitService() throws Exception{

		try {
			Properties props = new Properties();
			String path=java.util.ResourceBundle.getBundle("app").getString("freemarkerPath")+CONFIGFILE;
			props.load(new BufferedInputStream(new FileInputStream(path)));
			
			//props.load(new BufferedInputStream(new FileInputStream(this.getClass().getResource("/quartz.properties").getFile())));
			SchedulerFactory factory = new StdSchedulerFactory(props);
			scheduler = factory.getScheduler();
			scheduler.start();
			log.info("Scheduler has been started...");
		} catch (FileNotFoundException e) {
			log.error("Quartz.properties has not been found!");
			throw e;
		} catch (IOException e) {
			log.error("Quartz.properties can not be opened!");
			throw e;
		} catch (SchedulerException e) {
			log.error("Creating the Object of SchedulerFactory error!");
			throw e;
		}
	}
	
	public static synchronized void init() throws Exception{
		if (factory == null){
			factory = new QuartzInitService();
		}
	}

	public static synchronized void executeJob() throws Exception{
		if(scheduler==null)
		    init();
		JobManager jobManager = new JobManager(scheduler);
//		List list = JobFactory.getDefaultJobs();
//		for(int i=0;list!=null&&i<list.size();i++){
//			JobContainer container = (JobContainer)list.get(i);
//			jobManager.scheduleJob(container);
//		}
		log.info("all jobs have been executed!");
	}

	public static synchronized void resetJob(DataBus db) throws Exception{
		if(scheduler==null)
		    init();
		JobManager jobManager = new JobManager(scheduler);
	    JobContainer container = JobFactory.getJobById(db);	    
		jobManager.rescheduleJob(container);		
		log.info(" job have been reset!");
	}


	public static synchronized void stopJob(DataBus db) throws Exception{
		if(scheduler==null)
		    init();
		JobManager jobManager = new JobManager(scheduler);
	    JobContainer container = JobFactory.getJobById(db);	    
		jobManager.deleteJob(container.getJobDetail().getName(),container.getJobDetail().getGroup());	
		log.info(" job have been stop!");
	}

	
	public static synchronized void startJob(DataBus db) throws Exception{
		if(scheduler==null)
		    init();
		JobManager jobManager = new JobManager(scheduler);
	    JobContainer container = JobFactory.getJobById(db);
		jobManager.scheduleJob(container);		
		log.info(" job have been start!");
	}
	
	public static synchronized void destroy() throws Exception{
		log.debug("Object scheduler can not be shut down!");
		if(scheduler!=null){
			try {
				scheduler.shutdown();
			} catch (SchedulerException e) {
				log.error("Object scheduler can not be shut down!");
				throw e;
			}
		}
	}		
	public static Scheduler getScheduler() throws Exception{
		if(scheduler==null)
		    init();
		return scheduler;
	}
}
