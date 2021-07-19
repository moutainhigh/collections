package com.gwssi.dw.dq.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.gwssi.dw.dq.business.Definition;

public class DefinitionAction extends DispatchAction
{
	/**
	 * define.action?method=doTarget&action=(insert,check,query,update,delete)
	 */
	public ActionForward doTarget(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{
//		request.setCharacterEncoding("GBK");
		String action = request.getParameter("action");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		if (action == null) {
			writer.write("ȱ�ٲ���");
			writer.flush();
			writer.close();
			return null;
		}
		Definition de = new Definition();
		if (action.equals("insert")) { //����,����"true","false"
			String entityId = request.getParameter("entityId");
			String targetName = request.getParameter("name");
			String targetFormula = request.getParameter("formula");
			writer.write(de.addTarget(entityId, targetName, targetFormula));
		}
		else if (action.equals("check")) { //�������,����"true"���ߴ���ԭ��
			String tableName = request.getParameter("tableName");
			String condition = request.getParameter("condition");
			writer.write(de.checkTargetCondition(tableName, condition));
		}
		else if (action.equals("query")) { //��ѯ,����Json��ʽString,{targetName:'',targetFormula:''}
			writer.write(de.getTarget(request.getParameter("id")));
		}
		else if (action.equals("update")) { //����,����"true","false"
			String targetId = request.getParameter("targetId");
			String targetName = request.getParameter("name");
			String targetFormula = request.getParameter("formula");
			writer.write(de.updateTarget(targetId, targetName, targetFormula));
		}
		else if (action.equals("delete")) { //ɾ��,����"true","false"
			String[] id = request.getParameter("id").split(",");
			String flag = "true";
			for (int i=0;i < id.length;i++) {
				flag = de.delTarget(id[i]);
				if (flag.equals("false"))
					break;
			}
			writer.write(flag);
		}
		de.free();
		writer.flush();
		writer.close();
		return null;
	}

	public ActionForward doGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{
//		request.setCharacterEncoding("GBK");
		String action = request.getParameter("action");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		if (action == null) {
			writer.write("ȱ�ٲ���");
			writer.flush();
			writer.close();
			return null;
		}
		Definition de = new Definition();
		if (action.equals("insert")) { //����,����"true","false"
			writer.write(de.addGroup(request.getParameter("jsonStr")));
		}
		else if (action.equals("query")) { //��ѯ,����Json��ʽString
			writer.write(de.getGroup(request.getParameter("id")));
		}
		else if (action.equals("update")) { //����,����"true","false"
			writer.write(de.updateGroup(request.getParameter("jsonStr")));
		}
		else if (action.equals("delete")) { //ɾ��,����"true","false"
			String[] id = request.getParameter("id").split(",");
			String flag = "true";
			for (int i=0;i < id.length;i++) {
				flag = de.delGroup(id[i]);
				if (flag.equals("false"))
					break;
			}
			writer.write(flag);
		}
		de.free();
		writer.flush();
		writer.close();
		return null;
	}
	public ActionForward doEntity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{
//		request.setCharacterEncoding("GBK");
		String action = request.getParameter("action");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		
		if (action == null) {
			writer.write("ȱ�ٲ���");
			writer.flush();
			writer.close();
			return null;
		}
		Definition de = new Definition();
		if (action.equals("insert")) { //����,����"true","false"
			writer.write(de.insertEntity(request.getParameter("jsonStr")));
		}
		else if (action.equals("query")) { //��ѯ,����Json��ʽString
			writer.write(de.getEntity(request.getParameter("id")));
		}
		else if (action.equals("update")) { //����,����"true","false"
			writer.write(de.updateEntity(request.getParameter("jsonStr")));
		}
		else if (action.equals("detailsList")){ //����ʵ��ID���ʵ��ϸ�ڱ��е�ϸ�����Ƹ�ʵ��ϸ���ֶ�
			writer.write(de.getDetailsList(request.getParameter("id")));
		}
		else if (action.equals("addDetail")) { //����ʵ��ϸ��,����"true","false"
			writer.write(de.addDetail(request.getParameter("jsonStr")));
		}
		else if (action.equals("updateDetail")) { //����ʵ��ϸ��,����"true","false"
			writer.write(de.updateDetail(request.getParameter("jsonStr")));
		}
		else if (action.equals("getDetails")) {
			writer.write(de.getDetails(request.getParameter("id")));
		}
		else if (action.equals("delDetails")) {
			String[] id = request.getParameter("id").split(",");
			String flag = "true";
			for (int i=0;i < id.length;i++) {
				flag = de.delDetails(id[i]);
				if (flag.equals("false"))
					break;
			}
			writer.write(flag);
		}
		else if (action.equals("delete")) { //ɾ��,����"true","false"
			String[] id = request.getParameter("id").split(",");
			String flag = "true";
			for (int i=0;i < id.length;i++) {
				flag = de.delEntity(id[i]);
				if (flag.equals("false"))
					break;
			}
			writer.write(flag);
		}
		de.free();
		writer.flush();
		writer.close();
		return null;
		
	
	}
}
