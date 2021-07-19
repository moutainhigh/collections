package com.gwssi.socket.share.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerSocketListener implements ServletContextListener {
	private SocketThread socketThread;

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		// TODO Auto-generated method stub
		if (socketThread != null && socketThread.isInterrupted()) {
			socketThread.closeServerSocket();
			socketThread.interrupt();
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
		// TODO Auto-generated method stub
		ServletContext servletContext = e.getServletContext();
		if (socketThread == null) {
			socketThread = new SocketThread(null, servletContext);
			socketThread.start();// servlet�����ĳ�ʼ��ʱ����socket������߳�
		}
	}
}
