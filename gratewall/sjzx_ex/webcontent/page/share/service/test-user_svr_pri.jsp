<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="/WEB-INF/freeze.tld" prefix="freeze" %>
<%@ page import = "cn.gwssi.common.context.DataBus"%>
<%-- template single/single-table-query.jsp --%>
<freeze:html width="800" height="450">
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
	
var svrId = "<%= request.getParameter("svrId") %>";
var userId = "<%= request.getParameter("userId")%>";
var old_service_no = "<%= request.getParameter("old_service_no")%>";

var start = new Date().getTime();
var end = '';

function getLimitInfo(){
	var page = new pageDefine("/txn50204002.ajax", "查询某个用户对应某个服务的限定信息");
	page.addValue( userId, "select-key:sys_svr_user_id" );
	page.addValue( svrId, "select-key:service_id" );
	page.callAjaxService("callBack()");
}
var service_no = "";
function callBack(errCode, errDesc, xml){
		if(errCode!='000000'){
			alert("查询某个用户对应某个服务的限定信息时发生错误！")
			return
		}
     
	    //var code = _getXmlNodeValue(xml,'record:svr_code');
		var name = _getXmlNodeValue(xml,'record:svr_name');
		var createDate = _getXmlNodeValue(xml,'record:create_date');
		var createBy = _getXmlNodeValue(xml,'record:create_by');
		var maxNum = _getXmlNodeValue(xml,'record:max_records');
		var create_date = _getXmlNodeValue(xml,'record:create_date');
		var service_type = _getXmlNodeValue(xml,'record:svr_type');
		var desc = _getXmlNodeValue(xml,'record:svr_desc');
		var colNameEnArray = _getXmlNodeValue(xml,'config-inf:permit_column_en_array');
		var colNameCnArray = _getXmlNodeValue(xml,'config-inf:permit_column_cn_array');
		service_no = _getXmlNodeValue(xml,"config-inf:service_no");
		var username = _getXmlNodeValue(xml,"config-inf:username");
		var password = _getXmlNodeValue(xml,"config-inf:password");
		
		
	
	//是否有用户参数
	var haveUserParam = false;
	//是否配置了系统参数
	var haveSysParam = false;
	
	var configParams = _getXmlNodeValues(xml, "config-param:params");
	
		
	
	for(var i = 0; i < configParams.length; i++){
			if(configParams[i].trim() != ''){
				var configParamJSON = eval("(" + configParams[i] + ")"); 
				if(configParamJSON.paramType == "0"){
					haveSysParam = true;
				}else{	
					insertUserParamRow(configParamJSON);			
					haveUserParam = true;
					if(existInArray(userParamArray, configParamJSON.columnOneName)== -1){
						userParamArray.push(configParamJSON.columnOneName);
					}
				}
			}
	}
	
	if(!haveUserParam){
		insertSysParamRow("userParam", "<font color='red'>无用户参数!</font>", "center");
	}
	//document.getElementById("SVR_CODE").value = code;
	//document.getElementById("JSJLS").value = maxNum;
	document.getElementById("JSJLS").value = "5";
	document.getElementById("colNameEnArray").value = colNameEnArray;
	document.getElementById("colNameCnArray").value = colNameCnArray;
	document.getElementById("SVR_CODE").value = old_service_no;
	document.getElementById("LOGIN_NAME").value = username;
	document.getElementById("PASSWORD").value = password;
	
	//alert(    userParamArray[0] +" sssssssss");
	//alert(colNameCnArray +"\n" +colNameEnArray)
}

function insertSysParamRow(tblId, paramStr, align){
	var objTable = document.getElementById(tblId);
	objTable.border = 1;
	var newTr = objTable.insertRow();
	newTr.borderColor = "#aed0ea";
	newTr.height = 30;
	
	//添加列
	var newTd0 = newTr.insertCell();
	
	//设置列内容和属性
	newTd0.innerHTML = "&nbsp;&nbsp;"+paramStr; 
	newTd0.width = "100%";
	newTd0.align = align;
	newTd0.className = "odd12";
}
function insertUserParamRow(JSONObj){
	var objTable = document.getElementById("userParam");
	objTable.border = 1;
	var newTr = objTable.insertRow();
	newTr.borderColor = "#aed0ea";
	newTr.height = 30;
	
	//添加列
	var newTd0 = newTr.insertCell();
	var newTd1 = newTr.insertCell();
	
	//设置列内容和属性
	newTd0.innerHTML = "&nbsp;&nbsp;"+JSONObj.paramText.replace("（参数值）", ""); 
	newTd0.width = "60%";
	newTd0.className = "even2";
	
	newTd1.innerHTML = "<input type='text' id='"+JSONObj.columnOneName+"' name='"+JSONObj.columnOneName+"'/>"; 
	newTd1.width = "40%";
	newTd1.className = "odd2";
}

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

getLimitInfo();

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
	
	//post += "&service_no="+old_service_no;
    document.userParamForm.action = "wstest_pri.jsp?"+post
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
</script>

<table width="95%" border="0" align="center">
	<tr><td>
	<form name="userParamForm" action="wstest.jsp" method="post" target='wstest'/>
	  <table width="100%"  align="center" cellpadding="0" cellspacing="0"  class="frame-body" >  
		  <!-- <tr><td colspan="2">
						<table width="100%" cellspacing="0" cellpadding="0" border="0">
							<tr><td class="leftTitle"></td><td class="secTitle">服务参数</td><td class="rightTitle"></td></tr>
						</table>
					</td>
			</tr> -->
		  <tr>
		  	<td>
		  		<table cellspacing="0" cellpadding="0" width="100%" style="background-color:#f7f7f7;border:1px solid rgb(207,207,254);border-collapse:collapse;">
		  			<tbody id="userParam"></tbody>
		  <tr>
			<td height="40px" class="odd1" colspan="2" align="center" >
				<input type="hidden" name="SHARE_SERVICE_ID" id="SHARE_SERVICE_ID" value="<%= request.getParameter("svrId")%>" />
				<input type="hidden" name="SVR_CODE" id="SVR_CODE" value="<%= request.getParameter("old_service_no")%>" />
				<input type="hidden" name="SERVICE_NO" id="SERVICE_NO" value="<%= request.getParameter("old_service_no")%>" />
				<input type="hidden" name="colNameEnArray" id="colNameEnArray" />
				<input type="hidden" name="colNameCnArray" id="colNameCnArray" />
				<input type="hidden" name="KSJLS" id="KSJLS" value="1"/>
				<input type="hidden" name="JSJLS" id="JSJLS" value="5"/>
				<input type="hidden" name="LOGIN_NAME" id="LOGIN_NAME" value="ssjbuser"/>
				<input type="hidden" name="PASSWORD" id="PASSWORD" value="111111"/>
				
				<table cellspacing="0" cellpadding="0" class="button_table">
					<tr>
						<td class="btn_left"></td>
						<td>
							<div class="btn_query"  onclick="visitWs()"></div>
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
	 <iframe id='wstest'  name='wstest' scrolling='yes' frameborder=0 width=100% height=100% style="display:block;background-color:#F4FAFB;"></iframe>
	</td></tr>
	
	 </table>
	 <center><table cellspacing="0" cellpadding="0" class="button_table">
	<tr>
		<td class="btn_left"></td>
		<td>
			<!-- <input type="button" class="menu" value=" 关 闭 " onclick="func_record_goBack();" /> -->
			<div class="btn_cancel" onclick="func_record_goBack()"></div>
			</td>
		<td class="btn_right"></td>
	</tr></table></center>
</freeze:body>	
</freeze:html>
