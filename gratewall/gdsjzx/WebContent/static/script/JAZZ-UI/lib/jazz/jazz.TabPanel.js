(function( $, factory ){
	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'jazz.BoxComponent'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 0.5
	 * @name jazz.tabpanel
	 * @description 一种基础性的tab容器。
	 * @constructor
	 * @extends jazz.container
	 * @requires
	 * @example $('#div_id').tabpanel();
	 */
    $.widget("jazz.tabpanel", $.jazz.boxComponent, {
    	
    	options: /** @lends jazz.tabpanel# */ {
    		/**
    		 * @type  String
    		 * @desc  组件类型 
    		 * @default  'tabpanel'
    		 */      
    		vtype: "tabpanel",

    		/**
			 *@type Number
			 *@desc 当前活动的tab页
			 *@default 0
			 */
            activeindex:0,
            
			/**
			 *@type boolean
			 *@desc 否显示容器的边框
			 *@default true
			 */               
            showborder: true,
            
    		/**
			 *@type Number
			 *@desc tab标题的总宽度或高度超出所在容器时的显示类型
			 *@default null
    		 */
    		overflowtype: 2,
    		
    		/**
			 *@type Number
			 *@desc tab标题内容的宽度
			 *@default auto 自适应宽度
			 */
    		tabtitlewidth: null,
    		
    		/**
    		 *@type  String
    		 *@desc  标签标题水平对齐方向 'left'左对齐 'right'右对齐
    		 *@default  'left'
    		 */
    		tabalign: 'left',
            
            /**
			 *@type String
			 *@desc tab页方向
			 *@default 'top'
			 */
            orientation:'top',
            
            /**
			 *@type boolean
			 *@desc 是否显示tab页签上的关闭按钮
			 *@default false
			 */
            showtabclose: true,
            	
            // callbacks
            
            /**
			 *@desc 当切换tab页签触发
			 *@param {event,index} 事件,tab坐标
			 *@example $("#div_id").tabpanel({change: function( event, index ){  <br/>} <br/>});
			 */
            change: null ,
    		
    		/**
			 *@desc 当关闭tab页签触发
			 *@param {event,index} 事件,tab坐标
			 *@example $("#div_id").tabpanel({close: function( event, index ){  <br/>} <br/>});
			 */
    		close: null
    		
        },
        
        /** @lends jazz.tabpanel */
        
        /**
         * @desc 创建组件
		 * @private
         */
        _create: function() {
        	
        	this._super();
        	
        	this._showborder();
        	
            var element = this.element;
            this.options.selected = this.options.activeindex;

            //1.页面依然由用户写明ul和tabs-panels的内容，然后由组件封装
            element.addClass('jazz-tabview jazz-widget jazz-hidden-container')
            	   .addClass('jazz-tabview-' + this.options.orientation);
            
            /**
             * ul列表是标题栏
             * 第一个div是panel的容器
             */
            this.navContainer = element.find('ul:first');
            this.panelContainer = element.find('>div:first');
            
            
            
            //装饰tab标题部分
            this.navContainer.find("li")
            	.addClass('jazz-tabview-default')
            	.css({'width':this.options.tabtitlewidth})
            				 .eq(this.options.activeindex)
            				 .addClass('jazz-tabview-selected jazz-state-active')
            				 .css({'z-index':'3'});
            this.navContainer
            	.find('li:last')
            	.after('<div class="jazz-clearfix"></div>')
            	.end()
            	.addClass('jazz-tabview-nav jazz-helper-reset jazz-helper-clearfix')
            	.wrap('<div class="jazz-tabview-header"></div>')
            	.wrap('<div class="jazz-tabview-wrap"></div>');
            
            this.navContainer.find("li:eq(0)").children()
            .before("<div class ='nav-content-left'></div><div class ='nav-content-right-first'></div>");
            
            this.navContainer.find("li:gt(0)").children()
	            .before("<div class ='nav-content-left'></div><div class ='nav-content-right'><span class='jazz-icon-close nav-close'></span></div>");
            this.templeft = this.navContainer.find("li .nav-content-left").width() , this.tempright = this.navContainer.find("li .nav-content-right").width();
            this.tempwidth =  this.templeft + this.tempright;
            
            if(this.templeft != 0){
            	this.tempwidth = this.navContainer.find("li").width() - this.tempwidth;
            	this.navContainer.find("li a ").css({"width":this.tempwidth}).addClass("nav-content-middle");
            }
            
            //装饰tab内容区域
            this.panelContainer
            	.addClass('jazz-tabview-panels')
            	.children()
            	.addClass('jazz-tabview-panel jazz-widget-content')
            	.end()
            	.find('div.jazz-tabview-panel:not(:eq(' + this.options.activeindex + '))')
            	.css({"margin-left":"-3000px","z-index":"-1000"})
	            .end();
			
            this.tabswrapContainer = element.find('.jazz-tabview-wrap');
            this.tabsHeader = element.find('.jazz-tabview-header');
            
            if((this.options.orientation == 'top' 
            		|| this.options.orientation == 'bottom')){
            	this.navContainer
            		.css({'margin':'0 2px 0 0'})
            		.find("li")
            		.addClass("jazz-tabview-tabalign-" + this.options.tabalign);
            }
            
            this.isInner = false;
        },
        
        /**
         * @desc  绑定tabpanel各类响应事件
         * @private
         */
        _init: function(){
        	
        	//设置容器的大小
            this._compSize();
            
            this.tablefttitleheight = this.navContainer.find("li").outerHeight(true);
            this.tablefttitlewidth = this.navContainer.find("li").outerWidth(true);
            
            //tabs 超出宽度后展现形式  
            this.isorientation = true;
            this._tabsPositionInit();
            
            this._innerWidth();
            this._innerHeight();
			this.isInner = true;
			
			//绑定tab事件
			this._bindEvents();
        },
        
        /**
         * @desc 初始化容器大小
         * @private
         */
        _compSize: function(){
        	this._width();
        	this._height();
        },
        
        /**
         * @desc 判断是否显示边框
         * @private
         */          
        _showborder: function(){
	        if (this.options.showborder) {
	        	this.element.addClass("jazz-panel-border");
			} else {
				this.element.removeClass("jazz-panel-border");
			}
        }, 
        
		/**
         * @desc 组件宽度
		 * @private
         */        
        _width: function(){
        	this._super();
        	if(this.isInner){
                this._innerWidth();
        	}
        },
        
        _innerWidth: function(){
        	if((this.options.orientation == 'top' 
        		|| this.options.orientation == 'bottom')){
        		if(this.isInner){
                	this.tabswrapContainer.width(this.options.calculateinnerwidth);
        		}
            	this.panelContainer.outerWidth(this.options.calculateinnerwidth);
            }else{
            	this.tabsHeaderWidth = this.tabsHeader.outerWidth(true);
            	var tabwidth = this.options.calculateinnerwidth - this.tabsHeaderWidth; 
            	if(jazz.isIE(7) || jazz.isIE(6)){
            		tabwidth = tabwidth - 5;
    			}
            	this.panelContainer.outerWidth(tabwidth);
            }
        },
        
        /**
         * @desc 组件高度
         * @private
         */        
        _height: function(){
        	this._super(); 
        	if(this.isInner){
                this._innerHeight();
        	}
        },
        
        _innerHeight: function(){
        	this.tabsHeaderHeight = this.tabsHeader.outerHeight(true);
        	if((this.options.orientation == 'top' 
        		|| this.options.orientation == 'bottom')){
        		if(this.options.calculateinnerheight != '-1'){
                	this.panelContainer.height(this.options.calculateinnerheight - this.tabsHeaderHeight);
        		}
            }else{
            	if(this.isInner){
            		this.tabsHeader.outerHeight(this.options.calculateinnerheight);
                	this.tabswrapContainer.outerHeight(this.tabsHeaderHeight);
        		}
            	if(this.options.calculateinnerheight == '-1'){
            		this.options.calculateinnerheight = this.tabsHeader.outerHeight(true);
            	}
            	this.tabsHeader.find('.jazz-tabview-header-' + this.options.orientation).outerHeight(this.tabsHeaderHeight);
            	this.panelContainer.height(this.options.calculateinnerheight);
            }
        },
        
        
        
        /**
         * @desc  设置当标签页比较多时的显示方式
         * @private
         */
        _tabsPositionInit: function(){
        	var that = this;
        	if(that.options.orientation=='top'||that.options.orientation=='bottom'){
	       		if(that.options.orientation=='bottom'){
	       			if(this.isorientation){
	       				that.element.append(that.tabsHeader);
		       			that.tabsHeader.prepend("<div class='jazz-tabview-header-" 
					            				+ that.options.orientation 
					            				+ "'></div>");	
	       				this.isorientation = false;
	       			}
	       		}else{
	       			if(this.isorientation){
	       				that.tabsHeader.append("<div class='jazz-tabview-header-" 
						       					+ that.options.orientation 
						       					+ "'></div>");	
	       				this.isorientation = false;
	       			}
	       		}
	       		
	       		if(that.options.overflowtype==1){
            		that.tabs_more_horizon = $('<div class="tabs-more-horizon"></div>')
            			.appendTo(that.tabsHeader);
	            	that._initSelectableTopAndBottom();
            	}else if(that.options.overflowtype==2){
            		//左右滚动方式
        			that._computeTabsHeader();
            	}
	       		
        	}else if(that.options.orientation=='left' 
        		|| that.options.orientation=='right'){
            	if(this.isorientation){
            		if(this.iscalculateheight){
            			that.tabsHeader.outerHeight(this.options.calculateinnerheight);
                		that.tabswrapContainer.outerHeight(that.tabsHeader.height());
            		}
            		
            		that.tabsHeader.prepend("<div class='jazz-tabview-header-" 
				            				+ that.options.orientation 
				            				+ "' style='height:" 
				            				+ this.tabsHeader.height() 
				            				+ "px;'></div>");
            		
            		//设置标题栏宽度
                	that.tabsHeader.width(that.tabswrapContainer.find('li:first').width() 
                			+ that.tabsHeader.find('.jazz-tabview-header-' 
                				+ that.options.orientation).outerWidth(true));
                	
       				this.isorientation = false;
       			}
            	
            	if(that.options.overflowtype==1){
            		that.tabs_more_vertical = $('<div class="tabs-more-vertical"></div>')
            			.appendTo(that.tabsHeader);
	            	that._initSelectableLeftAndRight();
            	}else if(that.options.overflowtype==2){
            		that._computeTabsHeader();
            	}
            	
        	}
        },
        
        /**
         * @desc  设置标签标题导航部分
         * @private 
         */
        _computeTabsHeader: function(){
        	var that = this,
        		orientation = that.options.orientation,
        		tempTabsWidth = 0,
        		tempTabsHeight = 0;
        	//上下结构
        	if(orientation == 'top'
        		|| orientation == 'bottom'){
        		tempTabsWidth = 
        			that.navContainer.outerWidth(true)
        				- that.navContainer.outerWidth();
	        	that.navContainer
	        		.find('li:visible')
	        		.each(function(i){
	        			tempTabsWidth += $(this).outerWidth(true);
	        		});
	        	that.tabswrapContainer.scrollLeft(0);
	       		if(tempTabsWidth > that.tabswrapContainer.width()){
	       			if(!this._isResetMoreTab()){
	            		return;
	            	}
	       			//设置navContainer width5000px 实现不折行，滚动tabs
	       			that.navContainer.addClass('jazz-tabview-wrap-more');
	       			
	       			!that.tabs_scroller_left && (that.tabs_scroller_left = $('<a class="tabs-scroller-left"></a>')
	       				.appendTo(that.tabsHeader)
	       				.css({
	       					'height' : that.tabswrapContainer.height() + 'px', 
	       					'display': 'block'}));
	       			!that.tabs_scroller_right && (that.tabs_scroller_right = $('<a class="tabs-scroller-right"></a>')
	       				.appendTo(that.tabsHeader)
	       				.css({
	       					'height' : that.tabswrapContainer.height()+'px', 
	       					'display': 'block'}));
	       			if(that.options.tabalign=='right'){
	       				that.tabswrapContainer
	       					.scrollLeft(that.navContainer.outerWidth(true));
	       			}
	       			if(orientation == "bottom"){
	       				that.tabs_scroller_left.css({'bottom': 0, 'top': 'auto'});
	       				that.tabs_scroller_right.css({'bottom': 0, 'top': 'auto'});
	       			}
	       			
	       			var wrapwidth = that.tabswrapContainer.outerWidth(true) - 32;
		       		that.tabswrapContainer.css({"width":wrapwidth,"margin":"0px 16px"});
		       		
	       		}else{
	       			//that.navContainer.css("width","auto");;
	       			if(that.tabs_scroller_left){
	       				that.tabs_scroller_left.remove();
	       				that.tabs_scroller_left = null;
	       			}
	       			if(that.tabs_scroller_right){
	       				that.tabs_scroller_right.remove();
	       				that.tabs_scroller_right = null;
	       			}
	       			
	       			if(that.options.tabalign=='right'){
	       				that.tabswrapContainer
	       					.scrollLeft(that.navContainer.outerWidth(true));
	       			}
	       			
	       			
		       		that.tabswrapContainer.css({"margin":"0px"});
	       		}
        	}else if(that.options.orientation=='left' 
        		|| this.options.orientation=='right'){
        		
	        	that.navContainer.find('li:visible').each(function(i){
	   				tempTabsHeight += $(this).outerHeight(true);
	       		});
	        	
	        	that.tabswrapContainer.scrollTop(0);
	       		if(tempTabsHeight > that.tabswrapContainer.height()){
	       			if(!this._isResetMoreTab()){
	            		return;
	            	}
	       			!that.tabs_scroller_top && (that.tabs_scroller_top = $('<a class="tabs-scroller-top"></a>')
   						.prependTo(that.tabsHeader));
	       			!that.tabs_scroller_bottom && (that.tabs_scroller_bottom = $('<a class="tabs-scroller-bottom"></a>')
	       				.appendTo(that.tabsHeader));
	       			//设置navContainer height5000px 实现滚动tabs
	       			that.navContainer.addClass('jazz-tabview-wrap-hmore');
	       			that.tabswrapContainer.height(that.tabsHeader.height() 
	       					- that.tabs_scroller_top.height() 
	       					- that.tabs_scroller_bottom.height())
	       				.width(that.options.tabtitlewidth 
	       						|| (that.tabsHeader.width() 
	       								- that.tabs_more_horizon.width()));
	       			
	       			that.tabs_scroller_top.css({
	       				"width" : (that.options.tabtitlewidth.replace(/px/g, '') + 'px' 
	       						|| that.tabsHeader.width()), 
	       				"display":"block"});
	       			that.tabs_scroller_bottom.css({
	       				"width" : (that.options.tabtitlewidth.replace(/px/g, '') + 'px' 
	       						|| that.tabsHeader.width()),
	       				"display" : "block"});
	       			//that.tabswrapContainer.css({"margin-left": "30px", "margin-right": "30px"});
	       		}else{
	       			if(that.tabs_scroller_top){
	       				that.tabs_scroller_top.remove();
	       				that.tabs_scroller_top = null;
	       			}
	       			if(that.tabs_scroller_bottom){
	       				that.tabs_scroller_bottom.remove();
	       				that.tabs_scroller_bottom = null;
	       			}
	       			//that.navContainer.css('height','auto');
	       			that.tabswrapContainer.height(that.tabsHeader.height());
	       		}
        	}
        },
        
        /**
         * @desc  设置当标签过多时
         * 	更多操作的显示位置和样式
         * @private
         */
        _computeTabsHeaderMore: function(){
        	var that=this;
        	if(that.options.orientation=='top'||that.options.orientation=='bottom'){
	    		var tempTabsWidth = that.navContainer.outerWidth(true)-that.navContainer.outerWidth();
	        	that.navContainer.find('li:visible').each(function(i){
	       			tempTabsWidth += $(this).outerWidth(true);
	       		});
	       		if(tempTabsWidth>=that.tabswrapContainer.width()){
	       			that.navContainer.width(5000+"px");
	       			that.tabswrapContainer.width(that.options.tabtitlewidth || (that.tabsHeader.width()-that.tabs_more_horizon.width()));
	       			that.tabs_more_horizon.css({'height':that.tabswrapContainer.height()+'px','display':'inline-block','*display':'inline','zoom':'1'});
	       			
	       			/******************弹出div 显示超过了的tab标签******************************/
	       			if(that.tabsMoreDisplayDiv&&that.tabsMoreDisplayDiv.length>1){
	       				that.tabsMoreDisplayDiv.children().remove();
	       			}else{
		       			that.tabsMoreDisplayDiv = $('<div style="width:70px;position:absolute;display:none;"></div>').appendTo(document.body);
	       			}
	       			that.navContainer.clone().css('width','auto').appendTo(that.tabsMoreDisplayDiv);
	       			
	       			//为li绑定选中事件
	       			this._bindMoreHorizonEvents();
	       		}else{
	       			that.navContainer.css("width","auto");;
	       			that.tabswrapContainer.width(that.tabsHeader.width());
	       			that.tabs_scroller_right.css({'display':'none'});
       			}
	        }else if(that.options.orientation=='left'||this.options.orientation=='right'){
	    		var tempTabsHeight = 0;
	        	that.navContainer.find('li').each(function(i){
	       			if($(this).css("display")!="none"){
	   					tempTabsHeight += $(this).outerHeight();
	   				}
	       		});
	       		if(tempTabsHeight>that.tabswrapContainer.height()){
	       			that.navContainer.height(5000+"px");
	       			that.tabswrapContainer.width(that.options.tabtitlewidth || (that.tabsHeader.width()-that.tabs_more_horizon.width()));
	       			that.tabs_more_vertical.css({"width":that.tabsHeader.width(),"display":"block"});
	       			
	       			/******************弹出div 显示超过了的tab标签******************************/
	       			if(that.tabsMoreDisplayDiv&&that.tabsMoreDisplayDiv.length>1){
	       				that.tabsMoreDisplayDiv.children().remove();
	       			}else{
		       			that.tabsMoreDisplayDiv = $('<div style="width:70px;position:absolute;display:none;"></div>').appendTo(document.body);
	       			}
	       			that.navContainer.clone().css('height','auto').appendTo(that.tabsMoreDisplayDiv);
	       			
	       			//为li绑定选中事件
	       			this._bindMoreVerticalEvents();
	       		}else{
	       			that.navContainer.css('height','auto');
	       			that.tabswrapContainer.height(that.tabsHeader.height());
	       			that.tabs_more_vertical.css("display","none");
	       		}
	        }
        },
        
        /**
         * @desc 检查是否需要重置更多标签
         * @returns {Boolean} false 没有生成更多dom节点 true 已经生成更多dom节点
         */
        _isResetMoreTab: function(){
        	if(this.tabsHeader.children('div[class^="tabs-scroller"]').length){
        		return false;
        	}
        	
        	return true;
        },
        
        /**
         * @desc  初始化上下结构的标签页
         * @private
         */
        _initSelectableTopAndBottom: function(){
        	var that=this;
        	
    		var tempTabsWidth = that.navContainer.outerWidth(true)-that.navContainer.outerWidth();
        	that.navContainer.find('li').each(function(i){
       			if($(this).css("display")!="none"){
   					tempTabsWidth += $(this).outerWidth(true);
   				}
       		});
       		if(tempTabsWidth>=that.tabswrapContainer.width()){
       			that.navContainer.width(5000+"px");
       			that.tabswrapContainer.width(that.tabsHeader.width()-that.tabs_more_horizon.width());
       			that.tabs_more_horizon.css({'height':that.tabswrapContainer.height()+'px','display':'inline-block','*display':'inline','zoom':'1'});
       			
       			/******************弹出div 显示超过了的tab标签******************************/
       			that.tabsMoreDisplayDiv = $('<div style="width:70px;position:absolute;display:none;"></div>').appendTo(document.body);
       			that.navContainer.clone().css('width','auto').appendTo(that.tabsMoreDisplayDiv);
       			
       			//为li绑定选中事件
       			this._bindMoreHorizonEvents();
       			/******************弹出div 显示超过了的tab标签******************************/
       			if(that.options.tabalign=='right'){
       				that.tabswrapContainer.scrollLeft(that.navContainer.outerWidth(true));
       			}
       		}else{
       			that.navContainer.css("width","auto");;
       			that.tabswrapContainer.width(that.tabsHeader.width());
       			that.tabs_scroller_right.css({'display':'none'});
       			
       			if(that.options.tabalign=='right'){
       				that.tabswrapContainer.scrollLeft(that.navContainer.outerWidth(true));
       			}
       		}
        },
        
        /**
         * @desc  初始化左右结构的标签页
         * @private
         */
        _initSelectableLeftAndRight: function(){
        	var that=this;
        	
    		var tempTabsHeight = 0;
        	that.navContainer.find('li').each(function(i){
       			if($(this).css("display")!="none"){
   					tempTabsHeight += $(this).outerHeight();
   				}
       		});
       		if(tempTabsHeight>that.tabswrapContainer.height()){
       			that.navContainer.height(5000+"px");
       			that.tabswrapContainer.height(that.tabsHeader.height()-that.tabs_more_vertical.height());
       			that.tabs_more_vertical.css({"width":that.tabsHeader.width(),"display":"block"});
       			
       			/******************弹出div 显示超过了的tab标签******************************/
       			that.tabsMoreDisplayDiv = $('<div style="width:70px;position:absolute;display:none;"></div>').appendTo(document.body);
       			that.navContainer.clone().css('height','auto').appendTo(that.tabsMoreDisplayDiv);
       			
       			//为li绑定选中事件
       			this._bindMoreVerticalEvents();
       			/******************弹出div 显示超过了的tab标签******************************/
       		}else{
       			that.navContainer.css('height','auto');
       			that.tabswrapContainer.height(that.tabsHeader.height());
       			that.tabs_more_vertical.css("display","none");
       		}
        	
        },
        
        /**
         * @desc  绑定左右结构的更多操作的点击事件
         * @private
         */
        _bindMoreHorizonEvents: function(){
        	var that=this;
        	
        	that.tabs_more_horizon.off('click.more').on('click.more',function(){
        		that.tabsMoreDisplayDiv.css({left:'', top:''}).position({
			                my: 'left top',
			                at: 'right top',
			                of: that.tabs_more_horizon
			            }).show();
        	});
        	
        	that.tabsMoreDisplayDiv.find("li")
        		.off('click.morerli')
        		.on('click.moreli', function(e) {
                    var element = $(this);

                    if($(e.target).is(':not(.jazz-icon-close)')) {
                        var index = element.index();
                        if(!element.hasClass('jazz-state-disabled') && index != that.options.selected) {
                            that._selectMoreLi(index);
                            that.select(index);
                        }
                        
                        //滚动并显示navContainer
                        var el = that.navContainer.find('li:eq('+index+')');
                        var tabsTotalWidth = $(el).outerWidth(true);
		            	$(el).prevAll().each(function(i){
			       			if($(this).css("display")!="none"){
		       					tabsTotalWidth += $(this).outerWidth(true);
		       				}
			       		});
			       		var scrollTotalWidth = tabsTotalWidth - that.tabswrapContainer.width();
			       		if(scrollTotalWidth>0){
			       			that.tabswrapContainer.scrollLeft(scrollTotalWidth);
			       		}else{
			       			that.tabswrapContainer.scrollLeft(0);
			       		}
                    }
                    e.preventDefault();
                });

            //Closable tabs
            this.tabsMoreDisplayDiv.find('li .jazz-icon-close').off('click.tab')
                .on('click.tab', function(e) {
                	var index = $(this).parent().parent().index();
                    $this.remove(index);
                    e.preventDefault();
                });
                
           $(document).off('click.jazz-tabpanel-other').on('click.jazz-tabpanel-other', function (e) {
                if($(that.tabsMoreDisplayDiv).is(":hidden")) {
                    return;
                }
                var target = $(e.target);
                if(target.is($(that.tabsMoreDisplayDiv))||$(that.tabsMoreDisplayDiv).has(target).length > 0) {
                    return;
                }
                if(target.is(that.tabs_more_horizon)||that.tabs_more_horizon.has(target).length > 0) {
                    return;
                }
                var offset = $(that.tabsMoreDisplayDiv).offset(); 
                if(e.pageX < offset.left ||
                    e.pageX > offset.left + $(that.tabsMoreDisplayDiv).width() ||
                    e.pageY < offset.top ||
                    e.pageY > offset.top + $(that.tabsMoreDisplayDiv).height()) {
                    $(that.tabsMoreDisplayDiv).hide();
                }
        	});
        	
        },
        
        /**
         * @desc  绑定上下结构的更多操作的点击事件
         * @private
         */
        _bindMoreVerticalEvents: function(){
        	var that=this;
        	
        	that.tabs_more_vertical.off('click.more').on('click.more',function(){
        		that.tabsMoreDisplayDiv.css({left:'', top:''}).position({
			                my: 'left top',
			                at: 'right top',
			                of: that.tabs_more_vertical
			            }).show();
        	});
        	
        	that.tabsMoreDisplayDiv.find("li")
        		.off('click.moreli')
        		.on('click.moreli', function(e) {
                    var element = $(this);

                    if($(e.target).is(':not(.jazz-icon-close)')) {
                        var index = element.index();
                        if(!element.hasClass('jazz-state-disabled') && index != that.options.selected) {
                            that._selectMoreLi(index);
                            that.select(index);
                        }
                        
                        //滚动并显示navContainer
                        var el = that.navContainer.find('li:eq('+index+')');
                        var tabsTotalWidth = $(el).outerHeight(true);
		            	$(el).prevAll().each(function(i){
			       			if($(this).css("display")!="none"){
		       					tabsTotalWidth += $(this).outerHeight(true);
		       				}
			       		});
			       		//alert(tabsTotalWidth);
			       		var scrollTotalWidth = tabsTotalWidth - that.tabswrapContainer.height();
			       		if(scrollTotalWidth>0){
			       			that.tabswrapContainer.scrollTop(scrollTotalWidth);
			       		}else{
			       			that.tabswrapContainer.scrollTop(0);
			       		}
                    }
                    e.preventDefault();
                });

            //Closable tabs
            this.tabsMoreDisplayDiv.find('li .jazz-icon-close').off('click.tab')
                .on('click.tab', function(e) {
                	var index = $(this).parent().parent().index();
                    $this.remove(index);
                    e.preventDefault();
                });
                
           $(document.body).bind('mousedown.jazz-tabpanel-other', function (e) {
                if($(that.tabsMoreDisplayDiv).is(":hidden")) {
                    return;
                }
                var target = $(e.target);
                if(target.is($(that.tabsMoreDisplayDiv))||$(that.tabsMoreDisplayDiv).has(target).length > 0) {
                    return;
                }
                if(target.is(that.tabs_more_vertical)||that.tabs_more_vertical.has(target).length > 0) {
                    return;
                }
                var offset = $(that.tabsMoreDisplayDiv).offset(); 
                if(e.pageX < offset.left ||
                    e.pageX > offset.left + $(that.tabsMoreDisplayDiv).width() ||
                    e.pageY < offset.top ||
                    e.pageY > offset.top + $(that.tabsMoreDisplayDiv).height()) {
                    $(that.tabsMoreDisplayDiv).hide();
                }
        	});
        	
        },
        
        /**
         * @desc tab页选择方法
		 * @param {index} tab页码数从0开始第1个tab 
		 * @example $('#div_id').tabpanel('select','index');
         */
        _selectMoreLi: function(index) {
           var headers = this.tabsMoreDisplayDiv.find('li'),
           oldHeader = headers.filter('.jazz-state-active'),
           newHeader = headers.eq(index);
           //aria
           oldHeader.attr('aria-expanded', false);
           newHeader.attr('aria-expanded', true);

           oldHeader.removeClass('jazz-tabview-selected jazz-state-active');
           newHeader.removeClass('jazz-tabview-hover').addClass('jazz-tabview-selected jazz-state-active');
       },
        
       /**
        * @desc  绑定上下结构的标签页的滚动事件
        * @private
        */
       _bindTopAndBottomScrollEvent: function(){
        	var that = this,
        		scrollwidth = this.tablefttitlewidth;
        	that.tabsHeader.off("click.left", ".tabs-scroller-left")
        		.on("click.left", ".tabs-scroller-left", function(){
	        		var scrollleft = that.tabswrapContainer.scrollLeft() + scrollwidth;
		       		if(that.options.tabalign=='right'){
		       			that.tabswrapContainer.scrollLeft(scrollleft);
		       		}else{
			       		var tabsTotalWidth = 0;
		            	that.navContainer.children('li:visible').each(function(i){
		       				tabsTotalWidth += $(this).outerWidth();
			       		});
			       		var scrollTotalWidth = tabsTotalWidth - that.tabswrapContainer.width();
			       		if(scrollleft > scrollTotalWidth){
		        			that.tabswrapContainer.scrollLeft(scrollTotalWidth + 24);
		        		}else{
		        			that.tabswrapContainer.scrollLeft(scrollleft);
		        		}
		       		}
        		});
        	
        	that.tabsHeader.off("click.right")
        		.on("click.right", ".tabs-scroller-right" ,function(){
	        		var wrapcontainerScrollWidth = that.tabswrapContainer[0].scrollWidth;
	        		var scrollleft = that.tabswrapContainer.scrollLeft()-scrollwidth;
	        		if(that.options.tabalign=='right'){
		       			var tabsTotalWidth = 0;
		            	that.navContainer.find('li:visible').each(function(i){
		       				tabsTotalWidth += $(this).outerWidth();
			       		});
		        		if(that.tabswrapContainer.scrollLeft() < (wrapcontainerScrollWidth-tabsTotalWidth)){
		        			scrollleft = wrapcontainerScrollWidth - tabsTotalWidth - 24;
		        		}
		       		}
		       		that.tabswrapContainer.scrollLeft(scrollleft);
        		});
        },
        
        /**
         * @desc  绑定左右结构的标签页的滚动事件
         * @private
         */
        _bindLeftAndRightScrollEvent: function(){
        	var that = this,
    			scrollheight = this.tablefttitleheight,
       			singleLiHeight = 0;
        	that.tabsHeader.off("click.top")
        		.on("click.top", ".tabs-scroller-top", function(){
	        		var tabsTotalHeight = 0;
	            	that.navContainer.children('li:visible').each(function(i){
	            		tabsTotalHeight += $(this).outerHeight(true);
	   					singleLiHeight = $(this).outerHeight(true);
		       		});
		       		var scrollTotalHeight = tabsTotalHeight - that.tabswrapContainer.height();
	        		var scrolltop = that.tabswrapContainer.scrollTop()+scrollheight;
	        		if(scrolltop>scrollTotalHeight){
	        			that.tabswrapContainer.scrollTop(scrollTotalHeight+singleLiHeight);
	        		}else{
	        			that.tabswrapContainer.scrollTop(scrolltop);
	        		}
        		});
        	that.tabsHeader.off("click.bottom")
        		.on("click.bottom", ".tabs-scroller-bottom", function(){
        			var scrolltop = that.tabswrapContainer.scrollTop()-scrollheight;
        			that.tabswrapContainer.scrollTop(scrolltop);
        		});
        },
        
        /**
         * @desc 绑定事件
         * @private
		 * @example this._bindEvents();
         */
        _bindEvents: function() {
            var $this = this;
            //Tab header events
            this.navContainer
            	.off('click.tab', 'li')
                .on('click.tab', 'li', function(e) {
                    var element = $(this);

                    if($(e.target).is(':not(.jazz-icon-close)')) {
                        var index = element.index();

                        if(!element.hasClass('jazz-state-disabled') && index != $this.options.selected) {
                            $this.select(index);
                        }
                    }

                    e.preventDefault();
                });

            //Closable tabs
            this.navContainer
            	.off('click.tabclose', 'li .jazz-icon-close')
                .on('click.tabclose', 'li .jazz-icon-close', function(e) {
                    var index = $(this).parent().parent().index();
                    $this.remove(index);
                    e.preventDefault();
                });
            
            //绑定标签过多时滚动的事件
            if(this.options.orientation == 'top' 
            	|| this.options.orientation == 'bottom'){            	
            	this._bindTopAndBottomScrollEvent();
            }else if(this.options.orientation == 'left' 
            	|| this.options.orientation == 'right'){            	
            	this._bindLeftAndRightScrollEvent();
            }
        },
        
        /**
         * @desc tab页选择方法
		 * @param {index} tab页码数从0开始第1个tab 
		 * @example $('#div_id').tabpanel('select','index');
         */
        select: function(index) {
            this.options.selected = index;
            var newPanel = this.panelContainer.children().eq(index),
           		headers = this.navContainer.children(),
           		oldHeader = headers.filter('.jazz-state-active'),
           		newHeader = headers.eq(newPanel.index()),
           		oldPanel = this.panelContainer.children('.jazz-tabview-panel:visible'),
           		$this = this;

            oldHeader.removeClass('jazz-tabview-selected jazz-state-active');
        	oldPanel.css({"margin-left":"-3000px","z-index":"-1000"});
        	
        	newHeader.removeClass('jazz-tabview-hover').addClass('jazz-tabview-selected jazz-state-active');
        	newPanel.css({"margin-left":"0px","z-index":"0"});

        	this._event('change', null, index);
            
            var nowPosition = this._isOnScreen(index);
            if(nowPosition != true){
            	this.tabswrapContainer[nowPosition['direction']](nowPosition['scrollNum']);
            }
        },

        /**
         * @desc tab页删除方法
		 * @param {index} tab页码数从0开始第1个tab 
		 * @example $('#div_id').tabpanel('remove','index');
         */
        remove: function(index) {
    	    if(index < 0 || index > this.getLength() ){return false;}
    	   
            var header = this.navContainer.children().eq(index),
            	panel = this.panelContainer.children().eq(index);

            this._event('close', null, index);

            header.remove();
            panel.remove();

            //active next tab if active tab is removed
            if(index == this.options.selected) {
            	var newIndex = this.options.selected == this.getLength() 
            		? (this.options.selected - 1) 
            		: this.options.selected;
            	this.select(newIndex);
            }
            if(index == this.options.activeindex){
            	if(index == 0){
            		this.select(0);
            	}else if(index == this.getLength()){
            		this.select(index-1);
            	}
            }else{
            	this.select(this.options.activeindex); 
            }
           
            if(this.options.overflowtype == 2){
            	this._computeTabsHeader();
            }else if(this.options.overflowtype == 1){
            	this._computeTabsHeaderMore();
            }
        },
       
        /**
         * @desc 新增tab标签页之前验证tab的相关定义是否满足添加的条件
         * @param taboption  新增tab的相关定义
         * @returns {Boolean} true 通过校验 false 未通过校验
         */
        _beforeAddTab: function(taboption){
        	var prefix = "jazz_tabpanel_custom_";/*,
        		index = taboption['tabindex'] 
        			? taboption['tabindex'] 
        			: (this.getLength() - 1);*/
        	
        	if(!taboption['tabid']){
        		jazz.warn('参数中未找到属性[tabid]');
        		return false;
        	}
        	if(!taboption['tabtitle']){
        		jazz.warn('参数中未找到属性[tabtitle]');
        		return false;
        	}
        		
        	//如果存在相同的id
        	if(this.element.find("#" + prefix + taboption['tabid']).length){
        		jazz.warn('参数中未找到属性[tabtitle]');
        		return false;
        	}
        	
        	return true;
        },
        
        /**
         * @desc tab页添加方法
         * @param taboption tab页url路径
         * 	{
		 *		tabid: '',	tab页的id
		 *		tabtitle: '',	tab页的标题
		 *		tabindex: '',	tab的索引位置
		 *		taburl: '',	tab页的url
		 *		tabcontent: ''	tab页的内容
		 *	}
		 * <br> taburl是链接到指定地址文件
		 * <br> tabcontent是展示具体内容
		 * <br> taburl和tabcontent这两个参数只需要定义一个即可, 优先使用taburl
         * @example $('#div_id').tabpanel(taboption);
         */
        addTab: function(taboption) {
        	var $this = this,
        		prefix = "jazz_tabpanel_custom_",
        		tabLength = $this.getLength(),
        		index = taboption['tabindex'] ? taboption['tabindex'] : (tabLength + 1),
        		tabli = "<li class='jazz-tabview-default'><div class ='nav-content-left'></div><div class ='nav-content-right'><span class='jazz-icon-close nav-close'></span></div><a style='width:"+this.tempwidth+"px;' class='nav-content-middle' href='" 
        			+ prefix + taboption['tabid'] + "'>" 
        			+ taboption['tabtitle'] 
        			+ "</a><span style='display:none;' class='jazz-icon jazz-icon-close'></span></li>",
        		tabdiv = "<div class='jazz-tabview-panel jazz-widget-content' style='margin-left:-3000px;' id='" 
        			+ prefix + taboption['tabid'] + "'>" 
        			+ (taboption['tabcontent'] || "") 
        			+ "</div>";
    		if(taboption['taburl']){
    			tabdiv = "<div class='jazz-tabview-panel jazz-widget-content' style='margin-left:-3000px;' id='" 
    				+ prefix + taboption['tabid'] 
	    			+ "'><iframe src='" 
	    			+ taboption['taburl'] 
	    			+ "' id='" 
	    			+ prefix + taboption['tabid'] + "_" 
	    			+ $this.options.frameName 
	    			+ "' name='" + prefix + taboption['tabid'] 
	    			+ "_iframepage' frameBorder='0' scrolling='yes' width='100%'" 
	    			+ " height='100%'></iframe></div>";
    		}
    		//保证新增的tab索引在合理的位置
    		index = index > (tabLength + 1) ? (tabLength + 1) : index;
    		var tabIndex = index -1 ;
        	
        	//检查添加tab标签页
        	if(!this._beforeAddTab(taboption)){
        		return;
        	}
        	//在指定位置插入tab
        	if(index > tabLength){
        		$this.navContainer.children(":last").before(tabli);
        		$this.panelContainer.append(tabdiv);
        	}else{        		
        		$this.navContainer.children(':eq('+tabIndex+')').before(tabli);
        		$this.panelContainer.children(':eq('+tabIndex+')').before(tabdiv);
        	}
        	if((this.options.orientation == 'top' || this.options.orientation == 'bottom')){
        		this.navContainer.children(':eq('+ (index-1) +')').addClass("jazz-tabview-tabalign-" + this.options.tabalign);
        		this.panelContainer.children(':eq('+ (index-1) +')').css({'width':this.options.calculateinnerwidth, 
            		"height":this.options.calculateinnerheight - this.tabsHeaderHeight});
            }else{
            	this.panelContainer.children(':eq('+ (index-1) +')').css({'width':this.options.calculateinnerwidth - this.tabsHeaderWidth, 
            		"height":this.options.calculateinnerheight});
            }
        	
    		this.navContainer.children(':eq('+ tabIndex +')').css({
	    		'width': this.options.tabtitlewidth
	    	});
    		
    		$this.select(tabIndex);
    		
    		if(this.options.overflowtype == 2){
    			this._computeTabsHeader();
    		}else if(this.options.overflowtype == 1){
    			this._computeTabsHeaderMore();
    		}
        },
       
       /**
        * @desc tab页添加方法
		* @param {index} 新增tab页位置 从0开始第1个tab
		* @param {tabid} tab页id名称
		* @param {tabname} tab页name名称
		* @param {taburl} tab页url路径
		* @example $('#div_id').tabpanel('addTabCustom','index','tabid','tabname','taburl');
        */
       addTabCustom: function(index,tabid,tabname,taburl) {
    	   
    	   if($("#"+tabid).length>0){
			   var tabindex = 0;
			   this.panelContainer.children().each(function(i){
				   var id = $(this).attr("id");
				   if(tabid==id){
					   tabindex = i;
				   }
			   });
			   this.select(tabindex);
		   }else{
	    	   var tablength = this.getLength();
	    	   if(index>tablength){
	    		   index = tablength-1;
	    	   }
	    	   if(index<0){
	    		   index = 0;
	    	   }
			   var tabli = "<li><div class ='nav-content-left'></div><div class ='nav-content-right'><span class='jazz-icon-close nav-close'></span></div><a style='width:"+this.tempwidth+"px;' class='nav-content-middle' href='#"+tabid+"'>"+tabname+"</a></li>";
			   var tabdiv = "<div id='"+tabid+"'>"+tabname+"</div>";
			   if(taburl!=null&&taburl!=''){
				   tabdiv = "<div id='"+tabid+"'><iframe src='"+taburl+"' id='"+this.options.frameName+"' name='iframepage' frameBorder='0' scrolling='yes' width='100%' height='100%'></iframe></div>";
			   }
			   this.navContainer.children(':eq('+index+')').before(tabli);
			   this.panelContainer.children(':eq('+index+')').before(tabdiv);
			   
			   tabli = this.navContainer.find('li:eq('+ index +')');
			   tabdiv = this.panelContainer.find('div:eq('+ index +')');
			   
			   this.navContainer.children(':not(:eq(' + index + '))').removeClass('jazz-tabview-selected  jazz-state-active');
			   this.panelContainer.children(':not(:eq(' + index + '))').css({"margin-left":"-3000px","z-index":"-1000"});
			   tabli.addClass('jazz-tabview-default jazz-tabview-selected jazz-state-active')
			   		.css('width',this.options.tabtitlewidth);
			   
			   if((this.options.orientation == 'top' || this.options.orientation == 'bottom')){
				   this.navContainer.children(':eq('+ (index) +')').addClass("jazz-tabview-tabalign-" + this.options.tabalign);
				   tabdiv.addClass('jazz-tabview-panel jazz-widget-content').css({'width':this.options.calculateinnerwidth, 
               		"height":this.options.calculateinnerheight - this.tabsHeaderHeight});
               }else{
            	   tabdiv.addClass('jazz-tabview-panel jazz-widget-content').css({'width':this.options.calculateinnerwidth - this.tabsHeaderWidth, 
               		"height":this.options.calculateinnerheight});
               }
			   
			   var $this = this;
			   tabli.off('click.tab')
	               .on('click.tab', function(e) {
	                   var element = $(this);
	                   if($(e.target).is(':not(.jazz-icon-close)')) {
	                       var index = element.index();
	                       if(!element.hasClass('jazz-state-disabled') && index != $this.options.selected) {
	                           $this.select(index);
	                       }
	                   }
	
	                e.preventDefault();
	           });

			   this.select(tablength); 
			   this._computeTabsHeader();
		   }
       },

       /**
        * @desc  检查当前显示的标签页标题是否在屏幕浏览器可视区域内
        * @returns {Boolean}
        */
       _isOnScreen: function(index){
    	    //当前标签title的位置相对于tabpanel的容器位置
    	    var win = this.tabsHeader,
    	    	viewport = {
    	        	top : win.scrollTop(),
    	        	left : win.scrollLeft()
    	    	},
    	    	ele = this.navContainer.children(':eq('+index+')'),
    	    	eleHeight = ele.outerHeight(true),
    	    	eleWidth = ele.outerWidth(true),
    	    	orientation = this.options.orientation,
    	    	tabalign = this.options.tabalign,
    	    	scrollNum = 0;
    	    viewport.right = viewport.left + win.width();
    	    viewport.bottom = viewport.top + win.height();
    	     
    	    var bounds = ele.offset();
    	    bounds.right = bounds.left + ele.outerWidth();
    	    bounds.bottom = bounds.top + ele.outerHeight();
    	    
    	    
    	    if(/(left|right)/i.test(orientation)){
    	    	//超出在下方
        	    if(viewport.bottom < bounds.bottom){
        	    	scrollNum = this.tabswrapContainer.scrollTop() 
    					+ bounds.bottom
    					- viewport.bottom;

        	    	return {
        	    		scrollNum: scrollNum,
        	    		direction: "scrollTop"
        	    	};
        	    }
        	    //超出在上方
        	    if(viewport.top > bounds.top){
    	    		scrollNum = viewport.top;
    	    		
    	    		return {
    	    			scrollNum: scrollNum,
    	    			direction: "scrollTop"
    	    		};
        	    }
    	    }
    	    
    	    if(/(top|bottom)/i.test(orientation)){
    	    	//超出在左侧
        	    if(viewport.left > bounds.left){
        	    	scrollNum = viewport.left;
        	    	if(tabalign == 'right'){
        	    		scrollNum = (this.tabswrapContainer.scrollLeft() 
        	    				- Math.abs(viewport.left - bounds.left));
        	    	}
        	    	return {
        	    		scrollNum: scrollNum,
        	    		direction: "scrollLeft"
        	    	};
        	    }
        	    //超出在右侧
        	    if(viewport.right < bounds.right){
        	    	scrollNum = (this.tabswrapContainer.scrollLeft() 
        	    			+ bounds.right - viewport.right);
    				if(tabalign == 'right'){
        	    		scrollNum = (this.tabswrapContainer.scrollLeft() 
        	    				+ (bounds.right - viewport.right));
        	    	}
        	    	return {
        	    		scrollNum: scrollNum,
        	    		direction: "scrollLeft"
        	    	};
        	    }
    	    }
    	    
    	    //当前视野内可见
    	    return true;
    	     
    	},
       
       /**
        * @desc 动态设置属性
        * @param key
        * @param value
        */
       _setOption: function( key, value ) {
           this._super( key, value );
    	   if('width' == key || 'height' == key){
    		   this._tabsPositionInit();       		
    	   }
       },
       
       _setOptions: function( options ) {
           this._super( options );
       },

       /**
        * @desc 获取tab页个数方法
        * @return 返回tab页个数
		* @example $('#div_id').tabpanel('getLength');
        */
       getLength: function() {
           return this.navContainer.children().length - 1;
       },

       /**
        * @desc 获取当前活动的tab页
        * @return 返回当前所选tab页码
		* @example $('#div_id').tabpanel('getActiveIndex');
        */
       getActiveIndex: function() {
           return this.options.selected;
       },

       /**
        * @desc 
        * @param {panel}
        * @private
		* @example this._markAsLoaded('panel');
        */
       _markAsLoaded: function(panel) {
           panel.data('loaded', true);
       },

		/**
        * @desc 
        * @param {panel}
        * @private
		* @return 返回布尔值
		* @example this._isLoaded('panel');
        */                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
       _isLoaded: function(panel) {
           return panel.data('loaded') === true;
       },

       /**
        * @desc 关闭当前活动的tab页不可用
        * @param {index} tab页码数从0开始第1个tab
		* @example $('#div_id').tabpanel('disable','index');
        */
       disable: function(index) {
           this.navContainer.children().eq(index).addClass('jazz-state-disabled');
           this.navContainer.children().eq(index).find('.jazz-icon-close').off("click.tab").on("click.tab",function(e){
        	   e.stopPropagation();
           });
       },

       /**
        * @desc 打开当前活动的tab页可用
        * @param {index} tab页码数从0开始第1个tab 
	    * @example $('#div_id').tabpanel('enable','index');
        */
       enable: function(index) {
           this.navContainer.children().eq(index).removeClass('jazz-state-disabled');
       }

    });
});
