package com.gwssi.dw.dq.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.gwssi.dw.dq.business.Templates;

public class TemplatesAction extends DispatchAction
{
	
	public ActionForward getTemplateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
//		request.setCharacterEncoding("GBK");
		String property = request.getParameter("property");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		if(property == null){
			writer.write("没有参数！");
			writer.flush();
			writer.close();
		}
		Templates tl = new Templates();
		if(property.equals("query")){
			String pager = request.getParameter("pager:pageSize");
			String pageIndex = request.getParameter("pager:pageIndex");
			String propertyName = request.getParameter("property:templateName");
//			String propertyName = "";
//			try{
//				propertyName = getQParamter(request,"property:templateName");
//				System.out.println("propertyName:"+propertyName);
//			}catch(Exception e){
//				propertyName = "";
//			}
			writer.write(tl.searchTemp(propertyName,pager,pageIndex));
		}
		writer.flush();
		writer.close();
		return null;
	}
	
//	private String getQParamter(HttpServletRequest request,String parameterName){
//		String value = null;
//		String queryStr = request.getQueryString();
//		String[] parameterKeyValues = queryStr.split("&");
//		String parameterKeyValue = "";
//		for(int i=0;i<parameterKeyValues.length;i++){
//			parameterKeyValue = parameterKeyValues[i];
//			if(parameterKeyValue.indexOf(parameterName+"=")==0){
//				value = parameterKeyValue.split("=")[1];
//				System.out.println("value1:"+value);
//				try {
//					value = URLDecoder.decode(value, "UTF-8");
//					System.out.println("value2:"+value);
//				} catch (UnsupportedEncodingException e) {
//					log.info("编码不支持！");
//				}
//				break;
//			}
//			parameterKeyValue = null;
//		}
//		return value;
//	}

	public ActionForward getTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
//		request.setCharacterEncoding("GBK");
		String action = request.getParameter("action");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = response.getWriter();
		if (action == null) {
			writer.write("没有参数！");
		    writer.flush();
			writer.close();
			return null;
		}
		Templates tl = new Templates();
//		if (action.equals("query")) { 
//			String templateId = request.getParameter("TEMPLATEID");
//			writer.write(tl.getTemplate());
//		}
//		else 
//		if(action.equals("search")){
//			String templateName = request.getParameter("TEMPLATENAME");
//			writer.write(tl.searchTemplate(templateName));
//		}
		if(action.equals("delete")){
			String[] id = request.getParameter("template").split(",");
			String flag = "true";
			for (int i=0;i < id.length;i++) {
				flag = tl.delTemplate(id[i]);
				if (flag.equals("false"))
					break;
			}
			writer.write(flag);
		}
		writer.flush();
		writer.close();
		return null;
	}
}
