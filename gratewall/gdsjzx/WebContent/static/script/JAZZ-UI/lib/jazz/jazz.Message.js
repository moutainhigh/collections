(function( $, factory ){
	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'jazz.Window'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.message
	 * @description 一种弹出页面。
	 * @constructor
	 * @extends jazz.window
	 */
    $.widget("jazz.message", $.jazz.window, {
    		options: /** @lends jazz.message#  */ {
                /**
    			 *@type string
    			 *@desc 详细信息
    			 *@default ''
    			 */
   	   		    detail: '',    			
                
   	   		    /**
    			 *@type Number
    			 *@desc 窗口宽度
    			 *@default 340
    			 */
    			width: 340, 
                
                /**
    			 *@type Number
    			 *@desc 窗口高度
    			 *@default -1
    			 */
    			height: -1,
   		   		
   		   	    /**
    			 *@type number
    			 *@desc 显现提示信息的类型
    			 *@default 0
    			 */
   		   		showtype: 0,

   		   		/**
   				 *@type Boolean
   				 *@desc 确认按钮的点击事件
   				 *@event
   				 *@default true
   				 */
   		   		sure: null,
   	             
   	            /**
   				 *@type Boolean
   				 *@desc 取消按钮的点击事件
   				 *@event
   				 *@default true
   				 */
   		   		cancel: null   		   		
        },
          
        /** @lends jazz.message */
		
		/**
         * @desc 创建组件
		 * @private
         */
        _create: function() {
            this._super();
            this.element.addClass("jazz-message");
        },
        
        /** @lends jazz.message */
        /**
         * @desc 初始化组件
		 * @private
         */
        _init: function() {
        	this.options.modal = true;   //模态窗口
        	this.options.visible = true; //可见 
        	
        	this._super();

        	this.content.empty();
        	
        	var div = '<div class="jazz-message-content"><div class="jazz-message-img"></div><div class="jazz-message-text">&nbsp;</div><div class="jazz-message-button"></div></div>';
            this.content.append(div);
            
            jazz.config.errorMessageNumber++;
            
   		    var img = this.element.find(".jazz-message-img");
   		    var btn = this.element.find(".jazz-message-button");
   		    
   		    var $this = this;
   		    var qd = $('<div class="jazz-message-button-style jazz-message-img-queding"></div>').appendTo(btn);
   		    qd.off("click.message").on("click.message", function(){
			      $this.close(true);
			      jazz.config.errorMessageNumber=0;
				  var a = $this.options.sure;
				  if ($.isFunction(a)) {
				     a.call($this);
				  } 		    	
   		    });
   		    
		    var type = this.options.showtype;
   		    if(type==0){
		    	img.addClass("jazz-message-img-info");		    	
		    }else if(type==1){
		    	img.addClass("jazz-message-img-error");
		    }else if(type==2){
		    	img.addClass("jazz-message-img-warn");
		    }else if(type==3){
		    	img.addClass("jazz-message-img-confirm");
		    	var qx = $('<div class="jazz-message-button-style jazz-message-img-cancel"></div>').appendTo(btn);
		    	qx.off("click.message").on("click.message", function(){
				      $this.close(true);
				      jazz.config.errorMessageNumber=0;
					  var a = $this.options.cancel;
					  if ($.isFunction(a)) {
					     a.call($this);
					  }		    		
		    	});
		    }
		 
		    if(this.options.detail){
		       var text = this.element.find(".jazz-message-text");
	  	       text.html(this.options.detail); 
		    }
		    
        }
    });
});