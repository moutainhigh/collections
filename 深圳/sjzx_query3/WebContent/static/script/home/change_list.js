var newwindow ; 

function createNewWindow(url){
	newwindow = top.jazz.widget({
		vtype: 'window',
		name : "changeWindow",
		title : "变更详情",
		titleicon : rootPath+"/static/images/other/notepad-.png",
		minimizable : true, //是否显示最小化按钮
		showborder : true, //true显示窗体边框    false不显示窗体边
		closestate: true, 
        width: 750, 
        height:500, 
        modal:true,
		frameurl : url,
		framename : "viewChange"
	});
}

function leave(){
	newwindow.window('close'); 
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
		data[i]["tableNameEn"] = '<a href="javascript:void(0);" title="物理表" onclick="viewChange(\''+data[i]["pkDcChange"]+'\',\''+data[i]["pkDcDataSource"]+'\');">'+data[i]["tableNameEn"]+'</a>';
	}
	return data;
}

function viewChange(pkDcChange,pkDcDataSource){
	var url = rootPath+"/changeManager/viewChange.do?pkDcChange="+pkDcChange+"&pkDcDataSource="+pkDcDataSource;
	createNewWindow(url);
	if(!!newwindow){
		newwindow.window('open');
	}
}


