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
function init(){
	ValidationHelper.addValidListener(function(){
		wcmXCom.get('btnSubmit').disabled = false;
		wcmXCom.get('btnSave').disabled = false;
	}, "addEditForm");
	ValidationHelper.addInvalidListener(function(){
		wcmXCom.get('btnSubmit').disabled = true;
		wcmXCom.get('btnSave').disabled = true;
	}, "addEditForm");
	ValidationHelper.registerValidations(m_arrValidations);
	ValidationHelper.initValidation();
	PageContext.loadTemplateAttr();
	PageContext.notifyValid();
}
var PageContext = {
	params:{}
};
//lock when loaded while unlock when unloading
LockerUtil.register(getParameter('objectid'), 102, null, {
	failToLock : function(sMessage, json){
		Element.hide(wcmXCom.get('btnSubmit'));
		Element.hide(wcmXCom.get('btnSave'));
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
				if(getParameter('objectid') == 0){
					Element.show('masterbox');
					Element.show('outline_thumb_list');
					Element.hide('detail_thumb_list');
				}
				break;
			case 2: // 细览
				var eTempExt = $('txtTempExt');
				Element.show(eTempExt);
				Element.hide('divTempOutputFileAttr');
				Element.show('divTempFileExtAttr');
				Element.hide('txtFileName');
				if(getParameter('objectid') == 0){
					Element.show('masterbox');
					Element.show('detail_thumb_list');
					Element.hide('outline_thumb_list');
				}
				break;
			default: //嵌套或者新建
				Element.hide('divTempOutputFileAttr');
				Element.hide('divTempFileExtAttr');
				Element.hide('txtFileName');
				Element.hide('txtTempExt');
				Element.hide('masterbox');
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
	saveTemplate : function(_eForm, bNextEdit){
		if(!ValidationHelper.doValid(_eForm)) {
			return;
		}
		if($('selTempType').value < 0) {
			Ext.Msg.$alert(wcm.LANG.TEMPLATE_ALERT_0 || '请选择模板类型!');
			return;
		}
		if(getParameter('objectid') == 0){
			var radioEls = document.getElementsByName('MASTER_VIEW');
			var bChecked = false;
			for (var i = 0; i < radioEls.length; i++){
				if(radioEls[i].checked){
					$('MasterId').value = radioEls[i].value;
					bChecked = true;
					break;
				}
			}
			if(!bChecked){
				Ext.Msg.$alert(wcm.LANG.TEMPLATE_ALERT_1009 || '请选择母版!');
				return;
			}
		}
		this.doSave(bNextEdit);
	},
	doSave : function(bNextEdit){
		var eForm = $('addEditForm');
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call('wcm61_visualtemplate', 'save', eForm, true, function(_trans, _json){
			var nSavedTempId = $v(_json, 'result');
			var cb = wcm.CrashBoarder.get(window);
			cb.notify(nSavedTempId);
			if(bNextEdit){
				window.open('../special/design.jsp?templateId=' + nSavedTempId + 
					 '&HostType=' + $('hdHostType').value + '&HostId=' + $('hdHostId').value);
			} 
		});
	},
	validateTemplate : function(){
		var oPostData = {
			TempId: $('hdObjectId').value,
			hosttype: $('hdHostType').value,
			hostid: $('hdHostId').value,
			TempName : $('txt_temp_name').value
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
	}
});
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
window.m_cbCfg = {
		btns : [
			{
				id : 'btnSubmit',
				hidden : getParameter('readonly') == 'true',
				text : wcm.LANG.VISUALTEMPLATE_ADD_EDIT_1009 || '下一步',
				cmd : function(){
					PageContext.saveTemplate('addEditForm', true);
					return false;
				}
			},{
				id : 'btnSave',
				hidden : getParameter('readonly') == 'true',
				text : wcm.LANG.VISUALTEMPLATE_ADD_EDIT_1008 || '保存',
				cmd : function(){
					PageContext.saveTemplate('addEditForm', false);
					return false;
				}
			},
			{
				hidden : getParameter('readonly') == 'true',
				text : wcm.LANG['FLOW_CANCEL'] ||　'取消'
			}
		]
	};
	
	//母板列表查看大图，采用jQuery flyout插件实现
	function imgFly(){
		var animating = false;
		jQuery('.piclook').click(function(){
			if(animating === true) return false;
			animating = true;
			var parentDoc = parent.document;
			var dom = parentDoc.getElementById('jquery-temp');
			if(!dom){
				var dom = parentDoc.createElement('div');
				dom.id = 'jquery-temp';
				dom.style.position = 'absolute';
				dom.style.display = 'none';
				parentDoc.body.appendChild(dom);
			}		
			//清除掉父窗口中的临时节点
			if(parent.$('master-loader')){
				parent.Element.remove(parent.$('master-loader'));
			}
			//在更新dom的innerHTML之前，先解除dom内a标签的绑定事件
			var firstChild = dom.firstChild;
			if(firstChild){
				parent.jQuery(firstChild).unbind("click");
			}
			//a标签的outerhtml包含图片相对路径href，赋值到父页面的节点之前需要调整相对路径
			var outerHtml = this.outerHTML.replace(/\.\.\/special\/images\/zt_wt\.gif/g,"./special/images/zt_wt.gif");
			dom.innerHTML =  outerHtml;
			var aEl = dom.firstChild;
			parent.jQuery(aEl).flyout({
				loader : 'master-loader',
				loaderZIndex :2147483647,
				inOpacity:0, 
				outOpacity:1, 
				fullSizeImage:false, 
				inSpeed: 800,
				outSpeed : 1000, 
				closeTip : " - 点击关闭",
				flyOutStart : function(){
				},
				flyOutFinish : function(){
					animating = false;
				}
			});
			aEl.fireEvent('onclick');
			return false;
		});
	}
	//弹出图片和缩略图以外的元素上发生点击事件时，触发弹出图片的click事件，让其隐藏
	Event.observe(document, 'click', function(event){
		putAwayImgIfExist();
	});
	Event.observe(parent.document, 'click', function(event){
		putAwayImgIfExist();
	});
	function putAwayImgIfExist(){
		if(parent.$('master-loader')){
			parent.jQuery('#master-loader img').click();
		}
	}
