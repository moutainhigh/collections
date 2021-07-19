var newwindow ; 

function createNewWindow(url){
	newwindow = top.jazz.widget({
		vtype: 'window',
		name : "columnWindow",
		title : "字段管理",
		titleicon : rootPath+"/static/images/other/notepad-.png",
		minimizable : true, //是否显示最小化按钮
		showborder : true, //true显示窗体边框    false不显示窗体边
		closestate: true, 
        width: 750, 
        height:500, 
        modal:true,
		frameurl : url,
		framename : "columnTable"
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

function goBack(){
	history.back(-1);
}

function renderdata(event, obj){
	var data = obj.data;
	for(var i=0;i<data.length;i++){
		data[i]["custom"] = '<div class="jazz-grid-cell-inner">';
		data[i]["custom"] += '<a href="javascript:void(0);" title="管理" onclick="editorColumn(\''+data[i]["pkDcTable"]+'\',\''+data[i]["pkDcDataSource"]+'\',\''+data[i]["pkDcColumn"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
						   + '<a href="javascript:void(0);" title="查看" onclick="viewColumn(\''+data[i]["pkDcTable"]+'\',\''+data[i]["pkDcDataSource"]+'\',\''+data[i]["pkDcColumn"]+'\');"><img src="'+rootPath+'/static/images/other/mag.png" width="11px" height="12px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
						 /*  + '<a href="javascript:void(0);" title="认领" onclick="updateCheckColumn(\''+data[i]["pkDcColumn"]+'\',\''+data[i]["isCheck"]+'\');"><img src="'+rootPath+'/static/images/other/fengbei.png" width="11px" height="12px" border="0"/></a>'
							*/
				data[i]["custom"] += '</div>';
	}
	return data;
}

function rowrender(event,rows){
	 var data = rows.data;
	 var rowElements = rows.rowEl;
	 $.each(rowElements,function(i,trobj){
		$.each($(trobj).children(),function(j,tdobj){
			if(j==7||j==8){
				var txt = $(tdobj).text();
				if(txt=='否'){
					$(tdobj).css("color","red");
				}
			}
		});
	}); 
}

function editorColumn(pkDcTable,pkDcDataSource,pkDcColumn){
	var url = rootPath+"/page/tableManager/table_column_editor.jsp?type=1&pkDcDataSource="+pkDcDataSource+"&pkDcTable="+pkDcTable+"&pkDcColumn="+pkDcColumn;
	createNewWindow(url);
	if(!!newwindow){
		newwindow.window('open');
	}
}

function viewColumn(pkDcTable,pkDcDataSource,pkDcColumn){
	var url = rootPath+"/page/tableManager/table_column_editor.jsp?type=2&pkDcDataSource="+pkDcDataSource+"&pkDcTable="+pkDcTable+"&pkDcColumn="+pkDcColumn;
	createNewWindow(url);
	if(!!newwindow){
		newwindow.window('open');
	}
}

function updateCheckColumn(pkDcColumn,isCheck){
	if(isCheck=="Y"||isCheck=="是"){
		jazz.info("该字段已被认领");
		return;
	}
	var params = {
			url:rootPath+"/tableManager/updateCheckColumn.do", 
			params: {"pkDcColumn":pkDcColumn},
			callback : function(data,obj,res) {
   				if (res.getAttr("back") == 'success') {  	
   			        $('div[name="gridpanel"]').gridpanel('reload'); 
   					jazz.info("认领成功 ！！");
   				} else {
   					jazz.info("认领未成功，请稍候再试！！");
   				}
			}
		};
		$.DataAdapter.submit(params);
}

