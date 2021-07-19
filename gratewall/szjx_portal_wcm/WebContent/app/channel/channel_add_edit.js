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
	type : 0,//文字库
	paths : [
		'../document/document_addedit.jsp',
		'../document/document_list.html',
		'../document/document_detail.jsp'
	]
});

PathConfig.put({
	type : 1,//图片库
	paths : [
		'../photo/photo_upload.jsp',
		'../photo/photo_thumb.html',
		'../photo/photo_show.jsp'
	]
});

PathConfig.put({
	type : 2,//视频库
	paths : [
		'../video/video_addedit.jsp',
		'',
		'../video/player.jsp'
	]
});

PathConfig.put({
	type : 4,//资源库
	paths : [
		'../document/document_addedit.jsp',
		'../document/document_list.html',
		'../document/document_detail.jsp'
	]
});

PathConfig.put({
	type : 'infoview',//表单
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
		//栏目自身信息
		var PostDataUtil = com.trs.web2frame.PostData;
		var postdata = PostDataUtil.form(this.form, function(m){return m;});
		aCombine.push(oHelper.Combine(
			this.service,
			'save',
			postdata
		));
		//其他信息
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
*修改栏目时，需要获取栏目的工作流信息
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
*获取当前用户是否能添加工作流权限
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
*修改栏目时，需要显示栏目的工作流信息
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
	$('lnkFlow').setAttribute('title', wcm.LANG.CHANNEL_20||'点击进行查看');
});


/**
*加载表单信息
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
*表单选件判断处理
*/
oXChannel.on('beforerender', function(oTrans, oJson){
	try{
		if(PageConfig.getParameter("isInfoview")){
			/*
			if(!PageConfig.getParameter("infoviewenable")){
				Ext.Msg.$fail(wcm.LANG.CHANNEL_ALERT_4||'由于您尚未启用或尚未购买表单选件，无法进行当前表单栏目的属性查看或编辑操作！', function(){
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
*渲染之后自定义表单选件时的处理，进行是否配置表单选件的判断
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	try{
		if(PageConfig.getParameter("infoviewenable")){
			var eOption = document.createElement("option");
			$('chnlType').options.add(eOption);
			eOption.value = 13;
			eOption.text = wcm.LANG.CHANNEL_16||'自定义表单栏目';
		}
	}catch(err){
		//just skip it
	}
});

/**
*加载元数据信息
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
*元数据选件渲染之后的处理
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
*对于删除到回收站中的栏目，提前进行过滤
*/
oXChannel.on('beforerender', function(oTrans, oJson){
	if(PageConfig.getParameter("status") < 0) {
		Ext.Msg.warn(String.format((wcm.LANG.CHANNEL_ALERT_5||'当前栏目[ID={0}]可能已被放入栏目回收站中,您暂时无法对其进行操作!'), PageConfig.getParameter("objectId")), function(){
			FloatPanel.close();
		});
		return false;
	}
});

oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("objectid") > 0){//修改
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
	//新建
	Element.hide('spTextType');
	Element.show('spSelType');
	//switch4SpecialChnl(this.value);
});

/*
*同步栏目描述信息
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
*图片选件渲染之后的处理, 图片库下的栏目没有高级属性面板,并且修改标题
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("sitetype") == 1){
		FloatPanel.setTitle(wcm.LANG.CHANNEL_17||"新建/修改图片分类");
		Element.hide($("divAdvanceAttrs"));
		$("chnlType").disabled = true;
	}
});

/*
*视频选件渲染之后的处理, 视频库下的栏目不允许设置栏目类型, 并隐藏高级面板中的自定义页面部分
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("sitetype") == 2){
		FloatPanel.setTitle(wcm.LANG.CHANNEL_18||"新建/修改视频栏目");
		Element.hide($("divCustomPagesSetting"));
		$("chnlType").disabled = true;
	}
});

/*
*渲染之后前一个栏目的处理
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
*绑定模板选择事件
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
*设置概览模板的值
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
*绑定工作流选择事件
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
		sTempNames = ($(config["textId"]).outerText.trim()==(wcm.LANG.CHANNEL_NONE||"无"))?"":$(config["textId"]).outerText;
		nLen = sTempNames.length;
		if(sTempNames.slice(nLen-3,nLen-1)==", "){
			sTempNames = sTempNames.slice(0,nLen-3);
		}
	}else{
		sTempNames = ($(config["textId"]).innerHTML.trim()==(wcm.LANG.CHANNEL_NONE||"无"))?"":$(config["textId"]).innerHTML;
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
		Element.update(config["textId"], _args.selectedNames.join(', ') || (wcm.LANG.CHANNEL_NONE||"无"));
	});
}

/*
*渲染之后自定义页面的处理
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("objectid") == 0){
		Element.hide('spTextType');
		Element.show('spSelType');
		applyPaths(PageConfig.getParameter("pathtype"));
	}
});

/*
*渲染之后控制栏目的部门过滤属性是否显示
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if($MsgCenter.getActualTop().m_bWithDeptFilter){
		Element.show('DoDeptFilter');
	}
});


/*
*表单是否选择的校验
*/
oXChannel.on('beforesave', function(aCombine, oHelper){
	if(PageConfig.getParameter("isinfoview")){
		if( $('selInfoviews').value == -1){
			Ext.Msg.warn(wcm.LANG.CHANNEL_19||'请为表单栏目选择一个表单！');
			return false;
		}
		$('InfoviewId').value = $('selInfoviews').value;
	}
});

/*
*检索条件的校验
*/
oXChannel.on('beforesave', function(aCombine, oHelper){
	if($('chkOnlySearch').checked && $F('chnlQuery').trim() == ''){
		Ext.Msg.warn(wcm.LANG.CHANNEL_ALERT_6||"由于指定了“只使用检索条件检索”，请输入检索条件后再提交！", function(){
			try{
				$('chnlQuery').focus();
			}catch(error){}
		});
		return false;
	}
});

/*
*校验时间范围的合法性
*/
oXChannel.on('beforesave', function(aCombine, oHelper){
	if($('rdSchdMode_2').checked){
		if(!checkTime()){
			Ext.Msg.warn(wcm.LANG.CHANNEL_ALERT_7||"开始时间不能大于/等于结束时间", function(){
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
			Ext.Msg.warn(wcm.LANG.CHANNEL_ALERT_8||"间隔时间为0或超出了运行设定时间", function(){
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
		/*.....................校验计划发布时间不能为空.....................by@sj 20111221*/
		if(!checkTime3()){
			Ext.Msg.warn(wcm.LANG.WEBSITE_ALERT_4||"设置时间不能为空", function(){
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
*设置发布设置信息
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
*把时间组装到提交input中
*/
oXChannel.on('beforesave', function(aCombine, oHelper){
	$('execTime').value = $('execTimeHour').value + ':' + $('execTimeMinute').value;
	$('startTime').value = $('startTimeHour').value + ':' + $('startTimeMinute').value;
	$('endTime').value = $('endTimeHour').value + ':' + $('endTimeMinute').value;
	$('interval').value = parseInt($('intervalHour').value,10) * 60 + parseInt($('intervalMinute').value,10);
});

/*
*保存栏目的发布信息
*/
oXChannel.on('save', function(oTrans, oJson, aCombine, oHelper){
	aCombine.push(oHelper.Combine('wcm6_publish', 'saveChannelPublishConfig', 'addEditForm'));
});

/*
*保存栏目的表单信息
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
*保存栏目的工作流信息
*/
oXChannel.on('save', function(oTrans, oJson, aCombine, oHelper){
	var nFlowId = PageConfig.getParameter("FlowId");
	if(PageConfig.getParameter("OldFlowId") == nFlowId) {//没有任何变化
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
*保存栏目的扩展字段继承信息
*/
oXChannel.on('save', function(oTrans, oJson, aCombine, oHelper){
	var inheritextendfield = $('inheritextendfield');
	if(!inheritextendfield || !inheritextendfield.checked) return;
	var postData = {HostType : 101};
	aCombine.push(oHelper.Combine('wcm6_extendfield', 'inheritExtendFields', postData));
});

/*
*保存完栏目所有信息之后的处理
*/
oXChannel.on('aftersave', function(oTrans, oJson, aCombine, oHelper){
	//刷新主页面...
	notifyFPCallback(oTrans.responseText.trim());
	FloatPanel.close();
});


/**
*校验属性的唯一性
*/
function checkExists(oPostData, fCallback){
	window.requesting = true; //正在进行校验请求
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
*校验SQL的合法性
*/
function checkSQL(_oPostData, _fValid, _fInValid){
	window.requesting = true; //正在进行校验请求
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
					'<span style="font-weight: bold;">'+(wcm.LANG.CHANNEL_53||'校验语句')+'</span>: ', sSql,
				'</div>',
				'<div style="margin-bottom:5px;" title="', sError, '">',
					'<span style="font-weight:bold;">'+(wcm.LANG.CHANNEL_54||'校验结果')+'</span>: ' + sError,
				'</div>',
			'</div>'
		].join("");
		(_fInValid || Ext.emptyFn)(sHtml);
	});
}

Event.observe(window, 'load', function(){
	oXChannel.load();
})

//设置工作流的控制显示
function setChannelFlowDisp(){
	$('spAltLnkFlow').innerHTML = $('lnkFlow').innerHTML;
	if((!PageContext.canAddFlow && $('lnkFlow').innerHTML == (wcm.LANG.CHANNEL_NONE||'无'))
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
*设置页面链接地址
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

/*.....................校验计划发布时间不能为空.....................by@sj 20111221*/
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
//工作流选择
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
			$('lnkFlow').innerHTML = _args['FlowId'] == 0 ? (wcm.LANG.CHANNEL_NONE||'无') : _args['FlowName'];
			$('lnkFlow').setAttribute('title', wcm.LANG.CHANNEL_20||'点击进行查看');

			setChannelFlowDisp();
			//return false;
		});
	},
	viewWorkflow : function(){
		var params = null;
		if(!(PageConfig.getParameter("FlowId") > 0)) {//可以新建并应用
			params = {
				objectid: 0,
				OwnerId: PageConfig.getParameter("siteid"),
				OwnerType: 103,
				LoadView: 2,
				FlowId :  0
			};
		}else{// 查看
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
			//服务器端应返回新建后的flowid
			if(_params['objectid'] == 0 && _args['id'] > 0) {
				PageConfig.setParameters({
					FlowId : _args['id']
				});
				$('lnkFlow').innerHTML = _args['name'];
				$('lnkFlow').setAttribute('title', wcm.LANG.CHANNEL_20||'点击进行查看');
			}
		});
	},
	__openFlowSelector : function(_params){
		var aTop = (top.actualTop || top);
		if(aTop.m_eFlowSelectorDialog == null) {
			aTop.m_eFlowSelectorDialog = TRSDialogContainer.register(DIALOG_WORKFLOW_SELECTOR,
				wcm.LANG.CHANNEL_22||'选择工作流', './workflow/workflow_select.html', '370px', '310px', true);
		}
		aTop.m_eFlowSelectorDialog.onFinished = function(_args){
			PageContext.m_nFlowId = _args['FlowId'];
			$('lnkFlow').innerHTML = _args['FlowId'] == 0 ? (wcm.LANG.CHANNEL_NONE||'无') : _args['FlowName'];
			$('lnkFlow').setAttribute('title', wcm.LANG.CHANNEL_20||'点击进行查看');

			setChannelFlowDisp();
		}.bind(this);

		var positions = Position.getAbsolutePositionInTop(Event.element(Event.findEvent()));
		TRSCrashBoard.setMaskable(true);
		TRSDialogContainer.display(DIALOG_WORKFLOW_SELECTOR, _params, positions[0] + 25, positions[1] - 300);
	},
	getOwnerInfo : function(_bNotCareParent){//_bNotCareParent，用于指定是否需要判断父栏目
		var result = null;
		if(PageConfig.getParameter("objectid") > 0) {//修改栏目时，直接传入当前栏目信息
			result =  {
				OwnerId: PageConfig.getParameter("objectid"),
				OwnerType: 101
			}
		}else if(_bNotCareParent != true && PageConfig.getParameter("parentid") > 0) {//在栏目下新建栏目
			result = {
				OwnerId: PageConfig.getParameter("parentid"),
				OwnerType: 101
			}
		}else{//在站点下新建栏目
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
*渲染之后添加默认校验
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	ValidationHelper.registerValidations([
		{
			renderTo : 'chnlDesc',
			type :'string',
			required :'',
			max_len :PageConfig.getParameter("ChnlNameFieldLen"),
			desc :wcm.LANG.CHANNEL_21||'栏目显示名称'
		},
		{
			renderTo : 'chnlName',
			type:'string',
			required:'',
			max_len:PageConfig.getParameter("ChnlNameFieldLen"),
			desc:wcm.LANG.CHANNEL_23||'栏目唯一标识',
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
						ValidationHelper.failureRPCCallBack(wcm.LANG.CHANNEL_24||"唯一标识不唯一");
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
						ValidationHelper.failureRPCCallBack(wcm.LANG.CHANNEL_25||"存放位置不唯一");
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
			desc :wcm.LANG.CHANNEL_27||'小时'
		},
		{
			renderTo : 'execTimeMinute',
			type:'int',
			value_range:'0,59',
			showid:'exec',
			desc :wcm.LANG.CHANNEL_28||'分钟'
		},
		{
			renderTo : 'startTimeHour',
			type:'int',
			value_range:'0,23',
			showid:'start',
			desc :wcm.LANG.CHANNEL_27||'小时'
		},
		{
			renderTo : 'startTimeMinute',
			type:'int',
			value_range:'0,59',
			showid:'start',
			desc :wcm.LANG.CHANNEL_28||'分钟'
		},
		{
			renderTo : 'endTimeHour',
			type:'int',
			value_range:'0,23',
			showid:'end',
			desc :wcm.LANG.CHANNEL_27||'小时'
		},
		{
			renderTo : 'endTimeMinute',
			type:'int',
			value_range:'0,59',
			showid:'end',
			desc :wcm.LANG.CHANNEL_28||'分钟'
		},
		{
			renderTo : 'intervalHour',
			type:'int',
			value_range:'0,23',
			showid:'interv',
			desc :wcm.LANG.CHANNEL_27||'小时'
		},
		{
			renderTo : 'intervalMinute',
			type:'int',
			value_range:'0,59',
			showid:'interv',
			desc :wcm.LANG.CHANNEL_28||'分钟'
		},
		{
			renderTo : 'contentAddEditPage',
			type:'string',
			max_len :'200',
			showid : 'validation_desc_advance',
			desc :wcm.LANG.CHANNEL_31||'内容新建修改页面'
		},
		{
			renderTo : 'contentListPage',
			type:'string',
			max_len :'200',
			showid : 'validation_desc_advance',
			desc :wcm.LANG.CHANNEL_32||'内容列表页面'
		},
		{
			renderTo : 'contentShowPage',
			type:'string',
			max_len :'200',
			showid : 'validation_desc_advance',
			desc :wcm.LANG.CHANNEL_33||'内容查看页面'
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
*绑定获取栏目发布信息的请求
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	//根据权限设置工作流的控制显示
	var bCanAddFlow = ($v(oJson, 'FlowOptionAuth.canAddFlow') == 'true');
	PageContext.canAddFlow = bCanAddFlow;
	setChannelFlowDisp();
	return;

	if (PageContext.parentid == 0){
		$('spParentName').innerHTML = wcm.LANG.CHANNEL_NONE||'无';
	}

	switchSchdDisp(parseInt(nSchdMode, 10));

	setTabDisp();
	switch4SpecialChnl($F('hdChnlType'));

	if(!(PageContext.isToped)){
		PageContext.initTsp();
	}

	//聚焦到第一个输入框
	try{
		$('txtChnlName').focus();
		$('txtChnlName').select();
	}catch(err){
		//有可能由于不能编辑(例如对象已经被其他用户锁定), 可以直接跳过
	}
});

/*
*新建栏目时,允许发布该栏目是否选择的处理
*/
oXChannel.on('afterrender', function(oTrans, oJson){
	if(PageConfig.getParameter("objectid") == 0){
		$('chkCanPub').checked = true;
		$('chkSpCanPub').checked = true;
	}
});

/*
*新建栏目时,计划发布的模式初始化处理
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
			ValidationHelper.failureRPCCallBack(wcm.LANG.CHANNEL_26||"sql不正确");
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
			ValidationHelper.failureRPCCallBack(wcm.LANG.CHANNEL_26||"sql不正确");
			if(document.releaseCapture)document.releaseCapture();
		}
	);
}
function popupEditor(){
	var lastValue = $('chnlOrderBy').value;
	var bHiddenDocTitle = $('bHiddenDocTitle').value;
	$('chnlOrderBy').value = "";
     var cb = wcm.CrashBoarder.get('selectOrderWay').show({
			title : wcm.LANG['CHANNEL_SELECT_ORDERWAY'] || '选择排序方式',
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
		title : wcm.LANG['CHANNEL_84'] || '选择检索条件',
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
//wenyh@2009-05-04 定时撤销发布相关设置
oXChannel.on('beforesave', function(aCombine, oHelper){
	if($("unpubjob").checked){
		var exetime = $F("UNPUBTIME") || "";
		if(exetime.trim() == ''){
			Ext.Msg.warn(wcm.LANG.CHANNEL_79 || "计划撤销发布时间不能早于当前时间", function(){
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
			Ext.Msg.warn(wcm.LANG.CHANNEL_60||"计划撤销发布时间在当前时间之前", function(){
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
*保存栏目的定时撤稿信息
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
*检索栏目没有扩展字段的继承
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
		name : wcm.LANG.CHANNEL_TRUE||'确定'
	},{
		cmd : 'closefp',
		name : wcm.LANG.CHANNEL_CANCEL||'取消'
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
	if(window.requesting) {//正在进行异步校验请求的话，需要请求回来之后，才能保存
		window.bClickSave = true;
		return false;
	}
	oXChannel.save();
	return false;
}

//wenyh@2009-07-22 添加定时发布属性的设置
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

/*单击错误提示信息时,弹出完整的出错信息*/
oXChannel.on('afterrender', function(oTrans, oJson){
	Event.observe('queryByValidDesc', 'click', function(){
		var errorInfo = $('queryByValidDesc').getAttribute('errorInfo');
		if(!errorInfo) return;
		Ext.Msg.$fail(errorInfo);
	});
});

/*单击错误提示信息时,弹出完整的出错信息*/
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
