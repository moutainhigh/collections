/*=================================ScrollNews Widget Start=============================*/
/*
*定义在页面添加或修改时候之后，需要进行的初始化处理，同时也做了一些撤销与重做的处理
*/
(function(){
	/*
	* 定义需要进行处理的总eventTypes，如果单个资源还需要对其它事件进行处理，则需要在内部自己定义事件
	*/
	var eventTypes = ['afteradd', 'afteredit', 'afterrefresh','aftermove'];
	
	var eventHandler = function(doms){	
		for(var i = 0; i < doms.length; i++){
			var value = doms[i].value;
			var data = eval("("+value+")");
			var scrollObj = com.trs.ScrollNews.get(data.container);
			if(scrollObj){
				scrollObj.destroy();
			}
			new com.trs.ScrollNews(data).render();
		}
	};
	
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'trs-scroll-news')){
				return;
			}
			var doms = dom.getElementsByTagName("textarea");
			eventHandler(doms);
		});
	}

	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByName("trs-scroll-news-data");
		eventHandler(doms);
	});
})();
/*=================================ScrollNews Widget End=============================*/
