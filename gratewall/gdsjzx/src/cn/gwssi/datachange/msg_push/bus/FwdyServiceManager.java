package cn.gwssi.datachange.msg_push.bus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import cn.gwssi.datachange.dataservice.model.TPtFwdypzjbxxBO;
import cn.gwssi.datachange.msg_push.service.MsgSenderService;
import cn.gwssi.quartz.CronTriggerExecut;
import cn.gwssi.quartz.SchedFacory;

import com.gwssi.optimus.core.common.ThreadLocalManager;
import com.gwssi.optimus.core.exception.OptimusException;
import com.gwssi.optimus.core.web.event.OptimusResponse;

/**
 * 业务系统中服务订阅监控管理中心
 * @author wuyincheng ,Jul 14, 2016
 */
@Component
public class FwdyServiceManager implements ApplicationContextAware{
	
	private static  Logger log=Logger.getLogger(FwdyServiceManager.class);
	
	@Autowired
	private MsgSenderService dataServiceService;
	
	@Autowired
	private CronTriggerExecut cron;
	
	
	//文件暂存路径
	@Value("${upload.tempDir}")  
	private String tempPath ;
	
	@Autowired
	private PropertyPlaceholderConfigurer propertyConfigurer;
	
/*	private ConcurrentHashMap<String, TPtFwdypzjbxxBO> configs = 
			new ConcurrentHashMap<String, TPtFwdypzjbxxBO>();
*/	
	//增加一个订阅服务
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addConfig(TPtFwdypzjbxxBO tb) {
		cron.storeJob(tb.getFrequency(), 
				      new Date(), 
				      "", 
				      tb.getFwdypzjbxxid() 
				      , "cn.gwssi.datachange.msg_push.bus.PushMsgWithFtpJob", 
				      tb.getFwdypzjbxxid(), tb.getAcceptway(), tb.getStartsubscribetime());
	}
	
	//更新一个订阅服务(同步方法)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public synchronized void updateConfig(TPtFwdypzjbxxBO listener) {
		try {
			//更新对应订阅服务在任务列表中的状态
			List<String> params = new ArrayList<String>();
		    params.add(listener.getFwdypzjbxxid());
			cron.jobService
			    .getPersistenceDAO()
			    .execute("update T_PT_TASK set State='" +("0".equals(listener.getAcceptway()) ? "1'," : "0',")+ 
			    		 " timeParamer='" + listener.getFrequency() + "',starttime='" + listener.getStartsubscribetime() +  
			    		"' where taskid=?", params);
			JobDetail job = SchedFacory.getScheduler()
	                   .getJobDetail(new JobKey("job-" + listener.getFwdypzjbxxid(), "group-" + listener.getFwdypzjbxxid()));
						System.out.println("Fwdypzjbxxid: " + listener.getFwdypzjbxxid());
						System.out.println("JobDetail: " + job);
			//更新quartz中任务状态
			if("0".equals(listener.getAcceptway())){ //启动
				if(job == null) //如果是添加
					addConfig(listener);
				else{
					cron.updateJob(listener.getFwdypzjbxxid(), listener.getFrequency());
					//尝试恢复
					cron.resumeJob(listener.getFwdypzjbxxid());
				}
			}else{//暂停（不提供删除）
				cron.pauseJob(listener.getFwdypzjbxxid());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//增加一个订阅服务
		/*public void addConfig(TPtFwdypzjbxxBO config, String status) {
//			if(config != null && configs.get(config.getServiceid()) == null) {
//				if(null == configs.putIfAbsent(config.getServiceid(), config)){
					try {
//						cron.addJob(config.getFrequency(), new Date(), "", config.getServiceid(), 
//								new PushMsgWithFtpJob(), "cn.gwssi.datachange.msg_push.bus.PushMsgJob",
//								config.getServiceid());
						cron.storeJob(config.getFrequency(), new Date(), "", config.getServiceid() 
								, "cn.gwssi.datachange.msg_push.bus.PushMsgWithFtpJob", config.getServiceid(), status);
					} catch (Exception e) {
						e.printStackTrace();
					}
//				}
//			}
		}*/
	
	//删除一个订阅服务
	public void removeConfig(TPtFwdypzjbxxBO listener) {
		try {
			//调度框架中删除对应任务
			cron.deleteJob(listener.getFwdypzjbxxid());
			List params = new ArrayList();
			params.add(listener.getFwdypzjbxxid());
			//任务列表删除
			cron.jobService.getPersistenceDAO().execute("DELETE FROM T_PT_TASK WHERE TaskId = ?", params);
			//释放锁
			cron.jobService.releaseTaskLock(listener.getFwdypzjbxxid());
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} 
	}
	
	//更新一个订阅服务（有问题，CronTriggerExecut不支持，cron表达式被写死 。先删除后添加？）
//	public void updateConfig(TPtFwdypzjbxxBO config) {
////		if(config != null && configs.get(config.getServiceid()) != null) {
////			configs.put(config.getServiceid(), config);
//			removeConfig(config);
//			addConfig(config);
////		}
//	}
	
	/*public void updateConfig(TPtFwjbxxBO config) {
//		if(config != null && configs.get(config.getServiceid()) != null) {
		if(config.getServicetype().equals("2")){
		    List<String> params = new ArrayList<String>();
		    params.add(config.getServiceid());
			try {
				cron.jobService.
				getPersistenceDAO().execute("update T_PT_TASK set State='" +("0".equals(config.getServicestate()) ? "1" : "0")+ "' where taskid=?", params);
				final TPtFwdypzjbxxBO tPtFwdypzjbxxBO = dataServiceService.getTPtFwdypzjbxxBO(config.getServiceid());
				if(!"0".equals(config.getServicestate())){ //服务已经停用
					removeConfig(tPtFwdypzjbxxBO);//物理删除并停止任务
				}else{//启用
					if(cron.jobService.getJobById(config.getServiceid()).getTaskid() == null)//如果任务已经被删除则重新增加
						addConfig(tPtFwdypzjbxxBO, "1");
					else
						cron.updateJob(config.getServiceid(), cron.formatCron(tPtFwdypzjbxxBO.getFrequency()));
				}
			} catch (OptimusException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		}
	}*/
	
	
	//初始化时加载当前服务推送配置
	/*@Override
	public void setApplicationContext(ApplicationContext app)
			throws BeansException {
		//只需初始化一次，配置问题文件
		if(!app.getDisplayName().contains("springmvc-servlet"))
			return ;
		//初始化推送配置信息(调度信息)
		log.debug("Start Init FwdyServiceManager configs ....");
		IPersistenceDAO dao = dataServiceService.getPersistenceDAO();
		try {
			List<TPtFwdypzjbxxBO> lists = dao.queryForList(TPtFwdypzjbxxBO.class, "select * from T_PT_FWDYPZJBXX where 1=1", null);
			if(lists != null){
				for(TPtFwdypzjbxxBO bo : lists){
					configs.put(bo.getServiceid() , bo);
				}
			}
			log.info("End init FwdyServiceManager config, TPtFwdypzjbxxBO.size()=" + (configs == null ? 0 : configs.size()));
			log.debug(configs);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}*/
	
	
	//测试
	public static void main(String[] args) throws InterruptedException {
        String[] locations = {"applicationContext.xml","springmvc-servlet.xml"};
        ApplicationContext ctx = 
		    new ClassPathXmlApplicationContext(locations);
        FwdyServiceManager o = (FwdyServiceManager) ctx.getBean("fwdyServiceManager");
        MsgSenderService msg = (MsgSenderService) ctx.getBean("msgSenderService");
        System.out.println(o.dataServiceService);
        try {
        	//ThreadLocalManager.add("optimus_request", new OptimusRequest(new DummyRequest()));
        	ThreadLocalManager.add("optimus_response", new OptimusResponse());
			System.out.println(o.dataServiceService.selectSubscriptionList(null));
			System.out.println(o.dataServiceService.getPersistenceDAO().queryForList(TPtFwdypzjbxxBO.class, "select * from T_PT_FWDYPZJBXX where 1=1", null));
			System.out.println(o.cron);
			System.out.println("-----------------------------------------");
			TPtFwdypzjbxxBO config = new TPtFwdypzjbxxBO();
			config.setFrequency("0,20,40 * * * * ?");
			config.setServiceid("999c3e6d-0a12-45a4-a61f-e2ab13c4749f");
			config.setPath("ftp://uftp:123456@localhost:21/pub/{yyyy}{mm}{dd}/{time}{rand:8}");
//			o.addConfig(config);
			
			Thread.sleep(61000);//
			System.out.println("Remove this config");
			o.removeConfig(config);
			config.setFrequency("20 * * * * ?");
//			o.addConfig(config,);
			
//			try {
//				o.cron.addJob(config.getFrequency(), new Date(), "", config.getServiceid(), 
//						new PushMsgWithFtpJob(), "cn.gwssi.datachange.msg_push.bus.PushMsgJob",
//						config.getServiceid());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
		} catch (OptimusException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		
	}

	
}
