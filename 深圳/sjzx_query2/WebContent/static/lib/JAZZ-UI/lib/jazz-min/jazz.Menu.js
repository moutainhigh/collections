(function($, factory) {

    if (jazz.config.isUseRequireJS === true) {
        define(['jquery', 'jazz.BoxComponent'], factory);
    } else {
        factory($);
    }
})(jQuery, function($) {
    /**
     * @version 1.0
     * @name jazz.menu继承于BoxComponent
     * @description menu组件
     */
    $.widget("jazz.menu", $.jazz.boxComponent, {
        options: /** @lends jazz.menu# */  {
        	/**
             *@type String
             *@desc 组件类型
             *@default menu
        	 */        	
            vtype: "menu",

            /**
             *@type Array
             *@desc menu的数据结构
             *@default null
             */
            items: null
        },
        /** @lends jazz.menu */
        /**
         * @desc 创建组件
         * @private
         */
        _create: function() {
            this._super();
            this._createMenu();
        },
        /**
         * @desc 初始化组件
         * @private
         */
        _init: function() {
            this._super();
            this._bindDocument();
        },
        
        /**
         * @desc 绑定document点击事件
         * @private
         */        
        _bindDocument: function(){
        	var $this = this;
    		$(document).off('.menu').on('mousedown.menu', function(e){
    			var btn = $(e.target).closest('div.jazz-menu-btn'), list = $(e.target).closest('div.jazz-menu-list');
    			if(list.hasClass("jazz-menu-list") || btn.hasClass("jazz-menu-btn")){
    				return;
    			}else{
    				$this._hideAll();
    			}
    		});
        },
        
        /**
         * @desc 绑定菜单项目事件
         * @private
         */
        _bindMenuItemEvent: function(item, click){
        	var $this = this;
    		item.off('.menu');
    		item.on('click.menu', function(e){
    			if ($(this).hasClass('jazz-menu-item-disabled')){
    				return;
    			}
    			
    			$(this).trigger('mouseleave');
    			$this._customopration(click);   			
    			e.stopPropagation();
    		}).on('mouseenter.menu', function(e){
    			//隐藏其他菜单
    			item.siblings(".jazz-menu-item").each(function(){
    				var submenu = $(this).data("submenu");
    				if(submenu){
    					$this._hideMenu(submenu);
    				}
    				$(this).removeClass('jazz-menu-item-active');
    			});
    			//显示当前激活item
    			item.addClass('jazz-menu-item-active');
    			
    			if (item.hasClass('jazz-menu-item-disabled')){
    				item.addClass('jazz-menu-item-disabled');
    				return;
    			}
    			
    			var submenu = item.data("submenu");
    			if (submenu){
    				$this._showMenu(item, submenu);
    			}
    		}).on('mouseleave.menu', function(e){
    			var submenu = item.data("submenu");
    			if (submenu){
    				if (e.pageX >= parseInt(submenu.css('left'))){
    					item.addClass('jazz-menu-item-active');
    				} else {
    					$this._hideMenu(submenu);
    				}
    			} else {
    				item.removeClass('jazz-menu-item-active jazz-menu-item-disabled');
    			}
    		});        	 
        },
        
        /**
         * @desc 绑定菜单事件
         * @private
         */         
        _bindMenuEvent: function(menu){
        	var $this = this;
    		menu.off('.menu').on('mouseenter.menu', function(){
    			if ($this.timer){
    				clearTimeout($this.timer);
    				$this.timer = null;
    			}
    		}).on('mouseleave.menu', function(){
			    $this.timer = setTimeout(function(){
					$this._hideAll(menu);
				}, 100);
    		});        	
        },
        
        /**
         * @desc 绑定菜单事件
         * @private
         */        
        _bindMenubtnEvent: function(item, click){
        	var $this = this;
    		item.off('.menu').on('click.menu', function(e){
    			$this._customopration(click);
                e.stopPropagation();
    		}).on('mouseenter.menu', function(e){
    			if ($this.timer){
    				clearTimeout($this.timer);
    				$this.timer = null;
    			}
			
    			//隐藏其他菜单
    			item.siblings(".jazz-menu-btn").each(function(){
    				var submenu = $(this).data("submenu");
    				if(submenu){
    					$this._hideMenu(submenu);
    				}
    				$(this).removeClass('jazz-menu-btn-active');
    			});
    			//显示当前激活
    			item.addClass('jazz-menu-btn-active');
    			
    			if (item.hasClass('jazz-menu-btn-disabled')){
    				item.addClass('jazz-menu-btn-disabled');
    				return;
    			}
    			
    			var submenu = item.data("submenu");
    			if (submenu){
    				$this._showMenu(item, submenu, true);
    			}
    			
    		}).on('mouseleave.menu', function(){
			    $this.timer = setTimeout(function(){
			    	var submenu = item.data("submenu");
	    			if (submenu){
	    				$this._hideMenu(submenu);
	    			}
	    			item.removeClass('jazz-menu-btn-active');
				}, 100);
    		});        	
        },
        
        /**
         * @desc 创建menu结构
         * @private
         */        
        _createMenu: function(){
        	var arr = this.options.items;
        	if(arr != null){
        		arr = eval(this.options.items);
        		this.element.addClass("jazz-menu");
        		this._createMenubtn(arr);
        	}
        },
        
        /**
         * @desc 创建menu工具栏结构
         * @private
         */         
        _createMenubtn: function(items){
        	if(jazz.isArray(items)){
        		var str = [];
        		for(var i=0, len=items.length; i<len; i++){
        			var item = items[i];
        			var btn = $('<div class="jazz-menu-btn jazz-inline"></div>').appendTo(this.element);
        			str.push('<div class="jazz-menu-in">');
        			str.push('<div class="jazz-memu-btn-icon ');
        			if(item["iconclass"]){
        				str.push(item["iconclass"]);
        			}
        			str.push('"');
        			if(item["iconurl"]){
        				str.push(' style="background:url(\'');
        				str.push(item["iconurl"]);
        				str.push('\') no-repeat center center;"');
        			}
        			str.push('></div>');
        			str.push('<div class="jazz-memu-btn-font">');
        			str.push(item["text"]);
        			str.push('</div>');
        			str.push('</div>');
        			btn.append(str.join(""));
        			str = [];
        			
        			var subitem = item["items"];
        			if(subitem){
        				this._createMenulist(subitem, btn);      				
        			}
        			this._bindMenubtnEvent(btn, item["click"]);
        		}
        	}
        },
        
        /**
         * @desc 创建menulist结构
         * @private
         */
        _createMenulist: function (items, parentitem){
    		if(jazz.isArray(items)){
    			var menu = null, menuin = null;
    			if(items.length == 0){
    				return; 
    			}else{
    				menu = $('<div class="jazz-menu-list jazz-inline" style="display:none;"></div>').appendTo("body");
    				menuin = $('<div class="jazz-menu-list-in"></div>').appendTo(menu);
    				if(parentitem){ parentitem.data("submenu", menu); }
    				//if(flag){ menu.data("paritem", parentitem); }
    			}
        		for(var i=0, len=items.length; i<len; i++){
        			var item = items[i];
        			if(item == "-"){
        				menuin.append('<div class="jazz-menu-split-level"></div>');
        			}else{
        				var str = [];
        				str.push('<div class="jazz-menu-item-text">');
        				str.push(item["text"]);
        				str.push('</div>');
        				str.push('<div class="jazz-menu-icon ');
            			if(item["iconclass"]){
            				str.push(item["iconclass"]);
            			}
            			str.push('"');        				
            			if(item["iconurl"]){
            				str.push(' style="background:url(\'');
            				str.push(item["iconurl"]);
            				str.push('\') no-repeat center center;"');
            			}
        				str.push('></div>');
        				
        				var subsilingitem = item["items"];
        				if(subsilingitem){
        					str.push('<div class="jazz-menu-arrow"></div>');
        				}
        				var itemobj = $('<div class="jazz-menu-item"></div>').appendTo(menuin);
        				itemobj.append(str.join(""));
        				str = [];
        				
        				if(subsilingitem){
        					this._createMenulist(subsilingitem, itemobj);
        				}
        				
        				this._bindMenuItemEvent(itemobj, item["click"]);
        			}
        		}
        		this._bindMenuEvent(menu);
    		}
    	},
        
        /**
         * @desc 隐藏menu
         * @private
         */
        _hideAll: function(){
        	$.each($("body").children('.jazz-menu-list:visible'), function(){
        		$(this).find(".jazz-menu-item").removeClass('jazz-menu-item-active');
        		$(this).hide();
        	});       	
        },
        
        /**
         * @desc 隐藏menu
         * @private
         */        
        _hideMenu: function(menu){
        	var $this = this;
        	menu.find("div.jazz-menu-item-active").each(function(){
        		var submenu = $(this).data("submenu");
        		if(submenu){
        			$this._hideMenu(submenu);
        		}
        		$(this).removeClass('jazz-menu-item-active');
        	});
        	menu.hide();
        },
        
        /**
         * @desc 显示menu
         * @param {item} 菜单的item对象
         * @param {submenu} 子菜单
         * @param {flag} 标记 true 当前submenu的父为工具栏或按钮，为工具栏或按钮时，submenu显示在工具栏或按钮下边， false当前submenu的父不是工具栏，需要左右显示菜单
         * @param {e} 右键事件
         * @private
         */
        _showMenu: function(item, submenu, flag, e){
        	var winheight = $(window).height() || document.body.clientHeight,
        		winwidth = $(window).width() || document.body.clientWidth,
        	    left = 0, top = 0, itemwidth = 0, itemheight = 0;
        	if(e){
        		//右键时执行
        		left = e.pageX, top = e.pageY;
        	}
        	if(item){
        		left = item.offset().left, top = item.offset().top;
        		itemwidth = item.outerWidth(), itemheight = item.outerHeight();
        	}
        	if(flag){
            	if(left + submenu.outerWidth() + 5 > winwidth + $(document).scrollLeft()){
            		left = left + itemwidth - submenu.outerWidth();
            	}
        		if(top + itemheight + submenu.outerHeight() > winheight + $(document).scrollTop()){
        			top = top - submenu.outerHeight();
        		}else{
        			top = top + itemheight;
        		}   		
        	}else{
            	if(left + itemwidth + submenu.outerWidth() + 5 > winwidth + $(document).scrollLeft()){
            		left = left - submenu.outerWidth();
            	}else{
            		left = left + itemwidth;
            	}        	    
        		if(top + submenu.outerHeight() > winheight + $(document).scrollTop()){
        			top = winheight + $(document).scrollTop() - submenu.outerHeight();
        		}
        	}
        	if(top < 0){top = 0;}       		
        	
        	submenu.css({top: top, left: left, position: "absolute", "z-index": ++jazz.config.zindex});
        	submenu.show();
        	
        }
        
    });
    
    /**
     * @version 1.0
     * @name jazz.contextmenu继承于jazz.menu
     * @description menu组件
     */
    $.widget("jazz.contextmenu", $.jazz.menu, {
        options: {
        	/**
             *@type String
             *@desc 组件类型
             *@default contextmenu
        	 */      	
            vtype: "contextmenu",
            
        	/**
             *@type String
             *@desc 默认的触发事件
             *@default contextmenu
        	 */            
            event: "contextmenu",
            
            /**
             *@type String or jQuery Object
             *@desc 右键contextmenu绑定对象(如果不设置则绑定body), 或者event属性设置非"contextmenu"右键事件时，触发该事件的对象
             *@default null
             */
            target: null,
            
            /**
             *@type String or jQuery Object
             *@desc 当触发事件event属性设置为非"contextmenu"右键属性时，container属性必须设置，他的作用就是将菜单显示在他的下方。
             *@default null
             */            
            container: null
        },
        
        /** @lends jazz.contextmenumenu */
        /**
         * @desc 创建组件
         * @private
         */
        _create: function() {
            this._super();
        },
        
        /**
         * @desc 初始化组件
         * @private
         */
        _init: function() {
            this._super();
        },
        
        /**
         * @desc 创建menu结构
         * @private
         */        
        _createMenu: function(){
        	var items = this.options.items;
        	if(items != null){
            	var target = this.options.target || "body";
        		var menu = $(target);
        		
        		items = eval(items);
        		
        		if(!jazz.isArray(items)){ return ; }
        		//创建菜单
        		this._createMenulist(items, menu);       		

        		var $this = this, _event = $this.options.event;
        		if(_event == "contextmenu"){
	    			menu.off('.contextmenu').on(_event+'.contextmenu', function(e) {
	    				$this._showMenu(null, menu.data("submenu"), true, e);       					
	    				e.preventDefault();
	    			}); 
        		}
        	}       	
        },

        /**
         * @desc 显示menu菜单
         * @private
         */
        showMenu: function(){
        	var target = this.options.target || "body";
    		var menu = $(target);
    		var s = this.options.container;
    		if(s){
    			this._showMenu($(s), menu.data("submenu"), true);
    		}        	
        },
        
        /**
         * @desc 显示menu菜单
         * @private
         */        
        hideMenu: function(){
        	this._hideAll();
        }
    });
    
});
