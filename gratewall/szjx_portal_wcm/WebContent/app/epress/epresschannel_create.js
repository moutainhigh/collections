/*
*FloatPanel relations.
*/
window.m_fpCfg = {
	m_arrCommands : [{
		disableWhenClicked : 1,
		cmd : 'createEPressChannel',
		name : wcm.LANG.CHANNEL_TRUE||'确定'
	}],
	size : [500, 300]
};


Event.observe(window, 'load', function(){	
	ValidationHelper.addValidListener(function(){
		FloatPanel.disableCommand('createEPressChannel', false);
	}, "addEditForm");

	ValidationHelper.addInvalidListener(function(){
		FloatPanel.disableCommand('createEPressChannel', true);
	}, "addEditForm");
	
	ValidationHelper.initValidation();	
	Event.observe($("chkHasCoverPage"),'click',function(){
		if($("chkHasCoverPage").checked){
			Element.show($("divCoverPage"));
		}else{
			Element.hide($("divCoverPage"));
		}
	});
})

function syncChannelDesc(_eChnlName){
	var eChnlDesc = $('txtChnlDesc');
	if(eChnlDesc.value.trim() == '') {
		eChnlDesc.value = _eChnlName.value;
		ValidatorHelper.forceValid(eChnlDesc);
	}
	delete _eChnlName;
}

function checkChannelName(){		
	var eChnlName = $('txtChnlName');	
	BasicDataHelper.Call('wcm61_epress','existsSimilarName', {ChannelName: eChnlName.value, SiteId: $F('siteid')}, true, function(transport, json){
		if(com.trs.util.JSON.value(json, "result") == 'false'){
			ValidationHelper.successRPCCallBack();
		}else{
			ValidationHelper.failureRPCCallBack(wcm.LANG.epresschannel_creat_101 || "唯一标识已经存在");
		}
	});	
}

function checkEPressUuid(){		
	var uuid = $F('EPressUUID')||"";
	if(uuid && uuid.length > 0){
		BasicDataHelper.Call('wcm61_epress','checkUUID', {UUID:uuid}, false, function(transport, json){
			if(com.trs.util.JSON.value(json, "result") == 'false'){
				ValidationHelper.successRPCCallBack();
			}else{
				ValidationHelper.failureRPCCallBack(wcm.LANG.epresschannel_creat_102 || "报纸标识已经存在");
			}
		});
	}
}

function createEPressChannel(){	
	if($("chkHasCoverPage").checked && !$F("txtCoverPageName")){
		alert("没有指定封面名称");
		$("txtCoverPageName").focus();
		return false;
	}
	BasicDataHelper.call("wcm61_epress","createEPress","addEditForm",true,function(_transport,_json){		
		var id = com.trs.util.JSON.value(_json, "result");		
		notifyFPCallback(id);
	});	
	return false;
}