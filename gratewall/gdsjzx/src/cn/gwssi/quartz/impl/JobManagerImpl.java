package cn.gwssi.quartz.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.gwssi.quartz.inter.JobServer;
import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gwssi.optimus.core.exception.OptimusException;

import cn.gwssi.datachange.datashare.controller.ShareResourceController;
import cn.gwssi.quartz.inter.JobManagerInterface;
import cn.gwssi.quartz.model.TPtTaskBO;
import cn.gwssi.quartz.service.JobDataService;

@Component
public class JobManagerImpl  implements JobManagerInterface{
	private static  Logger log=Logger.getLogger(JobManagerImpl.class);

	@Autowired
	private JobDataService jobService;

	/* (non-Javadoc)负责执行
	 * @see cn.gwssi.quartz.inter.JobManagerInterface#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext jobContext) {
		Connection conn=null;
		JobDataMap dataMap = jobContext.getJobDetail().getJobDataMap();

		String taskId=dataMap.getString("taskId");
		String classz=dataMap.getString("clazz");
		String method=dataMap.getString("method");
		String paramers=dataMap.getString("paramers");
		this.jobService=(JobDataService) dataMap.get("JobDataService");

		String node;
		try {
			node = jobService.getJobLockById(taskId);
			if("".equals(node)){
				jobService.catchTaskLock(taskId);//加锁成功之后，往下执行，不成功，则抛出异常，证明该服务被其他应用节点运行
			}
			String myNode=InetAddress.getLocalHost().toString();
			//			if(node.equals(myNode)){
			this.excuteTask(classz,paramers);
			jobService.releaseTaskLock(taskId);//不清除，对任务进行锁定
			//			}
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
		}


	}



	//执行之前，需要对其他应用服务器进行通信，判断服务器状态


	/**真实执行
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public boolean excuteTask(String classz,String method,String paramers) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException{
		Object obj=Class.forName(classz).newInstance();
		Method[] methods= obj.getClass().getMethods();		
		for(int i=0;i<methods.length;i++){
			Method methodY=	methods[i];
			String name=methodY.getName();
			if(method.toLowerCase().equals(name)){SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String newtime=sdf.format(new Date().getTime());
			log.debug("当前任务类="+classz+" 当前方法名"+method+" 当前执行时间="+newtime);
			methodY.invoke(obj, null);
			}
		}

		return false;
	}






	/**真实执行
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public boolean excuteTask(String classz,String paramers) throws Exception, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException{
		JobServer jobServer =(JobServer) Class.forName(classz).newInstance();
		jobServer.job(paramers);
		return true;
	}



}
