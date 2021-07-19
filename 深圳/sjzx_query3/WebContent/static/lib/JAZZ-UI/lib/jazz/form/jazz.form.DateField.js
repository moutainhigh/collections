(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 
				 'form/jazz.form.DropdownField', 
				 'jazz.Date'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
/** 
 * @version 0.5
 * @name jazz.datefield
 * @description 时间类。
 * @constructor
 * @extends jazz.field
 * @requires
 * @example $('#input_id').datefield();
 */
	$.widget('jazz.datefield', $.jazz.dropdownfield, {
	    options: /** @lends jazz.datefield# */ {
			
			/**
			 *@type String
			 *@desc 组件的效验类型
			 *@default ''
			 */
			vtype: 'datefield',		
	        
            /**
	         *@type  boolean
	         *@desc  是否显示年份月份下拉框
	         *@default  true
	         */
	        isshowdatelist: true,		
	        
	        //event
            /**
             *@desc 鼠标焦点离开输入框时触发
             *@param {event} 事件
             *@param {ui.newValue} 新修改的值 
             *@param {ui.oldValue} 旧值
             *@event
             *@example
             *<br/>$("XXX").datefield("option", "change", function(event, ui){  <br/>} <br/>});
             *或:
             *<br/>$("XXX").on("datefieldchange",function(event, ui){  <br/>} <br/>});
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
			 *<br/>$("XXX").datefield("option", "enter", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("datefieldenter",function(event){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… enter="XXX()"></div> 或 <div…… enter="XXX"></div>
			 */			
			enter: null,
    		
    		/**
			 *@desc 当选择了某项时，触发itemselect事件  
			 *@param {event} 事件
			 *@param {value} 选中项的值 
			 *@event
			 *@example
			 *<br/>$("XXX").datefield("option", "itemselect", function(event, value){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("datefielditemselect",function(event, value){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… itemselect="XXX()"></div> 或 <div…… itemselect="XXX"></div>
			 */
			itemselect: null

		},
		
		/** @lends jazz.datefield*/
		
        /**
         * @desc 创建组件
         * @private
         */  
		_create: function(){
			//创建组件
        	this._super();
        	this.trigger.removeClass("jazz-field-comp-ar").addClass("jazz-field-comp-date");
        	//this._createDownpanel();
        	
        	this.input = this.inputtext;
		},
		
        /**
         * @desc 初始化组件
         * @private
         */
        _init: function() {
        	this._super();
        	
        	this._dropdate();
        	
        	var $this = this;
        	this.inputtext.off("focus.datefield blur.datefield").on("focus.datefield", function(){
        		if($(this).val() == $this.options.valuetip){
					$(this).val("");
					$(this).removeClass('jazz-field-comp-input-tip');
				}
        	}).on("blur.datefield", function(){
        		if($(this).val()=="" && $this.options.valuetip){
					$(this).val($this.options.valuetip);
					$(this).removeClass('jazz-field-comp-input-tip').addClass('jazz-field-comp-input-tip');
				}
        	});
        	
			//验证
			this._validator();         	
        },
 
        /**
         * @desc 下拉树
         * @private 
         */        
        _dropdate: function(){
        	var dropdatej = this.itemsContainer.date({
        		isshowdatelist: this.options.isshowdatelist
        	});
        	var $this = this;
        	dropdatej.off("dateselect.datefield").on("dateselect.datefield",function(event, ui){
        		$this._oldValue = $this._oldText = $this.getValue();
        		$this.setValue(ui.date);
                $this._event("itemselect", event ,{"value": ui.date});
                $this.panel.hide();
                $this._newValue = $this._newText = ui.date;
            	$this._event("change", event, $this._changeData());
        	});        	
        },

		/**
         * @desc 列表框是否显示滚动条
		 * @private
         */        
        _itemScroll: function(){},        
        
        /**
         * @desc 设置组件属性的值
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private 
         */
        _setOption: function(key, value){
        	switch(key){
	        	case 'isshowdatelist':
	        		this.options.isshowdatelist = value;
	        		this.itemsContainer.date("option", "isshowdatelist", value);
	        		break;
        	}
        	this._super(key, value);
        }
		
	});

});