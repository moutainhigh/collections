
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
		data[i]["custom"] += '<a href="javascript:void(0);" title="关系维护" onclick="editorRelation(\''+data[i]["pkDcTable"]+'\',\''+data[i]["pkDcDataSource"]+'\');"><img src="'+rootPath+'/static/images/other/forma-1.png" width="11px" height="11px" border="0"/></a>'
		data[i]["custom"] += '</div>';
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

function editorRelation(pkDcTable,pkDcDataSource){
	var url = rootPath+"/page/tableManager/table_relation_list.jsp?pkDcDataSource="+pkDcDataSource+"&pkDcTable="+pkDcTable;
	top.$("#maincontent").panel("option", "frameurl",url);
}

