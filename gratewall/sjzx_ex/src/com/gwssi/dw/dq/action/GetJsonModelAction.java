package com.gwssi.dw.dq.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gwssi.dw.dq.DqContants;
import com.gwssi.dw.dq.reportModel.DqReport;

/**
 * 为了从session中读取JSON对象字符串,继承Action
 * @version 1.0
 * @author FlyFish
 */
public class GetJsonModelAction extends Action
{

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String id = "";
		id = request.getParameter("id");
		//String jsonStr = (String)request.getSession().getAttribute(DqContants.SESSION_DQREPORT_KEY);
		//System.out.println(jsonStr);
		
		DqReport dqReport = (DqReport)request.getSession().getAttribute(DqContants.SESSION_DQREPORT_KEY);
		request.getSession().removeAttribute(id); //清理缓存
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		//writer.write(new String(jsonStr.getBytes("UTF-8"), "UTF-8"));
		writer.write(dqReport.getJsons());
		writer.flush();
		writer.close();
		return null;
	}

}
