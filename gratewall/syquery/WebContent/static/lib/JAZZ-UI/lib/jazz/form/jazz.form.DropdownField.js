(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',
		         'form/jazz.form.IconField'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.dropdownfield
	 * @description 选择下拉组件的基类。
	 * @constructor
	 * @extends jazz.iconfield
	 */
    $.widget("jazz.dropdownfield", $.jazz.iconfield, {
    	
    	options: /** @lends jazz.dropdownfield# */ {

        	/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default textfield
    		 */
			vtype: 'dropdownfield',
    		
			/**
			 *@type object
			 *@desc 下拉组件的容器,提供给可编辑表格使用
			 */
			appendTo: null,    		
    		
        	/**
			 *@type Number
			 *@desc 滚动条显示高度
			 *@default 200
			 */         
            downheight: 200,
            
            /**
             *@type String
             *@desc 请求数据的url地址
             *@default null
             */      
            dataurl: null,
            
            /**
             *@type Object
             *@desc 请求数据地址对应的参数 {}
             *@default null
             *@example {"key1":"value1", "key2": "value2"}
             */            
            dataurlparams: null,
            
        	/**
        	 *@type Boolean
        	 *@desc 是否可编辑 true可编辑 false不可编辑 
        	 *@default true
        	 */
        	editable: true,  
        	
        	/**
        	 *@type Boolean
        	 *@desc 是否懒加载数据(是否在初始组件时加载数据)， true 初始化时不加载数据 false初始化时加载数据
        	 *@default true
        	 */	
        	islazydata: false,        	
            
			/**
			 *@type number
			 *@desc 组件下拉框的最大宽度
			 *@default -1
			 */			
			maxdownwidth: -1,
			
			/**
			 *@type number
			 *@desc 组件下拉框的最小宽度
			 *@default -1
			 */
			mindownwidth: -1
    	},
    	
    	/** @lends jazz.dropdownfield */
    	
        /**
         * @desc 创建组件
         * @private
         */
        _create: function() {
        	//创建组件
        	this._super();
        	this.trigger = $('<span id="'+this.compId+'_ar" class="jazz-field-icon jazz-field-comp-ar"></span>').appendTo(this.parent);
        	this.datastatus = false;
        
        	this._createDownpanel();
        },

        /**
         * @desc 初始化组件
         * @private
         */
		_init: function(){
			this._super();
		},

		/**
         * @desc 调整下拉列表的展现位置
		 * @private
         */
        _alignPanel: function() {
        	var vtype = this.options.vtype;
        	if(vtype != "datefield" && vtype != 'colorfield'){
        		var parentwidth = this.parent.width();
        		if(this.options.maxdownwidth > 0 && this.options.maxdownwidth > parentwidth){
        			parentwidth = this.options.maxdownwidth;
        		}
        		
        		if(this.options.mindownwidth > 0 && this.options.mindownwidth < parentwidth){
        			parentwidth = this.options.mindownwidth;
        		}
        		this.panel.css("width",parentwidth);
        	}
            this.panel.css({'z-index': ++jazz.zindex})
                .position({
                	my: 'left top',
                	at: 'left bottom',
                	of: this.parent,
                	collision:"flipfit"
            });      	
        },

		/**
         * @desc 绑定事件
		 * @private
         */         
        _binddropdown: function() {
            var $this = this;
            var vtype = this.options.vtype;
            this.parent.off('mousedown.dropdown').on('mousedown.dropdown', function(e) {
            	$this._event("enter", e);
            	var target = e.target, $target = $(target);
			    if($target.is('span') || target==$this.inputtext.get(0)){
			    	if(vtype == 'comboxfield'){
						$this._updateSelectItemStyle();
					}
			    	var b = true;
			    	if(jazz.isArray($this.dataset)){
			    		b = $this.dataset.length > 0 ? true : false;
			    	}
			    	$this._showDropdown(b);
			    }

			    if($.browser.msie){
				    //修复IE下光标跳到最开始的问题
				    if($this.inputtext.get(0).createTextRange()){
	            		var range = $this.inputtext.get(0).createTextRange();
						range.collapse(false);
						range.select();
	            	}
			    }
			    
			    //e.stopPropagation();
            });
        },
        
		/**
         * @desc 生成panel
		 * @private
         */				
		_createDownpanel: function(){
			var name = this.options.name;
			
            var vtype = this.options.vtype;
            var zindex = ++jazz.config.zindex + 1000;
            //dropdownpanel_ 前缀不要修改, jazz.Event.js中有引用
			this.panel = $('<div name="dropdownpanel_'+name+'" type="'+vtype+'"  class="jazz-dropdown-panel jazz-widget-content jazz-helper-hidden jazz-'+vtype+'-panel"/>').appendTo(this.options.appendTo || document.body);
            this.panel.css('z-index', zindex);
			this.itemsWrapper = $('<div class="jazz-dropdown-wrapper" />').appendTo(this.panel);
			if(vtype== 'comboxtreefield'){
				//$('<div name="reset_comboxtree_'+name+'" class="jazz-comboxtree-reset">请选择</div>').appendTo(this.panel);
				this.resetcomboxtree = $("div[name='reset_comboxtree_"+name+"']");
				this.ulId = "zTree_"+name;
				this.itemsContainer = $('<ul id="'+this.ulId+'" class="ztree" style="margin-top:0; width:auto;"></ul>').appendTo(this.itemsWrapper);
			}else if(vtype== 'datefield' || vtype== 'colorfield'){
				this.itemsContainer = this.itemsWrapper;
			}else{
				this.itemsContainer = $('<ul class="jazz-dropdown-list jazz-helper-reset"></ul>').appendTo(this.itemsWrapper);
			}
			//临时处理
			this.panel.off("mousedown").on("mousedown",function(event){
        		//防止this.panel事件冒泡到document上，
        		event.stopPropagation();
        	});
		},
		
        /**
         * @desc 前后修改数据的变化
		 * @private
         */
        _changeData: function(){
        	var _change0 = function(v){
        		if(v===0){return v;}else{return v || "";};
        	};
        	var ui = {oldValue: _change0(this._oldValue), oldText: _change0(this._oldText),
        			  newValue: _change0(this._newValue), newText: _change0(this._newText) };
        	_change0 = null;
        	return ui;
        },
        
        /**
         * @desc 设置组件是否不可用
		 * @private
         */        
        _disabled: function(){
        	this._super();
        	if(this.options.disabled == true || this.options.disabled == "disabled"){
        		this.parent.off('mousedown.dropdown');
        	}else{
        		this._binddropdown();
        	}
        },
		
		/**
         * @desc 计算下拉列表查询框的宽度
		 * @private
         */
        _dimensionList: function() {
            if(this.options.filterable) {
                this.filterInput.width(this.filterContainer.width() - 20);
            }
        },

		/**
         * @desc 生成提示信息panel
		 * @private
         */				
		_hideDropdown: function(){
			this.panel.hide();
		},

		/**
         * @desc 列表框是否显示滚动条
		 * @private
         */        
        _itemScroll: function(){
        	 //当下拉组件的列表高度大于设定的高度时，显示滚动条
            var scrollh = this.options.downheight || 200;
            scrollh = parseInt(scrollh);
            
            if(this.panel.height() > scrollh) {
                this.itemsWrapper.outerHeight(scrollh);
                this.panel.height(scrollh);
            }      	
        },	
        
        /**
         * @desc 生成数据项
         * @param {callback_function} 回调函数
		 * @private
         */
		_setValueCallback: function(callback_function) {
			if($.isFunction(callback_function)){
				callback_function.call(this,null,this._changeData());
			}            
        },        
		
		/**
         * @desc  设置元素值
		 * @param {value} 设置的值
		 * @param {callback_function} 回调函数
		 * @private
         */
		_setValueData: function(value, callback_function){
			var $this = this;
			var i = 1, delay = 100, delaycount = $this.options.delaycount;
			var timeout = setInterval(function(){
				var status = $this.datastatus;
				if(status){
					if(value != $this.getValue()){
						$this.setText("");
					}
					$this._setData(value, callback_function);
				}
				i++;
				if(status || delay*i > delaycount){
					clearInterval(timeout);
				}
			}, delay);
		},        
        
		/**
         * @desc 是否显示下拉列表
         * @param {b} 判断是否隐藏 true 显示
		 * @private
         */				
		_showDropdown: function(b){
			if(b){
				this.panel.css({position:"absolute",left:"0", top:"0"});
				this._itemScroll();
				this.panel.show();
				this._alignPanel();
				if(this.options.vtype == 'comboxfield'){
					this._dimensionList();
				}   
			}else{
				this.panel.hide();
			}
		}      
        
    });
    
});