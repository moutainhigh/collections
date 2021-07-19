<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<%@ page import="com.gwssi.common.database.DBOperation" %>
<%@ page import="com.gwssi.common.database.DBOperationFactory" %>
<%@ page import="java.util.*" %>
<%@ page import="com.gwssi.common.result.*" %>
<%@ page import="com.gwssi.dw.aic.bj.ColumnCode" %>
<%
	DBOperation operation = DBOperationFactory.createTimeOutOperation();
	String sql = request.getParameter("record:query_sql");
//	System.out.println("sql before decode:" + sql);
//	sql = java.net.URLDecoder.decode(sql, "UTF-8");
//	System.out.println("sql after decode:" + sql);
	//String[] columnsEnArray = request.getParameter("record:columnsEnArray").split(",");
	String[] columnsEnArray = ColumnCode.getColumnNames(request.getParameter("record:columnsEnArray"));
	String[] columnsCnArray = request.getParameter("record:columnsCnArray").split(",");
	//System.out.println("SQL:\n" + sql);
	String displayType = request.getParameter("displayType");
	if (displayType == null || 
			(!displayType.equalsIgnoreCase("code") && !displayType.equalsIgnoreCase("mix"))){
		displayType = "name";
	}
	boolean isName = displayType.equalsIgnoreCase("name");
	boolean isCode = displayType.equalsIgnoreCase("code");
	boolean isMix = displayType.equalsIgnoreCase("mix");
%>
<head>
<title>高级查询信息</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
// 禁止焦点自动获得，以防止用户选择滚动条的时候自动弹回。
function _setFocus(){
	return;
}

var xmlObj;
function getTotalRecord(){
	if (!document.getElementById("totalRecord")){
		return false;
	}
	document.getElementById("totalRecord").innerHTML = "正在获取中...请稍候";
	window.setTimeout(function(){
		var rootPath = "<%=request.getContextPath() %>";
		// var sql = "<%=java.net.URLEncoder.encode(sql, "UTF-8")%>";
		var sql = "<%=sql%>";
		// alert("sql:" + sql);
		$.post(rootPath+"/getCount",{testsql:sql}, function(xml){
			var result = xml.selectSingleNode("/results/totalRecord").text;
			document.getElementById("totalRecord").innerHTML = "<span id='totalRecordSpan'>" + result + "</span>";
			var downloadButton = parent.document.getElementById("downloadButton");
			if (downloadButton){
				downloadButton.onclick = function(){
					// 如果是数据下载
					if (parent.funcType && parent.funcType=="download"){
						parent.downloadHandler(result, "download");
					}else{// 否则是高级查询
						parent.downloadHandler(result);
					}
				}
				downloadButton.disabled = false;
			}
			downloadButton = null;
		});
	}, 500);
}

//创建XMLHttpRequest对象
function createXMLHttpRequest() {
	try{
		xmlObj = new ActiveXObject("Msxml2.XMLHTTP"); 
	} catch (e) { 
		try{ 
			xmlObj = new ActiveXObject("Microsoft.XMLHTTP"); 
		} catch (E) { 
			xmlObj = false; 
		}
	}

	if (!xmlObj && typeof XMLHttpRequest!='undefined'){ 
		xmlObj = new XMLHttpRequest(); 
	}
	if (!xmlObj){
		alert("Get Ajax object failed");
	}
	return xmlObj;
}

function handleRespose(){
	if (xmlObj.readyState == 4) { // 判断对象状态
		if (xmlObj.status == 200) { // 信息已经成功返回，开始处理信息
			var xmlResults = xmlObj.responseXML;
			// alert(xmlResults.xml);
			var totalNumber = xmlResults.selectSingleNode("//results/totalRecord").text;
			document.getElementById("totalRecord").innerHTML = "<span id='totalRecordSpan'>" + totalNumber + "</span>";
			var downloadButton = parent.document.getElementById("downloadButton");
			if (downloadButton){
				downloadButton.onclick = function(){
					// 如果是数据下载
					if (parent.funcType && parent.funcType=="download"){
						parent.downloadHandler(totalNumber, "download");
					}else{// 否则是高级查询
						parent.downloadHandler(totalNumber);
					}
					
				}
				downloadButton.disabled = false;
			}
			downloadButton = null;
		} else { //页面不正常
			document.getElementById("totalRecord").innerHTML = "获取记录总数时出现异常。 " + "<a style='cursor:hand' onclick='getTotalRecord()'>刷新</a>";
		}
	}
}

// 更换显示格式
function changeDataType(dt){
	parent.document.getElementById("displayType").value = dt;
	parent.document.forms[0].submit();	
}

window.onload = getTotalRecord;
</script>
</head>
<freeze:body>
	<%try{ 
		Page newPage = new Page(1, 10);
		List list = operation.select(sql, newPage);
		list = ColumnCode.parseColumnCode(request.getParameter("record:columnsEnArray"), list, displayType);
	%>
		<TABLE align="center" cellspacing='1' cellpadding='0' border="0" class="frame-body" width="100%">
			<tr align='left' class='title-headrow'>
			    <td height="30">&nbsp;&nbsp;查询结果  记录总数：<span id="totalRecord"><a style='cursor:hand' onclick="getTotalRecord()">(点击此处获取)</a></span></td>
			    <td align="right">代码字段：<input type="radio" name="changeDataTypeRadio" onclick="changeDataType('name')" <%=isName ? "checked" : "" %> />显示为名称
			    <input type="radio" name="changeDataTypeRadio" onclick="changeDataType('code')" <%=isCode ? "checked" : "" %> />显示为代码
			    <input type="radio" name="changeDataTypeRadio" onclick="changeDataType('mix')" <%=isMix ? "checked" : "" %> />显示为混合</td>
			</tr>
			<tr>
			  <td colspan="2">
				  <table width='100%' cellpadding='0' cellspacing='1' class="frame-body" border="0">
					  <tr class="grid-headrow">
					  	<td align="center" width="6%" nowrap>序号</td>
				      	<%
				      		for (int arrayIndex = 0; arrayIndex < columnsCnArray.length; arrayIndex++){
				      	%>
				      			<td align="center" nowrap><%= columnsCnArray[arrayIndex]%></td>
				      	<%
				      		}
				      	%>
					  </tr>
					  <%
					  	if(list != null && list.size() > 0){
					  		for(int i = 0; i < list.size(); i++){
					  %>
					  		<tr class="framerow">
							  	<td nowrap><%= i+1%></td>
							  	<%
							  		Map map = (Map)list.get(i);
							  		Set set = map.keySet();
							  		for (int enArrayIndex = 0; enArrayIndex < columnsEnArray.length; enArrayIndex ++){
							  			String key = columnsEnArray[enArrayIndex].toUpperCase();
							  			String value = map.get(key) == null ? "&nbsp" : map.get(key).toString();
							  	%>
							  	<td nowrap><%= value%></td>
							  	<% 		
							  		}
							  	%>
							</tr>
					  <%
					  		}
					  	}
					  %>
		          </table>
	          </td>
			</tr>
		</table>
	<%}catch(Exception e){%>
		<TABLE align="center" cellspacing='1' cellpadding='0' border="0" class="frame-body" width="100%">
			<tr align='left' class='title-headrow'>
			    <td colspan="4" height="30" id="errorTd">&nbsp;&nbsp;查询执行异常</td>
			</tr>
			<tr>
		  		<td>
			  		<table width='100%' cellpadding='0' cellspacing='1' class="frame-body" border="0">
			  			<tr class="framerow">
					  	<td style="color:red;font-size:14px;height:30px"><%= e.getMessage()%></td>
					  	</tr>
					</table>
				</td>
			</tr>
		</TABLE>
	<%}%>
</freeze:body>
</freeze:html>
