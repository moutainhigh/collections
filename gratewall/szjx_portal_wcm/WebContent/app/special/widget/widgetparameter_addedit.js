
window.m_cbCfg = {
	btns : [
		{
			text : '保存',
			id : 'ParambtnSave',
			cmd : function(){
				savaData();
				return false;
			}
		},
		{
			extraCls : 'wcm-btn-close',
			text : '取消'
		}
	]
};	

function init(){
	//注册校验成功时执行的回调函数
	ValidationHelper.addValidListener(function(){
		//按钮有效处理
		wcmXCom.get('ParambtnSave').enable();
	}, "ObjectForm");

	//注册校验失败时执行的回调函数
	ValidationHelper.addInvalidListener(function(){
		//按钮失效处理
		wcmXCom.get('ParambtnSave').disable();
	}, "ObjectForm");

	//初始化页面中需要校验的元素
	//ValidationHelper.initValidation();

	initSpecialValue();
	WidgetParameterInfos.initEvent();
}

function savaData(){
	//校验
	if(!beforeSaveCheck())return false;
	if(!ValidationHelper.doValid('ObjectForm')){
		return false;
	}
	WidgetParameterInfos.checkClassInfoValid(function(){
			var domOfClassId = $('classId');
			domOfClassId.value = domOfClassId.value.split(":")[0];
			if(domOfClassId.value.trim() == ""){
				domOfClassId.value = "0";
			}
			var oHelper = new com.trs.web2frame.BasicDataHelper();
			var c_bWin = wcm.CrashBoarder.get(window);
			c_bWin.hide();
			oHelper.Call('wcm61_widgetparameter', 'save', $('ObjectForm'), true, function(_trans){
				c_bWin.notify(true);
			});   
		},
		function(sMsg){
			sMsg = sMsg == 'notFound' ? "不存在对应的分类法;" : "输入的不是正整数;"
			Element.update("validTip", "<font color='red'>" + sMsg + "</font>");
			//按钮需要失效
		}	
	)
	return false;
}

function checkWidgetParamName(){
	var nObjId = $('objectId').value;
	var sWidgetParamName = $('widgetparamName').value;
	var oPostData = {
		objectId : nObjId,
		widgetId : $('WIDGETID').value,
		widgetParamName : sWidgetParamName
	};
	if($('objectId') == 0 || (sWidgetParamName != $('widgetparamName').getAttribute('oldValue', 2))){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call('wcm61_widgetparameter', 'existsSimilarName', 
			oPostData, true, function(transport, json){
			if(com.trs.util.JSON.value(json, "result") == 'false'){
				ValidationHelper.successRPCCallBack();
			}else{
				ValidationHelper.failureRPCCallBack("资源变量名称已经存在");
			}
		});
	}
}

function initSpecialValue(){
	//init the checkbox status
	var checkboxArray = ['notNull', 'notEdit',  "RADORCHK"];
	for (var i = 0; i < checkboxArray.length; i++){
		var oCheckbox = $(checkboxArray[i]);
		if(!oCheckbox) continue;
		oCheckbox.checked = oCheckbox.getAttribute("initValue") == "1" ? true : false;
	}
	
}
var WidgetParameterInfos = {
	mappingDataType : {
		'radio' : {containerType : 'enmValueContainer'},
		'checkbox' : {containerType : 'enmValueContainer'},
		'select' : {containerType : 'enmValueContainer'},
		'classinfo' : {containerType : 'classIdContainer'},
		'inputselect' : {containerType : 'enmValueContainer'},
		'suggestion' : {containerType : 'enmValueContainer'},
		'selfenm' : {containerType : 'enmValueContainer'}
	},
	initEvent : function(){
		Event.observe('widgetParamType', 'change', this.fieldTypeChange.bind(this));
		Event.observe('enmValue', 'change', this.clearDefault.bind(this));
		Event.observe('selectClassInfo', 'click', this.selectClassInfo.bind(this));
		Event.observe('defaultSelectClassInfo', 'click', this.defaultSelectClassInfo.bind(this));
		Event.observe('defaultSelectChannel', 'click', this.defaultSelectChannel.bind(this));
		Event.observe('defaultValue', 'mouseover', showBubblePanel2);
		Event.observe('defaultValue', 'mouseout', hideBubblePanel2);
		Event.observe('enmValue', 'dblclick',this.setEnumValue.bind(this));
		Event.observe('defaultValue', 'dblclick',this.setEnumDefaultValue.bind(this));
		ValidationHelper.initValidation(); 

		if($('objectId').value != 0){
			$('widgetParamType').value = $('widgetParamType').getAttribute("initValue");
		}else{
			this.setDefaultFieldType();
		}
		

		$('widgetParamType').fireEvent('onchange');
		var inputElements = $('ObjectForm').getElementsByTagName("input");
		for (var i = 0, length = inputElements.length; i < length; i++){
			Event.observe(inputElements[i], 'focus', function(){
				this.select();
			}.bind(inputElements[i]));
		}
		
	},
	clearDefault : function(){
		if($("enmValue").value.replace(/(^\s*)|(\s*$)/g,"") =="")
			$("defaultValue").value = "";
	},
	setEnumDefaultValue : function(){
		var widgetParamType = $('widgetParamType');
		var option = widgetParamType.options[widgetParamType.selectedIndex];
		var sWidgetParamType = option.getAttribute("_value").toLowerCase();
		//处理枚举值
		var disableDefaultValueArray = ['radio', 'checkbox', 'select', 'inputselect','suggestion'];
		if(disableDefaultValueArray.include(sWidgetParamType) && $('enmValue').value.replace(/(^\s*)|(\s*$)/g,"")!=""){
			this.setEnumValue();
		}
	},
	setEnumValue : function(){
		var enumValue = $('enmValue');
		var defaultValue = $('defaultValue');
		var isRadio = true;
		var fieldType = $('widgetParamType');
		if(fieldType.options[fieldType.selectedIndex].getAttribute('_value') == "checkbox"){
			isRadio = false;
		}
		//使失去焦点，否则新打开页面的焦点仍在enumValue上。
		enumValue.blur();
		var sTitle = '<font style="font-size:12px;font-weight:normal;color:white;">'+(wcm.LANG.METADATA_ALERT_4||'按Enter追加新枚举值')+'</font>';
		var sURL = WCMConstants.WCM6_PATH + 'metadata/enumvalue_create.html';
		wcm.CrashBoarder.get('setEnumValue').show({
			title : sTitle,
			src : sURL,
			width: '450px',
			height: '270px',
			params : {enumValue : enumValue.value, defaultValue : defaultValue.value, isRadio : isRadio},
			reloadable : true,
			maskable : true,
			callback : function(_args){
				var newViewFieldEnmValue = _args ? _args["enumValue"] : "";
				if(window.validViewFieldEnmValue){
					var sDBfieldEnmValue = enumValue.getAttribute("dbfieldEnmValue");
					if(!validViewFieldEnmValue(newViewFieldEnmValue, sDBfieldEnmValue)){
						Ext.Msg.alert(wcm.LANG.METADATA_ALERT_34 || "输入的枚举值必须包含在元数据对应字段的枚举值中。");
						return;
					}
				}
				enumValue.value = newViewFieldEnmValue;
				defaultValue.value = _args ? _args["defaultValue"] : "";
			}
		});
	},
	setDefaultFieldType : function(){
		var widgetParamType = $('widgetParamType');
		for (var i = 0, length = widgetParamType.options.length; i < length; i++){
			if(widgetParamType.options[i].getAttribute("_value") == this.defaultFieldTypeValue){
				widgetParamType.selectedIndex = i;
				break;
			}
		}
	},
	showHideControls : function(element, sDisplayMethod){
		element = $(element);
		if(!element) return;
		Element[sDisplayMethod](element);
		if(sDisplayMethod == 'show'){
			var elements = Form.getElements(element);
			for (var i = 0; i < elements.length; i++){
				elements[i].removeAttribute('ignore');
			}
			ValidationHelper.pushElements(elements);
		}else{
			var elements = Form.getElements(element);
			for (var i = 0; i < elements.length; i++){
				elements[i].setAttribute('ignore', true);
			}
			ValidationHelper.popElements(elements);
		}
	},
	setControlsDefault : function(){
		//在viewfieldinfo_add_edit和fieldinfo_add_edit中被分别重载
	},
	fieldTypeChange : function(event){
		if(this.lastFieldTypeContainer){
			this.showHideControls(this.lastFieldTypeContainer, 'hide');
		}
		var widgetParamType = $('widgetParamType');
		var option = widgetParamType.options[widgetParamType.selectedIndex];
		var oRelateInfo = this.mappingDataType[option.getAttribute("_value").toLowerCase()];
		if(oRelateInfo && oRelateInfo["containerType"]){
			this.lastFieldTypeContainer = oRelateInfo["containerType"];
			this.showHideControls(oRelateInfo["containerType"], 'show');
		}
		var sWidgetParamType = option.getAttribute("_value").toLowerCase();
		if(sWidgetParamType=="selchannel"){
				$('defaultSelectChannel').style.display = '';
			//$('radioOrCheckContainer').style.display = '';
		}else{
				$('defaultSelectChannel').style.display = 'none';
			//$('radioOrCheckContainer').style.display = 'none';
		}
		this.disableDefaultValue();
		this.toggleDefaultValueContainer();
		this.toggleAttrRelation();  //控制不同字段不同属性的显隐。此处为扩展结构，可支持不同类型的字段及其属性的关联显隐
		//add by wyw 应要求清空默认值
		if($('defaultValue').getAttribute('loaded') == null){
			$('defaultValue').setAttribute('loaded', true);
		}else{
			$('defaultValue').value = "";
		}		
		if($('defaultSelectChannel').style.display=='none' && $('defaultSelectClassInfo').style.display=='none'){
			$('defaultValidTip').style.marginLeft = 0+'px';
		}
	},
	disableDefaultValue : function(){
		var widgetParamType = $('widgetParamType');
		var option = widgetParamType.options[widgetParamType.selectedIndex];
		var sWidgetParamType = option.getAttribute("_value").toLowerCase();
		//处理枚举值
		var disableDefaultValueArray = ['radio', 'checkbox', 'select', 'inputselect','suggestion','selchannel'];
		if(disableDefaultValueArray.include(sWidgetParamType)){
			$('plainTip').innerHTML = "";//wcm.LANG.METADATA_ALERT_5 || "可双击枚举值框进行设置";			
			$('defaultValue').readOnly = true;
			$('defaultValue').onfocus=function(){this.blur()};
		}else{
			$('plainTip').innerHTML = "";
			$('defaultValue').readOnly = false;
			$('defaultValue').onfocus=null;
		}
		//处理分类法
		if(sWidgetParamType == "classinfo"){
			$('defaultValue').disabled = true;
		}
	},
	hideDefaultValueArray : ['multitext', 'appendix', 'editor', 'editorchar', 'relation'],
	toggleDefaultValueContainer : function(){
		var fieldType = $('widgetParamType');
		var option = fieldType.options[fieldType.selectedIndex];
		var sFieldType = option.getAttribute("_value").toLowerCase();
		var defaultValue = $("defaultValue");
		var isHide = false;
		var hideArray = this.hideDefaultValueArray;
		for (var i = 0; i < hideArray.length; i++){
			if(hideArray[i] == sFieldType){
				isHide = true;
				break;
			}
		}
		Element[isHide ? "hide" : "show"]("defaultValueContainer");

		var defaultValue = $('defaultValue');
		if(isHide){
			ValidationHelper.popElements(defaultValue);
		}else{
			ValidationHelper.pushElements(defaultValue);
		}
	},
	
	toggleAttrRelation : function(){
		//分类法
		$('defaultSelectClassInfo').style.display = $('classIdContainer').style.display;
		this.toggleRadOrCkxContainer();
		//$('radioOrCheckContainer').style.display = $('classIdContainer').style.display;
		
	},
	toggleRadOrCkxContainer : function(){
		if($('classIdContainer').style.display==''||$('defaultSelectChannel').style.display==''){
			$('radioOrCheckContainer').style.display = '';
		}else{
			$('radioOrCheckContainer').style.display = 'none';
		}
	},
	defaultSelectChannel : function(){
		var selectedChannelIds = $('defaultValue').value;
		var radOrCkx = $('RADORCHK').checked==true?1:0;
		var oparams = {
			isRadio:radOrCkx,
			ExcludeSelf:0,
			SelectedChannelIds:selectedChannelIds,
			ExcludeTop:1,
			ExcludeVirtual:1,
			MultiSiteType:0,
			SiteTypes:'0'
		};
		wcm.CrashBoarder.get("default_select_channel").show({
			title : "选择栏目",
			src : WCMConstants.WCM6_PATH+'include/channel_select_forCB.html',
			width: '400px',
			height: '450px',
			maskable : true,
			params:oparams,
			callback : function(params){
				$('defaultValue').value = params[0];
            }

		});
	}, 
	defaultSelectClassInfo : function(){
		var classId = $('classId');
		var sClassIdValue = classId.value.trim().split(":")[0];
		if(sClassIdValue == 0) {
			Ext.Msg.alert(wcm.LANG.METADATA_ALERT_7 || "请先选择分类法ID.");
			return;
		}
		var oParams = {
			objectId : sClassIdValue,
			selectedValue : $("defaultValue").value,
			treeType : $("RADORCHK").checked?1:0
		};
		wcm.ClassInfoSelector.selectClassInfoTree(oParams, function(_args){
			if(_args.ids.length == 0){
				$("defaultValue").value = "";
			}else{
				$("defaultValue").value = _args.ids.join();
			}
		});
	},
	selectClassInfo : function(){
		var classId = $('classId');
		var sClassIdValue = classId.value.trim().split(":")[0];
		wcm.ClassInfoSelector.selectClassInfo(sClassIdValue, function(_args){
			if(_args.isSetCancel){
				classId.value = "";
			}
			if(_args.selectedIds.length > 0){
				classId.value = _args.selectedIds + ":" + _args.selectedNames;
				//当classid改变，则原来的默认值失效	，否则分类法树取到的还是原来的classid的
				if(sClassIdValue != _args.selectedIds){
					$("defaultValue").value = "";
				}
				//check the class info valid.
				WidgetParameterInfos.checkClassInfoValid(
					function(){
						Element.update("validTip", "");
						//按钮控制有效
					},
					function(sMsg){
						sMsg = sMsg == 'notFound' ? (wcm.LANG.METADATA_ALERT_8 || "不存在对应的分类法.") : (wcm.LANG.METADATA_ALERT_9 || "输入的不是正整数.")
						Element.update("validTip", "<font color='red'>" + sMsg + "</font>");
						//按钮控制无效
					}
				);
			}else{
				classId.value = "";
				$("defaultValue").value = "";
			}	
		});
	},
	checkClassInfoValid : function(fValid, fInvalid){
		//init the classId
		var oClassId = $('classId');
		var sClassId = oClassId.value.trim();
		if(sClassId == "" || sClassId == "0"){
			fValid("", sClassId, "");
			return;
		}
		if(sClassId.indexOf(":") > 0){
			sClassId = sClassId.split(":")[0];
		}
		//check the sClassId is valid.
		WidgetParameterInfos._checkClassInfoValid(sClassId, fValid, fInvalid);		
	},
	_checkClassInfoValid : function(sClassInfoId, fValid, fInvalid){
		if(!/^[0-9]{1,8}$/.test(sClassInfoId)){
			(fInvalid || Prototype.emptyFunction)(wcm.LANG.METADATA_ALERT_9 || "输入的不是正整数.");
			return;
		}
		wcm.ClassInfoSelector.exists({
			parentId	 : '0',
			propertyName : 'classInfoId',
			propertyValue : sClassInfoId
		}, fValid, fInvalid);	
	},
	disable: function(form) {
		var elements = Form.getElements(form);
		for (var i = 0; i < elements.length; i++) {
		  var element = elements[i];
		  element.blur();
		  element.disabled = 'true';
		}
	},
	findFirstElement: function(form) {
		return Form.getElements(form).find(function(element) {
		  return element.type != 'hidden' && !element.disabled &&
			['input', 'select', 'textarea'].include(element.tagName.toLowerCase());
		});
	}
};
var Form = {
	getElements : function(form){
		form = $(form);
		var rst = [], tags = ['input', 'select', 'textarea'], arr;
		for (var i=0;i<tags.length;i++) {
			arr = form.getElementsByTagName(tags[i]);
			for (var j = 0; j < arr.length; j++){
				rst.push(arr[j]);
			}
		}
		return rst;
	}
}
function checkClassInfoValid(){
	WidgetParameterInfos.checkClassInfoValid(
		function(sMsg, sClassInfoId, cName){
			var oClassId = $('classId');
			oClassId.value = sClassInfoId
			if(cName){	
				oClassId.value += ":" + cName;
			}
			ValidationHelper.successRPCCallBack();
		},
		function(sMsg){
			sMsg = sMsg == 'notFound' ? (wcm.LANG.METADATA_ALERT_8 || "不存在对应的分类法.") : (wcm.LANG.METADATA_ALERT_9 || "输入的不是正整数.")
			ValidationHelper.failureRPCCallBack(sMsg);
		}
	);
}

function beforeSaveCheck(){
	if(!Element.visible("defaultValueContainer")){
		$('defaultValue').value = "";
	}
	var sDefaultValue = $('defaultValue').value.trim();
	var widgetParamType = $('widgetParamType');
	var option = widgetParamType.options[widgetParamType.selectedIndex];
	var sWidgetParamType = option.getAttribute("_value").toLowerCase();
	if(sDefaultValue=="")return true;
	if(sWidgetParamType=="int"){
		var regExp = new RegExp('^-?\\d+(e[\+-]?\\d+)?$', "i");
		if(!regExp.test(sDefaultValue)){
			Ext.Msg.alert(String.format("字段默认值[{0}]不是合法的整型数.",sDefaultValue));
			return false;
		}
		var nDefaultValue = parseInt(sDefaultValue, 10);
		var nMinValue = 2 << 30;
		var nMaxValue = -(2 << 30) - 1;
		if(sDefaultValue.length > 10 || nMinValue > nDefaultValue || nDefaultValue > nMaxValue){
			Ext.Msg.alert(String,format("字段默认值[{0}]的范围不对，应该在-2^31({1})到2^31-1({2})之间",nDefaultValue, nMinValue,nMaxValue));
			return false;
		}
	}
	if(sWidgetParamType=="trueorfalse"){
		if($('defaultValue').value != "0" && $('defaultValue').value != "1"){
			Ext.Msg.alert("是否类型只能选0或1,请重设.");
			return false;
		}
	}
	return true;
}

//
function setNotNullEvent(oNotNull){
	var oEnmValue = $("enmValue");
	var fieldType = $('widgetParamType');
	var option = fieldType.options[fieldType.selectedIndex];
	var sFieldType = option.getAttribute("_value").toLowerCase();

	var enmTypeArray = ['radio', 'checkbox', 'select', 'inputselect','suggestion'];
	if(oEnmValue && enmTypeArray.include(sFieldType)){
		if(!(oEnmValue.value) && $('widgetParamType').value!=15 && $('widgetParamType').value!=17 && oNotNull.checked == true){
			$("notNull").checked = false;
			Ext.Msg.alert(wcm.LANG.METADATA_ALERT_12 || "枚举值不能为空.");
			return false;
		}
	}else{
		oNotNull.checked ==  !(oNotNull.checked);
	}
	if(Element.visible("classIdContainer")){
		var oClassIdValue = $("classId").value;
		if(oClassIdValue==""&&oNotNull.checked == true){
			$("notNull").checked = false;
			Ext.Msg.alert(wcm.LANG.METADATA_ALERT_32 || "分类法ID不能为空.");
			return false;
		}
	}
	return true;
}
function showBubblePanel(event){
	event = window.event || event;
	new wcm.BubblePanel('bubblePanel').bubble();
}

function hideBubblePanel(){
	$("bubblePanel").style.display = "none";
}
function showBubblePanel2(event){
	
	var fieldType = $('widgetParamType');
	var option = fieldType.options[fieldType.selectedIndex];
	var sFieldType = option.getAttribute("_value").toLowerCase();
	//处理枚举值
	var disableDefaultValueArray = ['radio', 'checkbox', 'select', 'inputselect','suggestion'];
	if(disableDefaultValueArray.include(sFieldType) && $('enmValue').value.replace(/(^\s*)|(\s*$)/g,"")!=""){
		event = window.event || event;
		new wcm.BubblePanel('bubblePanel2').bubble();
	}
}

function hideBubblePanel2(){
	$("bubblePanel2").style.display = "none";
}
