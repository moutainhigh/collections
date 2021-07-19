<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<freeze:html>
<head>
<script language="javascript">
window.onload = function(){
	document.getElementById("methodName").onchange = function(){
		var opIndex = document.getElementById("methodName");
		if(opIndex.selectedIndex == 0){
			document.getElementById("testDiv").style.display = "block";
			document.getElementById("pageList_frameX").style.display = "none";
		}else{
			document.getElementById("testDiv").style.display = "none";
			document.getElementById("pageList_frameX").src = "<%= request.getContextPath()%>/dw/runmgr/services/wstaxtest/"+opIndex.options[opIndex.selectedIndex].targetPage+"?methodName="+opIndex.options[opIndex.selectedIndex].value+"&methodNameCn="+opIndex.options[opIndex.selectedIndex].text;
			document.getElementById("pageList_frameX").style.display = "block";
		}
	}
	
}
</script>	
</head>
<freeze:body>
<freeze:title caption="选择方法"/>
	<table border="0" width="95%" align="center" class="frame-body" cellpadding="0" cellspacing="1">
		<tr class="title-row">
			<td colspan="2">方法列表</td>
		</tr>
		<tr class="oddrow">
			<td width="8%">请选择方法：</td>
			<td><select name="methodName">
				<option value="getLJ_Query">连接测试</option>
				<option value="getGSDJ_INFO" targetPage="singleResultParam.jsp">查询开业企业信息</option>
				<option value="getZX_INFO" targetPage="singleResultParam.jsp">查询注销企业信息</option>
				<option value="getDX_INFO" targetPage="singleResultParam.jsp">查询吊销企业信息</option>
				<option value="getBG_INFO" targetPage="singleResultParam.jsp">查询变更企业信息</option>
				<option value="getGSDJ_QUERY" targetPage="multiResultParam.jsp">查询开业企业列表</option>
				<option value="getZX_QUERY" targetPage="multiResultParam.jsp">查询注销企业列表</option>
				<option value="getDX_QUERY" targetPage="multiResultParam.jsp">查询吊销企业列表</option>
				<option value="getBG_QUERY" targetPage="multiResultParam.jsp">查询变更企业列表</option>
			</select></td>
		</tr>
	</table>
	<br>
	<div id="testDiv" align="center">
	<form method="post" action="<%= request.getContextPath()%>/dw/runmgr/services/wstaxtest/test.jsp" target="_parent">
		<input type="hidden" id="methodName" name="methodName" value="getLJ_Query"/>
		<input type="submit" class="menu" value=" 提 交 " />
	</form>
	</div>
	<IFRAME id="pageList_frameX" name="pageList_frameX" frameBorder=0 width="95%" align="center" height="400" style="display:none" ></IFRAME>
</freeze:body>
</freeze:html>