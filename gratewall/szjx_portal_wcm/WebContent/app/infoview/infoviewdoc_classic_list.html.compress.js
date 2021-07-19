Ext.ns('wcm.CrashBoard', 'wcm.LANG');
var m_template = [
	'<div class="wcm-cbd" id="{0}">',
		'<div class="header l" id="header-{0}"><div class="r"><div class="c">',
			'<div class="spt"></div>',
			'<div class="title" id="dialogTitle-{0}">{1}</div>',
			'<div class="tools" id="tools-{0}">',
				'<a class="close" href="#" id="close-{0}"></a>',
			'</div>',
		'</div></div></div>',
		'<div class="body l"><div class="r"><div class="c">',
			'<table border=0 cellspacing=0 cellpadding=0 class="cb-table">',
				'<tr><td id="content-{0}">',
				'<iframe src="{2}" id="frm-{0}" style="height:100%;width:100%;"',
				' frameborder="0" onload="wcm.CrashBoard.contentLoaded(\'{0}\', this);"></iframe>',
				'</td></tr>',
				'<tr style="display:{5}"><td class="buttons" id="buttons-{0}" style="text-align:center;">',
				'<span class="wcm-btn cbd-btn-right" id="ok-{0}-wrap"><span class="cbd-btn-left"><button class="cbd-btn" id="ok-{0}"><b>{3}</b></button></span></span>',
				'<span style="display:{7}" class="wcm-btn cbd-btn-right" id="next-{0}-wrap"><span class="cbd-btn-left"><button class="cbd-btn" id="next-{0}"><b>{6}</b></button></span></span>',
				'<span class="wcm-btn cbd-btn-right"><span class="cbd-btn-left" id="cancel-{0}-wrap"><button class="cbd-btn" id="cancel-{0}"><b>{4}</b></button></span></span>',
				'</td></tr>',
			'</table>',
		'</div></div></div>',
		'<div class="footer l"><div class="r"><div class="c"></div></div></div>',
	'</div>',
].join("");
function defCrashBoard(){
	var m_id = 0;
	var cache = {};
	function $cb(cfg){
		cfg = Ext.apply({
			id : 'cb-' + (++m_id),
			title : wcm.LANG.crashborad_2011 || '系统提示框',
			appendParamsToUrl : true
		}, cfg);
		Ext.apply(this, cfg);
		this._win = window;
		cache[cfg.id] = this;
	}
	$cb.prototype = {
		getEl : function(id){
			return this._win.$(id || this.id);
		},
		onOk : function(name){
			var frm = $('frm-' + this.id), win, fn;
			try{
				win = frm.contentWindow;
				fn = name ? win[name] : win.onOk;
			}catch(err){
			}
			if(!fn)return;
			var rst = fn(this);
			if(rst===false)return;
			if(this.callback)this.callback(rst);
			this.close();
		},
		onCancel : function(){
			this.close();
		},
		show : function(){
			var t = this;
			if(t.rendered) return;
			t.rendered = true;
			var sHtml = String.format(m_template, t.id, t.title, t.url,
				t.ok || wcm.LANG.DIALOG_BTN_OK || '确定', t.cancel || wcm.LANG.DIALOG_BTN_CANCEL || '取消', t.btns==false?'none':'', t.next , t.next == undefined?'none':'');
			var div = document.createElement('DIV');
			document.body.appendChild(div);
			div.innerHTML = sHtml;
			var cbEle = t.getEl();
			cbEle.style.zIndex = window.$MsgCenter ? $MsgCenter.genId(100) : 999;
			t.getEl("cancel-" + t.id).onclick = t.getEl("close-" + t.id).onclick
				= function(){t.onCancel();return false;};
			if(this.draggable !== false) drag(cbEle, t.getEl('header-' + t.id), this.maskable);
			t.getEl("ok-" + t.id).onclick = function(event){t.onOk()};
			t.getEl("next-" + t.id).onclick = function(event){t.onOk('onNext')};
			cbEle.style.display = '';
			if(t.width) t.getEl().style.width = t.width;
			if(t.height) {			
				var dom = t.getEl('content-' + t.id);
				dom.style.height = t.height;
				dom.style.height = (dom.offsetHeight - 65) + 'px';
			}
			var ua = navigator.userAgent.toLowerCase();
			var isIE = ua.indexOf("opera") == -1 && ua.indexOf("msie") > -1;
			var isStrict = document.compatMode == "CSS1Compat";
			var docEle = isIE && !isStrict ? document.body : document.documentElement;
			var left = (docEle.clientWidth - cbEle.offsetWidth) / 2 + docEle.scrollLeft + 'px';
			var top = (docEle.clientHeight - cbEle.offsetHeight) / 2 + docEle.scrollTop + 'px';
			cbEle.style.left = t.left || left;
			cbEle.style.top = t.top || top;
			this.showShield();
		},
		hide : function(){
			this.getEl().style.display = 'none';
			this.hideShield();
		},
		initShield : function(){
			if(!this.maskable && !Ext.isIE6) return;
			if($(this.id + '-shld')) return;
			var dom = document.createElement('iframe');
			dom.src = Ext.blankUrl;
			dom.style.display = 'none';
			dom.style.border = 0;
			dom.frameBorder = 0;
			Element.addClassName(dom, 'wcm-panel-shield');
			dom.style.zIndex = this.getEl().style.zIndex - 1;
			dom.id = this.id + '-shld';
			document.body.appendChild(dom);
		},
		showShield : function(){
			if(!this.maskable && !Ext.isIE6) return;
			this.initShield();
			var dom = this.getEl();
			var oStyle = $(this.id + '-shld').style;
			if(!this.maskable){
				oStyle.left = (parseInt(dom.style.left, 10) )+"px";
				oStyle.top = (parseInt(dom.style.top, 10) + 1)+"px";
				oStyle.width = (dom.offsetWidth - 4)+"px";
				oStyle.height = (dom.offsetHeight - 4)+"px";
			}	
			oStyle.display = '';
		},
		hideShield : function(){
			if(!this.maskable && !Ext.isIE6) return;
			$(this.id + '-shld').style.display = 'none';
		},
		destroyShield : function(){
			if(!this.maskable && !Ext.isIE6) return;
			var dom = $(this.id + '-shld');
			if(!dom) return;
			dom.parentNode.removeChild(dom);
		},
		close : function(){
			try{
				var t = this;
				t.hide();
				var dom = t.getEl("content-" + t.id);
				t.getEl("frm-" + t.id).src = '';
				dom.innerHTML = "";
				t.getEl("close-" + t.id).onclick = null;	
				t.getEl("ok-" + t.id).onclick = null;
				t.getEl("cancel-" + t.id).onclick = null;
				t.getEl('header-' + t.id).onmousedown = null;
				delete cache[t.id];
				dom = t.getEl();
				dom.parentNode.parentNode.removeChild(dom.parentNode);

			}catch(err){}
		}
	};
	function $toQueryStr(params){
		if(!params)return '';
		if(typeof params!='object')return params;
		var rst = [];
		for(var nm in params){
			var v = params[nm], type = typeof v;
			if(type!='string' && type!='number' && type!='boolean')continue;
			rst.push(nm, '=', encodeURIComponent(params[nm]), '&');
		}
		return rst.join('');
	}
	wcm.CrashBoard.get = function(cfg){
		if(!cfg.appendParamsToUrl)
		cfg = Ext.apply({
			appendParamsToUrl : true
		}, cfg);
		var cjoin = cfg.url.indexOf('?')==-1 ? '?' : '&';
		if(cfg.appendParamsToUrl==true)
		cfg.url = cfg.url + cjoin + $toQueryStr(cfg.params);
		return cache[cfg.id] || (new $cb(cfg));
	}
	wcm.CrashBoard.contentLoaded = function(id, frm){
		var cb = cache[id];
		if(!cb) return;
		try{
			var win = frm.contentWindow;
			if(!win.init)return;
		}catch(err){
		}
		win.init(cb.params, cb);
	}
}
var cbGetStyle = function(){
	return window.getComputedStyle ? function(el, style){
		var cs = window.getComputedStyle(el, "");
		return cs ? cs[style] : null;
	} : function(el, style){
		return el.style[style] || el.currentStyle[style];
	}
}();
function drag(o, p, maskable){
	var id = o.id;
    p.onmousedown=function(a){
		var frm = $('frm-' + id);
		if(frm) frm.style.visibility = 'hidden';
		var sld = $(id + '-shld');
		var d=document;if(!a)a=window.event;
		var l=parseInt(cbGetStyle(o,'left'),10),t=parseInt(cbGetStyle(o,'top'),10);
		var x=a.pageX?a.pageX:a.clientX,y=a.pageY?a.pageY:a.clientY;
		if(p.setCapture)p.setCapture();
		else if(window.captureEvents)window.captureEvents(Event.MOUSEMOVE|Event.MOUSEUP);
		d.onmousemove=function(a){
			if(!a)a=window.event;
			if(!a.pageX)a.pageX=a.clientX;
			if(!a.pageY)a.pageY=a.clientY;
			var tx=a.pageX-x+l,ty=a.pageY-y+t;
			o.style.left=tx+"px";
			o.style.top=ty+"px";
			if(!maskable && sld){
				sld.style.left=(tx)+"px";
				sld.style.top=(ty+1)+"px";
			}
		}
		d.onmouseup=function(){
			if(frm) frm.style.visibility = 'visible';
			if(p.releaseCapture)p.releaseCapture();
			else if(window.releaseEvents)window.releaseEvents(Event.MOUSEMOVE|Event.MOUSEUP);
			d.onmousemove=null;
			d.onmouseup=null;
		}
    }
}
defCrashBoard();
Ext.ns("com.trs.menu");
//简单菜单
(function(){
	var separateTemplate = "<div class='separator'></div>";
	com.trs.menu.SimpleMenu = function(config){
		com.trs.menu.SimpleMenu.superclass.constructor.apply(this, arguments);
		Ext.apply(this, config);
		this.addEvents('beforeshow', 'show', 'beforehide', 'hide', 'beforerender', 'beforeclick', 'click', 'mouseover', 'mouse');
	};
	Ext.extend(com.trs.menu.SimpleMenu, wcm.util.Observable, {
		sBaseCls : '',
		sMenuCls : 's-menu',
		sHideCls : 'display-none',
		sShieldCls : 's-menuShield',
		sFocusElCls : 's-focus-el',
		oContainer : null,
		oIframeShield : null,
		items : null,
		sSeparator : '/',
		sItemCls : 'item',
		sSelectedItemCls : 'selectedItem',
		oLastSelectedItem : null,
		args : null,
		isInContainer : false,
		maxWidth : 300,
		minWidth : 150,
		bindEvents : function(){
			Event.observe(this.oContainer, 'mousemove', this.mouseMoveEvent.bind(this));
			Event.observe(this.oContainer, 'mouseover', this.mouseOverEvent.bind(this));
			Event.observe(this.oContainer, 'mouseout', this.mouseOutEvent.bind(this));
			Event.observe(this.oContainer, 'click', this.clickEvent.bind(this));
			Event.observe(this.oFocusEl, 'blur', this.blurEvent.bind(this));
			Event.observe(window, 'unload', this.destroy.bind(this));
		},
		findItemTarget : function(dom){
			while(dom && dom.tagName != 'BODY'){
				if(Element.hasClassName(dom, this.sItemCls)) return dom;
				dom = dom.parentNode;
			}
			return null;
		},
		mouseMoveEvent : function(event){
			var srcElement = Event.element(window.event || event);
			var dom = this.findItemTarget(srcElement);
			if(dom == null) return;
			if(this.oLastSelectedItem == dom) return;
			if(this.oLastSelectedItem){
				Element.removeClassName(this.oLastSelectedItem, this.sSelectedItemCls);
			}
			Element.addClassName(dom, this.sSelectedItemCls);
			this.oLastSelectedItem = dom;
		},
		mouseOverEvent : function(event){
			this.isInContainer = true;
		},
		mouseOutEvent : function(event){
			this.isInContainer = false;
			if(!this.oLastSelectedItem) return;
			Element.removeClassName(this.oLastSelectedItem, this.sSelectedItemCls);
			this.oLastSelectedItem = null;
		},
		blurEvent : function(event){
			if(this.isInContainer) return;
			this.hide();
		},
		clickEvent : function(event){
			event = window.event || event;
			if(this.fireEvent('beforeclick', event) === false) return;
			this.hide();
			this.onClick(event);
			this.fireEvent('click', event);
		},	
		findOprKey : function(dom){
			while(dom && dom.tagName != 'BODY'){
				var sOprKey = dom.getAttribute("oprKey");
				if(sOprKey) return sOprKey;
				if(Element.hasClassName(dom, this.sMenuCls)) return null;
				dom = dom.parentNode;
			}
			return null;
		},
		onClick : function(event){
			var srcElement = Event.element(event);
			var sOprKey = this.findOprKey(srcElement);
			if(sOprKey == null) return;

			for (var i = 0, length = this.items.length; i < length; i++){
				if(this.items[i] && this.items[i]["oprKey"] == sOprKey){
					this.items[i]["cmd"](this.args);
					break;
				}
			}
		},
		renderBox : function(){
			if(this.oContainer) return;
			this.oFocusEl = document.createElement("a");
			this.oFocusEl.href = "#";
			document.body.appendChild(this.oFocusEl);
			Element.addClassName(this.oFocusEl, this.sFocusElCls);

			this.oContainer = document.createElement("div");
			document.body.appendChild(this.oContainer);
			Element.addClassName(this.oContainer, this.sMenuCls);
			Element.addClassName(this.oContainer, this.sBaseCls);			
			this.oIframeShield = document.createElement("iframe");
			this.oIframeShield.src = Ext.blankUrl;
			this.oIframeShield.frameBorder = "no";
			this.oIframeShield.scrolling = "no";
			document.body.appendChild(this.oIframeShield);
			Element.addClassName(this.oIframeShield, this.sShieldCls);			
			this.bindEvents();
		},
		renderItems : function(event){
			if(this.fireEvent('beforerender', event) === false) return;
			this.oContainer.innerHTML = this.html();
		},
		html : function(){
			var items = this.items;
			if(Ext.isString(items)) return items;
			var aHtml = [];
			for(var index = 0; index < items.length; index++){
				var oItem = items[index];
				if(oItem == this.sSeparator){
					if(aHtml.last() != separateTemplate){
						aHtml.push(separateTemplate);
					}
					continue;
				}
				if(oItem['visible'] != null){
					var visible = oItem['visible'];
					if(Ext.isFunction(visible)){
						visible = visible.call(this, this.args);
					}
					if(!visible) continue;
				}
				var aCls = ['item'];
				var cls = oItem['cls'];
				if(cls){
					aCls.push(Ext.isFunction(cls) ? (cls(this.args)) : cls);
				}
				if(aCls.include(this.sHideCls)) continue;
				aHtml.push([
					'<div',
						oItem['title'] != '' ? (' title="' + (oItem['title'] || oItem['desc'] || '') + '"') : "",
						' class="', aCls.join(' '), '"', 
						oItem['oprKey'] ? (' oprKey="' + oItem['oprKey'] + '"') : '',
						oItem['oprKey'] ? (' id="' + oItem['oprKey'] + '"') : '',
					'>',
						'<div class="icon ', (oItem['iconCls'] || ''), '"></div>',
						'<div class="desc">', oItem['desc'] || '', '</div>',
					'</div>'
				].join(""));
			}
			if(aHtml.last() == separateTemplate) aHtml.pop();
			if(aHtml.length <= 0) return "";
			return aHtml.join("");
		},
		hide : function(){
			if(this.fireEvent('beforehide') === false) return;
			this.onHide();
			this.fireEvent('hide');
		},
		onHide : function(){
			Element.hide(this.oContainer);
			Element.hide(this.oIframeShield);
			this.oLastSelectedItem = null;
			this.isShow = false;
		},
		getArgs : function(){
			return this.args;
		},
		getItems : function(){
			return this.items;
		},
		show : function(items, args){
			if(items){
				this.items = items;
			}
			items = this.items;
			this.args = args || {};
			if(this.fireEvent('beforeshow') === false) return;
			this.renderBox();
			this.renderItems();
			if(this.oContainer.innerHTML == "") return;
			this.onShow();
			this.fireEvent('show');
		},
		onShow : function(){
			//set width
			this.oContainer.style.overflow = 'visible';
			var offset = Element.getDimensions(this.oContainer);
			var width = Math.max(Math.min(this.maxWidth, offset["width"]), this.minWidth);
			this.oContainer.style.width = width;
			this.oContainer.style.overflow = 'hidden';
			Element.show(this.oContainer);

			//set menu position, because the edge of window
			var left = parseInt(this.args["x"], 10) || 0;
			var right = left + parseInt(this.oContainer.offsetWidth, 10);
			if(right >= parseInt(this.oContainer.ownerDocument.body.offsetWidth, 10)){
				left = Math.max(left - offset["width"], 0);
			}
			this.oContainer.style.left = left + "px";

			var top = parseInt(this.args["y"], 10) || 0;
			var bottom = top + parseInt(this.oContainer.offsetHeight, 10);
			if(bottom >= parseInt(this.oContainer.ownerDocument.body.offsetHeight, 10)){
				top = Math.max(top - offset["height"] - 5, 0);
			}
			this.oContainer.style.top = top + "px";

			Position.clone(this.oContainer, this.oIframeShield);
			Element.show(this.oIframeShield);
			this.isShow = true;
			setTimeout(function(){
				try{
					this.oFocusEl.focus();
				}catch(error){
					alert(error.message);
					//just skip it.
				}
			}.bind(this), 10);
		},
		destroy : function(){
			Event.stopAllObserving(this.oContainer);
			delete this.oContainer;
			delete this.oIframeShield;
			delete this.oFocusEl;
			delete this.items;
			delete this.oLastSelectedItem;
			delete this.args;
		}
	});
})();
//经典列表
Ext.ns('ClassicList.cfg');
var m_sToolbarTemplate = {
	item : [
		'<table class="toolbar_item {3}" {4} id="{0}" {2}>',
		'<tr>',
			'<td style="width:16px;"><div class="toolbar_icon {0}">&nbsp;</div></td>',
			'<td class="toolbar_cont" nowrap="true">{1}</td>',
		'</tr>',
		'</table>'
	].join(''),
	sep : [
		'<table class="toolbar_sep">',
		'<tr>',
			'<td>&nbsp;</td>',
		'</tr>',
		'</table>'
	].join(''),
	main : [
		'<table cellspacing="0" cellpadding="0" border="0" valign="top" class="list_table">',
			'<tr>',
				'<td height="26" class="head_td">',
				'<span>{0}：</span>',
				'<span id="literator_path"></span></td>',
				'<td class="head_td">{2}</td>',
			'</tr>',
		'</table>',
		'<table cellspacing="0" cellpadding="0" border="0" class="toolbar">',
			'<tr>',
				'<td height="32" valign="center" id="toolbar_container" style="visibility:hidden;">{1}</td>',
				'<td id="query_box"></td>',
				'<td width="20">&nbsp;</td>',
			'</tr>',
		'</table>'
	].join(''),
	morebtn : [
		'<span class="toolbar_more_btn" style="display:{1};" id="toolbar_more_btn">&nbsp;</span>',	
		'<div id="more_toolbar" class="more_toolbar" style="display:none;">{0}</div>'
	].join('')
};

function _mergeRight(objs){
	var arrRight = [];
	for (var i=0,n=objs.length(); i<n; i++){
		arrRight.push(objs.getAt(i).right);
	}
	return wcm.AuthServer.mergeRights(arrRight);
}

function getRight(item, event){
	if(!event) return wcm.AuthServer.getRightValue();
	var host = event.getContext().getHost();
	if(item.isHost) return host.right;
	var objs = event.getObjs();
	if(objs.length()==0) return host.right;
	if(objs.length()>1) return _mergeRight(objs);
	return objs.getAt(0).right;
}

function toToolbarHtml(cfg){
	var result = [];
	var moreResult = [];
	var json = {};
	var displayNum = window.screen.width <= 1024 ? 4 : 8;
	var nSep = 0;
	for(var i=0, n=cfg.length; i<n; i++){
		var item = cfg[i];
		if(item=='/'){
			result.push(m_sToolbarTemplate.sep);
			nSep ++;
			continue;
		}
		var bisvisible = true;
		if(item.isVisible){
			bisvisible = item.isVisible(PageContext.event);
		}
		if(!bisvisible){
			nSep ++;
			continue;
		}
		var event = PageContext.event;
		var right = getRight(item, event);
		json[item.id.toLowerCase()] = item;
		var bDisabled = (item.isDisabled && item.isDisabled(PageContext.event)) ||
			(item.rightIndex != undefined && !wcm.AuthServer.checkRight(right, item.rightIndex));
		item.disabled = bDisabled;
		if(i-nSep<displayNum){
			result.push(String.format(m_sToolbarTemplate.item,
					item.id.toLowerCase(), item.name, (item.desc?("title='"+item.desc+"'"):""),
					bDisabled ? 'toolbar_item_disabled' : '',
					bDisabled ? 'disabled' : ''
				)
			);
		}else{
			moreResult.push(String.format(m_sToolbarTemplate.item,
					item.id.toLowerCase(), item.name, (item.desc?("title='"+item.desc+"'"):""),
					bDisabled ? 'toolbar_item_disabled' : '',
					bDisabled ? 'disabled' : ''
				)
			);
		}
	}
	result.push(String.format(m_sToolbarTemplate.morebtn,
			moreResult.join(''), moreResult.length > 0 ? '' : 'none'));
	return {
		html : result.join(''),
		json : json
	};
}
function refreshToolbars(cfg){
	var result = toToolbarHtml(cfg);
	$('toolbar_container').style.visibility = 'visible';
	$('toolbar_container').innerHTML = result.html;
}
function doClassicList(){
	var loaded = false;
	ClassicList.makeLoad = ClassicList.autoLoad = function(){
		if(loaded)return;
		loaded = true;
		var arrToolbarCfg = ClassicList.cfg.toolbar || [];
		var result = toToolbarHtml(arrToolbarCfg);
		$('classic_cnt').innerHTML = String.format(m_sToolbarTemplate.main,
			ClassicList.cfg.listTitle || "",
			result.html,
			ClassicList.cfg.path || ""
		);
		function findTarget(target){
			while(target!=null && target.tagName!='BODY'){
				if(Element.hasClassName(target, 'toolbar_item'))return target;
				target = target.parentNode;
			}
			return null;
		}
		function clickToolbarMoreBtn(event, target){
			var p = event.getPoint();
			var x = p.x + 4;
			var y = p.y + 4;
			var bubblePanel = new wcm.BubblePanel($('more_toolbar'));
			bubblePanel.bubble([x,y], function(_Point){
				return [_Point[0], _Point[1]];
			});
		}
		Ext.get('classic_cnt').on('click', function(event, target){
			if(target.id == 'toolbar_more_btn'){
				clickToolbarMoreBtn.apply(this, arguments);
				return;
			}
			var target = findTarget(target);
			if(target==null || target.id==null)return;
			var toolbarItem = result.json[target.id];
			if(toolbarItem==null || !toolbarItem.fn)return;
			if(toolbarItem.disabled)return;
			toolbarItem.fn.call(null, PageContext.event, target);
		});
	}
}
doClassicList();
Event.observe(window, 'load', ClassicList.autoLoad);
$MsgCenter.on({
	objType : WCMConstants.OBJ_TYPE_ALL_CMSOBJS,
	afterselect : function(event){
		PageContext.event = event;
		try{
			refreshToolbars(ClassicList.cfg.toolbar);
		}catch(err){
		}
	}
});
Ext.apply(PageContext, {
	contextMenuEnable : true,
	gridDraggable : true,
	initDraggable : function(){
		var docGridDragDrop = new wcm.dd.GridDragDrop({
			id : 'wcm_table_grid', 
			rootId : 'grid_body',
			captureEnable:false
		});
		docGridDragDrop.addListener('dispose', function(){
			top.DragAcross = null;
			delete this.hintInSelf;
			delete this.hintInTree;
		});
		Ext.apply(docGridDragDrop, {
			_getHint : function(row){
				if(this.hintInSelf) return this.hintInSelf;
				if(!top.DragAcross){
					top.DragAcross = {};
				}
				var sCurrId = row.getObjId();
				var aSelectedIds = wcm.Grid.getRowIds(true);
				if(!aSelectedIds.include(sCurrId)) aSelectedIds.push(sCurrId);			
				Object.extend(top.DragAcross,{
					ObjectType : 605 ,
					FolderId :  row.getAttribute("channelid"),
					ObjectId : sCurrId,
					ObjectIds : aSelectedIds
				});
				if(!PageContext.CanSort){
					return wcm.LANG.infoviewdoc_list_base_1000 || "当前表单列表不支持排序";
				}
				if(PageContext.params["OrderBy"]){
					return wcm.LANG.infoviewdoc_list_base_2000 || "自动排序列表不支持手动排序";
				}
				if(!this.sortable){
					return wcm.LANG.infoviewdoc_list_base_3000 || "当前表单没有权限排序";
				}
				return "[表单-"+sCurrId+"]";
			},
			_isSortable : function(row){
				if(!PageContext.CanSort || PageContext.params["OrderBy"]) return false;
				var sRight = row.dom.getAttribute("right", 2);
				if(sRight!=null&&!wcm.AuthServer.checkRight(sRight, 62)){
					return false;
				}
				return true;
			},
			_move : function(srcRow, iPosition, dstRow, eTargetMore){
				//iPosition表示当前元素相对于目标元素的位置,1表示之前,0表示之后
				if(!PageContext.CanSort&&PageContext.params["OrderBy"]) return false;
				var bCurrTopped = srcRow.getAttribute("isTopped")=='true';
				var bTargetTopped = dstRow.getAttribute("isTopped")=='true';
				var docid = srcRow.getAttribute('docid');
				var rowId = srcRow.getAttribute('rowId');
				var targetDocId = dstRow.getAttribute('docid');
				if(bCurrTopped!=bTargetTopped){//当前行与目标行，一行是置顶，一行是非置顶
					var bFixedUp = true;
					if(iPosition==0){//有前一行，插入到目标行之后的情况
						if(!bCurrTopped&&eTargetMore!=null){//非置顶行判断前一行和后一行
							//若下一行为非置顶行，则不交叉
							//反之，则交叉
							var bTargetMoreTopped = eTargetMore.getAttribute("isTopped")=='true';
							if(!bTargetMoreTopped){
								//用后一行的数据，表示插入到它之前
								targetDocId = eTargetMore.getAttribute('docid');
								iPosition = (iPosition==0)?1:0;
								bFixedUp = false;
							}
						}
						else if(!bCurrTopped&&eTargetMore==null){//非置顶行上一行为置顶行，下一行无
							//相当于至少有n-1个置顶行,而被拖动的那行是非置顶行
							//所以非置顶行本身未移动,此种情况其实不需要考虑
							//考虑的话不计为交叉
							bFixedUp = false;
						}
						else if(bCurrTopped){//置顶行的上一行为非置顶行,必然交叉
							bFixedUp = true;
						}
					}
					else{//无前一行，但有后一行，插入到目标行之前的情况
						if(!bCurrTopped){//当前行非置顶，插在置顶行之前必然交叉
							bFixedUp = true;
						}
						else{//当前行置顶，插在非置顶行之前必然不交叉
							//但此种情况可以不考虑
							//当前置顶行拖动后前无置顶行(在第一行)，后无置顶行
							//可以知道当前只有一个置顶行，且没有交换位置
							//不应该到这里来
							bFixedUp = false;
							//若来到这里就不能按置顶交换顺序的方式处理了
							bCurrTopped = false;
						}
					}
					if(bFixedUp){
						Ext.Msg.$timeAlert(wcm.LANG.infoviewdoc_list_base_5000 || '置顶表单与非置顶表单间不能交叉排序.',5);
						return false;
					}
				}
				if(!confirm(wcm.LANG.infoviewdoc_list_base_6000 || '您确定要调整表单的顺序？')) return false;
				if(bCurrTopped){
					var oPostData = {
						"TopFlag" : 3,/*表示不改变置顶设置*/
						"ChannelId" : PageContext.getParameter("channelid"),
						"DocumentId" : docid,
						"Position" : iPosition,
						"TargetDocumentId" : targetDocId
					}
					BasicDataHelper.call('wcm6_viewdocument', 'setTopDocument', oPostData, true, function(){
						PageContext.updateCurrRows(rowId);
					});
				}
				else{
					var oPostData = {
						FromDocId:docid,
						ToDocId:targetDocId,
						position:iPosition,
						channelid: PageContext.getParameter("channelid")
					};
					BasicDataHelper.call('wcm6_viewdocument', 'changeOrder', oPostData, false, function(){
						PageContext.updateCurrRows(rowId);
					}, function(trans,json){
						wcm.FaultDialog.show({
							code		: $v(json,'fault.code'),
							message		: $v(json,'fault.message'),
							detail		: $v(json,'fault.detail'),
							suggestion  : $v(json,'fault.suggestion')
						}, wcm.LANG.infoviewdoc_list_base_7000 || '与服务器交互时出现错误' , function(){
							PageContext.updateCurrRows(rowId);
						});
					});
				}
				return true;
			}
		});

		var accrossDragger = new wcm.dd.AccrossFrameDragDrop(docGridDragDrop);
		Ext.apply(accrossDragger, {
			getWinInfos : function(){
				if(!top.$('nav_tree'))return [];
				return [
					{win : top}, 
					{
						win : top.$('nav_tree').contentWindow,
						enterMe : function(event, target, opt){
							if(!this.dd.hintInTree){
								if(top.DragAcross.ObjectIds.length>1){
									this.dd.hintInTree = String.format("[引用多篇表单:{0}]",top.DragAcross.ObjectIds);
								}
								else{
									this.dd.hintInTree = String.format("[表单-{0}]",top.DragAcross.ObjectId);
								}
							}
							this.dd.dragEl.innerHTML = this.dd.hintInTree;
						},
						leaveMe : function(event, target, opt){
							this.dd.dragEl.innerHTML = this.dd._getHint(this.dd.row);
						},
						endDrag : function(event, target, opt){
							if(!top.DragAcross || !top.DragAcross.TargetFolderId) return;
							var objId = top.DragAcross.ObjectId;
							BasicDataHelper.call('wcm6_viewdocument', 'quote', {
								"ObjectIds" : top.DragAcross.ObjectIds,
								"ToChannelIds" : top.DragAcross.TargetFolderId
							}, true, function(_transport,_json){
									Ext.Msg.report(_json, wcm.LANG.infoviewdoc_list_base_9000 || '表单引用结果', function(){
										var info = {objId : objId, objType : WCMConstants.OBJ_TYPE_CHNLDOC};
										CMSObj.createFrom(info, PageContext.getContext()).afteredit();
									});
								},
								function(_transport,_json){
									$render500Err(_transport,_json);
								}
							);
						}
				}];	
			}
		});
	}
});
