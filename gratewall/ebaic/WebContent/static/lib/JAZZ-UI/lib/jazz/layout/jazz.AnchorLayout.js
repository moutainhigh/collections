
(function($) {

	/**
	 * @version 0.5
	 * @name jazz.anchorlayout
	 * @description 锚布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 * @requires
	 * @example $('#panel_id').anchorlayout();
	 */
    $.widget("jazz.anchorlayout", $.jazz.containerlayout, {

    	/** @lends jazz.anchorlayout */        
        
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 当前组件对象
		 * @throws
		 * @example
         */
        layout: function(cthis, container, config) {
        	var anchor = config.anchor;
        	var points = config.point;
        	container.addClass("jazz-layout");
        	
        	container.css({border: '5px solid red'});
        	//config.type='0';
        	
        	if(anchor==null){
        		if(config.type=='1'){
	        		var page = this._getPageSize();
	        		var x  = page.windowWidth/2, y = page.windowHeight/2;
	        		anchor = config.anchor = {x: x, y: y};
	                //jazz.log('page.windowWidth='+page.windowWidth+'***page.windowHeight='+page.windowHeight+'x='+x+'***'+'y='+y);
        		}else{
        			container.css({width:1200, height: 400 });
        			var top = container.offset().top;
        			var left = container.offset().left;
        			var cw = container.width()/2;
        			var ch = container.height()/2;
        			
        			top = parseInt(top + ch);
        			left = parseInt(left + cw);
        			
        			anchor = config.anchor = {x: left, y: top};
        			//jazz.log('top='+top+'***left='+left+'***cw='+cw+'***ch='+ch);
        		}
        	}
        	
        	var cheight = 0;
        	$.each(points, function(i, p){
        		if(!!p.id){
        			var x  = parseInt(anchor.x) + parseInt(p.offset.x);
        			var y = parseInt(anchor.y) + parseInt(p.offset.y);
        			var n = $('#'+p.id).outerHeight(true) + y;   //计算DIV高度
        			if(n > cheight){
        				cheight = n;
        			}
        			$('#'+p.id).appendTo(container);
        			$('#'+p.id).css({
        				position: 'absolute',
        				top: y,
        				left: x
        			});
        		}
        	});
        	
        	container.height(cheight + 20);
        	
        },

		/**
         * @desc 计算页面大小
		 * @private
		 * @example  this._getPageSize();
         */
		_getPageSize: function () {
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
		}
        
    });
    
})(jQuery);