package cn.gwssi.broker.client;

import cn.gwssi.common.exception.BrokerException;
import cn.gwssi.common.model.ReponseContextBase;
import cn.gwssi.common.model.SynReponseContext;

/**
 * 通用的功能都放在接口类BrokerInterface，然后实现
 * @author xue
 * @version 1.0
 * @since 2016/4/20
 */
public interface BrokerInterface {//SynRequestContext requestContext//AsynRequestContext requestContext
	SynReponseContext synExecute(String serviceName,String params) throws BrokerException;
	SynReponseContext synExecute(String timeout,String serviceName,String params) throws BrokerException;
	ReponseContextBase asynExecute(String serviceName,String params,String asyClassPath) throws BrokerException;
}
