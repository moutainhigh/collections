/*
*页面的初始化
*/
function init(){
	/*
	*自适应页面大小
	*/
	autoJusty();

	/*
	*初始化置顶信息
	*/
	PgC.init();

	/*
	*初始化字段值
	*/
	initFieldValue();
	/*
	*初始化校验处理
	*/
	initValidation();

	/*
	*初始化加锁处理
	*/
	initLock();

	//对栏目id初始化
	initChannelId();
}

/**
*自适应页面大小
*/
function autoJusty(){
	//额外做个处理
	if($('idxID')){
		$('idxID').disabled = true;
	}
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

/*---------------- 全屏打开编辑器 ------------------*/
function FullOpenEditor(bFull){
	$('divHeader').style.display = bFull? 'none' : '';
	$('data').style.paddingTop = bFull ? (0+"px") : '';
	if(Ext.isStrict){
		$('center').style.top = bFull?(0+"px"):(310+"px");
	}
}

/*
*初始化加锁处理
*/
function initLock(){
	LockerUtil.register(m_nObjectId, 1936280531, true, {
		failToLock : function(_msg, _json){
			$('btnSave').disabled = true;
			$('btnSaveAndFlow').disabled = true;
			Ext.Msg.$timeAlert(String.format('<b>提示：</b> {0}',_msg), 5);
		}
	}, true);
}

/*
*初始化校验处理
*/
function initValidation(){
	ValidationHelper.addValidListener(function(){$('btnSave').disabled = false;});
	ValidationHelper.addInvalidListener(function(){$('btnSave').disabled = true;});
	ValidationHelper.addValidListener(function(){$('btnSaveAndFlow').disabled = false;});
	ValidationHelper.addInvalidListener(function(){$('btnSaveAndFlow').disabled = true;});
	ValidationHelper.initValidation();
};


/**
*页面按钮信息
*/
/*window.m_cbCfg = {
	btns : [
		{
			text : wcm.LANG.METAVIEWDATA_100 || '保存',
			cmd : function(){
				saveData();
				return false;
			},
			id : "btnSave"
		},
		{text : wcm.LANG.METAVIEWDATA_101 || '关闭'}
	]
};*/


ValidationHelper.validByValidation = function(sValidation, sValue){
	if(!sValidation) return true;
	var validation = eval('({' + sValidation + '})');

	//valid required.
	if(validation["required"] == 1 && sValue.length <= 0){
		Ext.Msg.alert(String.format("{0} 不能为空",validation["desc"] || ""));
		return false;
	}

	//valid max length.
	var maxLen = validation['max_len'] || 0;
	if(maxLen > 0 && sValue.length > maxLen){
		Ext.Msg.alert(String.format("{0}大于最大长度[{1}]",validation["desc"] || "",maxLen));
		return false;
	}	
	return true;
};

AjaxCaller.valid = function(){	
	var components = com.trs.ui.ComponentMgr.getAllComponents();
	for (var i = 0; i < components.length; i++){
		var validation = components[i].getProperty('validation');
		if(!validation) continue;
		if(!ValidationHelper.validByValidation(validation, components[i].getValue())) return false;
	}
	return true;
};

function getChannelId(){
	var nChannelId = $('channelId').value;
	if(nChannelId == "" || nChannelId == 0){
		if($('channelIdOfMetaView')){
			nChannelId = $('channelIdOfMetaView').value;
		}
	}	
	return nChannelId;
}

function initChannelId(){
	if(m_nChannelId == 0){
		m_nChannelId = getChannelId();
		window.DocChannelId = m_nChannelId;
	}
}

function getHTML(){
	var oTrsEditor = document.getElementById('content_frame');
	var FCK = oTrsEditor.contentWindow.GetEditor();
	var oWindow = oTrsEditor.contentWindow.GetTrueEditor();
	if(oWindow.OfficeActiveX)
		oWindow.OfficeActiveX.UploadLocals();
	return FCK.GetHTML(true,true);//QuickGetHtml(true,false);	
}

function saveData(){
	//先对复杂编辑器赋值
	$('_editorValue_').value =  getHTML();
	if($('_editorValue_').value==null||$('_editorValue_').value.trim()==""){
		Ext.Msg.alert("正文内容不能为空.");
		return false;
	}
	// 对所属栏目进行校验
	if($('channelIdOfMetaView') && $('channelIdOfMetaView').value == 0){
		Ext.Msg.alert("请选择所属栏目！");
		return false;
	}
	com.trs.ui.XAppendixMgr.upload(
		function(){
			//先对栏目ID赋值
			$('channelId').value = getChannelId();
			m_nChannelId = $('channelId').value;
			AjaxCaller.save('data');
		},
		function(){
			Ext.Msg.alert(arguments[0]);
		}
	);
}

AjaxCaller.makePostData = function(){
	//开始makepostdata说明已经校验通过，故在此处开始进度条。
	ProcessBar.start(wcm.LANG.METAVIEWDATA_100 || '保存');
	//保存一些额外数据，此处为存放路径
	this.push('wcm6_publish', 'saveSitePublishConfig', 'data');
};

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
			var info = {objType:WCMConstants.OBJ_TYPE_CHNLDOC, objId:result.chnldocId};
			var context = {objType:WCMConstants.OBJ_TYPE_CHANNEL, objId:m_nChannelId};
			cmsobj = window.opener.CMSObj.createFrom(info, context);
			cmsobj[getParameter("ChnlDocId") > 0 ? 'afteredit' : 'afteradd']();
		}
	}catch(error){
	}
	var cbr = wcm.CrashBoarder.get(window);
	cbr.notify(result.chnldocId);
	cbr.close();
};

/**
*保存附件信息
*/
AjaxCaller.addListener('save', function(){
	var myDocAppendixes = com.trs.ui.ComponentMgr.get('myDocAppendixes');
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
*保存细览模板信息
*/
AjaxCaller.addListener('save', function(){
	this.push('wcm6_publish', 'saveDocumentPublishConfig', {
		"ObjectId" : m_nObjectId,
		"DetailTemplate" : $("spDetailTemp").getAttribute('tempIds') || 0
	});
});

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
				window.close();
				return;
			},
			onFailure : function(_trans, _json){
				//alert(_trans.responseText);
			}
		});
	}
	return;
}

function saveAndFlow(){
	$('Force2start').value = "true";
	saveData();
}
