(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery',  'layout/jazz.ContainerLayout'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.rowlayout
	 * @description 行布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 */
    $.widget("jazz.rowlayout", $.jazz.containerlayout, {
		
    	/** @lends jazz.rowlayout */
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutconfig} 布局需要的配置参数
         */
        layout: function(cthis, container, layoutconfig) {
        	//列布局时，禁止容器出现滚动条
        	container.css('overflow', 'hidden');
        	var containerHeight = container.height();
        	var $this = this;
        	this.layoutconfig = layoutconfig; this.cthis = cthis;
        	this.border = (layoutconfig.border == true || layoutconfig.border == "true") || this.border || false;
        	//是否可以拖动, row布局中使用
        	this.draggable = (layoutconfig.draggable == true || layoutconfig.draggable == "true") || this.draggable || false;        	

        	this.rowheights = layoutconfig.rowheight || layoutconfig.height || this.rowheights;
        	//用于判断是否为 border布局调用isborderlayout=true
        	var isborderlayout = layoutconfig.isborderlayout || false;
        	
        	//设置border布局参数的默认值， 当不是border布局时，这些参数就取默认值，确保row布局正常使用
    		var north_show_border = (layoutconfig.north_show_border == false || layoutconfig.north_show_border == "false") ? false : true;
    		var south_show_border = (layoutconfig.south_show_border == false || layoutconfig.south_show_border == "false") ? false : true;
    		
    		var north_show_switch = (layoutconfig.north_show_switch == false || layoutconfig.north_show_switch == "false") ? false : true;
    		var south_show_switch = (layoutconfig.south_show_switch == false || layoutconfig.south_show_switch == "false") ? false : true;
    		
    		var north_drag = (layoutconfig.north_drag == false || layoutconfig.north_drag == "false") ? false : true;
    		var south_drag = (layoutconfig.south_drag == false || layoutconfig.south_drag == "false") ? false : true;  
    		
 			var north_drag_min = layoutconfig.north_drag_min ? parseInt(layoutconfig.north_drag_min) : 0;
 			var north_drag_max = layoutconfig.north_drag_max ? parseInt(layoutconfig.north_drag_max) : 0;
			var south_drag_min = layoutconfig.south_drag_min ? parseInt(layoutconfig.south_drag_min) : 0;
			var south_drag_max = layoutconfig.south_drag_max ? parseInt(layoutconfig.south_drag_max) : 0;      		

			this.north_open = (layoutconfig.north_open == false || layoutconfig.north_open == "false") ? false : true;
			this.south_open = (layoutconfig.south_open == false || layoutconfig.south_open == "false") ? false : true;
        	
        	//isborderlayout == true 为border布局调用
        	//broder布局内置了column布局, 所以要通过下边设置border布局所要的参数。
        	if(isborderlayout == true){
        		this.isrowlayout = false; this.border = true;
        		//在border布局中定义的，存储各区域的对象
        		this.bobject = layoutconfig.bobject;
        		this.isnorth = layoutconfig.regionnumber.north == 1 ? true : false;
        		this.issouth = layoutconfig.regionnumber.south == 1 ? true : false;
        	}else{
        		//用于判断是否为列布局使用，而不是border布局
        		this.isrowlayout = true;
        	}    		
    		
        	var rows = this.rowheights.length || 0;
        	if(rows == 0){
        		jazz.error("行布局的rowheight设置错误, 例： ['200', '30%', '*']");
        	}
        	
        	this.borderHeight = 0;
        	
        	//计算全部固行的高度 (固定行 px 非固定行 % *)
        	this.fixRowHeight = 0; 
        	
        	//存储非固定行的对象
        	this.noFixRow = [];  
        	
        	//当有收缩按钮时，通过点击收缩按钮后， 记录原始值， 保证能通过这些值还原上一次操作的状态
        	this.topval = 0;  this.bottomval = 0;  	

        	//如果刷新布局时，先判断上一次布局是否存在边框，如果存在则把边框销毁
        	if(this.border == true && rows > 1){
        		this.bordernumber = 0;
        		if(!this.borderObj){
        			//用于存放边框对象
        			this.borderObj = [];
        		}
        		//将上一次生成的dom元素，边框清除
        		$.each(this.borderObj, function(i, obj){
        			$(obj).remove();
        		});
        		this.borderObj = [];
        	}      	
        	this.layoutdiv = container.children().filter(":visible");        	//行布局的行元素对象
        	
        	for(var i=0; i<rows; i++){
    			var tempObject = $(this.layoutdiv[i]);

    			//tempObject.removeAttr("style");  //去除原有样式
        		tempObject.addClass("jazz-row-element");
        		
        		if(this.border == true && rows > 1 && (i+1) != rows) {
	        		//border布局时判断是否存在边框
	        		if( (isborderlayout == true && ((north_show_border && this.isnorth && i==0) || (south_show_border && this.issouth && (i+2)==rows) ) )
	        				|| this.isrowlayout ){        			
	        			var div = '<div index="'+i+'" class="jazz-row-border"></div>';
	        			var $b = $(div);
	        			if(isborderlayout == true &&  (north_show_switch && this.isnorth && i==0) ){
	        				this.switchTop = $('<div class="jazz-row-btn jazz-row-btn-t"></div>').appendTo($b);
	        			}else if(isborderlayout == true && (south_show_switch && this.issouth && (i+2)==rows) ){
	        				this.switchBottom = $('<div class="jazz-row-btn jazz-row-btn-b"></div>').appendTo($b);
	        			}	        			
	        			tempObject.after($b);
	        			this.borderObj.push($b);
	        			this.bordernumber += 1;
	        			
	        			if( (isborderlayout == true && ((north_drag == true && this.isnorth && i==0) || (south_drag == true && this.issouth && (i+2)==rows)) )
	        					|| (this.isrowlayout & this.draggable)){
	        				$b.addClass("jazz-row-border-cursor");
	        			}	        			
	        			
	        			//获取边框的高度
	        			this.borderHeight = $b.outerHeight() || 0;      			
	        			
	        			if(isborderlayout == true && ( (north_show_switch && this.isnorth && i==0) || (south_show_switch && this.issouth && (i+2)==rows) ) ){
		        			//只允许第一列与最后一列，通过点击按钮进行收缩
		        			$b.children(".jazz-row-btn").off("click.rt").on("click.rt", function(){
		        				var p = $(this).parent();
		        				var $pre = p.prev(), $next = p.next();
		        				var index = p.attr("index");
		        				
		        				$this._switch(cthis, $pre, $next, index, rows, true);
		        				//刷新布局下子组件的高度
		        				cthis._reflashChildHeight(); 
		        			});      			
	        			}
	        		}
        		}
        		
        		var _rh = this.rowheights[i]+"";
        		if(_rh != "*" && _rh.indexOf('%') == -1){  //固定宽度的行
        			tempObject.outerHeight(_rh);
        			this.fixRowHeight = this.fixRowHeight + tempObject.outerHeight(true);     			
        		}else{
        			//非固定列
        			this.noFixRow.push(tempObject);
        		} 
        	}
        	
			var $this = this;
			
			container.off('mousedown.row').on('mousedown.row', function(event){
				var target = event.target, $target = $(target);
				
				//用于存放位置坐标移动范围, topborder存放拖动时，上边界值  
			    //areaheight存放各区域的宽度，通过topborder和areaheight的相加，确定能拖动的底边界值
			    //areaobject不同区域的对象
				$this.topborder = [], $this.areaheight = [], $this.areaobject = [];
				
				if($target.is('.jazz-row-border')){
					//获取被点击边框的索引值
				    var index = parseInt($target.attr("index"));

					//if判断是否允许在border布局和row布局中，是否允许拖动分割线
				    if( ( isborderlayout==true && ((north_drag && index == 0 && $this.isnorth) || (south_drag && (index+2)==rows && $this.issouth) ) )
				    		|| ($this.draggable && $this.isrowlayout)){
					
					    //点击border边框时的坐标点
					    $this.row_y = $target.offset().top;
					    
					    //点击到拖动区域时，出现拖动线
					    $this.dragrow = $('<div class="jazz-row-drag" style="display:none" islayout="no"></div>').appendTo(document.body);
	    				$this.dragrow.css({display: 'block', top: $this.row_y, left: $target.offset().left, width: $target.width() - 2});
	    				
	    				//去除在拖动过程中的选中状态
					    if (!$.browser.mozilla) {
		                    $(document).on("selectstart", function(){return false; });
		                }else {
		                    $("body").css("-moz-user-select", "none");
		                }
	
				    	$.each(container.children(".jazz-row-element"), function(i, obj){
				    		//确定每一列移动的范围
				    		$this.topborder.push($(obj).offset().top);
				    		$this.areaheight.push($(obj).height());
				    		$this.areaobject.push($(obj));
				    	});
	
				    	//获取拖动的原始距离值
				    	var tdragwidth = $this.areaheight[index];
				        var bdragwidth = $this.areaheight[index + 1];
				    	
					    //抛出border布局时，开始拖动的事件
					    if(isborderlayout == true){
					    	if(index==0 && $this.isnorth){
					    		if(!$this.north_open){ tdragwidth = 0; }
					    		cthis._event("northdragstart", null, {"north": {"height": tdragwidth}, "center": {"height": bdragwidth}});	
					    	}else{
					    		if(!$this.south_open){ bdragwidth = 0; }
					    		cthis._event("southdragstart", null, {"center": {"height": tdragwidth}, "south": {"height": bdragwidth}});
					    	}
					    }else{
					    	//抛出开始拖动时的事件
						    cthis._event("rowdragstart", null, {index: index, top: tdragwidth, bottom: bdragwidth});					    	
					    }
				    	
				    	$(document).off('mousemove.row mouseup.row').on('mousemove.row', function(e){
				    		if($this.dragrow){
				    			//上侧边界值
				    			var topborder = $this.topborder[index];
				    			//下侧边界值
				    			var bottomborder = $this.topborder[index+1] + $this.areaheight[index+1] - $this.dragrow.outerHeight();
				    			
				    			if($this.issouth && !$this.south_open && (index+2)==rows){
				    				bottomborder = $target.offset().top;
				    			}
				    			
				    			var eY = e.pageY;
				    			//确定拖动的范围
				    			if(isborderlayout == true && (topborder < eY && eY < bottomborder)){
				    				if(index==0 && $this.isnorth){
				    					if((north_drag_min < eY && eY < north_drag_max) || (north_drag_min===0 && north_drag_max===0)){
						    				$this.moving = eY;
						    				$this.dragrow.css({top: $this.moving});				    						
				    					}
				    				}else{
				    					if(((containerHeight - south_drag_max) < eY && eY < (containerHeight - south_drag_min))
				    							|| (south_drag_max===0 && south_drag_min===0)){
						    				$this.moving = eY;
						    				$this.dragrow.css({top: $this.moving});    						
				    					}
				    				}
				    			}else{
				    				if(topborder < eY && eY < bottomborder){ //确定移动的范围
				    					$this.moving = eY;
				    					$this.dragrow.css({top: $this.moving});
				    				}
				    			}				    			
				    		}
				    	}).on('mouseup.row', function(e){
				    		var _bh = $this.areaheight[index + 1];
				    		var _th = $this.areaheight[index];
	        				//通过this.moving移动的距离判断是否移动
	        				if($this.moving){
	        					var _y = $this.dragrow.offset().top - $this.row_y;	  //移动的距离
	        					var b = $this.areaobject[index + 1];
	        					var t = $this.areaobject[index];
	        					
	        					//border布局
	        					if(isborderlayout == true){
	        						//拖动north
	        						if(index==0 && $this.isnorth){
	        							if(!$this.north_open){
	        								_th = 0;
	        								if(_y > 0){
	        									$this.north_open = true;
	        								}
	        							}
		        						_bh = _bh - _y;  _th = _th + _y;
		        						b.outerHeight(_bh);
		        						t.show().outerHeight(_th); 	
	        						
	        						//拖动south
	        						}else if($this.issouth){
	        							if(!$this.south_open){
	        								_bh = 0;
	        								if(_y < 0){
	        									$this.south_open = true;
	        								}
	        							}
	        							_bh = _bh - _y;  _th = _th + _y;
		        						t.outerHeight(_th); 
		        						b.show().outerHeight(_bh);
	        						}
	        					}else{
	        						//行布局
	        						_bh = _bh - _y;  _th = _th + _y;
	        						b.outerHeight(_bh);
	        						t.outerHeight(_th); 	        						
	        					}
	        					
	    					    //抛出border布局时，结束拖动的事件
	    					    if(isborderlayout == true){
	    					    	if(index==0 && $this.isnorth){
	    					    		cthis._event("northdragstop", null, {"north": {"height": _th}, "center": {"height": _bh}});	
	    					    	}else{
	    					    		cthis._event("southdragstop", null, {"center": {"height": _th}, "south": {"height": _bh}});
	    					    	}
	    					    }else{
	    					    	cthis._event("rowdragstop", null, {index: index, top: _th, bottom: _bh});
	    					    }
	    					    //刷新布局下子组件的高度
	    					    cthis._reflashChildHeight();    					    
	        				}else{
	        					if(tdragwidth != _th && bdragwidth != _bh){
		    					    if(isborderlayout == true){
		    					    	if(index==0 && $this.isnorth){
		    					    		cthis._event("northdragstop", null, {"north": {"height": _th}, "center": {"height": _bh}});	
		    					    	}else{
		    					    		cthis._event("southdragstop", null, {"center": {"height": _th}, "south": {"height": _bh}});
		    					    	}
		    					    }else{
		    					    	cthis._event("rowdragstop", null, {index: index, top: _th, bottom: _bh});
		    					    }	
	        					}
	        				}
	
	        				$this.dragrow.remove();
	        				//将移动的距离清空
	        				$this.moving = "";
	        				
	        				//拖动结束后，还原状态
	        				if (!$.browser.mozilla) {
	        					$(document).off("selectstart");
	        				}else{
	        					$("body").css("-moz-user-select", "auto");
	        				}
	        				
	        				$(document).off('mousemove.row mouseup.row');
	        			});
					}
				}
			});               	
        	
           	//容器的总高度
			//var containerHeight = Math.min(container.height(), container.get(0).clientHeight);
        	
        	this._calculateHeight(containerHeight, this.layoutdiv, rows);
        	
			if(isborderlayout == true){
				if(this.north_open == false && this.isnorth){  //layoutconfig["centerobject"] broder布局中定义的，中间区域外围的包裹层对象
					this.north_open = true;
					this._switch(cthis, this.bobject.north.object, layoutconfig["centerobject"], 0, rows);
				}
				if(this.south_open == false && this.issouth){
					this.south_open = true;
					this._switch(cthis, layoutconfig["centerobject"], this.bobject.south.object, 1, rows);						
				}
			}
        },
        
        _switch: function(cthis, $pre, $next, index, rows, e, num){
			if((index == 0 && this.isnorth) || (this.isrowlayout && index == 0)){
				var pre = $pre, center = $next, theight = 0, cheight = 0;
				var topheight = pre.height(), centerheight = center.height();
				if(!num){num = 0;}
				if(this.north_open){
					cheight = topheight + centerheight -  num; 
					theight = num;
					if(num==0){
						pre.hide();			
					}else{
						pre.height(num);						
					}
					center.height(cheight);        						
					this.topval = topheight;
					this.north_open = false;
					this.switchTop.removeClass("jazz-row-btn-t").addClass("jazz-row-btn-b");
				}else{
  					var tval = centerheight - (this.topval - num);
					if(tval < 0){
						theight = centerheight;
						center.height(cheight);
						pre.height(theight);
						if(num==0){
							pre.show();			
						}else{
							pre.height(theight);						
						}
					}else{
						theight = this.topval; cheight = centerheight - (this.topval - num);
						center.height(cheight);
						if(num==0){
							pre.show();			
						}else{
							pre.height(theight);						
						}
					}
					this.north_open = true;
					this.switchTop.removeClass("jazz-row-btn-b").addClass("jazz-row-btn-t");
				}
				if(e){
					cthis._event("northswitch", null, {"north": {"height": theight}, "center": {"height": cheight}});					
				}
			}else if(((parseInt(index)+2) == rows && this.issouth) || (this.isrowlayout && (parseInt(index)+2) == rows)){
				var center = $pre, next = $next, bheight = 0, cheight = 0;
				var centerheight = center.height(), bottomheight = next.height();
				if(!num){num = 0;}
				if(this.south_open){
					cheight = centerheight + bottomheight - num;
					bheight = num;
					if(num==0){
						next.hide();			
					}else{
						next.height(num);						
					}					
					center.height(cheight);
					this.bottomval = bottomheight;
					this.south_open = false;
					this.switchBottom.removeClass("jazz-row-btn-b").addClass("jazz-row-btn-t");
				}else{
					var tval = centerheight - (this.bottomval - num);
					if(tval < 0){
						bheight = centerheight;
						center.height(cheight);
						next.height(bheight);
						if(num==0){
							next.show();			
						}else{
							next.height(bheight);					
						}
					}else{
						bheight = this.bottomval; cheight = centerheight - (this.bottomval - num);
    					center.height(cheight);
						if(num==0){
							next.show();			
						}else{
							next.height(bheight);						
						}
					}
					this.south_open = true;
					this.switchBottom.removeClass("jazz-row-btn-t").addClass("jazz-row-btn-b");
				} 
				if(e){
					cthis._event("southswitch", null, {"south": {"height": bheight}, "center": {"height": cheight}});					
				}
			}        	
        },
        
		/**
         * @desc 计算宽度
         * @param {containerWidth} 容器的宽度
         * @param {element} 列布局的元素对象
         * @param {rows} 行数
         * @private
         */  
        _calculateHeight: function(containerHeight, element, rows){
        	//其他部分的高度
        	var otherHeight = 0; 
        	if(this.border == true && rows > 1){
        		containerHeight = containerHeight - this.borderHeight * this.bordernumber;
        		otherHeight = containerHeight - this.fixRowHeight;
        	}        	
        	
        	otherHeight = containerHeight - this.fixRowHeight;
        	
//        	jazz.log("*** row *******行布局 容器高度************"+containerHeight);
//        	jazz.log("*** row *******其他区域高度**************"+otherHeight);
        	
        	//百分比总宽度
        	this.percentHeight = 0;
        	for(var i=0; i<rows; i++){
        		var rowheight = this.rowheights[i];
        		var tempObject = $(element[i]);
        		//判断是否为百分比列宽
        		if(!!rowheight && rowheight.indexOf('%')!= -1){
        			var n = parseInt(rowheight.substring(0, rowheight.indexOf('%')));
        			rowheight = (n/100)*otherHeight;
        			//向下取整
        			rowheight = Math.floor(rowheight);
        			tempObject.outerHeight(rowheight);
        			
        			this.percentHeight += tempObject.outerHeight();
        		}
        		//自适应高度行
        		if($.trim(rowheight) == '*'){
        			this.emptyRow = tempObject;
        		}
        	}
        	
//        	jazz.log("*** row ******百分比行宽度**************"+this.percentHeight);
        	
        	//去除固定列、百分比列高度后，剩余高度
        	this.autoHeight = containerHeight - this.fixRowHeight - this.percentHeight;
        	
        	//判断未指定宽度的列的数量，仅有1列时，计算这列的宽度
        	if(this.emptyRow){
	    		this.emptyRow.outerHeight(this.autoHeight);
        	}

//        	jazz.log("*** row ******自适应行宽度**************"+this.autoHeight);
        	
    		//缓存容器高度,用于监听时进行比较窗体高度是否发生改变
        	this.cacheHeight = containerHeight;
        },
        
        /**
         * @desc 布局中区域的打开关闭的控制,只控制border布局 column布局 row布局
         * @param region 区域 border布局 
         *        region为east west north south
         * @param regionNumber 区域的数量
         * @param showHeight 显示高度
         */
        layoutSwitch: function(region, regionNumber, showHeight){
        	//用于判断是否为 border布局调用isborderlayout=true
        	var isborderlayout = this.layoutconfig.isborderlayout || false;
			if(isborderlayout == true){
				if(region=="north"){
					this._switch(this.cthis, this.bobject.north.object, this.layoutconfig["centerobject"], 0, regionNumber, true, showHeight);
    				//刷新布局下子组件的高度
    				this.cthis._reflashChildHeight(); 
				}
				if(region=="south"){
					this._switch(this.cthis, this.layoutconfig["centerobject"], this.bobject.south.object, 1, regionNumber, true, showHeight);
    				//刷新布局下子组件的高度
    				this.cthis._reflashChildHeight(); 
				}
			}
        },
        
        /**
         * @desc 设置布局自身容器的overflow样式
         */        
        reflashLayoutBefore: function(){
        	var cols = this.rowheights.length || 0;
        	this._overflow_xy = [];
        	for(var i=0, len=cols; i<len; i++){
        		var obj = $(this.layoutdiv[i]);
        		this._overflow_xy.push({x: obj.css("overflow-x"), y: obj.css("overflow-y")});
        		obj.css("overflow-x", "hidden"); obj.css("overflow-y", "hidden");
        	}
        },
        
        /**
         * @desc 恢复布局自身容器的overflow样式
         */        
        reflashLayoutAfter: function(){
        	if(this._overflow_xy.length > 1){
	        	var cols = this.rowheights.length || 0;
	        	for(var i=0, len=cols; i<len; i++){
	        		var obj = $(this.layoutdiv[i]);
	        		obj.css("overflow-x", this._overflow_xy[i]["x"]); 
	        		obj.css("overflow-y", this._overflow_xy[i]["y"]);
	        	}
	        	this._overflow_xy = [];
        	}
        }        

    });

});