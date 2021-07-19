package com.gwssi.common.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.gwssi.dw.component.quartz.QuartzInitService;


public class QuartzInitializerServlet extends HttpServlet {
	private static Log log = LogFactory.getLog(QuartzInitializerServlet.class);

	private boolean performShutdown = true;


	/*
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 
	 * Interface.
	 * 
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	public void init(ServletConfig cfg) throws javax.servlet.ServletException {
		super.init(cfg);
		log.info("Quartz Initializer Servlet loaded, initializing Scheduler...");

		try {
			String localIP = InetAddress.getLocalHost().getHostAddress();
			log.info("serverIP = "+ localIP);
			
//			if(localIP==null||"".equals(localIP)){
//				return;
//			}

			String shutdownPref = cfg.getInitParameter("shutdown-on-unload");
			if (shutdownPref != null)
				performShutdown = Boolean.valueOf(shutdownPref).booleanValue();

			// Should the Scheduler being started now or later
			String startOnLoad = cfg
					.getInitParameter("start-scheduler-on-load");
			/*
			 * If the "start-scheduler-on-load" init-parameter is not specified,
			 * the scheduler will be started. This is to maintain backwards
			 * compatability.
			 */
			if (startOnLoad == null
					|| (Boolean.valueOf(startOnLoad).booleanValue())) {
				QuartzInitService.init();
				QuartzInitService.executeJob();
			} else {
				log.info("Scheduler has not been started");
			}
		} catch (Exception e) {
			log.info("Quartz Scheduler failed to initialize: " + e.toString());
			throw new ServletException(e);
		}
	}

	public void destroy() {
		log.debug("destroy");
		if (!performShutdown)
			return;
		try {
			QuartzInitService.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("Quartz Scheduler successful shutdown.");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
}
