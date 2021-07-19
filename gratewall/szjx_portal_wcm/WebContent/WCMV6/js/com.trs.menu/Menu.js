$package("com.trs.menu");

$import("com.trs.web2frame.AjaxRequest");
$importCSS("com.trs.menu.resource.Menu");


com.trs.menu.Menu = Class.create();

com.trs.menu.Menu.prototype = {
	menuBar : null, //标识菜单的dom对象
	oPreviousItem : null,	//标识前一次访问的菜单项
	currStatus : false, //标识菜单当前状态,是展开还是关闭
	enableHotKey : true, //是否激活快捷键

	initialize : function(menuBar){
		this.menuBar = menuBar;
		if(_IE){
			Event.observe(window, "resize", this.onResize.bind(this), false);
			Event.observe(window, "focus", this.onfocus.bind(this), false);
		}else{
			menuBar.tabIndex = 1;			
		}
		Event.observe(menuBar, "mousemove", this.onMouseMove.bind(this), false);
		Event.observe(menuBar, "mouseout", this.onMouseOut.bind(this), false);
		Event.observe(menuBar, "click", this.onClick.bind(this), false);
		Event.observe(menuBar, "blur", this.onBlur.bind(this), false);	
		if(this.enableHotKey){
			Event.observe(document, 'keydown', this.onKeyDown.bind(this), false);
		}
	},
	onMouseMove : function(event){ 
		var oSrcElement = this.dealSrcElement(event);
		if(oSrcElement == null || oSrcElement == this.oPreviousItem){
			return;
		}

		if(this.oPreviousItem){
			if(this.oPreviousItem.parentNode == oSrcElement.parentNode.parentNode.parentNode){
			}else{
				this.hideToSameParent(oSrcElement);
			}
		}
		this.oPreviousItem = oSrcElement; 
		if(this.currStatus){
			this.showItems(oSrcElement);
		}else{
			Element.removeClassName(oSrcElement, "menu_out");	
			Element.addClassName(oSrcElement, "item_over");
		}
	},
	onMouseOut : function(event){
		if(this.oPreviousItem == null) return;
		var oSrcElement = this.dealSrcElement(event);
		if(oSrcElement == null){
			return;
		}
		var contentNode = this.getNextHTMLSibling(oSrcElement);
		if(contentNode == null || !this.currStatus){//没有子菜单或则有子菜单但处于隐藏状态
			Element.removeClassName(oSrcElement, "item_over");
			if(oSrcElement.className.indexOf("menuDesc") >= 0){
				Element.addClassName(oSrcElement, "menu_out");	
			}else{
				Element.addClassName(oSrcElement, "item_out");	
			}
			if(Element.hasClassName(oSrcElement, "menuDesc")){
				this.oPreviousItem = null;
			}else{
				//this.oPreviousItem = this.oPreviousItem.parentNode.parentNode;
				this.oPreviousItem = oSrcElement.parentNode.parentNode;
				if(this.oPreviousItem.parentNode){
					this.oPreviousItem = this.getFirstHTMLChild(this.oPreviousItem.parentNode);
				}	
			}
		}
	},
	onClick : function(event){
		var oSrcElement = this.dealSrcElement(event);
		if(oSrcElement == null){
			return;
		}
		if(Element.hasClassName(oSrcElement, "menuDesc")){
			this.menuBar.focus();
			this.currStatus = !this.currStatus;
			var contentNode = this.getNextHTMLSibling(oSrcElement);
			if(contentNode == null)return;
			if(this.currStatus){
				this.parseContentItems(contentNode);
				this.createCover(contentNode);
			}else{
				contentNode.style.display = 'none';				
			}
		}else{
			var contentNode = this.getNextHTMLSibling(oSrcElement);
			if(contentNode != null){
				this.menuBar.focus();
				return;
			}else{//执行相应操作
				this.clickItem(oSrcElement);
			}
		}
	},
	clickItem : function(itemNode){
		if(Element.hasClassName(itemNode, 'disabled_item')) return;
		if(itemNode.className.indexOf("moreItem") >= 0){
			//alert("单击了显示更多的操作！");	
			tempNode = itemNode;
			while(tempNode = this.getNextHTMLSibling(tempNode.parentNode)){
				tempNode = this.getFirstHTMLChild(tempNode);
				if(!tempNode) break;
				tempNode.style.display = '';
			}
			itemNode.style.display = 'none';
		}else{
			this.hideToTop();
			this.oPreviousItem = null;
			this.currStatus = false;
			var jsonObj = this.getItemJsonObj(itemNode);
			this.executeItemCommand(jsonObj, itemNode);
		}				
	},
	executeItemCommand : function(jsonObj, itemNode){
		alert("请实现executeItemCommand方法\n"+itemNode.outerHTML);
	},
	getItemJsonObj : function(itemNode){
		var params = itemNode.getAttribute("params");
		if(params == null){
			return undefined;
		}else{
			var jsonObj = null;
			try{
				eval("jsonObj = {" + params + "};");
				return jsonObj;
			}catch(error){
				alert("菜单的链接参数：" + params + "格式错误\n" + error.message);
				return null;
			}
		}
	},
	parseContentItems : function(contentNode){//设置菜单项是否为disabled状态，是否为checkbox型，等等
		contentNode.style.width = 'auto';
		var firstChild = this.getFirstHTMLChild(contentNode);
		while(firstChild){
			if(firstChild.getAttribute("loaded") == "false"){//需要动态载入菜单项
				this.setItems(firstChild, this.loadDynamicItem(firstChild) || '');//设置菜单项
			}else{
				this.setItemStyle(firstChild);
				var descChild = this.getFirstHTMLChild(firstChild);
				if(window._hideStart_){
					descChild.style.display = 'none';
				}
				if(descChild.className.indexOf("moreItem") >= 0){
					window._hideStart_ = true;
					Element.show(descChild);
				}
			}
			firstChild = this.getNextHTMLSibling(firstChild);
		}
		window._hideStart_ = false;
	},
	loadDynamicItem : function(itemNode){
		alert("请实现动态载入菜单的方法！");
	},
	setItemStyle : function(itemNode){
		//alert("如果需要实现菜单项的特殊样式，请重载setItemStyle方法！");
	},
	showItems : function(itemNode){
		this.menuBar.focus();
		if(itemNode.className.indexOf("menuDesc") >= 0){
			Element.removeClassName(itemNode, "menu_out");
		}else{
			Element.removeClassName(itemNode, "item_out");
		}
		Element.addClassName(itemNode, "item_over");

		var contentNode = this.getNextHTMLSibling(itemNode);
		if(contentNode){
			this.parseContentItems(contentNode);
			this.createCover(contentNode);
		}			
		this.oPreviousItem = itemNode;
		this.currStatus = true;
	},
	createCover : function(source){
		source.style.visibility = 'hidden';
		source.style.display = '';
		setTimeout(function(){
			if(!source.getAttribute("createCovered")){
				source.setAttribute("createCovered", true);
				var divDom = document.createElement("div");					
				divDom.setAttribute("ignore", true);
				divDom.style.position = 'absolute';		
				divDom.style.left = "0px";
				divDom.style.top = "0px";
				divDom.style.height = source.clientHeight;
				divDom.style.width = source.clientWidth;
				divDom.style.overflow = 'hidden';
				divDom.style.zIndex = source.style.zIndex-1;
				var iframeDom = document.createElement("iframe");
				iframeDom.src = '';		
				iframeDom.style.height = "100%";
				iframeDom.style.width = "100%";	
				iframeDom.frameBorder = 0;
				iframeDom.scrolling = 'no';
				divDom.appendChild(iframeDom);
				source.appendChild(divDom);	
				Element.addClassName(source, 'item_out');
			}else{
				var divDom = this.getLastHTMLChild(source);
				divDom.style.height = source.clientHeight;
				divDom.style.width = source.clientWidth;
			}	
			source.style.width = source.offsetWidth;
			source.style.visibility = 'visible';
		}, 10);
	},
	hideToTop : function(){
		this.hideToSameParent(this.getFirstHTMLChild(this.getFirstHTMLChild(this.menuBar)));
	},
	onBlur : function(event){
		var notMenuBar = true;
		var activeElement = document.activeElement;
		while(activeElement && activeElement.tagName != "BODY"){
			if(activeElement == this.menuBar){
				notMenuBar = false;
				break;
			}else{
				activeElement = activeElement.parentNode;
			}
		}
		if(notMenuBar){
			this.hideToTop();
			this.currStatus = false;
			this.oPreviousItem = null;
		}
	},
	onResize : function(event){
		if(this.oPreviousItem != null){
			this.hideToTop();
			this.currStatus = false;
			this.oPreviousItem = null;
		}
	},
	onfocus : function(event){
		//this.menuBar.focus();
		this.onResize();
	},
	onKeyDown : function(event){
		event = window.event || event;
		var charValue = String.fromCharCode(event.keyCode).toUpperCase();
		if(event.altKey){
			var menuDom = this.getFirstHTMLChild(this.menuBar);
			while(menuDom){
				var menuDescDom = this.getFirstHTMLChild(menuDom);
				var hotKeyContent = this.getFirstHTMLChild(menuDescDom);
				if(hotKeyContent && hotKeyContent.innerText == charValue){
					if(this.currStatus && menuDescDom.className.indexOf("item_over") >= 0){

					}else{
						this.hideToTop();
						setTimeout(function(){
							this.currStatus = true;
							this.oPreviousItem = menuDescDom;
							this.showItems(menuDescDom);	
						}.bind(this), 100);						
					}
					break;
				}
				menuDom = this.getNextHTMLSibling(menuDom);
			}
		}else{
			switch(event.keyCode){
				case Event.KEY_UP:
					this.keyTop();
					break;
				case Event.KEY_DOWN:
					this.keyBottom();
					break;
				case Event.KEY_LEFT:
					this.keyLeft();
					break;
				case Event.KEY_RIGHT:
					this.keyRight();
					break;
				case Event.KEY_RETURN:
					this.keyReturn(event);
					break;
				default:
			}
		}
	},
	keyTop : function(){
		if(this.oPreviousItem == null) return;	
		if(this.oPreviousItem.className.indexOf("menuDesc") >= 0){
			var nextItemContentDom = this.getNextHTMLSibling(this.oPreviousItem);
			var nextItemDescDom = this.getFirstHTMLChild(this.getLastHTMLChild(nextItemContentDom));
			nextItemDescDom = this.getRealItemDesc(nextItemDescDom, 'previous');
			if(nextItemDescDom == null) return;
			this.showItems(nextItemDescDom);
		}else{
			var nextItemDom =  this.getPreviousHTMLSibing(this.oPreviousItem.parentNode);
			if(nextItemDom == null){//定位同级第一个
				nextItemDom = this.getLastHTMLChild(this.oPreviousItem.parentNode.parentNode);
			}
			var nextItemDescDom = this.getFirstHTMLChild(nextItemDom);
			nextItemDescDom = this.getRealItemDesc(nextItemDescDom, 'previous');
			if(nextItemDescDom == null){
				nextItemDom = this.getLastHTMLChild(this.oPreviousItem.parentNode.parentNode);
				nextItemDescDom = this.getRealItemDesc(this.getFirstHTMLChild(nextItemDom), 'previous');
			}
			if(nextItemDescDom != this.oPreviousItem){
				this.hideToSameParent(nextItemDescDom);
				this.showItems(nextItemDescDom);
				this.oPreviousItem = nextItemDescDom;
			}		
		}		
	},
	keyBottom : function(){
		if(this.oPreviousItem == null) return;		
		if(this.oPreviousItem.className.indexOf("menuDesc") >= 0){
			var nextItemContentDom = this.getNextHTMLSibling(this.oPreviousItem);
			var nextItemDescDom = this.getFirstHTMLChild(this.getFirstHTMLChild(nextItemContentDom));
			nextItemDescDom = this.getRealItemDesc(nextItemDescDom, 'next');
			if(nextItemDescDom == null) return;
			this.showItems(nextItemDescDom);
		}else{
			var nextItemDom =  this.getNextHTMLSibling(this.oPreviousItem.parentNode);
			if(nextItemDom == null){//定位同级第一个
				nextItemDom = this.getFirstHTMLChild(this.oPreviousItem.parentNode.parentNode);
			}
			var nextItemDescDom = this.getFirstHTMLChild(nextItemDom);
			nextItemDescDom = this.getRealItemDesc(nextItemDescDom, 'next');
			if(nextItemDescDom == null){
				nextItemDom = this.getFirstHTMLChild(this.oPreviousItem.parentNode.parentNode);
				nextItemDescDom = this.getRealItemDesc(this.getFirstHTMLChild(nextItemDom), 'next');
			}
			if(nextItemDescDom != this.oPreviousItem){
				this.hideToSameParent(nextItemDescDom);
				this.showItems(nextItemDescDom);
				this.oPreviousItem = nextItemDescDom;
			}		
		}
	},
	keyLeft : function(){
		if(this.oPreviousItem == null) return;		
		if(this.oPreviousItem.className.indexOf("menuDesc") >= 0){
			var nextItemDom = this.getPreviousHTMLSibing(this.oPreviousItem.parentNode);
			if(nextItemDom == null){
				nextItemDom = this.getLastHTMLChild(this.oPreviousItem.parentNode.parentNode);
			}
			var nextItemDescDom = this.getFirstHTMLChild(nextItemDom);
			if(nextItemDescDom != this.oPreviousItem){
				this.hideToTop();
				this.showItems(nextItemDescDom);
			}
		}else{
			this.hideToSameParent(this.oPreviousItem);
			if(this.oPreviousItem.className.indexOf("menuDesc") >= 0){
				this.keyLeft();
			}		
		}
	},
	keyRight : function(){
		if(this.oPreviousItem == null) return;		
		if(this.oPreviousItem.className.indexOf("menuDesc") >= 0){
			var nextItemDom = this.getNextHTMLSibling(this.oPreviousItem.parentNode);
			if(nextItemDom == null){
				nextItemDom = this.getFirstHTMLChild(this.oPreviousItem.parentNode.parentNode);
			}
			var nextItemDescDom = this.getFirstHTMLChild(nextItemDom);
			if(nextItemDescDom != this.oPreviousItem){
				this.hideToTop();
				this.showItems(nextItemDescDom);
			}
		}else{
			var nextItemContentDom = this.getNextHTMLSibling(this.oPreviousItem);
			if(nextItemContentDom == null){//定位同级第一个
				nextItemContentDom = this.oPreviousItem
				while(!Element.hasClassName(nextItemContentDom, "menu")){
					nextItemContentDom = nextItemContentDom.parentNode;
				}
				this.hideToTop();
				this.oPreviousItem = this.getFirstHTMLChild(nextItemContentDom);
				this.keyRight();
			}else{
				var nextItemDescDom = this.getFirstHTMLChild(this.getFirstHTMLChild(nextItemContentDom));
				nextItemDescDom = this.getRealItemDesc(nextItemDescDom, 'next');
				if(nextItemDescDom == null) return;
				this.showItems(nextItemDescDom);				
			}		
		}		
	},
	keyReturn : function(event){
		this.clickItem(this.oPreviousItem);
		Event.stop(event);
	},
	isRealItemDesc : function(descNode){
		if(descNode == null) return false;
		if(descNode.parentNode.getAttribute('ignore') || descNode.className.indexOf("separate") >= 0){
			return false;
		}
		if(descNode.style.display == 'none'){
			return false;
		}
		return true;
	},
	/*
	*	从当前节点开始，直到找到一个有意义的菜单项节点
	*	forward为next表示向下查找,为previous表示向上查找
	*/
	getRealItemDesc : function(descNode, forward){
		var forwardFun = (forward == 'previous' ? this.getPreviousHTMLSibing : this.getNextHTMLSibling)
		while(descNode && !this.isRealItemDesc(descNode)){
			var nextItemDom = forwardFun(descNode.parentNode);
			descNode = this.getFirstHTMLChild(nextItemDom);
		}	
		return descNode;
	}, 
	hideToSameParent : function(oSrcElement){//在菜单树中，一直向上隐藏，直到达到oSrcElement同一级水平
		if(this.oPreviousItem == null)return;
		var oSrcParent = oSrcElement.parentNode.parentNode;
		var oPreviousParent = null;
		do{
			Element.removeClassName(this.oPreviousItem, "item_over");
			if(this.oPreviousItem.className.indexOf("menuDesc") >= 0){
				Element.addClassName(this.oPreviousItem, "menu_out");	
			}else{
				Element.addClassName(this.oPreviousItem, "item_out");	
			}
			var contentNode = this.getNextHTMLSibling(this.oPreviousItem);
			if(contentNode){
				contentNode.style.display = 'none';
			}
			oPreviousParent = this.oPreviousItem.parentNode.parentNode;
			if(Element.hasClassName(oPreviousParent, 'menuBar')){
				break;
			}
			this.oPreviousItem = this.getFirstHTMLChild(oPreviousParent.parentNode);
		}while(oSrcParent != oPreviousParent && oPreviousParent);
	},
	getNextHTMLSibling: function(node){//得到当前节点的下一个HTML节点
		if(node == null)return null;
		var contentNode = node.nextSibling;
		while(contentNode && contentNode.nodeType != 1){
			contentNode = contentNode.nextSibling;
		}
		return contentNode;
	},
	getPreviousHTMLSibing: function(node){//得到当前节点的前一个HTML节点
		if(node == null)return null;
		var contentNode = node.previousSibling;
		while(contentNode && contentNode.nodeType != 1){
			contentNode = contentNode.previousSibling;
		}
		return contentNode;
	},
	getFirstHTMLChild : function(node){//得到当前节点的第一个HTML孩子节点
		if(node == null)return null;
		var tempNode = null;
		for (var i = 0; i < node.childNodes.length; i++){
			if(node.childNodes[i].nodeType == 1){
				tempNode = node.childNodes[i];
				break;
			}
		}		
		return tempNode;
	},
	getLastHTMLChild : function(node){//得到当前节点的最后一个HTML孩子节点
		if(node == null)return null;
		var tempNode = null;
		for (var i = node.childNodes.length-1; i >= 0; i--){
			if(node.childNodes[i].nodeType == 1){
				tempNode = node.childNodes[i];
				break;
			}
		}		
		return tempNode;
	},

	//请求到达后，动态更新菜单项
	setItems : function(itemNode, content){
		//载入的节点为itemNode的兄弟节点
		var tempNode = this.getNextHTMLSibling(itemNode);
		var otherNode = null;
		while(tempNode){//删除原先载入的节点
			if(tempNode.getAttribute("dynamic") == null){
				break;
			}
			otherNode = this.getNextHTMLSibling(tempNode);
			$removeNode(tempNode);
			tempNode = otherNode;
		}
		//更新节点
		new Insertion.After(itemNode, content);
		//修改载入标记，以便下次处理
		if(itemNode.getAttribute("loadTime") == 'once'){
			itemNode.setAttribute('loaded', true);
		}
	},
	isOpened : function(){//判断当前菜单是否处于打开状态
		var isOpened = false;
		var menuDom = this.getFirstHTMLChild(this.menuBar);
		while(menuDom){
			var menuDescDom = this.getFirstHTMLChild(menuDom);
			if(menuDescDom.className.indexOf("item_over") >= 0){
				isOpened = true;
				break;
			}
			menuDom = this.getNextHTMLSibling(menuDom);
		}	
		return isOpened;
	},
	dealSrcElement : function(event){
		event = window.event || event;
		var oSrcElement = Event.element(event);
		if(oSrcElement.className.indexOf("Desc") < 0){
			if(oSrcElement.className.indexOf("hotKey") >= 0 
					|| oSrcElement.className.indexOf("radioItem") >= 0){
				oSrcElement = oSrcElement.parentNode;
			}else{
				var tempNode = this.getFirstHTMLChild(oSrcElement);
				if(!tempNode || tempNode.className.indexOf("Desc") < 0)
					return null;
				oSrcElement = tempNode;
			}
		}
		return oSrcElement;
	},
	addCover : function(container){
	},
	removeCover : function(container){
	}
}

/////////////////////////////////////////////////////////////////////////////////////
//初始化页面所有菜单的全局对象
com.trs.menu.globalMenuInitialler = new Object();

Object.extend(com.trs.menu.globalMenuInitialler, {
	initMenu : function(){

		//获得页面上所有菜单
		var menuBarArray = document.getElementsByClassName("menuBar");
		(top.actualTop||top).menuControllers = [];
		for (var i = 0; i < menuBarArray.length; i++){
			var menuBar = menuBarArray[i];
			var menuController = new com.trs.menu.Menu(menuBar);
			(top.actualTop||top).menuControllers.push(menuController);
		}
	}
});

Event.observe(window, "load", function(){
	com.trs.menu.globalMenuInitialler.initMenu();
}, false);

Event.observe(window, 'unload', function(){
	try{
		var menuCons = (top.actualTop||top).menuControllers;
		if(!menuCons || menuCons.length <= 0) return;
		for (var i = 0; i < menuCons.length; i++){
			menuCons[i].menuBar = null;
			menuCons[i].oPreviousItem = null;
			menuCons[i] = null;
		}
		(top.actualTop||top).menuControllers = null;
	}catch(error){
		//just skip it.
	}
});
