Ext.ns('wcm.PageContext');
//页面上下文
wcm.PageContext = function(){
	this.params = {};
	this.addEvents('beforeload', 'afterload', 'beforerender', 'afterrender');
}
Ext.extend(wcm.PageContext, wcm.util.Observable, {
	getInitParams : function(){
		var locationParams = location.search.parseQuery();
		var params = Ext.Json.toUpperCase(locationParams);
		return Ext.applyIf(params, Ext.Json.toUpperCase(PageContext.initParams));
	},
	getParams : function(info, _bRefresh){
		var params = (_bRefresh && this.params) ? this.params : this.getInitParams();
		params = Ext.apply(params, Ext.Json.toUpperCase(info));
		return params;
	},
	loadList : function(info, _fCallBack, _bRefresh){
		this.params = this.getParams(info, _bRefresh);
		this.fireEvent('beforeload');
		var oHelper = new com.trs.web2frame.BasicDataHelper();
		if(this.serviceId.indexOf('.jsp')==-1){
			oHelper.Call(this.serviceId, this.methodName || 'jQuery', 
				this.params, true, _fCallBack || this.renderList.bind(this));
		}
		else{
			oHelper.JspRequest(this.serviceId,
				this.params, true, _fCallBack || this.renderList.bind(this));
		}
		this.fireEvent('afterload');
	},
	refreshList : function(info, _selectRowIds){
		this.loadList(Ext.applyIf({
			"SELECTIDS" : (_selectRowIds || []).join(',')
		}, info), null, true);
	},
	renderList : function(_transport, _json, _ajaxRequest){
		this.fireEvent('beforerender', _transport, _json, _ajaxRequest);
		Element.update('list-data', _transport.responseText, true);
		this.fireEvent('afterrender', _transport, _json, _ajaxRequest);
	},
	mergeRights : function(arr){
		var maxLen = 0, rst = '', tmpidx = -1;
		var m_pSpecialIndexs = [38,39];
		for(var i=0;i<arr.length;i++){
			arr[i] = arr[i] || '';
			if(arr[i].length>maxLen){
				maxLen = arr[i].length;
			}
		}
		for(var i=0;i<maxLen;i++){
			var b = maxLen>i&&m_pSpecialIndexs.include(maxLen-1-i);
			var c0 = b?'0':'1', c1 = b?'1':'0';
			for(var j=0;j<arr.length;j++){
				tmpidx = i+arr[j].length-maxLen;
				if(tmpidx>=0&&arr[j].charAt(tmpidx)==c1){
					c0 = c1;
					break;
				}
			}
			rst += c0;
		}
		return rst;
	}
});
var PageContext = new wcm.PageContext();

Event.observe(window, 'load', function(){
	PageContext.loadList();
});


//页面检索
Ext.ns("wcm.ListQuery");
(function(){
	var sDefaultValueDesc;
	var sTemplate = [
		'<div class="l">',
			'<div class="r">',
				'<div class="c">',
					'<input type="text" name="queryValue" id="queryValue" value="" onblur="wcm.ListQuery.blurQueryValue();" onfocus="wcm.ListQuery.focusQueryValue();" onkeydown="wcm.ListQuery.keydownQueryValue(event);"/>',
				'</div>',
			'</div>',
		'</div>',
		'<div class="btn" onclick="wcm.ListQuery.doQuery();"></div>'
	].join("");

	Ext.apply(wcm.ListQuery, {
		config : null,
		register : function(config){
			this.config = Ext.apply({id : 'search'}, config);
			if($(this.config.id)){
				this.render();
			}else{
				Event.observe(window, 'load', this.render.bind(this), false);
			}
		},
		render : function(){
			$(this.config.id).innerHTML = sTemplate;	
			sDefaultValueDesc = "请输入"+(PageContext.PageNav.TypeName||"对象")+"名称...";
			$('queryValue').value = sDefaultValueDesc;
			Element.addClassName('queryValue', "defaultValueCls");
		},
		keydownQueryValue : function(event){
			Element.removeClassName('queryValue', "defaultValueCls");
			event = window.event || event;
			if(event.keyCode == 13) {
				Event.stop(event);
				this.doQuery();
			}
		},
		blurQueryValue : function(){
			var dom = $('queryValue');
			var sValue = dom.value.trim();
			if(sValue.length <= 0){
				dom.value = sDefaultValueDesc;
			}
			if(dom.value == sDefaultValueDesc){
				Element.addClassName(dom, "defaultValueCls");
			}
		},
		focusQueryValue : function(){
			$('queryValue').select();
		},
		doQuery : function(){
			var sValue = $('queryValue').value.trim();
			if(sValue == sDefaultValueDesc){
				sValue = "";
			}
			if(sValue.length >= 200){
				Ext.Msg.warn("输入的检索内容太长.");
				return;
			}
			if(this.config.callback){
				this.config.callback(sValue);
			}
		}
	});
})();



//顶部工具栏
Ext.ns('wcm.Toolbar');
Ext.applyIf(wcm.Toolbar, {
	id : 'toolbar',
	cmdIdentify : 'cmd',
	cmdHandlers : {},
	register : function(cmds){
		for(var sCmd in cmds){
			this.cmdHandlers[sCmd.toUpperCase()] = cmds[sCmd];
		}
	},
	refresh : function(sRightValue){
		var dom = Element.first($(this.id));
		while(dom){
			var rightIndex = dom.getAttribute('rightIndex');
			if(rightIndex){
				rightIndex = sRightValue.length-1-parseInt(rightIndex, 10);
				var hasRight = sRightValue.charAt(rightIndex) == '1';
				Element[hasRight ? 'removeClassName' : 'addClassName'](dom, 'disableCls');
			}
			dom = Element.next(dom);
		}
	},
	init : function(){
		if(!$(this.id)) return;
		Event.observe(this.id, 'click', function(event){
			var caller = wcm.Toolbar;
			var dom = Event.element(event);
			if(Element.hasClassName(dom, 'disableCls'))return;
			var sCmd = dom.getAttribute(caller.cmdIdentify);
			if(!sCmd) return;
			var fHandler = caller.cmdHandlers[sCmd.toUpperCase()];
			if(!fHandler) return;
			//顶部的按钮有需要event参数的情况，例如，更多按钮，需要利用event来定位
			fHandler('todolist',event);
		}, false);
	}
});

Event.observe(window, 'load', function(){
	wcm.Toolbar.init();
});


//顶部工具栏
Ext.ns('wcm.ThumbList');
(function(){
	var excludeAttrNames = /^(?:id|height|title|length|rowid|language|datafld|class|lang|hidefocus|dir|contenteditable|dataformatas|disabled|accesskey|tabindex|implementation|[v]?align|border.*|choff|bgcolor|ch|on.+)$/i;
	function getDefaultItemInfo(dom){        
		var attrs = dom.attributes;
		var result = {};
		for (var i = 0, length = attrs.length; i < length; i++){
			var attr = attrs[i];
			if(!attr.specified || excludeAttrNames.test(attr.nodeName)) continue;
			result[attr.nodeName] = true;
		}
		return result;
	}

	//the const for the thumb item list object.
	var m_ThumbItemConst = {
		container : 'list-data',
		thumb_item_cls : 'thumb',
		thumb_right_value : 'rightValue',
		thumb_item_active_cls : 'thumb_active',
		thumb_item_hover_cls : 'thumb_hover',
		identify_attr : 'itemId',
		thumb_id_prefix : 'thumb_',
		cbx_id_prefix : 'cbx_',
		desc_id_prefix : 'desc_',
		thumb_item_cmdIdentify : 'cmd',
		thumb_selectAll : 'selectAll'
	};

	//apply the default value.
	Ext.applyIf(wcm.ThumbList, m_ThumbItemConst);	
	
	//add the function for the click action.
	Ext.applyIf(wcm.ThumbList, {
		cmdHandlers : {},
		register : function(cmds){
			for(var sCmd in cmds){
				this.cmdHandlers[sCmd.toUpperCase()] = cmds[sCmd];
			}
		},
		initClickEvent : function(){
			Event.observe(this.container, 'click', function(event){
				wcm.ThumbList.execCmd(event);
				wcm.ThumbList.toggleActive(event);
			}, false);
		}
	});

	Ext.applyIf(wcm.ThumbList, {
		init : function(){
			this.initClickEvent();
		},
		execCmd : function(event){
			event = window.event || event;
			var caller = wcm.ThumbList;
			var dom = Event.element(event);
			if(Element.hasClassName(dom, 'disableCls'))return;
			var sCmd = dom.getAttribute(caller.thumb_item_cmdIdentify);
			if(!sCmd) return;
			var fHandler = caller.cmdHandlers[sCmd.toUpperCase()];
			if(!fHandler) return;
			var thumbItem = findItem(dom, caller.thumb_item_cls);
			if(!thumbItem) return;
			fHandler.call(this, caller.getProperties(thumbItem), event);			
		},
		toggleActive : function(event){
			event = window.event || event;
			var dom = Event.element(event);
			if(dom.type != 'checkbox')return;
			var parentDom = Element.find(dom,null,this.thumb_item_cls,null);
			if(dom.checked){
				Element.addClassName(parentDom,this.thumb_item_active_cls);
			}else{
				Element.removeClassName(parentDom,this.thumb_item_active_cls);
			}
			wcm.Toolbar.refresh(this.getRightValue());
		},
		setHostRightValue : function(hostRightValue){
			this.hostRightValue = hostRightValue;
		},
		getRightValue : function(){
			// no object select, return host right value.
			if(this.getSelectedThumbItemIds().length==0)return this.hostRightValue;

			//merge select objects right
			var box = $(this.container);
			var dom = Element.first(box);
			if(!Element.hasClassName(dom, this.thumb_item_cls)) return null;
			var arrRight = [];
			do{
				var itemId = dom.getAttribute(this.identify_attr);
				if($(this.cbx_id_prefix+itemId).checked){
					arrRight.push(dom.getAttribute(this.thumb_right_value));
				}
				dom = Element.next(dom);
			}while(dom);

			//return rightvalue
			return PageContext.mergeRights(arrRight);
		},
		isThumbItem : function(_oDom){
			if(!_oDom) return false;
			var sThumbCls = m_ThumbItemConst["thumb_item_cls"];
			return Element.hasClassName(_oDom, sThumbCls);
		},
		selectAll : function(){
			var box = $(m_ThumbItemConst["container"]);
			var dom = Element.first(box);
			while(dom){
				if(wcm.ThumbList.isThumbItem(dom)){
					var cbx = $(m_ThumbItemConst["cbx_id_prefix"] + dom.getAttribute("itemId"));
					if(cbx && !cbx.checked){
						cbx.click();
					}
				}
				dom = Element.next(dom);
			}
		},
		unselectAll : function(){
			var box = $(m_ThumbItemConst["container"]);
			var dom = Element.first(box);
			while(dom){
				if(wcm.ThumbList.isThumbItem(dom)){
					var cbx = $(m_ThumbItemConst["cbx_id_prefix"] + dom.getAttribute("itemId"));
					if(cbx && cbx.checked){
						cbx.click();
					}
				}
				dom = Element.next(dom);
			}
		},
		/**
		*Get the item info for the thumb item.
		*If the item info is not defined, then the default value will be built.
		*/
		getItemInfo : function(dom){
			if(!this.itemInfo){
				this.itemInfo = getDefaultItemInfo(dom);
			}
			return this.itemInfo;
		},

		/**
		*@param dom is the thumb item id with no prefix or the dom node object.
		*Get the properties for the thumb item.
		*/
		getProperties : function(dom){
			if(typeof dom == 'string') dom = $(this.thumb_id_prefix + dom);
			var itemInfo = this.getItemInfo(dom);
			var result = {id : dom.getAttribute(this.identify_attr, 2)};
			for(var propertyName in itemInfo){
				result[propertyName.toUpperCase()] = dom.getAttribute(propertyName, 2);
			}
			return result;
		},

		/**
		*Get the array of id for the selected thumb items.
		*/
		getSelectedThumbItemIds : function(){
			var box = $(this.container);
			var dom = Element.first(box);
			if(!Element.hasClassName(dom, this.thumb_item_cls)) return [];
			var result = [];
			do{
				var itemId = dom.getAttribute(this.identify_attr);
				if($(this.cbx_id_prefix+itemId) && $(this.cbx_id_prefix+itemId).checked){
					result.push(dom.getAttribute(this.identify_attr));
				}
				dom = Element.next(dom);
			}while(dom);
			return result;
		}
		
	});
})();

Event.observe(window, 'load', function(){
	wcm.ThumbList.init();
});

PageContext.addListener('afterrender', function(transport, json){
	wcm.ThumbList.setHostRightValue(transport.getResponseHeader("HostRightValue"));
	wcm.Toolbar.refresh(wcm.ThumbList.getRightValue());
});

PageContext.addListener('afterrender', function(transport, json){
	if(!$("selectAll"))return;
	$("selectAll").checked = false;
});

Ext.apply(wcm.LANG, {
	PAGENAV9 : "<span class=\"nav_pagesize\"><input type=\"text\" name=\"nav-pagesize\" id=\"nav-pagesize\" value=\"{0}\" onkeydown=\"PageContext.PageNav.setPageSize(arguments[0], this);\" onblur=\"PageContext.PageNav.setPageSize(arguments[0], this);\"/></span>{1}/页"
});

//分页导航条
Ext.applyIf(PageContext, {
	PageNav : {
		UnitName : wcm.LANG.PAGENAV1 || '条',
		TypeName : wcm.LANG.PAGENAV2 || '记录'
	}
});
Ext.apply(PageContext.PageNav, {
	go : function(_iPage, _maxPage){
		delete PageContext.params["SELECTIDS"];
		PageContext.loadList({
			CurrPage : (_iPage<1)? 1 : ((_iPage > _maxPage) ? _maxPage : _iPage)
		}, null, true);
	},
	applyKeyProvider : function(info){
		if(!PageContext.keyProvider)return;
		Ext.apply(PageContext.keyProvider, {
			ctrlPgUp : function(event){
				PageContext.PageNav.go(info.CurrPageIndex-1, info.PageCount);
			},
			ctrlPgDn : function(event){
				PageContext.PageNav.go(info.CurrPageIndex+1, info.PageCount);
			},
			ctrlEnd : function(event){
				PageContext.PageNav.go(info.PageCount, info.PageCount);
			},
			ctrlHome : function(event){
				PageContext.PageNav.go(1, info.PageCount);
			}
		});
	},
	setPageSize : function(event, _oElement){
		event = event || window.event;
		switch(event.type){
			case 'blur':
				var newNo = parseInt(_oElement.value, 10);
				_oElement.lastNo = _oElement.lastNo || "";
				if(isNaN(newNo)){
					_oElement.value = _oElement.lastNo;
				}
				else{
					_oElement.value = newNo;
				}
				if(_oElement.lastNo!=newNo){
					delete PageContext.params["SELECTIDS"];
					PageContext.loadList({
						PageSize : (_oElement.value < -1) ? -1 : _oElement.value
					}, null, true);
				}
				break;
			case 'keydown':
				if(event.keyCode==13){
					_oElement.blur();
					return;
				}
//				Event.stop(event);
				break;
		}
	},
	EffectMe : function(event, _oElement){
		event = event || window.event;
		switch(event.type){
			case 'blur':
				var newNo = parseInt(_oElement.value, 10);
				_oElement.lastNo = _oElement.lastNo || "";
				if(isNaN(newNo)){
					_oElement.value = _oElement.lastNo;
				}
				else{
					_oElement.value = newNo;
				}
				if(_oElement.lastNo!=_oElement.value){
					PageContext.PageNav.go(parseInt(_oElement.value, 10), PageContext.PageNav["PageCount"]);
				}
				break;
			case 'keydown':
				if(event.keyCode==13){
					_oElement.blur();
					return;
				}
//				Event.stop(event);
				break;
		}
	}
});

PageContext.getPageNavHtml = function(iCurrPage, iPages, info){
	var sHtml = '';
	//output first
	if(iCurrPage!=1){
		sHtml += '<span class="nav_page" title="' + (wcm.LANG.PAGENAV4 || "首页") + '" onclick="PageContext.PageNav.go(1, ' + iPages + ');">1</span>';
	}else{
		sHtml += '<span class="nav_page nav_currpage">1</span>';
	}
	var i,j;
	if(iPages-iCurrPage<=1){
		i = iPages-3;
	}
	else if(iCurrPage<=2){
		i = 2;
	}
	else{
		i = iCurrPage-1;
	}
	var sCenterHtml = '';
	var nFirstIndex = 0;
	var nLastIndex = 0;
	//output 3 maybe
	for(j=0;j<3&&i<iPages;i++){
		if(i<=1)continue;
		j++;
		if(j==1)nFirstIndex = i;
		if(j==3)nLastIndex = i;
		if(iCurrPage!=i){
			sCenterHtml += '<span class="nav_page" onclick="PageContext.PageNav.go('+i+','+iPages+');">'+i+'</span>';
		}else{
			sCenterHtml += '<span class="nav_page nav_currpage">'+i+'</span>';
		}
	}
	//not just after the first page
	if(nFirstIndex!=0&&nFirstIndex!=2){
		sHtml += '<span class="nav_morepage" title="' + (wcm.LANG.PAGENAV5 || "更多") + '">...</span>';
	}
	sHtml += sCenterHtml;
	//not just before the last page
	if(nLastIndex!=0&&nLastIndex!=iPages-1){
		sHtml += '<span class="nav_morepage" title="' + (wcm.LANG.PAGENAV5 || "更多") + '">...</span>';
	}
	//output last
	if(iCurrPage!=iPages){
		sHtml += '<span class="nav_page" title="' + (wcm.LANG.PAGENAV6 || "尾页") + '" onclick="PageContext.PageNav.go(' + iPages + ',' + iPages + ');">'+iPages+'</span>';
	}else{
		sHtml += '<span class="nav_page nav_currpage" title="' + (wcm.LANG.PAGENAV6 || "尾页") + '">'+iPages+'</span>';
	}
	return sHtml;
}

PageContext.getNavigatorHtml = function(info){
	var iRecordNum = info.Num;
	if(iRecordNum==0)return '';
	var iCurrPage = info.CurrPageIndex;
	var iPageSize = info.PageSize;
	var iPages = info.PageCount;
	var aHtml = [
		'<span class="nav_page_detail">',
			String.format("共<span class=\"nav_pagenum\">{0}</span>页", iPages),
			',',
			'<span class="nav_recordnum">', iRecordNum, '</span>',
			PageContext.PageNav["UnitName"], 
			(WCMLANG.LOCALE == 'en' ? PageContext.PageNav["TypeName"].toLowerCase() : PageContext.PageNav["TypeName"]),
			(WCMLANG.LOCALE == 'en' ? '(s)' : ''),
			',',
			String.format(wcm.LANG.PAGENAV9, iPageSize, PageContext.PageNav["UnitName"])
	];
	if(iPages > 1){
		aHtml.push(
			',',
			String.format("跳转到第{0}页",'<input type="text" name="nav-go" id="nav-go" value="" onkeydown="PageContext.PageNav.EffectMe(arguments[0], this);" onblur="PageContext.PageNav.EffectMe(arguments[0], this);"/>')
		);
	}
	aHtml.push('.</span>');
	var sHtml = aHtml.join("");
	if(iPages>1){
		sHtml += PageContext.getPageNavHtml(iCurrPage, iPages, info);
	}
	return sHtml;
}

/*avoid memory leak*/
PageContext.destroyPageNavHtml = function(){
	//remove input events.
	var dom = $('nav-go');
	if(dom){
		dom.onkeydown = null;
		dom.onblur = null;
	}
	//remove span events.
	var dom = $('list_navigator');
	if(dom){
		var nodes = dom.getElementsByTagName('span');
		for (var i = 0; i < nodes.length; i++){
			if(nodes[i].onclick) nodes[i].onclick = null;
		}
	}
}

PageContext.drawNavigator = function(info){
	//update for the no record.后期需要调整，放到一个合适的地方
	if(info.Num == 0 && $('objectNotFound')){
		$('list-data').innerHTML = $('objectNotFound').innerHTML;
	}

	Ext.apply(PageContext.PageNav, info);
	var eNavigator = $('list_navigator');
	if(!eNavigator)return;
	PageContext.destroyPageNavHtml();
	var sHtml = PageContext.getNavigatorHtml(info);
	Element.update(eNavigator, sHtml);
	//key provider
	PageContext.PageNav.applyKeyProvider(info);
}

//全选按钮事件绑定
Event.observe(window, 'load', function(){
	if(!$("selectAll"))return;
	$("selectAll").checked = false;
	Event.observe($("selectAll"), 'click', function(){
		$("selectAll").checked ? wcm.ThumbList.selectAll(): wcm.ThumbList.unselectAll();
	});

	document.onkeydown = function(evt){
		var event = evt || window.event;
		var element = event.srcElement || event.target;
		if(event.keyCode== 65 && element.tagName.toUpperCase() != "INPUT"){
			$("selectAll").click();
		}
	}
});

