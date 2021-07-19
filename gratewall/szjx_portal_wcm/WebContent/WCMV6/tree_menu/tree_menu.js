Event.observe(window, 'load', function(){
	var allFrames = document.frames;

	if(getParameter("isdebug", top.location.search) != 1){
		//disable all iframes context menu.
		for (var i = 0; i < allFrames.length; i++){
			//main iframe deals context menu by itself, disabled in MessageCenter.
			if(allFrames[i].frameElement.id == 'main'){
				continue;
			}
	//		if(allFrames[i].frameElement.style.display == ''
	//				|| Element.getStyle(allFrames[i].frameElement, 'display') == ''){
			/*
				Event.observe(allFrames[i].document, 'contextmenu', function(event){
					Event.stop(event);
				}, false);
			*/
				allFrames[i].document.oncontextmenu = function(){return false;};
	//		}
		}

		//disable the top document context menu.
		Event.observe(document, 'contextmenu', function(event){
			Event.stop(event);
		}, false);
	}
	//create menu div for set and show menu.
	var menuDivOuter = document.createElement("div");
	menuDivOuter.className = 'contextMenu';
	menuDivOuter.style.display = 'none';
	document.body.appendChild(menuDivOuter);

	//register the menu container's mousemove event.
	Event.observe(menuDivOuter, 'mousemove', function(event){
		var element = Event.element(event);
		if(!element.getAttribute('triggerEvent')){
			return;
		}
		if(menuDivOuter.lastItem){
			Element.removeClassName(menuDivOuter.lastItem, 'contextMenuItemActive');
			Element.addClassName(menuDivOuter.lastItem, 'contextMenuItem');
		}
		Element.removeClassName(element, 'contextMenuItem');
		Element.addClassName(element, 'contextMenuItemActive');
		menuDivOuter.lastItem = element;
	});
	var menuBubble = new com.trs.wcm.BubblePanel(menuDivOuter);
	menuBubble.doAfterHide = ContextMenuDealer.dealWithClickEvent;
	window._menuInfo_ = {container : menuDivOuter, bubbleObj : menuBubble};
});

//deal with the nav_tree context menu, called by nav_tree iframe.
function dealWithNavTreeContextMenu(event){
	try{
		var element = Event.element(event);
		switch(element.tagName.toUpperCase()){
			case 'A' :
				dealTreeNode(element, event);
				break;
			default:
				var creator = new BlankMenuCreator(null);
				creator.createMenu();
				creator.showMenu(event);
		}
	}catch(error){
		//skip the error caused by web load sequence.
	}
}

//deal with click tree node-a by right mouse button.
function dealTreeNode(aNode, event){
	var divNode = aNode.parentNode;
	var idInfo = divNode.id.split("_");
	switch(idInfo[0]){
		case "r" ://deal with click root node.
			var creator = new RootMenuCreator(divNode);
			creator.createMenu();
			creator.showMenu(event);
			break;
		case "s" ://deal with click site node.
			var creator = new SiteMenuCreator(divNode);
			creator.createMenu();
			creator.showMenu(event);
			break;
		case "c" ://deal with click channel node.
			var creator = new ChannelMenuCreator(divNode);
			creator.createMenu();
			creator.showMenu(event);
			break;
		default :
			break;
	}
}

var AbstractMenuCreator = Class.create();
Object.extend(AbstractMenuCreator.prototype, {
	htmlContent : '', //the menu's html content. 

	/*
	* store current tree node info. 
	* such as rightValue, channelType, id, isVirtual and so on.
	*/
	nodeInfo : null, 
	allOperators : null,

	initialize : function(divNode){
		var operAttrPanel = $MessageCenter.getOAP();
		this.allOperators = operAttrPanel.com.trs.wcm.AllOperators;
		this.ObjectDisplayNum = operAttrPanel.CONST.ObjectDisplayNum;
		this.menuInfo = window._menuInfo_;
		if(!divNode) return;
		var idInfo = divNode.id.split("_");
		this.nodeInfo = {
			prefix		: idInfo[0],
			id			: idInfo[1],
			rightValue	: divNode.getAttribute("RV") || 0,
			channelType	: divNode.getAttribute("ChannelType") || 0,
			isVirtual	: divNode.getAttribute("IsV")
		};

		// if clicking tree root node in the first time, get the right for tree root node.
		if(divNode.getAttribute("RV") == null && idInfo[0] == 'r'){
			this.setRightValue(divNode);
		}
	}, 

	// set the right for tree root nood.
	setRightValue : function(divNode){
		var url = '/wcm/center.do?serviceid=wcm6_website&methodname=findsitetypedesc';
		url += '&CONTAINSRIGHT=true&OBJECTID=' + this.nodeInfo["id"];
		var r = new Ajax.Request(url,{
			asynchronous:false
		});
		if(r.responseIsSuccess()){
			eval("var obj = " + r.transport.responseText);
			this.nodeInfo["rightValue"] = obj["WEBSITEROOT"]["RIGHT"];
			divNode.setAttribute("RV", this.nodeInfo["rightValue"]);
		}
	},

	//create self menu items.
	createSelfMenuItems : function(){
		var selfOperators = this.allOperators[this.operType];
		if(!selfOperators) return;
		var operatorNum = 0;
		for (var i = 0; i < selfOperators.length; i++){
			if(isSameToOperatorPanel && operatorNum >= this.ObjectDisplayNum[this.operType]){
				break;
			}
			var result = this.createMenuItem(selfOperators[i], this.operType, OperatorUpdateMapping, 'forSelf');
			if(result){
				operatorNum++;
			}
		}
	},
	
	createMenuItem : function(operator, operType, mappingObj, menuType){
		if(!operator) return;
		if(operator["operKey"] == 'seperate'){
			return false;
		}
		if(this.isIgnoreMenuItem(operator, operType)){
			return false;
		}
		if(!isAccessable4WcmObject(this.nodeInfo["rightValue"], operator["rightIndex"])){
			return false;
		}
		var name = operator['operName'];
		var desc = operator['operDesc'];
		var operatorMapping = mappingObj[operType + "_" + operator["operKey"]];
		if(operatorMapping){//the menu item has the operator mapping.
			if(operatorMapping.hide){
				return false;
			}else{
				name = operatorMapping["name"];
				desc = operatorMapping["desc"];
			}
		}
		this.htmlContent += "<div title='" + desc + "' operType='" + operType + "' menuType='" + menuType + 
				"' operKey='" + operator["operKey"] + "' class='contextMenuItem' triggerEvent='true'>";
		this.htmlContent += name;
		this.htmlContent += "</div>";	
		return true;
	},

	// add a separator in menu.
	addSeparator : function(){
		this.htmlContent += "<span class='contextMenuSeparator'></span>";
	},
	
	/*
	*judge whether ignore the menu item or not.
	*if necessary, overwrite the method in subclass.
	*/
	isIgnoreMenuItem : function(operator, operType){
		return false;
	},

	//create children menu items.
	createChildMenuItems : function(){
		if(this.htmlContent != ''){
			this.addSeparator();
		}
		var operatorMapping = OperatorAddMapping[this.addType];
		for (var prop in operatorMapping){
			if(operatorMapping[prop].hideCondition && operatorMapping[prop].hideCondition(this.nodeInfo))
				continue;
			var operInfo = prop.split("_");
			var operator = null;
			var operators = this.allOperators[operInfo[0]];

			for (var i = 0; i < operators.length; i++){
				if(operators[i]["operKey"] == operInfo[1]){
					operator = operators[i];
					break;
				}
			}
			this.createMenuItem(operator, operInfo[0], operatorMapping, 'forChild');
		}
	},

	//create other menu items.
	createOtherMenuItems : function(){},

	//show the menu.
	showMenu : function(event){
		var x = Event.pointerX(event);
		var y = Event.pointerY(event);
		this.menuInfo["container"].innerHTML = this.htmlContent;
		
		var menuItems = this.menuInfo["container"].getElementsByTagName("div");
		if(menuItems.length == 0){//no menu items.
			return;
		}
		//hide the no use separator 
		var separators = this.menuInfo["container"].getElementsByTagName("span");
		for (var i = 0; i < separators.length; i++){
			var separator = separators[i];
			var nextSibling = getNextHTMLSibling(separator);
			if(!nextSibling || nextSibling.tagName.toLowerCase() == "span"){
				separator.style.display = 'none';
			}
		}
		this.menuInfo["container"].setAttribute("treeNodeInfo", this.nodeInfo);
		var navIframe = $MessageCenter.getNav().frameElement;
		var offset = Position.cumulativeOffset(navIframe);
		this.menuInfo["bubbleObj"].bubble([x+offset[0], y+offset[1]], null, document.body);
	},

	//create menu items.
	createMenu : function(){
		this.createSelfMenuItems();
		this.createChildMenuItems();
		this.createOtherMenuItems();
	}
});

var RootMenuCreator = Class.create();
Object.extend(RootMenuCreator.prototype, AbstractMenuCreator.prototype);
Object.extend(RootMenuCreator.prototype, {
	addType : "Root",
	operType : '', //identify the current oprType.

	//judge whether ignore the menu item or not.
	isIgnoreMenuItem : function(operator, operType){
		if(top.RegsiterMgr && !top.RegsiterMgr.isValidPlugin(this.nodeInfo["id"])){
			return true;
		}
		var operKey = operator["operKey"];
		if(operKey == 'confphotolib' && this.nodeInfo["id"] != 1){
			return true;
		}
		if(operKey == 'confvideolib' && this.nodeInfo["id"] != 2){
			return true;
		}
		if(operKey == 'quicknew' && this.nodeInfo["id"] != 0){
			return true;
		}
		return false;
	},

	//create other menu items.
	createOtherMenuItems : function(){
		if(top.RegsiterMgr && !top.RegsiterMgr.isValidPlugin(this.nodeInfo["id"])){
			return;
		}
		if(this.nodeInfo && this.nodeInfo["id"] == 4){
			/*
			if(this.htmlContent != ''){
				this.addSeparator();
			}
			this.htmlContent += "<div title='资源库分类法检索' class='contextMenuItem' "
			this.htmlContent += "params=\"execCommand:'retrieveByClassInfo'\" triggerEvent='true'>";
			this.htmlContent += "分类法检索";
			this.htmlContent += "</div>";	
			*/

			/*
			if(top.DeployMgr && top.DeployMgr.isDeploy('govinfo')){
				if(this.htmlContent != ''){
					this.addSeparator();
				}
				this.htmlContent += "<div title='依申请公开' class='contextMenuItem' "
				this.htmlContent += "params=\"execCommand:'showApplyFormList'\" triggerEvent='true'>";
				this.htmlContent += "依申请公开";
				this.htmlContent += "</div>";	
			}
			*/
		}		
		if(isAccessable4WcmObject(this.nodeInfo["rightValue"], RIGHT_INDEX_CONSTANT["DELETE_SITE"])){
			if(this.htmlContent != ''){
				this.addSeparator();
			}
			this.htmlContent += "<div title='站点回收站列表' class='contextMenuItem' "
			this.htmlContent += "params=\"execCommand:'toTrashBoxList'\" triggerEvent='true'>";
			this.htmlContent += "站点回收站列表";
			this.htmlContent += "</div>";						
		}
	}
});

var SiteMenuCreator = Class.create();
Object.extend(SiteMenuCreator.prototype, AbstractMenuCreator.prototype);
Object.extend(SiteMenuCreator.prototype, {
	addType : "Site",
	operType : 'website', //identify the current oprType.

	//create other menu items.
	createOtherMenuItems : function(){
		//this.addSeparator();
	}
});

var ChannelMenuCreator = Class.create();
Object.extend(ChannelMenuCreator.prototype, AbstractMenuCreator.prototype);
Object.extend(ChannelMenuCreator.prototype, {
	addType : "Channel",
	operType : 'channel', //identify the current oprType.

	//judge whether ignore the menu item or not.
	isIgnoreMenuItem : function(operator, operType){
		if(this.nodeInfo["channelType"] != 0){
			var excludeOprKeys = {
				channelMaster : ['synch'],
				channel : ['synch']
			};
			if(excludeOprKeys[operType] &&
					excludeOprKeys[operType].include(operator["operKey"])){
				return true;
			}
		}
		return false;
	},

	//create other menu items.
	createOtherMenuItems : function(){
		//this.addSeparator();
	}
});

var BlankMenuCreator = Class.create();
Object.extend(BlankMenuCreator.prototype, AbstractMenuCreator.prototype);
Object.extend(BlankMenuCreator.prototype, {
	addType : "",
	operType : '', //identify the current oprType.

	//create other menu items.
	createOtherMenuItems : function(){
		if(this.htmlContent != ''){
			this.addSeparator();
		}
		this.htmlContent += "<div title='快速定位' class='contextMenuItem' ";
		this.htmlContent += "params=\"execCommand:'quickLocate'\" triggerEvent='true'>";
		this.htmlContent += "快速定位";
		this.htmlContent += "</div>";	
		this.htmlContent += "<div title='设置定制的站点' class='contextMenuItem' ";
		this.htmlContent += "params=\"execCommand:'showTreeMoreActions'\" triggerEvent='true'>";
		this.htmlContent += "设置定制的站点";
		this.htmlContent += "</div>";		
		this.htmlContent += "<div title='刷新' class='contextMenuItem' ";
		this.htmlContent += "params=\"execCommand:'refreshTree'\" triggerEvent='true'>";
		this.htmlContent += "刷新";
		this.htmlContent += "</div>";				
	}
});


//the dealer for menu. 
var ContextMenuDealer = {
	findTarget : function(srcElement){
		while(srcElement && srcElement.nodeType == 1){
			if(srcElement.getAttribute('triggerEvent')){
				return srcElement;
			}
			srcElement = srcElement.parentNode;
		}
		return null;
	},
	//while clicking the menu item, the function dealWithClickEvent is called.
	dealWithClickEvent : function(event){
		var element = Event.element(event);
		element = ContextMenuDealer.findTarget(element);
		if(!element) return;
		var operKey = element.getAttribute("operKey");
		if(operKey){
			ContextMenuDealer.dealWithCommonItem(operKey, element);
		}else{
			try{
				eval("var params = {" + element.getAttribute("params") + "};");
				if(!params){
					alert("竟然都没有为菜单项定义params属性");
					return;
				}
			}catch(error){
				alert("菜单项定义的params的格式错误\n" + error.message);
				return;
			}
			if(params["execCommand"]){
				ContextMenuDealer[params["execCommand"]](params, element);
			}else{
				alert("竟然都没有为菜单项定义execCommand属性");
				return;
			}
		}
	},

	retrieveByClassInfo : function(){
		$openCentralWin('metadata/classinfo_view_data_list.html');
	},

	showApplyFormList : function(){
		window.open('metadata/gkml/sqgk/applyform_list.jsp');
	},

	toTrashBoxList : function(params, element){
		var treeNodeInfo = element.parentNode.getAttribute("treeNodeInfo");
		var oParams = {
			RightValue	: treeNodeInfo["rightValue"],
			SiteType	: treeNodeInfo["id"]
		}
		$MessageCenter.changeSrc("main", 'siterecycle/site_recycle_list.html?' + $toQueryStr(oParams));
		/*
		$MessageCenter.getMain().document.location = 'siterecycle/site_recycle_list.html?' + $toQueryStr(oParams);
		if((top.actualTop || top).RotatingBar) {
			(top.actualTop || top).RotatingBar.start();
		}
		*/
	},

	refreshTree : function(){
		$MessageCenter.getNav().refreshTree();
	},

	quickLocate : function(){
		$MessageCenter.getNav().quickLocate();
	},

	showTreeMoreActions : function(){
		$MessageCenter.getNav().showTreeMoreActions();
	},

	formatParams : function(_oPageParams, mgr, operKey){//执行方法前的参数预处理
		if(!_oPageParams) return null;
		if(mgr == "ChannelMgr"){//格式化栏目参数
			if(operKey == 'export'){
				_oPageParams.hostPanelType = 'channel';
				if(_oPageParams['siteid'] != null){
					_oPageParams.hostObjectId = _oPageParams['siteid'];
				}else{					
					_oPageParams.hostObjectId = _oPageParams['channelid'];			
				}				
			}
		}else if(mgr == "TemplateMgr"){//格式化模板参数
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
		}else if(mgr == "ExtendFieldMgr"){//格式化扩展字段参数
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
		}
		return _oPageParams;
	},

	//deal with the menu item which has the operKey attribute.
	dealWithCommonItem : function(operKey, element){
		var mgrName = ManagerMapping.getManagerName(element.getAttribute("operType"));
		var oMgr = ManagerHelper.getManager(mgrName);
		var treeNodeInfo = element.parentNode.getAttribute("treeNodeInfo");
		var treeWin = $MessageCenter.getNav();
		if(element.getAttribute("menuType") == 'forSelf'){
			var parentNodeInfo = treeWin.getParentNodeInfo(treeWin.$(treeNodeInfo['prefix'] + "_" + treeNodeInfo["id"]));
			if(parentNodeInfo){
				if(treeNodeInfo.prefix == 's'){
					var params = {sitetype : parentNodeInfo["id"]};
				}else if(treeNodeInfo.prefix == 'c'){
					if(parentNodeInfo.prefix == 's'){
						var params = {siteid : parentNodeInfo["id"]};
					}else{
						var params = {channelid : parentNodeInfo["id"]};
					}
				}else{
					var params = {sitetype : parentNodeInfo["id"]};
				}
			}else{
				if(treeNodeInfo['prefix'] == 'r'){
					var params = {sitetype : treeNodeInfo["id"]};
				}
			}
			if(element.getAttribute("operType") == 'channel' && operKey == 'edit'){
				operKey = 'editFromMenu';
			}
			oMgr[operKey](treeNodeInfo["id"], ContextMenuDealer.formatParams(params, mgrName, operKey));
		}else{
			if(treeNodeInfo.prefix == 's'){
				var params = {siteid : treeNodeInfo["id"]};
			}else if(treeNodeInfo.prefix == 'c'){
				var params = {channelid : treeNodeInfo["id"]};
			}else if(treeNodeInfo.prefix == 'r'){
				var params = {sitetype : treeNodeInfo["id"]};
			}
			var operType = element.getAttribute("operType");
			if((operType == 'channelHost' || operType == 'websiteHost') && operKey == 'new'){
				operKey = 'newFromMenu';
			}
			setTimeout(function(){//解决弹出窗口不在最前面的问题
				oMgr[operKey](0, ContextMenuDealer.formatParams(params, mgrName, operKey));
			}, 10);
		}
	}
};

//load the config file.
document.write('<script src="tree_menu/menu_configs.js"></script>');

RIGHT_INDEX_CONSTANT = {
	DELETE_SITE : 2
};

