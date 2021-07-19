(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'form/jazz.form.IconField'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.numberfield
	 * @description 基本的数字字段。
	 * @constructor
	 * @extends jazz.iconfield
	 */
    $.widget("jazz.numberfield", $.jazz.iconfield, {
    	
    	options: /** @lends jazz.numberfield# */ {
    		
    		/**
			 *@type String
			 *@desc 组件的效验类型
			 *@default 'numberfield'
			 */
    		vtype: 'numberfield',  		
            
            /**
			 *@type Number
			 *@desc 数值最大值
			 *@default 1.0
			 */
            max: null,
            
            /**
			 *@type Number
			 *@desc 数值最小值
			 *@default 1.0
			 */
            min: null,
            
            /**
             *@type Number
             *@desc 数值间间隔的步长
             *@default 1.0
             */
            step: 1.0,
            
            // callbacks
    		
    		/**
			 *@desc 当文本框值变化时触发
			 *@param {event} 事件
			 *@param {ui.newvalue} 新修改的值
			 *@param {ui.oldvalue} 旧值
			 *@event
			 *@example
			 *初始化：
			 *<br/>$("#input_id").numberfield({change: function( event, ui ){  <br/>} <br/>});
			 *非初始化:
			 *<br/>$("#input_id").on("numberfieldchange",function( event, ui ){  <br/>} <br/>});
			 */
    		change: null
        },
        
        /** @lends jazz.numberfield */
        /**
         * @desc 创建组件
         * @private
         */  
        _create: function() {
        	//创建组件
        	this._super();
        	this.parent.append('<a class="jazz-field-icon jazz-number-btn jazz-number-btn-up"><span id="'+this.compId+'_up" class="jazz-number-img jazz-number-upImg"></span></a>'
	                          +'<a class="jazz-field-icon jazz-number-btn jazz-number-btn-down"><span id="'+this.compId+'_down" class="jazz-number-img jazz-number-downImg"></span></a>');

			this.upArrows = $('#'+this.compId+'_up');
			this.downArrows = $('#'+this.compId+'_down');
			
			this.input = this.inputtext;
			
//			this.upArrows.hover(function() {
//            }).focus(function() {
//            	$(this).addClass('jazz-number-upImg2');
//            }).blur(function() {
//            	$(this).removeClass('jazz-number-upImg2');
//            });
//			this.downArrows.hover(function() {
//            }).focus(function() {
//            	$(this).addClass('jazz-number-downImg2');
//            }).blur(function() {
//            	$(this).removeClass('jazz-number-downImg2');
//            });
        },

        /**
         * @desc 初始化组件
         * @private
         */
        _init: function(){
        	this._super();
        	
        	this._initValue();
        	var step = this.options.step;
            step = step || 1;
            
            if(parseInt(step, 10) === 0) {
                this.options.precision = step.toString().split(/[,]|[.]/)[1].length;
            }
            
            this._bindEvents();
            
			//验证
			this._validator();            
        },
        
    	/**
         * @desc 绑定事件
         * @private
         */ 
        _bindEvents: function() {
            var $this = this;
            this.parent.children('.jazz-number-btn').off('mouseout.number mouseup.number mousedown.number')
            	.on('mouseout.number', function() {
                    if($this.timer) {
                        window.clearInterval($this.timer);
                    }
                }).on('mouseup.number', function() {
                    window.clearInterval($this.timer);
                    //$this.inputtext.focus();
                }).on('mousedown.number', function(e) {
                    var element = $(this),
                    dir = element.hasClass('jazz-number-btn-up') ? 1 : -1;
                    $this.inputtext.focus();
                    $this._repeat(null, dir);
                    e.preventDefault();
                });

            this.inputtext.off("keydown.number keyup.number blur.number focus.number")
            .on("keydown.number", function (e) {   
                var keyCode = $.ui.keyCode;
                switch(e.which) {
                    case keyCode.UP:
                        $this._spin($this.options.step);
                    break;
                    case keyCode.DOWN:
                        $this._spin(-1 * $this.options.step);
                    break;
                }
            }).on("keyup.number", function () {
                $this._updateValue();              
            }).on("blur.number", function () {
            	if($this.inputtext.val()===''){
            		$this._valuetip();
            	}else{
            		$this._format();
            	}
            	$this._triggerChange();
            }).on("focus.number", function () {
        		var value = $(this).val();
            	if(value == $this.options.valuetip){
					$(this).val("");
					$(this).removeClass('jazz-field-comp-input-tip');
				}
        		if(!isNaN(value)){
        			$this._format();
        			$this.oldvalue = $(this).val();
        		}else{
        			$this.oldvalue = "";
        		}
            });
            
//            //mousewheel
//            this.element.bind('mousewheel', function(event, delta) {
//                if($this.element.is(':focus')) {
//                    if(delta > 0)
//                        $this._spin($this.options.step);
//                    else
//                        $this._spin(-1 * $this.options.step);
//
//                    return false;
//                }
//            });
        },
        
        /**
         * @desc 设置组件是否置灰
         * @private
         */        
        _disabled: function(){
        	this._super();
        	if(this.options.disabled == true || this.options.disabled == "disabled"){
        		this.parent.children('.jazz-number-btn').off('mouseout.number mouseup.number mousedown.number');
        		this.inputtext.off("keydown.number keyup.number blur.number focus.number");
        	}else{
        		this._bindEvents();
        	}
        },

        /**
         * @desc 格式化值
		 * @private
         */
        _format: function() {
            var value = this.value;
            var newvalue;

            if(this.options.precision){
                newvalue = parseFloat(this._toFixed(value, this.options.precision));
            }else{
                newvalue = parseInt(value, 10);
            }
            if((this.options.min || this.options.min===0) && newvalue < this.options.min) {
                newvalue = this.options.min;
            }
            if(this.options.max && newvalue > this.options.max) {
                newvalue = this.options.max;
            } 
            
            if(isNaN(newvalue)){
            	this._valuetip();
            }else{
            	this.value = newvalue;
            	this.inputtext.val(newvalue);
            }
        },
        
        /**
         * @desc 初始值
		 * @private
         */
        _initValue: function() {
            var value = this.inputtext.val();

            if(value === '' || (!this.options.defaultvalue && value == this.options.valuetip)) {
                if(this.options.min || this.options.min===0){
                    this.value = this.options.min;
                }else{
                    this.value = '';
                }
            } else {
//                if(this.options.prefix){
//                    value = value.split(this.options.prefix)[1];
//                }
//                if(this.options.suffix){
//                    value = value.split(this.options.suffix)[0];
//                }

                if(this.options.step){
                    this.value = parseFloat(value);
                }else{
                    this.value = parseInt(value, 10);
                }
            }
        },
        
        /**
         * @desc 加载值
		 * @param {interval}
		 * @param {dir}  
		 * @private
         */
        _repeat: function(interval, dir) {
            var $this = this, i = interval || 500;
            window.clearTimeout(this.timer);
            this.timer = window.setTimeout(function() {
                $this._repeat(40, dir);
            }, i);
            this._spin(this.options.step * dir);
        },
        
		/**
         * @desc 动态改变属性
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         */
        _setOption: function(key, value){
        	switch(key){
        		case 'max':
        			this.options.max = value;
        			break;
        		case 'min':
        			this.options.min = value;
        			break;
        		case 'step':
        			this.options.step = value;
        		break;
        	}
        	this._super(key, value);
        },
        
        /**
         * @desc 组件赋值
         * @param {step} 增长值
		 * @private
         */       
        _spin: function(step) {
            var newvalue, currentValue = this.value ? this.value : 0;
            currentValue = parseInt(currentValue, 10);
            
            if(this.options.precision){
                newvalue = parseFloat(this._toFixed(currentValue + step, this.options.precision));
            }else{
                newvalue = parseInt(currentValue + step, 10);
            }
            
            if((!!this.options.min || this.options.min===0) && newvalue < this.options.min) {
                newvalue = this.options.min;
            }
            
            if(!!this.options.max && newvalue > this.options.max) {
                newvalue = this.options.max;
            }

            this.inputtext.val(newvalue);
            this.value = newvalue;
            
            //this.oldvalue = currentValue;
        },
        
        /**
         * @desc 计算精度值
         * @param {value} 现有值
         * @param {precision}
         * @return 返回计算后的精度值
		 * @private
         */        
        _toFixed: function (value, precision) {
            var power = Math.pow(10, precision||0);
            return String(Math.round(value * power) / power);
        },

        /**
         * @desc change事件
         * @private
         */		
		_triggerChange: function(){
			var newvalue = this.value;
			var oldvalue = this.oldvalue;
			var ui = {
				newvalue: newvalue,                   //新值
				oldvalue: oldvalue                    //旧值
			};
			this._event("change", null, ui);
		},
                
        /**
         * @desc 更新值
		 * @private
         */
        _updateValue: function() {
            var value = this.inputtext.val();

            if(value === '') {
                if(this.options.min || this.options.min===0){
                    this.value = this.options.min;
                }else{
                    this.value = '';
                }
            }else {
                if(this.options.step){
                    value = parseFloat(value);
                }else{
                    value = parseInt(value, 10);
                }
                if(!isNaN(value)) {
                    this.value = value;
                }
            }
        },

		/**
         * @desc 设置元素值  
         * @param {value} 设置的值
		 * @example $('XXX').numberfield('setValue', value);
         */
		setValue: function(value) {
			this._super(value);
			if(value === "" || value == undefined){ value = this.options.min; }
			if(value){
				this.oldvalue = Number(value);
				this.value = Number(value);				
			}
		}
    });
});
