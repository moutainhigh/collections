/*
*页面的初始化
*/
function init(){
	/*
	*自适应页面大小
	*/
	//autoJusty();

	/*
	*初始化置顶信息
	*/
	PgC.init();

	/*
	*初始化校验处理
	*/
	initValidation();

	/*
	*初始化加锁处理
	*/
	initLock();

	/*
	*初始化所属栏目的信息
	*/
	initChannelInfo();
}

/**
*自适应页面大小
*/
function autoJusty(){
	var cbSelf = wcm.CrashBoarder.get(window).getCrashBoard();
	if(!cbSelf){
		return;
	}
	try{
		var box = Ext.isStrict?document.documentElement:document.body;
		var minWidth = 700, minHeight = 250, maxWidth = 900, maxHeight = (window.screen.width>1024)?600:450;
		var realWidth = box.scrollWidth;		
		var realHeight = box.scrollHeight;
		realWidth = realWidth > maxWidth ? maxWidth : (realWidth < minWidth ? minWidth : realWidth);
		realHeight = realHeight > maxHeight ? maxHeight : (realHeight < minHeight ? minHeight : realHeight);		
	}catch(e){
		Ext.Msg.alert(e.message)
	}
	cbSelf.setSize(realWidth+"px",realHeight+"px");		
	box.style.overflowY = 'auto';
	box.style.overflowX = 'hidden';	
	if(Ext.isGecko){//如果IE设上这个,会出现两个滚动条
		document.body.style.overflowY = 'auto';
		document.body.style.overflowX = 'hidden';
	}
	cbSelf.center();
}

/*
*初始化加锁处理
*/
function initLock(){
	LockerUtil.register(m_nObjectId, 1936280531, true, {
		failToLock : function(_msg, _json){
			if($('saveAsDraft'))$('saveAsDraft').disabled = true;
			if($('btnSaveAndClose'))$('btnSaveAndClose').disabled = true;
			if($('btnSaveAndNew'))$('btnSaveAndNew').disabled = true;
			if($('btnSaveAndPublish'))$('btnSaveAndPublish').disabled = true;
			if($('btnSaveAndFlow'))$('btnSaveAndFlow').disabled = true;
			Ext.Msg.$timeAlert(String.format('<b>提示：</b> {0}' ,_msg), 5);
		}
	}, true);
}

/*
*初始化校验处理
*/
function initValidation(){
	ValidationHelper.addValidListener(function(){
		if($('saveAsDraft'))$('saveAsDraft').disabled = false;
		if($('btnSaveAndClose'))$('btnSaveAndClose').disabled = false;
		if($('btnSaveAndNew'))$('btnSaveAndNew').disabled = false;
		if($('btnSaveAndPublish'))$('btnSaveAndPublish').disabled = false;
		if($('btnSaveAndFlow'))$('btnSaveAndFlow').disabled = false;
	});
	ValidationHelper.addInvalidListener(function(){
		if($('saveAsDraft'))$('saveAsDraft').disabled = true;
		if($('btnSaveAndClose'))$('btnSaveAndClose').disabled = true;
		if($('btnSaveAndNew'))$('btnSaveAndNew').disabled = true;
		if($('btnSaveAndPublish'))$('btnSaveAndPublish').disabled = true;
		if($('btnSaveAndFlow'))$('btnSaveAndFlow').disabled = true;
	});
	ValidationHelper.initValidation();
};
/*
*初始化所属栏目的信息
*/
function initChannelInfo(){
	var selectChannel = $('selectChannel');
	if(selectChannel){
		Event.observe(selectChannel,'change',function(){
			$('channelId').value = selectChannel.value;
			m_nChannelId = selectChannel.value;
		})
		
	}
}

ValidationHelper.validByValidation = function(sValidation, sValue, elBox){
	if(!sValidation) return true;
	var validation = eval('({' + sValidation + '})');
	//valid required.
	if(validation["required"] == 1 && sValue.length <= 0 && !bSaveAsDraft){
		Ext.Msg.alert(String.format("{0}不能为空",validation["desc"] || ""),function(){
			Element.addClassName(elBox,'errorStyle');
			elBox.focus();
		});
		return false;
	}

	//valid max length.
	var maxLen = validation['max_len'] || 0;
	if(maxLen > 0 && sValue.length > maxLen){
		Ext.Msg.alert(String.format("{0}大于最大长度[{1}]",validation["desc"] || "",maxLen),function(){
			Element.addClassName(elBox,'errorStyle');
			//elBox.focus();
		});
		return false;
	}	
	if(Element.hasClassName(elBox,'errorStyle')) {
		Element.removeClassName(elBox,'errorStyle');
	}
	return true;
};

AjaxCaller.valid = function(){	
	var components = com.trs.ui.ComponentMgr.getAllComponents();
	for (var i = 0; i < components.length; i++){
		var validation = components[i].getProperty('validation');
		if(!validation) continue;
		var eleBox = components[i].getBox();
		if(!ValidationHelper.validByValidation(validation, components[i].getValue(),eleBox)) return false;
	}
	//流转信息的校验
	if(m_nFlowDocId > 0 && $('XProcessIframe')){
		var winFlowdoc = $('XProcessIframe').contentWindow;
		//如果选择的是诸如“签收”、“返工”或者“拒绝”之类的操作，则不校验，但要保存
		if(!winFlowdoc.isSpecialOption()) {
			if(!winFlowdoc.validate(false)) {
				return false;
			}
		}
	}
	//是否选择目标栏目的校验
	var currChannel = $('channelId').value;
	var selectChannel = $('selectChannel');
	if(selectChannel && currChannel == 0){
		Ext.Msg.alert("必须选择一个所属栏目！");
		return false;
	}
	if($('saveAsDraft'))$('saveAsDraft').disabled = true;
	if($('btnSaveAndClose'))$('btnSaveAndClose').disabled = true;
	if($('btnSaveAndNew'))$('btnSaveAndNew').disabled = true;
	if($('btnSaveAndPublish'))$('btnSaveAndPublish').disabled = true;
	if($('btnSaveAndFlow'))$('btnSaveAndFlow').disabled = true;
	return true;
};
var bCloseWindow = false;
var bSaveAsDraft = false;
var m_bPublish = false;
function saveData(bClose, _bPublish){
	if(!ValidationHelper.doValid('data')){
		return;
	}
	m_bPublish = _bPublish;
	bCloseWindow = bClose;
	bSaveAsDraft = false;
	var currStatusEl = $('docStatus');
	if(currStatusEl.value == 1028){//如果当前是草稿状态，则修改为新稿状态
		currStatusEl.value = 1;
	}
	setDocLevel();
	setFlowInfo();
	com.trs.ui.XAppendixMgr.upload(
		function(){
			AjaxCaller.save('data');
		},
		function(){
			Ext.Msg.alert(arguments[0]);
		}
	);
}
function saveAsDraft(){
	$ValidatorConfigs.setDraftMode(true);
	if(!ValidationHelper.doValid('data')){
		return;
	}
	var nDraftStatus = m_nDraftStatus;
	if(!m_nDraftStatus){
		nDraftStatus = 1028;//草稿状态
	}
	$('docStatus').value = nDraftStatus;
	bCloseWindow = true;
	bSaveAsDraft = true;
	setDocLevel();
	setFlowInfo();
	com.trs.ui.XAppendixMgr.upload(
		function(){
			AjaxCaller.save('data');
		},
		function(){
			Ext.Msg.alert(arguments[0]);
		}
	);
}

function setFlowInfo(){
	if(!$('frmFlow')) {
		return false;
	}else {
		document.getElementById("ToUserIds").value = getFlowDocInfo().ToUserIds;
		document.getElementById("NotifyTypes").value = getFlowDocInfo().NotifyTypes;
	}
}
function setDocLevel(){
	var docLevel = document.getElementById("DocLevel");
	var docLevel_slt = document.getElementById("docLevel_slt");
	// 兼容没有设置文档权限的情况，这种情况下，默认元数据的密级值为0，也即“普通级”
	if(!docLevel_slt){
		docLevel.value = "0";
	}else{
		docLevel.value = docLevel_slt.value;
	}
}
	
AjaxCaller.makePostData = function(){
	//开始makepostdata说明已经校验通过，故在此处开始进度条。
	ProcessBar.start(wcm.LANG.METAVIEWDATA_100 || '保存');
	//保存一些额外数据，此处为存放路径
	this.push('wcm6_publish', 'saveSitePublishConfig', 'data');
};
function processFlowDoc(){
	if(m_nFlowDocId == 0)
		return;
	var winFlowdoc = $('XProcessIframe').contentWindow;
	var sPostData = winFlowdoc.buildPostXMLData();
	if(sPostData && sPostData.length > 0) {
		new ajaxRequest({
			url : '/wcm/center.do',
			postBody: sPostData,
			contentType : 'multipart/form-data',
			method : 'post',
			onSuccess : function(_trans, _json){
				window.opener.CMSObj.createFrom({
					objType : 'IFlowContent',
					objId : m_nObjectId
				}).afteredit();
				dealWithWindow();
				return;
			},
			onFailure : function(_trans, _json){
				//alert(_trans.responseText);
			}
		});
	}else{
		dealWithWindow();
	}
	return;
}
AjaxCaller.onSuccess = function(transport, json){
	ProcessBar.close();
	var result = eval(transport.responseText);
	//工作流流转的处理
	if(m_nFlowDocId > 0){
		processFlowDoc();
		return;
	}
	try{
		if(window.opener){
			var nFromRelationDoc = getParameter('FromRelationDoc') || 0;
			if(nFromRelationDoc == 0){
				var info = {objType:WCMConstants.OBJ_TYPE_CHNLDOC, objId:result.chnldocId};
				var context = {objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:m_nChannelId};
				cmsobj = window.opener.CMSObj.createFrom(info, context);
				cmsobj[getParameter("ChnlDocId") > 0 ? 'afteredit' : 'afteradd']();
			}else{
				var sRelationName = getParameter("RelationName");
				Ext.applyIf(result,{
					RelationName : sRelationName
				});
				window.opener.refreshAddRelatedDocs(result);
			}
		}
	}catch(error){
	}
	dealWithWindow();
	return;
};

AjaxCaller.onFailure = function(trans, json){
	ProcessBar.close();
	if($('saveAsDraft'))$('saveAsDraft').disabled = false;
	if($('btnSaveAndClose'))$('btnSaveAndClose').disabled = false;
	if($('btnSaveAndNew'))$('btnSaveAndNew').disabled = false;
	if($('btnSaveAndPublish'))$('btnSaveAndPublish').disabled = false;
	if($('btnSaveAndFlow'))$('btnSaveAndFlow').disabled = true;

	var msg = wcm.LANG.AJAX_ALERT_2 || '与服务器交互时出现错误！';
	try{
		if(json) {
			json = json.FAULT;
			Ext.Msg.alert(json.MESSAGE || msg);
		}else{
			Ext.Msg.alert(trans.responseText);
		}
	}catch(ex){
		alert(trans.responseText);
	}
}

function dealWithWindow(){
	if(bCloseWindow){
		window.close();
	}else{
		var currHref = location.href;
		currHref = currHref.replace(/ObjectId=[^&]*/ig,'ObjectId=0');
		currHref = currHref.replace(/DocumentId=[^&]*/ig,'DocumentId=0');
		currHref = currHref.replace(/ChnlDocId=[^&]*/ig,'ChnlDocId=0');
		currHref = currHref.replace(/FlowDocId=[^&]*/ig,'FlowDocId=0');
		location.href = currHref;
	}
}

/**
*保存附件信息
*/
AjaxCaller.addListener('save', function(){
	var myDocAppendixes = com.trs.ui.ComponentMgr.get('myDocAppendixes');
	if(!myDocAppendixes) return;
	var appendixTypes = [10, 20, 40];
	for (var i = 0; i < appendixTypes.length; i++){
		var appendixXML = myDocAppendixes.getAppendixXML(appendixTypes[i]);
		if(!appendixXML) continue;
		this.push('wcm6_document', 'saveAppendixes', {
			"DocId" : m_nObjectId,
			"FlowDocId" : m_nFlowDocId,
			"AppendixType" : appendixTypes[i],
			"AppendixesXML" : appendixXML
		});
	}
});

/**
*保存图片附件信息
*/
AjaxCaller.addListener('save', function(){
	var myImgAppendixes = com.trs.ui.ComponentMgr.get('myImgAppendixes');
	if(!myImgAppendixes) return;
	var appendixXML = myImgAppendixes.getValue();
	if(!appendixXML) return;
	this.push('wcm6_document', 'saveAppendixes', {
		"DocId" : m_nObjectId,
		"FlowDocId" : m_nFlowDocId,
		"AppendixType" : 20,
		"AppendixesXML" : appendixXML
	});
	 
});

/**
* 20120521 by CC 
*保存文件附件信息。用于设置了分组视图的调用。分组视图后，原附件管理进行了拆分
*/
AjaxCaller.addListener('save', function(){
	var myDocAppendixes = com.trs.ui.ComponentMgr.get('myDocMultiAppendixes');
	if(!myDocAppendixes) return;
	//var appendixXML = myDocAppendixes.getAppendixXML(appendixTypes[i]);
	var appendixXML = myDocAppendixes.getValue();
	if(!appendixXML) return;
	this.push('wcm6_document', 'saveAppendixes', {
		"DocId" : m_nObjectId,
		"FlowDocId" : m_nFlowDocId,
		"AppendixType" : 10,
		"AppendixesXML" : appendixXML
	});
});

/**
* 20120521 by CC 
*保存链接信息。用于设置了分组视图的调用。分组视图后，原附件管理进行了拆分
*/
AjaxCaller.addListener('save', function(){
	var myDocAppendixes = com.trs.ui.ComponentMgr.get('myDocLinkAppendixes');
	if(!myDocAppendixes) return;
	//var appendixXML = myDocAppendixes.getAppendixXML(appendixTypes[i]);
	var appendixXML = myDocAppendixes.getValue();
	if(!appendixXML) return;
	this.push('wcm6_document', 'saveAppendixes', {
		"DocId" : m_nObjectId,
		"FlowDocId" : m_nFlowDocId,
		"AppendixType" : 40,
		"AppendixesXML" : appendixXML
	});
});

/**
*保存细览模板信息
*/
//注释掉单独保存细缆模板的信息，与保存定时发布一同发送
/*
AjaxCaller.addListener('save', function(){
	this.push('wcm6_publish', 'saveDocumentPublishConfig', {
		"ObjectId" : m_nObjectId,
		"DetailTemplate" : $("spDetailTemp").getAttribute('tempIds') || 0
	});
});
*/

/**
*保存置顶信息
*/
AjaxCaller.addListener('save', function(){
	var info = PgC.getTopsetInfo();
	if(info.TopFlag == 2){
		info.TopFlag = 3;
	}
	this.push('wcm6_document', 'setTopDocument', {
		"TopFlag" : info.TopFlag,
		"Position" : info.Position,
		"TargetDocumentId" : info.TargetDocumentId,
		"InvalidTime" : (PgC.TopFlag==1)?$('TopInvalidTime').value:'',
		"ChannelId" : m_nChannelId,
		"DocumentId" : m_nObjectId,
		"FlowDocId" : m_nFlowDocId
	});
});


/**
*保存引用及镜像信息
*/
AjaxCaller.addListener('save', function(){
	var myQuote = com.trs.ui.ComponentMgr.get('myQuote');
	if(!myQuote) return;

	var info = myQuote.getQuotes();//获取引用及镜像信息以及删除的引用栏目[array of quote, array of mirror, array of delChnlIds]

	if(info[0] && info[0].length > 0){
		this.push('wcm61_viewdocument', 'quote', {
			"ToChannelIds" : info[0].join(",")
		});
	}
	if(info[1] && info[1].length > 0){
		this.push('wcm61_viewdocument', 'mirror', {
			"ToChannelIds" : info[1].join(",")
		});
	}
	/**
	* by CC 20120424 如果另存为有需要删除的引用栏目关系，则会执行这个服务操作
	* 获取到需要删除的引用栏目IDS
	*/
	if(info[2] && info[2].length > 0 && (info[2].join(",") != -1)){
		this.push('wcm6_document', 'setQuote', {
			"DocumentId" : m_nObjectId,
			"fromChannelId" : m_nChannelId,
			"ToChannelIds" : "",
			"delChannelIds" : info[2].join(","),
			"FlowDocId" : ""
		});
	}
});

/**
*by CC 20120331 
*
*保存并发布
*/
AjaxCaller.addListener('save', function(){
	var bCanPub = $('bCanPub');
	if(bCanPub.value == "true" && m_bPublish){
		this.push('wcm6_viewdocument', 'basicPublish', {
			"OBJECTIDS" : m_nObjectId
		});
	}
});

/*
*保存工作流流转信息
*/
function getFlowDocInfo(){
	var winFlowdoc = $('frmFlow').contentWindow;
	if(!winFlowdoc.validate(false)) {
		return false;
	}
	var sPostData = winFlowdoc.buildData();
	return sPostData;
}
/*AjaxCaller.addListener('save', function(){
	var flowDocInfo = getFlowDocInfo();
	if(!flowDocInfo) return false;
	this.push('wcm61_metaviewdata', 'saveMetaViewData', {
		ToUserIds : flowDocInfo.ToUserIds,
		NotifyTypes : flowDocInfo.NotifyTypes,
		startInFlow : true
	});
});*/


function resizeIfNeed(_loadImg){
	if(!_loadImg) return;
	var maxHeight = 200;
	var maxWidth = 300;
	var loadImg = new Image();
	loadImg.src = _loadImg.src;
	var height = loadImg.height, width = loadImg.width;
	if(height > maxHeight || width > maxWidth){	
		if(height > width){
			width = maxHeight * width/height;
			height = maxHeight
		}else{
			height = maxWidth * height/width;
			width = maxWidth;
		}
		_loadImg.width = width;
		_loadImg.height = height;
	}
}
//
function onPubNojobset(){
	if($("unpubjob").checked){
		Element.show($("unpubjobdatetime"));
	}else{
		Element.hide($("unpubjobdatetime"));
	}
}
function onPubjobset(){
	if($("ip_DefineSchedule").checked){
		Element.show($("sp_PublishOnTime"));
		Element.hide($("sp_NoPublish"));
	}else{
		Element.hide($("sp_PublishOnTime"));
		Element.show($("sp_NoPublish"));
	}
}
//定时发布添加提醒
AjaxCaller.addListener('beforesave', function(){
	if($("ip_DefineSchedule") && $("ip_DefineSchedule").checked){
		var exetime = $F("ScheduleTime") || "";
		if(exetime.trim() == ''){
			Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_262 || "定时发布时间不能为空", function(){
				try{
					//oTabPanel.setActiveTab('tab-advance');
					$('ScheduleTime').focus();
					$('ScheduleTime').select();
				}catch(error){
					//just skip it
				}
			});
			return false;
		}
		var s = exetime.split(" "); 
		var s1 = s[0].split("-"); 
		var s2 = s[1].split(":"); 
		var dtEtime = new Date(s1[0],s1[1]-1,s1[2],s2[0],s2[1]);
		var now = new Date();
		if(Date.parse(now)>=Date.parse(dtEtime)||isNaN(Date.parse(dtEtime))){
			Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_260||"定时发布时间不能早于当前时间", function(){
				try{
					//oTabPanel.setActiveTab('tab-advance');
					$('ScheduleTime').focus();
					$('ScheduleTime').select();
				}catch(error){
					//just skip it
				}
			});
			return false;
		}
	}
});
//定时撤稿
AjaxCaller.addListener('beforesave', function(){
	if($("unpubjob") && $("unpubjob").checked){
		var exetime = $F("UnpubTime") || "";
		if(exetime.trim() == ''){
			Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_264 || "计划撤销发布时间不能为空", function(){
				try{
					//oTabPanel.setActiveTab('tab-advance');
					$('UnpubTime').focus();
					$('UnpubTime').select();
				}catch(error){
					//just skip it
				}
			});
			return false;
		}
		var s = exetime.split(" "); 
		var s1 = s[0].split("-"); 
		var s2 = s[1].split(":"); 
		var dtEtime = new Date(s1[0],s1[1]-1,s1[2],s2[0],s2[1]);
		var now = new Date();
		if(Date.parse(now)>=Date.parse(dtEtime)||isNaN(Date.parse(dtEtime))){
			Ext.Msg.warn(wcm.LANG.DOCUMENT_PROCESS_207||"计划撤销发布时间不能早于当前时间", function(){
				try{
					//oTabPanel.setActiveTab('tab-advance');
					$('UnpubTime').focus();
					$('UnpubTime').select();
				}catch(error){
					//just skip it
				}
			});
			return false;
		}
	}
});
/**/

/**
*保存定时发布
*/
AjaxCaller.addListener('save', function(){
	if($('ip_DefineSchedule') && $('ip_DefineSchedule').checked){
		this.push('wcm6_publish', 'saveDocumentPublishConfig', {
			"OBJECTID" : m_nObjectId,
			"SCHEDULETIME" : $('ScheduleTime').value,
			"FLOWDOCID" : m_nFlowDocId,
			"DetailTemplate" : $("spDetailTemp").getAttribute('tempIds') || 0
		});
	}else {
		this.push('wcm6_publish', 'saveDocumentPublishConfig', {
			"ObjectId" : m_nObjectId,
			"DetailTemplate" : $("spDetailTemp").getAttribute('tempIds') || 0
		});
	}
});
/**
*保存定时撤稿
*/
AjaxCaller.addListener('save', function(){
	if($('unpubjob') && $('unpubjob').checked){
		this.push('wcm6_publish', 'setUnpubSchedule', {
			"SENDERTYPE" : 605,
			"SCHID" : $F("UnpubSchId")||0,
			"ETIME" : $('UnpubTime').value
		});
	}
});

function preview(){
	if(!ValidationHelper.doValid('data')){
		return;
	}
	//校验同时为编辑器内容赋值
	if(AjaxCaller.valid() === false){
		return;
	}
	AjaxCaller.oHelper = new com.trs.web2frame.BasicDataHelper();
	//提取表单中所有字段的值
	var data = AjaxCaller.oHelper._makeData('data');
	
	var postdata = {};
	var myDocument = CMSObj.createFrom({
		objType : WCMConstants.OBJ_TYPE_DOCUMENT,
		objId : m_nObjectId,
		channelId : m_nChannelId
	}, {
		postdata : postdata
	});
	var m_oAjaxHelper = new com.trs.web2frame.BasicDataHelper();
	m_oAjaxHelper.Call('wcm6_metadatacenter', 'preview',data,true,
		function(_transport, _json){
			myDocument.previewUrl = $v(_json, 'result');
			window.open(myDocument.previewUrl);
		},function(_transport, _json){
			Ext.Msg.fault({
				message : $v(_json, "FAULT.MESSAGE"),
				detail :  $v(_json, "FAULT.DETAIL")
			},wcm.LANG.DIALOG_SERVER_ERROR || '与服务器交互时出错啦!');
		}
	);
	if($('saveAsDraft'))$('saveAsDraft').disabled = false;
	if($('btnSaveAndClose'))$('btnSaveAndClose').disabled = false;
	if($('btnSaveAndNew'))$('btnSaveAndNew').disabled = false;
	if($('btnSaveAndPublish'))$('btnSaveAndPublish').disabled = false;
	if($('btnSaveAndFlow'))$('btnSaveAndFlow').disabled = false;
}
function saveAndFlow(){
	$('Force2start').value = "true";
	saveData(true);
}
Event.observe(window ,'load' ,function(){
	Event.observe('ScheduleTime', 'focus', function(event){
		if($('saveAsDraft'))$('saveAsDraft').disabled = false;
		if($('btnSaveAndClose'))$('btnSaveAndClose').disabled = false;
		if($('btnSaveAndNew'))$('btnSaveAndNew').disabled = false;
		if($('btnSaveAndPublish'))$('btnSaveAndPublish').disabled = false;
		if($('btnSaveAndFlow'))$('btnSaveAndFlow').disabled = false;
	});
	Event.observe('UnpubTime', 'focus', function(event){
		if($('saveAsDraft'))$('saveAsDraft').disabled = false;
		if($('btnSaveAndClose'))$('btnSaveAndClose').disabled = false;
		if($('btnSaveAndNew'))$('btnSaveAndNew').disabled = false;
		if($('btnSaveAndPublish'))$('btnSaveAndPublish').disabled = false;
		if($('btnSaveAndFlow'))$('btnSaveAndFlow').disabled = false;
	});
});
function isValidOnlyField(){
	var el = ValidationHelper.currRPC.element;
	var bIsValid = el.getAttribute('identityField');
	if(!bIsValid) {
		return true;
	}
	var data = {
		'ViewId'	:	m_nViewId,
		'FieldValue':	el.value,
		'FieldName'	:	el.id,
		'ObjectId'	:	m_nObjectId

	};
	var m_oAjaxHelper = new com.trs.web2frame.BasicDataHelper();
	m_oAjaxHelper.Call('wcm61_metaviewdata', 'isValidOnlyField',data,true,
		function(_transport, _json){
			if(com.trs.util.JSON.value(_json, "result") == 'true'){
				ValidationHelper.successRPCCallBack();
			}else{
				ValidationHelper.failureRPCCallBack("唯一字段值已经存在");
			}
		},function(_transport, _json){
			Ext.Msg.fault({
				message : $v(_json, "FAULT.MESSAGE"),
				detail :  $v(_json, "FAULT.DETAIL")
			},wcm.LANG.DIALOG_SERVER_ERROR || '与服务器交互时出错啦!');
		}
	);
} 