(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'jazz.Button'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
/**
 * @version 0.5
 * @name jazz.toolbar
 * @description 工具条组件
 * @constructor
 * @extends jazz.boxComponent
 * @requires
 * @example $('#div_id').toolbar();
 */
$.widget("jazz.toolbar", $.jazz.boxComponent, {

		options :/** @lends jazz.toolbar# */ {
        	/**
        	 *@desc 组件类型
        	 */
        	vtype: 'toolbar',
			/**
			 *@type Object
			 *@desc toolbar组件元素数据，数组格式可放置按钮，form表单元素组件等。
			 *@default null
			 */
			items: null,
			/**
			 *@type int
			 *@desc toolbar折行样式（wrap,换行;scroll,滚动）
			 *		建议使用toolbar换行时，将toolbar中元素同侧对齐布局，例如都靠左；
			 *@default 0
			 */
			overflowtype: "scroll",
			/**
			 * @type string 
			 * @desc toolbar排列方向（横向或纵向）,(horizontal/vertical)
			 * @default "horizontal"
			 */
			orientation: "horizontal" 
		},

		/** @lends jazz.toolbar */
		/**
         * @desc 创建组件
         * @private
         */ 
		_create : function() {
			this._super();
			
			//1.生成toolbar基本dom结构，横向和纵向布局都是这个结构
			var toolbardoms = "<div class='jazz-toolbar-scroll-first'></div>" +
								"<div class='jazz-toolbar-content'><div class='jazz-toolbar-content-wrap'>" +
								"<div class='jazz-toolbar-contentarea-second'></div><div class='jazz-toolbar-contentarea-first'></div>"+
								"</div></div>" +
							"<div class='jazz-toolbar-scroll-second'></div>";
			var toolbarclass = 'jazz-toolbar jazz-toolbar-orientation-horizontal';
			if(this.options.orientation=="vertical"){
				toolbarclass = 'jazz-toolbar jazz-toolbar-orientation-vertical';
			}
			this.element.addClass(toolbarclass).append(toolbardoms);
			
			//2.缓存toolbar基本dom结构
			this.scollFirst = this.element.find(".jazz-toolbar-scroll-first");
			this.toolbarContent = this.element.find(".jazz-toolbar-content");
			this.toolbarContentWrap = this.element.find(".jazz-toolbar-content-wrap");
			this.toolbarFirst= this.element.find(".jazz-toolbar-contentarea-first");
			this.toolbarSecond= this.element.find(".jazz-toolbar-contentarea-second");
			this.scollSecond = this.element.find(".jazz-toolbar-scroll-second");
			
			//3.toolbar纵向排列时，需要根据width或者height预设值定义样式
			if(this.options.orientation=="vertical"){
				if(this.options.height==-1){
					this.toolbarContent.addClass("toolbar-content-default-height");
				}
				if(this.options.width==-1){
					this.toolbarContent.addClass("toolbar-content-default-width");
					this.element.addClass("toolbar-default-width");
				}
			}
		},
		/**
         * @desc 初始化组件
         * @private
         */ 
		_init: function(){
			this._super();
			
			//1.初始化设置toolbar宽度和高度
        	this._setToolbarWith();
        	this._setToolbarHeight();
        	//2.根据items==null确定toolbar组件子元素创建方式
        	if(this.options.items==null){
        		this._vtypeCreateElements();
        	}else{
        		this._widgetCreateElements();
        	}
        	if(this.options.overflowtype=="wrap"){
        		if(this.options.orientation == "horizontal"){
        			this.toolbarContentWrap.css("width","100%");
        			this.toolbarFirst.addClass("white-space-normal");
					this.toolbarSecond.addClass("white-space-normal");
        		}
        	}else {
        		//绑定滚动事件
        		this._bindScrollEvent();
				//jazz.widget方式创建toolbar时，确认toolbar工具条内部子组件都创建完成后，执行宽度滚动条计算；
				if($.isArray(this.options.items) && this.options.items.length>0){
	        		var that = this;
	        		setTimeout(function(){that._computeToolbarWidth()},150);
	        	}
        	}
		},
		/**
		 * @desc 覆盖jazz.component finish方法，由父类调用(只是适用vtype创建组件时调用)；
		 * 		 确认待toolbar工具条内部子组件都创建完成后，执行宽度滚动条计算；
		 * @private
		 */
		finish: function(){
			var that = this;
			if(this.options.overflowtype!="wrap"){
				//this._computeToolbarWidth();
				//toolbar创建时，内部子组件存在图片加载情况，会有image加载延迟。
				//暂时使用setTimeout延时解决
				setTimeout(function(){that._computeToolbarWidth()},150);
			}
		},
		/**
		 * @desc 利用vtype形式创建toolbar组件
		 * @private
		 */
		_vtypeCreateElements: function(){
			var orientation = this.options.orientation;
			var childrens = this.element.children();
			var child=null,wrapHtml = "<div class='jazz-toolbar-element'></div>";
			if(orientation=="vertical"){
    			//现在纵向对齐，只支持顶部对齐自上往下排列，不支持上中下对齐
    			//1.顶部对齐，将所有子元素都放到this.toolbarFirst中
        		for(var i=0,m=childrens.length;i<m-3;i++){
        			child = $(childrens[i]);
        			child.wrap(wrapHtml);
    				this.toolbarFirst.append(child.parent());
        		}
        		//2.纵向对齐，子元素在this.toolbarFirst中，隐藏this.toolbarSecond
        		this.toolbarSecond.hide();
    		}else{
    			//现在横向toolbar布局分为左中右对齐，需要分别处理
    			//1.居中与（靠左，靠右对齐）不可同时存在，若同时存在，则居中按靠左对齐处理
    			if(this.element.children("[align=center]").length==(childrens.length-3)){
    				this.toolbarContent.css("text-align","center");
    			}
    			for(var i=0,m=childrens.length;i<m-3;i++){
        			child = $(childrens[i]);
        			child.wrap(wrapHtml);
        			if(child.attr("align")=="right"){
        				this.toolbarSecond.append(child.parent());
        			}else{
        				this.toolbarFirst.append(child.parent());
        			}
        		}
    		}
		},
		/**
		 * @desc 利用$()/$.widget形式创建toolbar组件
		 * @private
		 */
		_widgetCreateElements: function(){
			var items = this.options.items;
			if(!$.isArray(items)){
				return;
			}
			var orientation = this.options.orientation;
			var child=null,wrapHtml = "<div class='jazz-toolbar-element'></div>";
			if(orientation=="vertical"){
    			//现在纵向对齐，只支持顶部对齐自上往下排列，不支持上中下对齐
    			//1.顶部对齐，将所有子元素都放到this.toolbarFirst中
				for(var i=0, len=items.length; i<len; i++){
					if(items[i]["class"]=="separator"){
						child = $("<div class='separator'></div>");
					}else{
	    				child = jazz.widget(items[i],this.toolbarFirst);
					}
					if(items[i]["vtype"]=="button"){
    					child = child.data("button")["container"];
    				}
    				child.wrap(wrapHtml);
    				this.toolbarFirst.append(child.parent());
				}
				//2.纵向对齐，子元素在this.toolbarFirst中，隐藏this.toolbarSecond
        		this.toolbarSecond.hide();
    		}else{
    			//现在横向toolbar布局分为左中右对齐，需要分别处理
    			//1.居中与（靠左，靠右对齐）不可同时存在，若同时存在，则居中按靠左对齐处理
    			var isAllAlignCenter = true;
				for(var i=0, len=items.length; i<len; i++){
					if(items[i]["align"]!="center"){
						isAllAlignCenter = false;
					}
				}
				if(isAllAlignCenter){
    				this.toolbarContent.css("text-align","center");
    			}
				for(var i=0, len=items.length; i<len; i++){
        			if(items[i]["align"]=="right"){
        				if(items[i]["class"]=="separator"){
							child = $("<div class='separator'></div>");
						}else{
		    				child = jazz.widget(items[i],this.toolbarSecond);
						}
						if(items[i]["vtype"]=="button"){
        					child = child.data("button")["container"];
        				}
        				child.wrap(wrapHtml);
    					this.toolbarSecond.append(child.parent());
        			}else{
        				if(items[i]["class"]=="separator"){
							child = $("<div class='separator'></div>");
						}else{
	        				child = jazz.widget(items[i],this.toolbarFirst);
						}
        				if(items[i]["vtype"]=="button"){
        					child = child.data("button")["container"];
        				}
        				child.wrap(wrapHtml);
        				this.toolbarFirst.append(child.parent());
        			}
				}
    		}
		},
		/**
         * @desc 绑定toolbar组件横向或纵向滚动事件
         * @return undefined
         * @private
         * @example  this._bindScrollEvent();
         */
		_bindScrollEvent: function(){
			var that = this;
			this.element.on('contextmenu.jazz-toolbar-contextmenu' , function(e){
				//屏蔽右键
				return false;
            });
			//绑定滚动箭头滚动事件
			var orientation = this.options.orientation;
        	var scrollwidth = 50;//默认滚动距离，暂时处理方式（可一次滚动一个子元素的距离方式）
        	that.scollSecond.off("click").on("click",function(){
        		if(orientation=="horizontal"){
        			var scrollleft = that.toolbarContent.scrollLeft()+scrollwidth;
		       		var scrollTotalWidth = that.toolbarContentWrap.width() - that.toolbarContent.width();
		       		if(scrollleft>scrollTotalWidth){
	        			scrollleft = scrollTotalWidth;
	        		}
	        		that.toolbarContent.scrollLeft(scrollleft);
        		}else if(orientation=="vertical"){
        			var scrollheight = that.toolbarContent.scrollTop()+scrollwidth;
		       		var scrollTotalHeight = that.toolbarContentWrap.height() - that.toolbarContent.height();
		       		if(scrollheight>scrollTotalHeight){
	        			scrollheight = scrollTotalHeight;
	        		}
	        		that.toolbarContent.scrollTop(scrollheight);
        		}
        	});
        	that.scollFirst.off("click").on("click",function(){
        		if(orientation=="horizontal"){
        			var scrollleft = that.toolbarContent.scrollLeft()-scrollwidth;
	       			that.toolbarContent.scrollLeft(scrollleft);
        		}else if(orientation=="vertical"){
        			var scrollheight = that.toolbarContent.scrollTop()-scrollwidth;
	       			that.toolbarContent.scrollTop(scrollheight);
        		}
        	});
		},
		/**
		 * @desc 计算toolbar的宽度或者高度，以显示滚动箭头
		 * @private
		 */
		_computeToolbarWidth: function(){
			var that = this;
			var orientation = this.options.orientation;
			if(orientation=="horizontal"){
				//一、横向布局
				//1.当this.toolbarContentWrap宽度大于this.toolbarContent显示滚动箭头
				//var tbw1 = that.toolbarFirst.width();
				//var tbw2 = that.toolbarSecond.width();
				var tbw1 = 0;//累计每个非隐藏子元素宽度
				$.each(that.toolbarFirst.children(),function(index,obj){
					if($(obj).is(":visible")){
						tbw1 += $(obj).width();
					}
				});
				var tbw2 = 0;//累计每个非隐藏子元素宽度
				$.each(that.toolbarSecond.children(),function(index,obj){
					if($(obj).is(":visible")){
						//console.log("toolbar button width ......");
						//console.log($(obj).width());
						tbw2 += $(obj).width();
					}
				});
				if(tbw1>0){
					/*if(jazz.isIE(6)||jazz.isIE(7)){
						tbw1+=1;
					}*/
					//解决ie6、7低版本内核及怪异模式兼容问题，1像素bug
					tbw1+=1;
					
					//that.toolbarFirst.show();
					that.toolbarFirst.width(tbw1);
				}else{
					//that.toolbarFirst.hide();					
				}
				if(tbw2>0){
					/*if(jazz.isIE(6)||jazz.isIE(7)){
						tbw2+=1;
					}*/
					//解决ie6、7低版本内核及怪异模式兼容问题，1像素bug
					tbw2+=1;
					
					//that.toolbarSecond.show();
					that.toolbarSecond.width(tbw2);
				}else{
					//that.toolbarSecond.hide();					
				}
				var wrapWidth = 0;
				var contentWidth = that.toolbarContent.width();
				var toolbarWidth = that.element.width();
				//if(contentWidth<(tbw1+tbw2)){
				if(toolbarWidth<(tbw1+tbw2)){
					that.toolbarContent.width(that.element.width()-36);//36为左右两个滚动箭头的宽度和
					that.toolbarContent.css({"position":"relative","left":"18px"});
					
					/*if(jazz.isIE(6)){
						wrapWidth = tbw1+tbw2+4;
					}else{
						wrapWidth = tbw1+tbw2;
					}*/
					//解决ie6、7低版本内核及怪异模式兼容问题
					wrapWidth = tbw1+tbw2+3;
					
					that.toolbarContentWrap.width(wrapWidth);
					that.scollFirst.show();
					that.scollSecond.show();
				}else{
					that.toolbarContent.css({"width":"100%","position":"static","left":"0px"});
					
					that.toolbarContentWrap.css({"width":"100%"});
					that.scollFirst.hide();
					that.scollSecond.hide();
				}
			}else if(orientation=="vertical"){
				//二、纵向布局
				//1.当this.toolbarContentWrap高度大于this.toolbarContent显示滚动箭头
				var toolbarHeight = that.element.height();
				var contentHeight = that.toolbarContent.height();
				var	contentWrapHeight = that.toolbarContentWrap.height();
				
				if(jazz.isNormalSize(that.options.height)){
					//if(contentHeight<contentWrapHeight){
					if(toolbarHeight<contentWrapHeight){
						that.toolbarContent.height(toolbarHeight-36);//36为上下两个滚动箭头的高度和
						that.toolbarContent.css({"position":"relative","top":"18px"});
						
						that.scollFirst.show();
						that.scollSecond.show();
					}else{
						that.toolbarContent.css({"height":"100%","position":"static","top":"0px"});
						
						that.scollFirst.hide();
						that.scollSecond.hide();
					}
				}
			}
		},
		/**
		 * @desc 覆盖父级_width()方法，toolbar响应resize事件，进行宽度上的调整
		 * @private
		 */
		_width:function(forcecalculate){
			var orientation = this.options.orientation;
			var width = this.options.width;
			if(orientation=="vertical" && width == -1){
				//1.toolbar纵向时，若未设置宽度，则toolbar宽度随内部子元素扩充
				return false;
			}
			if(jazz.isNumber(width)){
				return false;
			}
			this._super(forcecalculate);
			if(this.iscalculatewidth){
				if(this.options.overflowtype=="wrap"){
					if(this.options.orientation == "horizontal"){
						var tbw1 = this.toolbarFirst.width();
						var tbw2 = this.toolbarSecond.width();
						var toolbarWidth = this.element.width();
						var leftchildsize = this.toolbarFirst.children().size();
						var rightchildsize = this.toolbarSecond.children().size();
						if(leftchildsize>0&&leftchildsize>0){
							if(toolbarWidth<(tbw1+tbw2)){
								this.toolbarFirst.css("width","70%");
								this.toolbarSecond.css("width","auto");
							}
						}else{
							if(leftchildsize===0){
								this.toolbarSecond.css("width","100%");
							}
							if(rightchildsize===0){
								this.toolbarFirst.css("width","100%");
							}
						}
					}
				}else{
					this._computeToolbarWidth();						
				}
        	}
		},
		/**
		 * @desc 覆盖父级_height()方法，toolbar响应resize事件，进行高度上的调整
		 * @private
		 */
		_height:function(forcecalculate){
			this._super(forcecalculate);
        	if(this.iscalculatewidth){
        		if(this.options.overflowtype=="wrap"){
        			
        		}else{
					this._computeToolbarWidth();
				}
        	}
		},
		/**
		 * @desc 初始化时设置toolbar的宽度
		 * @private
		 */
		_setToolbarWith:function(){
			//初始化时设置toolbar的宽度
			//若是window resize引起的toolbar宽度变化，则是由_width()方法调节
			//不设宽度时默认this.element width:auto
			//若是设定宽度则设置this.element.width(value)
			var orientation = this.options.orientation;
			var width = this.options.width;
			if(orientation=="vertical" && width == -1){
				//1.toolbar纵向时，若未设置宽度，则toolbar宽度随内部子元素扩充
				return false;
			}
			
			if(this.options.width == -1){
				width = "100%";
			}
			if(jazz.isNormalSize(width)){
				if(jazz.isNumber(width)){
					this.element.outerWidth(width);
				}else if(/^\d+(\.\d+)?%$/.test(width)){
					var n = this._getCalculatePercentWidth(width, this.element.parent());
					this.element.outerWidth(n);
				}
			}
		},
		/**
		 * @desc 初始化时设置toolbar的高度
		 * @private
		 */
        _setToolbarHeight:function(){
        	//初始化时设置toolbar的高度
			//若是window resize引起的toolbar高度变化，则是由_height()方法调节
			var height = this.options.height;
			if(jazz.isNormalSize(height)){
				if(jazz.isNumber(height)&& parseFloat(height)>=0){
					this.element.outerHeight(height);
				}else if(/^\d+(\.\d+)?%$/.test(height)){
					var n = this._getCalculatePercentHeight(height, this.element.parent());
					this.element.outerHeight(n);
				}
			}
        },
		/**
         * @desc 增加toolbar子元素数据项,相当于调用$('#div_id').toolbar('appendElement',items);
         * 建议使用appendElement接口方法
         * @param {items} 工具条子组件数据项items，格式为数组
         * @example  $('#div_id').toolbar('addElement',items);
         */
		addElement: function(items){
			this._insertSubElement(items);
			if(this.options.overflowtype!="wrap"){
        		this._computeToolbarWidth();
        	}
		},
		/**
		 * @desc 在对应的子组件后部插入toolbar子元素，无相应子元素时，插入到工具条最后面。
		 * @param {items} 工具条子组件数据项items，格式为数组
		 * @param {name} 匹配toolbar子元素名称name值，可选参数
		 * @example  $('#div_id').toolbar('appendElement',items,[name]);
		 */
		appendElement: function(items,name){
			this._insertSubElement(items,name,"after");
			if(this.options.overflowtype!="wrap"){
        		this._computeToolbarWidth();
        	}
		},
		/**
		 * @desc 在对应的子组件前部插入toolbar子元素，无相应子元素时，插入到工具条最前面。
		 * @param {items} 工具条子组件数据项items，格式为数组
		 * @param {name} 匹配toolbar子元素名称name值，可选参数
		 * @example  $('#div_id').toolbar('preappendElement',items,[name]);
		 */
		prependElement: function(items,name){
			this._insertSubElement(items,name,"before");
			if(this.options.overflowtype!="wrap"){
        		this._computeToolbarWidth();
        	}
		},
		/**
		 * @desc toolbar插入子元素逻辑处理
		 * @param {items} 工具条子组件数据项items，格式为数组
		 * @param {name} 插入toolbar子元素名称name值
		 * @param {position} 子元素插入位置before/after
		 * @private
		 */
		_insertSubElement: function(items,name,position){
			if(!$.isArray(items)){
				return;
			}
			var orientation = this.options.orientation;
			var child=null,wrapHtml = "<div class='jazz-toolbar-element'></div>";
			if(orientation=="vertical"){
    			//现在纵向对齐，只支持顶部对齐自上往下排列，不支持上中下对齐
    			//1.顶部对齐，将所有子元素都放到this.toolbarFirst中
				for(var i=0, len=items.length; i<len; i++){
					if(items[i]["class"]=="separator"){
						child = $("<div class='separator'></div>");
					}else{
	    				child = jazz.widget(items[i],this.toolbarFirst);
					}
					if(items[i]["vtype"]=="button"){
    					child = child.data("button")["container"];
    				}
    				child.wrap(wrapHtml);
    				//this.toolbarFirst.append(child.parent());
    				this._insertSubElementByCondition(name,position,child.parent(),this.toolbarFirst);
				}
    		}else{
    			//现在横向toolbar布局分为左中右对齐，需要分别处理
    			//1.居中与（靠左，靠右对齐）不可同时存在，若同时存在，则居中按靠左对齐处理
    			var isAllAlignCenter = false;
    			var textAlign = this.toolbarContent.css("text-align");
				if(textAlign =="center"){
					isAllAlignCenter = true;
				}else{
					var temp = true;
					for(var i=0, len=items.length; i<len; i++){
						if(items[i]["align"]!="center"){
							temp = false;
						}
					}
					var leftnums = this.toolbarFirst.children().length;
					var rightnums = this.toolbarSecond.children().length;
					if(leftnums==0&&rightnums==0&&temp){
						isAllAlignCenter = true;
					}
					if(isAllAlignCenter){
	    				this.toolbarContent.css("text-align","center");
	    			}
				}
				for(var i=0, len=items.length; i<len; i++){
        			if(items[i]["align"]=="right"){
        				if(items[i]["class"]=="separator"){
							child = $("<div class='separator'></div>");
						}else{
		    				child = jazz.widget(items[i],this.toolbarSecond);
						}
						if(items[i]["vtype"]=="button"){
	    					child = child.data("button")["container"];
	    				}
        				child.wrap(wrapHtml);
    					//this.toolbarSecond.append(child.parent());
    					this._insertSubElementByCondition(name,position,child.parent(),this.toolbarSecond);
        			}else{
        				if(items[i]["class"]=="separator"){
							child = $("<div class='separator'></div>");
						}else{
		    				child = jazz.widget(items[i],this.toolbarFirst);
						}
						if(items[i]["vtype"]=="button"){
	    					child = child.data("button")["container"];
	    				}
        				child.wrap(wrapHtml);
        				//this.toolbarFirst.append(child.parent());
        				this._insertSubElementByCondition(name,position,child.parent(),this.toolbarFirst);
        			}
				}
    		}
		},
		_insertSubElementByCondition: function(name,position,newEl,tbar){
			if(name){
				var target = this.element.find('div[name="'+name+'"]');
				if(target.length>0){
					if(position=="before"){
						$(target).parent().before(newEl);
					}else{
						$(target).parent().after(newEl);
					}
				}else{
					if(position=="before"){
						tbar.prepend(newEl);
					}else{
	    				tbar.append(newEl);
					}	
				}
			}else{
				if(position=="before"){
					tbar.prepend(newEl);
				}else{
    				tbar.append(newEl);
				}
			}
		},
		/**
		 * @desc 据子组件name属性值移除toolbar子元素
		 * @param {name} 工具条子组件name名称值
		 * @example  $('#div_id').toolbar('removeElement','name');
		 */
		removeElement: function(name){
			if(!name){
        		return;
        	}
        	var $this = this;
        	$this.element.find('div[name="'+name+'"]').each(function(i){
    			$(this).parents(".jazz-toolbar-element").remove();
    			//$(this).parent().remove();
        	});
        	if(this.options.overflowtype!="wrap"){
        		this._computeToolbarWidth();
        	}
		},
		/**
		 * @desc 据子组件name属性值隐藏toolbar子元素
		 * @param {name} 工具条子组件name名称值
		 * @example  $('#div_id').toolbar('hideElement','name');
		 */
		hideElement: function(name){
			if(!name){
        		return;
        	}
        	var $this = this;
        	$this.element.find('div[name="'+name+'"]').each(function(i){
    			$(this).parents(".jazz-toolbar-element").hide();
    			//$(this).parent().hide();
        	});
        	if(this.options.overflowtype!="wrap"){
        		this._computeToolbarWidth();
        	}
		},
		/**
		 * @desc 据子组件name属性值显示toolbar子元素
		 * @param {name} 工具条子组件name名称值
		 * @example  $('#div_id').toolbar('showElement','name');
		 */
		showElement: function(name){
			if(!name){
        		return;
        	}
        	var $this = this;
        	$this.element.find('div[name="'+name+'"]').each(function(i){
    			$(this).parents(".jazz-toolbar-element").show();
    			//$(this).parent().show();
        	});
        	if(this.options.overflowtype!="wrap"){
        		this._computeToolbarWidth();
        	}
		},
		/**
		 * @desc 据子组件name属性值移除toolbar按钮
		 * @param {name} 工具条按钮name名称值
		 * @deprecated 该接口为JAZZ向下版本兼容，请使用removeElement(name);
		 * @example  $('#div_id').toolbar('removeButton','name');
		 */
		removeButton: function(name){
			this.removeElement(name);
		},
		/**
		 * @desc 隐藏toolbar按钮
		 * @param {name} 工具条按钮name名称值
		 * @deprecated 该接口为JAZZ向下版本兼容，请使用hideElement(name);
		 * @example  $('#div_id').toolbar('hideButton','name');
		 */
		hideButton: function(name){
			this.hideElement(name);
		},
		/**
		 * @desc 显示toolbar按钮
		 * @param {name} 工具条按钮name名称值
		 * @deprecated 该接口为JAZZ向下版本兼容，请使用showElement(name);
		 * @example  $('#div_id').toolbar('showButton','name');
		 */
		showButton: function(name){
			this.showElement(name);
		},
		/**
         * @desc toolbar按钮不可用（此接口和jazz.button组件接口重复，建议直接查找到对应button组件，使用button API）
         * @param {name} 工具条按钮name名称值
         * @example  $('#div_id').toolbar('disableButton','name');
         */
        disableButton: function(name) {
        	if(!name){
        		return;
        	}
        	var $this = this;
        	$this.element.find('div[name="'+name+'"]').each(function(i){
    			$(this).button("disable");
        	});
        },
        /**
         * @desc toolbar按钮使可用 （此接口和jazz.button组件接口重复，建议直接查找到对应button组件，使用button API）
         * @param {name} 工具条按钮name名称值
         * @example  $('#div_id').toolbar('enableButton','name');
         */
        enableButton: function(name) {
        	if(!name){
        		return;
        	}
        	var $this = this;
        	$this.element.find('div[name="'+name+'"]').each(function(i){
    			$(this).button("enable");
        	});
        },
		/**
         * @desc toolbar组件高亮按钮选中样式 （此接口和jazz.button组件接口重复，建议直接查找到对应button组件，使用button API）
         * @param {name} 工具条按钮name名称值
         * @public
         * @example  $('#div_id').toolbar('highlightButton',name);
         */
		highlightButton: function(name){
        	if(!name){
        		return;
        	}
        	this.element.find('div[name="'+name+'"]').button("highlight");
		},
		/**
         * @desc toolbar组件取消高亮按钮选中样式 （此接口和jazz.button组件接口重复，建议直接查找到对应button组件，使用button API）
         * @param {name} 工具条按钮name名称值
         * @public
         * @example  $('#div_id').toolbar('unhighlightButton',name);
         */
		unhighlightButton: function(name){
        	if(!name){
        		return;
        	}
        	this.element.find('div[name="'+name+'"]').button("unhighlight");
		},
		/**
		 * @desc 隐藏toolbar组件
		 * @public
		 */
		hide: function(){
			var $this = this;
			$this.element.hide();
		},
		/**
		 * @desc 显示toolbar组件
		 * @public
		 */
		show:function(){
			var $this = this;
			$this.element.show();
		}

	});

});
