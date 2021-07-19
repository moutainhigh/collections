var Custom_radio_Validator = Class.create();
Object.extend(Custom_radio_Validator.prototype, Abstract.Validator.prototype);
Object.extend(Custom_radio_Validator.prototype, {
	method : function(){
		var radioArray = this.field.name ? document.getElementsByName(this.field.name) : [this.field];
		for (var i = 0; i < radioArray.length; i++){
			if(radioArray[i].checked)
				return true;
		}
		return false;
	}
});

var Custom_select_Validator = Class.create();
Object.extend(Custom_select_Validator.prototype, Abstract.Validator.prototype);
Object.extend(Custom_select_Validator.prototype, {
	method : function(){
		var excludeValue = this.validateObj[$ValidatorConfigs.EXCLUDE];
		if(excludeValue == undefined){
			excludeValue = this.field.options[0] ? this.field.options[0].value : "";
		}
		var selectValue = $F(this.field);
		if(excludeValue == selectValue){
			this.warning += this.getReplaceInfoContent("SELECT", "WARNING_INFO");
			return false;
		}
		return true;
	},
	getMessage : function(){
		if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
		return this.getReplaceInfoContent("SELECT", "MESSAGE_INFO");
	}
});


var Custom_time_Validator = Class.create();
Object.extend(Custom_time_Validator.prototype, Abstract.Validator.prototype);
Object.extend(Custom_time_Validator.prototype, {
	_format : [/^(\d\d):(\d\d)(:(\d\d))?$/, /^(\d{1,2}):(\d{1,2})(:(\d{1,2}))?$/],
	initialize : function(_field) {
		Abstract.Validator.prototype.initialize.call(this, _field);
		if(!this.checkValidationSyntax()) return false;	
	},
	method : function(){
		if($F(this.field).match(this._format[this._type])){
			if(parseInt(RegExp.$1) > 23){
				this.warning += this.getReplaceInfoContent("HOUR", "WARNING_INFO");
				return false;
			}
			if(parseInt(RegExp.$2) > 59){
				this.warning += this.getReplaceInfoContent("MINUTE", "WARNING_INFO");
				return false;
			}
			if(RegExp.$4 && parseInt(RegExp.$4) > 59){
				this.warning += this.getReplaceInfoContent("SECOND", "WARNING_INFO");
				return false;			
			}
			return true;
		}else{
			this.warning += this.getReplaceInfoContent("TIME", "WARNING_INFO");
			return false;
		}
	},
	getMessage : function(){
		if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
		if(this._type == 0){
			return this.getReplaceInfoContent("TIME_ONE", "MESSAGE_INFO");
		}else if(this._type == 1){
			return this.getReplaceInfoContent("TIME_TWO", "MESSAGE_INFO");
		}
	},
	checkValidationSyntax : function(){//仅执行少量的语法检验
		var timeFormat = this.validateObj[$ValidatorConfigs.TIME_FORMAT];
		if(timeFormat != undefined){
			timeFormat = timeFormat.trim();
			if(timeFormat.replace(/h|m|s|:/ig,'') != ""){
				alert("Custom_time_Validator.prototype:checkValidationSyntax:时间的time_format格式不对");
				return false;
			}
			var timeArray = timeFormat.split(":");
			if(timeArray[0].length == 2){
				this._type = 0;
			}else{
				this._type = 1;
			}
		}else{
			if($ValidatorConfigs.TIME_FORMAT_DEFAULT == 'hh:mm:ss'){
				this._type = 0;
			}else{
				this._type = 1;
			}
		}
		return true;
	}
});

var Custom_combin_Validator = Class.create();
Object.extend(Custom_combin_Validator.prototype, Abstract.Validator.prototype);
Object.extend(Custom_combin_Validator.prototype, {
	initialize : function(_field) {
		Abstract.Validator.prototype.initialize.call(this, _field);
		if(!this.checkValidationSyntax()) return false;	
	},
	method : function(){
		var innerValidationIns = ValidationFactory._makeValidator(this._subtype, this.field);
		return true;
	},
	getMessage : function(){
		if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
		return "";
	},
	checkValidationSyntax : function(){
		this._field = this.validateObj[$ValidatorConfigs.FIELD];
		this._subtype = this.validateObj[$ValidatorConfigs.SUBTYPE];
		if(this._field == undefined){
			alert("Custom_combin_Validator.prototype:checkValidationSyntax:没有指定field域");
			return false;
		}
		if(this._subtype == undefined){
			alert("Custom_combin_Validator.prototype:checkValidationSyntax:没有指定sub_type域");
			return false;
		}
		this._relation = this.validateObj[$ValidatorConfigs.RELATION];
		if(this._relation == undefined){
			this._relation = "=";
		}else{
			this._relation = this._relation.trim();
		}
		if(this._relation.replace(/>|=|</g, "") != ''){
			alert("Custom_combin_Validator.prototype:checkValidationSyntax:relation域指定不合法");
			return false;			
		}
	}
});

var Custom_link_Validator = Class.create();
Object.extend(Custom_link_Validator.prototype, Abstract.Validator.prototype);
Object.extend(Custom_link_Validator.prototype, {
	format:/^([^.]+\.){1,}[^.]+$/,
	getWarning:function (){
		return this.getReplaceInfoContent("LINK", "WARNING_INFO");
	},
	method : function(){
		var StringVal = new StringValidator(this.field);		
		var result = StringVal.execute();
		this.warning = StringVal.warning;
		return result;
	},	
	getMessage:function (){
		if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
		return this.getReplaceInfoContent("LINK", "MESSAGE_INFO");
	}
});

var Custom_common_char2_Validator = Class.create();
Object.extend(Custom_common_char2_Validator.prototype, Abstract.Validator.prototype);
Object.extend(Custom_common_char2_Validator.prototype, {	
	method : function(){
		if(!$F(this.field).match(/^[A-Za-z]\w*$/)){
			this.warning += this.getReplaceInfoContent("COMMON_CHAR2", "WARNING_INFO");
			return false;
		}
		var comCharVal = new CommonCharValidator(this.field);		
		var result = comCharVal.execute();
		this.warning = comCharVal.warning;
		return result;
	},
	getMessage:function (){
		if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
		return this.getReplaceInfoContent("COMMON_CHAR2", "MESSAGE_INFO");
	}
});

var Custom_filename_Validator = Class.create();
Object.extend(Custom_filename_Validator.prototype, Abstract.Validator.prototype);
Object.extend(Custom_filename_Validator.prototype, {	
	method : function(){
		if($F(this.field).match(/[\\\/\:\*\?\"\<\>\|]/)){
			this.warning += '不应包含\\/:*?"<>|等字符'; //TODO改为从配置中取
			return false;
		}
		var StringVal = new StringValidator(this.field);		
		var result = StringVal.execute();
		this.warning = StringVal.warning;
		return result;
	},
	getMessage:function (){
		//if(this.validateObj[$ValidatorConfigs.MESSAGE])return this.validateObj[$ValidatorConfigs.MESSAGE];
		return '普通字符，\\/:*?"<>|等字符除外'; //TODO改为从配置中取
	}
});

//wenyh@2007-07-27 uri validator,any protocol.
var Custom_uri_Validator = Class.create();
Object.extend(Custom_uri_Validator.prototype, Abstract.Validator.prototype);
Object.extend(Custom_uri_Validator.prototype, {
	format : /^(((\w+):\/\/)|(mailto:))([^/:]+)(:\d*)?([^# ]*)/i,		  
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