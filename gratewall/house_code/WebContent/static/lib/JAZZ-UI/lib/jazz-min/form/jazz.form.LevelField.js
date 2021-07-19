(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',
		         'form/jazz.form.Field'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.levelfield
	 * @description 水平选择组件。
	 * @constructor
	 * @extends jazz.field
	 */
    $.widget("jazz.levelfield", $.jazz.hiddenfield, {
    	
    	options: /** @lends jazz.levelfield# */ {

        	/**
    		 *@type String
    		 *@desc 组件类型
    		 *@default levelfield
    		 */
			vtype: 'levelfield',
			
    		/**
    		 *@type Array
    		 *@desc 获取数据项url地址
    		 *@default []
    		 *@example [{"checked": true, "text": "男", "value": "1"},{"text": "女", "value": "2"}] 
    		 */
    		dataurl: [],
    		
    		/**
    		 *@type Number
    		 *@desc 下拉列表的高度
    		 *@default
    		 */    		
    		listheight: 0,
    		
            /**
			 *@type Boolean
			 *@desc 是否多选 true是复选，false是单选
			 *@default false
			 */            
            multiple: false,    		

    		// callbacks
    		/**
			 *@desc 鼠标焦点离开输入框时触发
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").levelfield("option", "change", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("levelfieldchange",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… change="XXX()"></div> 或 <div…… change="XXX"></div>
			 */
    		change: null,
    		
    		/**
			 *@desc 当鼠标点击展开收起时触发
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").levelfield("option", "exchange", function(event, ui){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("levelfieldexchange",function(event, ui){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… exchange="XXX()"></div> 或 <div…… exchange="XXX"></div>
			 */    		
    		exchange: null
    	},
    	
    	/** @lends jazz.levelfield */
    	
        /**
         * @desc 创建组件
         * @private
         */ 
        _create: function() {
        	//创建组件
        	this._super();
        	
        	this.parent.addClass("jazz-level-comp-level");
        },

        /**
         * @desc 初始化组件
         * @private
         */ 
		_init: function(){
			
			this._super();
			
			this.options.listheight = jazz.config.levelfieldlistheight || this.options.listheight;
			
			this._dataItems();
			
			//验证
			//this._validator();            
		},
		
		/**
         * @desc 相关事件处理
         * @private
		 */
		_bindEvent: function(){
			var $this = this;
			this.inputFrame.find(".jazz-level-div").off("click.levelfield").on("click.levelfield", function(){
				if(!$this.options.multiple){
					$(this).siblings().removeClass("jazz-level-checked");
				}
				var f = false;
        		if($(this).hasClass("jazz-level-checked")){
        			$(this).removeClass("jazz-level-checked");
        		}else{
        			$(this).addClass("jazz-level-checked");
        			f = true;
        		}
        		$this.setCondition();
        		$this._event("change", null, {checked: f, value: $(this).attr("value")});
        	});
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
         * @desc 生成数据dom
         * @{items} 数据项
         * @{compId} id
         * @private
         */ 
        _commonDom: function(items, compId) {
        	//var name = this.options.name;
        	var div = "<div class='jazz-level-frame'><div class='jazz-level-frame-inner'>";
        	if(items && items.length>0){
        		var $this = this; this.checkItems = [];
        		$.each(items, function(i, item){
        			var id = compId+'_box_'+item.value, chked = '';
        			$this.checkItems.push(id);
        			div += "<div class='jazz-level-div";
        			if(item.checked=="true" || item.checked){
        				chked = " jazz-level-checked";
        			}
        			div += chked+"' value='"+item.value+"' text='"+item.text+"'>"+item.text+"</div>";
        		});
        	}
        	div += "</div></div>";
        	this.element.height("auto");
        	this.inputFrame.append(div);
        	this.inputFrame.addClass("jazz-level-position");
        	this.inputFrame.append("<div class='jazz-level-expend'><div class='jazz-level-zk jazz-level-zk2'><span class='jazz-level-yy'>展开</span><div class='jazz-level-zk-img'>&nbsp;&nbsp;</div></div></div>");
        	
        	var zk = this.inputFrame.find(".jazz-level-frame").height();
        	if(zk > 35){
        		var zkobj = this.inputFrame.find(".jazz-level-zk");
        		zkobj.removeClass("jazz-level-zk2");
        		var $this = this;
        		zkobj.on("click", function(){
        			var fr = $this.inputFrame.children(".jazz-level-frame");
        			var frc = fr.children();
        			if(fr.hasClass("jazz-level-frame-height")){
        				fr.removeClass("jazz-level-frame-height");
        				zkobj.children(".jazz-level-yy")[0].innerHTML = "收起";
        				zkobj.children(".jazz-level-zk-img").addClass("jazz-level-zk-img2");
        				var h = fr.height();
        				if($this.options.listheight!=0 && h > $this.options.listheight){
        					fr.height($this.options.listheight);
        					frc.height($this.options.listheight);
        					frc.addClass("jazz-level-frame-height2");
        				}
        			}else{
        				fr.attr("style", "");
        				frc.attr("style", "");
        				frc.removeClass("jazz-level-frame-height2");
        				fr.addClass("jazz-level-frame-height");
        				zkobj.children(".jazz-level-yy")[0].innerHTML = "展开";
        				zkobj.children(".jazz-level-zk-img").removeClass("jazz-level-zk-img2");
        			}
        			if(!!window.iFrameHeight){  
        				iFrameHeight();
        			};
        			$this._event("exchange", null);
        		});
        		$this.inputFrame.children(".jazz-level-frame").addClass("jazz-level-frame-height");
        	}
        	
			this._bindEvent();
        },		
		
		/**
         * @desc 生成level项
         * @private
         */
        _dataItems: function(){
        	this.inputFrame.find(".jazz-level-div").remove();
        	
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
         * @desc 获取当前选中状态对象的文本值
		 * @return 所有选中文本值
		 * @example $('XXX').levelfield('getText');
		 */
        getText: function(){
        	var chkvalue = new Array(); 
        	$.each(this.inputFrame.find(".jazz-level-div"), function(){
        		if($(this).hasClass("jazz-level-checked")){
        			chkvalue.push($(this).attr("text")||"");
        		}
        	});
        	return chkvalue.join(',');
        },        
        
        /**
         * @desc 获取当前选中状态对象的值      
         * @return 所有选中的值
         * @example $('XXX').levelfield('getValue');
         */
        getValue: function(){
        	var chkvalue = new Array(); 
        	$.each(this.inputFrame.find(".jazz-level-div"), function(){
        		if($(this).hasClass("jazz-level-checked")){
        			chkvalue.push($(this).attr("value")||"");
        		}
        	});
        	return chkvalue.join(',');
        },        
        
        /**
         * @desc 动态加载数据
		 * @example $('XXX').levelfield('reload');
         */
        reload: function(data, callbackfunction) {
        	this.callbackFunc = callbackfunction;
        	this.inputFrame.empty();
        	if(data && jazz.isArray(data)){ 
        		this._callback(data);  		
        	}else{
        		var dataurl = this.options.dataurl;
				if(dataurl) {
					if(jazz.isArray(dataurl)) {
						this._callback(dataurl);
					}else {
						var $this = this;
						this._ajax(!this.options.cacheflag, function(data){
							$this._callback(data);               	
						});
					}
				}        		
        	}
        },        
        
		/**
         * @desc 取消当前选中状态对象
		 * @example $('XXX').levelfield('reset');
		 */
        reset: function(){
        	$.each(this.inputFrame.find(".jazz-level-div"), function(){
        		$(this).removeClass("jazz-level-checked");
        	});
        },
        
		/**
         * @desc 设置当前状态对象选中
		 * @param {value} 选中对象的值
		 * @example $('XXX').levelfield('setValue','2,4');
		 */
        setValue: function(value){
        	$.each(this.inputFrame.find(".jazz-level-div"), function(){
        		var a = (value+"").split(",");
        		$(this).removeClass("jazz-level-checked");
        		for(var i=0, len=a.length; i<len; i++){
        			if($(this).attr("value") == a[i]){
        				$(this).addClass("jazz-level-checked"); 
        			}
        		}
        	});
        }      
		
    });
    
});
