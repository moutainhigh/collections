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
<title>�߼���ѯ��Ϣ</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<script type="text/javascript">
// ��ֹ�����Զ���ã��Է�ֹ�û�ѡ���������ʱ���Զ����ء�
function _setFocus(){
	return;
}

var xmlObj;
function getTotalRecord(){
	document.getElementById("totalRecord").innerHTML = "���ڻ�ȡ��...���Ժ�";
	window.setTimeout(function(){
		var rootPath = '<%=request.getContextPath() %>';
		//var post = "testsql=<%=sql %>";
		var sql = "<%=sql%>";
		$.post(rootPath+"/getCount",{testsql:sql}, function(xml){
			var result = xml.selectSingleNode("/results/sql").text;
			if (result=="true") { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
				var totalNumber = xml.selectSingleNode("/results/totalRecord").text;
				document.getElementById("totalRecord").innerHTML = totalNumber + "&nbsp;<a style='cursor:hand;color:white;' onclick='getTotalRecord()'>ˢ��</a>";
			} else { //ҳ�治����
				document.getElementById("totalRecord").innerHTML = "��ȡ��¼����ʱ�����쳣�� " + "<a style='cursor:hand' onclick='getTotalRecord()'>ˢ��</a>";
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

//����XMLHttpRequest����
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
	if (xmlObj.readyState == 4) { // �ж϶���״̬
		if (xmlObj.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ
			var xmlResults = xmlObj.responseXML;
			// alert(xmlResults.xml);
			var totalNumber = xmlResults.selectSingleNode("//results/totalRecord").text;
			document.getElementById("totalRecord").innerHTML = totalNumber + "&nbsp;<a style='cursor:hand' onclick='getTotalRecord()'>ˢ��</a>";
		} else { //ҳ�治����
			document.getElementById("totalRecord").innerHTML = "��ȡ��¼����ʱ�����쳣�� " + "<a style='cursor:hand' onclick='getTotalRecord()'>ˢ��</a>";
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
			    <td class="centerTitle" style="text-align:left;">��ѯ���  ��¼������<span id="totalRecord"><a style='cursor:hand' onclick="getTotalRecord()">(����˴���ȡ)</a></span></td>
				<td class="rightTitle"></td>
			</tr></table>
			</tr></table>
			<div style='width:100%;overflow-x:scroll;'>
				  <table width='100%' style='border:2px solid #006699;' cellpadding='0' cellspacing='0' class="frame-body" border="0">
					  <tr class="grid-headrow">
					  	<td align="center" width="6%" nowrap>���</td>
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
			    <td colspan="4" height="30">&nbsp;&nbsp;��ѯִ���쳣</td>
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
