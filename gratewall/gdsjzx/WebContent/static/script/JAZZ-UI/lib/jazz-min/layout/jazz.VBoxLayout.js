
(function($) {
	/**
	 * @version 0.5
	 * @name jazz.vboxlayout
	 * @description 垂直排列的布局。
	 * @constructor
	 * @extends jazz.boxlayout
	 * @requires
	 * @example $('#panel_id').vboxlayout();
	 */	
    $.widget("jazz.vboxlayout", $.jazz.boxlayout, {

    	/** @lends jazz.vboxlayout */        
        
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 当前组件对象
		 * @throws
		 * @example
         */
        layout: function(cthis, container, config) {
        	container.addClass("jazz-layout");
        	//container.css({border: '1px solid red'});
        	var items = container.children();   //获取全部的子对象
        	var bc = {   //记录前一组件的值
    			top: 0,
    			left: 0,
    			height: 0
            };
        	
        	//对齐位置
        	this._align(cthis, items, container, config, bc);
        	
        },

		/**
         * @desc 设置布局
         * @param {cthis} 当前对象
         * @param {items} 需要布局的组件集合
         * @param {container} 当前布局容器对象
         * @param {config} 组件布局配置对象
         * @param {bc} 布局时记录上一个组件的对象
		 * @private
		 * @example  this._align(cthis, items, container, config, bc);
         */        
        _align: function(cthis, items, container, config, bc){

    		//计算左右偏移量
        	for(var i = 0, len = items.length; i<len; i++){
        		var top = 0;
        		if(i==0){
        			top = bc.top = 0;
        		}else{
        			top = bc.top =  bc.height + bc.top;
        		}
        		bc.height = items.eq(i).outerHeight(true);

        		items.eq(i).css({position: 'absolute', top: top, left: 0 });
        	}
        	//重新计算容器高度
        	if(cthis.options.height === -1){
        		container.height(bc.height + bc.top );
        	}
        	
        }
        
    });
    
})(jQuery);