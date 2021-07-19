<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%
	String contextpath = request.getContextPath();
%>
<style type="text/css">
.jazz-column-element {
    border: 0 none;
    float: left;
    height: 100%;
    margin: 0;
    padding: 0;
    overflow-y: auto !important;
    overflow-x: hidden !important;
}
#toolbar{
    position:fixed;

}
#xx{width:50px;height:100%;border-right:1px dashed blue}
.ztree li span.button.ico_open {/* 树打开状态  有子节点的时候*/
    background: rgba(0, 0, 0, 0) url("../static/images/treeicon/tree_folderopen.gif") no-repeat scroll center center !important;
    height: 30px;
    width: 20px;
}
.ztree li span.button.ico_close {/* 树关闭状态 父类节点状况 */
    background: rgba(0, 0, 0, 0) url("../static/images/treeicon/tree_folder.gif") no-repeat scroll center center !important;
    height: 30px;
    width: 20px;
}
.ztree li span.button.ico_docu {/* 没有节点的状况 */
    background: rgba(0, 0, 0, 0) url("../static/images/treeicon/tree_page.gif") no-repeat scroll center center !important;
    height: 30px;
    width: 20px;
}
</style>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script>
var pkSysIntegration=null;
var type=null;
var pkRole =null;
	function initData(res){
		pkSysIntegration=res.getAttr("pkSysIntegration");
	}
	var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid"
			}
		},
		callback: {
			onClick: treeOnClick
		}
	};
	
	var roleId, //当前选中的功能代码
		roleName, //当前选中的功能名称
		upRoleId, //当前选中功能的上级功能ID
		upRoleName; //当前选中功能的上级功能名称
	var rID;
	function clearId(){
		rID='';
	}
	function treeOnClick(event, treeId, treeNode){
		RoleId = treeNode.id;
		rID=RoleId;
		if(rID=="0"){
			var type="onlyShow";
	  		$("#maincontent").panel("option", "frameurl",
	 				rootPath+'/auth/getRole_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type+'&pkRole='+pkRole); 
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
  				rootPath+'/auth/getRole_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type+'&pkRole='+pkRole);
	}
	var tree;
	$(document).ready(function(){
		addButton();
		$(".jazz-column-element").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});

		refreshFuncTree();
		var type="onlyShow";
  		$("#maincontent").panel("option", "frameurl",
 				rootPath+'/auth/getRole_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type+'&pkRole='+pkRole); 
	});

	
	function refreshFuncTree(){
		roleId='', roleName='',upRoleId='',upRoleName='';
		var params = {
			url : rootPath+'/auth/queryRoleTree.do?pkSysIntegration='+pkSysIntegration,
			callback : function(data, r, res) { 
				var data = data["data"];
				tree = $.fn.zTree.init($("#funcTree"), setting, data);
				tree.expandAll(true);
			}
		}
		$.DataAdapter.submit(params);
	}
	

	

	function addRole(){
		type="add";
  		$("#maincontent").panel("option", "frameurl",
  				rootPath+'/auth/getRole_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type+'&pkRole='+pkRole);  	
	}
	function deleteFunc(){
		//alert(rID);
		//roleId='', roleName='',upRoleId='',upRoleName='';
		if(rID==""){
			jazz.info("请选择要删除的角色");
		}else if(rID=="0"){
			jazz.confirm("确定删除当前全部角色？该操作不能恢复", function(){
				var params = {
					url : rootPath+'/auth/deleteAllRole.do',
					params: {
						pkSysIntegration : pkSysIntegration
					},
					callback : function(data, r, res) { 
						jazz.info("删除成功");
						var type="onlyShow";
				  		$("#maincontent").panel("option", "frameurl",
				 				rootPath+'/auth/getRole_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type+'&pkRole='+pkRole); 
						refreshFuncTree();
						clearId();
					}
				}
				$.DataAdapter.submit(params);
			}, function(){})				
		}else{
			jazz.confirm("确定删除所选功能？", function(){
				var params = {
					url : rootPath+'/auth/deletRole1.do',
					params: {
						funcIdsStr : rID
					},
					callback : function(data, r, res) { 
						jazz.info("删除成功");
						var type="onlyShow";
				  		$("#maincontent").panel("option", "frameurl",
				 				rootPath+'/auth/getRole_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type+'&pkRole='+pkRole); 
						refreshFuncTree();
						clearId();
					}
				}
				$.DataAdapter.submit(params);
			}, function(){})		
		}
	}

	function deleteFunc1(){
		var selectedData = tree.getCheckedNodes(true);
		if(selectedData==null || selectedData.length==0){
			jazz.warn("请选择要删除的功能");
			return;
		}else if(selectedData.length==1){
			$.each(selectedData, function(i, n){
				var gnId = n.id;
				if(gnId=="0"){
					jazz.warn("请选择要删除的功能");
					return;		
				}
			});
		}
		var funcIds = new Array();
		$.each(selectedData, function(i, n){
			funcIds.push(n.id);
		});
		var funcIdsStr = funcIds.join(","); 
		jazz.confirm("确定删除所选角色？", function(){
			var params = {
				url : rootPath+'/auth/deletRole1.do',
				params: {
					funcIdsStr : funcIdsStr
				},
				callback : function(data, r, res) { 
					jazz.info("删除成功");
					var type="onlyShow";
			  		$("#maincontent").panel("option", "frameurl",
			 				rootPath+'/auth/getRole_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type+'&pkRole='+pkRole); 
					refreshFuncTree();
				}
			}
			$.DataAdapter.submit(params);
		}, function(){})
	}
	function goback(){//返回           /auth/getMenu_manageHtml.do
		$("iframe", parent.document).attr("src", rootPath+'/auth/getRole_manageHtml.do');
		//$("#column_id1").panel("option", "frameurl",rootPath+'/page/auth/menu_manage.jsp');
		
	}	
	function addButton(){ 
	    $('#rolelist').panel('addTitleButton', [{ 
	        id: "id_1", 
	        align: "right", 
	        icon: "../static/script/JAZZ-UI/lib/themes/gongshang/images/panel-close.png",        
	        click:function(e,ui){ 
	        	 $('#rolelist').panel('close'); 
	        	goback(); 
	        } 
	     }]);  
	} 	
</script>
</head>
<body>
<div id="rolelist" vtype="panel" width="100%" height="100%" layout="fit" titledisplay="true" title="角色维护" bgcolor="white" showborder="true">
	<div id="table" height="100%" vtype="panel" layout="column" showborder="false" 
						layoutconfig="{columnwidth: ['20%','31','20','*']}"> 
        <div id="demo" >
          	<div id="div1" style="height:5px;"></div>
        	<div id="toolbar" name="toolbar" vtype="toolbar" align="left">
					<div id="btn1" name="btn1" vtype="button" align="center" defaultview="1" text="新增"
						icon="../../../style/default/images/save.png" click="addRole()"></div>
					<div id="btn2" name="btn2" vtype="button" text="删除"
						icon="../../../style/default/images/fh.png" align="center" defaultview="1"  click="deleteFunc()"></div>
				</div>
			 <div id="div1" style="height:20px;"></div>		
			<div id="funcTree" name="funcTree" class="ztree"  ></div>
		</div>
    	    	
        <div id="xx"> 
        </div>
        <div></div>
    	<div >
 	        <div vtype="panel"  width="100%" showtabclose="true"  height="100%" titledisplay="false" title="功能维护" name="maincontent" id="maincontent" showborder="false">
	   		</div>      	
		</div>
    </div>
</div>  
</body>

</html>