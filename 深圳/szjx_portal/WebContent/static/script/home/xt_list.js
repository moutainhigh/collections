var newwindow = top.$("div[name=xtwindow]"); 
$(function(){
	if (newwindow.length == 0) {
		createNewWindow();
	}
});

function initData(res){
	var flag = res.getAttr("flag");
	if(flag == "0"){
		$('#add_button').button('hide');
	}
}

function add(){
	if (newwindow.length == 0) {
		createNewWindow();		
	}else{
		newwindow=null;
		createNewWindow("添加");
	}
	newwindow.window("option","frameurl",rootPath+"/integration/initIntegration.do");
	newwindow.window('open');
}

function createNewWindow(title){
	if(title==undefined||"")title="编辑"
	newwindow = top.jazz.widget({
		vtype: 'window',
		name : "xtwindow",
		title : title,
		titleicon : rootPath+"/static/images/other/notepad-.png",
		minimizable : true, //是否显示最小化按钮
		showborder : true, //true显示窗体边框    false不显示窗体边
		closestate: true, 
        width: 750, 
        height:500, 
        modal:true,
		frameurl : "",
		framename : "addxt"
	});
}

function leave(){
	newwindow.window('close'); 
	newwindow = top.$("div[name=xtwindow]"); 
}

function query(){
	$("#gridpanel").gridpanel('query', ['formpanel']);
}

function reset(){
	$("#formpanel").formpanel('reset');
}

function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		data[i]["custom"] = '<div class="jazz-grid-cell-inner">';
		if(data[i]["issuper"]=="1"){
			data[i]["custom"] += '<a href="javascript:void(0);" title="编辑" onclick="editorSystem(\''+data[i]["pkSysIntegration"]+'\',\''+data[i]["systemType"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;';
				 
//				data[i]["custom"] += '<a href="javascript:void(0);" title="维护" onclick="fixMenu(\''+data[i]["pkSysIntegration"]+'\',\''+data[i]["systemType"]+'\');"><img src="'+rootPath+'/static/images/other/fengbei.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;';
			
			data[i]["custom"] +=  '<a href="javascript:void(0);" title="查看" onclick="viewSystem(\''+data[i]["pkSysIntegration"]+'\',\''+data[i]["systemType"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>&nbsp;&nbsp;&nbsp;';
			data[i]["custom"] +=  '<a href="javascript:void(0);" title="删除" onclick="deleteSystem(\''+data[i]["pkSysIntegration"]+'\');"><img src="'+rootPath+'/static/images/other/close.png" width="13px" height="13px" border="0"/></a>';
		}else{
//			data[i]["custom"] += '<a href="javascript:void(0);" title="维护" onclick="fixMenu(\''+data[i]["pkSysIntegration"]+'\');"><img src="'+rootPath+'/static/images/other/fengbei.png" width="11px" height="11px" border="0"/></a>'

		}
		data[i]["custom"] += '</div>';
	}
	
	for(var i=0;i<data.length;i++){
		data[i]["integratedUrl"] = '<div class="jazz-grid-cell-inner">'+data[i]["integratedUrl"]+'<pre></pre>';
		data[i]["integratedUrl"] += '</div>';
	}
	
	
	
	return data;
}

function rowrender(event,rows){
	 var data = rows.data;
	 var rowElements = rows.rowEl;
	 $.each(rowElements,function(i,trobj){
		$.each($(trobj).children(),function(j,tdobj){
			if(j==6){
				var txt = $(tdobj).text();
				if(txt=='停止'){
					$(tdobj).css("color","red");
				}
			}
		});
	}); 
}

function deleteSystem(pkid){
   	jazz.confirm('是否确认删除，删除后不可恢复',
			    function(){
			   		$('#gridpanel').gridpanel('removeRowById',pkid);
			   		var params = {
			   			url:rootPath+"/integration/deleteIntegration.do",
			   			params: {pkId:pkid},
			   			callback : function(data,obj,res) {
			   				
			   			}
			   		};
			   		$.DataAdapter.submit(params);
			   	},
			   	function(){
			   		return;
			   	}
	);   	
}

function editorSystem(pkid,systemType){
	if (newwindow.length == 0) {
		createNewWindow("编辑子系统");		
	}else{
		newwindow=null;
		createNewWindow("编辑子系统");
	}
	var systype;
	if(systemType=="NOSYS" || systemType=="是")	{
		systype="NOSYS";
		newwindow.window('option','frameurl',rootPath+"/page/integeration/xt_add.jsp?type=1&pkId="+pkid+"&systype="+systype);
		newwindow.window('open');
	}else{
		systype="SYS";
		newwindow.window('option','frameurl',rootPath+"/page/integeration/xt_add.jsp?type=1&pkId="+pkid+"&systype="+systype);
		newwindow.window('open');
	}

}

function viewSystem(pkid,systemType){
	if (newwindow.length == 0) {
		createNewWindow("查看子系统");		
	}else{
		newwindow=null;
		createNewWindow("查看子系统");
	}
	if(systemType=="NOSYS" || systemType=="是")	{
		systype="NOSYS";
		newwindow.window('option','frameurl',rootPath+"/page/integeration/xt_add.jsp?type=2&pkId="+pkid+"&systype="+systype);
		newwindow.window('open');
	}else{
		systype="SYS";
		newwindow.window('option','frameurl',rootPath+"/page/integeration/xt_add.jsp?type=2&pkId="+pkid+"&systype="+systype);
		newwindow.window('open')
	}
}

function fixMenu(pkSysIntegration,systemType) {//维护菜单
	if(systemType=="NOSYS" || systemType=="是")	{
		jazz.info("当前点击项非系统！");
		return ;
	}	

	$("iframe", parent.document).attr("src", rootPath+'/auth/getMenu_fixHtml.do?pkSysIntegration='+pkSysIntegration);
}