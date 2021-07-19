Ext.ns('wcm.TabPanel');
/**
 * @class wcm.TabPanel
*/
wcm.TabPanel = Ext.extend(wcm.Container, {
	'item-key' : 'item',
	removeNode : false,

	initComponent : function(){
		wcm.TabPanel.superclass.initComponent.apply(this, arguments);
		this.addEvents('beforetabchange', 'tabchange');
	},

	onRender : function(){
		if(this.activeTab){
			var activeTab= this.activeTab;
			delete this.activeTab;
			this.setActiveTab(activeTab);
		}
		this.initEvents();
	},

	getHeader : function(){
		if(this.header) return $(this.header);
		var dom = $(this.getId());
		var header = Element.first(dom);
		while(header){
			if(Element.hasClassName(header, 'head-box')){
				return header;
			}
			header = Element.next(header);
		}
		return null;
	},

    // private
    initEvents : function(){
		var header = this.getHeader();
		Event.observe(header, 'click', this.onStripClick.bind(this), false);
		var aLink = header.getElementsByTagName("a");
		for (var i = 0; i < aLink.length; i++){
			aLink[i].onfocus = function(){
				this.blur();
			};
		}
	},	

	onStripClick : function(event){
		Event.stop(event);
		var dom = Event.element(event);
		var target = this.findTarget(dom);
		if(!target) return;
		this.setActiveTab(target.getAttribute(this["item-key"]));
	},

	findTarget : function(dom){
		var id = this.getId();
		while(dom && dom.id != id){
			if(Element.hasClassName(dom, 'tab-head')){
				return dom;
			}
			dom = dom.parentNode;
		}
		return null;
	},

	findItem : function(itemKey){
		var header = this.getHeader();
		var doms = header.getElementsByTagName("*");
		for(var index = 0; index < doms.length; index++){
			if(doms[index].getAttribute(this["item-key"]) == itemKey){
				return doms[index];
			}
		}
		return null;
	},

	setActiveTab : function(sActiveTabKey){
		if(!sActiveTabKey || sActiveTabKey == this.activeTab) return;
		if(this.fireEvent('beforetabchange', this.activeTab, sActiveTabKey) === false) return;	
		var oldTab = this.activeTab;
		this.activeTab = sActiveTabKey;
        if(!this.rendered){
            return;
        }
		if(oldTab){
			var oldDom = this.findItem(oldTab);
			if(oldDom){
				Element.removeClassName(oldDom, 'active');
			}
			if($(oldTab)){
				Element.removeClassName(oldTab, 'active');
			}
		}		
		var newDom = this.findItem(sActiveTabKey);
		if(newDom){
			Element.addClassName(newDom, 'active');
		}
		if($(sActiveTabKey)){
			Element.addClassName(sActiveTabKey, 'active');
		}
		this.fireEvent('tabchange', sActiveTabKey)
	},
	beforeDestroy : function(){
		var header = this.getHeader();
		var aLink = header.getElementsByTagName("a");
		for (var i = 0; i < aLink.length; i++){
			aLink[i].onfocus = null;
		}
		wcm.TabPanel.superclass.beforeDestroy.apply(this, arguments);		
	}
});