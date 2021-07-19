(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',
		         'jazz.Tree', 
		         'form/jazz.form.DropdownField'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	
	/**
	 * @version 0.5
	 * @name jazz.comboxtreefield
	 * @description 表单元素的选择填报类。
	 * @constructor
	 * @extends jazz.dropdownfield
	 * @requires
	 * @example
	 */	

    $.widget("jazz.comboxtreefield", $.jazz.dropdownfield, {
       
        options: /** @lends jazz.comboxtreefield# */ {
			
       		/**
			 *@type String
			 *@desc 组件类型
			 *@default comboxtreefield
			 */
			vtype: 'comboxtreefield',

			/**
			 *@type Object
			 *@desc zTree的setting对象
			 *@default null
			 */ 
            setting: {
			    data: {
					simpleData: {
						enable: true,
						idKey: "id",
						pIdKey: "pId",
						rootPId: 0
					}
				},
				async: {
					enable: true,
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					dataType:"json", 
					autoParam:["id","level"]
				},							
				check: {
					enable: true,
					radioType: 'all'
				},
				view: {
					dblClickExpand: true
				},							
				callback: {
					
				}
            },
            
            /**
			 *@type String
			 *@desc 默认选中节点显示的字段属性
			 *@default 'name'
			 */            
            showlabel: "name",
            
			/**
			 *@type Number
			 *@desc 滚动条显示高度
			 *@default 200
			 */         
            downheight: 200,  
            
        	/**
        	 *@type Boolean
        	 *@desc 是否可编辑 true可编辑 false不可编辑 
        	 *@default true
        	 */
        	editable: false, 
        	
        	/**
        	 *@type Boolean
        	 *@desc 是否显示选择框 true显示 false不显示 
        	 *@default true
        	 */
        	checkenable : true,
			/**
			 *@type Boolean
			 *@desc 是否多选 true是复选框，false是单选框
			 *@default 'true'
			 */            
            multiple: false,
			
            // callbacks
			
    		/**
			 *@desc 鼠标焦点进入输入框时触发
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").comboxtreefield("option", "enter", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("comboxtreefieldenter",function(event, ui){  <br/>} <br/>});
			 */			
			enter: null,
    		
    		/**
			 *@desc 鼠标焦点离开输入框时触发
			 *@param {event} 事件
			 *@param {ui.newValue} 新修改的值 
			 *@param {ui.oldValue} 旧值
			 *@event
			 *@example
			 *<br/>$("XXX").comboxtreefield("option", "leave", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("comboxtreefieldleave",function(event, ui){  <br/>} <br/>});
			 */
    		leave: null,
    		
    		/**
			 *@desc 当选择了某项时，触发itemselect事件  配合dataurl属性使用
			 *@param {event} 事件
			 *@param {value} 选中项的值 
			 *@event
			 *@example
			 *<br/>$("XXX").comboxtreefield("option", "itemselect", function(event, value){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("comboxtreefielditemselect",function(event, value){  <br/>} <br/>});
			 */
			itemselect: null
        },

        /** @lends jazz.comboxtreefield */

        /**
         * @desc 创建组件 
         * @private
         */  
        _create: function() {
        	//创建组件
        	this._super();
        	//this._createDownpanel();       	
        },
        
        /**
         * @desc 初始化组件
         * @private
         */
        _init: function() {
        	this.dataset = [];
        	
        	this._treeSetting();
        	
        	this._loadtree();    

        	this._super();

        	this._bindEventInputtext();
        	
        	this._validator();
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
         * @desc 生成数据项
		 * @private
         */
        _callback: function(data) {
        	if(!!data){
        		if(jazz.isIE(7) || jazz.isIE(6)){
        	    	if(typeof(this.options.setting.async.autoParam) != 'object'){
        	    		this.options.setting.async.autoParam = jazz.stringToJson(this.options.setting.async.autoParam);
        	    	}
        	    }  
    			this._initTreeEvent();

//    			for(var i = 0, len = data.length; i < len; i++){
//    				var d = data[i];
//    				if(d["id"]){
//    					d["value"] = d["id"];
//    				}
//    				if(d["name"]){
//    					d["text"] = d["name"];
//    				}
//    			}
    			this.dataset = data;
    			this.zTreeObj = $.fn.zTree.init(this.itemsContainer, this.options.setting, data);
    			
    			this.datastatus = true;
    		}
        	
//        	if(!this.options.disabled){
//        		this._binddropdown(this.options.vtype);
//    		}
    		
    		this._validator();
    		
//    		if(this.options.defaultvalue){
//    			this.setValue(this.options.defaultvalue);
//    		}else{
//    			this.setText(this.options.blanktext);
//          }
        },
        
		/**
         * @desc 列表框是否显示滚动条
		 * @private
		 * @example this._itemScroll();
         */        
        _itemScroll: function(){
        	this.panel.height(this.options.downheight);       	
        },        

        /**
         * @desc 初始化树组件事件
         * @private
         */
        _initTreeEvent: function(){
        	var $this = this;
        	if(!!this.options.setting.check){
        		
        		this.options.setting.callback.onClick = function(event, treeId, treeNode) {
    				//var z_tree_init_001 = $.fn.zTree.getZTreeObj(treeId);
        			var z_tree_init_001 = $this.zTreeObj;
    				z_tree_init_001.checkNode(treeNode,true,true,true);
    				if(!!$this.options.onclick){
    					eval($this.options.onclick).call(this, event, treeId, treeNode);
    				}
    		    };
        		
        		var tempClick = {click: null};
        		if(!!this.funOnClick){
        			$.extend(true, tempClick, {click: this.funOnClick});
        		}
        		
        		this.options.setting.callback.onCheck = function(e, treeId, treeNode){
        			$this._oldText = $this.getText(); $this._oldValue = $this.getValue();
        			
        			//var z_tree_init_001 = $.fn.zTree.getZTreeObj(treeId),
        			var z_tree_init_001 = $this.zTreeObj,
    				z_tree_init_nodes = z_tree_init_001.getCheckedNodes(true),
    				node_label = "",  node_code = '';
        			
        			if(!$this.options.multiple){
        				z_tree_init_001.selectNode(treeNode);
            			if(!treeNode.checked){
        					z_tree_init_001.cancelSelectedNode();
            			}
        			}else{
        				if(!treeNode.checked){
        					z_tree_init_001.cancelSelectedNode();
            			}else{
            				z_tree_init_001.selectNode(treeNode);
            			}
        			}
    				
    				//z_tree_init_nodes.sort(function compare(a,b){return a.id-b.id;});
    				for (var i=0, l=z_tree_init_nodes.length; i<l; i++) {
    					if(i==0){
    						node_label = z_tree_init_nodes[i].name;
    						node_code = z_tree_init_nodes[i].id;    						
    					}else{
    						node_label = node_label + "," + z_tree_init_nodes[i].name;
    						node_code = node_code + "," + z_tree_init_nodes[i].id; 						
    					}
    				}
    				
    				$this.node_label = $this._newText = node_label, $this._newValue = node_code;
    				$this.setText(node_label);
    				$this.input.val(node_code);
    				
    				$this._event("itemselect", e, {"{text": node_label, "value":node_code});
    				
    				if(tempClick.click != null && $this.options.tempState!=0){
        				var func = eval(tempClick.click);
        				func(e, treeId, treeNode);
        			}
    				
    				$this.inputtext.focus();
    				if(!!$this.options.oncheck){
    					eval($this.options.oncheck).call(this, e, treeId, treeNode);
    	        	}
        		};
        	} else {
        		var tempClick = {click: null};
        		if(!!this.funOnClick){
        			$.extend(true, tempClick, {click: this.funOnClick});
        		}
        		
        		this.options.setting.callback.onClick = function(e, treeId, treeNode){

        			$this._oldText = $this.getText(); $this._oldValue = $this.getValue();
    				//var z_tree_init_001 = $.fn.zTree.getZTreeObj(treeId),
        			var z_tree_init_001 = $this.zTreeObj,
        			z_tree_init_nodes = z_tree_init_001.getSelectedNodes(),
    				node_label = "",  node_code = '';
    				
    				//z_tree_init_nodes.sort(function compare(a,b){return a.id-b.id;});
    				for (var i=0, l=z_tree_init_nodes.length; i<l; i++) {
    					if(i==0){
    						node_label = z_tree_init_nodes[i].name;
    						node_code = z_tree_init_nodes[i].id;      						
    					}else{
    						node_label = node_label + "," + z_tree_init_nodes[i].name;
    						node_code = node_code + "," + z_tree_init_nodes[i].id;    						
    					}
    				}
    				
    				$this.node_label = $this._newText = node_label, $this._newValue = node_code;
    				$this.setText(node_label);
    				$this.input.val(node_code);
    				
        			if(tempClick.click != null){
        				var func = eval(tempClick.click);
        				func(e, treeId, treeNode);
        			}
        			
        			$this.inputtext.focus();
        		};       		
        	}
			 
			/*if(!!this.options.onasyncsuccess){
        		this.options.setting.callback.onAsyncSuccess = function(e, treeId, treeNode){
					eval($this.options.onasyncsuccess).call(this, e, treeId, treeNode);
			    };
        	}*/
        	
        	this.options.setting.callback.onAsyncSuccess = function(e, treeId, treeNode){
        		$this.datastatus = true;
        		if(!!$this.options.onasyncsuccess){
        			eval($this.options.onasyncsuccess).call(this, e, treeId, treeNode);
        		}
		    }
			if(!!this.options.onnodecreated){
				this.options.setting.callback.onNodeCreated = function(e, treeId, treeNode){
					eval($this.options.onnodecreated).call(this, e, treeId, treeNode);
			    };
        	}

			this.resetcomboxtree.off("click.comboxtreefield").on("click.comboxtreefield", function(){
				$this.reset();
				//$this.setText($this.options.blanktext);
         		//var treeObj = $.fn.zTree.getZTreeObj($this.ulId);
         		var treeObj = $this.zTreeObj;
         		if(!!treeObj){
         			var nodes = treeObj.getCheckedNodes(true);
             		for (var i=0, l=nodes.length; i < l; i++) {
             			treeObj.checkNode(nodes[i], false, true);
             		}
         		}
			});
        },
        
		/**
         * @desc 重新加载数据
         * 		 注： 在数据初始化时也调用了这个方法 jazz.SwordAdapter.js
         *       obj.find('select').comboxtreefield('loadData',);  目的是为了加载SwordPageData数据
		 * @param {data} 静态数据
		 * @private
		 * @example this._loadData(data);
         */
        _loadData: function(data, func){
			this.itemsContainer.children().remove();
    		this._loadtree(data, func);
        },              
        
        /**
         * @desc 实例化树，调取zTree
         * @param {data} 静态数据
         * @private
         */
        _loadtree: function(data, func){
        	if(data && jazz.isArray(data)){ //通过reload方法刷新静态数据时
        		this._callback(data);
        	}else if(!!this.options.dataurl){
				if(!!this.options.asyncurl){
					this.options.setting.async.url = this.options.asyncurl; 
				}else{
					this.options.setting.async.url = this.options.dataurl; 
				}
				if(typeof(this.options.dataurl) == 'object' && jazz.isArray(this.options.dataurl)){
					this._callback(this.options.dataurl);
				}else{
                    //true  去缓存数据
                    //func  回掉函数
					this._ajax(true, func);
				}
			}
        },        
		       
		/**
         * @desc 回显选中的数据
         * @param {codes} 节点集合
         * @param {aTree} 当前树对象
         */        
         _setNodeData: function(codes, aTree){
			var $this = this, m = 1, delay = 100, delaycount = this.options.delaycount ,label = "",code = "";
         	if(codes || codes===0){
         		var codeArray = (codes+"").split(',') ;
         		var codetimeout = new Object();
         		for(var i=0; i<codeArray.length; i++){	
         			code = $.trim(codeArray[i]);
         			var node = null;
         			if(!!code){
         				codetimeout["code_"+code] = setInterval(function(x){
         					return function(){
             					node = aTree.getNodeByParam('id', x, null);
             					m++;
             					if(!!node || delay*m > delaycount){
             						clearInterval(codetimeout["code_"+x]);
             						aTree.checkNode(node, false, true);
             						aTree.checkNode(node, true, true);
             						label = label + "," + node[$this.options.showlabel];
             	 	    			code = node.id + ",";
                 					$this.setText(label.substring(1));
                 					$this.input.val(codes);
             					}
         					}
         				}(code), delay);
         			}
         		}
 	    		this._oldText = this.getText(); this._oldValue = this.getValue();
 	    		setTimeout(function(){
 	    			$this.node_label = $this._newText = label, $this._newValue = codes;
 	    		},delaycount/2);
         	}
        },  
		
//        /**
//         * @desc 设置组件属性的值
//         * @param {key} 对象的属性名称 
//         * @param {value} 对象的属性值
//		 * @private
//         */
//        _setOption: function(key, value){
//        	switch(key){
//	        	case 'disabled':
//	        		if(value){
//	        			this.parent.off('click');
//	        		}else{
//						this._binddropdown(this.options.vtype);
//	        		}
//	        		break;	 
//        	}
//        	this._super(key, value);
//        },	
        
        /**
         * @desc 处理设置值的回调函数
         * @param {value} 值
         * @param {callback_function} 回调函数
		 * @private
         */ 
        _setData: function(value, callback_function){
			//var aTree = $.fn.zTree.getZTreeObj(this.ulId);
			var aTree = this.zTreeObj;
     		if(aTree){
     			this._setNodeData(value, aTree);
     		}
     		this._setValueCallback(callback_function);            
        },
		
        /**
         * @desc 初始化ztree的settting配置
         * @private
         */
        _treeSetting: function(){
        	
        	if(this.options.checkenable){
				this.options.setting.check.chkStyle = "checkbox";

	    		if(!this.options.multiple){
	    			this.options.setting.check.chkStyle = "radio";		
	    		}else{
	    			this.resetcomboxtree.remove();
	    		}
			}else{
				this.options.setting.check.enable = false;
			}
    		
    		if(!!this.options.asyncenable){
				this.options.setting.async.enable = this.options.asyncenable; 
			}
    		
			if(!!this.options.autoparam){
				this.options.setting.async.autoParam = this.options.autoparam; 
			}
    		
			if(!!this.options.autochecktrigger){
				this.options.setting.check.autoCheckTrigger = this.options.autochecktrigger; 
			}
			if(!!this.options.radiotype){
				this.options.setting.check.radioType = this.options.radiotype; 
			}
			if(!!this.options.chkboxtype){
				this.options.setting.check.chkboxType = this.options.chkboxtype; 
			}
			
            if(!!this.options.setting.check){
            	this.funOnClick = this.options.setting.callback.onCheck;
			}else{
				this.funOnClick = this.options.setting.callback.onClick;
			}
        },        

        /**
         * @desc 获取值
		 * @return String
         */        
        getValue: function(){
        	var value = "";
        	if(this.options.editable == true){
        		var text = this.inputtext.val();
        		if(text != this.options.valuetip){
                    label = $.trim(this.node_label);
                    if(label===text){
                    	text = this._super();
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
         * @desc 初始化表单数据，依据dataurl
         * @param {data} 静态数据
         * @param {function} 回调函数
		 * @example $('XXX').comboxfield('reload', data, function(){ });
         */
        reload: function(data, func) {
        	this._loadData(data, func);
        },
        
		/**
         * @desc 重置
		 * @example $('XXX').comboxtreefield('reset');
		 */
        reset: function() {
        	this._super();
     		//var aTree = $.fn.zTree.getZTreeObj(this.ulId);
     		var aTree = this.zTreeObj;
     		aTree.cancelSelectedNode();
     		aTree.checkAllNodes(false);
     		var nodes = aTree.getCheckedNodes(true);
     		$.each(nodes,function(i,node){
     			aTree.checkNode(node, false);
     		});
        },

		/**
         * @desc 设置当前状态对象选中
		 * @param {value} 选中对象的值
		 * @param {callback_function} 回调函数
		 * @example $('XXX').comboxtreefield('setValue', '2');
		 */
		setValue: function(value, callback_function) {
			if(value || value===0){
				if(typeof(value) == 'object' && jazz.isArray(value)){
					this._initTreeEvent();
					this.zTreeObj = $.fn.zTree.init($this.itemsContainer, this.options.setting, value);
		        	this._setValueCallback(callback_function);
				}else{
					if(this.dataset.length > 0){
						this._setData(value, callback_function);
					}else{
						//非初始化时，加载数据
						if(this.options.islazydata){
							var $this = this;
							this._ajax(false, function(data){
								$this._callback(data);
								$this._setValueData(value, callback_function);							
							});
						}else{
							this._setValueData(value, callback_function);
						}
			     	}	
				}
			}else{
				//this.reset();
			}
		}
    });

});