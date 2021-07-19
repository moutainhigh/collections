window.m_cbCfg = {
	btns : [
		{
			text : '保存',
			id : 'valueSetBtn',
			cmd : function(){
				savaData();
				return false;
			}
		},
		{
			extraCls : 'wcm-btn-close',
			cmd : function(){
				this.notify(false);
			},
			text : '取消'
		}
	]
};	
Event.observe(window,'load', function(){
	var oTabPanel = new wcm.TabPanel({
		id : 'tabPanel',
		activeTab : 'tab-body-base'
	});
	
	oTabPanel.addListener('tabchange', function(sActiveTabKey){
		var sRadioName = '';
		var sCancelSelStyle = "";
		if(sActiveTabKey == 'tab-body-rstyle'){
			sRadioName = 'WStyle_option';
			sCancelSelStyle = "cancelSelWStyle";
		}else if(sActiveTabKey == 'tab-body-cstyle'){
			sRadioName = 'CStyle_option';
			sCancelSelStyle = "cancelSelCStyle";
		}

		var doms = document.getElementsByName(sRadioName);
		var bIsHasStyle = false;//判断是否为取消选择标示
		for(var index = 0; index < doms.length; index++){
			if(doms[index].checked){
				var dom = doms[index];
				while(dom.tagName != 'BODY'){
					if(Element.hasClassName(dom, 'thumb')){
						dom.scrollIntoView(true);
					}
					dom = dom.parentNode;
				}
				bIsHasStyle = true;
				break;
			}
		}
		//判断是否为取消选择
		if(!bIsHasStyle){
			var oCancleBox = document.getElementById(sCancelSelStyle);
			if(oCancleBox){
				oCancleBox.checked = true;
			}
		}
	});
	
	oTabPanel.show();


	//SearchQuery
	oTabPanel.addListener('beforetabchange', function(sActiveTabKey){
		if(sActiveTabKey == 'tab-body-base'){
			return;
		}
		var dom = $('searchquery');

		dom.setAttribute(sActiveTabKey+'-search-value', dom.value);
	});

	var sLastActiveTabKey = '';
	oTabPanel.addListener('tabchange', function(sActiveTabKey){
		if(sActiveTabKey == 'tab-body-base'){
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


//获取指定的内容风格
function getContentStyle(){
	var sCStyleId = "";
	var doms = document.getElementsByName('CStyle_option');
	for (var i = 0; i < doms.length; i++){
		if(doms[i].checked){
			return sCStyleId = doms[i].value;
		}
		continue;
	}
	return sCStyleId;
}
//获取指定的资源风格
function getWidgetStyle(){
	var sWStyleId = "";
	var doms = document.getElementsByName('WStyle_option');
	for (var i = 0; i < doms.length; i++){
		if(doms[i].checked){
			return sWStyleId = doms[i].value;
		}
		continue;
	}
	return sWStyleId;
}

function setStyle(){
	$('wStyle').value = getWidgetStyle();
	$('cStyle').value = getContentStyle();
}

function getHeadDisplayValue(){
	var doms = document.getElementsByName('HEADDISPLAY');
	for (var i = 0; i < doms.length; i++){
		if(doms[i].checked)return doms[i].value;
	}
}

function savaData(){
	//设置风格，准备保存
	setStyle();
	var nHeadDisplayValue = getHeadDisplayValue();
	var oParam = {
		WIDGETINSTANCEID : $('widgetInstanceId').value,
		bAdd : $('bAdd').value,
		WSTYLEID : $('wStyle').value,
		CSTYLEID : $('cStyle').value,
		HEADDISPLAY : nHeadDisplayValue
	};
	//调用iframe里面页面的获取数据的方法
	var hash = location.hash.trim();
	if(hash != ""){
		try { 
			var data = hash ? hash.substring(1):''; 
			var winIfrm = $("main").contentWindow;
			if(winIfrm.makeData) data = winIfrm.makeData(data);
			data = toJson(data);
			saveWidgetParam(oParam,data);
		}catch(e){}; 
	}else{
		try{
			var winIfrm = $("main").contentWindow;
			if(winIfrm.makeData)winIfrm.makeData(function(data){
				saveWidgetParam(oParam,data);
			});
		}catch(e){
			if(Ext.isDebug()){
				alert(e.message);
			}
		};
	}
}

//外域返回值以A:A1;B:B1方式返回
function toJson(data){
	var srt = {};
	var pairs = data.split(";");
	if(!data || data.trim() == "") return srt;
	for(var i=0,n=pairs.length;i<n;i++){
		pair = pairs[i].split(':');
		srt[pair[0]] = decodeURIComponent(pair[1]);
	};
	return srt;
}

function saveWidgetParam(oParam,data){
	var postdata = {};
	if(typeof data =='object'){
		postdata = data;
	}else if(typeof data == 'string'){
		var eFormBox = $("main").contentWindow.$(data);
		var PostDataUtil = com.trs.web2frame.PostData;
		postdata = PostDataUtil.form(eFormBox, function(m){return m;});
	}
	Ext.applyIf(oParam,postdata);
	var oHelper = new com.trs.web2frame.BasicDataHelper();
	oHelper.Call('wcm61_visualtemplate', 'saveWidgetInstParameters', oParam, true, function(_trans,json){
		var cbr = wcm.CrashBoarder.get("set_widgetparameter_value");
		cbr.notify(true);
		cbr.close();
	});
}

function autoJusty(){
	var cbSelf = wcm.CrashBoarder.get('set_widgetparameter_value').getCrashBoard();
	if(!cbSelf){
		return;
	}
	var winIfrm = $("main").contentWindow;
	var bHasEditor = "";
	if(winIfrm.$('bHasEditor')){
		bHasEditor = winIfrm.$('bHasEditor').value;
	}
	try{
		var box = Ext.isStrict?document.documentElement:document.body;
		var minWidth = 560, minHeight = 280, maxWidth = 660, maxHeight = 410;
		var realWidth = minWidth;		
		var realHeight = minHeight;
		$('body-box').style.height = 240 +"px";
		if(bHasEditor.toLowerCase()=="true"){//如果有编辑器，窗口就变大点
			var realWidth = maxWidth;		
			var realHeight = maxHeight;
			if(Ext.isStrict){
				$('rstyle_box').style.height = 320 + "px";
				$('cstyle_content').style.height = 320 + "px";
			}else{
				$('rstyle_box').style.height = 335 + "px";
				$('cstyle_content').style.height = 335 + "px";
			}
			document.getElementsByClassName('body-box')[0].style.height = 370 +"px";
		}	
	}catch(e){
		Ext.Msg.alert(e.message)
	}
	cbSelf.setSize(realWidth+"px",realHeight+"px");		
	box.style.overflowY = 'auto';
	box.style.overflowX = 'hidden';	
	if(Ext.isGecko){//如果IE设上这个,会出现两个滚动条
		document.body.style.overflowY = 'auto';
		document.body.style.overflowX = 'hidden';
	}
	//解决有时候iframe没有撑开的问题
	$('main').style.height = $('body-box').style.height;
	cbSelf.center();
}

function init(){
	//调整页面大小
	//autoJusty();
	//调用内部页面的校验
	var winIfrm = $("main").contentWindow;
	if(winIfrm.initValidation)winIfrm.initValidation();
}