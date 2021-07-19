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

var pkSmServices=null;

var tree;
	function initData(res){
		pkSmServices=res.getAttr("pkSmServices");
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
		}
	};
	$(document).ready(function(){
		refreshFuncTree();
	});
	function refreshFuncTree(){
		var params = {
			url : rootPath+'/auth/queryAuthServiceTree.do?pkSmServices='+pkSmServices,
			callback : function(data, r, res) { 
				var data = data["data"];
				 $("#funcTree").tree({setting: setting, data: data});
				$("#funcTree").layout({
					layout:"fit"
				}); 
				tree = $.fn.zTree.getZTreeObj("tree_funcTree");
					//disabledNode(); //不能选择的效果  
	
					//tree.expandAll(true); 
			}
		}
		$.DataAdapter.submit(params);
	}	
	
	function disabledNode() {//不是岗位不能选择效果
		//nodes =tree.getNodes();
		nodes = tree.getCheckedNodes(false),
		inheritParent = false, inheritChildren = false;

		for (var i=0, l=nodes.length; i<l; i++) {
			if(nodes[i].type == 8){
				
			}else{
			tree.setChkDisabled(nodes[i], true, inheritParent, inheritChildren);
			}
		}
	}
	function save(){
 		var selectedData = tree.getCheckedNodes(true);
		var funcIds = new Array();
		$.each(selectedData, function(i, n){
				if(n.id!=0){
					funcIds.push(n.id);
				}
		});
		var funcIdsStr = funcIds.join(","); 
		//alert(funcIdsStr);
			var params = {
				url:rootPath+'/auth/saveServiceAuth.do?',
				params: {
					funcIdsStr : funcIdsStr,
					pkSmServices:pkSmServices
					
				},
				callback : function(data, r, res) { 
					jazz.info("保存成功");
				}
			}
			$.DataAdapter.submit(params); 
	
	}

	function goback(){
		leave();
		//parent.win.window("close"); 
	}
	function refreshService(){
		window.top.$("#frame_maincontent").get(0).contentWindow.queryMenu();
	}
	function leave(){
		window.top.$("#frame_maincontent").get(0).contentWindow.leave();
	}
</script>
</head>
<body>
<div vtype="panel" width="100%" height="100%" titledisplay="false" title="角色岗位分配" name="panel"  showborder="false">
			<div id="funcTree" name="funcTree" class="ztree" showborder="true" ></div>
	   		<div  name="formpanel" vtype="formpanel" showborder="false" titledisplay="false"  >			
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