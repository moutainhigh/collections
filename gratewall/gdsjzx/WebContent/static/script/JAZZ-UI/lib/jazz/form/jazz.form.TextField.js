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
	 * @name jazz.textfield
	 * @description 文本输入类组件。
	 * @constructor
	 * @extends jazz.field
	 */
    $.widget("jazz.textfield", $.jazz.field, {
    	
    	options: /** @lends jazz.textfield# */ {

        	/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default textfield
    		 */
			vtype: 'textfield',
			
			/**
			 *@type Number
			 *@desc 最大输入长度
			 *@default null
			 */              
            maxlength: null,			

    		// callbacks
    		/**
			 *@desc 鼠标焦点离开输入框时触发
			 *@param {event} 事件
			 *@param {ui.newValue} 新修改的值 
			 *@param {ui.oldValue} 旧值
			 *@event
			 *@example
			 *<br/>$("XXX").textfield("option", "change", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("textfieldchange",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… change="XXX()"></div> 或 <div…… change="XXX"></div>
			 */
    		change: null,
    		
    		/**
			 *@desc 鼠标焦点进入输入框时触发
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").textfield("option", "enter", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("textfieldenter",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… enter="XXX()"></div> 或 <div…… enter="XXX"></div>
			 */			
			enter: null,
    		
    		/**
			 *@desc 点击键盘按键结束时触发
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").textfield("option", "keyup", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("textfieldkeyup",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… keyup="XXX()"></div> 或 <div…… keyup="XXX"></div>
			 */			
			keyup: null
    	},
    	
    	/** @lends jazz.textfield */
    	
        /**
         * @desc 创建组件
         * @private
         */ 
        _create: function() {
        	//创建组件
        	this._super();
        	
        	this._createElements();
        },
        
        /**
         * @desc 创建结构组件
         * @private
         */ 
        _createElements: function(){
        	this.inputtext = this.input = $('<input id="'+this.compId+'_input" class="jazz-field-comp-input" type="text" name="'+this.options.name+'" autocomplete="off">').appendTo(this.inputFrame);        	
        },

        /**
         * @desc 初始化组件
         * @private
         */ 
		_init: function(){
			
			this._super();

			this._bindEvent();
			
			//验证
			this._validator();            
		},
		
		/**
         * @desc 相关事件处理
         * @private
		 */
		_bindEvent: function(){
			var $this = this;
			this.inputtext.off("focus.textfield blur.textfield keyup.textfield").on("focus.textfield", function(){
        		if($(this).val() == $this.options.valuetip){
					$(this).val("");
					$(this).removeClass('jazz-field-comp-input-tip');
				}
        		$this.inputValue = $this.inputtext.val();
        		$this._event("enter", null, {newValue: $this.inputValue});

        	}).on("blur.textfield", function(){
        		var value = $(this).val();
        		if(value == "" && $this.options.valuetip){
					$(this).val($this.options.valuetip);
					$(this).removeClass("jazz-field-comp-input-tip").addClass("jazz-field-comp-input-tip");
				}
        		var ui = {
    				newValue: value || "",    //新值
    				oldValue: $this.inputValue || ""       	  //旧值
        		};
        		$this._event("change", null, ui);
        		
        	}).on("keyup.textfield", function(e) {
            	setTimeout(function(){
            		var text = $this.inputtext.val(), length = text.length;
            		var maxlength = $this.options.maxlength;
            		if(maxlength){
	            		if(length > maxlength) {
	                    	text = text.substr(0, maxlength);
	                        $this.inputtext.val(text);
	                    }
            		}
            		$this._event("keyup", null, {newValue: text});
            	}, 500);
            });		
		}	
		
    });
    
});
