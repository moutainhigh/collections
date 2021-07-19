<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="<%=request.getContextPath()%>/static/script/jquery-1.8.3.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jquery.blockUI.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/static/script/jazz.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/jbxx/detial.css" />
<style type="text/css">
td {
	text-align: center;
}

.jazz-pagearea {
	height: 0px;
}
</style>

</head>
<body>

<div></div>

<div id='reportName' name='reportName' vtype="textfield" readonly="true" label="报表名称" lreadonly='true' abelAlign="right" width="400" labelwidth="100"></div>
<div id='reportYear' name='reportYear' vtype="textfield" readonly="true" label="报表年份" lreadonly='true' abelAlign="right" width="400" labelwidth="100"></div>
<div id='term'   name='term' vtype="textfield"  readonly="true"  label="报表期别" labelAlign="right" width="400" labelwidth="100"></div>
<div id="funcTree" name="funcTree" class="ztree"></div>


<div id="toolbar" name="toolbar" vtype="toolbar" location="bottom" align="center">
	<div id="btn1"  name="btn1" vtype="button" text="保存" icon="../../../style/default/images/save.png" click="save()"></div>
	<div id="btn2"  name="btn2" vtype="button" text="返回" icon="../../../style/default/images/fh.png" click="back()"></div>
</div>
</body>
</html>
<script>
var reportId = '${param.id}';
var roleId='', roleName='',upRoleId='',upRoleName='';
var tree;
var setting = {
	data: {
		simpleData: {
			enable: true,
			idKey: "id",
			pIdKey: "pid"
		}
	},
	check: { 
           enable: true, 
           chkboxType:{ "Y":'s', "N":'s'} 
       }, 
	callback: {
		onClick: treeOnClick
		//onRightClick: zTreeOnRightClick 右键菜单功能
	}
};
$(function(){
	$('div[name="reportId"]').hiddenfield('setValue', reportId);
	//get report info
	$.ajax({
	    url:rootPath+'/cognosController/readAllCognos.do?id=' + reportId,
		type:"post",
		dataType : 'json',
		success:function(data){
			if(data.data){
				//console.log(data.data[0].data.rows[0]);
				data = data.data[0].data.rows[0];
				$('div[name="reportName"]').textfield('setValue',data.reportname);
				$('div[name="term"]').textfield('setValue',data.reportparamters);
				$('div[name="reportYear"]').textfield('setValue',data.year);
				refreshFuncTree();
			}
		}
	});
});
function back() {
	parent.winEdit.window("close");
}

function refreshFuncTree(){
	roleId='', roleName='',upRoleId='',upRoleName='';
	var params = {
		url : rootPath+'/reportDistribute/getReportDeptTree.do?reportId=' + reportId,	
		callback : function(data, r, res) { 
			var data = data["data"];
			//console.log(data);
			tree = $.fn.zTree.init($("#funcTree"), setting, data);
			tree.expandAll(false);
		}
	}
	$.DataAdapter.submit(params);
}

function save(){
	var selectedData = tree.getCheckedNodes(true);
	var funcIds = new Array();
	//console.log(selectedData);
	$.each(selectedData, function(i, n){
		if(n.id=="system"){
			
		}else{
			funcIds.push(n.id);
		}
	});
	var funcIdsStr = funcIds.join(",");
	//alert(funcIdsStr);
		 var params = {
			url:rootPath+'/reportDistribute/updateDeptsTree.do?reportId='+reportId + '&deptIds=' + funcIdsStr,
			params: {
				funcIdsStr : funcIdsStr
			},
			callback : function(data, r, res) {
			
				/* $("#maincontent").panel("option", "frameurl",
		  				rootPath+'/reportDistribute/updateDeptsTree.do?reportId='+reportId + '&deptIds=' + funcIdsStr); */
				jazz.info("保存成功");
				back();
			}
		}
		$.DataAdapter.submit(params); 
}

function treeOnClick(event, treeId, treeNode){
	/* $("#info").text("");
	RoleId = treeNode.id;
	rID=RoleId;
	if(rID=="0"){
		var type="onlyShow";
  		$("#maincontent").panel("option", "frameurl",
 				rootPath+'/auth/getRoleFuncHtml.do?pkposition='+pkposition+'&type='+type+'&pkRole='+pkRole); 
		return;
	}
	RoleName = RoleId=="0"? "" : treeNode.name ;
	var pnode = treeNode.getParentNode();
	upRoleId = pnode.id;
	upRoleName = upRoleId=="0"? "无" : pnode.name;
	//alert("角色主键:"+RoleId+"角色名称："+RoleName+"系统名称："+upRoleId+"系统id："+upRoleName);
	pkRole =RoleId;
	type="update";
		$("#maincontent").panel("option", "frameurl",
				rootPath+'/auth/getRoleFuncHtml.do?pkSysIntegration='+upRoleId+'&type='+type+'&pkRole='+pkRole); */
}
</script>
