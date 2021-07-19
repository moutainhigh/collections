$package("com.trs.menu");
com.trs.menu.SimpleMenu = Class.create();
Object.extend(com.trs.menu.SimpleMenu.prototype, {
	sMenuCls : 'menu_',
	sShieldCls : 'menuShield_',
	oContainer : null,
	oIframeShield : null,
	aItems : null,
	sSeparator : '/',
	sSelectedItemCls : 'selectedItem',
	oLastSelectedItem : null,
	args : null,
	isInContainer : false,
	maxWidth : 300,
	minWidth : 150,
	initialize : function(){
		
	},
	bindEvents : function(){
		Event.observe(this.oContainer, 'mousemove', this.mouseMoveEvent.bind(this));
		Event.observe(this.oContainer, 'mouseover', this.mouseOverEvent.bind(this));
		Event.observe(this.oContainer, 'mouseout', this.mouseOutEvent.bind(this));
		Event.observe(this.oContainer, 'blur', this.blurEvent.bind(this));
		Event.observe(this.oContainer, 'click', this.clickEvent.bind(this));
		Event.observe(window, 'unload', this.destroy.bind(this));
	},
	mouseMoveEvent : function(event){
		var srcElement = Event.element(window.event || event);
		var oprKey = srcElement.getAttribute("oprKey");
		if(oprKey == null) return;
		if(this.oLastSelectedItem == srcElement) return;
		if(this.oLastSelectedItem){
			Element.removeClassName(this.oLastSelectedItem, this.sSelectedItemCls);
		}
		Element.addClassName(srcElement, this.sSelectedItemCls);
		this.oLastSelectedItem = srcElement;
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
		Element.hide(this.oContainer);
		Element.hide(this.oIframeShield);
		this.oLastSelectedItem = null;
	},
	clickEvent : function(event){
		this.hide();
		var srcElement = Event.element(window.event || event);
		var oprKey = srcElement.getAttribute("oprKey");
		if(oprKey == null) return;

		for (var i = 0, length = this.aItems.length; i < length; i++){
			if(this.aItems[i] && this.aItems[i]["oprKey"] == oprKey){
				this.aItems[i]["cmd"](this.args);
				break;
			}
		}
	},
	hide : function(){
		Element.hide(this.oContainer);
		Element.hide(this.oIframeShield);
		this.isShow = false;
	},
	show : function(aItems, args){
		if(!aItems || aItems.length <= 0) return;
		this.args = args || {};
		if(this.oContainer == null){
			this.oContainer = document.createElement("div");
			document.body.appendChild(this.oContainer);
			Element.addClassName(this.oContainer, this.sMenuCls);
			this.oIframeShield = document.createElement("iframe");
			this.oIframeShield.src = "about:blank";
			this.oIframeShield.frameBorder = "no";
			this.oIframeShield.scrolling = "no";
			document.body.appendChild(this.oIframeShield);
			Element.addClassName(this.oIframeShield, this.sShieldCls);			
			this.bindEvents();
		}
		var isItemShowed = false;
		var isSeparatorInPre = true;
		var sContent = '';
		for(var index = 0; index < aItems.length; index++){
			var oItem = aItems[index];
			if(oItem == this.sSeparator){
				if(isSeparatorInPre) continue;
				sContent += "<div class='separator'></div>";
				isSeparatorInPre = true;
				continue;
			}
			var isHide = false;
			var attrs = ["oprKey='" + oItem["oprKey"] + "'"];
			var aCls = [];
			if(oItem["iconCls"]){
				aCls.push(oItem["iconCls"]);
			}
			if(oItem["cls"]){
				var sCls = oItem["cls"];
				if(typeof oItem["cls"] == 'function'){
					sCls = oItem["cls"](this.args);
				}
				if(sCls && sCls.trim() != ""){
					if(sCls.indexOf('display_none') >= 0){
						isHide = true;
					}
					aCls.push(sCls);
				}
			}
			if(!isHide){
				isItemShowed = true;
				isSeparatorInPre = false;
			}
			if(aCls.length > 0){
				attrs.push("class='" + aCls.join(" ") + "'");
			}
			sContent += "<div " + (attrs.join(" ")) + ">" + (oItem["desc"] || "") + "</div>";
		}
		if(!isItemShowed) return;
		this.oContainer.innerHTML = sContent;

		//deal with the last separator.
		if(isSeparatorInPre){
			var tempNode = getLastHTMLChild(this.oContainer);
			while(tempNode){
				if(Element.hasClassName(tempNode, "separator")){
					Element.remove(tempNode);
					break;
				}
				tempNode = getPreviousHTMLSibling(tempNode);
			}
		}
		//set width
		this.oContainer.style.overflow = 'visible';
		var offset = Element.getDimensions(this.oContainer);
		var width = Math.max(Math.min(this.maxWidth, offset["width"]), this.minWidth);
		this.oContainer.style.width = width;
		this.oContainer.style.overflow = 'hidden';
		Element.show(this.oContainer);

		//set menu position, because the edge of window
		var left = this.args["x"] || 0;
		var right = left + this.oContainer.offsetWidth;
		if(right >= this.oContainer.ownerDocument.body.offsetWidth){
			left = Math.max(left - offset["width"], 0);
		}
		this.oContainer.style.left = left;

		var top = this.args["y"] || 0;
		var bottom = top + this.oContainer.offsetHeight;
		if(bottom >= this.oContainer.ownerDocument.body.offsetHeight){
			top = Math.max(top - offset["height"] - 5, 0);
		}
		this.oContainer.style.top = top;

//		Element.show(this.oContainer);
		Position.clone(this.oContainer, this.oIframeShield);
		Element.show(this.oIframeShield);
		this.isShow = true;
		setTimeout(function(){
			try{
				this.oContainer.focus();
			}catch(error){
				//just skip it.
			}
		}.bind(this), 10);
		this.aItems = aItems;
	},
	destroy : function(){
		Event.stopAllObserving(this.oContainer);
		delete this.oContainer;
		delete this.oIframeShield;
		delete this.aItems;
		delete this.oLastSelectedItem;
		delete this.args;
	}
});