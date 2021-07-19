/**
*本文件处理设计页面中对一些资源块的扩展处理。
*如：视频库资源块在添加到设计页面后，还需要进行一些初始化
*/

/*
* 定义需要进行处理的总eventTypes，如果单个资源还需要对其它事件进行处理，则需要在内部自己定义事件
*/
var eventTypes = ['afteradd', 'afteredit', 'afterrefresh','afterremove'];
/*=================================Video Widget Start=============================*/
/*
*定义在页面添加或修改时候之后，需要进行的初始化处理，同时也做了一些撤销与重做的处理
*/
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
				renderVideoWidget(data);
			}
		}
	};
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'trs-video')){
				return;
			}
			var doms = document.getElementsByClassName("c_video", dom);
			eventHandler(doms);
		});
	}

	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByClassName("c_video");
		eventHandler(doms);
	});
})();
/*=================================Video Widget End=============================*/

				
/*=================================Rollingnews Widget Start=============================*/
/*
*定义在页面添加或修改时候之后，需要进行的初始化处理，同时也做了一些撤销与重做的处理
*/
(function(){
	var rollingnewsEventHandler = function(doms){	
		for(var i = 0; i < doms.length; i++){
			var dom = doms[i];
			dom.id = dom.id || genExtId();
			renderRollingNewsVidget(dom);
		}
	};
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'trs-RollingNews')){
				return;
			}
			var doms = document.getElementsByClassName("c_rollingnews_content_node", dom);
			rollingnewsEventHandler(doms);
		});
	}

	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByClassName("c_rollingnews_content_node");
		rollingnewsEventHandler(doms);
	});
})();

/*=================================Rollingnews Widget End=============================*/
/*===============处理图片文档动态显示资源块时的加载处理(trs-pic-dynamic-show)==============*/
(function(){
	var eventHandler = function(doms){
		for(var i=0;i<doms.length;i++){
			if(!Element.hasClassName(doms[i],"trs-pic-dynamic-show"))return;
			var firstLiEl=document.getElementsByClassName("dynamic_li_1",doms[i]);
			if(!firstLiEl[0])return;
			Element.addClassName(firstLiEl[0],"selected");
			var imgEls = document.getElementsByClassName("dynamic_img_cl",firstLiEl[0]);
			if(imgEls[0]){
				imgEls[0].style.height=doms[i].getAttribute("PicHeight")+"px";
			}
		}
		// 处理IE下刷新，撤销，重做时页面出现没有重画的问题
		if(Ext.isIE){
			setTimeout(function(){
				Element.addClassName(document.body, 'hack-cls');
				Element.removeClassName(document.body, 'hack-cls');
			}, 0);
		}
	}

	for(var i=0;i<eventTypes.length;i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			var el=$(dom);
			if(!Element.hasClassName(Element.first(el), 'trs-pic-dynamic-show')) return;
			var doms = document.getElementsByClassName("trs-pic-dynamic-show",el);
			eventHandler(doms);
		});
	}

	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByClassName("trs-pic-dynamic-show");
		eventHandler(doms);
	});
})();
/*==========================trs-pic-dynamic-show End=============================*/


/*=================================Appendix Widget Start=============================*/
/*
*定义在页面添加或修改时候之后，需要进行的初始化处理，同时也做了一些撤销与重做的处理
*/
(function(){
	var eventHandler = function(doms){	
		for(var i = 0; i < doms.length; i++){
			var dom = Element.previous(doms[i]);
			dom.id = dom.id || genExtId();
			var value = doms[i].value;
			var data = eval("("+value+")");
			data.container = dom.id;
			renderAppendixWidget(data);
		}
	};
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'trs-docappendix')){
				return;
			}
			var doms = dom.getElementsByTagName("textarea");
			eventHandler(doms);
		});
	}

	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByName("docAppendixData");
		eventHandler(doms);
	});
})();
/*=================================Appendix Widget End=============================*/

/*=================================NumDetailPage Widget Start=============================*/
/*
*定义在页面添加或修改时候之后，需要进行的初始化处理，同时也做了一些撤销与重做的处理
*/
(function(){
	var eventHandler = function(doms){	
		for(var i = 0; i < doms.length; i++){
			var dom = Element.previous(doms[i]);
			dom.id = dom.id || genExtId();
			var value = doms[i].value;
			var data = eval("("+value+")");
			var urls = data.urls.split(",");
			dom.innerHTML = createPageHTML(urls.length-1, getPageIndex(), urls);
		}
	};
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'trs-num-detailpage')){
				return;
			}
			var doms = dom.getElementsByTagName("textarea");
			eventHandler(doms);
		});
	}

	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByName("numDetailPageData");
		eventHandler(doms);
	});
})();
/*=================================NumDetailPage Widget End=============================*/
/*=================================Special Logo Widget Start============================*/
(function(){
	var eventHandler = function(doms){
		for(var i=0;i<doms.length;i++){
			//如果已经存在元素，则不再创建元素了
			if(Element.next(doms[i]))continue;
			var data = eval("("+doms[i].value+")");
			if(isFlash(data.url)){
				var elem = document.createElement("DIV");
				doms[i].parentNode.appendChild(elem);
				elem.id="flashcontent_"+data.id;
				var flashvars = {
					autoPlay:"false",
					logoAlpha:0,
					isAutoBandWidthDetection:"false",
					videoSource : data.url
				};
				swfobject.embedSWF(data.url,elem.id,data.width,data.height,"9.0.124",false,flashvars,{allowFullScreen:"true",wmode:"opaque"},{})
			}else{
				var elem = document.createElement("IMG");
				doms[i].parentNode.appendChild(elem);
				elem.setAttribute("src",data.url);
				elem.style.width="100%";
				//elem.setAttribute("width",data.width);
				//elem.setAttribute("height",data.height);
			}
		}
	};
	var isFlash = function(filename){
		var fileExt = filename.substring(filename.lastIndexOf(".")+1);
		if(fileExt.toLowerCase()=="swf")
			return true;
		return false;
	};
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'trs-logo')){
				return;
			}
			var doms = dom.getElementsByTagName("textarea");
			eventHandler(doms);
		});
	}
	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByName("special-logo-data");
		eventHandler(doms);
	});
})();
/*=================================Special Logo Widget End==============================*/
/*=================================trs-picdrag Widget Start=============================*/
(function(){
	var eventHandler = function(doms){
		for(var i=0;i<doms.length;i++){
			var data = eval("("+doms[i].value+")");
			var opel = $("c_rotPic_"+data.id);
			var infos = data.infos.split(";");
			var pics="",links="",texts="";
			for(var j=0;j<infos.length;j++){
				if(infos[j].trim().length<=0)continue;
				pics+=infos[j].split(",")[0];
				links+=infos[j].split(",")[1];
				texts+=infos[j].split(",")[2];
				if(j<infos.length-1 && infos[j+1] && infos[j+1].trim().length!=0){
					pics+="|";links+="|";texts+="|";
				}
			}
			//设置传入的links为空，则点击图片和链接不跳转页面
			links = null;
			opel.innerHTML=GetFlashHTML(data.WIDTH,data.HEIGHT,20,pics,links,texts,data.sRootPath);
		}
	}
	for(var i = 0; i < eventTypes.length; i++){
		wcm.special.widget.InstanceMgr.addListener(eventTypes[i], function(dom){
			dom = Element.first(dom);
			if(!Element.hasClassName(dom, 'trs-picdrag')){
				return;
			}
			var doms = dom.getElementsByTagName("textarea");
			eventHandler(doms);
		});
	}
	PageController.getStateHandler().addListener('restorestate', function(state){
		var doms = document.getElementsByName("trs-picdrag-data");
		eventHandler(doms);
	});
	DOM.ready(function(){
		var doms = document.getElementsByName("trs-picdrag-data");
		eventHandler(doms);
	});
})();
/*=================================trs-picdrag Widget End==============================*/