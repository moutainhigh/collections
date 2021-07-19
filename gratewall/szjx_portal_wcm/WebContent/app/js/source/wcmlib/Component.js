Ext.ns("wcm", "wcm.util", "wcmXCom");

Ext.apply(Ext, {
	removeNode : Ext.isIE ? function(){
	
		var removeIframes = function(dom){
			if(!dom) return;
			if(dom.tagName == "IFRAME"){
				dom.src = Ext.blankUrl || '';
				return;
			}
			var aIframes = dom.getElementsByTagName("iframe");
			for (var i = 0; i < aIframes.length; i++){
				try{
					if(aIframes[i].contentWindow && aIframes[i].contentWindow.onFrameClose){
						aIframes[i].contentWindow.onFrameClose();
					}
				}catch(error){
				}
				aIframes[i].src = Ext.blankUrl || '';
			}
		};

		return function(n){
			if(n && n.tagName != 'BODY'){
				removeIframes(n);
				var d = n.ownerDocument.createElement('div');
				d.appendChild(n);
				d.innerHTML = '';
			}
		}
	}() : function(n){
		if(n && n.parentNode && n.tagName != 'BODY'){
			n.parentNode.removeChild(n);
		}
	}
});

/**
 * @class wcm.ComponentMgr
*/
wcm.ComponentMgr =  function(){
	var all = {};
	var types = {};
	return {
		register : function(c){
			all[c.getId()] = c;
		},
		unregister : function(c){
			delete all[c.getId()];
		},
		all : function(){
			return all;
		},
		get : function(id){
			if(!id) return null;
			return all[id.id || id];
		},
		registerType : function(type, cls){
			types[type.toUpperCase()] = cls;
			cls.type = type;
		},
		create : function(config, defaultType){
			return new types[(config["type"] || defaultType).toUpperCase()](config);
		}
	};
}();

(function(){
	wcm.ComponentMgr0 = Ext.apply({}, wcm.ComponentMgr);
	var topMgr = function(){
		if(!window.$MsgCenter) return wcm.ComponentMgr0;
		var acutalTop = $MsgCenter.getActualTop();
		if(acutalTop == window || !acutalTop.wcm.ComponentMgr) return wcm.ComponentMgr0;
		return acutalTop.wcm.ComponentMgr;
	};
    for (var sFn in wcm.ComponentMgr){
        wcm.ComponentMgr[sFn] = function(){
			var $mgr = topMgr();
            return $mgr[this].apply($mgr, arguments);
        }.bind(sFn);
    }
})();

wcmXCom.get = wcm.ComponentMgr.get;
wcmXCom.reg = wcm.ComponentMgr.registerType;


/**
 * @class wcm.GarbageCollector
*/
(function(){
	var cache = {};
	wcm.GarbageCollector = {
		register : function(c){
			cache[c.getId()] = c;
		},
		unregister : function(c){
			delete cache[c.getId()];
		},
		collect : function(){
			for (var id in cache){
				var c = cache[id];
				if(c.autoDestory){
					c.destroy();
					delete cache[id];
				}
			}
		}
	};

	//detroy the current window components.
	Event.observe(window, 'beforeunload', wcm.GarbageCollector.collect);
})();



/**
 * @class wcm.Component
*/
wcm.Component = function(config){
	Ext.apply(this, config);
	wcm.ComponentMgr.register(this);
	wcm.GarbageCollector.register(this);
	wcm.Component.superclass.constructor.call(this);
	this.initComponent();
};

Ext.apply(wcm.Component, {
	AUTO_ID : 2
});

Ext.extend(wcm.Component, wcm.util.Observable, {
	hidden : false,
	disabled : false,
	rendered : false,
	autoDestory : true,
	hideMode : 'display',
	disabledCls : 'wcm-disabled',
	removeNode : true,

	initComponent : function(){
		this.addEvents(
			'beforeshow',
			'show',
			'beforehide',
			'hide',
			'disable',
			'enable',
			'beforerender',
			'render',
			'beforedestroy',
			'destroy'
		);
	},

	getId : function() {
		return this.id || (this.id = "wcm-comp-" + window.$MsgCenter.genId());//(++wcm.ComponentMG.AUTO_ID));
	},

	getElement : function(id){
		var id = id || this.getId();
		return this.getWin().$(id);
	},

	getDoc : function(){
		return this.getWin().document;
	},

	getWin : function(){
		var win = this.getWin0();
		var container =  this.renderTo || this.container;
		if(container){
			var doc = win.$(container).ownerDocument;
			return doc.parentWindow || doc.defaultView;
		}
		return win;
	},

	getWin0 : function(){
		return window;
	},

	show : function(){
		if(this.fireEvent("beforeshow", this) !== false){
			this.render();
			if(this.rendered){
				this.onShow();
			}
			this.hidden = false;
			this.fireEvent("show", this);
		}
		return this;
	},

	// private
	onShow : function(){
		Element.removeClassName(this.getElement(), "wcm-hide-" + this.hideMode);
	},

	hide : function(){
		if(this.fireEvent("beforehide", this) !== false){
			if(this.rendered){
				this.onHide();
			}
			this.hidden = true;
			this.fireEvent("hide", this);
		}
		return this;
	},
	
	// private
	onHide : function(){
		Element.addClassName(this.getElement(), "wcm-hide-" + this.hideMode);
	},
		
	setVisible : function(visible){
		return this[visible ? 'show' : 'hide']();
	},

	enable : function(){
		if(this.rendered){
			this.onEnable();
		}
		this.disabled = false;
		this.fireEvent('enable', this);
		return this;
	},

	// private
	onEnable : function(){
		var dom = this.getElement();
		Element.removeClassName(dom, this.disabledCls);
		dom.disabled = false;
	},

	disable : function(){
		if(this.rendered){
			this.onDisable();
		}
		this.disabled = true;
		this.fireEvent('disable', this);
		return this;
	},
	
	// private
	onDisable : function(){
		var dom = this.getElement();
		Element.addClassName(dom, this.disabledCls);
		dom.disabled = true;
	},

	setDisabled : function(disabled){
		return this[disabled ? "disable" : "enable"]();
	},

	focus : function(){
        if(this.rendered){
			try{
	            this.getElement().focus();
			}catch(error){
				//just skip it.
			}
		}
		return this;
	},

	blur : function(){
        if(this.rendered){
 			try{
				this.getElement().blur();
			}catch(error){
				//just skip it.
			}
		}
		return this;
	},

	render : function(){
        if(!this.rendered && this.fireEvent("beforerender", this) !== false){
			this.rendered = true;
			this.onRender();
            this.fireEvent("render", this);
			this.afterRender(this.container);
			if (this.hidden) {
				this.hide();
			}
			if (this.disabled) {
				this.disable();
			}
		}
		return this;
	},

	onRender : Ext.emptyFn,

	afterRender : Ext.emptyFn,

	destroy : function() {
		if (this.fireEvent("beforedestroy", this) !== false) {
			this.beforeDestroy();
			if (this.rendered) {
				var dom = this.getElement();
				if(dom){
					Event.stopAllObserving(dom);
					if(this.removeNode){
						Element.remove(dom);
					}
				}
			}
			this.onDestroy();
			wcm.ComponentMgr.unregister(this);
			wcm.GarbageCollector.unregister(this);
			this.fireEvent("destroy", this);
			this.purgeListeners();
		}
	},

	beforeDestroy :Ext.emptyFn,

	onDestroy : Ext.emptyFn
});
wcmXCom.reg('component', wcm.Component);


/**
 * @class wcm.BoxComponent
*/
wcm.BoxComponent = Ext.extend(wcm.Component, {
	initComponent : function(){
		wcm.BoxComponent.superclass.initComponent.apply(this, arguments);
		this.addEvents('resize', 'move');
	},

	setWidth : function(width){
		this.setSize(width);	
	},

	setHeight : function(height){
		this.setSize(undefined, height);	
	},

	setSize : function(width, height){
		if(!this.rendered){
			this.width = width;
			this.height = height;
			return this;
		}	
		if(width == this.lastWidth 
				&& height == this.lastHeight){
			return this;
		}
		if(width == undefined && height == undefined) return this;
		var dom = this.getElement();
		if(width){
			this.lastWidth = width;
			dom.style.width = width;
		}
		if(height){
			this.lastHeight = height;
			dom.style.height = height;
		}
		this.onResize(width, height);
		this.fireEvent('resize', this, width, height);
	},

	setLeft : function(left){
		this.setPosition(left);	
	},

	setTop : function(top){
		this.setPosition(undefined, top);	
	},

	setPosition : function(left, top){
		if(!this.rendered){
			this.left = left;
			this.top = top;
			return this;
		}
		if(left == this.lastLeft 
				&& top == this.lastTop){
			return this;
		}
		if(left == undefined && top == undefined) return this;
		var dom = this.getElement();
		if(left){
			this.lastLeft = left;
			dom.style.left = left;
		}
		if(top){
			this.lastTop = top;
			dom.style.top = top;
		}
		this.onPosition(left, top);
		this.fireEvent('move', this, left, top);
	},
	
	afterRender : function(){
		wcm.BoxComponent.superclass.afterRender.apply(this, arguments);
		this.setSize(this.width, this.height);
		this.setPosition(this.left, this.top);
	},

	onResize : function(width, height){
		
	},

	onPosition : function(left, top){
		
	}
});
wcmXCom.reg('box', wcm.BoxComponent);


/**
 * @class wcm.Container
*/
wcm.Container = Ext.extend(wcm.BoxComponent, {
	initComponent : function(){
		wcm.Container.superclass.initComponent.apply(this, arguments);
		this.addEvents('add', 'remove');
	},

	initItems : function(){
		if(!this.items){
			this.items = [];
		}
	},

	add : function(comp){
		var a = arguments;
		if(a.length > 1){
			for (var i = 0; i < a.length; i++){
				this.add(a[i]);
			}
			return;
		}		
		if(!this.items){
			this.initItems();
		}
		this.items.push(comp);
		comp.ownerCt = this;
		this.fireEvent('add', this, comp, this.items.length);
		return comp;
	},

	remove : function(comp){
		if(!comp) return;
		if(!this.items) return;
		this.items.remove(comp);
		delete comp.ownerCt;
		comp.destroy();
		this.fireEvent('remove', this, comp);
	},

	onRender : function(){
		wcm.Container.superclass.onRender.apply(this, arguments);
		var items = this.items;
		if (items) {
			for (var i = 0; i < items.length; i++){
				if(!items[i].rendered){
					items[i].render(items[i]);
				}
			}
		}
	},

	beforeDestroy : function() {
		var items = this.items;
		if (items) {
			for (var i = 0; i < items.length; i++){
				items[i].destroy();
			}
		}
		delete this.items;
		wcm.Container.superclass.beforeDestroy.apply(this, arguments);
	}
});
wcmXCom.reg('container', wcm.Container);


/**
 * @class wcm.Panel
*/
(function(){	
	var htmlTemplate = [
		'<div class="{0} l">',
			'<div class="r">',
				'<div class="c" id="{1}">{2}</div>',
			'</div>',
		'</div>'
	].join("");

	wcm.Panel = Ext.extend(wcm.Container, {
		baseZIndex : 0,
		closable : true,
		maskable : true,
		closeAction : 'close',
		draggable : true,
		constrainBound : false, 

		initComponent : function(){
			wcm.Panel.superclass.initComponent.apply(this, arguments);
		},

		getDragElId : function(){
			return this.header;
		},
		
		setTitle : function(title){
			Element.update(this.getElement(this.titleId), title);
		},

		onRender : function(){
			wcm.Panel.superclass.onRender.apply(this, arguments);
			var id = this.getId();
			var container = this.getWin().document.body;
			if(this.renderTo){
				container = this.getElement(this.renderTo);
			}
			new Insertion.Bottom(container, String.format('<div class="{0}" id="{1}"></div>', this.baseCls || id, id));		
			this.initHeader();
			this.initBody();
			this.initFooter();
			this.getElement().style.zIndex = this.baseZIndex + $MsgCenter.genId(100);
			if(this.draggable){
				var caller = this;
				var win = this.getWin();
				if(win.wcm.dd){
					this.dragger = Ext.apply(new win.wcm.dd.DragDrop({id : id, handleElId : this.getDragElId()}), {
						drag : function(x, y, event){
							if(caller.constrainBound){
								if(x -  this.deltaX <= 0) return;
								if(y -  this.deltaY <= 0) return;
								var pageWidth = win.document.documentElement.offsetWidth;
								if(x -  parseInt(this.deltaX, 10) + parseInt(caller.width,10) >= pageWidth) return;
								var pageHeight = win.document.documentElement.offsetHeight;
								if(y -  parseInt(this.deltaY, 10) + parseInt(caller.height,10) >= pageHeight) return;
							}
							caller.setPosition((x - this.deltaX) + "px", (y -  this.deltaY) + "px");
						}
					});
				}
			}
		},

		center : function(){
			var box = this.getCenterXY();
			this.setPosition(box.x, box.y);
		},

		getCenterXY : function(){
			var doc = this.getWin().document;
			var container = Ext.isIE ? doc.body : doc.documentElement;
			if(this.renderTo){
				container = this.getElement(this.renderTo);
			}
			var dom = this.getElement();
			var box = Element.getDimensions(dom);
			var l = (parseInt(container.clientWidth, 10) - box["width"]) / 2 + parseInt(container.scrollLeft, 10);
			var left = l + "px";
			var t = (parseInt(container.clientHeight, 10) - box["height"]) / 2 + parseInt(container.scrollTop, 10);
			var top = t + "px";
			return {x : left, y : top};
		},

		afterRender : function(){
			var box = this.getCenterXY();
			if(!this.left){
				this.left = box.x;
			}
			if(!this.top){
				this.top = box.y;
			}
			wcm.Panel.superclass.afterRender.apply(this, arguments);
		},

		syncShield : function(){
	setTimeout(function(){//fix ie7 processbar increase bug
			var dom = this.getElement();
			if(!dom) return;
			if(!this.shieldId){
				this.shieldId = this.getId() + "-sld";
			}
			var sldDom = this.getElement(this.shieldId);
			if(!sldDom){
				var doc = this.getWin().document;
				sldDom = doc.createElement("iframe");
				sldDom.id = this.shieldId;
				sldDom.src = Ext.isSecure ? Ext.SSL_SECURE_URL : '';
				sldDom.frameBorder = 0;
				Element.addClassName(sldDom, "wcm-panel-shield");
				//dom.parentNode.insertBefore(sldDom, dom);
				dom.parentNode.appendChild(sldDom);
			}
			this.synZIndex();
			if(!this.maskable){
				Position.clone(dom, sldDom);
			}else{
				var doc = this.getDoc();
				var container = Ext.isIE ? doc.body : doc.documentElement;
				sldDom.style.width = parseInt(container.scrollWidth, 10)+"px";
				sldDom.style.height = parseInt(container.scrollHeight, 10)+"px"
			}
	}.bind(this), 10);
		},

		onResize : function(){
			wcm.Panel.superclass.onResize.apply(this, arguments);
			this.syncShield();
		},

		onPosition : function(){
			wcm.Panel.superclass.onPosition.apply(this, arguments);
			this.syncShield();
		},

		synZIndex : function(){
			var zIndex = this.baseZIndex + $MsgCenter.genId(100);
			this.getElement().style.zIndex = zIndex;
			this.getElement(this.shieldId).style.zIndex = zIndex - 1;
		},

		onShow : function(){
			this.synZIndex();
			try{
				$MsgCenter.cancelKeyDown();
			}catch(error){
			}
			wcm.Panel.superclass.onShow.apply(this, arguments);
			var dom = this.getElement(this.shieldId);
			Element.removeClassName(dom, "wcm-hide-" + this.hideMode);
		},

		onHide : function(){
			var dom = this.getElement(this.shieldId);
			if(!dom || Element.hasClassName(dom, "wcm-hide-" + this.hideMode)){
				return;
			}
			Element.addClassName(dom, "wcm-hide-" + this.hideMode);
			wcm.Panel.superclass.onHide.apply(this, arguments);
			try{
				$MsgCenter.enableKeyDown();
			}catch(error){
			}
		},

		close : function(){
			//setTimeout(function(){
				if(this.fireEvent("beforeclose", this) !== false){
					this.hide();
					this.fireEvent('close', this);
					this.destroy();
				}
			//}.bind(this), 1);
		},

		beforeDestroy : function(){
			if(this.dragger){
				this.dragger.destroy();
				delete this.dragger;
			}
			if(this.closable && this.closer){
				var closer = this.getElement(this.closer);
				
				if(closer){
					Event.stopAllObserving(closer);
					closer.onblur = null;
				}
			}
			var dom = this.getElement(this.shieldId);
			Element.remove(dom);
			wcm.Panel.superclass.beforeDestroy.apply(this, arguments);
		},

		initHeader : function(){
			var dom = this.getElement();
			var id = this.getId();
			this.titleId = id + "-title";
			this.toolsId = id + "-tools";
			var sContent = String.format([
				'<div class="spt"></div>',
				'<div class="title" id="{0}"></div>',
				'<div class="tools" id="{1}"></div>'
			].join(""), this.titleId, this.toolsId);
			this.header = id + "-header";
			new Insertion.Bottom(dom, String.format(htmlTemplate, "header", this.header, sContent));
			if(this.closable){
				this.closer = id + "-closer";
				this.addTool('close', this.closer, function(event){
					Event.stop(event);
					this[this.closeAction]();
				}.bind(this));
			}
			if(this.title){
				this.setTitle(this.title);
			}
		},

		addTool : function(cls, id, cmd){
			var tools = this.getElement(this.toolsId);
			var sHtml = String.format('<a class="{0}" href="#" onfocus="this.blur();" id="{1}"></a>', cls, id);
			new Insertion.Top(tools, sHtml);		
			var dom = this.getElement(id);
			Event.observe(dom, 'click', cmd, false);
		},

		initBody : function(){
			var dom = this.getElement();
			this.bodyId = this.getId() + "-body";
			new Insertion.Bottom(dom, String.format(htmlTemplate, "body", this.bodyId, ""));
		},

		initFooter : function(){
			var dom = this.getElement();
			this.footerId = this.getId() + "-footer";
			new Insertion.Bottom(dom, String.format(htmlTemplate, "footer", this.footerId, ""));
		}
	});
	wcmXCom.reg('panel', wcm.Panel);
})();


/**
 * @class wcm.Button
*/
(function(){
	var htmlTemplate = [
		'<div class="wcm-btn wcm-btn-left {2}" id="{0}">',
			'<div class="wcm-btn-right">',
				'<div class="wcm-btn-center">',
					'<a class="wcm-btn-text" href="#" onfocus="this.blur();">{1}</a>',
				'</div>',
			'</div>',
		'</div>'
	].join("");

	wcm.Button = Ext.extend(wcm.Component, {
		onRender : function(ownerCt){
			var id = this.getId();
			var text = this.text;
			this.container = this.renderTo || ownerCt;
			new Insertion.Bottom($(this.container), String.format(htmlTemplate, id, text, this.extraCls||""));
			var dom = this.getElement();
			if(this.tip) dom.title = this.tip;
			if(this.cmd) this.on('click', this.cmd, this.scope || this);
			Event.observe(dom, 'click', this.onClick.bind(this));
		},

		onClick : function(event){
			Event.stop(event);
			if(!this.disabled && this.fireEvent("beforeclick", this) !== false){
				this.fireEvent("click", this, event);
			}
		}
	});
	wcmXCom.reg('button', wcm.Button);
})();

/**
 * @class wcm.ButtonWithMore
*/
(function(){
	var htmlTemplate = [
		'<div class="wcm-btn wcm-btn-left {2}" id="{0}">',
			'<div class="wcm-btn-right">',
				'<div class="wcm-btn-center">',
					'<a class="wcm-btn-text" href="#" onfocus="this.blur();">{1}</a>',
				'</div>',
			'</div>',
		'</div>'
	].join("");
	var btnItem = [
		'<table class="btn_item {2}" id="{0}" {2}>',
		'<tr>',
			'<td class="btn_cont" nowrap="true">{1}</td>',
		'</tr>',
		'</table>'
	].join('');
	var btnSep = [
		'<div class="btnOps_sep"></div>'
	].join('');
	var moreBtnTemplate = [
		'<div class="wcm-btn-more-btn" id="{0}_more_btn" style="display: none"></div>',
		'<div class="wcm-btn-more" id="{0}_more" style="display: none">{3}</div>'	
	].join("");
	var findOperItem = function(target){
		while(target!=null&&target.tagName.toUpperCase()!='BODY'){
			if(Element.hasClassName(target, 'btn_item')){
				return target;
			}
			target = target.parentNode;
		}
		return null;
	}
	wcm.ButtonWithMore = Ext.extend(wcm.Button, {
		operResult : {},
		onRender : function(ownerCt){
			var id = this.getId();
			var text = this.text;
			this.container = this.renderTo || ownerCt;
			if(this.more){
				var btnOpers = this.moreOpers;
				var moreBtn = this.getMoreBtnHtml(btnOpers);
				new Insertion.Bottom($(this.container), String.format(htmlTemplate+moreBtnTemplate, id, text, this.extraCls||"",moreBtn));
				this.onClickItem();
			}else{
				new Insertion.Bottom($(this.container), String.format(htmlTemplate, id, text, this.extraCls||""));
			}
			
			var dom = this.getElement();
			if(this.tip) dom.title = this.tip;
			if(this.more){
				Element.show(id+'_more_btn');
				Event.observe(id+'_more_btn','click', this.showMore.bind(this));
			}
			if(this.cmd) this.on('click', this.cmd, this.scope || this);
			Event.observe(dom, 'click', this.onClick.bind(this));
		},
		onClickItem : function(){
			var aOperResult = this.operResult;
			var id = this.getId();
			Ext.get(id+'_more').on('click', function(event, target){
				var target = findOperItem(target);
				if(target==null)return;
				var operItem = aOperResult.json[target.id];
				if(operItem==null || !operItem.cmd)return;
				if(operItem.disabled)return;
				operItem.cmd.call();
			});
			Ext.get(id+'_more').on('mouseover', function(event, target){
				var target = findOperItem(target);
				if(target==null)return;
				Element.addClassName(target, 'item_active');
			});
			Ext.get(id+'_more').on('mouseout', function(event, target){
				var target = findOperItem(target);
				if(target==null)return;
				Element.removeClassName(target, 'item_active');
			});
		},
		onClick : function(event){
			Event.stop(event);
			if(!this.disabled && this.fireEvent("beforeclick", this) !== false){
				this.fireEvent("click", this, event);
			}
		},
		getMoreBtnHtml : function(_btnOpers){
			var moreBtnHtml = [];
			var json = {};
			for (var i = 0,nLen = _btnOpers.length; i < nLen; i++){
				if(moreBtnHtml.length>0){
					moreBtnHtml.push(btnSep);
				}
				json[_btnOpers[i].id.toLowerCase()] = _btnOpers[i];
				moreBtnHtml.push(String.format(btnItem, _btnOpers[i].id, _btnOpers[i].text, _btnOpers[i].extraCls||""));
			}
			this.operResult = {
				html : moreBtnHtml.join(''),
				json : json
			};
			return moreBtnHtml.join('');
			
		},
		showMore : function(event){
			var sPanelId = this.getId()+'_more';
			//var point = {x:Event.pointerX(event), y:Event.pointerY(event)};
			//var x = point.x - 23;
			//var y = point.y + 4;
			//var x = event.x - 11;
			//var y = event.y + 20;
			var x = event.x + 4;
			var y = event.y + 4;
			var oBubbler = new wcm.BubblePanel(sPanelId);
			oBubbler.bubble([x,y], function(_Point){
				return [_Point[0], _Point[1]];
			});
			
			Event.stop(event);
		}
	});
	wcmXCom.reg('button', wcm.ButtonWithMore);
})();
