(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',
		         'form/jazz.form.HiddenField'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.checkboxfield
	 * @description 表单元素的多选框。
	 * @constructor
	 * @extends jazz.hiddenfield
	 * @example $('XXX').checkboxfield();
	 */	

    $.widget("jazz.checkboxfield", $.jazz.hiddenfield, {
		
    	options: /** @lends jazz.checkboxfield# */ {
    		
    		/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default 'checkboxfield'
    		 */
    		vtype: 'checkboxfield',
    		
    		/**
    		 *@type Array
    		 *@desc 获取数据项url地址
    		 *@default []
    		 *@example [{"checked": true, "text": "男", "value": "1"},{"text": "女", "value": "2"}] 
    		 */
    		dataurl: [],

    		/**
    		 *@type number
    		 *@desc 每一个数据项的宽度 
    		 *@default 100
    		 */    		
    		itemwidth: 100,
    		
            //callbacks
    		/**
			 *@desc 当选择了某项时，触发itemselect事件  配合dataurl属性使用
			 *@param {event} 事件
			 *@param {ui} 选中项的值 {checked: boolean, text: "", value: ""}
			 *@event
			 *@example
			 *<br/>$("XXX").checkboxfield("option", "itemselect", function(event, value){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("checkboxfielditemselect",function(event, value){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… itemselect="XXX()"></div> 或 <div…… itemselect="XXX"></div>
			 */
			itemselect: null
    	},
    	
    	/** @lends jazz.checkboxfield */
        /**
         * @desc 创建组件
         * @private
         */  
        _create: function() {
        	//创建组件
        	this._super();
        	
        	var vtype = this.options.vtype;
        	if(vtype == "checkboxfield"){
        		this.checkfield = "checkbox";
        	}else{
        		this.checkfield = "radio";
        	}
        },
        
        /**
         * @desc 初始化组件
         * @private
         */ 
        _init: function(){
        	this._dataItems(); 
        	
        	this._super();  
        	
        	this._bindEvents();
        },
    	
    	/**
         * @desc 绑定事件
         * @private
         */ 
        _bindEvents: function() {
            var $this = this;
            if((this.options.disabled != true && this.options.disabled != "disabled" ) &&  this.options.editable == true){
            	 this.parent.off('click.checkboxfield').on('click.checkboxfield', function(e) {
	               	 var target = e.target, $target = $(target);
	               	 var boxid = $target.attr("id");
	               	 boxid = boxid.substring(0, boxid.length-5);
	               	 var boxObj = $('#'+boxid);
	               	 
	   				 if($target.is('span')){
	   	                  $this._toggle(boxObj, $target ,e);			  
	   				 }
	   				 if($target.is('label')){
	   					  span = $('#'+boxid+'_span');
	   					  
	   					  if(boxObj.prop('checked')){
	   						  boxObj.prop("checked", false);
	   						  $this._nocheckImg(span);
	   					  }else{
	   						  boxObj.prop("checked", true);
	   						  $this._checkImg(span);
	   					  }
	   					  
	   					  $this._event("itemselect", e ,{checked: !$this._isChecked(boxObj), text: boxObj.attr("text"), value: boxObj.val()});
	   				 }
            	 });
			}
        },        
        
        /**
         * @desc 生成数据项
		 * @private
         */
        _callback: function(data) {
        	if(data){
        		this._commonDom(data, this.compId);
        	}
        	//如果reload方法中有回调, 则调用回调函数
        	if(this.callbackFunc){
        		this.callbackFunc.call(this, this.getValue());
        	}
        },
        
		/**
         * @desc 选中当前对象
		 * @param {box} radio对象
         * @private
		 */   
        _checked: function(box, spanObj){
    		box.prop('checked', true);
    		this._checkImg(spanObj);
        },
        
		/**
         * @desc 选中当前对象
		 * @param {obj} radio对像中的span对象
         * @private
		 */ 
        _checkImg: function(obj){
        	obj.removeClass('jazz-'+this.checkfield+'-nochecked').addClass('jazz-'+this.checkfield+'-checked');
        },        
        
        /**
         * @desc 生成数据dom
         * @{items} 数据项
         * @{compId} id
         * @private
         */ 
        _commonDom: function(items, compId) {
        	this.parent.addClass("jazz-field-comp-in-check");
        	this.compIdspan = compId;
        	var name = this.options.name;
        	var div = '<div class="jazz-checkbox-frame">';
        	if(items && items.length>0){
        		var $this = this; this.checkItems = [];
        		var disabled = '';
				if($this.options.disabled == true || $this.options.editable == false){
					disabled = ' disabled="false" ';
				}
        		$.each(items, function(i, item){
        			var id = compId+'_box_'+item.value;
        			$this.checkItems.push(id);
        			var chkedspan = " jazz-"+$this.checkfield+"-nochecked " , chkedtext= "";
        			if(item.checked=="true" || item.checked){
        				chkedspan = " jazz-"+$this.checkfield+"-checked ";
        				chkedtext= " checked='true' ";
        			}
        			
        			div = div + '<div index="'+i+'" class="jazz-field-comp2 jazz-checkbox-item" style="width: '+$this.options.itemwidth+'px;">'
        					+ '<div class="jazz-checkbox-hidden"><input type="'+$this.checkfield+'" id="'+id+'" text="'+item.text+'" name="'+name+'" '+chkedtext+' value="'+item.value+'" '+disabled+'/></div>'
        					+ '<span id="'+id+'_span" class="jazz-checkbox '+chkedspan+'"></span>'
        					+ '<div style="padding-left: 25px"><label id="'+id+'label" class="jazz-checkbox-label">'+item.text+'</label></div>'
        					+ '</div>';
        		});
        	}
        	div += '<div class="jazz-field-clear"></div></div>';
        	this.inputFrame.append(div);
        },
        
		/**
         * @desc 生成checkbox项
         * @private
         */
        _dataItems: function(){
        	this.inputFrame.children(".jazz-checkbox-frame").remove();
        	
        	if(typeof(this.options.dataurl) == 'string' && /^\s*[\[|{](.*)[\]|}]\s*$/.test(this.options.dataurl)){
        		//转换成对象
        		this.options.dataurl = jazz.stringToJson(this.options.dataurl);
        	}
        	
            if(typeof(this.options.dataurl) == 'object' && jazz.isArray(this.options.dataurl)){            	
            	this._commonDom(this.options.dataurl, this.compId);
            	
        		//如果reload方法中有回调, 则调用回调函数
            	if(this.callbackFunc){
            		this.callbackFunc.call(this, this.getValue());
            	}
			}else{
				this._ajax();
			}
        },        
        
        /**
         * @desc 设置默认值
         * @private
         */
		_defaultvalue: function(){
        	//默认值
        	var value = this.options.defaultvalue;	
        	if(value || value===0){
        		var $this = this;
        		$.each(this.checkItems, function(i, data){
            		$this.inputFrame.find("input[id='"+data+"']").removeAttr('checked');
            		var span = $('#'+data+'_span');
            		span.removeClass('jazz-'+$this.checkfield+'-checked').addClass('jazz-'+$this.checkfield+'-nochecked');
            	});        		
            	$.each((value+"").split(","), function(i, data){
            		$this.inputFrame.find("input[value='"+data+"']").attr('checked', 'true');
            		var span = $('#'+$this.compIdspan+'_box_'+data+'_span');
            		span.removeClass('jazz-'+$this.checkfield+'-nochecked').addClass('jazz-'+$this.checkfield+'-checked');
                });
            	this.inputview.text(this.getText());            	
        	}		
		},  
		
        /**
         * @desc 设置组件是否置灰
         * @private
         */		
		_disabled: function(){
			if(this.options.disabled == true || this.options.disabled == "disabled"){
				this.inputFrame.children(".jazz-checkbox-frame").addClass("jazz-field-disabled");
				this.parent.off('click.checkboxfield');
			}else{
				this.inputFrame.children(".jazz-checkbox-frame").removeClass("jazz-field-disabled");
				this._bindEvents(); 
			}
		},
        
		/**
         * @desc 覆盖field中的_editable方法
         * @private
         */
        _editable: function(){
    		if(this.options.editable == false || this.options.editable == "false"){
    			this.parent.off('click.checkboxfield');
    		}else{
    			this._bindEvents(); 
    		}        	
        },        
        
        /**
         * @desc 覆盖field中的_height方法
         * @private
         */
        _height: function(){
        	this.parent.css("border", "0px");
        },
        
		/**
         * @desc 判断是否选中
		 * @param {box} radio对象
         * @private
		 */ 
        _isChecked: function(box){
        	return box.prop('checked');
        },
        
		/**
         * @desc 设置每一项的宽度
         * @private
		 */         
        _itemwidth: function(){
        	var itemwidth = this.options.itemwidth;
        	if(itemwidth){
        		this.inputFrame.find(".jazz-checkbox-item").outerWidth(itemwidth);        		
        	}
        },
        
		/**
         * @desc 取消当前对象
		 * @param {obj} radio对像中的span对象
         * @private
		 */
        _nocheckImg: function(obj){
        	obj.removeClass('jazz-'+this.checkfield+'-checked').addClass('jazz-'+this.checkfield+'-nochecked');
        }, 
        
		/**
		 * @desc 设置组件是否为输入框或者是文本
		 * @private
		 */        
        _readonly: function(){
			if(this.options.readonly == true){
				this.inputFrame.children(".jazz-checkbox-frame").css({"display": "none"});
				this.inputview.css({"display": "block"});
				this.inputview.html(this.getText() || "");
			}else{
				this.inputview.css({"display": "none"});
                this.inputFrame.children(".jazz-checkbox-frame").css({"display": "block"});
			}  
        },        
        
        /**
         * @desc 设置组件属性的值
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         */
        _setOption: function(key, value){
        	switch(key){
	        	case 'itemwidth':
	        		this.options.itemwidth = value;
	        		this._itemwidth();
	        		break;
        	}
        	this._super(key, value);
        },     
        
		/**
         * @desc 切换是否被选中
		 * @param {box} radio对象
		 * @param {spanObj} radio对像中的span对象
         * @private
		 */ 
        _toggle: function(box, spanObj, event) {
            if(this._isChecked(box)) {
                this._unchecked(box, spanObj);
            } else {
                this._checked(box, spanObj);
            }
            
            this._event("itemselect", event, {checked: this._isChecked(box), text: box.attr("text"), value: box.val()});
        },
        
		/**
         * @desc 解除勾选
		 * @param {box} radio对象
		 * @param {spanObj} radio对像中的span对象
         * @private
		 */ 
        _unchecked: function(box, spanObj) {
        	box.prop('checked', false);
        	this._nocheckImg(spanObj);        	
        },
        
		/**
         * @desc 获取当前选中状态对象的文本值
		 * @return 所有选中文本值
		 * @example $('XXX').checkboxfield('getText');
		 */
        getText: function(){
        	var name = this.options.name;
        	var chkvalue = new Array(); 
        	this.parent.find("input[name='"+name+"']:checked").each(function(){
        		chkvalue.push($(this).attr("text"));
        	});
        	return chkvalue.join(',');
        },
        
        /**
         * @desc 获取当前选中状态对象的值
         * @return 所有选中的值
         * @example $('XXX').checkboxfield('getValue');
         */
        getValue: function(){
        	var name = this.options.name;
        	var chkvalue = new Array(); 
        	this.parent.find("input[name='"+name+"']:checked").each(function(){
        		chkvalue.push($(this).val());
        	});
        	return chkvalue.join(',');
        },
        
        /**
         * @desc 动态加载数据
		 * @example $('XXX').checkboxfield('reload');
         */           
        reload: function(data, func) {
        	this.callbackFunc = func;
        	this._dataItems(); 
        },           
        
		/**
         * @desc 取消当前选中状态对象
		 * @example $('XXX').checkboxfield('reset');
		 */
        reset: function(){
        	for(var i=0, len=this.checkItems.length; i<len; i++){
        		var obj = $('#'+this.checkItems[i]+'');
        		var span = $('#'+this.checkItems[i]+'_span');
        		span.removeClass('jazz-'+this.checkfield+'-checked').addClass('jazz-'+this.checkfield+'-nochecked');
        		obj.attr('checked', false);
        	}
        },
             
		/**
         * @desc 设置当前状态对象选中
		 * @param {value} 选中对象的值
		 * @example $('XXX').checkboxfield('setValue','2,4');
		 */
        setValue: function(value){
        	var $this = this;
//        	if(value){
        		$.each((value+"").split(","),function(i,data){
            		$this.parent.find("input[value='"+data+"']").attr('checked', 'true');
            		var span = $('#'+$this.compIdspan+'_box_'+data+'_span');
            		span.removeClass('jazz-checkbox-nochecked').addClass('jazz-checkbox-checked');
            	});
//        	}else{
//        		$.each($this.checkItems,function(i,data){
//            		$this.parent.find("input[id='"+data+"']").attr('checked', 'true');
//            		var span = $('#'+data+'_span');
//            		span.removeClass('jazz-checkbox-nochecked').addClass('jazz-checkbox-checked');
//            	});
//        	}
        	this.inputview.text(this.getText() || "");
        }
        
    });
    
});
