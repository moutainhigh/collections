Abstract.Validator = Class.create();
Abstract.Validator.prototype = {
	initialize : function(_field) {
		this.field = $(_field);
		this.validateObj = this.field.getAttribute("_VALIDATEOBJ_");
		this.warning = "";		
	},
	execute : function(){
		this.warning = "";
		if(this.validateObj[$ValidatorConfigs.CLOSE] != undefined){
			try{
				if(eval(this.validateObj[$ValidatorConfigs.CLOSE]))
					return true;
			}catch(error){
				alert("Abstract.Validator.prototype:execute:" + error.message);
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

		var strMethods = this.validateObj[$ValidatorConfigs.METHODS] || "";
		var arrayMethod = strMethods.split(",");
		arrayMethod.push((this.method || Prototype.emptyFunction) + ".call(this)");

		var result = true;
		for (var i = 0; i < arrayMethod.length; i++){
			if(arrayMethod[i].trim() != ""){
				eval("var temp = " + arrayMethod[i]);
				if(temp == false){
					result = false;
				}
			}
		}
		return result;
	},
	getWarning : function(){
//		return ValidationHelper.getReplaceInfoContent("DEFAULT", "WARNING_INFO", this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id);
		return this.getReplaceInfoContent("DEFAULT", "WARNING_INFO", null, this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id);
	},
	getReplaceInfoContent : function(info, infoType, args, _desc){
		var desc = '';
		if(this.validateObj[$ValidatorConfigs.NO_DESC] == undefined){
			desc = _desc || this.validateObj[$ValidatorConfigs.DESC] || this.field.name || this.field.id;
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

var NumberValidator = Class.create();
Object.extend(NumberValidator.prototype, Abstract.Validator.prototype);
Object.extend(NumberValidator.prototype, {
	initialize : function(_field) {
		Abstract.Validator.prototype.initialize.call(this, _field);
		this.type = this.validateObj[$ValidatorConfigs.TYPE].trim().toUpperCase();
		if(this.validateObj[$ValidatorConfigs.TYPE].trim().toLowerCase() == 'int'){
			this.parseMethod = parseInt;
			this.format = new RegExp('^-?\\d+(e[\+-]?\\d+)?$', "i");
		}else{
			this.parseMethod = parseFloat;
			this.format = new RegExp('^-?\\d+(\.\\d+)?(e[\+-]?\\d+)?$', "i");
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
				alert('NumberValidator.prototype:checkValidationSyntax:' + "指定的" + this.validateObj[$ValidatorConfigs.VALUE_RANGE] + "不是,分割的两个数字！");
				return false;
			}
			this.minValue = (valueRange[0] === "" ? Number.NEGATIVE_INFINITY : this.parseMethod(valueRange[0]));
			this.maxValue = (valueRange[1] === "" ? Number.POSITIVE_INFINITY : this.parseMethod(valueRange[1]));
		}
		if(this.validateObj[$ValidatorConfigs.MIN]){
			this.minValue = this.parseMethod(this.validateObj[$ValidatorConfigs.MIN].trim());
			if(isNaN(this.minValue)){
				alert('NumberValidator.prototype:checkValidationSyntax:' + "指定的" + this.validateObj[$ValidatorConfigs.MIN] + "不是个数字！");
				return false;
			}
		}
		if(this.validateObj[$ValidatorConfigs.MAX]){
			this.maxValue = this.parseMethod(this.validateObj[$ValidatorConfigs.MAX.trim()]);
			if(isNaN(this.maxValue)){
				alert('NumberValidator.prototype:checkValidationSyntax:' + "指定的" + this.validateObj[$ValidatorConfigs.MIN] + "不是个数字！");
				return false;
			}
		}
		return true;
	}	
});

var StringValidator = Class.create();
Object.extend(StringValidator.prototype, Abstract.Validator.prototype);
Object.extend(StringValidator.prototype, {
	initialize : function(_field) {
		Abstract.Validator.prototype.initialize.call(this, _field);
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
		this.length = $F(this.field).byteLength();
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
				alert('StringValidator.prototype:checkValidationSyntax:' + "指定的" + this.validateObj[$ValidatorConfigs.LENGTH_RANGE] + "不是,分割的两个数字！");
				return false;
			}
			this.minLen = (lengthRange[0] === "" ? 0 : parseInt(lengthRange[0]));
			this.maxLen = (lengthRange[1] === "" ? Number.POSITIVE_INFINITY : parseInt(lengthRange[1]));			
		}
		if(this.validateObj[$ValidatorConfigs.MIN_LEN]){
			this.minLen = parseInt(this.validateObj[$ValidatorConfigs.MIN_LEN].trim());
			if(isNaN(this.minLen)){
				alert('StringValidator.prototype:checkValidationSyntax:' + "指定的" + this.validateObj[$ValidatorConfigs.MIN_LEN] + "不是个数字！");
				return false;
			}
		}
		if(this.validateObj[$ValidatorConfigs.MAX_LEN]){
			this.maxLen = parseInt(this.validateObj[$ValidatorConfigs.MAX_LEN.trim()]);
			if(isNaN(this.maxLen)){
				alert('StringValidator.prototype:checkValidationSyntax:' + "指定的" + this.validateObj[$ValidatorConfigs.MAX_LEN] + "不是个数字！");
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
Object.extend(URLValidator.prototype, Abstract.Validator.prototype);
Object.extend(URLValidator.prototype, {
	format : /^(http|https):\/\/(([A-Z0-9][A-Z0-9_-]*)(\.[A-Z0-9][A-Z0-9_-]*)+)(:(\d+))?(\/)?/i,		  
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
Object.extend(IPV4Validator.prototype, Abstract.Validator.prototype);
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
Object.extend(DateValidator.prototype, Abstract.Validator.prototype);
Object.extend(DateValidator.prototype, {
	formatRegExp : /^(yy|yyyy)(-|\/)(m{1,2})(\2)(d{1,2})(\W+(h{1,2})(:)(M{1,2})((\8)(s{0,2}))?)?$/,
	dateRegExp : /^(\d{2}|\d{4})(-|\/)(\d{1,2})(\2)(\d{1,2})(\W+(\d{1,2})(:)(\d{1,2})((\8)(\d{0,2}))?)?$/,

	initialize : function(_field) {
		Abstract.Validator.prototype.initialize.call(this, _field);
		if(!this.checkValidationSyntax()) return false;
	},
	method : function(){
		var matchs = $F(this.field).match(this.dateRegExp);
		if(!matchs){
			this.warning = "没有匹配日期格式：" + this.validateObj[$ValidatorConfigs.DATE_FORMAT];   
			return false;
		}
		var year = parseInt(matchs[1], 10);
		var month = parseInt(matchs[3], 10);
		var day = parseInt(matchs[5], 10);

		var hour = parseInt(matchs[7], 10);
		var minute = parseInt(matchs[9], 10);
		var second = parseInt(matchs[12], 10);
		if($F(this.field).length != this.validateObj[$ValidatorConfigs.DATE_FORMAT].length
				|| matchs[2] != this.formatMatchs[2]){
			this.warning = "没有匹配日期格式：" + this.validateObj[$ValidatorConfigs.DATE_FORMAT];   
			return false;
		}
		if(month < 1 || month > 12){
			this.warning = "月份应该为1到12的整数";   
			return false;
		}
		if (day < 1 || day > 31){
			this.warning = "每个月的天数应该为1到31的整数"+day; 
			return false;
		}
		if ((month==4 || month==6 || month==9 || month==11) && day==31){   
			this.warning = "该月不存在31号";   
			return false;   
		}   
		if (month==2){   
			var isleap=(year % 4==0 && (year % 100 !=0 || year % 400==0));   
			if (day>29){   
				this.warning = "2月最多有29天";   
				return false;   
			}   
			if ((day==29) && (!isleap)){   
				this.warning = "闰年2月才有29天";   
				return false;   
			}   
		}
		if(hour && hour<0 || hour>23){   
			this.warning = "小时应该是0到23的整数";   
			return false;   
		}   
		if(minute && minute<0 || minute>59){   
			this.warning = "分应该是0到59的整数";   
			return false;   
		}   
		if(second && second<0 || second>59){   
			this.warning = "秒应该是0到59的整数";   
			return false;   
		}		
		return true;
	},
	checkValidationSyntax : function(){
		var timeFormat = this.validateObj[$ValidatorConfigs.DATE_FORMAT]
				= this.validateObj[$ValidatorConfigs.DATE_FORMAT] || this.validateObj[$ValidatorConfigs.DATE_FORMAT_DEFAULT];
		this.formatMatchs = timeFormat.match(this.formatRegExp);
		if(!this.formatMatchs){
			alert("日期格式错误：" + timeFormat);
			return false;
		}
		return true;
	}
});