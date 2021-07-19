(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 
		         'jazz.Container', 
		         'jazz.Toolbar' ], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
/** 
 * @version 0.5
 * @name jazz.panel
 * @description 是面板容器，是一特定的功能和结构化组件。
 * @constructor
 * @extends jazz.container
 */
    $.widget("jazz.panel", $.jazz.container, {
       
        options: /** @lends jazz.panel# */ {
        	
        	/**
        	 *@desc 组件类型
        	 */
        	vtype: 'panel',
            
            /**
			 *@type string
			 *@desc 背景颜色
			 *@default white
			 */
            bgcolor: null,
            
    		/**
    		 *@type array
    		 *@desc 按钮集合，[{},{},{}], {}对应button的options. 
    		 *@default null
    		 */
    		buttons: null,
            
			/**
			 *@type boolean
			 *@desc 标题栏是否显示关闭按钮
			 *@default false
			 */               
            closable: false,
            
			/**
			 *@type boolean
			 *@desc 触发窗体大小改变，true 折叠， false 非折叠，
			 *@default false
			 */              
            collapsable: false,

            /**
			 *@type Object
			 *@desc 在标题栏自定操作按钮
			 *@default null
			 *@example 	
			 *<br>[{       	
			 *<br>	id: 't_1',
	         *<br>	align: 'left',
	         *<br>	icon: 'test.png',
	         *<br>	click: function(e){
	         *<br>	}
	         *<br>},{……},{……}]
			 */  
            customtitlebutton: null,
            
            /**
             *@type String
             *@desc frame的名称
             *@default ""
             */            
            framename: "",
            
            /**
             *@type String
             *@desc 内容区域URL
             *@default null
             */
            frameurl: null, 
            
			/**
			 *@type boolean
			 *@desc 是否显示容器的边框
			 *@default true
			 */               
            showborder: true,

    		/**
			 *@type Boolean
			 *@desc 是否显示标题栏   true显示 false不显示
			 *@default true
			 */
    		titledisplay: false,        	
        	
			/**
			 *@type String
			 *@desc 标题名称
			 *@default ''
			 */          	
        	title: '',
        	
    		/**
			 *@type String
			 *@desc 标题名称的显示位置 left center right
			 *@default 'left'
			 */
    		titlealign: 'left',
    		
    		/**
			 *@type String
			 *@desc 标题前的图片 例如：../../images/title.png
			 *@default null
			 */
    		titleicon: null,
    		
            /**
			 *@type Boolean
			 *@desc 控制标题框上操作按钮的位置   right 右侧  left 左侧
			 *@default false
			 */
    		titlebuttonalign: 'right',
        		
			/**
			 *@type boolean
			 *@desc 是否显示可触发窗体大小改变的按钮
			 *@default false
			 */
            toggleable: false,
            
			/**
			 *@type string
			 *@desc 控制折叠方向   vertical   horizontal   未定入API  需要完善
			 *@default vertical
			 *@private
			 */
            toggleorientation : 'vertical',
            
            //event
			/**
			 *@desc 关闭窗体前触发
			 *@param {event} 事件		 
			 *@event
			 *@example
			 *<br/>$("XXX").panel("option", "beforeclose", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("panelbeforeclose",function(event){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… beforeclose="XXX()"></div> 或 <div…… beforeclose="XXX"></div>	
			 */	           
            beforeclose: null,
            
			/**
			 *@desc 关闭窗体后触发
			 *@param {event} 事件		 
			 *@event
			 *@example
			 *<br/>$("XXX").panel("option", "afterclose", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("panelafterclose",function(event){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… afterclose="XXX()"></div> 或 <div…… afterclose="XXX"></div>	
			 */	 	            
            afterclose: null,
            
			/**
			 *@desc 窗体收起前触发
			 *@param {event} 事件		 
			 *@event
			 *@example
			 *<br/>$("XXX").panel("option", "beforecollapse", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("panelbeforecollapse",function(event){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… beforecollapse="XXX()"></div> 或 <div…… beforecollapse="XXX"></div>	
			 */	             
            beforecollapse: null,
            
			/**
			 *@desc 窗体收起后触发
			 *@param {event} 事件		 
			 *@event
			 *@example
			 *<br/>$("XXX").panel("option", "aftercollapse", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("panelaftercollapse",function(event){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… aftercollapse="XXX()"></div> 或 <div…… aftercollapse="XXX"></div>	
			 */	            
            aftercollapse: null,
            
			/**
			 *@desc 窗体展开前触发
			 *@param {event} 事件		 
			 *@event
			 *@example
			 *<br/>$("XXX").panel("option", "beforeexpand", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("panelbeforeexpand",function(event){  <br/>} <br/>});
			 *或：
			 *function XXX(){……}
			 *<div…… beforeexpand="XXX()"></div> 或 <div…… beforeexpand="XXX"></div>	
			 */              
            beforeexpand: null,
            
			/**
			 *@desc 窗体展开后触发
			 *@param {event} 事件		 
			 *@event
			 *@example
			 *<br/>$("XXX").panel("option", "afterexpand", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("panelafterexpand",function(event){  <br/>} <br/>});
			 *或:
			 *function XXX(){……}
			 *<div…… afterexpand="XXX()"></div> 或 <div…… afterexpand="XXX"></div>	
			 */
            afterexpand: null
        },
        
        /** @lends jazz.panel */        
        
		/**
         * @desc 创建组件
		 * @private
         */        
        _create: function() {
        	this._super();
        	
        	this.compId = this.getCompId();
        	
        	var title = this.element.attr('title');
        	if(title){
        		this.element.removeAttr('title');
        	}
        	
        	this.element.css('overflow', 'hidden').addClass('jazz-panel');
        	
        	//创建基本组件
       	    this._createPanelElement();
        },
        
        /**
         * @desc 初始化
         * @private
         */
        _init: function(){
        	
        	this._super();
        	
        	//是否显示边框  边框未渲染任何子元素时加载
        	this._showborder();
        	
			//标题栏信息
			this._title();
            
        	//定义标题栏操作按钮容器
        	this._titleButton();
        	
        	//关闭
            this._closable();
            
            //自定义标题栏按钮
            this._customTitleButton(); 
            
            //是否可收起
            this._toggleable();     
            
            //是否显示标题
            this._titledisplay();

            //是否显示颜色
            this._bgcolor(); 

			//计算panel宽度
			this._width();
            
            //添加按钮
            this._buttons();
            
            //计算panel高度
            this._height();
            
			//初始化内容区域
			this._frameurl();     
			
        	//设置加载容器布局
            this.doLayout(this.content);

            //处理content内容
            if(this.options.content){
            	this._createContent(this.content);    	
            }
       
            //处理items内容
            if(this.options.items){
            	this._createItems(this.content);            	
            }
            
            //判断初始化时是否折叠
            this._collapsable();
        },
        
        /**
         * @desc 背景颜色
         * @private
         */
        _bgcolor: function(){
        	if(this.options.bgcolor != null){
        		this.content.css('background', this.options.bgcolor);        
        	}
        },

        /**
         * @desc 按钮
         * @private
         */        
        _buttons: function(){
        	var buttons = this.options.buttons;
        	if($.isArray(buttons)){
        		//是否存在，存在移除
        		if(this.toolbar){
        			this.toolbar.remove();
        		}
        		this.toolbar = $('<div>').appendTo(this.element);
        		for(var i=0, len=buttons.length; i<len; i++){
        			buttons[i]["vtype"] = "button";
        		}
        		this.toolbar.addClass('jazz-panel-toolbar').toolbar({items: buttons});
        	}
        },
        
        /**
         * @desc 关闭窗口
         * @private
         */
        _closable: function(){
        	if(this.options.closable){
        		var $this = this;
        		var a = this.titlebar.find('.jazz-panel-titlebar-close');
        		if(a.hasClass('jazz-panel-titlebar-close')){
        			a.remove();
        		}                
                var obj = this._renderTitleButton('jazz-panel-titlebar-close', 'jazz-titlebar-icon-close');
                obj.off('click.panelclose').on('click.panelclose', function(e) {
                	$this.close();
	                e.preventDefault();
	            });
        	}else{
        		this.titlebar.find('.jazz-panel-titlebar-close').remove();
        	}
        },
        
        /**
         * @desc 判断初始化时是否折叠
         * @private
         */        
        _collapsable: function(){
        	if(this.options.toggleable){
        		if(this.options.collapsable){
        			this.collapse();
        		}
        	}
        },
        
        /**
         * @desc 创建panel组件结构
         * @private
         */
        _createPanelElement: function(){
            var compId = this.compId, el = this.element, vtype = this.options.vtype;
            var div = [];
            div.push('<div id = "');
            div.push(compId);
            div.push('_content" class="jazz-panel-content"></div>');
            el.wrapInner(div.join(""));

            div = [];
            div.push('<div id = "'+compId+'_titlebar" class="jazz-panel-titlebar jazz-panel-header jazz-');
            div.push(vtype);
            div.push('-titlebar" style="text-align: left;"><div id="');
            div.push(compId);
            div.push('_titlebar_inner" class="jazz-');
            div.push(vtype);
            div.push('-titlebar-inner" style="width:100%; height:100%"></div></div>');
            
            el.prepend(div.join(""));
            
            this.titlebar = $('#'+compId+'_titlebar');
            this.titlebarInner = $('#'+compId+'_titlebar_inner');
            this.content = $('#'+compId+'_content');
            if(this.options.content){
            	this.content.append(this.options.content);            	
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
         * @desc 自定义操作按钮
         * @private
         */
        _customTitleButton: function(){
        	var ctb = this.options.customtitlebutton;
        	if(ctb){
        		if(!$.isArray(ctb)){
        			ctb = jazz.stringToJson(ctb);
        		}
		    	var $this = this;
		    	$.each(ctb, function(i, handler){
		        	var customIcons = $this.handlerIcons1;
		        	if(handler.align == 'right'){
		        		customIcons = $this.handlerIcons2;
		        	}
		        	$this._customIcon(handler, customIcons, handler.icon);
		    	});        			
        	}
        },
        
        /**
         * @desc 定义弹出框操作按钮
		 * @private
         */
        _customIcon: function(handler, handlerIcons, icon, iconClass) {
        	var $this = this;
        	var f = "float: left";
        	if(handler.align == "right"){
        		f = "float: right";
        	}
        	if(!iconClass){ iconClass = "";}
        	if(icon){ icon = 'style="background: url(\'' + icon + '\') no-repeat"'; }else{icon=""; }
            var c = $('<a id="'+handler.id+'" class="jazz-panel-titlebar-icon jazz-panel-titlebar-custom" style="'+f+'">' +
                    '<span '+icon+' class="jazz-titlebar-icon jazz-titlebar-icon-custom '+iconClass+' " /></a>').appendTo(handlerIcons);
            c.on('click', function(e){
            	if($.isFunction(handler['click'])){
	            	handler.click.call(this, e, {
	            		object: $this,
	            		element: $this.element
	            	});
            	}
            });
        },        

        /**
         * @desc 初始化内容区域
         * @private
         */
        _frameurl: function() {
            if(this.options.frameurl != null){
            	this.content.empty();
            	var framename = this.options.framename;
            	if(framename){
            		this.frameId = framename;           		
            	}else{
            		var name = this.options.name;
            		if(name){
            			this.frameId = 'frame_'+name;
            		}else{
            			this.frameId = 'frame_'+jazz.getRandom();            		
            		}
            	}
            	
            	var width = this.content.width(), height = this.content.height();
            	
        		this.frameObject = $('<iframe name="'+this.frameId+'" id="' + this.frameId +'" frameBorder="0"></iframe>');
	    		this.displayDiv = $('<div style="display:none; width:100%; height:100%; background:#FFFFFF;"></div>').appendTo(this.content);
	    		this.content.css('overflow', 'hidden').append(this.frameObject);
	    		this.frameObject.css({width: width, height: height}).attr("src", this.options.frameurl || "");
	    		this.pageParams = [];
	    		this.pageParams.push({url: this.options.frameurl});	    		
        	}  	
        },

        /**
         * @desc 组件高度
         * @private
         */
        _height: function(){
        	var layout = this.options.layout;
        	if(layout != 'fit'){
        		if(layout == "border"){
        			this.options.height = "100%";
        		}
        		this._super();
        		if(this.iscalculateheight){
            	    var h = 0;
            	    if(this.toolbar && this.options.vtype!="gridpanel"){
            	    	h = this.toolbar.outerHeight(true);
            	    }
                    var titleHeight = 0;
                    if(this.options.titledisplay){
                    	titleHeight = this.titlebar.outerHeight(true);
                    }
            		this.content.outerHeight(this.options.calculateinnerheight - titleHeight - h, true);
        		}
        		
				if(this.options.frameurl != null && this.frameObject){
					this.frameObject.height(this.content.height());
				}
        	}        	
        },
        
        /**
         * @desc 标题栏上的操作按钮
         * @private
         */
        _titleButton: function(){
	    	var icon_width = "";
	    	if(jazz.isIE(7) || jazz.isIE(6)){
	    		icon_width += 'width:100px';
	    	}
	    	
	        //操作按钮集 左侧区域
	        this.handlerIcons1 = $('<span class="jazz-panel-rtl-left" style="'+icon_width+'"></span>').appendTo(this.titlebarInner);
	        
	        //操作按钮集右侧区域
	        this.handlerIcons2 = $('<span class="jazz-panel-rtl-right" style="'+icon_width+'"></span>').appendTo(this.titlebarInner);
	        
	    	var handlerIcons = this.handlerIcons1;
	    	
	        if(this.options.titlebuttonalign == 'right') {
	        	handlerIcons = this.handlerIcons2;
	        }
	        
	        this.handlerIcons = handlerIcons;
        },
        
        /**
         * @desc 标题栏是否显示
         * @private
         */        
        _titledisplay: function(){
            if(!this.options.titledisplay){
            	this.titlebar.css('display', 'none');
            }else{
            	this.titlebar.css('display', '');
            }        	
        },

        /**
         * @desc fit布局的回调函数
         * @param {$this} 当前类的类对象
         * @private
         */
        _panelFitCallback: function($this){
        	var h = 0;
        	if($this.toolbar){
        		h = $this.toolbar.outerHeight(true);
        	}
        	if($this.options.titledisplay){
        		$this.content.outerHeight($this.element.height() - $this.titlebar.outerHeight(true) - h, true);
        	}else{
        		$this.content.outerHeight($this.element.height() - h, true);
        	}
        	if($this.options.frameurl){
        		$this.content.css({overflow: 'hidden'});
        		if($this.options.frameurl != null){
					$this.frameObject.outerHeight($this.content.height(), true);
        		}
        	}
        	if($this.options.vtype=="gridpanel" && $this.options.layout=="fit"){
        		//$this._width();
        	    //girlpanel只計算高度
        	    //寬度由resize事件 
        		$this._height();
        	}else {
        		//this._reflashChildWidth();
        		this._reflashChildHeight();
        	}
        },        
        
        /**
         * @desc 渲染操作按钮
		 * @param {styleClass} 头样式
		 * @param {icon} 图片样式
		 * @param {id}
		 * @private
         */
        _renderTitleButton: function(styleClass, icon, id) {
        	var f = "float:"+this.options.titlebuttonalign;
        	var _id = id ? 'id="'+id+'"': '';
            var obj = $('<a '+_id+' class="jazz-panel-titlebar-icon ' + styleClass + '" style="'+f+'" >' +
              '<span class="jazz-titlebar-icon ' + icon + '"></span></a>');
            
            if("jazz-panel-titlebar-close" == styleClass){
            	if(this.options.titlebuttonalign == "left"){
            		this.handlerIcons.append(obj);
            	}else{
            		this.handlerIcons.prepend(obj);
            	}                	
            }else{
            	this.handlerIcons.append(obj);
            }
            
            return obj;
        },        
        
		/**
         * @desc 动态改变属性
         * @param {key} 对象的属性名称 
         * @param {value} 对象的属性值
		 * @private
         */
        _setOption: function(key, value){
        	switch(key){
	    		case 'title':
	    			this.options.title = value;
	    			this.title.html(value);
	    		break;
	    		case 'titlealign':
	    			this.options.titlealign = value;
	    			this.titlebar.css({'text-align': value});
	    		break;	
	    		case 'titleicon':
	    			this.options.titleicon = value;
	    			this.title.css({'background': 'url("'+value+'")', 'background-repeat': 'no-repeat', 'padding-left': '22px'});
	    		break;
	    		case 'bgcolor':
	        		this.options.bgcolor = value;
	        		this.content.css('background-color', value);
	        	break;
	    		case 'toggleable':
	        		this.options.toggleable = value;
	        		this._toggleable();
		            if(this.options.collapsable) {
		            	this.collapse();            	
		            }else {
		            	this.expand();
		            }
	        	break;
	    		case 'collapsable':
	    			if(this.options.toggleable){
	    				if(value == "true" || value == true){
	    					this.collapse();
	    				}else{
	    					this.expand();
	    				}    	
    				}else{
    					this.options.collapsable = value;
    				}
	        	break;
	    		case 'titlebuttonalign':
	        		var align = value =="left" ? "left" : "right";
	        		if(align === "left"){
		        		if(this.options.titlebuttonalign=="right"){
		        			this.handlerIcons1.append(this.handlerIcons2.contents());
		        			this.handlerIcons2.empty();
		        			this.handlerIcons = this.handlerIcons1;
		        		}
	        		}else{
	        			if(this.options.titlebuttonalign=="left"){
		        			this.handlerIcons2.append(this.handlerIcons1.contents());
		        			this.handlerIcons1.empty();	
		        			this.handlerIcons = this.handlerIcons2;
	        			}
	        		}
	        		this.options.titlebuttonalign = align;
	        	break;	
	    		case 'customtitlebutton':
	    			this.options.customtitlebutton = value;
	    			this._customTitleButton();
	    		break;
	    		case 'closable':
	    			if(value == "true" || value == true) { 
	    				this.options.closable = true; 
	    			}else{
	    				this.options.closable = false;
	    			}
	    			this._closable();
	    		break;	        	
	    		case 'showborder':
	    			if(value == "true" || value == true) { 
	    				this.options.showborder = true; 
	    			}else{
	    				this.options.showborder = false;
	    			}
	    			this._showborder();
	    			//border的改变会影响高度和宽度的变化
	    			this._compSize();
	    		break;
	    		case 'frameurl':
	    			if(this.options.frameurl == null){
	    				this.options.frameurl = value;
	    				this._frameurl();
	    			}else{
	    				this.options.frameurl = value;
	    				this.frameObject.attr("src", value);
	    			}
	    		break;
	    		case 'titledisplay':
	    			this.options.titledisplay = value;
	    			this._compSize();
	    			this._titledisplay();
	    		break;
	    		case 'buttons':
	    			var b = this.options.buttons;
	    			this.options.buttons = value;
	    			this._buttons();
	    			if(b==null){
	    				this._compSize();	    				
	    			}
	    		break;
        	}
        	this._super(key, value);
        },
        
		/**
         * @desc 滑动向上
		 * @private
         */         
        _slideUp: function() {
            this._event('beforecollapse');
            
            if(this.options.calculateinnerheight != -1){
            	this.content_height = this.content.height();
            	this.element.height("auto");
            }            
            
            this.content.css("display", "none");
            
            this._event('aftercollapse');
            this.options.collapsable = true; 
        },

		/**
         * @desc 滑动向下
		 * @private
         */         
        _slideDown: function() {
	        this._event('beforeexpand');

	        if(this.options.calculateinnerheight != -1){
	        	this.element.height(this.options.calculateinnerheight);
	        	this.content.height(this.content_height);
	        }
	        this.content.css("display", "block");
	       
	        this._event('afterexpand');
	        this.options.collapsable = false;
        },

		/**
         * @desc 滑动向左
		 * @private
         */          
        _slideLeft: function() {
            var $this = this;

            this.originalWidth = this.options.calculateinnerwidth;

            this.title.hide();
            this.toggler.hide();
            this.content.hide();

            this.element.animate({
                width: '42px'
            }, this.options.toggleSpeed, 'easeInOutCirc', function() {
                $this.toggler.show();
                $this.element.addClass('jazz-panel-collapsed-h');
                $this.options.collapsable = !$this.options.collapsable;
            });
        },

		/**
         * @desc 滑动向右
		 * @private
         */            
        _slideRight: function() {
            var $this = this,
            expandWidth = this.originalWidth || '100%';

            this.toggler.hide();

            this.element.animate({
                width: expandWidth
            }, this.options.toggleSpeed, 'easeInOutCirc', function() {
                $this.element.removeClass('jazz-panel-collapsable-h');
                $this.title.show();
                $this.toggler.show();
                $this.options.collapsable = !$this.options.collapsable;

                $this.content.css({
                    'visibility': 'visible',
                    'display': 'block',
                    'height': 'auto'
                });
            });
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
         * @desc 标题栏内容
         * @private
         */
        _title: function(){
            var title = this.options.title || "";
            //先删除下边的对象
            this.titlebarInner.children().remove();
            
        	var titleicon = "";
        	if(this.options.titleicon){
        		titleicon = "style=\"background: url('"+this.options.titleicon+"') no-repeat; padding-left: 22px; \"";
        	}
        	
        	var tfontclass = 'jazz-panel-title-font jazz-'+this.options.vtype+'-title-font';

	    	if(this.options.titlealign){
	        	this.titlebar.css({'text-align': this.options.titlealign});
	        }

	    	this.titlebarInner.append('<span id="'+this.compId+'_title" class="'+tfontclass+'" '+titleicon+' >' + title + '&nbsp;</span>');	
	    	
	    	this.title = $('#'+this.compId+'_title');
        },        
        
		/**
         * @desc 是否可收起
		 * @private
         */
        _toggleable: function(){
        	var $this = this;
	        if(this.options.toggleable) {
	        	var icon = this.options.collapsable ? 'jazz-titlebar-icon-expand' : 'jazz-titlebar-icon-collapse';
	        	this.titlebar.find('#toggleableId').remove();
	        	
	        	this.toggler = this._renderTitleButton("", icon, "toggleableId");
	            this.toggler.off("click.toggler").on("click.toggler", function(e) {
		            if($this.options.collapsable) {
		            	$this.expand();	            	
		            }else {
		            	$this.collapse();
		            }
	                e.preventDefault();
	            });
	        }else{
	        	this.titlebar.find('#toggleableId').remove();
	        }	        
        },
        
		/**
         * @desc 组件宽度
		 * @private
         */
        _width: function(){
        	this._super();
        	if(this.iscalculatewidth){
    			this.content.outerWidth(this.options.calculateinnerwidth, true);
    			if(this.options.titledisplay){
    				this.titlebar.width('auto');
    			}
    			if(this.options.frameurl != null && this.frameObject){
    				this.frameObject.width(this.content.width());
    			}
        	}
        },

        /**
         * @desc 添加自定义按钮
         * @param {handlers} 添加自定义按钮数组对象
         * @example $(XXX).panel("addTitleButton", [{id: 't_1', align: 'left', icon: 'test2.png', 
         *  <br> //iconClass: 'customClass1',
         *	<br>   click: function(e, ui){
         *	<br>  	  ui.win.previous();
         *	<br>   }
         *  <br> }, {……}, {……}]);
         */
        addTitleButton: function(handlers){
        	var $this = this;
        	if($.isArray(handlers)){
	        	$.each(handlers, function(i, handler){
	            	var customIcons = $this.handlerIcons1;
	            	if(handler.align == 'right'){
	            		customIcons = $this.handlerIcons2;
	            	}
	            	$this._customIcon(handler, customIcons, handler.icon, handler.iconClass);
	        	});
        	}
        },        
        
		/**
         * @desc 关闭窗口
         * @example $(XXX).panel("close");
         */        
        close: function() {
            var $this = this;
            
            this._event('beforeclose', null);
            
            this.element.fadeOut("normal",
                function() {
                    $this._event('afterclose', null);
                }
            );
        },

		/**
         * @desc 窗口的收起
		 * @example $(XXX).panel("collapse");
         */         
        collapse: function() {
            this.toggler.children('span.jazz-titlebar-icon').removeClass('jazz-titlebar-icon-collapse').addClass('jazz-titlebar-icon-expand');
            
            if(this.options.toggleorientation === 'vertical') {
                this._slideUp();
            } 
            else if(this.options.toggleorientation === 'horizontal') {
                this._slideLeft();
            }
        },        
        
		/**
         * @desc 窗口的展开
         * @example $(XXX).panel("expand");
         */         
        expand: function() {
            this.toggler.children('span.jazz-titlebar-icon').removeClass('jazz-titlebar-icon-expand').addClass('jazz-titlebar-icon-collapse');
            
            if(this.options.toggleorientation === 'vertical') {
                this._slideDown();
            } 
            else if(this.options.toggleorientation === 'horizontal') {
                this._slideRight();
            }
        },
        
		/**
         * @desc 获得标题名称
         */  
        getTitle: function(){
        	return this.options.title;
        },
        
        /**
         * @desc 隐藏自定义按钮
         * @param {r} 通过选择器，得到按钮对象并隐藏  例如： "#id" ".class_name"  "div[name='btn']"
         */
        hideTitleButton: function(r){
        	$(r+"").hide();
        },

        /**
         * @desc 动态改变iframe的src
         * @param {src} 设置iframe的src元素
         */
        iframeSrcHandler: function(src){
        	if(this.frameObject){
        		this.content.loading();
        		this.frameObject.attr('src', src);
        		var $this = this;
        		this.frameObject.load(function(){
        			$this.content.loading("hide");
			    });
        	}        
        },        
        
        /**
         * @desc 打开窗口
         */
        open: function(){
        	var $this=this;
        	this._event('beforeopen',null);
        	this.element.fadeIn("normal",
        	      function(){
        		       $this._event('afteropen',null);
        	      }		
        	);
        },
        
        /**
         * @desc 移除自定义按钮
         * @param {r} 通过选择器，得到按钮对象并移除  例如： "#id" ".class_name"  "div[name='btn']"
         */
        removeTitleButton: function(r){
        	$(r+"").remove();
        },
        
        /**
         * @desc 显示自定义按钮
         * @param {r} 通过选择器，得到按钮对象并显示  例如： "#id" ".class_name"  "div[name='btn']"
         */      
        showTitleButton: function(r){
        	$(r+"").show();
        }       
    });
});