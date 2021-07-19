$package('com.trs.wcm.ui');
var UIButtons = com.trs.wcm.ui.Buttons = Class.create('wcm.ui.Buttons');
Object.extend(UIButtons,{
	CSS_TAB : 'ui_buttons',
	CSS_ITEM : 'ui_button',
	CSS_ITEM_OVER : 'ui_button_over',
	CSS_ITEM_NOPADDING : 'ui_button_nopadding'
});
com.trs.wcm.ui.Buttons.prototype = {
	initialize : function(_BtnsId){
		this.ButtonsId = _BtnsId;
		this.ButtonItems = [];
	},
	size : function(){
		return this.ButtonItems.length;
	},
	addItem : function(_oItem){
		_oItem.group = this;
		_oItem.nIndex = this.ButtonItems.length;
		this.ButtonItems.push(_oItem);
	},
	draw : function(_element){
		if(!$(_element)){
			document.write(this.getHtml());
		}
		else{
			var sHtml = this.getHtml();
			new Insertion.Bottom($(_element),sHtml);
		}
	},
	getHtml : function(){
		var sHtml = '<div id="'+this.ButtonsId+'" class="'+UIButtons["CSS_TAB"]+'">';
		for(var i=0;i<this.ButtonItems.length;i++){
			sHtml += this.ButtonItems[i].getHtml();
		}
		sHtml += "</div>";
		return sHtml;
	},
	bindEvent : function(){
		for(var i=0;i<this.ButtonItems.length;i++){
			this.ButtonItems[i].bindEvent();
		}
	}
};
var UIButton = com.trs.wcm.ui.UIButton = Class.create('wcm.ui.UIButton');
com.trs.wcm.ui.UIButton.prototype = {
	initialize : function(_sLabel, _sCmdName, _sClassName, _sTitle){
		this.Label = _sLabel;
		this.CmdName = _sCmdName;
		this.ClassName = _sClassName || UIButtons["CSS_ITEM_NOPADDING"];
		this.Title = _sTitle || '';
	},
	draw : function(_element){
		if(!$(_element)){
			document.write(this.getHtml());
		}
		else{
			var sHtml = this.getHtml();
			new Insertion.Bottom($(_element),sHtml);
		}
	},
	getHtml : function(){
		this.itemId = (this.group||{ButtonsId:'uibutton'}).ButtonsId+ '_' + this.nIndex;
		var sHtml = '';
		sHtml = '<div id="'+this.itemId+'" class="'+UIButtons["CSS_ITEM"]+'">'
			+'<a href="#" class="'+this.ClassName+'" title="'+this.Title+'">'+this.Label+'</a>'
			+'</div>';
		return sHtml;
	},
	bindEvent : function(){
		var element = $(this.itemId);
		Event.observe(element,"click",this.onClick.bindAsEventListener(this));
		Event.observe(element,"mouseover",this.onMouseOver.bindAsEventListener(this));
		Event.observe(element,"mouseout",this.onMouseOut.bindAsEventListener(this));
	},
	onClick : function(){
		if(typeof(this.CmdName)=='function'){
			this.CmdName();
		}
		else if(typeof(this.CmdName)=='string'){
			eval(this.CmdName);
		}
		return false;
	},
	onMouseOver : function(){
		var element = $(this.itemId);
		Element.addClassName(element,UIButtons['CSS_ITEM_OVER']);
	},
	onMouseOut : function(){
		var element = $(this.itemId);
		Element.removeClassName(element,UIButtons['CSS_ITEM_OVER']);
	}
};