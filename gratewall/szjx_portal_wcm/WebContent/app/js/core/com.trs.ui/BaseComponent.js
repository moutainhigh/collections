Ext.ns('com.trs.ui');

com.trs.ui.BaseComponent = function(config){
	this.init(config);
}

Ext.extend(com.trs.ui.BaseComponent, com.trs.util.Observable, {
	disabledCls : 'XDisabledCls',
	init : function(config){
		this.initConfig = config || {};
		this.addEvents('render');
		com.trs.ui.ComponentMgr.register(this);
	},
	getProperty : function(sPropertyName){
		return this.initConfig[sPropertyName];
	},
	getId : function(){
		return this.initConfig['name'];
	},
	initActions : function(){
	},
	getHtml : function(){
		return "";
	},
	render : function(){
		var sHtml = this.getHtml();
		var config = this.initConfig;
		var dom;
		if(config['renderTo']){
			Element.update(config['renderTo'], sHtml);
		}else{
			document.write(sHtml);
		}
		this.fireEvent('render', this);
		this.afterRender();
		this.initActions();
	},	
	getBox : function(){
		var id = this.getId();
		if(id && $(id)){
			return $(id).parentNode;
		}
		return null;
	},
	afterRender : function(){	
		var box = this.getBox();
		if(box && this.initConfig['disabled']){
			Element.addClassName(box, this.disabledCls);
		}
		if(this.initConfig['cls']){
			Element.addClassName(box, this.initConfig['cls']);
		}
	},
	getValue : function(){
		var config = this.initConfig;
		return $(config['name']).value;
	},
	getData : function(){
		var data = {};
		data[this.initConfig['name']] = this.getValue();
	}
});

com.trs.ui.ComponentMgr = function(){
	var all = {};
	return {
		register : function(c){
			all[c.getId()] = c;
		},
		unregister : function(c){
			delete all[c.getId()];
		},
		getAllComponents : function(){
			var components = [];
			for (var sId in all){
				components.push(all[sId]);
			}
			return components;	
		},
		get : function(id){
			if(!id) return null;
			return all[id.id || id];
		}
	};
}();

var XConstants = {
	BASE_PATH : '/wcm/app/js/core/'
};