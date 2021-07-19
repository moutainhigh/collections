/*=================================带分页的文档列表 Widget start==============================*/

(function(){
	function eventHandler(doms){
		for (var i = 0; i < doms.length; i++){
			var dom = Element.previous(doms[i]),
				count = parseInt(dom.getAttribute("count"), 10) || 5;
			// 设计页面中不需要，跳转的url
			dom.innerHTML = createPageHTML(count, getPageIndex(),[]);
		}
	}
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'trs-doc-list-width-pagnav')){
				return;
			}
			var doms = dom.getElementsByTagName("TEXTAREA");
			eventHandler(doms);
		});
	}
})();
/*=================================带分页的文档列表 Widget end==============================*/