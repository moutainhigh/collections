/*=================================trs-hotpic-change-inst Widget begin==============================*/
(function(){
	var eventHandler = function(){
		var hotPicDoms = document.getElementsByName("HotPicTextarea");
		for(var index = 0; index < hotPicDoms.length; index++){
			FocusPicRender(hotPicDoms[index]);
		}
	}
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'trs-hotpic-change-inst')){
				return;
			}
			eventHandler();
		});
	}
	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByName("trs-hotpic-change-inst");
		eventHandler();
	});
})();
/*=================================trs-hotpic-change-inst Widget end==============================*/