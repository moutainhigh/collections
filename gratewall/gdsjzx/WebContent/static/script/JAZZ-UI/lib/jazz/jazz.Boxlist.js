(function($) {
	/**
	 * @version 0.5
	 * @name jazz.boxlist
	 * @description 表单元素的选择填报类。
	 * @constructor
	 * @extends jazz.field
	 * @requires
	 * @example $('input_id').boxlist();
	 */
    $.widget("jazz.boxlist", $.jazz.boxComponent, {
       
        options: /** @lends jazz.boxlist# */ { 
        	
        	/**
			 *@type String
			 *@desc 组件类型
			 *@default 'boxlist'
			 */	
        	vtype: 'boxlist',

        	/**
			 *@type Number
			 *@desc 组件的宽度
			 *@default 130
			 */        	
        	width:150,
        	
        	/**
			 *@type Number
			 *@desc 组件的高度
			 *@default 180
			 */        	
        	height:180,
       
            /**
			 *@type String或Json
			 *@desc 数据链接或者初始化数据 {"data":[{"text":"11","val":"22"},{"text":"11","val":"22"}]}
			 *@default null
			 */      
            dataurl: null,

            /**
			 *@type Object
			 *@desc 参数
			 *@default null
			 */            
            params: null,            
            
            
			/**
			 *@type Boolean
			 *@desc 选择多条记录  true多选  false单选
			 *@default true
			 */            
            multiple: false,
            
             
			/**
			 *@desc 用户自定义选项行结构
			 *@param {event}  事件
			 *@param {data}  选项数据	
			 *@return 用户自定义li内部div结构  如：'<img src="box1.png"/>'
			 *@event
			 *@example
			 *<br/>$("#input_id").boxlist({"option","datarender"，function( data ){  <br/>} <br/>});
			 */		
            datarender: null,
            
             
			/**
			 *@desc 点击事件
			 *@param {event} 事件
			 *@param {ui} 	选中项数据	 
			 *@event
			 *@example
			 *<br/>$("#input_id").boxlist({"option","click"，function( event, ui ){  <br/>} <br/>});
			 */		
            click: null,
            
             
			/**
			 *@desc 改变事件
			 *@param {event} 事件
			 *@param {ui.newObject} 	当前选中项数据
		     *@param {ui.oldObject}  之前选中项数据		 	
			 *@event
			 *@example
			 *<br/>$("#input_id").boxlist({"option","change"，function( event, ui ){  <br/>} <br/>});
			 */		
            change:null,
             
			/**
			 *@desc 取消事件
			 *@param {event} 事件
			 *@param {ui} 	取消项数据		 
			 *@event
			 *@example
			 *<br/>$("#input_id").boxlist({"option","unclick"，function( event, ui ){  <br/>} <br/>});
			 */		
            unclick: null,
              
			/**
			 *@desc 双击事件
			 *@param {event} 事件
			 *@param {ui} 	选中项数据	 
			 *@event
			 *@example
			 *<br/>$("#input_id").boxlist({"option", "dblclick", function( event, ui ){  <br/>} <br/>});
			**/
            dblclick: null
        },

        /** @lends jazz.comboxfield */
        /**
         * @desc 创建组件
         * @private
         */      
        _create: function() {
        	this._super();
        	
        	this.element.addClass("jazz-boxlist-ul jazz-boxlist-content");
			
        	this._width();
			
			this._height();
            
            this.listContainer = $('<ul class="jazz-listbox-list"></ul>').appendTo(this.element);
        },
        
         /**
         * @desc 初始化组件
         * @private
         */     
         _init: function(){
        	 //加载数据
        	 if(typeof(this.options.dataurl) == 'object'){
            	this._callback('1',this);
            }else if(!!this.options.dataurl){
            
            	this._ajax();
            }else{
            	this._callback({}, this);
            }
        },
        
        /**
         * @desc ajax请求函数
         * @return 返回请求响应的数据
         * @private
         */      
        _ajax: function(){
             param = {
        		url: this.options.dataurl,
        		params: this.options.params,
	        	callback: this._callback  //回调函数
            };
        	$.DataAdapter.submit(param, this);        	
        }, 
        
        /**
         * @desc ajax请求回调函数
         * @params {data} ajax请求返回的值
         * @params {sourceThis} 当前类对象
         * @private
         */
        _callback: function (data, sourceThis){
        	var jsonData = null;
        	var $this = null;
            if(data == '1'){
	            jsonData = this.options.dataurl;  
	            $this = sourceThis;
            }else{
            	jsonData =data ;
            	$this = sourceThis;
            }
            $this.arrayOption=[];	
			if(!!jsonData && !!jsonData.data){
				$this.arrayOption = jsonData.data;
			}
			
			var optHtml = '';
			var render = '';
            for(var i = 0; i < $this.arrayOption.length; i++) {
                var choice = $this.arrayOption[i];
                var _text = $.trim(choice.text);
                if(!choice.text) _text = ''; 
			
                if(!!$this.options.datarender){
	                render = eval($this.options.datarender).call(this,choice);
					optHtml += '<li class="jazz-boxlist-item jazz-boxlist-li">' + render + '</li>';
                }else{
					optHtml += '<li class="jazz-boxlist-item jazz-boxlist-li">' + _text + '</li>';
				}      
            }
			
            $this.listContainer.append(optHtml);
            $this.items = $this.listContainer.find('.jazz-boxlist-item');
            $this._bindEvents();
        },    
                
		/**
         * @desc 绑定事件
		 * @private
         */        
        _bindEvents: function() {
            var time = null;
            var $this=this;
            
            this.element.off()
            .on('dblclick.boxlist', function(event) {   
				var target = event.target;
				var $target=$(target);
	        	if($target.is("li")){  
					//清除延时触发的事件
					clearTimeout(time);
					
					var item=$target;
					if($this.options.multiple){
						item=$this._clickMultiple(event, item);
						$this._trigger('dblclick',null, item); 
					}else{         		
						item=$this._clickSingle(event, item);
						$this._trigger('dblclick',null, item); 
					}
				}
            })           
            .on('click.boxlist', function(event) {
	          	var target = event.target;
				var $target=$(target);
	        	if($target.is("li")){  
		            //清除延时触发的事件
		            clearTimeout(time);
					
					var item=$target;
					//单击事件延时300ms触发
					time = setTimeout(function(){
						if($this.options.multiple){
							item=$this._clickMultiple(event, item);
							$this._trigger('click',null, item);
						}else{         		
							item=$this._clickSingle(event, item);
							$this._trigger('click',null, item);
						}
					}, 200);
				}
            });
        },
           
		/**
         * @desc 点击选择一条记录
		 * @params {event} click事件对象
		 * @params {item} 触发click事件的元素
		 * @return 选中项数据
		 * @private	
         */        
        _clickSingle: function(event, item) {
            var selectedItem = this.items.filter('.jazz-boxlist-highlight');
			var itemIndex=item.index();
			var	selectedIndex=selectedItem.index();
            if(itemIndex !== selectedIndex) {
                if(selectedItem.length) {
                    this._unselectItem(selectedItem);
                }
                this._selectItem(item);

                //触发change事件

                if(selectedItem.length){
                	this._changeTrigger(selectedItem,item);
                }else{
                	this._changeTrigger(null,item);
                }
                
            }

            return this.arrayOption[itemIndex];
        },

		/**
         * @desc 点击选择多条记录
		 * @params {event} click事件对象
		 * @params {item} 触发click事件的元素
		 * @return 返回所有已选项数据
		 * @private
         */
        _clickMultiple: function(event, item) { 
        	var itemIndex=item.index();
        	if(this.items.eq(itemIndex).hasClass('jazz-boxlist-highlight')){
	       		this._unselectItem(item);
				//触发unclick事件 
				this._trigger('unclick',null, item);
				//触发change事件
	       		this._changeTrigger(item,null);
				    }else{
		        this._selectItem(item);
				//触发change事件
		        this._changeTrigger(null,item);
				}
        	
        	//返回所选项		
            return this.getSelected();
        },


		/**
         * @desc change事件触发
		 * @params {oldItem} 之前选项元素
		 * @params {newItem} 新选项元素
		 * @private
         */    
        _changeTrigger : function(oldItem,newItem){

			var changeItem = {};
			if(!oldItem){
				var newIndex=newItem.index();
				changeItem['newObject'] = this.arrayOption[newIndex];
				}
			else	
				if(!newItem){
				var oldIndex=oldItem.index();
					changeItem['oldObject']=this.arrayOption[oldIndex];
			}
			else{
					var newIndex=newItem.index();
					var oldIndex=oldItem.index();
					changeItem['newObject']=this.arrayOption[newIndex];
					changeItem['oldObject']=this.arrayOption[oldIndex];
				}
			this._trigger('change',null,changeItem);
           
	     },


    	/**
         * @desc 获得选中的项记录
		 * @params {value} 所选项元素或者索引
		 * @private
         */          
        _selectItem: function(value) {
            var item = null;
            if($.type(value) === 'number')
                item = this.items.eq(value);
            else
                item = value;
            item.addClass('jazz-boxlist-highlight').removeClass('jazz-boxlist-li');

        },

    	/**
         * @desc 取消获得选中的项记录
		 * @params {value} 取消项元素或者索引
		 * @private
         */        
        _unselectItem: function(value) {
            var item = null;
            if($.type(value) === 'number')
                item = this.items.eq(value);
            else
                item = value;
            
            item.addClass('jazz-boxlist-li').removeClass('jazz-boxlist-highlight');	           

        },
        
        /**
         * @desc 动态添加组件下拉框中的内容
         * @public
		 * @example $('div_id').boxlist('reload');
         */           
        reload: function() {
        	this._init();
        },
        
    	/**
         * @desc 取消选择的全部记录
         * @public
		 * @example $('#boxlist_id').boxlist('unselectAll');
         */           
        unselectAll: function() {
            this.items.removeClass('jazz-boxlist-highlight');
            //触发change事件
	    	this._trigger('change',null);    	 
        },

		/**
         * @desc 获取已选项数据
		 * @public
		 * @return 所选项的数据
		 * @example $('#boxlist_id').boxlist('getSelected');
         */       
        getSelected: function(){
        	var selected = this.items.filter('.jazz-boxlist-highlight');
        	var allSelected = [];
			var selectedIndex = null;
        	for(var i=0;i<selected.length;i++){
				selectedIndex = selected.index();
        		allSelected.push(this.arrayOption[selectedIndex]);
        	}
        	return allSelected;
        }
      
    });
        
})(jQuery);