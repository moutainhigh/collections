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
		var config = this.initConfig;

		//延迟加载
		if(!config['_lazyRendered_'] && config['lazyRender']){
			config['_lazyRendered_'] = true;
			if(!config['renderTo']){//如果没有renderTo，需要输出一个占位符			
				config['renderTo'] = config['name'] + '-' + new Date().getTime();
				var template = '<div class="lazyRenderBox" id="{0}"><input type="hidden" name="{1}" id="{1}" value="" /></div>';
				document.write(String.format(template, config['renderTo'], config['name']));
				$(config['name']).value = config['value'];							
			}
			return;
		}

		var sHtml = this.getHtml();
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
	setValue : function(info){
		if(typeof info == 'string'){
			var config = this.initConfig;
			$(config['name']).value = info;
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
	var lazyCache = {};
	var all = {};
	return {
		register : function(c){
			all[c.getId()] = c;
			if(c.getProperty('lazyRender')){
				lazyCache[c.getId()] = c;
			}
		},
		unregister : function(c){
			if(c.getProperty('lazyRender')){
				delete lazyCache[c.getId()];
			}
			delete all[c.getId()];
		},
		getAllComponents : function(){
			var components = [];
			for (var sId in all){
				components.push(all[sId]);
			}
			return components;	
		},
		getAllLazyRenderComponents : function(){
			var components = [];
			for (var sId in lazyCache){
				components.push(lazyCache[sId]);
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
	BASE_PATH : '/wcm/app/application/core/'
};