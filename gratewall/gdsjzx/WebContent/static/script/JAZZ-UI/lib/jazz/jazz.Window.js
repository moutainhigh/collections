(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 
		         'jazz.loading', 
		         'jazz.Panel' ], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 0.5
	 * @name jazz.window
	 * @description 一种窗体容器，弹出页面。
	 * @constructor
	 * @extends jazz.panel
	 */
    $.widget("jazz.window", $.jazz.panel, {
       
    	options: /** @lends jazz.window# */ {
        	
    		vtype: 'window',
    		
    		/**
    		 *@type Boolean
    		 *@desc 显示关闭面板的按钮
    		 *@default true
    		 */
    		closable: true,
    		
    		/**
    		 *@type String
    		 *@desc  是否采用Esc键关闭窗体， true 可以用Esc键关闭窗体， false不可以
    		 *@default false
    		 */
    		closeonescape: false,
    		
    		/**
    		 *@type Boolean
    		 *@desc 关闭状态  true 关闭   false 隐藏 
    		 *@default false
    		 */
    		closestate: true,        
    		
    		/**
			 *@type Boolean
			 *@desc 窗口是否拖动
			 *@default false
			 */
            draggable: true,
            
            /**
             *@type String
             *@desc 窗口高度
             *@default 230
             */
            height: 230,
            
            /**
             *@type Boolean
             *@desc 窗口是否最大化展开
             *@default false
             */            
            maximize: false,
            
            /**
             *@type Number
             *@desc 窗口最小宽度
             *@default 200
             */
            minwidth: 200,
            
            /**
             *@type Number
             *@desc 窗口的最小高度
             *@default 200
             */
            minheight: 200,
            
            /**
             *@type Boolean
             *@desc 显示最小化面板的按钮
             *@default false
             */
            minimizable: false,
            
            /**
             *@type Boolean
             *@desc 显示最大化面板的按钮
             *@default false
             */
            maximizable: false,
            
            /**
             *@type Boolean
             *@desc 窗口是否显示为模式窗体
             *@default false
             */
            modal: false,
            
            /**
			 *@type Boolean
			 *@desc 窗口是否可通过拖拽改变尺寸
			 *@default false
			 */
            resizable: false,
            
            /**
             *@type Object
             *@desc 任务栏
             *@default null
             *@private
             */            
            taskbar: null,
            
    		//覆盖panel中的属性，因为panel默认不显示
    		/**
    		 * @type Boolean
    		 * @desc 是否显示标题
    		 * @default true
    		 */
    		titledisplay: true,            
            
            /**
			 *@type Boolean
			 *@desc 窗口是否可见
			 *@default false
			 */
            visible: false,
            
            /**
             *@type String
             *@desc 窗口宽度
             *@default '300'
             */
            width: 300,

            
            //event
            /**  
             *@desc 弹出框展示后触发
             *@param {event} 事件
             *@event
			 *@example
			 *<br/>$("XXX").window("option", "afteropen", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("windowafteropen",function(event){  <br/>} <br/>});
			 *或:
			 *function XXX(){……}
			 *<div…… afteropen="XXX()"></div> 或 <div…… afteropen="XXX"></div>	
			 */
            afteropen: null,

            /**  
			 *@desc 弹出框展示前触发
			 *@param {event} 事件
			 *@event
			 *@example
			 *<br/>$("XXX").window("option", "beforeopen", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("windowbeforeopen",function(event){  <br/>} <br/>});
			 *或:
			 *function XXX(){……}
			 *<div…… beforeopen="XXX()"></div> 或 <div…… beforeopen="XXX"></div>	
			 */
            beforeopen: null,
            
            /**  
			 *@desc 弹出框隐最大化触发
			 *@event
			 *@example
			 *<br/>$("XXX").window("option", "maximize", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("windowmaximize",function(event){  <br/>} <br/>});
			 *或:
			 *function XXX(){……}
			 *<div…… maximize="XXX()"></div> 或 <div…… maximize="XXX"></div>	
			 */
            maximize: null,
            
            /**  
			 *@desc 弹出框隐最小化触发
			 *@event
			 *@example
			 *<br/>$("XXX").window("option", "minimize", function(event){  <br/>} <br/>});
			 *或:
			 *<br/>$("XXX").on("windowminimize",function(event){  <br/>} <br/>});
			 *或:
			 *function XXX(){……}
			 *<div…… minimize="XXX()"></div> 或 <div…… minimize="XXX"></div>	
			 */
            minimize: null
        },
        
        /** @lends jazz.window */
		
		/**
         * @desc 创建组件
		 * @private
         */
        _create: function() {
        	this._super();
        },

        _init: function() {
        	this._super();
        	
        	this.element.addClass('jazz-window jazz-helper-hidden');
        	
            //icons
            this.icons = this.titlebarInner.find('.jazz-panel-titlebar-icon');
            this.closeIcon = this.handlerIcons.children('.jazz-panel-titlebar-close');
            
            //最大化
            this._maximizable();
            
            //最小化
            this._minimizable(); 
            
            //是否采用esc关闭页面
            this._esc();
            
            this.blockEvents = 'focus.window mousedown.window mouseup.window keydown.window keyup.window';          
            this.parent = this.element.parent();
            
            //事件
            this._winbindEvents();

            //拖动
            this._draggable();

            //改变窗体大小
            if(this.options.resizable){
            	this._setupResizable();
            }

            //默认最大化展开
            if(this.options.maximize){
            	this.toggleMaximize();
            }
          
            //docking zone
            if($(document.body).children('.jazz-window-docking-zone').length === 0) {
                $(document.body).append('<div class="jazz-window-docking-zone"></div>');
            }            
            
            //是否可见
            if(this.options.visible){
                this.open();
            }
            
            this.pageindex = 0;
        },

        /**
         * @desc 弹出框获取焦点
         * @private
		 * @example this._applyFocus();
         */
        _applyFocus: function() {
            this.element.find(':not(:submit):not(:button):input:visible:enabled:first').focus();
        },
        
        /**
         * @desc 窗口拖动
         * @private
         */
        _draggable: function(){
            if(this.options.draggable) {
            	this.titlebar.addClass('jazz-panel-move-titlebar');
                if(this.options.maximizable){
                	this._setupDraggable();
                }else if(!this.options.maximize){
                	this._setupDraggable();
                }
            }else{
            	this.titlebar.removeClass('jazz-panel-move-titlebar');
            	this.element.draggable("option", "disabled", true);
            }     	
        },
        
        /**
         * @desc 隐藏遮罩层
		 * @private
         */
        _disableModality: function(){
        	this.modality.loading('destroy');
            this.modality = null;
            $(document).unbind(this.blockEvents).unbind('keydown.window');
        },        

        /**
         * @desc 锁定窗口
         * @private
         * @param zone
		 * @example this._dock();
         */
        _dock: function(zone) {
            this.element.appendTo(zone).css('position', 'static');
            this.element.css({'height':'auto', 'width':'auto', 'float': 'left'});
            this.content.hide();
            this.handlerIcons.find('.jazz-titlebar-icon-minus').removeClass('jazz-titlebar-icon-minus').addClass('jazz-titlebar-icon-plus');
            this.minimized = true;

            if(this.options.resizable) {
                this.resizers.hide();
            }

            zone.css('z-index',++jazz.config.zindex);

            this._event('minimize');
        },
        
        /**
         * @desc 是否用Esc键关闭窗口
         * @private
         */
        _esc: function(){
        	if(this.options.closeonescape) {
        		var $this = this;
	            $(document).on('keydown.window', function(e) {
	                var keyCode = $.ui.keyCode,
	                active = parseInt($this.element.css('z-index'), 10) === jazz.config.zindex;
	
	                if(e.which === keyCode.ESCAPE && $this.element.is(':visible') && active) {
	                    $this.close();
	                }
	            });  
        	}else{
        		$(document).off('keydown.window');
        	}
        },        
        
        /**
         * @desc 显示遮罩层
		 * @private
         */
        _enableModality: function() {
        	var $this = this;
        	//this.modalId = "modal_"+this.getCompId()+"_"+jazz.getRandom();
        	var _height = "100%";
            if(jazz.isIE(6)){
            	_height = jazz.util.windowHeight();
            }
        	this.modality = $('<div></div>').addClass('jazz-window-modal').css({
                            	'top': '0px',
                            	'left': '0px',                            	
                                'width': '100%',
                                'height': _height,
                                'z-index': $this.element.css('z-index') - 1
                            }).appendTo(document.body).loading({blank: true});
        },        

        /**
         * @desc 确定窗体的显示位置
         * @private
         */
        _initPosition: function() {
    		if(this.options.maximize){
    			this.element.css({top: 0, left: 0});
    		}else{
    			var windowWidth = jazz.util.windowWidth(), windowHeight = jazz.util.windowHeight();
    			var eleWidth = this.element.width(), eleHeight = this.element.height();

    			var top = 0, left = 0;
    			if(eleWidth > windowWidth){
    				left = 0;
    			}else{
    				left = Math.ceil((windowWidth - eleWidth)/2);
    			}
    			
    			if(eleHeight > windowHeight){
    				top = 0;
    			}else{
    				top = Math.ceil((windowHeight - eleHeight)/2);
    			}
    			
    			this.element.css({top: top, left: left});
    		}
            this.positionInitialized = true;
        },
        
        /**
         * @desc 最大化按钮
         * @private
         */
        _maximizable: function(){
        	if(this.options.maximizable === true){
        		var $this = this;
        		var a = this.titlebarInner.find('.jazz-titlebar-icon-extlink');
        		if(a.hasClass('jazz-titlebar-icon-extlink')){
        			this.titlebarInner.find('.jazz-panel-titlebar-maximize').remove();
        		}
        		var obj = this._renderTitleButton('jazz-panel-titlebar-maximize', 'jazz-titlebar-icon-extlink');
	            this.titlebar.off('dblclick.window').on('dblclick.window', function(e){
	                 $this.toggleMaximize();
	                 e.preventDefault();
	            }); 
                obj.off('click.panelmax').on('click.panelmax', function(e) {
                    $this.toggleMaximize();
                    e.preventDefault();
                });
        	}else{
        		this.titlebarInner.find('.jazz-panel-titlebar-maximize').remove();
        	}
        },

        /**
         * @desc 最小化按钮
         * @private
         */        
        _minimizable: function(){
        	if(this.options.minimizable === true){
        		var $this = this;
        		var a = this.titlebarInner.find('.jazz-titlebar-icon-minus');
        		if(a.hasClass('jazz-titlebar-icon-minus')){
        			this.titlebarInner.find('.jazz-panel-titlebar-minimize').remove();
        		}
        		var obj = this._renderTitleButton('jazz-panel-titlebar-minimize', 'jazz-titlebar-icon-minus');
                obj.on('click.panelmin', function(e) {
                    $this.toggleMinimize();
                    e.preventDefault();
                });        		
        	}else{
        		this.titlebarInner.find('.jazz-panel-titlebar-minimize').remove();
        	}
        },

        /**
         * @desc 由最大化还原成正常窗口
         * @private
         */        
        _maxToResotre: function(){
            this.element.removeClass('jazz-window-maximized');
            this._restoreState();

            this.handlerIcons.find('.jazz-titlebar-icon-newwin').removeClass('jazz-titlebar-icon-newwin').addClass('jazz-titlebar-icon-extlink');
            this.maximized = false;        	
        },
        
        /**
         * @desc 移动到最顶层
         * @private
         */
        _moveToTop: function() {
            this.element.css('z-index', ++jazz.config.zindex);
        },
        
        /**
         * @desc 显示弹出框触发afteropen事件
         * @private
         */
        _postShow: function() {
            //execute user defined callback
            this._event('afteropen', null);

            this._applyFocus();
        },       

        /**
         * @desc 恢复原窗口大小
         * @private
		 * @example this._restoreState();
         */
        _restoreState: function() {
            this.element.width(this.state.width).height(this.state.height);
            this._winCompSize(this.state.height);         
            
            var win = $(window);
            this.element.offset({
                    top: this.state.offset.top + (win.scrollTop() - this.state.windowScrollTop),
                    left: this.state.offset.left + (win.scrollLeft() - this.state.windowScrollLeft)
            });
        },
        
        _setOption: function(key, value){
        	switch(key){
				case 'closeonescape':
					if(value == "true" || value == true) { 
						this.options.closeonescape = true; 
					}else{
						this.options.closeonescape = false;
					}
					this._esc();
				break;   
				case 'draggable':
					if(value == "true" || value == true) { 
						this.options.draggable = true;
					}else{
						this.options.draggable = false;
					}
					this._draggable();
					break;
        		case 'resizable':
        			if(value == "true" || value == true) { 
        				this.options.resizable = true;
        			}else{
        				this.options.resizable = false;
        			}
        			this._setupResizable();
        		break;
        		case 'maximize':
            		if(value == "true" || value == true) { 
            			this.options.maximize = true;
            			//非最大化时，变成最大化窗口
            			if(!this.maximized){
            				this.toggleMaximize();            				
            			}
            		}else{
            			if(this.maximized){
            				this._maxToResotre();
            			}
            			this.options.maximize = false;
            		}
        		break;
        		case 'maximizable':
            		if(value == "true" || value == true) { 
            			this.options.maximizable = true;
            		}else{
            			this.options.maximizable = false;
            		}
            		this._maximizable();
        		break;
        		case 'minimizable':
            		if(value == "true" || value == true) {
            			this.options.minimizable = true;
            		}else{
            			this.options.minimizable = false;
            		}
            		this._minimizable();
        		break;
        		case 'visible':
            		if(value == "true" || value == true) {
            			this.options.visible = true;
            			this.element.show();
            		}else{
            			this.options.visible = false;
            			this.element.hide();
            		}
        		break;         		
        	}
        	this._super(key, value);
        },
        
        /**
         * @desc 设置拖拽
         * @private
         */
        _setupDraggable: function() {
        	var $this = this;
        	var xWidth = jazz.util.windowWidth() - $this.options.calculatewidth;
        	var yHeight = jazz.util.windowHeight() - $this.titlebar.outerHeight() -5;      	
        	this.element.draggable({
                cancel: '.jazz-panel-content, .jazz-panel-titlebar-close',
                handle: '.jazz-panel-titlebar',
                containment : [0, 0, xWidth, yHeight],
                start: function(e, ui){
                	if($this.maximized){  //拖动开始时判断是否是最大化窗口，如果是最大化窗口，将窗口回复原状进行拖动
                		$this.toggleMaximize();
                	}
                	var el = $this.element;
//                	$this.xWidth = $this.winSize.width + $(document).scrollLeft() - $this.element.outerWidth();
//                	$this.yHeight = $this.winSize.height + $(document).scrollTop() - $this.titlebar.outerHeight() - 3;
                	xWidth = jazz.util.windowWidth() - $this.options.calculatewidth;
                	yHeight = jazz.util.windowHeight() - $this.titlebar.outerHeight() -5;
                	el.draggable('option', 'containment', [0, 0, xWidth, yHeight]);
                	if($this.frameObject != null){
                		$this.frameObject.css('marginLeft', '-4000px');
                		$this.displayDiv.css('display', 'block');
                	}
               	 	el.css('z-index', ++jazz.config.zindex);         	
                },
                drag: function(e, ui){
                },
                stop: function(e, ui){
                	if($this.frameObject != null){
            			$this.displayDiv.css('display', 'none');
                		$this.frameObject.css('marginLeft', '0px');
                	}
                }
            });
        },

        /**
         * @desc 设置大小调整
         * @private
         */
        _setupResizable: function() {
        	if(this.options.resizable === true){
            	var $this = this;
            	this.element.resizable({
                    minWidth : this.options.minwidth,
                    minHeight : this.options.minheight,
                    start: function(e, ui){
                    	if($this.options.frameurl!=null){
                    		$this.frameObject.css('marginLeft', '-4000px');
                    		$this.displayDiv.addClass('jazz-window-framemb');
                    	}
                    	$this.content.width('100%');
                    },
                    resize: function(){},
                    stop: function(e, ui){
                        var width = ui.size.width;
                        var toHeight = ui.size.height;
                        if ($this.titlebar) {
                            toHeight -= $this.titlebar.outerHeight();
                        }
                        if ($this.toolbar) {
                            toHeight -= $this.toolbar.outerHeight();
                        }
                        $this.content.outerWidth(width);
                        $this.content.outerHeight(toHeight);
                    	if($this.options.frameurl!=null){
                    		$this.displayDiv.removeClass('jazz-window-framemb');
                    		$this.frameObject.outerWidth($this.content.width()).outerHeight($this.content.height()).css('marginLeft', '0px');
                    	}
                    }
                });                    
	        	this.resizers = this.element.children('.jazz-resizable-handle');
        	}else{
        		this.element.resizable("destroy");
        	}
        },

        /**
         * @desc 保存窗体状态
         * @private
         */
        _saveState: function() {
        	var el = this.element, win = $(window);
            this.state = {
                width: el.width(),
                height: el.height(),
                offset: el.offset(),
                windowScrollLeft: win.scrollLeft(),
                windowScrollTop: win.scrollTop()
            };
        },
        
        /**
         * @desc 绑定事件
         * @privatef
         */
        _winbindEvents: function() {   
            var $this = this, el = this.element;    
            
            el.on('click.window', function(e){
            	el.css('z-index', ++jazz.config.zindex);
            });
            
        	if(this.options.frameurl!=null){
        		$('#'+this.frameId).load(function(){
        			$('#'+$this.frameId).contents().find('body').bind('mousedown.iframe', function(e){
           			 	$this.element.css('z-index', ++jazz.config.zindex);
            		});        			
        		});
        	}
        },        
        
        /**
         * @desc 计算内容区大小
         * @private
         */
        _winCompSize: function(height) {
        	var h = 0;
        	if(this.toolbar){
        		h = this.toolbar.outerHeight();
        	}
            this.content.width('auto').outerHeight(height - this.titlebar.outerHeight() - h);
            if(this.options.frameurl != null){
            	var h = this.content.height();
            	this.frameObject.width('100%').outerHeight(h).load(function(){
            		$(this).width('100%').outerHeight(h);
            	});
            }        	
        },
           
        /**
         * @desc 关闭弹出框
         * @param {trigger} 是否触发事件  true触发事件 false不触发事件 默认true
		 * @example $("#div_id").window('close');
         */
        close: function(trigger) {
             if(this.options.taskbar instanceof jQuery){
            	var id = $('#'+this.liId);
            	if(id instanceof jQuery){
            		id.remove();
            	}
             }

             if(this.options.closestate == true){ 
	             if(!trigger){
	            	 this._event('beforeclose', null);
	             }
				 if(this.options.frameurl!=null){
					 this.frameObject.attr('src', 'about:blank');
					 try{
						 var dom = this.frameObject.get(0);
						 dom.contentWindow.document.write(''); 
						 dom.contentWindow.document.clear();
					 }catch(e){
						  //this.frameObject.parentNode.removeChild(this.frameObject.get(0));
						 this.frameObject.remove();
					 }
				 }
				 this.element.remove();
				 this.destroy();
		         if(this.options.modal) {
		         	this._disableModality();
		         }
		         if(!trigger){
		        	 this._event('afterclose', null);
		         }       
             }else{
            	 this.element.hide();
            	 if(this.options.afterhidden){
	            	 this._event('afterhidden', null);
            	 }
		         if(this.options.modal) {
		        	this._disableModality();
			     }            	 
             }
        },        

        /**
         * @desc 当前页显示
         */
        currentPage: function(url, align){
        	var obj = {
        		url: url
        	};
        	this.pageParams.push(obj);
        	this.pageindex = this.pageindex + 1;   

        	this.slider('left', url);
        	
        	if(this.pageindex === 1){
        		this.customIconId = 'customIconId'+jazz.getRandom();
        		var $this = this;
        		this.addTitleButton([{
        			id: this.customIconId,
        			align: align,
        			iconClass: 'jazz-leftarrow-icon',
        			click: function(e, ui){
        				$this.previous();
        			}
        		}]);
        	}
        },

        /**
         * @desc 下一页
         */
        next: function(){
        	var index = this.pageindex + 1;
        	if(index!=this.pageParams.length){
	        	var obj = this.pageParams[index];
	        	if(!!obj){
	        		this.slider('right', obj["url"]);
	        		this.pageindex = this.pageindex + 1;
	        	}
        	}
        },

        /**
         * @desc 显示弹出框
		 * @example $("#div_id").window('open');
         */
        open: function() {
        	this.element.css('z-index', ++jazz.config.zindex);

        	if(this.element.is(':visible')) {
                return;
            }
            
            this._event('beforeopen', null);

            //确定窗体展现的位置
            if(!this.positionInitialized) {
                this._initPosition();
            }

            this.element.show();

            var _url = this.options.frameurl;
			if(_url!=null){
				if(this.options.closestate == false){
					if(!this.frameObject.attr('src')){
						this.frameObject.attr('src', _url);
					};
				}else{
					this.frameObject.attr('src', _url);					
				}
			}

            this._postShow();

            this._moveToTop();

            if(this.options.modal) {
                this._enableModality();
            }
            
            //为青海项目单独开发的，放在门户的托盘上的
            if(this.options.taskbar instanceof jQuery){
            	var s = "";
            	if(this.options.titleicon){
            		s = 'style="background: url('+this.options.titleicon+') no-repeat center center "';
            	}
            	this.liId = 'li_id_'+jazz.getRandom();
            	var id = this.options.id ? this.options.id: this.options.name;
            	$('<li id="'+this.liId+'" winid="'+id+'"><div><span class="jazz-portal-bottom-li-img" '+s+'></span>'
    		  	 +'<span class="jazz-portal-bottom-li-word">'+this.getTitle()+'</span>'
    		  	 +'<span class="jazz-portal-bottom-li-close"></span></div></li>').appendTo(this.options.taskbar); 
            }
        },
        
        /**
         * @desc 上一页
         */
        previous: function(){
        	var index = this.pageindex - 1;
        	if(index > -1){
	        	var obj = this.pageParams[index];
	        	if(!!obj){
		        	this.slider('left', obj["url"]);
		        	this.pageindex = this.pageindex - 1;
		        	if(this.pageindex === 0){
		        		this.removeTitleButton("#"+this.customIconId);
		        	}
	        	}
        	}
        },
        
        /**
         * @desc 平滑效果
         * @param {align} 滑动方向 默认left
         * @param {url} 设置url
         */
        slider: function(align, url){
        	if(!!url){
    			var $this = this;
    			this.content.loading();
    			var margin = {marginLeft: (0 - parseInt(this.element.width()))};
                if(align=='right'){
        			margin = {marginLeft: parseInt(this.element.width())};
        		}
                if(this.options.frameurl!=null){
	    			this.frameObject.animate(margin, 200, null, function(){
    	        		$this.frameObject.attr('src', url);
    	        		$this.frameObject.load(function(){
    	        			 $this.content.loading("hide");
    	        			 $this.frameObject.css({marginLeft: 0});
    				    });
	    			});
                }
        	}
        },
        
        /**
         * @desc 最大化
         */
        toggleMaximize: function() {
            if(this.minimized) {
                this.toggleMinimize();
            }

            if(this.maximized) {
            	this._maxToResotre();
            }else {
                this._saveState();

                var winTop = this.state.windowScrollTop;        
                var winLeft = this.state.windowScrollLeft;
                
                var page = this.getPageSize();
                var winHeight = page.windowHeight, winWidth = page.windowWidth;

                if(this.options.taskbar instanceof jQuery){
                	winHeight = winHeight - this.options.taskbar.outerHeight();
                }
                
                this.element.addClass('jazz-window-maximized').outerWidth(winWidth).outerHeight(winHeight)
                            .offset({top: winTop, left: winLeft});
                
                this._winCompSize(winHeight);

                this.handlerIcons.find('.jazz-titlebar-icon-extlink').removeClass('jazz-titlebar-icon-extlink').addClass('jazz-titlebar-icon-newwin');
                this.maximized = true;
                this._event('maximize');
            }
        },

        /**
         * @desc 最小化
         */
        toggleMinimize: function() {
            var animate = true,
            dockingZone = $(document.body).children('.jazz-window-docking-zone');

            if(this.maximized) {
            	if(this.options.maximizable){
            		this.toggleMaximize();
            	}
                animate = false;
            }

            var $this = this;
            if(this.minimized) {
                //this.element.appendTo(this.parent).css({'float': 'none'});
                this._restoreState();
                this.content.show();
                this.handlerIcons.find('.jazz-titlebar-icon-plus').removeClass('jazz-titlebar-icon-plus').addClass('jazz-titlebar-icon-minus');
                this.minimized = false;

                if(this.options.resizable) {
                    this.resizers.show();
                }
                
            } else {
                this._saveState();
                
                if(this.options.taskbar instanceof jQuery){
                	this.element.css({'margin-left': '-3000px'});
                }else{
	                if(animate) {
	                    this.element.effect('transfer', {
	                        to: dockingZone,
	                        className: 'jazz-window-minimizing'
	                     }, 300,
	                     function() {
	                        $this._dock(dockingZone);
	                        var width = $this.handlerIcons1.width()*2;
	                        if($this.handlerIcons1.width() < $this.handlerIcons2.width()){
	                        	width = $this.handlerIcons2.width()*2;
	                        }
	                        $this.element.width(width + $this.titlebar.width() + 40);
	                     });
	                } else {
	                    this._dock(dockingZone);
	                    var width = $this.handlerIcons1.width()*2;
	                    if($this.handlerIcons1.width() < $this.handlerIcons2.width()){
	                    	width = $this.handlerIcons2.width()*2;
	                    }                 
	                    $this.element.width(width + $this.titlebar.width() + 40);
	                }
                
                }
            }
            
        }

    });

});
