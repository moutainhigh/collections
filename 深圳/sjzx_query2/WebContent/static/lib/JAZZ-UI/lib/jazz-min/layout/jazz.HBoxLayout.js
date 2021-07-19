
(function($) {
	
	/**
	 * @version 0.5
	 * @name jazz.hboxlayout
	 * @description 水平布局。
	 * @constructor
	 * @extends jazz.boxlayout
	 * @requires
	 * @example $('#panel_id').hboxlayout();
	 */	
    $.widget("jazz.hboxlayout", $.jazz.boxlayout, {

    	
        /** @lends jazz.hboxlayout */       

		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 当前组件对象
         */
        layout: function(cthis, container, config) {
        	cthis.content.addClass("jazz-layout");
        	//container.css({border: '1px solid red'});
        	var items = container.children();   //获取全部的子对象
        	var bc = {   //记录前一组件的值
    			top: 0,
    			left: 0,
    			width: 0
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
         */
        _align: function(cthis, items, container, config, bc){

        	for(var i = 0, len = items.length; i<len; i++){
        		
            		var left = 0;
            		if(i==0){
            			left = bc.left = 0;   //0;
            		}else{
            			left = bc.left =  bc.width + bc.left;
            		}
            		bc.width = items.eq(i).outerWidth(true);

            		items.eq(i).css({position: 'absolute', top:0, left: left });
            	
        		
        	}
        	
        	//重新计算容器高度
        	if(cthis.options.height === -1){ //外部未指定容器高度，自适应
        		container.height(allheight + parseInt(config.margin.top) + parseInt(config.margin.bottom));
        	}
        	
        }    
  
    });
    
})(jQuery);