(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',
		         'jazz.Validator', 
		         '../jazz.Tooltip'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
/** 
 * @version 1.0
 * @name jazz.field
 * @description 表单元素的基类，提供基本的元素内容。
 * @constructor
 * @extends jazz.boxComponent
 */
	$.widget('jazz.field', $.jazz.boxComponent, {
		
	    options: /** @lends jazz.field# */ {
        	
        	/**
    		 *@type String
    		 *@desc 组件默认值
    		 *@default
    		 */
        	defaultvalue: '',
        	
        	/**
        	 *@type Boolean
        	 *@desc 是否禁用 true不可用 false可用
        	 *@default true
        	 */ 
        	disabled: false,
        	
        	/**
        	 *@type Boolean
        	 *@desc 是否可编辑 true可编辑 false不可编辑 
        	 *@default true
        	 */
        	editable: true,
        	
        	/**
        	 *@type Boolean
        	 *@desc 控制组件是否参与验证 true　验证 false　不验证
        	 *@default true
        	 */        	
        	isrule: true,

        	/**
        	 *@type String
        	 *@desc 组件标签信息
        	 *@default 
        	 */
        	label: '',
        	
        	/**
        	 *@type String
        	 *@desc 标签的显示位置   left 居左  center 居中  right 居右
        	 *@default 'right'
        	 */
        	labelalign: 'right',
        	
        	/**
        	 *@type Number
        	 *@desc 组件标签的宽度
        	 *@default 80
        	 */
        	labelwidth: '',
        	
        	/**
        	 *@type String
        	 *@desc 组件标签信息和内容区的分隔符
        	 *@default ':'
        	 */
        	labelsplit: '：',
			
			/**
			 *@type String
			 *@desc 输入框内容区的前缀
			 *@default ''
			 */			
			prefix: '',
        	
        	/**
        	 *@type Boolean
        	 *@desc 是否是可读字段 true只读  false非只读
        	 *@default false
        	 */
        	readonly: false,
			
			/**
			 *@type String
			 *@desc 组件的验证规则
			 *@default ''
			 */	
        	rule: '',
        	
        	/**
        	 * @type Number
        	 * @desc 数据有效性的验证类型，用于验证不通过后的提示信息如何展示 共有 0 1 2 3种验证
        	 * @default 0
        	 */
        	ruletype: 0,
			
			/**
			 *@type String
			 *@desc 输入框内容区的后缀
			 *@default ''
			 */			
			suffix: '',
			
			/**
			 *@type String
			 *@desc 输入框的提示信息
			 *@default ''
			 */			
			tooltip: '',
			
			/**
			 *@type String
			 *@desc 输入框默认提示文字
			 *@default ''
			 */		
			valuetip: ''			
		},
		
		/** @lends jazz.field */
		
        /**
         * @desc 生成公用部分的dom元素
         * @private 
         */
		_create: function(){
			this._super();
			
			var el = this.element;
			
			//定义disabled属性时，与IE的disabled属性名相同，
			//导致IE7 IE8 IE9下边DIV遮罩，所在要判断如果有这个属性就将其去掉
			if(this.element.prop("disabled")){
				this.element.removeAttr("disabled");
			}
			
			this.compId = this.getCompId();

			//设置组件必要属性
			el.addClass('jazz-field-comp').addClass('jazz-' + this.options.vtype + '-comp');
			
			var comp = '<label id="'+this.compId+'_label" class="jazz-field-comp-label jazz-form-text-label" for="'+this.compId+'_input"></label>'
			         + '<div id="'+this.compId+'_out" class="jazz-field-comp-out"><div id="'+this.compId+'_in" class="jazz-field-comp-in jazz-field-comp-in2">'
			         + '<span id="'+this.compId+'_prefix" class="jazz-field-comp-prefix"></span>'
			         + '<div id="'+this.compId+'_frame" class="jazz-field-input-frame"><label id="'+this.compId+'_editor" class="jazz-form-text-label" style="display:none"></label></div>'
			         + '<span id="'+this.compId+'_suffix" class="jazz-field-comp-suffix"></span>'
			         + '</div><div id="'+this.compId+'_r" class="jazz-field-comp-r jazz-helper-hidden"></div></div>';
			
			el.append(comp);
			
			//创建相应对象
			this.label = $('#'+this.compId+'_label');
			this.grandpa = $('#'+this.compId+'_out');
			this.parent = $('#'+this.compId+'_in');
			this.inputFrame = $('#'+this.compId+'_frame');
			this.inputview = $('#'+this.compId+'_editor');
			this.prefix = $('#'+this.compId+'_prefix');
			this.suffix = $('#'+this.compId+'_suffix');
			this.ruleImg = $('#'+this.compId+'_r');
			
			//验证区域警示图片所要占用宽度
			this.ruleAreaWidth = jazz.config.ruleImgWidth;	
			
			//icon图标所占的宽度
			this.arrowwidth = jazz.config.fieldIconWidth;
		},
		
        /**
         * @desc 初始化
         * @private
         */
		_init: function(){
			this._super();
			
			//设置组件的验证状态是验证通过的
			this.options.isverify = true;			
			
			this._formpanel();

			this._label();
			//label显示位置
			this._labelalign();
			//label宽度
			this._labelwidth();			
			//前缀
			this._prefix();
			//后缀
			this._suffix();
			//浮动的提示信息
			this._tooltip();
			//设置默认值
			this._defaultvalue();
			//值提示
			this._valuetip();
			//验证
			this._rule();
			//输入框是否可编辑
			this._editable();
			//默认false, 是否可用，true不可用，不可用时输入框置灰显示， false可用
			this._disabled();
			
			if(this.options.vtype=="hiddenfield"){
				this.element.hide();
			}
			this.isInputBox = this._filterVtype();
			
			this._width();
			
			this._height();

			//默认false, 是否只读 true只读 无输入框  false非只读 有输入框, 
			//放在计算的宽度高度之后执行，防止初始化时，设置成readonly==false时，计算宽度时缺少边框
			this._readonly();		
		},
		
        /**
         * @desc 设置默认值
         * @private
         */
		_defaultvalue: function(){
        	//默认值
        	var value = this.options.defaultvalue;
        	if(value || value===0){
        		this.setValue(value);
        	}	
		},
		
        /**
         * @desc 设置组件是否置灰
         * @private
         */		
		_disabled: function(){
    		if(this.options.vtype != "hiddenfield"){
        		if(this.options.disabled == true || this.options.disabled == "disabled"){
        			this.inputtext.attr("disabled", "disabled");
        			this.inputtext.addClass("jazz-field-disabled");
        			this.options.isrule = false;
        		}else{
        			if(this.inputtext.prop("disabled")){
        				this.inputtext.removeAttr("disabled");
        				this.inputtext.removeClass("jazz-field-disabled");
        			}
        			this.options.isrule = true;
        		}
    		}			
		},
		
        /**
         * @desc 设置是否可编辑  默认true, 是否可编辑 true可编辑  false不可编辑
         * @private
         */		
		_editable: function(){
			if(this.options.editable == false){
				this.inputtext.attr("readonly", "true");
			}else{
				this.inputtext.removeAttr("readonly");
			}	
		},

        /**
         * @desc 当表单组件被放置到formpanel里时，formpanel设置的属性相当于全局属性，控制表单的显示
         * @private
         */		
		_formpanel: function(){
			var compParent = this.getParentComponent(), dataview = false, readonly = false;
			compParent = $(compParent);
			if(compParent.attr("vtype") === "formpanel"){
				this.formpanelobj = compParent.data("formpanel");
				dataview = this.formpanelobj.options.dataview;
				readonly = this.formpanelobj.options.readonly;
				this.condition = this.formpanelobj.options.iscondition;
			}
			if(dataview == "true" || dataview == true || readonly == "true" || readonly == true){
				//判断是否存在readonly属性, 不存在时，恰巧formpanel有这个属性设置，则按formpanel的值进行设置
				var b = this.element.attr("readonly") ? false: true;
				if(b){
					this.options.readonly = true;
				}
			}
		},
		
		/**
		 * @desc 过滤掉没有输入框的组件 true有输入框  false没有输入框 例如  radio  checkbox  attachment 都没有输入框
		 * @private
		 * @returns {Boolean}
		 */
		_filterVtype: function(){
			var vtype = this.options.vtype;
			if(vtype !="radiofield" && vtype != "checkboxfield" 
				& vtype !="hiddenfield" && vtype != "attachment"){
				return true;
			}else{
				return false;
			}
		},

		/**
		 * @desc 设置组件高度
		 * @private
		 */
		_height: function(forcecalculate){
			var height = this.options.height;
			this.options.height = height != -1 ? height : jazz.config.fieldDefaultHeight;
			this._super(forcecalculate);

	        if(this.iscalculateheight){
	        	this._heightContent();
	        }
		},
		
		/**
		 * @desc 设置组件内部高度
		 * @private
		 */		
		_heightContent: function(){
        	var bw = this.parent.css("border-bottom-width");
        	if(bw){
        		bw = bw.substring(0, 1);
        	}else{
        		bw = 0;
        	}
        	
        	//计算组件的高度
        	if(this.isInputBox){
        		this.inputtext.outerHeight(this.options.calculateinnerheight - parseInt(bw)*2);
        	}else{
        		this.parent.css("border", "0px");
        	}			
		},
		
		/**
		 * @desc 设置组件描述
		 * @private
		 */
		_label: function(){
			var label = this.options.label || "";
			if(label){
				this.label.css({"display": 'block'});
				var newlabel = label + this.options.labelsplit;
				this.label.html(newlabel);
			}else{
				this.label.css({"display": 'none'});
			}
		},

		/**
		 * @desc 设置组件描述的显示位置
		 * @private
		 */		
		_labelalign: function(){
			if(this.options.label){
				var labelalign = this.options.labelalign;
				var align = ["left", "center", "right"];
				if($.inArray(labelalign, align) >= 0){
					this.label.css({"text-align": labelalign});				
				}else{
					this.label.css({"text-align": "right"});
				}
			}
		},
		
		/**
		 * @desc 设置组件描述的宽度
		 * @private
		 */		
		_labelwidth: function(){
			if(this.options.label){
				var labelwidth = this.options.labelwidth || jazz.config.fieldLabelWidth || 80;
				this.label.outerWidth(labelwidth);
			}
		},
		
		/**
		 * @desc 设置组件描述的宽度
		 * @private
		 */		
		_labelsplit: function(){
			this._label();
		},
		
		/**
		 * @desc 设置组件前缀
		 * @private
		 */			
		_prefix: function(){
			var prefix = this.options.prefix;
			if(!prefix){
				this.prefix.css({"display": 'none'});
			}else{
				this.prefix.css({"display": 'block'}).html(prefix);
			}			
		},
		
		/**
		 * @desc 设置组件是否为输入框或者是文本
		 * @private
		 */			
		_readonly: function(){
			//请注意：
			//1. _readonly 共有三处  当前是一处
			//2. 组件为 checkbox, radio时，jazz.form.checkboxfield.js中覆盖了_readonly
			//3. 组件为number, combox, comboxtree等继承iconfield类的下拉框组件，都在jazz.form.icon.js中对_readonly进行了覆盖
			if(this.options.readonly == true){
	    		this.parent.removeClass("jazz-field-comp-in2");
				this.inputtext.css({"display": "none"});										
				this.inputview.css({"display": "block"});
				var text = this.inputtext.val();
				if(text == this.options.valuetip){
					this.inputview.html("&nbsp;");
				}else{
					this.inputview.html(text || "&nbsp;");	
				}
			}else{
				this.inputview.css({"display": "none"});
				this.inputtext.css({"display": "block"});					
				this.parent.addClass("jazz-field-comp-in2");
			}
		},
		
		/**
		 * @desc 设置权限验证
		 * @private
		 */		
		_rule: function(){ 
			var rule = this.options.rule, label = this.options.label;
			if(rule){
				this.options.isrule = true;
			}else{
				this.options.isrule = false;
			} 
			if(label){
				var x = this.label.find("font").html() || "";
				var f = false;
				if(x=="*"){
					f = false;
				}else{
					f = true;
				}
				rule = (rule+"").indexOf("must");
				if(rule >=0 && f){
					this.label.prepend("<font color='red'>*</font>"); 
				}else if(rule < 0 && !f){
					this.label.find("font").remove();
				}
				
			}   
		},

//		/**
//		 * @desc 判断动态修改rule属性时，是否对组件进行验证
//		 * @private
//		 */			
//		_ruleverify: function(){
//			var rule = this.options.rule;
//			if((rule+"").indexOf("must")>=0){
//				var vtype = this.options.vtype;
//				if(vtype=="comboxfield" || vtype=="comboxtreefield" || vtype=="autocompletecomboxfield"){
//					this.reload();
//				}				
//			}
//			if(rule){
//				this._validator();
//			}else{
//				this._validatoroff();
//			}
//		},
		
		/**
         * @desc 动态改变属性
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         */
        _setOption: function(key, value){
        	switch(key){
        		case 'height':
        			this.options.height = value;
        			this._height();
        		break;
	        	case 'width':
	        		this.options.width = value;
	        		this._width();
	        		break;        		
    			case 'defaultvalue':
    				this.options.defaultvalue = value;
    				this._defaultvalue(value);
    				break;        	
        		case 'editable':
        			this.options.editable = value;
        			this._editable();
        			break;	
        		case 'readonly':
        			this.options.readonly = value;
        			this._readonly();
        			break;	
        		case 'rule':
        			this.options.rule = value;
        			this._rule();
        			if(value){
        				this._validator();
        			}else{
        				this._validatoroff();
        				this.verify();
        			}
        			break;
        		case 'ruletype':
        			this.options.ruletype = value;
        			this._widthInner();
        			var b = this.verify();
        			this._validateStyle(b);
        			break; 			
        		case 'prefix':
	        		this.options.prefix = value;
	        		this._prefix();
	        		//修改内容区宽度
	        		this._widthContent();
	        		break;
        		case 'suffix':
	        		this.options.suffix = value;
	        		this._suffix();
	        		//修改内容区宽度
	        		this._widthContent();
	        		break;
	        	case 'tooltip':
	        		this.options.tooltip = value;
	        		this._tooltip();
	        		break;
	        	case 'disabled':
	        		this.options.disabled = value;
	        		this._disabled();
	        		break;
				case 'valuetip':
					var oldtip = this.options.valuetip;
					this.options.valuetip = value;
					this._valuetip(true, oldtip);
					break;
				case 'label':
					this.options.label = value;
					this._label();
					this._labelalign();
					this._labelwidth();
					this._width();
					break;
				case 'labelalign':
					this.options.labelalign = value;
					this._labelalign();
					break;
				case 'labelwidth':
					this.options.labelwidth = value;
					this._labelwidth();
					//因为label宽度发生变化，重新计算组件宽度
					this._width();
					break;
				case 'labelsplit':
					this.options.labelsplit = value;
					this._labelsplit();
					break;
        	}
        	this._super(key, value);
        },
		
        /**
         * @desc 设置输入框的文本展现内容
         * @param {value} 设置的文本内容
         * @private
		 */
		_setInputview: function(value){
			var val = value;
			if(val === "" || val == undefined){ val = ""; }
			if(this.options.blanktext != val && this.options.valuetip != val){
				this.inputview.html(val+"&nbsp;");     	
			}else{
				this.inputview.html("&nbsp;");
			}				
		},        
        
        /**
         * @desc 切换组件展现形式（编辑或者不可编辑）
         * @private
		 */
		_suffix: function(){
			var suffix = this.options.suffix;
			if(!suffix){
				this.suffix.css({"display": 'none'});
			}else{
				this.suffix.css({"display": 'block'}).html(suffix);
			}
		},
		
		/**
         * @desc 设置tooltip提示信息
		 * @private
         */
		_tooltip: function(){
			if(this.options.tooltip){
				var $this = this;
				this.element.tooltip({
					 showevent : 'mousedown',
					 hideevent : 'blur',
					 content : $this.options.tooltip || "",
					 position: $this.options.tooltipposition || 0
				});
			}
		},		
		
        /**
         * @desc 取消验证的绑定事件
         * @private
         */
        _validatoroff: function(){
        	var vtype = this.options.vtype;
        	this.inputtext.off("keyup.rule blur.rule");
        	if("datefield"===vtype){
        		this.element.off("datefielditemselect.rule");
        	}else if('numberfield'===vtype){
        		this.upArrows.off("click.ruleUp");
        		this.downArrows.off("click.ruleDown");
        	}
        },
        
		/**
		 * @desc 验证显示风格
		 * @params {state} 验证状态 false未通过验证  true通过验证
		 * @private
		 */
		_validateStyle: function(state){
			//根据验证规则调整组件
			 if(this.options.ruletype == "0"){
				 if(state == false){
					 this.parent.css("width", this.parentwidth - this.ruleAreaWidth);
					 this.inputFrame.css("width", this.framewidth - this.ruleAreaWidth);
					 if(this.isInputBox){
						 this.inputtext.outerWidth(this.inputFrame.width());
					 }
				 }else{
					 this.parent.css("width", this.parentwidth);
					 this.inputFrame.css("width", this.framewidth);
					 if(this.isInputBox){
						 this.inputtext.outerWidth(this.framewidth);
					 }
				 }
			 }			
		},
		
        /**
         * @desc 验证方法
         * @private
         */			
        _validator: function(){ 
        	var $this = this;
        	//this._validatoroff();
        	var vtype = this.options.vtype;
        	if(this.options.rule){
        		if('textfield'===vtype || 'textareafield'===vtype || 'comboxfield'===vtype || 'datefield'===vtype 
					|| 'comboxtreefield'===vtype || 'numberfield'===vtype || 'passwordfield'===vtype 
					|| 'autocompletecomboxfield'===vtype ){
        			var keyuptimeout, blurtimeout;
	        		this.inputtext.off("keyup.rule blur.rule").on("keyup.rule", function(){
    			         if(keyuptimeout){
    			       	     window.clearTimeout(keyuptimeout);
    			         }
    			         keyuptimeout = setTimeout(function(){
	        				 var val = $this.getText();
	        				 //验证
	        				 var state = jazz.doTooltip($this, val, $this.options.rule, $this.options.msg);
	        				 $this._validateStyle(state);
	        			 }, 500);
	        		}).on('blur.rule', function(){
	        			 if(blurtimeout){
	   			       	     window.clearTimeout(blurtimeout);
	   			         }
	        			 blurtimeout = setTimeout(function(){
	        				 var val = $this.getText();
							 var state = jazz.doTooltip($this, val, $this.options.rule, $this.options.msg);
							 $this._validateStyle(state);
	        			 }, 500);
					});
        		}
        		if("datefield"===vtype){
        			var vtime;
        			this.element.off("datefielditemselect.rule").on("datefielditemselect.rule", function(){
        				if(vtime){
        					window.clearTimeout(vtime);
        				}
        				vtime = setTimeout(function(){
						     var val = $this.getText();
							 var state = jazz.doTooltip($this, val, $this.options.rule, $this.options.msg);
							 $this._validateStyle(state);
	        			 }, 500);
        			});
        		}
        		if('numberfield'===vtype){
        			var uptimeout;
        			this.upArrows.off("click.ruleUp").on("click.ruleUp", function(){
	        			 if(uptimeout){
	   			       	     window.clearTimeout(uptimeout);
	   			         }
	        			 uptimeout = setTimeout(function(){
						     var val = $this.getText();
							 var state = jazz.doTooltip($this, val, $this.options.rule, $this.options.msg);
							 $this._validateStyle(state);
	        			 }, 100);
        			});
        			this.downArrows.off("click.ruleDown").on("click.ruleDown", function(){
	        			 if(uptimeout){
	   			       	     window.clearTimeout(uptimeout);
	   			         }
	        			 uptimeout = setTimeout(function(){
						     var val = $this.getText();
							 var state = jazz.doTooltip($this, val, $this.options.rule, $this.options.msg);
							 $this._validateStyle(state);
	        			 }, 100);
        			});
        		}
        		if('colorfield'===vtype){
        			var colortimeout;
        			this.inputtext.off('blur.rule').on('blur.rule', function(){
	        			 if(colortimeout){
	   			       	     window.clearTimeout(colortimeout);
	   			         }
	        			 colortimeout = setTimeout(function(){
	        				 var val = $this.getText();
							 var state = jazz.doTooltip($this, val, $this.options.rule, $this.options.msg);
							 $this._validateStyle(state);
	        			 }, 200);
					});
        		}
        	}
        },	
        
        /**
         * @desc 值提示
         * @param {opt} true 动态修改时调用  false 非动态修改时调用
         * @param {oldtip} 修改之间的valuetip值 
         * @private
         */	        
        _valuetip: function(opt, oldtip){
        	//输入框内提示信息
        	var valuetip = this.options.valuetip;
        	if(opt){
        		//动态改属性时
        		var value = this.inputtext.val();
        		if(value=='' || value==oldtip){
        			this.inputtext.val(valuetip);
        			this.inputtext.addClass("jazz-field-comp-input-tip");        			
        		}
        	}else if(!this.options.defaultvalue && valuetip){
        		//初始化时执行
    			this.inputtext.val(valuetip);
    			this.inputtext.addClass("jazz-field-comp-input-tip");
        	}
        },       
        
		/**
         * @desc 设置组件的宽度
		 * @private
		 */          
        _width: function(forcecalculate){
        	var width = this.options.width;
        	this.options.width = width != -1 ? width : jazz.config.fieldDefaultWidth;
        	this._super(forcecalculate);
        	if(this.iscalculatewidth){
        		this._widthInner();
        	}
        },
        
		/**
		 * @desc 重置组件内容区宽度大小
		 * @private
		 */        
        _widthInner: function(){
			var labelWidth = 0;
			if(this.label.css("display") == "block"){
				labelWidth = this.label.outerWidth() + 5;
			}
			
			//验证区
			var ruletype = this.options.ruletype;
			
			var innerwidth = this.options.calculateinnerwidth;
			
			var iew = 1; //满足在ie6下多减去一个像素
			
//			if(jazz.isIE(7) || jazz.isIE(6)){
				this.grandpa.outerWidth(innerwidth - labelWidth - iew);
//			}else{
//				this.grandpa.css({'padding-left': labelWidth});
//				this.grandpa.outerWidth(innerwidth);
//			}

			var iDivWidth = 0;
			if(ruletype==1){
				iDivWidth = innerwidth - labelWidth  - this.ruleAreaWidth;
			}else{
				iDivWidth = innerwidth - labelWidth;
			}
			this.parent.outerWidth(iDivWidth - iew);
			this._widthContent();        	
        },
        
		/**
		 * @desc 重置组件内容区宽度大小
		 * @private
		 */
		_widthContent: function(){
			var prefixWidth = 0, suffixWidth = 0; //前缀宽度, 后缀宽度
			
			this.parentwidth = this.parent.width();
			if(this.options.prefix){
				prefixWidth = this.prefix.outerWidth();
			}
			if(this.options.suffix){
				suffixWidth = this.suffix.outerWidth();
			}

			var vtype = this.options.vtype;
			if(vtype == "datefield" || vtype == "comboxfield" || vtype == "numberfield" || vtype == "comboxtreefield" || vtype == "colorfield"){
				this.inputFrame.css({"padding-left": prefixWidth, "padding-right": suffixWidth});
				this.inputFrame.outerWidth(this.parentwidth - this.arrowwidth);
				if(this.options.suffix){
					this.suffix.css("right", this.arrowwidth+"px");
				}
			}else{
				this.inputFrame.css({"padding-left": prefixWidth, "padding-right": suffixWidth});
				this.inputFrame.outerWidth(this.parentwidth);
			}
			
    		this.framewidth = this.inputFrame.width();
   			
   			//判断是否存在输入框，有输入框，则设置输入框宽度
   			if(this.isInputBox){
   				this.inputtext.outerWidth(this.framewidth);
   			}
		},        

		/**
         * @desc 获取输入框文本值的值
		 * @return String
		 * @example $('XXX').field('getText');
		 */      
        getText: function(){
        	var value = this.inputtext.val() || "";
        	if(value == this.options.valuetip || value == this.options.blanktext){
        		value = "";
        	}
        	return value;
        },
		
		/**
         * @desc 获取输入框的值
         * @return String
		 * @example $('XXX').field('getValue');
         */				
		getValue: function(){
			var value = this.input.val();			
        	if(value == this.options.valuetip){
				value = "";
			}
        	//value = value.replace(/\n\r/g, "<br>&nbsp;&nbsp;");
        	//value = value.replace(/\r\n/g, "<br>&nbsp;&nbsp;");
        	//value = value.replace(/\n/g, "<br>");
        	//value = value.replace(/\t/g, "&nbsp;&nbsp;&nbsp;&nbsp;");
        	//value = value.replace(/ /g, "&nbsp;");
			return value;
		},		
		
		/**
         * @desc  重置输入框值
         * @example $('XXX').field('reset');
         */
		reset: function(){
			this.input.val("");
			if(this.options.valuetip){
				this.inputtext.val(this.options.valuetip).addClass('jazz-field-comp-input-tip');
			}else{
				this.inputtext.val("");
			}
			this.inputview.text("");
		},
		
		/**
         * @desc 设置显示的文本内容
         * @param {value} 设置的显示文本
		 * @example $('XXX').field('setText', text);
         */
        setText: function(text){
			var val = text;
			if(val === "" || val == undefined){ val = ""; }
			//设置输入框中的值
			var valuetip = this.options.valuetip;
			if(valuetip != "" && valuetip == val ){
				this.inputtext.val(val).addClass("jazz-field-comp-input-tip");
			}else{
				this.inputtext.val(val).removeClass("jazz-field-comp-input-tip");
			}
			//设置查看的值
			this._setInputview(val);
        },
		
		/**
         * @desc 设置元素值
         * @param {value} 设置的值
		 * @example $('XXX').field('setValue', value);
         */	
		setValue: function(value) {
			var val = value;
			if(val === "" || val == undefined){ val = ""; }
			this.input.val(val).removeClass("jazz-field-comp-input-tip");
			//设置查看的值
			this._setInputview(val);
		},
		
		/**
         * @desc 设置formpanel的显示条件
		 * @example $('XXX').field('setValue', value);
         */		
        setCondition: function(){
        	if(this.condition){
    			this.formpanelobj._setCondition(this);
    		}
        },
		
		/**
         * @desc 组件验证 验证通过返回 true, 验证未通过返回 false
		 * @example $('XXX').field('verify');
		 * @return boolean
         */		
		verify: function(){
//			var val = this.getText();
//			var state = jazz.doTooltip(this, val, this.options.rule, this.options.msg);
//			this._validateStyle(state);
			var isverify = this.options.isverify ? true : false;
			return isverify;
		}
	});	

});
