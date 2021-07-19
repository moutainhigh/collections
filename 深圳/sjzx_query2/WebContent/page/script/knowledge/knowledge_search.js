
//查询知识
var search_keyword=getUrlParam("keyword")||"";


$(function(){
	
	//设置默认查询条件为按发布时间排序
	var search_sort_type = getUrlParam("keysort") || "";

	if(search_sort_type=='2'){
		$("#search_sort").val(search_sort_type);
	}
	
	
	initSearchBox();
	initDataGrid();
//	initResultFilterPanel();
	//initDetailWindow();
	$("#search_sort").change(function(){
		var isInResultHide=$("#result_filter").hasClass("hide");
		if(isInResultHide){
			var params={keyword:search_keyword,sort:$("#search_sort").val()};
			$('#search_datagrid').datagrid("load",paramsEncodeURI(params));
		}else{
			search_inresult();
		}
		
	});
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//隐藏父窗口滚动条
	}
	$(".result-select").click(function(){
		if($("#result_filter").hasClass("hide")){
			$("#result_filter").removeClass("hide");
			/*$('#search_datagrid').datagrid("resize",{height:316});*/
		}else{
			$("#result_filter").addClass("hide");
			/*$('#search_datagrid').datagrid("resize",{height:440});*/
		}
		if($(".result-select").hasClass("show")){
			$(".result-select").removeClass("show");
		}else{
			$(".result-select").addClass("show");
		}
	});
	$('#center-layout').css('height','auto');
});

function queryHotwords(){
	$.ajax({
		url:"../../knowledge/manageAction.do?method=queryHotwords",
		type:"post",
		success:function(data){
			if(data&&data.length>0){
				$("#span_hotword_list").empty();
				$.each(data,function(){
					var hotword=this.keywords;
					$("#span_hotword_list").append("<span class='hot-word'>"+hotword+"</span>");
				});
			}else{
				var default_hotwords="<span class='hot-word'>公司设立</span>" +
						"<span class='hot-word'>公司变更</span>" +
						"<span class='hot-word'>住所</span>" +
						"<span class='hot-word'>注册资本</span>";
				$("#span_hotword_list").append(default_hotwords);
			}
		}
	});
	
	$(".hot-word").die("click").live("click",function(){
		var keyword=$(this).text();
		search_keyword=keyword;
		search_knowledge(keyword);
		$("#search_textbox").val(keyword);
	});
}


function initSearchBox(){
	$("#search_textbox").val(search_keyword);
	document.onkeydown=function(e){
		 var theEvent = e || window.event;    
	     var code = theEvent.keyCode || theEvent.which || theEvent.charCode;  
	     if (code == 13&&$("#search_textbox").is(":focus")) {  
	    	 search_knowledge();
	         return false;    
	     }    
	     return true;    
	}; 
	$("#search_textbox").focus(function(){
		if($("#search_textbox").val()=="请输入您要搜索的内容"){
			$("#search_textbox").val("");
			$("#search_textbox").css("color","#333333");
		}
	});
	$("#search_textbox").blur(function(){
		if($("#search_textbox").val()==""){
			$("#search_textbox").val("请输入您要搜索的内容");
			$("#search_textbox").css("color","#999999");
		}
	});
}
function search_knowledge(keyword){
	search_keyword=keyword?keyword:$("#search_textbox").val();
	if(search_keyword&&search_keyword!='请输入您要搜索的内容'){
		search_categoryId="";//清空分类代码
		var params={keyword:search_keyword,sort:$("#search_sort").val()};
		$('#search_datagrid').datagrid("load",paramsEncodeURI(params));
	}
}
function advance_search(){
	search_keyword=$("#search_knowledge_box").val();
	search_categoryId="";
	var params={keyword:search_keyword};
	$('#search_datagrid').datagrid("load",paramsEncodeURI(params));
}
//初始化datagrid表格
function initDataGrid(){
	$('#search_datagrid').datagrid({
	    url:"../../knowledge/searchAction.do?method=SearchByCat",
	    view:knowledge_view,
	    idField: 'docId',
	    striped: true,
	    queryParams:{keyword:encodeURI(search_keyword),categoryId:search_categoryId,sort:$("#search_sort").val()},
	    fitColumns: true,
	    showHeader:false,
	    singleSelect: true,
	    pageSize: 10,//每页显示的记录条数，默认为10 
        pageList: [10,20,30],//可以设置每页记录条数的列表 
	    pagination: true,
	    nowrap: true,
	    showFooter: true,
	    onLoadSuccess:loadCallBack
	});
}

//表格加载回调函数
function loadCallBack(data){
	
	if(data&&data.total>0){
		saveSearchLog(search_keyword);
		if(data.group){
			var count="为您找到相关结果约<font color='blue'>"+data.total+"<font>个";
			$("#result_count").html(count);
			var group_result=data.group;
			$("#result_group_count").empty();
			$.each(group_result,function(i,group){
				/*var group_html="<div class='group-count-div'><span class='group-count-icon'></span><a href='javascript:void(0)' onclick=search_by_cat(this,'"+group.categoryCode+"')>"*/
				var group_html="<div class='group-count-div'><a href='javascript:void(0)' onclick=search_by_cat(this,'"+group.categoryCode+"')>"
				+group.catName+"("+group.count+")</a></div>";
				$("#result_group_count").append(group_html);	
			});
		}
	}else{
		$("#result_count").html("没有找到相关结果");
		$("#result_group_count").empty();
	}
	queryHotwords();
}
var fileContentMap={};
//自定义视图,渲染数据航
var knowledge_view= $.extend({},$.fn.datagrid.defaults.view,{
	renderRow:function(target, fields, frozen, rowIndex, rowData){
		if(rowData.docId){
			
			fileContentMap[rowData.docId+"_"+rowIndex]=rowData;
			var title=rowData.title;
			var issueDate=rowData.issueDate;
			var issuer=rowData.issuer;
			var keywords=rowData.keywords;
			//var fileName=rowData.fileName;
			var docContent=rowData.docContent;
			
			if(docContent.length>500){
				docContent=docContent.substr(0,500);
				if(docContent.substr(docContent.length-16).indexOf("<")!=-1){ //16=<font color=red>的长度
					docContent=docContent.substr(0,docContent.lastIndexOf("<"))+"</font>";
				}else{
					docContent=docContent+"</font>";
				}
				docContent+="......";
			}
			
			var html="<td>" +
						"<div class='row-container'>" +
					  		"<div class='row-title'>" +
					  			"<span class='knowrow-span-title'>标题："+title+"</span>" +
					  		"</div>"+
					  		"<div style='position:relative;'>"+
					  			"<span class='knowrow-span-issuer'>关键字："+keywords+"</span>" +
				  				"<span class='knowrow-span-issuer'>发布单位："+issuer+"</span>" +
			  					"<span class='knowrow-span-issuer data'>发布日期："+issueDate+"</span>" +
  							"</div>" +
		  					"<div class='row-content'>" +
		  						/*"<span class='knowrow-span-content-label'>内容：</span>" +*/
		  						"<span class='knowrow-span-content'>内容："+docContent+"</span>" +
							"</div>" +
//							"<div class='row-files'>"+
//								"<span class='knowrow-span-filename'>附件名："+fileName+"</span>" +
//							"</div>"+
							"<div align='right' class='row-buttons'>" +
								"<span class='knowrow-span-detail' onclick=\"doc_detail('"+rowData.docId+"','"+encodeURI(search_keyword)+"');return false;\">" +
									/*"<a href='javascript:void(0)' onclick=doc_detail('"+rowData.docId+"','"+rowIndex+"')>查看详情</a>" +*/
								"</span>"+
							"</div>"+
	  					"</div>" +
  					"</td>";
			return html;
		}
	}
});

//按具体某个属性类别查询
var search_categoryId="";
function search_by_cat(target,categoryId){
	if(categoryId){
		/*$(target).parent().siblings().css("background-color","#eef5ff");
		$(target).parent().css("background-color","orange");*/
		$(target).parent().siblings().removeClass("selected");
		$(target).parent().addClass("selected");
		search_categoryId=categoryId;
		var params={keyword:search_keyword,categoryId:categoryId};
		$('#search_datagrid').datagrid("load",paramsEncodeURI(params));
	}
}

function initDetailWindow(){
	$("#window_doc_detail").window({    
	    width:1034,    
	    height:516,  
	    title:"知识详情",
	    collapsible:false,
	    minimizable:false,
	    maximizable:false,
	    inline:false,
	    modal:true   
	});
	$("#window_doc_detail").window("close");
}

function doc_detail(doc_id,keyword){
	window.location.href="knowledge_search_detail.html?doc_id="+doc_id+"&keyword="+keyword;
}
//在结果中查询
function search_inresult(){
	//search_categoryId="";//清空分类代码
	var busi_code=$("#busiCode").val();
	var keywords=$("#keywords").val();
	var issuer=$("#issuer").val();
	var issue_date=$("#issue_date").val();
	var params={
				keyword:search_keyword,
				keywords:keywords,
				busi_code:busi_code,
				categoryId:search_categoryId,
				issuer:issuer,
				issue_date:issue_date,
				inresult:"1",
				sort:$("#search_sort").val()
			};
	$('#search_datagrid').datagrid("load",paramsEncodeURI(params));
}

//清空结果过滤条件
function search_inresult_reset(){
	$("#knowledge_query_form").form("reset");
	$("#busiCode").val("");
	//清空树勾选项
	reset_zTree("zTree_busiCode");
}

//初始化结果过滤panel
function initResultFilterPanel(){
	/*$('#result_filter').panel({    
		width:"100%",
		height:"175",
		title:"结果过滤",
		collapsible:true,
		collapsed:true,
		onCollapse:function(){
			$('#search_datagrid').datagrid("resize",{height:440});
		},
		onExpand:function(){
			$('#search_datagrid').datagrid("resize",{height:316});
		}
	});*/
}
function detail_window_close(){
	$("#window_doc_detail").window("close");
}


function download_file(fileId){
	window.location.href="../../knowledge/manageAction.do?method=download&fileId="+fileId;
}

//保存检索日志 
function saveSearchLog(keywords){
	if(keywords){
		$.ajax({
			url:"../../knowledge/manageAction.do?method=saveSearchLog",
			type:"post",
			data:{keywords:encodeURI(keywords)},
			error:function(){
				$.messager.alert("错误信息","保存失败。","error");
			}
		});
	}
}

//知识收藏
function add_to_favorite(doc_id){
	$.ajax({
		url:"../../knowledge/manageAction.do?method=addToFavorite",
		type:"post",
		data:{docId:doc_id},
		success:function(data){
			$.messager.alert("提示信息","收藏成功。","info");
		}
	});
}
function resize(){
	$('#center-layout').css('height','auto'); 
}