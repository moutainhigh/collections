
(function($) {

	/**
	 * @version 0.5
	 * @name jazz.boxlayout
	 * @description boxlayout是布局hboxLayout和vboxLayout基础类。通常不应该直接使用。
	 * @constructor
	 * @extends jazz.containerlayout
	 * @requires
	 * @example $('#panel_id').boxlayout();
	 */
    $.widget("jazz.boxlayout", $.jazz.containerlayout, {

        options: /** @lends jazz.boxlayout# */ {
			/**
			 *@type boolean
			 *@desc 组件间的间隔数值
			 *@default false
			 */
        	interval: 10,
        	
			/**
			 *@type number
			 *@desc 距离顶部边框的值
			 *@default 10
			 */        	
        	top: 20,
        	
			/**
			 *@type number
			 *@desc 距离边框的值
			 *@default false
			 */        	  	
        	left: 10
        },
        
        /** @lends jazz.boxlayout */        
        
		/**
         * @desc 创建组件
		 * @throws
		 * @example
         */        
        _create: function() {
        	
        }
        
        
        
        

    });
    
    
})(jQuery);