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
            activeindex: 0,
            
    		/**
			 *@type boolean
			 *@desc 是否允许关闭第一个标签
			 *@default false
			 */
            isclosefirst: false,
            
    		/**
			 *@type Number
			 *@desc tab标题的总宽度或高度超出所在容器时的显示类型
			 *@default null
    		 */
    		overflowtype: 2,
            
            /**
			 *@type String
			 *@desc tab标题所摆放的位置，'top'标题在上边 'left'标题在左边 'bottom'标题在下边 'right'标题在右边
			 *@default 'top'
			 */
            orientation:'top',    		
    		
			/**
			 *@type boolean
			 *@desc 否显示容器的边框
			 *@default true
			 */               
            showborder: true,
            
            /**
			 *@type boolean
			 *@desc 是否显示tab页签上的关闭按钮
			 *@default false
			 */
            showtabclose: true,
    		
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
            
            this.options.selected = this.options.activeindex;

            this.element.addClass('jazz-tabview').addClass('jazz-tabview-' + this.options.orientation);
            
            this._tabsCreateContent();
            
            this._showborder();
            
            this.isInner = false;
        },
        
        /**
         * @desc  绑定tabpanel各类响应事件
         * @private
         */
        _init: function(){
        	
        	this.tabsinner = this.navContainer.parent();
        	this.tabswrapContainer = this.tabsinner.parent();
            this.tabsHeader = this.tabswrapContainer.parent();
            
            this.tablefttitleheight = this.navContainer.find("li").outerHeight(true);
            this.tablefttitlewidth = this.navContainer.find("li").outerWidth(true);
            
        	//设置容器的大小
            this._compSize();
            
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
	        	this.panelContainer.addClass("jazz-panel-border");
			} else {
				this.panelContainer.removeClass("jazz-panel-border");
			}
        }, 
        
		/**
         * @desc 组件宽度
		 * @private
         */        
        _width: function(forcecalculate){
        	this._super(forcecalculate);
        	if(this.isInner){
                this._innerWidth();
        	}
        },
        
        _innerWidth: function(){
        	if((this.options.orientation == 'top' || this.options.orientation == 'bottom')){
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
        _height: function(forcecalculate){
        	var height = this.options.height;
        	if(height=="auto" || height=="-1"){
        		this.element.height("auto");
                this.panelContainer.children().height('auto');
        	}else{
        		this._super(forcecalculate);
        		if(this.iscalculateheight){
        			if(this.isInner){
        				this._innerHeight();
        			}        			
        		}
        	}
        },

        _innerHeight: function(){
        	this.tabsHeaderHeight = this.tabsHeader.outerHeight(true);
        	if((this.options.orientation == 'top' || this.options.orientation == 'bottom')){
        		if(this.options.calculateinnerheight != '-1'){
                	this.panelContainer.outerHeight(this.options.calculateinnerheight - this.tabsHeaderHeight);
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
            	this.panelContainer.outerHeight(this.options.calculateinnerheight);
            }
        },
        
        
        /**
         * @desc  创建组件内部元素结构
         * @private
         */
        _tabsCreateContent: function(){
        	
        	var element = this.element;
        	/**
             * ul列表是标题栏
             * 第一个div是panel的容器
             */
            this.navContainer = element.find('ul:first');
            this.panelContainer = element.find('>div:first');
            
        	//装饰tab标题部分
            this.navContainer.addClass('jazz-tabview-nav')
            				 .wrap('<div class="jazz-tabview-header"></div>')
            				 .wrap('<div class="jazz-tabview-wrap"></div>')
            				 .wrap('<div class="jazz-tabview-in"></div>');
            
            var li = this.navContainer.find("li");
            
            li.addClass('jazz-tabview-default').eq(this.options.activeindex)
										       .addClass('jazz-tabview-selected jazz-state-active');
            
			if(!!this.options.tabtitlewidth){
				li.outerWidth(this.options.tabtitlewidth);
			}
            
            if((this.options.orientation == 'top' || this.options.orientation == 'bottom')){
            	li.addClass("jazz-tabview-tabalign-" + this.options.tabalign);
            }
            
            li.find("a").addClass("nav-content-middle");
            
            this._tabShowtabclose();
            
            //装饰tab内容区域
            this.panelContainer.addClass('jazz-tabview-panels').children().addClass('jazz-tabview-panel');
            this.panelContainer.children('div.jazz-tabview-panel:not(:eq(' + this.options.activeindex + '))').hide();
        },
        
        _tabShowtabclose: function(index){
        	var flag = true ;
        	this.tempright = 0 ,this.templeft = 0;
        	if(index || index===0){
        		flag = false;
        	}else{
        		index = 0;
        	}
        	
        	var isclosefirst = this.options.isclosefirst, first = "";
        	if(!isclosefirst){
        		first = "-first";
        	}
        	if(this.options.showtabclose && flag){
        		var lilist = this.navContainer.find("li");
        		var str = "";
        		$.each(lilist,function(i,liObj){
        			if(i==0){
        				str = "<div class ='nav-content-left'></div><div class ='nav-content-right"+first+"'><span class='jazz-icon-close nav-close'></span></div>";
        			}else{
        				str = "<div class ='nav-content-left'></div><div class ='nav-content-right'><span class='jazz-icon-close nav-close'></span></div>";
        			}
        			$(liObj).children().before(str);
        		});
            }else if(this.options.showtabclose){
        		if(index===0 || isclosefirst){
        			this.navContainer.find("li:eq("+index+")").children().before("<div class ='nav-content-left'></div><div class ='nav-content-right-first'></div>");
        			this.navContainer.find("li:eq("+index+1+") .nav-content-right-first").addClass("nav-content-right").removeClass("nav-content-right-first").append("<span class='jazz-icon-close nav-close'></span>");
        		}else{
        			this.navContainer.find("li:eq("+index+")").children().before("<div class ='nav-content-left'></div><div class ='nav-content-right'><span class='jazz-icon-close nav-close'></span></div>");
        		}
            }
        	
        	if(!isclosefirst){
        		this.tempright = this.navContainer.find("li:eq("+index+") .nav-content-right-first").outerWidth(true);
        	}else{
        		this.tempright = this.navContainer.find("li:eq("+index+") .nav-content-right").outerWidth(true);
        	}
        	this.templeft = this.navContainer.find("li:eq("+index+") .nav-content-left").outerWidth(true);
        	
            this.tempwidth =  this.templeft + this.tempright;
            
            this.tempwidth = this.navContainer.find("li:eq("+index+")").outerWidth() - this.tempwidth;
            if(this.options.showtabclose && flag){
            	this.navContainer.find("li a ").outerWidth(this.tempwidth);
            }else{
            	this.navContainer.find("li:eq("+index+") a ").outerWidth(this.tempwidth);
            }
        },
        
        /**
         * @desc  设置当标签页比较多时的显示方式
         * @private
         */
        _tabsPositionInit: function(){
        	var orientation = this.options.orientation,
        	    overflowtype = this.options.overflowtype;
        	
        	if(orientation=='top' || orientation=='bottom'){
        		var line = "<div class='jazz-tabview-header-" + orientation + "'></div>";
        		this.navContainer.addClass('jazz-tabview-wrap-more');
	       		if(orientation=='bottom'){
	       			if(this.isorientation){
	       				this.element.append(this.tabsHeader);
		       			this.tabsHeader.prepend(line);	
	       				this.isorientation = false;
	       			}
	       		}else{
	       			if(this.isorientation){
	       				this.tabsHeader.append(line);	
	       				this.isorientation = false;
	       			}
	       		}
	       		
	       		if(overflowtype==1){
	       			this.tabs_more_horizon = $('<div class="tabs-more-horizon"></div>').appendTo(this.tabsHeader);
	       			this._initSelectableTopAndBottom();
            	}else if(overflowtype==2){
            		//左右滚动方式
            		this._computeTabsHeader();
            	}
	       		
        	}else if(orientation=='left' || orientation=='right'){
        		this.navContainer.addClass('jazz-tabview-wrap-hmore');
            	if(this.isorientation){
            		if(this.iscalculateheight){
            			this.tabsHeader.outerHeight(this.options.calculateinnerheight);
            			this.tabswrapContainer.outerHeight(this.tabsHeader.height());
            		}
            		
            		this.tabsHeader.prepend("<div class='jazz-tabview-header-" 
				            				+ orientation 
				            				+ "' style='height:" 
				            				+ this.tabsHeader.height() 
				            				+ "px;'></div>");
            		
            		//设置标题栏宽度
            		this.tabsHeader.width(this.tabswrapContainer.find('li:first').width() 
                			+ this.tabsHeader.find('.jazz-tabview-header-' 
                				+ orientation).outerWidth(true));
                	
       				this.isorientation = false;
       			}
            	
            	if(overflowtype==1){
            		this.tabs_more_vertical = $('<div class="tabs-more-vertical"></div>').appendTo(this.tabsHeader);
            		this._initSelectableLeftAndRight();
            	}else if(overflowtype==2){
            		this._computeTabsHeader();
            	}
            	
        	}
        },
        
        /**
         * @desc  设置标签标题导航部分
         * @private 
         */
        _computeTabsHeader: function(){
        	var that = this,
        		orientation = this.options.orientation,
        		tempTabsWidth = 0,
        		tempTabsHeight = 0;
        	//上下结构
        	if(orientation == 'top' || orientation == 'bottom'){
        		tempTabsWidth = this.navContainer.outerWidth(true) - this.navContainer.outerWidth();
        		this.navContainer.find('li:visible').each(function(i){
	        		 tempTabsWidth += $(this).outerWidth(true);
	        	});
        		//this.tabsinner.scrollLeft(0);
	       		if(tempTabsWidth > this.tabsinner.width()){
	       			if(this._isResetMoreTab()){
	            		return;
	            	}

	       			var tabheight = this.tabsinner.height();
	       			if(!this.tabs_scroller_left){
	       				this.tabswrapContainer.addClass("jazz-tabview-wrap-level");
	       				
	       				this.tabs_scroller_left = $('<a class="tabs-scroller-left"></a>').appendTo(this.tabsHeader);
	       				this.tabs_scroller_left.css({
	       					'height': tabheight + 'px', 
	       					'display': 'block'
	       				});
	       				this.tabs_scroller_right = $('<a class="tabs-scroller-right"></a>').appendTo(this.tabsHeader);
	       				this.tabs_scroller_right.css({
	       					'height' : tabheight + 'px',
	       					'display': 'block'
	       				});
	       			}
	       			
	       			if(this.options.tabalign=='right'){
	       				this.tabsinner.scrollLeft(this.navContainer.outerWidth(true));
	       			}
	       			if(orientation == "bottom"){
	       				this.tabs_scroller_left.css({'bottom': 0, 'top': 'auto'});
	       				this.tabs_scroller_right.css({'bottom': 0, 'top': 'auto'});
	       			}
	       			
	       			//var wrapwidth = this.tabswrapContainer.outerWidth(true) - 32;
	       			//this.tabswrapContainer.css({"width":wrapwidth, "margin":"0px 16px"});
	       			
	       			this.tabsinner.outerHeight(tabheight);
		       		
	       		}else{
	       			if(this.tabs_scroller_left){
	       				this.tabs_scroller_left.remove();
	       				this.tabs_scroller_right.remove();
	       				this.tabs_scroller_left = null;
	       				this.tabs_scroller_right = null;
	       				this.tabswrapContainer.removeClass("jazz-tabview-wrap-level");
	       			}
	       			
	       			if(this.options.tabalign=='right'){
	       				this.tabsinner.scrollLeft(this.navContainer.outerWidth(true));
	       			}
	       		}
        	}else if(orientation=='left' || orientation=='right'){
        		
	        	this.navContainer.find('li:visible').each(function(i){
	   				tempTabsHeight += $(this).outerHeight(true);
	       		});
	        	
	        	//this.tabsinner.scrollTop(0);
	       		if(tempTabsHeight > this.tabsinner.height()){
	       			if(this._isResetMoreTab()){
	            		return;
	            	}
	       			
	       			if(!this.tabs_scroller_top){
	       				this.tabs_scroller_top = $('<a class="tabs-scroller-top"></a>').prependTo(this.tabsHeader);
	       			}
	       			if(!this.tabs_scroller_bottom){
	       				this.tabs_scroller_bottom = $('<a class="tabs-scroller-bottom"></a>').appendTo(this.tabsHeader);
	       			}
	       			
	       			//设置navContainer height5000px 实现滚动tabs
	       			//that.navContainer.addClass('jazz-tabview-wrap-hmore');
	       			this.tabsinner.height(that.tabsHeader.height() 
	       					- this.tabs_scroller_top.height()
	       					- this.tabs_scroller_bottom.height())
	       				.width(this.options.tabtitlewidth 
	       						|| (this.tabsHeader.width() 
	       								- this.tabs_more_horizon.width()));
	       			
	       			this.tabs_scroller_top.css({"width" : (this.options.tabtitlewidth.replace(/px/g, '') + 'px' 
	       						|| this.tabsHeader.width()), "display":"block"});
	       			this.tabs_scroller_bottom.css({
	       				"width" : (this.options.tabtitlewidth.replace(/px/g, '') + 'px' 
	       						|| this.tabsHeader.width()),
	       				"display" : "block"});
	       		}else{
	       			if(this.tabs_scroller_top){
	       				this.tabs_scroller_top.remove();
	       				this.tabs_scroller_top = null;
	       			}
	       			if(this.tabs_scroller_bottom){
	       				this.tabs_scroller_bottom.remove();
	       				this.tabs_scroller_bottom = null;
	       			}
	       			//that.navContainer.css('height','auto');
	       			this.tabsinner.height(this.tabsHeader.height());
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
        		return true;
        	}
        	
        	return false;
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
        	this.tabsHeader.off("click.left", ".tabs-scroller-left")
        		.on("click.left", ".tabs-scroller-left", function(){
	        		var scrollleft = that.tabsinner.scrollLeft() + scrollwidth;
		       		if(that.options.tabalign=='right'){
		       			that.tabsinner.scrollLeft(scrollleft);
		       		}else{
			       		var tabsTotalWidth = 0;
		            	that.navContainer.children('li:visible').each(function(i){
		       				tabsTotalWidth += $(this).outerWidth();
			       		});
		            	var scrollTotalWidth = tabsTotalWidth - that.tabsinner.width();
			       		if(scrollleft > scrollTotalWidth){
		        			that.tabsinner.scrollLeft(scrollTotalWidth);
		        		}else{
		        			that.tabsinner.scrollLeft(scrollleft);
		        		}
		       		}
        		});
        	
        	that.tabsHeader.off("click.right")
        		.on("click.right", ".tabs-scroller-right" ,function(){
	        		var scrollWidth = that.tabsinner.scrollWidth;
	        		var scrollleft = that.tabsinner.scrollLeft()- scrollwidth;
	        		if(that.options.tabalign=='right'){
		       			var tabsTotalWidth = 0;
		            	that.navContainer.find('li:visible').each(function(i){
		       				tabsTotalWidth += $(this).outerWidth();
			       		});
		        		if(that.tabsinner.scrollLeft() < (scrollWidth - tabsTotalWidth)){
		        			scrollleft = scrollWidth - tabsTotalWidth;
		        		}
		       		}
		       		that.tabsinner.scrollLeft(scrollleft);
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
            this.navContainer.off('click.tab', 'li').on('click.tab', 'li', function(e) {
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
            if(this.options.orientation == 'top' || this.options.orientation == 'bottom'){            	
            	this._bindTopAndBottomScrollEvent();
            }else if(this.options.orientation == 'left' || this.options.orientation == 'right'){            	
            	this._bindLeftAndRightScrollEvent();
            }
        },
        
        /**
         * @desc tab页选择方法
		 * @param {index} tab标签页的索引值 
		 * @example $('XXX').tabpanel('select','index');
         */
        select: function(index) {
            this.options.selected = index;
            var newPanel = this.panelContainer.children().eq(index),
           		headers = this.navContainer.children(),
           		oldHeader = headers.filter('.jazz-state-active'),
           		newHeader = headers.eq(newPanel.index()),
           		oldPanel = this.panelContainer.children('.jazz-tabview-panel:visible');

            oldHeader.removeClass('jazz-tabview-selected jazz-state-active');
            oldPanel.hide();
            //oldPanel.css({"z-index":"-1000"});
        	
        	newHeader.removeClass('jazz-tabview-hover').addClass('jazz-tabview-selected jazz-state-active');
        	newPanel.show();        	
        	//newPanel.css({"z-index":"0"});

        	this._event('change', null, index);
            
        	this.options.activeindex = index;
        	
            var nowPosition = this._isOnScreen(index);
            if(nowPosition){
            	this.tabsinner[nowPosition['direction']](nowPosition['scrollNum']);
            }
        },

        /**
         * @desc tab页删除方法
		 * @param {index} tab页的索引值,索引值从0开始 
		 * @example $('XXX').tabpanel('remove', 'index');
         */
        remove: function(index) {
        	var len = this.getLength();
    	    if(index < 0 || index > len ){return false;}
    	   
            var header = this.navContainer.children().eq(index),
            	panel = this.panelContainer.children().eq(index);

            this._event('close', null, index);

            header.remove();
            panel.remove();

            var activeindex = this.options.activeindex;
            if(activeindex == index){
            	index = index - 1;
            	if(len == 0){ return ;}
            	if(index < 0){
            		index = 0;
            	}            	
            }else{
            	if(activeindex < index){
            		index = activeindex;
            	}else{
            		index = activeindex - 1;
            	}
            }
            
            this.select(index); 
                       
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
		 *		tabindex: '', 新增加tab页索引值,确定新增加页面的位置,如果放在最后一个可以不设置该值
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
        		index = taboption['tabindex'];
        	
        	var tabli = "<li class='jazz-tabview-default'><a style='width:"+this.tempwidth+"px;' class='nav-content-middle' href='" 
        			+ prefix + taboption['tabid'] + "'>" 
        			+ taboption['tabtitle'] 
        			+ "</a></li>",
        		tabdiv = "<div class='jazz-tabview-panel' id='" 
        			+ prefix + taboption['tabid'] + "'>" 
        			+ (taboption['tabcontent'] || "") 
        			+ "</div>";
    		if(taboption['taburl']){
    			tabdiv = "<div class='jazz-tabview-panel' id='" 
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
        	if(index < 0 ){
        		index = 0;
        	}
        	if(index > tabLength){
        		index = tabLength;
        	}    		
        	
        	//检查添加tab标签页
        	if(!this._beforeAddTab(taboption)){
        		return;
        	}
        	//在指定位置插入tab
        	if(index == tabLength){
        		$this.navContainer.children(":last").after(tabli);
        		$this.panelContainer.append(tabdiv);
        	}else{        		
        		$this.navContainer.children(':eq('+index+')').before(tabli);
        		$this.panelContainer.children(':eq('+index+')').before(tabdiv);
        	}
        	
        	if((this.options.orientation == 'top' || this.options.orientation == 'bottom')){
        		this.navContainer.children(':eq('+ index +')').addClass("jazz-tabview-tabalign-" + this.options.tabalign);
        		this.panelContainer.children(':eq('+ index +')').css({'width':this.options.calculateinnerwidth, 
            		"height":this.options.calculateinnerheight - this.tabsHeaderHeight});
            }else{
            	this.panelContainer.children(':eq('+ tabIndex +')').css({'width':this.options.calculateinnerwidth - this.tabsHeaderWidth, 
            		"height":this.options.calculateinnerheight});
            }
    		
    		this._tabShowtabclose(index);
    		
    		this.select(index);
    		
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
			   var tabli = "<li><a style='width:"+this.tempwidth+"px;' class='nav-content-middle' href='#"+tabid+"'>"+tabname+"</a></li>";
			   var tabdiv = "<div id='"+tabid+"'>"+tabname+"</div>";
			   if(taburl!=null&&taburl!=''){
				   tabdiv = "<div id='"+tabid+"'><iframe src='"+taburl+"' id='"+this.options.frameName+"' name='iframepage' frameBorder='0' scrolling='yes' width='100%' height='100%'></iframe></div>";
			   }
			   this.navContainer.children(':eq('+index+')').before(tabli);
			   this.panelContainer.children(':eq('+index+')').before(tabdiv);
			   
			   tabli = this.navContainer.find('li:eq('+ index +')');
			   tabdiv = this.panelContainer.find('div:eq('+ index +')');
			   
			   this.navContainer.children(':not(:eq(' + index + '))').removeClass('jazz-tabview-selected  jazz-state-active');
			   this.panelContainer.children(':not(:eq(' + index + '))').css({"z-index":"-1000"});
			   tabli.addClass('jazz-tabview-default jazz-tabview-selected jazz-state-active');
			   
			   if((this.options.orientation == 'top' || this.options.orientation == 'bottom')){
				   this.navContainer.children(':eq('+ (index) +')').addClass("jazz-tabview-tabalign-" + this.options.tabalign);
				   tabdiv.addClass('jazz-tabview-panel').css({'width':this.options.calculateinnerwidth, 
               		"height":this.options.calculateinnerheight - this.tabsHeaderHeight});
               }else{
            	   tabdiv.addClass('jazz-tabview-panel').css({'width':this.options.calculateinnerwidth - this.tabsHeaderWidth, 
               		"height":this.options.calculateinnerheight});
               }
			   
			   this._tabShowtabclose(index);

			   this.select(tablength); 
			   
			   if(this.options.overflowtype == 2){
	    			this._computeTabsHeader();
	    		}else if(this.options.overflowtype == 1){
	    			this._computeTabsHeaderMore();
	    		}
		   }
       },

       /**
        * @desc  检查当前显示的标签页标题是否在屏幕浏览器可视区域内
        * @returns {Boolean}
        */
       _isOnScreen: function(index){
    	    //当前标签title的位置相对于tabpanel的容器位置
    	    var liobj = this.navContainer.children(':eq('+index+')'),
    	    	orientation = this.options.orientation,
    	    	tabalign = this.options.tabalign,
    	    	scrollNum = 0;
    	    var inneroffset = this.tabsinner.offset();
	    	var tabsinner = {
    	        top: inneroffset.top,
    	        left: inneroffset.left
    	    };
	    	tabsinner.right = tabsinner.left + this.tabsinner.width();
	    	tabsinner.bottom = tabsinner.top + this.tabsinner.height();
    	     
    	    var li = liobj.offset();
    	    li.right = li.left + liobj.outerWidth(true);
    	    li.bottom = li.top + liobj.outerHeight(true);
    	    
    	    
    	    if(orientation == "left" || orientation == "right"){
    	    	//超出在下方
        	    if(tabsinner.bottom < li.bottom){
        	    	scrollNum = this.tabsinner.scrollTop() + li.bottom - tabsinner.bottom;

        	    	return {
        	    		scrollNum: scrollNum,
        	    		direction: "scrollTop"
        	    	};
        	    }
        	    //超出在上方
        	    if(tabsinner.top > li.top){
    	    		scrollNum = tabsinner.top;
    	    		return {
    	    			scrollNum: scrollNum,
    	    			direction: "scrollTop"
    	    		};
        	    }
    	    }
    	    
    	    if(orientation == "top" || orientation == "bottom"){
    	    	//超出在左侧
        	    if(tabsinner.left > li.left){
        	    	scrollNum = tabsinner.left;
        	    	if(tabalign == 'right'){
        	    		scrollNum = (this.tabsinner.scrollLeft() - Math.abs(tabsinner.left - li.left));
        	    	}
        	    	return {
        	    		scrollNum: scrollNum,
        	    		direction: "scrollLeft"
        	    	};
        	    }
        	    //超出在右侧
        	    if(tabsinner.right < li.right || this.tabsinner.scrollLeft() > 0){
        	    	scrollNum = this.tabsinner.scrollLeft() + (li.right - tabsinner.right);
    				if(tabalign == 'right'){
        	    		scrollNum = this.tabsinner.scrollLeft() + (li.right - tabsinner.right);
        	    	}
        	    	return {
        	    		scrollNum: scrollNum,
        	    		direction: "scrollLeft"
        	    	};
        	    }
    	    }
    	    
    	    //当前视野内可见
    	    return false;
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
    	   }else if('showtabclose' == key){
    		   this.options.showtabclose = value;
    		   $(".nav-content-left").remove();
			   $(".nav-content-right").remove();
			   $(".nav-content-right-first").remove();
    		   this._tabShowtabclose();
    	   }
       },
       
       _setOptions: function( options ) {
           this._super( options );
       },

       /**
        * @desc 获取tab页个数方法
        * @return number
		* @example $('XXX').tabpanel('getLength');
        */
       getLength: function() {
           return this.navContainer.children().length;
       },

       /**
        * @desc 获取当前活动的tab页
        * @return number
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
