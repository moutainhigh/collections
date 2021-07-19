/**
 * 
 */
package com.gwssi.dw.dq.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.gwssi.dw.dq.DqContants;
import com.gwssi.dw.dq.business.JsonForReport;
import com.gwssi.dw.dq.business.QueryService;
import com.gwssi.dw.dq.dao.DqQueryDao;
import com.gwssi.dw.dq.reportModel.DqReport;

/**
 * <p>Title: 灵活查询数据提供类</p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) Sep 17, 2008</p>
 * <p>Company: 长城软件</p>
 * @author zhouyi
 * @version 1.0
 */
public class DqQueryAction extends DispatchAction{

	public ActionForward queryValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// TODO Auto-generated method stub
		String sql = request.getParameter("sql");
		//sql = URLDecoder.decode(sql,"UTF-8");
//		System.out.println("查询语句："+sql);
		DqQueryDao dqQueryDao = new DqQueryDao();
		String value = "";
		
		try {
			value = dqQueryDao.queryValue(sql, DqContants.CON_TYPE);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			
		}
		response.getOutputStream().write(value.getBytes());
		return null;
	}
	/**
	 * 查询主栏一块中对应的一列数据
	 * json 数据（参数 sqls 格式：[{sql:sql1,values:[v11,v12...]},{{sql:sql2,values:[v21,v22...]}}]） 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String jsonSqls = request.getParameter("sqls");
		JSONArray jsa = JSONArray.fromObject(jsonSqls);
		DqQueryDao dqQueryDao = new DqQueryDao();
		System.out.println("jsonSqls:"+jsonSqls);//参数
		int size = jsa.size();
		String[] results = new String[size]; 
		Object[][] sqlsValues = new Object[size][];
		Object[] values;
		String[] sqls = new String[size];
		JSONArray jsaValues;
		Map sqlMap;
		for(int i=0;i<size;i++){
			sqlMap = (Map)jsa.get(i);
			sqls[i]= sqlMap.get("sql").toString();
			jsaValues = (JSONArray)sqlMap.get("values");
			values = new Object[jsaValues.size()];
			for(int j=0;j<jsaValues.size();j++){
				values[j] = jsaValues.get(j).toString();
			}
			sqlsValues[i] = values;
			sqlMap = null;
			jsaValues = null;
			values = null;
		}
		results = dqQueryDao.queryValues(sqls, sqlsValues, DqContants.CON_TYPE);
		StringBuffer outDatas = new StringBuffer();
		for(int i=0;i<size;i++){
			outDatas.append(",");
			outDatas.append(results[i]);
		}
		
		response.getOutputStream().write(outDatas.substring(1).getBytes());
		return null;
	} 
	//{sql:sql1,values:[v11,v12...]}
	/**
	 * 生成明细查询页面
	 */
	public ActionForward queryDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String jsonSql = request.getParameter("sql");
		String serverPath = request.getContextPath();
		QueryService queryService = new QueryService();
		try {
			writeOut(response,
					queryService.getPagerHtml(jsonSql, serverPath, DqContants.CON_TYPE));
		} catch (RuntimeException e) {
			writeOut(response,"该单元尚未定义详细数据项！"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public ActionForward getEntityDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String entityTable = request.getParameter("entityTable");
		QueryService queryService = new QueryService();
		try {
			List detailsList = queryService.getEntityDetails(entityTable,DqContants.CON_TYPE);
			writeOut(response,getJsonRecords(detailsList));
		} catch (RuntimeException e) {
			writeOut(response,"该单元尚未定义详细数据项！ </br>"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	private String getJsonRecords(List list){
		Map dataMap = new HashMap();
		dataMap.put("records", list);
		dataMap.put("totalCount", list.size() + "");
		return JSONObject.fromObject(dataMap).toString();
	}
	
	/**
	 * 返回明细查询分页数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward pagerDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String pagerJson = "{totalCount:100,records:[]}";
		String pageSize = request.getParameter("pager:pageSize");
		String pageIndex = request.getParameter("pager:pageIndex");
		String queryType = request.getParameter("pager:queryType");
		
		String jsonSql = request.getParameter("json:sql");
		
		Pager pager = new Pager(pageIndex, pageSize,queryType);
		
		QueryService queryService = new QueryService();
		Map filterMap = getRequestProperties(request);
		pagerJson = queryService.pagerDetails(jsonSql, pager, filterMap,DqContants.CON_TYPE);
		
		writeOut(response,pagerJson);
		return null;
	}

	/**
	 * 为了解析页面传过来的JSON字符串
	 * @param jsonStr 用以解析的JSON字符串
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		request.setCharacterEncoding("UTF-8");
		String jsonStr;
		jsonStr = request.getParameter("jsonStr");
		JsonForReport jsonFR = new JsonForReport(jsonStr, this.servlet.getServletContext().getResourceAsStream(DqContants.PARAM_XML_PATH));
		DqReport dqReport = jsonFR.getDqReport();
		String id = System.currentTimeMillis() + ""; //产生唯一ID
		request.getSession().removeAttribute(DqContants.SESSION_DQREPORT_KEY);//查询前先删除
		request.getSession().setAttribute(DqContants.SESSION_DQREPORT_KEY, dqReport); //存在session里，供其他方法调用
		
		PrintWriter writer = response.getWriter();
		writer.write(id);
		writer.flush();
		writer.close();
		return null;
	}
	
	/**
	 * 导出EXCEL
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//DqReport dqReport = (DqReport)request.getSession().getAttribute(DqContants.SESSION_DQREPORT_KEY);
		String jsonValue = request.getParameter("jsonValue");
		response.setContentType("application/vnd.ms-excel;charset=GBK");
		try {
			response.getOutputStream().write("<meta http-equiv=\"content-type\" content=\"text/html; charset=GBK\"/>".getBytes());
			response.getOutputStream().write(jsonValue.getBytes());
		} catch (RuntimeException e) {
			//
		}finally{
			response.getOutputStream().close();
		}
		return null;
	}
	
	protected void writeOut(HttpServletResponse response,String outStr){
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			response.setContentType("text/html; charset=UTF-8");
			out.write(outStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}
	
	protected Map getRequestProperties(HttpServletRequest request){
		Map parameterMap = request.getParameterMap();
		Map propertiesMap = new HashMap();
		String paramName,paramValue;
		Iterator pIter = parameterMap.keySet().iterator();
		while(pIter.hasNext()){
			paramName = (String)pIter.next();
			if(paramName.indexOf("property:")!=-1){
				paramValue = gerParameter(request,paramName);
				if(paramValue!=null&&!paramValue.equals("")){
					propertiesMap.put(paramName.substring("property:".length()), paramValue);
				}
			}
			paramName = null;
			paramValue = null;
		}
		return propertiesMap;
	}
	
	/**
	 * 获得转码之后的参数
	 * @param request
	 * @param paramName
	 * @return
	 */
	protected String gerParameter(HttpServletRequest request,String paramName){
		String paramValue = request.getParameter(paramName);
		
		return paramValue;
	}
}
