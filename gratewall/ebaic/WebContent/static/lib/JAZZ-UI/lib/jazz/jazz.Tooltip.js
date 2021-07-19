(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'jazz.BoxComponent'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
/** 
 * @version 1.0
 * @name jazz.tooltip
 * @description 提示信息组件。
 * @constructor
 * @extends jazz.BoxContainer
 * @requires
 */	
    $.widget("jazz.tooltip", $.jazz.boxComponent, {
       
        options:  /** @lends jazz.tooltip# */{ 

        	/**
        	 *@type String
        	 *@desc 提示内容
        	 *@default ''
        	 */            
        	content: '',
        	
        	/**
        	 *@type String
        	 *@desc 隐藏提示内容要由哪种事件触发
        	 *@default 'mouseout'
        	 */             
        	hideevent: 'mouseout',
			
        	/**
			 *@type String
			 *@desc 提示信息前边所要添加的图片的样式类, 默认无图片
			 *@default null
			 */        	
        	iconclass: null, 
        	
        	/**
			 *@type Boolean
			 *@desc 内部绑定事件
			 *@default true
			 */        	        	
        	isbindevent: true,

        	/**
			 *@type Object
			 *@desc 显示位置
			 *@default null
			 *@example
			 *{
             *   my: 定义被定位元素上对准目标元素的位置, 例： "left, top"
             *   at: 目录元素, "right top",
             *   collision: 当被定位元素在某些方向上溢出窗口，则移动它到另一个位置   例如： 'flipfit none',
             *   of: 要定位的元素， 例如： this.element 或  #id  或  .class,
             *   using: function(pos) { } 描述：当指定了该选项，实际属性设置则委托给该回调
             *   within（默认值：window）类型：Selector 或 Element 或 jQuery 描述：元素定位为 within，会影响 collision 检测。如果您提供了一个选择器（Selector）或 jQuery 对象，则使用第一个匹配的元素。 
             *}
			 */
        	position: null,
        	
        	/**
			 *@type String
			 *@desc 显示提示内容要由哪种事件触发
			 *@default 'mouseover'
			 */        	
            showevent: 'mouseover'
        },
        
    	/** @lends jazz.tooltip */
  
		/**
         *@desc 创建组件
         */	        
        _create: function() {
            this.container = $('<div class="jazz-tooltip" />').appendTo(document.body);

            var styleClass = "", _iconclass = this.options.iconclass;
            if(_iconclass){
            	//styleClass = '<span class="jazz-tooltip-img" style="background: url('+this.options.icon+') no-repeat"></span>';
            	styleClass = '<span class="jazz-tooltip-img '+_iconclass+'"></span>';
            }else{
            	this.arrow = $('<div class="jazz-tooltip-arrow"></div>').appendTo(this.container);
            	this.container.css({"padding-left": "10px"});
            }
            
            var content = '<div class="jazz-tooltip-div"> ' + styleClass + '<span class="jazz-tooltip-label"></span></div>';
            this.container.append(content);
            
            this.contentobj = this.container.children(".jazz-tooltip-div");
        },      

        /**
         *@desc 初始化组件
         *@private
         */
        _init: function(){
        	 if(this.options.isbindevent){
        		 this._bindEvent();
        	 }
        	 if(this.options.width != -1){
        		 this.contentobj.outerWidth(this.options.width);
        	 }else{
        		 this.contentobj.outerWidth(200);
        	 }
        	 if(this.options.content){
        		 var obj = this.contentobj.children(".jazz-tooltip-label");
        		 obj.html(this.options.content);
        	 }
        },
        
        _setOption: function(key, value){
        	switch(key){
    			case 'content':
    				this.options.content = value;
           		 	var obj = this.contentobj.children(".jazz-tooltip-label");
           		 		obj.html(value);    				
    				break; 
    			case 'position':
    				this._align();
    				break;
        	}
        	this._super(key, value);
        },        
        
		/**
         * @desc 显示位置
		 * @private
         */	         
        _align: function() {
        	var $this = this;
        	var pos = {
                my: 'left top',
                at: 'right top',
                collision: 'flipfit none',
                of: $this.element,
                using: function(pos) {
                	var lp = 0;
                	if(!$this.options.iconclass){
	                	//根据tooltip高度, 确定tooltip的显示位置
	                	var tipHeight = $this.container.outerHeight();
	                	var eleHeight = $this.element.height();
	                	if(eleHeight > 50){
		                	if(tipHeight <= 50){  
		                		lp=0; 
		                		$this.arrow.css({"top": "7px"});
		                	}else {
		                		if(eleHeight - tipHeight > 20) {
			                		lp = 20; 
			                		$this.arrow.css({"top": "35px"});
		                		}else{
			                		lp = 0; 
			                		$this.arrow.css({"top": "25px"});		                			
		                		}
		                	}	                		
	                	}else{
		                	if(tipHeight <= 50){  
		                		lp=0; 
		                		$this.arrow.css({"top": "7px"});
		                	}else {
		                		lp = 18; 
		                		$this.arrow.css({"top": "25px"});
		                	}
	                	}
                	}
                	
                	var n = 0;
                	if($this.options.position == 1){
                		n = $this.element.height();
                	}
                    var l = pos.left < 0 ? 0 : pos.left + 5,
                    t = pos.top < 0 ? 0 : pos.top - lp + n;

                    $(this).css({
                        left: l,
                        top: t
                    });
                }
            };
        	
            this.container.css({
                left:'', 
                top:'',
                'z-index': ++jazz.zindex
            }).position(pos);
        },        
        
		/**
         * @desc 目标范围内
		 * @private
         */	        
        _bindEvent: function() {
            var $this = this;
            var hideevent = this.options.hideevent, showevent = this.options.showevent;
            
            if(hideevent == "mouseout"){
            	//处理gridpanel卡片调用
            	this.globalSelector = null;
            }else{
            	this.globalSelector = 'a,:input,:button,img';
            }
            
            //处理多事件绑定
            var _sevent = "", _hevent = "";
            if(hideevent){
            	var _h = hideevent.split(";");
            	for(var i=0, len=_h.length; i<len; i++){
                	if(_h[i]){
                		_hevent = _h[i]+".tooltip " + _hevent;
                	}
                }
            }
            if(showevent){
            	var _s = showevent.split(";");
            	for(var i=0, len=_s.length; i<len; i++){
                	if(_s[i]){
                		_sevent = _s[i]+".tooltip " + _sevent;
                	}
                }            	
            }

            this.element.off(_sevent + " " + _hevent)
				             .on(_sevent, this.globalSelector, function() {
				                $this.show();
				             }).on(_hevent, this.globalSelector, function() {
				                $this.hide();
				             });
            this.element.removeAttr('title');
        },
        
        /**
         * @desc 隐藏提示信息
         * @example $("XXX").tooltip("hide");
         */          
        hide: function() {
        	this.container.hide();
        	this.container.css('z-index', '');
        },

        /**
         * @desc 显示提示信息
		 * @example $("XXX").tooltip("show");
         */           
        show: function() {
            this._align();
            this.container.show();
        }

    });
});