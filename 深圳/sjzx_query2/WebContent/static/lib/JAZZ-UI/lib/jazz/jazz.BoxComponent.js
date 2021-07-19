(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'jazz.Component'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
/**
 * @version 0.5
 * @name jazz.boxComponent
 * @description 使用矩形容器组件的基类，提供自适应高度、宽度调节的功能，具备大小调节和定位的能力。
 * @constructor
 * @extends jazz.component
 * @requires
 */
	$.widget('jazz.boxComponent', $.jazz.component, {
	    options: /** @lends jazz.boxComponent# */ {
//			/**
//			 *@type number
//			 *@desc 组件相对页面left坐标
//			 *@default ''
//			 */
//			left: '',
//			
//			/**
//			 *@type number
//			 *@desc 组件相对页面top坐标
//			 *@default ''
//			 */
//			top: '',			
//			
//			/**
//			 *@type number
//			 *@desc 鼠标相对于当前组件左边位置距离
//			 *@default ''
//			 */
//			x: '',
//			
//			/**
//			 *@type number
//			 *@desc 鼠标相对于当前组件上边位置距离
//			 *@default ''
//			 */
//			y: '',		
//
//			/**
//			 *@type number
//			 *@desc 鼠标相对于页面左边位置距离
//			 *@default ''
//			 */
//			pagex: '',
//			
//			/**
//			 *@type number
//			 *@desc 鼠标相对于页面上边位置距离
//			 *@default ''
//			 */
//			pagey: '',

			/**
			 *@type number
			 *@desc 组件计算后的高度   
			 *@default -1
			 *@example
			 *组件的高度为： height: 400 或 40%  calculateheight高度就为400 或 560（40/100）*1200（父容器内高度）
			 */
			calculateheight: -1,
			
			/**
			 *@type number
			 *@desc 组件计算后的宽度
			 *@default -1
			 *@example
			 *组件的计算宽度为： height: 600 或 60% calculatewidth宽度就为600 或 720  （60/100）*1200（父容器内宽度）
			 */			
			calculatewidth: -1,			
			
			/**
			 *@type number
			 *@desc 组件计算后内部的高度  
			 *@default -1
			 *@example
			 *组件的高度为： height: 400 或 40%  边框总高度为：4  calculateinnerheight高度就为396 或 556 （40/100）*1200（父容器内高度）
			 */
			calculateinnerheight: -1,
			
			/**
			 *@type number
			 *@desc 组件计算后内部的宽度
			 *@default -1
			 *@example
			 *组件的计算宽度为： height: 600 或 60% 边框总高度为：4  calculatewidth宽度就为596 或 716 （60/100）*1200（父容器内宽度）
			 */			
			calculateinnerwidth: -1,			
			
			/**
			 *@type number
			 *@desc 组件的高度
			 *@default -1
			 *@example
			 *组件设置的高度为： height: 400 或 40%,  height高度就为400 或 40%
			 */
			height: -1,			

			/**
			 *@type number
			 *@desc 组件的宽度
			 *@default -1
			 *@example
			 *组件设置的宽度为： width: 600 或 60%,  width宽度就为600 或 60%
			 */
			width: -1,

			
			//callbacks
			
			/**
			 *@desc 当组件调整大小时触发
			 *@param {event} 事件
			 *@param {ui} 
			 *@event
			 *@example
			 */
			resize: null
//			
//			/**
//			 *@desc 当前组件被移动后触发
//			 *@param {event} 事件
//			 *@param {ui} 
//			 *@event
//			 *@example
//			 */
//			move: null			
		},
		
		/** @lends jazz.boxComponent */
		/**
		 * @desc 创建组件
		 * @private
		 */
		_create: function(){
			this._super();
		},
		
		/**
		 * @desc 初始化
		 * @private
		 */
		_init: function(){
			this._super();
		},
		
		/**
         * @desc 获取计算后的组件高度
         * @return Number
		 * @private
         */
		_getCalculatePercentHeight: function(height, parent){
			var parentinnerheight;
			if(parent[0].nodeName == "BODY"){
				var sw = 0;
				if(this.isScrollX(parent)){ sw = jazz.config.scrollWidth; }
				parentinnerheight = jazz.util.windowHeight() - (parent.outerHeight(true) - parent.height()) - sw;
			}else{
				parentinnerheight = parent.height();
			    var h2 = parent[0].clientHeight;
			    if(h2 < parentinnerheight){
			    	parentinnerheight = h2;
			    }
			}
			var n = parseFloat(height.substring(0, (height+"").length - 1));
			n = (n/100) * parentinnerheight;
			//向下取整数
			return Math.floor(n);
		},
		
		/**
         * @desc 获取计算后的组件宽度	 
         * @return Number
		 * @private
         */		
		_getCalculatePercentWidth: function(width, parent){
			var parentinnerwidth;
			if(parent[0].nodeName == "BODY"){
				var sw = 0;
				if(this.isScrollY(parent)){ sw = jazz.config.scrollWidth; }
				parentinnerwidth = jazz.util.windowWidth() - (parent.outerWidth(true) - parent.width()) - sw;
			}else{
			    parentinnerwidth = parent.width();
			    var w2 = parent[0].clientWidth;
			    if(w2 < parentinnerwidth){
			    	parentinnerwidth = w2;
			    }
			}
			var n = parseFloat(width.substring(0, (width+"").length - 1));
			n = (n/100) * parentinnerwidth;
			//向下取整数
			return Math.floor(n);
		},
		
		/**
         * @desc 获取当前组件的高度	 
         * @return Number
		 * @private
         */
		_height: function(forcecalculate){
			var height = this.options.height;
			if((jazz.isNormalSize(height) && this.options.calculateheight != height) || forcecalculate){
				if(jazz.isNumber(height)){
					this.element.outerHeight(height, true);
					//计算后组件的高度
					this.options.calculateheight = height;
					this.options.calculateinnerheight = this.element.height();
					this.iscalculateheight = true;
				}else{
					if(/^\d+(\.\d+)?%$/.test(height)){
						var parent = this.element.parent();
						var n = this._getCalculatePercentHeight(height, parent);
						if(this.options.calculateheight === n && !forcecalculate){
							this.iscalculateheight = false;
						}else{
							this.element.outerHeight(n, true);
							//计算后组件的高度
							this.options.calculateheight = n;
							this.options.calculateinnerheight = this.element.height();
							this.iscalculateheight = true;
						}
					}
				}
			}else {
				this.iscalculateheight = false;
			}
		},
		
		/**
         * @desc 获取当前组件的宽度	 
         * @return Number
		 * @private
         */
		_width: function(forcecalculate){
			var width = this.options.width;
        	//不设宽度时，默认占满父容器宽度
			if(this.options.width == -1){
				width = "100%";
			}
			if((jazz.isNormalSize(width) && this.options.calculatewidth != width) || forcecalculate){
				if(jazz.isNumber(width)){
					this.element.outerWidth(width); //outerWidth(width, true) 在margin:0 auto时，第二次修改宽度值时，会出错
					//计算后组件的宽度
					this.options.calculatewidth = width;
					this.options.calculateinnerwidth = this.element.width();
					this.iscalculatewidth = true;
				}else if(/^\d+(\.\d+)?%$/.test(width)){
					var parent = this.element.parent();
					var n = this._getCalculatePercentWidth(width, parent,forcecalculate);
					if(this.options.calculatewidth === n && !forcecalculate){
						this.iscalculatewidth = false;
					}else{
						this.element.outerWidth(n);
						//计算后组件的宽度
						this.options.calculatewidth = n;
						this.options.calculateinnerwidth = this.element.width();
						this.iscalculatewidth = true;
					}
				}
			}else{
				this.iscalculatewidth = false;
			}
		},

		/**
         * @desc 刷新布局
		 * @private
         */
		_reflashLayout: function(){  //jazz.log("vtype=\""+this.options.vtype+"\" name=\""+this.options.name+"\" id=\""+this.options.id+"\" _reflashLayout");
			//更新布局
			if(this.layoutobject){
				this.layoutobject.reflashLayout(this, this.layoutcontainer, this.options.layoutconfig);				
			}
		},
		
		/**
         * @desc 刷新子组件的高度
		 * @private
         */
		_reflashChildHeight: function(forcecalculate){ //jazz.log("vtype=\""+this.options.vtype+"\" name=\""+this.options.name+"\" id=\""+this.options.id+"\" _reflashChildHeight");
			$.each(this.getChildrenComponent(), function(i, obj){
				var element = $(this);
				var vtype = element.attr("vtype");
				//修改子组件的高度
				var el = element.data(vtype);
				if(el){
					el._resizeHeight(forcecalculate);				
				}
			});
		},
		
		/**
         * @desc 刷新子组件的宽度
		 * @private
         */
		_reflashChildWidth: function(forcecalculate){//jazz.log("vtype=\""+this.options.vtype+"\" name=\""+this.options.name+"\" id=\""+this.options.id+"\" _reflashChildWidth");
			$.each(this.getChildrenComponent(), function(i, obj){
				var element = $(this);
				var vtype = element.attr("vtype");
				//修改子组件的宽度
				var el = element.data(vtype);
				if(el){
					el._resizeWidth(forcecalculate);					
				}
			});
		},
		
		/**
         * @desc 高度	 
		 * @private
         */		
		_resizeHeight: function(forcecalculate){ //jazz.log("vtype=\""+this.options.vtype+"\" name=\""+this.options.name+"\" id=\""+this.options.id+"\" _resizeHeight");
			var y = ""; 
			if(this.content){
				y = this.content.css("overflow-y");
				this.content.css("overflow-y", "hidden");
			}			
			this._height(forcecalculate);
			if(this.iscalculateheight){
				if(this.layoutobject){
					this.layoutobject.reflashLayoutBefore();			
				}					
				this._reflashLayout();
				this._reflashChildHeight(forcecalculate);
				if(this.layoutobject){
					this.layoutobject.reflashLayoutAfter();	
				}
			}
			if(this.content){
				this.content.css("overflow-y", y);
			}			
		},		
		
		/**
         * @desc 宽度	 
		 * @private
         */
		_resizeWidth: function(forcecalculate){ //jazz.log("vtype=\""+this.options.vtype+"\" name=\""+this.options.name+"\" id=\""+this.options.id+"\" _resizeWidth");
			var x = "";
			if(this.content){
				x = this.content.css("overflow-x");
				this.content.css("overflow-x", "hidden");
			}
			this._width(forcecalculate);
			if(this.iscalculatewidth){
				if(this.layoutobject){
					this.layoutobject.reflashLayoutBefore();			
				}
				this._reflashLayout();
				this._reflashChildWidth(forcecalculate);
				if(this.layoutobject){
					this.layoutobject.reflashLayoutAfter();	
				}
			}
			if(this.content){
				this.content.css("overflow-x", x);
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
	    		case 'width':
	    			//判断是否为 “12” “23.5” “40%” “40.5%”
	    			//判断动态改变的值是否与目前组件的值相同, 如果相同则不作处理
	    			if(jazz.isNormalSize(value)){
    					this.options.width = value;
    					this._width();
    					if(this.iscalculatewidth){
    						this._reflashLayout(); 
    						this._reflashChildWidth();
    					}
	    			}	    			
	    			
	    		break;
	    		case 'height':
	    			if(jazz.isNormalSize(value)){
	    				this.options.height = value;
	    				this._height();
	    				if(this.iscalculateheight){
    						this._reflashLayout();
    						this._reflashChildHeight();    					
	    				}
	    			}
	        	break;
        	}
        	this._super(key, value);
        },
		 
        /**
         * @desc 布局中区域的打开关闭的控制,只控制border布局 column布局 row布局
         * @param region 区域 border布局
         *        region为east west north south
         * 
         */
        layoutSwitch: function(region, showSize){
        	if(this.layoutobject){
        		this.layoutobject.layoutSwitch(region, showSize);
        	}
        },
		
		/**        
         * @desc 计算页面大小
         */
		getPageSize: function () {
		    var xScroll, yScroll;
		    if (window.innerHeight && window.scrollMaxY) {
		        xScroll = window.innerWidth + window.scrollMaxX;
		        yScroll = window.innerHeight + window.scrollMaxY;
		    } else {
		        if (document.body.scrollHeight > document.body.offsetHeight) { // all but Explorer Mac    
		            xScroll = document.body.scrollWidth;
		            yScroll = document.body.scrollHeight;
		        } else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari    
		            xScroll = document.body.offsetWidth;
		            yScroll = document.body.offsetHeight;
		        }
		    }
		    var windowWidth, windowHeight;
		    if (self.innerHeight) { // all except Explorer    
		        if (document.documentElement.clientWidth) {
		            windowWidth = document.documentElement.clientWidth;
		        } else {
		            windowWidth = self.innerWidth;
		        }
		        windowHeight = self.innerHeight;
		    } else {
		        if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode    
		            windowWidth = document.documentElement.clientWidth;
		            windowHeight = document.documentElement.clientHeight;
		        } else {
		            if (document.body) { // other Explorers    
		                windowWidth = document.body.clientWidth;
		                windowHeight = document.body.clientHeight;
		            }
		        }
		    }       
		    // for small pages with total height less then height of the viewport    
		    if (yScroll < windowHeight) {
		        pageHeight = windowHeight;
		    } else {
		        pageHeight = yScroll;
		    }
		    // for small pages with total width less then width of the viewport    
		    if (xScroll < windowWidth) {
		        pageWidth = xScroll;
		    } else {
		        pageWidth = windowWidth;
		    }
		    
		    return {
		    	'pageWidth': pageWidth, 
		    	'pageHeight': pageHeight, 
		    	'windowWidth': windowWidth, 
		    	'windowHeight': windowHeight 
		    };
		},
		
		/**
         * @desc 判断是否有横向滚动条
         * @param {obj} 对象
		 * @private
         */		
		isScrollX: function (obj) {
			var scrollX = false;
			if(obj[0]){
				 if(obj[0].tagName == "BODY"){
					   var elems = [document.documentElement, document.body];
					   for (var i = 0; i < elems.length; i++) {
					     var o = elems[i];
					     var sl = o.scrollLeft;
					     o.scrollLeft += (sl > 0) ? -1 : 1;
					     o.scrollLeft !== sl && (scrollX = scrollX || true);
					     o.scrollLeft = sl;
					   }
//					 if(document.body.scrollWidth > document.body.clientWidth){
//						 scrollX = true;
//					 }else{
//						 scrollX = false;
//					 }
				 }else{
					 if(obj.width() > obj[0].clientWidth){
						 scrollX = true;
					 }
				 }
			 }
			 return scrollX;
		},
		
		/**
         * @desc 判断是否有纵向滚动条
         * @param {obj} 对象
		 * @private
         */			
		isScrollY: function (obj) {
			var scrollY = false;
			if(obj[0]){
				 if(obj[0].tagName == "BODY"){
					   var elems = [document.documentElement, document.body];
					   for (var i = 0; i < elems.length; i++) {
						 var o = elems[i];
					     var st = o.scrollTop;
					     o.scrollTop += (st > 0) ? -1 : 1;
					     o.scrollTop !== st && (scrollY = scrollY || true);
					     o.scrollTop = st;
					   }

//					 if(document.body.scrollHeight > jazz.util.windowHeight()){
//						 scrollY = true;
//					 }else{
//						 scrollY = false;
//					 }
				 }else{
					 if(obj.height() > obj[0].clientHeight){
						 scrollY = true;
					 }
					 //alert("obj.scrollHeight="+obj.scrollHeight  +" obj.clientHeight="+obj.clientHeight +" obj.offsetHeight="+obj.offsetHeight);
				 }
			 }
			 return scrollY;				 
		},
		
		resizeWH: function(forcecalculate){
			this._resizeWidth(forcecalculate);
			this._resizeHeight(forcecalculate);
		}
		
	});	
});
