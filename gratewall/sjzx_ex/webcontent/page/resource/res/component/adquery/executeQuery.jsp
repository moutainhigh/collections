<%@ page contentType="text/html; charset=GBK" %>
<%@page import="org.apache.commons.lang.StringUtils"%>
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
  //保存查询日志
   var totalRecord=document.getElementById('totalCount').value;
   var sqlcondition=document.getElementById('sqlCondition').innerHTML;
   if(sqlcondition!=null&&sqlcondition!='null'&&sqlcondition!=''){
	   var page = new pageDefine("/txn6025011.ajax", "保存查询日志");
	   page.addValue( totalRecord, "select-key:download_count" );
	   page.addValue( sqlcondition, "select-key:download_cond" );
	   page.callAjaxService('doCallback_save'); 
   }  
}

function doCallback_save(errCode, errDesc, xmlResults){
	     if(errCode == '000000'){
		        //alert('保存成功');
		  }
}

_browse.execute("__userInitPage()");
</script>
<freeze:body>
<%
	try{
	DBOperation operation = DBOperationFactory.createTimeOutOperation();
	String sys_advanced_query_id = request.getParameter("record:sys_advanced_query_id");
	//Map advMap = operation.selectOne("SELECT sys_advanced_query_id, query_sql, display_columns_cn_array, display_columns_en_array, exec_total FROM sys_advanced_query WHERE sys_advanced_query_id='" + sys_advanced_query_id + "'");
	//String sql = (String)session.getAttribute("query_sql");
	String sql = (String)request.getParameter("query_sql");
	String reg_org = (String)request.getParameter("reg_org");
	//如果为区县用户
	if(StringUtils.isNotBlank(reg_org)){
	  if(sql.indexOf("REG_BUS_ENT")!=-1){
		  if(sql.indexOf("WHERE")!=-1){
			  sql+=" AND REG_BUS_ENT.DOM_DISTRICT ='"+reg_org+"' ";
		  }else{
			  sql+=" WHERE REG_BUS_ENT.DOM_DISTRICT ='"+reg_org+"' ";
		  }
	  }else{
		  if(sql.indexOf("WHERE")!=-1){
			  sql+=" AND REG_INDIV_BASE.REG_ORG LIKE '"+reg_org+"%' ";
		  }else{
			  sql+=" WHERE REG_INDIV_BASE.REG_ORG LIKE '"+reg_org+"%' ";
		  }
	  }
	}
	System.out.println(reg_org+"-----------"+sql);
	//sql = java.net.URLDecoder.decode(sql, "UTF-8");
	int total = (new Integer((String)request.getParameter("exec_total")).intValue()) + 1;
	int curPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
	Page newPage = new Page(curPage, 10);
	String[] columnsEnArray = ColumnCode.getColumnNames((String)request.getParameter("display_columns_en_array"));
	//String[] columnsEnArray = ((String)advMap.get("DISPLAY_COLUMNS_EN_ARRAY")).split(",");
	String[] columnsCnArray = ((String)request.getParameter("display_columns_cn_array")).split(",");
    int totalRecord = new Long(operation.count(sql)).intValue();
	newPage = PageUtil.createPage(newPage,totalRecord);
	List list = operation.select(sql, newPage);
	list = ColumnCode.parseColumnCode((String)request.getParameter("display_columns_en_array"), list, ColumnCode.NAME);
%><br />
<div id="sqlCondition" style="display: none"><%=(request.getParameter("sqlCondition")==null ? "" :request.getParameter("sqlCondition"))%></div>

<form action="/executeQuery.jsp" method="post" id="form1">
  <input type="hidden" name="record:sys_advanced_query_id" value="<%=request.getParameter("sys_advanced_query_id")%>"/>
  <input type="hidden" id="condition_sql" name="query_sql" value="<%=request.getParameter("query_sql") %>" />
  <input type="hidden" id="exec_total" name="exec_total" value="<%=request.getParameter("exec_total") %>" />
  <input type="hidden" name="display_columns_en_array" value="<%=request.getParameter("display_columns_en_array") %>" />
  <input type="hidden" name="display_columns_cn_array" value="<%=request.getParameter("display_columns_cn_array") %>" />
</form>
	<div style="width:100%;overflow-x:auto;">
	 <table width="100%" align="center" cellpadding="0" cellspacing="0" class="frame-body" style="border:2px solid #006699;">
	  <tr class="grid-headrow">
	      <td align="center" nowrap>序号</td>
	     	  <%
	     		for (int arrayIndex=0; arrayIndex < columnsCnArray.length; arrayIndex++){
	     	  %>
	     			<td nowrap align="center" <%if(columnsCnArray[arrayIndex].indexOf("经营场所")>-1 ||columnsCnArray[arrayIndex].indexOf("经营范围")>-1 || columnsCnArray[arrayIndex].indexOf("住所(全)")>-1){out.print("width='200px'");} %> nowrap><%= columnsCnArray[arrayIndex]%></td>
	     	  <%
	     		}
	     	  %>
	  </tr>
	  <%
	  	if(list != null && list.size() > 0){
	  		for(int i = 0; i < list.size(); i++){
	  %>
	  		<tr class="framerow" style="line-height: 30px;">
			  	<td class="even<%=2-i%2%>"><%= newPage.getCurrentPage() != 1 ? (newPage.getCurrentPage()-1)*10 + (i+1):i+1%></td>
			  	<%
			  		Map map = (Map)list.get(i);
			  		Set set = map.keySet();
			  		for (int enArrayIndex = 0; enArrayIndex < columnsEnArray.length; enArrayIndex ++){
			  			String key = columnsEnArray[enArrayIndex].toUpperCase();
			  			String value = map.get(key) == null ? "&nbsp" : map.get(key).toString();
			  	%>
			  	<td class="odd<%=2-i%2%>" ><%= value%></td>
			  	<% 		
			  		}
			  	%>
			</tr>
	  <%
	  		}
	  	}
	  	if(request.getParameter("isUpdate") != null && request.getParameter("isUpdate").equals("true")){
		  	String todayStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			String updateSQL = "UPDATE sys_advanced_query SET LAST_EXEC_DATE='"+todayStr+"', EXEC_TOTAL="+total+" WHERE sys_advanced_query_id='"+sys_advanced_query_id+"'";
			operation.execute(updateSQL,false);
		}
	  %>
	       </table></div>
	       <input type="hidden" id="totalCount" value="<%=newPage.getTotalRecords() %>"/>
	<TABLE borderColor=#7f9db9 width="95%" align="center">
	<TBODY>
		<TR>
			<TD vAlign=center>
			当前第<SPAN id=record_curPage name="record_curPage">
			<%= newPage.getCurrentPage()%></SPAN>页 /共
			<SPAN id=record_totalPage name="record_totalPage">
			<%= newPage.getTotalPage()%></SPAN>页 记录总数
			<SPAN id=record_totalRow name="record_curRow">
			<%= newPage.getTotalRecords()%></SPAN>条 
	<INPUT id=attribute-node:record_record-number type=hidden name=attribute-node:record_record-number> 转到
	 <INPUT id=record_selectPage onkeydown="if(event.keyCode == 13){jumpPage(); return false;}" 
	 style="BORDER-RIGHT: #7f9db9 1px solid; BORDER-TOP: #7f9db9 1px solid; BORDER-LEFT: #7f9db9 1px solid; BORDER-BOTTOM: #7f9db9 1px solid" size=3 value="<%= newPage.getCurrentPage()%>" name=record_selectPage> 页 </TD>
			<TD align=right>
			<BUTTON class="goFirst" title=第一页 style="WIDTH: 25px" <%= newPage.getHasPrePage() == true ? "" : "disabled" %> onclick="turnPage(1);" /> 
			<BUTTON class="goPre" title=上一页 style="WIDTH: 25px" <%= newPage.getHasPrePage() == true ? "" : "disabled" %> onclick="turnPage('<%=newPage.getCurrentPage() - 1%>');">
			<BUTTON class="goNext" title=下一页 style="WIDTH: 25px" <%= newPage.getHasNextPage() == true ? "" : "disabled"%> onclick="turnPage('<%=newPage.getCurrentPage()+ 1%>');" /> 
			<BUTTON class="goLast" title=最后一页 style="WIDTH: 25px" <%= newPage.getHasNextPage() == true ? "" : "disabled"%> onclick="turnPage('<%=newPage.getTotalPage()%>');" /> 
			 <INPUT id=attribute-node:record_start-row type=hidden value=1 name=attribute-node:record_start-row> 
			</TD>
		</TR>
	</TBODY>
    </TABLE>
<script language="javascript">
// 禁止焦点自动获得，以防止用户选择滚动条的时候自动弹回。
function _setFocus(){
	return;
}

window.onload=function(){
	window.frameElement.style.display = "block";
}
	//分页中“跳转到某页”用到的方法
	function jumpPage(){
		var pageNo = document.getElementById('record_selectPage').value;
		var reg = new RegExp("^[0-9]*[1-9][0-9]*$");
	    if (reg.test(pageNo)==false){
			alert("请输入有效数字！");
			document.getElementById('record_selectPage').focus();
			return;
	    }
		var totalPage = "<%=newPage.getTotalPage()%>";
		if(parseInt(pageNo) > parseInt(totalPage)){
			alert("超过最大页数！");
			return;
		}	
		turnPage(pageNo);
		//window.location.href = 'executeQuery.jsp?record:sys_advanced_query_id=<%= sys_advanced_query_id%>&currentPage='+pageNo;
	}
	
	function turnPage(curPage){
	   document.getElementById('form1').action = 'executeQuery.jsp?record:sys_advanced_query_id=<%= sys_advanced_query_id%>&currentPage='+curPage;
	   document.getElementById('form1').submit();
	}
	
</script>
<%}catch(Exception e){%>
<TABLE align="center" cellspacing='0' cellpadding='0' border="0" class="frame-body" width="100%">
	<tr align='left' class='title-headrow'>
	    <td colspan="4" height="30">&nbsp;&nbsp;查询执行异常</td>
	</tr>
	<tr>
  		<td>
	  		<table width='100%' cellpadding='0' cellspacing='0' class="frame-body" border="0">
	  			<tr class="framerow">
			  	<td style="color:red;font-size:14px;height:30px"><%= e.getMessage()%></td>
			  	</tr>
			</table>
		</td>
	</tr>
</TABLE>

<%
}finally{
	%>
	<script language="javascript">
	window.onload=function(){
		window.frameElement.style.display = "block";
	}
	</script>
	<%
}%>

</freeze:body>
</freeze:html>
