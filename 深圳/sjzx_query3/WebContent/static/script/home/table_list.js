var newwindow ; 

function createNewWindow(url){
	newwindow = top.jazz.widget({
		vtype: 'window',
		name : "tableWindow",
		title : "表管理",
		titleicon : rootPath+"/static/images/other/notepad-.png",
		minimizable : true, //是否显示最小化按钮
		showborder : true, //true显示窗体边框    false不显示窗体边
		closestate: true, 
        width: 750, 
        height:500, 
        modal:true,
		frameurl : url,
		framename : "editorTable"
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
		data[i]["custom"] = '<div class="jazz-grid-cell-inner">';
		data[i]["custom"] += '<a href="javascript:void(0);" title="管理" onclick="editorTable(\''+data[i]["pkDcTable"]+'\',\''+data[i]["pkDcDataSource"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
							+ '<a href="javascript:void(0);" title="认领" onclick="updateCheckTable(\''+data[i]["pkDcTable"]+'\',\''+data[i]["isCheck"]+'\');"><img src="'+rootPath+'/static/images/other/fengbei.png" width="11px" height="12px" border="0"/></a>&nbsp;&nbsp;&nbsp;'
							+ '<a href="javascript:void(0);" title="共享" onclick="updateShareTable(\''+data[i]["pkDcTable"]+'\',\''+data[i]["isShare"]+'\');"><img src="'+rootPath+'/static/images/other/fengbei.png" width="11px" height="12px" border="0"/></a>'
		data[i]["custom"] += '</div>';
		data[i]["tableNameEn"] = '<a href="javascript:void(0);" title="物理表" onclick="editorColumn(\''+data[i]["pkDcTable"]+'\',\''+data[i]["pkDcDataSource"]+'\');">'+data[i]["tableNameEn"]+'</a>';
	}
	return data;
}

function rowrender(event,rows){
	 var data = rows.data;
	 var rowElements = rows.rowEl;
	 $.each(rowElements,function(i,trobj){
		$.each($(trobj).children(),function(j,tdobj){
			if(j==8){
				var txt = $(tdobj).text();
				if(txt=='否'){
					$(tdobj).css("color","red");
				}
			}
		});
	}); 
}

function editorTable(pkDcTable,pkDcDataSource){
	var url = rootPath+"/tableManager/editorTable.do?pkDcDataSource="+pkDcDataSource+"&pkDcTable="+pkDcTable;
	createNewWindow(url);
	if(!!newwindow){
		newwindow.window('open');
	}
}

function editorColumn(pkDcTable,pkDcDataSource){
	var url = rootPath+"/page/tableManager/table_column_list.jsp?pkDcDataSource="+pkDcDataSource+"&pkDcTable="+pkDcTable;
	top.$("#maincontent").panel("option", "frameurl",url);
}
function updateCheckTable(pkDcTable,isCheck){
	if(isCheck=="Y"||isCheck=="是"){
		jazz.info("该表已经被认领");
		return;
	}
	var params = {
			url:rootPath+"/tableManager/updateCheckTable.do", 
			params: {"pkDcTable":pkDcTable},
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

function updateShareTable(pkDcTable,isShare){
	if(isShare=="Y"||isShare=="是"){
		var params = {
				url:rootPath+"/tableManager/deleteShareTable.do", 
				params: {"pkDcTable":pkDcTable},
				callback : function(data,obj,res) {
	   				if (res.getAttr("back") == 'success') {  	
	   			        $('div[name="gridpanel"]').gridpanel('reload'); 
	   					jazz.info("共享取消成功 ！！");
	   				} else {
	   					jazz.info("共享取消未成功，请稍候再试！！");
	   				}
				}
			};
	}
	else{
	var params = {
			url:rootPath+"/tableManager/updateShareTable.do", 
			params: {"pkDcTable":pkDcTable},
			callback : function(data,obj,res) {
   				if (res.getAttr("back") == 'success') {  	
   			        $('div[name="gridpanel"]').gridpanel('reload'); 
   					jazz.info("共享成功 ！！");
   				} else {
   					jazz.info("共享未成功，请稍候再试！！");
   				}
			}
		};
	}
		$.DataAdapter.submit(params);
	
}
