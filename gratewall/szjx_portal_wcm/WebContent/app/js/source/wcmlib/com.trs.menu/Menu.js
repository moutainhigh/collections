Ext.ns("com.trs.menu");

com.trs.menu.Menu = function(menuBar){
	this.menuBar = menuBar;
	if(Ext.isIE){
		Event.observe(window, "resize", this.onResize.bind(this), false);
		Event.observe(window, "focus", this.onfocus.bind(this), false);
	}else if(Ext.isGecko){
		menuBar.tabIndex = 1;			
	}
	Event.observe(menuBar, "mousemove", this.onMouseMove.bind(this), false);
	Event.observe(menuBar, "mouseout", this.onMouseOut.bind(this), false);
	Event.observe(menuBar, "click", this.onClick.bind(this), false);
	if(!Ext.isSafari && !Ext.isGecko){
		Event.observe(menuBar, "blur", this.onBlur.bind(this), false);	
	}else{
		this.blurHide = new BlurHide(menuBar, this.onBlur.bind(this));
	}
	if(this.enableHotKey){
		Event.observe(document, 'keydown', this.onKeyDown.bind(this), false);
	}
};

Ext.apply(com.trs.menu.Menu.prototype, {
	menuBar : null, //标识菜单的dom对象
	oPreviousItem : null,	//标识前一次访问的菜单项
	currStatus : false, //标识菜单当前状态,是展开还是关闭
	enableHotKey : true, //是否激活快捷键
	itemMinWidth : 120,//子菜单项的最小宽度

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
			Element.removeClassName(oSrcElement, "item_out");	
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
			Element.addClassName(oSrcElement, "item_out");	
			if(this.isMenuDesc(oSrcElement)){
				this.oPreviousItem = null;
			}else{
				this.oPreviousItem = oSrcElement.parentNode.parentNode;
				if(this.oPreviousItem.parentNode){
					this.oPreviousItem = this.getFirstHTMLChild(this.oPreviousItem.parentNode);
				}	
			}
		}
	},
	isMenuDesc : function(oElement){
		return Element.hasClassName(oElement, "menuDesc") && !Element.hasClassName(oElement.parentNode, "item");
	},
	onClick : function(event){
		var oSrcElement = this.dealSrcElement(event);
		if(oSrcElement == null){
			return;
		}
		if(this.isMenuDesc(oSrcElement)){
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
			//if(contentNode != null){
			if(!this.isEmptyContent(contentNode)){
				this.menuBar.focus();
				return;
			}else{//执行相应操作
				this.clickItem(oSrcElement);
			}
		}
	},
	isEmptyContent : function(contentNode){
		return !this.getFirstHTMLChild(contentNode);
	},
	clickItem : function(itemNode){
		if(Element.hasClassName(itemNode, 'disabled')) return;
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
			this.executeItemCommand(itemNode);
		}				
	},
	executeItemCommand : function(jsonObj, itemNode){
		alert("请实现executeItemCommand方法\n"+itemNode.outerHTML);
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
		alert(wcm.LANG.Menu_4569 || "请实现动态载入菜单的方法！");
	},
	setItemStyle : function(itemNode){
		//alert("如果需要实现菜单项的特殊样式，请重载setItemStyle方法！");
	},
	showItems : function(itemNode){
		if(!Ext.isSafari && !Ext.isGecko){
			this.menuBar.focus();
		}else{
			this.blurHide.show();
		}
		Element.removeClassName(itemNode, "item_out");
		Element.addClassName(itemNode, "item_over");

		if(this.isMenuDesc(itemNode) || Element.hasClassName(itemNode, "hasChild")){
			var contentNode = this.getNextHTMLSibling(itemNode);
			if(contentNode){
				this.parseContentItems(contentNode);
				this.createCover(contentNode);
			}			
		}
		this.oPreviousItem = itemNode;
		this.currStatus = true;
	},
	createCover : function(source){
		source.style.visibility = 'hidden';
		source.style.display = '';
		setTimeout(function(){
			//比较source内容的宽度和设置的最小宽度的值，两者取较大的值赋给source的样式中的宽度
			var nWidth = Math.max(parseInt(source.offsetWidth, 10), this.itemMinWidth);
			source.style.width = nWidth + "px";
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
		}.bind(this), 10);
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
			Element.addClassName(this.oPreviousItem, "item_out");	
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
	//请求到达后，动态更新菜单项
	setItems : function(itemNode, content){
		//载入的节点为itemNode的兄弟节点
		var tempNode = this.getNextHTMLSibling(itemNode);
		var otherNode = null;
		while(tempNode){//删除原先载入的节点
			if(tempNode.getAttribute("dynamic") == null){
				if(!Element.hasClassName(this.getFirstHTMLChild(tempNode), "separate")){
					break;
				}
			}
			otherNode = this.getNextHTMLSibling(tempNode);
			Ext.removeNode(tempNode);
			tempNode = otherNode;
		}
		//更新节点
		new Insertion.After(itemNode, content);	
		//Element[content.trim().length == 0 ? "addClassName" : "removeClassName"](itemNode.parentNode, 'noChild');
		Element[content.trim().length == 0 ? "addClassName" : "removeClassName"](itemNode, 'noChild');
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
	dealSrcElement : function(event){
		event = window.event || event;
		var oSrcElement = Event.element(event);
		while(oSrcElement){
			if(oSrcElement.className.indexOf("Desc") >= 0){
				return oSrcElement;
			}
			if(oSrcElement.className.indexOf("menuBar") >= 0){
				break;
			}
			oSrcElement = oSrcElement.parentNode;
		}
		return null;
	},
	addCover : function(container){
	},
	removeCover : function(container){
	}
});


/**
*菜单键盘事件的相关处理
*/
Ext.apply(com.trs.menu.Menu.prototype, {
	onKeyDown : function(event){
		event = window.event || event;
		var charValue = String.fromCharCode(event.keyCode).toUpperCase();
		if(event.altKey){
			var menuDom = this.getFirstHTMLChild(this.menuBar);
			while(menuDom){
				var menuDescDom = this.getFirstHTMLChild(menuDom);
				var hotKeyContent = this.getFirstHTMLChild(menuDescDom);
				if(hotKeyContent && (hotKeyContent.innerText 
					|| hotKeyContent.textContent || hotKeyContent.innerHTML) == charValue){
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
		if(this.isMenuDesc(this.oPreviousItem)){
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
		if(this.isMenuDesc(this.oPreviousItem)){
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
	count : 0,
	keyLeft : function(){
		if(this.oPreviousItem == null) return;		
		this.count++;
		if(this.count > 100){
			throw "dasf";
		}
		if(this.isMenuDesc(this.oPreviousItem)){
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
			if(this.isMenuDesc(this.oPreviousItem)){
				this.keyLeft();
			}		
		}
	},
	keyRight : function(){
		if(this.oPreviousItem == null) return;		
		if(this.isMenuDesc(this.oPreviousItem)){
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
				
				while(!(Element.hasClassName(nextItemContentDom, "menu") 
					&& !Element.hasClassName(nextItemContentDom, "item"))){
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
	}
});


/**
*Dom遍历相关操作
*/
Ext.apply(com.trs.menu.Menu.prototype, {
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
	}
});


/////////////////////////////////////////////////////////////////////////////////////
/**
*菜单视图对象，将json配置信息转化成页面上的html内容，以处理菜单项的动态注册
*/
com.trs.menu.MenuView = function(){
	this.orphanCache = [];
	this.cache = {};
	var rootItem = {key:this.ROOT_KEY};
	rootItem[this.INNER_ITEMS_KEY] = [];
	this.put(rootItem);
};

Ext.apply(com.trs.menu.MenuView, {
	/**
	*获取到下一个可用的key
	*/
	getKey : function(){
		var index = (arguments.callee.index || 0) + 1;
		arguments.callee.index = index;
		return "MENU_KEY_" + index;
	}
});


Ext.apply(com.trs.menu.MenuView.prototype, {
	ROOT_KEY : '_ROOT_',
	INNER_ITEMS_KEY : '_ITEMS_',

	getConfig : function(){
		return this.get(this.ROOT_KEY)[this.INNER_ITEMS_KEY];
	},

	getKey : function(){
		return com.trs.menu.MenuView.getKey();
	},

	/**
	*cache relations
	*/
	remove : function(_sKey){
		var oItem = this.cache[_sKey.toUpperCase()];
		delete this.cache[_sKey.toUpperCase()];
		return oItem;
	},
	put : function(_oItem){
		if(!_oItem["key"]){
			_oItem["key"] = this.getKey();
		}
		_oItem["key"] = _oItem["key"].toUpperCase();
		this.cache[_oItem["key"]] = _oItem;
	},
	get : function(_sKey){
		return this.cache[_sKey.toUpperCase()];
	},


	handleOrder : function(_oItem){
		var oParentItem = this.get(_oItem["parent"]);
		var nMaxChildOrder = 0;
		if(oParentItem && oParentItem.MAX_CHILD_INDEX){
			 nMaxChildOrder = oParentItem.MAX_CHILD_INDEX;
		}
		if(_oItem["order"] == null){
			_oItem["order"] = nMaxChildOrder + 1;
		}
		if(_oItem["order"] > nMaxChildOrder){
			if(oParentItem){
				oParentItem.MAX_CHILD_INDEX = _oItem["order"];
			}
		}
	},

	/**
	*register relations
	*/
	beforeRegister : function(_oItem, _sParentKey){
		//handle the key of parent.
		if(!_oItem["parent"]){
			_oItem["parent"] = _sParentKey || this.ROOT_KEY;
		}

		//handle the order.
		this.handleOrder(_oItem);

		//store the items to _items_
		_oItem[this.INNER_ITEMS_KEY] = [];
		if(Array.isArray(_oItem["items"])){
			_oItem[this.INNER_ITEMS_KEY] = _oItem["items"];
		}

		//put to the cache.
		this.put(_oItem);

		//recur the child items.
		if(!_oItem[this.INNER_ITEMS_KEY] || !Array.isArray(_oItem[this.INNER_ITEMS_KEY])){
			return;
		}
		
		var items = _oItem[this.INNER_ITEMS_KEY];
		for (var i = 0; i < items.length; i++){
			this.beforeRegister(items[i], _oItem["key"]);
		}
	},
	register : function(_oItem){
		if(!_oItem){
			return;
		}
		if(Array.isArray(_oItem)){
			for (var i = 0, length = _oItem.length; i < length; i++){
				this.register(_oItem[i]);
			}
			return;
		}

		this.beforeRegister(_oItem);
		if(this.attach(_oItem) === false){
			//store the no parent node to orphanCache and attach where rendering
			this.orphanCache.push(_oItem);
		}
	},	
	unregister : function(_sItemKey){
		if(!_sItemKey) return;
		_sItemKey = _sItemKey.toUpperCase();
		this.detach(_sItemKey);
		this.remove(_sItemKey);
	},

	//attach the Orphan nodes to tree
	attachOrphan : function(){
		var cache = this.orphanCache;
		for (var i = 0; i < cache.length; i++){
			this.attach(cache[i]);
		}
		this.orphanCache = [];
	},

	/**
	*tree operator relations
	*/
	attach : function(_oItem){
		var oParentItem = this.findParent(_oItem["key"]);
		if(!oParentItem) return false;
		oParentItem[this.INNER_ITEMS_KEY].push(_oItem);
	},
	detach : function(_sItemKey){
		var oParentItem = this.findParent(_sItemKey);
		if(!oParentItem) return;
		var aItems = oParentItem[this.INNER_ITEMS_KEY];
		if(!aItems || !Array.isArray(aItems)) return;
		for (var i = 0; i < aItems.length; i++){
			if(aItems[i].key == _sItemKey){
				break;
			}
		}
		if(i == aItems.length) return;
		oParentItem[this.INNER_ITEMS_KEY].splice(i, 1);		
	},

	/**
	*根据_sItemKey找到父对象
	*/
	findParent : function(_sItemKey){
		if(!_sItemKey) return null;
		var oCurrItem = this.get(_sItemKey);
		if(!oCurrItem) return null;
		var oParentItem = this.get(oCurrItem["parent"]);
		return oParentItem;
	},

	/**
	*找到key为_sItemKey的项在父项中的位置，否则返回-1
	*/
	findPositionInItems : function(_sItemKey){
		if(!_sItemKey) return -1;
		_sItemKey = _sItemKey.toUpperCase();
		var oCurrItem = this.get(_sItemKey);
		if(!oCurrItem) return -1;
		var oParentItem = this.get(oCurrItem["parent"]);
		if(!oParentItem) return -1;
		var aItems = oParentItem[this.INNER_ITEMS_KEY];
		if(!aItems || !Array.isArray(aItems)) return -1;
		for (var i = 0; i < aItems.length; i++){
			if(aItems[i].key == _sItemKey){
				return i;
			}
		}
		return -1;
	}
});

Ext.apply(com.trs.menu.MenuView.prototype, {
	/**
	*菜单项快捷键使用的替换模板
	*/
	hotKeyTemplate : ' (<span class="hotKey">{0}</span>)',

	/**
	*菜单分隔线模板
	*/
	separateTemplate : '<div class="menu item"><div class="separate"></div></div>',

	/**
	*菜单项使用的替换模板
	*/
	menuTemplate : [
		'<div class="menu{0}" {6}>',
			'<div class="menuDesc{1}" key="{2}">{3}{4}</div>',
			'<div class="menuContent" style="display:none;">{5}</div>',
		'</div>'
	].join(""),

	/**
	*需要动态加载的菜单模板
	*/
	dynamicMenuTemplate : [
		'<div {0} style="display:none;">',
			'<div></div>',
		'</div>'
	].join(""),

	render : function(renderTo){
		this.attachOrphan();
		renderTo = renderTo || document.body;	
		Element.update(renderTo, this.parse());
	},

	object2string : function(_obj){
		var aResult = [];
		for(var prop in _obj){
			aResult.push(prop + "=\"" + _obj[prop] + "\"");
		}
		return aResult.join(" ");
	},

	sort : function(_oItem1, _oItem2){
		return _oItem1["order"] - _oItem2["order"];
	},
	parse : function(_aConfig){
		var aHTML = [];
		var aConfigs = _aConfig || this.getConfig();
		aConfigs.sort(this.sort);
		for (var i = 0; i < aConfigs.length; i++){
			var oConfig = aConfigs[i];

			switch(oConfig.type){
				case 'separate':
					aHTML.push(this.separateTemplate);
					break;
				case 'dynamic':
					var oOptions = {						
						key : oConfig["key"],
						loaded : 'false',
						//loadTime : "everyTime",
						ignore : 1
					};
					Ext.apply(oOptions, oConfig.params || {});
					aHTML.push(String.format(this.dynamicMenuTemplate, this.object2string(oOptions)));
					break;
				default:
					var sDesc = oConfig["desc"] || "";
					var sExtraCls = "";
					if(oConfig["cls"] && Ext.isString(oConfig["cls"])){
						sExtraCls = " " + oConfig["cls"];
					}
					if(oConfig.type == "checkItem"){
						sExtraCls += " " + oConfig.type;
					}else if(oConfig.type == "radioItem"){
						sExtraCls += " " + oConfig.type;
						sDesc = "<li>" + sDesc + "</li>";
					}
					aHTML.push(
						String.format(
							this.menuTemplate,
							_aConfig ? " item" : "",
							(_aConfig && oConfig[this.INNER_ITEMS_KEY].length > 0 ? " hasChild" : "") + sExtraCls,
							oConfig["key"],
							sDesc,
							oConfig["hotKey"] ? String.format(this.hotKeyTemplate, oConfig["hotKey"]) : "",
							oConfig[this.INNER_ITEMS_KEY].length > 0  ? this.parse(oConfig[this.INNER_ITEMS_KEY]) : "",
							this.object2string(oConfig["params"])
						)
					);
			}
		}
		return aHTML.join("");
	}	
});



/////////////////////////////////////////////////////////////////////////////////////
//菜单初始化对象
com.trs.menu.MenuInitialler = new Object();

Ext.apply(com.trs.menu.MenuInitialler, {
	menuControllers : [],
	init : function(menuBar){
		if(Array.isArray(menuBar)){
			for (var i = 0; i < menuBar.length; i++){
				this.init(menuBar[i]);
			}
			return;
		}
		this.menuControllers.push(new com.trs.menu.Menu($(menuBar)));
	},
	destroy : function(){
		var menuCons = this.menuControllers;
		for (var i = 0; i < menuCons.length; i++){
			try{
				delete menuCons[i].menuBar;
				delete menuCons[i].oPreviousItem;
				menuCons[i] = null;
			}catch(error){
				//just skip it.
			}
		}
		this.menuControllers = [];
	}
});

Event.observe(window, 'unload',function(){
	com.trs.menu.MenuInitialler.destroy();
});


function BlurHide(el, fHideCallBack){
	this.element = $(el);
	this.hideCallBack = fHideCallBack;
}
Ext.apply(BlurHide.prototype, {
	init : function(){
		if(this.inited) return;
		this.inited = true;

		//assert focus element.
		var el = document.createElement("a");
		el.href = "#";
		el.style.position = 'absolute';
		el.style.left = '-1000px';
		el.style.top = '-1000px';
		document.body.appendChild(el);
		this.focusEl = el;

		//bind events
		Event.observe(this.element, 'mouseover', this.onMouseOver.bind(this));
		Event.observe(this.element, 'mouseout', this.onMouseOut.bind(this));
		Event.observe(this.focusEl, 'blur', this.onBlur.bind(this));
		Event.observe(window, 'unload', this.onDestory.bind(this));
	},
	show : function(){
		this.init();
		this.focusEl.focus();
	},
	onMouseOver : function(){
		this.inElement = true;
	},
	onMouseOut : function(){
		this.inElement = false;
	},
	onBlur : function(){
		if(this.inElement) return;
		(this.hideCallBack || Ext.emptyFn)();
	},
	onDestory : function(){
		delete this.element;
		delete this.hideCallBack;
		delete this.focusEl;
	}
});