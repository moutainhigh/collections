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
var pkSysIntegration;
var type=null;//类型（新增  修改 查看 ）
var pkRole=null;
function initData(res){
	type=res.getAttr("type");
	pkSysIntegration = res.getAttr("pkSysIntegration");
	pkRole=res.getAttr("pkRole");

}
$(function(){
	if(type==null||type==""){
		//错误的传参--应该直接关闭
	}else if(type=="add"){
		//$("#maincontent2").panel("option", "frameurl",rootPath+'/page/auth/menu_manage.jsp');
		$("#maincontent").panel("option", "frameurl",rootPath+'/auth/getAddSmRoleHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type);
		$('#tab_name').tabpanel('disable','1');//设置角色权限分配不可用
		$('#tab_name').tabpanel('disable','2');//设置角色岗位映射不可用		
	}else if(type=="update"||type=="see"){
		$("#maincontent").panel("option", "frameurl",rootPath+'/auth/getAddSmRoleHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type+'&pkRole='+pkRole);
		setEnable(pkRole,pkSysIntegration,type);
		setPosiEnable(pkRole,pkSysIntegration,type);
	}else if(type=="onlyShow"){
		$("#maincontent").panel("option", "frameurl",rootPath+'/auth/getAddSmRoleHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type);
		$('#tab_name').tabpanel('disable','1');//设置角色权限分配不可用
		$('#tab_name').tabpanel('disable','2');//设置角色岗位映射不可用		
	}

});
function setEnable(pkRole1,pksys,type){
	$('#tab_name').tabpanel('enable','1');//设置角色权限分配可用
	$("#maincontent2").panel("option", "frameurl",rootPath+'/auth/getRoleFuncHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type+'&pkRole='+pkRole1);
	$('#tab_name').tabpanel('enable','2');//设置角色岗位映射可用		
}
function setPosiEnable(pkRole1,pksys,type){
	$('#tab_name').tabpanel('enable','1');//设置角色权限分配可用
	$("#maincontent3").panel("option", "frameurl",rootPath+'/auth/getRolePosiHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type+'&pkRole='+pkRole1);
	$('#tab_name').tabpanel('enable','2');//设置角色岗位映射可用			
}
function closewin(){
	window.parent.queryRole();
	parent.win1.window("close"); 
}
function goback(){
	window.parent.goback();
}
function refreshFuncTree(){//刷新树
	window.parent.refreshFuncTree();
}
function clearId(){
	window.parent.clearId();//清除ID
}
function refreshright(sys,id){
	window.parent.refreshright(sys,id)
}
</script>
</head>
<body>
	<div name="tab_name" vtype="tabpanel"	showborder="false" overflowtype="2" tabtitlewidth="130"  width="100%" height="100%" orientation="top" id="tab_name">    
	    <ul>    
	        <li><a href="#tab1">角色基本信息</a></li> 
	        <li><a href="#tab1">角色权限分配</a></li> 
	        <li><a href="#tab1">角色岗位映射</a></li>    
	    </ul>    
	    <div>
	    	<div style="width:100%;height:100%;">
		        <div vtype="panel"  width="100%" showtabclose="false"  height="100%" titledisplay="false" title="角色基本信息" name="maincontent" id="maincontent" showborder="false">
		   		</div>   
	    	</div>
	    	<div style="width:100%;height:100%;">
		        <div vtype="panel"  width="100%" showtabclose="false"  height="100%" titledisplay="false" title="角色权限分配" name="maincontent2" id="maincontent2" showborder="false">
		   		</div>   
	    	</div>
	    	<div style="width:100%;height:100%;">
		        <div vtype="panel"  width="100%" showtabclose="false" height="100%" titledisplay="false" title="角色权限分配" name="maincontent3" id="maincontent3" showborder="false">
		   		</div>  	   			   		 
	    	</div>    
	    </div>    
	</div>
  
</body>

</html>