var catcode_setting={
	data:{
		simpleData:{
			enable:true
		}
	},
	check:{
		enable:true,
		chkStyle:"radio",
		radioType: "all"
	},
	callback: {
		onCheck: zTreeOnCheck
	}
};
var busicode_setting={
	data:{
		simpleData:{
			enable:true
		}
	},
	check:{
		enable:true
	},
	callback: {
		onCheck: zTreeOnCheck
	}
};
$(function(){
	loadTree();
	bindTreeEvent();
});
//���ڵ㹴ѡ�����¼�
function zTreeOnCheck(event, treeId, treeNode){
	var el_id=$(event.target).parents("#treeDiv_categoryCode").length>0?"categoryCode":"busiCode";
	var treeObj = $.fn.zTree.getZTreeObj("zTree_"+el_id);
	var nodes = treeObj.getCheckedNodes(true);
	var checkedNodes="";
	var checkedNodeIds="";
	$.each(nodes,function(){
		var node=this;
		checkedNodes+=node.name+";";
		checkedNodeIds+=node.id+";";
	});
	if(checkedNodes){
		checkedNodes=checkedNodes.substring(0,checkedNodes.length-1);
		checkedNodeIds=checkedNodeIds.substring(0,checkedNodeIds.length-1);
	}
	if(el_id=="categoryCode"&&checkedNodes=="�ڲ�֪ͨ"){
		$("#docContent").validatebox({validType:"length[0,2000]",invalidMessage:"�ڲ�֪ͨ��֪ʶ���ݳ��Ȳ��ܳ���2000"});
	}else{
		$("#docContent").validatebox({validType:""});
	}
	$("#text_"+el_id).val(checkedNodes);
	$("#"+el_id).val(checkedNodeIds);
	if($("#text_"+el_id).hasClass("easyui-validatebox")){
		$("#text_"+el_id).validatebox("validate");
	}
	
}

//��ʾ�����˵�
function showMenu(id){
    var obj = $("#text_"+id);
    var offset = $("#text_"+id).offset();
    $("#treeDiv_"+id).css({left:offset.left+"px",top:offset.top+obj.outerHeight()+"px"}).slideDown("fast");    
}  

//����������
function loadTree(){
	$.ajax({
		url:"../../knowledge/manageAction.do?method=queryCategoryList",
		type:"post",
		success:function(data){
			if(data){
				initTree(data);
			}
		}
	});
}

//��ʼ����
function initTree(data){
	var catCodeList=data.catCode;
	var busiCodeList=data.busiCode;
	$.fn.zTree.init($("#zTree_categoryCode"), catcode_setting, catCodeList);
	$.fn.zTree.init($("#zTree_busiCode"), busicode_setting, busiCodeList);
	
	var zTree_catCode = $.fn.zTree.getZTreeObj("zTree_categoryCode");
	var zTree_busiCode = $.fn.zTree.getZTreeObj("zTree_busiCode");
	
	//չ���ڵ�
	zTree_catCode.expandAll(true);
	zTree_busiCode.expandAll(true);
	//���Է��������ڵ��ֹ��ѡ
	var cat_nodes=zTree_catCode.getNodes();
	$.each(cat_nodes,function(){
		if(this.children){
			zTree_catCode.setChkDisabled(this,true);
		}
	});
	//�Ǽ�������һ���ڵ��ֹ��ѡ
	var busi_nodes=zTree_busiCode.getNodes();
	$.each(busi_nodes,function(){
		if(this.children){
			zTree_busiCode.setChkDisabled(this,true);
		}
	});
}

//���������˵�
function hideMenu(id) {
	if(id){
		$("#treeDiv_"+id).slideUp("fast");
	}else{
		$("#treeDiv_categoryCode").slideUp("fast");
		$("#treeDiv_busiCode").slideUp("fast");
	}
}

//������Ĺ�ѡ��,֧�ֶ����
function reset_zTree(treeIds){
	var treeIdArray=treeIds.split(",");
	if(treeIdArray.length>0){
		$.each(treeIdArray,function(i,treeId){
			var zTreeObj=$.fn.zTree.getZTreeObj(treeId);
			if(zTreeObj){
				var tree_nodes = zTreeObj.getCheckedNodes(true);
				if (tree_nodes.length>0) { 
					for(var i=0;i<tree_nodes.length;i++){
						zTreeObj.checkNode(tree_nodes[i], false, true);
					}
				}
			}
		});
	}
}

/**
 * ��ѡ���ڵ�
 * @param treeId   ��id
 * @param nodeIds  �ڵ�id,����ö��Ÿ���
 * @param checked �Ƿ�ѡ
 */
function check_node(treeId,nodeIds){
	var nodeIdArray=nodeIds.split(";");
	var zTreeObj=$.fn.zTree.getZTreeObj(treeId);
	if(nodeIdArray.length>0){
		$.each(nodeIdArray,function(i,nodeId){
			var node =  zTreeObj.getNodeByParam("id", nodeId);
			if(node){
				zTreeObj.checkNode(node, true, false);
			}
		});
	}
}

//��������¼���������
function bindTreeEvent(){
	$("body").bind("mousedown", function(event){
		if (event.target.id == "text_categoryCode" ) {
	        hideMenu("busiCode");
	    }else if (event.target.id == "text_busiCode" ) {
	        hideMenu("categoryCode");
	    }else if(event.target.id != "treeDiv_busiCode"&&event.target.id != "treeDiv_categoryCode"&&$(event.target).parents("#treeDiv_categoryCode").length==0&&$(event.target).parents("#treeDiv_busiCode").length==0){
			hideMenu();
		}
	});
}