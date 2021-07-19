$package('com.trs.validation.Validator');

$import('com.trs.validation.ValidatorConfig');
$import('com.trs.logger.Logger');
$import('com.trs.util.Other');
$importCSS("com.trs.validation.css.validator");
com.trs.validation.imgsPath = com.trs.util.Common.BASE+'com.trs.validation/images/';
Event.observe(window,'unload',function(){
	com.trs.validation=null;
});
//-------------------------------Constants define begin------------------------------------
var ATTR_NAME_DESC = 'desc';
var ATTR_NAME_PATTERN = 'pattern';
var ATTR_NAME_FORMATE = 'format';
var ATTR_NAME_REQUIRED = 'required';
var ATTR_NAME_EQUALS_WITH = 'equals-with';
var ATTR_NAME_LENGTH_MIN = 'min-len';
var ATTR_NAME_LENGTH_MAX = 'max-len';
var ATTR_NAME_MIN = 'min';
var ATTR_NAME_MAX = 'max';
var ATTR_NAME_ALERTION = 'alertion';
var DEFAULT_DATE_FORMATE = 'yyyy-mm-dd';

var PREFIX_HINT_SPAN_ID = 'com_trs_wcm_ajax_hint_node_';
var PREFIX_ALERTION_SPAN_ID = 'com_trs_wcm_ajax_alertion_node_';
//-------------------------------Constants define end------------------------------------

//-------------------------------ValidationHelper begin------------------------------------
var ValidationHelper = Class.create();
ValidationHelper.doAlert = function(_sMsg, _func){
	try{
		$alert(_sMsg, function(){
			$dialog().hide();
			_func();
		});
	}catch (ex){
		alert(_sMsg);
	}
}

var Timer = new Object();
Timer.fire = function(){
	Timer._currMSec = new Date().getUTCMilliseconds();
}
Timer.pause = function(){
	if (Timer._currMSec)
		return new Date().getUTCMilliseconds() - Timer._currMSec;
	Timer._currMSec = new Date().getUTCMilliseconds();
	return 0;
}
ValidationHelper.getFields4Validation =  function(_sFormId, _sAlertionWidth, _bHoverAlertion, _bWrapped) {
	var form = $(_sFormId);
	var arFrameInputs = $A();
	var children =null;
	if (form){
		//$log().setLevel(3);
		//Timer.fire();
		var arInputs	= $A(form.getElementsByTagName('INPUT'));
		var arTextAreas = $A(form.getElementsByTagName('TEXTAREA'));

		children	= $A(form.getElementsByTagName('*'));//arInputs.concat(arTextAreas);
		var nIndex = 0;
		children.each(function(child){
			var sType = child.type;
			if (!sType || ((sType = sType.toLowerCase()) != 'text' 
				&& sType != 'password' && sType != 'textarea')){
				throw $continue;							}
			if (!Element.visible(child) || child.disabled)	
				throw $continue;
			var pattern = $GS(child, ATTR_NAME_PATTERN);
			if (!pattern || pattern.trim().length == 0)
				throw $continue;
			//else
			Object.extend(child, {
				index : ++nIndex,
				pattern : pattern
			});
			child.alertion=$GS(child, ATTR_NAME_ALERTION);
			var sDesc = $GS(child, ATTR_NAME_DESC);
			if (sDesc){
				child.desc=sDesc;
			}else if(child.name != String.Empty){
				child.desc=child.name;
			}
			child.hint=ValidationHelper.getFieldHint(child);
			//
			var sSpanId = PREFIX_HINT_SPAN_ID + child.index;
			var sHint = child.alertion;
			var arHintList = null;
			if (!sHint && (arHintList = child.hint).length != 0)
				sHint = '<span>' + arHintList.join('</span><span>') + '</span>';
			var option = {
				id: sSpanId, 
				content: sHint, 
				logoSrc: com.trs.validation.imgsPath + 'information.gif', 
				className: 'alertion_hint', 
				alertionWidth: _sAlertionWidth,
				wrapped: _bWrapped
			};

			if (_bHoverAlertion){
				child.option = option;
				ValidationHelper.observeHoverEvent(child, option);
			}else{
				ValidationHelper.makeAlertionBoard(child, option, false);
			}
			
			//
			var sRequired = $GS(child, ATTR_NAME_REQUIRED);
			if (sRequired && sRequired.trim().toLowerCase() == 'true'){
				var sSpanId = 'star_' +PREFIX_ALERTION_SPAN_ID + child.index;
				var oStarNode = $(sSpanId);
				if (oStarNode == null){
					//alert(sSpanId + ': ' + oInsertionNode)
					var oStarNode = '<span style="color:#FF6100" id="' + sSpanId + '" isNull="1">&nbsp;*</span>';
					new Insertion.After(child, oStarNode);
				}
			}

			arFrameInputs.push(child);
			delete child;
		});
		children=null;
	}
	//$log().error(Timer.pause());
	return arFrameInputs;
}
ValidationHelper.makeAlertionBoard = function(_field, _option, _bHover){
	var sContent = _option.content;
	if (!sContent) return;
	sContent = '<img src="' + _option.logoSrc + '" style="height:18px;" align="absmiddle">' 
		+ '<span id="sp_' + _option.id.trim() + '">' + sContent + '</span>';
	var oReturn;
	if (_bHover){
		var oInsertionNode = document.body.appendChild(document.createElement('div'));
		var sWidth = _option.alertionWidth;
		if (sWidth && !isNaN(parseInt(sWidth)))	
			oInsertionNode.style.width = sWidth;
		oInsertionNode.style.textAlgin = 'justify';
		oInsertionNode.id = _option.id;
		oInsertionNode.className = _option.className;
		oInsertionNode.innerHTML = sContent;
		oInsertionNode.style.position = 'absolute';
		oInsertionNode.style.display = 'none';
		oInsertionNode.style.opacity = '0.85';
		oInsertionNode.style.filter = 'alpha(opacity=85)';
		var offsets = Position.cumulativeOffset(_field);
		oInsertionNode.style.top    = (offsets[1] + (_option.wrapped ? _field.offsetHeight : 0)) + 'px';
		oInsertionNode.style.left   = (offsets[0] + (_option.wrapped ? 15 : _field.offsetWidth - 10)) + 'px';
		oReturn=document.body.appendChild(oInsertionNode);
	}else{
		var sStyle = null;
		var sWidth = _option.alertionWidth;
		if (sWidth && !isNaN(parseInt(sWidth)))
			sStyle = 'style="width:' + sWidth + '"';

		var whoAfter = _field;
		var sSpanId = 'star_' +PREFIX_ALERTION_SPAN_ID + _field.index;
		var oStarNode = $(sSpanId);
		if (oStarNode != null)
			whoAfter = oStarNode;
		oReturn=new Insertion.After(whoAfter, 
			'<span class="' + _option.className + '" id="' + _option.id 
				+ '"' + (sStyle == null ? '' : ' ' + sStyle) + '>'+ sContent + '</span>');
		
		delete whoAfter;
	}
	delete _field;
	delete _option;
	return oReturn;
}
ValidationHelper.makeAlertionEntity = function(_element, _sAlertInfo, _bUnexpectedNull){
	return {
		field: _element,
		alertion: _sAlertInfo,
		unexpected_null: _bUnexpectedNull || false,
		index: _element.index,
		desc: _element.desc
	};
}
ValidationHelper.observeHoverEvent = function(_field, _option){
	Event.observe(_field,"mouseover",function(event){
		_option.event = event || window.event;
		var hoverBoard = $(_option.id);
		if(hoverBoard == null) 
			hoverBoard = ValidationHelper.makeAlertionBoard(_field, _option, true);
		if (hoverBoard)	
			Element.show(hoverBoard);
		delete _field;
		delete _option;
	});
	Event.observe(_field,"mouseout",function(){
		var hoverBoard = $(_option.id);
		if(hoverBoard) 
			Element.hide(hoverBoard);
		delete _option;
	});
}
//-------------------------------ValidationHelper end------------------------------------

//-------------------------------ValidatorFactory begin------------------------------------
var ValidatorFactory = Class.create();
ValidatorFactory.make = function(_field){
	var pattern = _field.getAttribute(ATTR_NAME_PATTERN);
	if (pattern == null 
		|| (pattern = pattern.trim()) == String.Empty)
		return;

	var instance = new DateValidator();
	switch(pattern.toLowerCase()){
		case 'date':
			instance = new DateValidator();
			break;
		case 'string':
			instance = new StringValidator();
			break;
		case 'int':
			instance = new IntegerValidator();
			break;
		case 'float':
			instance = new FloatValidator();
			break;
		default:
			instance = new SpecialValidator();
	}
	instance.setValidatingField(_field);

	return instance;
}
Event.observe(window,'unload',function(){
	$destroy(ValidatorFactory);
	ValidatorFactory = null;
});
//-------------------------------ValidatorFactory end------------------------------------

//-------------------------------ValidationWorker begin------------------------------------
var ValidationWorker = Class.create();
ValidationWorker.prototype = {
	initialize : function() {
		this.alertions = $A();
		this.isDefaultProcess = arguments[0] == null ? false : arguments[0];
		this.alertionWidth	  = arguments[1];
		this.displayAlertion  = arguments[2] == null ? false : arguments[2];
		this.displayHover	  = arguments[3] == null ? false : arguments[3];
		this.wrapped		  = arguments[4] == null ? false : arguments[4];
		this.fields = $A();
		Event.observe(window,'unload',this.destroy.bind(this));
	},
	prepare : function(_sFormId){
		this.fields = ValidationHelper.getFields4Validation(_sFormId, this.alertionWidth, this.displayHover, this.wrapped);
	},
	doValidate : function(_sFormId){
		if (_sFormId){	
			this.prepare(_sFormId);
		}
		// if no fields found, aim it as passed
		if (this.fields.length == 0)
			return true;

		var alertions = $A();
		this.fields.each(function(field){
			// prepare - clear the node for alertion display
			var oAlertionNode = $(PREFIX_ALERTION_SPAN_ID + field.index);
			if (oAlertionNode)
				Element.hide(oAlertionNode);
			var oHintNode = $(PREFIX_HINT_SPAN_ID + field.index);
			
			if (oHintNode) 
				Element.hide(oHintNode);

			// check
			if (!Element.visible(field)){
				throw $continue;	
			}else{
				try{
					field.focus();
				}catch(ex){
					throw $continue;
				}
			}
			// make the instance of a certain validator
			var validator = ValidatorFactory.make(field);
			// check empty first
			var sCheckEmptyResult = validator.checkEmpty();
			if (sCheckEmptyResult && sCheckEmptyResult != String.Empty){
				alertions.push(ValidationHelper.makeAlertionEntity(field, field.hint, true));
				if (this.displayHover){
					ValidationHelper.observeHoverEvent(field, field.option);
					field.style.backgroundColor = '#ffffff';
				}
				throw $continue;	
			}else if (sCheckEmptyResult == String.Empty){
				try{
					Element.show(oHintNode);	
				}catch (ex){
					// do nothin'
				}
				throw $continue;
			}

			// check alertion
			var sAlertion = validator._render();
			if (sAlertion == null || sAlertion == String.Empty 
				|| sAlertion == 'null'){
				if (this.displayAlertion && oHintNode){
					Element.show(oHintNode);
				}
				if (this.displayHover){
					ValidationHelper.observeHoverEvent(field, field.option);
					field.style.backgroundColor = '#ffffff';
				}
				throw $continue;
			}
			// else
			alertions.push(ValidationHelper.makeAlertionEntity(field, sAlertion, false));
			//alert(nIndex + ': ' + );
		}.bind(this));

		this.alertions = alertions;
		this.__fireAlert();

		return this.isPassed();
	},	
	destroy : function(){
		//$log().error('-fields');
		for (var i=0;this.fields&&i<this.fields.length;i++){
			if(this.fields[i]){
				for(var param in this.fields[i]){
					try{
						if(this.fields[i][param]){
							delete this.fields[i][param];
							this.fields[i][param]=null;
						}
					}catch(err){
					}
				}
				try{
					if(this.fields[i].parentNode){
						this.fields[i].parentNode.removeChild(this.fields[i]);
					}
					delete this.fields[i];
					this.fields[i]=null;
				}catch(err){
				}
			}
		}
		// clear up all others
		for(var param in this){
			try{
				if(this[param]){
					delete this[param];
					this[param]=null;
				}
			}catch(err){
			}
		}
	},
	getFields : function(){
		return this.fields;
	},	
	isPassed : function (){
		return 	(this.alertions.length == 0);
	},

	getAlertions : function(){
		return this.alertions;
	},

	setDisplayAlertion : function(_bDisplayAlertion){
		this.displayHover = _bDisplayAlertion;
	},

	setAlertionWidth : function(_sAlertionWidth){
		this.alertionWidth = _sAlertionWidth;
	},

	inspect: function() {
		var result = $A();
		this.alertions.each(function(element){
			result.push(element.alertion);
		});
		return result;
	},
	
	__fireAlert : function(){
		if (this.alertions.length <= 0)
			return;
		
		this.alertions.each(function(element){
			//else{//check alertion
				var sSpanId = PREFIX_ALERTION_SPAN_ID + element.index;
				var oInsertionNode = $(sSpanId);
				var option = {
					id: sSpanId, 
					content: element.alertion, 
					logoSrc:com.trs.validation.imgsPath + 'alert_logo.gif', 
					className:'alertion_err', 
					alertionWidth: this.alertionWidth,
					wrapped: this.wrapped
				};
				var bFirstLoad = false;
				if (oInsertionNode == null){
					bFirstLoad = true;
					oInsertionNode = ValidationHelper.makeAlertionBoard(element.field, option, this.displayHover);
				}
				// else
				if (!bFirstLoad && this.displayAlertion){
					$('sp_' + sSpanId).innerHTML = element.alertion;
					Element.show(oInsertionNode);
				}
				if (this.displayHover){
					ValidationHelper.observeHoverEvent(element.field, option);
					element.field.style.backgroundColor = '#FFF3E7';
				}
		
			//}
			if (element.unexpected_null){//check empty
				sSpanId = 'star_' +PREFIX_ALERTION_SPAN_ID + element.index;
				oInsertionNode = $(sSpanId);
				if (oInsertionNode == null){
					//alert(sSpanId + ': ' + oInsertionNode)
					var sInsertionNode = '<span style="color:#FF6100" id="' + sSpanId + '" isNull="1">&nbsp;*</span>';
					new Insertion.After(element.field, sInsertionNode);
				}
			}
		}.bind(this));

		if (this.alertions[0] && Element.visible(this.alertions[0].field)){
			if (this.isDefaultProcess)
				ValidationHelper.doAlert(this.alertions[0].alertion, 
				function(){
					try{
						this.alertions[0].field.select();
						this.alertions[0].field.focus();
					}catch(ex){
						//no op
					}
			}.bind(this));
		}
	}
}
//-------------------------------ValidationWorker end------------------------------------

//-------------------------------BaseValidator begin------------------------------------
BaseValidator = Class.create();
BaseValidator.prototype = {
	initialize : function() {
		this.field = null;
		Event.observe(window,'unload',function(){$destroy(this)}.bind(this));
	},

	checkEmpty : function(){
		var bEmpty = ($F(this.field).isEmpty());
		if (!this.__isRequired()){
			return bEmpty ? String.Empty : null;
		}
		if (bEmpty) {
			//alert(this.field.name + ' - Empty')
			return this.__getAlertionStr('required');
		}
		return null;
	},
	setValidatingField : function(_field){
		this.field = _field;
	},
		
	_checkEquals : function(){
		var sEqualsWith = $GS(this.field, ATTR_NAME_EQUALS_WITH);
		if (sEqualsWith == null)
			return null;
		
		var oPartner = $(sEqualsWith);
		if (oPartner && ($F(oPartner) != $F(this.field))){
			return this.__getAlertionStr('equals-with', $GS(oPartner, ATTR_NAME_DESC));
		}

		return null;
	},

	_render : function(){
		// no op
		return null;
	},

	__isRequired : function(){
		var sRequired = this.field.getAttribute(ATTR_NAME_REQUIRED);
		if (sRequired && sRequired.trim().toLowerCase() == 'true'){
			return true;
		}

		return false;
	},
	
	__getAlertionStr : function(_sAnchor, _args){
		return ValidationHelper.getAlertionString(this.field, _sAnchor, _args);
	}
}
//-------------------------------BaseValidator end------------------------------------
StringValidator = Class.create();
StringValidator.prototype = Object.extend(new BaseValidator(), {
	_render : function(){
		// check length
		var result = this._checkLength();
		if (this._checkLength())
			return result;
		
		return this._checkEquals();
	},

	_checkLength : function(){
		var sVal = $F(this.field);

		var nLen = $F(this.field).getLength();
		var nMinLen = $GN(this.field, ATTR_NAME_LENGTH_MIN, 0);
		var nMaxLen = $GN(this.field, ATTR_NAME_LENGTH_MAX, 0);
		if (nMinLen > 0 && (nLen < nMinLen))
			return this.__getAlertionStr('below_min_len', [nLen, nMinLen]);
		if (nMaxLen > 0 && (nLen > nMaxLen))
			return this.__getAlertionStr('beyond_max_len', [nLen, nMaxLen]);

		return null;
	}
});
//-------------------------------NumberValidator begin------------------------------------
NumberValidator = Class.create();
NumberValidator.prototype = Object.extend(new BaseValidator(), {
	_render : function(){
		var sVal = $F(this.field);
		if (sVal.isEmpty()) return null;

		var conditions = this._getValidationConditions();
		if (!(conditions.reg_exp.test(sVal))) {
			return conditions.type_desc;
		}

		return this._checkRange(sVal, $GS(this.field, ATTR_NAME_MIN), 
			$GS(this.field, ATTR_NAME_MAX), conditions.parse_method);
	},

	_checkRange : function(_sVal, _sMin, _sMax, _parseMethod){
		var val = _parseMethod(_sVal);
		var minVal = _parseMethod(_sMin);
		var maxVal = _parseMethod(_sMax);
		if (val < minVal)
			return this.__getAlertionStr('below_min_val',  [_sMin]);
		if (val > maxVal)
			return this.__getAlertionStr('beyond_max_val', [_sMax]);

		return null;		
	},
	
	_makeValidationConditions : function(_regExp, _sTypeDesc, _parseMethod){
		return {
			reg_exp: _regExp,
			type_desc: _sTypeDesc,
			parse_method: _parseMethod			
		}	
	}
	
	/**
	the template methods is:
		_getValidationConditions
	*/
});

IntegerValidator = Class.create();
IntegerValidator.prototype = Object.extend(new NumberValidator(), {
	_getValidationConditions : function(){
		return this._makeValidationConditions(new RegExp('^-?\\d+$'),
			this.__getAlertionStr('int'), parseInt);
	}
});
FloatValidator = Class.create();
FloatValidator.prototype = Object.extend(new NumberValidator(), {
	_getValidationConditions : function(){
		return this._makeValidationConditions(new RegExp('^-?\\d+\.\\d+$'),
			this.__getAlertionStr('float'), parseFloat);
	}
});
//-------------------------------NumberValidator end------------------------------------

//-------------------------------DateValidator begin------------------------------------
DateValidator = Class.create();
DateValidator.prototype = Object.extend(new BaseValidator(), {
	_render : function(){
		var sDateVal = this.field.value;
		var sDateFormat = this.field.getAttribute(ATTR_NAME_FORMATE);
		if (sDateFormat)
			sDateFormat = sDateFormat.toLowerCase().trim();
		
		var regexp, index, sDesc;
		var year, month, day;
		var iyear, imonth, iday;
		var fmt, regfmt, ordfmt, fmtDesc;
		var dateArray;


		
		fmt = new Array("yyyy/mm/dd",
					  "mm/dd/yyyy",
					  "dd/mm/yyyy",
					  "yyyy-mm-dd",
					  "mm-dd-yyyy",
					  "dd-mm-yyyy");
		fmtDesc = new Array("yyyy/MM/dd(e.g.1979/07/27)",
					  "MM/dd/yyyy(e.g.07/27/1979)",
					  "dd/MM/yyyy(e.g.27/07/1979)",
					  "yyyy-MM-dd(e.g.1979-07-27)",
					  "MM-dd-yyyy(e.g.07-27-1979)",
					  "dd-MM-yyyy(e.g.27-07-1979)");				  

		regfmt = new Array("^([0-9]{4})\/([0-9]{2})\/([0-9]{2})$",
						"^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$",
						"^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$",
						"^([0-9]{4})\-([0-9]{2})\-([0-9]{2})$",
						"^([0-9]{2})\-([0-9]{2})\-([0-9]{4})$",
						"^([0-9]{2})\-([0-9]{2})\-([0-9]{4})$");

		ordfmt = new Array("123","312","321", "123","312","321");

		if(sDateFormat == null || sDateFormat == ""){
			sDateFormat = DEFAULT_DATE_FORMATE;
		}

		sDateFormat = sDateFormat.toLowerCase();
		for(index = 0; index < fmt.length; index++){
			if(sDateFormat == fmt[index]){ 
				regexp = new RegExp(regfmt[index]);
				sDesc  = fmtDesc[index];
				iyear = parseInt(ordfmt[index].charAt(0));
				imonth = parseInt(ordfmt[index].charAt(1));
				iday = parseInt(ordfmt[index].charAt(2));
				break;
			}
		}

		if(index == fmt.length){
			return this.__getAlertionStr('unknow_date_format', sDateFormat);
		}

		if(regexp.test(sDateVal)){
			dateArray = sDateVal.match(regexp);
			year = dateArray[iyear];
			month= dateArray[imonth];
			day = dateArray[iday];
			if(year <= 0){
				return this.__getAlertionStr('invalid_year');
			}
			if(month < 0 || month > 12){
				return this.__getAlertionStr('invalid_month');
			}
			if(day < 0 || day > 31){
				return this.__getAlertionStr('invalid_day');
			}else{ 
				if(month == 2 && day > 28){ 
					if(this.__isLeapYear(year) && day > 29){
						return this.__getAlertionStr('invalid_leapyear_feb');
					}
					if(!this.__isLeapYear(year) && day > 28){
						return this.__getAlertionStr('invalid_non_leapyear_feb');
					}	
				}
				if((month == 4 || month == 6 || month == 9 || month == 11) && (day > 30)){
					return this.__getAlertionStr('invalid_day_of_month');
				}
			}
		}else{
			return this.__getAlertionStr('unexpected_date_format', sDesc);
		}

		return null;
	},

	__isLeapYear : function(year){
		if((year%4==0&&year%100!=0)||(year%400==0)){
			return true;
		}
		
		return false;
	}
});

//----------------------------------DateValidator end-------------------------------

//-------------------------------SpecialValidator begin------------------------------------
var SpecialValidator = Class.create();
SpecialValidator.prototype = Object.extend(new StringValidator(), {
	_render : function(){
		//check length first
		var sAlertion = this._checkLength();
		if (sAlertion != null)
			return sAlertion;

		//else, continue to check special rules 
		var sVal = $F(this.field);
		var sRule = $GS(this.field, ATTR_NAME_PATTERN);
		switch(sRule){
			case 'common-char':
				if (!this.__validateCommonChar(sVal))
					return this.__getAlertionStr('common-char');
				break;
			case 'chinese':
				if (!this.__validateChineseChar(sVal))
					return this.__getAlertionStr('chinese');
				break;
			case 'string2':
				if (!this.__validateNonTagChar(sVal))
					return this.__getAlertionStr('string2');
				break;
			default :
				return this.__checkSpecialRule(sRule, sVal);
		}
	},
	__checkSpecialRule : function(_sRule, _sVal){
		// no op
		var extendees = ValidationHelper.extendees;//.reverse(false);
		if (extendees == null || extendees.length == 0) return null;

		var result = null;
		var oField = this.field;
		for (var i=extendees.length - 1; i>=0 ; i-- )	{
			var sAlertion = extendees[i](_sRule, _sVal, oField);
			if (sAlertion != null && sAlertion != String.Empty){
				result = sAlertion;
				break;
			}
		}
		return result;
	},
	__validateCommonChar : function(sVal) {
		if (sVal == String.Empty) return true;
		var exp = new RegExp("^[A-Za-z0-9_]*$");
		return exp.test(sVal);
	},
	__validateChineseChar : function(sVal) {
		if (sVal == String.Empty) return true;
		var exp = new RegExp("^[\u4E00-\u9FA5\uF900-\uFA2D]*$");
		return exp.test(sVal);
	},
	__validateNonTagChar : function(sVal) {
		if (sVal == String.Empty) return true;
		return !this.__containsStr(sVal, /\</) && !this.__containsStr(sVal, /\$\&/)
			&& !this.__containsStr(sVal, /\#INNERTHML/);
	},
	__containsStr : function(_string, _reg){
		return _string.replace(_reg, '') != _string;
	}
});


//-------------------------------SpecialValidator end------------------------------------

// -----------------------------extending------------------------------------
ValidationHelper.getAlertionString = function(_field, _sAnchor, _args, _bHideDefault) {
	var stringResource = null;
	// determine specified language first
	var lang = UserLanguage.toLowerCase();
	if (lang == 'auto') {
		if (navigator.userLanguage == null)
			lang = navigator.language.toLowerCase();
		else
			lang = navigator.userLanguage.toLowerCase();
	}
	// get defined language
	if (typeof DefinedAlertion[lang] != 'object') {
		stringResource = DefinedAlertion['zh-cn'];
	} else {
		stringResource = DefinedAlertion[lang];
	}
	// get the field description
	var sFieldDesc = '';
	if (!ValidationHelper.HideFieldDesc4AlertionStr){
		sFieldDesc = _field.toString();
		if (typeof _field != 'string' && _field.desc){
			sFieldDesc = _field.desc;
			if (typeof sFieldDesc != 'string')	sFieldDesc = '';
		}
	}
	
	var anchor = _sAnchor == null ? 'default' : _sAnchor.toLowerCase().trim();
	var result = stringResource[anchor];
	if (result == null){
		if (_bHideDefault) return null;	
		result = stringResource['default'];
	}
	result = result.replace('{0}', sFieldDesc);
	// specify some certain arguments
	if (_args == null) return result;
	// else
	if (typeof _args == 'string'){
		return result.replace('{1}', _args);
	}
	for (var i = 0; i < _args.length; i++){
		result = result.replace('{' + (i+1) + '}', _args[i]);
	}	
	return result;
}

ValidationHelper.getFieldHint = function(_field){
//	_field=Object.extend({},_field);
	Object.extend(_field, {
		required:$GS(_field, ATTR_NAME_REQUIRED),
		max_len: $GS(_field, ATTR_NAME_LENGTH_MAX),
		min_len: $GS(_field, ATTR_NAME_LENGTH_MIN),
		max: $GS(_field, ATTR_NAME_MAX),
		min: $GS(_field, ATTR_NAME_MIN),
		date_format: $GS(_field, ATTR_NAME_FORMATE),
		equal_with: $GS(_field, ATTR_NAME_EQUALS_WITH)
	});
	
	var result = $A();
	var sPtnStr = ValidationHelper.getAlertionString(_field, _field.pattern, null, true);
	if (sPtnStr != null && sPtnStr.trim() != String.Empty) result.push(sPtnStr);
	//if (_field.required) result.push(ValidationHelper.getAlertionString(_field, ATTR_NAME_REQUIRED, _field.required));
	if (_field.date_format) result.push(ValidationHelper.getAlertionString(_field, ATTR_NAME_FORMATE, _field.date_format, true));
	else if(_field.pattern == 'date')	result.push(ValidationHelper.getAlertionString(_field, ATTR_NAME_FORMATE, DEFAULT_DATE_FORMATE, true));

	if (_field.equal_with) result.push(ValidationHelper.getAlertionString(_field, ATTR_NAME_EQUALS_WITH, $GS($(_field.equal_with), ATTR_NAME_DESC), true));

	if (_field.max_len && _field.min_len) result.push(ValidationHelper.getAlertionString(_field, 'length_range', [_field.min_len,_field.max_len], true));
	else if (_field.max_len) result.push(ValidationHelper.getAlertionString(_field, ATTR_NAME_LENGTH_MAX, _field.max_len, true));
	else if (_field.min_len) result.push(ValidationHelper.getAlertionString(_field, ATTR_NAME_LENGTH_MIN, _field.min_len, true));

 	if (_field.max && _field.min_len) result.push(ValidationHelper.getAlertionString(_field, 'value_range', [_field.min,_field.max], true));
	else if (_field.max) result.push(ValidationHelper.getAlertionString(_field, ATTR_NAME_MAX, _field.max, true));
	else if (_field.min) result.push(ValidationHelper.getAlertionString(_field, ATTR_NAME_MIN, _field.min, true));
	delete _field;
	return result;
}
ValidationHelper.extendees = $A();
ValidationHelper.extend = function(_extendee){
	if (_extendee == null
		|| typeof(_extendee) != 'function')	
		return;

	ValidationHelper.extendees.push(_extendee);
};
Event.observe(window,'unload',function(){
	$destroy(ValidationHelper);
	ValidationHelper = null;
});