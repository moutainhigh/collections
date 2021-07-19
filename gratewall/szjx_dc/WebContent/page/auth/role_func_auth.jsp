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
var pkRole=null;//就是rolecode
var pkSysIntegration=null;
var type=null;
var isSys=null;//判断是否管理员
var tree;
	function initData(res){
		pkRole=res.getAttr("pkRole");
		pkSysIntegration=res.getAttr("pkSysIntegration");
		type=res.getAttr("type");
		isSys=res.getAttr("isSys");
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
			onCheck: iswarn,
			beforeCheck:warn
		}
	};
	var oldnodeslength=0;
	var old=0;
 	function warn(){
 		old =old+1;
 		if(old>1){
 			return;
 		}
		nodes = tree.getCheckedNodes(true);
		oldnodeslength=nodes.length;
	} 
	var no=0;//计算点击次数
	function iswarn(){
		no=no+1;

		nodes = tree.getCheckedNodes(true);
		if(nodes.length>=1){
			if(no>1){
				return;
			}
			if(isSys=="Y"){
				 jazz.warn("你改变了当前管理员功能, 当保存后只有你选择的功能（当你选择全部功能或0个功能时，该角色拥有所有功能）", function(){
					 
				 });

			}	
		}else{	
			if(isSys=="Y"){
				if(oldnodeslength==0){
					
				}else{		
					 jazz.warn("该角色，当保存后拥有该系统所有功能", function(){
						 
					 });
				}
			}	
		}

	}
	$(document).ready(function(){
		$("#panel .jazz-panel-content").css({"overflow":"none","overflow-y":"auto","overflow-x":"hidden"});
		refreshFuncTree();
		if(type==null||type==""){
			//错误的传参--应该直接关闭
		}else if(type=="see"){
			$("#formpanel").formpanel('option', 'readonly', true); 
			$("#toolbar").hide();
		}
	});
	function refreshFuncTree(){
		var params = {
			url : rootPath+'/auth/queryAuthFunc.do?pkRole='+pkRole+'&pkSysIntegration='+pkSysIntegration,
			callback : function(data, r, res) { 
				var data = data["data"];
/* 					tree = $.fn.zTree.init($("#funcTree"), setting, data);
					tree.expandAll(true); */
					
 				 $("#funcTree").tree({setting: setting, data: data});
					$("#funcTree").layout({
						layout:"fit"
					}); 
					tree = $.fn.zTree.getZTreeObj("tree_funcTree");
					tree.expandAll(true);
			}
		}
		$.DataAdapter.submit(params);
	}	
	function save(){
		var selectedData = tree.getCheckedNodes(true);
		var funcIds = new Array();
		$.each(selectedData, function(i, n){
			if(n.id=="system"){
				
			}else{
				funcIds.push(n.id);
			}
		});
		var funcIdsStr = funcIds.join(","); 
			var params = {
				url:rootPath+'/auth/saveSmRolefunc.do?pkRole='+pkRole+'&pkSysIntegration='+pkSysIntegration,
				params: {
					funcIdsStr : funcIdsStr
				},
				callback : function(data, r, res) { 
					jazz.info("保存成功");
				}
			}
			$.DataAdapter.submit(params);

	}

	function goback(){
		//window.parent.closewin();
		window.parent.goback();
	}
	
</script>
</head>
<body>
<div id="panel" vtype="panel" width="100%" height="100%" titledisplay="false" title="权限分配" name="panel"  showborder="false">
			<div id="funcTree" name="funcTree" class="ztree"></div>
			
	   		<div id="formpanel" name="formpanel" vtype="formpanel" showborder="false" titledisplay="false" >			
				<div id="toolbar" name="toolbar" vtype="toolbar" align="center">
					<div id="btn1" name="btn1" vtype="button" text="保 存"
						icon="../../../style/default/images/save.png"align="center" defaultview="1" click="save()"></div>
					<div id="btn2" name="btn2" vtype="button" text="返 回"
						icon="../../../style/default/images/fh.png" align="center" defaultview="1" click="goback()"></div>
				</div>
			</div>	
</div>  

</body>

</html>