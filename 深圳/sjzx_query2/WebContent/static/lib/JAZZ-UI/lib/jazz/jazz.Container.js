(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',
		         'layout/jazz.Layout'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
/**
 * @version 0.5
 * @name jazz.container
 * @description 使用矩形容器组件的基类，为子组件的提供布局服务。
 * @constructor
 * @extends jazz.boxComponent
 * @requires
 */
	$.widget('jazz.container', $.jazz.boxComponent, {
	    
		options: /** @lends jazz.container# */ {
	    	
			/**
			 *@type String
			 *@desc 容器指定的布局类型
			 *@default 'auto'
			 */
			layout: 'auto',
			
			/**
			 *@type Object
			 *@desc 容器布局的配置参数
			 *@default {}
			 */			
			layoutconfig: {}
		},
		
		/** @lends jazz.container */
		/**
		 * @desc 创建组件
		 * @private
		 */
		_create: function(){
			this._super();
		},
		
		/**
		 * @desc 初始化
		 * @private
		 */
		_init: function(){
			this._super();
		},	
		
		/**
         * @desc 动态改变属性
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         */
        _setOption: function(key, value){
			if(key == "layoutconfig" && typeof value == "object"){
    			this.options.layoutconfig = value;
            	try{
            		this.layoutobject.layout(this, this.layoutcontainer, value);
            	}catch(e){
            		jazz.error('错误的修改布局！！！');
            	}	
			}
			this._super(key, value);
        },		
	
		/**
         * @desc 设置组件的布局 当有新组件加入或组件改变大小/位置时，就需要执行此方法
         * @param {container} 需要布局的容器对象
		 * @throws
		 * @example $('#container').container('doLayout', container);
         */			
        doLayout: function(container){
        	var _layout = this.options.layout,
        	    comp = jazz.layout[ _layout], 
        	    obj , that = this;

        	function replaceLetter(str){
        		var reg = /\b(\w)|\s(\w)/g;
        		return str.replace(reg,function(m){return m.toUpperCase();});
        	}

        	this.layoutcontainer = container;
        	
        	if(typeof jazz.layout[ _layout ] == 'undefined'){
        		_layout = jazz.layout.auto;
        	}
        	        	
    		if(!(typeof(this.options.layoutconfig) == 'object')){
    			this.options.layoutconfig = jazz.stringToJson(this.options.layoutconfig);
        	}
        	
        	if(_layout == 'fit') {
        		var $this = this;
        		this.element.layout({
            		layout: 'fit',
            		layoutconfig: {
            			callback: function(){
            				$this._panelFitCallback($this);          				
            			}
            		}
        		});
        	}else{
    			if(jazz.config.isUseRequireJS === true){				
    				require(['layout/jazz.' + replaceLetter(_layout) + "Layout"], function(){
        				obj = that.layoutcontainer[ comp ]();
        				that.layoutobject = obj.data(comp);
        				that.layoutobject.layout(that, that.layoutcontainer, that.options.layoutconfig);
        			});
    			}else{    				
    				obj = this.layoutcontainer[ comp ]();
    				this.layoutobject = obj.data(comp);
    				this.layoutobject.layout(this, this.layoutcontainer, this.options.layoutconfig);
    			}
        		
        	}
        }

	});

});
