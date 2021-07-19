var docId=getUrlParam("doc_id");

$(function(){
	$.ajax({
		url:"../../knowledge/manageAction.do?method=queryKnowledge",
		data:{docId:docId},
		type:"post",
		success:function(data){
			$("#knowledge_detail span[name]").each(function() {
				$(this).empty();
				var attr = $(this).attr("name");
				var value = data[attr];
				$(this).html(value);
			});
			//alert(data.docContent.indexOf("<br>"));
			var docContent=data.docContent.replace(/\r\n+/ig,"<br>").replace(/ /ig,"&nbsp;");
			$("#docContent").html(docContent);
			if(data.klFiles){
				showFiles(data.klFiles);
			}
		}
	});
	
});

//ÏÔÊ¾¸½¼þ
function showFiles(files){
	var html="";
	$.each(files,function(){
		var file=this;
		html+="<a href='javascript:void(0)' onclick=\"download_file('"+file.fileId+"');return false;\">"+file.fileName+"</a></br>";
	});
	$("#klFiles").html(html);
}
function download_file(fileId){
	window.location.href="../../knowledge/manageAction.do?method=download&fileId="+fileId;
}