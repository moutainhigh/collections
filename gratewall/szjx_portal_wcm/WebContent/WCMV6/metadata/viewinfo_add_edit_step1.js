var ViewInfos = {
	servicesName : 'wcm6_MetaDataDef',
	findMethodName : 'findViewById',
	queryTableMethodName : 'queryDBTableInfos',
	objectId : getParameter("objectId") || 0,

	loadData : function(){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var aCombine = [];
        aCombine.push(oHelper.Combine(this.servicesName,this.findMethodName, {fieldsToHTML:'viewDesc',objectId : this.objectId})); 
        aCombine.push(oHelper.Combine(this.servicesName,this.queryTableMethodName,{
			selectFields:"tableinfoid,tablename,anothername",
			pageSize : -1,
			fieldsToHTML : 'tablename,anothername'
		})); 
        oHelper.MultiCall(aCombine, this.dataLoaded.bind(this));        
	},
	dataLoaded : function(transport, json){
        var sValue = TempEvaler.evaluateTemplater('objectTemplate', json["MULTIRESULT"]);
        Element.update('objectContainer', sValue);
		if(this.objectId == 0){
			$('tableName').disabled = false;
			Element.show('tableNameSel');
		}
		$('tableNameSel').value = $F('tableName');
		this.bindEvents();
		Form.focusFirstElement('objectContainer');
		ValidationHelper.initValidation(); 
	},
	bindEvents : function(){
		Event.observe('tableNameSel', 'click', function(){
			var tableName = $F('tableName').toLowerCase();
			var tableNameSel = $('tableNameSel');
			var options = tableNameSel.options;
			for (var i = 0; i < options.length; i++){
				if(options[i].value.toLowerCase() == tableName){
					tableNameSel.selectedIndex = i;
					break;
				}
			}
			if(i == options.length) tableNameSel.selectedIndex = -1;
		});
		Event.observe('tableNameSel', 'change', function(){
			var tableName = $('tableName');
			var tableNameSel = $('tableNameSel');
			if(tableNameSel.value == ""){
				Field.activate(tableName);
			}else{
				tableName.value = tableNameSel.value;
			}
			ValidationHelper.forceValid(tableName);
		});
	}
};

function saveData(){
	if(containKeyWordsInContainer('objectContainer')){
		return false;
	}
	$beginSimplePB("正在保存数据");
	//get MainTableId
	var tableName = $F('tableName').toLowerCase();
	var tableNameSel = $('tableNameSel');
	var options = tableNameSel.options;
	for (var i = 0; i < options.length; i++){
		if(options[i].value.toLowerCase() == tableName){
			tableNameSel.selectedIndex = i;
			break;
		}
	}
	if(i == options.length) tableNameSel.selectedIndex = -1;
	var mainTableId = tableNameSel.selectedIndex == -1 ? 0 : tableNameSel.options[tableNameSel.selectedIndex].getAttribute("_id");
	$viewInfoMgr.save(ViewInfos["objectId"], {
		viewDesc	: $F('viewDesc'),
		tableName	: $F('tableName'),
		channelId	: getParameter('channelId') || 0,
		mainTableId	: mainTableId
	}, null, function(transport, json){
		$endSimplePB();
		var $v = com.trs.util.JSON.value;
		var objectId = $v(json, "MetaView.VIEWINFOID");
		var objectDesc = $v(json, "MetaView.VIEWDESC");
		var tableId = $v(json, "MetaView.MAINTABLEID");
		$MessageCenter.sendMessage('main', 'ViewInfos.refreshList', 'ViewInfos', [objectId, objectDesc]);
		$viewInfoMgr.addEditStepTwo(objectId, {tableId:tableId});
	});
	return false;
}

FloatPanel.addCloseCommand();   
FloatPanel.addCommand('savebtn', '下一步', 'saveData', null);

ValidationHelper.addValidListener(function(){
	FloatPanel.disableCommand('savebtn', false);
}, "ObjectForm");
ValidationHelper.addInvalidListener(function(){
	FloatPanel.disableCommand('savebtn', true);
}, "ObjectForm");

Event.observe(window, 'load', function(){
	ViewInfos.loadData();
});
