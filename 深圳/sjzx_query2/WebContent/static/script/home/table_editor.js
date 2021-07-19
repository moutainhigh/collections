$(function(){
	$("#formpanel").formpanel("option","dataurlparams",{"pkDcTable":pkDcTable});
	$("#formpanel").formpanel("option","dataurl",rootPath+"/tableManager/findTableById.do?pkDcTable="+pkDcTable);
	$("#formpanel").formpanel("reload");
});
function save(){
	var params = {
		url:rootPath+"/tableManager/updateTable.do", 
		components: ['formpanel'],
		callback : function(data,obj,res) {
			query();
			leave();
		}
	};
	$.DataAdapter.submit(params);
}

function leave(){
	window.top.$("#frame_maincontent").get(0).contentWindow.leave();
}

function query(){
	window.top.$("#frame_maincontent").get(0).contentWindow.query();
}

function initData(res){
	var busiObjectName = res.getAttr("busiObjectName");
	var primaryKeyName = res.getAttr("primaryKeyName");
	setTimeout(function(){
		$("#busiObjectName").textfield("setValue",busiObjectName);
		$("#primaryKeyName").textfield("setValue",primaryKeyName);
	},300);
}

