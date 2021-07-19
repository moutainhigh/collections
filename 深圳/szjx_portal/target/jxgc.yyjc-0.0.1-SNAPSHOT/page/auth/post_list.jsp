<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>岗位角色映射关系</title>
<%
	String contextpath = request.getContextPath();
%>
<style type="text/css">
.title_posi {
    color: #666666;
    font-family: simsun;
    font-size: 12px;
    font-weight: normal;
    line-height: 30px;
    padding-left: 15px;
}
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
     background: #F8F9FB;
    padding:4px 0;

}
#xx{width:50px;height:100%;border-left:1px solid #C5D7DA}
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
div#rMenuNode {
    background-color:#555555;
    text-align: left;
    padding:2px;
    width:80px;
    position:absolute;
    display:none;
}
div#rMenuNode ul {
    margin:1px 0;
    padding:0 5px;
    cursor: pointer;
    list-style: none outside none;
    background-color:#DFDFDF;
    display:none;
}
div#rMenuNode ul li {
    margin:0;
    padding:2px 0;
}
</style>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script>
	var pkposition=null;
	var pkpos=null;
	var type=null;
	var pkRole =null;
	function initData(res){
		pkposition=res.getAttr("pkposition");
		pkpos=res.getAttr("pkposition");
	}
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
	
    
	var roleId, //当前选中的功能代码
		roleName, //当前选中的功能名称
		upRoleId, //当前选中功能的上级功能ID
		upRoleName; //当前选中功能的上级功能名称
	var rID;
	function clearId(){
		rID='';
	}
	function treeOnClick(event, treeId, treeNode){
		$("#info").text("");
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
  				rootPath+'/auth/getRoleFuncHtml.do?pkSysIntegration='+upRoleId+'&type='+type+'&pkRole='+pkRole);
	}
	function refreshright(sys,id){
		pkRole =id;
		type="update";
		$("#maincontent").panel("option", "frameurl",
  				rootPath+'/auth/getPost_crudHtml.do?pkposition='+pkposition+'&type='+type+'&pkRole='+id);
				//rootPath+'/auth/getPost_crudHtml.do?');
	}		
	var tree;
	var reTree;
	$(document).ready(function(){
		addButton();
		$(".jazz-column-element").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
		var params = {
				url : rootPath+'/auth/getPositionName.do?pkposition=' + pkposition,	
				callback : function(data, r, res) { 
					$("#showPosition").text("岗位名称：" + res.getAttr("showPosition"));
				}
			}
			$.DataAdapter.submit(params);
		refreshFuncTree();
		
		//reloadTree();  
		    
	
	});

	
	function refreshFuncTree(){
		roleId='', roleName='',upRoleId='',upRoleName='';
		var params = {
			url : rootPath+'/auth/queryPositionTree.do?pkposition=' + pkposition,	
			callback : function(data, r, res) { 
				var data = data["data"];
				tree = $.fn.zTree.init($("#funcTree"), setting, data);
				tree.expandAll(false);
			}
		}
		$.DataAdapter.submit(params);
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
			}, function(){});				
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
			}, function(){});		
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
	function save(){
		debugger;
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
			var params = {
				url:rootPath+'/auth/savePositionRolefunc.do?pkRole='+pkRole+'&pkposition='+pkposition,
				params: {
					funcIdsStr : funcIdsStr
				},
				callback : function(data, r, res) {
				
					$("#maincontent").panel("option", "frameurl",
			  				rootPath+'/auth/getPosiCurrRoleHtml.do?pkposition='+pkpos);
					jazz.info("保存成功")
				
					//$("#maincontent").panel("option", "frameurl",
			  				//rootPath+'/auth/getRoleFuncHtml.do?pkSysIntegration='+upRoleId+'&type='+type+'&pkRole='+pkRole);
/* 					jazz.info("保存成功");
					$("#info").text("查看已保存系统与角色映射信息关系");
					var data = data["data"];
					Tree = $.fn.zTree.init($("#reTree"), setting, data);
					Tree.expandAll(true); */
				}
			}
			$.DataAdapter.submit(params);

	}

	
	//鼠标右键事件-创建右键菜单  
    function zTreeOnRightClick(event, treeId, treeNode) {
    	roleId = treeNode.id;
    	sysId = treeNode.pid;
    	if(treeNode.pid != null){
    		showRMenu("node", event.clientX, event.clientY);
    	}
    	else{
    		showRMenu("root", event.clientX, event.clientY);
    	}
    	     
    }
	
	//显示右键菜单  
    function showRMenu(type, x, y) { 
    	$("#rMenuNode ul").show();
		if(type == "node"){
        	$("#add_role").hide();
        	$("#rMenuNode").css({"top":y+"px", "left":x+"px", "display":"block"});
		}
		else if(type == "root"){
			$("#view_role").hide();
			$("#add_sys").hide();
        	$("#rMenuNode").css({"top":y+"px", "left":x+"px", "display":"block"});
		}
		//在当前页面绑定 鼠标事件
        $(document).bind("mousedown", onBodyMouseDown); 
    }
	
    //事件触发 隐藏菜单
    function onBodyMouseDown(event) {
    	//comsole.log(event.target.id);
    	//$("#rMenuNode").hide();
        //if (!(event.target.id == "rMenuNode" || $(event.target).parents("#rMenuNode").length > 0)) {
        	//$("#rMenuNode").hide();
        //}
    }
    //隐藏右键菜单  
    function hideRMenu() {  
    	if(rMenu){
            rMenu.css({
                "visibility" : "hidden"
            });
        }
        //取消绑定
        $(document).unbind("mousedown", onBodyMouseDown);
    } 
	  
    function reloadTree() {  
        hideRMenu();  
        //zTree = $("#funcTree").zTree(setting, treeNodes);  
    }
    
	function addRole(){
		$("#rMenuNode").hide();
		type="add";
  		$("#maincontent").panel("option", "frameurl",
  				rootPath+'/auth/getRole_posiHtml.do?pkSysIntegration='+roleId+'&type='+type+'&pkRole='+roleId);  	
	}
	
    function viewRole(){
    	$("#rMenuNode").hide();
    	type="update";
  		$("#maincontent").panel("option", "frameurl",
  				rootPath+'/auth/getRole_posiHtml.do?pkSysIntegration='+sysId+'&type='+type+'&pkRole='+roleId);    	
    }
    
    function delRole(){
    	$("#rMenuNode").hide();
    	jazz.confirm("确定删除所选角色？", function(){
			var params = {
				url : rootPath+'/auth/deleteRole.do?',
				params: {
					pkSysIntegration:sysId,
					pkRole:roleId
				},
				callback : function(data, r, res) { 
					jazz.info("删除成功"); 
					refreshFuncTree();
					
				}
			}
			$.DataAdapter.submit(params);
		}, function(){});
    }
</script>
</head>
<body>
<div class="title_nav">当前位置：权限管理 > <span>岗位管理</span></span></div>
<div id="rolelist" vtype="panel" width="100%" height="100%" layout="fit" titledisplay="false" title="系统角色关系表" bgcolor="white" showborder="true">
	<div id="table" height="100%" vtype="panel" layout="column" showborder="false" 
						layoutconfig="{columnwidth: ['40%','31','20','*']}"> 
        <div id="demo" >
          	<!-- <div id="div1" style="height:5px;"></div> -->
        	<div id="toolbar" name="toolbar" vtype="toolbar" align="left">
					<div id="btn1" name="btn1" vtype="button" align="center" defaultview="1" text="保存"
						icon="../../../style/default/images/save.png" click="save()"></div>
				</div>
			 <div id="div1" style="height:20px;"></div>
			 
  		<div name="showPosition" style="margin-top:6px;" class="title_posi" id="showPosition"> </div>
 	<div id="rMenuNode">  
 		<li>  
 			<ul id="add_role" onclick="addRole()"><li>增加角色</li></ul>  
 			<ul id="view_role" onclick="viewRole()"><li>查看角色</li></ul>  
 			<ul id="add_sys" onclick="delRole()"><li>删除角色</li></ul>   
 		</li>  
 	</div> 		
 				
			<div id="funcTree" name="funcTree" class="ztree">
			</div>
		
		</div>
    	    	
        <div id="xx"></div>
        <div></div>
    	<div>
    		
 	        <div vtype="panel"  width="100%" showtabclose="true"  height="100%" titledisplay="false" title="功能维护" name="maincontent" id="maincontent" showborder="false">
 	        	<div id ="info"></div>
 	        	<div id="reTree" name="reTree" class="ztree">
				</div>
	   		</div>      	
		</div>
    </div>
</div>  

</body>

</html>