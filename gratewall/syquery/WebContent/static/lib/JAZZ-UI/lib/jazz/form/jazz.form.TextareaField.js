(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'form/jazz.form.TextField'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/** 
	 * @version 1.0
	 * @class jazz.textareafield
	 * @description 文本输入类组件。
	 * @constructor
	 * @extends jazz.textfield
	 */
    $.widget("jazz.textareafield", $.jazz.textfield, {
       
        options: /** @lends jazz.textareafield# */ {
        	
			/**
			 *@type String
			 *@desc 组件的效验类型
			 *@default 'textareafield'
			 */        	
        	vtype: 'textareafield',
        	
        	/**
    		 *@type String
    		 *@desc 前后缀的位置 0 前缀和后缀左右排列  1 前缀和后缀上下排列
    		 *@default 0
    		 */			
			prefixposition: 0       
            	
    		// callbacks
        },

		/** @lends jazz.textareafield */
		
        /**
         * @desc 创建组件
         * @private
         */
        _create: function() {
        	this._super();
        },
        
        /**
         * @desc 创建结构组件
         * @private
         */ 
        _createElements: function(){
        	this.inputtext = this.input = $('<textarea id="'+this.compId+'_input" class="jazz-textarea-resize jazz-field-comp-input" type="text" name="'+this.options.name+'" autocomplete="off" ></textarea>').appendTo(this.inputFrame);
        	
           	//当有前后缀时，控制显示位置
        	var prefixposition = jazz.config.prefixPosition;
        	if(prefixposition){
        		this.options.prefixposition = prefixposition;
        	}        	
        },
        
        /**
         * @desc 初始化组件
         * @private
         */        
        _init: function(){
        	this._super();
        },
        
		/**
		 * @desc 设置组件内部高度, 动态改变prefixposition属性时，在基类中调用
		 * @private
		 */
		_heightContent: function(){
        	var bw = this.parent.css("border-bottom-width");
        	if(bw){
        		bw = bw.substring(0, 1);
        	}else{
        		bw = 0;
        	}
    		var innerheight = this.options.calculateinnerheight;
    		
    		//单独处理textarea组件的前缀、后缀，如果prefixposition==1则前后缀的结构为上下结构
			var preH = 0, sufH = 0;
			if(this.options.prefixposition == "1"){
    			if(this.options.prefix){
    				preH = this.prefix.outerHeight();
    				this.inputFrame.css("padding-top", preH);
    			}
    			if(this.options.suffix){
    				sufH = this.suffix.outerHeight();
    				this.inputFrame.css("padding-bottom", sufH);
    				this.suffix.removeClass("jazz-field-comp-suffix").addClass("jazz-field-comp-suffix2");
    			}
    			var _h = innerheight - parseInt(bw)*2 - preH - sufH;
    			this.inputtext.outerHeight(_h);
		    }else{
		    	this.inputFrame.css({"padding-top": 0, "padding-bottom": 0});
		    	this.suffix.removeClass("jazz-field-comp-suffix2").addClass("jazz-field-comp-suffix");
		    	this.inputtext.outerHeight(innerheight - parseInt(bw)*2);
		    }
		},        
        
		/**
         * @desc 动态改变属性
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         */	
		_setOption: function(key, value){
			switch(key){
	    		case 'prefixposition':
	    			this.options.prefixposition = value;
	    			this._widthContent();
	    			this._heightContent();
	    		break;				
			}
			this._super(key, value);
		},
		
		/**
		 * @desc 重置组件内容区宽度大小
		 * @private
		 */
		_widthContent: function(){
			var prefixWidth = 0, suffixWidth = 0,  //前缀宽度, 后缀宽度
				//判断textarea前缀、后缀的显示位置
				prefixposition = this.options.prefixposition;
			
			this.parentwidth = this.parent.width();
			if(this.options.prefix){
				if(prefixposition == 1){					
					prefixWidth = this.parentwidth;
				}else{
					prefixWidth = this.prefix.outerWidth();
				}
			}
			if(this.options.suffix){
				if(prefixposition == 1){				
					suffixWidth = this.parentwidth;
				}else{
					suffixWidth = this.suffix.outerWidth();
				}
			}
					
			if(prefixposition == 1){
				this.inputFrame.css({"padding-left": 0, "padding-right": 0});
				this.inputFrame.outerWidth(this.parentwidth);
			}else{
				this.inputFrame.css({"padding-left": prefixWidth, "padding-right": suffixWidth});
				this.inputFrame.outerWidth(this.parentwidth);
			}
    		
    		this.framewidth = this.inputFrame.width();
   			this.inputtext.outerWidth(this.framewidth);
		}		
		
    });
    
});
