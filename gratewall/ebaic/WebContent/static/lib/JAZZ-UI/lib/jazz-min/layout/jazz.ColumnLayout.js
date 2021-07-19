(function( $, factory ){

	if ( jazz.config.isUseRequireJS === true ) {
		define( ['jquery', 'layout/jazz.ContainerLayout'], factory );
	} else {
		factory($);
	}	 
})(jQuery, function($){
	/**
	 * @version 1.0
	 * @name jazz.columnlayout
	 * @description 列布局。
	 * @constructor
	 * @extends jazz.containerlayout
	 */
    $.widget("jazz.columnlayout", $.jazz.containerlayout, {

    	/** @lends jazz.columnlayout */
		/**
         * @desc 设置布局
         * @param {cthis} 当前组件对象
         * @param {container} 需要渲染的容器对象
         * @param {layoutconfig} 布局需要的配置参数
         */
        layout: function(cthis, container, layoutconfig) {

        	//列布局时，禁止容器出现滚动条
        	container.css('overflow', 'hidden');
			var containerWidth = container.width();
        	var $this = this;
        	this.layoutconfig = layoutconfig; this.cthis = cthis;
			//是否允许有边框
        	this.border = (layoutconfig.border == true || layoutconfig.border == "true") || this.border || false;
        	//是否可以拖动, coloumn布局中使用
        	this.draggable = (layoutconfig.draggable == true || layoutconfig.draggable == "true") || this.draggable || false;
        	//layoutconfig.columnwidth 0.5版本属性 || layoutconfig.width 1.0版本属性， 同事支持两种写法
        	this.colwidths = layoutconfig.columnwidth || layoutconfig.width || this.colwidths;

        	//用于判断是否为 border布局调用isborderlayout=true
        	var isborderlayout = layoutconfig.isborderlayout || false;
       	
        	//设置border布局参数的默认值, 当不是border布局时，这些参数就取默认值，确保column布局正常使用
    		var west_show_border = (layoutconfig.west_show_border == false || layoutconfig.west_show_border == "false") ? false : true;
    		var east_show_border = (layoutconfig.east_show_border == false || layoutconfig.east_show_border == "false") ? false : true;
    		
    		var west_show_switch = (layoutconfig.west_show_switch == false || layoutconfig.west_show_switch == "false") ? false : true;
    		var east_show_switch = (layoutconfig.east_show_switch == false || layoutconfig.east_show_switch == "false") ? false : true;
    		
    		var west_drag = (layoutconfig.west_drag == false || layoutconfig.west_drag == "false") ? false : true;
    		var east_drag = (layoutconfig.east_drag == false || layoutconfig.east_drag == "false") ? false : true;
    		
 			var west_drag_min = layoutconfig.west_drag_min ? parseInt(layoutconfig.west_drag_min) : 0;
			var west_drag_max = layoutconfig.west_drag_max ? parseInt(layoutconfig.west_drag_max) : 0;
			var east_drag_min = layoutconfig.east_drag_min ? parseInt(layoutconfig.east_drag_min) : 0;
			var east_drag_max = layoutconfig.east_drag_max ? parseInt(layoutconfig.east_drag_max) : 0;    		

    		this.west_open = (layoutconfig.west_open == false || layoutconfig.west_open == "false") ? false : true;
    		this.east_open = (layoutconfig.east_open == false || layoutconfig.east_open == "false") ? false : true;			
			
        	//isborderlayout == true 为border布局调用
        	//broder布局内置了column布局, 所以要通过下边设置border布局所要的参数。
        	if(isborderlayout == true){
        		this.iscolumnlayout = false; this.border = true;
        		//在border布局中定义的，存储各区域的对象
        		this.bobject = layoutconfig.bobject;
        		this.iswest = layoutconfig.regionnumber.west == 1 ? true : false;        		
        		this.iseast = layoutconfig.regionnumber.east == 1 ? true : false;
        	}else{
        		//用于判断是否为列布局使用，而不是border布局
        		this.iscolumnlayout = true;
        	}
        	
        	var cols = this.colwidths.length || 0;   //定义的列数
        	if(cols == 0){
        		jazz.error("行布局的rowheight设置错误, 例： ['200', '30%', '*']");
        	}

//        	//计算后的宽度
//        	this.calculatewidth = [cols];
//        	layoutconfig["calculatewidth"] = this.calculatewidth;
        	
        	this.borderWidth = 0;
        	
        	//计算全部固定列的宽(固定列 px 非固定列 % *)
        	this.fixColumnWidth = 0; 
        	
        	//存储非固定列的对象
        	this.noFixColumn = [];   
        	
        	//当有收缩按钮时，通过点击收缩按钮后，记录上一次的值, 保证能通过这些值还原上一次操作的状态
        	this.leftval = 0;  this.rightval = 0;
        	
        	//如果刷新布局时，先判断上一次布局是否存在边框，如果存在则把边框销毁
        	
        	if(this.border == true && cols > 1){
        		this.bordernumber = 0;
        		if(!this.borderObj){
        			//用于存放边框对象
        			this.borderObj = [];
        		}
        		$.each(this.borderObj, function(i, obj){
        			$(obj).remove();
        		});
        		this.borderObj = [];
        	}

        	this.layoutdiv = container.children().filter(":visible");	//列布局的列元素对象
        	
        	for(var i=0; i<cols; i++){
    			var tempObject = $(this.layoutdiv[i]);

    			//tempObject.removeAttr("style");  //去除原有样式
        		tempObject.addClass('jazz-column-element');
        		if(this.border == true && cols > 1 && (i+1) != cols) {
        			//border布局时判断是否存在边框
        			if( (isborderlayout==true && ( (west_show_border && this.iswest && i==0) || (east_show_border == true && this.iseast && (i+2)==cols) ) )
        					|| this.iscolumnlayout){
        				
	        			var div = '<div index="'+i+'" class="jazz-column-border"></div>';
	        			var $b = $(div);
	        			if( isborderlayout==true && (west_show_switch && this.iswest && i==0) ){
	        				this.switchLeft = $('<div class="jazz-column-btn jazz-column-btn-l"></div>').appendTo($b);
	        			}else if(isborderlayout==true && (east_show_switch && this.iseast && (i+2)==cols)){
	        				this.switchRight = $('<div class="jazz-column-btn jazz-column-btn-r"></div>').appendTo($b);
	        			}
	        			tempObject.after($b);
	        			this.borderObj.push($b);
	        			this.bordernumber += 1;
	        			
	        			if( (isborderlayout==true && ((west_drag && this.iswest && i==0) || (east_drag && this.iseast && (i+2)==cols))) 
	        					|| (this.iscolumnlayout && this.draggable)){
	        				$b.addClass("jazz-column-border-cursor");
	        			}
	        			
	        			//获取边框的宽度
	        			this.borderWidth = $b.outerWidth() || 0;
	        			
	        			if(isborderlayout==true && ((west_show_switch && this.iswest && i==0) || (east_show_switch && this.iseast && (i+2)==cols)) ){
		        			//只允许第一列与最后一列，通过点击按钮进行收缩
		        			$b.children(".jazz-column-btn").off("click.ct").on("click.ct", function(){
		        				var p = $(this).parent();
		        				var $pre = p.prev(), $next = p.next();
		        				var index = p.attr("index");

		        				$this._switch(cthis, $pre, $next, index, cols, true);
		        				//刷新布局下子组件的宽度
		        				cthis._reflashChildWidth();
		        			});
	        			}
	        		}
        		}
		
        		var _cw = this.colwidths[i]+"";
        		if(_cw != "*" && _cw.indexOf('%') == -1){  //固定宽度的列
        			tempObject.outerWidth(_cw);
//        			this.calculatewidth[i] = _cw;
        			var a = tempObject.outerWidth();
        			if(a < 0){ a = 0; }
        			this.fixColumnWidth = this.fixColumnWidth + a;  			
        		}else{  //非固定列
        			this.noFixColumn.push(tempObject);
        		}
        	}
        	
        	if(cols > 0){
        		if(container.children(".jazz-clear-both").length==0){
        			container.append('<div class="jazz-clear-both"></div>');
        		}
        	}
        	
			container.off('mousedown.column').on('mousedown.column', function(event){
				var target = event.target, $target = $(target);
				
				//用于存放位置坐标移动范围, leftborder存放拖动时，左侧边界值  
			    //areawidth存放各区域的宽度，通过leftborder和areawidth的相加，确定能拖动的右侧边界值
			    //areaobject不同区域的对象
				$this.leftborder = [], $this.areawidth = [], $this.areaobject = [];
				
				if($target.is('.jazz-column-border')){
					//获取被点击边框的索引值
				    var index = parseInt($target.attr("index"));
				    				    
				    //if判断是否允许在border布局和column布局中，是否允许拖动分割线
				            //border布局时
				    if( ( isborderlayout==true && ( (west_drag && index == 0 && $this.iswest) || (east_drag && (index+2)==cols && $this.iseast) ) )
				    	       //可拖拽的列布局
				    		|| ($this.draggable == true && $this.iscolumnlayout) ){
						//点击border边框时的坐标点
					    $this.clo_x = $target.offset().left;
					    
					    //点击到拖动区域时，出现拖动线
					    $this.dragcol = $('<div class="jazz-column-drag" style="display:none" islayout="no"></div>').appendTo(document.body);
					    
	    				$this.dragcol.css({display: 'block', top: $target.offset().top, left: $this.clo_x, height: $target.height() - 2});
	    				
	    				//去除在拖动过程中的选中状态
					    if (!$.browser.mozilla) {
		                    $(document).on("selectstart", function(){return false; });
		                }else {
		                    $("body").css("-moz-user-select", "none");
		                }
					    
				    	$.each(container.children(".jazz-column-element"), function(i, obj){
				    		//确定每一列移动的范围
				    		$this.leftborder.push($(obj).offset().left);
				    		$this.areawidth.push($(obj).width());
				    		$this.areaobject.push($(obj));
				    	});
				    	
				    	//获取拖动的原始距离值
				    	var ldragwidth = $this.areawidth[index];
				        var rdragwidth = $this.areawidth[index + 1];

					    //抛出border布局时，开始拖动的事件
					    if(isborderlayout == true){
					    	if(index==0 && $this.iswest){
					    		if(!$this.west_open){ ldragwidth = 0; }
					    		cthis._event("westdragstart", null, {"west": {"width": ldragwidth}, "center": {"width": rdragwidth}});	
					    	}else{
					    		if(!$this.east_open){ rdragwidth = 0; }
					    		cthis._event("eastdragstart", null, {"center": {"width": ldragwidth}, "east": {"width": rdragwidth}});
					    	}
					    }else{
						    //抛出column布局时，开始拖动时的事件
						    cthis._event("columndragstart", null, {index: index, left: ldragwidth, right: rdragwidth});					    	
					    }
					    
				    	$(document).off('mousemove.column mouseup.column').on('mousemove.column', function(e){
				    		if($this.dragcol){
				    			//左侧边界值
				    			var leftborder = $this.leftborder[index];
				    			//右侧边界值
				    			var rightborder = $this.leftborder[index+1] + $this.areawidth[index+1] - $this.dragcol.outerWidth();
				    			
				    			if($this.iseast && !$this.east_open && (index+2)==cols){
				    				rightborder = $target.offset().left;
				    			}
				    			var eX = e.pageX;
				    			
				    			//确定拖动的范围
				    			if(isborderlayout == true && (leftborder < eX && eX < rightborder)){
				    				if(index==0 && $this.iswest){
				    					//(west_drag_min===0 && west_drag_max===0) 表示没有设置拖动范围
				    					if((west_drag_min < eX && eX < west_drag_max) || (west_drag_min===0 && west_drag_max===0)){
						    				$this.moving = eX;
						    				$this.dragcol.css({left: $this.moving});			    						
				    					}
				    				}else{
				    					//(east_drag_max===0 && east_drag_min===0) 没有设置拖动范围
				    					if(((containerWidth - east_drag_max) < eX && eX < (containerWidth - east_drag_min)) 
				    							|| (east_drag_max===0 && east_drag_min===0)){
						    				$this.moving = eX;
						    				$this.dragcol.css({left: $this.moving});				    						
				    					}
				    				}
				    			}else if(leftborder < eX && eX < rightborder){
				    				$this.moving = eX;
				    				$this.dragcol.css({left: $this.moving});
				    			}
				    		} 
				    	}).on('mouseup.column', function(e){
        					var _rw = $this.areawidth[index + 1];
        					var _lw = $this.areawidth[index];
				    		
	        				//通过this.moving移动的距离判断是否移动
	        				if($this.moving){
	        					var _x = $this.dragcol.offset().left - $this.clo_x;	  //移动的距离
	        					var r = $this.areaobject[index + 1];
	        					var l = $this.areaobject[index];
	        					
	        					//border布局
	        					if(isborderlayout == true){
	        						//拖动west
	        						if(index==0 && $this.iswest){
	        							if(!$this.west_open){
	        								_lw = 0;
	        								if(_x > 0){
	        									$this.west_open = true;
	        								}
	        							}
	        							_rw = _rw - _x;  _lw = _lw + _x;	
	        							r.outerWidth(_rw);
	        							l.show().outerWidth(_lw);
	        						//拖动east	
	        						}else if($this.iseast){
	        							if(!$this.east_open){
	        								_rw = 0;
	        								if(_x < 0){
	        									$this.east_open = true;
	        								}
	        							}
	        							_rw = _rw - _x;  _lw = _lw + _x;	
	        							l.outerWidth(_lw);
	        							r.show().outerWidth(_rw);
	        						}
	        					}else{
	        						//列布局
		        					_rw = _rw - _x;  _lw = _lw + _x;	
		        					r.outerWidth(_rw);
		        					l.outerWidth(_lw);	        						
	        					}
	        					
	    					    //抛出border布局时，结束拖动的事件
	    					    if(isborderlayout == true){
	    					    	if(index==0 && $this.iswest){
	    					    		cthis._event("westdragstop", null, {"west": {"width": _lw}, "center": {"width": _rw}});	
	    					    	}else{ 
	    					    		cthis._event("eastdragstop", null, {"center": {"width": _lw}, "east": {"width": _rw}});
	    					    	}
	    					    }else{
	    					    	cthis._event("columndragstop", null, {index: index, left: _lw, right: _rw});
	    					    }
	    					    //刷新布局下子组件的宽度
	    					    cthis._reflashChildWidth();
	        				}else{
	        					if(ldragwidth != _lw && rdragwidth != _rw){  
		    					    //抛出border布局时，结束拖动的事件
		    					    if(isborderlayout == true){
		    					    	if(index==0 && $this.iswest){
		    					    		cthis._event("westdragstop", null, {"west": {"width": _lw}, "center": {"width": _rw}});	
		    					    	}else{
		    					    		cthis._event("eastdragstop", null, {"center": {"width": _lw}, "east": {"width": _rw}});
		    					    	}
		    					    }else{
		    					    	cthis._event("columndragstop", null, {index: index, left: _lw, right: _rw });
		    					    }	        						
	        					}
	        				}
	
	        				$this.dragcol.remove();
	        				//将移动的距离清空
	        				$this.moving = "";
	        				
	        				//拖动结束后，还原状态
	        				if (!$.browser.mozilla) {
	        					$(document).off("selectstart");
	        				}else{
	        					$("body").css("-moz-user-select", "auto");
	        				}
	        				
	        				$(document).off('mousemove.column mouseup.column');
	        			});
					}
				
				}
				
			});        	

           	//容器的总宽度
        	//var containerWidth = Math.min(container.width(), container.get(0).clientWidth);
        	
			this._calculateWidth(containerWidth, this.layoutdiv, cols);

			if(isborderlayout == true){
				if(this.west_open == false && this.iswest){
					this.west_open = true;
					this._switch(cthis, this.bobject.west.object, this.bobject.center.object, 0, cols, false);
				}
				if(this.east_open == false && this.iseast){
					this.east_open = true;
					this._switch(cthis, this.bobject.center.object, this.bobject.east.object, 1, cols, false);
				}
			}
        },
        
        _switch: function(cthis, $pre, $next, index, cols, e, num){
			if((index == 0 && this.iswest) || (this.iscolumnlayout && index == 0)){
				var pre = $pre, center = $next, lwidth = 0, cwidth = 0;
				var leftwidth = pre.width(), centerwidth = center.width();
				if(!num){num = 0;}
				if(this.west_open){
					cwidth = leftwidth + centerwidth -  num;
					lwidth = num;
					if(num == 0){
						pre.hide();
					}else{
						pre.width(num);
					}
					center.width(cwidth);					
					this.leftval = leftwidth;
					this.west_open = false;
					this.switchLeft.removeClass("jazz-column-btn-l").addClass("jazz-column-btn-l2");
				}else{
					var tval = centerwidth - (this.leftval - num);
					if(tval < 0){
						lwidth = centerwidth;
						center.width(cwidth);
						pre.width(lwidth);
						if(num==0){
							pre.show();			
						}else{
							pre.width(lwidth);						
						}
					}else{
						lwidth = this.leftval; cwidth = centerwidth - (this.leftval - num);
						center.width(cwidth);
						if(num==0){
							pre.show();
						}else{
							pre.width(lwidth);					
						}
					}
					this.west_open = true;
					this.switchLeft.removeClass("jazz-column-btn-l2").addClass("jazz-column-btn-l");
				}
				if(e){
					cthis._event("westswitch", null, {"west": {"width": lwidth}, "center": {"width": cwidth}});
				}
			}else if(((parseInt(index)+2) == cols && this.iseast) || (this.iscolumnlayout && index == (parseInt(index)+2) == cols)){
				var center = $pre, next = $next, rwidth = 0, cwidth = 0;
				var centerwidth = center.width(), rightwidth = next.width();
				if(!num){num = 0;}
				if(this.east_open){
					cwidth = centerwidth + rightwidth - num;
					rwidth = num;
					if(num==0){
						next.hide();			
					}else{
						next.width(num);						
					}
					center.width(cwidth);
					this.rightval = rightwidth;
					this.east_open = false;
					this.switchRight.removeClass("jazz-column-btn-r").addClass("jazz-column-btn-r2");
				}else{
					var tval = centerwidth - (this.rightval - num);
					if(tval < 0){
						rwidth = centerwidth;
						center.width(cwidth);
						next.width(rwidth);
						if(num==0){
							next.show();			
						}else{
							next.width(rwidth);					
						}
					}else{
						rwidth = this.rightval; cwidth = centerwidth - (this.rightval - num);
						center.width(cwidth);
						if(num==0){
							next.show();			
						}else{
							next.width(rwidth);				
						}
					}
					this.east_open = true;
					this.switchRight.removeClass("jazz-column-btn-r2").addClass("jazz-column-btn-r");
				}
				if(e){
					cthis._event("eastswitch", null, {"east": {"width": rwidth}, "center": {"width": cwidth}});					
				}
			}
        },
        
		/**
         * @desc 计算宽度
         * @param {containerWidth} 容器的宽度
         * @param {element} 列布局的元素对象
         * @param {cols} 列数
         * @private
         */
        _calculateWidth: function(containerWidth, element, cols){
        	 //其他部分的宽度
        	var otherWidth = 0;
        	if(this.border == true && cols > 1){
        		containerWidth = containerWidth - this.borderWidth * this.bordernumber;
        		otherWidth = containerWidth - this.fixColumnWidth;
        	}
        	
        	otherWidth = containerWidth - this.fixColumnWidth;
        	
//        	jazz.log("*** column *******列布局 容器宽度************"+containerWidth);
//        	jazz.log("*** column *******其他区域宽度**************"+otherWidth);
        	
        	//百分比总宽度
        	this.percentWidth = 0;  //, ei = 0;
        	for(var i=0; i<cols; i++){
        		var colwidth = this.colwidths[i];
        		var tempObject = $(element[i]);
        		//判断是否为百分比列宽
        		if(!!colwidth && colwidth.indexOf('%') != -1){
        			var n = parseInt(colwidth.substring(0, colwidth.indexOf('%')));
        			colwidth = (n/100) * otherWidth;
        			//向下取整数
        			colwidth = Math.floor(colwidth);
//        			this.calculatewidth[i] = colwidth;
        			tempObject.outerWidth(colwidth);
        			
        			this.percentWidth += tempObject.outerWidth();
        		}
        		//自适应宽度列
        		if($.trim(colwidth) == '*'){
        			this.emptyColumn = tempObject;
//        			ei = i;
        		}
        	}
        	
//        	jazz.log("*** column ******百分比列宽度**************"+this.percentWidth);
        	
        	//去除固定列、百分比列宽度后，剩余宽度
        	this.autoWidth = containerWidth - this.fixColumnWidth - this.percentWidth;
        	
        	//判断未指定宽度的列的数量，仅有1列时，计算这列的宽度
        	if(this.emptyColumn){
//        		this.calculatewidth[ei] = this.autoWidth;
	    		this.emptyColumn.outerWidth(this.autoWidth);
        	}
        	
//        	jazz.log("*** column ******自适应列的宽度**************"+this.autoWidth);
    		
    		//缓存容器宽度,用于监听时进行比较窗体宽度是否发生改变
        	this.cacheWidth = containerWidth;
        },
        
        /**
         * @desc 布局中区域的打开关闭的控制,只控制border布局 column布局 row布局
         * @param region 区域 border布局 
         *        region为east west north south
         * @param regionNumber 区域的数量
         * @param showWidth 显示宽度 
         */
        layoutSwitch: function(region, regionNumber, showWidth){
        	//用于判断是否为 border布局调用isborderlayout=true
        	var isborderlayout = this.layoutconfig.isborderlayout || false;
			if(isborderlayout == true){
				if(region=="west"){
					this._switch(this.cthis, this.bobject.west.object, this.bobject.center.object, 0, regionNumber, true, showWidth);
					//刷新布局下子组件的宽度
					this.cthis._reflashChildWidth();
				}
				if(region=="east"){
					this._switch(this.cthis, this.bobject.center.object, this.bobject.east.object, 1, regionNumber, true, showWidth);
					//刷新布局下子组件的宽度
					this.cthis._reflashChildWidth();
				}
			}
        },
        
        /**
         * @desc 设置布局自身容器的overflow样式
         */        
        reflashLayoutBefore: function(){
        	var cols = this.colwidths.length || 0;
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
	        	var cols = this.colwidths.length || 0;
	        	for(var i=0, len=cols; i<len; i++){
	        		var obj = $(this.layoutdiv[i]);
	        		obj.css("overflow-x", this._overflow_xy[i]["x"]); 
	        		obj.css("overflow-y", this._overflow_xy[i]["y"]);
	        	}
	        	this._overflow_xy = [];
        	}
        	this.cthis._reflashChildWidth();
        }

    });

});
