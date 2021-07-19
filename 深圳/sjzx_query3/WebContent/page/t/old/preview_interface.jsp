<%@page import="com.gwssi.dw.aic.download.common.TestSqlServlet"%>
<%@page import="cn.gwssi.common.component.logger.TxnLogger"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.gwssi.common.constant.CollectConstants"%>
<%@ page contentType="text/html; charset=GBK" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.gwssi.common.database.DBPoolConnection"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import="com.gwssi.common.database.DBOperation" %>
<%@ page import="com.gwssi.common.database.DBOperationFactory" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.*" %>
<%@ page import="com.gwssi.common.result.*" %>
<%@ page import="com.gwssi.dw.aic.bj.ColumnCode" %>
<%@ page import="cn.gwssi.common.context.DataBus"%>
<%@ page import="cn.gwssi.common.context.Recordset"%>

<freeze:html>
<head>
<title>高级查询信息</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/module/layout/layout-weiqiang/css/gwssi.css">
</head>
<script type="text/javascript">
function __userInitPage(){
	parent._hideProcessHintWindow ();
}
_browse.execute("__userInitPage()");
</script>
<freeze:body>
<%
	//try{
	Logger	logger	= TxnLogger.getLogger(TestSqlServlet.class.getName());
	DBOperation operation = DBOperationFactory.createTimeOutOperation();
	String sys_advanced_query_id = request.getParameter("record:sys_advanced_query_id");
	//Map advMap = operation.selectOne("SELECT sys_advanced_query_id, query_sql, display_columns_cn_array, display_columns_en_array, exec_total FROM sys_advanced_query WHERE sys_advanced_query_id='" + sys_advanced_query_id + "'");
	//String sql = (String)session.getAttribute("query_sql");
	String sql = (String)request.getParameter("table_sql");
	String tableIds = request.getParameter("tableIds");
	String[] title=new String[0];
	String[] column=new String[0];
	if (StringUtils.isNotBlank(tableIds)) {
		String tids="";
		String[] ids=tableIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			tids+= (tids=="" ? "'"+ids[i]+"'" : ",'"+ids[i]+"'");
		}
		StringBuffer sql_str = new StringBuffer();
		sql_str.append("select * from(select t.share_table_id,t1.table_name_en,");
		sql_str.append("t.dataitem_name_en,t.dataitem_name_cn,row_number() ");
		sql_str.append("over(partition by t.share_table_id order by t.show_order) rn");
		sql_str.append(" from res_share_dataitem t,res_share_table t1 where t.share_table_id");
		sql_str.append("=t1.share_table_id and t.share_table_id in (");
		sql_str.append(tids).append(") )  where rn <= 3");
		logger.debug("接口sql预览:[获取基础接口所选表的部分字段]--->"+sql_str.toString());
		//System.out.println("接口sql预览:[获取基础接口所选表的部分字段]--->"+sql_str.toString());
		List list = operation.select(sql_str.toString());
		//System.out.println("获取基础接口所选表的部分字段-结果:"+list.size());
		String sql_temp="";
		title=new String[list.size()];
		column=new String[list.size()];
		int temp=0;
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map)list.get(i);
			if(sql_temp.indexOf(map.get("DATAITEM_NAME_EN").toString())==-1){
				sql_temp += (sql_temp == "" ? map.get("TABLE_NAME_EN") + "."
						+ map.get("DATAITEM_NAME_EN") : ","
						+ map.get("TABLE_NAME_EN") + "."
						+ map.get("DATAITEM_NAME_EN"));
				title[temp]=map.get("DATAITEM_NAME_CN")==null?map.get("DATAITEM_NAME_EN").toString():map.get("DATAITEM_NAME_CN").toString();
				column[temp]=map.get("DATAITEM_NAME_EN").toString();
				temp++;
			}
		}
		sql= sql.replace("*", sql_temp) + (sql.indexOf("WHERE")>-1 ? " and rownum<=1 " : "  where rownum <=1" ) ;
		if(sql.indexOf("''")!=-1){
			sql=sql.replaceAll("''","'");
		}
		//System.out.println("接口sql预览:--->"+sql);
		logger.debug("接口sql预览:--->"+sql);
	}	
	
	DBPoolConnection conn=new DBPoolConnection(CollectConstants.DATASOURCE_GXK);
	//System.out.println("准备执行接口");
	List record_list = conn.selectBySql(sql);	
	//System.out.println("执行接口sql成功，返回结果条数为："+record_list.size());
	%>
<div style="width:100%;overflow-x:auto;padding-bottom:20px;">
	 <table width="100%" align="center" cellpadding="0" cellspacing="0" class="frame-body" style='width: 95%; border: 1px solid #ccc;'>
	 <%
	 for (int j=0; j < record_list.size(); j++){
		 Map data=(Map)record_list.get(j);
		 System.out.println(title.length);
		 for (int i=0; i < title.length; i++){
		    if(null!=title[i]){
	  			out.print("<tr class='oddrow' ><td align=\"right\" width='20%'>"+title[i]+"：</td>");
	  			out.print("<td align=\"left\" width='80%'>"+(data.get(column[i])==null ? "" : data.get(column[i])) +"</td></tr>");
		    }
  		}	
	 }
	 
	 
%>	 
      </table>	  
      </div>
</freeze:body>
</freeze:html>
