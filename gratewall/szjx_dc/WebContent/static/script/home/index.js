$(function(){
	//loadTitle(); // 去除加载资源菜单  只显示搜索框
	$('.sjzx_content').layout({
		layout:"fit"
	});
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			$("#search").click();//进行跳转
			//search();
		}
	});
});

function mainPage(code){
	var url = rootPath+"/page/integeration/index.jsp?code="+code
	window.open(url);
}

function loadTitle(){
	var params = {
		url:rootPath+"/home/getAppFuncByAppId.do",
		callback : function(data,obj,res) {
			if(!!data["data"]){
				var content = "";
				$.each(data["data"],function(i,data){
					if(!data.superFuncCode){
						content += "<span><a href='javascript:mainPage(\""+data.functionCode+"\");'>"+data.functionName+"</a></span>";
					}
				});
				$(".title").html(content);
				
			}
		}
	};
	$.DataAdapter.submit(params);
}



function search(){
/*	var url = rootPath+"/page/home/list.jsp";
	window.open(url);*/


    
	var txt = $("#search_txt").val();
	if(txt.length<1){
		jazz.info("搜索内容不能为空");
		return;
	}
	var url = rootPath+"/page/trs/trs.jsp?search="+encode(txt);
/*	var s="深圳市和顺堂医疗机构投资有限公司";
	alert(s);
	var str=encode(s);
	alert(str);
	alert(decode(str));*/

   
	window.open(url);
}