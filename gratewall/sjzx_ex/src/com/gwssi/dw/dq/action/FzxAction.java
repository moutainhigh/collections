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
import com.gwssi.dw.dq.dao.FzxDao;

/**
 * @author zhengziguo
 *
 */
public class FzxAction extends DispatchAction
{
	public ActionForward getFzxTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String fzTree = "";
		try {
			fzTree = (String) request.getParameter("fzTree");
		} catch (RuntimeException e) {
			fzTree = "";
		}
		Document doc = null;
		try {
			doc = new FzxDao(DqContants.CON_TYPE).getGroupTree(fzTree);

		} catch (DBException e) {
			e.printStackTrace();
			return mapping.findForward("fail");
		}
		request.setAttribute("xmlString", removeHead(doc.asXML()));
		return mapping.findForward("success");
	}

	public ActionForward getFzModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String DlId = "";
		try {
			DlId = (String) request.getParameter("DlId");
		} catch (RuntimeException e) {
			DlId = "";
		}
		response.setContentType("text/html;charset=GBK");
		PrintWriter writer = response.getWriter();
		writer.write(new FzxDao(DqContants.CON_TYPE).getFzModel(DlId));
		writer.flush();
		writer.close();
		return null;
	}
	
	public ActionForward getFzTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String tempId = "";
		try {
			tempId = (String) request.getParameter("tempId");
		} catch (RuntimeException e) {
			tempId = "";
		}
		response.setContentType("text/html;charset=GBK");
		PrintWriter writer = response.getWriter();
		writer.write(new FzxDao(DqContants.CON_TYPE).getFzModelTree(tempId));
		writer.flush();
		writer.close();
		return null;
	}
	
	public ActionForward getRootTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String groupId = request.getParameter("groupId");
		FzxDao dao = new FzxDao(DqContants.CON_TYPE);
		Document doc = null;
		doc = dao.getGroupTree(groupId);
//		request.setAttribute("xmlString", removeHead(doc.asXML()));
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(removeHead(doc.asXML()));
		writer.flush();
		writer.close();
		return null;
	}
	
	public ActionForward getChildTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String groupId = request.getParameter("groupId");
		String pId = request.getParameter("pId");
		FzxDao dao = new FzxDao(DqContants.CON_TYPE);
		String back = dao.getChildTree(groupId, pId);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.write(back);
		writer.flush();
		writer.close();
		return null;
	}
   
	private String removeHead(String xml)
	{
		int i = xml.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		return xml.substring(i + 39);
	}
}
