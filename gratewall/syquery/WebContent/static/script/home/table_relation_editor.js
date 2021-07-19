function save(){
	var params = {
		url:rootPath+"/relationManager/saveRelation.do", 
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
	var dataSourceName = res.getAttr("dataSourceName");
	var busiObjectName = res.getAttr("busiObjectName");
	var tableNameEn = res.getAttr("tableNameEn");
	var pkDcDataSource = res.getAttr("pkDcDataSource");
	var pkDcTable = res.getAttr("pkDcTable");
	$("#dataSourceName").textfield("setValue",dataSourceName);
	$("#busiObjectName").textfield("setValue",busiObjectName);
	$("#pkDcDataSource").hiddenfield("setValue",pkDcDataSource);
	$("#pkDcTable").hiddenfield("setValue",pkDcTable);
	$("#tableNameEn").textfield("setValue",tableNameEn);
}

function changeBusiTalbe(event, ui){
	var datasourceid = ui.newValue;
	if(!!datasourceid){
		var params = {
			url:rootPath+"/relationManager/changeRelationBusi.do", 
			params: {"pkDcDataSource":datasourceid},
			callback : function(data,obj,res) {
				var busiObjectName = res.getAttr("busiObjectName");
				$("#busiObjectNameRelation").textfield("setValue",busiObjectName);
			}
		};
		$.DataAdapter.submit(params);
		var params2 = {
			url:rootPath+"/relationManager/changeRelationTable.do", 
			params: {"pkDcDataSource":datasourceid},
			callback : function(data,obj,res) {
				$("#pkDcTableRelation").comboxfield("setValue",data.data);
			}
		};
		$.DataAdapter.submit(params2);
	}else{
		$("#busiObjectNameRelation").textfield("setValue","");
		var data = [];
		$("#pkDcTableRelation").comboxfield("setValue",data);
	}
}

function changeColumn(event, ui){
	var datasourceid = $("#pkDcDataSourceRelation").comboxfield("getValue");
	var tableid = ui.newValue;
	if(!!datasourceid){
		var params = {
			url:rootPath+"/relationManager/changeRelationColumn.do", 
			params: {"pkDcDataSource":datasourceid,"pkDcTable":tableid},
			callback : function(data,obj,res) {
				$("#pkDcColumnRelation").comboxfield("setValue",data.data);
			}
		};
		$.DataAdapter.submit(params);
	}
}


