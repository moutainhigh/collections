/**
 * 
 */
package com.gwssi.dw.dq.business;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gwssi.dw.dq.action.Pager;
import com.gwssi.dw.dq.dao.DqQueryDao;

/**
 * <p>Title: </p>
 * <p>Description:  </p>
 * <p>Copyright: Copyright (c) Nov 11, 2008</p>
 * <p>Company: 长城软件</p>
 * @author zhouyi
 * @version 1.0
 */
public class QueryService
{
	public String getPagerHtml(String jsonSql,String serverPath,String account){
		DqQueryDao dqQueryDao = new DqQueryDao();
		JSONObject jso = JSONObject.fromObject(jsonSql);//jsonSql对象
		String statSql = jso.getString("sql");
		
		String entityName = getEntityTableFromSql(statSql);
		List detailsList = dqQueryDao.getEntityDetails(entityName, account);
		StringBuffer htmlBuf= new StringBuffer();
		htmlBuf.append(htmlHead(detailsList,jsonSql,serverPath,entityName));//加入html头
		htmlBuf.append("<body class=\"flora\"><div id=\"grid-dqQuery\"></div></body></html>");
		return htmlBuf.toString();
	}
	/**
	 * 明细数据分页查询
	 * @param jsonSql
	 * @param pager
	 * @param filterMap
	 * @param account
	 * @return
	 */
	public String pagerDetails(String jsonSql,Pager pager,Map filterMap,String account){
		DqQueryDao dqQueryDao = new DqQueryDao();
		JSONObject jso = JSONObject.fromObject(jsonSql);//jsonSql对象
		String statSql = jso.getString("sql");
		Object[] values =getJsonSqlValues(jso);
		String entityTable = getEntityTableFromSql(statSql);
		List detailsList = dqQueryDao.getEntityDetails(entityTable, account);
		
		return dqQueryDao.pagerDetails(pager, statSql, values,filterMap, detailsList,entityTable,getForeignEntityTablesFromSql(statSql),account).getRecordsJson();
	}
	/************************************************/
	
	private Object[] getJsonSqlValues(JSONObject jso){
		Object[] values;//查询参数数组
		JSONArray jsaValues;//json对象中的参数数组
		
		jsaValues = (JSONArray)jso.get("values");
		values = new Object[jsaValues.size()];
		for(int j=0;j<jsaValues.size();j++){
			values[j] = jsaValues.get(j).toString();
		}
		return values;
	}
	private String htmlHead(List detailsList,String jsonSql,String serverPath,String entityName){
		StringBuffer htmlBuf= new StringBuffer();
		htmlBuf.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" >");
		htmlBuf.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		htmlBuf.append("<head><title>明细数据</title>");
		htmlBuf.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\"/>");
		htmlBuf.append("<script type=\"text/javascript\" src=\""+serverPath+"/scripts-dq/lib/jquery-1.2.6-packed.js\"></script>");
		htmlBuf.append("<script type=\"text/javascript\" src=\""+serverPath+"/scripts-dq/lib/jquery.ui-1.6-packed.js\"></script>");
		htmlBuf.append("<script type=\"text/javascript\" src=\""+serverPath+"/scripts-dq/youi/youi.all-1.1.0-packed.js\"></script>");
		htmlBuf.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+serverPath+"/styles-dq/youi.grid-1.1.0.css\"/>");
		htmlBuf.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+serverPath+"/styles-dq/youi.panel-1.1.0.css\"/>");
		htmlBuf.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+serverPath+"/styles-dq/youi.field-1.1.0.css\"/>");
		htmlBuf.append("<link rel=\"stylesheet\" type=\"text/css\" href=\""+serverPath+"/styles-dq/ui/themes/flora/flora.dialog.css\"/>");
		htmlBuf.append(htmlGridScript(detailsList,jsonSql,serverPath,entityName));
		htmlBuf.append("</head>");
		return htmlBuf.toString();
	}
	
	private String htmlGridScript(List detailsList,String jsonSql,String serverPath,String entityName){
		//String 
		List filterList = new ArrayList();//查询条件
		
		Map detailsMap;
		String isFilter;//"		是否作为查询条件 ISFILTER," +//
		for(int i=0;i<detailsList.size();i++){
			detailsMap = (Map)detailsList.get(i);
			isFilter =getMapValue(detailsMap,"ISFILTER");
			if(isFilter!=null&&isFilter.equals("1")){
				filterList.add(detailsMap);
			}
			isFilter = null;
		}
		String sql;
		try {
			sql = java.net.URLEncoder.encode(jsonSql,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			sql = "";
		}
		StringBuffer htmlBuf= new StringBuffer();
		htmlBuf.append("<script>");
		htmlBuf.append("$(document).ready(function(){");
		htmlBuf.append("	$(\"#grid-dqQuery\").grid({");
		htmlBuf.append("		src:'"+serverPath+"/dqQuery.action?method=pagerDetails&json:sql="+sql+"',");
		htmlBuf.append("		scrollHeight:350,");
		htmlBuf.append("		showNum:true,");
		htmlBuf.append("		lazyCount:true,");
		htmlBuf.append(htmlCols(detailsList));
		htmlBuf.append(htmlFilters(filterList));
		htmlBuf.append("		buttons:[{active:'0',caption:'编辑显示字段',action:function(){window.showModalDialog('"+serverPath+"/dw/aic/dq/editDetails.jsp?entityTable="+entityName+"',self,'dialogWidth:\"1010px\";dialogHeight:\"600px\";resizable:yes;status:no;help:no');}}],");
		htmlBuf.append("		width:1000");
		htmlBuf.append("	});");
		htmlBuf.append(" });");
		
		htmlBuf.append("</script>");
		return htmlBuf.toString();
	}
	
	private String htmlFilters(List filterList)
	{
		if(filterList.size()==0)return "";
		Map detailsMap;
		String detailsField;// 字段名DETAILS_FIELD," +//
		String detailsName;//" 字段中文名		DETAILS_NAME," +//
		
		StringBuffer htmlBuf= new StringBuffer("filterCaption:'明细查询',");
		htmlBuf.append("filters:[");
		for(int i=0;i<filterList.size();i++){
			detailsMap = (Map)filterList.get(i);
			detailsField = getMapValue(detailsMap,"DETAILS_FIELD");
			detailsName =getMapValue(detailsMap,"DETAILS_NAME");
			if(i>0)htmlBuf.append(",");
			htmlBuf.append("{");
			htmlBuf.append("	property:'"+detailsField+"',");
			htmlBuf.append("	fieldType	:'fieldText',");
			htmlBuf.append("	caption:'"+detailsName+"'");
			htmlBuf.append("}");
			detailsField = null;
			detailsName = null;
			
		}
		htmlBuf.append("],");
		return htmlBuf.toString();
	}

	private String htmlCols(List detailsList){
		Map detailsMap;
		String detailsField;// 字段名DETAILS_FIELD," +//
		String detailsName;//" 字段中文名		DETAILS_NAME," +//
		String detailsWidth;//"	宽度	DETAILS_WIDTH " +//
		String detailsHref;//"	链接	DETAILS_HREF," +//
		String detailsParams;//"链接参数		DETAILS_PARAMS," +//
		String detailsParamsAlis;//链接别名	DETAILS_PARAMSALIAS
		String isShow;//是否显示列 ISSHOW
		
		StringBuffer htmlBuf= new StringBuffer("cols:[");

		StringBuffer colsBuf= new StringBuffer();
		for(int i=0;i<detailsList.size();i++){
			detailsMap = (Map)detailsList.get(i);
			detailsField = getMapValue(detailsMap,"DETAILS_FIELD").toUpperCase().replaceAll("\\.", "_");
			detailsName =getMapValue(detailsMap,"DETAILS_NAME");
			detailsWidth =getMapValue(detailsMap,"DETAILS_WIDTH");
			detailsHref =getMapValue(detailsMap,"DETAILS_HREF");
			detailsParams =getMapValue(detailsMap,"DETAILS_PARAMS");
			detailsParamsAlis=getMapValue(detailsMap,"DETAILS_PARAMSALIAS");
			isShow = getMapValue(detailsMap,"ISSHOW");
			if(!isShow.equals("0")){
				colsBuf.append(",");
				colsBuf.append("{");
				colsBuf.append("	property:'"+detailsField+"',");
				colsBuf.append("	width:'"+detailsWidth+"',");
				
				if(detailsHref!=null&&!detailsHref.equals("")){
					colsBuf.append("type:'link',");
					colsBuf.append("href:'"+detailsHref+"',");
					if(detailsParams!=null&&!detailsParams.equals(""))colsBuf.append("params:'"+detailsParams+"',");
					if(detailsParamsAlis!=null){
						colsBuf.append("paramsAlias:'"+detailsParamsAlis+"',");
					}
				}
				
				colsBuf.append("	caption:'"+detailsName+"'");
				colsBuf.append("}");
			}
			detailsField = null;detailsName=null;detailsWidth=null;
			detailsHref=null;detailsParams=null;
		}
		htmlBuf.append(colsBuf.substring(1));
		htmlBuf.append("],");
		return htmlBuf.toString();
	}
	/**
	 * 从sql语句中获得查询主实体的表名
	 * @param statSql
	 * @return
	 */
	private String getEntityTableFromSql(String statSql){
		statSql = statSql.toUpperCase();
		int fromIndex = statSql.indexOf("FROM");
		int whereIndex =  statSql.indexOf("WHERE");
		String entityTables = statSql.substring(fromIndex+4,whereIndex-1).trim();
		String entityTable = "";//查询主实体表名
		entityTable = entityTables.split(",|\\s")[0].trim();
		
		return entityTable;
	}
	/*
	 * 
	 */
	private Set getForeignEntityTablesFromSql(String statSql){
		Set tableSet = new HashSet();
		String pattern = "(LEFT JOIN )([^\\s]*)( ON)"; //查找table的名称
		Matcher matcher =Pattern.compile(pattern).matcher(statSql.toUpperCase());
		while (matcher.find()) {
			String temp = matcher.group(2).trim();
			if(!temp.toUpperCase().equals("DM"))tableSet.add(temp);
		}
		return tableSet;
	}
	
	public static void main(String[] args){
		
	}
	
	private String getMapValue(Map map ,String key){
		Object obj = map.get(key);
		if(obj!=null){
			return obj.toString();
		}else{
			return null;
		}
	}

	public List getEntityDetails(String entityTable, String conType)
	{
		DqQueryDao dqQueryDao = new DqQueryDao();
		return dqQueryDao.getEntityDetails(entityTable, conType);
	}
}
