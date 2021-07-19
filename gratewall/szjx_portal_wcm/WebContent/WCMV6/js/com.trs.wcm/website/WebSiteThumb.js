$package('com.trs.wcm.website');

com.trs.wcm.website.WebSiteMgr=Class.create('wcm.website.WebSiteMgr');
com.trs.wcm.website.WebSiteMgr.prototype = {
	initialize: function(_oPageParams){
		this.oPageParams = _oPageParams || {};
		this.helpers = {};
		this.fOnSuceesses = {};
		this.helpers['WebSites'] = new com.trs.web2frame.DataHelper('wcm6_website');
//		this.helpers['WebSites'] = $dataHelper(com.trs.wcm.datasource.website.WebSites);
		this.fOnSuceesses['WebSites'] = function(_transport,_json){
			this.bindWebSitesEvents($('WebSitesTagId'));
		}.bind(this);
	},
	loadWebSites : function(){
		try{
			this.helpers['WebSites'].callBind('WebSitesTagId','query',
				this.oPageParams,false,this.fOnSuceesses['WebSites']);
		}catch(err){
			//TODO logger
			//alert(err.message);
		}
	},
	bindWebSitesEvents:function(oSiteTag){
		var divOuter = document.getElementsByClassName("outline_border")[0];
		Event.observe(divOuter, 'click', function(event){
			event = event || window.event;
			var srcElement = Event.element(event);
			if(Element.hasClassName(srcElement, "inner_tabular") || Element.hasClassName(srcElement, "inner_tabular_tex")){
				alert("checked");
			}else{
				alert("no");
			}
		}, false);
		
		/*this.setCountPreRow();
		var  tabularArray = document.getElementsByClassName("tabular",oSiteTag);
		for(var i=0;i<tabularArray.length;i++){
			//绑定鼠标单击事件
			Event.observe(tabularArray[i],'click',function(event){
				event = event || window.event;
				var pressCtrl = event.ctrlKey;
				var oSrcElement = Event.element(event);
				if(oSrcElement.tagName.toLowerCase()=='img'){
					WebSites[oSrcElement.getAttribute("_optType")] + "Site"](oSrcElement.getAttribute("_siteId"));
					Event.stop(event);
					return;
				}else if(oSrcElement.tagName.toLowerCase() == 'input'){
					pressCtrl = true;
				}
				var divArray = this.getElementsByTagName("div");
				if(pressCtrl){
					if(Element.hasClassName(divArray[0],"inner_tabular_blueness")){
						WebSites.select
					}
				}
		}
		this.setCountPreRow();
		var tabularArray = document.getElementsByClassName("tabular", oSiteTag);
		for (var i = 0; i < tabularArray.length; i++){
			//绑定鼠标单击事件
			Event.observe(tabularArray[i], 'click', function(event){
				event = event || window.event;
				var pressCtrl = event.ctrlKey;
				var oSrcElement = Event.element(event);
				if(oSrcElement.tagName.toLowerCase() == 'img'){
					WebSites[oSrcElement.getAttribute("_optType") + "Site"](oSrcElement.getAttribute("_siteId"));
					Event.stop(event);
					return;
				}else if(oSrcElement.tagName.toLowerCase() == 'input'){
					pressCtrl = true;
				}
				var divArray = this.getElementsByTagName("div");
				if(pressCtrl){		
					if(Element.hasClassName(divArray[0], "inner_tabular_blueness")){
						WebSites.selectedElements = WebSites.selectedElements.without(this);
						Element.removeClassName(divArray[0], "inner_tabular_blueness");
						Element.addClassName(divArray[0], "inner_tabular");
					}else{
						WebSites.selectedElements.push(this);
						Element.removeClassName(divArray[0], "inner_tabular");
						Element.addClassName(divArray[0], "inner_tabular_blueness");
					}
				}else{
					WebSites.removeClassName();
					WebSites.selectedElements = [this];
					Element.removeClassName(divArray[0], "inner_tabular");
					Element.addClassName(divArray[0], "inner_tabular_blueness");
				}
				WebSiteIndex.keepCheckBoxStatus(divArray[0], this);
				WebSites.sendMessage(WebSites.getObjectIds());
			}.bind(tabularArray[i]), false);
			//绑定鼠标双击事件
			Event.observe(tabularArray[i], 'dblclick', function(event){
				WebSites.openSite();
			}, false);
			Event.observe(tabularArray[i], 'mouseover', function(event){
				Element.show("tb_operators_" + this.getAttribute("_siteId"));
			}.bind(tabularArray[i]), false);
			Event.observe(tabularArray[i], 'mouseout', function(event){
				Element.hide("tb_operators_" + this.getAttribute("_siteId"));
			}.bind(tabularArray[i]), false);
		}
		WebSites.selectedElements=[];
		WebSites.sendMessage(WebSites.getObjectIds());*/
	},
	setCountPreRow : function(){
		var oSiteTag = $("WebSitesTagId");
		var tabularArray = document.getElementsByClassName('tabular', oSiteTag);
		var i = 0;
		for (i = 1; i < tabularArray.length; i++){
			if(tabularArray[i].offsetTop != tabularArray[i-1].offsetTop)
				break;
		}
		this.countPreRow = i;
	},
	keepCheckBoxStatus : function(divObj, tabularObj){
		var inputObj = tabularObj.getElementsByTagName("input")[0];
		if(Element.hasClassName(divObj, "inner_tabular_blueness")){
			inputObj.checked = true;
		}else{
			inputObj.checked = false;
		}
	},
	showOrderTypes : function(_evt){
		_evt = _evt || window.event;
		sOrderTypes = '<ul><li><a href="#" onclick="WebSiteIndex.orderContent(\'\'); return false;">默认</a>\
			<li><a href="#" onclick="WebSiteIndex.orderContent(\'SiteDesc\'); return false;">站点显示名称</a>\
			<li><a href="#" onclick="WebSiteIndex.orderContent(\'CrTime\'); return false;">创建时间</a>\
			<li><a href="#" onclick="WebSiteIndex.orderContent(\'CrUser\'); return false;">创建者</a></ul>';
		showHelpTip(_evt, sOrderTypes, false);	
	},
	orderContent : function(_sOrderType){
		Object.extend(WebSiteIndex.oPageParams, {OrderBy : _sOrderType});
		WebSiteIndex.loadWebSites();
	}
}

var WebSiteMgr = new com.trs.wcm.website.WebSiteMgr();
var WebSites = com.trs.wcm.website.WebSiteIndex.WebSites = {	
	selectedElements : [],
	updateCurrSite : function(oPostData){
		var divArray = this.selectedElements[0].getElementsByTagName("div");
		if(oPostData["sitedesc"]){
			divArray[1].getElementsByTagName("a")[0].innerText = oPostData["sitedesc"];
		}else if(oPostData["sitename"]){
			var index = divArray[0].title.lastIndexOf("站点名称：");
			divArray[0].title = divArray[0].title.substring(0, index) + "站点名称：" + oPostData["sitename"];
		}
	},
	getObjectIds : function(){
		var objectIds = new Array();
		for (var i = 0; i < this.selectedElements.length; i++){
			objectIds.push(this.selectedElements[i].getAttribute("_siteId"));
		}
		return objectIds;
	},
	selectPre : function(tabularArray, event){
		var index = this.getMinIndex(tabularArray);
		if(index == -2)
			return;
		this.removeClassName(this.selectedElements);
		index = ((index == -1 || index == 0) ? 0 : index - 1);
		var divArray = tabularArray[index].getElementsByTagName("div");
		Element.removeClassName(divArray[0], "inner_tabular");
		Element.addClassName(divArray[0], "inner_tabular_blueness");
		//add word select
		this.selectedElements = [tabularArray[index]];	
		WebSiteIndex.keepCheckBoxStatus(divArray[0], tabularArray[index]);
		this.sendMessage(WebSites.getObjectIds());
	},
	selectNext : function(tabularArray, event){
		var index = this.getMaxIndex(tabularArray);
		if(index == -2)
			return;
		this.removeClassName(this.selectedElements);
		if(index == -1)
			index = 0;
		else if(index != tabularArray.length-1)
			index = index + 1;
		var divArray = tabularArray[index].getElementsByTagName("div");
		Element.removeClassName(divArray[0], "inner_tabular");
		Element.addClassName(divArray[0], "inner_tabular_blueness");
		//add word select
		this.selectedElements = [tabularArray[index]];
		WebSiteIndex.keepCheckBoxStatus(divArray[0], tabularArray[index]);
		this.sendMessage(WebSites.getObjectIds());
	},
	selectAbove : function(tabularArray, event){		
		var index = this.getMinIndex(tabularArray);
		if(index == -2)
			return;
		this.removeClassName(this.selectedElements);
		if(index == -1)
			index = 0;
		else{
			var row = Math.floor(index / WebSiteIndex.countPreRow);
			var col = index % WebSiteIndex.countPreRow;
			var virtualIndex = (row - 1) * WebSiteIndex.countPreRow + col;
			if(virtualIndex >= 0)
				index = virtualIndex;
		}
		var divArray = tabularArray[index].getElementsByTagName("div");
		Element.removeClassName(divArray[0], "inner_tabular");
		Element.addClassName(divArray[0], "inner_tabular_blueness");
		//add word select
		this.selectedElements = [tabularArray[index]];	
		WebSiteIndex.keepCheckBoxStatus(divArray[0], tabularArray[index]);
		this.sendMessage(WebSites.getObjectIds());
	},
	selectBelow : function(tabularArray, event){
		var index = this.getMinIndex(tabularArray);
		if(index == -2)
			return;
		this.removeClassName(this.selectedElements);
		if(index == -1)
			index = 0;
		else{
			var row = Math.floor(index / WebSiteIndex.countPreRow);
			var col = index % WebSiteIndex.countPreRow;
			var virtualIndex = (row + 1) * WebSiteIndex.countPreRow + col;	
			if(virtualIndex < tabularArray.length)
				index = virtualIndex;
		}
		var divArray = tabularArray[index].getElementsByTagName("div");
		Element.removeClassName(divArray[0], "inner_tabular");
		Element.addClassName(divArray[0], "inner_tabular_blueness");
		//add word select
		this.selectedElements = [tabularArray[index]];
		WebSiteIndex.keepCheckBoxStatus(divArray[0], tabularArray[index]);
		this.sendMessage(WebSites.getObjectIds());
	},
	toggleAllSites:function(tabularArray){
		/*
		if(this.selectedElements.length > 0){
			this.removeClassName();
			this.selected
		}else{*/
			for (var i = 0; i < tabularArray.length; i++){
				var divArray = tabularArray[i].getElementsByTagName("div");
				Element.addClassName(divArray[0], "inner_tabular_blueness");
				var inputObj = tabularArray[i].getElementsByTagName("input")[0];
				inputObj.checked = true;		
			}
			this.selectedElements = tabularArray;
		//}
		this.sendMessage(WebSites.getObjectIds());
	},
	deleteSites : function(){	
		if(this.selectedElements.length > 0){
			if(confirm("确实要删除当前所选站点吗？")){
				var ids = this.getObjectIds().join(",");
				WebSiteIndex.helpers['WebSites']._delete(ids, function(){
					WebSiteIndex.loadWebSites();
				});
			}
		}
	},
	openSite : function(){
		if(this.selectedElements.length > 0){			
			alert("Open WebSite:" + this.selectedElements.last().getAttribute("_siteId"));
		}
	},

	previewSite : function(ids){
		if(!ids && this.selectedElements.length > 0){
			ids = this.selectedElements.last().getAttribute("_siteId");
		}
		if(ids){
			alert("Preview WebSite:" + ids);
		}
	},
	homePublishSite : function(ids){
		if(!ids && this.selectedElements.length > 0){
			var ids = this.getObjectIds();
		}
		if(ids){	
			alert("Home Publish WebSite:" + ids);
		}
	},
	editSite : function(ids){
		if(!ids && this.selectedElements.length > 0){
			ids = this.selectedElements.last().getAttribute("_siteId");
		}
		if(ids){			
			alert("Edit WebSite:" + ids);
			window.open("floatpanel.htm?SiteId=" + ids, "", "width=550, height=550, titlebar=no, menubar=no, status=no");
		}		
	},
	newSite : function(){
		window.open("floatpanel.htm?SiteId=0", "", "width=550, height=550");
	},
	renameSite : function(){
		if(this.selectedElements.length > 0){	
		}
	},
	/*
	得到所选站点的最小序号.如果没有站点,返回-2;如果未选中站点,返回-1;
	*/
	getMinIndex:function(tabularArray){
		if(tabularArray.length <= 0)
			return -2;
		if(this.selectedElements.length == 0)
			return -1;
		var minIndex = this.selectedElements[0].getAttribute("_index") - 1;
		for (var i = 1; i < this.selectedElements.length; i++){	
			if(this.selectedElements[i].getAttribute("_index") - 1 < minIndex)
				minIndex = this.selectedElements[i].getAttribute("_index") - 1;
		}
		return minIndex;
	},

	/*
	得到所选站点的最大序号.如果没有站点,返回-2;如果未选中站点,返回-1;
	*/
	getMaxIndex:function(tabularArray){
		if(tabularArray.length <= 0)
			return -2;
		var maxIndex = -1;
		for (var i = 0; i < this.selectedElements.length; i++){
			if(this.selectedElements[i].getAttribute("_index") - 1 > maxIndex)
				maxIndex = this.selectedElements[i].getAttribute("_index") - 1;
		}
		return maxIndex;	
	},
	removeClassName:function(){
		for (var i = 0; i < this.selectedElements.length; i++){
			var divArray = this.selectedElements[i].getElementsByTagName("div");
			Element.removeClassName(divArray[0], "inner_tabular_blueness");
			Element.addClassName(divArray[0], "inner_tabular");
			//change word css
			var input = this.selectedElements[i].getElementsByTagName("input")[0];
			input.checked = false;
		}
	},
	//更新属性和操作面板
	sendMessage : function(objectIds){	
		try{
			$MessageCenter.sendMessage('website_attribute',
				'WebSiteAttribute.response',"WebSiteAttribute",
				Object.extend(WebSiteIndex.oPageParams, {"objectids" : objectIds||[]}));
		}catch(_error){
			//TODO logger
			//alert(_error.description);
		}		
	}
}

var selTxtQuery = {
	WEBSITEDESC_SEL : '..输入站点显示名称', 
	WEBSITEID_SEL	: '..输入站点ID',

	changeQueryType : function changeQueryType(_sel){
		var txtQueryVal = $('txtQueryVal');
		if(_sel.value == 0) {
			if(txtQueryVal.value == selTxtQuery.WEBSITEID_SEL 
					|| txtQueryVal.value.trim() == '') {
				txtQueryVal.value = selTxtQuery.WEBSITEDESC_SEL;
				txtQueryVal.style.color = 'gray';
			}
		}else{
			if(txtQueryVal.value == selTxtQuery.WEBSITEDESC_SEL 
					|| txtQueryVal.value.trim() == '') {
				txtQueryVal.value = selTxtQuery.WEBSITEID_SEL;
				txtQueryVal.style.color = 'gray';
			}			
		}
	},

	changeQueryVal : function (_txt){
		//_txt.value = '';
		_txt.select();
		_txt.style.color = '#010101';
	},
	queryByDescOrId : function(){
		var selObj = $('selQueryType');
		if(selObj.value == 0){//模糊查找
			Object.extend(WebSiteIndex.oPageParams, {
				QuerySiteDesc : $F("txtQueryVal"),
				QuerySiteId	  : ""
			});
		}else{
			var siteId = $F("txtQueryVal");
			if(siteId != "" && isNaN(parseInt(siteId))){
				alert("请输入合法格式的站点号");
				$('txtQueryVal').focus();
				$('txtQueryVal').select();
				return false;
			}
			Object.extend(WebSiteIndex.oPageParams, {
				QuerySiteId  : $F("txtQueryVal"),
				QuerySiteDesc: ""
			});
		}
		WebSiteIndex.loadWebSites();
		return false;
	}
}
function keydownEvent(event){
	var oSiteTag = $("WebSitesTagId");
	var tabularArray = document.getElementsByClassName('tabular', oSiteTag);
	event = event || window.event;
	if(event.ctrlKey){
		switch(event.keyCode){
			case QuickKeys["Q_CTRL_SelectAll"]:
				WebSites.toggleAllSites(tabularArray);		
				break;
			case QuickKeys["Q_CTRL_Preview"]:
				WebSites.previewSite();
				break;
			case QuickKeys["Q_CTRL_Publish"]:
				WebSites.homePublishSite();
				break;
			case QuickKeys["Q_CTRL_Edit"]:
				WebSites.editSite();
				break;
		}
		Event.stop(event);
		return;
	}
	switch(event.keyCode){
		case Key2KeyCodes["LEFT ARROW"]:
			WebSites.selectPre(tabularArray, event);
			break;
		case Key2KeyCodes["RIGHT ARROW"]:
			WebSites.selectNext(tabularArray, event);
			break;
		case Key2KeyCodes["UP ARROW"]:
			WebSites.selectAbove(tabularArray, event);
			break;
		case Key2KeyCodes["DOWN ARROW"]:
			WebSites.selectBelow(tabularArray, event);
			break;
		case QuickKeys["Q_DELETE"]:
			WebSites.deleteSites(event);
			break;
		case QuickKeys["Q_CTRL_Open"]:
			WebSites.openSite(event);
			break;
		case QuickKeys["Q_F2"]:
			WebSites.renameSite(event);
			break;
	}
}
Event.observe(document,'keydown',keydownEvent, false);