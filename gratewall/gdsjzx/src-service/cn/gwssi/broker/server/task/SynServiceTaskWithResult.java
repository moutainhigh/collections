package cn.gwssi.broker.server.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import cn.gwssi.broker.server.Cursor;
import cn.gwssi.broker.server.ServiceFactory;
import cn.gwssi.broker.server.ServiceProvider;
import cn.gwssi.broker.server.kafka.producer.ProducThreadPool;
import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.model.DataCarrier;
import cn.gwssi.common.model.RequestContext;
import cn.gwssi.common.model.TPtFwrzxxxxBO;
import cn.gwssi.common.resource.ServiceConstants;

/**
 * 同步结果线程处理
 * @author xue
 * @version 1.0
 * @since 2016/5/15
 */
public class SynServiceTaskWithResult implements Callable<Object> {
	private static  Logger log=Logger.getLogger(SynServiceTaskWithResult.class);
	private static Logger logK = Logger.getLogger("kafka");
	
	private RequestContext requestParam;
	public SynServiceTaskWithResult(RequestContext requestParam){
		this.requestParam=requestParam;
	}
	
	@Override
	public Object call() throws BrokerException{
		Cursor cursor=null;
		DataCarrier dataCarrier= new DataCarrier();
		dataCarrier.setObjectName(requestParam.getObjectName());
		dataCarrier.setServiceId(requestParam.getServiceId());
		dataCarrier.setServiceName(requestParam.getServiceName());
		dataCarrier.setStartNumber(0+"");
		dataCarrier.setFlag("true");//结束
		log.info("SynServiceTaskWithResult参数："+requestParam.toString());
		
		TPtFwrzxxxxBO t1 = new TPtFwrzxxxxBO();
		t1.setFwrzjbid(requestParam.getServiceId());
		Date startTime = new Date();
		t1.setStartTime(ServiceConstants.sdf.format(startTime));
		t1.setObj("服务端数据处理");
		t1.setFwrzxxid(UUID.randomUUID().toString());
		t1.setDetail(requestParam.toString());
		try {
			List result = new ArrayList();
			ServiceProvider s = ServiceFactory.getServiceInstance(requestParam.getExcuteServicePath());
			log.info("ServiceProvider:"+s);
			cursor= s.execute(requestParam.getParams());
			log.info("cursor:"+cursor);
			dataCarrier.setCode("0");
			if(cursor!=null){
				int size = cursor.size();
				if(size>0){
					for(int i=0;i<size;i++){
						dataCarrier= new DataCarrier();
						String res = cursor.next();
						result.add(res);
						dataCarrier.setResponseResults(result);
						dataCarrier.setDesc("获取数据成功！");
						if(i==0){
							t1.setExecutecontent(res);
						}
					}
				}else{
					dataCarrier.setDesc("获取数据成功,但没有找到数据！");
					t1.setExecutecontent("获取数据成功,但没有找到数据！");
				}
			}else{
				dataCarrier.setDesc("获取数据成功,但没有找到数据！");
				t1.setExecutecontent("获取数据成功,但没有找到数据！");
			}
			t1.setCode("0");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			t1.setExecutecontent("数据处理失败");
			t1.setCode("1");
			dataCarrier.setCode("1");
			dataCarrier.setDesc("获取数据失败！");
			dataCarrier.setErrorCode("001");
			dataCarrier.setErrorMsg("【处理Cursor出错】"+e.getMessage());
			throw new BrokerException("001","处理Cursor出错",e.getMessage());
		}finally{
			Date endTime =new Date(); 
			long time = endTime.getTime()-startTime.getTime();
			t1.setEndTime(ServiceConstants.sdf.format(endTime));
			t1.setTime(String.valueOf(time));
			logK.info(t1);
			if(cursor!=null){
				cursor.colse();
			}
			log.info(dataCarrier+"kafka开始:"+requestParam.getServiceId());
			ProducThreadPool.createSingleProducTopic(requestParam.getServiceId(),ServiceConstants.SYN_DATA_HAND_TOPIC,dataCarrier);
			log.info("kafka结束:"+ServiceConstants.SYN_DATA_HAND_TOPIC);
		}
	}

}
