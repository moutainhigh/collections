Ext.ns('wcm.util');
(function(){
var simpleMenu;
var isLocked;
wcm.util.ElementFinder = {
	findElement : function(dom, sIdentify){
		while(dom && dom.tagName != 'BODY'){
			if(dom.getAttribute(sIdentify) != null){
				return dom;
			}
			dom = dom.parentNode;
		}
		return null;
	}
};


Ext.ns('wcm.special.data');
wcm.special.data.OperMgr = function(){
	var mOpers = {};

	var isVisible = function(oOper,oContext){
		if(oOper.rightIndex){
			if(!AuthServer.checkRight(oContext.get('rightValue'), oOper.rightIndex)){
				return false;
			}
		}
		if(Ext.isFunction(oOper.isVisible)){
			return oOper.isVisible(oContext);
		}
		return true;
	};

	return {
		addOper : function(oper){
			var sType = oper['type'].toUpperCase();
			var sKey = oper['oprKey'];
			mOpers[sType] = mOpers[sType] || {};
			mOpers[sType][sKey] = oper;
		},
		addOpers : function(opers){
			for(var i=0; i< opers.length;i++){
				this.addOper(opers[i]);
			}
		},
		getOpers : function(sType, oContext){
			sType = sType.toUpperCase();
			var opers = mOpers[sType];
			//convert to array.
			var aOpers = [];
			for(var sKey in opers){
				if(!isVisible(opers[sKey], oContext)) continue;
				aOpers.push(opers[sKey]);
			}

			//sort the array
			return aOpers.sort(function(oper1, oper2){
				return oper1.order - oper2.order;
			});
		},
		getOper : function(sType, sKey){
			sType = sType.toUpperCase();
			return mOpers[sType][sKey];
		},
		removeOper : function(sType, sKey){
			sType = sType.toUpperCase();
			delete mOpers[sType][sKey];
		}
	};
}();



wcm.special.data.BackgroundIdentify = function(){
	var el;
	return {
		identify : function(dom){
			if(isLocked) return;
			if(dom == el) return;
			this.unidentify();
			if(!dom) return;
			Element.addClassName(dom, 'active-data-block');
			el = dom;
		},
		unidentify : function(){
			if(!el)return;
			Element.removeClassName(el, 'active-data-block');
			el = null;
		}
	};
}();


wcm.special.data.ElementContext = function(){
	var el;
	return {
		get : function(sPropertyName){
			return el.getAttribute(sPropertyName) || "";
		},
		build : function(dom){
			el = dom; 
			return this;
		}
	}
}();


wcm.special.data.ElementController = {
	initMouseEvent : function(){
		Event.observe(document, 'mousemove', function(event){
			var dom = Event.element(event);
			var target = wcm.util.ElementFinder.findElement(dom, 'operType');
			wcm.special.data.BackgroundIdentify.identify(target);
		});
		
	},
	initContextMenu : function(){
		Event.observe(document, 'contextmenu', function(event){
			Event.stop(event);
			var dom = Event.element(event);
			var target = wcm.util.ElementFinder.findElement(dom, 'operType');
			if(!target) return;
			var sOperType = target.getAttribute('operType');
			var oContext = wcm.special.data.ElementContext.build(target);
			var opers = wcm.special.data.OperMgr.getOpers(sOperType, oContext);
			//显示右键菜单
			if(!simpleMenu) {
				simpleMenu = new com.trs.menu.SimpleMenu({sBaseCls : 'trs-data-oper-menu'});
				simpleMenu.addListener('show', function(){isLocked = true;});
				simpleMenu.addListener('hide', function(){isLocked = false;});
			}

			var dom = Event.element(event);
			var doc = dom.ownerDocument;
			var win = doc.parentWindow || doc.defaultView;
			var frameElement = win.frameElement;
			var topOffset = Position.getPageInTop(frameElement);
			var pageOffsetX = event.clientX;
			var pageOffsetY = event.clientY;

			simpleMenu.show(opers,{property:target,x:topOffset[0] + pageOffsetX,y:topOffset[1] + pageOffsetY});
		});
	},
	init : function(){
		this.initMouseEvent();
		this.initContextMenu();
	},	
	destroy : function(){
		if(simpleMenu) simpleMenu.destroy();
		simpleMenu = null;
	}	
};
})();