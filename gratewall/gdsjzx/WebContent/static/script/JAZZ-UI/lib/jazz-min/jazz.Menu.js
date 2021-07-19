(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'jazz.BoxComponent'], factory );
	} else {
		factory($);
	}
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.basemenu
	 * @description menu/contextmenu组件的基类
	 */
	$.widget("jazz.basemenu", $.jazz.boxComponent,{
		options:{
			/**
			 * @type String
			 * @desc 组件名称
			 * @default ""
			 */
			name:"",
			/**
        	 * @type number
        	 * @desc menu宽度设置
        	 * @default -1
        	 */
        	width:-1,
        	/**
        	 * @type number
        	 * @desc menu高度设置
        	 * @default -1
        	 */
        	height:-1,
			/*
			// my/at属性是为了确定menu/contextmenu相对于target目标元素显示位置
			// 现my/at这两属性暂时未使用，默认menu在target目标元素下方显示
			my: 'left top',
			at: 'right top',
			*/
			/**
			 * @type number
			 * @desc 鼠标移出menu菜单时，隐藏menu的延迟时间
			 * @default null
			 */
			delayhidetime:null,
			/**
			 *@type Object 或者 字符串选择器
			 *@desc menu绑定对象
			 *@default null
			 */
			target: null,
			/**
			 *@type Object
			 *@desc menu组件数据项数组，数据格式参考[{xx:mm},{xx:mm}]
			 *@default null
			 */
			items:null,
			/**
			 *@type function
			 *@desc menu组件初始化items数据的回调函数，返回同items属性相同格式数组数据
			 *@return 默认空数组[]
			 */
			createitems:function(){
				return [];
			}
		},
		/** @lends jazz.basemenu */
        /**
         * @desc 创建组件
         * @private
         */
		_create: function(){
			this._super();
			
			//1.获取当前页面请求地址，计算menu默认图片地址
        	this._getMenuDefaultUrl();
			
        	//2.menu初始化宽度和高度样式
			var el = this.element;
			el.addClass("jazz-menu-base");
			//2.1建议：使用自定义宽度，否则使用默认样式宽度
			if(this.options.width>0){
				el.width(this.options.width);
			}
			//2.2建议：高度不用设，使用内部元素自动扩充
			if(this.options.height>0){
				el.height(this.options.height);
			}
			//3.将items都通过eval获取，这样无论vtype还是widget形式都可统一创建方式
			//3.1注意：此处通过eval(this.options.item)获取数据，是相当于在当前页面进行变量寻址
			//即如通过vtype方式在页面创建menu<div vtype=menu items="opt"></div>，items数据是页面opt的全局变量，
			//这样的实现方式，会造成页面的全局污染，不好理解和维护，可以使用createitems回调方式实现
			var items = eval(this.options.items);
			if(!items){
				var createitems = this.options.createitems;
				if(createitems){
					items = this._customopration(this.options.createitems);
				}
			}
			this.options.items = items || [];
			this._widgetCreateMenuDom();
		},
		/**
         * @desc 初始化组件
         * @private
         */
		_init: function(){
			this._super();
			
			var that = this;
			var el = that.element;
			that._bindMenuEvent();
		},
		/**
         * @desc 覆盖组件响应resize事件时，重新计算组件宽度的方法
         * 		即menu组件不予响应resize大小调整
         */
		_width:function(){
			
		},
		/**
         * @desc 覆盖组件响应resize事件时，重新计算组件高度的方法
         * 		即menu组件不予响应resize大小调整
         */
		_height:function(){
			
		},
		/**
         * @desc 通过正则获取插入body中元素的背景图url路径，得到当前页面的默认主目录路径，以此找到默认图标路径。
         * @private
         */
		_getMenuDefaultUrl: function(){
        	var obj = $('<div id="div-img-src"  class="jazz-menu-img-src"></div>').appendTo(document.body);
        	var str = obj.css("background-image");
        	//url("http://localhost:8082/JAZZ/lib/themes/default/images/tool-sprites-dark.png")
        	var start = str.indexOf('(');
        	var end = str.lastIndexOf('tool-sprites-dark.png');
        	this.defaulturl = str.substring(start+1,end).replace('"',"");
        	obj.remove();
        },
        /**
         * @desc 绑定menu基本响应事件，主要结合menu操作习惯进行事件设定和行为的优化
         * @event {mouseover}{mouseout}{click}{mouseenter}{mouseleave}
         * @private
         */
		_bindMenuEvent: function(){
			var that = this;
			var el = that.element;
			el.on('contextmenu.jazz-menu-contextmenu' , function(e){
				//屏蔽鼠标右键
				return false;
            }).on("mouseover.jazz-menu",".jazz-menu-label",null,function(e){
				//1.鼠标移入menu的每个li时，确认是否缓存隐藏.jazz-menu dom的timeCounter计时器id
            	//若是有代表鼠标触发过mouseleave.jazz-submenu事件，此时鼠标再次移入menu的li中来，就
            	//需要清除掉这个延迟隐藏menu的执行函数
				var time = el.data("timeCounter");
				if(time){
					el.data("timeCounter","");
					clearTimeout(time);
				}
				//2.移入鼠标时，首先清除上一个active的menu样式，并隐藏上一个active的子menu
				var parent = $(this).closest(".jazz-menu");
				parent.find(".jazz-menu-label-active").removeClass("jazz-menu-label-active");
				parent.find(".jazz-menu").hide();
				//3.添加当前鼠标选中的li样式，并显示他的对应子menu
				var nextmenu = $(this).next(".jazz-menu .jazz-submenu");
				nextmenu.show();
				nextmenu.css({left:'', top:'','z-index':++jazz.config.zindex}).position({
			        my: 'left top',
			        at: 'right top',
			        of: $(this)
			    });
			    //4.鼠标移入jazz-menu-label的li时动作
			    $(this).addClass("jazz-menu-label-active");
			    e.stopPropagation();
			    
			}).on("mouseout.jazz-menu",".jazz-menu-label",null,function(e){
				var target = $(e.target);
				var nextmenu = $(this).next(".jazz-menu .jazz-submenu");
				var li = $(this).parent();
				
				var x = e.pageX,y=e.pageY;
				var nw = nextmenu.width();
				var nleft = nextmenu.offset().left;
				var lih = li.height();
				var lileft = li.offset().left;
				var litop = li.offset().top;
				//1.鼠标移出menu的li且不是移入它对应的子menu时，就要隐藏它的子menu，并去掉li选中样式
				if(x<=lileft || y<=litop || y>=Math.floor(litop+lih-1) ){
					nextmenu.hide();
					$(this).removeClass("jazz-menu-label-active");
				}
				
			}).on("click.jazz-menu",".jazz-menu-label",null,function(e){
				//鼠标点击menu的li时，进行子menu的显示隐藏
				var nextmenu = $(this).next(".jazz-menu .jazz-submenu");
				if(nextmenu.is(":hidden")){
					nextmenu.show();
				}else{
					nextmenu.hide();
				}
			});
			
			//为menu组件element的.jazz-menu dom对象添加mouseenter、mouseleave事件
			//鼠标移入、移出menu延迟隐藏事件
			var delayHideTime = that.options.delayhidetime || 1000;
			el.on("mouseenter.jazz-submenu",".jazz-menu",null,function(e){
				//1.移入的时候，需要消除延迟隐藏
				var time = el.data("timeCounter");
				if(time){
					el.data("timeCounter","");
					clearTimeout(time);
				}
				$(this).find(".jazz-menu-label-active").removeClass("jazz-menu-label-active");
				$(this).find(".jazz-menu").hide();
				e.stopPropagation();
			}).on("mouseleave.jazz-submenu",".jazz-menu",null,function(e){
				//1.移出的时候需要添加延迟隐藏
				var time = setTimeout(function(){
					el.find(".jazz-menu").hide();
					el.hide();
				},delayHideTime);
				el.data("timeCounter",time);
				e.stopPropagation();
			});
			
			//为每个.jazz-menuitem-content标签绑定click事件
			var itemEvents = that.allItemsClickEvents;
			el.find(".jazz-menuitem-content").each(function(){
				var id = $(this).attr("id");
				var clickobj = $(this);
				$.each(itemEvents,function(i,temp){
					var auid = temp["auid"];
					var clickevent = temp["click"];
					if(auid==id){
						clickobj.on('click', function(e){
							if($.isFunction(clickevent)){
								clickevent.call($(this));
							}else{
								eval(clickevent).call($(this));
							}
							el.hide();
						});
					}
				});
			});
			
			//document绑定隐藏menu事件
			$(document).on('mousedown.jazzz-menu', function(e) {
                if(el.is(":hidden")) {
                    return;
                }
                var target = $(e.target);
                if(target.is(el) || el.has(target).length>0) {
                    return;
                }
                var offset = el.offset();
                if(e.pageX < offset.left ||
                    e.pageX > offset.left + el.width() ||
                    e.pageY < offset.top ||
                    e.pageY > offset.top + el.height()) {
                    
                    el.hide();
                }
            });
		},
		/**
		 * @desc 创建menu的dom结构
		 * @private
		 */
		_widgetCreateMenuDom: function(){
			var that = this;
			var el = that.element;
			var items = that.options.items;
			//检查items中vtype类型的定义
			that.vtypeWidgets = [];
			that.allItemsClickEvents = [];
			that._recorderVtypeWidet(items);
			
			var menuHtml = that._getWidgetMemuDom(items);
			el.append(menuHtml);
			
			//生成html后统一初始化menu中的其他组件
			that._initVtypeSubmenuComponent();
		},
		/**
		 * @desc 递归记录具有vtype属性的组件，以便生成menu的html结构后，解析创建每个vtype组件
		 * @param {items} menu的数组数据
		 * @private
		 */
		_recorderVtypeWidet: function(items){
			var that = this;
			if(items&&items.length>0){
				var item,nextItems,name,vtype;
				for(var i=0;i<items.length;i++){
					item = items[i];
					name = item["name"];
					vtype= item["vtype"];
					nextItems = item["items"];
					//vtype组件下面不再检查是否有items
					if(vtype){
						var uid = jazz.util.getIndex();
						name = name || "submenu"+uid;
						item["name"] = name;
						that.vtypeWidgets.push({"vtype":vtype,"name":name,"option":item});
					}else{
						if(nextItems&&$.isArray(nextItems)){
							that._recorderVtypeWidet(nextItems); 
						}
					}
				}
			}
		},
		/**
		 * @desc 根据items数组数据，创建相应每一个menu及子menu的html dom结构
		 * @param {items} 每级menu的数组数据
		 * @param {isSubmenu} 是否是子menu
		 */
		_getWidgetMemuDom: function(items,isSubmenu){
			var that = this;
			var menuHtml = [];
			if(isSubmenu){
				menuHtml.push("<div class=\"jazz-menu jazz-submenu\">");
			}else{
				menuHtml.push("<div class=\"jazz-menu\">");
			}
			menuHtml.push("<div class=\"jazz-menu-content\" >");
			menuHtml.push("<ul class=\"first-of-type\" >");
			if(items&&items.length>0){
				var item,nextItems,name,vtype,html,aid,subMenuHtml;
				for(var i=0;i<items.length;i++){
					item = items[i];
					name = item["name"];
					vtype= item["vtype"];
					html = item["html"];
					nextItems = item["items"];
					aid = "menu-uid-"+(++jazz.config.zindex);
					item["auid"] = aid;
					
					menuHtml.push("<li class=\"jazz-menuitem\" >");
					if(vtype){
						menuHtml.push("<a class=\"jazz-menuitem-content-component\" id='"+aid+"'>");
						menuHtml.push("<div name='"+name+"' vtype='"+vtype+"'></div>");
						menuHtml.push("</a>");
					}else{
						var iconurl = item["iconurl"];
						if(nextItems&&$.isArray(nextItems)){
							menuHtml.push("<a class=\"jazz-menu-label\" id='"+aid+"'>");
							
							menuHtml.push("<div class=\"jazz-menuitem-content-wrap\">");
							menuHtml.push("<div class=\"jazz-menuitem-label-img\">");
							if(iconurl){
								menuHtml.push("<img src=\""+iconurl+"\" alt=\"\">");
							}else{
								//iconurl = "../../../demos/classic/menu/images/menu-blank.png";
								iconurl = this.defaulturl+"/menu-blank.png";
								menuHtml.push("<img class=\"menuitem-default-icon\" src=\""+iconurl+"\" alt=\"\">");
							}
							menuHtml.push("</div>");
							menuHtml.push("<div class=\"jazz-menuitem-label-inner\">");
							if(html){
								menuHtml.push(html);
							}else{
								menuHtml.push(item["text"]);
							}
							menuHtml.push("</div>");
							menuHtml.push("</div>");
							menuHtml.push("</a>");
							subMenuHtml = that._getWidgetMemuDom(nextItems,true); 
							menuHtml.push(subMenuHtml);
						}else{
							menuHtml.push("<a class=\"jazz-menuitem-content\" id='"+aid+"'>");
							
							//将放置图片的位置给留出来
							menuHtml.push("<div class=\"jazz-menuitem-content-wrap\">");
							menuHtml.push("<div class=\"jazz-menuitem-content-img\">");
							if(iconurl){
								menuHtml.push("<img src=\""+iconurl+"\" alt=\"\">");
							}else{
								//iconurl = "../../../demos/classic/menu/images/menu-blank.png";
								iconurl = this.defaulturl+"/menu-blank.png";
								menuHtml.push("<img class=\"menuitem-default-icon\" src=\""+iconurl+"\" alt=\"\">");
							}
							menuHtml.push("</div>");
							menuHtml.push("<div class=\"jazz-menuitem-content-inner\">");
							if(html){
								menuHtml.push(html);
							}else{
								menuHtml.push(item["text"]);
							}
							menuHtml.push("</div>");
							menuHtml.push("</div>");
							menuHtml.push("</a>");
							
							that.allItemsClickEvents.push({"auid":aid,"click":item["click"]});
						}
					}
					menuHtml.push("</li>");
				}
			}
			menuHtml.push("</ul>");
			menuHtml.push("</div>");
			menuHtml.push("</div>");
			return menuHtml.join("");
		},
		/**
		 * @desc 根据this.vtypeWidgets解析创建具有vtype属性值的jazz组件
		 * @private
		 */
		_initVtypeSubmenuComponent: function(){
			var that = this;
			var el = that.element;
			var widgets = that.vtypeWidgets;
			var name,widget,option;
			for(var i=0,len=widgets.length;i<len;i++){
				widget = widgets[i];
				name = widget["name"];
				option = widget["option"];
				el.find("div[name='"+name+"']").parseComponent(option);
			}
		},
		destroy: function(){
			this.element.children().remove();
			this.element.remove();
			this._super();
		}
	});
	/**
	 * @version 1.0
	 * @name jazz.menu继承于jazz.basemenu
	 * @description menu组件
	 */
	$.widget("jazz.menu", $.jazz.basemenu,{
		options:{
			vtype:"menu",
			/**
			 *@type Object 或者 字符串选择器
			 *@desc menu绑定对象
			 *@default null
			 */
			target: null
		},
		/** @lends jazz.menu */
        /**
         * @desc 创建组件
         * @private
         */
		_create: function(){
			this._super();
		},
		/**
         * @desc 初始化组件
         * @private
         */
		_init: function(){
			this._super();
		},
		/**
		 * @desc 重写jazz.basemenu中的_bindMenuEvent()事件绑定
		 * @private
		 */
		_bindMenuEvent: function(){
			this._super();
			var that = this;
			var el = that.element;
			
			var target = that.options.target||$(document);
			var positionConfig = {
                my: that.options.my,
                at: that.options.at,
                of: $(target)
            };
			//1.初始化时主动绑定到target对象上
			el.css({left:'', top:'','z-index':++jazz.config.zindex}).position(positionConfig);
			
			//2.为menu组件element的.jazz-menu dom对象添加mouseenter、mouseleave事件（鼠标移入、移出menu延迟隐藏事件）
			//2.1注意： 此处覆盖basemenu中已经绑定的mouseenter/mouseleave事件，因为menu基本是和button结合使用，特别事件绑定处理
			//而basemenu中是对menu和contextmenu共同事件的绑定。
			var delayHideTime = that.options.delayhidetime || 1000;
			el.off("mouseenter.jazz-submenu mouseleave.jazz-submenu").on("mouseenter.jazz-submenu",".jazz-menu",null,function(e){
				//1.移入的时候，需要消除延迟隐藏
				var time = el.data("timeCounter");
				if(time){
					el.data("timeCounter","");
					clearTimeout(time);
				}
				$(this).find(".jazz-menu-label-active").removeClass("jazz-menu-label-active");
				$(this).find(".jazz-menu").hide();
				e.stopPropagation();
			}).on("mouseleave.jazz-submenu",".jazz-menu",null,function(e){
				//1.移出的时候需要添加延迟隐藏
				var time = setTimeout(function(){
					el.find(".jazz-menu-label-active").removeClass("jazz-menu-label-active");
					el.find(".jazz-menu .jazz-submenu").hide();
					el.hide();
					//当target为button时，即下拉菜单按钮时专门处理
					if($(target).hasClass("jazz-button")){
	            		$(target).find(".button-arrow-td img").hide();
	            		$(target).removeClass("jazz-button-pressed");
	            	}
				},delayHideTime);
				el.data("timeCounter",time);
				e.stopPropagation();
			});
			/*=====================2015-06-29 modify end============================*/
		}
	});
	/**
	 * @version 1.0
	 * @name jazz.contextmenu继承于jazz.basemenu
	 * @description contextmenu组件
	 */
	$.widget("jazz.contextmenu", $.jazz.basemenu,{
		options:{
			vtype:"contextmenu",
			/**
			 *@type Object 或者 字符串选择器
			 *@desc 右键contextmenu绑定对象
			 *@default null
			 */
			target: null
		},
		/** @lends jazz.contextmenumenu */
        /**
         * @desc 创建组件
         * @private
         */
		_create: function(){
			this._super();
		},
		/**
         * @desc 初始化组件
         * @private
         */
		_init: function(){
			this._super();
		},
		_bindMenuEvent: function(){
			this._super();
			var that = this;
			var el = that.element;
			
			//鼠标右键事件可以指定绑定对象，如树、document.body上
			/*var target = that.options.target||$(document);
			$(target).on('contextmenu.jazz-contextmenu' , function(e){
				var x = e.pageX,y=e.pageY;
				el.css({left:x, top:y,'z-index':++jazz.config.zindex}).show();
			    e.preventDefault();
            	e.stopPropagation();
            });*/
            
            if(!this.element.is(document.body)) {
                this.element.appendTo('body');
            }
           /* var target = that.options.target||$(document);
			$(target).on('contextmenu.jazz-contextmenu' , function(e){
				that._showContextMenu(e);
            });*/
        	var target = that.options.target;
            if((typeof target =='string') ||(target instanceof Object)){
				$(target).on('contextmenu.jazz-contextmenu' , function(e){
					that._showContextMenu(e);
	            });
            }
            /*el.on("mouseleave.jazz-menu-all",function(e){
            	$(this).fadeOut();
			});*/
		},
		/**
		 * @desc 右键显示菜单menu位置计算
		 * @param {event} contextmenu事件
		 * @private
		 */
		_showContextMenu: function(e) { 
            //隐藏其他鼠标右键菜单menu
            $(document.body).children('.jazz-contextmenu:visible').hide();
            
            var win = $(window),
            left = e.pageX,
            top = e.pageY,
            width = this.element.outerWidth(),
            height = this.element.outerHeight();
			
            //超过窗口边界处理
            if((left + width) > (win.width())+ win.scrollLeft()) {
                left = left - width;
            }
            if((top + height ) > (win.height() + win.scrollTop())) {
                top = top - height;
            }
            /*if(this.options.beforeShow) {
                this.options.beforeShow.call(this);
            }*/
            this.element.css({
                'left': left,
                'top': top,
                'z-index': ++jazz.config.zindex
            }).show();
            this.element.children().show();
            e.preventDefault();
            e.stopPropagation();
        },
        showContextMenu: function(e) {
            this._showContextMenu(e);
        }
	});
});
