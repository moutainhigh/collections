
var search_keyword=getUrlParam("keyword")||"";



$(function(){
	initQueryCondition();
	initDataGrid();  
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//隐藏父窗口滚动条
	}
});


/**
 * 初始化查询条件
 */
function initQueryCondition(){
	$("#text_categoryCode").val(search_keyword);
	var categoryValue = "";
	$.ajax({
		url:"../../knowledge/manageAction.do?method=queryCategoryList",
		type:"post",
		async:false,
		success:function(data){
			if(data){
				var category = data.catCode;
				if(category!=null && category.length>0){
					for(var i=0;i<category.length;i++){
						if(search_keyword==category[i].name){
							categoryValue = category[i].id;
							break;
						}
					}
				}
			}
		}
	});
	
	$("#categoryCode").val(categoryValue);
}


function initDataGrid(){
	
	
	$('#knowledgeGrid').datagrid({
	    height: 567,
	    width:"99%",
	    url: '../../knowledge/manageAction.do?method=queryKnowledgeTypeList',
	    method: 'POST',
	    queryParams:{cat_name:encodeURI(search_keyword)},
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
   	        { field: 'title', title: '标题', width:"60%", align: 'left',fixed:true},
   	        { field: 'catName', title: '属性类别', width: "10%", align: 'left',fixed:true},
   	        { field: 'issuer', title: '发布单位', width: "13%", align: 'left',fixed:true},
   	        { field: 'state', title: '状态', width: "8%", align: 'left',fixed:true},
	        { field: 'option', title: '操作', width: "8%", align: 'center',fixed:true,formatter:optformat}
	    ]]
	});

	setPagination();
}

//渲染操作列
function optformat(field,rowdata,rowindex){
	var doc_id=rowdata.docId;
	var html="";
	
	html="<a href='javascript:void(0)' onclick=\"knowledge_detail('"+doc_id+"');return false;\">详情</a>&nbsp;&nbsp;";
	
	return html;
}

//知识详情
function knowledge_detail(doc_id){
	//window.location.href="knowledge_detail.html?doc_id="+doc_id;
	window.location.href="knowledge_search_detail.html?doc_id="+doc_id+"&keyword=";
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



