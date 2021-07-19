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
var pkFunction=null;
var type=null;//类型（新增  修改 查看 ）
var pkRole=null;
function initData(res){
	type=res.getAttr("type");
	pkSysIntegration = res.getAttr("pkSysIntegration");
	pkRole=res.getAttr("pkRole");
}
function refreshFuncTree(){//刷新树
	window.parent.refreshFuncTree();
}
function save() {
	$("#pkSysIntegration").hiddenfield("setValue", pkSysIntegration);
	if(type=="add"){//新增
	    var aa = $("#formpanel").formpanel('getValue'); 	    
 		var params = {
			url:rootPath+'/auth/saveSmRole.do',
			components : [ 'formpanel' ],
			callback : function(data, r, res) {
				if (res.getAttr("back") == 'success') {
					var pkRole=$('div[name="roleCode"]').textfield('getValue');
					window.parent.setEnable(pkRole,pkSysIntegration,type);
					window.parent.setPosiEnable(pkRole,pkSysIntegration,type);
					refreshFuncTree();
					window.parent.clearId();
					jazz.info("角色新增成功");
				} else {
					
					jazz.info("该角色代码已存在，请重新创建"); 
		
				}
			}
		};
		$.DataAdapter.submit(params); 
		
	}else if(type=="update"){//更新
		var params = {
			url:rootPath+'/auth/updateSmRole.do',
			components : [ 'formpanel' ],
			callback : function(data, r, res) {
				if (res.getAttr("back") == 'success') {
					jazz.info("修改成功");
				} else {
/* 					jazz.info("菜单名已存在，保存失败"); */
					jazz.info("修改失败");
				}
			}
		};
		$.DataAdapter.submit(params);		
	}
}


function reset() {
	var id=	$("#tab_name", parent.document).attr("id");
	alert(id);
	
	
	//$("#formpanel").formpanel('reset');
}
function setRole(){
		$('div[name="formpanel"]').formpanel('option', 'dataurl',rootPath+
				'/auth/setRole.do?pkSysIntegration='+pkSysIntegration+'&type='+type);
			$('div[name="formpanel"]').formpanel('reload');	
			$('div[name="systemName"]').textfield('option', 'disabled', true);
			//$('div[name="pkRole"]').textfield('option', 'disabled', true);
			$('div[name="roleCode"]').textfield('option', 'disabled', true);

}

$(function(){
	$("#formpanel .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});

	if(type=="add"){
		setRole();
		 $('div[name="modifierName"]').hide();
		 $('div[name="modifierTime"]').hide();
	}else if(type=="update"){
		$('#formpanel').formpanel('option', 'dataurl',rootPath+
				'/auth/getRoleSysNameByPK.do?pkRole='+pkRole);
		$('#formpanel').formpanel('reload');	
		$('div[name="systemName"]').textfield('option', 'disabled', true);
		//$('div[name="pkRole"]').textfield('option', 'disabled', true);
		$('div[name="roleCode"]').textfield('option', 'disabled', true);
	}else if(type=="see"){
		$('#formpanel').formpanel('option', 'dataurl',rootPath+
				'/auth/getRoleSysNameByPK.do?pkRole='+pkRole);
		$('#formpanel').formpanel('reload');	
		$('div[name="systemName"]').textfield('option', 'disabled', true);
		//$('div[name="pkRole"]').textfield('option', 'disabled', true);
		$('div[name="roleCode"]').textfield('option', 'disabled', true);	
        $("#formpanel").formpanel('option', 'readonly', true); 
        $("#toolbar").hide();		
	}else if(type=="onlyShow"){
		$("#btn1").button('option','disabled',true);
		$("#btn1").button("hide");//隐藏
	}
	if(jazz.util.getParameter("queryone")=="yes"){
        $("#formpanel").formpanel('option', 'readonly', true); 
        $("#toolbar").hide();
	}	
	$("#pkSysIntegration").hiddenfield("setValue", pkSysIntegration);
	
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
				
				<div name='pkSysIntegration' id="pkSysIntegration" vtype="hiddenfield" label="系统集成表主键"
					 labelAlign="right"></div>
				<div name='roleName' id="gnMc" vtype="textfield" label="角色名称"   labelAlign="right"   maxlength="50"
					rule="must" width="90%"></div>
				<div name='systemName' id="systemName" vtype="textfield" label="所属系统"   labelAlign="right"
					 width="90%"></div>					
				<div  name='pkRole' vtype="hiddenfield" label="角色表主键PKROLE"
					labelAlign="right" width="90%"
					dataurl=''></div>						
				<div id='roleCode' name='roleCode' vtype="textfield" label="角色代码"
					labelAlign="right" rule="must" width="90%"
					dataurl=''></div>									
				<div id='roleType' name='roleType' vtype="comboxfield" label="角色类型"
					labelAlign="right" rule="must" width="90%"
					dataurl="<%=contextpath%>/auth/findRoleType.do"></div>	
				<div id='roleState' name='roleState' vtype="comboxfield" label="角色状态"
					labelAlign="right" rule="must" width="90%"
					dataurl="<%=contextpath%>/auth/findRoleState.do"></div>							
				<div id='orderNo' name='orderNo' vtype="textfield" label="序号" rule="number" msg="必须为数字"   maxlength="31"
					labelAlign="right" width="90%"
					dataurl=''></div>
 				<div id='createrId' name='createrId' vtype="hiddenfield" label="创建人ID" disabled=true
					labelAlign="right"  width="90%" 
					dataurl=''></div>
 				<div name='createrName' vtype="textfield" label="创建人" disabled=true
					labelAlign="right"  width="90%" 
				></div>
 				<div id='createrTime' name='createrTime' vtype="textfield" label="创建时间" disabled=true
					labelAlign="right"  width="90%" 
					dataurl=''></div>
					
 				<div  name='modifierId' vtype="hiddenfield" label="修改人ID" disabled=true
					labelAlign="right"  width="90%" 
					dataurl=''></div>					
 				<div name='modifierName' vtype="textfield" label="修改人" disabled=true
					labelAlign="right"  width="90%" 
				></div>
 				<div id='modifierTime' name='modifierTime' vtype="textfield" label="修改时间" disabled=true
					labelAlign="right"  width="90%" 
					dataurl=''></div>
					
        		<div name='remarks' colspan="2" rowspan="1"   maxlength="1000"
             	vtype="textareafield" label="备注" labelalign="right" width="90%" height="85">             	
             	</div> 										
				<div id="toolbar" name="toolbar" vtype="toolbar" align="center" height="80">
					<div id="btn1" name="btn1" vtype="button" text="保 存"
						icon="../../../style/default/images/save.png"align="center" defaultview="1" click="save()"></div>
					<div id="btn2" name="btn2" vtype="button" text="返 回"
						icon="../../../style/default/images/fh.png" align="center" defaultview="1" click="goback()"></div>
				</div>
			</div>
  
  
</body>

</html>