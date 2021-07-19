 
(function($){
/** 
 * @version 0.5
 * @name jazz.icon
 * @description 图标式展现组件。
 * @constructor
 * @extends jazz.panel
 * @requires
 * @example $('XXX').icon();
 */
	$.widget('jazz.icon', $.jazz.panel, {
	    options: /** @lends jazz.icon# */ {
	    	
	    	/**
        	 *@desc 组件类型
	    	 */
	    	vtype: "icon",
	    	
			/**
			 *@desc 翻页所需要时间
			 *@default 500
			 */  	    	
	    	scrolltime: 500,
	    	
    		/**
			 *@type Number
			 *@desc 当前活动的页面
			 *@default 0
			 */
            activeindex: 0,
            
            /**
			 *@type Boolean
			 *@desc 是否启用控制按钮, true 启动  false 不启动
			 *@default 0
			 */
            iscontroller: true,

    		/**
			 *@type boolean
			 *@desc 是否显示分页条
			 *@default true
			 */            
            isshowpaginator: true,
            
    		/**
			 *@type Number
			 *@desc 显示主题
			 *@default 0
			 */            
            theme: 0,
            
            /**
			 *@type String
			 *@desc 链接
			 *@default null
			 */      
            dataurl: null,
            
            /**
			 *@type Object
			 *@desc dataurl需要的参数 {a: '123', b: '345'}
			 *@default null
			 */            
            dataurlparams: null,           
            
            //paramicons: {width: 120, height: 120},
            /**
			 *@desc 参数 适用于模式2
			 *@default {width: 140, height: 145}
			 *@private
             */
            paramicons: {width: 140, height: 145},
            
            /**
			 *@type Number
			 *@desc 显示行数量
			 *@default 2
			 */             
            rows: 2,
            
            /**
			 *@type Number
			 *@desc 显示列数量
			 *@default null
			 */                         
            cols: -1,
            
            /**
			 *@type Number
			 *@desc 
			 *@default null
             */
            iconrender: null,
	    	
			// callbacks
			/**
			 *@desc 点击图标后触发
			 *@param {event} 事件
			 *@param {ui} 
			 *@event
			 *@example
			 *<br/>$("XXX").icon("option", "click", function( event, ui ){  <br/><br/>});
			 */
	    	click: null
		},
		
		/** @lends jazz.icon */
		
		/**
         * @desc 创建组件
		 * @private
         */
		_create: function(){
			this._super();

			this.innerPanel = $('<div style="width: 100%; height: 100%"></div>').appendTo(this.content);
			this.paginator = $('<div></div>');
		},
		
		_init: function(){
			this._super();

			this.content.css("overflow", "hidden");  
			
			this.paramIconData = [];
			this.contentWidth = this.content.width();
			this.contentHeight = this.content.height();

			this._build(this.innerPanel);
			
			this._bindDrag(this.innerPanel);
			if(this.options.iscontroller && this.options.isshowpaginator){
				this._bindPaginator(this.innerPanel); //绑定翻页组件
			}			
		},

//		/**
//         * @desc 创建面板的大小
//		 * @private
//         */			
//		_panelSize: function(){  
//			var w = this.options.width,  h = this.options.height;
//			if(w.indexOf('%')==-1){
//				if(w=='')
//					w = this.element.parent().width();
//			}else{
//				w = (parseInt(w.substring(0, w.indexOf('%')))/100) * this.element.parent().outerWidth();
//			}
//			this.options.winSize = {'w': parseInt(w), 'h': parseInt(h)};
//		},
		
	   /**
        * @desc ajax请求
        */
       _ajax: function(){
           var params = {
       		   url: this.options.dataurl,
       		   params: this.options.dataurlparams,
	           callback: this._callback  //回调函数
           };
       	   $.DataAdapter.submit(params, this);        	
       },
       
       _callback: function (data, sourceThis){//alert(jazz.jsonToString(data));
	       	var jsonData = null;
	       	var $this = null;
           if(data == '1'){
	            jsonData = this.options.data;
	            $this = this;
           }else{
	           	jsonData = data;
	           	$this = sourceThis;
           }
           var icondata = jsonData["data"];
           if(jsonData != null && icondata){
        		   var theme = $this.options.theme;
        		   var k = 0;
//					var arrayLi = [];
//					if(template == "1"){
//						$.each(icondata, function(i, obj){
//							var li = $('<li class="jazz-iconLi" index="'+k+'"></li>');
//							li.append('<span class="icon"><img src="'+obj.imageurl+'"/></span><div class="text">'+obj.label+'<s></s></div>');
//							arrayLi[i] = li;
//							if(i===0){
//								$.extend($this.options.paramicons, {width: li.width(), height: li.height(), iconLiObject: arrayLi});
//							}else{
//								$.extend($this.options.paramicons, {iconLiObject: arrayLi});
//							}
//							$this.paramIconData[i] = obj; 
//							k += 1;
//						});
//					}else
        		   
					if(theme == "2"){  //给青海项目做的
						var gHeight = 0, rows = $this.options.rows;

						var innerHeight = $this.innerPanel.height();
						var all = $this.options.paramicons.width * icondata.length;  
						if(all > $this.innerPanel.width() && innerHeight > 190 && rows==1){
							$this.options.rows = 2;  rows = 2;
						}
						gHeight = parseInt(innerHeight/parseInt($this.options.rows))-1;					
						var paramicons = $this.options.paramicons;
						var iconLiObject = paramicons["iconLiObject"] = []; 
						$.each(icondata, function(i, obj){
							var li = $('<li class="jazz-iconLi2" index="'+k+'"></li>');
								li.css({height: gHeight});						
							var imgClass = '';
							if(obj.count == '0' || obj.count == undefined){
								imgClass = '';
								obj.count = '';
							}else{
								imgClass = 's-icon-img';
							}
							li.append('<span id="img_'+obj.id+'" class="'+imgClass+'">'+obj.count+'</span>');
							li.append('<div><span class="icon2" style="background: url('+obj.imageurl+')"></span><div class="text">'+obj.label+'</div></div>');
							
							var c = li.children('div');
							c.css('top', parseInt((li.height()-100)/2) + 5);
							li.hover(function(){
								c.children('span').css('background', 'url("'+obj.imageurl3+'")');
								c.children('div').addClass('jazz-icon-text2');
							},function(){
								c.children('span').css('background', 'url("'+obj.imageurl+'")');
								c.children('div').removeClass('jazz-icon-text2');
							});
							
							if(i===0){
								paramicons["height"] = li.height();
							}
							iconLiObject.push(li);
							$this.paramIconData[i] = obj; 
							k += 1;
						});
					}else{
						var gHeight = 0, gWidth = 0;
						var rows = $this.options.rows, cols = $this.options.cols;
						var innerHeight = $this.innerPanel.height();
						gHeight = parseInt(innerHeight/parseInt(rows))-2;
						if(cols != -1){
							var innerWidth = $this.innerPanel.width();
							gWidth = parseInt(innerWidth/parseInt(cols))-0;
						}
						var paramicons = $this.options.paramicons;
						var iconLiObject = paramicons["iconLiObject"] = [];
						$.each(icondata, function(i, obj){
							var li = $('<li class="jazz-iconLi2" index="'+k+'"></li>');
								li.css({height: gHeight});
								if(cols != -1){
									li.css({width: gWidth});
								}
							var imgClass = '';
							if(obj.count == '0' || obj.count == undefined){
								imgClass = '';
								obj.count = '';
							}else{
								imgClass = 's-icon-img';
							}
							
							li.append('<span id="img_'+obj.id+'" class="'+imgClass+'">'+obj.count+'</span>');
							
							var iconrender = $this.options.iconrender;
							var flag = true;
							if($.isFunction(iconrender)){
								var str = iconrender.call(this, li, obj);
								li.append(str);
								flag = false;
							}else{
								li.append('<div><span class="icon2" style="background: url('+obj.imageurl+')"></span><div class="text">'+obj.label+'</div></div>');								
							}
							if(flag){
								var c = li.children('div');
								c.css('top', parseInt((li.height()-100)/2) + 5);
								li.hover(function(){
									c.children('span').css('background', 'url("'+obj.imageurl3+'")');
									c.children('div').addClass('jazz-icon-text2');
								},function(){
									c.children('span').css('background', 'url("'+obj.imageurl+'")');
									c.children('div').removeClass('jazz-icon-text2');
								});
							}
							if(i===0){
								paramicons["width"] = li.width();
								paramicons["height"] = li.height();
							}
							iconLiObject.push(li);
							
							$this.paramIconData[i] = obj; 
							k += 1;
						});
					}
					$this.iconallnumber = k;
					
					var innerPanel = $this.innerPanel;
				
					$this._buildPage(innerPanel, $this.iconallnumber);
					
					$this.ul = innerPanel.children('ul');
					
					//绑定图标点击事件
					$this._bindIconEvent($this.ul);
					
					//添加左右按钮
					if($this.options.iscontroller){
						$this._buildButton(innerPanel, $this.ul.size());
					}
					
			        $this._updatePosition(innerPanel);   //修改各元素位置
					
//					$(window).resize(function(){
//						//$this._panelSize();
//						$this._updatePosition(innerPanel);
//			   		});					
					
   	       }
       },       

		/**
         * @desc 创建组件元素
         * @param {innerPanel} 内层div容器对象
		 * @private
         */		
		_build: function(innerPanel){
			this.iconallnumber = 0;
			
			if(this.options.data){
				this._callback("1");
			}else if(this.options.dataurl){
            	this._ajax();
            }
		},
		
		/**
         * @desc 创建页面元素
         * @param {innerPanel} 内层div容器对象
         * @param {k} 图片总数
		 * @private
         */			
		_buildPage: function(innerPanel, k){
			//每个页面摆放多少个图标
			var navBarHeight = 0;
			var gH = this.options.paramicons.height;  //一个图标总高度，包括上下margin
			var gW = this.options.paramicons.width;   //图标总宽度,包括左右margin
			
			var rows = Math.floor((this.contentHeight-navBarHeight)/gH);     //页面图标行数
			var cols = Math.floor(this.contentWidth/gW);                     //页面图标列数
			
			rows = this.options.rows;
			gH = parseInt(innerPanel.height()/parseInt(rows))-1;
			
			//计算出有多少个页面
			var perIconNum = rows*cols;
			if(perIconNum==0) perIconNum = 1;
			var pageNumber = Math.ceil(k / perIconNum);
			
			$.extend(this.options.paramicons, {rows: rows, cols: rows, pageNumber: pageNumber});
			for(var n=0; n<pageNumber; n++){
				var ul = $('<ul class="jazz-iconUl currDesktop"></ul>').appendTo(innerPanel);
				for(var j=n*perIconNum; j<(n+1)*perIconNum; j++){
					ul.append(this.options.paramicons.iconLiObject[j]);
				}
			}
			if(this.options.iscontroller && this.options.isshowpaginator){
				this._paginator(k, perIconNum);
			}
		},	
		
		/**
         * @desc 分页
         * @param {total} 总记录数量
         * @param {rows} 每页显示记录数量
		 * @private
         */			
		_paginator: function(total, rows){
//			this.paginator.paginator({
//				template: ' {PageLinks} ',
//				pagerows: rows,
//				totalrecords: total,
//				pagelinks: 20,
//				theme: '1',
//				name: 'icon_paginator' + Math.random()
//			});

			var width = this.contentWidth;
			//页面数
			var pagenum = Math.ceil(total/rows);

			var p = $('<div style="height: 20px;"></div>').appendTo(this.paginator);
			for(var i=0, len=pagenum; i<len; i++){
				p.append('<span class="jazz-icon-item" index="'+i+'"></span>');
			}
			
			//定位按钮所在位置
			var w = (width - pagenum*20)/2 - 5;
			
			p.css({"padding-left": w});
			p.outerWidth(width);
		},
		
		/**
         * @desc 修改各元素的位置
         * @param {innerPanel} 内层div容器对象
		 * @private
         */			
		_updatePosition: function(innerPanel) {   
			var w = this.contentWidth
			    ,h = this.content.height()
			    ,$this=this
				,ulNumber = this.ul.size();
			    this.moveWidth = w;

				//设置桌面图标容器元素区域大小
				innerPanel.css({width:((w) * ulNumber), height: h});
				innerPanel.children('ul').css({width: w, height: h, 'margin-right':0});
				 
				//添加翻页
				if(this.options.isshowpaginator){
					 this.paginator.appendTo(this.content).css({
						 'position': 'absolute',
						 'left': '0',
						 'bottom': '0',
						 'padding': '0'
					 });
				}
				this.ul.each(function(){
					  $this._iconsArrange($(this));
				});
				 
				//设置面板的偏移
				$this._panelMove(innerPanel, $this.ul, w, $this.options.activeindex);				 
		},		
			
		/**
         * @desc 绑定元素的拖拽事件
         * @param {innerPanel} 内层div容器对象
		 * @private
         */			
		_bindDrag: function(innerPanel){
			//var $ul = this.ul;
			var $this = this;
			
			//桌面可使用鼠标拖动切换
			var dxStart, dxEnd;  //, beginTime, endTime;
			innerPanel.draggable({
				axis: 'x',
				start: function(event,ui) {
					$(this).css("cursor", "move");
					beginTime = new Date().getTime();
					dxStart = event.pageX;
				},
				stop: function(event,ui) {
					var $ul = $this.ul;
					$(this).css("cursor", "inherit");
					//endTime = new Date().getTime();
					dxEnd = event.pageX;
					//var timeCha = endTime - beginTime
					var dxCha = dxEnd - dxStart  //鼠标的拖动距离，根据拖动距离判断是否翻页
						,currDesktop = $(this).find("ul.currDesktop")
						,deskIndex = $ul.index(currDesktop);
					var moveWidth = $this.contentWidth;

					//左移
					if(dxCha < -150 && deskIndex < ($ul.size()-1)) {
						$this._panelMove($(this), $ul, moveWidth, deskIndex+1);
					//右移
					}else if(dxCha > 150 && deskIndex > 0) {
						$this._panelMove($(this), $ul, moveWidth, deskIndex-1);
					}else{
						$(this).animate({
							left: - (deskIndex) * moveWidth
						}, $this.options.scrolltime);
					}
				}	
			});			 
		},
		
		/**
         * @desc 绑定图标事件 
         * @param {ul} ul对象
		 * @private
         */			
		_bindIconEvent: function(ul){
			var $this = this;
			this.ul.children('li').on('click', function(e, i){
				$this._trigger('click', e, $this.paramIconData[$(this).attr('index')]);
			});
		},
		
		/**
         * @desc 绑定翻页组件 
         * @param {innerPanel} 内层div容器对象
		 * @private 
         */			
		_bindPaginator: function(innerPanel){
			var $this = this;
//			$this.paginator.paginator({
//				click: function(e, ui){
//					$this._panelMove(innerPanel, $this.ul, $this.contentWidth, ui.page);
//				}
//			});
			
			 $.each($this.paginator.find(".jazz-icon-item"), function(i){
				 $(this).off("click.paicon").on("click.paicon", function(){
					  $this._panelMove(innerPanel, $this.ul, $this.contentWidth, i);
				 });
			 });
			
		},
		
		/**
         * @desc 控制面板的移动
         * @param {innerPanel} 内层div容器对象
         * @param {ul} ul元素对象
         * @param {moveWidth} 翻页时页面的宽度
         * @param {nextIndex} 页面的所引值
		 * @private
         */			
		_panelMove: function(innerPanel, ul, moveWidth, nextIndex) {
			var $this = this;
			innerPanel.stop().animate({
				 left: -(nextIndex) * moveWidth
			}, $this.options.scrolltime, function() {
				 ul.removeClass("currDesktop").eq(nextIndex).addClass("currDesktop");
				 if($this.options.isshowpaginator){
					 //$this.paginator.paginator('option', 'page', nextIndex);
					 $.each($this.paginator.find(".jazz-icon-item"), function(i){
						 if(nextIndex == i){
							 $(this).addClass("jazz-icon-item-current");
						 }else{
							 $(this).removeClass("jazz-icon-item-current");
						 }
					 });
				 }
				 if($this.options.iscontroller){
		         	 if(nextIndex > 0){
			        	 $this.leftButton.addClass('jazz-iconpanel-leftbtn');
		        	 }else{
		        		 $this.leftButton.removeClass('jazz-iconpanel-leftbtn');
		        	 }
	
		        	 if(nextIndex < ul.size()-1){
		        		 $this.rightButton.addClass('jazz-iconpanel-rightbtn');
		        	 }else{
		        		 $this.rightButton.removeClass('jazz-iconpanel-rightbtn');
		        	 }			 
				 }	 
	 		});
			this.options.activeindex = nextIndex;
	
		},		
		
		/**
         * @desc 图标的排列
         * @param {ul} ul元素对象
		 * @private
         */			
		_iconsArrange: function(ul) {
			 var $ul = ul;
			
			 var navBarHeight = 0
			     //计算一共有多少图标
			     ,iconNum = $ul.find("li").size();
				
			 	 //存储当前总共有多少桌面图标
			 	 $ul.data('iconNum', iconNum);
			 
			 var gH = this.options.paramicons.height;  //一个图标总高度，包括上下margin
			 var gW = this.options.paramicons.width;   //图标总宽度,包括左右margin
			 
			 gH = parseInt(this.innerPanel.height()/parseInt(this.options.rows))-1;
			 
			 var rows=Math.floor((this.contentHeight-navBarHeight)/gH);     //页面图标行数
			 var cols=Math.floor(this.contentWidth/gW);                    //页面图标列数
			 var curcol=0, currow=0;
			 var mW = 0; mH = 0;
			 if(this.options.theme === 1){
				 mW = 30; mH = 20;
			 }
			 
			 $ul.find("li").css({
			     "position":"absolute",
			     "margin": 0,
			     "left": function(index, value){
				             /* 行排列  */
				   		     var v = (index - cols*currow)*gW + mW;
				   		     if((index+1)%cols == 0){
				   			    currow = currow + 1;                                                
				   		     }
				   		     return v;
//	                         /* 列排列 */				   
//					           var v = curcol*gW + 30;
//					           if((index+1)%rows==0){
//							       curcol=curcol+1;
//					           }
//						       return v;	 
					    },
				 "top": function(index, value){
					         /* 行排列  */
							 var v = curcol*gH + mH;
							 if((index+1)%cols == 0){
								curcol = curcol + 1;
							 }
						     return v;
//	                         /* 行排列 */					       
//							   var v=(index-rows*currow)*gH+20;
//							   if((index+1)%rows==0){
//									currow=currow+1;
//							   }
//						       return v;
				        }
			});
		},

		/**
         * @desc 添加左右按钮滚动
         * @param {innerPanel} 内层div容器对象
         * @param {number}     窗体数量
         */	
		_buildButton: function(innerPanel, number){
			var $this = this;
	        this.leftButton = $('<a href="javascript:void(0);" target="_self"></a>').appendTo(innerPanel.parent());
	        this.rightButton = $('<a href="javascript:void(0);" target="_self"></a>').appendTo(innerPanel.parent());	
	        this.leftButton.on('click.icon', function(){
	        	if($this.options.activeindex > 0){
	        		$this._panelMove(innerPanel, $this.ul, $this.contentWidth, $this.options.activeindex-1);
	        	}
	       	});
	       	this.rightButton.on('click.icon', function(){
	       		if($this.options.activeindex < $this.ul.size()-1){
	       			$this._panelMove(innerPanel, $this.ul, $this.contentWidth, $this.options.activeindex+1);
	       		}
//2014-3-19
//加入else @desc最后页时移动到首页
	       		else{
	       			$this._panelMove(innerPanel, $this.ul, $this.contentWidth, 0);
	       		}
//2014-3-19	       		
	       	});
	       	
	       	innerPanel.parent().hover(function(){
	         	 if($this.options.activeindex > 0){
		        	 $this.leftButton.addClass('jazz-iconpanel-leftbtn');
	        	 }
//2014-3-19 @ 注释掉了if 当鼠标放到面板上时，显示按键	         	 
//	         	 if($this.options.activeindex < $this.ul.size()-1){
	         		$this.rightButton.addClass('jazz-iconpanel-rightbtn');	         		 
//	         	 }
//2014-3-19
           },function(){
           	     $this.leftButton.removeClass('jazz-iconpanel-leftbtn');
           	     $this.rightButton.removeClass('jazz-iconpanel-rightbtn');
           }); 
		},
		
	   /**
        * @desc 重新加载数据
        * @param {url} 数据URL
        * @param {params} URL所要带的参数
        * @param {flag} 标记
		* @example $('XXX').icon('loadData', url, params, flag);
        */
       loadData: function(url, params, flag){ 
	       if(!url){
	       		if(this.options.dataurl != null){
	       			if(!!params){
	       				this.options.params = params;
	       			}
	       			this.innerPanel.children().remove();
	       			if(this.options.isshowpaginator){
	       				this.paginator.remove();
	       				this.paginator = $('<div></div>');
	       			}
	    			this._build(this.innerPanel);
	    			this._bindDrag(this.innerPanel);
	    			if(this.options.isshowpaginator){
	    				this._bindPaginator(this.innerPanel); //绑定翻页组件
	    			}
	       		}
	        }else{
	        	this.innerPanel.children().remove();
	        	if(this.options.isshowpaginator){
	        		this.paginator.remove();
	        		this.paginator = $('<div></div>');	        	
	        	}
       			if(flag == 'static'){   //static  目的是为了加载SwordPageData数据
            		this.options.data = url;          		
            	}else{
            		if(!!url){
            			this.options.dataurl = url;
            			this.options.data = null;
            		}
            		if(!!params){
            			this.options.params = params;
            		}
            	}	
            	
    			this._build(this.innerPanel);
    			this._bindDrag(this.innerPanel);
    			if(this.options.isshowpaginator){
    				this._bindPaginator(this.innerPanel); //绑定翻页组件            	
    			}
	       	}
        },	
        
        getCount: function(id){
        	var img = $('#img_'+id);
	    		img.addClass('s-icon-img');
	    		
	    		if($.trim(img.html())){
	    			return img.html();
	    		}else{
	        		return 0;
	        	}
        },

        updateCount: function(url, params){
            var param = {
    		   url: url,
    		   params: params,
    		   async: false,
     	       callback: this._callbackCount  //回调函数
           };
           $.DataAdapter.submit(param, this);          	
        },
        
        _callbackCount: function (data, sourceThis){
            	var jsonData = data;
    			
    			if(!!jsonData && !!jsonData.data){
    				 for(var i = 0; i < jsonData.data.length; i++) {
    					 var obj = jsonData.data[i];
    					 if(obj.count=='0'){
    						 $('#img_'+obj.id).hide();
    					 }else{
    						 $('#img_'+obj.id).show();
    						 $('#img_'+obj.id).html(obj.count);
    					 }
    				 }
    			}
          },
          
          /**
           * @desc 外部调用组件的翻页按钮，向左
   		   * @example $('XXX').icon('leftside');
           */
          leftside: function(){
			  var innerPanel = this.innerPanel;
			  if(this.options.activeindex > 0){
				  this._panelMove(innerPanel, this.ul, this.contentWidth, this.options.activeindex-1);
	      	  }			  
			  if(this.options.activeindex == 0){
				  return false;
			  }else{
				  return true;
			  }
          },
          
          /**
           * @desc 外部调用组件的翻页按钮，向右
   		   * @example $('XXX').icon('rightside');
           */          
          rightside: function(){
        	  var innerPanel = this.innerPanel, num = this.ul.size();
	       	  if(this.options.activeindex < this.ul.size()-1){
	       		  this._panelMove(innerPanel, this.ul, this.contentWidth, this.options.activeindex+1);
	       	  }  
	       	  if(this.options.activeindex == this.ul.size()-1){
	       		  return false;
	       	  }else{
	       		  return true;
	       	  }
          }

	});
	
})(jQuery);
