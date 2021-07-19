$(function(){
	initDataGrid();  
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//隐藏父窗口滚动条
	}
});

function initDataGrid(){
	$('#knowledgeGrid').datagrid({
	    height: 567,
	    width:"99%",
	    url: '../../knowledge/manageAction.do?method=queryKnowledgeList',
	    method: 'POST',
	    idField: 'docId',
	    striped: true,
	    title:"查询列表",
	    fitColumns: true,
	    singleSelect: false,
	    selectOnCheck:true,
	    checkOnSelect:false,
	    rownumbers: true,
	    pagination: true,
	    nowrap: true,
	    showFooter: true,
	    columns: [[
	        { field: 'ck',checkbox:true},
   	        { field: 'docId', title: 'id', hidden:true},
   	        { field: 'title', title: '标题', width:"38%", align: 'left',fixed:true},
   	        { field: 'catName', title: '属性类别', width: "15%", align: 'left',fixed:true},
   	        { field: 'issuer', title: '发布单位', width: "15%", align: 'left',fixed:true},
   	        { field: 'state', title: '状态', width: "11%", align: 'left',fixed:true},
	        { field: 'option', title: '操作', width: 170, align: 'center',fixed:true,formatter:optformat}
	    ]],
	    toolbar: [
	      { 
	        text: '添加', 
	        iconCls: 'icon-add', 
	        handler: function() { 
	            window.location.href="knowledge_input.html";
	            return false;
	        } 
	      },"-",
//	      { 
//	          text: '修改', 
//	          iconCls: 'icon-edit', 
//	          handler: function() { 
//	              var rows=$('#knowledgeGrid').datagrid("getChecked");
//	              if(rows.length==0){
//	            	  $.messager.alert("提示信息","请选择要修改的数据。","info");
//	              }else if(rows.length>1){
//	            	  $.messager.alert("提示信息","一次只能修改一条数据。","info");
//	              }else{
//	            	  var docId=rows[0].docId;
//	            	  edit_knowledge(docId);
//	              }
//	          } 
//	       },
	       "-",
		      { 
		          text: '删除', 
		          iconCls: 'icon-remove', 
		          handler: delBatchKnowledge
		       }
	    ]
	});
	//$(".datagrid").css("width",802);

	setPagination();
}

//渲染操作列
function optformat(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var html="";
	if(rowdata.state=="未提交"||rowdata.state=="退回修改"){
		html="<a href='javascript:void(0)' onclick=knowledge_submit('"+doc_id+"')>提交</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">详情</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=\"javascript:edit_knowledge('"+doc_id+"');return false;\">修改</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=delete_knowledge('"+doc_id+"')>删除</a>&nbsp;&nbsp;";
	}else{
		html="<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">详情</a>&nbsp;&nbsp;";
	}
	return html;
}

//知识详情
function knowledge_detail(doc_id){
	window.location.href="knowledge_detail.html?doc_id="+doc_id;
}


function batchSubmitKnowledge(){
	var rows = $('#knowledgeGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("提示信息","请选择要提交的数据。","info");
		return;
	}
	$.messager.confirm('确定', '提交后不可修改，确定要 提交这些数据吗？', function(confirm) {
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
					state  : doc_state.unchecked
				},
				success : function(data) {
					$.messager.alert("提示信息", "操作成功。", "info", function() {
						$("#knowledgeGrid").datagrid("reload");
					});
				}
			});
		}
	});
}
//知识提交
function knowledge_submit(doc_id){
	$.messager.confirm('确定','提交后不可修改，确定提交吗？',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=updateKnowledgeState",
	    	   type:"post",
	    	   data : {
	    		   docId : doc_id,
				   state  : doc_state.unchecked
				},
	    	   success:function(data){
	    		   $.messager.alert("提示信息","操作成功。","info",function(){
	    			   $("#knowledgeGrid").datagrid("reload");
	    		   });
	    	   }
	       });
	    }    
	}); 
}

//修改知识
function edit_knowledge(doc_id){
	window.location.href="knowledge_edit.html?doc_id="+doc_id;
}


//删除知识
function delete_knowledge(doc_id){
	$.messager.confirm('确认','您确认想要删除记录吗？',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=delKnowledge",
	    	   type:"post",
	    	   data:{docId:doc_id},
	    	   success:function(data){
	    		   $.messager.alert("提示信息","删除成功。","info",function(){
	    			   $("#knowledgeGrid").datagrid("reload");
	    		   });
	    	   }
	       });
	    }    
	}); 
}

//批量删除知识
function delBatchKnowledge() {
	var rows = $('#knowledgeGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("提示信息","请选择要删除的数据。","info");
		return;
	}
	var isCandel=true;
	var docIds = "";
	$.each(rows, function(i, row) {
		docIds += row.docId + ",";
		if(rows.state!="未提交"){
			isCandel=false;
		}
	});
	if(!isCandel){
		$.messager.alert("提示信息","所选记录中包含已经提交的记录，只能删除未提交的记录。","info");
		return;
	}
	docIds = docIds.substring(0, docIds.length - 1);
	$.messager.confirm('确认', '您确认想要删除记录吗？', function(confirm) {
		if (confirm) {
			$.ajax({
				url : "../../knowledge/manageAction.do?method=delBatchKnowledge",
				type : "post",
				data : {
					docIds : docIds
				},
				success : function(data) {
					$.messager.alert("提示信息", "删除成功。", "info", function() {
						$("#knowledgeGrid").datagrid("reload");
					});
				}
			});
		}
	});
}

//设置分页信息
function setPagination() {
	var p = $('#knowledgeGrid').datagrid('getPager'); 
    $(p).pagination({ 
        pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [10,15,20],//可以设置每页记录条数的列表 
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
    });
}

//查询知识
function search_knowledge(){
	var params=deal_query_params();
	//console.log($("#knowledge_query_form").serialize());
	
	$('#knowledgeGrid').datagrid("reload",params);
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



