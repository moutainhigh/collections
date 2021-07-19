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