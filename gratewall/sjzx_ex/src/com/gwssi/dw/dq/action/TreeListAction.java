package com.gwssi.dw.dq.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;

import com.genersoft.frame.base.database.DBException;
import com.gwssi.dw.dq.DqContants;
import com.gwssi.dw.dq.dao.TreeQueryDAO;

/**
 * 指标、分组树的读出
 * @version 1.0
 * @author FlyFish
 */
public class TreeListAction extends DispatchAction {
	
	public ActionForward getTargetTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse) throws Exception
	{
		Document doc = null;
		try {
			doc = new TreeQueryDAO(DqContants.CON_TYPE).getTree("target");
		} catch (DBException e) {
			e.printStackTrace();
			return mapping.findForward("fail");
		}
		request.setAttribute("xmlString", removeHead(doc.asXML()));
		return mapping.findForward("success");
	}
	
	public ActionForward getGroupTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse reponse) throws Exception
	{
		Document doc = null;
		try {
			doc = new TreeQueryDAO(DqContants.CON_TYPE).getTree("group");
		} catch (DBException e) {
			e.printStackTrace();
			return mapping.findForward("fail");
		}
		request.setAttribute("xmlString", removeHead(doc.asXML()));
		return mapping.findForward("success");
	}
	
	public ActionForward getChildTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{   
		String type = request.getParameter("type");
		String entityId = request.getParameter("entityId");
		String contextPath = request.getContextPath();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			writer.write(new TreeQueryDAO(DqContants.CON_TYPE).getChildTree(contextPath, type, entityId));
		} catch(Exception e) {
			e.printStackTrace();
			writer.write("");
		}
		writer.flush();
		writer.close();
		return null;
	}
	
	private String removeHead(String xml) {
		int i = xml.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		return xml.substring(i + 39);
	}

}
