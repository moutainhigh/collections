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
<title>�߼���ѯ��Ϣ</title>
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery1.1.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
// ��ֹ�����Զ���ã��Է�ֹ�û�ѡ���������ʱ���Զ����ء�
function _setFocus(){
	return;
}

var xmlObj;
function getTotalRecord(){
	if (!document.getElementById("totalRecord")){
		return false;
	}
	document.getElementById("totalRecord").innerHTML = "���ڻ�ȡ��...���Ժ�";
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
					// �������������
					if (parent.funcType && parent.funcType=="download"){
						parent.downloadHandler(result, "download");
					}else{// �����Ǹ߼���ѯ
						parent.downloadHandler(result);
					}
				}
				downloadButton.disabled = false;
			}
			downloadButton = null;
		});
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
			document.getElementById("totalRecord").innerHTML = "<span id='totalRecordSpan'>" + totalNumber + "</span>";
			var downloadButton = parent.document.getElementById("downloadButton");
			if (downloadButton){
				downloadButton.onclick = function(){
					// �������������
					if (parent.funcType && parent.funcType=="download"){
						parent.downloadHandler(totalNumber, "download");
					}else{// �����Ǹ߼���ѯ
						parent.downloadHandler(totalNumber);
					}
					
				}
				downloadButton.disabled = false;
			}
			downloadButton = null;
		} else { //ҳ�治����
			document.getElementById("totalRecord").innerHTML = "��ȡ��¼����ʱ�����쳣�� " + "<a style='cursor:hand' onclick='getTotalRecord()'>ˢ��</a>";
		}
	}
}

// ������ʾ��ʽ
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
			    <td height="30">&nbsp;&nbsp;��ѯ���  ��¼������<span id="totalRecord"><a style='cursor:hand' onclick="getTotalRecord()">(����˴���ȡ)</a></span></td>
			    <td align="right">�����ֶΣ�<input type="radio" name="changeDataTypeRadio" onclick="changeDataType('name')" <%=isName ? "checked" : "" %> />��ʾΪ����
			    <input type="radio" name="changeDataTypeRadio" onclick="changeDataType('code')" <%=isCode ? "checked" : "" %> />��ʾΪ����
			    <input type="radio" name="changeDataTypeRadio" onclick="changeDataType('mix')" <%=isMix ? "checked" : "" %> />��ʾΪ���</td>
			</tr>
			<tr>
			  <td colspan="2">
				  <table width='100%' cellpadding='0' cellspacing='1' class="frame-body" border="0">
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
			    <td colspan="4" height="30" id="errorTd">&nbsp;&nbsp;��ѯִ���쳣</td>
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
