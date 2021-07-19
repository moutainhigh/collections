package com.gwssi.dw.dq.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gwssi.dw.dq.DqContants;
import com.gwssi.dw.dq.business.JsonListPack;

public class JsonListAction extends Action
{

	/** 
	 * getJsonList.action?method=(getSubjectList, getTableList, getColumnList, queryColumnList, getEntityList, getTargetList, getGroupList, getPrevParamsList)&id=
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String method = request.getParameter("method");
		String[] args = new String[3];
		args[0] = request.getParameter("id");
		args[1] = request.getParameter("tableName");
		args[2] = request.getParameter("subjectId");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		JsonListPack jlp = new JsonListPack();
		if (method == null)
			writer.write("no method");
		else if (method.equals("getPrevParamsList")) {
			String temp = jlp.paramXML(this.servlet.getServletContext().getResourceAsStream(DqContants.PARAM_XML_PATH));
			writer.write(temp);
		}
		else if (method.equals("getTable")){
			String temp = jlp.get("getTable", args);
			writer.write(temp);
		}
		else {
			String temp = jlp.get(method, args);
//			System.out.println("temp11111111111111111111111:"+temp);
			writer.write(temp);
		}
		writer.flush();
		writer.close();
		return null;
	}

}
