package com.gwssi.dw.dq.action;

import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.gwssi.dw.dq.DqContants;
import com.gwssi.dw.dq.dao.ModelDao;

/**
 * 模板相关Action,继承DispatchAction
 * @version 1.0
 * @author FlyFish
 */
public class ModelAction extends DispatchAction
{
	/**
	 * 添加模板
	 * @param name 模板名称
	 * @param subjectId 主题ID
	 * @param model 模板内容,大字段
	 * @return
	 * @throws Exception
	 */
	public ActionForward addModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{
//		request.setCharacterEncoding("UTF-8");
		String subjectId = request.getParameter("subjectId");
		if (subjectId == null) 
			subjectId = "0100";
		String modelName = request.getParameter("name");
		String model = request.getParameter("model");
		String modelMemo = request.getParameter("memo");
//		System.out.println("model:\t" + model);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter(); 
		try {
			new ModelDao().addModel(subjectId, modelName, modelMemo, model, DqContants.CON_TYPE);
			writer.write("true");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("false");
		}
		writer.flush();
		writer.close();
		return null;
	}
	
	/**
	 * 更新模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{
		String id = request.getParameter("id");
		String modelName = request.getParameter("name");
		String model = request.getParameter("model");
		String modelMemo = request.getParameter("memo");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter(); 
		try {
			new ModelDao().updateModel(id, modelName, modelMemo, model, DqContants.CON_TYPE);
			writer.write("true");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("false");
		}
		writer.flush();
		writer.close();
		return null;
	}

	/**
	 * 读取模板
	 * @param id 模板ID
	 * @return
	 * @throws Exception
	 */
	public ActionForward getModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		response.setContentType("text/html;charset=GBK");
		OutputStream out = response.getOutputStream();
		new ModelDao().getModel(id, DqContants.CON_TYPE, out);
//		PrintWriter writer = response.getWriter();
//		if (id != null) {
//			writer.write(new ModelDao().getModel(id, DqContants.CON_TYPE));
//		}
//		else
//			writer.write("error");
//		writer.flush();
//		writer.close();
		return null;
	}
	
	/**
	 * 读取模板ID与Name列表,暂供测试用
	 * @return
	 * @throws Exception
	 */
	public ActionForward getModelList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			writer.write(new ModelDao().getModelList(DqContants.CON_TYPE));
		} catch (Exception e) {
			writer.write("error");
			e.printStackTrace();
		}
		writer.flush();
		writer.close();
		return null;
	}
	
	public ActionForward towardQuery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward("success");
	}
	
}
