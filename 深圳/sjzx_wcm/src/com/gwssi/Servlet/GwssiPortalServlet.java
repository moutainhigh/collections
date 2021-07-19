package com.gwssi.Servlet;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.gwssi.AppConstants;
import com.gwssi.Contorller.BaseContorller;
import com.gwssi.Contorller.HomeController;
import com.gwssi.Contorller.QueryWcmContorller;
import com.gwssi.Contorller.TodoContorller;
import com.gwssi.Contorller.TodoGDZCContorller;
import com.gwssi.Contorller.WcmController;
import com.gwssi.util.ErrorUtil;
import com.trs.dev4.jdk16.servlet24.ResponseUtil;

/**
 * Î¨Ò»Èë¿Ú¡£
 * 
 * @author chaihaowei
 */
public class GwssiPortalServlet extends HttpServlet {
	
	private static Map<String,BaseContorller> controllers = new ConcurrentHashMap<String,BaseContorller>() ;
	
	static {
		controllers.put("home", new HomeController());
		controllers.put("todo", new TodoContorller());
		controllers.put("wcm", new WcmController());
		controllers.put("queryWCm", new QueryWcmContorller());
		//controllers.put("trafficStatistics", new PortalTrafficStatisticsController());
		controllers.put("queryPma", new TodoGDZCContorller());
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String controllerName = request.getParameter(AppConstants.REQUEST_PARAM_NAME_CONTROLLER);
		if(StringUtils.isBlank(controllerName)){
			String error = ErrorUtil.getErrorResponse("Parameter controller is required.");
			ResponseUtil.response(response, error);
			return ;
		}
		
		BaseContorller handler = controllers.get(controllerName);
		if(handler==null){
			String error = ErrorUtil.getErrorResponse("Controller \""+controllerName+"\" is missing.");
			ResponseUtil.response(response, error);
			return ;
		}
		try {
			handler.handle(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			String error = ErrorUtil.getErrorResponse("Error occurred when processing request : "+e.getMessage());
			ResponseUtil.response(response, error);
			return ;
		}
	}
	
	private static final long serialVersionUID = -1081832111312514932L;

	public GwssiPortalServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
	}

}
