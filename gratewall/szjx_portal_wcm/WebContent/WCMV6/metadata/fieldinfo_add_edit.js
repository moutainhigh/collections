var FieldInfos = {
	servicesName : 'wcm6_MetaDataDef',
	findMethodName : 'findDBFieldInfoById',
	findClassInfoServicesName : 'wcm6_ClassInfo',
	existsClassInfoMethodName : 'exists',
	lastFieldTypeContainer : '',
	lastDBTypeContainer : '',
	defaultFieldTypeValue : 'normalText',
	isChanged : false,
	params : {
		objectId : getParameter("objectId") || 0,
		fieldsToHtml : 'fieldName,anotherName',
		ContainsRight : true
	},
	mappingDataType : FieldInfoMgr.mappingDataType,
	mappingDBDataType : FieldInfoMgr.mappingDBDataType,
	getHelper : function(){
		return new com.trs.web2frame.BasicDataHelper();		
	},
	loadData : function(){
        this.getHelper().Call(this.servicesName, this.findMethodName, this.params, true, this.dataLoaded.bind(this));
	},
	dataLoaded : function(transport, json){
        var sValue = TempEvaler.evaluateTemplater('objectTemplate', json);
        Element.update('objectContainer', sValue);	
		this.initSpecialValue();
		this.initEvent();
	},
	initSpecialValue : function(){
		//init the checkbox status
		var checkboxArray = ['notNull', 'notEdit', 'hiddenField', 'inOutline', "inDetail", "searchField", "titleField", "RADORCHK"];
		for (var i = 0; i < checkboxArray.length; i++){
			var oCheckbox = $(checkboxArray[i]);
			if(!oCheckbox) continue;
			oCheckbox.checked = oCheckbox.getAttribute("initValue") == "1" ? true : false;
		}

		//新建时，初始化一些字段选中
		if(this.params.objectId == 0){
			var checkedArray = ["inDetail"];
			for (var i = 0; i < checkedArray.length; i++){
				var oCheckbox = $(checkedArray[i]);
				if(!oCheckbox) continue;
				oCheckbox.checked = true;
			}
		}
		
		//字段为标题字段时设置其可以在概览中显示
		if($('titleField')){
			setTitleEvent($('titleField'));
		}

		//init the tableAnotherName
		var sTableAnotherName = getParameter("tableAnotherName") || '';
		var tableInfoId = getParameter("tableInfoId") || '0';
		var tableAnotherName = $('tableAnotherName');
		tableAnotherName.innerHTML = decodeURIComponent(sTableAnotherName.escapeHTML()) + "[" + tableInfoId + "]";
	},
	excludeItems : function(){
		if(this.params.objectId == 0) return;
		var oSelect = $('fieldType');
		var currDataType = oSelect.options[oSelect.selectedIndex].getAttribute("_value").toLowerCase();
		var dataTypeObjs = FieldInfoMgr.mappingDataType;
		var currDBType = dataTypeObjs[currDataType]["dbType"];
		if(!currDBType){
			var dbTypeSel = $("dbType");
			currDBType = dbTypeSel.options[dbTypeSel.selectedIndex].getAttribute("_value").toLowerCase();
		}
		for (var i = 0; i < oSelect.options.length; i++){
			var option = oSelect.options[i];
			var dataType = option.getAttribute("_value").toLowerCase();
			if(dataType == 'selfdefine') continue;
			var dbType = dataTypeObjs[dataType]["dbType"];
			if(dbType != currDBType){
				oSelect.options.remove(i);
				i--;
			}
		}
	},
	initEvent : function(){
		Event.observe('fieldType', 'change', this.fieldTypeChange.bind(this));
		Event.observe('dbType', 'change', this.dbTypeChange.bind(this));
		Event.observe('selectClassInfo', 'click', this.selectClassInfo.bind(this));
		Event.observe('defaultSelectClassInfo', 'click', this.defaultSelectClassInfo.bind(this));
		Event.observe('selectValidator', 'click', this.selectValidator.bind(this));
		//bind title event.
		PopTip.registerAtOnce([$('enmValue')]);
		Event.observe('enmValue', 'dblclick',function(){
			var enumValue = $('enmValue');
			FieldInfoMgr.setEnumValue(enumValue.value, function(_params){
				enumValue.value = _params ? _params["enumValue"] : "";
			});
		});
		ValidationHelper.initValidation(); 
		if(this.params.objectId != 0){
			$('fieldType').value = $('fieldType').getAttribute("initValue");
		}else{
			this.setDefaultFieldType();
		}
//		$('fieldType').fireEvent('onchange');
		if(this.params.objectId != 0){
			$('dbType').value = $('dbType').getAttribute("initValue");
		}
		$('fieldType').fireEvent('onchange');
//		$('dbType').fireEvent('onchange');
		var inputElements = $('objectContainer').getElementsByTagName("input");
		for (var i = 0, length = inputElements.length; i < length; i++){
			Event.observe(inputElements[i], 'focus', function(){
				this.select();
			}.bind(inputElements[i]));
		}
		this.excludeItems();
	},	
	setDefaultFieldType : function(){
		var fieldType = $('fieldType');
		for (var i = 0, length = fieldType.options.length; i < length; i++){
			if(fieldType.options[i].getAttribute("_value") == this.defaultFieldTypeValue){
				fieldType.selectedIndex = i;
				break;
			}
		}
		this.setDBLength(this.defaultFieldTypeValue);
	},
	setDBLength : function(fieldTypeKey){
		var dbLength = $('dbLength');
		dbLength.value = FieldInfos.dataTypeMappings[fieldTypeKey] || 0;		
		if(fieldTypeKey.toLowerCase() == 'selfdefine'){
			var dbTypeSel = $('dbType');
			var maxLength = dbTypeSel.options[dbTypeSel.selectedIndex].getAttribute("_maxLength");
			if(maxLength == "0"){
				dbLength.value = 0;
			}
		}
		var displayMethod = dbLength.value.trim() == '0' ? 'hide' : 'show';
		this.showHideControls('dbLengthContainer', displayMethod);
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
		//clear some controls.
		var resetControls = ['fieldName','anotherName','enmValue','classId','dbScale','defaultValue','notNull','notEdit','hiddenField','RADORCHK','titleField','searchField','validator'];
		for (var i = 0; i < resetControls.length; i++){
			var oControl = $(resetControls[i]);
			if(!oControl) continue;
			oControl.value = '';
			if(oControl.tagName == 'INPUT'){
				var type = oControl.type.toLowerCase();
				if(type == 'checkbox' || type == 'radio'){
					oControl.checked = false;
				}
			}
		}
		
		//字段为标题字段时设置其可以在概览中显示
		if($('titleField')){
			setTitleEvent($('titleField'));
		}

		//set select default.
		this.setDefaultFieldType();
	},
	fieldTypeChange : function(event){
		if(this.lastFieldTypeContainer){
//			Element.hide(this.lastFieldTypeContainer);
			this.showHideControls(this.lastFieldTypeContainer, 'hide');
		}
		var fieldType = $('fieldType');
		fieldType.value = fieldType.value;
		var option = fieldType.options[fieldType.selectedIndex];
		var oRelateInfo = this.mappingDataType[option.getAttribute("_value").toLowerCase()];
		if(oRelateInfo && oRelateInfo["containerType"]){
			this.lastFieldTypeContainer = oRelateInfo["containerType"];
//			Element.show(oRelateInfo["containerType"]);
			this.showHideControls(oRelateInfo["containerType"], 'show');
		}
		var dbLength = $('dbLength');
		var isFirstLoaded = dbLength.getAttribute('firstLoaded');
		if(isFirstLoaded){
			dbLength.removeAttribute('firstLoaded');
			if(dbLength.value.trim() == "" || dbLength.value.trim() == "0"){
				this.setDBLength(option.getAttribute("_value"));
			}
		}else{
			this.setDBLength(option.getAttribute("_value"));
		}
		var dbType = $('dbType');
		if(isFirstLoaded){
			this.dbTypeChange();
		}
		if(oRelateInfo && oRelateInfo["dbType"]){
			for (var i = 0, length = dbType.options.length; i < length; i++){
				var option = dbType.options[i];
				if(option.getAttribute("_value").toLowerCase() == oRelateInfo["dbType"]){
					dbType.value = option.value;
					$('dbType').fireEvent('onchange');
					break;
				}
			}
			dbType.disabled = true;
		}else{
			if(this.params.objectId == 0){
				dbType.disabled = false;	
			}else{
				dbType.disabled = true;	
			}
		}
		this.toggleSelectClassInfo();
		this.toggleSelectClassInfoRadochk();
		this.toggleDefaultValueContainer();
	},
	toggleSelectClassInfo : function(){
		$('defaultSelectClassInfo').style.display = $('classIdContainer').style.display;
	},
	toggleSelectClassInfoRadochk:function(){
		$('radioOrCheckContainer').style.display = $('classIdContainer').style.display;
	},
	hideDefaultValueArray : ['multitext', 'appendix', 'editor', 'editorchar', 'relation'],
	toggleDefaultValueContainer : function(){
		var fieldType = $('fieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var sFieldType = option.getAttribute("_value").toLowerCase();

		var isHide = false;
		if(sFieldType == "selfdefine"){
			var dbType = $('dbType');
			var option = dbType.options[dbType.selectedIndex];
			var sDBType = option.getAttribute("_value").toLowerCase();
			if(sDBType == "text"){
				isHide = true;
			}
		}else{
			var hideArray = this.hideDefaultValueArray;
			for (var i = 0; i < hideArray.length; i++){
				if(hideArray[i] == sFieldType){
					isHide = true;
					break;
				}
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
	dbTypeChange : function(event){
		var dbType = $('dbType');
		var option = dbType.options[dbType.selectedIndex];
		var maxLength = option.getAttribute("_maxLength");
		var displayMethod = maxLength == "0" ? 'hide' : 'show'; 
		//float,double 需要隐藏库中长度
		if(option.value == 6 || option.value == 8){
			displayMethod = 'hide';
		}
//		Element[displayMethod]('dbLengthContainer');
		this.showHideControls('dbLengthContainer', displayMethod);
		var oRelateInfo = this.mappingDBDataType[option.getAttribute("_value").toLowerCase()];
		if(this.lastDBTypeContainer){
			Element.hide(this.lastDBTypeContainer);
		}
		if(oRelateInfo && oRelateInfo["containerType"]){
//			Element.show(oRelateInfo["containerType"]);
			this.showHideControls(oRelateInfo["containerType"], 'show');
			this.lastDBTypeContainer = oRelateInfo["containerType"];
		}
		this.toggleDefaultValueContainer();
	},
	selectValidator : function(){
		alert("选择校验规则！");
	},
	defaultSelectClassInfo : function(){
		var classId = $('classId');
		var sClassIdValue = classId.value.trim().split(":")[0];
		if(sClassIdValue == 0) {
			alert("请先选择分类法ID");
			return;
		}
		var oParams = {
			objectId : sClassIdValue,
			selectedValue : $("defaultValue").value
		};
		ClassInfoMgr.selectClassInfoTree(oParams, function(_args){
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
		ClassInfoMgr.selectClassInfo(sClassIdValue, null, function(_args){
			if(_args.isSetCancel){
				classId.value = "";
			}
			if(_args.ids.length > 0){
				classId.value = _args.ids + ":" + _args.names;
				classId.focus();//cause the valid.
				$('defaultValue').focus();
			}else{
				classId.focus();//cause the valid.
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
		FieldInfos._checkClassInfoValid(sClassId, fValid, fInvalid);		
	},
	_checkClassInfoValid : function(sClassInfoId, fValid, fInvalid){
		if(!/^[0-9]{1,8}$/.test(sClassInfoId)){
			(fInvalid || Prototype.emptyFunction)("输入的不是正整数;");
			return;
		}
		ClassInfoMgr.exists({
			parentId	 : '0',
			propertyName : 'classInfoId',
			propertyValue : sClassInfoId
		}, fValid, fInvalid);	
	}
};

var addCount = 1;
function saveData(needClose){
	//if defaultValueContainer hidden, then set the value of defaultvalue to ""
	if(!Element.visible("defaultValueContainer")){
		$('defaultValue').value = "";
	}

	//校验默认值的范围
	var sDefaultValue = $F('defaultValue').trim();
	if(sDefaultValue != ""){
		var sErrMsg = "";
		var nDBType = $('dbType').value;
		switch(nDBType){
			case "4"://int
				var regExp = new RegExp('^-?\\d+(e[\+-]?\\d+)?$', "i");
				if(!regExp.test(sDefaultValue)){
					sErrMsg = "默认值[" + sDefaultValue + "]不是合法的整型数.";
					break;
				}
				var nDefaultValue = parseInt(sDefaultValue, 10);
				var nMinValue = 2 << 30;
				var nMaxValue = -(2 << 30) - 1;
				if(nMinValue > nDefaultValue || nDefaultValue > nMaxValue){
					sErrMsg = "默认值[" + nDefaultValue + "]的范围不对，应该在2^31(" + nMinValue + ")到2^31-1(" + nMaxValue + ")之间";
					break;
				}
				break;
			case "6"://float
			case "8"://double
				var regExp = new RegExp('^-?\\d+(\.\\d+)?(e[\+-]?\\d+)?$', "i");
				if(!regExp.test(sDefaultValue)){
					sErrMsg = "默认值[" + sDefaultValue + "]不是合法的小数.";
					break;
				}
				var nDefaultValue = parseFloat(sDefaultValue, 10);
				var nMinValue = 2e-125;
				var nMaxValue = 2e125;
				if(nMinValue > nDefaultValue || nDefaultValue > nMaxValue){
					sErrMsg = "默认值[" + sDefaultValue + "]的范围不对，应该在2e-125(" + nMinValue + ")到2e125(" + nMaxValue + ")之间";
					break;
				}
				break;
		}
		if(sErrMsg){
			try{
				$alert(sErrMsg, function(){
					$dialog().hide();
					setTimeout(function(){
						$('defaultValue').focus();
					}, 10);
				});
			}catch(error){
				alert(sErrMsg);
				$('defaultValue').focus();
			}
			return false;
		}
	}

	//校验关键字
	if(containKeyWordsInFields('fieldName')){
		return false;
	}

	var oAnotherName = $('anotherName');
	if(oAnotherName){
		var msg = null;
		if(/[<>;&]/.test(oAnotherName.value)){
			msg = "<font color='red'>中文名称中含有特殊字符<b>;&&lt;&gt;</b></font>.";
		}else if(/^\d+/.test(oAnotherName.value)){
			msg = "<font color='red'>中文名称不能以数字开头</font>.";
		}
		if(msg){
			try{
				$alert(msg, function(){
					$dialog().hide();
					setTimeout(function(){
						oAnotherName.focus();
					}, 10);
				});
			}catch(error){
				alert(msg);
				oAnotherName.focus();
			}
			return false;
		}
	}

	//check the class info valid.
	FieldInfos.checkClassInfoValid(
		function(){
			$beginSimplePB("正在保存数据");
			var objectId = getParameter("objectId");
			var domOfClassId = $('classId');
			domOfClassId.value = domOfClassId.value.split(":")[0];
			if(domOfClassId.value.trim() == ""){
				domOfClassId.value = "0";
			}
			FieldInfoMgr.save(objectId, "ObjectForm", function(){
				$('objectId').value = getParameter("objectId") || "0";
				$('tableInfoId').value = getParameter("tableInfoId") || '0';
			}, function(transport, json){
				$endSimplePB();
				FieldInfos.isChanged = true;
				if(objectId != "0"){
					FloatPanel.close(true);
				}else{
					FieldInfos.setControlsDefault();
					FieldInfos.fieldTypeChange();
					addCount++;
					var sTilte = "新建元数据字段<span style='font-size:12px;'>--新建第<font color='blue'>[" + addCount + "]</font>个</span>";
					FloatPanel.setTitle(sTilte);
					$('fieldName').focus();
					FloatPanel.disableCommand('savebtn', true);
				}
			});
		},
		function(sMsg){
			sMsg = sMsg == 'notFound' ? "不存在对应的分类法;" : "输入的不是正整数;"
			Element.update("validTip", "<font color='red'>" + sMsg + "</font>");
			FloatPanel.disableCommand('savebtn', true);
		}
	);
	return false;
}

FloatPanel.addCloseCommand("关闭");   
FloatPanel.addCommand('savebtn', '保存', 'saveData', null);
FloatPanel.setAfterClose(function(){
	var objectId = getParameter("objectId");
	objectId = objectId == "0" ? null : objectId;
	if(FieldInfos.isChanged){
		$MessageCenter.sendMessage('main','PageContext.RefreshList',"PageContext",[objectId],true,true);
	}
});

ValidationHelper.addValidListener(function(){
	FloatPanel.disableCommand('savebtn', false);
}, "ObjectForm");
ValidationHelper.addInvalidListener(function(){
	FloatPanel.disableCommand('savebtn', true);
}, "ObjectForm");

function checkClassInfoValid(){
	FieldInfos.checkClassInfoValid(
		function(sMsg, sClassInfoId, cName){
			var oClassId = $('classId');
			oClassId.value = sClassInfoId
			if(cName){	
				oClassId.value += ":" + cName;
			}
			ValidationHelper.successRPCCallBack();
		},
		function(sMsg){
			sMsg = sMsg == 'notFound' ? "不存在对应的分类法;" : "输入的不是正整数;"
			ValidationHelper.failureRPCCallBack(sMsg);
		}
	);
}

function setTitleEvent(oTitleCbx){
	if(oTitleCbx.checked){
		$('inOutline').disabled = true;
		$('inOutline').checked = true;
	}else{
		$('inOutline').disabled = false;
	}
}

Event.observe(window, 'load', function(){
	FieldInfos.loadData();
});
