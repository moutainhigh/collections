package cn.gwssi.datachange.msg_push.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.gwssi.common.model.TPtFwrzjbxxBO;
import cn.gwssi.datachange.datashare.service.ObtainAappStateServiceImpl;

/**
 * job获取服务层帮助类,通过spring传入服务对象
 * 
 * @author wuyincheng ,Jul 15, 2016
 */
@Component
public class PushServiceHelper {
	
    private static Logger log = Logger.getLogger("kafka");
	
	//--订阅服务配置基本信息 & 	-服务内容信息
	public static MsgSenderService senderService;
	
	public static ObtainAappStateServiceImpl obtainAappStateServiceImpl;
	
	public static void log(Exception e){
		TPtFwrzjbxxBO log = new TPtFwrzjbxxBO();
//		log.setContent(content);
	}
	
	@Autowired(required = true)
	public void setMsgSenderService(MsgSenderService senderService) {
		PushServiceHelper.senderService = senderService;
	}

	@Autowired(required = true)
	public void setObtainAappStateServiceImpl(
			ObtainAappStateServiceImpl obtainAappStateServiceImpl) {
		PushServiceHelper.obtainAappStateServiceImpl = obtainAappStateServiceImpl;
	}

	//需要改成异步(kafka @TODO)
	public static void log(TPtFwrzjbxxBO logEntity) {
		log.info(logEntity);
		//System.out.println(logEntity);
	}
	
}
