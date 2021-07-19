/*!
 * Copyright 2011, trs
 * 
 *
 * Date: 2011-1-26
 */
$(document).ready(function(){
	if($.browser.msie&&($.browser.version==6.0)){
		$("body").addClass("jquery-ie6");
	}else{
		$("body").addClass("jquery-strict");
	}
	String.format = function(format){
		var args = Array.prototype.slice.call(arguments, 1);
		return format.replace(/\{(\d+)\}/g, function(m, i){
			return args[i];
		});
	}
	String.prototype.trim = function(){
		return this.replace(/^\s*(.*?)\s*$/,"$1");
	}
});
function $El(el){
	if (typeof el == 'string')
		el = document.getElementById(el) || document.getElementsByName(el)[0];
	return el;
}
$.getFormElements = function(form){
	form = $El(form);
	var rst = [], tags = ['input', 'select', 'textarea'], arr;
	for (var i=0;i<tags.length;i++) {
		arr = form.getElementsByTagName(tags[i]);
		for (var j = 0; j < arr.length; j++){
			rst.push(arr[j]);
		}
	}
	return rst;
}
$.getParameter = function(_sName, _sQuery){
	if(!_sName)return '';
	var query = _sQuery || location.search;
	if(!query)return '';
	var arr = query.substring(1).split('&');
	_sName = _sName.toUpperCase();
	for (var i=0,n=arr.length; i<n; i++){
		if(arr[i].toUpperCase().indexOf(_sName+'=')==0){
			return arr[i].substring(_sName.length + 1);
		}
	}
	return '';
}
function parseXml(xml){
	var root = xml.documentElement;
	if(root == null) return null;
	var vReturn = {}, json = parseElement(root);
	vReturn[root.nodeName.toUpperCase()] = json;
	return vReturn;
}
function parseElement(ele){
	if(ele == null)return null;
	var json = {}, attrs = ele.attributes, hasAttr = false, hasValue = false, hasNode = false;
	for(var i=0;attrs && i<attrs.length;i++){
		hasAttr = true;
		json[attrs[i].nodeName.toUpperCase()] = attrs[i].nodeValue.trim();
	}
	var childs = ele.childNodes;
	for(var i=0;childs&&i<childs.length;i++){
		var ndn = childs[i].nodeName.toUpperCase();
		switch(ndn){
			case '#TEXT':
			case '#CDATA-SECTION':
				hasValue = true;
				json.NODEVALUE = childs[i].nodeValue.trim();
				break;
			case '#COMMENT':
				break;
			default:
				hasNode = true;
				var a = json[ndn], b = parseElement(childs[i]);
				if(!a)json[ndn] = b;
				else if(Array.isArray(a))a.push(b);
				else json[ndn] = [a, b];
		}
	}
	if(!hasAttr && !hasNode){
		if(!hasValue)return '';
		return json.NODEVALUE;
	}
	return json;
}
$.fn.valueToObj = function(){
	var data = {};
	if (this.length === 0) {
		return data;
	}
	var els = this.find("input,textarea,select");
	els.each(function(){
		// TODO 过滤 disabled的元素，对radio和checkbox进行处理
		var el = $(this),
			sName = el.attr("name"),
			sValue = el.val(),
			checkAndRadio={checkbox:1,radio:1};
		if(!sName) return;
		data[sName] = "";
		//1 如果是checkbox和radio
		if(this.checked && this.tagName =="INPUT" && checkAndRadio[$(this).attr("type")]){
			data[sName]?data[sName]+=","+sValue:data[sName]=sValue;
		}else if(!checkAndRadio[$(this).attr("type")]){//1 即不是checkbox也不是radio
			data[sName] = sValue;
		}
	});
	return data
};

$.wcmAjax = function(ServiceId,methodName,data,success){
	// TODO  添加调试信息 例如发送请求的时间等等
	// 可以提交某个元素 (组装里面的input,select,textarea值)hide 还是要，disabled的不要
	if(typeof data == "string" && /^[^=]+$/.test(data)){//如果提交的是某个元素
		// 收集参数
		data = $(data).valueToObj();
	}else if(typeof data == "string"){
		data = toJson(data);
	}else if(data.nodeType){
		data = $(data).valueToObj();
	}
	$.ajax({
		url : "/wcm/center.do",
		type : "POST",
		data : $.extend(data,{
			ServiceId : ServiceId,
			methodName : methodName
		}),
		dataFilter : function(data, type){
			// 需要对服务器端返回的结果进行转换
			return parseXml(data);
		},
		success : success
	});
}
