(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'layout/jazz.ContainerLayout'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.autolayout
	 * @description 自动布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 */
    $.widget("jazz.autolayout", $.jazz.containerlayout, {

    	/** @lends jazz.autolayout */        
        
		/**
         * @desc 设置布局
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutconfig} 布局需要的配置参数
         */
        layout: function(cthis, container, layoutconfig) {
        	
        }

    });
    
});
