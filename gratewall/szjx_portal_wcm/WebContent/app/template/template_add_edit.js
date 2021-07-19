//Validation的附加部分,主要用于处理多语种问题
var m_arrValidations = [{
		renderTo : 'txt_temp_name',
		desc : wcm.LANG.TEMPLATE_TEMPNAME||'模板名称'
	},{
		renderTo : 'TempDesc',
		desc : wcm.LANG.TEMPLATE_TEMPDESC||'模板描述'
	},{
		renderTo : 'txtTempExt',
		desc : wcm.LANG.TEMPLATE_TEXNAME||'文件扩展名'
	}, {
		renderTo : 'txtFileName',
		desc : wcm.LANG.TEMPLATE_OUTLINENAME||'发布文件名'
	}
];
Event.observe(window, 'load', function(){
	ValidationHelper.addValidListener(function(){
		$('btnSubmit').disabled = false;
	}, "addEditForm");
	ValidationHelper.addInvalidListener(function(){
		$('btnSubmit').disabled = true;
	}, "addEditForm");
	ValidationHelper.registerValidations(m_arrValidations);
	ValidationHelper.initValidation();
	PageContext.loadTemplateAttr();
	PageContext.notifyValid();
	if(screen.width>1024){
		$('txtTempExtMsg').style.position = 'relative';
		if(document.all){
			$('txtTempExtMsg').style.left = '236px';
		}else{
			$('txtTempExtMsg').style.left = '215px';
		}
	}
});
var PageContext = {
	params:{}
};
//lock when loaded while unlock when unloading
LockerUtil.register(getParameter('objectid'), 102, null, {
	failToLock : function(sMessage, json){
		Element.hide('btnSubmit');
		Ext.Msg.$timeAlert('<b>' + (wcm.LANG['TIPS'] || '提示:') + '</b>' + json.Message, 5);
	}
});

Object.extend(PageContext,{
	loadTemplateAttr : function(){
		var oParams = {
			objectid: getParameter('objectid'),
			hosttype: getParameter('hosttype'),
			hostid: getParameter('hostid'),
			temptype: parseInt(getParameter('temptype')),
			typestub: parseInt(getParameter('typestub')),
			fieldsToHtml: 'tempname,tempdesc'
		};
		
		Object.extend(PageContext.params, oParams);
		PageContext.determinDisplay();
		//$('selTempType').value = parseInt(getParameter('temptype'));
		window.loadAdditionalOptions();
		//focus the first
		try{
			if(getParameter('objectid')>0){
				$('txtContent').focus();
			}else{
				$('txt_temp_name').focus();
				$('txt_temp_name').select();
			}
		}catch(err){
			//TODO logger
			//alert(err.message);
		}		
	},
	determinDisplay : function(){
		var eSelTempType = $('selTempType');
		if(PageContext.params.typestub === 1) {
			eSelTempType.value = PageContext.params.temptype;
			eSelTempType.disabled = true;
		}
		var tempType = parseInt(eSelTempType.value);

		var eTempExt = $('txtTempExt');
		var eFileName = $('txtFileName');
		switch(tempType){
			case 1: // 概览
				Element.show(eFileName);
				Element.show(eTempExt);
				Element.show('divTempOutputFileAttr');
				Element.show('divTempFileExtAttr');
				break;
			case 2: // 细览
				var eTempExt = $('txtTempExt');
				Element.show(eTempExt);
				Element.hide('divTempOutputFileAttr');
				Element.show('divTempFileExtAttr');
				Element.hide('txtFileName');
				break;
			default: //嵌套或者新建
				Element.hide('divTempOutputFileAttr');
				Element.hide('divTempFileExtAttr');
				Element.hide('txtFileName');
				Element.hide('txtTempExt');
				break;
		}
		if(Element.visible(eTempExt) && eTempExt.value.trim() == '') {
			eTempExt.value = 'html';
		}
		if(Element.visible(eFileName) && eFileName.value.trim() == '') {
			eFileName.value = 'default';
		}
		
		PageContext.params.temptype = tempType;
		PageContext.notifyValid();
	},
	notifyValid : function(){
		Element.hide('txtTempExtMsg');
		Element.hide('txtFileNameMsg');
		ValidationHelper.forceValid('txtTempExt');
		ValidationHelper.forceValid('txtFileName');
		ValidationHelper.notify('txtTempExt');
		ValidationHelper.notify('txtFileName');		
	},
	saveTemplate : function(_eForm){
		if(!ValidationHelper.doValid(_eForm)) {
			return;
		}
		if($('selTempType').value < 0) {
			Ext.Msg.$alert(wcm.LANG.TEMPLATE_ALERT_0 || '请选择模板类型!');
			return;
		}
		// 提交前的模板内容校验
		var eContent = $('txtContent');
		if(eContent.value.trim() == '') {
			Ext.Msg.$alert(wcm.LANG.TEMPLATE_ALERT_1 || '请输入模板内容!', function(){
				eContent.focus();
				eContent.select();
				//$dialog().hide();
			});
			return false;
		}
		var oPostData = {
			TempId: $('hdObjectId').value,
			hosttype: $('hdHostType').value,
			hostid: $('hdHostId').value,
			TempName : $('txt_temp_name').value,
			TemplateText: eContent.value		
		};
		//$beginSimplePB('正在校验模板内容..', 2);
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call('wcm6_template', 'checkTemplateText', oPostData, true, function(_trans, _json){
			//$endSimplePB();
			var bSuccess = $v(_json, 'Report.IS_SUCCESS').trim().toLowerCase() == 'true';
			if(bSuccess) {
				this.doSave();
				return;
			}
			var cb = wcm.CrashBoard.get({
				id : 'tempcheck_rst2',
				width:'500px',
				height:'240px',
				title : wcm.LANG.TEMPLATE_RESULT1||'模板校验结果',
				url : WCMConstants.WCM6_PATH + 'template/template_check_result.html',
				params : _json,
				callback : function(_bResume){
					if(_bResume) {
						this.doSave();
						return;
					}
					$('txtContent').focus();
				}.bind(this),
				ok : wcm.LANG.TEMPLATE_ALERT8||'忽略错误',
				cancel : wcm.LANG.TEMPLATE_ALERT9||'返回修改'
			});
			cb.show();		
		}.bind(this))
	},
	doSave : function(){
		var eForm = $('addEditForm');
		var hostId = $('hdHostId').value;
		var hostType = $('hdHostType').value;
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call('wcm6_template', 'save', eForm, true, function(_trans, _json){
			var nSavedTempId = $v(_json, 'result');
			var nTempType = $('selTempType').value;
			var opener = window.opener;
			var nObjId = getParameter('objectid');
			var params = {
				objectId : nObjId,
				saveTempId : nSavedTempId,
				tempType : nTempType,
				hostId : hostId,
				hostType : hostType
			}
			if(opener) {
				opener.focus();
				if(getParameter('callback')){
					var fCallBack = eval("opener." + getParameter('callback'));
					if(fCallBack) {
						fCallBack(params);
					}
				}
				else{
					opener.$MsgCenter.$main({
						eventType : nObjId ? 'edit' : 'add',
						objId : nSavedTempId
					}).afteredit();
					/*var context = {
						objId : nSavedTempId,
						objType : WCMConstants.OBJ_TYPE_TEMPLATE
					};
					opener.CMSObj.createFrom(context)[parseInt(getParameter("objectId")) ? 'afteredit' : 'afteradd']();*/
				}
			}
			window.setTimeout(function(){
				window.close();
			}, 300);
		});
	},
	validateTemplate : function(){
		var oPostData = {
			TempId: $('hdObjectId').value,
			//ObjectId: $('hdObjectId').value,
			hosttype: $('hdHostType').value,
			hostid: $('hdHostId').value,
			TempName : $('txt_temp_name').value,
			TemplateText: $('txtContent').value
		};
		if($('txt_temp_name').value == ""){
			Ext.Msg.$alert(wcm.LANG.TEMPLATE_141||"请输入模板名.");
			return;
		}
		//$beginSimplePB('正在校验模板内容..', 2);
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call('wcm6_template', 'checkTemplateText', oPostData, true, function(_trans, _json){
			//$endSimplePB();
			_json.isJustCheck = true;
			var cb = wcm.CrashBoard.get({
				id : 'tempcheck_rst',
				width:'500px',
				height:'240px',
				title : wcm.LANG.TEMPLATE_RESULT1||'模板校验结果',
				url : WCMConstants.WCM6_PATH + 'template/template_check_result.html',
				params : _json,
				cancel : wcm.LANG.TEMPLATE_46 || '返回'
			});
			cb.show();
		}.bind(this));
	},
	validateWCAG2Template : function(){
		var oPostData = {
			//objectid: $('hdObjectId').value,
			hosttype: $('hdHostType').value,
			hostid: $('hdHostId').value,
			TemplateText: $('txtContent').value		
		};
		if($('txtContent').value.trim() == ''){
			Ext.Msg.$alert(wcm.LANG.TEMPLATE_137||'模板正文不能为空');
			return false;
		}
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call('wcm6_template', 'checkWCAG2TemplateText', oPostData, true, function(_trans, _json){
			var cb = wcm.CrashBoard.get({
				id : 'wcag2-template',
				width:'680px',
				height:'380px',
				title : wcm.LANG.TEMPLATE_55||'模板WCAG2校验结果',
				url : WCMConstants.WCM6_PATH + 'template/template_wcag2_check_result.html',
				params : _json,
				cancel : wcm.LANG.TEMPLATE_46 || '返回'
			});
			cb.show();
		}.bind(this));
	}
});

var ex = null
var bWizard = false;
function switchWizardDisp(_firer){
	if(!bWizard) {
		bWizard = true;
		_firer.innerHTML = wcm.LANG.TEMPLATE_INNER_0 || '隐藏TRS置标添加向导';
		//_firer.title = wcm.LANG.TEMPLATE_INNER_0 || '隐藏TRS置标添加向导';
	}else{
		bWizard = false;
		_firer.innerHTML = wcm.LANG.TEMPLATE_INNER_2 || '显示TRS置标添加向导';
		//_firer.title = wcm.LANG.TEMPLATE_INNER_2 || '显示TRS置标添加向导';
	}
	toggle('divWinzard');
	return true;
//	Ext.get('divWinzard').toggle();
}
function toggle(element) {
	element = $(element);
	return Element[Element.visible(element) ? 'hide' : 'show'](element);
}

//TRSCrashBoard.setMaskable(true);
var DIALOG_TEMPSEL_SOLO = "Template_Select_Solo";
var DIALOG_TEMPCHECK_RESULT = "Template_Check_Result";
var m_eTempCheckResult = null;
var m_eTempSelector = null;

function getTempfilterType(_tempType){
	switch(_tempType){
		case 0:
			return 10;
		case 1:
			return 11;
		case 2:
			return 12;
		defalut:
			return 12;
	}
}
function selectSourceTemp(){
	//debugger;
	if($('selTempType').value == -1) {
		Ext.Msg.$alert(wcm.LANG.TEMPLATE_ALERT_2 || '请选择模板类型后再进行此操作!');
		return;
	}
	
	var oParams = {
		objecttype: PageContext.params.hosttype||$('hdHostType').value,
		objectid: PageContext.params.hostid||$('hdHostId').value,
		templatetype: PageContext.params.temptype,
		templateids: PageContext.params.curr_tempid ? [PageContext.params.curr_tempid] : [],
		mustSelect: true
	};
	var cb = wcm.CrashBoard.get({
		id : 'tempsel_solo',
		width:'510px',
		height:'240px',
		title : wcm.LANG.TEMPLATE_SELECT||'选择模板',
		url : WCMConstants.WCM6_PATH + 'template/template_select_listsimple.html',
		params : oParams,
		callback : function(_args){
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			if(_args.selectedIds[0]==undefined||_args.selectedIds[0]==""){
				return false;
			}
			oHelper.Call('wcm6_template', 'findbyid', {ObjectId: _args.selectedIds[0]}, true, function(_trans, _json){
				PageContext.params.curr_tempid = $v(_json, 'Template.TempId');
				var sContent = $v(_json, 'Template.TempText');
				insertToTextArea(sContent);
			});
			
		},
		ok : wcm.LANG.TRUE||'确定',
		next : wcm.LANG.TEMPLATE_40||'刷新',
		cancel : wcm.LANG.CANCEL||'取消'
	});
	cb.show();
	$("next-tempsel_solo").style.display = '';
}	


//

var _TOTLE_STEPS =  3
var $winzard = {
	_rawStep: 1,
	_currStep: 1,
	_arrFramesSrc: null,
	_frame: null,
	_inited: false,
	init : function(){
		this._arrFramesSrc = new Array();
		this._arrFramesSrc[1] = 'template_wizard_step1.html';
		this._arrFramesSrc[2] = 'template_wizard_step2.jsp';
		this._arrFramesSrc[3] = 'template_wizard_step3.jsp';
		
		this._frame = $('frmStep');
		this._inited = true;
	},
	stepFirst : function(){
		if(!this._inited) {
			this.init();
		}

		this.doBeforeSteping();
		this._rawStep = 1;
		this._currStep = 1;
	
		this.disable('first');
		this.disable('pre');
		Element.hide('divCopyOpts');
		this.enable('next');

		this.sychClue();
		this.retrace(false);
	},
	stepNext : function(){
		if(!this._inited) {
			this.init();
		}
		this._rawStep = this._currStep;
		if(this._currStep >= _TOTLE_STEPS) {
			return ;
		}

		if(this.doSubmit() === false) {
			return;
		}
		//else
		this.doBeforeSteping();

		this._currStep++;

		//switch UI
		if(this._currStep == _TOTLE_STEPS) {
			this.disable('next');
			Element.show('divCopyOpts');
		}
		this.enable('pre');
		this.enable('first');
		this.sychClue();
		//return true;
	},
	stepPre : function(){
		if(!this._inited) {
			this.init();
		}
		this._rawStep = this._currStep;
		if(this._currStep <= 1) {
			return ;
		}

		//else
		this.doBeforeSteping();

		this._currStep--;
		this.retrace();
		if(this._currStep == 1) {
			this.disable('pre');
			this.disable('first');
		}
		this.enable('next');
		this.sychClue();
		Element.hide('divCopyOpts');
	},
	copy : function(){
		var bDo = this._frame.contentWindow.doCopy();
		if(bDo == false || bDo == undefined) {
			return;
		}
		Ext.Msg.$success(wcm.LANG.TEMPLATE_ALERT6||"已经复制到剪切板中!");
	},
	insert : function(){
		var sContent = this._frame.contentWindow.getText();
		insertToTextArea(sContent);
	},
	doSubmit : function(_frameIndex){
		var result = this._frame.contentWindow.doSubmit();
		return result;
	},
	retrace : function(_bRemainParams){
		var postData = this._frame.contentWindow.getPageParams();
		var sUrl = this._arrFramesSrc[this._currStep];
		if(_bRemainParams != false && typeof(postData) == 'string') {
			sUrl += '?' + postData;
		}
		this._frame.src = sUrl;
	},
	disable : function(_appendix){
		$('lnk_' + _appendix).disabled = true;
	},
	enable : function(_appendix){
		$('lnk_' + _appendix).disabled = false;
	},
	enableOptions : function(_bFlag){
		if(_bFlag === false) {
			if(!(this.m_bFirstShowMask == false)) {
				Position.clone($('divWiz_Right'), $('divMask'));
				this.m_bFirstShowMask = false;
			}
			Element.show('divMask');
		}else{
			Element.hide('divMask');
		}
	},
	doBeforeSteping : function(){
		this.enableOptions(false);
		Element.hide('spFrmStep');
		Element.show('spStepingPanel');
	},
	doAfterSteped : function(){
		Element.hide('spStepingPanel');
		Element.show('spFrmStep');
		this.enableOptions(true);
	},
	sychClue : function(){
		for (var i = 1; i < 4; i++){
			if(i == this._currStep) {
				$('step_clue_' + i).className = 'listOrderCurrent';
				continue;
			}
			//else
			$('step_clue_' + i).className = 'listOrderOthers';
		}
	}
}//*/

function setCursorPos(_oTextArea){
	//alert(document.selection.type)
	if (_oTextArea.createTextRange)  
		_oTextArea.cursorPos = document.selection.createRange().duplicate();
}

function insertToTextArea(_sCode){
	var oTextArea = $('txtContent');
	
	if(oTextArea == null){
		Ext.Msg.$alert(wcm.LANG.TEMPLATE_ALERT_3 || "没有找到模板正文所在的文本框!");
		return;
	}

	var sCode = _sCode || "";
	if (oTextArea.createTextRange && oTextArea.cursorPos){
		var cursorPos = oTextArea.cursorPos;
		cursorPos.text = (cursorPos.text.charAt(cursorPos.text.length - 1) == ' ')
			? (sCode + ' ') : sCode;

	}else if(typeof(oTextArea.selectionStart) == "number"){
	　　 var start = oTextArea.selectionStart;
	　　 var end = oTextArea.selectionEnd;
	　　 var pre = oTextArea.value.substr(0, start);
	　　 var post = oTextArea.value.substr(end);
　　	 oTextArea.value = pre + _sCode + post;

	}else if(confirm(wcm.LANG.TEMPLATE_45 || "选择的模板将全部替换当前编辑器的内容,你确定要执行此操作么?")){
		oTextArea.value = sCode;
	}
}

function insertCode(){
	var sCode = null;
	try{
		sCode = window.clipboardData.getData("Text");
	}catch(err){
	}
	insertToTextArea(sCode);
}

function checkTempName(){
	var nTempId = PageContext.params.objectid || 0;
	var eTempName = $('txt_temp_name');
	if(nTempId == 0 || (eTempName.value != eTempName.getAttribute('_rawValue', 2))){
		//TODO siteid
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		var nSiteId = $('hdRootId').value;
		oHelper.Call('wcm6_template', 'existsSimilarName', 
			{tempName: eTempName.value, siteid: nSiteId, objectid: $F('hdObjectId')}, 
			true, function(transport, json){
			if(com.trs.util.JSON.value(json, "result") == 'false'){
				ValidationHelper.successRPCCallBack();
			}else{
				ValidationHelper.failureRPCCallBack(wcm.LANG.TEMPLATE_CALLBACK || "模板名称已经存在");
			}
		});
	}
}

Event.observe('txtContent', 'click', function(event){
	event = window.event || event;
	var dom = Event.element(event);
	setCursorPos(dom);
})

Event.observe('txtContent', 'select', function(event){
	event = window.event || event;
	var dom = Event.element(event);
	setCursorPos(dom);
})

Event.observe('txtContent', 'keyup', function(event){
	event = window.event || event;
	var dom = Event.element(event);
	setCursorPos(dom);
})

Event.observe('txtTempExt', 'focus', function(){
	Element.show('txtTempExtMsg');
});
Event.observe('txtTempExt', 'blur', function(){
	var isValid = ValidationHelper.isValid('txtTempExt');
	if(isValid) Element.hide('txtTempExtMsg');
});
Event.observe('txtFileName', 'focus', function(){
	Element.show('txtFileNameMsg');
});
Event.observe('txtFileName', 'blur', function(){
	var isValid = ValidationHelper.isValid('txtFileName');
	if(isValid) Element.hide('txtFileNameMsg');
});