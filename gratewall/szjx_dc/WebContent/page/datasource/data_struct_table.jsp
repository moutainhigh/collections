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
var tables=false;

var pkDcDataSource=null;
var oldselectedData=null;
var tree;
	function initData(res){
		pkDcDataSource=res.getAttr("pkDcDataSource");
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
			url : rootPath+'/dataSource/query_data_table.do?pkDcDataSource='+pkDcDataSource,
			callback : function(data, r, res) { 
				var data = data["data"];
				console.log(data);
				 $("#funcTree").tree({setting: setting, data: data});
				$("#funcTree").layout({
					layout:"fit"
				}); 
				tree = $.fn.zTree.getZTreeObj("tree_funcTree");
				oldselectedData=tree.getCheckedNodes(true);
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
	//判断有是否存在的数据
	function checkexitsIds(funcIds){
		var oldIds=new Array();
		$.each(oldselectedData, function(i, n){
			if(n.id!=0){
				oldIds.push(n.id);
			}
		});		
		var exitsIds = new Array();
		//判断加载的表中是否有已经加载过的 
		for(var i=0;i<funcIds.length;i++){
			for(var j=0;j<oldIds.length;j++){
				if(funcIds[i]==oldIds[j]){
					exitsIds.push(funcIds[i]);
					break;
				}
			}
		}
		return exitsIds; 

	}
	
	
	function save(){
		//防止未加载完成，再次加载
		if(!tables){
			tables=true;
		}else{
			/* jazz.info("正在加载，请稍候！！"); */
			return;
		}
		
 		var selectedData = tree.getCheckedNodes(true);
		var funcIds = new Array();
		$.each(selectedData, function(i, n){
				if(n.id!=0){
					funcIds.push(n.id);
				}
		});
		
		var treenIds=null;
		if(oldselectedData!=null){
					treenIds=checkexitsIds(funcIds);
					var names='已经加载过表 ：';
					if(treenIds.length>0){
						for(var i=0;i<treenIds.length;i++){

							var nodes = tree.getNodesByParam("id", treenIds[i], null);
							$.each(nodes, function(i, n){
								names=names+"    "+n.name;
							});
							if(i>=2){
								names=names+"...";
								break;
							}
						}
						
					}
					
					names= names+" 确定要再次加载？"
					if(treenIds.length>0){
						jazz.confirm(names,
							    function(){
					   			saveTablesAndFields(funcIds);
							   	},
							   	function(){
							   		tables=false;
							   		return;
							   	});  				
					}else{
						saveTablesAndFields(funcIds);
					}
			}else{
					saveTablesAndFields(funcIds);
			}

	
	}
	//加载表
	function saveTablesAndFields(funcIds){
		var funcIdsStr = funcIds.join(","); 
		//alert(funcIdsStr);
			var params = {
				url:rootPath+'/dataSource/saveTableAndFields.do',
				params: {
					funcIdsStr : funcIdsStr,
					pkDcDataSource:pkDcDataSource
					
				},
				callback : function(data, r, res) { 
					tables=false;
					if (res.getAttr("back") == 'success') {
						jazz.info("加载成功！！");
						//goback();
					} else {
						jazz.info("加载成功！！");
						//jazz.info("加载失败");
					}
				}
			}
			$.DataAdapter.submit(params); 		
		
	}

	function goback(){
		leave();
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
<div vtype="panel" width="100%" height="100%" titledisplay="false" title="表装载" name="panel"  showborder="false">
			<div id="funcTree" name="funcTree" class="ztree" showborder="true" ></div>
	   		<div  name="formpanel" vtype="formpanel" showborder="false" titledisplay="false"  >			
				<div id="toolbar" name="toolbar" vtype="toolbar" align="center">
					<div id="btn1" name="btn1" vtype="button" text="加载"
						icon="../../../style/default/images/save.png"align="center" defaultview="1" click="save()"></div>
					<div id="btn2" name="btn2" vtype="button" text="返 回"
						icon="../../../style/default/images/fh.png" align="center" defaultview="1" click="goback()"></div>
				</div>
			</div>	
</div>    
</body>

</html>