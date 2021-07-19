Ext.ns('com.trs.suggestion');

Ext.applyIf(Position, {
  // set to true if needed, warning: firefox performance problems
  // NOT neeeded for page scrolling, only if draggable contained in
  // scrollable elements
  includeScrollOffsets: false,

  // must be called before calling withinIncludingScrolloffset, every time the
  // page is scrolled
  prepare: function() {
    this.deltaX =  window.pageXOffset
                || document.documentElement.scrollLeft
                || document.body.scrollLeft
                || 0;
    this.deltaY =  window.pageYOffset
                || document.documentElement.scrollTop
                || document.body.scrollTop
                || 0;
  },

  cumulativeOffset: function(element) {
    var valueT = 0, valueL = 0;
    do {
      valueT += element.offsetTop  || 0;
      valueL += element.offsetLeft || 0;
      element = element.offsetParent;
    } while (element);
    return [valueL, valueT];
  },

  // caches x/y coordinate pair to use with overlap
  within: function(element, x, y) {
    if (this.includeScrollOffsets)
      return this.withinIncludingScrolloffsets(element, x, y);
    this.xcomp = x;
    this.ycomp = y;
    this.offset = this.cumulativeOffset(element);

    return (y >= this.offset[1] &&
            y <  this.offset[1] + element.offsetHeight &&
            x >= this.offset[0] &&
            x <  this.offset[0] + element.offsetWidth);
  },

  withinIncludingScrolloffsets: function(element, x, y) {
    var offsetcache = this.realOffset(element);

    this.xcomp = x + offsetcache[0] - this.deltaX;
    this.ycomp = y + offsetcache[1] - this.deltaY;
    this.offset = this.cumulativeOffset(element);

    return (this.ycomp >= this.offset[1] &&
            this.ycomp <  this.offset[1] + element.offsetHeight &&
            this.xcomp >= this.offset[0] &&
            this.xcomp <  this.offset[0] + element.offsetWidth);
  }
});

Ext.applyIf(Event, {
  pointerX: function(event) {
    return event.pageX || (event.clientX +
      (document.documentElement.scrollLeft || document.body.scrollLeft));
  },

  pointerY: function(event) {
    return event.pageY || (event.clientY +
      (document.documentElement.scrollTop || document.body.scrollTop));
  }
});

com.trs.suggestion.Suggestion = Class.create();

com.trs.suggestion.Suggestion.SUGGESTION_PREFIX = "COM_TRS_SUGGESTION_";
com.trs.suggestion.Suggestion.SUGGESTION_COUNT = 0;

com.trs.suggestion.Suggestion.prototype = {
	initialize : function(_sAttachElement){
		this.oRelatedElement = $(_sAttachElement);

		if(this.oRelatedElement == null){
			alert(String.format('没有找到指定的元素[{0}]！' , _sAttachElement));
			return null;
		}

		this.oPreElement = null;
		this.m_nCurrentItemIndex = -1;

		var divId = com.trs.suggestion.Suggestion.SUGGESTION_PREFIX + (com.trs.suggestion.Suggestion.SUGGESTION_COUNT);
		var frameId = com.trs.suggestion.Suggestion.SUGGESTION_PREFIX + (com.trs.suggestion.Suggestion.SUGGESTION_COUNT) + "_IFRAME";
		new Insertion.After(this.oRelatedElement, '<iframe src="about:blank" frameborder="0" id="' + frameId + '" class="Suggestion2" style="display:none;"></iframe>' + '<div id="' + divId + '" class="Suggestion" style="display:none;"></div>');
		this.oSuggestionRegionElement = $(divId);
		this.oSuggestionRegionShieldElement = $(frameId);

		Position.clone(this.oRelatedElement,this.oSuggestionRegionElement, {setHeight:false,offsetTop:this.oRelatedElement.offsetHeight});

		this.oSuggestionRegionShieldElement.style.left	= this.oSuggestionRegionElement.style.left;
		this.oSuggestionRegionShieldElement.style.width	= this.oSuggestionRegionElement.style.width;
		this.oSuggestionRegionShieldElement.style.top	= this.oSuggestionRegionElement.style.top;			
		
		this.onInputMouseDown.bindFn = this.onInputMouseDown.bind(this);
		this.onInputKeyDown.bindFn = this.onInputKeyDown.bind(this);
		this.onInputKeyUp.bindFn = this.onInputKeyUp.bind(this);
		this.onInputFocus.bindFn = this.onInputFocus.bind(this);
		this.onInputClick.bindFn = this.onInputClick.bind(this);
		this.onInputBlur.bindFn = this.onInputBlur.bind(this);
		this.onItemsBlur.bindFn = this.onItemsBlur.bind(this);
		Event.observe(this.oRelatedElement, 'mousedown',this.onInputMouseDown.bindFn, false);
		Event.observe(this.oRelatedElement, 'keydown',	this.onInputKeyDown.bindFn, false);
		Event.observe(this.oRelatedElement, 'keyup',	this.onInputKeyUp.bindFn, false);
		Event.observe(this.oRelatedElement, 'focus',	this.onInputFocus.bindFn, false);
		Event.observe(this.oRelatedElement, 'click',	this.onInputClick.bindFn, false);
		Event.observe(this.oRelatedElement, 'blur',		this.onInputBlur.bindFn, false);
		
		Event.observe(this.oSuggestionRegionElement, 'blur',	this.onItemsBlur.bindFn, false);

		com.trs.suggestion.Suggestion.SUGGESTION_COUNT++;
	},

	onKeyDeal : function(nKeyCode, _event){
		this.oSuggestionRegionElement.style.display = '';
		this.oSuggestionRegionShieldElement.style.display = "";	

		if(!(nKeyCode == Event.KEY_DOWN || nKeyCode == Event.KEY_UP || nKeyCode == Event.KEY_RETURN)){
			if(this.doOnCommonKey(nKeyCode)){//返回true表示要请求刷新，false表示不请求刷新
				this.m_nCurrentItemIndex = -1;
				this.oPreElement = null;				
			}
			return true;
		}

		if(!this.oSuggestionRegionElement.hasChildNodes()){
			if(this.dealWithOnKeyReturn)
				this.dealWithOnKeyReturn();
			return true;;
		}

		var nChildrenSize = this.oSuggestionRegionElement.childNodes.length;
		var keyType = '';
		switch(nKeyCode){
			case Event.KEY_DOWN:
				/*
				this.m_nCurrentItemIndex++;
				if( this.m_nCurrentItemIndex >= nChildrenSize){
					this.m_nCurrentItemIndex = 0;
				}
				*/
				this.getNextValidItem();
				keyType = "KeyDown";
				break;
			case Event.KEY_UP:
				/*
				this.m_nCurrentItemIndex--;	
				if( this.m_nCurrentItemIndex<0 ){
					this.m_nCurrentItemIndex = nChildrenSize-1;
				}
				*/
				this.getPreviousValidItem();
				keyType = "KeyUp";
				break;
			case Event.KEY_RETURN:
				keyType = "KeyReturn";
				break;
			default:
				break;
		}

		if(this.oPreElement != null){
			Element.removeClassName(this.oPreElement, "Suggestion_MouseOver");
			Element.addClassName(this.oPreElement, "Suggestion_MouseOut");
		}

		var oSrcElement = this.oSuggestionRegionElement.childNodes[this.m_nCurrentItemIndex];

		var fun = eval("this.dealWithOn" + keyType);
		if(fun){//对于按下某个功能键的额外处理
			fun(oSrcElement);
		}
		if(keyType == 'KeyReturn'){
			//this.oSuggestionRegionElement.style.display = 'none';
			//this.oSuggestionRegionShieldElement.style.display = "none";		
			//this.oRelatedElement.focus();
		}
		if(oSrcElement == null){
			return true;
		}
		Element.removeClassName(oSrcElement, "Suggestion_MouseOut");
		Element.addClassName(oSrcElement, "Suggestion_MouseOver");

		this.oPreElement = oSrcElement;
		var json = AttributeHelper.getAttribute(this.oPreElement,"_oJson");
		AttributeHelper.setAttribute(this.oRelatedElement,"json", json);
		if(json && this._onOptionClick(_event, json)){
			this.oRelatedElement.value = this.oPreElement.innerText;
		}else if(json == null){
			this.oRelatedElement.value = this.oPreElement.innerText;
		}
		var nScrollTopOffset = this.m_nCurrentItemIndex - 1;
		if(nScrollTopOffset < 0) nScrollTopOffset = 0;
		this.oSuggestionRegionElement.scrollTop = nScrollTopOffset * 15;
		return true;		
	},

	onInputKeyDown : function(_event){
		var event = _event || window.event;
		var nKeyCode = event.keyCode;
		setTimeout(function(){
			//var eventObj = document.createEventObject();  for ff
			if(document.createEventObject){
				var eventObj = document.createEventObject();
			}else{
				var eventObj = Object.clone(_event);
			}
			eventObj.keyCode = nKeyCode;
			eventObj.type = 'keyup';
			this.onKeyDeal(nKeyCode, eventObj);
			if(this.oSuggestionRegionElement.hasChildNodes() && (nKeyCode == Event.KEY_DOWN || nKeyCode == Event.KEY_UP)){
				if(!this.exitTimeout){
					setTimeout(arguments.callee, 200);
				}
			}
		}.bind(this), 10);
	},

	onInputKeyUp : function(_event){
		this.exitTimeout = true;
	},
	
	onInputBlur : function(_event){
		var event = window.event || _event;
		if(!Position.within(this.oSuggestionRegionElement, Event.pointerX(event), Event.pointerY(event))){
			this.oSuggestionRegionElement.style.display = '';
			this.oSuggestionRegionShieldElement.style.display = "none";	
			return;
		}		
	},
	onInputFocus : function(_event){	
		this.oSuggestionRegionElement.style.display = "none";
		this.oSuggestionRegionShieldElement.style.display = "";	
	},
	onInputMouseDown : function(){
		this.synPosition();
	},
	synPosition : function(){
		Position.clone(this.oRelatedElement,this.oSuggestionRegionElement, {setWidth:false,setHeight:false,offsetTop:this.oRelatedElement.offsetHeight});
		this.oSuggestionRegionShieldElement.style.left	= this.oSuggestionRegionElement.style.left;
		this.oSuggestionRegionShieldElement.style.width	= this.oSuggestionRegionElement.style.width;
		this.oSuggestionRegionShieldElement.style.top	= this.oSuggestionRegionElement.style.top;			
	},

	onInputClick : function(_event){	
		this.onInputFocus(_event);
	},

	doOnCommonKey : function(nKeyCode){
		this.clearAllItems();
		this._sendRequest();	
		return true; //表示是否请求刷新
	},

	clearAllItems : function(){
		this.oPreElement = null;
		this.m_nCurrentItemIndex = -1;
		while(this.oSuggestionRegionElement.childNodes.length > 0){
			this.oSuggestionRegionElement.removeChild(this.oSuggestionRegionElement.childNodes[0]);
		}
	},
	
	_sendRequest : function(){
		this.sendRequest();
	},

	sendRequest : function(){
		var url = this.oRelatedElement.getAttribute("_url");
		if(url == undefined){
			alert(wcm.LANG.suggestion_1089 || "必须实现sendRequest方法或者指定合法的_url属性！");
			return;
		}
		var params = this.oRelatedElement.getAttribute("_params");
		if(params == undefined){//没有定义参数，则用控件名和控件值作为参数
			params = encodeURIComponent(this.oRelatedElement.name + "=" + this.oRelatedElement.value);
		}else{//用指定的参数作为QueryString,如：_params = "userName=$F('userName')&password=$F('password')"
			var tempParams = params.toQueryParams();
			for(prop in tempParams){
				tempParams[prop] = eval(tempParams[prop]);
			}
			params = $H(tempParams).toQueryString();
		}
		var ajax = new Ajax.Request(url, {
			method		: 'get',
			parameters	: params,
			onComplete	: this._onComplete.bind(this),
			onFailure	: this.onFailure.bind(this)
		});	
	},

	_onComplete : function(originalRequest){
		this.onComplete(originalRequest);
	},

	/*必须实现onComplete方法或者ajax请求返回的xml格式为
	 *	<items><item>...</item>...</items>
	 *  其中的item用的是控件的_item属性，没有指定的话，则用item字符串
	*/
	onComplete : function(originalRequest){
		var subTagName = this.oRelatedElement.getAttribute("_item") || "item";
		var xmlDoc = originalRequest.responseXML;	
		var items = xmlDoc.getElementsByTagName(subTagName);
		if(!items || !items.length){
			return;
		}
		for(var count = 0; count < items.length; count++){
			var itemValue = items[count].firstChild.nodeValue;
			this.addItem(itemValue);
		}
	},

	onFailure : function(originalRequest){
		alert('Ajax request failure');
	},

	//添加多项，这些项的内容由_fWorker(_arrJson[...])得到
	addOptions : function(_arrJson,_fWorker){
		_arrJson.each(function(e){this.addItem(e, _fWorker)}.bind(this));
	},

	//添加一项，这项的内容由_fWorker(_oJson)得到
	addOption : function(_oJson, _fWorker){
		var sHtml = _oJson;
		if(_fWorker){
			sHtml = _fWorker(_oJson);
		}
		if(typeof sHtml == 'string'){
			this.addItem(sHtml, _oJson);
		}else{
			//this.addItem(sHtml);
			//TODO
		}	
	},

	//添加一项，这项的内容为_sItemHTML
	addItem : function(_sItemHTML, _oJson){
		var div = document.createElement("div");
		div.innerHTML = _sItemHTML;
		
		this.oSuggestionRegionElement.appendChild(div);
		if(this.oSuggestionRegionElement.childNodes.length > 10){
			this.oSuggestionRegionElement.style.overflow  = "auto";
		}
		
		div.setAttribute("_index", "" + (this.oSuggestionRegionElement.childNodes.length - 1) + "");
		if(_oJson){
			AttributeHelper.setAttribute(div,"_oJson", _oJson);
		}

		Element.addClassName(div, "Suggestion_MouseItem");
		Element.addClassName(div, "Suggestion_MouseOut");
		
		Event.observe(div, 'mouseover', this.onItemMouseOver.bind(this, div), false);
		Event.observe(div, 'click',	this.onItemClick.bind(this, div), false);	
	},

	onItemMouseOver : function(srcElement, _event){
		if(this.oPreElement){
			Element.removeClassName(this.oPreElement, "Suggestion_MouseOver");
			Element.addClassName(this.oPreElement, "Suggestion_MouseOut");
		}
		Element.removeClassName(srcElement, "Suggestion_MouseOut");
		Element.addClassName(srcElement, "Suggestion_MouseOver");
		this.oPreElement = srcElement;
		this.m_nCurrentItemIndex = parseInt(srcElement.getAttribute("_index"));
	},

	_onOptionClick : function(_event, _oJson){//单击某下拉项时的处理
		if(this.onOptionClick){
			return this.onOptionClick(_event, _oJson);
		}
		return Prototype.K(true);
	},

	onItemClick : function(srcElement, _event){
		var event = window.event || _event;
		if(this._onOptionClick(event, AttributeHelper.getAttribute(srcElement,"_oJson"))){
			this.oRelatedElement.value = this.oPreElement.innerText;
		}
		this.oSuggestionRegionElement.style.display = 'none';
		this.oSuggestionRegionShieldElement.style.display = "none";	
	},
	getCurrSelect : function(){
		return this.oPreElement;
	},
	onItemsBlur : function(_event){	
		var event = window.event || _event;
		if(!Position.within(this.oSuggestionRegionElement, Event.pointerX(event), Event.pointerY(event))){
			this.oSuggestionRegionElement.style.display = 'none';
			this.oSuggestionRegionShieldElement.style.display = "none";	
		}
	},
	hideNotMatch : function(){//隐藏所有不匹配的项
		var matchValue = this.oRelatedElement.value;
		var childs = this.oSuggestionRegionElement.childNodes;
		for (var i = 0; i < childs.length; i++){
			var child = childs[i];
			if(child.innerText.indexOf(matchValue) >= 0){
				child.style.display = '';
			}else{
				child.style.display = 'none';
			}
		}
	},
	isValid : function(element){//判断项element是否有效
		try{
			return Element.visible(element);
		}catch(error){
			alert(element.nodeType);
		}
	},
	getNextValidItem : function(){//得到有效的下一项
		var childs = this.oSuggestionRegionElement.childNodes;
		var childSize = childs.length;
		if(childSize == 0)return;
		var oldIndex = this.m_nCurrentItemIndex; //可能为-1
		var index = (oldIndex+1) % childSize;
		while(index != oldIndex){
			if(this.isValid(childs[index])){
				break;
			}
			index = (++index) % childSize;
			if(index == 0 && oldIndex == -1){
				index = -1;
				break;
			}
		}
		this.m_nCurrentItemIndex = index;
	},
	getPreviousValidItem : function(){//得到有效的前一项
		var childs = this.oSuggestionRegionElement.childNodes;
		var childSize = childs.length;
		if(childSize == 0)return;
		var oldIndex = this.m_nCurrentItemIndex;
		var index = (oldIndex <= 0) ? (childSize-1) : (oldIndex-1) % childSize;
		while(index != oldIndex){
			if(this.isValid(childs[index])){
				break;
			}
			index = (--index) % childSize;
			if(index < 0){
				index = childSize-1;
			}
			if(index == (childSize-1) && oldIndex == -1){
				index = -1;
				break;
			}
		}
		this.m_nCurrentItemIndex = index;		
	},
	destroy : function(){
		Event.stopObserving(this.oRelatedElement, 'mousedown',this.onInputMouseDown.bindFn, false);
		Event.stopObserving(this.oRelatedElement, 'keydown',	this.onInputKeyDown.bindFn, false);
		Event.stopObserving(this.oRelatedElement, 'keyup',	this.onInputKeyUp.bindFn, false);
		Event.stopObserving(this.oRelatedElement, 'focus',	this.onInputFocus.bindFn, false);
		Event.stopObserving(this.oRelatedElement, 'click',	this.onInputClick.bindFn, false);
		Event.stopObserving(this.oRelatedElement, 'blur',		this.onInputBlur.bindFn, false);		
		Event.stopObserving(this.oSuggestionRegionElement, 'blur',	this.onItemsBlur.bindFn, false);
	}
}

var AttributeHelper = {
	prefix : 'attr_',
	autoId : 0,
	cache : {},
	setAttribute : function(element, sAttrName, sAttrValue){
		element = $(element);
		if(!element) return false;
		var _id = this.getId(element);
		var cache = this.cache;
		var attributes = cache[_id];
		if(!attributes){
			attributes = cache[_id] = {};
		}
		attributes[sAttrName.toUpperCase()] = sAttrValue;
	},
	getAttribute : function(element, sAttrName){
		element = $(element);
		if(!element) return null;
		var _id = this.getId(element);
		var cache = this.cache;
		var attributes = cache[_id];
		if(!attributes){
			return null;
		}
		return attributes[sAttrName.toUpperCase()];
	},
	removeAttribute : function(element, sAttrName){
		element = $(element);
		if(!element) return;
		var _id = this.getId(element);
		var cache = this.cache;
		var attributes = cache[_id];
		if(!attributes){
			return;
		}
		delete attributes[sAttrName.toUpperCase()];
	},
	getId : function(element){
		var _id = element.getAttribute("_id");
		if(_id != undefined) return _id;
		_id = element.name || element.id || (this.prefix + (++this.autoId));
		_id = _id.toUpperCase();
		element.setAttribute("_id", _id);
		return _id;
	}
};