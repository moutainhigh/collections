//下拉提示
Ext.ns('Event');
Ext.applyIf(Event, {
	KEY_BACKSPACE:	8,
	KEY_TAB:		9,
	KEY_RETURN:		13,
	KEY_ESC:		27,
	KEY_LEFT:		37,
	KEY_UP:			38,
	KEY_RIGHT:		39,
	KEY_DOWN:		40,
	KEY_DELETE:		46,
	KEY_PGUP:		33,
	KEY_PGDN:		34,
	KEY_HOME:		36,
	KEY_END:		35
});
Ext.ns("wcm.Suggestion");
wcm.Suggestion = function(config){
	Ext.apply(this, config || {});
};
Ext.apply(wcm.Suggestion.prototype, {
	autoComplete : true,
	requestOnFocus : true,
	init : function(config){
		if(this.inited) return;
		this.inited = true;
		config = Ext.apply({"list-min-height" : '80px', "list-max-height" : '140px'}, config);
		if(!config['el']){
			alert("the [el] parameter must be set! ");
			return;
		}
		this.inputEl = $(config['el']);
		Ext.apply(this, config);
		this.initBoxEl();
		this.initEvents();
	},
	initBoxEl : function(){
		var doc = this.inputEl.ownerDocument;
		var dom = doc.createElement("div");
		dom.style.display = 'none';
		dom.setAttribute("unselectable", "on");
		Element.addClassName(dom, "sg-box");
		doc.body.appendChild(dom);
		this.boxEl = dom;
	},
	initEvents : function(){
		this.keyUpEventBind = this.keyUpEvent.bind(this);
		this.blurEventBind = this.blurEvent.bind(this);
		Event.observe(this.inputEl, 'keyup', this.keyUpEventBind);
		Event.observe(this.inputEl, 'blur', this.blurEventBind);
		if(this.requestOnFocus){
			this.focusEventBind = this.focusEvent.bind(this);
			Event.observe(this.inputEl, 'focus', this.focusEventBind);
		}
		Event.observe(this.boxEl, 'mousemove', this.mouseMoveEvent.bind(this));
		Event.observe(this.boxEl, 'click', this.clickEvent.bind(this));
		Event.observe(this.boxEl, 'mouseover', this.mouseOverEvent.bind(this));
		Event.observe(this.boxEl, 'mouseout', this.mouseOutEvent.bind(this));
		Event.observe(this.boxEl, 'selectstart', function(){return false;});
	},
	keyUpEvent : function(event){
		event = event || window.event;
		var keyCode = event.keyCode;
		switch(keyCode){
			case Event.KEY_RETURN:
				this.keyEnter(event);
				break;
			case Event.KEY_UP:
				this.keyUp(event);
				break;
			case Event.KEY_DOWN:
				this.keyDown(event);
				break;
			default:
				this.keyCommon(event);
		}
	},
	mouseOverEvent : function(event){
		this.inBoxEl = true;
	},
	mouseOutEvent : function(event){
		this.inBoxEl = false;
	},
	blurEvent : function(event){
		if(this.inBoxEl) return;
		this.hideList();
	},
	focusEvent : function(){
		this.request(this.getInputValue());
	},
	keyCommon : function(event){
		var keyCode = event.keyCode;
		if(this.getInputValue() == "" && !this.requestOnFocus){
			this.hideList();
			return;
		}
		if(keyCode == Event.KEY_LEFT || keyCode == Event.KEY_RIGHT) return;
		this.request(this.getInputValue());
		this.showList();
	},
	request : function(){
		//TODO request for get something and the addItem to this suggestion.
	},
	keyEnter : function(){
		this.hideList();
		this.execute();
	},
	keyUp : function(){
		if(!this.boxEl || !Element.visible(this.boxEl)) return;
		var item = null;
		if(this.selectedItem){
			item = Element.previous(this.selectedItem);
			var height = this.selectedItem.offsetHeight;
			var scrollTop = this.boxEl.scrollTop;
			this.boxEl.scrollTop = scrollTop - height;
		}else{
			item = Element.last(this.boxEl);
			this.boxEl.scrollTop = this.boxEl.scrollHeight;
		}
		if(!item) return;
		this.setSelectedItem(item, true);
	},
	keyDown : function(){
		if(!this.boxEl || !Element.visible(this.boxEl)) return;
		var item = null;
		if(this.selectedItem){
			item = Element.next(this.selectedItem);
			var height = this.selectedItem.offsetHeight;
			var scrollTop = this.boxEl.scrollTop;
			this.boxEl.scrollTop = scrollTop + height;
		}else{
			item = Element.first(this.boxEl);
			this.boxEl.scrollTop = 0;
		}
		if(!item) return;
		this.setSelectedItem(item, true);
	},
	mouseMoveEvent : function(event){
		event = event || window.event;
		var element = Event.element(event);
		var item = this.findItem(element);
		if(item == this.selectedItem) return;
		this.setSelectedItem(item);
	},
	findItem : function(dom){
		while(dom && dom.tagName != "BODY"){
			if(Element.hasClassName(dom, "item")){
				return dom;
			}
			dom = dom.parentNode;
		}
		return null;
	},
	clickEvent : function(event){
		event = event || window.event;
		var element = Event.element(event);
		var item = this.findItem(element);
		if(!item) return;	
		this.setSelectedItem(item, true);
		this.hideList();
		this.execute();
	},
	showList : function(){
		//set height.
		var nodes = this.boxEl.childNodes;
		if(nodes.length < 4) this.boxEl.style.height = this["list-min-height"];
		if(nodes.length > 8) this.boxEl.style.height = this["list-max-height"];
		if(Element.visible(this.boxEl)) return;
		if(!this.initPosition){
			this.initPosition = true;
			Position.clone(this.inputEl, this.boxEl, {setHeight:false, offsetTop:this.inputEl.offsetHeight});
		}else{
			Position.clone(this.inputEl, this.boxEl, {setLeft : false, setHeight:false, offsetTop:this.inputEl.offsetHeight});
		}
		Element.show(this.boxEl);
	},
	hideList : function(){
		Element.hide(this.boxEl);
	},
	setSelectedItem : function(item, bSynInputValue){
		if(this.selectedItem) Element.removeClassName(this.selectedItem, "selected");		
		if(item) Element.addClassName(item, "selected");		
		this.selectedItem = item;
		if(bSynInputValue && this.autoComplete) this.setInputValue(this.getListValue());
	},
	getList : function(){
		return this.boxEl;
	},
	getListValue : function(item){
		item = item || this.selectedItem;
		if(!item) return this.getInputValue();
		return item.getAttribute("_value") || "";
	},
	getInput : function(){
		return this.inputEl;
	},
	getInputValue : function(){
		return this.inputEl.value;
	},
	setInputValue : function(sValue){
		this.inputEl.value = sValue;
	},
	execute : function(){
		//TODO override the method.
		//alert(this.getInputValue());
	},
	setItems : function(items){
		delete this.selectedItem;
		this.setItemsHtml(this.html(items));			
		this.fix4Ie();
	},
	setItemsHtml : function(sHtml){
		delete this.selectedItem;
		Element.update(this.boxEl, sHtml);
		this.showList();
		this.fix4Ie();
	},
	addItems : function(items){
		var sHtml = this.html(items);
		new Insertion.Bottom(this.boxEl, sHtml);
		this.showList();
		this.fix4Ie();
	},
	addItem : function(item){
		var sHtml = this.html(item);
		new Insertion.Bottom(this.boxEl, sHtml);
		this.showList();
		this.fix4Ie();
	},
	isIE : function(){
		var ua = navigator.userAgent.toLowerCase();
		var isIE = ua.indexOf("opera") == -1 && ua.indexOf("msie") > -1;
		return isIE;
	}(),
	fix4Ie : function(){
		return; //be afraid of performance.
		if(!this.isIE) return;
		var doms = this.boxEl.getElementsByTagName("*");
		for (var i = 0; i < doms.length; i++){
			doms.setAttribute("unselectable", "on");
		}
	},
	html : function(item){
		item = Ext.Json.toUpperCase(item);
		if(Array.isArray(item)){
			var aHtml = [];
			for (var i = 0; i < item.length; i++){
				aHtml.push(this.html(item[i]));
			}
			return aHtml.join("");
		}
		var sValue = (""+item["VALUE"]).replace(/\'/g,"&#39;");
		var sLable = (""+item["LABEL"]).replace(/\'/g,"&#39;");
		return [
			"<div unselectable='on' class='item' _value='", sValue, "'>", sLable, "</div>" 
		].join("");
	},
	destroy : function(){
		delete this.inited;
		Event.stopObserving(this.inputEl, 'keyup', this.keyUpEventBind);
		delete this.keyUpEventBind;
		Event.stopObserving(this.inputEl, 'blur', this.blurEventBind);
		delete this.blurEventBind;
		if(this.requestOnFocus){
			Event.stopObserving(this.inputEl, 'focus', this.focusEventBind);
			delete this.focusEventBind;
		}
		delete this.inputEl;
		if(this.boxEl1){
			Event.stopObserving(this.boxEl);
			Element.remove(this.boxEl);
		}
		delete this.boxEl;
		delete this.inBoxEl;
	}
});