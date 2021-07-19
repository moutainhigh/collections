$package('com.trs.wcm.ui');
var SimpleTab = com.trs.wcm.ui.SimpleTab = Class.create('wcm.ui.SimpleTab');
Object.extend(SimpleTab,{
	CSS_TAB : 'simpletab',
	CSS_ACTIVE_ITEM : 'simpletab_item_active',
	CSS_ITEM : 'simpletab_item',
	TARGET : '_self',
	ID_DEFAULT : 'id_SimpleTab',
	CSS_TAB_BEFORE : 'simpletab_item_before',
	CSS_TAB_AFTER : 'simpletab_item_after'
});
com.trs.wcm.ui.SimpleTab.prototype = {
	initialize : function(_TabId , _nCurrIndex){
		this.tabId = _TabId || SimpleTab["ID_DEFAULT"];
		this.tabItems = [];
		this.nCurrIndex = (!isNaN(_nCurrIndex))?_nCurrIndex:-1;
	},
	size : function(){
		return this.tabItems.length;
	},
	addItem : function(_oItem){
		_oItem.tab = this;
		_oItem.nIndex = this.tabItems.length;
		this.tabItems.push(_oItem);
	},
	draw : function(_element){
		if(!$(_element)){
			document.write(this.getHtml());
		}
		else{
			new Insertion.Bottom($(_element),this.getHtml());
		}
	},
	select : function(_nIndex,_bForced){
		if(this.tabItems[_nIndex]&&(this.nCurrIndex!=_nIndex||_bForced)){
			this.tabItems[_nIndex].onClick();
		}
	},
	getHtml : function(){
		var sHtml = '<div id="'+this.tabId+'" class="'+SimpleTab["CSS_TAB"]+'">';
		sHtml += '<div class="'+SimpleTab["CSS_TAB_BEFORE"]+'"></div>';
		for(var i=0;i<this.tabItems.length;i++){
			sHtml += this.tabItems[i].getHtml();
		}
		sHtml += '<div class="'+SimpleTab["CSS_TAB_AFTER"]+'"></div>';
		sHtml += "</div>";
		return sHtml;
	},
	bindEvent : function(){
		for(var i=0;i<this.tabItems.length;i++){
			this.tabItems[i].bindEvent();
		}
	},
	openItem : function(_nIndex){
		var nPreIndex	= this.nCurrIndex;
		var tabItem = this.tabItems[nPreIndex];
		if(tabItem&&$(tabItem.itemId)){
			Element.removeClassName($(tabItem.itemId),SimpleTab["CSS_ACTIVE_ITEM"]);
		}
		tabItem = this.tabItems[_nIndex];
		if(tabItem&&$(tabItem.itemId)){
			Element.addClassName($(tabItem.itemId),SimpleTab["CSS_ACTIVE_ITEM"]);
		}
		this.nCurrIndex = _nIndex;
		return true;
	},
	onClickItem : function(_nIndex){
		var nIndex = _nIndex || 0;
		var tabItem = this.tabItems[nIndex];
		if(tabItem == null) return;

		if(tabItem.bDisabled)
			return;
		tabItem.fireEvent('click');
	}
}
var SimpleTabItem = com.trs.wcm.ui.SimpleTabItem = Class.create('wcm.ui.SimpleTabItem');
com.trs.wcm.ui.SimpleTabItem.prototype = {
	initialize : function(_sTitle, _sOnClick, _sHref, _bDisabled){
		this.sTitle = _sTitle;
		this.sHref = _sHref;
		this.bDisabled = _bDisabled;
		this.onClickFunc = _sOnClick+';'+(_sHref||'').replace(/javascript:/ig,'');
	},
	isFirst : function(){
		return this.nIndex == 0;
	},
	isLast : function(){
		return this.nIndex == this.tab.size()-1;
	},
	draw : function(){
		document.write(this.getHtml());
	},
	getHtml : function(){
		this.itemId = this.tab.tabId+ '_' + this.nIndex;
		var sHtml = '<div id="'+this.itemId+'" class="'+SimpleTab["CSS_ITEM"]
			+((this.nIndex==this.tab.nCurrIndex)?' '+SimpleTab["CSS_ACTIVE_ITEM"]:'')+'">'
			+'<a href="'+(this.sHref||'#')+'" target="'+SimpleTab["TARGET"]+'" '
			+((this.sHref)?'':'onclick="return false;"')+'>'+this.sTitle+'</a>'
			+'</div>';
		return sHtml;
	},
	bindEvent : function(){
		Event.observe($(this.itemId),"click",this.onClick.bindAsEventListener(this));
	},
	onClick : function(){
		if(this.bDisabled)return;
		this.tab.openItem(this.nIndex);
		if(this.onClickFunc){
			try{
				eval(this.onClickFunc);
			}catch(err){
				$alert(err.stack||err.message);
			}
		}
	}
};