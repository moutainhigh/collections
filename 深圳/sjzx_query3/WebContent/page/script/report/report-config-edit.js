var reportId=getUrlParam("id");
$(function(){
	loadReport();
});

//加载知识
function loadReport(){
	$.ajax({
		url:"../../report/reportManagerAction.do?method=queryReport",
		type:"post",
		data:{reportId:reportId},
		success:function(data){
			$("#rpt_cfg_form").form("load",data);
		}
	});
}

