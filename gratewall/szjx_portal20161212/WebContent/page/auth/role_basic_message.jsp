<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String contextpath = request.getContextPath();
%>

<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script>
var appCode=null;
var roleCode=null;
function initData(res){
	appCode = res.getAttr("appCode");
	roleCode = res.getAttr("roleCode");
}
function refreshFuncTree(){//刷新树
	window.parent.refreshFuncTree();
}


function setRole(){
		$('div[name="formpanel"]').formpanel('option', 'dataurl',rootPath+
				'/auth/setRole.do?pkSysIntegration='+pkSysIntegration+'&type='+type);
			$('div[name="formpanel"]').formpanel('reload');	
			$('div[name="systemName"]').textfield('option', 'disabled', true);
			//$('div[name="pkRole"]').textfield('option', 'disabled', true);
			$('div[name="roleCode"]').textfield('option', 'disabled', false);

}

$(function(){
	$("#formpanel .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
		$('#formpanel').formpanel('option', 'dataurl',rootPath+
				'/auth/getShowAddSmRoleHtml.do?appCode='+appCode+'&roleCode='+roleCode);
		$('#formpanel').formpanel('reload');
		$('div[name="systemName"]').textfield('option', 'disabled', true);
		//$('div[name="pkRole"]').textfield('option', 'disabled', true);
		$('div[name="roleCode"]').textfield('option', 'disabled', true);
	if(jazz.util.getParameter("queryone")=="yes"){
        $("#formpanel").formpanel('option', 'readonly', true); 
        $("#toolbar").hide();
	}	
	
	//getSystemName();
});
function goback(){
	//window.parent.closewin();
	window.parent.goback();
}
</script>
</head>
<body>
	   		<div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" 
				height="100%" width="100%" layout="table" layoutconfig="{border:false,columnwidth:['50%','*']}">
				<div name='rolecode' id="roleCode" vtype="textfield" label="角色编码"   labelAlign="right"   maxlength="50"
					rule="must" width="90%" disabled="true"></div>
				<div name='rolename' id="roleName" vtype="textfield" label="角色名称"   labelAlign="right"   maxlength="50"
					rule="must" width="90%" disabled="true"></div>
				<div name='appcode' id="appCode" vtype="textfield" label="系统编码"   labelAlign="right"
					 width="90%" disabled="true"></div>					
				<div  name='timestamp' id='timeStamp' vtype="textfield" label="创建时间"
					labelAlign="right" width="90%"
					dataurl='' disabled="true"></div>						
				<div  name='systemname' id='timeStamp' vtype="textfield" label="系统名称"
					labelAlign="right" width="90%"
					dataurl='' disabled="true"></div>						
<!-- 				<div id='systemname' name='systemName' vtype="textfield" label="系统名称" -->
<!-- 					labelAlign="right" rule="must" width="90%" -->
<!-- 					dataurl=''></div>									 -->
				<div id='url' name='url' vtype="textfield" label="系统URL"
					labelAlign="right" rule="must" width="90%" disabled="true"></div>	
			</div>
  
  
</body>

</html>