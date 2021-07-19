//菜单适配
﻿Ext.ns("wcm.MenuView", "wcm.MenuContext");
//override the com.trs.menu.Menu.prototype methods
Object.extend(com.trs.menu.Menu.prototype, {
	enableHotKey : false,
	executeItemCommand : function(descNode){
		var sKey = descNode.getAttribute("key");
		if(!sKey) return;
		var oItem = wcm.MenuView.get(sKey);
		if(!oItem || !oItem["cmd"]){
			return;
		}
		var event = wcm.MenuContext.getEvent() || wcm.MenuContext.getEvent('mainpage');
		oItem["cmd"].call(oItem, event, descNode);
	},
	loadDynamicItem : function(itemNode){
		var sKey = itemNode.getAttribute("key");
		if(!sKey) return;
		var oItem = wcm.MenuView.get(sKey);
		if(!oItem || !oItem["items"]){
			return "";
		}
		var event = wcm.MenuContext.getEvent() || wcm.MenuContext.getEvent('mainpage');
		var appendItems = oItem["items"].call(oItem, event, itemNode);
		if(!appendItems) return "";
		if(!Array.isArray(appendItems)){
			appendItems = [appendItems];
		}
		if(appendItems.length <= 0) return "";
		wcm.MenuView.register(appendItems);
		return wcm.MenuView.parse(appendItems);
	},
	setItemStyle : function(itemNode){
		var descNode = Element.first(itemNode);
		var sKey = descNode.getAttribute("key");
		if(!sKey) return;
		var oItem = wcm.MenuView.get(sKey);
		if(!oItem || !oItem["cls"]){
			return;
		}
		oItem["cls"].call(oItem, wcm.MenuContext.getEvent(), descNode);
	}
});


wcm.MenuView = new com.trs.menu.MenuView();
/*
Event.observe(window, 'load', function(){
	wcm.MenuView.render("menuBar");
	com.trs.menu.MenuInitialler.init("menuBar");
});
*/

(function(){
	var events = {};
	wcm.MenuContext = {
		getEvent : function(type){
			type = type || 'cmsobj';
			return events[type.toLowerCase()];
		},
		clearEvents : function(){
			events = {};
		},
		setEvent : function(type, _event){
			events[type.toLowerCase()] = _event;
		}
	};
})();


$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
	afterselect : function(event){
		if(!event.fromMain()) return;
		wcm.MenuContext.setEvent('cmsobj', event);
	}
});

$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_MAINPAGE,
	afterinit : function(event){
		wcm.MenuContext.setEvent('mainpage', event);
	},
	afterdestroy : function(event){
		operpanelhided = false;
		wcm.MenuContext.clearEvents();
	}
});

Ext.applyIf(Event, {
	KEY_RETURN:		13,
	KEY_LEFT:		37,
	KEY_UP:			38,
	KEY_RIGHT:		39,
	KEY_DOWN:		40
});
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_SYSTEM,
	onkeydown : function(wcmEvent){
		var context = wcmEvent.getContext();
		if(!context) return;
		var extEvent = context.extEvent, event = extEvent.browserEvent;
		var kc = event.keyCode;
		if(kc==18)return;
		if(!event.altKey && 
			!((kc>=37 && kc<=40) || kc==13))return;
		if(!event.altKey && wcmEvent.from()!=wcm.getMyFlag())return;
		var menu = com.trs.menu.MenuInitialler.menuControllers[0];
		menu.onKeyDown(event);
		Event.stop(event);
		return false;
	}
});