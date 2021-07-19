if(!window.Class){
	var Class = {};
}
Ext.apply(Class, {
  create: function() {
	return function() {
	  this.initialize.apply(this, arguments);
	}
  }
});

Ext.applyIf(Array.prototype, {
	last : function(){
		return this[this.length - 1];
	}
});

Ext.applyIf(Event, {
  pointerX: function(event) {
    return event.pageX || (event.clientX +
      (document.documentElement.scrollLeft || document.body.scrollLeft));
  },

  pointerY: function(event) {
    return event.pageY || (event.clientY +
      (document.documentElement.scrollTop || document.body.scrollTop));
  }
});


var AttributeHelper = {
	prefix : 'attr_',
	autoId : 0,
	cache : {},
	setAttribute : function(element, sAttrName, sAttrValue){
		element = $(element);
		if(!element) return false;
		var _id = this.getId(element);
		var cache = this.cache;
		var attributes = cache[_id];
		if(!attributes){
			attributes = cache[_id] = {};
		}
		attributes[sAttrName.toUpperCase()] = sAttrValue;
	},
	getAttribute : function(element, sAttrName){
		element = $(element);
		if(!element) return null;
		var _id = this.getId(element);
		var cache = this.cache;
		var attributes = cache[_id];
		if(!attributes){
			return null;
		}
		return attributes[sAttrName.toUpperCase()];
	},
	removeAttribute : function(element, sAttrName){
		element = $(element);
		if(!element) return;
		var _id = this.getId(element);
		var cache = this.cache;
		var attributes = cache[_id];
		if(!attributes){
			return;
		}
		delete attributes[sAttrName.toUpperCase()];
	},
	getId : function(element){
		var _id = element.getAttribute("_id");
		if(_id != undefined) return _id;
		_id = element.name || element.id || (this.prefix + (++this.autoId));
		_id = _id.toUpperCase();
		element.setAttribute("_id", _id);
		return _id;
	}
};

AbstractValidator = Class.create();
AbstractValidator.prototype = {
	initialize : function(_field) {
		this.field = $(_field);
		this.validateObj = AttributeHelper.getAttribute(this.field, "_VALIDATEOBJ_");
		this.warning = "";		
	},
	execute : function(){
		this.warning = "";
		if(this.validateObj[$ValidatorConfigs.CLOSE] != undefined){
			try{
				if(eval(this.validateObj[$ValidatorConfigs.CLOSE]))
					return true;
			}catch(error){
				alert("AbstractValidator.prototype:execute:" + error.message);
				return false;
			}
		}
		if($F(this.field).trim() == ''){
			if(ValidationHelper.hasRequired(this.validateObj)){
				this.warning += this.getReplaceInfoContent("REQUIRED", "WARNING_INFO");
				return false;
			}else{
				return true;
			}
		}
		if(this.validateObj[$ValidatorConfigs.FORMAT]){
			eval("this.outerformat = " + this.validateObj[$ValidatorConfigs.FORMAT] + ";"); 
			if($F(this.field).trim() != '' && !this.outerformat.test($F(this.field))){
				this.warning += this.validateObj[$ValidatorConfigs.WARNING] || this.getWarning();
				return false;
			}
		}
		if($F(this.field).trim() != '' && this.format && !this.format.test($F(this.field))){
			this.warning += this.getWarning();
			return false;
		}

		var arrayMethod = [];
		arrayMethod.push(this.method || Ext.emptyFn);
		var strMethods = this.validateObj[$ValidatorConfigs.METHODS];
		if(strMethods){
			if(String.isString(strMethods)){//string			
				Array.prototype.push.apply(arrayMethod, strMethods.split(","));
			}else{//function
				if(!Array.isArray(strMethods)){		
					strMethods = [strMethods];
				}
				Array.prototype.push.apply(arrayMethod, strMethods);
			}
		}
		var result = arrayMethod[0].call(this);
		if(!result || arrayMethod.length <= 1) return result;
		result = true;
		for (var i = 1; i < arrayMethod.length; i++){
			if(Ext.isFunction(arrayMethod[i])){
				var temp = arrayMethod[i].call(this);
				if(temp == null)result = null;
				if(temp === false)result = false;
				continue;
			}
			arrayMethod[i] = arrayMethod[i].trim();
			if(arrayMethod[i] != ""){
				eval("var temp = " + arrayMethod[i]);
				if(temp == null)result = null;
				if(temp === false)result = false;
			}
		}
		return result;
	},
	getDesc : function(){
		var sDesc = this.field.getAttribute("validation_desc");
		if(sDesc != null) return sDesc;
		return this.validateObj[$ValidatorConfigs.DESC];
	},
	getWarning : function(){
		return ValidationHelper.getReplaceInfoContent("DEFAULT", "WARNING_INFO", this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id);
	},
	getReplaceInfoContent : function(info, infoType, args, _desc){
		var desc = '';
		if(this.validateObj[$ValidatorConfigs.NO_DESC] == undefined){
			desc = this.getDesc() || _desc || this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id;
		}
		return ValidationHelper.getReplaceInfoContent(info, infoType, desc, args);
	},
	checkSyntaxValidation:function(){
		return true;
	},
	getMessage : function(){
		return this.validateObj[$ValidatorConfigs.MESSAGE] || '';
	}
};
//locale
if(ValidatorLang.LOCALE == 'en'){
	Ext.apply(AbstractValidator.prototype, {
		getDesc : function(_desc){
			return "It ";
		},
		getReplaceInfoContent : function(info, infoType, args, _desc){
			var desc = this.getDesc() || _desc; 
			return ValidationHelper.getReplaceInfoContent(info, infoType, desc, args);
		}
	});
}
var NumberValidator = Class.create();
Object.extend(NumberValidator.prototype, AbstractValidator.prototype);
Object.extend(NumberValidator.prototype, {
	initialize : function(_field) {
		AbstractValidator.prototype.initialize.call(this, _field);
		this.type = this.validateObj[$ValidatorConfigs.TYPE].trim().toUpperCase();
		if(this.validateObj[$ValidatorConfigs.TYPE].trim().toLowerCase() == 'int'){
			this.parseMethod = parseInt;
			this.format = new RegExp('^-?\\d+(e[+-]?\\d+)?$', "i");
		}else{
			this.parseMethod = parseFloat;
			this.format = new RegExp('^-?\\d+(\\.\\d+)?(e[+-]?\\d+)?$', "i");
		}
		if(!this.checkValidationSyntax()) return false;
	},
	method : function(){
		if(this.validateObj[$ValidatorConfigs.VALUE_RANGE]){
			if(this.minValue > this.parseMethod($F(this.field)) || this.parseMethod($F(this.field)) > this.maxValue){
				this.warning += this.getReplaceInfoContent("VALUE_RANGE", "WARNING_INFO", [this.minValue, this.maxValue]);
				return false;
			}
			return true;
		}
		var flag = true;
		if(this.validateObj[$ValidatorConfigs.MIN]){
			if(this.parseMethod($F(this.field)) < this.minValue){
				flag = false;
				this.warning += this.getReplaceInfoContent("MIN", "WARNING_INFO", this.minValue);
			}
		}
		if(this.validateObj[$ValidatorConfigs.MAX]){
			if(this.parseMethod($F(this.field)) > this.maxValue){
				flag = false;
				this.warning += this.getReplaceInfoContent("MAX", "WARNING_INFO", this.maxValue);
			}
		}
		return flag;
	},
	getMessage:function (){
		if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
		var message = "";
		if(this.validateObj[$ValidatorConfigs.VALUE_RANGE]){
			message += this.getReplaceInfoContent("VALUE_RANGE", "MESSAGE_INFO", [this.minValue, this.maxValue]);
			return message;
		}
		if(this.validateObj[$ValidatorConfigs.MIN]){
			message += this.getReplaceInfoContent("MIN", "MESSAGE_INFO", this.minValue);
		}
		if(this.validateObj[$ValidatorConfigs.MAX]){
			message += this.getReplaceInfoContent("MAX", "MESSAGE_INFO", this.maxValue);
		}
		return message;
	},
	getWarning : function(){
//		return ValidationHelper.getReplaceInfoContent(this.type, "WARNING_INFO", this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id);
		return this.getReplaceInfoContent(this.type, "WARNING_INFO", null, this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id);
	},
	checkValidationSyntax : function(){
		if(this.validateObj[$ValidatorConfigs.VALUE_RANGE]){
			var valueRange = this.validateObj[$ValidatorConfigs.VALUE_RANGE].split(",");
			for (var i = 0; i < valueRange.length; i++){
				valueRange[i] = (valueRange[i] + "").trim();
			}
			if((valueRange.length != 2) ||
					(valueRange[0] != "" && isNaN(this.parseMethod(valueRange[0]))) || 
					(valueRange[1] != "" && isNaN(this.parseMethod(valueRange[1])))){
				alert('NumberValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是分割的两个数字！",this.validateObj[$ValidatorConfigs.VALUE_RANGE]));
				return false;
			}
			this.minValue = (valueRange[0] === "" ? Number.NEGATIVE_INFINITY : this.parseMethod(valueRange[0]));
			this.maxValue = (valueRange[1] === "" ? Number.POSITIVE_INFINITY : this.parseMethod(valueRange[1]));
		}
		if(this.validateObj[$ValidatorConfigs.MIN]){
			this.minValue = this.parseMethod(this.validateObj[$ValidatorConfigs.MIN].trim());
			if(isNaN(this.minValue)){
				alert('NumberValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是个数字！",this.validateObj[$ValidatorConfigs.MIN]));
				return false;
			}
		}
		if(this.validateObj[$ValidatorConfigs.MAX]){
			this.maxValue = this.parseMethod(this.validateObj[$ValidatorConfigs.MAX.trim()]);
			if(isNaN(this.maxValue)){
				alert('NumberValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是个数字！",this.validateObj[$ValidatorConfigs.MIN]));
				return false;
			}
		}
		return true;
	}	
});

var StringValidator = Class.create();
Object.extend(StringValidator.prototype, AbstractValidator.prototype);
Object.extend(StringValidator.prototype, {
	initialize : function(_field) {
		AbstractValidator.prototype.initialize.call(this, _field);
		if(!this.checkValidationSyntax()) return false;
	},
	method : function(){
		this.getLength();
		if(this.validateObj[$ValidatorConfigs.LENGTH_RANGE]){
			if(this.minLen > this.length || this.length > this.maxLen){
				this.warning += this.getReplaceInfoContent("LENGTH_RANGE", "WARNING_INFO", [this.minLen, this.maxLen]);
				return false;
			}
			return true;
		}
		var flag = true;
		if(this.validateObj[$ValidatorConfigs.MIN_LEN]){
			if(this.length < this.minLen){
				flag = false;
				this.warning += this.getReplaceInfoContent("MIN_LEN", "WARNING_INFO", [this.minLen]);
			}
		}
		if(this.validateObj[$ValidatorConfigs.MAX_LEN]){
			if(this.length > this.maxLen){
				flag = false;
				this.warning += this.getReplaceInfoContent("MAX_LEN", "WARNING_INFO", [this.maxLen]);
			}
		}
		return flag;	
	},
	getLength : function(){
		this.length = ($F(this.field)||'').byteLength();
	},
	getMessage : function(){
		if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
		var message = "";
		if(this.validateObj[$ValidatorConfigs.LENGTH_RANGE]){
			message += this.getReplaceInfoContent("LENGTH_RANGE", "MESSAGE_INFO", [this.minLen, this.maxLen]);
			return message;
		}
		if(this.validateObj[$ValidatorConfigs.MIN_LEN]){
			message += this.getReplaceInfoContent("MIN_LEN", "MESSAGE_INFO", this.minLen);
		}
		if(this.validateObj[$ValidatorConfigs.MAX_LEN]){
			message += this.getReplaceInfoContent("MAX_LEN", "MESSAGE_INFO", this.maxLen);
		}
	
//		if(message == '' && this.validateObj[$ValidatorConfigs.REQUIRED] != undefined){
		if(message == '' && ValidationHelper.hasRequired(this.validateObj)){
			message = this.getReplaceInfoContent("REQUIRED", "MESSAGE_INFO");
		}
		return message;
	},
	checkValidationSyntax : function(){
		if(this.validateObj[$ValidatorConfigs.LENGTH_RANGE]){
			var lengthRange = this.validateObj[$ValidatorConfigs.LENGTH_RANGE].split(",");
			for (var i = 0; i < lengthRange.length; i++){
				lengthRange[i] = lengthRange[i].trim();
			}
			if((lengthRange.length != 2) ||
					(lengthRange[0] != "" && isNaN(parseInt(lengthRange[0]))) || 
					(lengthRange[1] != "" && isNaN(parseInt(lengthRange[1])))){
				alert('StringValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是分割的两个数字！",this.validateObj[$ValidatorConfigs.LENGTH_RANGE]));
				return false;
			}
			this.minLen = (lengthRange[0] === "" ? 0 : parseInt(lengthRange[0]));
			this.maxLen = (lengthRange[1] === "" ? Number.POSITIVE_INFINITY : parseInt(lengthRange[1]));			
		}
		if(this.validateObj[$ValidatorConfigs.MIN_LEN]){
			this.minLen = parseInt((""+this.validateObj[$ValidatorConfigs.MIN_LEN]).trim());
			if(isNaN(this.minLen)){
				alert('StringValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是个数字！",this.validateObj[$ValidatorConfigs.MIN_LEN]));
				return false;
			}
		}
		if(this.validateObj[$ValidatorConfigs.MAX_LEN]){
			this.maxLen = parseInt((""+this.validateObj[$ValidatorConfigs.MAX_LEN]).trim());
			if(isNaN(this.maxLen)){
				alert('StringValidator.prototype:checkValidationSyntax:' + String.format("指定的{0}不是个数字！",this.validateObj[$ValidatorConfigs.MAX_LEN]));
				return false;
			}
		}
		return true;
	}
});

var CommonCharValidator = Class.create();
Object.extend(CommonCharValidator.prototype, StringValidator.prototype);
Object.extend(CommonCharValidator.prototype, {
	format : /^\w*$/,
	getMessage : function(){
		if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
		var message = StringValidator.prototype.getMessage.call(this);
		if(message != "")
			message += this.getReplaceInfoContent("COMMON_CHAR", "MESSAGE_INFO", null, ' ');
		else
			message = this.getReplaceInfoContent("COMMON_CHAR", "MESSAGE_INFO");
		return message;
	},
	getWarning : function(){
		return this.getReplaceInfoContent("COMMON_CHAR", "WARNING_INFO");
	}
});

var URLValidator = Class.create();
Object.extend(URLValidator.prototype, AbstractValidator.prototype);
Object.extend(URLValidator.prototype, {
	format : /^(http|https):\/\/(?:(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)|localhost)(:(\d+))?(\/)?/i,		  
	getMessage : function(){
		if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
		return this.getReplaceInfoContent("URL", "MESSAGE_INFO");
	},
	method : function(){
		var StringVal = new StringValidator(this.field);		
		var result = StringVal.execute();
		this.warning = StringVal.warning;
		return result;
	},	
	getWarning:function(){
		return this.validateObj[$ValidatorConfigs.WARNING] || this.getReplaceInfoContent("URL", "WARNING_INFO");
	}
});

var IPV4Validator = Class.create();
Object.extend(IPV4Validator.prototype, AbstractValidator.prototype);
Object.extend(IPV4Validator.prototype, {
	getMessage : function(){
		if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
		return this.getReplaceInfoContent("IPV4", "MESSAGE_INFO");
	},
	getWarning : function(){
		//return this.getReplaceInfoContent("IPV4", 'WARNING_INFO');
	},
	method : function(){
		var format = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;
		if(format.exec($F(this.field)) == null){
			this.warning += this.getReplaceInfoContent("IPV4", 'WARNING_INFO');
			return false;
		}
		if (RegExp.$1 == 0){
			this.warning += this.getReplaceInfoContent("IPV4_HIGH_EQUAL_0", 'WARNING_INFO');
			return false;
		}
		if (Math.max(RegExp.$1, RegExp.$2, RegExp.$3, RegExp.$4) > 255){
			this.warning += this.getReplaceInfoContent('IPV4_BEYOND_255', "WARNING_INFO");
			return false;
		}
		return true;
	}
});

var DateValidator = Class.create();
Object.extend(DateValidator.prototype, AbstractValidator.prototype);
Object.extend(DateValidator.prototype, {
	formatRegExp : /^(yy|yyyy)(-|\/)(m{1,2})(\2)(d{1,2})(\W+(h{1,2})(:)(M{1,2})((\8)(s{0,2}))?)?$/,
	dateRegExp : /^(\d{2}|\d{4})(-|\/)(\d{1,2})(\2)(\d{1,2})(\W+(\d{1,2})(:)(\d{1,2})((\8)(\d{0,2}))?)?$/,

	initialize : function(_field) {
		AbstractValidator.prototype.initialize.call(this, _field);
		if(!this.checkValidationSyntax()) return false;
	},
	method : function(){
		var sFormat = this.validateObj[$ValidatorConfigs.DATE_FORMAT];
		var sTemp = sFormat.replace(/yy|mm|dd|hh|ss/gi, "t").replace(/m|d|h|s/ig, '\\d{1,2}').replace(/t/ig, '\\d{2}');
		var oRegExp = new RegExp("^"+sTemp+"$");
		var sValue = $F(this.field);
		if(!oRegExp.test(sValue)){
			this.warning = ((ValidatorLang.TEXT7 || "没有匹配日期格式:") + this.validateObj[$ValidatorConfigs.DATE_FORMAT].toLowerCase());
			if(!this.validateObj["showDefault"]){
				this.field.value = new Date().format(sFormat || "yyyy-mm-dd HH:MM");
				this.field.select();
				this.field.focus();
			}
			return false;
		}


		var matchs = $F(this.field).match(this.dateRegExp);
		if(!matchs){
			this.warning = ((ValidatorLang.TEXT7 || "没有匹配日期格式:") + this.validateObj[$ValidatorConfigs.DATE_FORMAT].toLowerCase());   
			this.field.value = new Date().format(sFormat || "yyyy-mm-dd HH:MM");
			this.field.select();
			this.field.focus();
			return false;
		}
		var year = parseInt(matchs[1], 10);
		var month = parseInt(matchs[3], 10);
		var day = parseInt(matchs[5], 10);

		var hour = parseInt(matchs[7], 10);
		var minute = parseInt(matchs[9], 10);
		var second = parseInt(matchs[12], 10);
		
		if(matchs[1].length != this.formatMatchs[1].length
				|| matchs[2] != this.formatMatchs[2]){
			this.warning = ((ValidatorLang.TEXT7 || "没有匹配日期格式:") + this.validateObj[$ValidatorConfigs.DATE_FORMAT].toLowerCase());   
			this.field.value = new Date().format(sFormat || "yyyy-mm-dd HH:MM");
			this.field.select();
			this.field.focus();
			return false;
		}
		if(month < 1 || month > 12){
			this.warning = ValidatorLang.TEXT14 || "月份应该为1到12的整数";   
			return false;
		}
		if (day < 1 || day > 31){
			this.warning = (ValidatorLang.TEXT15 || "每个月的天数应该为1到31的整数"); 
			return false;
		}
		if ((month==4 || month==6 || month==9 || month==11) && day==31){   
			this.warning = ValidatorLang.TEXT16 || "该月不存在31号";   
			return false;   
		}   
		if (month==2){   
			var isleap=(year % 4==0 && (year % 100 !=0 || year % 400==0));   
			if (day>29){   
				this.warning = ValidatorLang.TEXT17 || "2月最多有29天";   
				return false;   
			}   
			if ((day==29) && (!isleap)){   
				this.warning = ValidatorLang.TEXT18 || "闰年2月才有29天";   
				return false;   
			}   
		}
		if(hour && hour<0 || hour>23){   
			this.warning = ValidatorLang.TEXT19 || "小时应该是0到23的整数";   
			return false;   
		}   
		if(minute && minute<0 || minute>59){   
			this.warning = ValidatorLang.TEXT20 || "分应该是0到59的整数";   
			return false;   
		}   
		if(second && second<0 || second>59){   
			this.warning = ValidatorLang.TEXT21 || "秒应该是0到59的整数";   
			return false;   
		}		
		return true;
	},
	checkValidationSyntax : function(){
		var timeFormat = this.validateObj[$ValidatorConfigs.DATE_FORMAT]
				= this.validateObj[$ValidatorConfigs.DATE_FORMAT] || $ValidatorConfigs.DATE_FORMAT_DEFAULT;
		this.formatMatchs = timeFormat.match(this.formatRegExp);
		if(!this.formatMatchs){
			alert((ValidatorLang.TEXT22 || "日期格式错误：") + timeFormat.toLowerCase());
			return false;
		}
		return true;
	}
});