package cn.gwssi.broker.server;

import cn.gwssi.common.exception.BrokerException;

public interface Cursor {
	String next() throws BrokerException;
	
	int size() throws BrokerException;
	
	void colse() throws BrokerException;
}
