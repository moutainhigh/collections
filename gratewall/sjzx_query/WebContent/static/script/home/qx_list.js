var newwindow = top.$("div[name=qxwindow]"); 
$(function(){
	if (newwindow.length == 0) {
		createNewWindow();
	}
});

function createNewWindow(){
	newwindow = top.jazz.widget({
		vtype: 'window',
		name : "qxwindow",
		title : "管理员和岗位映射",
		titleicon : "../../static/images/other/notepad-.png",
		minimizable : true, //是否显示最小化按钮
		showborder : true, //true显示窗体边框    false不显示窗体边
		closestate: true, 
        width: 750, 
        height:500,
        modal:true,
		frameurl : "",
		framename : "addqx"
	});
}



function leave(){
	newwindow.window('close');
	newwindow = top.$("div[name=qxwindow]");
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
		data[i]["custom"] = '<div class="jazz-grid-cell-inner">'
							+'<a href="javascript:void(0);" title="编辑" onclick="editorSystem(\''+data[i]["roleCode"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
							+'</div>';
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

function editorSystem(pkid){
	if (newwindow.length == 0) {
		createNewWindow();
	} 
	newwindow.window('option','frameurl',rootPath+"/integration/findPostitonRole.do?type=1&pkRole="+pkid);
	newwindow.window('open');
}
