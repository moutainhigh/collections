var TableInfos = {
	servicesName : 'wcm6_MetaDataDef',
	findMethodName : 'findDBTableInfoById',
	objectId : getParameter("objectId") || 0,

	loadData : function(){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(this.servicesName, this.findMethodName, {
			fieldsToHTML : 'tableName,anotherName,tableDesc',
			objectId : TableInfos["objectId"]
		}, true, this.dataLoaded.bind(this));
	},
	dataLoaded : function(transport, json){
        var sValue = TempEvaler.evaluateTemplater('objectTemplate', json);
        Element.update('objectContainer', sValue);	
		Form.focusFirstElement('objectContainer');
		ValidationHelper.initValidation(); 
	}
};

function saveData(){
	if(containKeyWordsInContainer('objectContainer')){
		return false;
	}
	$beginSimplePB("正在保存数据");
	$tableInfoMgr.save(TableInfos["objectId"], {
		tableName : $F('tableName'),
		anotherName : $F('anotherName'),
		tableDesc : $F('tableDesc')
	}, null, function(transport, json){
		$endSimplePB();
		var $v = com.trs.util.JSON.value;
		var objectId = $v(json, "DBTABLEINFO.TABLEINFOID");
		$MessageCenter.sendMessage('main', 'TableInfos.refreshList', 'TableInfos', [[objectId]]);
		FloatPanel.close(true);
	});
	return false;
}

FloatPanel.addCloseCommand();   
FloatPanel.addCommand('savebtn', '确定', 'saveData', null);

ValidationHelper.addValidListener(function(){
	FloatPanel.disableCommand('savebtn', false);
}, "ObjectForm");
ValidationHelper.addInvalidListener(function(){
	FloatPanel.disableCommand('savebtn', true);
}, "ObjectForm");

Event.observe(window, 'load', function(){
	TableInfos.loadData();
});
