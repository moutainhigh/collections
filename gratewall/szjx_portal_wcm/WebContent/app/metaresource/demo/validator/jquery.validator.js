/*
*	jquery 表单校验组件
*	auth:hdg1988@gmail.com
*	使用方式：在input和textarea元素中使用validate属性来进行校验，例如，我要求输入的字符串长度为10-100，则validate的值为validate="type:'string',maxLen:100,minLen:10,desc:'测试字符串',required:1"，这个校验组件除了支持字符串外，还支持数字和正则表达式等。目前已经有的正则在COMMON_REGEXP中，例如对输入的email进行校验可以使用validate = "type:'email',required:1,desc:'电子邮件'"
*
*/
(function($){
	$.validator = function(){
		var elClass = "validate_el";
		var elErrorClass = "validate_el_error";
		var infoClass = "validate_info";
		var rightClass = "validate_right";
		var errorClass = "validate_error";
		var requiredClass = "validate_required";
		var TEMPLATES ={
			"REQUIRED":"<span class='validate_required'>*</span>",
			"MESSAGE":"<span class='validate_info {0}'>{1}</span>"
		}
		var COMMON_REGEXP = {
			"int":/^[+-]{0,1}(0|([1-9]\d*))$/,//整数
			"num":/^[+-]{0,1}(0|([1-9]\d*))(\.\d+){0,1}$/,//数字，包括小数部分
			"email":/^(\w+\.)*\w+@(\w+\.)*\w+$/,//email
			"ip":/^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/,//ip
			"http":/^https{0,1}:\/\/\w+/ //http(s)连接
		}
		function validateDom(dom,o){
			var result = validateObj(dom.value,o);
			dowith_result(dom,result);
			return result;
		}
		function getValidateInfo(dom){
			var sVal = dom.getAttribute("validate");
			try{
				var o = eval("({"+sVal+"})");
				return o
			}catch(e){
				// TODO  需要进行提示
				alert("元素的validate属性格式不正确！"+"validate=["+sVal+"]");
				return {};
			}
		}
		function validateObj(s,o){
			var result = null;
			switch(o.type){
				case "string" :
					result = validateString(s,o);
					break;
				case "int" :
					result = validateInt(s,o);
					break;
				case "num" :
					result = validateNum(s,o);
					break;
				case "regexp" :
					result = validateRegExp(s,o);
					break;
				default :
					result = validateRegExp(s,o,o.type);
					break;
			}
			return result;
		}
		/** 验证字符串 */
		function validateString(s,o){
			var len = s.length;
			var result = {};
			if(o.required && len==0){
				result.msg = String.format("{0}不能为空",o.desc);
				result.r = false;
			}else if(!o.required && len==0){
				result.msg = "";
				result.r = true;
			}else if(o.maxLen && o.maxLen<len){
				result.msg = String.format("{0}大于最大长度{1}",o.desc,o.maxLen);
				result.r = false;
			}else if(o.minLen && o.minLen>len){// 小于最小长度
				result.msg = String.format("{0}小于最大长度{1}",o.desc,o.minLen);
				result.r = false;
			}else{
				result.msg =(o.minLen == undefined?"":String.format("{0}允许长度为大于{1}",o.desc,o.minLen))+(o.maxLen == undefined?"":String.format("{0}允许长度为小于{1}",o.desc,o.maxLen));
				result.r = true;
			}
			return result;
		}
		/** 验证整数 */
		function validateInt(s,o){
			var result = {};
			// 大于最大长度
			var msg = "";
			if(o.required && s.length==0){//必填，并且为空出错
				result.msg = String.format("{0}不能为空",o.desc);
				result.r = false;
			}else if(!o.required && s.length==0){//不必填，并且为空不出错
				result.msg = "";
				result.r = true;
			}else if(!COMMON_REGEXP["int"].test(s)){
				result.msg = String.format("{0}要求输入为整数，或者整数格式不正确",o.desc);
				result.r = false;
			}else if(o.max && o.max<s){
				result.msg = String.format("{0}大于允许最大值{1}",o.desc，o.max);
				result.r = false;
			}else if(o.min && o.min>s){// 小于最小长度
				result.msg = String.format("{0}大于允许最大值{1}",o.desc，o.min);
				result.r = false;
			}else{
				result.msg =(o.min == undefined?"":String.format("{0}数值的范围为大于{1}",o.desc,o.min))+(o.max == undefined?"":String.format("{0}数值的范围为小于{1}",o.desc,o.max));
				result.r = true;
			}
			return result;
		}
		/** 验证数字，包括整数和小数 */
		function validateNum(s,o){
			var result = {};
			// 大于最大长度
			var msg = "";
			if(o.required && s.length==0){//必填，并且为空出错
				result.msg = String.format("{0}不能为空",o.desc);
				result.r = false;
			}else if(!o.required && s.length==0){//不必填，并且为空不出错
				result.msg = "";
				result.r = true;
			}else if(!COMMON_REGEXP["num"].test(s)){
				result.msg = String.format("{0}要求输入为数字，或者数字格式不正确",o.desc)
				result.r = false;
			}else if(o.max && o.max<s){
				result.msg = String.format("{0}大于允许最大值{1}",o.desc，o.max);
				result.r = false;
			}else if(o.min && o.min>s){// 小于最小长度
				result.msg = String.format("{0}大于允许最大值{1}",o.desc，o.min);
				result.r = false;
			}else{
				result.msg (o.min == undefined?"":String.format("{0}数值的范围为大于{1}",o.desc,o.min))+(o.max == undefined?"":String.format("{0}数值的范围为小于{1}",o.desc,o.max));
				result.r = true;
			}
			return result;
		}
		/** 正则表达式进行匹配 */
		function validateRegExp(s,o,regName){
			var result = {};
			if(regName){
				o.format = o.format || COMMON_REGEXP[regName];
			}
			if(o.required && s.length==0){//必填，并且为空出错
				result.msg = String.format("{0}不能为空",o.desc);
				result.r = false;
			}else if(o.format && o.format.test(s)){
				result.r = true;
				result.msg = String.format("{0}格式符合要求",o.desc);
			}else{
				result.r = false;
				result.msg = String.format("{0}格式不符合要求",o.desc);
			}
			return result;
		}
		/** 处理验证结果 */
		function dowith_result(dom,r){
			var next = $(dom).next();
			var o = getValidateInfo(dom);
			var infoEl = null;
			if(o.tipId){// 如果指定tipId
				infoEl = $("#"+o.tipId);
			}else{
				infoEl = o.required?next.next():next;
			}
			infoEl.removeClass(r.r?errorClass:rightClass).addClass(r.r?rightClass:errorClass).html(r.msg);
			r.r?$(dom).removeClass(elErrorClass).addClass(elClass):$(dom).addClass(elErrorClass).removeClass(elClass);
		}
		/** 对每一个dom对象进行初始化，主要是事件*/
		function initDom(dom){
			var o = getValidateInfo(dom);
			$(dom).keyup(function(){
				validateDom(this,o);
			});
			$(dom).addClass(elClass);
			if(!o.tipId){// 如果没有指定tipId
				// 是否为必填项进行处理
				if(o.required){
					$(dom).after(String.format(TEMPLATES["MESSAGE"],"","")).after(TEMPLATES["REQUIRED"]);
				}else{
					$(dom).after(String.format(TEMPLATES["MESSAGE"],"",""));
				}
			}else{
				if(o.required){
					$(dom).after(TEMPLATES["REQUIRED"]);
				}
				$("#"+o.tipId).html(String.format(TEMPLATES["MESSAGE"],"",""));
			}
		}
		String.format = function(format){
			var args = Array.prototype.slice.call(arguments, 1);
			return format.replace(/\{(\d+)\}/g, function(m, i){
				return args[i];
			});
		}
		
		return {
			init:function(){
				// 获取所有需要验证的元素
				$("input[validate],textarea[validate]").each(function(){
					initDom(this);
				});
			},
			doValid:function(){
				var result = {};
				var inputString = (arguments[0]?"#"+arguments[0]+" ":"")+"input[validate]";
				var textAreaString = (arguments[0]?"#"+arguments[0]+" ":"")+"textarea[validate]";
				$(inputString+","+textAreaString).each(function(){
					result = validateDom(this,getValidateInfo(this));
					if(!result.r)
						return false;
				});
				return result.r;
			}
		}
	}();
})(jQuery);
$(function(){
	$.validator.init();
});