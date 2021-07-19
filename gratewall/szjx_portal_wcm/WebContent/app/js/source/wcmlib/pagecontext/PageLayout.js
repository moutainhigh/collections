Ext.ns('wcm.Layout');
//布局控制器
Ext.apply(wcm.Layout, {
	collapse : function(_sDire, _sid){
		var element = $(_sid);
		Element.addClassName(element, 'hide_'+_sDire.toLowerCase());
	},
	expand : function(_sDire, _sid){
		var element = $(_sid);
		Element.removeClassName(element, 'hide_'+_sDire.toLowerCase());
	},
	collapseByChild : function(_sDire, _sid){
		var element = $(_sid);
		if(!element)return;
		var elDivMain = element.parentNode;
		Element.addClassName(elDivMain, 'hide_'+_sDire.toLowerCase());
	},
	expandByChild : function(_sDire, _sid){
		var element = $(_sid);
		if(!element)return;
		var elDivMain = element.parentNode;
		Element.removeClassName(elDivMain, 'hide_'+_sDire.toLowerCase());
	}
});