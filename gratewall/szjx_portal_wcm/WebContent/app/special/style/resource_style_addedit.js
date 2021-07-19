// 定义 crashboard 的内容，标题、尺寸、按钮
var root_path = WCMConstants.WCM6_PATH + 'special/style/';
window.m_cbCfg = {
	btns : [
		{
			text : '预览',
			extraCls : 'wcm-btn-preview',
			cmd : function(){
				preview();
				return false;
			}
		},{
			text : '确定',
			id : 'btnSave',
			cmd : function(){
				save();
				return false;
			}
		},{
			text : '取消',
			extraCls : 'wcm-btn-close'
		}
	]
}
function doSave(_oXML, styleJson){
	var postData = styleJson;
	Ext.apply(postData, {
		StyleItemXML : _oXML
	});
	if(top.ProcessBar)
		top.ProcessBar.start("新建资源风格！");
	top.BasicDataHelper.call("wcm61_resourcestyle", 'save', postData, 'true', function(_transport, _json){
		if(top.ProcessBar)
			top.ProcessBar.close();
		var cb = top.window.wcm.CrashBoarder.get('resourcestyle_addedit');
		cb.notify();
	});
}
function preview(){
	// 数据校验
	if(!ValidationHelper.doValid("frm")){
		return false;
	}
	// 整合数据
	var eFrom = $("frm");
	var postData = getPostStyleJson(eFrom);
	Ext.apply(postData, {
		StyleItemXML : getStyleXML(eFrom)
	});
	var sCssFlag = eFrom.CssFlag.value;
	//top.window.ProcessBar.init();
	//top.window.ProcessBar.start("正在获取预览样式，请稍后......");

	top.BasicDataHelper.call("wcm61_resourcestyle", 'previewStyle', postData, 'true', function(_transport, _json){
		var sCssContent = _transport.responseText;
		top.window.wcm.CrashBoarder.get('resource-style-preview').show({
			title : '预览',
			src : root_path + 'stylepreview/resource_preview_page.jsp?CssFlag='+sCssFlag,
			width:'500px',
			height:'320px',
			appendParamsToUrl : false,
			params:{
				CssContent : sCssContent
			}
		});	
	});
}
// 切换编辑区域
function switchStyleSet(_event,_eBtn) {
	// 判断是否可以点击
	if(Element.hasClassName(_eBtn, "btn_clicked")){
		return;
	}
	// 获取需要的数据
	var event = window.event||_event;
	Event.stop(event);
	var eBtnParent = _eBtn.parentNode;
	var eBtnBrothers = eBtnParent.childNodes;

	// 改变按钮的样式
	for (var i=0; i<eBtnBrothers.length; i++) {
		var eCurrBtn = eBtnBrothers[i];
		if(!eCurrBtn||!Element.hasClassName(eCurrBtn, "btn")){
			continue;
		}
		var eSetBox = $(eCurrBtn.getAttribute("ForSetBox"));
		
		if(eCurrBtn==_eBtn){
			Element.removeClassName(_eBtn,"btn_unclick");
			Element.addClassName(_eBtn,"btn_clicked");
			Element.removeClassName(eSetBox,"box_hide");
		}else{
			Element.addClassName(eSetBox,"box_hide");
			Element.removeClassName(eCurrBtn,"btn_clicked");
			Element.addClassName(eCurrBtn,"btn_unclick");
		}
	}
}

// 可输入下拉列表框
var dealWith_inputselect=function(sAttachElement){
	var sSelectId = "select_"+sAttachElement;
	Event.observe(sSelectId, 'click', function(event){
		var oInput = $("input_"+sAttachElement);
		var oSelect = $(sSelectId);
		oSelect.value = oInput.value;
	});
	Event.observe(sSelectId, 'change', function(event){
		var oInput = $("input_"+sAttachElement);
		var oSelect = $(sSelectId);
		oInput.value = oSelect.value;
		//ValidationHelper.forceValid(oInput);
	});
}
// 图片上传组件
function dealWithUploadedImageFile(_sSaveFilePath, _sInputId){
	if(_sSaveFilePath.length<0){
		top.window.Ext.Msg.alert('上传文件失败');
	}
	if(!_sSaveFilePath&&_sSaveFilePath==''){
		return;
	}
	var eInput = document.getElementById(_sInputId);
	eInput.value = _sSaveFilePath;
}
// 将设置的背景颜色清除
function resumeColor(_sColorSelId){
	var eColorSel = $(_sColorSelId);
	var eOpt = eColorSel.options[ eColorSel.selectedIndex ];
	eOpt.style.backgroundColor = "transparent";
	eColorSel.style.backgroundColor = "transparent";
	eOpt.innerHTML = "无";
	eOpt.value = "transparent";
}
// 将设置的背景图片清除
function resumeBgImg(_sImgSetDomId){
	top.window.Ext.Msg.confirm('您确定要清除背景图吗？', {
		yes : function(){
			$(_sImgSetDomId).value = "";
			var sIframeSrc = $(_sImgSetDomId+"_iframe").src;
			var sNewIframSrc = sIframeSrc.substring(0,sIframeSrc.indexOf("?"));
			var lParamList = sIframeSrc.substring(sIframeSrc.indexOf("?")).split("&");
			for (var i=0; i<lParamList.length; i++) {
				var lParamItemList = lParamList[i].split("=");
				if(lParamItemList[0]!="FileUrl"){
					sNewIframSrc += lParamList[i] + "&";
				}else{
					sNewIframSrc += "FileUrl=&";
				}
			}
			$(_sImgSetDomId+"_iframe").src = sNewIframSrc;
		}
	});
}
function blurCssFlagSucFunc(_transport, _json){
	var size = _transport.responseText.trim();
	var _eErrorMgrDom = $('CssFlag_mgr');
	var _eInput = $('CssFlag');
	if(size > 0 &&_eErrorMgrDom){
		_eErrorMgrDom.innerHTML = "该标识已被使用";
		ValidationHelper.failureRPCCallBack();
		_eInput.setAttribute("ValueCanUsed",false);
	}else{
		_eInput.setAttribute("ValueCanUsed",true);
	}
}
function blurNameSucFunc(_transport, _json){
	var size = _transport.responseText.trim();
	var _eErrorMgrDom = $('StyleName_mgr');
	var _eInput = $('StyleName');
	if(size > 0 &&_eErrorMgrDom){
		_eErrorMgrDom.innerHTML = "该名称已被使用";
		ValidationHelper.failureRPCCallBack();
		_eInput.setAttribute("ValueCanUsed",false);
	}else{
		_eInput.setAttribute("ValueCanUsed",true);
	}
}
// 判断CssFlag是否可用
function CssFlagUsedCheck(_eInput,_nStyleType,_nPageStyleId,CurrStyleId,_eErrorMgrDom, successfunc){
	if(!_eInput){
		return;
	}
	var sCssFlag = _eInput.value;
	var sPostStr = "CssFlag=" + sCssFlag 
		+ "&StyleType=" + _nStyleType
		+ "&PageStyleId=" + _nPageStyleId
		+ "&CurrStyleId=" + CurrStyleId;
	var sUrl = root_path + "cssflag_used_check.jsp";
	new top.window.ajaxRequest({
		url : sUrl,
		parameters : sPostStr,
		method : 'get',
		onSuccess : successfunc
	});
}
// 判断StyleName是否可用
function StyleNameUsedCheck(_eInput,_nStyleType,_nPageStyleId,CurrStyleId,_eErrorMgrDom, successfunc){
	if(!_eInput){
		return;
	}
	var sStyleName = _eInput.value;
	var sPostStr = "StyleName=" + encodeURIComponent(sStyleName)
		+ "&StyleType=" + _nStyleType
		+ "&PageStyleId=" + _nPageStyleId
		+ "&CurrStyleId=" + CurrStyleId;
	var sUrl = root_path + "stylename_used_check.jsp";
	new top.window.ajaxRequest({
		url : sUrl,
		parameters : sPostStr,
		method : 'get',
		onSuccess : successfunc
	});
}