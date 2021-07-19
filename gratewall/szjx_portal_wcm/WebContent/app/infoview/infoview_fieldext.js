var ViewFieldInfos = {
	servicesName : 'wcm6_MetaDataDef',
	findMethodName : 'findViewFieldById',
	findClassInfoServicesName : 'wcm6_ClassInfo',
	existsClassInfoMethodName : 'exists',
	lastViewFieldTypeContainer : '',
	lastDBTypeContainer : '',
	isChanged : false,
	params : {
		objectId : getParameter("objectId") || 0,
		fieldsToHtml : 'fieldName,anotherName',
		ContainsRight : true
	},
	mappingDataType : ViewFieldInfoMgr.mappingDataType,
	mappingDBDataType : ViewFieldInfoMgr.mappingDBDataType,
	initData : function(){		
		this.viewId = getParameter('viewId') || 0;
		this.viewDesc = getParameter('viewDesc') || '';
		try{
			var viewInfo = $main().PageContext.getViewInfo();
			this.viewId = viewInfo.objectId;
			this.viewDesc = viewInfo.objectName;
		}catch(error){
			//alert(error.message);
		}
		this.loadData();
	},
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
		//init the objectId
		$('objectId').value = getParameter("objectId") || "0";

		//init the objectId
		$('viewId').value = this.viewId;

		//init the checkbox status
		var checkboxArray = ['notNull', 'inOutline', "searchField", "titleField"];
		for (var i = 0; i < checkboxArray.length; i++){
			var oCheckbox = $(checkboxArray[i]);
			if(!oCheckbox) continue;
			oCheckbox.checked = oCheckbox.getAttribute("initValue") == "1" ? true : false;
		}

		//init the tableAnotherName
		var viewDesc = $('viewDesc');
		viewDesc.innerHTML = decodeURIComponent(this.viewDesc) + "[" + this.viewId + "]";
	},
	initEvent : function(){
		Event.observe('fieldType', 'change', this.fieldTypeChange.bind(this));
		Event.observe('dbType', 'change', this.dbTypeChange.bind(this));
		Event.observe('selectClassInfo', 'click', this.selectClassInfo.bind(this));
		Event.observe('selectValidator', 'click', this.selectValidator.bind(this));
		ValidationHelper.initValidation(); 
		if(this.params.objectId != 0){
			$('fieldType').value = $('fieldType').getAttribute("initValue");
		}else{
			this.setDefaultFieldType();
		}
		$('fieldType').fireEvent('onchange');
		if(this.params.objectId != 0){
			$('dbType').value = $('dbType').getAttribute("initValue");
		}
//		$('dbType').fireEvent('onchange');
		var inputElements = $('objectContainer').getElementsByTagName("input");
		for (var i = 0, length = inputElements.length; i < length; i++){
			Event.observe(inputElements[i], 'focus', function(){
				this.select();
			}.bind(inputElements[i]));
		}
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
		var resetControls = ['fieldName','anotherName','enmValue','classId','dbScale','defaultValue','validator'];
		for (var i = 0; i < resetControls.length; i++){
			$(resetControls[i]).value = '';
		}
		//set select default.
		this.setDefaultFieldType();
	},
	fieldTypeChange : function(event){
		if(this.lastViewFieldTypeContainer){
//			Element.hide(this.lastViewFieldTypeContainer);
			this.showHideControls(this.lastViewFieldTypeContainer, 'hide');
		}
		var fieldType = $('fieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var oRelateInfo = this.mappingDataType[option.getAttribute("_value").toLowerCase()];
		if(oRelateInfo && oRelateInfo["containerType"]){
			this.lastViewFieldTypeContainer = oRelateInfo["containerType"];
//			Element.show(oRelateInfo["containerType"]);
			this.showHideControls(oRelateInfo["containerType"], 'show');
		}
		var dbType = $('dbType');
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
			dbType.disabled = false;
		}
	},
	dbTypeChange : function(event){
		var dbType = $('dbType');
		var option = dbType.options[dbType.selectedIndex];
		var maxLength = option.getAttribute("_maxLength");
		var displayMethod = maxLength == "0" ? 'hide' : 'show'; 
//		Element[displayMethod]('dbLengthContainer');				
		this.showHideControls('dbLengthContainer', displayMethod);
		var oRelateInfo = this.mappingDBDataType[option.getAttribute("_value").toLowerCase()];
		if(this.lastDBTypeContainer){
			//Element.hide(this.lastDBTypeContainer);
			this.showHideControls(this.lastDBTypeContainer, 'hide');
		}
		if(oRelateInfo && oRelateInfo["containerType"]){
			//Element.show(oRelateInfo["containerType"]);
			this.showHideControls(oRelateInfo["containerType"], 'show');
			this.lastDBTypeContainer = oRelateInfo["containerType"];
		}
	},
	selectValidator : function(){
		Ext.Msg.$alert(wcm.LANG['INFOVIEW_DOC_35'] || "选择校验规则！");
	},
	selectClassInfo : function(){
		ClassInfoMgr.selectClassInfo(function(_args){
			var classId = $('classId');
			if(_args.ids.length > 0){
				classId.value = _args.ids + ":" + _args.names;
				classId.focus();//cause the valid.
				$('defaultValue').focus();
			}else{
				classId.focus();//cause the valid.
			}		
		});
	},
	checkClassInfoValid : function(){
		//init the classId
		var oClassId = $('classId');
		var sClassId = oClassId.value.trim();
		if(sClassId == "" || sClassId == "0") return;
		if(sClassId.indexOf(":") > 0){
			sClassId = sClassId.split(":")[0];
		}
		//check the sClassId is valid.
		ViewFieldInfos._checkClassInfoValid(sClassId, function(sMsg, sClassInfoId, cName){
			oClassId.value = sClassId + ":" + cName;
			ValidationHelper.successRPCCallBack();
		}, function(sMsg){
			sMsg = sMsg == 'notFound' ? (wcm.LANG['INFOVIEW_DOC_36'] || "不存在对应的分类树;") : (wcm.LANG['INFOVIEW_DOC_37'] || "输入的不是数字;")
			ValidationHelper.failureRPCCallBack(sMsg);
		});		
	},
	_checkClassInfoValid : function(sClassInfoId, fValid, fInvalid){
		if(!/^[0-9]{1,8}$/.test(sClassInfoId)){
			(fInvalid || Prototype.emptyFunction)(wcm.LANG['INFOVIEW_DOC_37'] || "输入的不是数字;");
			return;
		}
		ClassInfoMgr.exists({
			propertyName : 'classInfoId',
			propertyValue : sClassInfoId
		}, fValid, fInvalid);	
	}
};

/*-----------------------------------------command button start-------------------------------*/
FloatPanel.addCloseCommand(wcm.LANG['INFOVIEW_DOC_38'] ||  "关闭");   
FloatPanel.addCommand('savebtn', wcm.LANG['INFOVIEW_DOC_39'] || '保存', 'saveData', null);
function saveData(){
	var selectClassInfos = $F('classId').split(":");
	if(selectClassInfos.length >= 1){
		$('classId').value = selectClassInfos[0];
	}
	$beginSimplePB(wcm.LANG['INFOVIEW_DOC_40'] || "正在保存数据");
	var objectId = getParameter("objectId");
	ViewFieldInfoMgr.save(objectId, "ObjectForm", null, function(transport, json){
		$endSimplePB();
		ViewFieldInfos.isChanged = true;
		FloatPanel.close(true);
	});
	return false;
}

FloatPanel.setAfterClose(function(){
	var objectId = getParameter("objectId");
	if(ViewFieldInfos.isChanged){
		$MessageCenter.sendMessage('main', 'PageContext.RefreshList', "PageContext", [objectId], true, true);
	}
});
/*-----------------------------------------command button end---------------------------------*/


/*-----------------------------------------Validation start-------------------------------*/
ValidationHelper.addValidListener(function(){
	FloatPanel.disableCommand('savebtn', false);
}, "ObjectForm");
ValidationHelper.addInvalidListener(function(){
	FloatPanel.disableCommand('savebtn', true);
}, "ObjectForm");
/*-----------------------------------------Validation end---------------------------------*/

Event.observe(window, 'load', function(){
	ViewFieldInfos.initData();
});
