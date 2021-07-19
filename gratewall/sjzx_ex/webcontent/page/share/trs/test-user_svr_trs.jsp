<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="840" height="600">
<head>
<title>测试服务</title>
<link href="<%=request.getContextPath()%>/script/css/cupertino/jquery.ui.all.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>

<%
DataBus db = (DataBus)request.getAttribute("freeze-databus");

%>
</head>
<script language="javascript">

//保存用户参数的字段名字，用于提交时遍历参数值
var userParamArray = new Array();
	
var svrNo = "<%= request.getParameter("svrNo") %>";
var start = new Date().getTime();
var end = '';

// 返 回
function func_record_goBack()
{
	goBack();	
}

function __userInitPage()
{
}

_browse.execute("__userInitPage()");

</script>
<freeze:body>
<freeze:title caption="测试用户服务" />
<freeze:errors/>
<script language="javascript">


function existInArray(arr, v){
	if(arr == null || arr.length == 0)
		return -1;
	for(var i=0;i<arr.length;i++){
		if(v.toString() == arr[i].toString()){
			return i;
		}
	}
	return -1;
}
function visitWs(){
    if(document.getElementById('queryStr').value == ""){
    		alert("请输入测试查询词");
    		return;
    }
    
    
	var post = "";
	post += "SVR_CODE="+svrNo;
	post += "&";
	post += "queryStr="+document.getElementById('queryStr').value;
	post += "&";
	post += "trs_data_base="+document.getElementById('trs_data_base').value;
	
    document.userParamForm.action = "wstest_trs.jsp?"+post
    start = new Date().getTime();
	document.userParamForm.submit();
	_showProcessHintWindow ("查询中...");
	var frame = document.getElementById('wstest');

	frame.attachEvent('onload', function(){ 
    	end = new Date().getTime();
    	_hideProcessHintWindow ();
    	var used = (end-start)/1000 ;
    	document.getElementById("usedTime").innerHTML = used  + ' 秒';
	});
}

function downloadWs1(){
	
	var post = "";
	for(var i = 0; i < userParamArray.length; i++){
		var params = document.getElementsByName(userParamArray[i]);
		post += userParamArray[i] + "=";
		for(var j = 0; j < params.length; j++){
			post += params[j].value;
			if(j != params.length -1){
				post += ",";
			}
		}
		
		if(i != userParamArray.length -1){
			post += "&";
		}
	}
	
	
    document.userParamForm.action = "downWs.jsp?"+post
    start = new Date().getTime();
	document.downloadForm.submit();
	_showProcessHintWindow ("正在生成数据文件...");
	
}
</script>

<table width="95%" border="0" align="center">
	<tr><td>
	<form name="userParamForm" action="trstest.jsp" method="post" target='wstest'/>
	  <table width="100%"  align="center" cellpadding="0" cellspacing="0"  class="frame-body" >  
		  
		  <tr>
		  	<td>
		  		<table cellspacing="0" cellpadding="0" width="100%" style="background-color:#f7f7f7;border:1px solid rgb(207,207,254);border-collapse:collapse;">
		  			<tr>
		  <td class=even2 colspan="2" > 
		  	<%= request.getParameter("service_targets_id") %>：<%= request.getParameter("trs_service_name") %></td>
		  </tr>
		  			<tbody id="userParam">
		  			
		  			<tr>
		  				<TD class=even2  width="30%">测试查询词： </TD>
<TD class=odd2 width="70%"><INPUT id="queryStr" name="queryStr"></TD>
</tr>
		  			</tbody>
		  <tr>
			<td height="40px" class="odd1" colspan="2" align="center" >
				<input type="hidden" name="SVR_CODE" id="SVR_CODE" value="<%= request.getParameter("svrNo")%>" />
				<input type="hidden" name="USER_TYPE" id="USER_TYPE" value="TEST"/>
				<input type="hidden" name="search_num" id="search_num" value="5"/>
				<input type="hidden" name="trs_data_base" id="trs_data_base" value="<%= request.getParameter("trs_data_base")%>"/>
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<div class="btn_query" onclick="visitWs()"></div>
							
						</td>
						<td class="btn_right"></td>
					</tr>
				</table>
				
		  </tr>
		  		</table>
		  	</td>
		  </tr> 
	  </table>
	</form>
	</td></tr>
	<tr><td colspan="2">执行时间: <span id="usedTime"></span></td></tr>
	<tr><td colspan="2">
	 <iframe id='wstest'  name='wstest' scrolling='yes' frameborder=0 width=100% height=300 style="display:block;background-color:#F4FAFB;"></iframe>
	</td></tr>
	
	 </table>
	 <center><table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
			<div class="btn_cancel" onclick="func_record_goBack()"></div>
		</td>
		<td class="btn_right"></td>
	</tr></table></center>
</freeze:body>	
</freeze:html>
