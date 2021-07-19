/**
*本文件为公共页面需要对某些特殊资源块的扩展处理。
*如：视频库资源块在发布之后的页面，还需要进行一些初始化
*/

/*=================================Video Widget Start=============================*/
/*
*定义视频渲染时，需要做的处理，该方法在页面加载后，添加或修改资源时都使用到
*/
function existURL(_sParam){
  var preSub = _sParam.split(",")[0].trim();
  var index = preSub.indexOf(":");
  var url = preSub.substring(index,preSub.length-1);
  return url;
}
function checkUrlWithOutProtocol(_url){
	var url = _url.toUpperCase();
	return url.indexOf("HTTP:") < 0 && url.indexOf("MMS:") < 0;
}
function renderVideoWidget(data){
	var rootPath = data.url;
	if(checkUrlWithOutProtocol(rootPath)){
		rootPath = wcm.Constant.ROOT_PATH + rootPath;
	}
	if(!rootPath.endsWith(".flv")){
		var autoStart = data.autoStart ? 1 : 0;
		var sHtml = ["<embed loop='1' menu='1' autostart='",autoStart,"' src='",rootPath,"' width='",data.width,"' height='",data.height,"'></embed>"];
		$(data.container).innerHTML=sHtml.join("");
		return;
	}
	var flashvars = {
		autoPlay:"false",
		logoAlpha:0,
		isAutoBandWidthDetection:"false",
		videoSource : rootPath 
	};
	var params = {allowFullScreen:"true",wmode:"opaque"};
	var attributes = {};
	var path = "TRSVideoPlayer.swf";
	if(window.BasePath){
		path = wcm.Constant.SITE_PATH + "images/resources/TRSVideoPlayer.swf";
	}
	swfobject.embedSWF(path, data.container, 
					   data.width, data.height, "9.0.124", false, flashvars, params, attributes);
}

/*
*页面加载后，对视频资源的初始化操作
*/
(function(){
	DOM.ready(function(){
		var doms = document.getElementsByClassName("c_video");
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
	});
})();
/*=================================Video Widget End=============================*/

/*=================================tab_chnl_doc Widget Start=============================*/
(function(){
	DOM.ready(function(){
		var domInitBoxs = document.getElementsByClassName("divBox-select-1");
		var domInitChnlBoxs = document.getElementsByClassName("chnl-item-select-1");
		
		if(domInitBoxs && domInitChnlBoxs){
			for (var t = 0,nLen = domInitBoxs.length; t < nLen; t++){
				if(domInitBoxs[t]){
					domInitBoxs[t].style.display = "block";
				}
			}
			for (var i = 0,nChnlLen = domInitChnlBoxs.length; i < nChnlLen; i++){
				if(domInitChnlBoxs[i]){
					domInitChnlBoxs[i].className = "selected";
				}
			}
		}
		Event.observe(document.body, 'mouseover', function(event){
			var event = window.event || event;
			var dom = Event.element(event);
			var liDom = Element.find(dom,"movetab");
			if(liDom == null) return false;
			dom = liDom;
			var sEventTargetId = dom.getAttribute("eventTargetId");
			var oDivId = sEventTargetId.split("_")[0];
			var elTabs = document.getElementsByClassName("tab_holder", $(oDivId));
			
			for (var j = 0,nLenj = elTabs.length; j < nLenj; j++){
				var elLis = elTabs[j].childNodes;
				var oldDom = null;
				for (var i = 0,nLeni = elLis.length; i < nLeni; i++){
					if(elLis[i].className == "selected"||elLis[i].className == "chnl-item-select-1"){
						elLis[i].className = "";
						oldDom = document.getElementById(elLis[i].getAttribute("eventTargetId"));

						oldDom.style.display = "none";
					}
				}
				dom.className = "selected";
				var eTargetId = dom.getAttribute("eventTargetId");
				document.getElementById(eTargetId).style.display = "block";
			}
		});
	});
})();
/*=================================tab_chnl_doc Widget End=============================*/
/*=================================RollingNews Widget Start=============================*/
/*
*定义滚动新闻渲染时，需要做的处理，该方法在页面加载后，添加或修改资源时都使用到
*/
function renderRollingNewsVidget(dom){
		var contentdom;
		if(!Element.hasClassName(dom, 'c_rollingnews_content_node'))
			return;
		contentdom = dom;
		contentboxdom = contentdom.parentNode;
		if(!Element.hasClassName(contentboxdom, 'c_rollingnews_content'))
			return;
		var stopScroll = false;
		var timer=setInterval(function(contentdom,contentboxdom){
			if(stopScroll == true)
				return;
			if(contentboxdom.innerHTML == '' || contentdom.innerHTML == '')
				return;
			if(contentdom.offsetWidth<=contentboxdom.scrollLeft){
				contentboxdom.scrollLeft-=contentdom.offsetWidth;
			}else{
				++contentboxdom.scrollLeft;
			}
			if(contentboxdom.scrollLeft + contentboxdom.offsetWidth >= contentboxdom.scrollWidth){
				contentboxdom.scrollLeft = 0;
			}
		}.bind(window, contentdom, contentboxdom),30);
		Event.observe(contentboxdom, 'mouseout',function(){
			stopScroll = false;
		});
		Event.observe(contentboxdom, 'mouseover',function(){
			stopScroll = true;
		});
}
/*
*页面加载后，对滚动新闻资源的初始化操作
*/
(function(){
	DOM.ready(function(){
		var doms = document.getElementsByClassName("c_rollingnews_content_node");
		for(var i = 0; i < doms.length; i++){
			var dom = doms[i];
			dom.id = dom.id || genExtId();
			renderRollingNewsVidget(dom);
		}
	});
})();

/*=================================RollingNews Widget End=============================*/
/*=================================trs-pic-dynamic-show Widget Start=============================*/
(function(){
	var FocusVertical=function(){
		var bPlaying = false;
		function initOptions(op1,op2){
			if(!op2)return;
			for(var p in op1)
				op1[p]=op2[p] || op1[p];
		}
		var startPlay=function(el,targetEl,s,max_v,min_v){
			if(!el)return;
			var a=setTimeout(function(){
				var v = max_v;
				if(el.offsetHeight+v<s){
					el.style.height=el.offsetHeight+v+"px";
				}else{
					el.style.height = s+"px";
				}
				if(targetEl){
					if(targetEl.offsetHeight>=v){
						targetEl.style.height=targetEl.offsetHeight-v+"px";
					}else{
						/*修改动态图片展示资源在IE6下设置高度为0时不生效的问题*/
						if(Ext.isIE6)
							targetEl.style.height = 1+"px";
						else
							targetEl.style.height = 0+"px";
					}
				}
				if(el.offsetHeight<s ||(targetEl &&  targetEl.offsetHeight>=v)){
					startPlay(el,targetEl,s,max_v,min_v);
				}else{
					clearTimeout(a);
					bPlaying = false;
				}
			},20); 
		}
		// 获取上一次图片显示的元素
		var getLastEl = function(el){
			var children = el.parentNode.childNodes;
			for(var i=0;i<children.length;i++){
				if(Element.hasClassName(children[i],options.Select_Cl) && children[i]!=el)
					return children[i];
			}
		}
		// 配置选项
		var options = {
				Cl:"dynamic_li",//绑定事件的元素class名
				Img_Cl : "dynamic_img_cl",
				First_Cl:"dynamic_li_1",
				Select_Cl : "selected",
				Widget_Cl:"trs-pic-dynamic-show",
				//EventType:"mouseover",//默认事件
				Distance:200,//图片的默认高度
				Max_Speed : 80,//最大速度
				Min_Speed : 10//最小速度 
		}
		// 是否为触发正确的事件
		var isRightEvent= function(e){
			var dom = Event.element(e);
			var widget = Element.find(dom, null, "trs-pic-dynamic-show");
			if(!widget)return false;
			if(widget.getAttribute("EventType")!=e.type) return false;
			options.Distance = widget.getAttribute("PicHeight") || options.Distance;
			return  true;
		}
		return{
			/*
			*	初始化绑定事件
			*/
			init : function(_options){
				initOptions(options,_options);
				var events = ["mousedown","mousemove"];
				for(var i=0;i<events.length;i++){
					Event.observe(document,events[i],this.mouseAction);
				}
				// 初始化第一个元素
				this.initFirstElem();
			},
			// 触发的事件动作
			mouseAction : function(e){
				var dom = Event.element(e);
				var el=Element.find(dom, null, options.Cl);
				if(!el || bPlaying || !isRightEvent(e))return;
				var lastEl = getLastEl(el);
				var imgEl2 = null;
				if(lastEl){
					imgEl2= document.getElementsByClassName(options.Img_Cl,lastEl)[0];
					Element.removeClassName(lastEl,options.Select_Cl);
				}
				Element.addClassName(el,options.Select_Cl);
				var imgEl1 = document.getElementsByClassName(options.Img_Cl,el)[0];
				//动画开始
				bPlaying = true;
				startPlay(imgEl1,imgEl2,options.Distance,options.Max_Speed,options.Min_Speed);
			},
			// 初始化第一个元素
			initFirstElem : function(){
				var els=document.getElementsByClassName(options.First_Cl);
				for(var i=0;i<els.length;i++){
					Element.addClassName(els[i],options.Select_Cl);
					var imgEls = document.getElementsByClassName(options.Img_Cl,els[i]);
					if(imgEls[0]){
						var widgetEl=Element.find(els[i],null,options.Widget_Cl);
						imgEls[0].style.height=widgetEl.getAttribute("PicHeight")+"px";
					}
				}
			}
		}
	}
	DOM.ready(function(){
		new FocusVertical().init();
	});
})();
/*=======================trs-pic-dynamic-show Widget End===========================*/

/*=================================Appendix Widget Start=============================*/
/*
*定义附件渲染时，需要做的处理，该方法在页面加载后，添加或修改资源时都使用到
*/
function renderAppendixWidget(data){
	var aHtml = [];
	var urls = data.urls.split(",");
	for(var i=0; i < urls.length; i++){
		if(urls[i].trim().length <= 0){
			continue;
		}
		aHtml.push("<img border=0",
			" src='", urls[i], "'",
			" width='", data.width, "'",
			" height='", data.height, "'/>"
		);
		aHtml.push(data.separator);;
	}
	if(aHtml.length > 0){
		aHtml.pop();
	}
	Element.update(data.container, aHtml.join(""));
}

/*
*页面加载后，对附件资源的初始化操作
*/
(function(){
	//Event.observe(window, 'load', function(event){
	DOM.ready(function(){
		var doms = document.getElementsByName("docAppendixData");
		for(var i = 0; i < doms.length; i++){
			var dom = Element.previous(doms[i]);
			dom.id = dom.id || genExtId();
			var value = doms[i].value;
			var data = eval("("+value+")");
			data.container = dom.id;
			renderAppendixWidget(data);
		}
	});
})();
/*=================================Appendix Widget End=============================*/

/*=================================NumDetailPage Widget Begin=============================*/
//产生分页代码
function createPageHTML(_nPageCount, _nCurrIndex, _urls){
	if(_nPageCount == null || _nPageCount<=1){
		return "";
	}

	var nCurrIndex = _nCurrIndex || 0;

	var aHtml = [];
	// 1 输出首页和上一页
	// 1.1 当前页是首页
	if(nCurrIndex == 0){
		aHtml.push("<span>首页</span><span>上一页</span>");
	}
	//1.2 当前页不是首页
	else{
		var nPreIndex = nCurrIndex - 1;
		var sPreFileExt = nPreIndex == 0 ? "" : ("_" + nPreIndex);

		aHtml.push("<a href=\""+_urls[0]+"\">首页</a>");
		aHtml.push("<a href=\"" +_urls[nPreIndex]+"\">上一页</a>");
	}
	var startIndex = 0;
	if(_nCurrIndex-2>0){
		startIndex = _nCurrIndex-2;
		aHtml.push("...");
	}
	// 2 输出中间分页
	for(var i=startIndex; i<_nPageCount && i< _nCurrIndex+3; i++){
		if(nCurrIndex == i)
			aHtml.push("<span>"+(i+1) + "</span>");
		else
			aHtml.push("<a href=\""+_urls[i]+"\">"+(i+1)+"</a>");
	}
	if(_nCurrIndex+3<_nPageCount){
		aHtml.push("...");
	}

	// 3 输出下一页和尾页
	// 3.1 当前页是尾页
	if(nCurrIndex == (_nPageCount-1)){
		aHtml.push("<span>下一页</span><span>尾页</span>");
	}
	// 3.2 当前页不是尾页
	else{
		var nNextIndex = nCurrIndex + 1;
		var sPreFileExt = nPreIndex == 0 ? "" : ("_" + nPreIndex);

		aHtml.push("<a href=\""+_urls[nNextIndex] +"\">下一页</a>&nbsp;");
		aHtml.push("<a href=\""+_urls[_nPageCount-1] +"\">尾页</a>");
	}
	
	return aHtml.join("");
}

//获取当前分页
function getPageIndex(){
	var src = window.location.href;
	src = src.split("/")[src.split("/").length -1];
	if(src.indexOf("?") >=0){
		src = src.substring(0,src.indexOf("?"));
	}
	var length = src .split("_").length;
	var index = 0;
	if(length > 1){
		src = src.split("_")[length-1];
		src = src.substring(0,src.indexOf("."));
		index = parseInt(src);
	}
	return index;
}

/*
*页面加载后，对数字分页导航资源的初始化操作
*/
(function(){
	//Event.observe(window, 'load', function(event){
	DOM.ready(function(){
		var doms = document.getElementsByName("numDetailPageData");
		for(var i = 0; i < doms.length; i++){
			var dom = Element.previous(doms[i]);
			dom.id = dom.id || genExtId();
			var value = doms[i].value;
			var data = eval("("+value+")");
			var urls = data.urls.split(",");
			dom.innerHTML = createPageHTML(urls.length-1, getPageIndex(), urls);
		}
	});
})();
/*=================================NumDetailPage Widget End=============================*/
/*=================================Special Logo Widget Start============================*/
(function(){
	//Event.observe(window, 'load', function(event){
	DOM.ready(function(){
		var doms = document.getElementsByName("special-logo-data");
		for(var i = 0; i < doms.length; i++){
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
				elem.style.width = "100%";
				//elem.setAttribute("width",data.width);
				//elem.setAttribute("height",data.height);
			}
		}
	});
	function isFlash(filename){
		var fileExt = filename.substring(filename.lastIndexOf(".")+1);
		if(fileExt.toLowerCase()=="swf")
			return true;
		return false;
	}
})();
/*=================================Special Logo Widget End==============================*/
