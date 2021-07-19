//初始化资源块的高度
wcm.special.widget.InstanceMgr.addListener('afterrefresh', function(widget){
	var doms = document.getElementsByClassName('p_w_content', widget);
	
	if(doms.length <= 0) return;
	try{
		doms[0].style.height = widget.getAttribute('trs-WHEIGHT') || 'auto';
	}catch(error){
		//just skip it
	}
});


//组合资源的特殊处理
(function(){

	//记录组合资源的内容，以便恢复
	var sHtml;

	var beforeEventTypes = ['beforeedit', 'beforerefresh'];

	for(var i = 0; i < beforeEventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(beforeEventTypes[i], function(widget){
			var dom = Element.first(widget);

			if(!Element.hasClassName(dom, 'trs-composite')){
				return;
			}

			var doms = document.getElementsByClassName('p_w_content', widget);
			
			if(doms.length <= 0) return;

			sHtml = doms[0].innerHTML;
		});
	}

	var afterEventTypes = ['afteredit', 'afterrefresh'];

	for(var i = 0; i < afterEventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(afterEventTypes[i], function(widget){
			var dom = Element.first(widget);

			if(!Element.hasClassName(dom, 'trs-composite')){
				return;
			}

			var doms = document.getElementsByClassName('p_w_content', widget);
			
			if(doms.length <= 0) return;

			doms[0].innerHTML = sHtml || "";
		});
	}
})();
