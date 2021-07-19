package com.gwssi.dw.dq.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import cn.gwssi.common.context.DataBus;
import cn.gwssi.common.context.TxnContext;

import com.gwssi.dw.dq.DqContants;
import com.gwssi.dw.dq.dao.LinkDao;

public class LinkAction extends DispatchAction
{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String method = request.getParameter("method");
		String type = request.getParameter("type");
		String link = request.getParameter("link");
		if (link != null && link.equals("first")) {
			TxnContext context = (TxnContext) request.getAttribute("freeze-databus");
			String userID = context.getRecord("oper-data").getValue("userID");
			request.setAttribute("userID", userID);
			request.setAttribute("cognosHost", DqContants.COGNOS_HOST);
//			System.out.println("method:" + method + "type:" + type + "link:" + link);
			return mapping.findForward("success");
		}
		LinkDao dao = new LinkDao(DqContants.CON_TYPE);
		String userID = request.getParameter("userID");
//		System.out.println("secondTime-----------");
//		response.sendRedirect(dao.getURL(method, userID, type).replaceAll("localhost", request.getServerName()));
		String url = dao.getURL(method, userID, type);
		System.out.println("url1:\t" + url);
		url = url.replaceAll("localhost", DqContants.COGNOS_HOST);
		System.out.println("url2:\t" + url);
		response.sendRedirect(url);
		return null;
	}

}
