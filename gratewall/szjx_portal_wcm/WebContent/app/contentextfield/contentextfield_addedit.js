var ObjectTypeConst_CONTENTEXTFIELD = 1319922479;//to be a global var?
var PageContext = {};
Object.extend(PageContext,{
	objId :	getParameter("ObjectId"),
	mappingDataType : {
		'selfdefine' : {},
		'password' : {dbType : 'char'},
		'normaltext' : {dbType : 'char'},
		'multitext' : {dbType : 'char'},
		'editor' : {dbType : 'char'},
		'radio' : {containerType : 'enmValueContainer', dbType : 'char'},
		'checkbox' : {containerType : 'enmValueContainer', dbType : 'char'},
		'select' : {containerType : 'enmValueContainer', dbType : 'char'},
		'timestamp' : {dbType : 'timestamp'},
		'inputselect' : {containerType : 'enmValueContainer', dbType : 'char'},
		'suggestion' : {containerType : 'enmValueContainer', dbType : 'char'},
		'selfenm' : {containerType : 'enmValueContainer', dbType : 'char'}
	},
	mappingDBDataType : {
		'float': {containerType : 'dbScaleContainer'},
		'double' : {containerType : 'dbScaleContainer'}
	},
	typeLengthpairs : {
		'char' : ['string' , 100 , 1000], 
		'int' : ['int' , 10 , 4], 
		'float' : ['float',18, 8],
		'timeStamp' : ['date',17, 8]
	},
	setDBLength : function(fieldTypeKey){
		var dbLength = $('dbLength');
		var bIsShow = true;
		dbLength.value =  $("FieldType").options[$("FieldType").selectedIndex].getAttribute("_maxLength")|| 0;	
		if(fieldTypeKey.toLowerCase() == 'trueorfalse' || fieldTypeKey.toLowerCase() == 'timestamp'){
			bIsShow = false;
		}
		if(fieldTypeKey.toLowerCase() == 'selfdefine'){
			var dbTypeSel = $('dbType');
			if(PageContext.typeLengthpairs[dbTypeSel.value.toLowerCase()])
				dbLength.value = PageContext.typeLengthpairs[dbTypeSel.value.toLowerCase()][2];
			if(dbTypeSel.selectedIndex != 0){
				bIsShow = false;
			}
		}
		var displayMethod = bIsShow ? 'show' : 'hide';
		Element[displayMethod]('dbLengthContainer');
		if(displayMethod == "show"){
			ValidationHelper.forceValid(dbLength);
			ValidationHelper.blurEvent(dbLength);
		}
	},
	init : function(_params){
		var hostId = getParameter("HostId");
		var hostType = getParameter("HostType");
		this.params = {"ObjectId":PageContext.objId,"HostId":hostId,"HostType":hostType};		
		
		this.serviceId = "wcm6_extendfield";
		this.lastFieldTypeContainer = '',
		this.lastDBTypeContainer = '',
		this.defaultFieldTypeValue = 'normalText',
		this.helpers = {};
		this.fOnSuceesses = {};	
		this.helpers["DataTypes"] = BasicDataHelper;
		this.fOnSuceesses["DataTypes"] = function(_transport,_json){	
			PageContext.initSpecialValue();
			if(PageContext.objId == 0){
				PageContext.makePhysicalFieldsSelect();	
			}
		};
	},	
	setDefaultFieldType : function(){
		var fieldType = $('FieldType');
		for (var i = 0, length = fieldType.options.length; i < length; i++){
			if(fieldType.options[i].getAttribute("_type") == this.defaultFieldTypeValue){
				fieldType.selectedIndex = i;
				break;
			}
		}
		PageContext.setDBLength(this.defaultFieldTypeValue);
	},
	setDefaultDBType : function(){
		var dbType = $('dbType');
		for (var i = 0, length = dbType.options.length; i < length; i++){
			if(dbType.options[i].innerHTML == dbType.getAttribute("initValue")){
				dbType.selectedIndex = i;
				break;
			}
		}
	},
	initSpecialValue : function(){	
		Event.observe($("dbType"),'change',this.dbTypeChange.bind(this));
		Event.observe($("FieldType"),'change',this.fieldTypeChange.bind(this));
		Event.observe('enmValue', 'dblclick',function(){
			var enumValue = $('enmValue');
			var defaultValue = $('defaultValue');
			var isRadio = true;
			var fieldType = $('FieldType');
			if(fieldType.options[fieldType.selectedIndex].getAttribute('_type') == "checkbox"){
				isRadio = false;
			}
			enumValue.blur();
			var sTitle = '<font style="font-size:12px;font-weight:normal;">' + (wcm.LANG.CONTENTEXTFIELD_CONFIRM_22 || '构造枚举值') + '</font>';
			var sURL = WCMConstants.WCM6_PATH + 'contentextfield/enumvalue_create.html';
			wcm.CrashBoarder.get('setEnumValue').show({
				title : sTitle,
				src : sURL,
				width: '530px',
				height: '270px',
				params : {enumValue : enumValue.value, defaultValue : defaultValue.value, isRadio : isRadio},
				reloadable : true,
				maskable : true,
				callback : function(_args){
					enumValue.value = _args ? _args["enumValue"] : "";
					defaultValue.value = _args ? _args["defaultValue"] : "";
				}
			});
		});
 
		if(this.objId != 0){
			$('FieldType').value = $('FieldType').getAttribute("initValue");
		}else{
			PageContext.setDefaultFieldType();
		}

		if(this.objId != 0){
			PageContext.setDefaultDBType();
		}
		if($('FieldType').dispatchEvent){//all browsers except IE.version < 9 
			var evt = document.createEvent('Event'); 
			evt.initEvent('change',true,true); 
			$('FieldType').dispatchEvent(evt);
		}else if($('FieldType').fireEvent){
			$('FieldType').fireEvent('onchange');
		} 
		

		var inputElements = $('createContainer').getElementsByTagName("input");
		for (var i = 0, length = inputElements.length; i < length; i++){
			Event.observe(inputElements[i], 'focus', function(){
				this.select();
			}.bind(inputElements[i]));
		}
		PageContext.excludeItems();
	},
	excludeItems : function(){
		if(this.objId == 0) return;
		var oSelect = $('FieldType');
		var currDataType = oSelect.options[oSelect.selectedIndex].getAttribute("_type").toLowerCase();
		var dataTypeObjs = this.mappingDataType;
		var currDBType = dataTypeObjs[currDataType]["dbType"];
		if(!currDBType){
			var dbTypeSel = $("dbType");
			currDBType = dbTypeSel.options[dbTypeSel.selectedIndex].getAttribute("_type").toLowerCase();
		}
		for (var i = 0; i < oSelect.options.length; i++){
			var option = oSelect.options[i];
			var dataType = option.getAttribute("_type").toLowerCase();
			if(dataType == 'selfdefine') continue;
			var dbType = dataTypeObjs[dataType]["dbType"];
			if(dbType != currDBType){				
				Element.remove(option);
				i--;
			}
		}
	},
	loadPhysicalFields : function(){
		Object.extend(PageContext.params,{PageSize:-1});
		this.helpers["ExistsFields"].call('queryExtendFields',PageContext.params,false,this.fOnSuceesses["ExistsFields"]);
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
				if(elements[i].name != "dbLength"){
					elements[i].setAttribute('ignore', true);
				}
			}
			ValidationHelper.popElements(elements);
		}
	},
	toggleDbScaleContainer : function(){
		var fieldType = $('FieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var sFieldType = option.getAttribute("_type").toLowerCase();

		var isHide = true;
		if(sFieldType == "selfdefine"){
			var dbType = $('dbType');
			var option = dbType.options[dbType.selectedIndex];
			var sDBType = option.getAttribute("_type").toLowerCase();
			if(sDBType == "float"){
				isHide = false;
			}
		}else{
			isHide = true;
		}
		var sMethod = isHide ? "hide" : "show";
		this.showHideControls("dbScaleContainer", sMethod);
	},
	toggleDefaultValueContainer : function(){
		var fieldType = $('FieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var sFieldType = option.getAttribute("_type").toLowerCase();

		var defaultValue = $("defaultValue");
		var dbTypeSel = $('dbType');
		if(defaultValue && dbTypeSel){
			var _type = dbTypeSel.options[dbTypeSel.selectedIndex].getAttribute("_type");
			if(PageContext.typeLengthpairs[_type][0] != "text"){

				if(ValidationHelper.hasValid(defaultValue)){
					ValidationHelper.popElements(defaultValue);
				}

				ValidationHelper.registerValidations([
					{
						renderTo : 'defaultValue',
						type : PageContext.typeLengthpairs[_type][0] ,
						max_len : PageContext.typeLengthpairs[_type][1],
						showid : 'defaultValidTip',
						desc : wcm.LANG.CONTENTEXTFIELD_CONFIRM_27 || '默认值',
						date_format	: "yyyy-mm-dd hh:MM",
						showDefault : true
					}
				]);
			}
			ValidationHelper.initValidation();
		}

		var isHide = false;
		if(sFieldType == "selfdefine"){
			var dbType = $('dbType');
			var option = dbType.options[dbType.selectedIndex];
			var sDBType = option.getAttribute("_type").toLowerCase();
			if(sDBType == "text"){
				isHide = true;
			}
		}else if(sFieldType == "multitext"){
			isHide = true;
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
		var FieldType = $('FieldType');
		var FieldOption = FieldType.options[FieldType.selectedIndex];
		var option = dbType.options[dbType.selectedIndex];
		var maxLength = option.getAttribute("_maxLength");
		if(PageContext.objId == 0){
			$("dbLength").value = maxLength;
		}
		// 如果在数据库中的字段类型不是文本，不显示长度限制
		var displayMethod = dbType.selectedIndex!=0 ? 'hide' : 'show';
		this.showHideControls('dbLengthContainer', displayMethod);
		var oRelateInfo = PageContext.mappingDBDataType[option.getAttribute("_type").toLowerCase()];
		if(this.lastDBTypeContainer){
			Element.hide(this.lastDBTypeContainer);
		}
		if(oRelateInfo && oRelateInfo["containerType"]){
			this.showHideControls(oRelateInfo["containerType"], 'show');
			this.lastDBTypeContainer = oRelateInfo["containerType"];
		}
		PageContext.toggleDefaultValueContainer();
		if($("defaultValue").value.trim() != ""){
			ValidationHelper.forceValid($("defaultValue"));
		}
	},
	disableDefaultValue : function(){
		var fieldType = $('FieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var sFieldType = option.getAttribute("_type").toLowerCase();
		//处理枚举值
		var disableDefaultValueArray = ['multitext','editor','radio', 'checkbox', 'select', 'inputselect'];
		if(disableDefaultValueArray.include(sFieldType)){
			Element.hide("defaultValueContainer");
			
		}else{
			Element.show("defaultValueContainer");
		}
	},
	fieldTypeChange : function(){
		if(this.lastFieldTypeContainer){
			PageContext.showHideControls(this.lastFieldTypeContainer, 'hide');
		}
		var fieldType = $('FieldType');
		var option = fieldType.options[fieldType.selectedIndex];
		var oRelateInfo = PageContext.mappingDataType[option.getAttribute("_type").toLowerCase()];
		if(oRelateInfo && oRelateInfo["containerType"]){
			this.lastFieldTypeContainer = oRelateInfo["containerType"];
			PageContext.showHideControls(oRelateInfo["containerType"], 'show');
		}
		var dbLength = $('dbLength');
		var dbType = $('dbType');
		if(oRelateInfo && oRelateInfo["dbType"]){
			for (var i = 0, length = dbType.options.length; i < length; i++){
				var option = dbType.options[i];
				if(option.getAttribute("_type").toLowerCase() == oRelateInfo["dbType"]){
					dbType.value = option.value;
					if($('dbType').dispatchEvent){//all browsers except IE.version < 9 
						var evt = document.createEvent('Event'); 
						evt.initEvent('change',true,true); 
						$('dbType').dispatchEvent(evt);
					}else if($('dbType').fireEvent){
						$('dbType').fireEvent('onchange');
					}
					break;
				}
			}
			dbType.disabled = true;
		}else{
			if(this.objId == 0){
				dbType.disabled = false;	
			}else{
				dbType.disabled = true;	
			}
		}
		var isFirstLoaded = dbLength.getAttribute('firstLoaded');
		if(isFirstLoaded){
			PageContext.dbTypeChange();
		}
		if(isFirstLoaded){
			dbLength.removeAttribute('firstLoaded');
			if(dbLength.value.trim() == "" || dbLength.value.trim() == "0"){
				PageContext.setDBLength(option.getAttribute("_type"));
			}
		}else{
			PageContext.setDBLength(option.getAttribute("_type"));
		}

		PageContext.toggleDefaultValueContainer();
		PageContext.disableDefaultValue();
		PageContext.toggleDbScaleContainer();
		if(!isFirstLoaded && $("defaultValue").value.trim() != ""){
			ValidationHelper.forceValid($("defaultValue"));
		}
	},
	makePhysicalFieldsSelect : function(){
		Event.observe($("cancelFieldSelected"),'click',function(){
			$("selFieldInfo").value ="";
			$("FieldName").value = "";
			$("FieldName").disabled = false;
			$('selFieldInfo').disabled = false;
			$("FieldName").focus();

			$("FieldType").selectedIndex = 0;
			$("FieldType").disabled = false;
			$("dbType").selectedIndex = 0;
			$("FieldDesc").value = "";
			$("defaultValue").value = "";
			//$("syncchildren").checked = false;
			PageContext.fieldTypeChange();
			Element.hide($("cancelFieldSelected"));
		});
		Ext.get('selFieldInfo').on('change', function(_event){
			var selFieldInfo = $('selFieldInfo');
			var optionCurr = selFieldInfo.options[selFieldInfo.selectedIndex];
			if(optionCurr==null || optionCurr.value == -1)return;
			$('FieldName').value = optionCurr.getAttribute('_fieldName');
			$('FieldName').disabled = true;
			ValidatorHelper.forceValid($('FieldName'));
			$('selFieldInfo').value = optionCurr.getAttribute('_dataType');
			var fdType = optionCurr.getAttribute('_fieldType');
			$('FieldType').value = fdType.trim() != "" ? fdType : 1 ;
			ValidatorHelper.forceValid($('FieldType'));
			$('selFieldInfo').disabled = true;
			for(var i =0; i < $("dbType").options.length;i++){
				if($("dbType").options[i].innerHTML == optionCurr.value.trim()){
					$("dbType").options[i].selected = true;
				}
			}
			ValidatorHelper.forceValid($('dbType'));
			$('selFieldInfo').disabled = true;
			$('dbLength').value = optionCurr.getAttribute('_fieldLength');
			PageContext.fieldTypeChange();
			$("defaultValue").value = optionCurr.getAttribute("_defaultValue");
			$("dbScale").value = optionCurr.getAttribute("_scale");
			if(!$("dbScale").value){
				Element.hide("dbScaleContainer");
			}
			if(!$("defaultValue").value){
				Element.hide("defaultValueContainer");
			}
			$("enmValue").value = optionCurr.getAttribute("_enum");
			ValidatorHelper.forceValid($('FieldDesc'));
			$('FieldDesc').focus();
			Element.show($("cancelFieldSelected"));
			return false;
		});
		
		this.FieldName = $("FieldName");
		this.FieldDesc = $("FieldDesc");
		this.FieldType = $("FieldType");
		this.FieldLength = $("attr_fieldlength_in");
		this.ExtFieldId = $("ExtFieldId");
	},
	loadDataTypes : function(){		
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call(this.serviceId,'getSupportedDataTypes',{},false,this.fOnSuceesses['DataTypes']);		
	}
});
PageContext.init();

function getOptionValue( _nIndex){
	var selFieldInfo = $('selFieldInfo');
	var optionCurr = selFieldInfo.options[_nIndex];
	if(optionCurr==null)return;
	$('FieldName').value = optionCurr.getAttribute('_fieldName');
	$('FieldName').disabled = true;
	var fdType = optionCurr.getAttribute('_fieldType');
	$('FieldType').value = fdType.trim() != "" ? fdType : 1 ;
	ValidatorHelper.forceValid($('FieldType'));
	$('FieldType').disabled = true;
	for(var i =0; i < $("dbType").options.length;i++){
		if($("dbType").options[i].innerHTML == optionCurr.value){
			$("dbType").options[i].selected = true;
		}
	}
	PageContext.fieldTypeChange();
	$('dbLength').value = optionCurr.getAttribute('_fieldLength');
	$("defaultValue").value = optionCurr.getAttribute("_defaultValue");
	$("dbScale").value = optionCurr.getAttribute("_scale");
	$("enmValue").value = optionCurr.getAttribute("_enum");
	ValidatorHelper.forceValid($('FieldDesc'));
	$('FieldDesc').focus();
	Element.show($("cancelFieldSelected"));
	return false;
}
function getChildNodeValue(_parentNode,_name,_attr){
	var childNode = _parentNode.selectSingleNode(_name);
	if(childNode != null){
		if(_attr){
			return childNode.getAttribute(_attr) || "";
		}
		return childNode.text;
	}

	return "";
}
function pairSplit (sPair){
	var arValue = new Array();
	if(sPair.indexOf("`") != -1){
		var tempArr = sPair.split("`");
		if(tempArr.length == 2){
			arValue[0] = tempArr[0];
			arValue[1] = tempArr[1];
		}else if(tempArr.length == 1){
			arValue[0] = arValue[1] = tempArr[0];
		}else{
			return arValue;
		}
	}else{
		arValue[0] = sPair;
		arValue[1] = sPair;
	}
	return arValue;
}
function saveExtField(){
	//首先判断这两个必填项的状态
	if(!Element.visible("dbLengthContainer")){
		$("dbLength").removeAttribute("validation");
	}
	if(!Element.visible("dbScaleContainer")){
		$("dbScale").removeAttribute("validation");
	}
	if(!ValidationHelper.doValid('addEditForm')){
		return false;
	}

	if(!Element.visible("defaultValueContainer") && "678".indexOf($F("FieldType")) == -1){
		$('defaultValue').value = "";
	}
	//校验默认值的范围
	var sDefaultValue = $F('defaultValue').trim();
	if(sDefaultValue != ""){
		if(!checkDefault(sDefaultValue))
			return false;
	}
	//枚举值检测
	var sEnmValue = $F("enmValue");
	var sErrMsg = "";
	if(Element.visible("enmValue")){
		if(sEnmValue.length > 0 && sEnmValue.length > $F('dbLength')){
			sErrMsg = wcm.LANG.CONTENTEXTFIELD_CONFIRM_40 || "枚举值长度超出了库中长度，请重设.";
		}
		if(sEnmValue.trim().length > 0){
		   var arValues = sEnmValue.split("~");
		   var temp = new Array();
			for(var ix=0,len=arValues.length;ix<len;ix++){
				var arValue = pairSplit(arValues[ix]);
				temp.push(arValue[1]);
			}
			temp = temp.sort();
			for(var i=0;i<temp.length-1;i++){
				if(temp[i].trim() == temp[i+1].trim()){
					sErrMsg = wcm.LANG.CONTENTEXTFIELD_CONFIRM_46 || "枚举值不允许存在值相同情况.(值为空时默认会取描述为值)";
				}
			}
		}
		
		if(sErrMsg){
			try{
				Ext.Msg.alert(sErrMsg, function(){
					setTimeout(function(){
						if(Element.visible('enmValue')){
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
	this.doSave = function(){

		PageContext.helpers["DataTypes"].call("wcm6_extendfield",'save','addEditForm',true,function(_trans){
			notifyFPCallback(_trans);
			FloatPanel.close();
		}, function(_trans, _json){
			Ext.Msg.fault({
                message : $v(_json, "FAULT.MESSAGE"),
                detail :  $v(_json, "FAULT.DETAIL")
            });
		});	
	}
	
	var zSync = $("ContainsChildren").value = $("syncchildren").checked;
	if(zSync){
		 Ext.Msg.confirm(wcm.LANG.CONTENTEXTFIELD_CONFIRM_1 || "确定要同步创建到所有子对象吗?", {
                yes : function(){
                    doSave();
                }
            });
	}else{
		this.doSave();
	}
	
	return false;
}

function checkDefault(sDefaultValue){
	var sErrMsg = "";
	//1,类型校验
	var nDBType = $('dbType').value;
	switch(nDBType){
		case "INT"://int
			var regExp = new RegExp('^-?\\d+(e[\+-]?\\d+)?$', "i");
			if(!regExp.test(sDefaultValue)){
				sErrMsg = (String.format("默认值[{0}]不是合法的整型数.", sDefaultValue));
				break;
			}
			var nDefaultValue = parseInt(sDefaultValue, 10);
			var nMinValue = 2 << 30;
			var nMaxValue = -(2 << 30) - 1;
			if(nMinValue > nDefaultValue || nDefaultValue > nMaxValue){
				sErrMsg = String.format("默认值[{0}]的范围不对,应该在-2^31到2^31-1之间({1}——{2})",$F('defaultValue').trim(), nMinValue,nMaxValue);
				break;
			}
			break;
		case "FLOAT"://float
			var dbScale = $('dbScale');
			var regExp = new RegExp('^-?\\d+(\.\\d+)?(e[\+-]?\\d+)?$', "i");
			if(!regExp.test(sDefaultValue)){
				sErrMsg = String.format("默认值[{0}]不是合法的小数.",sDefaultValue);
				break;
			}
			var nDefaultValue = parseFloat(sDefaultValue, 10);
			var nMinValue = -2e125;
			var nMaxValue = 2e125;
			if(nMinValue > nDefaultValue || nDefaultValue > nMaxValue){
				sErrMsg = String.format("默认值[{0}]的范围不对,应该在-2e125到2e125之间({1}——{2})",$F('defaultValue').trim(),nMinValue,nMaxValue);
				break;
			}
			if(Element.visible('dbScaleContainer')){
				if(dbScale.value.trim() > 18){
					sErrMsg = (wcm.LANG.CONTENTEXTFIELD_CONFIRM_38 || '小数位数不能超过18');
					break;										
				}
				var defaultValue = $F("defaultValue");
				if(defaultValue){
					var dotIndex = 0;
					if((dotIndex = defaultValue.indexOf(".")) > 0){
						var decimalPart = defaultValue.substr(dotIndex + 1);
						if(decimalPart && decimalPart.length > 0){
							if(decimalPart.length > dbScale.value){
								sErrMsg = wcm.LANG.CONTENTEXTFIELD_CONFIRM_39 || "默认值的小数位数超长,请重设.";
							}
						}
					}
				}
			}
			break;
		case "NVARCHAR"://char
			var dbLength = $('dbLength');
			if(dbLength && dbLength.value){
				if(sDefaultValue.length > dbLength.value){
					sErrMsg = wcm.LANG.CONTENTEXTFIELD_CONFIRM_33 || "默认值长度超出了库中长度{0},请重设.";
					break;
				}
			}
			break;
		case "DATETIME"://timestamp
			break;
	}
	if(sErrMsg){
		try{
			Ext.Msg.alert(sErrMsg);
			if(Element.visible('defaultValueContainer')){
				$('defaultValue').focus();
			}
		}catch(error){
		}
		return false;
	}
	return true;
}
function checkFieldName(){
	Object.extend(PageContext.params,{"DBFieldName":$F("FieldName")});

	PageContext.helpers["DataTypes"].call("wcm6_extendfield","existsSimilarName",PageContext.params,false,function(_transport,_json){		
		if(com.trs.util.JSON.value(_json, "Report.IS_SUCCESS") == 'true'){
            ValidationHelper.successRPCCallBack();
        }else{
            ValidationHelper.failureRPCCallBack(com.trs.util.JSON.value(_json, "Report.TITLE"));
        }
	});

	//判断是否是已建立的扩展字段
	var selFieldInfo = $('selFieldInfo');
//	var optionCurr = selFieldInfo.options[selFieldInfo.selectedIndex];
//	if((optionCurr.value != -1 && optionCurr._fieldName == $F("FieldName")) || optionCurr == null)return;
	if(selFieldInfo.options.length>1){
		for(var i=1;i < selFieldInfo.options.length;i++){
			if($F("FieldName").toUpperCase() == selFieldInfo.options[i].getAttribute('_fieldName')){
				getOptionValue(i);
			}
		}
	}
}

LockerUtil.register2(PageContext.params.ObjectId,ObjectTypeConst_CONTENTEXTFIELD,true,"saveExtField");
Event.observe(window,'load',function(){
	var params = PageContext.params;
	PageContext.loadDataTypes();		

	params = null;	
	//initial validator
	ValidationHelper.addValidListener(function(){
	FloatPanel.disableCommand('saveExtField', false);
	}, "addEditForm");
	ValidationHelper.addInvalidListener(function(){
		FloatPanel.disableCommand('saveExtField', true);
	}, "addEditForm");
});

function showBubblePanel(event){
	Element.show('bubblePanel');
	return;
	event = window.event || event;
	new wcm.BubblePanel('bubblePanel').bubble();
}

function hideBubblePanel(){
	$("bubblePanel").style.display = "none";
}

function format(){
	var date = new Date();
	var now = "";
	now = Adjust(date.getFullYear(),4)+"-";
	now = now + Adjust(date.getMonth()+1,2)+"-";
	now = now + Adjust(date.getDate(),2)+" ";
	now = now + Adjust(date.getHours(),2)+":";
	now = now + Adjust(date.getMinutes(),2);
	return now;
}

function Adjust(value, length) {
	if (!length) length = 2;         
	value = String(value);           
	for (var i = 0, zeros = ''; i < (length - value.length); i++) {           
		zeros += '0';           
	}           
    return zeros + value;           
    };
