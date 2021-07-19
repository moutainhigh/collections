(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'form/jazz.form.HiddenField'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.passwordfield
	 * @description 基本的数字字段。
	 * @constructor
	 * @extends jazz.hiddenfield
	 * @example $('XXX').passwordfield();
	 */
    $.widget("jazz.passwordfield", $.jazz.hiddenfield, {
        
        options: /** @lends jazz.passwordfield# */ {
        	
    		/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default passwordfield
    		 */        	
        	vtype: 'passwordfield',
        	
    		/**
			 *@type String
			 *@desc 中等强度密码提示
			 *default '中'
			 */               
            goodlabel: '中',

    		/**
			 *@type Boolean
			 *@desc 是否显示强弱的提示信息  false 不显示  true 显示
			 *@default false
			 */            
            passwordtip: false,
        	
    		/**
			 *@type String
			 *@desc 提示信息
			 *@default ''
			 */           	
            promptlabel: jazz.config.i18n.password,
            
    		/**
			 *@type String
			 *@desc 高等强度密码提示
			 *@default '强'
			 */               
            stronglabel: '强',

    		/**
			 *@type String
			 *@desc 低等强度密码提示
			 *@default '弱'
			 */              
            weaklabel: '弱'
                        
        },
       
        /** @lends jazz.passwordfield */ 
        /**
         * @desc 创建组件
         * @private
         */         
        _create: function() {
        	//创建组件
        	this._super();
        	
    		this.input = this.inputtext = $('<input id="'+this.compId+'_input" class="jazz-field-comp-input" type="password" name="'+this.options.name+'" autocomplete="off" >').appendTo(this.inputFrame);
        	
    		this._passwordtip();
        },
        
        /**
         * @desc 初始化组件
         * @private
         */                 
        _init: function(){
        	this._super();
        	
            this._bindEvents();
            
			//验证
			this._validator();
        },       
        
        /**
         * @desc 覆盖valuetip提示, passwordfield不支持valuetip功能
         * @private
         */         
        _valuetip: function(){},
        
		/**
		 *@desc 提示信息显示位置
		 *@private
		 */         
        _align: function() {
            this.panel.css({
                left:'', 
                top:'',
                'z-index': ++jazz.zindex
            })
            .position({
                my: 'left top',
                at: 'right top',
                of: this.parent
            });
        },
        
    	/**
         * @desc 绑定事件
         * @private
         */     
        _bindEvents: function() {
        	if(this.options.passwordtip == true) {
	            var $this = this;
	            
	            this.inputtext.off('focus.passwordfield blur.passwordfield keyup.passwordfield').on('focus.passwordfield', function() {
	            	if($this.options.passwordtip == "true" || $this.options.passwordtip == true){
	            		$this.infoText.text($this.options.promptlabel);	            		
	            	}
	                $this.showTip();
	            }).on('blur.passwordfield', function() {
	                $this.hideTip();
	            }).on('keyup.passwordfield', function() {
	                var value = $this.inputtext.val(),
	                label = null,
	                meterPos = null;
	
	                if(value.length === 0) {
	                    label = $this.options.promptlabel;
	                    meterPos = '0px 0px';
	                }else {
	                    var score = $this._testStrength($this.inputtext.val());
	
	                    if(score < 30) {
	                        label = $this.options.weaklabel;
	                        meterPos = '0px -10px';
	                    }
	                    else if(score >= 30 && score < 80) {
	                        label = $this.options.goodlabel;
	                        meterPos = '0px -20px';
	                    } 
	                    else if(score >= 80) {
	                        label = $this.options.stronglabel;
	                        meterPos = '0px -30px';
	                    }
	                }
	
	                $this.meter.css('background-position', meterPos);
	                $this.infoText.text(label);
	                
	                $this._align();
	            });
        	}
        },
        
    	/**
         * @desc 是否显示密码强弱的提示信息
         * @private
         */
        _passwordtip: function(){
        	if(!this.element.prop(':disabled') && this.options.passwordtip == true) {
                var panelMarkup = '<div class="jazz-password-panel jazz-state-highlight jazz-helper-hidden">';
                panelMarkup += '<div class="jazz-password-meter" style="background-position:0px 0px"></div>';
                panelMarkup += '<div class="jazz-password-info">' + this.options.promptlabel + '</div>';
                panelMarkup += '</div>';

                this.panel = $(panelMarkup).insertAfter(this.element);
                this.meter = this.panel.children('div.jazz-password-meter');
                this.infoText = this.panel.children('div.jazz-password-info');

                if(this.options.inline) {
                    this.panel.addClass('jazz-password-panel-inline');
                } else {
                    this.panel.addClass('jazz-password-panel-overlay').appendTo('body');
                }
            }        	
        },

		/**
		 *@desc 规格化
		 *@param {x} 
		 *@param {y}
		 *@private
		 */         
        _normalize: function(x, y) {
            var diff = x - y;

            if(diff <= 0) {
                return x / y;
            }
            else {
                return 1 + 0.5 * (x / (x + y/4));
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
        		case 'passwordtip':
        			this.options.passwordtip = value;
        			if(value == true){
        				this._passwordtip();
        				this._bindEvents();
        			}else{
        				if(this.panel){
        					this.inputtext.off('focus.passwordfield blur.passwordfield keyup.passwordfield');
        					this.panel.hide();
        				}
        			}
        		break;
        	}
        	this._super(key, value);
        },
        
		/**
		 *@desc 效验密码的强弱
		 *@param {str} 输入的字符串
		 *@private
		 */         
        _testStrength: function(str) {
            var grade = 0, 
            val = 0, 
            $this = this;

            val = str.match('[0-9]');
            grade += $this._normalize(val ? val.length : 1/4, 1) * 25;

            val = str.match('[a-zA-Z]');
            grade += $this._normalize(val ? val.length : 1/2, 3) * 10;

            val = str.match('[!@#$%^&*?_~.,;=]');
            grade += $this._normalize(val ? val.length : 1/6, 1) * 35;

            val = str.match('[A-Z]');
            grade += $this._normalize(val ? val.length : 1/6, 1) * 30;

            grade *= str.length / 8;

            return grade > 100 ? 100 : grade;
        },

		/**
		 *@desc 隐藏提示
		 *@example $('XXX').passwordfield('hideTip');
		 */  		
        hideTip: function() {
            if(this.options.inline){
                this.panel.slideUp();
            }else{
                this.panel.fadeOut();
            }
        },

		/**
		 *@desc 显示提示
		 *@example $('XXX').passwordfield('showTip');
		 */  
        showTip: function() {
            if(!this.options.inline) {
                this._align();

                this.panel.fadeIn();
            }
            else {
                this.panel.slideDown(); 
            }        
        }
        
    });
    
});
