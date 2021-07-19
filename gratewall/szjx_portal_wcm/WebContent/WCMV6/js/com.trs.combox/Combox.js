$package('com.trs.combox');

$importCSS("com.trs.combox.resource.combox");


com.trs.combox.Combox = Class.create();

com.trs.combox.Combox.COMBOX_PREFIX = "COM_TRS_COMBOX_";
com.trs.combox.Combox.COMBOX_COUNT = 0;
com.trs.combox.Combox.CACHE = {};
/*
eg.
com.trs.combox.Combox.crate({
	container		: 'divContainer',
	height:20,
	width:200,
    defaultValue : 'defaultValue',
	input			: {
		name : 'inputName',
		value: 'inputValue',
		className: 'inputClass',
		attributes: [{name: '', value: ''}]
	},
	entrys			: [
		{text : '', value :''],
		{text : '', value :''],
		{text : '', value :'']
	]
});
*/
/**
*combox生成的静态方法
*/
com.trs.combox.Combox.create = function(oComboxInfo){
	var oContainer = $(oComboxInfo.container);
	if(oContainer == null){
		alert("容器对象为空");
		return;
	}
	oInputInfo = Object.extend({
		name		: 'combox_input_' + com.trs.combox.Combox.COMBOX_COUNT,
		value		: '',
		className	: ''
	}, oComboxInfo.input);
	var oInput = document.createElement("<input type=text name='" + oInputInfo.name + "' value='" + oInputInfo.value +"'/>");
	oInput.className = oInputInfo.className;
	oInput.id = oInput.name;

	//ge gfc add @ 2007-8-7 13:55 添加input的附加属性
	var attrs = oInputInfo.attributes;
	if(attrs != null && attrs.length > 0) {
		for (var i = 0; i < attrs.length; i++){
			oInput.setAttribute(attrs[i]['name'], attrs[i]['value']);
		}
	}

	oContainer.appendChild(oInput);
	var oCombox = new com.trs.combox.Combox(oInput, oComboxInfo.triggerOnChange);
	if(oComboxInfo.entrys){
		var entrys = oComboxInfo.entrys;
		for (var i = 0; i < entrys.length; i++){
			oCombox.addItem(entrys[i].text, entrys[i].value);
		}
	}
	if(oComboxInfo.width){
		oCombox.setWidth(oComboxInfo.width);
	}
	if(oComboxInfo.height){
		oCombox.setHeight(oComboxInfo.height);
	}
	if(oComboxInfo.defaultValue){
		oCombox.setValue(oComboxInfo.defaultValue);
	}
	return oCombox;
};

com.trs.combox.Combox.prototype = {
	initialize : function(_sAttachElement, triggerOnChange){
		this.oRelatedElement = $(_sAttachElement);

		if(this.oRelatedElement == null){
			alert('没有找到指定的元素！[' + _sAttachElement + ']' );
			return null;
		}
		com.trs.combox.Combox.CACHE[this.oRelatedElement.getAttribute("id") || this.oRelatedElement.getAttribute("name")] = this;

		this.oPreElement = null;
		this.m_nCurrentItemIndex = -1;

		//创建输入框后面的图标
		var imgId = com.trs.combox.Combox.COMBOX_PREFIX + (com.trs.combox.Combox.COMBOX_COUNT)  + "_IMG";
		new Insertion.After(this.oRelatedElement, '<iframe src="about:blank" frameborder="0" class="ComboxImgIframe" id="' + imgId + '_IFRAME"></iframe><span id="' + imgId + '" class="ComboxImg"></span>');
		this.oComboxImg = $(imgId);
		this.oComboxImgShield = $(imgId + "_IFRAME")
		Position.clone(this.oRelatedElement,this.oComboxImg,{setWidth:false,offsetLeft:this.oRelatedElement.offsetWidth-this.oComboxImg.offsetWidth});
		Event.observe(this.oComboxImg, 'click', this.onComboxImgClick.bind(this), false);		

		this.oComboxImgShield.style.width = this.oComboxImg.offsetWidth || this.oComboxImg.style.width;
		this.oComboxImgShield.style.height = this.oComboxImg.offsetHeight || this.oComboxImg.style.height;
		this.oComboxImgShield.style.top = this.oComboxImg.style.top;
		this.oComboxImgShield.style.left = this.oComboxImg.style.left;

		//创建输入框下面的下拉框
		var selectId = com.trs.combox.Combox.COMBOX_PREFIX + (com.trs.combox.Combox.COMBOX_COUNT)  + "_DIV";
		var iframeId = com.trs.combox.Combox.COMBOX_PREFIX + (com.trs.combox.Combox.COMBOX_COUNT)  + "_IFRAME";
		new Insertion.After(this.oRelatedElement, '<iframe src="about:blank" frameborder="0" class="ComboxIframe" id="'+iframeId+'" style="display:none;"></iframe><div id="' + selectId + '" class="Combox" style="display:none;"></div>');
		this.oComboxRegionElement = $(selectId);
		this.oComboxRegionShieldElement = $(iframeId);
		Position.clone(this.oRelatedElement,this.oComboxRegionElement,{setHeight:false,offsetTop:this.oRelatedElement.offsetHeight});
		
		this.oComboxRegionShieldElement.style.width = this.oComboxRegionElement.style.width;
		this.oComboxRegionShieldElement.style.top = this.oComboxRegionElement.style.top;
		this.oComboxRegionShieldElement.style.left = this.oComboxRegionElement.style.left;
		Event.observe(this.oComboxRegionElement, 'blur',	this.onItemsBlur.bind(this), false);
		Event.observe(this.oComboxRegionElement, 'keyup',	this.onItemsKeyUp.bind(this), false);

		Event.observe(this.oRelatedElement, 'keyup',	this.onInputKeyUp.bind(this), false);
		if(triggerOnChange){
			Event.observe(this.oRelatedElement, 'change',	function(event){
				this._onOptionClick(event, this.oRelatedElement.value)
			}.bind(this), false);
		}	
	
		com.trs.combox.Combox.COMBOX_COUNT++;
	},

	onComboxImgClick : function(event){
		if(this.oComboxRegionElement.style.display == 'none'){
			this.oComboxRegionElement.style.display = '';
			this.oComboxRegionShieldElement.style.display = '';
			this.oComboxRegionElement.focus();
			if(!this.oComboxRegionElement.hasChildNodes()){
				//请求得到下拉项
				this._sendRequest();			
			}
		}else{
			this.oComboxRegionElement.style.display = 'none';
			this.oComboxRegionShieldElement.style.display = 'none';
		}
		Event.stop(event || window.event);
	},
	
	onItemsKeyUp : function(_event){
		this.onInputKeyUp(_event);
	},
	onInputKeyUp : function(_event){
		var event = window.event || _event;
		var nKeyCode = (event.keyCode ? event.keyCode : event.which);

		if(!(nKeyCode == Event.KEY_DOWN || nKeyCode == Event.KEY_UP || nKeyCode == Event.KEY_RETURN)){
			return;
		}

		if(!this.oComboxRegionElement.hasChildNodes()){
			//请求得到下拉项
			this._sendRequest();
			if(!this.oComboxRegionElement.hasChildNodes())
				return ;
		}
		
		var nChildrenSize = this.oComboxRegionElement.childNodes.length;

		switch(nKeyCode){
			case Event.KEY_DOWN:
				this.m_nCurrentItemIndex++;
				if( this.m_nCurrentItemIndex >= nChildrenSize){
					this.m_nCurrentItemIndex = 0;
				}
				break;
			case Event.KEY_UP:
				this.m_nCurrentItemIndex--;	
				if( this.m_nCurrentItemIndex<0 ){
					this.m_nCurrentItemIndex = nChildrenSize-1;
				}
				break;
			case Event.KEY_RETURN:				
				this.oComboxRegionElement.style.display = 'none';
				this.oComboxRegionShieldElement.style.display = 'none';
				break;
			default:
				break;
		}

		if(this.oPreElement == null && this.m_nCurrentItemIndex == -1){
			return true;
		}
		if(this.oPreElement != null){
			Element.removeClassName(this.oPreElement, "Combox_MouseOver");
			Element.addClassName(this.oPreElement, "Combox_MouseOut");
		}

		var oSrcElement = this.oComboxRegionElement.childNodes[this.m_nCurrentItemIndex];
		if(oSrcElement == null) return;
		Element.removeClassName(oSrcElement, "Combox_MouseOut");
		Element.addClassName(oSrcElement, "Combox_MouseOver");
		
		this.oPreElement = oSrcElement;
		var json = this.oPreElement.getAttribute("_oJson");

		if(json){
			this._onOptionClick(event, json);
		}else{
			this.oRelatedElement.value = this.oPreElement.innerText;
		}
		var nScrollTopOffset = this.m_nCurrentItemIndex - 1;
		if(nScrollTopOffset < 0) nScrollTopOffset = 0;
		this.oComboxRegionElement.scrollTop = nScrollTopOffset * 15;
		return true;
	},

	setNoSelected : function(){
		if(this.oPreElement){
			Element.removeClassName(this.oPreElement, "Combox_MouseOver");
			Element.addClassName(this.oPreElement, "Combox_MouseOut");
		}
		this.oPreElement = null;
		this.m_nCurrentItemIndex = -1;
	},

	clearAllItems : function(){
		while(this.oComboxRegionElement.childNodes.length > 0){
			this.oComboxRegionElement.removeChild(this.oComboxRegionElement.childNodes[0]);
		}
	},
	
	_sendRequest : function(){
		this.sendRequest();
	},

	sendRequest : function(){
		var url = this.oRelatedElement.getAttribute("_url");
		if(url == undefined){
			if(!this.oRelatedElement.getAttribute("noPromptInfo")){
				alert("必须实现sendRequest方法或者指定合法的_url属性！");
			}
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
		var itemTagName = this.oRelatedElement.getAttribute("_item") || "item";
		var keyTagName = this.oRelatedElement.getAttribute("_key") || itemTagName;
		var excludeArray = (this.oRelatedElement.getAttribute("_exclude") || "").split(",");
		var xmlDoc = originalRequest.responseXML;	
		var items = xmlDoc.getElementsByTagName(itemTagName);
		var keys = xmlDoc.getElementsByTagName(keyTagName);
		if(!items || !items.length){
			return;
		}
		for(var count = 0; count < items.length; count++){
			var itemValue = items[count].firstChild.nodeValue;
			var keyValue = keys[count].firstChild.nodeValue;
			if(excludeArray.include(keyValue))continue;
			this.addItem(itemValue, keyValue);
		}
	},

	onFailure : function(originalRequest){
		alert('Ajax request failure');
	},

	//添加多项，这些项的内容由_fWorker(_arrJson[...])得到
	addOptions : function(_arrJson, _fWorker){
		_arrJson.each(function(e){this.addOption(e, _fWorker)}.bind(this));
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
	addItem : function(_sItemHTML, _oJson, noMouseEvent){
		var div = document.createElement("div");
		div.innerHTML = _sItemHTML;
		
		this.oComboxRegionElement.appendChild(div);
		if(this.oComboxRegionElement.childNodes.length > 10){
			this.oComboxRegionElement.style.overflow  = "auto";
		}
		
		div.setAttribute("_index", "" + (this.oComboxRegionElement.childNodes.length - 1) + "");
		if(_oJson){
			div.setAttribute("_oJson", _oJson);
		}

		if(noMouseEvent) return;
		Element.addClassName(div, "Combox_MouseItem");
		Element.addClassName(div, "Combox_MouseOut");
		
		Event.observe(div, 'mouseover', this.onItemMouseOver.bind(this, div), false);
		Event.observe(div, 'click',	this.onItemClick.bind(this, div), false);
	},

	onItemMouseOver : function(srcElement, _event){
		if(this.oPreElement){
			Element.removeClassName(this.oPreElement, "Combox_MouseOver");
			Element.addClassName(this.oPreElement, "Combox_MouseOut");
		}
		Element.removeClassName(srcElement, "Combox_MouseOut");
		Element.addClassName(srcElement, "Combox_MouseOver");
		this.oPreElement = srcElement;
		this.m_nCurrentItemIndex = parseInt(srcElement.getAttribute("_index"));
	},

	_onOptionClick : function(_event, _oJson){//单击某下拉项时的处理
		if(this.onOptionClick){
			return this.onOptionClick(_event, _oJson);
		}
		return Prototype.K(true);
	},

	onItemClick : function(srcElement, _event){//单击某项时把当前内容填在输入控件中，默认内容为列表向的内容
		var event = window.event || _event;
		if(this._onOptionClick(event, srcElement.getAttribute("_oJson")) != false){
			this.oRelatedElement.value = this.oPreElement.innerText;
		}
		this.oComboxRegionElement.style.display = 'none';	
		this.oComboxRegionShieldElement.style.display = 'none';
		Event.stop(event);
	},

	onItemsBlur : function(_event){	
		var event = window.event || _event;
		var activeElement = document.activeElement;
		if(this.isContainerActive()){
		}else{
			this.oComboxRegionElement.style.display = 'none';
			this.oComboxRegionShieldElement.style.display = 'none';
		}
	},
	isContainerActive : function(){
		var activeElement = document.activeElement;
		while(activeElement && activeElement.tagName.toUpperCase != 'BODY'){
			if(Element.hasClassName(activeElement.parentNode, "Combox")){
				return true;
			}
			activeElement = activeElement.parentNode;
		}
		return false;
	},
	setHeight : function(height){
		this.oComboxRegionShieldElement.style.height = height;
		this.oComboxRegionElement.style.height = height;
	},

	setWidth : function(width){
		this.oComboxRegionShieldElement.style.width = width;
		this.oComboxRegionElement.style.width = width;
	},

	setImage : function(url){
		this.oComboxImg.style.backgroundImage = "url(" + url + ")";
		this.oComboxImg.style.backgroundRepeat = 'no-repeat';
		this.oComboxImg.style.backgroundPosition = 'center center';
		this.oComboxImg.style.overflow = 'hidden';
		
		Position.clone(this.oRelatedElement,this.oComboxImg,{setWidth:false,offsetLeft:this.oRelatedElement.offsetWidth-this.oComboxImg.offsetWidth});

		this.oComboxImgShield.style.width = this.oComboxImg.offsetWidth || this.oComboxImg.style.width;
		this.oComboxImgShield.style.height = this.oComboxImg.offsetHeight || this.oComboxImg.style.height;
		this.oComboxImgShield.style.left = this.oComboxImg.style.left;
	},

	/*
	*selectValue	需要选中的值，可能是key，也可能是content,默认为key
	*isContent		true表示selectValue是content，否则表示是key
	*triggerEvent	表示是否触发click事件
	*/
	setSelectedItem : function(selectValue, isContent, triggerEvent){
		var nodes = this.oComboxRegionElement.childNodes;
		var bMatched = false;
		for (var i = 0; i < nodes.length; i++){
			var tempValue = nodes[i].getAttribute("_oJson");
			if(isContent){
				tempValue = nodes[i].innerText;		
			}
			if(selectValue == tempValue){
				bMatched = true;
				if(triggerEvent){
					nodes[i].fireEvent("onclick");
				}else{
					Element.removeClassName(nodes[i], "Combox_MouseOut");
					Element.addClassName(nodes[i], "Combox_MouseOver");			
					this.oRelatedElement.value = nodes[i].innerText;
					this.oPreElement = nodes[i];
					this.m_nCurrentItemIndex = parseInt(nodes[i].getAttribute("_index"));
				}
			}else if(Element.hasClassName(nodes[i], "Combox_MouseOver")){
				Element.removeClassName(nodes[i], "Combox_MouseOver");
				Element.addClassName(nodes[i], "Combox_MouseOut");				
			}
		}	
		if(bMatched == false) {
			this.oRelatedElement.value = selectValue;
		}
	},

	setValue : function(selectValue){
		this.setSelectedItem(selectValue, false, false);		
	},

	_resize_ : function(){
		Position.clone(this.oRelatedElement,this.oComboxImg,{setWidth:false,setHeight:false,offsetLeft:this.oRelatedElement.offsetWidth-this.oComboxImg.offsetWidth});
		this.oComboxImgShield.style.top = this.oComboxImg.style.top;
		this.oComboxImgShield.style.left = this.oComboxImg.style.left;

		Position.clone(this.oRelatedElement,this.oComboxRegionElement,{setWidth:false,setHeight:false,offsetTop:this.oRelatedElement.offsetHeight});
		this.oComboxRegionShieldElement.style.top = this.oComboxRegionElement.style.top;
		this.oComboxRegionShieldElement.style.left = this.oComboxRegionElement.style.left;		
	},
	getComboxImage : function(){
		return this.oComboxImg;	
	}
}


Event.observe(window, 'resize', function(){
	for(var prop in com.trs.combox.Combox.CACHE){
		com.trs.combox.Combox.CACHE[prop]._resize_();
	}
});

Event.observe(window, 'unload', function(){
	for(var prop in com.trs.combox.Combox.CACHE){
		delete com.trs.combox.Combox.CACHE[prop];
	}
});