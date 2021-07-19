(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.layout
	 * @description 布局参数定义。
	 * @constructor
	 * @example jazz.layout.auto;
	 */             	
	jazz.layout = {
		auto: 'autolayout',
		border: 'borderlayout',
		column: 'columnlayout',
		fit: 'fitlayout',
		row: 'rowlayout',
		vbox: 'vboxlayout',
		hbox: 'hboxlayout',
		table: 'tablelayout',
		query: 'querylayout',
		card: 'cardlayout',
		anchor: 'anchorlayout'
	};

	/**
	 * @version 1.0
	 * @name jazz.containerlayout
	 * @description 每个Container都委托布局管理器来渲染其子组件Component,通常不应该直接使用。
	 * @constructor
	 * @extends jazz.container
	 */	
    $.widget("jazz.containerlayout", {

        options: /** @lends jazz.containerlayout# */ {
        	
    		/**
			 *@type Object
			 *@desc 存储用来调用布局的容器对象 
			 *@default null
			 */        		
        	container: null,
        	
			/**
			 *@type Object
			 *@desc 布局的配置参数
			 *@default {}
			 */
			layoutconfig: {}

        },

        /** @lends jazz.containerlayout */
		/**
         * @desc 刷新布局
         * @param {componentObject} 当前组件的this对象
         * @param {layoutcontainer} 需要渲染的容器对象
         * @param {layoutconfig} 布局需要的配置参数
         */        
        reflashLayout: function(componentObject, layoutcontainer, layoutconfig){
        	this.layout(componentObject, layoutcontainer, layoutconfig);
        },
        
        /**
         * @desc 刷新布局前要做的操作
         */        
        reflashLayoutBefore: function(){},
        
        /**
         * @desc 刷新布局后要做的操作
         */           
        reflashLayoutAfter: function(){}
    });    
});
