Event.observe(window,'load', function(){
	var oTabPanel = new wcm.TabPanel({
		id : 'tabPanel',
		activeTab : 'tab-body-base'
	});
	oTabPanel.show();
	//SearchQuery
	oTabPanel.addListener('beforetabchange', function(sActiveTabKey){
		if(sActiveTabKey == 'tab-body-base' || sActiveTabKey == 'tab-body-parameter'){
			return;
		}
		var dom = $('searchquery');
		dom.setAttribute(sActiveTabKey+'-search-value', dom.value);
	});
	var sLastActiveTabKey = '';
	oTabPanel.addListener('tabchange', function(sActiveTabKey){
		if(sActiveTabKey == 'tab-body-base' || sActiveTabKey == 'tab-body-parameter'){
			Element.hide('searchquery-box');
		}else{
			Element.show('searchquery-box');
			var dom = $('searchquery');
			dom.value = dom.getAttribute(sActiveTabKey+'-search-value') || "";
			sLastActiveTabKey = sActiveTabKey;
		}	
	});
	Event.observe('searchquery', 'keydown', function(event){
		event = window.event || event;
		var keyCode = event.keyCode;
		if(keyCode != 13) return;
		var sSearchValue = $('searchquery').value.trim();
		//filter the 风格
		if(!sLastActiveTabKey || !$(sLastActiveTabKey)) return; 
		var items = document.getElementsByClassName('thumb', $(sLastActiveTabKey));
		for(var index = 0; index < items.length; index++){
			var sStyleName = items[index].getAttribute('stylename');
			Element[sStyleName.indexOf(sSearchValue) >= 0 ? 'show' : 'hide'](items[index]);
		}
	});
});

window.m_cbCfg = {
	btns : [
		{
			text : '保存',
			id : 'btnSave',
			cmd : function(){
				savaData();
				return false;
			}
		},
		{
			extraCls : 'wcm-btn-close',
			text : '取消'
		}
	]
};	

//获取资源可用内容风格,改存风格标识
function getContentStyles(){
	var sCStyle = "";
	var doms = document.getElementsByName('CStyle');
	for (var i = 0; i < doms.length; i++){
		if(doms[i].checked){
			if(sCStyle == ""){
				sCStyle += doms[i].value;//getAttribute("_value");
			}else{
				sCStyle += ("," + doms[i].value);//getAttribute("_value"));
			}
		}
		continue;
	}
	return sCStyle;
}
//获取资源默认风格
function getWDefaultStyle(){
	var sWDefaultStyle = "";
	var doms = document.getElementsByName('WStyle');
	for (var i = 0; i < doms.length; i++){
		if(doms[i].checked){
			return sWDefaultStyle = doms[i].value;
		}
		continue;
	}
	return sWDefaultStyle;
}

function getHeadDisplayValue(){
	var sHeadDisplayValue = "";
	var doms = $('paramIfrm').contentWindow.document.getElementsByName("HEADDISPLAY");
	for (var i = 0; i < doms.length; i++){
		if(doms[i].checked){
			return sHeadDisplayValue = doms[i].value;
		}
		continue;
	}
	return sHeadDisplayValue;
}

function getWidgetType(){
	var nWidgetType = 0;
	var doms = document.getElementsByName("WIDGETTYPE");
	for (var i = 0,nLen = doms.length; i < nLen; i++){
		if(doms[i].checked){
			nWidgetType = doms[i].value;
			return nWidgetType;
		}
		continue;
	}
	return nWidgetType;
}

function savaData(){
	//校验
	if(!ValidationHelper.doValid('widgetName')){
		return false;
	}
	var bAdd = false;
	if($('objectId').value=='0'){
		bAdd = true;
	}
	var sContentStyles = getContentStyles();
	var nWDefaultStyle = getWDefaultStyle();
	var nWidgetType = getWidgetType();
	var oPostData = {
		ObjectId : $('objectId').value,
		WNAME: $('widgetName').value,
		WDESC:$('widgeDesc').value,
		WIDGETCONTENT:$('widgetContent').value,
		WIDGETATTRURL:$('widgetAttrURL').value,
		WIDGETPIC : $('PicFile').value,
		WDEFAULTSTYLE:nWDefaultStyle,
		CONTENTSTYLE :sContentStyles,
		WIDGETTYPE : nWidgetType,
		WIDGETCATEGORY : $('WidgetCategory').value,
		CDEFAULTSTYLE :$('cDefalutStyle').value,
		WIDGETORDER : $('WidgetOrder').value
	};
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	var c_bWin = wcm.CrashBoarder.get(window);
	if($('objectId').value!='0'){
		c_bWin.hide();
	}
	oHelper.Call('wcm61_widget', 'save', oPostData, true, function(_trans,json){
		if(json.RESULT){
				$('objectId').value = json.RESULT;
			}
		if(bAdd){
			Ext.Msg.confirm('是否要新建资源变量？', {
				yes : function(){
					//跳到资源变量页面
					wcmXCom.get('tabPanel').setActiveTab('tab-body-parameter');
					//需要重新刷一下页面，保证取到新的头部id
					$('paramIfrm').src="widgetparameter_list.html?widgetId="+$('objectId').value;
					Element.hide('divMask');
					Element.show('contentbox');
				},
				no : function(){
					var c_bWin = wcm.CrashBoarder.get(window);
					c_bWin.notify(true);
					c_bWin.close();
				}
			});
		}else{
			var sHeadDisplayValue = getHeadDisplayValue();
			var oParams = {
				OBJECTID : $('paramIfrm').contentWindow.$('HEADDISPLAY_1').getAttribute('objId'),
				WIDGETID : $('objectId').value,
				WIDGETPARAMNAME : "HEADDISPLAY",
				DEFAULTVALUE : sHeadDisplayValue,
				WIDGETPARAMTYPE : 5
			};
			oHelper.Call('wcm61_widgetparameter', 'save', oParams, true, function(_trans,json){
				c_bWin.notify(true);
				/*
				* modify by ffx@2012-06-25 
				* 修改新建资源后，第一次打开修改页面，点击“保存”没有反应的问题
				*/
				c_bWin.close();
			})
		}
	});   
	return false;
}
//选择初始化
Event.observe(window, 'load', function(){
	var doms = document.getElementsByName('CStyle');
	for (var i = 0; i < doms.length; i++){
		if(doms[i].getAttribute("hasSelect")=="true"){
			doms[i].click();
		}
		continue;
	}
});

function init(){
	//注册校验成功时执行的回调函数
	ValidationHelper.addValidListener(function(){
		//按钮有效处理
		wcmXCom.get('btnSave').enable();
	}, "widget_data");

	//注册校验失败时执行的回调函数
	ValidationHelper.addInvalidListener(function(){
		//按钮失效处理
		wcmXCom.get('btnSave').disable();
	}, "widget_data");

	//初始化页面中需要校验的元素
	ValidationHelper.initValidation();
}

function checkWidgetName(){
	var nObjId = $('objectId').value;
	var sWidgetName = $('widgetName').value;
	var oPostData = {
		objectId : nObjId,
		widgetName : sWidgetName
	};
	if($('objectId') == 0 || (sWidgetName != $('widgetName').getAttribute('oldValue', 2))){
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		oHelper.Call('wcm61_widget', 'existsSimilarName', 
			oPostData, true, function(transport, json){
			if(com.trs.util.JSON.value(json, "result") == 'false'){
				ValidationHelper.successRPCCallBack();
			}else{
				ValidationHelper.failureRPCCallBack("资源名称已经存在");
			}
		});
	}
}

Event.observe(window, 'load', function(){
	if($('objectId').value == '0'){
		Position.clone($('contentbox'), $('divMask'));
		$('divMask').style.width = "100%";
		$('divMask').style.height = "100%";
		if(Ext.isStrict){
			$('divMask').style.height = "90%";
			$('divMask').style.top = "35px";
		}
		Element.hide('contentbox');
		Element.show('divMask');
	}
});

//处理上传
//上传母板文件的upload_dowith页面中用到的方法
function addFile(_sFilePath){
	if(_sFilePath==null){
		Ext.Msg.$alert("文件路径为空，上传失败！");
		return false;
	}

	var oForm = document.getElementById('formData');
	oForm.FILENAME.value = _sFilePath;
	oForm.submit();

}

//上传母板文件错误提示
function notifyOnUploadFileError(_sErrorMsg) {
	Ext.Msg.$alert(_sErrorMsg);
}

		
// 处理上传到临时目录的图片，在 upload_dowith 页面中使用
function dealWithUploadedPicFile(_saveFileHttpPath, _saveFileName){
	if(_saveFileHttpPath.indexOf("upload")<0){
		Ext.Msg.$alert("上传文件失败");
	}
	if(!_saveFileHttpPath && _saveFileHttpPath=="")
		return;
	$("img_ViewThumb").src = "../../../file/read_image.jsp?FileName=" + _saveFileName;
	$("PicFile").value = _saveFileName;
}

// 将缩略图还原为默认状态
function resumeThumb(){
	if($("PicFile").value == "" || $("PicFile").value == "0"){
		Ext.Msg.$alert("未发现上传的缩略图！");
		return;
	}
	Ext.Msg.confirm('您确定要清除此母板的缩略图吗？', {
		yes : function(){
			$("img_ViewThumb").src = "../images/list/none.gif";
			$("PicFile").value = "";
		}
	});
}

Event.observe(window, 'load', function(){
	var dom = $('WidgetOrder');
	var opts = dom.options;
	if($('objectId').value == '0'){
		dom.selectedIndex = (opts.length - 1);
		return;
	}

	var nWidgetOrder = dom.getAttribute("_value");
	dom.value = nWidgetOrder;
	dom.selectedIndex -= 1;
	if(dom.selectedIndex < 0){
		dom.selectedIndex = 0;
	}
	opts[dom.selectedIndex].setAttribute("value", nWidgetOrder);
	if(opts.remove){//IE
		opts.remove(dom.selectedIndex + 1);
	}else{//Not IE
		dom.removeChild(opts[dom.selectedIndex + 1]);
	}

});