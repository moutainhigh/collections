(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',
		         'form/jazz.form.HiddenField'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.iconfield
	 * @description 选择输入组件的基类。
	 * @constructor
	 * @extends jazz.hiddenfield
	 */
    $.widget("jazz.iconfield", $.jazz.hiddenfield, {
    	
    	options: /** @lends jazz.iconfield# */ {

        	/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default textfield
    		 */
			vtype: 'iconfield'

    	},
    	
    	/** @lends jazz.iconfield */
    	
        /**
         * @desc 创建组件
         * @private
         */ 
        _create: function() {
        	//创建组件
        	this._super();
        	this.inputtext = $('<input id="'+this.compId+'_text" class="jazz-field-comp-input" type="text" autocomplete="off">').appendTo(this.inputFrame);
        },

        /**
         * @desc 初始化组件
         * @private
         */ 
		_init: function(){
			this._super();
		},
		
		/**
		 * @desc 设置组件是否为输入框或者是文本
		 * @private
		 */			
		_readonly: function(){
			if(this.options.readonly == true){
	    		this.parent.removeClass("jazz-field-comp-in2");
				this.inputtext.css({"display": "none"});					
				this.inputview.css({"display": "block"});
				var text = this.inputtext.val();
				if(this.options.vtype == 'colorfield'){
					text = this.inputview.attr("value");
				}
				if(text == this.options.valuetip){
					this.inputview.html("&nbsp;");
				}else{
					this.inputview.html(text || "&nbsp;");	
				}				
				this.parent.children(".jazz-field-icon").css({"display": "none"});
				//把下拉图片的宽度清0，然后再生新计算组件宽度
				this.arrowwidth = 0;
				//修改内容区宽度
        		this._widthContent();
			}else{
				this.inputview.css({"display": "none"});
				this.inputtext.css({"display": "block"});					
				this.parent.addClass("jazz-field-comp-in2");
				this.parent.children(".jazz-field-icon").css({"display": "block"});
				
				this.arrowwidth = jazz.config.fieldIconWidth;
				//修改内容区宽度
        		this._widthContent();
			}		
		}
    });
    
});
