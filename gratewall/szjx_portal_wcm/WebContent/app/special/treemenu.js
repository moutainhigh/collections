Ext.ns('wcm.special.tree');

wcm.special.tree.OperMgr = function(){
	var maxOrder = 0;
	var mOpers = {};

	var isVisible = function(oOper, oContext){
		if(Ext.isFunction(oOper.isVisible)){
			return oOper.isVisible();
		}
		return true;
	};

	return {
		addOper : function(oper){
			Ext.applyIf(oper, {order : ++maxOrder});
			if(typeof(oper)=="string")
				return;
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
		getOpers : function(sType){
			sType = sType.toUpperCase();
			var opers = mOpers[sType];
			//convert to array.
			var aOpers = [];
			for(var sKey in opers){
				if(!isVisible(opers[sKey])) continue;
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


(function(){
	//private
	var getPosition = function(event){
		var dom = Event.element(event);
		var doc = dom.ownerDocument;
		var win = doc.parentWindow || doc.defaultView;
		var frameElement = win.frameElement;
		var topOffset = Position.getPageInTop(frameElement);
		var pageOffsetX = event.clientX;
		var pageOffsetY = event.clientY;
		return [topOffset[0] + pageOffsetX, topOffset[1] + pageOffsetY];
	};

	var simpleMenu;
	com.trs.tree.TreeNav.onContextMenu = function(event){
		Event.stop(event);
		var dom = Event.element(event);
		if(dom.tagName != 'A') return;
		dom = dom.parentNode;
		var nodeType = dom.getAttribute("type");
		var nodeId = dom.getAttribute("id");
		var items = wcm.special.tree.OperMgr.getOpers(nodeType);
		var position = getPosition(event);
		if(!simpleMenu){
			simpleMenu = new com.trs.menu.SimpleMenu({sBaseCls : 'tree-menu'});
			simpleMenu.addListener('show', function(){
				var args = this.getArgs();
				Element.addClassName(args['property'], 'active-tree-node');
			});
			simpleMenu.addListener('hide', function(){
				var args = this.getArgs();
				Element.removeClassName(args['property'], 'active-tree-node');
			});
			simpleMenu.addListener('beforerender', function(){
				var items = this.items;
				for (var i = 0; i < items.length; i++){
					if(items[i]['isSeparator'] == true){
						items[i] = "/";
					}
				}
			});
		}
		simpleMenu.show(items, {property:dom, nodeId:nodeId, nodeType:nodeType, x:position[0], y:position[1]});
	}
})();

