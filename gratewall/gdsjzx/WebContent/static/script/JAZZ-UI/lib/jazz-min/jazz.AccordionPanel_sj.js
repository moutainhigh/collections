(function( $, factory ){
	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'jazz.BoxComponent'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){	
	/**
	 * @version 0.5
	 * @name jazz.accordionpanel
	 * @description 
	 * @constructor
	 * @requires
	 * @example $('#div_id').accordionpanel();
	 */
    $.widget("jazz.accordionpanel", $.jazz.boxComponent,  {
       
    	options: /** @lends jazz.accordionpanel# */ {
    		
    		/**
        	 *@desc 组件类型
        	 */
        	vtype: 'accordionpanel',
        	
        	/**
			 *@type string
			 *@desc 当前活动面板的标题栏小图片
			 *@default 
			 */               
        	iconurl: "",
        	
        	/**
			 *@type string
			 *@desc 选中当前活动面板标题栏小图片
			 *@default 
			 */               
        	iconselect: "",
        	
			/**
			 *@type boolean
			 *@desc 否显示容器的边框
			 *@default true
			 */               
            showborder: true,
        	
        	 /**
			 *@type Number
			 *@desc 当前活动的accordionpanel页
			 *@default 0
			 */
             activeindex: 0,
             
             /**
  			 *@type Boolean
  			 *@desc 是否多个accordionpanel页展开
  			 *@default false
  			 */
             multiple: false
        },
        
        /** @lends jazz.accordionpanel */
        
        /**
         * @desc 创建组件
		 * @private
         */
        _create: function() {
        	this._super(); 
        	
        	this._showborder();
        	
        	if(this.options.multiple) {
            	var index = this.options.activeindex;
                this.options.activeindex = [];
                this._addToSelection(index);
            }
        
            this.element.addClass('jazz-accordion jazz-helper-reset');
            
            //加载内容区域
            var $this = this;
            this.childrens = this.element.children();
            $.each(this.childrens, function(i,child){
            	
            	var childitem = $(child).children();
            	$.each(childitem,function(i,item){
            		$(item).addClass("jazz-accordion-content-item");
            		if(i == childitem.length - 1){
            			$(item).css({"border-bottom":"none","margin-bottom":"5px"});
            		}
            	});
            	
            	$(child).addClass('jazz-accordion-content jazz-helper-reset jazz-widget-content');
            	var childname = $(child).attr("name");
            	var childid = $(child).attr("id");
            	var compId = $this.getCompId();
            	if(!childname){
            		$(child).attr("name", compId+"_content"+i);
            	}
            	if(!childid){
            		$(child).attr("id", compId+"_content"+i);
            	}
            	var headertext = $("<div class='jazz-accordion-headertext'></div>");
            	var iconurl = $(child).attr('iconurl');
            		
            	if(iconurl){
            		headertext.append('<span style="background:url('+iconurl+') 50% 50% no-repeat;" class="jazz-accordion-header-icon jazz-icon"></span>');
            	}else{
            		headertext.css('padding-left', '5px');
            	}
            	headertext.append("<span title='"+$(child).attr("title")+"' class='jazz-accordion-header-text'>" 
            		+ $(child).attr("title")+"</span>");
            	
            	var header = $("<div class='jazz-accordion-header jazz-helper-reset jazz-state-default'></div>");
            	if(!childname){
            		header.attr("name",compId +"_header"+i);
            	}else{
            		header.attr("name",childname);
            	}
            	if(!childid){
            		header.attr("id",compId +"_header"+i);
            	}else{
            		header.attr("id",childid);
            	}
            	if($this.options.activeindex == i) { 
            		header.addClass('jazz-state-active');
            		iconurl = $(child).attr('iconselect');
                	if(iconurl){
                		headertext.find('.jazz-accordion-header-icon')
                			.css('background-image', "url("+iconurl+")");
                	}
                }else{
                	$(child).addClass('jazz-helper-hidden');
                }
            	header.append(headertext);
            	var headermessage = $("<div class='jazz-accordion-message' id='"+compId+"'></div>");
            	header.append(headermessage);
            	$(child).before(header);
            	
            });
        },
        
        /**
         * @desc  绑定事件
         * @private
         */
        _init:function(){
        	
        	 //设置容器的大小
            this._compSize();
        	
        	this.headers = this.element.children('.jazz-accordion-header');
            this.panels = this.element.children('.jazz-accordion-content');
            this._bindEvents();

            var $this = this;
            if(this.options.multiple) {
            	$.each(this.options.activeindex,function(i,index){
            		var panel = $this.panels.eq(index);
            		$this._calscroll(panel);
            	});
            }else{
            	var panel = this.panels.eq(this.options.activeindex);
                this._calscroll(panel);
            }
           
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
        	var $this = this;
        	if(this.iscalculatewidth && !!this.headers){
        		$.each(this.headers,function(i,header){
        			$(header).outerWidth($this.options.calculateinnerwidth);
        			$(header).next($this.options.calculateinnerwidth);
        			var headertextobj = $(header).children(".jazz-accordion-headertext");
    		    	var iconurl = headertextobj.children(".jazz-accordion-header-icon");
    		    	var headerWidth = $this.options.calculateinnerwidth - headertextobj.next().outerWidth(true);
    		    	headertextobj.width(headerWidth);
    		    	//等加载完图标和文字，计算文字需要的实际宽度，防止过长溢出
    		    	var textWidth = $this.options.calculateinnerwidth;
    		    	
    		    	if(iconurl){
    		    		textWidth = headerWidth - headertextobj.children('.jazz-accordion-header-icon').outerWidth(true);
    		    	}
    		    	headertextobj.children('.jazz-accordion-header-text').outerWidth(textWidth);
            	});
        	}else{
        		$.each(this.childrens, function(i, child){
        			var childobj = $(child);
        			childobj.outerWidth($this.options.calculateinnerwidth);
        			childobj.prev($this.options.calculateinnerwidth);
        			var headerobj = childobj.prev();
        			var headertextobj = headerobj.children(".jazz-accordion-headertext");
    		    	var iconurl = childobj.attr('iconurl');
    		    	var headerWidth = $this.options.calculateinnerwidth - headertextobj.next().outerWidth(true);
    		    	if(headertextobj.children('.jazz-accordion-header-icon')){
    		    		headerWidth = headerWidth - 5;
    		    	}
    		    	headertextobj.width(headerWidth);
    		    	//等加载完图标和文字，计算文字需要的实际宽度，防止过长溢出
    		    	var textWidth = $this.options.calculateinnerwidth;
    		    	
    		    	if(iconurl){
    		    		textWidth = headerWidth - headertextobj.children('.jazz-accordion-header-icon').outerWidth(true);
    		    	}
    		    	
    		    	headertextobj.children('.jazz-accordion-header-text').outerWidth(textWidth);
    		    	headertextobj.append("<div style='clear: both;'></div>");
    		    });
        	}
        },
        
        /**
         * @desc 组件高度
         * @private
         */        
        _height: function(){
        	this._super(); 
        	if(this.iscalculateheight && !!this.panels){
        		var panel = this.panels.eq(this.options.activeindex);
                this._calscroll(panel);
        	}
        },
        
        /**
         * @desc 绑定事件
         * @private
		 * @example this._bindEvents();
         */
        _bindEvents: function() {
            var $this = this;

            this.headers.off('click.accordion')
            /*.on('mouseover.accordion', function() {
                var element = $(this);
                if(!element.hasClass('jazz-state-disabled')) {
                    element.addClass('jazz-state-hover');
                }
            }).on('mouseout.accordion', function() {
                var element = $(this);
                if(!element.hasClass('jazz-state-disabled')) {
                    element.removeClass('jazz-state-hover');
                }
            })*/.on('click.accordion', function(e) {
                var element = $(this);
                if(!element.hasClass('jazz-state-disabled')) {
                    var tabIndex = element.index() / 2;
                    $this.options.activeLastIndex = tabIndex;
                    if(element.hasClass('jazz-state-active')) {
                        $this.unselect(tabIndex);
                    }
                    else {
                        $this.select(tabIndex);
                    }
                }

                e.preventDefault();
            });
            /**针对IE6绑定hover事件**/
            if(jazz.isIE(6)){
            	this.headers
	                .hover(function() {
	                    var element = $(this);
	                    if(!element.hasClass('jazz-state-disabled')) {
	                        element.addClass('jazz-state-hover');
	                    }
	                }, function() {
	                    var element = $(this);
	                    if(!element.hasClass('jazz-state-disabled')) {
	                        element.removeClass('jazz-state-hover');
	                    }
	                });
            }
        },

        /**
         * @desc accordionpanel显示方法
         * @private
         * @param {panel} accordionpanel页面对象 
		 * @example this._show('panel');
         */
        _show: function(panel) {
        	var iconurl;
            if(!this.options.multiple) {
                var oldHeader = this.headers.filter('.jazz-state-active');
                //oldHeader.removeClass('jazz-state-active').next().slideUp();
                oldHeader.removeClass('jazz-state-active').next().hide();
            	oldHeader.each(function(i, header){
            		iconurl = $(header).next().attr('iconurl');
            		$(header).find('.jazz-accordion-header-icon')
        				.css('background-image', "url("+iconurl+")");
            	});
            }
            //activate selected
            var newHeader = panel.prev();
            newHeader.addClass('jazz-state-active');
            iconurl = panel.attr('iconselect');
        	if(iconurl){
        		newHeader.find('.jazz-accordion-header-icon')
        			.css('background-image', "url("+iconurl+")");
        	}
            
            //panel.slideDown('fast');
        	panel.show();
               
        },

        /**
         * @desc accordionpanel添加活动的页面方法
         * @private
         * @param {nodeId} accordionpanel页码数从0开始第1个accordionpanel  
		 * @example this._addToSelection('nodeId');
         */
        _addToSelection: function(nodeId) {
            this.options.activeindex.push(nodeId);
        },

        /**
         * @desc accordionpanel移除活动的页面方法
         * @private
         * @param {index} accordionpanel页码数从0开始第1个accordionpanel  
		 * @example this._removeFromSelection('index');
         */
        _removeFromSelection: function(index) {
            this.options.activeindex = $.grep(this.options.activeindex, function(r) {
                return r != index;
            });
        },
        
        /**
         * @desc accordionpanel页选择方法
		 * @param {index} accordionpanel页码数从0开始第1个accordionpanel 
		 * @example $('#div_id').accordionpanel('select','index');
         */
        select: function(index) {
        	var panel = this.panels.eq(index);
        	
        	this._calscroll(panel);
        	
            this._event('change', panel);
            
            //update state
            if(this.options.multiple){
            	this.options.activeindex = [];
                this._addToSelection(index);
            }else{
            	this.options.activeindex = index;
            }
            
            $.each(panel.parents("div[vtype]:first").getChildrenComponent(), function(){
    			var element = $(this);
				var vtype = element.attr("vtype");
				element.data(vtype)._resizeHeight();
    		});
            
            this._show(panel);
        },

        /**
         * @desc accordionpanel页不选择方法
 		 * @param {index} accordionpanel页码数从0开始第1个accordionpanel 
 		 * @example $('#div_id').accordionpanel('unselect','index');
         */
        unselect: function(index) {
            var panel = this.panels.eq(index),
            header = panel.prev();

            header.removeClass('jazz-state-active');
            var iconurl = panel.attr('iconurl');
            header.find('.jazz-accordion-header-icon')
				.css('background-image', "url("+iconurl+")");
            
            //panel.slideUp();
            panel.hide();
            this._removeFromSelection(index);

            /*var headerid = panel.attr("id").substring(0,panel.attr("id").lastIndexOf("_"));
        	var comobj = $("#"+headerid+"_header"+index);
        	$("#"+headerid).position({
            	my: 'left top',
            	at: 'right-5 top-5',
            	of: comobj,
            	collision:"flipfit"
            });*/
        },
        
        /**
         * @desc 计算滚动条  
	  	 * @private
	  	 * @param {panel} 当前活动页的内容区 
	  	 * @example this._calscroll(panel);
         */
        _calscroll: function(panel) {
        	var $this = this;
        	var allheight = this.options.calculateinnerheight , otherheight = 0;
        	if(this.options.multiple) {
        		this.element.css({"overflow":"auto"});
        	    var nScrollHight = this.element[0].scrollHeight; //滚动距离总长(注意不是滚动条的长度)
        	    var nScrollTop =this.element[0].scrollTop; //滚动到的当前位置
        	    if(nScrollTop + allheight >= nScrollHight){
            		$.each(this.headers,function(i,header){
            			var headertextobj = $(header).children(".jazz-accordion-headertext");
        		    	var iconurl = headertextobj.children(".jazz-accordion-header-icon");
        		    	var headerWidth = $this.options.calculateinnerwidth - headertextobj.next().outerWidth(true) - jazz.config.scrollWidth;
        		    	if(headertextobj.children('.jazz-accordion-header-icon')){
        		    		headerWidth = headerWidth - 5;
        		    	}
        		    	headertextobj.width(headerWidth);
        		    	//等加载完图标和文字，计算文字需要的实际宽度，防止过长溢出
        		    	var textWidth = $this.options.calculateinnerwidth;
        		    	
        		    	if(iconurl){
        		    		textWidth = headerWidth - headertextobj.children('.jazz-accordion-header-icon').outerWidth();
        		    	}
        		    	headertextobj.children('.jazz-accordion-header-text').outerWidth(textWidth);
                	});
        	    }
        	}else{
        		if(allheight != -1){
        			$.each(this.headers,function(i,head){
                    	otherheight = otherheight + $(head).outerHeight(true);
                    });
                	var panelheight = allheight-otherheight;
                    panel.outerHeight(panelheight).css({"overflow":"auto"});
        		}
        	}
        },
        
        /**
         * @desc 销毁组件  
         * @throws
	  	   * @private
	  	   * @example this._destroy();
         */
        _destroy: function() {
            
        }
        
    });

});
