$package('com.trs.wcm.ui');
var ButtonGroup = com.trs.wcm.ui.ButtonGroup = Class.create('wcm.ui.ButtonGroup');
Object.extend(ButtonGroup,{
	CSS_TAB : 'buttongroup',
	CSS_ITEM_CONTAINER : 'buttongroup_item_container',
	CSS_ITEM : 'buttongroup_item',
	CSS_ITEM_DEFAULT : 'buttongroup_default_item',
	CSS_ITEM_BTN_MORE_DOWN : 'buttongroup_item_btnmore_down',
	CSS_ITEM_BTN_MORE_UP : 'buttongroup_item_btnmore_up',
	CSS_ITEM_OVER : 'buttongroup_item_over',
	CSS_ITEM_DEFAULT_OVER : 'buttongroup_default_item_over',
	CSS_ITEM_BTN_MORE_OVER : 'buttongroup_item_btnmore_over',
	ID_BTN_MORE_PRE : 'buttongroup_more_',
	ID_CONTAINER_PRE : 'buttongroup_container_'
});
com.trs.wcm.ui.ButtonGroup.prototype = {
	initialize : function(_GroupId,_bDirection){
		this.GroupId = _GroupId;
		this.Direction = (_bDirection!=false);
		this.GroupContainId = ButtonGroup['ID_CONTAINER_PRE']+this.GroupId;
		this.GroupMoreId = ButtonGroup['ID_BTN_MORE_PRE']+this.GroupId;
		this.ButtonGroupItems = [];
	},
	size : function(){
		return this.ButtonGroupItems.length;
	},
	addItem : function(_oItem){
		_oItem.group = this;
		_oItem.nIndex = this.ButtonGroupItems.length;
		this.ButtonGroupItems.push(_oItem);
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
		var sHtml = '<div id="'+this.GroupId+'" class="'+ButtonGroup["CSS_TAB"]+'">';
		sHtml += this.ButtonGroupItems[0].getHtml();
		sHtml += '<div id="'+this.GroupMoreId+'" class="'+ButtonGroup[(this.Direction)?"CSS_ITEM_BTN_MORE_DOWN":"CSS_ITEM_BTN_MORE_UP"]+'"></div></div>';
		sHtml += '<div id="'+this.GroupContainId+'" class="'+ButtonGroup["CSS_ITEM_CONTAINER"]+'" style="display:none">';
		for(var i=1;i<this.ButtonGroupItems.length;i++){
			sHtml += this.ButtonGroupItems[i].getHtml();
		}
		sHtml += "</div>";
		return sHtml;
	},
	bindEvent : function(){
		var btnFirst = $(this.FirstItemId);
		var btnMore = $(this.GroupMoreId);
		var eRuler = $(this.ButtonGroupItems[0].itemId);
		var eItemContainer = $(this.GroupContainId);
		var offsetHeight = (this.Direction)?eRuler.offsetHeight:-Element.getDimensions(eItemContainer)["height"];
		var bubbleMore = this.bubbleMore = new com.trs.wcm.BubblePanel(eItemContainer);
		var caller = this;
		Event.observe(btnMore,'click',function(){
			var eRuler = $(caller.ButtonGroupItems[0].itemId);
			Position.clone(eRuler,eItemContainer,{setWidth:false,setHeight:false,offsetTop:offsetHeight});
			caller.bubbleMore.bubble();
		});
		Event.observe(btnMore,'mouseover',function(){
			var eRuler = $(caller.ButtonGroupItems[0].itemId);
			var btnMore = $(caller.GroupMoreId);
			Element.addClassName(eRuler,ButtonGroup['CSS_ITEM_DEFAULT_OVER']);
			Element.addClassName(btnMore,ButtonGroup['CSS_ITEM_BTN_MORE_OVER']);
		});
		Event.observe(btnMore,'mouseout',function(){
			var eRuler = $(caller.ButtonGroupItems[0].itemId);
			var btnMore = $(caller.GroupMoreId);
			Element.removeClassName(eRuler,ButtonGroup['CSS_ITEM_DEFAULT_OVER']);
			Element.removeClassName(btnMore,ButtonGroup['CSS_ITEM_BTN_MORE_OVER']);
		});
		Event.observe(btnFirst,'mouseover',function(){
			var eRuler = $(caller.ButtonGroupItems[0].itemId);
			var btnMore = $(caller.GroupMoreId);
			Element.addClassName(eRuler,ButtonGroup['CSS_ITEM_DEFAULT_OVER']);
			Element.addClassName(btnMore,ButtonGroup['CSS_ITEM_BTN_MORE_OVER']);
		});
		Event.observe(btnFirst,'mouseout',function(){
			var eRuler = $(caller.ButtonGroupItems[0].itemId);
			var btnMore = $(caller.GroupMoreId);
			Element.removeClassName(eRuler,ButtonGroup['CSS_ITEM_DEFAULT_OVER']);
			Element.removeClassName(btnMore,ButtonGroup['CSS_ITEM_BTN_MORE_OVER']);
		});
		//*/
		for(var i=0;i<this.ButtonGroupItems.length;i++){
			this.ButtonGroupItems[i].bindEvent();
		}
	},
	destroy : function(){
		if(this.bubbleMore){
			this.bubbleMore.destroy();
			this.bubbleMore = null;
		}
		Event.stopAllObserving(this.GroupContainId);
		Event.stopAllObserving(this.FirstItemId);
		Event.stopAllObserving(this.GroupMoreId);
		for(var i=0;i<this.ButtonGroupItems.length;i++){
			this.ButtonGroupItems[i].group = null;
			this.ButtonGroupItems[i].destroy();
			this.ButtonGroupItems[i] = null;
		}
		this.ButtonGroupItems = null;
	}
};
var ButtonGroupItem = com.trs.wcm.ui.ButtonGroupItem = Class.create('wcm.ui.ButtonGroupItem');
com.trs.wcm.ui.ButtonGroupItem.prototype = {
	initialize : function(_sLabel, _sCmdName ,_sClassName,_sTitle){
		this.Label = _sLabel;
		this.CmdName = _sCmdName;
		this.ClassName = _sClassName || '';
		this.Title = _sTitle || '';
	},
	draw : function(){
		document.write(this.getHtml());
	},
	getHtml : function(){
		this.itemId = this.group.GroupId+ '_' + this.nIndex;
		var sHtml = '';
		if(this.nIndex==0){
			this.group.FirstItemId = this.itemId;
			sHtml = '<div id="'+this.itemId+'" class="'+ButtonGroup["CSS_ITEM_DEFAULT"]+'">'
				+'<a href="#" class="'+this.ClassName+'" title="'+this.Title+'">'+this.Label+'</a>'
				+'</div>';
		}
		else{
			sHtml = '<div id="'+this.itemId+'" class="'+ButtonGroup["CSS_ITEM"]+'">'
				+'<a href="#" class="'+this.ClassName+'" title="'+this.Title+'">'+this.Label+'</a>'
				+'</div>';
		}
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
		Element.addClassName(element,ButtonGroup['CSS_ITEM_OVER']);
	},
	onMouseOut : function(){
		var element = $(this.itemId);
		Element.removeClassName(element,ButtonGroup['CSS_ITEM_OVER']);
	},
	destroy : function(){
		Event.stopAllObserving(this.itemId);
		this.group = null;
		this.CmdName = null;
	}
};