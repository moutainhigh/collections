var EventUtil = {
	addHandler : function(element,type,handler){
		if(element.addEventListener) {
			element.addEventListener(type,handler,false);
		}else if(element.attachEvent) {
			element.attachEvent("on" + type,handler);
		}else {
			element["on" + type] = handler;
		}
	},
	removeHandler : function(element,type,handler){
		if(element.removeEventListener) {
			element.removeEventListener(type,handler,false);
		}else if(element.detachEvent) {
			element.detachEvent("on" + type,handler);
		}else {
			element["on" + type] = null;
		}
	},
	getAttributes : function(){
		var temp = "";
		var checkBoxTypes = ["d_kee","d_del","d_add"];
		var inputTextType = "d_inp";
		for (var j=0; j<checkBoxTypes.length; j++) {
			var tempArray = DefaultFormat_ControlPanel[checkBoxTypes[j]];
			for (var i=0; i<tempArray.length; i++) {
				var tempKey = tempArray[i];
				var tempValue = $(tempKey).checked ? 1 : 0; 
				temp += tempKey + ":" + tempValue + ";";
			}
		}
		//input框处理（段前、段后、行距）
		for (var i=0; i<DefaultFormat_ControlPanel[inputTextType].length; i++) {
			var tempKey = DefaultFormat_ControlPanel[inputTextType][i];
			var tempValueKey = "v" + tempKey.substring(1);
			var tempValue = $(tempValueKey).value;
			if(!tempValue) {
				tempValue = 1;
			}
			if($(tempKey).checked) {
				temp += tempKey + ":" + 1 + ";";
				temp += tempValueKey + ":" + tempValue + ";";
			}else {
				temp += tempKey + ":" + 0 + ";";
				temp += tempValueKey + ":" + tempValue + ";";
			}
		}
		//字体
		var bFontName = DefaultFormat_ControlPanel["d_sel"]["d_selfontname"];
		if(bFontName && $("d_selfontname").value !="") {
			temp += "d_selfontname:" + $("d_selfontname").value + ";";
		}
		//字号
		var bFontSize = DefaultFormat_ControlPanel["d_sel"]["d_selfontsize"];
		if(bFontSize && $("d_selfontsize").value !="") {
			temp += "d_selfontsize:" + $("d_selfontsize").value + ";";
		}
		return temp;
	}
}
function formPost(){
	if(!do_validation("inpmargintop")) {
		alert("[段前值]设置参数不合法");
		return false;
	}
	if(!do_validation("inpmarginbottom")) {
		alert("[段后值]设置参数不合法");
		return false;
	}
	if(!do_validation("inplineheight")) {
		alert("[行距值]设置参数不合法");
		return false;
	}
	$('isForWord').value = $('defautlFormat_forword_checked').checked ? "1" : "0";
	$('sysStatus').value = $('defautlFormat_status_checked').checked ? "1" : "0";
	//关闭系统级默认排版
	if($('defautlFormat_status_checked').checked) {
		$('postData').value = EventUtil.getAttributes();
	}else {
		$('postData').value = "";
	}
	$('frm_check_value').submit();
}
/*
*根据JSON配置，按照DefaultFormat_ControlPanel存储规则，获取当前选项是否显示的值
*(仅限字体和字号使用)
*/
function getBoolean(_name){
	var classvalue = _name.substr(0,5);
	return (DefaultFormat_ControlPanel[classvalue][_name]>0) ? true : false;
}
/*
*INIT
*/
var theDefaultConfig = {//默认排版显示默认值
	defaultAttributes : ""
};
function init(sAttributes){
	//初始化该栏目或站点的默认排版配置
	var oAttributes = {};
	if(sAttributes == "" || sAttributes == null) {
		sAttributes = theDefaultConfig.defaultAttributes;
	}
	var arrAttributes = sAttributes.split(";");
	for (var i=0; i<arrAttributes.length; i++) {
		var attribute = arrAttributes[i];
		var attributeKey = attribute.split(":")[0];
		var attributeValue = attribute.split(":")[1];
		oAttributes[attributeKey] = attributeValue;
	}
	theDefaultConfig.sAttributes = oAttributes;
	/*
	*theDefaultConfig.sAttributes.KEY为当前系统为默认排版设置的值
	*判断 (theDefaultConfig.sAttributes && theDefaultConfig.sAttributes.KEY)
	*/
	//初始化按钮
	initCheckboxInput();
	//为"去除所有属性"添加title
	if($('_delallattr')) {
		var delallattrLang = "去除所有属性包括去除样式、事件以及src、href、type、name、value等";
		$('_delallattr').setAttribute("title",delallattrLang);
	}
	initTextInput();
	initSelect();
	//开关按钮
	if(oAttributes['status'] && oAttributes['status'] =="0") {
		$('defautlFormat_status_nochecked').checked = true;
	}
	if(oAttributes['isForWord'] && oAttributes['isForWord'] =="1") {
		$('defautlFormat_forword_checked').checked = true;
	}
	//点击去除所有属性，在界面上的对所有去除属性的单选框选定
	var aboutAllDelCheck = function () {
		var clickInput = $("d_delallattr");
		if (clickInput.checked) {
			$("d_delstyle").checked = true;
			$("d_delon").checked = true;
			$("d_delstyle").disabled = true;
			$("d_delon").disabled = true;
		} else {
			$("d_delstyle").disabled = false;
			$("d_delon").disabled = false;
		}
	}
	//点击保留所有属性，在界面上的对所有保留属性的单选框选定
	var aboutAllKeepCheck = function() {
		var clickInput = $("d_keepall");
		if (clickInput.checked) {
			$("d_keepul").checked = true;
			$("d_keeptable").checked = true;
			$("d_keepimg").checked = true;
			$("d_keepobject").checked = true;
			$("d_keepa").checked = true;
			$("d_keepul").disabled = true;
			$("d_keeptable").disabled = true;
			$("d_keepimg").disabled = true;
			$("d_keepobject").disabled = true;
			$("d_keepa").disabled = true;
		} else {
			$("d_keepul").disabled = false;
			$("d_keeptable").disabled = false;
			$("d_keepimg").disabled = false;
			$("d_keepobject").disabled = false;
			$("d_keepa").disabled = false;
		}
	}
	//关闭状态，按钮置灰
	var displayForChecked = function(){
		if($("defautlFormat_status_nochecked").checked) {
			for (var i=0; i<DefaultFormat_ControlPanel["d_kee"].length; i++) {
				var kee_checked = DefaultFormat_ControlPanel["d_kee"][i];
				$(kee_checked).disabled = true;
			}
			for (var j=0; j<DefaultFormat_ControlPanel["d_del"].length; j++) {
				var del_checked = DefaultFormat_ControlPanel["d_del"][j];
				$(del_checked).disabled = true;
			}
			for (var k=0; k<DefaultFormat_ControlPanel["d_add"].length; k++) {
				var add_checked = DefaultFormat_ControlPanel["d_add"][k];
				$(add_checked).disabled = true;
			}
			for (var z=0; z<DefaultFormat_ControlPanel["d_inp"].length; z++) {
				var inp_checked = DefaultFormat_ControlPanel["d_inp"][z];
				$(inp_checked).disabled = true;
			}
			//除checkbox外的其他
			$("v_inpmargintop").disabled = true;
			$("v_inpmarginbottom").disabled = true;
			$("v_inplineheight").disabled = true;
			$("defautlFormat_forword").disabled = true;
			$("d_selfontname").disabled = true;
			$("d_selfontsize").disabled = true;
		}
		if($("defautlFormat_status_checked").checked) {
			for (var i=0; i<DefaultFormat_ControlPanel["d_kee"].length; i++) {
				var kee_checked = DefaultFormat_ControlPanel["d_kee"][i];
				$(kee_checked).disabled = false;
			}
			for (var j=0; j<DefaultFormat_ControlPanel["d_del"].length; j++) {
				var del_checked = DefaultFormat_ControlPanel["d_del"][j];
				$(del_checked).disabled = false;
			}
			for (var k=0; k<DefaultFormat_ControlPanel["d_add"].length; k++) {
				var add_checked = DefaultFormat_ControlPanel["d_add"][k];
				$(add_checked).disabled = false;
			}
			for (var z=0; z<DefaultFormat_ControlPanel["d_inp"].length; z++) {
				var inp_checked = DefaultFormat_ControlPanel["d_inp"][z];
				$(inp_checked).disabled = false;
			}
			//除checkbox外的其他
			$("v_inpmargintop").disabled = false;
			$("v_inpmarginbottom").disabled = false;
			$("v_inplineheight").disabled = false;
			$("defautlFormat_forword").disabled = false;
			$("d_selfontname").disabled = false;
			$("d_selfontsize").disabled = false;
			
			//开启后设置默认值
			if(!$("d_keepall").checked && !$("d_keepul").checked && !$("d_keeptable").checked && !$("d_keepimg").checked && !$("d_keepobject").checked && !$("d_keepa").checked) {
				$("d_keepall").checked = true;
				$("d_keepul").checked = true;
				$("d_keeptable").checked = true;
				$("d_keepimg").checked = true;
				$("d_keepobject").checked = true;
				$("d_keepa").checked = true;
				$("d_delline").checked = true;
				$("d_delspace").checked = true;
				$("d_delhidden").checked = true;
				$("d_delallattr").checked = false;
				$("d_delstyle").checked = true;
				$("d_delon").checked = true;

				$("d_addindent").checked = false;
				$("d_addjustify").checked = true;
				$("d_addbr2p").checked = true;
				$("d_addtablebc").checked = false;
				
				$("d_inpmargintop").checked = false;
				$("d_inpmarginbottom").checked = false;
				$("d_inplineheight").checked = true;
				$("v_inpmargintop").value = '5px';
				$("v_inpmarginbottom").value = '5px';
				$("v_inplineheight").value = '1';
				$("d_selfontname").value = "宋体";
				$("d_selfontsize").value = "12pt";
			}
			//去除和保留选项的生效
			if($("d_keepall").checked) {
				aboutAllKeepCheck();
			}
			if($("d_delallattr").checked) {
				aboutAllDelCheck();
			}
		}
	}
	//handler();
	aboutAllDelCheck();aboutAllKeepCheck();
	displayForChecked();
	/*
	EventUtil.addHandler(element_mt,"click",handler);
	EventUtil.addHandler(element_mtv,"change",handler);
	EventUtil.addHandler(element_mb,"click",handler);
	EventUtil.addHandler(element_mbv,"change",handler);
	EventUtil.addHandler(element_lh,"click",handler);
	EventUtil.addHandler(element_lhv,"change",handler);
	EventUtil.addHandler(element_fontSize,"click",handler);
	EventUtil.addHandler(element_fontName,"click",handler);
	*/
	var element_keepAll = $("d_keepall");
	var element_delAll = $("d_delallattr");
	EventUtil.addHandler(element_keepAll,"click",aboutAllKeepCheck);
	EventUtil.addHandler(element_delAll,"click",aboutAllDelCheck);
	
	var element_divStatus = $("defautlFormat_status");
	EventUtil.addHandler(element_divStatus,"click",displayForChecked);

}
/*
*初始化复选框界面，复选框父容器id为defaultSelected_checkbox
*/
function initCheckboxInput(){
	configureFactor("d_kee","defaultSelected_checkbox_kee");
	configureFactor("d_del","defaultSelected_checkbox_del");
	configureFactor("d_add","defaultSelected_checkbox_add");
}
/*
*初始化输入框界面，输入框父容器id为defaultSelected_text
*/
function initTextInput(){
	configureFactor("d_inp","defaultSelected_text_inp");
}
/*
*初始化字体、字号界面，该父容器id为defaultSelected_select
*/
function initSelect(){
	var bFontName = getBoolean("d_selfontname");
	var bFontSize = getBoolean("d_selfontsize");
	if(bFontName) {
		var cp = "";
		for (var k = 0; k < lang["FontNameItem"].length; k++) {
			cp += "<option value='" + lang["FontNameItem"][k] + "'";
			cp += ">" + lang["FontNameItem"][k] + "</option>";
		}
		$("div_addfontfamily").innerHTML = "<select id=d_selfontname size=1 style='width:100px'><option selected>" + lang["FontName"] + "</option>" + cp + "</select>";
	}
	if(bFontSize) {
		var cp = "";
		for (var k = 0; k < lang["FontSizeItem"].length; k++) {
			cp += "<option value='" + lang["FontSizeItem"][k][0] + "'";
			cp += ">" + lang["FontSizeItem"][k][1] + "</option>";
		}
		$("div_addfontsize").innerHTML = "<select id=d_selfontsize size=1 style='width:100px'><option selected>" + lang["FontSize"] + "</option>" + cp + "</select>";
	}
	valueAttributeGet("d_selfontname");
	valueAttributeGet("d_selfontsize");

}
/*
*工厂模式创建配置选项
*/
function configureFactor(type,parentId){
	var arrInitValue = DefaultFormat_ControlPanel[type];
	var configureKey = null;
	var configureName = null;
	var parentBox = document.getElementById(parentId);
	//className分组参数
	var k = 0;
	for (var i=0; i<arrInitValue.length; i++) {
		configureKey = arrInitValue[i];
		configureName = DefaultFormat_Lang[configureKey];
		var div_box = document.createElement("DIV");
		div_box.id = configureKey.substring(2);
		//div_box.style.background = "#FFFF00";
		if(type=="d_del" || type=="d_add" || type=="d_kee") {
			//className分组逻辑
			if(i>0) {
				k++;
				if(k>1) {
					k = 0;
				}
			}
			div_box.className = "Class_"+k;
			//
			div_box.innerHTML=	"<input type=\"checkbox\" name=\"" + configureKey +"\" id=\"" + configureKey + "\"><label for=\""+configureKey+"\"><span id=\""+ configureKey.substring(1) +"\">" + configureName + "</span>";
			parentBox.appendChild(div_box);
			//补齐div布局
			if(i==arrInitValue.length-1 && k==0) {
				var div_black_box = document.createElement("DIV");
				div_black_box.className = "Class_1";
				div_black_box.innerHTML = "&nbsp;";
				parentBox.appendChild(div_black_box);
			}
			//读取checkbox的cookie值————后期调整为读取数据库中保存的值
			checkAttributeGet(configureKey);
		}
		if(type=="d_inp") {
			div_box.innerHTML=	"<input type=\"checkbox\" name=\"" + configureKey +"\" id=\"" + configureKey + "\"><label for=\""+configureKey+"\"><span>" + configureName + "</span><input size=\"3\" type=\"text\" name=\"v_"+ configureKey.substring(2) +"\" id=\"v_"+ configureKey.substring(2) +"\">";
			parentBox.appendChild(div_box);
			//读取输入框的cookie值————后期调整为读取数据库中保存的值
			valueAttributeGet(configureKey);
			checkAttributeGet(configureKey);
		}
	}
}
//----------------------------------------OK相关逻辑------------------------------------------------------
/*
*选中配置后执行的逻辑
*/
function $toQueryStr(_oParams){
	var arrParams = _oParams || {};
	var rst = [], value;
	for (var param in arrParams){
		value = arrParams[param];
		rst.push(param + '=' + encodeURIComponent(value));
	}
	return rst.join('&');
}
function do_validation(_id){
	var regNum = /^\d*(\.\d+)?(px|pt|em|cm)?$/;
	if($("d_"+_id).checked && !regNum.test($("v_"+_id).value)) {
		$("v_"+_id).focus();
		$("v_"+_id).select();
		return false;
	}
	return true;
}
function myDo(){
	//前端数据校验
	if(!do_validation("inpmargintop")) {
		alert("[段前值]设置参数不合法");
		return false;
	}
	if(!do_validation("inpmarginbottom")) {
		alert("[段后值]设置参数不合法");
		return false;
	}
	if(!do_validation("inplineheight")) {
		alert("[行距值]设置参数不合法");
		return false;
	}
	var temp = EventUtil.getAttributes();
	//在setEditorStyle.jsp页面保存css文件到应用目录下
	var oCurrStyle = getCssStyle(temp);
	var strCurrStyle = transObj2Str(oCurrStyle);
	var text = getStyle(oCurrStyle,strCurrStyle);
	var oData = {
		cssText : text,
		ObjType : getParameter('ObjType'),
		ObjId   : getParameter('ObjId')
	}
	var sURL = "setEditorStyle.jsp";
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.JspRequest(
		sURL,
		oData,  true,
		function(transport, json){
			
		}
	);
	//END保存
	var oPostData = {
		ObjType		:	getParameter('ObjType'),
		ObjId		:	getParameter('ObjId'),
		Status		:	$("defautlFormat_status_checked").checked,
		Inherit		:	$("defautlFormat_inherit_checked").checked ? 1 : 0,//1:使用继承设置;0:使用自身设置
		ForWord		:	$("defautlFormat_forword_checked").checked,
		Attribute	:	temp
	};
	var BasicDataHelper = new com.trs.web2frame.BasicDataHelper();
	BasicDataHelper.call("wcm6_defaultformat","saveDefaultFormat",oPostData,true,function(_transport,_json){
		if(_transport.status == 200) {
			//
		}
		FloatPanel.close();	
	});
	return false;
}
function getCssStyle(_attribute){
	var oStyle = {};
	var arrAttributes = _attribute.split(";");
	for (var i=0; i<arrAttributes.length; i++) {
		var attribute = arrAttributes[i];
		var attKey = attribute.split(":")[0];
		var attValue = attribute.split(":")[1];
		oStyle[attKey] = attValue;
	}
	
	var oCurrStyle = {};
	//单线表格
	if(oStyle["d_addtablebc"] == "1") {
		oCurrStyle[".TRS_Editor TABLE"]={
			"border-collapse":"collapse",
			"border":"1px solid black",
			"font-family" : oStyle["d_selfontname"] ? oStyle["d_selfontname"] : "",
			"font-size" : oStyle["d_selfontsize"] ? oStyle["d_selfontsize"] : ""
		}
	}else {
		oCurrStyle[".TRS_Editor TABLE"]={
			"font-family" : oStyle["d_selfontname"] ? oStyle["d_selfontname"] : "",
			"font-size" : oStyle["d_selfontsize"] ? oStyle["d_selfontsize"] : ""
		}
	}
	oCurrStyle[".TRS_Editor"] =  {
		"font-family" : oStyle["d_selfontname"] ? oStyle["d_selfontname"] : "",
		"font-size" : oStyle["d_selfontsize"] ? oStyle["d_selfontsize"] : ""
	}
	oCurrStyle[".TRS_Editor P"] = {
		"font-family" : oStyle["d_selfontname"] ? oStyle["d_selfontname"] : "",
		"font-size" : oStyle["d_selfontsize"] ? oStyle["d_selfontsize"] : "",
		"margin-top" : $("d_inpmargintop").checked ? oStyle["v_inpmargintop"] : 0,
		"margin-bottom" : $("d_inpmarginbottom").checked ? oStyle["v_inpmarginbottom"] : 0
	}
	//两端对齐
	if(oStyle["d_addjustify"] == "1") {
		oCurrStyle[".TRS_Editor P"]["align"] = "justify";
	}
	oCurrStyle[".TRS_Editor P"]["line-height"] = $("d_inplineheight").checked ? oStyle["v_inplineheight"] : 1;

	var quoteEles = ["H1","H2","H3","H4","H5","H6","HR","BLOCKQUOTE","DL","DD","DT","OL","UL","LI","PRE","CODE","TEXTAREA","SELECT","CITE","PRE","CENTER","TABLE","DIV","FORM","FIELDSET","LEGEND","SELECT","TR","TD","TH"];
	var inlineEles = ["BUTTON","OPTION","ADDRESS","DFN","EM","VAR","KBD","INPUT","SMALL","SAMP","SUB","SUP","SPAN","A","B","I","U","S","STRONG","LABEL","IMG","BR","FONT"];
	
	var sPreCssText = "";
	for(var i=0; i < quoteEles.length; i++){
		sPreCssText += (".TRS_Editor " + quoteEles[i] + ",");
	}
	sPreCssText = sPreCssText.substring(0,sPreCssText.length -1);
	oCurrStyle[sPreCssText] = {
		"margin-top" : $("d_inpmargintop").checked ? oStyle["v_inpmargintop"] : 0,
		"margin-bottom" : $("d_inpmarginbottom").checked ? oStyle["v_inpmarginbottom"] : 0
	}
	oCurrStyle[sPreCssText]["line-height"] = $("d_inplineheight").checked ? oStyle["v_inplineheight"] : 1;

	return oCurrStyle;
}
function getStyle(_oStyles,strCurrStyle){
	var result = "";
	var sCssText = "";
	var idx = 0;
	for(var sRuleName in _oStyles){
		var oStyle = _oStyles[sRuleName];
		sCssText = "";
		for(var sName in oStyle){
			sCssText += sName + ":" + oStyle[sName] + ";" ;
		}
		result += (sRuleName + "{" + sCssText+"}\n");
	}
	return result;
}
function transObj2Str(_obj){
	if(_obj==null)return '""';
	if(typeof _obj != 'object')return '"' + _obj + '"';
	var retVal = "{";
	var bFirst = true;
	for(var sName in _obj){
		if(_obj[sName]==null)continue;
		if(!bFirst)retVal += ',';
		retVal += '"' + sName + '":' + transObj2Str(_obj[sName]);
		bFirst = false;
	}
	retVal += '}';
	return retVal;
}

//checkBox
function checkAttributeGet(domID){
	if(theDefaultConfig.sAttributes && theDefaultConfig.sAttributes[domID]=="1") {
		$(domID).checked = true;
	}else {
		$(domID).checked = false;
	}
}
//inputValue
function valueAttributeGet(domID){
	//字体、字号
	if(domID == "d_selfontname" || domID == "d_selfontsize") {
		var currValue = theDefaultConfig.sAttributes&&theDefaultConfig.sAttributes[domID] ? theDefaultConfig.sAttributes[domID] : "" ;
		//特殊考虑到select选项中字体和字号的显示问题，当未对字体进行设置时，不读取cookie中的值，仍为默认的"字体"和"字号"
		if(currValue && (currValue !== "--系统字体--") && (currValue !== "--系统字号--")) {
		$(domID).value = currValue;
		}
		return;
	}
	//段前、段后、行距
	var key = "v_"+ domID.substring(2);
	var inputValue = theDefaultConfig.sAttributes&&theDefaultConfig.sAttributes[key] ? theDefaultConfig.sAttributes[key] : "" ;
	if($(key)) {
		$(key).value = inputValue;
	}
}

/*
*不再采用cookie的方式，改用从数据库中取值
*/
/*
function setCookie4Format(){
	var types = ["d_del","d_add","d_inp"];
	for (var i=0; i<types.length; i++) {
		var type = types[i]
		var arrInitValues = DefaultFormat_ControlPanel[type];
		for (var j=0; j<arrInitValues.length; j++) {
			var configureKey = arrInitValues[j];
			checkCookieSet(configureKey);
		}
	}
	var inputValues = DefaultFormat_ControlPanel["d_inp"];
	for (var i=0; i<inputValues.length; i++) {
		var configureKey = inputValues[i];
		configureKey = "v_"+ configureKey.substring(2);
		if($(configureKey)) {
			valueCookieSet(configureKey);
		}
	}
	valueCookieSet("d_selfontname");
	valueCookieSet("d_selfontsize");

}
*/