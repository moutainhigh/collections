Ext.ns('wcm.special.design');

var PageController = {
	/**
	*获取当前的StateHandler
	*/
	getStateHandler : function(){
		return wcm.special.state.StateHandler;
	},
	/**
	*执行来自跨页面的调用
	*/
	execute : function(sMethod){
		switch(sMethod){
			case 'getCurrentWidgetDimension' :
				return {width:window._currentWidgetWidth||0, height:window._currentWidgetHeight||0};
				break;
			case 'redo' :
			case 'undo' :
				var caller = this.getStateHandler();
				caller[sMethod].apply(caller, Array.prototype.slice(arguments, 1));
				break;
			case 'testRedo' :
				var stateProvider = this.getStateHandler().getStateProvider();
				return stateProvider.testState(stateProvider.TYPE_REDO);
			case 'testUndo' :
				var stateProvider = this.getStateHandler().getStateProvider();
				return stateProvider.testState(stateProvider.TYPE_UNDO);
			case 'select' :
				var sType = arguments[1];
				if(sType == 'widget'){
					wcm.special.widget.InstanceMgr.select(arguments[2]);
				}else if(sType == 'layout'){
					wcm.special.layout.mark(arguments[2]);
				}else if(sType == 'column'){

				}
				alert('select'+arguments[1]+arguments[2]);
				break;
		}
	},
	/**
	*为避免单击可视化设计页面的链接时，设计页面发生跳转，
	*从而使设计页面不正常，所以需要对页面所有链接做disable处理
	*/
	disableLinkAction : function(){
		Event.observe(document, 'click', function(event){
			var dom = Event.element(event);
			var link = wcm.util.ElementFinder.findElement(dom, 'href');
			if(link && (link.tagName == 'A' || link.tagName == "IMG")){
				//var sTarget = link.getAttribute('target');
				//if(!sTarget || sTarget == '_self'){
				Event.stop(event);
				//}
			}
		});
	},
	/**
	*获取内部设计页面的可视化编辑内容
	*/
	getVisualTemplate : function(){
		return wcm.special.design.VisualTemplateHelper.getVisualTemplate();
	},
	/**
	*获取导航树页面
	*/
	getTopWin : function(){
		return top;
	},
	/**
	*获取导航树页面
	*/
	getTreeWin : function(){
		if(this.getTopWin().$('TreeNav')){
			try{
				var TreeNavWin = this.getTopWin().$('TreeNav').contentWindow;
				if(TreeNavWin.$){
					return TreeNavWin.$('PageStructureTreeNav').contentWindow;
				}
			}catch(error){
			}
		}
		return null;
	},
	refreshTree : function(json){
		json = json || wcm.special.design.tree.init();
		if(PageController.getTreeWin() && PageController.getTreeWin().PageController){
			PageController.getTreeWin().PageController.refreshTree(json);
		}else{
			this.getTopWin().lastTreeJsonData = json;
		}
	},
	setPageStyle : function(nPageStyleId, sCssFilePath){
		//确保pagestylecss这个link元素存在
		//添加组合资源的样式文件切换
		var pageStyle_composite = $('pagestylecss_composite');// 不用每次都获取
		var pageStyle = $('pagestylecss');
		if(!pageStyle){
			var link = document.createElement('link');
			link.setAttribute('rel', "stylesheet");
			link.id = 'pagestylecss';
			document.getElementsByTagName("head")[0].appendChild(link);
			pageStyle = link;
		}
		if(!pageStyle_composite){
			var link_composite = document.createElement('link');
			link_composite.setAttribute('rel', "stylesheet");
			link_composite.id = 'pagestylecss_composite';
			document.getElementsByTagName("head")[0].appendChild(link_composite);
			pageStyle_composite = link_composite;
		}
		// 如果没有变换就返回
		if(pageStyle.getAttribute("pagestyleid")==nPageStyleId) return false;
		pageStyle.href = sCssFilePath;
		pageStyle.setAttribute("pagestyleid", nPageStyleId);
		//拼合组合资源样式文件的链接
		var sPageStyle_composite = sCssFilePath;
		var nPos = sPageStyle_composite.lastIndexOf(".");
		if(nPos > -1){
			sPageStyle_composite = sPageStyle_composite.substring(0,nPos) +"_composite.css";
		}
		pageStyle_composite.href = sPageStyle_composite;
		pageStyle_composite.setAttribute("pagestyleid", nPageStyleId);
		
		// 处理IE下刷新，撤销，重做时页面出现没有重画的问题
		if(Ext.isIE){
			setTimeout(function(){
				Element.addClassName(document.body, 'hack-cls');
				Element.removeClassName(document.body, 'hack-cls');
			}, 0);
		}
		/*激活工具栏中的撤销和保存按钮*/
		this.getStateHandler().saveState();
		this.getTopWin().wcm.special.design.ToolBar.refresh();
	},
	getPageStyleId : function(){
		if($('pagestylecss')){
			return $('pagestylecss').getAttribute("pagestyleid", 2) || 0;
		}
		return 0;
	},
	/**
	*页面加载完成时，需要触发的初始化处理
	*/
	init : function(){
		this.disableLinkAction();
		wcm.special.widget.WidgetController.init();
		wcm.special.layout.LayoutController.init();
		wcm.special.data.ElementController.init();
		this.getStateHandler().saveState();
	},
	/**
	* 在DOM ready的时候就可以去刷新树
	*/
	ready : function(fn){
		DOM.ready(fn);
	},
	/**
	*页面卸载时，需要触发的销毁处理
	*/
	destroy : function(){
		wcm.special.widget.WidgetController.destroy();
		wcm.special.layout.LayoutController.destroy();
		wcm.special.data.ElementController.destroy();
	}	
};

// dom ready 事件
PageController.ready(function(){
	PageController.refreshTree();
});
Event.observe(window, 'load', function(){
	PageController.init();
});
Event.observe(window, 'unload', function(){
	PageController.destroy();
});


//refresh tree when undo and redo. 
PageController.getStateHandler().addListener('restorestate', function(){
	top.isPageChanged = true;
	PageController.getTopWin().wcm.special.design.ToolBar.refresh();
	PageController.refreshTree();
});
//还原页面风格
PageController.getStateHandler().addListener('restorestate', function(state){
	if(!state.pageStyleId || !state.pageStyleCssPath) return;
	var pageStyle = $('pagestylecss');// 不用每次都获取
	if(!pageStyle) return;
	if(pageStyle.getAttribute("pagestyleid")==state.pageStyleId)
		return;
	pageStyle.href = state.pageStyleCssPath;
	pageStyle.setAttribute("pagestyleid", state.pageStyleId);
});
//set the page state in the top window.
PageController.getStateHandler().addListener('savestate', function(state){
	//需要保存页面的风格信息ID 和CSS路径
	var pageStyle = $('pagestylecss');
	if(!pageStyle) return;
	state.pageStyleId=pageStyle.getAttribute("pagestyleid");
	state.pageStyleCssPath=pageStyle.href;

	if(!arguments.callee.isRunned){
		arguments.callee.isRunned = true;
		return;
	}
});

//set the page state in the top window.
PageController.getStateHandler().addListener('aftersavestate', function(){
	if(!arguments.callee.isRunned){
		arguments.callee.isRunned = true;
		return;
	}
	
	top.isPageChanged = true;
	PageController.getTopWin().wcm.special.design.ToolBar.refresh();
});


//add empty node in it when there is no node in column. 
wcm.special.widget.InstanceMgr.addListener('add', function(dom, position){
	if(position == 'Top' || position == 'Bottom'){
		var node = Element.first(dom);
		if(!wcm.special.widget.InstanceMgr.isInstance(node)){
			dom.innerHTML = "";
		}
	}
});
 
wcm.special.widget.InstanceMgr.addListener('afterremove', function(parentNode){
	var dom = Element.first(parentNode);
	if(dom) return;
	parentNode.innerHTML = PageController.getTopWin().sEmptyColumnHtml;
});
/**
*	资源粘贴后，需要刷新树
*/
wcm.special.widget.InstanceMgr.addListener('aftermove', function(newElem,srcParentNode){
	PageController.refreshTree();
	var dom = Element.first(srcParentNode);
	if(!dom){
		srcParentNode.innerHTML = PageController.getTopWin().sEmptyColumnHtml;
	}

	//移除目标之处的“单击添加资源节点”
	var destWidget = $(newElem);
	var next = Element.next(destWidget);
	if(next && Element.hasClassName(next, 'c-empty-column')){
		Element.remove(next);
	}
	var previous = Element.previous(destWidget);
	if(previous && Element.hasClassName(previous, 'c-empty-column')){
		Element.remove(previous);
	}
	PageController.getTopWin().scrollInToView(destWidget,window);
	wcm.special.widget.InstanceMgr.mark(destWidget);
})
wcm.special.widget.InstanceMgr.addListener('beforemark', function(){
	wcm.special.widget.InstanceMgr.unmark();
	wcm.special.layout.LayoutUI.unmark();
});

wcm.special.layout.LayoutUI.addListener('beforemark', function(){
	wcm.special.widget.InstanceMgr.unmark();
	wcm.special.layout.LayoutUI.unmark();
});


/**state handler for widgets**/
(function(){
	var events = ['afteradd', 'afterremove', 'afteredit','aftermove'];
	var stateHandler = function(){
		PageController.getStateHandler().saveState();
		PageController.refreshTree();
	};
	for (var i = 0; i < events.length; i++){
		wcm.special.widget.InstanceMgr.addListener(events[i], stateHandler);
	}
})();


/**state handler for Layout**/
(function(){
	var events = ['afteradd', 'afterremove', 'afteredit','aftermove'];
	var handler = function(){
		PageController.getStateHandler().saveState();
		PageController.refreshTree();
	};
	for (var i = 0; i < events.length; i++){
		wcm.special.layout.LayoutUI.addListener(events[i], handler);
	}
})();


/**
*用于管理可视化设计页面中的可视化html内容
*/
wcm.special.design.VisualTemplateHelper = function(){
	var el = document.createElement("div");
	var DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict/dtd\">";
	//处理页面风格的替换
	var unparsePageStyle = function(sHtml){
		var regExp = /<link[^>]+pagestyleid=(?:"|')([^>'"]+)(?:"|')[^>]*(?:\/?>|><\/link>)/i;
		sHtml = sHtml.replace(regExp, "<TRS_PageStyle id=\"$1\"/>");
		return sHtml.replace(regExp, "");
	};
	//处理公共资源的替换
	var unparseCommonResources = function(sHtml){
		var regExp = /<(?:script)[^>]+commonresource=(?:"|')([^>'"]+)(?:"|')[^>]*(?:\/>|><\/script>)/i;
		sHtml = sHtml.replace(regExp, "<TRS_Resources/>");
		var regExp = /<(?:script)[^>]+commonresource=(?:"|')([^>'"]+)(?:"|')[^>]*(?:\/>|><\/script>)(?:\n|\r)*/ig;
		sHtml = sHtml.replace(regExp, "");
		var regExp = /<(?:link)[^>]+commonresource=(?:"|')([^>'"]+)(?:"|')[^>]*(?:\/?>|><\/link>)(?:\n|\r)*/ig;
		sHtml = sHtml.replace(regExp, "");
		return sHtml;
	};
	/*处理一下head和charset的顺序，将title设置成<TRS_ObjTitle />，见bug：SPECIAL-177
	*因为在ie8下，documentElement.outerHTML之后顺序发生了调换，需保证charset在title之前
	*/
	var verifyCharsetAndTitleSequence = function(sHtml){
		var titleRegExp = /(<TITLE>.*?<\/TITLE>)/i;
		sHtml = sHtml.replace(titleRegExp, '');
		var sTitle = RegExp.$1;
		if(!/<TITLE[^>]*reserved[^>]*>/i.test(sTitle)){//特指标题，需要保留,非特指标题，设置成<TRS_ObjTitle>
			sTitle = "<title><TRS_ObjTitle /></title>";
		}
		var charsetRegExp = /(<META[^>]*?http-equiv=('|")?Content-Type\2?[^>]*?>)/i;
		sHtml = sHtml.replace(charsetRegExp, "$1\n"+sTitle);
		return sHtml;
	};
	//添加一个特殊标识，供特殊使用
	var appendTSPECIALRSKeywordMeta = function(sHtml){
		var metaRegExp = /(<META[^>]*?content=('|")?tspecialrs\2?[^>]*?>)/i;
		if(metaRegExp.test(sHtml)){
			return sHtml;
		}
		var sKeywordsMeta = '<meta name="Keywords" content="tspecialrs" />';
		var titleRegExp = /(<TITLE>.*?<\/TITLE>)/i;
		sHtml = sHtml.replace(titleRegExp, '$1\n'+sKeywordsMeta);		
		return sHtml;
	}
	//清除html节点的垃圾样式ext-strict
	var clearHTMLCls = function(sHtml){
		var htmlRegExp = /(<html[^>]*?class=("|')?.*?ext-.*?\1[^>]*?>)/i;
		if(!htmlRegExp.exec(sHtml)){
			return sHtml;
		}
		var str = RegExp.$1;
		str = str.replace(/ext-[a-z]+/ig, "").replace(/\s{2,}/ig, " ");
		sHtml = sHtml.replace(htmlRegExp, str);		
		return sHtml;
	}
	//清除body节点的垃圾样式ext-ie6
	var formatBodyAttribute = function(sHtml){
		//body属性中，需要进行变换的正则，根据需要，可扩充该数组
		var convertRegExps = [
			//[匹配的变换正则，匹配正则后替换之后的内容]
			[/ext-[a-z0-9]+/ig, ''],	
			[/_cssrender=('|")\d*\1/ig, ''],	
			[/res-\d+x\d+/ig, ''],
			[/\s{2,}/ig, " "]//请确保该正则位于最后面，清楚多余的空格
		];
		
		//获取到body元素的属性信息
		var bodyAttributeRegExp = /<body([^>]*?)>/i;
		var result = bodyAttributeRegExp.exec(sHtml);
		var sBodyAttr = (result && result[1]) || "";

		//进行正则变换
		for(var index = 0; index < convertRegExps.length; index++){
			sBodyAttr = sBodyAttr.replace(convertRegExps[index][0], convertRegExps[index][1]);
		}
		return sHtml.replace(/<body[^>]*?>/i, "<body "+sBodyAttr+" >");
	}
	return {
		getVisualTemplate : function(){
			var sBodyInnerHtml = this.unparseWidgetInstance(Element.first(document.body).innerHTML);
			var sHtml =DOCTYPE + document.documentElement.outerHTML;
			sHtml = unparsePageStyle(sHtml);
			sHtml = unparseCommonResources(sHtml);
			sHtml = verifyCharsetAndTitleSequence(sHtml);
			sHtml = clearHTMLCls(sHtml);
			sHtml = formatBodyAttribute(sHtml);
			sHtml = appendTSPECIALRSKeywordMeta(sHtml);
			var regExp = /(<body[^>]*>)(?:.|\n)*(<\/body>)/i;
			var sHtml = sHtml.replace(regExp, "$1"+sBodyInnerHtml+"$2");
			return sHtml;
		},
		/**
		*将资源实例替换成特殊字符，以便服务器端解析
		*/
		unparseWidgetInstance : function(sHtml){
			var widgetInstanceMgr = wcm.special.widget.InstanceMgr;
			el.innerHTML = sHtml;
			var doms = [];
			var all = el.getElementsByTagName("*");
			for (var i = 0; i < all.length; i++){
				if(widgetInstanceMgr.isInstance(all[i])){
					doms.push(all[i]);
				}
			}
			for (var i = 0; i < doms.length; i++){
				//支持组合的修改
				if(doms[i].innerHTML.indexOf('trs-widget')>-1)continue;
				var aIdInfo = doms[i].id.split("-");
				doms[i].innerHTML = "@!@--"+aIdInfo[aIdInfo.length-1]+"--@!@";
			}
			return el.innerHTML;
		}
	};
}();

/**compute the width and height for widgets**/
(function(){
	var events = ['beforeadd', 'beforeedit'];
	var handler = function(dom){
		var doms = document.getElementsByClassName("p_w_content", dom);
		dom = doms.length > 0 ? doms[0] : dom;
		var paddingLeft = parseInt(Element.getStyle(dom, "paddingLeft"), 10) || 0;
		var paddingRight = parseInt(Element.getStyle(dom, "paddingRight"), 10) || 0;
		var paddingTop = parseInt(Element.getStyle(dom, "paddingTop"), 10) || 0;
		var paddingBottom = parseInt(Element.getStyle(dom, "paddingBottom"), 10) || 0;
		window._currentWidgetWidth = dom.offsetWidth - paddingLeft - paddingRight;
		window._currentWidgetHeight = dom.offsetHeight - paddingTop - paddingBottom;
	};
	for (var i = 0; i < events.length; i++){
		wcm.special.widget.InstanceMgr.addListener(events[i], handler);
	}
})();


/**
*用于对页面，布局，列及资源支持样式属性，如：
*背景图片、margin，padding等
*/
(function(){
	wcm.special.design.Node = {
		getTRSArgs : function(dom){
			var attributes = dom.attributes;
			var args = {};
			for(var index = 0; index < attributes.length; index++){
				var node = attributes[index];
				if(node.specified){
					var nodeName = node.nodeName;
					if(nodeName.startsWith('trs-')){
						args[nodeName.substring('trs-'.length)] = node.nodeValue;
					}
				}
			}
			//背景图片特殊处理，防止服务器端保存模板时，反解出两个置标
			args['background-image'] = dom.style.backgroundImage || '';
			return args;
		},
		setTRSArgs : function(dom, args){
			//背景图片特殊处理，防止服务器端保存模板时，反解出两个置标
			if(args['background-image']){
				try{
					dom.style["backgroundImage"] = args['background-image'];
					delete args['background-image'];
				}catch(error){
				}
			}
			for(name in args){
				if(!Ext.isString(args[name])) continue;
				try{
					dom.style[name.camelize0()] = args[name];
				}catch(error){
					//alert(error.message);
				}
			}
		}
	};
})();