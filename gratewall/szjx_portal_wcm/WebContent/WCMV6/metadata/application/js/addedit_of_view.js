var ViewDataEditor = {
	objectId			: getParameter('objectId') || getParameter('documentId') || 0,//对象的ID属性
	objectContainer		: 'objectContainer',//页面 form Element 容器
	objectForm			: 'objectForm', //表单form id
	objectTemplate		: 'objectTemplate', //textarea id
	offerBasicValidation: true,	//是否提供简单的校验
	_alertMsg_			: false,

	/**---------------------------------------选择实现的方法　STAET-------------------------------------**/
	/**
	*init data here before request.	if need, override the method.
	*/
	_beforeInitData : function(){
		if(this._alertMsg_) this._fAlert('beforeInitData...');
		if(this.beforeInitData) this.beforeInitData();
	},
	/**
	*init data here after request.	if need, override the method.
	*/
	_afterInitData : function(transport, json){
		if(this._alertMsg_) this._fAlert('afterInitData...');
		HTMLElementParser.parse();
		if(this.afterInitData) this.afterInitData(transport, json);
		adjustDimension();
	},
	/**
	*bind some events here.	if need, override the method.
	*/
	_bindEvents : function(){
		if(this._alertMsg_) this._fAlert('bindEvents...');
		if(this.bindEvents) this.bindEvents();
	},
	/**
	*compose the post data for ajax saving here. return {...} or formId.
	*if need, override the method.
	*/
	_getPostData : function(){
		if(this._alertMsg_) this._fAlert('getPostData...');
		if(this.getPostData) return this.getPostData();
		//default deal with.
		if(this.objectForm) return this.objectForm;
	},
	/**
	*valid here. if need, override the method.
	*@return	if valid return true, else return false.
	*/
	_validData : function(fValidCallBack){
		//init the SimpleEditor. 由于编辑器也要校验
		if(window.SimpleEditorCache){
			for (var i = 0; i < SimpleEditorCache.length; i++){
				var oInputElement = $(SimpleEditorCache[i][0]);
				var oAttachElement = $(SimpleEditorCache[i][1]);
				if(oInputElement && oAttachElement){
					//oInputElement.value = oAttachElement.innerHTML;
					oInputElement.value = top.getValueForEditor(oAttachElement);
					if(oInputElement.value == "<DIV>&nbsp;</DIV>"){
						oInputElement.value ="";
					}
				}
			}	
		}
		//valid the aNotNullElementsCache
		if(HTMLElementParser.aNotNullElementsCache){
			var elements = HTMLElementParser.aNotNullElementsCache;
			for (var i = 0; i < elements.length; i++){
				var sDesc = elements[i][0];
				var sName = elements[i][1];
				var aTargetElements = document.getElementsByName(sName);
				if(aTargetElements.length <= 0) continue;

				var bValid = true;
				switch(aTargetElements[0].tagName){
					case "INPUT" : 
						var sType = aTargetElements[0].type.toLowerCase();
						if(sType == "radio" || sType == "checkbox"){
							bValid = false;
							for (var j = 0; j < aTargetElements.length; j++){
								if(aTargetElements[j].checked){
									bValid = true;
									break;
								}
							}
						}else{
							bValid = aTargetElements[0].value.trim() != "";
						}
						break;
					case "SELECT":
					case "TEXTAREA":
						bValid = aTargetElements[0].value.trim() != "";
						break;
				}
				if(!bValid){
					$alert(sDesc + "不能为空.");
					return false;
				}
			}
		}
		if(this._alertMsg_) this._fAlert('validData...');
		if(this.validData) return this.validData(fValidCallBack);
		return true;
	},
	/**
	*execute the ajax request for saving. if need, override the method.
	*/
	_doSave : function(){
		if(this._alertMsg_) this._fAlert('doSave...');
		if(this.doSave) {
			this.doSave();
		}else{//default deal with.
			if($('FlowDocId')){
				$('FlowDocId').value = getParameter("FlowDocId") || 0;
			}
			//alert(Object.parseSource(com.trs.web2frame.PostData.form(this._getPostData())));
			ViewTemplateMgr.save(this.objectId, this._getPostData(), function(){
				$('objectId').value = ViewDataEditor.objectId;//getParameter('objectId') || 0;
				$('channelId').value = ViewDataEditor.getChannelId();
				$('viewId').value = getPageParams()["viewId"] || 0;
			}, this._afterDoSave.bind(this));				
		}
		try{
			//FloatPanel.close();		
		}catch(error){
			//window.close();
		}
	},
	_afterDoSave : function(transport, json){
		if(this._alertMsg_) this._fAlert('_afterDoSave...');
		//exec something after save the data.
		//deal with the releated appendixs.
		if(this.afterDoSave){
			this.afterDoSave();
		}else{
			SaveMgr.doRequest(transport, json);
		}
	},
	/**
	*保存之后的处理
	*/
	saveCallBack : function(transport, json){
		try{
			if(window.opener || window == window.parent){//兼容window.open方式
				try{
					ViewDataEditor.refreshMain(window.opener);
				}catch(err){
				}				
				window.opener = null;
				window.close();
			}else{//FloatPanel 方式
				ViewDataEditor.refreshMain(window);
				FloatPanel.close(true);		
			}
		}catch(error){
			if(ViewDataEditor._alertMsg_) ViewDataEditor._fAlert(error.message);
		}		
	},
	/**
	*刷新主窗口
	*/
	refreshMain : function(_oWindow){
		_oWindow = _oWindow || window;
		var mainWin = null;
		if(_oWindow.$main){
			mainWin = _oWindow.$main();
		}
		//需要重新加载页面
		if(mainWin && mainWin.gContainDynamicSelect) {
			var sOldSrc = mainWin.frameElement.src;
			var index = sOldSrc.indexOf("?");
			var src = sOldSrc.substr(0, index);
			var sParams = sOldSrc.substr(index);

			var oParams = sParams.toQueryParams(sParams);
			var sChannelId = this.getChannelId();
			if(sChannelId){
				oParams['channelid'] = sChannelId;
			}
			if(mainWin.PageContext && mainWin.PageContext.params){
				if(mainWin.PageContext.params["RightValue"]){
					oParams['RightValue'] = mainWin.PageContext.params["RightValue"];
				}
			}
			mainWin.frameElement.src = src + "?" + $toQueryStr(oParams);
			//mainWin.location.reload(true);
		}else{//ajax刷新页面
			_oWindow.$MessageCenter.sendMessage('main', 'PageContext.RefreshList', 'PageContext');
		}
	},
	/**
	*show the operate process, if need, override the method.
	*/
	_fAlert : function(msg){
		if(this.fAlert){
			this.fAlert(msg);
		}else{//default deal with.
			alert(msg);
		}
	},
	/**---------------------------------------选择实现的方法　END-------------------------------------**/
	
	/**---------------------------------------内部实现方法　START-------------------------------------**/
	_doSave_ : function(){
		if(this._alertMsg_) this._fAlert('_doSave_...');
		//init the fields from other Table.
		if(window.m_oOtherTableFields){
			for (var sTableName in m_oOtherTableFields){
				var oTableInfo = m_oOtherTableFields[sTableName];
				if(oTableInfo.id == null) continue;
				var oInput = document.createElement("<input type='hidden' name='" + sTableName + "Id'>");
				oInput.value = oTableInfo.id;
				$(this.objectForm).appendChild(oInput);
			}
		}
		//translate the input select from desc to value
		var aInput = $(this.objectForm).getElementsByTagName("input");
		for (var i = 0; i < aInput.length; i++){
			if(aInput[i].getAttribute("isInputable") != "true")
				continue;
			var sel = $(aInput[i].id + "_sel");
			sel.value = aInput[i].value;
			if(sel.selectedIndex != -1){
				aInput[i].value = sel.options[sel.selectedIndex].getAttribute("_value");
			}
		}

		//trigger the file upload.
		if(window.FileUploader){
			var cache = FileUploader.getCache();
			if(cache.length > 0){
				if(FileUploader.isEmptyValue()){
					this._doSave();	
					return;
				}
				if(!FileUploader.isUploadAll()){
					$beginSimpleRB("正在上传附件,请稍候...");
				}
				FileUploader.setUploadAll(function(){
					$endSimpleRB();
					this._doSave();			
				}.bind(this));
				for (var i = 0; i < cache.length; i++){
					cache[i].doUpload();
				}
			}else{
				this._doSave();
			}
		}else{
			this._doSave();
		}
	},
	saveData : function(){
		if(this._alertMsg_) this._fAlert('saveData...');
		if(this._validData(this._doSave_.bind(this))){
			this._doSave_();
		}
		return false;
	},
	initData : function(){	
		document.body.style.display = 'none';
		this._beforeInitData();
		this.loadData();
	},
	getChannelId : function(){
		var channelId = getParameter("channelid");
		if(channelId == "" || channelId == 0){
			channelId = getPageParams()["channelId"] || 0;
		}
		if(channelId == "" || channelId == 0){
			if($('channelIdOfMetaView')){
				channelId = $('channelIdOfMetaView').value;
			}
		}		
		return channelId;
	},
	loadData : function(){
		//ViewTemplateMgr.findById(this.objectId, {viewId : getPageParams()["viewId"]}, null, this.dataLoaded.bind(this));
		if(this.objectId == 0 || this.objectId == ""){
			 this.dataLoaded({}, {});
		}else{
			ViewTemplateMgr.findById(this.objectId, {
				isAddEdit : true,
				FlowDocId : getParameter("FlowDocId") || 0,
				channelId : this.getChannelId()
			}, null, this.dataLoaded.bind(this), function _f500(){
				//兼容视图被删除，但从站点访问的情况
				if(window.opener){
					var sRedirectURL = getWebURL() + "WCMV6/document/document_addedit.jsp?NoRedirect=1&";
					sRedirectURL += window.location.search.substring(1);
					window.location.href =  sRedirectURL;
				}
			});
		}
	},
	dataLoaded : function(transport, json){
		document.body.style.display = '';
		var sValue = TempEvaler.evaluateTemplater(this.objectTemplate, json);
		//fix the WebSphere bug.
		sValue = sValue.replace(/(< ?\/?\s*)_textarea(\s[^<]*>|>)/ig, '$1textarea$2');
//		Element.update(this.objectContainer, sValue);
		new Insertion.Top(this.objectContainer, sValue);
		this._afterInitData(transport, json);
		this.initCommand();
		this.initEvent();
	},
	initEvent : function(){
		this._bindEvents();
		if(ViewDataEditor.offerBasicValidation){
			ValidationHelper.initValidation();
		}
	},

	initCommand : function(){
		try{
			FloatPanel.addCloseCommand("关闭");
			FloatPanel.addCommand('savebtn', '保存', ViewDataEditor.saveData, ViewDataEditor);
		}catch(error){
			//just skip it. 兼容window.open方式
			//Event.observe(window, 'load', function(){
				Event.observe('okBtn', 'click', ViewDataEditor.saveData.bind(ViewDataEditor));
				Element.show('CommandButtons');
			//});	
		}
		if(window.sErrorMsg){
			try{
				$alert(sErrorMsg);
			}catch(error){
				alert(sErrorMsg);
			}
			try{
				FloatPanel.disableCommand('savebtn', true, true);
			}catch(error){
				//Event.observe(window, 'load', function(){
					Element.hide("okBtn");
				//}, false);
			}
		}
	}
	/**---------------------------------------内部实现方法　END-------------------------------------**/
};

/*-----------------------------------------command button start-------------------------------*/

LockerUtil.url = "../../../include/cmsobject_locked.jsp";
LockerUtil.register2(ViewDataEditor.objectId, 1936280531, false, 'savebtn');
/*-----------------------------------------command button end---------------------------------*/

/*-----------------------------------------Validation start-------------------------------*/
if(ViewDataEditor.offerBasicValidation){
	ValidationHelper.addValidListener(function(){
		try{
			FloatPanel.disableCommand('savebtn', false);
		}catch(error){
			//just skip it. 兼容window.open方式
			if(ViewDataEditor._alertMsg_) this._fAlert(error.message);
		}
		$('okBtn').disabled = false;
	}, ViewDataEditor.objectForm);
	ValidationHelper.addInvalidListener(function(){
		try{
			FloatPanel.disableCommand('savebtn', true);
		}catch(error){
			//just skip it. 兼容window.open方式
			if(ViewDataEditor._alertMsg_) this._fAlert(error.message);
		}
		$('okBtn').disabled = true;
	}, ViewDataEditor.objectForm);
}
/*-----------------------------------------Validation end---------------------------------*/

Event.observe(window, 'load', function(){
	ViewDataEditor.initData();
});

function adjustDimension(){
	var minWidth = 650, minHeight = 350, maxWidth = 800, maxHeight = 500;
	var realWidth = document.body.scrollWidth;		
	var realHeight = document.body.scrollHeight;
	realWidth = realWidth > maxWidth ? maxWidth : (realWidth < minWidth ? minWidth : realWidth);
	realHeight = realHeight > maxHeight ? maxHeight : (realWidth < minHeight ? minHeight : realHeight);
	//if(realHeight == maxHeight){
		$(ViewDataEditor.objectForm).getAttribute("style").overflow = "auto";
	//}
	try{
		FloatPanel.setSize(realWidth, realHeight);
		//FloatPanel.show();
	}catch(error){
		//just skip it. 兼容window.open方式
		if(ViewDataEditor._alertMsg_) this._fAlert(error.message);
	}
}

/*------------------------------------SaveMgr Start--------------------------------*/
function InteractiveMgr(){
	this.requests = [];
	this.responses = [];
	this.afters = [];
};

Object.extend(InteractiveMgr.prototype, {
	getHelper : function(){
		if(!this.oHelper){
			this.oHelper = new com.trs.web2frame.BasicDataHelper();	
		}
		return this.oHelper;
	},
	register : function(request, response){
		if(request){
			this.requests.push(request);
		}
		if(response){
			this.responses.push(response);
		}
	},

	doRequest : function(transport, json){
		var aCombin = [];
		var requests = this.requests;
		for (var i = 0; i < requests.length; i++){
			var aInfo = requests[i](transport, json);
			if(!aInfo) continue;
			if(!Array.isArray(aInfo)) aInfo = [aInfo];
			aCombin.push.apply(aCombin, aInfo);
		}
		if(aCombin.length <= 0) {
			this.doResponse.apply(this, arguments)
			return;
		}
		this.getHelper().MultiCall(aCombin, this.doResponse.bind(this));
	},

	doResponse : function(_transport, _json){
		var responses = this.responses;
		for (var i = 0; i < responses.length; i++){
			try{
				responses[i](_transport, _json);
			}catch(error){
			}
		}
		this.requests = [];
		this.responses = [];
		this.doAfter(_transport, _json);
	},

	addAfter : function(after){
		this.afters.push(after);
	},

	doAfter : function(_transport, _json){
		var afters = this.afters;
		for (var i = 0; i < afters.length; i++){
			try{
				afters[i](_transport, _json);
			}catch(error){
			}
		}
		this.afters = [];
	}
});

var LoadMgr = new InteractiveMgr();
var SaveMgr = new InteractiveMgr();
SaveMgr.addAfter(ViewDataEditor.saveCallBack.bind(ViewDataEditor));
/*--------------------------------------SaveMgr End-----------------------------------*/

SaveMgr.register(function(transport, json){
	if(!window.ReleatedAppendixesCache){
		return false;
	}
	try{
		var nDocumentId = $v(json, "METAVIEWDATA.METADATAID") || 0;
		var nFlowDocId = getParameter('FlowDocId') || 0;
	}catch(error){
		if(nDocumentId == 0){
			//alert("没有获得DocumentId");
			return false;
		}
	}
	return [
		SaveMgr.getHelper().Combine(
			'wcm6_document',
			'saveAppendixes',
			{
				"DocId" : nDocumentId,
				"AppendixType" : 10,
				"AppendixesXML" : getAppendixesXML(10),
				FlowDocId : nFlowDocId
			}
		),
		SaveMgr.getHelper().Combine(
			'wcm6_document',
			'saveAppendixes',
			{
				"DocId" : nDocumentId,
				"AppendixType" : 20,
				"AppendixesXML" : getAppendixesXML(20),
				FlowDocId : nFlowDocId
			}
		),
		SaveMgr.getHelper().Combine(
			'wcm6_document',
			'saveAppendixes',
			{
				"DocId" : nDocumentId,
				"AppendixType" : 40,
				"AppendixesXML" : getAppendixesXML(40),
				FlowDocId : nFlowDocId
			}
		)
	];
});

function getAppendixesXML(iType, oAppendixs){
	/**
	 *  <OBJECTS>
	 *		<OBJECT ID="" APPFILE="" SRCFILE="" APPLINKALT="" APPFLAG="" APPDESC=""/>
	 *	</OBJECTS>
	 */
	var appendixs = oAppendixs || window.ReleatedAppendixesCache['Type_'+iType];
	var arr = com.trs.util.JSON.array(appendixs,"APPENDIXES.APPENDIX")||[];
	var sParams = ["APPENDIXID","APPFILE","SRCFILE","APPLINKALT","APPFLAG","APPDESC"];
	var myValue = com.trs.util.JSON.value;
	var sRetVal = '<OBJECTS>';
	for(var i=0;i<arr.length;i++){
		var oAppendix = arr[i];
		sRetVal += '<OBJECT';
		for(var j=0;j<sParams.length;j++){
			var sName = sParams[j];
			var sValue = myValue(oAppendix,sName)||'';
			if(sName=='APPENDIXID'){
				if(isNaN(sValue)) sValue = 0;
			}
			if(iType==20&&sName=='APPFILE'){
				sRetVal += ' APPFILE="'+((myValue(oAppendix,'APPFILE.FILENAME')||'')+'').escape4Xml()+'"';
			}
			else if(sName=='APPENDIXID'){
				sRetVal += ' ID="'+sValue+'"';
			}
			else{
				sRetVal += ' '+sName+'="'+(sValue+'').escape4Xml()+'"';
			}
		}
		sRetVal += '/>';
	}
	sRetVal += '</OBJECTS>';
	return sRetVal;
}
/*--------------------------------------Releated Appendix End-----------------------------------*/


/*------------------------------------ saveDocumentPublishConfig Start--------------------------------*/
SaveMgr.register(function(transport, json){
	var dom = $('spDetailTemp');
	if(!dom) return false;
	var first = getFirstHTMLChild(dom);
	if(!first) return false;
	var templateId = first.getAttribute("_tempid");
	if(templateId == 0) return false;
	try{
		var nDocumentId = $v(json, "METAVIEWDATA.METADATAID") || 0;
	}catch(error){
		if(nDocumentId == 0){
			return false;
		}
	}
	return SaveMgr.getHelper().Combine(
		'wcm6_publish',
		'savedocumentpublishconfig',
		{
			objectId : nDocumentId,
			detailTemplate : first.getAttribute("_tempid")		
		}
	);
});
/*--------------------------------------saveDocumentPublishConfig End-----------------------------------*/
