var FieldInfos = {
	//方法和服务的差异将在各自的js中定义。
	//servicesName : 'wcm6_MetaDataDef',
	//findMethodName : 'findDBFieldInfoById',
	//saveMethodName : 'saveDBFieldInfo',
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
	mappingDataType : {
		'selfdefine' : {},
		'password' : {dbType : 'char'},
		'normaltext' : {dbType : 'char'},
		'multitext' : {dbType : 'char'},
		'link' : {dbType : 'char'},
		'trueorfalse' : {dbType : 'int'},
		'radio' : {containerType : 'enmValueContainer', dbType : 'char,int'},
		'checkbox' : {containerType : 'enmValueContainer', dbType : 'char'},
		'select' : {containerType : 'enmValueContainer', dbType : 'char'},
		'appendix' : {dbType : 'char'},
		'classinfo' : {containerType : 'classIdContainer', dbType : 'char'},
		'timestamp' : {dbType : 'timestamp'},
		//wenyh@2011-12-05 去掉特性库的设置入库(TODO 编辑器支持才能使用)
		'editor' : {/*containerType : 'editorContainer',*/ dbType : 'text'},
		'editorchar' : {/*containerType : 'editorContainer',*/ dbType : 'char'},
		'relation' : {containerType : 'relationViewContainer',dbType : 'char'},
		'inputselect' : {containerType : 'enmValueContainer', dbType : 'char'},
		'suggestion' : {containerType : 'enmValueContainer', dbType : 'char'},
		'selfenm' : {containerType : 'enmValueContainer', dbType : 'char'}
	},
	mappingDBDataType : {
		'float' : {containerType : 'dbScaleContainer'},
		'double' : {containerType : 'dbScaleContainer'}
	},
	typeLengthpairs : {
		'char' : ['string' , 100], 
		'int' : ['int' , 10], 
		'float' : ['float',18],
		'double' : ['double',38],
		'timeStamp' : ['date',17],
		'text' : ['text',4000]
	},
	getHelper : function(){
		return new com.trs.web2frame.BasicDataHelper();		
	},
	excludeItems : function(){
		if(this.params.objectId == 0) return;
		var oSelect = $('fieldType');
		var currDataType = oSelect.options[oSelect.selectedIndex].getAttribute("_value").toLowerCase();
		var dataTypeObjs = this.mappingDataType;
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
				Element.remove(option);
				i--;
			}
		}
	},
	initEvent : function(){
		Event.observe('fieldType', 'change', this.fieldTypeChange.bind(this));
		Event.observe('enmValue', 'change', this.clearDefault.bind(this));
		Event.observe('dbType', 'change', this.dbTypeChange.bind(this));
		Event.observe('selectClassInfo', 'click', this.selectClassInfo.bind(this));
		Event.observe('defaultSelectClassInfo', 'click', this.defaultSelectClassInfo.bind(this));
		Event.observe('selectValidator', 'click', this.selectValidator.bind(this));
		Event.observe('defaultValue', 'mouseover', showBubblePanel2);
		Event.observe('defaultValue', 'mouseout', hideBubblePanel2);
		//bind title event.
		//PopTip.registerAtOnce([$('enmValue')]);
		Event.observe('enmValue', 'dblclick',this.setEnumValue.bind(this));
		Event.observe('defaultValue', 'dblclick',this.setEnumDefaultValue.bind(this));
		
		if(this.params.objectId != 0){
			$('dbType').value = $('dbType').getAttribute("initValue");
		}

		if(this.params.objectId != 0){
			$('fieldType').value = $('fieldType').getAttribute("initValue");
			this.fieldTypeChange();
		}else{
			this.setDefaultFieldType();
		}
		//$('fieldType').fireEvent('onchange');
		
		var inputElements = $('ObjectForm').getElementsByTagName("input");
		for (var i = 0, length = inputElements.length; i < length; i++){
			Event.observe(inputElements[i], 'focus', function(){
				this.select();
			}.bind(inputElements[i]));
		}

		//为了使库中类型指定多选，先保存所有的options
		this.storeItems();

		this.excludeItems();

		ValidationHelper.initValidation(); 
	},
	opts: [],
	storeItems : function(){
		if(this.params.objectId != 0) return;
		var options = $('dbType').options;
		var opts = this.opts;
		for (var i = 0; i < options.length; i++){
			opts.push(options[i]);
		}
	},
	restoreItems : function(sIncludeOpts){
		if(this.params.objectId != 0) return;
		var options = $('dbType').options;
		var sOldValue = $('dbType').value;
		while(options.length > 0){
			options.remove(0);
		}

		if(sIncludeOpts){
			var aDBKey = sIncludeOpts.split(",");
			var opts = this.opts;
			for (var i = 0; i < opts.length; i++){
				if(aDBKey.indexOf(opts[i].getAttribute("_value")) >= 0){
					options.add(opts[i]);
				}				
			}
		}else{
			var opts = this.opts;
			for (var i = 0; i < opts.length; i++){
				options.add(opts[i]);
			}
		}
		$('dbType').value = sOldValue;

	},
	clearDefault : function(){
		if($("enmValue").value.replace(/(^\s*)|(\s*$)/g,"") =="")
			$("defaultValue").value = "";
	},
	setEnumDefaultValue : function(){
		var fieldType = $('fieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var sFieldType = option.getAttribute("_value").toLowerCase();
		//处理枚举值
		var disableDefaultValueArray = ['radio', 'checkbox', 'select', 'inputselect','suggestion'];
		if(disableDefaultValueArray.include(sFieldType) && $('enmValue').value.replace(/(^\s*)|(\s*$)/g,"")!=""){
			this.setEnumValue();
		}
	},
	setEnumValue : function(){
		var enumValue = $('enmValue');
		var defaultValue = $('defaultValue');
		var isRadio = true;
		var fieldType = $('fieldType');
		if(fieldType.options[fieldType.selectedIndex].getAttribute('_value') == "checkbox"){
			isRadio = false;
		}
		//使失去焦点，否则新打开页面的焦点仍在enumValue上。
		enumValue.blur();
		var sTitle = '<font style="font-size:12px;font-weight:normal;color:green;">'+(wcm.LANG.METADATA_ALERT_4||'按Enter追加新枚举值')+'</font>';
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
				enumValue.focus();
				defaultValue.value = _args ? _args["defaultValue"] : "";
			}
		});
	},
	setDefaultFieldType : function(){
		var fieldType = $('fieldType');
		for (var i = 0, length = fieldType.options.length; i < length; i++){
			if(fieldType.options[i].getAttribute("_value") == this.defaultFieldTypeValue){
				fieldType.selectedIndex = i;  //ie9 这样改变selectedIndex不会触发change事件
				//this.fieldTypeChange();
				break;
			}
		}
		this.setDBLength(this.defaultFieldTypeValue);
	},
	setDBLength : function(fieldTypeKey){
		var dbLength = $('dbLength');
		if('editor' == fieldTypeKey){
			dbLength.value = 0;
		}else{
			dbLength.value = Math.max(dbLength.getAttribute("_minvalue")||0,FieldInfos.dataTypeMappings[fieldTypeKey] || 0);
		}		
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
		//在viewfieldinfo_add_edit和fieldinfo_add_edit中被分别重载
	},
	fieldTypeChange : function(event){
		//库中类型支持多选，为了不影响原逻辑，先创造和修改前一样的环境，即：还原所有的opts
		this.restoreItems();

		if(this.lastFieldTypeContainer){
			this.showHideControls(this.lastFieldTypeContainer, 'hide');
		}
		var fieldType = $('fieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var oRelateInfo = this.mappingDataType[option.getAttribute("_value").toLowerCase()];
		if(oRelateInfo && oRelateInfo["containerType"]){
			this.lastFieldTypeContainer = oRelateInfo["containerType"];
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
			if(oRelateInfo["dbType"].indexOf(",") == -1){
				for (var i = 0, length = dbType.options.length; i < length; i++){
					var option = dbType.options[i];
					if(option.getAttribute("_value").toLowerCase() == oRelateInfo["dbType"]){
						dbType.value = option.value;
						//$('dbType').fireEvent('onchange');
						elementFireClickEvent($('dbType'));
						break;
					}
				}
				dbType.disabled = true;
			}else{
				this.restoreItems(oRelateInfo["dbType"]);
				//$('dbType').fireEvent('onchange');
				elementFireClickEvent($('dbType'));
				if(this.params.objectId == 0){
					dbType.disabled = false;
				}else{
					dbType.disabled = true;	
				}
			}
		}else{
			if(this.params.objectId == 0){
				dbType.disabled = false;	
			}else{
				dbType.disabled = true;	
			}
		}
		this.disableDefaultValue();
		this.toggleDefaultValueContainer();
		this.toggleDbScaleContainer();
		this.toggleAttrRelation();  //控制不同字段不同属性的显隐。此处为扩展结构，可支持不同类型的字段及其属性的关联显隐
		//add by wyw 应要求清空默认值
		if($('defaultValue').getAttribute('loaded') == null){
			$('defaultValue').setAttribute('loaded', true);
		}else{
			$('defaultValue').value = "";
		}
		//改变类型后，库中长度重现赋值，需重新校验
		if($('dbLength').offsetWidth > 0){
			ValidationHelper.forceValid('dbLength');
		}		
	},
	disableDefaultValue : function(){
		var fieldType = $('fieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var sFieldType = option.getAttribute("_value").toLowerCase();
		//处理枚举值
		var disableDefaultValueArray = ['radio', 'checkbox', 'select', 'inputselect','suggestion'];
		if(disableDefaultValueArray.include(sFieldType)){
			$('plainTip').innerHTML = "";//wcm.LANG.METADATA_ALERT_5 || "可双击枚举值框进行设置";			
			$('defaultValue').readOnly = true;
			$('defaultValue').onfocus=function(){this.blur()};
		}else{
			$('plainTip').innerHTML = "";
			$('defaultValue').readOnly = false;
			$('defaultValue').onfocus=null;
		}
		//处理分类法
		if(sFieldType == "classinfo"){
			$('defaultValue').disabled = true;
		}
	},
	hideDefaultValueArray : ['multitext', 'appendix', 'editor', 'editorchar', 'relation'],
	toggleDefaultValueContainer : function(){
		var fieldType = $('fieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var sFieldType = option.getAttribute("_value").toLowerCase();

		var defaultValue = $("defaultValue");
		var dbTypeSel = $('dbType');
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
	toggleDbScaleContainer : function(){
		var fieldType = $('fieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var sFieldType = option.getAttribute("_value").toLowerCase();

		var isHide = true;
		if(sFieldType == "selfdefine"){
			var dbType = $('dbType');
			var option = dbType.options[dbType.selectedIndex];
			var sDBType = option.getAttribute("_value").toLowerCase();
			if(sDBType == "float" || sDBType == "double"){
				isHide = false;
			}
		}else{
			isHide = true;
		}
		var sMethod = isHide ? "hide" : "show";
		this.showHideControls("dbScaleContainer", sMethod);
		if(!isHide) {
			if(this.params.objectId){
				$('dbScale').disabled = true;
			}
		}
	},
	toggleAttrRelation : function(){
		//在viewfieldinfo_add_edit和fieldinfo_add_edit中被分别重载
	},
	dbTypeChange : function(event){
		var dbType = $('dbType');
		var option = dbType.options[dbType.selectedIndex];
		var maxLength = option.getAttribute("_maxLength");
		var displayMethod = maxLength == "0" ? 'hide' : 'show'; 
		if(option.value == 6 || option.value == 8){
			displayMethod = 'hide';
		}
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
		this.toggleDefaultValueContainer();
	},
	selectValidator : function(){
		Ext.Msg.alert(wcm.LANG.METADATA_ALERT_6 || "选择校验规则！");
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
				//classId.focus();//cause the valid.
				//$('defaultValue').focus();
				//当classid改变，则原来的默认值失效	，否则分类法树取到的还是原来的classid的
				if(sClassIdValue != _args.selectedIds){
					$("defaultValue").value = "";
				}
				//check the class info valid.
				FieldInfos.checkClassInfoValid(
					function(){
						Element.update("validTip", "");
						FloatPanel.disableCommand('saveData', false);
					},
					function(sMsg){
						sMsg = sMsg == 'notFound' ? (wcm.LANG.METADATA_ALERT_8 || "不存在对应的分类法.") : (wcm.LANG.METADATA_ALERT_9 || "输入的不是正整数.")
						Element.update("validTip", "<font color='red'>" + sMsg + "</font>");
						FloatPanel.disableCommand('saveData', true);
					}
				);
			}else{
				classId.value = "";
				$("defaultValue").value = "";
				//classId.focus();//cause the valid.
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
			sMsg = sMsg == 'notFound' ? (wcm.LANG.METADATA_ALERT_8 || "不存在对应的分类法.") : (wcm.LANG.METADATA_ALERT_9 || "输入的不是正整数.")
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

function setNotNullEvent(oNotNull){
	var oEnmValue = $("enmValue");
	var fieldType = $('fieldType');
	var option = fieldType.options[fieldType.selectedIndex];
	var sFieldType = option.getAttribute("_value").toLowerCase();

	var enmTypeArray = ['radio', 'checkbox', 'select', 'inputselect','suggestion'];
	if(oEnmValue && enmTypeArray.include(sFieldType)){
		if(!(oEnmValue.value) && $('fieldType').value!=15 && $('fieldType').value!=17 && oNotNull.checked == true){
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

function beforeSaveCheck(){
	//校验关键字
	if(containKeyWordsInFields('fieldName')){
		return false;
	}

	//if defaultValueContainer hidden, then set the value of defaultvalue to ""
	if(!Element.visible("defaultValueContainer")){
		$('defaultValue').value = "";
	}

	//校验默认值的范围
	var sDefaultValue = $F('defaultValue').trim();
	var dbLength = $('dbLength');
	if(sDefaultValue != ""){
		var sErrMsg = "";
		//1、类型校验
		var nDBType = $('dbType').value;

		var fieldType = $('fieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var sFieldType = option.getAttribute("_value").toLowerCase();

		switch(nDBType){
			case "4"://int
				var regExp = new RegExp('^-?\\d+(e[\+-]?\\d+)?$', "i");
				if(!regExp.test(sDefaultValue)){
					sErrMsg = String.format("字段默认值[{0}]不是合法的整型数.",sDefaultValue);
					break;
				}
				var nDefaultValue = parseInt(sDefaultValue, 10);
				var nMinValue = 2 << 30;
				var nMaxValue = -(2 << 30) - 1;
				if(sDefaultValue.length > 10 || nMinValue > nDefaultValue || nDefaultValue > nMaxValue){
					sErrMsg = String.format("字段默认值[{0}]的范围不对，应该在-2^31({1})到2^31-1({1})之间",nDefaultValue, nMinValue,nMaxValue);
					break;
				}
				break;
			case "6"://float
			case "8"://double
				var regExp = new RegExp('^-?\\d+(\.\\d+)?(e[\+-]?\\d+)?$', "i");
				if(!regExp.test(sDefaultValue)){
					sErrMsg = String.format("字段默认值[{0}]不是合法的小数.",sDefaultValue);
					break;
				}
				var nDefaultValue = parseFloat(sDefaultValue, 10);
				var nMinValue = -2e125;
				var nMaxValue = 2e125;
				if(nMinValue > nDefaultValue || nDefaultValue > nMaxValue){
					sErrMsg = String.format("字段默认值[{0}]的范围不对，应该在-2e125({0})到2e125( {1})之间",sDefaultValue, nMinValue,nMaxValue);
					break;
				}
				break;
			case "12"://char
				if(Element.visible('dbLengthContainer')){
					if(dbLength.value && sDefaultValue.byteLength() > dbLength.value){
						sErrMsg = wcm.LANG.METADATA_ALERT_22 || "字段默认值长度超出了库中长度，请重设.";
						break;
					}
				}
				break;
			case "93"://timestamp
				if(sFieldType == "timestamp"){
					if(sDefaultValue.startsWith('$sysdate')){
						var date = new Date();
						date.setDate(eval(sDefaultValue.replace('$sysdate', date.getDate())));
						sDefaultValue = date.getYear() + "-" + toNumberString((date.getMonth() + 1)) + "-" + toNumberString(date.getDate());
					}
					sErrMsg = DateHelper.check(sDefaultValue, "yyyy-mm-dd");
				}
				if(sFieldType == "selfdefine"){
					sErrMsg = DateHelper.check(sDefaultValue, "yyyy-mm-dd HH:MM:ss");
				}
				break;
		}

		//2、实际类型校验
		switch(sFieldType){
			case "trueorfalse" :
				if($('defaultValue').value != "0" && $('defaultValue').value != "1"){
					sErrMsg =wcm.LANG.METADATA_ALERT_23 || "是否类型只能选0或1,请重设.";
					break;
				}
		}
		if(sErrMsg){
			try{
				Ext.Msg.alert(sErrMsg, function(){
					setTimeout(function(){
						if(Element.visible('defaultValueContainer')){
							if($('defaultValue').disabled) return;
							$('defaultValue').focus();
						}
					}, 10);
				});
			}catch(error){
			}
			return false;
		}
	}

	var sEnmValue = $F("enmValue");
	if(Element.visible("enmValueContainer")){
		//枚举值类型的长度的校验不应该与dbLength比较，注掉此处的校验，在元素自己的属性上添加校验。
		/*
		if(sEnmValue.length > 0 && sEnmValue.byteLength() > dbLength.value){
			sErrMsg = wcm.LANG.METADATA_ALERT_24 || "枚举值长度超出了库中长度，请重设.";
		}
		*/

		if(window.validViewFieldEnmValue){
			var sDBfieldEnmValue = $('enmValue').getAttribute("dbfieldEnmValue");
			if(!validViewFieldEnmValue(sEnmValue, sDBfieldEnmValue)){
				sErrMsg = wcm.LANG.METADATA_ALERT_34 || "输入的枚举值必须包含在元数据对应字段的枚举值中。";
			}
		}
		if(sErrMsg){
			try{
				Ext.Msg.alert(sErrMsg, function(){
					setTimeout(function(){
						if(Element.visible('enmValueContainer')){
							if($('enmValue').disabled) return;
							$('enmValue').focus();
						}
					}, 10);
				});
			}catch(error){
			}
			return false;
		}
	}
	
	var nDBType = $('dbType').value;
	var dbScale = $('dbScale');
	if(Element.visible('dbScaleContainer')){
		var dbScaleMsg = "";
		switch(nDBType){
			case "6"://float
			case "8"://double
				if(dbScale.value.trim() == ""){
					dbScaleMsg = wcm.LANG.METADATA_ALERT_25 || '小数位数不能为空.';
					break;
				}
				if(nDBType == 6 && dbScale.value.trim() > 18){
					dbScaleMsg = String.format('小数位数不能超过18');
					break;										
				}
				if(nDBType == 8 && dbScale.value.trim() > 38){
					dbScaleMsg = String.format('小数位数不能超过38') ;
					break;					
				}
				var defaultValue = $F("defaultValue");
				if(defaultValue){
					var dotIndex = 0;
					if((dotIndex = defaultValue.indexOf(".")) > 0){
						var decimalPart = defaultValue.substr(dotIndex + 1);
						if(decimalPart && decimalPart.length > 0){
							if(decimalPart.length > dbScale.value){
								dbScaleMsg = wcm.LANG.METADATA_ALERT_27 || "字段默认值的小数位数超长,请重设.";
							}
						}
					}
				}
		}
		if(dbScaleMsg){
			try{
				Ext.Msg.alert(dbScaleMsg, function(){
					setTimeout(function(){
						if(dbScale.disabled) return;
						dbScale.focus();
					}, 10);
				});
			}catch(error){
			}
			return false;
		}
	}

	if(Element.visible('dbLengthContainer')){
		var dbLengthMsg = "";
		if(dbLength.value.trim() == ""){
			dbLengthMsg = wcm.LANG.METADATA_ALERT_28 || '库中长度不能为空.';
		}
		if(dbLength.value.trim() > 4000){
			dbLengthMsg = wcm.LANG.METADATA_ALERT_29 || '库中长度超过了字符串类型的最大允许值（4000）.';
		}
		if(dbLengthMsg){
			try{
				Ext.Msg.alert(dbLengthMsg, function(){
					setTimeout(function(){
						dbLength.focus();
					}, 10);
				});
			}catch(error){
			}
			return false;
		}
	}

	var oAnotherName = $('anotherName');
	if(oAnotherName){
		var msg = null;
		if(/[<>;&]/.test(oAnotherName.value)){
			msg = "<font color='red'>" 
				+ (wcm.LANG.METADATA_ALERT_30 || "中文名称中含有特殊字符")
				+ "<b>;&&lt;&gt;</b></font>.";
		}
		if(msg){
			try{
				Ext.Msg.alert(msg, function(){
					setTimeout(function(){
						oAnotherName.focus();
					}, 10);
				});
			}catch(error){
			}
			return false;
		}
	}
	
	var radorchk = $('RADORCHK');
	if(Element.visible('radioOrCheckContainer')){
		var radorchkMsg = "";
		if(radorchk.checked){
			if(sDefaultValue && sDefaultValue.split(",").length > 1){
				radorchkMsg = wcm.LANG.METADATA_ALERT_31 || "单选树只能选择一个默认的分类法，请重设.";
			}
		}
		if(radorchkMsg){
			try{
				Ext.Msg.alert(radorchkMsg, function(){
					setTimeout(function(){
						radorchk.focus();
					}, 10);
				});
			}catch(error){
			}
			return false;
		}
	}

	//临时添加的特殊处理
	if(!setNotNullEvent($("notNull"))){
		return false;
	}
	return true;
}

function showBubblePanel2(event){
	var fieldType = $('fieldType');
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

function toNumberString(nNumber){
	return (nNumber > 9)?(nNumber+""):("0"+nNumber);
}
function elementFireClickEvent(el){
	if(el.dispatchEvent){
		var evt = document.createEvent('Event');
		evt.initEvent('change',true,true);
		el.dispatchEvent(evt);   
	}else if(el.fireEvent){
		el.fireEvent("onchange");
	}
}