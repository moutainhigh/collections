var ViewInfos = {
	servicesName : 'wcm6_MetaDataDef',
	findMethodName : 'findViewById',
	objectId : getParameter("objectId") || 0,

	loadData : function(){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
        oHelper.Call(this.servicesName, this.findMethodName, {objectId : this.objectId}, true, this.dataLoaded.bind(this));        
	},
	dataLoaded : function(transport, json){
        var sValue = TempEvaler.evaluateTemplater('objectTemplate', json);
        Element.update('objectContainer', sValue);
		if(this.objectId == 0){
			$('tableName').disabled = false;
		}
		ValidationHelper.initValidation(); 
	}
};

function saveData(){
	if(containKeyWordsInContainer('objectContainer')){
		return false;
	}
	$beginSimplePB("正在保存数据");
	$viewInfoMgr.save(ViewInfos["objectId"], {
		viewDesc	: $F('viewDesc'),
		tableName	: $F('tableName')
	}, null, function(transport, json){
		$endSimplePB();
		var $v = com.trs.util.JSON.value;
		var objectId = $v(json, "MetaView.VIEWINFOID");
		var objectDesc = $v(json, "MetaView.VIEWDESC");
		$MessageCenter.sendMessage('main', 'ViewInfos.refreshList', 'ViewInfos', [objectId, objectDesc]);
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
	ViewInfos.loadData();
});
