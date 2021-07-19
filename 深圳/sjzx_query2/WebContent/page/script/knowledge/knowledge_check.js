$(function(){
	initUncheckedDataGrid();
	initCheckedDataGrid();
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//隐藏父窗口滚动条
	}
});

function initCheckedDataGrid(){
	$('#knowledgeCheckedGrid').datagrid({
	    height: 547,
	    width:"100%",
	    url: '../../knowledge/manageAction.do?method=queryKnowledgeList&state=2',
	    method: 'POST',
	    idField: 'docId',
	    striped: true,
	    title:"知识列表",
	    fitColumns: true,
	    singleSelect: false,
	    selectOnCheck:true,
	    checkOnSelect:false,
	    rownumbers: true,
	    pagination: true,
	    pageSize: 10,
        pageList: [10,15,20],
	    nowrap: true,
	    showFooter: true,
	    columns: [[
	   	        { field: 'ck',checkbox:true},
	   	        { field: 'docId', title: 'id', hidden:true},
	   	        { field: 'title', title: '标题', width:"45%", align: 'left',fixed:true},
	   	        { field: 'catName', title: '属性类别', width: "16%", align: 'left',fixed:true},
	   	        { field: 'issuer', title: '发布单位', width: "18%", align: 'left',fixed:true},
	   	        { field: 'option', title: '操作', width: 120, align: 'center',fixed:true,formatter:optformat_checked}
	   	    ]]
	});
}
function initUncheckedDataGrid(){
	$('#knowledgeUncheckedGrid').datagrid({
	    height: 547,
	    width:"100%",
	    url: '../../knowledge/manageAction.do?method=queryKnowledgeList&state=1',
	    method: 'POST',
	    idField: 'docId',
	    striped: true,
	    title:"知识列表",
	    fitColumns: true,
	    singleSelect: false,
	    selectOnCheck:true,
	    checkOnSelect:false,
	    rownumbers: true,
	    pagination: true,
	    pageSize: 10,
        pageList: [10,15,20],
	    nowrap: true,
	    showFooter: true,
	    columns: [[
	        { field: 'ck',checkbox:true},
	        { field: 'docId', title: 'id', hidden:true},
	        { field: 'title', title: '标题', width:"45%", align: 'left',fixed:true},
	        { field: 'catName', title: '属性类别', width: "16%", align: 'left',fixed:true},
	        { field: 'issuer', title: '发布单位', width: "16%", align: 'left',fixed:true},
	        { field: 'option', title: '操作', width: 210, align: 'center',fixed:true,formatter:optformat_unchecked}
	    ]],
	    toolbar: [
	      { 
	        text: '退回修改', 
	        //iconCls: 'icon-add', 
	        handler: backmodifyBatchKnowledge 
	      },"-",
	      { 
	          text: '审核通过', 
	          //iconCls: 'icon-edit', 
	          handler: checkBatchKnowledge
	       }
	    ]
	});
}
//渲染操作列
function optformat_unchecked(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var html="<a href='javascript:void(0)' onclick=check_knowledge('"+doc_id+"')>审核通过</a>&nbsp;&nbsp;"+
	 		 "<a href='javascript:void(0)' onclick=\"edit_knowledge('"+doc_id+"','"+doc_state.unchecked+"');return false;\">修订</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=backmodify_knowledge('"+doc_id+"')>退回修改</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">详情</a>";
	return html;
}

//渲染操作列
function optformat_checked(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var html="<a href='javascript:void(0)' onclick=\"edit_knowledge('"+doc_id+"','"+doc_state.checked+"');return false;\">修订</a>&nbsp;&nbsp;"+
	 	     "<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">详情</a>";
	 		 
	return html;
}

//知识详情
function knowledge_detail(doc_id){
	window.location.href="knowledge_detail.html?doc_id="+doc_id;
}

//修改知识
function edit_knowledge(doc_id,state){
	window.location.href="knowledge_edit.html?from=chk&doc_id="+doc_id+"&state="+state;
}


//知识审核通过
function check_knowledge(doc_id){
	$.messager.confirm('确定','确定审核通过吗？',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=updateKnowledgeState",
	    	   type:"post",
	    	   data : {
	    		   docId : doc_id,
				   state  : doc_state.checked
				},
	    	   success:function(data){
	    		   $.messager.alert("提示信息","操作成功。","info",function(){
	    			   $("#knowledgeUncheckedGrid").datagrid("reload");
	    			   $("#knowledgeCheckedGrid").datagrid("reload");
	    		   });
	    	   }
	       });
	    }    
	}); 
}

//批量审核知识
function checkBatchKnowledge() {
	var rows = $('#knowledgeUncheckedGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("提示信息","请选择要审核的数据。","info");
		return;
	}
	$.messager.confirm('确定', '您确定要 审核这些数据吗？', function(confirm) {
		if (confirm) {
			var docIds = "";
			$.each(rows, function(i, row) {
				docIds += row.docId + ",";
			});
			docIds = docIds.substring(0, docIds.length - 1);
			$.ajax({
				url : "../../knowledge/manageAction.do?method=updateBatchKnowledgeState",
				type : "post",
				data : {
					docIds : docIds,
					state  : doc_state.checked
				},
				success : function(data) {
					$.messager.alert("提示信息", "操作成功。", "info", function() {
						$("#knowledgeUncheckedGrid").datagrid("reload");
						$("#knowledgeCheckedGrid").datagrid("reload");
					});
				}
			});
		}
	});
}


//知识退回修改
function backmodify_knowledge(doc_id){
	$.messager.confirm('确定','确定退回修改吗？',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=updateKnowledgeState",
	    	   type:"post",
    		   data : {
    			   docId : doc_id,
				   state : doc_state.backmodify
				},
	    	   success:function(data){
	    		   $.messager.alert("提示信息","操作成功。","info",function(){
	    			   $("#knowledgeUncheckedGrid").datagrid("reload");
	    			   $("#knowledgeCheckedGrid").datagrid("reload");
	    		   });
	    	   }
	       });
	    }    
	}); 
}

//批量退回修改
function backmodifyBatchKnowledge() {
	var rows = $('#knowledgeUncheckedGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("提示信息","请选择要退回修改的数据。","info");
		return;
	}
	$.messager.confirm('确定', '您确定要 退回这些数据吗？', function(confirm) {
		if (confirm) {
			var docIds = "";
			$.each(rows, function(i, row) {
				docIds += row.docId + ",";
			});
			docIds = docIds.substring(0, docIds.length - 1);
			$.ajax({
				url : "../../knowledge/manageAction.do?method=updateBatchKnowledgeState",
				type : "post",
				data : {
					docIds : docIds,
					state  : doc_state.backmodify
				},
				success : function(data) {
					$.messager.alert("提示信息", "操作成功。", "info", function() {
						$("#knowledgeUncheckedGrid").datagrid("reload");
						$("#knowledgeCheckedGrid").datagrid("reload");
					});
				}
			});
		}
	});
}

//查询知识
function search_knowledge(){
	var tab = $('#knowledge_tab').tabs('getSelected');
	var index = $('#knowledge_tab').tabs('getTabIndex',tab);
	
	var params=deal_query_params();
	if (index == 0) {
		$('#knowledgeUncheckedGrid').datagrid("reload",params);
	} else {
		$('#knowledgeCheckedGrid').datagrid("reload",params);
	}
}
//处理查询参数
function deal_query_params(){
	var data=$("#knowledge_query_form").serializeArray();
	var jsonData={};
	$.each(data,function(){
		jsonData[this.name]=encodeURI(this.value);
	});
	return jsonData;
}

//重置
function reset_search(){
	//清空表单
	$("#knowledge_query_form").form("reset");
	//清空属性类别、登记事项ID隐藏域的值
	$("#busiCode").val("");
	$("#categoryCode").val("");
	//清空树勾选项
	reset_zTree("zTree_categoryCode,zTree_busiCode");
}



