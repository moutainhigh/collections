(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'layout/jazz.ContainerLayout'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.fitlayout
	 * @description 自适应高度布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 */	
    $.widget("jazz.fitlayout", $.jazz.containerlayout, {

    	/** @lends jazz.fitlayout */  
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutconfig} layoutconfig配置信息
         */
        layout: function(cthis, container, layoutconfig) {
        	jazz.listener.fitlistener.add(cthis.element, layoutconfig);
        	jazz.listener.fitlistener.start();
        },
        
		/**
         * @desc 刷新布局
         * @param {componentObject} 当前组件的this对象
         * @param {layoutcontainer} 需要渲染的容器对象
         * @param {layoutconfig} 布局需要的配置参数
         */        
        reflashLayout: function(componentObject, layoutcontainer, layoutconfig){}      
        
    });
});