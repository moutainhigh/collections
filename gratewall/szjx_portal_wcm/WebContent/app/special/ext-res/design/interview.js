/**
*本文件处理设计页面中对访谈资源块的扩展处理以及页面加载后，对访谈资源的初始化操作。
*/

/*=================================Interview Cotent Start=============================*/
(function(){
	var eventHandler = function(doms){	
		for(var i = 0; i < doms.length; i++){
			var dom = doms[i];
			dom.id = dom.id || genExtId();
			//增加有效性的校验
			var value = Element.next(dom).value;
			value = String.format(value, pageIndex)
			+ '?r=' + new Date().getTime();
			doAction(value,1);
		}
	};
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'interview_list')){
				return;
			}
			var doms = document.getElementsByClassName("c_interview_list", dom);
			eventHandler(doms);
		});
	}

	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByClassName("c_interview_list");
		eventHandler(doms);
	});
})();
/*=================================Interview Content End=============================*/

/*=================================Interview Introduce Start=============================*/
(function(){
	var eventHandler = function(doms){	
		for(var i = 0; i < doms.length; i++){
			var dom = doms[i];
			dom.id = dom.id || genExtId();
			//增加有效性的校验
			var value = Element.next(dom).value;
			doAction(value,2);
		}
	};
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'interview_introduce')){
				return;
			}
			var doms = document.getElementsByClassName("c_interview_introduce", dom);
			eventHandler(doms);
		});
	}

	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByClassName("c_interview_introduce");
		eventHandler(doms);
	});
})();

/*=================================Interview Introduce End=============================*/

/*=================================Interview PostQuestion Start=============================*/
(function(){
	var eventHandler = function(doms){	
		for(var i = 0; i < doms.length; i++){
			var dom = doms[i];
			dom.id = dom.id || genExtId();
			//增加有效性的校验
			var value = Element.next(dom).value;
			if(existURL(value) != ""){
				var data = eval("("+value+")");
				postActionAttach(data);
			}
		}
	};
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'interview_postQuestion')){
				return;
			}
			var doms = document.getElementsByClassName("c_interview_postQuestion", dom);
			eventHandler(doms);
		});
	}

	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByClassName("c_interview_postQuestion");
		eventHandler(doms);
	});
})();
/*=================================Interview PostQuestion End=============================*/
/*=================================Interview JBIntroduce Start=============================*/
(function(){
	var eventHandler = function(doms){	
		for(var i = 0; i < doms.length; i++){
			var dom = doms[i];
			dom.id = dom.id || genExtId();
			//增加有效性的校验
			var value = Element.next(dom).value;
			doAction(value,2);
		}
	};
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'interview_jbintroduce')){
				return;
			}
			var doms = document.getElementsByClassName("c_interview_jbintroduce", dom);
			eventHandler(doms);
		});
	}

	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByClassName("c_interview_jbintroduce");
		eventHandler(doms);
	});
})();
/*=================================Interview JBIntroduce End=============================*/
/*=================================Interview PhotoList Start=============================*/
(function(){
	var eventHandler = function(doms){	
		for(var i = 0; i < doms.length; i++){
			var dom = doms[i];
			dom.id = dom.id || genExtId();
			//增加有效性的校验
			var value = Element.next(dom).value;
			if(existURL(value) != ""){
				var data = eval("("+value+")");
				data.container = dom.id;
				showPicList(data);
			}
		}
	};
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'interview-piclist')){
				return;
			}
			var doms = document.getElementsByClassName("c_interview-piclist", dom);
			eventHandler(doms);
		});
	}

	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByClassName("c_interview-piclist");
		eventHandler(doms);
	});
})();
/*=================================Interview PhotoList End=============================*/