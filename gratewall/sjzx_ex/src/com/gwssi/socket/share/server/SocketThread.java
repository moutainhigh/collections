package com.gwssi.socket.share.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletContext;

public class SocketThread extends Thread
{
	private ServletContext	servletContext;

	private ServerSocket	serverSocket;

	public SocketThread(ServerSocket serverSocket, ServletContext servletContext)
	{
		this.servletContext = servletContext;
		// ��web.xml��context-param�ڵ��ȡsocket�˿�
		String port = this.servletContext.getInitParameter("socketPort");
		if (serverSocket == null) {
			try {
				this.serverSocket = new ServerSocket(Integer.parseInt(port));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void run()
	{
		
		 
		while (!this.isInterrupted()) {
			try {
				Socket socket = serverSocket.accept();// �߳�δ�ж�ִ��ѭ��
				if (socket != null)
					new ProcessSocketData(socket, this.servletContext).start();
			} catch (IOException e) {
				e.printStackTrace();
			} 
//			finally {
//				try {
//					closeServerSocket();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
		}
	}

	public void closeServerSocket()
	{
		try {
			if (serverSocket != null && !serverSocket.isClosed())
				serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
