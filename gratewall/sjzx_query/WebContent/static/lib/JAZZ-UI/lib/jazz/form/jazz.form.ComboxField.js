(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',
		         'form/jazz.form.DropdownField'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	
	/**
	 * @version 1.0
	 * @name jazz.comboxfield
	 * @description 表单元素的选择填报类。
	 * @constructor
	 * @extends jazz.dropdownfield
	 * @requires
	 * @example $('XXX').comboxfield();
	 */

    $.widget("jazz.comboxfield", $.jazz.dropdownfield, {
        
        options: /** @lends jazz.comboxfield# */ {
			
        	/**
			 *@type String
			 *@desc 组件类型
			 *@default 'comboxfield'
			 */        	        	
        	vtype: 'comboxfield',
        	
        	/**
        	 *@type boolean
        	 *@desc  缓存开关 true 使用缓存  false 不使用缓存
        	 *@default true 使用缓存
        	 */
        	cacheflag: true,        	
        	
			/**
			 *@type String
			 *@desc 空白项，显示的默认文本内容 
			 *@default '请选择……'
			 */	    		
    		blanktext: jazz.config.fieldBlankText,        	
        	
        	/**
        	 *@type Boolean
        	 *@desc 是否区分字母的大小写过滤  true 区分大小写
        	 *@default false
        	 */             
        	casesensitive: false,

            /**
             *@type Function
             *@desc 自定义combox下拉列表展现内容
             *@default null
             *@example
             *datarender: function(data){var a = "<div>"+data["text"]+"***"+data["value"]+"</div>" return a;}
             */
            datarender: null,
            
        	/**
        	 *@type Boolean
        	 *@desc 是否可编辑 true可编辑 false不可编辑 
        	 *@default true
        	 */
        	editable: false,        

            /**
			 *@type Boolean
			 *@desc 是否进行过滤
			 *@default false
			 */            
            filterable: false,
            
			/**
			 *@type String
			 *@desc 过滤模式  startsWith  contains   endsWith
			 *@default 'contains'
			 */            
            filtermatchmode: 'contains',
            
			/**
			 *@type Function
			 *@desc 自定义查询过滤函数
			 *@default null
			 *@private
			 */                
            filterfunction: null,   //目前没有实现， 以后实现 filtermatchmode: custom  0.5 以后版本在加
			
			/**
			 *@type Boolean
			 *@desc 是否显示空白项
			 *@default true
			 *@private
			 */              
            isshowblankitem: true,
            
            /**
			 *@type Boolean
			 *@desc 是否多选 true是复选，false是单选
			 *@default false
			 */            
            multiple: false,
            
            /**
			 *@type Number
			 *@desc 默认显示记录的所引值
			 *@default null
			 */            
            selectedindex: null,
            
            /**
			 *@type Object
			 *@desc 将默认text value 转换成所需要的
			 *@default simpleData: { text: "text",  value: "value" }
			 */             
            simpledata: {
            	text: "text",
            	value: "value"
            },
			
            // callbacks
			
    		/**
			 *@desc 鼠标焦点进入输入框时触发
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").comboxfield("option", "enter", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("comboxfieldenter",function(event, ui){  <br/>} <br/>});
			 */			
			enter: null,
    		
    		/**
			 *@desc 鼠标焦点离开输入框时触发
			 *@param {event} 事件
			 *@param {ui.newValue} 新修改的值 
			 *@param {ui.newText} 新修改的文本
			 *@param {ui.oldValue} 旧值
			 *@param {ui.oldText}  旧文本
			 *@event
			 *@example
			 *<br/>$("XXX").comboxfield("option", "change", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("comboxfieldchange",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… change="XXX()"></div> 或 <div…… change="XXX"></div>
			 */
			change: null,
    		
    		/**
			 *@desc 当选择了某项时，触发itemselect事件  配合dataurl属性使用
			 *@param {event} 事件
			 *@param {ui.value} 选中项的值
			 *@param {ui.text} 选中项的文本 
			 *@event
			 *@example
			 *<br/>$("XXX").comboxfield("option", "itemselect", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("comboxfielditemselect",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… itemselect="XXX()"></div> 或 <div…… itemselect="XXX"></div>
			 */
			itemselect: null
            
        },

		/** @lends jazz.comboxfield */
	
        /**
         * @desc 创建组件
         * @private
         */ 
        _create: function() {
        	//创建组件
        	this._super();
        	//dropdownfield中定义
        	//this._createDownpanel();
        }, 
        
        /**
         * @desc 初始化组件
         * @private
         */ 
        _init: function() {
        	//定义展现的数据集合
        	this.blankitem = [], this.dataset = [], this.datakeyvalue = {};

        	//是否可以对列表项进行过滤
        	this._filterable();
        	
        	//设置过滤数据的匹配机制
            this._setupFilterMatcher();

            //是否显示空白项
            this._isshowblankitem();
            
        	//加载数据
        	this._loaddata();
        	
        	this._super();
        	
        	this._bindEvent();
        	
        	this._validator();
        },
        
		/**
         * @desc 绑定事件
		 * @private
         */         
        _bindEvent: function() {
            var $this = this;

            this.itemsContainer.off("mousedown.comboxfield").on("mousedown.comboxfield", function(e){
            	var t = $(e.target); 
            	if(t.is(".jazz-dropdown-list-item a")){
            		
            		$this._selectedItem(e, t);
    				
    				e.stopPropagation();
                 }
            });

            this._bindEventFilterInput();
            
            this._bindEventInputtext();
            
//            //绑定列表  
//        	if(!this.options.disabled){
//        		this._binddropdown(this.dataset.length);  
//        	}
        },
        
        /**
         * @desc 绑定过滤查询事件
		 * @private
		 */
        _bindEventFilterInput: function(){
        	var $this = this;
            if(this.options.filterable) {
                this.filterInput.off('keyup.comboxfield').on('keyup.comboxfield', function() {
                    $this._filter($(this).val());
                });
            }     	
        },
        
        /**
         * @desc 绑定输入框事件
		 * @private
		 */
        _bindEventInputtext: function(){
        	var $this = this;
        	this.inputtext.off("focus.comboxfield blur.comboxfield").on("focus.comboxfield", function(){
        		if($(this).val()==$this.options.valuetip){
					$(this).val("");
					$(this).removeClass('jazz-field-comp-input-tip');
				}
        	}).on("blur.comboxfield", function(){
        		if($(this).val()=="" && !!$this.options.valuetip){
					$(this).val($this.options.valuetip);
					$(this).addClass('jazz-field-comp-input-tip');
				}
        	});        	
        },
      
        /**
         * @desc 处理设置值的回调函数
         * @param {value} 值
         * @param {callback_function} 回调函数
		 * @private
         */ 
        _setData: function(value, callback_function){
        	this._oldValue = this.getValue(), this._oldText = this.getText();
        	var text = "";
			if(this.options.multiple){
				var arr = value.split(",");
				for(var i=0, len=arr.length; i<len; i++){
					if(i==0){
						text = this._getDataText(arr[i]);
					}else{
						text = text + "," + this._getDataText(arr[i]);
					}
				}
			}else{
				text = this._getDataText(value);
			}
			if(text){
				this._setTextValue(text, value);
				this._newText = text;
			}
			this._newValue = value;
			this._setValueCallback(callback_function);
        },
        
        /**
         * @desc 选择列表项
		 * @private
		 */        
        _selectedItem: function(e, t){
            var ui;
            t = t.parent();
            this._newText = t.data('text'), this._newValue = t.data('value');
            ui = {text: this._newText, value: this._newValue};
            
        	this.inputtext.removeClass("jazz-field-comp-input-tip");
        	
        	var multiple = this.options.multiple;
			if(multiple){
				if(t.hasClass("jazz-state-highlight")){
					t.removeClass("jazz-state-highlight");
				}else{
					t.addClass("jazz-state-highlight");
				}
				
				this._oldValue = this.getValue(), this._oldText = this.getText();
				var value = this._getSelectedData();
				//设置鼠标选中记录时的值
				this._setSelectedValue(value);
				this._event("itemselect", e, ui);
			}else{
				this._oldValue = this.getValue(), this._oldText = this.getText();
				//设置鼠标选中记录时的值
				this._setSelectedValue(t.data('value'));
                this._hideDropdown();
                
                this._event("itemselect", e, ui);
            	
            	this._event("change", e, this._changeData());
			}        	
        },
        
        /**
         * @desc 处理设置值的回调函数
         * @param {data} 返回的结果集
         * @param {value} 值
         * @param {callback_function} 回调函数
		 * @private
         */        
		_setValue: function(data, value, callback_function){
            if(jazz.isArray(data)){       	
            	this.dataset = data;
            	this._dataItems();
            	this._setData(value, callback_function);           
        	}
		},
 
        /**
         * @desc ajax生成数据的回调
		 * @private
         */
        _callback: function(data, callbackfunction) {
            if(jazz.isArray(data)){           	
            	this.dataset = data;
            	this._dataItems(true);  //auto组件需要    _dataItems(true)
        	}
            if($.isFunction(callbackfunction)){
            	callbackfunction.call(this);
            }
        },
        
		/**
         * @desc 包含内容过滤
         * @param {value} 实际值
         * @param {filter} 过滤值
		 * @private
         */         
        _containsFilter: function(value, filter) {
            return value.indexOf(filter) !== -1;
        },

		/**
         * @desc 生成数据项
		 * @private
         */
        _dataItems: function() {
        	var rule = this.options.rule;
        	this.itemsContainer.empty();
        	var liset = []; this.valueset = [];
            for(var i = 0; i < this.dataset.length; i++) {
                var option = this.dataset[i],
                value = option[this.options.simpledata.value], text = option[this.options.simpledata.text],
                content = text, datarender = this.options.datarender;
                this.datakeyvalue[value] = text+"", this.valueset[i] = value;
                
                if(datarender){
                	if(!$.isFunction(datarender)){
        				if(/\(/.test(datarender)){
        					datarender = datarender.split("(")[0];
        				}
        				datarender = eval(datarender+"");
                	}
                	content = datarender.call(this, option);
                }
                liset.push('<li data-value="' + value + '" data-text="' + text + '" class="jazz-dropdown-list-item" ><a href="javascript:;">' + content + '</a></li>');
            }
           
        	if(this.blankitem.length > 0 && (rule+"").indexOf("must") < 0){
        		this.itemsContainer.append(this.blankitemdom);
                //合并全部数据
                this.dataset = this.blankitem.concat(this.dataset);
        	}            
            
            if(liset.length>0){
            	this.itemsContainer.append(liset.join(""));
            }
            
            if(this.options.selectedindex){
            	var _value = this.valueset[this.options.selectedindex];
            	this.setValue(_value);
            }
            
            //this.items所有<li>元素的对象集合
            this.items = this.itemsContainer.children('.jazz-dropdown-list-item');
            
            this.datastatus = true;
            
        },
        
		/**
         * @desc 从后匹配过滤
         * @param {value} 实际值
         * @param {filter} 过滤值
		 * @private
         */  
        _endsWithFilter: function(value, filter) {
            return value.indexOf(filter, value.length - filter.length) !== -1;
        },

		/**
         * @desc 过滤
         * @param {value} 过滤值
		 * @private
         */        
        _filter: function(value) {
//            this.initialHeight = this.initialHeight || this.itemsWrapper.height();   
            var filterValue = this.options.casesensitive ? $.trim(value) : $.trim(value).toLowerCase();
            for(var i = 0; i < this.dataset.length; i++) {
                var option = this.dataset[i],
                itemLabel = this.options.casesensitive ? option[this.options.simpledata.text] : option[this.options.simpledata.text].toLowerCase(),
                item = this.items.eq(i);
                if(this.filterMatcher(itemLabel, filterValue)){
                    item.show();
            	}else{
                	item.hide();
                }
            }
//            var h = this.itemsContainer.height();
//            if(h < this.initialHeight) {
//                this.itemsWrapper.css('height', 'auto');
//                this.panel.height('auto');
//            }else {
//                this.itemsWrapper.height(this.initialHeight);
//            }
        },
        
        /**
         * @desc 是否可以对查询的结果过滤
		 * @private
         */
        _filterable: function(){
            //查询
            if(this.options.filterable) {
            	if(this.filterContainer){
            		this.filterContainer.remove();
            	}
                this.filterContainer = $('<div class="jazz-dropdown-filter"></div>').prependTo(this.panel);
                this.filterInput = $('<input type="text" autocomplete="off" class="jazz-field-comp-input">').appendTo(this.filterContainer);
                this.filterContainer.append('<span class="jazz-dropdown-search"></span>');
            }        	
        },

        /**
         * @desc 获取当前选中的列表项
		 * @private
         */        
        _getActiveItem: function() {
            return this.items.filter('.jazz-state-highlight');
        },
        
        /**
         * @desc 根据传入的值获得结果集中对应的文本内容
         * @param {value} 要查询的值
         * @private
         * @returns String
         */        
        _getDataText: function(value){
        	return this.datakeyvalue[value] || "";
        },
            
        /**
         * @desc 根据当前选中的条目，组织显示字符串
         * @private
         * @returns String
         */
        _getSelectedData: function(){
        	var items = this.itemsContainer.children(".jazz-state-highlight"); 
        	var value = "";
    		$.each(items, function(i, item){
    			var obj = $(item);
    			if(i == 0){
    				value = obj.data("value");
    			}else{
    				value = value + "," + obj.data("value");
    			}
    		});        	
        	
        	return value;
        },

		/**
         * @desc 是否显示空白的数据项
		 * @private
         */
        _isshowblankitem: function(){
        	this.blankitem = [], blanktext = this.options.blanktext;
        	if(this.options.isshowblankitem && !this.options.multiple){
        		var str = '{'+this.options.simpledata.text+': "'+blanktext+'", '+this.options.simpledata.value+': ""}';
        		str = eval('('+str+')');
        		this.blankitem = [str];
        		this.blankitemdom = $('<li data-value="" data-text="' + blanktext + '" class="jazz-dropdown-list-item" ><a href="javascrript:;">' + (blanktext || ' ') + '</a></li>');
        	}
        },
        
        /**
         * @desc 加载数据
		 * @private
         */
        _loaddata: function(){
        	/**
        	 * 针对个别浏览器,包括不限于IE7-
        	 * dataurl数组字符串未转换的情况
        	 */
        	if(typeof(this.options.dataurl) == 'string' && /^\s*[\[|{](.*)[\]|}]\s*$/.test(this.options.dataurl)){
        		//转换成对象
        		this.options.dataurl = jazz.stringToJson(this.options.dataurl);
        	}
        	
            if(jazz.isArray(this.options.dataurl)){          	
            	this.dataset = this.options.dataurl;
            	this._dataItems();
			}else{
				this._ajax(!this.options.cacheflag);
			}
        },
               
		/**
         * @desc 设置过滤的匹配机制
		 * @private
         */        
        _setupFilterMatcher: function() {
            this.filterMatchers = {
                'startsWith': this._startsWithFilter,
                'contains': this._containsFilter,
                'endsWith': this._endsWithFilter,
                'custom': this.options.filterfunction
            };

            this.filterMatcher = this.filterMatchers[this.options.filtermatchmode];
        },

		/**
         * @desc 从前匹配过滤
         * @param {value} 实际值
         * @param {filter} 过滤值 
		 * @private
         */          
        _startsWithFilter: function(value, filter) {
            return value.indexOf(filter) === 0;
        },

        /**
         * @desc 设置组件属性的值
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private 
         */
        _setOption: function(key, value){
        	switch(key){
	        	case 'selectedindex':
	        		if(value){
	                    if(value){
	                    	this.options.selectedindex = value;
	                    	var _value = this.valueset[this.options.selectedindex];
	                    	this.setValue(_value);
	                    }
	        		}
	        		break;	 
        	}
        	this._super(key, value);
        },
        
		/**
         * @desc 设置鼠标选中记录时的值
         * @param {value} 选中的值 
		 * @private
         */
        _setSelectedValue: function(value){
        	var text = "", val = (value+"").split(",");
        	for(var i=0, len=val.length; i<len; i++){
        		if(i==0){
        			text = this._getDataText(val[i]);        			
        		}else{
        			text = text + "," + this._getDataText(val[i]);
        		}
        	}
        	this._setTextValue(text, value);
        	
        	this.setCondition();
        },
        
		/**
         * @desc 设置鼠标选中记录时的值
         * @param {text}  文本 
         * @param {value} 值 
		 * @private
         */        
        _setTextValue: function(text, value){
        	this.input.val(value);
        	if(value==="" && this.options.blanktext){
        		text = this.options.blanktext;
        	}
        	this.setText(text);
        },

        /**
         * @desc 更新下拉列表中当前选中的样式
         * @private
         */
        _updateSelectItemStyle: function(){
        	var value = this.getValue();
        	items = this.itemsContainer.children();
    		items.removeClass("jazz-state-highlight");
    		if(this.options.multiple){
        		$.each(items, function(i, item){
        			var obj = $(item);
        			if(obj.data('value') !=='' && (value+"").indexOf(obj.data('value')+"") > -1){
        				obj.addClass("jazz-state-highlight"); 
        			}
        		});
    		}else{
        		$.each(items, function(i, item){
        			var obj = $(item);
        			if(obj.data('value')+"" == value){
        				obj.addClass("jazz-state-highlight"); 
        			}
        		});    			
    		}
        },
                
        /**
         * @desc 动态添加组件下拉框中的内容
         * @param {text} 文本内容
         * @param {value} 文本对应的数值
		 * @example $('div_id').comboxfield('addOption', 'text', 'value');
         */           
        addOption: function(text, value) {
            var item = $('<li data-value="' + value 
            		+ '" data-text="' + text 
            		+ '" class="jazz-dropdown-list-item"><a href="javascript:;">' 
            		+ text + '</a></li>');
            item.appendTo(this.itemsContainer);
            var str = '{"'+this.options.simpledata.text+'": "'+text+'", "'+this.options.simpledata.value+'": "'+value+'"}';
    		str = eval('('+str+')');
            this.dataset.push(str);
            this.datakeyvalue[value] = text;
        },
        
		/**
         * @desc 获取当前选中状态对象的索引
		 * @param {value} 对象值
		 * @return 选中对象的索引
		 * @private 
		 */  
        getDataIndex: function(value){
        	for(var i=0, len=this.dataset.length; i<len; i++){
        		if(this.dataset[i][this.options.simpledata.value] == value){
        			return i;
        		}
        	}
        },        
        
		/**
         * @desc 获取当前选中状态对象的值
		 * @return 所有选中的值
		 * @example $('XXX').comboxfield('getValue');
		 */
        getValue: function() {
        	var value = "";
        	if(this.options.editable == true){
        		var text = this.inputtext.val();
        		if(text != this.options.valuetip && text != this.options.blanktext){
	                for(var i = 0; i < this.dataset.length; i++) {
	                    var option = this.dataset[i], label = option[this.options.simpledata.text];
	                    text = $.trim(text);
	                    if(label===text){
	                    	text = option[this.options.simpledata.value];
	                    	break;
	                    }
	                }
	                value = text;
        		}else{
        			value = "";
        		}
        	}else{
        		value = this._super();
        	}
        	return value;
        },
        
        /**
         * @desc 动态隐藏组件下拉框中的内容
         * @param {value} 文本对应的数值
		 * @example $('XXX').comboxfield('hideOption','value');
         */           
        hideOption: function(value) {
        	this.itemsContainer.children("[data-value='"+value+"']").hide();
        },
     
        /**
         * @desc 动态添加组件下拉框中的内容
         * @param {data} 静态数据
         * @param {callbackfunction} 回调函数
		 * @example $('XXX').comboxfield('reload', data, function(){ });
         */           
        reload: function(data, callbackfunction) {
        	this.reset();
        	if(data && jazz.isArray(data)){
        		this._callback(data, callbackfunction);  		
        	}else{
        		var dataurl = this.options.dataurl;
				if(dataurl) {
					if(jazz.isArray(dataurl)) {
						this._callback(dataurl, callbackfunction);
					}else {
						var $this = this;
						this._ajax(!this.options.cacheflag, function(data){
							$this._callback(data, callbackfunction);                    	
						});
					}
				}        		
        	}
        },

        /**
         * @desc 动态移除组件下拉框中的内容
         * @param {value} 文本对应的数值
         */
        removeOption: function(value) {
        	for(var i=0,len=this.dataset.length; i<len; i++){
        		if(this.dataset[i][this.options.simpledata.value] == value){
        			this.dataset.splice(i, 1);
        			break;
        		}
        	}
        	this.itemsContainer.children("[data-value='"+value+"']").remove();
        },

        /**
         * @desc 动态显示组件下拉框中的内容
         * @param {value} 文本对应的数值
		 * @example $('XXX').comboxfield('showOption','value');
         */           
        showOption: function(value) {
        	this.itemsContainer.children("[data-value='"+value+"']").show();
        },

		/**
         * @desc 设置当前状态对象选中
		 * @param {value} 选中对象的值
		 * @param {callback_function} 设置值后的回调函数
		 * @example $('XXX').comboxfield('setValue', '2', callback_function);
		 */
		setValue: function(value, callback_function) {
			if(value || value===0){
				if(typeof(value) == "object" && jazz.isArray(value)){
					this.dataset = value;
					//是存在空白项
					this._isshowblankitem();
					//数据项
					this._dataItems();
					this.reset();
					this._setValueCallback(callback_function);
				}else{
					value = value+"";
					//判断结果集是否存在
					if(this.dataset.length > 0){
						var text = "";
						if(this.options.multiple){
							var arr = value.split(",");
							for(var i=0, len=arr.length; i<len; i++){
								if(i==0){
									text = this._getDataText(arr[i]);
								}else{
									text = text + "," + this._getDataText(arr[i]);
								}
							}
						}else{
							text = this._getDataText(value);
						}
						
						if(text){
							this._setTextValue(text, value);
						}				
						this._setValueCallback(callback_function);
						
					//结果集不存在，查询结果后在设置值
					}else{
						if(this.options.islazydata || this.options.vtype == "autocompletecomboxfield"){					
							if(this.options.dataurl) {
								if(jazz.isArray(this.options.dataurl)) {
									this._setValue(this.options.dataurl, value, callback_function);
								}else {
									var $this = this;
									this._ajax(!this.options.cacheflag, function(data){
										$this._setValue(data, value, callback_function);	                    	
									});
								}
							}													
						}else{
							this._setValueData(value, callback_function);
						}
					}
				}
			}else{
				this.reset();
			}
		}	

    });
    
});