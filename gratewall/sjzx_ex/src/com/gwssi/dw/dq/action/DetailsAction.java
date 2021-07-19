package com.gwssi.dw.dq.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.gwssi.dw.dq.DqContants;
import com.gwssi.dw.dq.dao.DetailsDao;

public class DetailsAction extends DispatchAction
{
	public ActionForward updateStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String str = request.getParameter("id");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		if (str == null) {
			writer.write("没有参数");
			writer.flush();
			writer.close();
			return null;
		}
		DetailsDao dao = new DetailsDao(DqContants.CON_TYPE);
		writer.write(dao.updateStatus(str.split(","))); //"true" or "false"
		writer.flush();
		writer.close();
		return null;
	}
	
	public ActionForward getDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		if (id == null) {
			writer.write("没有参数");
			writer.flush();
			writer.close();
			return null;
		}
		DetailsDao dao = new DetailsDao(DqContants.CON_TYPE);
		writer.write(dao.getDetail(id)); //"false" or JSON
		writer.flush();
		writer.close();
		return null;
	}
	
	public ActionForward updateDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Map map = new HashMap();
		request.setCharacterEncoding("UTF-8");
		map.put("details_id", get("property:DETAILS_ID",request));
		map.put("details_name", get("property:DETAILS_NAME",request));
		map.put("details_width", get("property:DETAILS_WIDTH",request));
		map.put("isShow", get("property:ISSHOW",request));
		map.put("isFilter", get("property:ISFILTER",request));
		map.put("details_num", get("property:DETAILS_NUM",request));
		map.put("details_href", get("property:DETAILS_HREF",request));
		map.put("details_params", get("property:DETAILS_PARAMS",request));
		map.put("details_paramsalias", get("property:DETAILS_PARAMSALIAS",request));
		
		String flagSort =  get("property:FLAG_SORT",request);
		flagSort = (flagSort==null||flagSort.equals(""))?"0":flagSort;
		map.put("flag_sort",flagSort);
//		System.out.println("property:DETAILS_ID " + get("property:DETAILS_ID",request));
//		System.out.println("property:DETAILS_NAME " + get("property:DETAILS_NAME",request));
//		System.out.println("property:DETAILS_WIDTH " + get("property:DETAILS_WIDTH",request));
//		System.out.println("property:ISSHOW " + get("property:ISSHOW",request));
//		System.out.println("property:ISFILTER " + get("property:ISFILTER",request));
//		System.out.println("property:DETAILS_NUM " + get("property:DETAILS_NUM",request));
//		System.out.println("property:DETAILS_HREF " + get("property:DETAILS_HREF",request));
//		System.out.println("property:DETAILS_PARAMS " + get("property:DETAILS_PARAMS",request));
//		System.out.println("property:DETAILS_PARAMSALIAS " + get("property:DETAILS_PARAMSALIAS",request));
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		DetailsDao dao = new DetailsDao(DqContants.CON_TYPE);
		writer.write(dao.updateDetail(map)); //"true" or "false"
		writer.flush();
		writer.close();
		return null;
	}
	
	private String get(String name, HttpServletRequest request) {
		String val = request.getParameter(name);
		if (val == null)
			return "";
		else
			return val;
	}
}
