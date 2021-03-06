var PageConfig = function(){
	var params = {
		objectId: 0,
		channelId: 0,
		parentId: 0,
		siteId: 0,
		siteType : 0,
		isSpecial: false,
		isToped : false,
		isLinked : false,
		isInfoview : false,
		containsRight: true,
		infoviewenable : false,
		metadataenable : false,
		pathtype : 0,
		fieldsToHtml: 'chnlname,chnldesc'
	};
	params = Ext.Json.toUpperCase(params);

	return {
		init : function(){
			this.setParameters(location.search.parseQuery());
			this.setParameters({
				objectId : $F('objectId'),
				siteId : $F('siteId'),
				parentId : $F('parentId'),
				siteType : $F('siteType'),
				status : $F('status'),
				ChnlNameFieldLen : $F('ChnlNameFieldLen')
			});
			this.initChnlParams();
		},
		initChnlParams : function(){
			var nChnlType = $('chnlType').value;
			this.setParameters({
				chnlType : nChnlType,
				isLinked : (nChnlType == 11),
				isSpecial : (nChnlType != 0 && nChnlType != 13),
				isInfoview : (nChnlType == 13),
				isToped : (nChnlType == 1 || nChnlType == 2),
				pathtype : (nChnlType == 13 ? 'infoview' : this.getParameter('sitetype'))
			});
		},
		initChnlPage : function(){
			$('divClusterChannel').style.display = PageConfig.getParameter("islinked") ? 'none' : '';
			$('ContainsChildren').style.display = (PageConfig.getParameter("islinked") || PageConfig.getParameter("isinfoview") || PageConfig.getParameter("isspecial")) ? 'none' : '';
			$('divSelectInfoviews').style.display = PageConfig.getParameter("isinfoview") ? '' : 'none';
			$('selectinfoviewprinttemp').style.display = PageConfig.getParameter("isinfoview") ? '' : 'none';
			$('dvFilterAndOrder').style.display = PageConfig.getParameter("isinfoview") ? 'none' : '';
			var sDispMode = PageConfig.getParameter("isspecial") ? 'none' : '';
			$('dataPath').style.display = sDispMode;
			$('tab-advance-header').style.display = sDispMode;
			$('divBasicChannelOrder').style.display = PageConfig.getParameter("istoped") ? 'none' : '';
			$('divBasicPublishAttrs').style.display = sDispMode;
			$('divBasicWFAttrs').style.display = sDispMode;
			$('linkUrl').style.display = PageConfig.getParameter("islinked") ? '' : 'none';
			$('divLinkUrl').style.display = PageConfig.getParameter("islinked") ? '' : 'none';
			$('divSpecialCanPubAttr').style.display = PageConfig.getParameter("islinked") ? '' : 'none';
			$('divSpecialPublishAttr').style.display = PageConfig.getParameter("isspecial") ? '' : 'none';
			$('useDocLevel').style.display = PageConfig.getParameter("islinked") ? 'none' : '';
			ValidationHelper.notify('dataPath');
			ValidationHelper.notify('linkUrl');
		},
		getParameters : function(){
			var length = arguments.length;
			if(length <= 0) return params;
			var result = {};
			for(var index = 0; index < length; index++){
				result[arguments[index].toUpperCase()] = params[arguments[index].toUpperCase()];
			}
			return result;
		},
		setParameters : function(_oParams){
			for(var sKey in _oParams){
				params[sKey.toUpperCase()] = _oParams[sKey];
			}
		},
		getParameter : function(sName){
			return params[sName.toUpperCase()];
		},
		setParameter : function(sName, sValue){
			params[sName.toUpperCase()] = sValue;
		}
	};
}();

var PathConfig = function(){
	var paths = {};
	return {
		put : function(item){
			var type = new String(item.type).toLowerCase()
			paths[type] = item;
		},
		get : function(sKey){
			var sKey = new String(sKey).toLowerCase()
			return paths[sKey];
		}
	};
}();

var oTabPanel = new wcm.TabPanel({
	id : 'tabPanel',
	activeTab : 'tab-general'
});
PathConfig.put({
	type : 0,//?????????
	paths : [
		'../document/document_addedit.jsp',
		'../document/document_list.html',
		'../document/document_detail.jsp'
	]
});

PathConfig.put({
	type : 1,//?????????
	paths : [
		'../photo/photo_upload.jsp',
		'../photo/photo_thumb.html',
		'../photo/photo_show.jsp'
	]
});

PathConfig.put({
	type : 2,//?????????
	paths : [
		'../video/video_addedit.jsp',
		'',
		'../video/player.jsp'
	]
});

PathConfig.put({
	type : 4,//?????????
	paths : [
		'../document/document_addedit.jsp',
		'../document/document_list.html',
		'../document/document_detail.jsp'
	]
});

PathConfig.put({
	type : 'infoview',//??????
	paths : [
		'../infoview/infoview_document_addedit.jsp',
		'../infoview/infoviewdoc_list.html',
		'../infoview/infoview_document_show.jsp'
	]
});


Ext.ns('wcm.XChannel');
wcm.XChannel = function(config){
	wcm.XChannel.superclass.constructor.apply(this, arguments);
	Ext.apply(this, config);
	this.init();
};

Ext.extend(wcm.XChannel, wcm.util.Observable, {
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
		PageConfig.init();
		var aCombine = [];
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		if(this.fireEvent('beforeload', aCombine, oHelper) === false) return;
		if(aCombine.length > 0){
			oHelper.MultiCall(aCombine, this.render.bind(this));
		}else{
			this.render.bind(this);
		}
	},

	render : function(oTrans, oJson){
		oJson = oJson || {};
		oJson = oJson["MULTIRESULT"] || oJson;
		if(this.fireEvent('beforerender', oTrans, oJson) === false) return;
		this.fireEvent('afterrender', oTrans, oJson);
		Element.show("tabPanel");
	},

	save : function(){
		if(this.fireEvent('beforesave') === false) return;
		//var oHelper = new com.trs.web2frame.BasicDataHelper();
		this.saveAction();
//		oHelper.Call(this.service, 'save', this.form, true, this.saveAction.bind(this));
	},

	saveAction : function(oTrans, oJson){
//		$("objectid").value = $v(oJson, 'result');
		var aCombine = [];
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		//??????????????????
		var PostDataUtil = com.trs.web2frame.PostData;
		var postdata = PostDataUtil.form(this.form, function(m){return m;});
		aCombine.push(oHelper.Combine(
			this.service,
			'save',
			postdata
		));
		//????????????
		this.fireEvent('save', oTrans, oJson, aCombine, oHelper);
		if(aCombine.length > 0){
			oHelper.JspMultiCall('../../app/channel/channel_addedit_dowith.jsp', aCombine,
				this.afterSave.bind(this)
			);
//			oHelper.MultiCall(aCombine, this.afterSave.bind(this));
		}
	},

	afterSave : function(oTrans, oJson){
		this.fireEvent('aftersave', oTrans, oJson);
	}
});

var oXChannel = new wcm.XChannel({
	service : 'wcm6_channel',
	form : 'addEditForm',
	template : 'channel_template'
});

/**
*??????????????????????????????????????????????????????
*/
oXChannel.on('beforeload', function(aCombine, oHelper){
	if(PageConfig.getParameter("objectid") <= 0
			|| PageConfig.getParameter('isspecial')){
		return;
	}
	aCombine.push(oHelper.Combine(
		'wcm6_process',
		'getFlowUsedByChannel',
		PageConfig.getParameters("objectid")
	));
});

/**
*????????????????????????????????????????????????
*/
oXChannel.on('beforeload', function(aCombine, oHelper){
	aCombine.push(oHelper.Combine(
		'wcm6_process',
		'getFlowOptionAuth',
		Ext.apply({
			ChannelId : PageConfig.getParameter('parentid')
		}, PageConfig.getParameters('siteid'))
	));
});

/*
*??????????????????????????????????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("objectid") <= 0
			|| PageConfig.getParameter('isspecial')){
		return;
	}
	var nFlowId = $v(oJson, 'Flow.FlowId', false) || 0;
	PageConfig.setParameters({
		OldFlowId : nFlowId,
		FlowId : nFlowId
	});
	if(nFlowId <= 0) {
		return;
	}
	$('lnkFlow').innerHTML = $v(oJson, 'Flow.FlowName');
	$('lnkFlow').setAttribute('title', wcm.LANG.CHANNEL_20||'??????????????????');
});


/**
*??????????????????
*/
oXChannel.on('beforeload', function(aCombine, oHelper){
	try{
		if(PageConfig.getParameter('infoviewenable')){
			aCombine.push(oHelper.Combine(
				'wcm6_infoview',
				'queryChannelUsableInfoviews',
				PageConfig.getParameters('channelid')
			));
		}
	}catch(err){
		//just skip it
	}
});

/*
*????????????????????????
*/
oXChannel.on('beforerender', function(oTrans, oJson){
	try{
		if(PageConfig.getParameter("isInfoview")){
			/*
			if(!PageConfig.getParameter("infoviewenable")){
				Ext.Msg.$fail(wcm.LANG.CHANNEL_ALERT_4||'??????????????????????????????????????????????????????????????????????????????????????????????????????????????????', function(){
					FloatPanel.close();
				});
				return false;
			}
			*/
		}
	}catch(err){
		//just skip it
	}
});

/*
*???????????????????????????????????????????????????????????????????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	try{
		if(PageConfig.getParameter("infoviewenable")){
			var eOption = document.createElement("option");
			$('chnlType').options.add(eOption);
			eOption.value = 13;
			eOption.text = wcm.LANG.CHANNEL_16||'?????????????????????';
		}
	}catch(err){
		//just skip it
	}
});

/**
*?????????????????????
*/
oXChannel.on('beforeload', function(aCombine, oHelper){
	try{
		if(PageConfig.getParameter("objectid") > 0){
			if(PageConfig.getParameter('metadataenable')){
				aCombine.push(oHelper.Combine(
					'wcm6_MetaDataDef',
					'findViewByChannel',
					PageConfig.getParameters('channelid')
				));
			}
		}
	}catch(err){
		//just skip it
	}
});

/*
*????????????????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	try{
		if(PageConfig.getParameter("objectid") > 0){
			if(PageConfig.getParameter('metadataenable')){
				var nViewId = $v(oJson, 'MetaView.VIEWINFOID');
				if(nViewId != 0){
					Element.hide("lnkCheckQuery");
					Element.hide("lnkCheckOrderby");
				}
			}
		}
	}catch(err){
		//just skip it
	}
});

/*
*?????????????????????????????????????????????????????????
*/
oXChannel.on('beforerender', function(oTrans, oJson){
	if(PageConfig.getParameter("status") < 0) {
		Ext.Msg.warn(String.format((wcm.LANG.CHANNEL_ALERT_5||'????????????[ID={0}]????????????????????????????????????,?????????????????????????????????!'), PageConfig.getParameter("objectId")), function(){
			FloatPanel.close();
		});
		return false;
	}
});

oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("objectid") > 0){//??????
		var sel = $('chnlType');
		sel.value = sel.getAttribute("_value");
		$('spChannelType').innerHTML = sel.options[sel.selectedIndex].text;
		if(sel.value==13){
			Element.show('divSelectInfoviews');
			Element.show('selectinfoviewprinttemp');
			sel.options[sel.selectedIndex].selected;
		}
		return;
	}
	//??????
	Element.hide('spTextType');
	Element.show('spSelType');
	//switch4SpecialChnl(this.value);
});

/*
*????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	Event.observe('chnlName', 'blur', function(){
		var eChnlName = $('chnlName');
		var eChnlDesc = $('chnlDesc');
		if(eChnlDesc.value.trim() == '') {
			eChnlDesc.value = eChnlName.value;
			ValidatorHelper.forceValid(eChnlDesc);
		}
	});
});

/*
*?????????????????????????????????, ?????????????????????????????????????????????,??????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("sitetype") == 1){
		FloatPanel.setTitle(wcm.LANG.CHANNEL_17||"??????/??????????????????");
		Element.hide($("divAdvanceAttrs"));
		$("chnlType").disabled = true;
	}
});

/*
*?????????????????????????????????, ????????????????????????????????????????????????, ????????????????????????????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("sitetype") == 2){
		FloatPanel.setTitle(wcm.LANG.CHANNEL_18||"??????/??????????????????");
		Element.hide($("divCustomPagesSetting"));
		$("chnlType").disabled = true;
	}
});

/*
*????????????????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	var dom = $("chnlOrder");
	var opts = dom.options;
	if(PageConfig.getParameter("objectid") == 0){
		dom.selectedIndex = (opts.length - 1);
		return;
	}
	var nChnlOrder = dom.getAttribute("_value");
	dom.value = nChnlOrder;
	dom.selectedIndex -= 1;
	if(dom.selectedIndex < 0){
		dom.selectedIndex = 0;
	}
	opts[dom.selectedIndex].setAttribute("value", nChnlOrder);
	if(opts.remove){//IE
		opts.remove(dom.selectedIndex + 1);
	}else{//Not IE
		dom.removeChild(opts[dom.selectedIndex + 1]);
	}
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
	},
	{
		templateType : "3",
		selectType : 'radio',
		valueId : 'infoviewPrintTemplate',
		textId : 'spInfoviewPrintTemp'
	}
];

/*
*????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if(!window.hasEditRight) return;
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
*????????????????????????
*/
oXChannel.on('beforesave', function(aCombine, oHelper){
	var aResult = [];
	var sOutlineTemplate = $F('outlineTemplate').trim();
	if(sOutlineTemplate.length > 0){
		aResult.push(sOutlineTemplate);
	}else{
		aResult.push(0);
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
oXChannel.on('afterrender', function(oTrans, oJson){
	var doms = document.getElementsByName("selWorkflow");
	for (var i = 0; i < doms.length; i++){
		var dom = doms[i];
		Event.observe(dom, 'click', function(event){
			Event.stop(event);
			flowSelector.selWorkflow(this);
		}.bind(dom));
	}
});

function selectTemplate(dom){
	var index = dom.getAttribute("index");
	var config = TemplateConfig[index];
	var sTempNames = "";
	var nLen = 0;
	if(window.navigator.userAgent.toLowerCase().indexOf("msie")>=1){
		sTempNames = ($(config["textId"]).outerText.trim()==(wcm.LANG.CHANNEL_NONE||"???"))?"":$(config["textId"]).outerText;
		nLen = sTempNames.length;
		if(sTempNames.slice(nLen-3,nLen-1)==", "){
			sTempNames = sTempNames.slice(0,nLen-3);
		}
	}else{
		sTempNames = ($(config["textId"]).innerHTML.trim()==(wcm.LANG.CHANNEL_NONE||"???"))?"":$(config["textId"]).innerHTML;
		sTempNames = sTempNames.trim();
		nLen = sTempNames.length;
		if(sTempNames.slice(nLen-3,nLen)==", "){
			sTempNames = sTempNames.slice(0,nLen-3);
		}
	}
	var params = {
		siteId : PageConfig.getParameter("siteid"),
		templateType : config["templateType"],
		selectType : config["selectType"],
		templateIds : $(config["valueId"]).value,
		tempNames : sTempNames
	};
	if(PageConfig.getParameter("channelid") > 0){
		params["channelId"] = PageConfig.getParameter("channelid");
	}else if(PageConfig.getParameter("parentid") > 0){
		params["channelId"] = PageConfig.getParameter("parentid");
	}
	wcm.TemplatSelector.selectTemplate(params, function(_args){
		$(config["valueId"]).value = _args.selectedIds.join(",");
		Element.update(config["textId"], _args.selectedNames.join(', ') || (wcm.LANG.CHANNEL_NONE||"???"));
	});
}

/*
*????????????????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("objectid") == 0){
		Element.hide('spTextType');
		Element.show('spSelType');
		applyPaths(PageConfig.getParameter("pathtype"));
	}
});

/*
*?????????????????????????????????????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if($MsgCenter.getActualTop().m_bWithDeptFilter){
		Element.show('DoDeptFilter');
	}
});


/*
*???????????????????????????
*/
oXChannel.on('beforesave', function(aCombine, oHelper){
	if(PageConfig.getParameter("isinfoview")){
		if( $('selInfoviews').value == -1){
			Ext.Msg.warn(wcm.LANG.CHANNEL_19||'???????????????????????????????????????');
			return false;
		}
		$('InfoviewId').value = $('selInfoviews').value;
	}
});

/*
*?????????????????????
*/
oXChannel.on('beforesave', function(aCombine, oHelper){
	if($('chkOnlySearch').checked && $F('chnlQuery').trim() == ''){
		Ext.Msg.warn(wcm.LANG.CHANNEL_ALERT_6||"???????????????????????????????????????????????????????????????????????????????????????", function(){
			try{
				$('chnlQuery').focus();
			}catch(error){}
		});
		return false;
	}
});

/*
*??????????????????????????????
*/
oXChannel.on('beforesave', function(aCombine, oHelper){
	if($('rdSchdMode_2').checked){
		if(!checkTime()){
			Ext.Msg.warn(wcm.LANG.CHANNEL_ALERT_7||"????????????????????????/??????????????????", function(){
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
			Ext.Msg.warn(wcm.LANG.CHANNEL_ALERT_8||"???????????????0??????????????????????????????", function(){
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
*????????????????????????
*/
oXChannel.on('beforesave', function(aCombine, oHelper){
	if(PageConfig.getParameter("isspecial")) {
		$('onlyManager').value = $('sp-OnlyManager').checked ? 1: 0;
		if(PageConfig.getParameter("islinked")) {
			$('chkCanPub').checked = $('chkSpCanPub').checked ? 1: 0;
		}
	}else{
		$('onlyManager').value = $('ad-OnlyManager').checked ? 1: 0;
	}
});

/*
*????????????????????????input???
*/
oXChannel.on('beforesave', function(aCombine, oHelper){
	$('execTime').value = $('execTimeHour').value + ':' + $('execTimeMinute').value;
	$('startTime').value = $('startTimeHour').value + ':' + $('startTimeMinute').value;
	$('endTime').value = $('endTimeHour').value + ':' + $('endTimeMinute').value;
	$('interval').value = parseInt($('intervalHour').value,10) * 60 + parseInt($('intervalMinute').value,10);
});

/*
*???????????????????????????
*/
oXChannel.on('save', function(oTrans, oJson, aCombine, oHelper){
	aCombine.push(oHelper.Combine('wcm6_publish', 'saveChannelPublishConfig', 'addEditForm'));
});

/*
*???????????????????????????
*/
oXChannel.on('save', function(oTrans, oJson, aCombine, oHelper){
	if(PageConfig.getParameter("isinfoview")) {
		aCombine.push(oHelper.Combine(
			'wcm6_infoview',
			'setEmployedInfoView',
			Ext.apply({
				infoviewid: $('selInfoviews').value
			}, PageConfig.getParameters("channelid"))
		));
	}
});

/*
*??????????????????????????????
*/
oXChannel.on('save', function(oTrans, oJson, aCombine, oHelper){
	var nFlowId = PageConfig.getParameter("FlowId");
	if(PageConfig.getParameter("OldFlowId") == nFlowId) {//??????????????????
		return;
	}
	var flowData = {
		FlowId: nFlowId,
		ObjectId: PageConfig.getParameter('objectid')
	};
	var serviceMethod = nFlowId > 0 ? 'enableFlowToChannel' : 'disableFlowToChannel';
	aCombine.push(oHelper.Combine('wcm6_process', serviceMethod, flowData));
});

/*
*???????????????????????????????????????
*/
oXChannel.on('save', function(oTrans, oJson, aCombine, oHelper){
	var inheritextendfield = $('inheritextendfield');
	if(!inheritextendfield || !inheritextendfield.checked) return;
	var postData = {HostType : 101};
	aCombine.push(oHelper.Combine('wcm6_extendfield', 'inheritExtendFields', postData));
});

/*
*??????????????????????????????????????????
*/
oXChannel.on('aftersave', function(oTrans, oJson, aCombine, oHelper){
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
			if(result == 'false'){
				notifySaveChannel();
			}		
		}
	);
}

/**
*??????SQL????????????
*/
function checkSQL(_oPostData, _fValid, _fInValid){
	window.requesting = true; //????????????????????????
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.call('wcm6_channel','checkSQLValid', _oPostData, true, function(oTrans, oJson){
		window.requesting = false;
		var sSql = $v(oJson, 'sql');
		var sError = $v(oJson, 'error');
		if(sError == null) {
			(_fValid || Ext.emptyFn)();
			notifySaveChannel();
			return;
		}
		var sHtml = [
			'<div class="sqlOuter">',
				'<div class="sqlInner"', 'title="', sSql, '"', '>',
					'<span style="font-weight: bold;">'+(wcm.LANG.CHANNEL_53||'????????????')+'</span>: ', sSql,
				'</div>',
				'<div style="margin-bottom:5px;" title="', sError, '">',
					'<span style="font-weight:bold;">'+(wcm.LANG.CHANNEL_54||'????????????')+'</span>: ' + sError,
				'</div>',
			'</div>'
		].join("");
		(_fInValid || Ext.emptyFn)(sHtml);
	});
}

Event.observe(window, 'load', function(){
	oXChannel.load();
})

//??????????????????????????????
function setChannelFlowDisp(){
	$('spAltLnkFlow').innerHTML = $('lnkFlow').innerHTML;
	if((!PageContext.canAddFlow && $('lnkFlow').innerHTML == (wcm.LANG.CHANNEL_NONE||'???'))
		|| PageContext.readonly){

		Element.hide('lnkFlow');
		Element.show('spAltLnkFlow');
	}else{
		Element.hide('spAltLnkFlow');
		Element.show('lnkFlow');
	}
	//else
	if(PageContext.readonly) {
		Element.hide('imgSelFlow');
	}
}

/*
*????????????????????????
*/
function applyPaths(_nType){
	_nType = _nType || PageConfig.getParameter("pathtype");
	if(!PathConfig.get(_nType)) return;
	var paths = PathConfig.get(_nType)["paths"];
	$('contentAddEditPage').value = paths[0];
	$('contentListPage').value = paths[1];
	$('contentShowPage').value = paths[2];
}

function switchSchdDisp(_nMode){
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
	$('ScheduleMode').value = _nMode;
}

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
	//var spanTime2 = $F('interval');

	//if((IntervalHourInt == 0 && IntervalMinuteInt == 0) || spanTime1 < spanTime2){
	if(spanTime2 == 0 || spanTime1 < spanTime2){
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

//ge gfc add @ 2008-1-8
function getAdaptedInfoviewSelection(_bUsed){
	return (_bUsed ? 'selected' : '');
}

//======================================
//???????????????
var DIALOG_WORKFLOW_EDITOR = 'Dialog_Workflow_Editor';
var DIALOG_WORKFLOW_SELECTOR = 'Dialog_Workflow_Selector';
var flowSelector = {
	selWorkflow : function(){
		var params = Object.extend({
			CurrFlowId: PageConfig.getParameter("FlowId") || 0
		}, flowSelector.getOwnerInfo(true));

		//this.__openFlowSelector(params);
		wcm.Flow_AddEdit_Selector.selectFlow(params, function(_args){
			//PageContext.m_nFlowId = _args['FlowId'];
			PageConfig.setParameters({
				OldFlowId : _args['nOldFlowId'],
				FlowId : _args['FlowId']
			});
			$('lnkFlow').innerHTML = _args['FlowId'] == 0 ? (wcm.LANG.CHANNEL_NONE||'???') : _args['FlowName'];
			$('lnkFlow').setAttribute('title', wcm.LANG.CHANNEL_20||'??????????????????');

			setChannelFlowDisp();
			//return false;
		});
	},
	viewWorkflow : function(){
		var params = null;
		if(!(PageConfig.getParameter("FlowId") > 0)) {//?????????????????????
			params = {
				objectid: 0,
				OwnerId: PageConfig.getParameter("siteid"),
				OwnerType: 103,
				LoadView: 2,
				FlowId :  0
			};
		}else{// ??????
			params = {
				objectid: PageConfig.getParameter("FlowId"),//PageContext.m_nFlowId,
				readonly: true,
				LoadView: 1,
				FlowId : PageConfig.getParameter("FlowId"),
				ShowButtons : 1
			};
			Object.extend(params, flowSelector.getOwnerInfo(true));
		}

		this.__openFlowEditor(params);
	},
	__openFlowEditor : function(_params){
		wcm.Flow_AddEdit_Selector._addEditFlow(_params, function(_args){
			//?????????????????????????????????flowid
			if(_params['objectid'] == 0 && _args['id'] > 0) {
				PageConfig.setParameters({
					FlowId : _args['id']
				});
				$('lnkFlow').innerHTML = _args['name'];
				$('lnkFlow').setAttribute('title', wcm.LANG.CHANNEL_20||'??????????????????');
			}
		});
	},
	__openFlowSelector : function(_params){
		var aTop = (top.actualTop || top);
		if(aTop.m_eFlowSelectorDialog == null) {
			aTop.m_eFlowSelectorDialog = TRSDialogContainer.register(DIALOG_WORKFLOW_SELECTOR,
				wcm.LANG.CHANNEL_22||'???????????????', './workflow/workflow_select.html', '370px', '310px', true);
		}
		aTop.m_eFlowSelectorDialog.onFinished = function(_args){
			PageContext.m_nFlowId = _args['FlowId'];
			$('lnkFlow').innerHTML = _args['FlowId'] == 0 ? (wcm.LANG.CHANNEL_NONE||'???') : _args['FlowName'];
			$('lnkFlow').setAttribute('title', wcm.LANG.CHANNEL_20||'??????????????????');

			setChannelFlowDisp();
		}.bind(this);

		var positions = Position.getAbsolutePositionInTop(Event.element(Event.findEvent()));
		TRSCrashBoard.setMaskable(true);
		TRSDialogContainer.display(DIALOG_WORKFLOW_SELECTOR, _params, positions[0] + 25, positions[1] - 300);
	},
	getOwnerInfo : function(_bNotCareParent){//_bNotCareParent??????????????????????????????????????????
		var result = null;
		if(PageConfig.getParameter("objectid") > 0) {//????????????????????????????????????????????????
			result =  {
				OwnerId: PageConfig.getParameter("objectid"),
				OwnerType: 101
			}
		}else if(_bNotCareParent != true && PageConfig.getParameter("parentid") > 0) {//????????????????????????
			result = {
				OwnerId: PageConfig.getParameter("parentid"),
				OwnerType: 101
			}
		}else{//????????????????????????
			result = {
				OwnerId: PageConfig.getParameter("siteid"),
				OwnerType: 103
			}
		}

		return result;
	},
	getEditorDimensions : function(){
		var nRateWidth  = window.screen.width / 1024;
		var nRateHeight = window.screen.height / 768;
		return {width: nRateWidth * 650, height: nRateHeight * 500};
	}
}

/*
*??????????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	ValidationHelper.registerValidations([
		{
			renderTo : 'chnlDesc',
			type :'string',
			required :'',
			max_len :PageConfig.getParameter("ChnlNameFieldLen"),
			desc :wcm.LANG.CHANNEL_21||'??????????????????'
		},
		{
			renderTo : 'chnlName',
			type:'string',
			required:'',
			max_len:PageConfig.getParameter("ChnlNameFieldLen"),
			desc:wcm.LANG.CHANNEL_23||'??????????????????',
			rpc : function(){
				checkExists({
					objId : PageConfig.getParameter('objectid'),
					objType : 101,
					SiteId : PageConfig.getParameter('siteid'),
					chnlName : $F('chnlName')
				}, function(warning){
					if(warning == 'false'){
						ValidationHelper.successRPCCallBack();
					}else{
						ValidationHelper.failureRPCCallBack(wcm.LANG.CHANNEL_24||"?????????????????????");
					}
				});
			}
		},
		{
			renderTo : 'linkUrl',
			type : 'url',
			required : '',
			max_len : '200',
			showid : 'urlMsg',
			no_desc : ''
		},
		{
			renderTo : 'dataPath',
			type:'common_char',
			required :'',
			max_len :PageConfig.getParameter("ChnlNameFieldLen"),
			showid : 'dataPathValidDesc',
			no_desc :'',
			rpc : function(){
				checkExists(Ext.apply({
					'datapath' : $F('dataPath')
				}, PageConfig.getParameters('siteid', 'parentid', 'channelid')),
				function(warning){
					if(warning == 'false'){
						ValidationHelper.successRPCCallBack();
					}else{
						ValidationHelper.failureRPCCallBack(wcm.LANG.CHANNEL_25||"?????????????????????");
					}
				});
			}
		},
		{
			renderTo : 'chnlQuery',
			type:'string',
			max_len :'500',
			showid : 'queryByValidDesc',
			no_desc :'',
			rpc : function(){
				validchnlQuery();
			}
		},
		{
			renderTo : 'chnlOrderBy',
			type:'string',
			max_len :'500',
			showid : 'txtOrderByValidDesc',
			no_desc :'',
			rpc : function(){
				validChnlOrderBy();
			}
		},
		{
			renderTo : 'UNPUBTIME'
		},
		{
			renderTo : 'execTimeHour',
			type:'int',
			value_range:'0,23',
			showid:'exec',
			desc :wcm.LANG.CHANNEL_27||'??????'
		},
		{
			renderTo : 'execTimeMinute',
			type:'int',
			value_range:'0,59',
			showid:'exec',
			desc :wcm.LANG.CHANNEL_28||'??????'
		},
		{
			renderTo : 'startTimeHour',
			type:'int',
			value_range:'0,23',
			showid:'start',
			desc :wcm.LANG.CHANNEL_27||'??????'
		},
		{
			renderTo : 'startTimeMinute',
			type:'int',
			value_range:'0,59',
			showid:'start',
			desc :wcm.LANG.CHANNEL_28||'??????'
		},
		{
			renderTo : 'endTimeHour',
			type:'int',
			value_range:'0,23',
			showid:'end',
			desc :wcm.LANG.CHANNEL_27||'??????'
		},
		{
			renderTo : 'endTimeMinute',
			type:'int',
			value_range:'0,59',
			showid:'end',
			desc :wcm.LANG.CHANNEL_28||'??????'
		},
		{
			renderTo : 'intervalHour',
			type:'int',
			value_range:'0,23',
			showid:'interv',
			desc :wcm.LANG.CHANNEL_27||'??????'
		},
		{
			renderTo : 'intervalMinute',
			type:'int',
			value_range:'0,59',
			showid:'interv',
			desc :wcm.LANG.CHANNEL_28||'??????'
		},
		{
			renderTo : 'contentAddEditPage',
			type:'string',
			max_len :'200',
			showid : 'validation_desc_advance',
			desc :wcm.LANG.CHANNEL_31||'????????????????????????'
		},
		{
			renderTo : 'contentListPage',
			type:'string',
			max_len :'200',
			showid : 'validation_desc_advance',
			desc :wcm.LANG.CHANNEL_32||'??????????????????'
		},
		{
			renderTo : 'contentShowPage',
			type:'string',
			max_len :'200',
			showid : 'validation_desc_advance',
			desc :wcm.LANG.CHANNEL_33||'??????????????????'
		}
	]);
	ValidationHelper.addValidListener(function(){
		FloatPanel.disableCommand('saveChannel', false);
	}, "addEditForm");
	ValidationHelper.addInvalidListener(function(){
		FloatPanel.disableCommand('saveChannel', true);
	}, "addEditForm");
	ValidationHelper.doSubmitAll();
});

function onChnlTypeChange(event){
	PageConfig.initChnlParams();
	PageConfig.initChnlPage();
	toggleInheritExtendField();
	applyPaths();
}

oXChannel.on('afterrender', function(oTrans, oJson){
	Event.observe('chnlType', 'change', onChnlTypeChange);
	if($('selInfoviews')){
		Event.observe('selInfoviews', 'change', function(){
			if($('selInfoviews').value != -1){
				ValidationHelper.doSubmitAll();
				//FloatPanel.disableCommand('saveChannel', false);
			}
		});
	}
});

Ext.ns("PageContext");

/*
*???????????????????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	//??????????????????????????????????????????
	var bCanAddFlow = ($v(oJson, 'FlowOptionAuth.canAddFlow') == 'true');
	PageContext.canAddFlow = bCanAddFlow;
	setChannelFlowDisp();
	return;

	if (PageContext.parentid == 0){
		$('spParentName').innerHTML = wcm.LANG.CHANNEL_NONE||'???';
	}

	switchSchdDisp(parseInt(nSchdMode, 10));

	setTabDisp();
	switch4SpecialChnl($F('hdChnlType'));

	if(!(PageContext.isToped)){
		PageContext.initTsp();
	}

	//???????????????????????????
	try{
		$('txtChnlName').focus();
		$('txtChnlName').select();
	}catch(err){
		//???????????????????????????(???????????????????????????????????????), ??????????????????
	}
});

/*
*???????????????,??????????????????????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("objectid") == 0){
		$('chkCanPub').checked = true;
		$('chkSpCanPub').checked = true;
	}
});

/*
*???????????????,????????????????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	var nSchdMode = $F('ScheduleMode').trim();
	$('rdSchdMode_' + nSchdMode).click();
});

oXChannel.on('afterrender', function(oTrans, oJson){
	PageConfig.initChnlParams();
	PageConfig.initChnlPage();
});
oXChannel.on('afterrender', function(oTrans, oJson){
	var chnlOrderByConfig = {
		excludes : [{'selectordersp':false}],
		element : 'chnlOrderBy',
		blur : function(){
			validChnlOrderBy();
			return false;
		}
	};
	BlurMgr.add(chnlOrderByConfig);
});
oXChannel.on('afterrender', function(oTrans, oJson){
	var chnlQueryConfig = {
		excludes : [{'chnlQuerySp':false}],
		element : 'chnlQuery',
		blur : function(){
			validchnlQuery();
			return false;
		}
	};
	BlurMgr.add(chnlQueryConfig);
});
function validChnlOrderBy(){
	if($F('chnlOrderBy').length <= 0) return;
	checkSQL(
		Ext.apply({
			orderby : $F('chnlOrderBy')
		}, PageConfig.getParameters('channelid', 'siteid')),
		function(){
			$('txtOrderByValidDesc').removeAttribute('errorinfo');
			ValidationHelper.successRPCCallBack();
		},
		function(sErrorinfo){
			$('txtOrderByValidDesc').setAttribute('errorinfo', sErrorinfo);
			ValidationHelper.failureRPCCallBack(wcm.LANG.CHANNEL_26||"sql?????????");
		}
	);
}
function validchnlQuery(){
	if($F('chnlQuery').length <= 0) return;
	checkSQL(
		Ext.apply({
			queryby : $F('chnlQuery')
		}, PageConfig.getParameters('channelid', 'siteid')),
		function(){
			$('queryByValidDesc').removeAttribute('errorinfo');
			ValidationHelper.successRPCCallBack();
			if(document.releaseCapture)document.releaseCapture();
		},
		function(sErrorinfo){
			$('queryByValidDesc').setAttribute('errorinfo', sErrorinfo);
			ValidationHelper.failureRPCCallBack(wcm.LANG.CHANNEL_26||"sql?????????");
			if(document.releaseCapture)document.releaseCapture();
		}
	);
}
function popupEditor(){
	var lastValue = $('chnlOrderBy').value;
	var bHiddenDocTitle = $('bHiddenDocTitle').value;
	$('chnlOrderBy').value = "";
     var cb = wcm.CrashBoarder.get('selectOrderWay').show({
			title : wcm.LANG['CHANNEL_SELECT_ORDERWAY'] || '??????????????????',
			src : WCMConstants.WCM6_PATH + 'channel/channel_get_orderway.html',
			width:'500px',
			height : '200px',
			maskable : true,
			params : {initValue : lastValue,
				bHiddenDocTitle : bHiddenDocTitle
			},
			callback : function(orderValue){
				if(orderValue){
					$('chnlOrderBy').value = orderValue;
				}else{
					$('chnlOrderBy').value = lastValue;
				}
				$('chnlOrderBy').focus();
				var cbr = wcm.CrashBoarder.get("selectOrderWay");
				cbr.close();
			}
		});
}
function openSearchCondition(){
	var lastValue = $('chnlQuery').value;
	var bHiddenDocTitle = $('bHiddenDocTitle').value;
	$('chnlQuery').value = "";
	var cb = wcm.CrashBoarder.get('selectSearchConditonWay').show({
		title : wcm.LANG['CHANNEL_84'] || '??????????????????',
		src : WCMConstants.WCM6_PATH + 'channel/channel_get_search_condition.jsp',
		width:'650px',
		height : '250px',
		maskable : true,
		params : {initValue : lastValue,
			bHiddenDocTitle : bHiddenDocTitle
		},
		callback : function(queryValue){	
			if(queryValue){
				$('chnlQuery').value = queryValue;
			}else{
				$('chnlQuery').value = lastValue;
			}
			$('chnlQuery').focus();

			var cbr = wcm.CrashBoarder.get("selectSearchConditonWay");
			cbr.close();
		}
	});
}
//wenyh@2009-05-04 ??????????????????????????????
oXChannel.on('beforesave', function(aCombine, oHelper){
	if($("unpubjob").checked){
		var exetime = $F("UNPUBTIME") || "";
		if(exetime.trim() == ''){
			Ext.Msg.warn(wcm.LANG.CHANNEL_79 || "????????????????????????????????????????????????", function(){
				try{
					oTabPanel.setActiveTab('tab-advance');
					$('UNPUBTIME').focus();
					$('UNPUBTIME').select();
				}catch(error){
					//just skip it
				}
			});
			return false;
		}
		var s = exetime.split(" "); 
		var s1 = s[0].split("-"); 
		var s2 = s[1].split(":"); 
		var dtEtime = new Date(s1[0],parseInt(s1[1],10)-1,s1[2],s2[0],s2[1]);
		var now = new Date();
		if(Date.parse(now)>=Date.parse(dtEtime)||isNaN(Date.parse(dtEtime))){
			Ext.Msg.warn(wcm.LANG.CHANNEL_60||"?????????????????????????????????????????????", function(){
				try{
					oTabPanel.setActiveTab('tab-advance');
					$('UNPUBTIME').focus();
					$('UNPUBTIME').select();
				}catch(error){
					//just skip it
				}
			});
			return false;
		}
	}
})
/*
*?????????????????????????????????
*/
oXChannel.on('save', function(oTrans, oJson, aCombine, oHelper){
	var postData = {SenderType : 101,SchId:$F("UnpubSchId")||0};
	if($("unpubjob").checked){
		Object.extend(postData,{ETime:$F("UNPUBTIME")});
		aCombine.push(oHelper.Combine('wcm6_publish', 'setUnpubSchedule', postData));
	}else if($F("UnpubSchId")>0){
		Object.extend(postData,{Unset:1});
		aCombine.push(oHelper.Combine('wcm6_publish', 'setUnpubSchedule', postData));
	}
});

function onPubjobset(){
	if($("unpubjob").checked){
		Element.show($("unpubjobdatetime"));
	}else{
		Element.hide($("unpubjobdatetime"));
	}
}


function toggleInheritExtendField(){
	if(PageConfig.getParameter("objectid") != 0) return;
	var isVirtual = $('chkOnlySearch').checked
		|| PageConfig.getParameter("isLinked")
		|| PageConfig.getParameter("isToped");
	Element[isVirtual ? 'hide' : 'show']('extendfieldbox');
}

/*
*???????????????????????????????????????
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("objectid") != 0) return;
	Event.observe('chkOnlySearch', 'change', function(){
		toggleInheritExtendField();
	});
});


/*
*FloatPanel relations.
*/
window.m_fpCfg = {
	m_arrCommands : [{
		disableWhenClicked : 1,
		cmd : 'saveChannel',
		name : wcm.LANG.CHANNEL_TRUE||'??????'
	},{
		cmd : 'closefp',
		name : wcm.LANG.CHANNEL_CANCEL||'??????'
	}],
	withclose : false,
	size : [570, 530]
};
if(!window.hasEditRight){
	FloatPanel.disableCommand('saveChannel', true,true);
}
function notifySaveChannel(){
	if(!window.bClickSave) return;
	bClickSave = false;
	saveChannel();
}
function saveChannel(){
	if(window.requesting) {//??????????????????????????????????????????????????????????????????????????????
		window.bClickSave = true;
		return false;
	}
	oXChannel.save();
	return false;
}

//wenyh@2009-07-22 ?????????????????????????????????
oXChannel.on('beforesave', function(aCombine, oHelper){
	if($("rdSchdMode_2").checked){
		$("rdForcepub_2").removeAttribute("ignore");
	}else if($("rdSchdMode_1").checked){
		$("rdForcepub_1").removeAttribute("ignore");
	}
})

function closefp(){
	//setTimeout(function(){
		closeWindow();
	//},10);
	return false;
}

LockerUtil.register2(nObjectId, 101, true, "saveChannel");

/*???????????????????????????,???????????????????????????*/
oXChannel.on('afterrender', function(oTrans, oJson){
	Event.observe('queryByValidDesc', 'click', function(){
		var errorInfo = $('queryByValidDesc').getAttribute('errorInfo');
		if(!errorInfo) return;
		Ext.Msg.$fail(errorInfo);
	});
});

/*???????????????????????????,???????????????????????????*/
oXChannel.on('afterrender', function(oTrans, oJson){
	Event.observe('txtOrderByValidDesc', 'click', function(){
		var errorInfo = $('txtOrderByValidDesc').getAttribute('errorInfo');
		if(!errorInfo) return;
		Ext.Msg.$fail(errorInfo);
	});
});

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

oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("objectid") <= 0|| hasEditRight) return;
	FloatPanel.disableCommand('saveChannel', true, true);
	Form.disable("addEditForm");
});
