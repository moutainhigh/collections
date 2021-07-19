$package('com.trs.validator');

$import('com.trs.validator.ValidatorConfig');
$import('com.trs.validator.SystemValidator');
$import('com.trs.validator.MoreCustomValidator');
$import('com.trs.dialog.Dialog');
$importCSS('com.trs.validator.css.validator');


var ValidationFactory = Class.create();
ValidationFactory.makeValidator = function(_field){
	var eField = $(_field);
	var validatorIns = null;	
	try{
		eval("var validateObj = {" + eField.getAttribute("validation") + "}");
	}catch(err){
		alert('Validation属性创建失败！\nfield[' 
			+ (eField.name || eField.id) + ']\nvalidation[' + eField.getAttribute('validation') + ']');
		return;
	}
	_field.setAttribute("_VALIDATEOBJ_", validateObj);
	try{	
		if(validateObj[$ValidatorConfigs.TYPE] == undefined){
			validatorIns = new Abstract.Validator(_field);		
		}else{
			validatorIns = ValidationFactory._makeValidator(validateObj[$ValidatorConfigs.TYPE], _field);
		}
	}catch(_error){
		alert("ValidationFactory.makeValidator:" + _error.message);
		return;
	}	
	_field.setAttribute("_VALIDATORINS_", validatorIns);
	return validatorIns;
};

ValidationFactory._makeValidator = function(type, _field){
	var validatorIns = null;
	switch(type.toLowerCase()){
		case "int":
		case "float":
		case "double":
			validatorIns = new NumberValidator(_field);
			break;
		case "date":
			validatorIns = new DateValidator(_field);
			break;
		case "url":
			validatorIns = new URLValidator(_field);
			break;
		case "string":
			validatorIns = new StringValidator(_field);
			break;			
		case "ip":
			validatorIns = new IPV4Validator(_field);
			break;
		case "common_char":
			validatorIns = new CommonCharValidator(_field);
			break;
		default:
			eval("validatorIns = new Custom_" + type.toLowerCase() + "_Validator(_field)");
	}
	return validatorIns;
};

var ValidationHelper = Class.create();
ValidationHelper.refreshValid = function(_field){
	var eField = $(_field);
	var sValidation = eField.getAttribute("validation");
	ValidationHelper.popElements(eField);
	changeBorderStyle(eField);
	if(sValidation){
		ValidationFactory.makeValidator(_field);
		ValidationHelper.pushElements(eField);
		ValidationHelper.forceValid(eField);
	}
};
ValidationHelper.doAlert = function(_sMsg, _func){
	_func = _func || Prototype.emptyFunction;
	try{
		$alert(_sMsg, function(){
			$dialog().hide();
			_func();
		});
	}catch (ex){
		alert(_sMsg);
	}
};

/*
*添加验证合法时需执行的回调函数
*callBackFun	回调函数名
*validationId	validation编号
*/
ValidationHelper.validFuns = {};
ValidationHelper.addValidListener = function(callBackFun, validationId){
	validationId = validationId || '$NoValidationId';
	if(!ValidationHelper.validFuns[validationId]){
		ValidationHelper.validFuns[validationId] = [callBackFun];
		return;
	}
	if(!ValidationHelper.validFuns[validationId].include(callBackFun)){
		ValidationHelper.validFuns[validationId].push(callBackFun);
	}
};

/*
*添加验证不合法时需执行的回调函数
*callBackFun	回调函数名
*validationId	validation编号
*/
ValidationHelper.invalidFuns = {};
ValidationHelper.addInvalidListener = function(callBackFun, validationId){
	validationId = validationId || '$NoValidationId';
	if(!ValidationHelper.invalidFuns[validationId]){
		ValidationHelper.invalidFuns[validationId] = [callBackFun];
		return;
	}
	if(!ValidationHelper.invalidFuns[validationId].include(callBackFun)){
		ValidationHelper.invalidFuns[validationId].push(callBackFun);
	}
};

/*
*递交某个表单时执行的函数
*/
ValidationHelper.doSubmit = function(_field){
	var sameValidationIdControls = ValidationHelper.getSameValidationIdControls(_field);
	for (var i = 0; i < sameValidationIdControls.length; i++){
		if(sameValidationIdControls[i].getAttribute("isValid") == false){
			//执行不合法时的回调函数
			ValidationHelper.execInvalidFuns(_field);
			return false;
		}
	}
	//执行合法时的回调函数
	ValidationHelper.execValidFuns(_field);
	return true;
};

ValidationHelper.doSubmitAll = function(){
	var validateControlsSimpleClone = ValidationHelper.getCloneControls(ValidationHelper.validateControls);
	validateControlsSimpleClone.sort(ValidationHelper.sortFun);
	while(validateControlsSimpleClone.length > 0){
		var topObj = validateControlsSimpleClone.pop();
		if(!ValidationHelper.doSubmit(topObj)){
			while(validateControlsSimpleClone.length > 0){
				if(ValidationHelper.getControlValidationId(topObj) == 
						ValidationHelper.getControlValidationId(validateControlsSimpleClone.last()))
					validateControlsSimpleClone.pop();
				else break;
			}	
		}
	}
};

ValidationHelper.sortFun = function(_field1, _field2){
	var id1 = ValidationHelper.getControlValidationId(_field1);
	var id2 = ValidationHelper.getControlValidationId(_field2);
	if(id1 < id2)return -1;
	if(id1 > id2)return 1;
	return 0;
};

//得到数组arrayObj的简单拷贝
ValidationHelper.getCloneControls = function(arrayObj){
	var cloneArrayObj = [];
	for (var i = 0; i < arrayObj.length; i++){
		cloneArrayObj[i] = arrayObj[i];
	}
	return cloneArrayObj;
};

//某个表单校验不合法时执行的回调函数
ValidationHelper.execInvalidFuns = function(_field){
	var validationId = ValidationHelper.getControlValidationId(_field);
	if(ValidationHelper.invalidFuns[validationId]){
		ValidationHelper.invalidFuns[validationId].each(function(invalidFun){invalidFun();});
	}
	return false;
};

//某个表单校验合法时执行的回调函数
ValidationHelper.execValidFuns = function(_field){
	var validationId = ValidationHelper.getControlValidationId(_field);
	if(ValidationHelper.validFuns[validationId]){
		ValidationHelper.validFuns[validationId].each(function(validFun){validFun();});
	}
	return false;
};

//得到具有相同ValidationId的控件数组
ValidationHelper.getSameValidationIdControls = function(_field){
	var sameValidationIdControls = [];
	var validationId = ValidationHelper.getControlValidationId(_field);
	ValidationHelper.validateControls.each(function(element){
		if(element == null) throw $continue;
		var tempValidationId = ValidationHelper.getControlValidationId(element)
		if(validationId == tempValidationId){
			sameValidationIdControls.push(element);
		}
	});
	return 	sameValidationIdControls;
};

//得到某个控件的ValidationId，优先级：自身validationId、表单validationId、表单id、表单名、'$NoValidationId',
ValidationHelper.getControlValidationId = function(_field){
	_field = $(_field);
	var validationId = _field.getAttribute("validationId");

	if(!validationId && _field.form){
		validationId = _field.form.getAttribute("validationId") || _field.form.id || _field.form.name;
	}
	return validationId ? validationId : '$NoValidationId';
};

//得到控件提示/警告信息格式化后的内容
ValidationHelper.getReplaceInfoContent = function(info, infoType, desc, _args){
	var hintString = $ValidatorConfigs[infoType]["zh-cn"][info];
	hintString = hintString.replace('{0}', desc);

	if (_args == null) return hintString;

	if (typeof _args != 'object'){
		return hintString.replace('{1}', _args);
	}
	for (var i = 0; i < _args.length; i++){
		hintString = hintString.replace('{' + (i+1) + '}', _args[i]);
	}	
	return hintString;
};

//初始化控件的isValid属性
ValidationHelper.initIsValid = function(element){
	var isValid = element.getAttribute("_VALIDATORINS_").execute();
	element.setAttribute("isValid", isValid);
	if(isValid) {
		ValidationHelper.addBoardToDomTree(element, "MESSAGE");
	}else{
		ValidationHelper.addBoardToDomTree(element, "WARNING");
	}
};

ValidationHelper.hasRequired = function(validateObj){
	var required = validateObj[$ValidatorConfigs.REQUIRED];
	return !(required=='0' || required=='false' || (required==null&&required!=''));
//	return required==='' || required==1 || required=='true';
	/*
	switch(required){
		case '':
		case '1':
		case 1:
		case 'true':
		case true:
			return true;
		case '0':
		case 0:
		case false:
		case 'false':
		case null:
		case window.undefined :
			return false;
		default:
			return false;
	}
	*/
};

//必填项样式生成函数
ValidationHelper.makeRequiredHint = function(_field){
	try{
		var validateObj = _field.getAttribute("_VALIDATEOBJ_");		
		if(validateObj[$ValidatorConfigs.REQUIRED] != undefined){
			if(validateObj[$ValidatorConfigs.NO_REQUIRE_HINT]){
				return;
			}
			if(!ValidationHelper.hasRequired(validateObj)){
				return;
			}
			var index = _field.getAttribute("_VALID_INDEX_");
			var requireContainer = validateObj[$ValidatorConfigs.REQUIRE_CONTAINER];
			if(requireContainer){
				var sHTML = "<span class='" + $ValidatorConfigs.REQUIREDCLASS + "' id='" + $ValidatorConfigs.PREFIX_HINT_SPAN_ID + index + "'>*</span>";
				new Insertion.Bottom($(requireContainer), sHTML);
				return;
			}
			if(_field.style.position != 'absolute'){
				afterObj = "<span class='" + $ValidatorConfigs.REQUIREDCLASS + "' id='" + $ValidatorConfigs.PREFIX_HINT_SPAN_ID + index + "'>*</span>";
				new Insertion.After(_field, afterObj);
			}else{
				var requiredObj = document.createElement("span");
				requiredObj.id = $ValidatorConfigs.PREFIX_HINT_SPAN_ID + index;
				requiredObj.innerHTML = "*";
				requiredObj.className = $ValidatorConfigs.REQUIREDCLASS;
				var offsets = Position.cumulativeOffset(_field);
				requiredObj.style.top    = offsets[1] + "px";
				requiredObj.style.left   = (offsets[0] + _field.offsetWidth + 1) + 'px';
				requiredObj.style.position    = "absolute";
				document.body.appendChild(requiredObj);			
			}
		}
	}catch(error){
		alert("ValidationHelper.makeRequiredHint:" + error.message);	
	}
};

//提示/警告信息样式生成函数
ValidationHelper.makeInfoBoard = function(_field, _option){
	var sContent = _option.content;
	sContent = 
		  //'<img id="img_' + _option.id.trim() + '" src="' + _option.logoSrc + '" style="height:18px;" align="absmiddle">' +
		 '<span id="sp_' + _option.id.trim() + '">' + sContent + '</span>';
	var oInsertionNode = document.createElement('span');
	oInsertionNode.style.textAlgin = 'justify';
	oInsertionNode.id = _option.id;
	oInsertionNode.className = _option.className;
	oInsertionNode.innerHTML = sContent;
	oInsertionNode.style.position = 'absolute';
	oInsertionNode.style.display = 'none';
	oInsertionNode.style.opacity = '0.85';
	oInsertionNode.style.filter = 'alpha(opacity=85)';
	return oInsertionNode;
};

/*
*得到某种控件某种类型的option信息。某种事件：KEY,MOUSE;某种类型：MESSAGE,WARNING
*/
ValidationHelper.getOption = function(element, _infoType, _eventType){
	var validatorIns = element.getAttribute("_VALIDATORINS_");
	var index = element.getAttribute("_VALID_INDEX_");
	return {
		id: $ValidatorConfigs["PREFIX_" + _infoType + "_SPAN_ID"] + _eventType + "_" + index, 
		content: _infoType == "MESSAGE" ? validatorIns.getMessage() : validatorIns.warning, 
		logoSrc: $ValidatorConfigs[_infoType + "_LOG_PATH"], 
		className: $ValidatorConfigs[_infoType + "_CLASSNAME_" + _eventType]			
	};
};

/*
*将得到的dom对象添加到dom树中
*/
ValidationHelper.addBoardToDomTree = function(element, infoType){
	var validateObj = element.getAttribute("_VALIDATEOBJ_");			
	var showControlId = validateObj[$ValidatorConfigs.SHOWID];
	if(!showControlId && element.form){
		showControlId = element.form.getAttribute($ValidatorConfigs.SHOWID);
	}
	var option = ValidationHelper.getOption(element, infoType, "KEY");
	boardObj = ValidationHelper.makeInfoBoard(element, option);
	if(showControlId){
		if(typeof(showControlId) == 'function'){
			showControlId = showControlId();
		}else if(typeof(showControlId) == 'string'){
			showControlId = $(showControlId);
		}
		$removeChilds(showControlId);
		//showControlId.innerHTML = boardObj.outerHTML;
		showControlId.appendChild(boardObj);
		boardObj.style.position = 'static';
		var noClass = validateObj[$ValidatorConfigs.NO_CLASS];
		if(noClass == undefined && element.form){
			noClass = element.form.getAttribute($ValidatorConfigs.NO_CLASS);
		}
		if(noClass != undefined){
			boardObj.className = '';
		}
	}else{
		if(element.style.position=='absolute'){
			var offsets = Position.cumulativeOffset(element);
			boardObj.style.top    = offsets[1] + "px";
			boardObj.style.left   = (offsets[0] + element.offsetWidth + 5) + 'px';		
			document.body.appendChild(boardObj);
		}else{
			var afterElement = element;
//			if(validateObj[$ValidatorConfigs.REQUIRED] != undefined)
			if(ValidationHelper.hasRequired(validateObj))
				afterElement = element.nextSibling;
			new Insertion.After(afterElement, boardObj.outerHTML);
			afterElement.nextSibling.style.position = 'static';
			return afterElement.nextSibling;			
		}
	}
	return boardObj;
};

//提示/警告信息一直显示
ValidationHelper.showAllTime = function(element){
	var validatorIns = element.getAttribute("_VALIDATORINS_");
	var boardObjMessage = ValidationHelper.addBoardToDomTree(element, "MESSAGE");
	Element.show(boardObjMessage);
};

//target:fix a bug,
/*
在输入框中按下一个字母键，输入几个后，手指不要离开按键，
用鼠标在另一个输入框中点一下，原提示信息不准确。
如：唯一标识提示说不能为空，但事实上是有值的。
*/
ValidationHelper.keyDownEvent = function(element){
	if(event.keyCode == Event.KEY_TAB || event.keyCode == Event.KEY_RETURN){
		element.releaseCapture();
	}else{
		element.setCapture();
	}
};

ValidationHelper.keyUpEvent = function(element){
	element.releaseCapture();
	var validatorIns = element.getAttribute("_VALIDATORINS_");
	var index = element.getAttribute("_VALID_INDEX_");
	var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
	var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
	var isValid = validatorIns.execute();
	element.setAttribute("isValid", isValid);

	if(isValid) {
		if(!boardObjMessage){
			boardObjMessage = ValidationHelper.addBoardToDomTree(element, "MESSAGE");
		}
		if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
			if(boardObjWarning) Element.hide(boardObjWarning);	
			Element.show(boardObjMessage);
		}
		changeBorderStyle(element);	
		return ValidationHelper.doSubmit(element);	
		ValidationHelper.doSubmit(element);	
	}else{
		if(!boardObjWarning){
			boardObjWarning = ValidationHelper.addBoardToDomTree(element, "WARNING");
		}
		$('sp_' + $ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index).innerHTML = validatorIns.warning;
		if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
			if(boardObjMessage) Element.hide(boardObjMessage);	
			Element.show(boardObjWarning);
		}
		changeBorderStyle(element, $ValidatorConfigs["WARNING_BORDER"]);
		return ValidationHelper.execInvalidFuns(element);
		ValidationHelper.execInvalidFuns(element);
	}
};

ValidationHelper.changeEvent = function(element){
	element = $(element);
	var validatorIns = element.getAttribute("_VALIDATORINS_");
	var isValid = validatorIns.execute();	
	var index = element.getAttribute("_VALID_INDEX_");	
	var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
	var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
	if(isValid){//执行与服务器端交互的检验
		var validateObj = element.getAttribute("_VALIDATEOBJ_");
		if(validateObj[$ValidatorConfigs.RPC]){
			ValidationHelper.currRPC = new Object();
			ValidationHelper.currRPC.element = element;
			eval(validateObj[$ValidatorConfigs.RPC] + "();");
		}else{
			changeBorderStyle(element);	
			if(!boardObjMessage){
				boardObjMessage = ValidationHelper.addBoardToDomTree(element, "MESSAGE");
			}
			if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
				if(boardObjWarning) Element.hide(boardObjWarning);	
				Element.show(boardObjMessage);
			}
			element.setAttribute('isValid', true);
			ValidationHelper.doSubmit(element);				
		}
	}else{
		changeBorderStyle(element, $ValidatorConfigs["WARNING_BORDER"]);
		if(!boardObjWarning){
			boardObjWarning = ValidationHelper.addBoardToDomTree(element, "WARNING");
		}
		$('sp_' + $ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index).innerHTML = validatorIns.warning;

		if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
			if(boardObjMessage) Element.hide(boardObjMessage);	
			Element.show(boardObjWarning);
		}
		element.setAttribute('isValid', false);
		ValidationHelper.execInvalidFuns(element);		
	}
};

//与服务器端交互检验,结果成功时执行的回调函数
ValidationHelper.successRPCCallBack = function(message){
	changeBorderStyle(ValidationHelper.currRPC.element);	
	ValidationHelper.currRPC.element.setAttribute("isValid", true);

	var index = ValidationHelper.currRPC.element.getAttribute("_VALID_INDEX_");
	var validateObj = ValidationHelper.currRPC.element.getAttribute("_VALIDATEOBJ_");
	var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
	if(boardObjMessage && validateObj[$ValidatorConfigs["BLUR_SHOW"]] == undefined){
		Element.hide(boardObjMessage);
	}
	var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
	if(boardObjWarning){
		Element.hide(boardObjWarning);
	}
	return true;
};

//与服务器端交互检验,结果失败时执行的回调函数
ValidationHelper.failureRPCCallBack = function(warning){
	try{
		changeBorderStyle(ValidationHelper.currRPC.element, $ValidatorConfigs["WARNING_BORDER"]);
		var index = ValidationHelper.currRPC.element.getAttribute("_VALID_INDEX_");
		var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
		var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);
		with(ValidationHelper.currRPC.element){
			setAttribute("isValid", false);
			getAttribute("_VALIDATORINS_").warning = warning;
		}
		if(!boardObjWarning){
			boardObjWarning = ValidationHelper.addBoardToDomTree(ValidationHelper.currRPC.element, "WARNING");
		}
		var index = ValidationHelper.currRPC.element.getAttribute("_VALID_INDEX_");
		$('sp_' + $ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index).innerHTML = warning;
		if($ValidatorConfigs.getShowAllMode() || $ValidatorConfigs.getFocusMode()){
			if(boardObjMessage) Element.hide(boardObjMessage);	
			Element.show(boardObjWarning);
		}
		return ValidationHelper.execInvalidFuns(ValidationHelper.currRPC.element);
	}catch(error){
		//alert(error.message);
		//可能由于调用popElements方法，ValidationHelper.currRPC==null
	}
};

/*
*控件得到焦点时显示信息
*/
ValidationHelper.focusEvent = function(element){	
	var validatorIns = element.getAttribute("_VALIDATORINS_");
	var index = element.getAttribute("_VALID_INDEX_");
	var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
	var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);

	if(element.getAttribute("isValid") == false && element.style.border == $ValidatorConfigs["WARNING_BORDER"]){
		if(boardObjMessage) Element.hide(boardObjMessage);
		if(!boardObjWarning){
			boardObjWarning = ValidationHelper.addBoardToDomTree(element, "WARNING");
		}
		Element.show(boardObjWarning);	
		return;	
	}
	if(boardObjWarning) Element.hide(boardObjWarning);
	if(!boardObjMessage){
		boardObjMessage = ValidationHelper.addBoardToDomTree(element, "MESSAGE");
	}
	Element.show(boardObjMessage);
};

/*
*控件失去焦点时隐藏信息
*/
ValidationHelper.blurEvent = function(element){
	element = $(element);
	var validatorIns = element.getAttribute("_VALIDATORINS_");
	//处理一些直接粘贴的bug
	var isValid = validatorIns.execute();
	element.setAttribute("isValid", isValid);

	var validateObj = element.getAttribute("_VALIDATEOBJ_");
	var index = element.getAttribute("_VALID_INDEX_");
	var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index);
	var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index);

	if(element.getAttribute("isValid") == false){
		if(boardObjMessage) Element.hide(boardObjMessage);
		if(!boardObjWarning){
			boardObjWarning = ValidationHelper.addBoardToDomTree(element, "WARNING");
		}
		$('sp_' + $ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index).innerHTML = validatorIns.warning;
		Element.show(boardObjWarning);
		changeBorderStyle(element, $ValidatorConfigs["WARNING_BORDER"]);
	}else{
		if(boardObjWarning) Element.hide(boardObjWarning);
		changeBorderStyle(element);
		if(boardObjMessage && validateObj[$ValidatorConfigs["BLUR_SHOW"]] == undefined){
			Element.hide(boardObjMessage);
		}		
		ValidationHelper.doSubmit(element);		
		if(validateObj[$ValidatorConfigs.RPC]){
			ValidationHelper.currRPC = new Object();
			ValidationHelper.currRPC.element = element;
			eval(validateObj[$ValidatorConfigs.RPC] + "();");
		}else{	
/*			
			if(boardObjWarning) Element.hide(boardObjWarning);
			changeBorderStyle(element);
			if(boardObjMessage && validateObj[$ValidatorConfigs["BLUR_SHOW"]] == undefined){
				Element.hide(boardObjMessage);
			}		
			ValidationHelper.doSubmit(element);		
*/
		}
	}
};

/*
*鼠标移到控件上时显示信息
*/
ValidationHelper.mouseOverEvent = function(element, event){
	var validatorIns = element.getAttribute("_VALIDATORINS_");
	var index = element.getAttribute("_VALID_INDEX_");
	var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "MOUSE_" + index);
	var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "MOUSE_" + index);
	if(element.getAttribute("isValid") == false){
		if(!boardObjWarning){
			var option = ValidationHelper.getOption(element, "WARNING", "MOUSE");
			boardObjWarning = ValidationHelper.makeInfoBoard(element, option);
			document.body.appendChild(boardObjWarning);
			boardObjWarning.style.left = Event.pointerX(window.event || event);
			boardObjWarning.style.top = Event.pointerY(window.event || event);	
		}		
		if(boardObjMessage){
			Element.hide(boardObjMessage);
		}
		Element.show(boardObjWarning);
	}else{
		if(!boardObjMessage){
			var option = ValidationHelper.getOption(element, "MESSAGE", "MOUSE");
			boardObjMessage = ValidationHelper.makeInfoBoard(element, option);
			document.body.appendChild(boardObjMessage);
			boardObjMessage.style.left = Event.pointerX(window.event || event);
			boardObjMessage.style.top = Event.pointerY(window.event || event);	
		}
		if(boardObjWarning){
			Element.hide(boardObjWarning);
		}	
		Element.show(boardObjMessage);
	}
};

/*
*鼠标移出控件时隐藏信息
*/
ValidationHelper.mouseOutEvent = function(element){
	var validatorIns = element.getAttribute("_VALIDATORINS_");
	var index = element.getAttribute("_VALID_INDEX_");
	var boardObjMessage = $($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "MOUSE_" + index);
	var boardObjWarning = $($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "MOUSE_" + index);
	if(boardObjMessage){
		Element.hide(boardObjMessage);
	}
	if(boardObjWarning){
		Element.hide(boardObjWarning);
	}
};

ValidatorHelper = ValidationHelper;

/**
*对传入的控件ID/对象序列进行校验
*当传入一个控件ID/对象，则返回一个对象
*否则，返回一个数组
*/
ValidationHelper.valid = function(){
	var arrRetVal = [];
	for(var i=0;i<arguments.length;i++){
		var element = arguments[i];
		element = $(element);
		//wenyh@2007-06-21 如果元素指定了forceValid=true,则总是校验
		if(element.getAttribute("forceValid") != "true" && (element.disabled || element.style.display == 'none')) continue;
		if(element.getAttribute("validation") == undefined) continue;
		var validatorIns = ValidationFactory.makeValidator(element);
		var isValid = validatorIns.execute();
		element.setAttribute("isValid", isValid);
		if(isValid){
			changeBorderStyle(element);			
		}else{
			changeBorderStyle(element, $ValidatorConfigs["WARNING_BORDER"]);
		}
		var validInfo = new Object();
		validInfo.getMessage = function(){
			return this.getMessage() || '';
		}.bind(validatorIns);
		validInfo.getWarning = function(){
			return this.warning || '';
		}.bind(validatorIns);
		validInfo.isValid = function(){
			return this.isValid;
		}.bind({isValid:isValid});
		//ge gfc add @ 2007-6-6 15:27 加入id信息，以便可以通过该标识找到对应的element
		validInfo['id'] = element.id || element.name;

		arrRetVal.push(validInfo);
	}
	return (arrRetVal.length==0)?null:(arrRetVal.length==1)?arrRetVal[0]:arrRetVal;
};


ValidatorHelper.validAndDisplay = function(){
	var validInfo = ValidationHelper.valid.apply(ValidationHelper, arguments);
	if(!Array.isArray(validInfo)){
		validInfo = [validInfo];
	}
	var result = true;
	var warning = '';
	var firstInvalid = null;
	validInfo.each(function(element){
		if(!element.isValid()){
			result = false;
			warning += element.getWarning() + "<br>";
			if(firstInvalid == null) {
				firstInvalid = element;
			}
		}
	});
	if(!result){
		$alert(warning, function(){
			$dialog().hide();
			if(firstInvalid != null) {
				try{
					$(firstInvalid['id']).select();
					$(firstInvalid['id']).focus();					
				}catch(err){
					//alert(err.message);
					//just skip it
				}

			}
		});
	}
	return result;
};

ValidatorHelper.forceValid = function(element){
	element = $(element);
	if(element.getAttribute("validation") == undefined) return;
	var validatorIns = element.getAttribute("_VALIDATORINS_");
	if(validatorIns == undefined){
		ValidationHelper.registerValidation(element);
	}
	ValidationHelper.changeEvent(element);
};

/**
*@param		parentId 要校验控件的父级ID或Dom Object
*@param		_fDoAfterValidate 校验失败时要执行的回调函数
*@param		isExcludeHide 是否对隐藏控件不进行校验
*
*@return	如果所有需要校验的控件满足校验条件，返回true;
*			否则，返回false
*/
ValidationHelper.doValid = function(parentId, _fDoAfterValidate, isExcludeHide){
	var parentControl = $(parentId);
	var inputArray = $A(parentControl.getElementsByTagName("INPUT"));
	var textAreaArray = $A(parentControl.getElementsByTagName("TEXTAREA"));
	var selectArray = $A(parentControl.getElementsByTagName("SELECT"));
	var warning = "";
	var flag = false;
	var allControls = inputArray.concat(textAreaArray, selectArray);
	if(isExcludeHide){
		var _allControls = [];
		for (var i = 0; i < allControls.length; i++){
			if(Element.visible(allControls[i])){
				_allControls.push(allControls[i]);
			}
		}
		allControls = _allControls;
	}
	var firstInValidControl = null;
	for (var i = 0; i < allControls.length; i++){
		var validInfo = ValidationHelper.valid(allControls[i]);
		if(!validInfo)continue;
		if(!validInfo.isValid()){
			if(flag == false) firstInValidControl = allControls[i];
			flag = true;
			warning += validInfo.getWarning() + "<br>";			
		}
	}
	if(flag){
		if(_fDoAfterValidate && typeof(_fDoAfterValidate) == 'function') {
			_fDoAfterValidate(warning, firstInValidControl);
			return;
		}
		$alert(warning, function(){
			$dialog().hide();
			firstInValidControl.focus();
			if(firstInValidControl.select){//wenyh@2007-02-26 select元素似乎没有select方法,故判断一下
				firstInValidControl.select();
			}
			return false;
		});
	}
	return !flag;
};

//Validation组件初始化
ValidationHelper.validateControls = [];
ValidationHelper.initValidation = function(){
	var inputArray = $A(document.getElementsByTagName("INPUT"));
	var textAreaArray = $A(document.getElementsByTagName("TEXTAREA"));
	var selectArray = $A(document.getElementsByTagName("SELECT"));

	inputArray.concat(textAreaArray, selectArray).each(function(element){
		if(element.disabled || element.style.display == 'none')
			throw $continue;
		if(element.tagName.toLowerCase() == "input" && element.getAttribute('type').toLowerCase() == "hidden")
			throw $continue;
		ValidationHelper.registerValidation(element);		
	});
	ValidationHelper.doSubmitAll();
};

//对单个控件注册Validation
ValidationHelper.registerValidation = function(element){
	element = $(element);
	if(element.getAttribute("validation") == undefined) return;
	if(ValidationHelper.validateControls.include(element)) return;
	var index = ValidationHelper.validateControls.length;
	element.setAttribute("_VALID_INDEX_", index);
	ValidationHelper.validateControls.push(element);
	var validatorIns = ValidationFactory.makeValidator(element);	

	if($ValidatorConfigs.getRequiredHint()){
		ValidationHelper.makeRequiredHint(element);	
	}
	ValidationHelper.initIsValid(element);

	element.bindMethod = {};
	element.bindMethod.keyUpEvent = ValidationHelper.keyUpEvent.bind(ValidationHelper, element);
	element.bindMethod.keyDownEvent = ValidationHelper.keyDownEvent.bind(ValidationHelper, element);
	//element.bindMethod.changeEvent = ValidationHelper.changeEvent.bind(ValidationHelper, element);
	Event.observe(element, 'keydown', element.bindMethod.keyDownEvent, false); 
	Event.observe(element, 'keyup', element.bindMethod.keyUpEvent, false); 
	//Event.observe(element, 'change', element.bindMethod.changeEvent, false); 

	if($ValidatorConfigs.getShowAllMode()){
		ValidationHelper.showAllTime(element);
	}else if($ValidatorConfigs.getFocusMode()){
		element.bindMethod.blurEvent = ValidationHelper.blurEvent.bind(ValidationHelper, element);	
		Event.observe(element, 'blur', element.bindMethod.blurEvent, false); 
		if(element.tagName.toUpperCase() == "SELECT"){
			element.bindMethod.changeEvent = ValidationHelper.changeEvent.bind(ValidationHelper, element);		
			Event.observe(element, 'change', element.bindMethod.changeEvent, false); 
		}else{
			element.bindMethod.focusEvent = ValidationHelper.focusEvent.bind(ValidationHelper, element);				
			Event.observe(element, 'focus', element.bindMethod.focusEvent, false); 
		}
	}
	if($ValidatorConfigs.getMouseMode()){
		element.bindMethod.mouseOverEvent = ValidationHelper.mouseOverEvent.bind(ValidationHelper, element, event);
		element.bindMethod.mouseOutEvent = ValidationHelper.mouseOutEvent.bind(ValidationHelper, element, event);
		Event.observe(element, 'mouseover', element.bindMethod.mouseOverEvent, false); 
		Event.observe(element, 'mouseout', element.bindMethod.mouseOutEvent, false); 
	}
	return true;
};

/*
 *将某些元素添加到校验数组中
 *参数：传入单个元素或多个元素
 */
ValidationHelper.pushElements = function(){
	var elementArray = $.apply($, arguments);
	if(elementArray.constructor != Array) elementArray = [elementArray];

	for (var i = 0; i < elementArray.length; i++){
		element = elementArray[i];
		var registerStatus = ValidationHelper.registerValidation(element);
		if(registerStatus){
			if(element.value != "" && element.getAttribute("isValid")){
				var validateObj = element.getAttribute("_VALIDATEOBJ_");
				if(validateObj[$ValidatorConfigs.RPC]){
					ValidationHelper.currRPC = new Object();
					ValidationHelper.currRPC.element = element;
					eval(validateObj[$ValidatorConfigs.RPC] + "();");
				}
			}else{
				ValidationHelper.doSubmit(element);
			}
		}
/*
		if(registerStatus && !element.getAttribute("isValid")){
			ValidationHelper.doSubmit(element);
		}
*/
	}
};

/*
 *将某些元素从校验数组中删除
 *参数：传入单个元素或多个元素
 */
ValidationHelper.popElements = function(){
	var elementArray = $.apply($, arguments);
	if(elementArray.constructor != Array) elementArray = [elementArray];

	for (var i = 0, length = ValidationHelper.validateControls.length; i < length; i++){
		var element = ValidationHelper.validateControls[i];
		if(elementArray.include(element)){
			ValidationHelper.validateControls[i] = null;
			var index = element.getAttribute("_VALID_INDEX_");	

			//移除dom节点信息
			var removeDomArray = [];
			removeDomArray.push($($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "KEY_" + index));
			removeDomArray.push($($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "KEY_" + index));
			removeDomArray.push($($ValidatorConfigs.PREFIX_MESSAGE_SPAN_ID + "MOUSE_" + index));
			removeDomArray.push($($ValidatorConfigs.PREFIX_WARNING_SPAN_ID + "MOUSE_" + index));
			removeDomArray.push($($ValidatorConfigs.PREFIX_HINT_SPAN_ID + index));
			for (var i = 0; i < removeDomArray.length; i++){
				if(removeDomArray[i]){
					Element.remove(removeDomArray[i]);
					removeDomArray[i] = null;
				}
			}

			//移除注册的事件
			for(var tempEvent in element.bindMethod){				
				Event.stopObserving(element, tempEvent.replace("Event", '').toLowerCase(), element.bindMethod[tempEvent], false);
				delete element.bindMethod[tempEvent];
			}
			element.bindMethod = null;
			element.removeAttribute("bindMethod");
			element._VALIDATORINS_ = null;
			element.removeAttribute("_VALIDATORINS_");
			element._VALIDATEOBJ_ = null;
			element.removeAttribute("_VALIDATEOBJ_", null);

			if(element.getAttribute("isValid") == false){
				element.removeAttribute("isValid");
				ValidationHelper.doSubmit(element);
			}	
		}
	}
};

ValidationHelper.notify = function(){
	var elementArray = $.apply($, arguments);
	if(elementArray.constructor != Array) elementArray = [elementArray];
	for (var i = 0; i < elementArray.length; i++){
		var element = elementArray[i];
		if(element.disabled || element.style.display == 'none'){
			ValidationHelper.popElements(element);
		}else{				
			ValidationHelper.pushElements(element);
		}
	}
};

Event.observe(window, 'unload', function(){
	for (var i = 0; i < ValidationHelper.validateControls.length; i++){
		var element = ValidationHelper.validateControls[i];
		if(element == null || !element.bindMethod)continue;
		for (prop in element.bindMethod){
			delete element.bindMethod[prop];
		}
		element.bindMethod = null;
		element.removeAttribute("bindMethod");
		delete element._VALIDATORINS_.field;
		element._VALIDATORINS_ = null;
		element.removeAttribute("_VALIDATORINS_");
		element._VALIDATEOBJ_ = null;
		element.removeAttribute("_VALIDATEOBJ_", null);
	}
});

//改变输入框的样式。未指定newBorder时，则修改为原样式
function changeBorderStyle(element, newBorder){
	if(element.getAttribute("_oldBorderStyle_") === null){
		var oldBorderWidth = Element.getStyle(element, "borderWidth") || '';
		var oldBorderStyle = Element.getStyle(element, "borderStyle") || '';
		var oldBorderColor = Element.getStyle(element, "borderColor") || '';
		element.setAttribute("_oldBorderWidth_", oldBorderWidth);
		element.setAttribute("_oldBorderStyle_", oldBorderStyle);
		element.setAttribute("_oldBorderColor_", oldBorderColor);
	}
	if(newBorder){
		//element.style.width = element.offsetWidth;
		element.style.border = newBorder;
	}else{
		if(element.tagName!='TEXTAREA' && element.getAttribute("_oldBorderStyle_") == 'none'){
			element.style.borderWidth = '';
			element.style.borderStyle = 'none';
			element.style.borderColor = '';
		}else{
			var borderWidth = element.getAttribute("_oldBorderWidth_");
			if(borderWidth.startsWith("0")){
				element.style.borderWidth = '';
			}else{
				element.style.borderWidth = borderWidth;
			}
			var borderColor = element.getAttribute("_oldBorderColor_");
			var borderStyle = element.getAttribute("_oldBorderStyle_");
			if(borderColor=="#000000" && borderStyle.toLowerCase()=="inset"){
				element.style.borderColor = "#ffffff";
			}else{
				element.style.borderColor = element.getAttribute("_oldBorderColor_");
			}
			element.style.borderStyle = borderStyle;
		}		
	}
}

//Event.observe(window, 'load', ValidationHelper.initValidation, false);