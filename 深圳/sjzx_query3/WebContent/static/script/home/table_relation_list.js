var newwindow ; 

function createNewWindow(url){
	newwindow = top.jazz.widget({
		vtype: 'window',
		name : "relationWindow",
		title : "表关系添加",
		titleicon : rootPath+"/static/images/other/notepad-.png",
		minimizable : true, //是否显示最小化按钮
		showborder : true, //true显示窗体边框    false不显示窗体边
		closestate: true, 
        width: 750, 
        height:500, 
        modal:true,
		frameurl : url,
		framename : "relationTable"
	});
}

function add(){
	var url = rootPath+"/relationManager/editorRelation.do?pkDcDataSource="+pkDcDataSource+"&pkDcTable="+pkDcTable;
	createNewWindow(url);
	if(!!newwindow){
		newwindow.window('open');
	}
}

function leave(){
	newwindow.window('close'); 
}

function query(){
	$("#gridpanel").gridpanel('query', ['formpanel']);
}

function goBack(){
	history.back(-1);
}

function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		data[i]["custom"] = '<div class="jazz-grid-cell-inner">';
		data[i]["custom"] += '<a href="javascript:void(0);" title="删除" onclick="deleteColumn(\''+data[i]["pkDcRelation"]+'\');"><img src="'+rootPath+'/static/images/other/close.png" width="11px" height="11px" border="0"/></a>'
		data[i]["custom"] += '</div>';
	}
	return data;
}

function deleteColumn(pkDcRelation){
	jazz.confirm("是否确认删除，删除后不可恢复", function(){
		var params = {
				url:rootPath+"/relationManager/deleteRelation.do", 
				params: {"pkDcRelation":pkDcRelation},
				callback : function(data,obj,res) {
					query();
				}
			};
			$.DataAdapter.submit(params);
	}, function(){})
}
