package cn.gwssi.broker.server.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
 * 异步线程处理
 * @author xue
 * @version 1.0
 * @since 2016/5/15
 */
public class AsynServiceTaskWithResult implements Runnable{
	private static Logger logK = Logger.getLogger("kafka");
	private RequestContext requestParam;
	
	public AsynServiceTaskWithResult(RequestContext requestContext){
		this.requestParam=requestContext;
	}
	
	@Override
	public void run() {
		Cursor cursor=null;
		try {
			TPtFwrzxxxxBO t = new TPtFwrzxxxxBO();
			String uuid=UUID.randomUUID().toString();
			t.setFwrzxxid(uuid);
			t.setDetail(requestParam.getParams());
			t.setFwrzjbid(requestParam.getServiceId());
			t.setExecutecontent("具体服务类"+requestParam.getExcuteServicePath());
			t.setTime(ServiceConstants.sdf.format(new Date()));
			logK.info(t);
			ServiceProvider s = ServiceFactory.getServiceInstance(requestParam.getExcuteServicePath());
			cursor= s.execute(requestParam.getRequestParam().toString());
			
			if(cursor!=null){
				int size = cursor.size();
				if(size>0){
					long totalPage = size/ServiceConstants.PACKET_NUMBER; //共N页  
			        if (size % ServiceConstants.PACKET_NUMBER != 0) {    
			            totalPage += 1;    
			        } 
			        DataCarrier dataCarrier = null;
			        int sum = 0;
					List list = new ArrayList();
					for(int i=0;i<size;i++){
						dataCarrier = new DataCarrier();
						dataCarrier.setEndNumber(size+"");
						String res = cursor.next();
						list.add(res);
						if(list.size()>=ServiceConstants.PACKET_NUMBER){
							sum++;
							
							dataCarrier.setAsyClassPath(requestParam.getAsyClassPath());
							dataCarrier.setObjectName(requestParam.getObjectName());
							dataCarrier.setServiceName(requestParam.getServiceName());
							dataCarrier.setServiceId(requestParam.getServiceId());
							
							dataCarrier.setResponseResults(list);
							dataCarrier.setStartNumber(sum+"");
							if(i==size-1){
								dataCarrier.setFlag("true");//结束
								dataCarrier.setCode("0");
								dataCarrier.setDesc("获取数据成功！");
							}
							ProducThreadPool.createSingleProducTopic(requestParam.getServiceId(),requestParam.getObjectName(),dataCarrier);
							list.clear();
						}else{
							if(totalPage==sum+1){
								if(i==size-1){
									sum++;
									dataCarrier.setAsyClassPath(requestParam.getAsyClassPath());
									dataCarrier.setObjectName(requestParam.getObjectName());
									dataCarrier.setServiceName(requestParam.getServiceName());
									dataCarrier.setServiceId(requestParam.getServiceId());
									
									dataCarrier.setStartNumber(sum+"");
									dataCarrier.setResponseResults(list);
									dataCarrier.setFlag("true");//结束
									dataCarrier.setCode("0");
									dataCarrier.setDesc("获取数据成功！");
									ProducThreadPool.createSingleProducTopic(requestParam.getServiceId(),requestParam.getObjectName(),dataCarrier);
									list.clear();
								}
							}
						}
					}
				}else{
					DataCarrier dataCarrier= new DataCarrier();
					dataCarrier.setAsyClassPath(requestParam.getAsyClassPath());
					dataCarrier.setObjectName(requestParam.getObjectName());
					dataCarrier.setServiceName(requestParam.getServiceName());
					dataCarrier.setServiceId(requestParam.getServiceId());
					dataCarrier.setCode("0");
					dataCarrier.setDesc("获取数据成功,但没有找到数据！");
					dataCarrier.setStartNumber("0");
					dataCarrier.setFlag("true");//结束
					ProducThreadPool.createSingleProducTopic(requestParam.getServiceId(),requestParam.getObjectName(),dataCarrier);
				}
			}else{
				DataCarrier dataCarrier= new DataCarrier();
				dataCarrier.setAsyClassPath(requestParam.getAsyClassPath());
				dataCarrier.setObjectName(requestParam.getObjectName());
				dataCarrier.setServiceName(requestParam.getServiceName());
				dataCarrier.setServiceId(requestParam.getServiceId());
				dataCarrier.setCode("0");
				dataCarrier.setDesc("获取数据成功,但没有找到数据！");
				dataCarrier.setStartNumber("0");
				dataCarrier.setFlag("true");//结束
				ProducThreadPool.createSingleProducTopic(requestParam.getServiceId(),requestParam.getObjectName(),dataCarrier);
			}
		} catch (BrokerException e) {
			DataCarrier dataCarrier= new DataCarrier();
			dataCarrier.setAsyClassPath(requestParam.getAsyClassPath());
			dataCarrier.setObjectName(requestParam.getObjectName());
			dataCarrier.setServiceName(requestParam.getServiceName());
			dataCarrier.setServiceId(requestParam.getServiceId());
			dataCarrier.setCode("1");
			dataCarrier.setDesc("获取数据失败！");
			dataCarrier.setErrorCode("001");
			dataCarrier.setErrorMsg("【处理Cursor出错】"+e.getMessage());
			dataCarrier.setStartNumber("0");
			dataCarrier.setFlag("true");//结束
			ProducThreadPool.createSingleProducTopic(requestParam.getServiceId(),requestParam.getObjectName(),dataCarrier);
			e.printStackTrace();
		}finally{
			if(cursor!=null){
				try {
					cursor.colse();
				} catch (BrokerException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
/*if(ServiceConstants.PACKET_NUMBER >= size){
List result = new ArrayList();
dataCarrier.setNumber("1");
result.add(cursor.next());
for(int i=0;i<size;i++){
	dataCarrier.setResponseResults(result);
}
dataCarrier.setPacketMarking("true");
//写入kafka的那个topic
executor.execute(new ProducThread(requestParam.getServiceId(),requestParam.getObjectName(),dataCarrier));
}else{
List result = new ArrayList();
int count=0;
for(int i=0;i<size;i++){
	if(count>=99){//表示已经有100条数据了
		count = 0;
	}else{
		result.add(cursor.next());
	}
	count++;
}
}*/