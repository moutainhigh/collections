
(function($) {

	/**
	 * @version 0.5
	 * @name jazz.cardlayout
	 * @description 卡片布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 * @requires
	 * @example $('#panel_id').cardlayout();
	 */
    $.widget("jazz.cardlayout", $.jazz.containerlayout, {

        /** @lends jazz.cardlayout */

		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {config} 需要渲染的容器对象
		 * @throws
		 * @example
         */
        layout: function(cthis, container, config) {
        	var buttondisplay = this.options.cardlayoutObject.buttondisplay = config.buttondisplay;
        	var cardlayoutObject = this.options.cardlayoutObject;
        	cardlayoutObject.container = container;
        	cardlayoutObject.$this = this;
        	
        	var obj = container.children();
        	if(!obj[0]){
        		jazz.error('card layout is error!'); return false;
        	}
        	
        	var w = obj[0].width || $(obj[0]).outerWidth(true);
        	
        	var num = obj.size();
        	var ulWidth = num * w;
        	cardlayoutObject.width = w;
        	
        	cthis.element.addClass('jazz-cardpanel');  
        	
        	
        	var box = $('<div class="jazz-cardpanel-box"></div>').appendTo(container);
        	var content = $('<div class="jazz-cardpanel-content"></div>').appendTo(box);
        	var ul = $('<div class="jazz-cardpanel-ul" style="width:'+ulWidth+'px"></div>').appendTo(content);
			$.each(obj, function(i, el){
        		var li = $('<div class="jazz-cardpanel-li" style="width:'+cardlayoutObject.width+'px" >').appendTo(ul);
        		li.append(el);
        	});
			
			var h = ul.height();
        	
			if(buttondisplay===true){
			    this.paginator = $('<div></div>').appendTo(box);
				this.paginator.paginator({
					template: '{PageLinks}',
					rows: 1,
					totalRecords: obj.size(),
					click: function(e, ui){
						cardlayoutObject.point(ui.page, num);
					}
				});
				this.paginator.removeClass('jazz-widget-header');
				cardlayoutObject.paginator = this.paginator;
			}
			
			
			//重新计算高度
			$.each(obj, function(i, el){
				$(el).height(h - cardlayoutObject.height);
			});

			if(buttondisplay===true){
				this._buildButton(box, num);
			}

        	box.css({height: h});
        	content.css({width: cardlayoutObject.width, height: h - cardlayoutObject.height});
       	
        },
        
		/**
         * @desc 设置布局
         * @param {box} box容器
         * @param {num} 滚动页数
		 * @example
         */        
		_buildButton: function(box, num){
			var $this = this;
			var cardlayoutObject = this.options.cardlayoutObject;
        	this.leftButton = $('<a href="javascript:void(0);" target="_self"></a>').appendTo(box);
        	this.rightButton = $('<a href="javascript:void(0);" target="_self"></a>').appendTo(box);
        	this.leftButton.on('click', function(){
        		if(cardlayoutObject.activeIndex > 0){
        			cardlayoutObject.slide('l', num);
        		}
        	});
        	this.rightButton.on('click', function(){
        		if(cardlayoutObject.activeIndex < num-1){
        			cardlayoutObject.slide('r', num);
        		}
        	});
	        box.hover(function(){
	         	 if(cardlayoutObject.activeIndex > 0){
		        	 $this.leftButton.addClass('jazz-cardpanel-leftbtn');
	        	 }
	         	 if(cardlayoutObject.activeIndex < num-1){
	         		$this.rightButton.addClass('jazz-cardpanel-rightbtn');	         		 
	         	 }	        	 
            },function(){
          	     $this.leftButton.removeClass('jazz-cardpanel-leftbtn');
	        	 $this.rightButton.removeClass('jazz-cardpanel-rightbtn');
            });         	
		},
		
		/**
         * @desc 设置布局
         * @param {n} 页数
		 * @example
         */  		
		previousPage: function(n){
			return this.options.cardlayoutObject.slide('l', n);
		},
		
		/**
         * @desc 设置布局
         * @param {n} 页数
		 * @example
         */  		
		nextPage: function(n){
			return this.options.cardlayoutObject.slide('r', n);
		},
    
        options : {
    		/**
             * @desc 存储处理逻辑
             */        	
	        cardlayoutObject: {
	        	    height: 25,
		    		width: 400,
		    		activeIndex: 0,
		    		paginator: null,
		    		container: null,
		    		$this: null,
		    		buttondisplay: true,
		    	    point: function(index, num) {
		    	    	this.activeIndex = index;   
		    	    	this.switching(index, num);
		    	    },
		    	    slide: function(lr, num) {
		    	        var idx = this.index(lr, num);
		    	        if(idx<0) idx = 0;
		    	        else if(idx>=num) idx = 4;
		    	        this.switching(idx, num);
                        return idx;
		    	    },
		    	    index: function(lr, num) {
		    	        if (lr == "l") {
		    	        	this.activeIndex = this.activeIndex - 1;
		    	        	if(this.activeIndex < 0) this.activeIndex = 0; 
		    	        }
		    	        else {
		    	        	this.activeIndex = this.activeIndex + 1;
		    	        	if(this.activeIndex >= num) this.activeIndex = num - 1;
		    	        }
		    	        if(this.buttondisplay === true){
		    	        	this.paginator.paginator('option', 'page', this.activeIndex);
		    	        }
		    	        return this.activeIndex;
		    	    },
		    	    switching: function(index, num) {
		    	    	this.container.find(".jazz-cardpanel-ul").animate({marginLeft: (0 - this.width * index)}, 500);
		    	    	
		    	    	if(this.buttondisplay === true){
				         	if(this.activeIndex > 0){
					        	this.$this.leftButton.addClass('jazz-cardpanel-leftbtn');
				        	}else{
				        		this.$this.leftButton.removeClass('jazz-cardpanel-leftbtn');
				        	}
				        	if(this.activeIndex < num-1){
				        		this.$this.rightButton.addClass('jazz-cardpanel-rightbtn');
				        	}else{
				        		this.$this.rightButton.removeClass('jazz-cardpanel-rightbtn');
				        	}	
		    	    	}
		    	    }
	        }
        }
    
    });
    
})(jQuery);

