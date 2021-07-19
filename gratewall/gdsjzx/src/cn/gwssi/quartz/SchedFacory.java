package cn.gwssi.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
public class SchedFacory {
	
	private static  SchedulerFactory sf = new StdSchedulerFactory();	
	
	public static Scheduler getScheduler() throws SchedulerException {
			return sf.getScheduler();
	}
}


