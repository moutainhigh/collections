(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',
		         'form/jazz.form.ComboxField'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.autocompletecomboxfield
	 * @description 表单元素的选择填报类。
	 * @constructor
	 * @extends jazz.comboxfield
	 * @example $('XXX').autocompletecomboxfield();
	 */

    $.widget("jazz.autocompletecomboxfield", $.jazz.comboxfield, {
       
        options: /** @lends jazz.autocompletecomboxfield# */ {
			
        	/**
			 *@type String
			 *@desc 组件类型
			 *@default 'autocompletecomboxfield'
			 */        	        	
        	vtype: 'autocompletecomboxfield',
        	
        	/**
			 *@type String
			 *@desc 保存数据类型 'text or 'value'
			 *@default 'text'
			 */            
            datatype: 'text',
        	
    		/**
			 *@type Number
			 *@desc 最小查询的长度 
			 *@default 1
			 */            
            minquerylength: 1
            
        },

		/** @lends jazz.autocompletecomboxfield */

        /**
         * @desc 创建组件
         * @private
         */
        _create: function() { 
        	this.options.editable = true;   //auto可编辑
        	this.options.multiple = false;  //auto不能为多选
        	this.options.isshowblankitem = false;  //autocomplete不支持空白项, 所以将该属性设置成false
        	
        	//创建组件
        	this._super();
        	
        	this.trigger.removeClass("jazz-field-comp-ar");
        },
        
        //重写父类的方法
        //是否对列表过滤，只适用于comboxfield
        _filterable: function(){},
        //加载数据项
        _loaddata: function(){},       
        
        /**
         * @desc 初始化组件
         * @private
         */ 
        _init: function() {
        	this.query = "";

        	this._super();
        },
        
        //覆盖父类方法
        _binddropdown: function(){},
        
		/**
         * @desc 绑定事件
		 * @private
         */         
        _bindEvent: function() {
            this._bindKeyEvents();
            
            var $this = this;
            this.itemsContainer.off("mousedown.comboxfield").on("mousedown.comboxfield", function(e){
            	var t = $(e.target); 
            	if(t.is(".jazz-dropdown-list-item a")){

            		$this._selectedItem(e, t);
    				
    				e.stopPropagation();
                 }
            });            
            
            this._bindEventInputtext();
        },        

        /**
         * @desc 绑定的键盘事件
         * @private
         */
        _bindKeyEvents: function() {
            var $this = this;
            var dataurl = $this.options.dataurl;
            this.flag = true;
            this.inputtext.off('keyup.autocomplete, mousedown.autocomplete').on('keyup.autocomplete, mousedown.autocomplete',function(e) {
            	var keyCode = $.ui.keyCode, key = e.which, shouldSearch = true;
                if(key == keyCode.UP || key == keyCode.LEFT || key == keyCode.DOWN || key == keyCode.RIGHT || key == keyCode.TAB || key == keyCode.SHIFT ||
                   key == keyCode.ENTER || key == keyCode.NUMPAD_ENTER) {
                       shouldSearch = false;
                }
                
                if(shouldSearch) {
                	if(!!$this.timeout) {
                        window.clearTimeout($this.timeout);
                    }
    				$this.timeout = window.setTimeout(function() {
    					var value = $this.getValue();
    					var text = $this.getText();
    					if(dataurl && !$.isArray(dataurl)){
    						if($this.flag){
	    						if(dataurl.indexOf("?") != -1){
	    							dataurl += "&text={text}";
	        					}else{
	        						dataurl += "?text={text}";
	        					}
	    						$this.flag = false;
    						}
    						var url = encodeURI(dataurl.replace('{text}', text));
    						
    						if(!$this.options.cacheflag){
    							url += ("&tty=" + Math.random());
    						}
    						$this.options.dataurl = url;
    					}

    					if($this.options.dataurlparams){
    						$this.options.dataurlparams["text"] = text;
    						$this.options.dataurlparams["value"] = value;			
    					}
			
    					if($this.options.minquerylength == 0){
    						$this._search(text);
    					}else if(text){
    						$this._search(text);
    					}
    					
    				}, $this.options.delay);
                }
            });
        },
        
		/**
         * @desc 生成数据项
		 * @private
         */
        _dataItems: function(f){
	         var data = [], emptyQuery = ($.trim(this.query) === '');
	         if(f){
	            for(var i = 0 ; i < this.dataset.length; i++) {
	    			var option = this.dataset[i], optionLabel = option['text']+"";
	                if(!this.options.casesensitive) {
	                    itemLabel = optionLabel.toLowerCase();
	                }
	                if(emptyQuery && this.options.minquerylength == 0){
						data.push(option);
					}else{
						if((this.filterMatcher(itemLabel, $.trim(this.query))) ){  //&& (this.query.length >= this.options.minquerylength)) {
							data.push(option);
						}
					}
	            }
	    		
	        	this.dataset = data;
        	}
       	
        	this._super();

        	if(f){
        		this._hideDropdown();
        		var len = data.length; 
        		if(len > 0){
        			var $this = this;
        			$.each(this.items, function(i, item){
        				var obj = $(item);
        				if($this.query){
        					var text = obj.html(), re = new RegExp(jazz.escapeRegExp($this.query || ""), 'gi'),
        					highlighedText = text.replace(re, '<span class="jazz-textfield-query">$&</span>');
        					obj.html(highlighedText);
        				}
        			});
        			if(!this.panel.is(":visible") && this.datastatus){
    	        		this._showDropdown(len);
    	        	}        			
        		}
	        	
        	}
        },
        
		//覆盖field中的_editable方法， color组件不可编辑
		_editable: function(){
			this.options.editable = true;
			this._super();
		},        
        
        /**
         * @desc 查询
         * @param {text} 输入的查询条件
         * @private
         */
        _search: function(text) {
        	this.query = this.options.casesensitive ? text : text.toLowerCase();
            if(this.options.dataurl) {
            	if($.isArray(this.options.dataurl)) {
            		this.dataset = this.options.dataurl;
                	this._dataItems(true);
                }else {
                	//ajax接收的参数定义是是否请求新数据
                    this._ajax(!this.options.cacheflag);
                }
            }
        },		
  
		/**
         * @desc 获取输入框的值
         * @return String
		 * @example $('XXX').autocompletecomboxfield('getValue');
         */				
		getValue: function(){
			var value = "";
			if(this.options.datatype=='text'){
				value = this.getText();
			}else{
				var supervalue = this._super();
				if(supervalue || supervalue===0){
					value = supervalue;
				}
			}
			return value;
		},
        
		/**
         * @desc 设置元素值 
         * @param {value} 设置的值
         * @param {callback_function} 设置值后的回调函数
		 * @example $('XXX').autocompletecomboxfield('setValue', value);
         */
		setValue: function(value, callback_function) {
			if(this.options.datatype == 'text'){
				this.setText(value);
				this._setValueCallback(callback_function);
			}else{
				this._super(value, callback_function);
			}
		}        
    });
});
