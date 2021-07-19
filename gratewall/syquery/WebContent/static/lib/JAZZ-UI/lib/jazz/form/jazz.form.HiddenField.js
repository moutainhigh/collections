(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',
		         'form/jazz.form.Field'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.hiddenfield
	 * @description 隐藏域字段组件。
	 * @constructor
	 * @extends jazz.field
	 */
    $.widget("jazz.hiddenfield", $.jazz.field, {
        
    	options: /** @lends jazz.hiddenfield# */ {
    		
    		/**
			 *@type String
			 *@desc 组件类型
			 *@default hiddenfield
			 */
			vtype: 'hiddenfield',
			
			/**
			 *@type Number
			 *@desc 数字类型
			 *@default 延迟请求间隔时间
			 */
			delay: 300,
			
			/**
			 *@type Number
			 *@desc 数字类型
			 *@default 延迟间隔时间
			 */
			delaycount: 30000
				
    	},
    	
    	/** @lends jazz.hiddenfield */
    	
        /**
         * @desc 创建组件
         * @private
         */  
        _create: function() {
        	this._super();
			this.inputtext = this.input = $('<input type="hidden" id="'+this.compId+'_input" name="'+this.options.name+'" >').appendTo(this.inputFrame);
        },
    	
    	_init: function(){
    		this._super();
    	},
    	
        /**
         * @desc ajax请求函数
         * @param {cacheflag} boolean  true 请求数据,不取缓存数据   false 取缓存数据   default 默认通过缓存通道  
         * @return 返回请求响应的数据
         * @private
         */
        _ajax: function(cacheflag, callbackfunction){
        	var $this = this;
        	var i = 1, delay = $this.options.delay, delaycount = $this.options.delaycount;
        	try{
				var timeout = setInterval(function(){
					var status = false;
	            	data = G.getPageDataSetCache($this.options.dataurl, $this.options.dataurlparams, cacheflag);
	                
	                if(data && data.status === 'success'){
	                	clearInterval(timeout);
	                	var d = data["data"];
	                	// jazz.config.platForm 为other时是Optimus框架返回数据格式，为sword时是Sword框架返回数据格式
	                	if(jazz.config.platForm=='other'){
	                	    $.each(d,function(i,sondata){
	                	    	d = sondata["data"];
	                        });
	                	}
                        //执行回调函数
                        $this._callback(d);
                        //执行用户回调函数
	                	if($.isFunction(callbackfunction)){
	                		callbackfunction.call(this, d);
	                	}
	                    status = true;
	                }
	                i++;
	                
					if(status || delay*i > delaycount){
						clearInterval(timeout);
					}
				}, delay);
        	}catch(e){
        	}
        }
    	
    });
});
