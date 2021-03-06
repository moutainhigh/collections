Ext.ns('wcm.XWebSite');
wcm.XWebSite = function(config){
	wcm.XWebSite.superclass.constructor.apply(this, arguments);
	Ext.apply(this, config);
	this.init();
};

Ext.extend(wcm.XWebSite, wcm.util.Observable, {
	init : function(){
		this.addEvents(
			/**
			*@event beforeload
			*before send request for load data.
			*/
			'beforeload',
			/**
			*@event beforerender
			*after get the data from server and before render the html to page.
			*/
			'beforerender',
			/**
			*@event render
			*render the html to page.
			*/
			'render',
			/**
			*@event afterrender
			*after render the html to page.
			*/
			'afterrender',
			/**
			*@event beforesave
			*before post the data to server.
			*/
			'beforesave',
			/**
			*@event save
			*after save the channel data to server, and then save other datas.
			*/
			'save',
			/**
			*@event aftersave
			*after while all datas saved.
			*/
			'aftersave'
		);
	},

	load : function(){
		var aCombine = [];
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		if(this.fireEvent('beforeload', aCombine, oHelper) === false) return;
		if(aCombine.length > 0){
			oHelper.MultiCall(aCombine, this.render.bind(this));
		}else{
			this.render();
		}
	},

	render : function(oTrans, oJson){
		oJson = oJson || {};
		oJson = oJson["MULTIRESULT"];
		if(this.fireEvent('beforerender', oTrans, oJson) === false) return;
		this.fireEvent('afterrender', oTrans, oJson);
		Element.show("tabPanel");
	},

	save : function(){
		if(this.fireEvent('beforesave') === false) return;
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		if(this.fireEvent('beforesave') === false) return;
		this.saveAction();
	},

	saveAction : function(oTrans, oJson){
		//$("objectId").value = $v(oJson, 'result');
		var aCombine = [];
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var PostDataUtil = com.trs.web2frame.PostData;
		var postdata = PostDataUtil.form(this.form, function(m){return m;});
		aCombine.push(oHelper.Combine(
			this.service,
			'save',
			postdata
		));
		this.fireEvent('save', oTrans, oJson, aCombine, oHelper);
		if(aCombine.length > 0){
			oHelper.JspMultiCall('../../app/website/website_addedit_dowith.jsp', aCombine,
				this.afterSave.bind(this)
			);
		}
	},

	afterSave : function(oTrans, oJson){
		this.fireEvent('aftersave', oTrans, oJson);
	}
});

var oXWebSite = new wcm.XWebSite({
	service : 'wcm6_website',
	form : 'formData'
});

/*
*?????????????????????????????????????????????????????????
*/
oXWebSite.on('beforerender', function(oTrans, oJson){
	if($F("status") < 0) {
		Ext.Msg.warn(String.format(wcm.LANG.WEBSITE_ALERT_1||'????????????????????????????????????????????????,?????????????????????????????????!',$F("objectId")), function(){
			FloatPanel.close();
		});
		return false;
	}
});

/*
*???????????????????????????
*/
oXWebSite.on('afterrender', function(oTrans, oJson){
	setTimeout(function(){
		try{
			$('siteName').focus();
			$('siteName').select();
		}catch(err){
			//???????????????????????????(???????????????????????????????????????), ??????????????????
		}
	},0);
});

/*
*????????????????????????
*/
oXWebSite.on('afterrender', function(oTrans, oJson){
	Event.observe('siteName', 'blur', function(){
		var eSiteName = $('siteName');
		var eSiteDesc = $('siteDesc');
		if(eSiteDesc.value.trim() == '' && eSiteName.value.trim() != '') {
			eSiteDesc.value = eSiteName.value;
			ValidatorHelper.forceValid(eSiteDesc);
		}
	});
});

/*
*??????????????????????????????
*/
oXWebSite.on('afterrender', function(oTrans, oJson){
	Event.observe('dataPath', 'blur', function(){
		var eRootDomain = $('rootDomain');
		var eDataPath = $('dataPath');
		if(eRootDomain.value == '' && eDataPath.value.trim() != ''){
			eRootDomain.value = location.protocol + "//" + location.host + "/pub/" + eDataPath.value.trim();
			ValidationHelper.forceValid(eRootDomain);
		}
	}, false);
});

/*
*????????????????????????
*/
oXWebSite.on('afterrender', function(oTrans, oJson){
	var dom = $("siteOrder");
	var opts = dom.options;
	if($F("objectId") == 0){
		dom.selectedIndex = (opts.length - 1);
		return;
	}
	var nSiteOrder = dom.getAttribute("_value");
	dom.value = nSiteOrder;
	dom.selectedIndex -= 1;
	if(dom.selectedIndex < 0){
		dom.selectedIndex = 0;
	}
	opts[dom.selectedIndex].setAttribute("value", nSiteOrder);
	if(opts.remove){//IE
		opts.remove(dom.selectedIndex + 1);
	}else{//Not IE
		dom.removeChild(opts[dom.selectedIndex + 1]);
	}
});

/*
*???????????????????????????
*/
oXWebSite.on('afterrender', function(oTrans, oJson){
	if($F('objectId') == 0) return;
	var dom = $('siteLanguage');
	dom.value = dom.getAttribute('_value');
});

/*
*?????????????????????????????????
*/
oXWebSite.on('afterrender', function(oTrans, oJson){
	var siteId = $F('objectId');
	var box = $('statusesCanDoPubBox');
	var sValue = "," + box.getAttribute('_value') + ",";
	var doms = document.getElementsByName("statusesCanDoPub");
	for (var i = 0; i < doms.length; i++){
		if(doms[i].value == '10') {
			doms[i].disabled = true;
			doms[i].checked = true;
			continue;
		}else if(doms[i].value == 16 && siteId == 0){
			doms[i].checked = true;
			continue;
		}
		if(sValue.indexOf(","+doms[i].value+",") < 0) continue;
		doms[i].checked = true;
	}
});

/*
*???????????????????????????????????????
*/
oXWebSite.on('afterrender', function(oTrans, oJson){
	if($F('objectId') == 0) return;
	var dom = $('statusIdAfterModify');
	dom.value = dom.getAttribute('_value');

});

var TemplateConfig = [
	{
		templateType : "1",
		selectType : "radio",
		valueId : 'outlineTemplate',
		textId : 'spOutlineTemp'
	},
	{
		templateType : "2",
		selectType : "radio",
		valueId : 'detailTemplate',
		textId : 'spDetailTemp'
	},
	{
		templateType : "1",
		selectType : "checkbox",
		valueId : 'otherTemplates',
		textId : 'spOtherTemps'
	}
];

/*
*????????????????????????
*/
oXWebSite.on('afterrender', function(oTrans, oJson){
	if($F('objectId') == '0' || !hasEditRight) return;
	var doms = document.getElementsByName("template");
	for (var i = 0; i < doms.length; i++){
		var dom = doms[i];
		dom.style.display = '';
		Event.observe(dom, 'click', function(event){
			Event.stop(event);
			selectTemplate(this);
		}.bind(dom));
	}
});

/*
*????????????
*/
function selectTemplate(dom){
	var index = dom.getAttribute("index");
	var config = TemplateConfig[index];
	var sTempNames = "";
	var nLen = 0;
	if(window.navigator.userAgent.toLowerCase().indexOf("msie")>=1){
		sTempNames = ($(config["textId"]).outerText.trim()==(wcm.LANG.WEBSITE_NONE||"???"))?"":$(config["textId"]).outerText;
		nLen = sTempNames.length;
		if(sTempNames.slice(nLen-3,nLen-1)==", "){
			sTempNames = sTempNames.slice(0,nLen-3);
		}
	}else{
		sTempNames = ($(config["textId"]).innerHTML.trim()==(wcm.LANG.WEBSITE_NONE||"???"))?"":$(config["textId"]).innerHTML;
		sTempNames = sTempNames.trim();
		nLen = sTempNames.length;
		if(sTempNames.slice(nLen-3,nLen-1)==", "){
			sTempNames = sTempNames.slice(0,nLen-3);
		}
	}
	wcm.TemplatSelector.selectTemplate(
		{
			siteId : $F('objectId'),
			templateType : config["templateType"],
			selectType : config["selectType"],
			templateIds : $(config["valueId"]).value,
			tempNames : sTempNames
		},
		function(_args){
			$(config["valueId"]).value = _args.selectedIds.join(",");
			Element.update(config["textId"], _args.selectedNames.join(', ') || (wcm.LANG.WEBSITE_NONE||"???"));
		}
	);
}

/*
*??????????????????
*/
function switchSchdDisp(_nMode){
	$('scheduleMode').value = _nMode;
	switch(_nMode){
		case 0:
			ValidationHelper.popElements($('execTimeHour'));
			ValidationHelper.popElements($('execTimeMinute'));
			ValidationHelper.popElements($('startTimeHour'));
			ValidationHelper.popElements($('startTimeMinute'));
			ValidationHelper.popElements($('endTimeHour'));
			ValidationHelper.popElements($('endTimeMinute'));
			ValidationHelper.popElements($('intervalHour'));
			ValidationHelper.popElements($('intervalMinute'));
			Element.hide('trSchdRunOnce');
			Element.hide('trSchdRunTimes');
			break;
		case 1:
			ValidationHelper.pushElements($('execTimeHour'));
			ValidationHelper.pushElements($('execTimeMinute'));
			ValidationHelper.popElements($('startTimeHour'));
			ValidationHelper.popElements($('startTimeMinute'));
			ValidationHelper.popElements($('endTimeHour'));
			ValidationHelper.popElements($('endTimeMinute'));
			ValidationHelper.popElements($('intervalHour'));
			ValidationHelper.popElements($('intervalMinute'));
			Element.show('trSchdRunOnce');
			Element.hide('trSchdRunTimes');
			break;
		case 2:
			ValidationHelper.popElements($('execTimeHour'));
			ValidationHelper.popElements($('execTimeMinute'));
			ValidationHelper.pushElements($('startTimeHour'));
			ValidationHelper.pushElements($('startTimeMinute'));
			ValidationHelper.pushElements($('endTimeHour'));
			ValidationHelper.pushElements($('endTimeMinute'));
			ValidationHelper.pushElements($('intervalHour'));
			ValidationHelper.pushElements($('intervalMinute'));
			Element.hide('trSchdRunOnce');
			Element.show('trSchdRunTimes');
			break;
		defalut:
			break;
	}
}

/*
*?????????????????????
*/
oXWebSite.on('afterrender', function(oTrans, oJson){
	var dom = $('ScheduleModeBox');
	$('rdSchdMode_' + dom.getAttribute("_value")).click();
});

function checkTime(){
	var aStartTimeHour = $F('startTimeHour');
	var aStartTimeMinute = $F('startTimeMinute');
	var aEndTimeHour = $F('endTimeHour');
	var aEndTimeMinute = $F('endTimeMinute');
	var startTimeHourInt = parseInt(aStartTimeHour, 10);
	var startTimeMinuteInt = parseInt(aStartTimeMinute, 10);
	var endTimeHourInt = parseInt(aEndTimeHour, 10);
	var endTimeMinuteInt = parseInt(aEndTimeMinute, 10);
	if(startTimeHourInt > endTimeHourInt || (startTimeHourInt == endTimeHourInt && startTimeMinuteInt >= endTimeMinuteInt)){
		return false;
	}
	return true;
}

function checkTime2(){
	var aStartTimeHour = $F('startTimeHour');
	var aStartTimeMinute = $F('startTimeMinute');
	var aEndTimeHour = $F('endTimeHour');
	var aEndTimeMinute = $F('endTimeMinute');
	var aIntervalHour = $F('intervalHour');
	var aIntervalMinute = $F('intervalMinute');
	var startTimeHourInt = parseInt(aStartTimeHour, 10);
	var startTimeMinuteInt = parseInt(aStartTimeMinute, 10);
	var endTimeHourInt = parseInt(aEndTimeHour, 10);
	var endTimeMinuteInt = parseInt(aEndTimeMinute, 10);
	var IntervalHourInt = parseInt(aIntervalHour, 10);
	var IntervalMinuteInt = parseInt(aIntervalMinute, 10);
	var spanTime1 = (endTimeHourInt - startTimeHourInt) * 60 + (endTimeMinuteInt - startTimeMinuteInt);
	var spanTime2 = IntervalHourInt * 60 + IntervalMinuteInt;

	if((IntervalHourInt == 0 && IntervalMinuteInt == 0) || spanTime1 < spanTime2){
		return false;
	}
	return true;
}
/*.....................????????????????????????????????????.....................by@sj 20111221*/
function checkTime3(){
	var aStartTimeHour = $F('startTimeHour');
	var aStartTimeMinute = $F('startTimeMinute');
	var aEndTimeHour = $F('endTimeHour');
	var aEndTimeMinute = $F('endTimeMinute');
	var aIntervalHour = $F('intervalHour');
	var aIntervalMinute = $F('intervalMinute');
	if(aStartTimeHour==''||aStartTimeMinute==''||aEndTimeHour==''||aEndTimeMinute==''||aIntervalHour==''||aIntervalMinute=='') {
		return false;
	}
	return true;
}

/*
*??????????????????????????????
*/
oXWebSite.on('beforesave', function(aCombine, oHelper){
	if($('rdSchdMode_2').checked){
		if(!checkTime()){
			Ext.Msg.warn(wcm.LANG.WEBSITE_ALERT_2||"????????????????????????/??????????????????", function(){
				try{
					oTabPanel.setActiveTab('tab-advance');
					$('startTimeHour').focus();
					$('startTimeHour').select();
				}catch(error){
					//just skip it
				}
			});
			return false;
		}
		if(!checkTime2()){
			Ext.Msg.warn(wcm.LANG.WEBSITE_ALERT_3||"???????????????0??????????????????????????????", function(){
				try{
					oTabPanel.setActiveTab('tab-advance');
					$('intervalHour').focus();
					$('intervalHour').select();
				}catch(error){
					//just skip it
				}
			});
			return false;
		}
		/*.....................????????????????????????????????????.....................by@sj 20111221*/
		if(!checkTime3()){
			Ext.Msg.warn(wcm.LANG.WEBSITE_ALERT_4||"????????????????????????", function(){
				try{
					oTabPanel.setActiveTab('tab-advance');
					$('intervalHour').focus();
					//$('intervalHour').select();
				}catch(error){
					//just skip it
				}
			});
			return false;
		}
	}
});

/*
*??????PageEncoding
*/
oXWebSite.on('beforesave', function(aCombine, oHelper){
	var dom = $('siteLanguage');
	var index = dom.selectedIndex;
	if(index == -1) dom.selectedIndex = 0;
	$('pageEncoding').value = dom.options[dom.selectedIndex].getAttribute("Encode");
});

/*
*????????????????????????input???
*/
oXWebSite.on('beforesave', function(aCombine, oHelper){
	$('execTime').value = $('execTimeHour').value + ':' + $('execTimeMinute').value;
	$('startTime').value = $('startTimeHour').value + ':' + $('startTimeMinute').value;
	$('endTime').value = $('endTimeHour').value + ':' + $('endTimeMinute').value;
	$('interval').value = parseInt($('intervalHour').value,10) * 60 + parseInt($('intervalMinute').value,10);
});

/*
*????????????????????????
*/
oXWebSite.on('beforesave', function(aCombine, oHelper){
	var aResult = [];
	var sOutlineTemplate = $F('outlineTemplate').trim();
	if(sOutlineTemplate.length > 0){
		aResult.push(sOutlineTemplate);
	}else{
		aResult = [0];
	}
	var sOtherTemplates = $F('otherTemplates').trim();
	if(sOtherTemplates.length > 0){
		aResult.push(sOtherTemplates);
	}
	$('outlineTemplates').value = aResult.join(",");
});

/*
*???????????????????????????
*/
oXWebSite.on('save', function(oTrans, oJson, aCombine, oHelper){
	aCombine.push(oHelper.Combine('wcm6_publish', 'saveSitePublishConfig', this.form));
});

/*
*??????????????????????????????????????????
*/
oXWebSite.on('aftersave', function(oTrans, oJson, aCombine, oHelper){
	//???????????????...
	notifyFPCallback(oTrans.responseText.trim());
	FloatPanel.close();
});

/**
*????????????????????????
*/
function checkExists(oPostData, fCallback){
	window.requesting = true; //????????????????????????
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.JspRequest(
		WCMConstants.WCM6_PATH + "include/property_test_exist.jsp",
		oPostData,  true,
		function(transport, json){
			window.requesting = false;
			var result = transport.responseText.trim();
			(fCallback || Ext.emptyFn)(result);
			if(window.bClickSave && result == 'false'){
				bClickSave = false;
				onOk();
			}
		}
	);
}

/*
*??????????????????
*/
oXWebSite.on('afterrender', function(oTrans, oJson){
	ValidationHelper.registerValidations([
		{
			renderTo : 'siteDesc',
			type :'string',
			required :'',
			max_len :$F('ChnlNameFieldLen'),
			showid:'siteDescValidDesc',
			no_desc :''
		},
		{
			renderTo : 'siteName',
			type:'string',
			required:'',
			max_len:$F('ChnlNameFieldLen'),
			showid:'siteNameValidDesc',
			no_desc :'',
			rpc : function(){
				var dom = $('siteName');
				if(dom.value == dom.getAttribute("_value")){//no change.
					return;
				}
				checkExists({
					objId : $F('objectId'),
					objType : 103,
					SiteName : dom.value
				}, function(warning){
					if(warning == 'false'){
						dom.setAttribute("_value", dom.value);
						ValidationHelper.successRPCCallBack();
					}else{
						ValidationHelper.failureRPCCallBack(wcm.LANG.WEBSITE_11||"?????????????????????");
					}
				});
			}
		},
		{
			renderTo : 'dataPath',
			type:'common_char',
			required :'',
			max_len :$F('ChnlNameFieldLen'),
			no_desc :'',
			showid :'dataPathValidDesc',
			rpc : function(){
				var dom = $('dataPath');
				if(dom.value == dom.getAttribute("_value")){
					return;
				}
				checkExists({
					siteid : $F('objectId'),
					datapath : dom.value
				}, function(warning){
					if(warning == 'false'){
						dom.setAttribute("_value", dom.value);
						ValidationHelper.successRPCCallBack();
					}else{
						ValidationHelper.failureRPCCallBack(wcm.LANG.WEBSITE_12||"?????????????????????");
					}
				});
			}
		},
		{
			renderTo : 'rootDomain',
			type :'url',
			required :'',
			max_len :'250',
			no_desc :'',
			showid:'rootDomainValidDesc'
		},
		{
			renderTo : 'execTimeHour',
			type:'int',
			value_range:'0,23',
			showid:'exec',
			desc :wcm.LANG.WEBSITE_13||'??????'
		},
		{
			renderTo : 'execTimeMinute',
			type:'int',
			value_range:'0,59',
			showid:'exec',
			desc :wcm.LANG.WEBSITE_14||'??????'
		},
		{
			renderTo : 'startTimeHour',
			type:'int',
			value_range:'0,23',
			showid:'start',
			desc :wcm.LANG.WEBSITE_13||'??????'
		},
		{
			renderTo : 'startTimeMinute',
			type:'int',
			value_range:'0,59',
			showid:'start',
			desc :wcm.LANG.WEBSITE_14||'??????'
		},
		{
			renderTo : 'endTimeHour',
			type:'int',
			value_range:'0,23',
			showid:'end',
			desc :wcm.LANG.WEBSITE_13||'??????'
		},
		{
			renderTo : 'endTimeMinute',
			type:'int',
			value_range:'0,59',
			showid:'end',
			desc :wcm.LANG.WEBSITE_14||'??????'
		},
		{
			renderTo : 'intervalHour',
			type:'int',
			value_range:'0,23',
			showid:'interv',
			desc :wcm.LANG.WEBSITE_13||'??????'
		},
		{
			renderTo : 'intervalMinute',
			type:'int',
			value_range:'0,59',
			showid:'interv',
			desc :wcm.LANG.WEBSITE_14||'??????'
		}
	]);
	ValidationHelper.addValidListener(function(){
		FloatPanel.disableCommand('onOk', false);
	});
	ValidationHelper.addInvalidListener(function(){
		FloatPanel.disableCommand('onOk', true);
	});
	ValidationHelper.doSubmitAll();
});

Event.observe(window, 'load', function(){
	oXWebSite.load();
})

function onOk(){
	if(window.requesting) {//??????????????????????????????????????????????????????????????????????????????
		window.bClickSave = true;
		return false;
	}
	oXWebSite.save();
	return false;
}

/*
*FloatPanel relations.
*/
window.m_fpCfg = {
	m_arrCommands : [{
		disableWhenClicked : 1,
		cmd : 'onOk',
		name : wcm.LANG.WEBSITE_TRUE||'??????'
	}],
	size : [550, 440]
};

if(!window.hasEditRight){
	FloatPanel.disableCommand('onOk', true,true);
}

oXWebSite.on('afterrender', function(oTrans, oJson){
	if($F('objectId') == '0' || hasEditRight) return;
	FloatPanel.disableCommand('onOk', true, true);
	Form.disable("formData");
});

/*
*?????????????????????????????????????????????
*/
oXWebSite.on('afterrender', function(oTrans, oJson){
	Event.observe('statusesCanDoPubBox', 'click', function(){
		var box = $('statusesCanDoPubBox');
		var nodes = box.getElementsByTagName("input");
		for (var i = 0; i < nodes.length; i++){
			if(nodes[i].value == 10) continue;
			if(nodes[i].checked){
				Element.hide('publicstatustip');
				return;
			}
		}
		Element.show('publicstatustip');
	});
});

LockerUtil.register2(nObjectId, 103, true, "onOk");
//????????????????????????????????????
Event.observe(window, 'load', function(){
	if($('PublishLimit').value == "1")
		$('PublishLimitCtrl').checked = true;
	if($('PublishLimitCtrl').checked){
		Element.show($("showPubLimit"));
	}else{
		Element.hide($("showPubLimit"));
	}
});
function onPublishLimit(){
	if($("PublishLimitCtrl").checked){
		$("PublishLimit").value = 1;
		Element.show($("showPubLimit"));
	}else{
		$("PublishLimit").value = 0;
		Element.hide($("showPubLimit"));
	}
}