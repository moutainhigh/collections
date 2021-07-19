function defClass(){
	return function(){
		this.initialize.apply(this, arguments);
	}
}
String.prototype.byteLength = function(){
	var length = 0;
	this.replace(/[^\x00-\xff]/g,function(){length++;});
	return this.length+length;
}
var AttrHelper = {
	prefix : 'attr_',
	autoId : 0,
	cache : {},
	setAttribute : function(el, attr, v){
		if(!(el = $(el))) return;
		var _id = this.getId(el);
		var attrs = this.cache[_id] = this.cache[_id] || {};
		attrs[attr.toUpperCase()] = v;
	},
	getAttribute : function(el, attr){
		if(!(el = $(el))) return null;
		var attrs = this.cache[this.getId(el)];
		if(!attrs)return null;
		return attrs[attr.toUpperCase()];
	},
	removeAttribute : function(el, attr){
		if(!(el = $(el))) return;
		var attrs = this.cache[this.getId(el)];
		if(!attrs)return;
		delete attrs[attr.toUpperCase()];
	},
	getId : function(el){
		var _id = el.getAttribute("_id");
		if(_id != null) return _id;
		_id = el.name || el.id || (this.prefix + (++this.autoId));
		_id = _id.toUpperCase();
		el.setAttribute("_id", _id);
		return _id;
	}
};
if(!window.m_Vdcf){
	var m_Vdcf = {};
}
Object.extend(m_Vdcf, {
	msg_info : {
		REQUIRED : wcm.LANG.WCM52_ALERT_78 || '{0}不能为空',
		NUMBER : wcm.LANG.WCM52_ALERT_79 || '{0}必须为数字',
		INT : wcm.LANG.WCM52_ALERT_80 || '{0}必须为整数',
		INTEGER : wcm.LANG.WCM52_ALERT_81 || '{0}必须为整数',
		FLOAT : wcm.LANG.WCM52_ALERT_82 || '{0}必须为小数',
		DOUBLE : wcm.LANG.WCM52_ALERT_83 || '{0}必须为双精度数',
		MIN_LEN : wcm.LANG.WCM52_ALERT_84 || '{0}最小长度为{1}',
		MAX_LEN : wcm.LANG.WCM52_ALERT_85 || '{0}最大长度为{1}',
		MIN : wcm.LANG.WCM52_ALERT_86 || '{0}最小值为{1}',
		MAX : wcm.LANG.WCM52_ALERT_87 || '{0}最大值为{1}',
		LENGTH_RANGE : wcm.LANG.WCM52_ALERT_88 || '{0}长度范围为{1}~{2}',
		VALUE_RANGE : wcm.LANG.WCM52_ALERT_89 || '{0}值范围为{1}~{2}',
		URL : wcm.LANG.WCM52_ALERT_90 || '{0}期望格式为:http(s)://[站点](:[端口])/(子目录)',
		LINK : wcm.LANG.WCM52_ALERT_91 || '{0}期望格式为:xxx.xxx.xxx',
		IPV4 : wcm.LANG.WCM52_ALERT_92 || '{0}IPV4格式，期望格式为: 192.9.200.114',
		COMMON_CHAR : wcm.LANG.WCM52_ALERT_93 || '{0}必须为字母、下划线或者数字所组成',
		EMAIL : wcm.LANG.WCM52_ALERT_94 || '{0}必须为Email格式(如xxx@xxx.com)'
	},
	warn_info : '{0}',
	warn_reginfo : wcm.LANG.WCM52_ALERT_95 || '格式不正确，需匹配正则式：{0}'
});
var vdFactory = {
	getValidateObj : function(dom){
		dom = $(dom);
		var vd = dom.getAttribute("validation") || "", rst;
		eval("rst={" + vd + "}");
		return rst;
	},
	makeValidator : function(el){
		el = $(el);
		var obj = this.getValidateObj(el);
		AttrHelper.setAttribute(el, "_VALIDATEOBJ_",  obj);
		var sType = obj['type'];
		var rst = !sType ? new AbsVdtor(el) : this._makeValidator(sType, el);		
		AttrHelper.setAttribute(el, "_VALIDATORINS_",  rst);
		return rst;
	},
	_makeValidator : function(type, _field){
		switch(type.toLowerCase()){
			case "int":
			case "integer":
			case "float":
			case "double":
			case "number":
				return new NumVdtor(_field);
			case "string":
				return new StrVdtor(_field);
			case "url":
				var rst = new StrVdtor(_field);
				rst.format = /^(http|https|ftp|mtsp):\/\/.+$/i;  
				rst.formatType = 'URL';
				return rst;
			case "ip":
				var rst = new StrVdtor(_field);
				rst.format = /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;  
				rst.formatType = 'IPV4';
				return rst;
			case "common_char":
				var rst = new StrVdtor(_field);
				rst.format = new RegExp('^[a-z][a-z0-9_]*$', 'i');
				rst.formatType = 'COMMON_CHAR';
				return rst;
			case "email":
				var rst = new StrVdtor(_field);
				rst.format = new RegExp('^.+@.+$', '');
				rst.formatType = 'EMAIL';
				return rst;
		}
		return null;
	}
};

var ValidationHelper = {
	isRequired : function(obj, el){
		var required = obj['required'];
		var rst = !(required=='0' || required=='false' || (required==null&&required!=''));
		return rst || el.getAttribute("not_null", 2) == "1";
	},
	filter : function(dom){
		if(!dom.getAttribute("validation")) return false;
		if(dom.getAttribute("forceValid")) return true;
		if(dom.disabled || dom.offsetWidth <= 0 || dom.offsetHeight <= 0) return false;
		return true;
	},
	valid : function(){
		var result = [];
		for(var i = 0; i < arguments.length; i++){
			var dom = $(arguments[i]);
			if(!this.filter(dom)) continue;
			var vdor = vdFactory.makeValidator(dom);
			if(!vdor)continue;
			var isValid = vdor.execute();
			dom.setAttribute('isValid', isValid?1:0);
			if(!isValid){
				dom.setAttribute('validError', vdor.warning);
			}
			result.push({
				id : dom.id || dom.name,
				isValid : isValid,
				message : vdor.getMessage(),
				warning : vdor.warning || ""
			});
		}
		return result.length <= 1 ? result[0] : result;
	},
	validForm : function(boxId){
		var doms = [], box = $(boxId);
		var tags = ["INPUT", "TEXTAREA", "SELECT"];
		for (var i = 0; i < tags.length; i++){
			var eles = box.getElementsByTagName(tags[i]);
			doms.push.apply(doms, $A(eles));
		}
		return this.valid.apply(this, doms);
	}
};
AbsVdtor = defClass();
AbsVdtor.prototype = {
	method : function(){return true;},
	initialize : function(_field) {
		this.el = $(_field);
		this.obj = AttrHelper.getAttribute(this.el, "_VALIDATEOBJ_");
		this.warning = "";		
	},
	execute : function(){
		this.warning = "";
		var v = $F(this.el);
		if(v==null || v.trim() == ''){
			if(ValidationHelper.isRequired(this.obj, this.el)){
				this.warning = this.warn("REQUIRED", "msg_info");
				return false;
			}
			return true;
		}
		if(this.obj['format']){
			eval("this.outerformat = " + this.obj['format'] + ";"); 
			if(!this.outerformat.test(v)){
				this.warning += String.format(m_Vdcf.warn_reginfo, this.outerformat);
				return false;
			}
		}
		if(this.format && !this.format.test(v)){
			this.warning += this.warn(this.formatType||this.type.toUpperCase(), "msg_info");
			return false;
		}
		return this.method();
	},
	_warn : function(){
		var args = $A(arguments);
		args.unshift(m_Vdcf.warn_info);
		return String.format.apply(null, args);
	},
	warn : function(){
		return this._warn(this._info.apply(this, arguments));
	},
	_info : function(info, infoType, value){
		var args = value ? [value] : [];
		var desc = this.obj[m_Vdcf.DESC] || this.el.elname || this.el.name || this.el.id;
		args.unshift(desc);
		args.unshift(m_Vdcf.msg_info[info]);
		return String.format.apply(null, args);
	},
	_check : function(){
		return true;
	},
	getMessage : function(){
		return '';
	}
};

var NumVdtor = defClass();
Object.extend(NumVdtor.prototype, AbsVdtor.prototype);
Object.extend(NumVdtor.prototype, {
	initialize : function(_field) {
		AbsVdtor.prototype.initialize.call(this, _field);
		var type = this.type = this.obj['type'].trim().toUpperCase();
		if(type == 'INT'){
			this.parseMethod = parseInt;
			this.format = new RegExp('^-?\\d+(e[\+-]?\\d+)?$', "i");
		}else{
			this.parseMethod = parseFloat;
			this.format = new RegExp('^-?\\d+(\.\\d+)?(e[\+-]?\\d+)?$', "i");
		}
		if(!this._check()) return false;
	},
	method : function(){
		var v = this.parseMethod($F(this.el)) || 0;
		if(this.obj['value_range']){
			if(this.minValue > v || v > this.maxValue){
				this.warning += this.warn("VALUE_RANGE", "msg_info", this.minValue, this.maxValue);
				return false;
			}
			return true;
		}
		if(this.obj['min'] && v < this.minValue){
			this.warning += this.warn("MIN", "msg_info", this.minValue);
			return false;
		}
		if(this.obj['max'] && v > this.maxValue){
			this.warning += this.warn("MAX", "msg_info", this.maxValue);
			return false;
		}
		return true;
	},
	getMessage:function (){
		var msg = "";
		if(this.obj['value_range']){
			return this._info("VALUE_RANGE", "msg_info", this.minValue, this.maxValue);
		}
		if(this.obj['min']){
			msg += this._info("MIN", "msg_info", this.minValue);
		}
		if(this.obj['max']){
			msg += this._info("MAX", "msg_info", this.maxValue);
		}
		return msg;
	},
	_check : function(){
		if(this.obj['value_range']){
			var arr = this.obj['value_range'].split(",");
			this.minValue = this.parseMethod(arr[0]) || Number.NEGATIVE_INFINITY;
			this.maxValue = this.parseMethod(arr[1]) || Number.POSITIVE_INFINITY;
		}
		if(this.obj['min']){
			this.minValue = this.parseMethod(this.obj['min']) || Number.NEGATIVE_INFINITY;
		}
		if(this.obj['max']){
			this.maxValue = this.parseMethod(this.obj['max']) || Number.POSITIVE_INFINITY;
		}
		return true;
	}	
});

var StrVdtor = defClass();
Object.extend(StrVdtor.prototype, AbsVdtor.prototype);
Object.extend(StrVdtor.prototype, {
	initialize : function(_field) {
		AbsVdtor.prototype.initialize.call(this, _field);
		if(!this._check()) return false;
	},
	method : function(){
		var len = ($F(this.el)||'').byteLength();
		if(this.obj['len_range']){
			if(this.minLen > len || len > this.maxLen){
				this.warning += this.warn("LENGTH_RANGE", "msg_info", this.minLen, this.maxLen);
				return false;
			}
			return true;
		}
		if(this.obj['min_len'] && len < this.minLen){
			this.warning += this.warn("MIN_LEN", "msg_info", this.minLen);
			return false;
		}
		if(this.obj['max_len'] && len > this.maxLen){
			this.warning += this.warn("MAX_LEN", "msg_info", this.maxLen);
			return false
		}
		return true;
	},
	getMessage : function(){
		var msg = "";
		if(this.obj['len_range']){
			return this._info("LENGTH_RANGE", "msg_info", this.minLen, this.maxLen);
		}
		if(this.obj['min_len']){
			msg += this._info("MIN_LEN", "msg_info", this.minLen);
		}
		if(this.obj['max_len']){
			msg += this._info("MAX_LEN", "msg_info", this.maxLen);
		}	
		if(msg == '' && ValidationHelper.isRequired(this.obj, this.el)){
			msg = this._info("REQUIRED", "msg_info");
		}
		return msg;
	},
	_check : function(){
		if(this.obj['len_range']){
			var arr = this.obj['len_range'].split(",");
			this.minLen = parseInt(arr[0]) || 0;
			this.maxLen = parseInt(arr[1]) || Number.POSITIVE_INFINITY;			
		}
		if(this.obj['min_len']){
			this.minLen = parseInt(this.obj['min_len']) || 0;
		}
		if(this.obj['max_len']){
			this.maxLen = parseInt(this.obj['max_len']) || 0;
		}
		return true;
	}
});
