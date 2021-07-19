/*
*PageFilter
*/
PageContext.filterEnable = true;
PageContext.setFilters = function(filters, info){
	this.pageFilters = new wcm.PageFilters(Ext.apply({
		displayNum : 6,
		filterType : PageContext.getParameter('FilterType') || 0
	}, info));
	this.pageFilters.register(filters);
};

 
/*
*OperPanel
*/
PageContext.operEnable = true;
PageContext.setRelateType = function(sRelateType){
	this.relateType = sRelateType;
};
 

/*
*PageGrid
*/
PageContext.addGridFunctions = function(actions){
	for(var actionName in actions){
		wcm.Grid.addFunction(actionName, actions[actionName]);
	}
};
Ext.apply(wcm.Grid, {
	rowType : function(){
		return PageContext.objectType;
	},
	initRowInfo : function(){
		var excludeAttrNames = /^(?:id|height|title|length|rowid|language|datafld|class|lang|hidefocus|dir|contenteditable|dataformatas|disabled|accesskey|tabindex|implementation|[v]?align|border.*|choff|bgcolor|ch|on.+)$/i;
		return function(dom){        
			var attrs = dom.attributes;
			var result = {};
			for (var i = 0, length = attrs.length; i < length; i++){
				var attr = attrs[i];
				if(!attr.specified || excludeAttrNames.test(attr.nodeName)) continue;
				result[attr.nodeName] = true;
			}
			return result;
		}
	}(),
	defRowInfo : function(){
		if(this.rowInfo) return this.rowInfo;
		var rows = $('grid_body').rows;
		for(var i=0, n=rows.length; i<n; i++){
			if(Element.hasClassName(rows[i], 'grid_row')) break;
		}
		if(i >= n) return {};
		this.rowInfo = this.initRowInfo(rows[i]);
		return this.rowInfo;
	}
});


/*
*PageTab
*/
PageContext.tabEnable = true;
PageContext.setTabs = function(tabs){
	this.pageTabs = wcm.PageTab.getTabs(Ext.applyIf(tabs, {
		hostType : PageContext.tabHostType
	}));
}


/*
*Draggable
*/
PageContext.gridDraggable = false;
PageContext.initDraggable = function(){
	//TODO...
};

Event.observe(window, 'load', function(){
	if(PageContext.gridDraggable){
		PageContext.initDraggable();
	}
});