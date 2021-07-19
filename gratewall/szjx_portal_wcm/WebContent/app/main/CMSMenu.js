Ext.ns('wcm.cms.menu');
Ext.apply(Array.prototype, {
	last : function(){
		return this[this.length - 1];
	}
});
/**
*CMSObj对象右键菜单
*/
wcm.cms.menu.CMSMenu = new com.trs.menu.SimpleMenu();	
Ext.apply(wcm.cms.menu.CMSMenu, {
	filter : function(type){
		var args = this.getArgs();
		var context = args.event.context;
		if(context.fromTree){
			var dom = context.element;
			if(dom && dom.tagName != "A"){
				if(!type) return true;
				return false;
			}
		}
		var wcmEvent = args.wcmEvent;
		if(!wcmEvent) return !type || false;			
		var context = args.event.getContext();
		if(context.isHost) return false;
		//var host = wcmEvent.getCMSObj();
		//return host.getType() == type;
		var objs = wcmEvent.getObjs();
		return objs!= null && objs.getType() == type;
	},
	fromTree : function(){
		var args = this.getArgs();
		var context = args.event.context;
		return context.get('fromTree');
	}
});

/**
*Operator到右键菜单的适配器
*/
wcm.cms.menu.OperatorAdapter = function(){
	return {
		execute : function(aOpers, wcmEvent){
			var opers = aOpers[0].concat(aOpers[1]);
			var len = aOpers[0].length;
			var menuItems = [];
			for (var i = 0; i < opers.length; i++){
				var oper = opers[i];
				var menuItem = this.item(opers[i], wcmEvent);
				menuItem['visible'] = i < len;
				menuItems.push(menuItem);
			}
			return menuItems;
		},
		item : function(oper, wcmEvent){
			var oprKey = oper["key"] || "";
			//fix the work error.
			if(oprKey == 'seperate') oprKey = 'separate';
			return {
				oprKey : oprKey.toLowerCase(),
				desc : oper["desc"],
				title : oper['title'] || "",
				iconCls : oper["key"],
				order : oper["order"],
				cmd : wcm.SysOpers.exec.bind(wcm.SysOpers, oper, wcmEvent)
			};
		}
	};
}();


(function(){
	//private
	var indexOf = function(sOprKey){
		sOprKey = sOprKey.toLowerCase();
		var items = this.items;
		for (var i = 0; i < items.length; i++){
			if(items[i]["oprKey"] == sOprKey){
				return i;
			}
		}
		return -1;
	};

	var add = function(oItem){
		if(oItem["oprKey"]){
			oItem["oprKey"] = oItem["oprKey"].toLowerCase();
		}
		if(oItem["order"] == null){
			var order;
			if(this.items.length > 0){
				order = this.items.last().order;
			}
			var order = order || this.items.length;
			oItem["order"] = order + 1;
		}
		this.items.push(oItem);
	};

	//private
	var unselectAll = function(){
		var items = this.items;
		for (var i = 0; i < items.length; i++){
			items[i]['visible'] = false;
		}
	};

	//private
	var select = function(sOprKey){
		if(Ext.isArray(sOprKey)){
			select.apply(this, sOprKey);
			return;
		}
		if(arguments.length > 1){
			for (var i = 0; i < arguments.length; i++){
				select.call(this, arguments[i]);
			}
			return;
		}
		if(sOprKey == "/"){
			select.index = (select.index || 0) + 1;
			var item = {oprKey : 'separate', order : select.index};
			add.call(this, item);
			return;
		}
		var item = this.get(sOprKey);
		if(item) {
			select.index = (select.index || 0) + 1;
			Ext.apply(item, {
				visible : true,
				order : select.index
			});
		}
	};

	//private
	var handleOperItem = function(item){
		if(!item) return false;
		var operInfo = item['operItem'];
		if(!operInfo) return false;
		delete item['operItem'];
		operItem = wcm.SysOpers.getOperItem(operInfo[0], operInfo[1], this.wcmEvent);
		if(!operItem) return true;
		var currOperItem = this.get(item["oprKey"] || operInfo[1])
		if(currOperItem){
			currOperItem['visible'] = true;
			Ext.apply(currOperItem, item);
			return;
		}
		var menuItem = wcm.cms.menu.OperatorAdapter.item(operItem, this.wcmEvent);
		item['order'] = item['order'] || null;
		Ext.apply(menuItem, item);
		add.call(this, menuItem);
		return true;
	};

	//private
	var handleNormalItem = function(item){
		var menuItem = this.get(item["oprKey"]);
		if(menuItem){
			Ext.apply(menuItem, item);
			return;
		}
		//item['visible'] = true;
		item['cmd'] = (item['cmd'] || Ext.emptyFn).bind(item, this.wcmEvent);
		add.call(this, item);
	};

	/**
	*@class wcm.cms.menu.ItemGroup
	*/
	wcm.cms.menu.ItemGroup = function(items, wcmEvent){
		this.items = items || [];
		this.wcmEvent = wcmEvent;
	};

	Ext.apply(wcm.cms.menu.ItemGroup.prototype, {
		get : function(sOprKey){
			var index = indexOf.apply(this, arguments);
			return this.items[index];
		},
		select : function(sOprKey){
			unselectAll.apply(this, arguments);
			select.index = 0;
			select.apply(this, arguments);
		},
		unapply : function(sOprKey){
			if(Ext.isArray(sOprKey)){
				this.unapply.apply(this, sOprKey);
				return;
			}
			if(arguments.length > 1){
				for (var i = 0; i < arguments.length; i++){
					this.unapply.call(this, arguments[i]);
				}
				return;
			}
			var index = indexOf.call(this, sOprKey);
			if(index == -1) return;
			this.items.splice(index, 1);
		},
		applyCfgs : function(oItem){
			if(Ext.isArray(oItem)){
				this.applyCfgs.apply(this, oItem);
				return;
			}
			if(arguments.length > 1){
				for (var i = 0; i < arguments.length; i++){
					this.applyCfgs.call(this, arguments[i]);
				}
				return;
			}
			if(oItem['oprKey'] == "separate"){
				add.call(this, oItem);
				return;
			}
			if(handleOperItem.call(this, oItem)) return;
			handleNormalItem.call(this, oItem);
		},
		applyCfgsMapping : function(fMapping){
			this.items.each(fMapping);
		}
	});
})();

(function(){
	//private
	var getPosition = function(event){
		var dom = Event.element(event);
		var doc = dom.ownerDocument;
		var win = doc.parentWindow || doc.defaultView;
		var frameElement = win.frameElement;
		var topOffset = Position.getPageInTop(frameElement);
		var pageOffsetX = event.pageX || (event.clientX +
		(doc.documentElement.scrollLeft || doc.body.scrollLeft));
		var pageOffsetY = event.pageY || (event.clientY +
		(doc.documentElement.scrollTop || doc.body.scrollTop));
		return [topOffset[0] + pageOffsetX, topOffset[1] + pageOffsetY];
	};

	//private
	var showMenu = function(items, xwcmEvent){
		var event = xwcmEvent.getContext().event;
		var wcmEvent = xwcmEvent.getContext().wcmEvent;
		var position = getPosition(event);
		var itemGroup = new wcm.cms.menu.ItemGroup(items, wcmEvent);
		wcm.cms.menu.CMSMenu.show(items, {
			x : position[0],
			y : position[1],
			event : xwcmEvent,
			wcmEvent : wcmEvent,
			items : itemGroup
		});
	};

	$MsgCenter.on({
		objType : WCMConstants.OBJ_TYPE_TREENODE,
		contextmenu : function(event){
			var context = event.getContext();
			if(!context) return;
			var dom = context.element;
			var wcmEvent = context.wcmEvent;
			var opers = [[],[]];
			if(wcmEvent != null && dom && dom.tagName == "A"){
				wcmEvent.displayNum = wcmEvent.displayNum || 6;
				opers = wcm.SysOpers.getOpersByInfo(wcmEvent);
			}
			showMenu(wcm.cms.menu.OperatorAdapter.execute(opers, wcmEvent), event);
		}
	});

	$MsgCenter.on({
		objType : WCMConstants.OBJ_TYPE_MAINPAGE,
		contextmenu : function(event){
			var context = event.getContext();
			if(!context) return;
			var wcmEvent = context.wcmEvent;
			var opers = context.opers;
			if(!opers){
				wcmEvent.displayNum = wcmEvent.displayNum || 7;
				opers = wcm.SysOpers.getOpersByInfo(wcmEvent);
			}
			showMenu(wcm.cms.menu.OperatorAdapter.execute(opers, wcmEvent), event);
		}
	});
})();

wcm.cms.menu.CMSMenu.on('beforerender', function(){
	this.items.sort(function(item1, item2){
		return (item1.order || 0) - (item2.order || 1);
	});
	var items = this.items;
	for (var i = 0; i < items.length; i++){
		if(items[i]['oprKey'] == 'separate'){
			items[i] = "/";
		}
	}
});

wcm.cms.menu.CMSMenu.on('show', function(){
	this.treenodecontextmenu = false;
	var wcmEvent = this.getArgs().event;
	if(!wcmEvent) return;
	var context = wcmEvent.getContext();
	if(!context) return;
	if(!context.fromTree) return;
	var dom = context.element;
	if(!dom || dom.tagName != "A") return;
	this.treenodecontextmenu = true;
	Element.addClassName(dom, 'contextmenustatus');
});

wcm.cms.menu.CMSMenu.on('hide', function(){
	if(!this.treenodecontextmenu) return;
	var wcmEvent = this.getArgs().event;
	var context = wcmEvent.getContext();
	var dom = context.element;
	Element.removeClassName(dom, 'contextmenustatus');
});

