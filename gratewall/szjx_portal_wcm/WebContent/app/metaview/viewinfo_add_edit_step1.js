function saveData(){
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
	var oViewDesc = $('viewDesc');
	if(oViewDesc){
		var msg = null;
		if(/[<>;&]/.test(oViewDesc.value)){
			msg = "<font color='red'>" 
				+ (wcm.LANG.METAVIEW_ALERT_6 || "中文名称中含有特殊字符")
				+ "<b>;&&lt;&gt;</b></font>.";
		}
		if(msg){
			try{
				Ext.Msg.alert(msg, function(){
					setTimeout(function(){
						oViewDesc.focus();
					}, 10);
				});
			}catch(error){
			}
			return false;
		}
	}
	if(i == options.length) tableNameSel.selectedIndex = -1;
	var mainTableId = tableNameSel.selectedIndex == -1 ? 0 : tableNameSel.options[tableNameSel.selectedIndex].getAttribute("_id");
	ProcessBar.start(wcm.LANG.METAVIEW_PROCESSBAR_TIP_2 || "进度执行中，请稍候...");
	BasicDataHelper.call("wcm61_metaview", "saveView", {
		objectId	: getParameter('objectId') || 0,
		viewDesc	: $F('viewDesc'),
		tableName	: $F('tableName'),
		channelId	: getParameter('channelId') || 0,
		TemplatePath : $F('TemplatePath'),
		mainTableId	: mainTableId
	}, true, function(transport, json){
		ProcessBar.exit();
		var viewId = $v(json, "MetaView.VIEWINFOID");
		var tableInfoId = $v(json, "MetaView.MAINTABLEID");
		notifyFPCallback(tableInfoId, viewId);
		FloatPanel.close();
	});	
	return false;
}

function activate(element) {
    element = $(element);
    element.focus();
    if (element.select)
      element.select();
}


Event.observe(window, 'load', function(){
	if(getParameter('objectId') == 0){
		$('tableName').readOnly = false;
		$('tableNameSel').disabled = false;
		Element.removeClassName($('tableName').parentNode, "disabledCls");
	}
	$('tableNameSel').value = $F('tableName');
		
	ValidationHelper.bindValidations([
		{
			renderTo:'viewDesc',
			type:'string',
			required:'',
			max_len:'60',
			showid:'validTip',
			desc:wcm.LANG.METAVIEW_VIEWDESC||'视图名称'
		},
		{
			renderTo:'tableName',
			type:'common_char',
			required:'',
			require_container:'requireContainer',
			max_len:'29',
			showid:'validTip',
			desc:wcm.LANG.METAVIEW_MAINTABLENAME||'英文名称'
		}
		
	]);

	ValidationHelper.addValidListener(function(){
		FloatPanel.disableCommand('saveData', false);
	}, "ObjectForm");
	ValidationHelper.addInvalidListener(function(){
		FloatPanel.disableCommand('saveData', true);
	}, "ObjectForm");
	ValidationHelper.initValidation();
	
	//activate("viewDesc");

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
			activate(tableName);
		}else{
			tableName.value = tableNameSel.value;
		}
		ValidationHelper.forceValid(tableName);
	});
	
});

window.m_fpCfg = {
	m_arrCommands : [{
		cmd : 'saveData',
		name : wcm.LANG.METAVIEW_BUTTON_1||'下一步'
	}],
	size : [400, 130]
};
LockerUtil.register2(getParameter('objectId'), 1874605631, true, "saveData");
