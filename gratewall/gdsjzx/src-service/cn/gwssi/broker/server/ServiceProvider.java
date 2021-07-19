package cn.gwssi.broker.server;

import cn.gwssi.common.exception.BrokerException;

public abstract class ServiceProvider {
	public Cursor execute(String params)throws BrokerException {
		return new DefaultCursor(null,null,null);
	}
}
