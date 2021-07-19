Object.extend(com.trs.menu.Menu.prototype, {
	enableHotKey : false,
	executeItemCommand : function(jsonObj, itemNode){
		if(jsonObj === undefined){
			try{
				$alert("尚未实现<font color='red'><B>" + itemNode.innerText + "</B></font>操作");
			}catch(error){
				alert("尚未实现[" + itemNode.innerText + "]操作");
			}
		}else if(jsonObj){
			if(MenuOperates[jsonObj["execCommand"]]){
				MenuOperates[jsonObj["execCommand"]](jsonObj, itemNode);
			}else{
				try{
					$alert("执行<font color='red'><B>" + itemNode.innerText + "</B></font>操作");
				}catch(error){
					alert("执行[" + itemNode.innerText + "]操作");
				}
			}
		}
	},
	loadDynamicItem : function(itemNode){
		return ItemsSetter[itemNode.getAttribute("getItems") || "defaultGetItems"].call(this,itemNode);
	},
	setItemStyle : function(itemNode){
		var descChild = this.getFirstHTMLChild(itemNode);
		var jsonObj = this.getItemJsonObj(descChild);
		if(jsonObj){
			if(jsonObj["identify"] && ExtraStyleDealer[jsonObj["identify"]]){//额外菜单样式处理,如：checkbox型菜单
				ExtraStyleDealer[jsonObj["identify"]](jsonObj, descChild);
			}
			if(jsonObj["identify"] && DisableDecider[jsonObj["identify"]]){//是否disabled菜单处理
				if(DisableDecider[jsonObj["identify"]](jsonObj)){
					Element.addClassName(descChild, "disabled_item");
				}else{
					Element.removeClassName(descChild, "disabled_item");
				}
			}
		}
	}
});

/*
*菜单项是否disabled的条件
*/
var DisableDecider = {
	attribute_bar : function(){
		var type = $tab().getCurrentTabType();
		var mainWin = $MessageCenter.getMain();
		if(mainWin && mainWin.PageContext){
			if(mainWin.PageContext.enableAttrPanel === false) return true;
			if(mainWin.PageContext.enableAttrPanel) return false;
		}

		if(!mainWin){
			return true;
		}
//		return (type == 'right' || (type=="document" && (top.actualTop||top).PageContext.mode=="read"));
		return (type == 'right' || (window.frames['main'].isReadMode));
	},
	newWebSite : function(jsonObj, descChild){
		if(isAdmin()){
			return false;
		}
		return true;
	},
	importWebSite : function(jsonObj, descChild){
		if(isAdmin()){
			return false;
		}
		return true;
	},
	skipTo : function(jsonObj, descChild){
//		var tabIndex = jsonObj["Path"].split(",")[0];
		var pathKey = jsonObj["Path"].split(",")[0];
		return globalTabDisabled[v6To52[pathKey]];
	}
};

/*
*菜单项是否需要添加而外样式的处理。如：在菜单前面添加checkbox选择框
*/
var ExtraStyleDealer = {
	navigate_bar : function(jsonObj, descChild){
		if(isNavAdvanced()){
			Element.addClassName(descChild, 'checkItem');
		}else{
			Element.removeClassName(descChild, 'checkItem');
		}
	},
	attribute_bar : function(jsonObj, descChild){
		if(getAttrPanelStatus()){
			Element.addClassName(descChild, 'checkItem');
		}else{
			Element.removeClassName(descChild, 'checkItem');
		}
	}
};

/*
*得到不同类型的Manager
*/
var ManagerHelper = {
	getManager:function(managerName){
		var attrWin = null;
		try{
			attrWin = $MessageCenter.iframes["oper_attr_panel"].contentWindow;
		}catch(error){
			alert("没有引入属性面板");
			return;
		}
		var managerStr = "var manager = attrWin.com.trs.wcm.domain." + managerName;
		try{
			eval(managerStr);
			if(typeof manager == 'function'){
				manager = new manager();
			}
			if(manager == null){//发送请求去获得对应的manager
				managerStr = "var manager = attrWin." + managerName;
				eval(managerStr);
			}
			if(manager == null){
				alert("发送请求去获得对应的manager!!!");
			}
			return manager;
		}catch(error){
			alert(error.message);
		}
	}
};

/*
*得到当前视图的参数信息
*/
var ParamsHelper = {
	getParams : function(type){//type=WebSiteRoot,WebSite,Channel
		var attrWin = $MessageCenter.iframes["oper_attr_panel"].contentWindow;
		FloatPanel.open('menu/select.html?type=' + type, '选择对象', 550, 400);
		return attrWin.OperAttrPanel.params || {};
	}
};

/*
*设置菜单的菜单项
*/
var ItemsSetter = {
	getWorkList : function(itemNode){//获得视图>工作窗口 下面的工作列表
		var isDisabled = $tab().isTabDisabled();
		var currType = $tab().getCurrentTabType();
		var itemsStr = '';
		var sheetsObj = $tab().getSheets();
		for (sheetObj in sheetsObj){
			itemsStr += "<div class=\"subMenuItem\" dynamic>";
			itemsStr +=		"<div class=\"subMenuItemDesc " + (isDisabled?'disabled_item':'') +  "\" params=\"execCommand:'changeSheet',type:\'" + sheetObj + "\'\">";
			if(currType == sheetObj){
				itemsStr +=		"<li class='radioItem'>" + sheetsObj[sheetObj] +"列表</li>";
			}else{
				itemsStr +=		sheetsObj[sheetObj] +"列表";
			}
			itemsStr +=		"</div>";
			itemsStr += "</div>";
		}
		return itemsStr;
	},
	getOperators : function(itemNode){//获得操作任务任务下面的根据对象改变的动态内容
		var operatorsStr = '';

		var type = $tab().getCurrentTabType();
		//if(type == 'right' || (type=="document" && (top.actualTop||top).PageContext.mode=="read")){
		if(type == 'right' || window.frames['main'].isReadMode){

		}else{
			var attrWin = $MessageCenter.iframes["oper_attr_panel"].contentWindow;
			if(attrWin.ObjectOperatorPanel && attrWin.ObjectOperatorPanel.Operators
					&& attrWin.Element.visible(attrWin.ObjectOperatorPanel.panelId)){
				var currOperators = attrWin.ObjectOperatorPanel.Operators;
				for (var i = 0; i < currOperators.length; i++){
					if(currOperators[i]["operKey"] == "seperate"){
						if(currOperators.length != (i + 1)){
							operatorsStr += "<div class==\"subMenuItem\" dynamic><div class=\"separate\"></div></div>";
						}
					}else{
						operatorsStr += "<div class=\"subMenuItem\" dynamic>";
						operatorsStr +=		"<div class=\"subMenuItemDesc\" title=\"" + currOperators[i]["operDesc"] + "\" ";
						operatorsStr +=		"params=identify:'ww',execCommand:'execOperate',operKey:'" + currOperators[i]["operKey"] + "'>"
						operatorsStr +=			currOperators[i]["operName"];
						operatorsStr +=		"</div>";
						operatorsStr += "</div>";
					}
				}
			}
		}
		//显示隐藏此菜单项前面的分割线
		if(operatorsStr ==""){
			this.getPreviousHTMLSibing(itemNode).style.display = 'none';
		}else{
			this.getPreviousHTMLSibing(itemNode).style.display = '';
		}
		return operatorsStr;
	},
	defaultGetItems : function(itemNode){//发送请求去获得菜单项
		alert("请实现一个从本地获得菜单项的方法或则实现一个从服务器端返回菜单项的方法");
		return '';
		/*********************************************************************************
		var url = "menu/dynamic.html";
		var r = new Ajax.Request(url, {asynchronous:false});
		if(r.responseIsSuccess()){
			this.setItems(itemNode, r.transport.responseText);
		}
		*********************************************************************************/
	}
};
/*
*菜单项执行的操作命令
*/
var MenuOperates = {
	skipTo : function(jsonObj){//弹出新窗口，跳转到原52页面
		if(jsonObj["Path"]){
			/*
			var winObj = window.open('/wcm/console/index/index.jsp?Path=' + jsonObj["Path"], document.hostname + 'WCM52',"fullscreen=yes,toolbar =no");
			*/
			var winObj = $openMaxWin('/wcm/console/index/index.jsp?Path=' + jsonObj["Path"], window.location.hostname.replace(/\.|-/g, "_") + 'WCM52'+m_sUserId);
			if(winObj){
				winObj.focus();
			}else{
				var msg = "窗口可能被拦截工具拦截，当前操作失效！\n";
				msg +=    "请先关闭可能的拦截工具，如：Windows IE窗口拦截设置、google bar、\n";
				msg +=    "网易助手等。然后尝试再次操作！\n";
				msg +=    "为此给你带来不便，我们深表歉意！";
				alert(msg);
			}
		}else{
			alert("想跳转到52页面时，竟然都不存在path参数！！！");
		}
	},
	quickLocate : function(){//快速定位
		$nav().quickLocate();
	},
	execOperate : function(jsonObj){//操作任务下面的动态菜单执行的命令
		var attrWin = $MessageCenter.iframes["oper_attr_panel"].contentWindow;
		var params = attrWin.OperAttrPanel.params;
		var objectType = params["ObjectType"];
		var operKey = jsonObj["operKey"];
		attrWin.OperatorsExtend._fireEvent(objectType, operKey);
	},
	checkMenu : function(jsonObj, itemNode){//视图 下面的导航栏、属性栏的命令
		var flag = true;
		if(Element.hasClassName(itemNode, 'checkItem')){
			flag = false;
		}
		switch(jsonObj.identify){
			case 'navigate_bar' :
				top.showHideNav(true, flag);
				break;
			case 'attribute_bar':
				top.showHideAttrPanel(true, flag);
				break;
			default:
				break;
		}
	},
	viewMyRight : function(){
		var sFeatures = "status=yes,toolbar=no,menubar=no,location=no,resizable =yes";
		window.open('auth/operator/right_view.jsp?OperId=' + m_sUserId + '&OperType=' + userObjType, document.hostname + 'rightview', sFeatures);
	},
	backfeedOnline : function(jsonObj){
		window.open(jsonObj["link"], document.hostname + 'backfeedOnline');
	},
	contact : function(jsonObj){
		var mailToA = $("mailToA");
		mailToA.href = jsonObj["link"];
		mailToA.click();
	},
	aboutWCM : function(){
		if(window.showModalDialog){
			var sFeatures = "dialogHeight:350px;dialogWidth:410px;status:no;scroll:no;";
			showModalDialog("menu/about.html", null, sFeatures);
		}else{
			$success("北京拓尔思信息技术有限公司<br>产品二部WCM");
		}
	},
	execOperateNew : function(jsonObj){//新建对象
		var manager = ManagerHelper.getManager(jsonObj["mgr"]);
		window.lastRunMethod = manager[jsonObj["operKey"] || 'new'].bind(manager);
		window.lastRunIds = 0;
		window.lastJsonObj = jsonObj;
		var type = jsonObj["mgr"].replace(/Mgr$/, '');
		FloatPanel.open('/menu/select.html?type=' + type, '选择对象', 550, 400);
	},
	execOperateExport : function(jsonObj){//新建对象
		var manager = ManagerHelper.getManager(jsonObj["mgr"]);
		window.lastRunMethod = manager[jsonObj["operKey"] || 'exportall'].bind(manager);
		window.lastRunIds = 0;
		window.lastJsonObj = jsonObj;
		var type = jsonObj["mgr"].replace(/Mgr$/, '');
		FloatPanel.open('/menu/select.html?method=exportall&type=' + type, '选择对象', 550, 400);
	},
	execOperateImport : function(jsonObj){//新建对象
		var manager = ManagerHelper.getManager(jsonObj["mgr"]);
		window.lastRunMethod = manager[jsonObj["operKey"] || 'import'].bind(manager);
		window.lastRunIds = 0;
		window.lastJsonObj = jsonObj;
		var type = jsonObj["mgr"].replace(/Mgr$/, '');
		if(type == 'WebSite'){
			this.callBack();
		}else{
			FloatPanel.open('/menu/select.html?method=import&type=' + type, '选择对象', 550, 400);
		}
	},
	individuate : function(path){
		var sPath = String.isString(path) ? path : "login";
		window.showModalDialog('individuation/individual.html?path='+sPath, top, "dialogHeight:400px;dialogWidth:560px;status:no;scroll:no");
	},
	showSMmList : function(){
		var sName = 'msg_list_' + (m_sUserId || Math.random());
		$openCentralWin('./communications/short_msg_list.html' + '?' + this.__getUserInfo()
			, sName, false, true);
	},
	callBack : function(ids, _oPageParams){//选择页面关闭后的回调函数
		if(window.lastRunIds != null){
			ids = window.lastRunIds;
		}
		this.formatParams(_oPageParams);
		window.lastRunMethod(ids, Object.extend(_oPageParams, window.lastJsonObj['extra'] || {}));
	},
	formatParams : function(_oPageParams, mgr, operKey){//执行方法前的参数预处理
		if(!_oPageParams) return null;
		var mgr = mgr || window.lastJsonObj["mgr"];
		var operKey = operKey || window.lastJsonObj["operKey"];

		switch(mgr){
			case "ChannelMgr": //栏目
				if(operKey == 'export'){
					if(_oPageParams['siteid'] != null){
						_oPageParams.hostPanelType = 'websiteHost';
						_oPageParams.hostObjectId = _oPageParams['siteid'];
					}else{
						_oPageParams.hostPanelType = 'channelHost';
						_oPageParams.hostObjectId = _oPageParams['channelid'];
					}
				}
				break;
			case "TemplateMgr": //模板
				if(operKey == 'new' || operKey == 'import'){
					if(_oPageParams['siteid'] != null){
						_oPageParams.hostObjectType = 'website';
						_oPageParams.hostObjectId = _oPageParams['siteid'];
					}else{
						_oPageParams.hostObjectType = 'channel';
						_oPageParams.hostObjectId = _oPageParams['channelid'];
					}
				}else if(operKey == 'export'){
					if(_oPageParams['siteid'] != null){
						_oPageParams.hostPanelType = 'templateInSite';
						_oPageParams.hostObjectId = _oPageParams['siteid'];
					}else{
						_oPageParams.hostPanelType = 'templateInChannel';
						_oPageParams.hostObjectId = _oPageParams['channelid'];
					}
				}
				break;
			case "ExtendFieldMgr": //扩展字段
				if(operKey == 'new'){
					if(_oPageParams['siteid'] != null){
						_oPageParams.hostType = '103';
						_oPageParams.hostId = _oPageParams['siteid'];
					}else if(_oPageParams['channelid'] != null){
						_oPageParams.hostType = '101';
						_oPageParams.hostId = _oPageParams['channelid'];
					}else{
						_oPageParams.hostType = '1';
						_oPageParams.hostId = _oPageParams['sitetype'];
					}
				}
				break;
			case "WorkflowMgr":
				if(operKey == 'add') {
					if(_oPageParams['siteid'] != null){
						_oPageParams.OwnerType = '103';
						_oPageParams.OwnerId = _oPageParams['siteid'];
					}else if(_oPageParams['channelid'] != null){
						_oPageParams.OwnerType = '101';
						_oPageParams.OwnerId = _oPageParams['channelid'];
					}else{
						_oPageParams.OwnerType = '1';
						_oPageParams.OwnerId = _oPageParams['sitetype'];
					}
				}
				break;
			default:
				break;
		}
		return _oPageParams;
	},
	changeSheet : function(jsonObj){//工作列表菜单项修改sheet
		$tab().setCurrentSheetAndAction(jsonObj["type"]);
	},
	listMyworks : function(_jsonObj){
		var sName = 'worklist_' + (m_sUserId || Math.random());
		$openCentralWin('./workflow/mywork_list.html?ViewType='
			+ _jsonObj["ViewType"] + '&' + this.__getUserInfo()
			, sName, false, true);
	},
	__getUserInfo : function(){
		return 'user=' + encodeURIComponent(m_sUserName)
			+ '&nick=' + encodeURIComponent(m_sUserNickName);
	}
};

Event.observe(window, 'unload', function(){
	window.lastRunMethod = null;
	window.lastRunIds = null;
	window.lastJsonObj = null;
})

