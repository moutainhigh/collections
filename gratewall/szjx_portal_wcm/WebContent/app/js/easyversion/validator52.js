Ext.ns('TRSValidator52', 'm_Valid52Info', 'm_Valid52Helper');
var initTRSValidator52 = function(){
	if(arguments.callee.inited) return;
	arguments.callee.inited = true;
	Ext.applyIf(m_Valid52Info, {
		pre : wcm.LANG.WCM52_ALERT_30 || "您输入的",
		required : wcm.LANG.WCM52_ALERT_67 || "[{0}]为空,此字段为必填!",
		number : wcm.LANG.WCM52_ALERT_96 || "[{0}]不是整型数!",
		'double' : wcm.LANG.WCM52_ALERT_97 || "[{0}]不是浮点数!",
		scalemore	:	wcm.LANG.WCM52_ALERT_98 || "[{0}]小数位数超过[18]!",
		scaleexceed	:	wcm.LANG.WCM52_ALERT_99 || "[{0}]小数位数超过[{1}]!",
		max : wcm.LANG.WCM52_ALERT_68 || "[{0}]大于最大值 [{1}]!",
		min : wcm.LANG.WCM52_ALERT_69 || "[{0}]小于最小值 [{1}]!",
		max_len : wcm.LANG.WCM52_ALERT_70 || "[{0}]长度大于最大长度 [{1}](注:每个汉字长度为2)!",
		min_len : wcm.LANG.WCM52_ALERT_71 || "[{0}]长度小于最小长度 [{1}](注:每个汉字长度为2)!",
		email : wcm.LANG.WCM52_ALERT_72 || "[{0}]不符合Email格式,如xxx@xxx.com!",
		url : wcm.LANG.WCM52_ALERT_73 || "[{0}]不符合URL格式,如http://www.trs.com.cn!",
		ip : wcm.LANG.WCM52_ALERT_74 || "[{0}]不符合ip格式,如192.9.200.22!",
		common_char : wcm.LANG.WCM52_ALERT_75 || "[{0}]不符合格式,必须为字母,下划线或者数字所组成,首字母需为字母!",
		common_char2 : wcm.LANG.WCM52_ALERT_76 || "[{0}]不符合格式,必须为字母,下划线或者数字所组成!",
		checkdbkeywords : wcm.LANG.WCM52_ALERT_77 || "[{0}]中含有数据库关键字,不合法!",
		info : function(type, a, b, c, d){
			return this.pre + String.format(this[type.toLowerCase()], a, b, c, d);
		}
	});
};
var m_Valid52Re = {
	email : /^.+@.+$/g,
	url : /^(http|https|ftp|mtsp):\/\/.+$/i,
	ip : /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/,
	common_char : /^[a-z][a-z0-9_]*$/i,
	common_char2 : /^[a-z0-9_]+$/i,
	'double' : /^-?\d+(\.\d+)?(e[\+-]?\d+)?$/,
	number : /^-?\d+(e[\+-]?\d+)?$/,
	required : /^.+$/
};
function formEles(_fm){
	var fm = $(_fm), arr = ['input', 'textarea', 'select'], els = [];
	for(var i=0,n=arr.length;i<n;i++){
		var tags = fm.getElementsByTagName(arr[i]);
		for (var j = 0; j < tags.length; j++){
			var tag = tags[j];
			if(!tag.name || tag.getAttribute("ignore"))continue;
			els.push(tags[j]);
		}
	}
	return els;
}
TRSValidator52 = {
	validatorForm : function(fm, bFailStop, filter){
		initTRSValidator52();
		var arEls = formEles(fm), rst = {valid:true, einfos:[]}, o, e;
		for(var i = 0;i<arEls.length;i++){
			e = arEls[i];
			if(!e.getAttribute("pattern"))continue;
			if(filter && filter(e))continue;
			e.elname =e.getAttribute("elname") || e.getAttribute("name");
			if(!(o = this.gv(e)))continue;
			if(o.validate())continue;
			rst.valid = false;
			rst.einfos.push(o.einfo);
			if(!rst.fstEle)rst.fstEle = e;
			if(bFailStop)return rst;
		}
		return rst;
	},
	gv : function(oEle){
		var pattern = oEle.getAttribute("pattern").toLowerCase(), fn = this.vos[pattern];
		if(fn)return fn(oEle);
		return null;
	},
	vos : m_Valid52Helper
};
Ext.apply(m_Valid52Helper, {
	_format : function(ele, attr, v){
		if(v == null)return true;
		if(v=='')return true;
		if(!v.match(m_Valid52Re[attr])){
			this.einfo = m_Valid52Info.info(attr, ele.elname);
			return false;
		}
		return true;
	},
	_validator : function(oEle, attrs, helper){
		var v = $F(oEle) || '';
		for(var i=0,n=attrs.length;i<n;i++){
			if(!oEle.getAttribute(attrs[i]))continue;
			var fn = helper[attrs[i]] || m_Valid52Helper._format;
			if(fn && !fn.call(this, oEle, attrs[i], v))return false;
		}
		return true;
	},
	string : function(oEle){
		if(!oEle)return null;
		var abc = function(ele, attr, bMax){
			var nMLen = parseInt(ele.getAttribute(attr), 10);
			var nLen = ($F(ele)||'').byteLength();
			if((bMax && nLen>nMLen) || (!bMax && nLen<nMLen)){
				this.einfo = m_Valid52Info.info(attr, oEle.elname, nMLen);
				return false;
			}
			return true; 
		}
		var helper = {
			max_len : function(ele, attr, v){
				return abc.call(this, ele, attr, true);
			},
			min_len : function(ele, attr, v){
				return abc.call(this, ele, attr, false);
			}
		};
		return {
			validate : function(){
				var attrs = ['required', 'min_len', 'max_len', 'email', 'url', 'ip', 'common_char', 'common_char2'];
				return m_Valid52Helper._validator.call(this, oEle, attrs, helper);
			}
		};
	},
	number : function(oEle){
		if(!oEle)return null;
		var abc = function(ele, attr, v, bMax){
			var sMethod = oEle.getAttribute("pattern").toLowerCase() == "number" ? parseInt : parseFloat;
			var nMNum = sMethod(ele.getAttribute(attr), 10);
			var nNum = sMethod(v, 10);
			if((bMax && nNum>nMNum) || (!bMax && nNum<nMNum)){
				this.einfo = m_Valid52Info.info(attr, oEle.elname, nMNum);
				return false;
			}
			return true; 
		}
		var helper = {
			max : function(ele, attr, v){
				return abc.call(this, ele, attr, v, true);
			},
			min : function(ele, attr, v){
				return abc.call(this, ele, attr, v, false);
			},
			scale : function(ele, attr, v){
				if(v.indexOf(".") > 0){
					var decimalPart = v.substr(v.indexOf(".") + 1);
					if(decimalPart && decimalPart.length > 0){
						if(decimalPart.length > 18){
							this.einfo = m_Valid52Info.info("scalemore",oEle.elname);
							return false;
						}
						if(decimalPart.length > ele.getAttribute(attr)){
							this.einfo = m_Valid52Info.info("scaleexceed",oEle.elname,ele.getAttribute(attr));
							return false;
						}
					}
				}
				return true;
			}
		};
		return {
			validate : function(){
				var attrs = ['required', oEle.getAttribute("pattern").toLowerCase(), 'min', 'max', 'scale'];
				return m_Valid52Helper._validator.call(this, oEle, attrs, helper);
			}
		};
	},
	'double' : function(oEle){
		return m_Valid52Helper.number(oEle);
	}
});