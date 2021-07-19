(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',
		         'form/jazz.form.CheckboxField'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.radiofield
	 * @description 表单元素的单选按钮。
	 * @constructor
	 * @extends jazz.checkboxfield
	 * @example $('XXX').radiofield();
	 */	
    
    $.widget("jazz.radiofield", $.jazz.checkboxfield, {
       
    	options: /** @lends jazz.radiofield# */ {
    		
    		/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default radiofield
    		 */
    		vtype: 'radiofield'
    		
            // callbacks
    	},
    	
    	/** @lends jazz.radiofield */ 
    	
        /**
         * @desc 创建组件
         * @private
         */
        _create: function() {
        	//创建组件
        	this._super();
        },
        
        /**
         * @desc 初始化组件
         * @private
         */   
        _init: function(){
        	this._super();
        },
        
    	/**
         * @desc 绑定事件
         * @private
         */ 
        _bindEvents: function() {
            var $this = this;
            if(this.options.disabled != true &&  this.options.editable == true){
            	 this.parent.off('click.checkboxfield').on('click.checkboxfield', function(e) {
	               	 var target = e.target, $target = $(target);
	               	 var boxid = $target.attr("id");
	               	 boxid = boxid.substring(0, boxid.length-5);
	               	 var box = $('#'+boxid);
	               	 
	   				 if($target.is('span')){
	   	                 if(!$this._isChecked(box)){
	                       	 $this._nocheckAllImg();
	   	                 }
	   	                 $this._toggle(box, $target,e);
	   				 }
	   				 if($target.is('label')){
		                 var span = $('#'+boxid+'_span');
		                 if(!$this._isChecked(box)){
			               	 $this._nocheckAllImg();
			               	 $this._checked(box, span);
		                 }
	   					 $this._event("itemselect", e ,{checked: true, text:box.attr("text"), value: box.val()});
	   				 }
               });
			}
        },        

		/**
         * @desc 取消所有radio对象
         * @private
		 */ 
        _nocheckAllImg: function(){
        	for(var i=0, len=this.checkItems.length; i<len; i++){
        		var span = $('#'+this.checkItems[i]+'_span');
        		span.removeClass('jazz-radio-checked').addClass('jazz-radio-nochecked');
        	}         	
        },        
                
		/**
         * @desc 切换是否被选中
		 * @param {box} radio对象
		 * @param {spanObj} radio对像中的span对象
         * @private
		 * @example this._toggle();
		 */  
        _toggle: function(box, spanObj, event) {
            if(!this._isChecked(box)) {
                this._checked(box, spanObj);
            } 
			this._event("itemselect", event ,{checked: true, text:box.attr("text"), value:box.val()});
        },
        
		/**
         * @desc 获取当前选中状态对象的值
		 * @return 所有选中的值
		 * @example $('XXX').radiofield('getValue');
		 */
        getValue: function(){
        	var name = this.options.name;
        	return this.parent.find("input[name='"+name+"'][type='radio']:checked").val() || "";
        },        
        
		/**
         * @desc 设置当前状态对象选中
		 * @param {value} 选中对象的值
		 * @example $('XXX').radiofield('setValue','2');
		 */
        setValue: function(value, name){
        	this.reset();
        	var $this = this;
        	$.each((value+"").split(","),function(i, data){
        		$this.parent.find("input[value='"+data+"'][type='radio']").attr('checked', 'true');
        		var span = $('#'+$this.compIdspan+'_box_'+data+'_span');
        		span.removeClass('jazz-radio-nochecked').addClass('jazz-radio-checked');
        	});
        	this.inputview.text(this.getText() || "");
        }
        
    });
    
});
