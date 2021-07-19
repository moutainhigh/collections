$(function(){
	initDataGrid();  
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//隐藏父窗口滚动条
	}
});

function initDataGrid(){
	$('#myknowledgeGrid').datagrid({
	    height: 567,
	    width:"99%",
	    url: '../../knowledge/manageAction.do?method=queryMyknowledgeList',
	    method: 'POST',
	    idField: 'id',
	    striped: true,
	    title:"我的知识列表",
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
	        { field: 'id', title: '主键id', hidden:true},
   	        { field: 'docId', title: '知识id', hidden:true},
   	        { field: 'title', title: '标题', width:"35%", align: 'left',fixed:true},
   	        { field: 'catName', title: '属性类别', width: "15%", align: 'left',fixed:true},
   	        { field: 'issuer', title: '发布单位', width: "15%", align: 'left',fixed:true},
   	        { field: 'source', title: '来源', width: "8%", align: 'left',fixed:true,formatter:sourceformat},
   	        { field: 'recPerson', title: '推荐人', width: "10%", align: 'left',fixed:true},
	        { field: 'option', title: '操作', width: 130, align: 'center',fixed:true,formatter:optformat}
	    ]],
	    toolbar: [{ 
      		          text: '删除', 
      		          iconCls: 'icon-remove', 
      		          handler: delBatchKnowledge
      		      }]
	    
	});
	//$(".datagrid").css("width",802);

	setPagination();
}




//渲染操作列
function optformat(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var id=rowdata.id;
	var source=rowdata.source;
	var html="<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">详情</a>&nbsp;&nbsp;"+
			 "<a href='javascript:void(0)' onclick=delete_knowledge('"+id+"','"+source+"')>删除</a>";
	return html;
}

//渲染操作列
function sourceformat(field,rowdata,rowindex){
	var source=rowdata.source;
	if(source=="fav"){
		return "知识收藏";
	}else if(source=="rec"){
		return "知识推荐";
	}
}

//删除知识
function delete_knowledge(id,type){
	$.messager.confirm('确认','您确认想要删除记录吗？',function(confirm){    
	    if (confirm){    
	       $.ajax({
	    	   url:"../../knowledge/manageAction.do?method=delMyknowledge",
	    	   type:"post",
	    	   data:{id:id,type:type},
	    	   success:function(data){
	    		   $.messager.alert("提示信息","删除成功。","info",function(){
	    			   $("#myknowledgeGrid").datagrid("reload");
	    		   });
	    	   }
	       });
	    }    
	}); 
}

//批量删除知识
function delBatchKnowledge() {
	var rows = $('#myknowledgeGrid').datagrid("getChecked");
	if(rows.length==0){
		$.messager.alert("提示信息","请选择要删除的数据。","info");
		return;
	}
	$.messager.confirm('确认', '您确认想要删除记录吗？', function(confirm) {
		if (confirm) {
			var favIds = "";
			var recIds = "";
			$.each(rows, function(i, row) {
				if(row.source=="fav"){ 			//来源为知识收藏
					favIds += row.id + ",";
				}else if(row.source=="rec"){	//来源为知识推荐
					recIds += row.id + ",";
				}
				
			});
			favIds = favIds.substring(0, favIds.length - 1);
			recIds = recIds.substring(0, recIds.length - 1);
			$.ajax({
				url : "../../knowledge/manageAction.do?method=delBatchMyknowledge",
				type : "post",
				data : {
					favIds : favIds,
					recIds : recIds
				},
				success : function(data) {
					$.messager.alert("提示信息", "删除成功。", "info", function() {
						$("#myknowledgeGrid").datagrid("reload");
					});
				}
			});
		}
	});
}
//知识详情
function knowledge_detail(doc_id){
	window.location.href="knowledge_detail.html?doc_id="+doc_id;
}


//设置分页信息
function setPagination() {
	var p = $('#myknowledgeGrid').datagrid('getPager'); 
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
	
	$('#myknowledgeGrid').datagrid("reload",params);
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


