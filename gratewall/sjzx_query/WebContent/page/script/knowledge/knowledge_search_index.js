$(function(){
	queryHotwords();
	showHotwordstype();
	initSearchBox();
	switchTitle(1);
	querySearchTop("init");
	queryNewPublished("init");
	if(window.name){
		$("#"+window.name,window.parent.document).parent().css("overflow","hidden");//隐藏父窗口滚动条
	}
});

function switchTitle(index){
	if(index==0){
		$("#search_top").addClass("high-title");
		$("#publish_new").removeClass("high-title");
		$("#search_top_content").show();
		$("#publish_new_content").hide();
	}else{
		$("#search_top").removeClass("high-title");
		$("#publish_new").addClass("high-title");
		$("#search_top_content").hide();
		$("#publish_new_content").show();
	}
}

function initSearchBox(){
	document.onkeydown=function(e){
		 var theEvent = e || window.event;    
	     var code = theEvent.keyCode || theEvent.which || theEvent.charCode;  
	     if (code == 13&&$("#search_textbox").is(":focus")) {  
	    	 go_search();
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
function go_search(keyword){
	if(keyword){
		window.location.href="knowledge_search.html?keyword="+encodeURI(keyword)+"&keysort=2";
	}else{
		var keyword=$("#search_textbox").val();
		if(keyword&&keyword!='请输入您要搜索的内容'){
			window.location.href="knowledge_search.html?keyword="+encodeURI(keyword)+"&keysort=2";
		}
	}
	
}


/**
 * 搜索类型
 * @param keyword
 */
function go_search_type(keyword){
	if(keyword){
		window.location.href="knowledge_search_type.html?keyword="+encodeURI(keyword);
	}
}


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
				var default_hotwords="<span class='hot-word'>名称设立</span>" +
									 "<span class='hot-word'>公司设立</span>" +
									 "<span class='hot-word'>公司变更</span>" +
									 "<span class='hot-word'>住所</span>" +
									 "<span class='hot-word'>注册资本</span>";
				$("#span_hotword_list").append(default_hotwords);
			}
		}
	});
	
	$(".hot-word").die('click').live('click',function(){
		var keyword=$(this).text();
		go_search(keyword);
	});
}


/**
 * 显示热词类型
 */
function showHotwordstype(){
	var hotwordtype = "<span class='hot-word-type'>业务通知</span>" +
					  "<span class='hot-word-type'>市局发文</span>" +
					  "<span class='hot-word-type'>行政法规</span>" ;
	$("#span_hotwordtype_list").append(hotwordtype);
	
	$(".hot-word-type").die('click').live('click',function(){
		var keyword=$(this).text();
		go_search_type(keyword);
	});
}


//查询搜索风云榜
var pageIndex_top=0;
function querySearchTop(opt){
	if(opt=="pre"){
		pageIndex_top=pageIndex_top-1;
	}else if(opt=="next"){
		pageIndex_top=pageIndex_top+1;
	}
	$.ajax({
		url:"../../knowledge/searchAction.do?method=queryTopSearch&pageIndex="+pageIndex_top,
		type:"post",
		async:false,
		success:function(data){
			$("#search_top_content").empty();
			var search_top_html="";
			var pageCount=data.pagecount;
			$.each(data.rows,function(){
				var isRead=this.isRead;
				var catName=this.catName||"内部通知";
				var unreadStyle=isRead==1?"":"style='font-weight:bold;'";
				var isReadTip = isRead==1?"<span>[已读]</span>":"<span>[未读]</span>";
				search_top_html+="<div class='hot-content-main-title' "+unreadStyle+" onclick=\"top_search_detail('"+this.docId+"');return false;\">"+
				  		"<span class='li-tips select'></span>"+isReadTip+
					  	(this.title.length+catName.length>20?"<span class='overflow-text' title='"+this.title+"'>":"<span title='"+this.title+"'>")+
					  	"["+catName+"]"+this.title+"</span><span style='float:right;'>(浏览"+this.searchCount+"次)</span></div>"+
					  "<div class='clear'></div>";
			});
			var paginator_html='<div style="width:300px;position:absolute;bottom:12px;">'+
				       			'<input onclick="querySearchTop(\'pre\')"  class="paginator-button '+(pageIndex_top==0?"gwssi-disabled":"")+'" '+(pageIndex_top==0?"disabled":"")+'  value="上一页" type="button">'+
								'<input onclick="querySearchTop(\'next\')" class="paginator-button '+(pageIndex_top==pageCount-1?"gwssi-disabled":"")+'" '+(pageIndex_top==pageCount-1?"disabled":"")+' value="下一页" type="button">'+
								'</div>';
			$("#search_top_content").html(search_top_html);
			$("#search_top_content").append(paginator_html);
		}
	});
	/*var wordspan = $(".hot-content-main-title span");
	for(var k=0;k<wordspan.size();k++){
		wordspan[k].style.fontSize = '15px';
	}*/
}

//查询最新发布
var pageIndex_new=0;
function queryNewPublished(opt){
	if(opt=="pre"){
		pageIndex_new=pageIndex_new-1;
	}else if(opt=="next"){
		pageIndex_new=pageIndex_new+1;
	}
	$.ajax({
		url:"../../knowledge/searchAction.do?method=queryNewPublished&pageIndex="+pageIndex_new,
		async:false,
		type:"post",
		success:function(data){
			$("#publish_new_content").empty();
			var new_published_html="";
			var pageCount=data.pagecount;
			$.each(data.rows,function(){
				var isRead=this.isRead;
				var catName=this.catName||"内部通知";
				var unreadStyle=isRead==1?"":"style='font-weight:bold;'";
				var isReadTip = isRead==1?"<span>[已读]</span>":"<span>[未读]</span>";
				new_published_html+="<div class='hot-content-main-title' "+unreadStyle+" onclick=\"top_search_detail('"+this.docId+"');return false;\">"+
				  		"<span class='li-tips select'></span>"+isReadTip+
					  	(this.title.length+catName.length>20?"<span class='overflow-text' title='"+this.title+"'>":"<span title='"+this.title+"'>")+
					  	"["+catName+"]"+this.title+"</span><span style='float:right;'>"+this.publishDate+"</span></div>"+
					  "<div class='clear'></div>";
			});
			var paginator_html='<div  style="width:300px;position:absolute;bottom:12px;">'+
				       			'<input onclick="queryNewPublished(\'pre\')"  class="paginator-button '+(pageIndex_new==0?"gwssi-disabled":"")+'" '+(pageIndex_new==0?"disabled":"")+'  value="上一页" type="button">'+
								'<input onclick="queryNewPublished(\'next\')"  class="paginator-button '+(pageIndex_new==pageCount-1?"gwssi-disabled":"")+'" '+(pageIndex_new==pageCount-1?"disabled":"")+' value="下一页" type="button">'+
								'</div>';
			$("#publish_new_content").html(new_published_html);
			$("#publish_new_content").append(paginator_html);
		}
	});
	
	/*var wordspan = $(".hot-content-main-title span");
	for(var k=0;k<wordspan.size();k++){
		wordspan[k].style.fontSize = '15px';
	}*/
	
}

function top_search_detail(docId){
	window.location.href="knowledge_search_detail.html?doc_id="+docId+"&keyword=";
}