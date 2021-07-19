<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单维护</title>
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
    overflow-y: auto ;
    overflow-x: hidden !important;
}
#toolbar{
    position:fixed;

}
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
#xx{width:50px;height:100%;border-left:1px solid #C5D7DA}
</style>
<script src="<%=contextpath%>/static/script/jazz.js" type="text/javascript"></script>
<script>
var pkSysIntegration=null;
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
/*  	edit: { //拖拽功能 
		drag: {
			autoExpandTrigger: true,
			prev: dropPrev,
			inner: dropInner,
			next: dropNext,
			isCopy: false,
			isMove: true
		},
		enable: true,
		showRemoveBtn: false,
		showRenameBtn: false
	},  */ 
	callback: {
		onClick: treeOnClick,
		beforeDrag: beforeDrag,
		beforeDrop: beforeDrop,
		beforeDragOpen: beforeDragOpen,
		onDrag: onDrag,
		onDrop: onDrop,
		onExpand: onExpand		
	}
};

var gnId, //当前选中的功能代码
	gnMc, //当前选中的功能名称
	sjgnId, //当前选中功能的上级功能ID
	sjgnMc, //当前选中功能的上级功能名称
	functype,//功能类型  判断是包还是什么
	gnUrl;// 当前功能URL
function treeOnClick(event, treeId, treeNode){
	gnId = treeNode.id;
	gnMc = gnId=="system"? "" : treeNode.name ;
	if(gnId==null||gnId=="system"||gnId==""){
		var type="onlyShow";
		$("#maincontent").panel("option", "frameurl",
				rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type); 
		return;
	}
	var pnode = treeNode.getParentNode();
	sjgnId = pnode.id;
	sjgnMc = sjgnId=="system"? "无" : pnode.name;
	functype= treeNode.functype==null? "无":treeNode.functype;
	gnUrl=treeNode.gnurl==null? "wu":treeNode.gnurl;
	//alert("功能主键:"+gnPk+"功能代码："+gnId+"功能名称："+gnMc+"上级功能代码："+sjgnId+"上级功能名称："+sjgnMc+"功能url:"+gnUrl);
		$("#maincontent").panel("option", "frameurl",
			rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&pkFunction='+gnId); 
}
function refreshright(sys,id){
	$("#maincontent").panel("option", "frameurl",
			rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+sys+'&pkFunction='+id); 
}
var tree;
$(document).ready(function(){
	$(".jazz-column-element").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
	addButton();
	refreshFuncTree();
	var type="onlyShow";
		$("#maincontent").panel("option", "frameurl",
				rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type); 
});

function urlDatarender(event,obj) {
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		var htm = '<a href="javascript:void(0);" onclick="editUrl(\''+data[i]["urlId"]+'\')">'
			+ "编辑" + '</a>';
		data[i]["edit"] = htm; 
	}
	return data;
}



function refreshFuncTree(){
	gnId='', gnMc='',sjgnId='',sjgnMc='',gnUrl='',functype='';
	var params = {
		url : rootPath+'/auth/queryFuncTree.do?pkSysIntegration='+pkSysIntegration,
		callback : function(data, r, res) { 
			//alert(JSON.stringify(data.data));
			var data = data["data"];
		
			//console.log(JSON.stringify(data)); 
			tree = $.fn.zTree.init($("#funcTree"), setting, data);
			$("#callbackTrigger").bind("change", {}, setTrigger);
			tree.expandAll(true);
		}
	}
	$.DataAdapter.submit(params);
}


function addFunc(){//新增功能

	if(gnId==null||gnId=="system"||gnId==""){
 		$("#maincontent").panel("option", "frameurl",
 				rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration); 
	}else{
		if(functype=="1"){
			
	 		$("#maincontent").panel("option", "frameurl",
	 				rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&superFunction='+gnId); 			
		}else{
			
			
	 		$("#maincontent").panel("option", "frameurl",
	 				rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&superFunction='+gnId); 	
		}
	}
}

function addFunc1(){//旧的增加功能 没用 
	
	var selectedData = tree.getCheckedNodes(true);
	if(selectedData==null || selectedData.length==0){
 		$("#maincontent").panel("option", "frameurl",
 				rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration); 
	}else if(selectedData.length==1){
		$.each(selectedData, function(i, n){
			gnUrl=n.gnurl;
			functype=n.functype;
			gnId = n.id;
			if(gnId=="system"){//系统
		 		$("#maincontent").panel("option", "frameurl",
		 				rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration); 					
			}
			if(functype=="1"){
			
		 		$("#maincontent").panel("option", "frameurl",
		 				rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&superFunction='+gnId); 			
			}else{
				jazz.info("请选择一个包 ，且只能选择一个");
			}
			//alert("jieshu");
		});
	}else{
		jazz.info("请选择一个包 ，且只能选择一个");
	}

}
function deleteFunc(){
	if(gnId==""){
		jazz.info("请选择要删除的项");
	}else if(gnId=="system"){
		jazz.confirm("确定删除全部功能？该操作不能恢复", function(){
			var params = {
				url : rootPath+'/auth/deleteAllFunc.do',
				params: {
					pkSysIntegration : pkSysIntegration
				},
				callback : function(data, r, res) { 
					jazz.info("删除成功");
					var type="onlyShow";
			 		$("#maincontent").panel("option", "frameurl",
			 				rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type); 
					refreshFuncTree();
					
				}
			}
			$.DataAdapter.submit(params);
		}, function(){})				
	}else{
		jazz.confirm("确定删除所选功能？", function(){
			var params = {
				url : rootPath+'/auth/deleteFunc1.do',
				params: {
					gnId : gnId,
					pkSysIntegration:pkSysIntegration
				},
				callback : function(data, r, res) { 
					jazz.info("删除成功");
					var type="onlyShow";
			 		$("#maincontent").panel("option", "frameurl",
			 				rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type); 
					refreshFuncTree();
					
				}
			}
			$.DataAdapter.submit(params);
		}, function(){})		
	}
}

function deleteFunc1(){//
	var selectedData = tree.getCheckedNodes(true);
	if(selectedData==null || selectedData.length==0){
		jazz.warn("请选择要删除的功能");
		return;
	}else if(selectedData.length==1){
		$.each(selectedData, function(i, n){
			gnUrl=n.gnurl;
			gnPk=n.pkfunc;
			gnId = n.id;
			if(gnId=="system"){
				jazz.warn("请选择要删除的功能");
				return;		
			}
		});
	}
	var funcIds = new Array();
	$.each(selectedData, function(i, n){
		if(n.id=="system"){//排除头部
			
		}else{
			funcIds.push(n.id);
		}
		
	});
	var funcIdsStr = funcIds.join(","); 

	jazz.confirm("确定删除所选功能？", function(){
		var params = {
			url : rootPath+'/auth/deleteFunc.do',
			params: {
				funcIdsStr : funcIdsStr
			},
			callback : function(data, r, res) { 
				jazz.info("删除成功");
				var type="onlyShow";
		 		$("#maincontent").panel("option", "frameurl",
		 				rootPath+'/auth/getMenu_crudHtml.do?pkSysIntegration='+pkSysIntegration+'&type='+type); 
				refreshFuncTree();
				
			}
		}
		$.DataAdapter.submit(params);
	}, function(){})
}
function goback(){//返回           /auth/getMenu_manageHtml.do
	$("iframe", parent.document).attr("src", rootPath+'/page/integeration/xt_list.jsp');
	//$("iframe", parent.document).attr("src", rootPath+'/auth/getMenu_manageHtml.do');
	//$("#column_id1").panel("option", "frameurl",rootPath+'/page/auth/menu_manage.jsp');
	
}	
function addButton(){ 
    $('#menufunc').panel('addTitleButton', [{ 
        id: "id_1", 
        align: "right", 
        icon: "../static/script/JAZZ-UI/lib/themes/gongshang/images/panel-close.png",        
        click:function(e,ui){ 
        	 $('#menufunc').panel('close'); 
        	goback(); 
        } 
     }]);  
} 
function dropPrev(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i=0,l=curDragNodes.length; i<l; i++) {
			var curPNode = curDragNodes[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
function dropInner(treeId, nodes, targetNode) {
	if (targetNode && targetNode.dropInner === false) {
		return false;
	} else {
		for (var i=0,l=curDragNodes.length; i<l; i++) {
			if (!targetNode && curDragNodes[i].dropRoot === false) {
				return false;
			} else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
				return false;
			}
		}
	}
	return true;
}
function dropNext(treeId, nodes, targetNode) {
	var pNode = targetNode.getParentNode();
	if (pNode && pNode.dropInner === false) {
		return false;
	} else {
		for (var i=0,l=curDragNodes.length; i<l; i++) {
			var curPNode = curDragNodes[i].getParentNode();
			if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
				return false;
			}
		}
	}
	return true;
}

var log, className = "dark", curDragNodes, autoExpandNode;
function beforeDrag(treeId, treeNodes) {
	className = (className === "dark" ? "":"dark");
	for (var i=0,l=treeNodes.length; i<l; i++) {
		if (treeNodes[i].drag === false) {
			curDragNodes = null;
			return false;
		} else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
			curDragNodes = null;
			return false;
		}
	}
	curDragNodes = treeNodes;
	//alert(JSON.stringify(curDragNodes)); 
	return true;
}
function beforeDragOpen(treeId, treeNode) {
	autoExpandNode = treeNode;
	return true;
}
function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
	//className = (className === "dark" ? "":"dark");
	
	//moveType  String
	//指定移动到目标节点的相对位置
	//"inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
	
	//alert(JSON.stringify(treeNodes)); 
/*  	alert("树名："+treeId+"-上一级-"+targetNode.name);
	for (var i=0,l=treeNodes.length; i<l; i++) {
		alert(treeNodes[i].name);
	}  */
	

	//只能移动为子节点
	if(!(moveType=="inner")){
		return false;
	}else{
		alert("当前 name： "+treeNodes[0].name+"   上级name："+targetNode.name);
		return changeFuncCode(pkSysIntegration,targetNode.id,treeNodes[0].id);
		
	}

	

}
function changeFuncCode(cursysy,shangjicaidan,gndaima) {//修改菜单 改变层次码 和功能代码
     //cursysy 当前修改系统   shangjicaidan 要改变的菜单，gndaima 功能代码
    alert("jinru");
		var params = {
				url :rootPath+'/auth/ChangeTreelevelCode.do?pkSysIntegration='+cursysy+'&superFuncCode='+shangjicaidan+'&pkFunction='+gndaima,
				callback : function(data, r, res) {
					if(res.getAttr("status")=='success'){
						return true;
					}else{
						return false;
					}
				}
			};
			$.DataAdapter.submit(params);			
	
}
function onDrag(event, treeId, treeNodes) {

	className = (className === "dark" ? "":"dark");
}
function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
	className = (className === "dark" ? "":"dark");
}
function onExpand(event, treeId, treeNode) {
	if (treeNode === autoExpandNode) {
		className = (className === "dark" ? "":"dark");
		showLog("[ "+getTime()+" onExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
	}
}

function showLog(str) {

}
function getTime() {
	var now= new Date(),
	h=now.getHours(),
	m=now.getMinutes(),
	s=now.getSeconds(),
	ms=now.getMilliseconds();
	return (h+":"+m+":"+s+ " " +ms);
}

function setTrigger() {
	var zTree = $.fn.zTree.getZTreeObj("funcTree");
	zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
}

</script>
</head>
<body>
<div id="menufunc" vtype="panel" width="100%" height="100%" layout="fit" titledisplay="true" title="菜单维护" bgcolor="white" showborder="true">
<div id="table" vtype="panel" height="100%" layout="column" showborder="false" 
			layoutconfig="{columnwidth: ['25%','31','20','*']}"> 
        <div id="demo" >
        	 <div id="div1" style="height:20px;"></div>
        	<div id="toolbar" name="toolbar" vtype="toolbar" align="left">
					<div id="btn1" name="btn1" vtype="button" align="center" defaultview="1" text="新增"
						icon="../../../style/default/images/save.png" click="addFunc()"></div>
					<div id="btn2" name="btn2" vtype="button" text="删除"
						icon="../../../style/default/images/fh.png" align="center" defaultview="1"  click="deleteFunc()"></div>
				</div>
			 <div id="div1" style="height:20px;"></div>	
			<div id="funcTree" name="funcTree" class="ztree"  ></div>
		</div>
    	
        <div id="xx"> 
	     </div>
	     <div></div>
    	<div>
	      	<div style="width:100%;height:100%;">
	 	        <div vtype="panel"  height="100%" width="100%" showtabclose="true"  height="100%" titledisplay="false" title="功能维护" name="maincontent" id="maincontent" showborder="false">
		   		</div>    
		   		
		   	</div>  	
		</div>
    </div>
 </div> 
</body>

</html>