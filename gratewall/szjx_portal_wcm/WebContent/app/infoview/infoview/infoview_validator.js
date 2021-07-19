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
		REQUIRED : '{0}不能为空',
		NUMBER : '{0}必须为数字',
		INT : '{0}必须为整数',
		INTEGER : '{0}必须为整数',
		FLOAT : '{0}必须为小数',
		DOUBLE : '{0}必须为双精度数',
		MIN_LEN : '{0}最小长度为{1}',
		MAX_LEN : '{0}最大长度为{1}',
		MIN : '{0}最小值为{1}',
		MAX : '{0}最大值为{1}',
		LENGTH_RANGE : '{0}长度范围为{1}~{2}',
		VALUE_RANGE : '{0}值范围为{1}~{2}',
		URL : '{0}期望格式为:http(s)://[站点](:[端口])/(子目录)',
		LINK : '{0}期望格式为:xxx.xxx.xxx',
		IPV4 : '{0}IPV4格式，期望格式为: 192.9.200.114',
		COMMON_CHAR : '{0}必须为字母开头的字母、下划线或者数字所组成',
		COMMON_CHAR2 : '{0}必须为字母、下划线或者数字所组成',
		EMAIL : '{0}必须为Email格式(如xxx@xxx.com)'
	},
	warn_info : '{0}',
	warn_reginfo : '格式不正确，需匹配正则式：{0}'
});
var vdFactory = {
	getValidateObj : function(dom){
		dom = $(dom);
		var vd = dom.getAttribute("validation") || "", rst;
		eval("rst={" + vd + "}");
		var data_pattern = dom.getAttribute("data_pattern");
		if(data_pattern){
			Ext.apply(rst,{type:data_pattern});
		}
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
				return new NumVdtor(_field);
			case "string":
				return new StrVdtor(_field);
			case "date":
			case "datetime":
				return new DateVdtor(_field);
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
			case "common_char2":
				var rst = new StrVdtor(_field);
				rst.format = new RegExp('^[a-z0-9_]+$', 'i');
				rst.formatType = 'COMMON_CHAR2';
				return rst;			
			case "email":
				var rst = new StrVdtor(_field);
				rst.format = new RegExp('^.+@.+$', '');
				rst.formatType = 'EMAIL';
				return rst;
		}
		//no such type,return default validator.
		return new AbsVdtor(_field);
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
		if(dom.disabled || dom.getAttribute("ignore")) return false;
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
		//格式化文本元素,span
		eles = document.getElementsByClassName("xdRichTextBox");		
		doms.push.apply(doms, eles);
		var rst = this.valid.apply(this, doms);
		return rst==null ? [] : (Array.isArray(rst) ? rst : [rst]);
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
		var v = $$F(this.el) || EleHelper.getValue(this.el);
		if(v==null || v.trim() == ''){
			if(ValidationHelper.isRequired(this.obj, this.el)){
				this.warning = this.warn("REQUIRED", "msg_info");
				return false;
			}
			return true;
		}
		if(this.obj['format']){
			eval("this.outerformat = " + this.obj['format'] + ";"); 
			if(this.obj['message']){
				eval('this.message = "' + this.obj['message'] + '";'); 
			}
			if(!this.outerformat.test(v)){
				if(this.message){
					this.warning += this.message;
					return false;
				}
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
		//var args = value ? [value] : [];
		var args = Array.prototype.slice.call(arguments, 2);
		var desc = this.obj[m_Vdcf.DESC] || this.el.getAttribute("elname",2) || this.el.name || this.el.id;
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
		if(type == 'INT' || type == 'INTEGER'){
			this.parseMethod = parseInt;
			this.format = new RegExp('^-?\\d+(e[\+-]?\\d+)?$', "i");
		}else{
			this.parseMethod = parseFloat;
			this.format = new RegExp('^-?\\d+(\\.\\d+)?(e[\+-]?\\d+)?$', "i");
		}
		if(!this._check()) return false;
	},
	method : function(){
		var v = this.parseMethod($$F(this.el)) || 0;
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
		var len = ($$F(this.el)||'').byteLength();
		if(this.obj['length_range']){
			var lengthRange = this.obj['length_range'].split(',');
			if(lengthRange[0] > len || len > lengthRange[1]){
				this.warning += this.warn("LENGTH_RANGE", "msg_info", lengthRange[0], lengthRange[1]);
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


var DateVdtor = defClass();
Object.extend(DateVdtor.prototype, AbsVdtor.prototype);
Object.extend(DateVdtor.prototype, {
	formatRegExp : /^(yy|yyyy)(-|\/)(m{1,2})(\2)(d{1,2})(\W+(h{1,2})(:)(M{1,2})((\8)(s{0,2}))?)?$/,
	dateRegExp : /^(\d{2}|\d{4})(-|\/)(\d{1,2})(\2)(\d{1,2})(\W+(\d{1,2})(:)(\d{1,2})((\8)(\d{0,2}))?)?$/,
	initialize : function(_field) {
		AbsVdtor.prototype.initialize.call(this, _field);
		if(!this._check()) return false;
	},
	method : function(){
		var sFormat = this.obj['date_format'];
		if(!sFormat){
			var type = this.obj['type'];
			if(type == 'date'){
				sFormat = "yyyy-mm-dd";
			}else if(type == 'datetime'){
				sFormat = "yyyy-mm-dd HH:MM:ss";
			}
		}
		var sTemp = sFormat.replace(/yy|mm|dd|hh|ss/gi, "t").replace(/m|d|h|s/ig, '\\d{1,2}').replace(/t/ig, '\\d{2}');
		var oRegExp = new RegExp("^"+sTemp+"$");
		var sValue = $$F(this.el) || '';
		if(!oRegExp.test(sValue)){
			this.warning = "没有匹配日期格式:" + sFormat;
			return false;
		}
		var matchs = sValue.match(this.dateRegExp);
		if(!matchs){
			this.warning = "没有匹配日期格式:" + sFormat;;   
			return false;
		}
		var year = parseInt(matchs[1], 10);
		var month = parseInt(matchs[3], 10);
		var day = parseInt(matchs[5], 10);

		var hour = parseInt(matchs[7], 10);
		var minute = parseInt(matchs[9], 10);
		var second = parseInt(matchs[12], 10);
		
		if(month < 1 || month > 12){
			this.warning = "月份应该为1到12的整数";   
			return false;
		}
		if (day < 1 || day > 31){
			this.warning = "每个月的天数应该为1到31的整数"; 
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
	getMessage : function(){
		var sFormat = this.obj['date_format'];
		if(!sFormat){
			var type = this.obj['type'];
			if(type == 'date'){
				sFormat = "yyyy-mm-dd";
			}else if(type == 'datetime'){
				sFormat = "yyyy-mm-dd HH:MM:ss";
			}
		}
		return String.format("日期格式为：{0}" ,sFormat);
	}
});

function _valid(ev){
	var el = this;
	var rst = ValidationHelper.valid(el);
	if(rst==null)return;
	if(window.bShowTitle)
		el.title = !rst.isValid ? rst.warning : rst.message;
	Element[rst.isValid?'removeClassName':'addClassName'](el, 'valid_error');
}
function initEvents(){
	var doms = [], tags = ["INPUT", "TEXTAREA", "SELECT"];
	for (var i = 0; i < tags.length; i++){
		var eles = document.body.getElementsByTagName(tags[i]);
		doms.push.apply(doms, $A(eles));
	}
	for(var i = 0; i < doms.length; i++){
		var dom = doms[i], f = (window.onValid||_valid).bind(dom);
		if(dom.getAttribute("validation", 2)==null)continue;
		Event.observe(dom, 'change', f);
		Event.observe(dom, 'blur', f);
		Event.observe(dom, 'focus', f);
	}

}
exCenter.onafterInitData(function(){
	var doms = [], tags = ["INPUT", "TEXTAREA", "SELECT"];
	for (var i = 0; i < tags.length; i++){
		var eles = document.body.getElementsByTagName(tags[i]);
		doms.push.apply(doms, $A(eles));
	}
	for(var i = 0; i < doms.length; i++){
		var dom = doms[i];
		var v = dom.getAttribute("validation", 2), pt, tmp, fmt;
		var pt = dom.getAttribute("pattern", 2);
		if(pt=='string' && (fmt = dom.getAttribute("xd:datafmt", 2))!=null){
			if(fmt.indexOf('"number"')!=-1)
				pt =  fmt.indexOf('numDigits:auto')!=-1? 'float' : 'integer';
		}
		if(v==null || v.trim().length<=0){
			if(pt==null || pt=='string')continue;
			dom.setAttribute("validation", 'type:\'' + pt + '\'');
		}else if(pt!=null && pt!='string'){
			if(v.indexOf('type:')!=-1){
				v = v.replace(/(^|,)type:[^,]*(,|$)/, '$1type:\'' + pt + '\'$2');
			}else{
				v = v + ',type:\'' + pt + '\'';
			}
			dom.setAttribute("validation", v);
		}
		(window.onValid||_valid).apply(dom, []);
	}
	initEvents();
});
function _validError(){
	//donothing
}
document.getElementsByClassName = function(cls, p) {
	if(p && p.getElementsByClassName) return p.getElementsByClassName(cls);
	var arr = ($(p) || document.body).getElementsByTagName('*');
	var rst = [];
	var regExp = new RegExp("(^|\\s)" + cls + "(\\s|$)");
	for(var i=0,n=arr.length;i<n;i++){
		if (arr[i].className.match(regExp))
			rst.push(arr[i]);
	}
	return rst;
}
function doValidAppendixFields(_msgs, _infos){
	var rtv = true;
	var fileAttachmentEls = document.getElementsByClassName("xdFileAttachment");
	rtv = rtv && collectValidInfo(fileAttachmentEls, _msgs, _infos);
	var inlinePictureEls = document.getElementsByClassName("xdInlinePicture");
	rtv = rtv && collectValidInfo(inlinePictureEls, _msgs, _infos);
	return rtv;
}
function collectValidInfo(appendixDoms, _msgs, _infos){
	var rtv = true;
	for(var i=0; i<appendixDoms.length; i++){
		var currDom = appendixDoms[i];
		var validationInfo = currDom.getAttribute("validation");
		if(validationInfo == null) return;
		eval("var validationInfo={" + validationInfo +"}");
		if(validationInfo['required'] == true){
			var tipDoms = document.getElementsByClassName('appendix_txt', currDom);
			var markDom = null;
			if(tipDoms[0] != null)
				markDom = tipDoms[0];
			else{
				tipDoms = currDom.childNodes;
				for(var j=0; j<tipDoms.length; j++){
					if((tipDoms[j].tagName).toUpperCase() != 'SPAN')continue;
					else if(tipDoms[j].getAttribute("text_body", 2) == 1)
						markDom = tipDoms[j];
				}
			}
			if(markDom != null && markDom.style.display == ''){
				var name = currDom.getAttribute("trs_field_name", 2);
				_msgs.push(name + "不能为空");
				_infos.push({
					id : currDom.id,
					isValid : false,
					message : name + "不能为空",
					warning : name + "不能为空"
				})
				rtv = rtv && false;
			}
			 
		}
	}
	return rtv;
}
exCenter.onbeforeSubmit(function(json){
	var rsts = ValidationHelper.validForm(document.body);
	var rtv = true, msgs = [], valid, infos = [];
	for(var i=0;i<rsts.length;i++){
		valid = rsts[i].isValid;
		rtv = rtv && valid;
		if(!valid){
			infos.push(rsts[i]);
			msgs.push(rsts[i].warning);
		}
	}
	rtv = rtv && doValidAppendixFields(msgs, infos);
	json.msgs = msgs;
	json.infos = infos;
	(window.onValidError || _validError)(json);
	return rtv;
});