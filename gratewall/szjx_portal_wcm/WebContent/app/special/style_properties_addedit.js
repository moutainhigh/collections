window.m_cbCfg = {
	btns : [
		{
			text : '确定',
			id:'btnSave',
			cmd : function(){
				if(!doValid()){
					return false;
				}
				this.notify(getData(true));
			}
		},
		{
			extraCls : 'wcm-btn-close',
			text : '取消'
		}
	]
};		


/**
*CrashBoarder自动调用的初始化函数；
*根据params这个json对象的属性，初始化页面上的对应form元素；
*元素的id为属性名称加上trs-前缀；
*这样做的目的是为了支持后期扩展，即只需要在页面上添加相应的form元素；
*将自动将该form元素对应的样式提取出来及初始化。
*/
function init(params){
	for(var styleName in params){
		var sValue = params[styleName];
		if(!Ext.isString(sValue)) continue;
		//不用$主要还是为了兼容checkbox,radio的情况
		var doms = document.getElementsByName('trs-' + styleName);
		if(doms.length <= 0) continue;
		for(var index = 0; index < doms.length; index++){
			var dom = doms[index];
			if(dom.getAttribute("type") == 'radio' || dom.getAttribute("type") == 'checkbox'){
				if(dom.value == sValue){
					dom.checked = true;
					dom.setAttribute("data-init-value", sValue);
				}
			}else{
				dom.value = sValue;
				dom.setAttribute("data-init-value", sValue);
				initBorderStyleEl(dom,sValue);
			}
		}
	}
	ValidationHelper.initValidation();
}
/*初始化边框设置中的，线形下拉列表和颜色下拉列表*/
function initBorderStyleEl(dom,_value){
	if(dom.tagName != "SELECT") return;
	if(dom.getAttribute('value') == _value) return;
	var domId = dom.id;
	if(domId == 'trs-border-style'){
		var borderStyleEl = $('replaceEl');
		if(_value != 'none'){
			borderStyleEl.style.borderBottomStyle = _value;
			dom.options[0].value = _value;
			dom.options[0].selected = _value;
			borderStyleEl.setAttribute('value',_value);
			borderStyleEl.className = "border_style_opt";
			borderStyleEl.innerHTML = '&nbsp';
		}
	}
	if(domId == 'trs-border-color'){
		if(_value != '#000000'){
			dom.style.backgroundColor = _value;
			dom.options[0].selected = _value;
			dom.options[0].value = _value;
		}
	}
}
function doValid(){
	if(!ValidationHelper.doValid("data")){
		ValidationHelper.failureRPCCallBack();
		return false;
	}else{
		return true;
	}
}

/**
*单击确定按钮时执行的函数；
*提取form元素下，所有id以trs-前缀开始的元素；
*参数excludeNoChange标识提取数据过程中，是否排除没有修改的元素，默认都提取；
*返回一个json对象，changed标识form元素的值是否发生改变；
*data标识提取的所有数据；
*/
function getData(excludeNoChange){
	var changed = false;
	var data = {};
	var doms = $('data').elements;
	for(var index=0; index < doms.length; index++){
		var dom = doms[index];
		if(!dom.name.startsWith('trs-')) continue;
		if(dom.getAttribute('type') == 'radio' || dom.getAttribute('type') == 'checkbox'){
			if(!dom.checked) continue;
		}
		if(excludeNoChange){
			var sInitValue = dom.getAttribute('data-init-value');
			if(sInitValue == undefined) sInitValue = "";
			if(sInitValue  == dom.value) continue;
		}
		var sName = dom.name.substring('trs-'.length);
		data[sName] = dom.value;
		changed = true;
	}
	return {changed : changed, data : data};
}

/**
*改变一个边距的值时，如果其他的边距没有任何输入值，则设置与当前输入的边距的值相同
*/
function changeOtherValue(dom){
	var locations = ["top", "right", "bottom", "left"];
	var sId = dom.id;
	if(sId.indexOf("-") == -1){
		return;
	}

	var sIdInfos = sId.split("-");
	var sLocation = sIdInfos[1];
	var sValue = dom.value;
	for(var i=0; i< locations.length; i++){
		var sTemp = locations[i];
		if(sTemp == sLocation)
			continue;
		
		var oDom = document.getElementById(sIdInfos[0] + "-" + sTemp);
		if(!oDom)
			continue;
		var sDomValue = oDom.value;
		if(sDomValue != "")
			continue;
		oDom.value = sValue;
	}
}