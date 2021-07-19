package com.gwssi.dw.dq.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.gwssi.dw.dq.DqContants;
import com.gwssi.dw.dq.dao.CheckDao;

public class CheckAction extends DispatchAction
{
	public ActionForward checkEntityCond(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String jsonStr = request.getParameter("jsonStr");
		CheckDao cd = new CheckDao(DqContants.CON_TYPE);
		String back = cd.checkEntityCond(jsonStr);
		PrintWriter writer = response.getWriter();
		writer.write(back);
		writer.flush();
		writer.close();
		return null;
	}
	
	public ActionForward checkGroupPrev(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String jsonStr = request.getParameter("jsonStr");
		CheckDao cd = new CheckDao(DqContants.CON_TYPE);
		String back = cd.checkGroupPrev(jsonStr); //返回true或者false
		PrintWriter writer = response.getWriter();
		writer.write(back);
		writer.flush();
		writer.close();
		return null;
	}
	
	public ActionForward checkModelName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		CheckDao cd = new CheckDao(DqContants.CON_TYPE);
		String back = cd.checkModelName(name, id); //返回true或者false,error
		PrintWriter writer = response.getWriter();
		writer.write(back);
		writer.flush();
		writer.close();
		return null;
	}
	
//	public ActionForward checkGroupCustom(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) throws Exception
//	{
//		String jsonStr = request.getParameter("jsonStr");
//		CheckDao cd = new CheckDao(DqContants.CON_TYPE);
//		String back = cd.checkGroupCustom(jsonStr); //返回true或者false
//		PrintWriter writer = response.getWriter();
//		writer.write(back);
//		writer.flush();
//		writer.close();
//		return null;
//	}

}
