Ext.applyIf = function(o, c){
	if(!o || !c)return o;
	for(var p in c){
		if(typeof o[p] == "undefined")o[p] = c[p];
	}
	return o;
};

document.getElementsByClassName = function(cls, p) {
	if(p && p.getElementsByClassName) return p.getElementsByClassName(cls);
	var arr = ($(p) || document.body).getElementsByTagName('*');
	var rst = [];
	var regExp = new RegExp("(^|\\s)" + cls + "(\\s|$)");
	for(var i=0,n=arr.length;i<n;i++){
		if (arr[i].className.match(regExp))
			rst.push(arr[i]);
	}
	return rst;
};

Ext.ns("wcm.Constant");

//init the wcm.Constant
(function(){
	var url = window.location.protocol;
	url += "//" + window.location.host;
	wcm.Constant.ROOT_PATH = url;

	var matchs = window.location.pathname.replace(/^(\/[^\/]+\/[^\/]+\/).*$/ig,"$1");
	if(matchs){
		url += matchs;
	}
	wcm.Constant.SITE_PATH = url;
})();


Ext.applyIf(Element, {
	next : function(dom){
		if(dom == null) return null;
		var node = dom.nextSibling;
		while(node && node.nodeType != 1){
			node = node.nextSibling;
		}
		return ((node == null)||(node.parentNode != dom.parentNode)) ? null : node;
	},
	previous : function(dom){
		if(!dom) return null;
		var dom = dom.previousSibling;
		while(dom && dom.nodeType != 1){
			dom = dom.previousSibling;
		}
		return dom;
	},
	first : function(dom){
		if(!dom) return null;
		var nodes = dom.childNodes;
		for (var i = 0, len = nodes.length; i < len; i++){
			if(nodes[i].nodeType == 1)return nodes[i];
		}
		return null;
	},
	last : function(dom){
		if(!dom) return null;
		var nodes = dom.childNodes;
		for (var i = nodes.length-1; i >= 0; i--){
			if(nodes[i].nodeType == 1)return nodes[i];
		}
		return null;
	}
});

