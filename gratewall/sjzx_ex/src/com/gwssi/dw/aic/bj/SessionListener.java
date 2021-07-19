package com.gwssi.dw.aic.bj;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.genersoft.frame.base.database.DBException;

public class SessionListener implements HttpSessionListener
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{

	}

	public void sessionCreated(HttpSessionEvent event)
	{
	}

	public void sessionDestroyed(HttpSessionEvent event)
	{
		try {
			
			com.gwssi.dw.aic.bj.OnlineUserInfo.removeLoginUser(event.getSession().getId());
			//System.out.println("***Ïú»Ùsession***");
			event.getSession().invalidate();
		} catch (DBException e) {
			e.printStackTrace();
		}
	}
}