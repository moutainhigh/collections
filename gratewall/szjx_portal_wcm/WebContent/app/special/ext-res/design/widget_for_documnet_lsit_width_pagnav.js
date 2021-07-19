/*=================================����ҳ���ĵ��б� Widget start==============================*/

(function(){
	function eventHandler(doms){
		for (var i = 0; i < doms.length; i++){
			var dom = Element.previous(doms[i]),
				count = parseInt(dom.getAttribute("count"), 10) || 5;
			// ���ҳ���в���Ҫ����ת��url
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
/*=================================����ҳ���ĵ��б� Widget end==============================*/