var pkId;

function initPostTree(){
	$('#qxlist').tree({
		setting: {				
			view: {
				dblClickExpand: false
			},
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: function onClick(e, treeId, treeNode) {
					if(treeNode.id == 0){
						getPostData();
					}
				},
				onExpand: function onClick(e, treeId, treeNode) {
					if(treeNode.id == 0){
						getPostData();
					}
				}
			}
		}
	});	
}

function getPostData(){
	var params = {
		url:rootPath+"/integration/findByListPost.do",
		params: {},
		callback : function(data,obj,res) {
			if(!!data["data"]){
				var fisrtnode = {"id":"1", "pId":"0", "name":"深圳市市场监督管理委" , "type":"0" ,"open":"true"};
				data["data"].push(fisrtnode);
				$('#qxlist').tree('loadData', data["data"], null, 'static');
			}
		}
	};
	$.DataAdapter.submit(params);
}

function save(){
	var treeObj = $.fn.zTree.getZTreeObj("tree_qxlist");
	var nodes = treeObj.getCheckedNodes(true);
	var nodelist = [];
	$.each(nodes,function(i,node){
		if(node.type == 8){
			nodelist.push(node.id);
		}
	});
	
	if(nodelist.length == 0){
		alert("请先选着到岗位");
		return;
	}
	var params = {
		url:rootPath+"/integration/saveListPost.do", 
		params: {"pkPositions":nodelist.join(","),"pkRole":pkId},
		callback : function(data,obj,res) {
			leave();
		}
	};
	$.DataAdapter.submit(params);
}

function leave(){
	window.top.$("#frame_maincontent").get(0).contentWindow.leave();
}

function initData(res){
	initPostTree();
	var pkRole = res.getAttr("pkRole");
	pkId = pkRole;
	var pkPositions = res.getAttr("pkPositions");
	if(!!pkPositions){
		getPostData();
		var treeObj = $.fn.zTree.getZTreeObj("tree_qxlist");
		var codetimeout = new Object();
		var codeArray = pkPositions.split(',');
		var m = 1;
		$.each(codeArray,function(i,code){
			var node = null;
			if(!!code){
				codetimeout["code_"+code] = setInterval(function(x){
					return function(){
	 					node = treeObj.getNodeByParam('id', x, null);
	 					m++;
	 					if(!!node || 300*m > 6000){
	 						clearInterval(codetimeout["code_"+x]);
	 						treeObj.checkNode(node, false, true);
	 						treeObj.checkNode(node, true, true);
	 					}
					}
				}(code), 300);
			}
		});
	}else{
		var data = [
     		{id:0, pId:1, name:"深圳市市场监督管理委","chkDisabled":true},
     		{id:2, pId:0, name:""}
    	];
    	$('#qxlist').tree('loadData', data, null, 'static');
	}
	$("#qxlist").layout({
		layout:"fit"
	});
}