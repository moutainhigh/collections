package cn.gwssi.datachange.datashare.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;

import com.gwssi.optimus.core.exception.OptimusException;

import cn.gwssi.datachange.msg_push.service.PushServiceHelper;
import cn.gwssi.quartz.inter.JobServer;

public class ObtainAappStateUtil implements JobServer{
	
	@Override
	public String job(String paramers) throws Exception {
		selectServiceObject();
		return null;
	}
	
	public void selectServiceObject(){
		List<Map> list = null;
		try {
			list = PushServiceHelper.obtainAappStateServiceImpl.selectServiceObject(null);
		} catch (OptimusException e) {
			e.printStackTrace();
		}
		if(list!=null && list.size()>0){
			for(Map m : list){
				if(m!=null && m.size()>0){
					String xml = "";
					try {
						xml=WebClient.create("http://"+m.get("serviceobjectip")+":"+m.get("serviceobjectport")+"/SOAService/testApp/testAppPost").post("",String.class);
					} catch (Exception e) {
						xml = "fail";
						e.printStackTrace();
					}
					if(!"success".equals(xml)){//失败将服务对象状态停掉
						String fwdxjbid = (String) m.get("fwdxjbid");
						if(StringUtils.isNotBlank(fwdxjbid)){
							try {
								PushServiceHelper.obtainAappStateServiceImpl.updateServiceObject(fwdxjbid);
							} catch (OptimusException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}
