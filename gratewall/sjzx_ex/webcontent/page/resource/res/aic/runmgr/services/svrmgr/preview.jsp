<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<%@ page import="com.gwssi.common.database.DBOperation" %>
<%@ page import="com.gwssi.common.database.DBOperationFactory" %>
<%@ page import="java.util.*" %>
<%@ page import="com.gwssi.common.result.*" %>
<%
	DBOperation operation = DBOperationFactory.createTimeOutOperation();
	String sql = request.getParameter("record:query_sql");
	String[] columnsEnArray = request.getParameter("record:columnsEnArray").split(",");
	String[] columnsCnArray = request.getParameter("record:columnsCnArray").split(",");
%>
<head> 
<title>高级查询信息</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript">
// 禁止焦点自动获得，以防止用户选择滚动条的时候自动弹回。
function _setFocus(){
	return;
}

var xmlObj;
function getTotalRecord(){
	document.getElementById("totalRecord").innerHTML = "正在获取中...请稍候";
	window.setTimeout(function(){
		var rootPath = '<%=request.getContextPath() %>';
		//var post = "testsql=<%=sql %>";
		var sql = "<%=sql%>";
		$.post(rootPath+"/getCount",{testsql:sql}, function(xml){
			var result = xml.selectSingleNode("/results/sql").text;
			if (result=="true") { // 信息已经成功返回，开始处理信息
				var totalNumber = xml.selectSingleNode("/results/totalRecord").text;
				document.getElementById("totalRecord").innerHTML = totalNumber + "&nbsp;<a style='cursor:hand;color:white;' onclick='getTotalRecord()'>刷新</a>";
			} else { //页面不正常
				document.getElementById("totalRecord").innerHTML = "获取记录总数时出现异常。 " + "<a style='cursor:hand' onclick='getTotalRecord()'>刷新</a>";
			}
		});
		//post=encodeURI(post); 
		//post=encodeURI(post); 
		//xmlObj = createXMLHttpRequest();
		//var URL = rootPath+'/getCount';
		//alert(post);
		//xmlObj.open ('post',URL, true);
		//xmlObj.onreadystatechange = handleRespose;
		//xmlObj.setrequestheader("cache-control","no-cache"); 
		//xmlObj.setrequestheader("Content-Type","application/x-www-form-urlencoded");
		//xmlObj.send(post);
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
			document.getElementById("totalRecord").innerHTML = totalNumber + "&nbsp;<a style='cursor:hand' onclick='getTotalRecord()'>刷新</a>";
		} else { //页面不正常
			document.getElementById("totalRecord").innerHTML = "获取记录总数时出现异常。 " + "<a style='cursor:hand' onclick='getTotalRecord()'>刷新</a>";
		}
	}
}

window.onload = getTotalRecord;
</script>
</head>
<freeze:body>
	<%try{ 
		Page newPage = new Page(1, 10);
		List list = operation.select(sql, newPage);
	%>
		<TABLE align="center" cellspacing='0' cellpadding='0' border="0" class="frame-body" width="100%">
			<tr style="font-size:10pt;text-align:left;">
			    <td class="leftTitle"></td>
			    <td class="centerTitle" style="text-align:left;">查询结果  记录总数：<span id="totalRecord"><a style='cursor:hand' onclick="getTotalRecord()">(点击此处获取)</a></span></td>
				<td class="rightTitle"></td>
			</tr></table>
			</tr></table>
			<div style='width:100%;overflow-x:scroll;'>
				  <table width='100%' style='border:2px solid #006699;' cellpadding='0' cellspacing='0' class="frame-body" border="0">
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
							  	<td class="odd<%=i%2==0 ? "2" : "1" %>" nowrap><%= i+1%></td>
							  	<%
							  		Map map = (Map)list.get(i);
							  		Set set = map.keySet();
							  		for (int enArrayIndex = 0; enArrayIndex < columnsEnArray.length; enArrayIndex ++){
							  			String key = columnsEnArray[enArrayIndex].toUpperCase();
							  			String value = map.get(key) == null ? "&nbsp" : map.get(key).toString();
							  			if(enArrayIndex%2==0){
							  	%>
							  	<td class="odd<%=i%2==0 ? "2" : "1" %>" nowrap><%= value%></td>
							  	<%} else { %>
							  	<td class="odd<%=i%2==0 ? "2" : "1" %>" nowrap><%= value%></td>	
							  	<%} %>
							  	<% 		
							  		}
							  	%>
							</tr>
							
							<%
					  		}
					  	}
					  %>
		          </table></div>
	<%}catch(Exception e){%>
		<TABLE align="center" cellspacing='0' cellpadding='0' border="0" class="frame-body" width="100%">
			<tr align='left' class='title-headrow'>
			    <td colspan="4" height="30">&nbsp;&nbsp;查询执行异常</td>
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
