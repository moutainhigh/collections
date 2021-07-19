jQuery.iPopWindow = {
	create:function(config){
		var p = this;
		this.config = {
			caption 	: config.caption||'pop window',
			content 	: config.content,
			dragHandleId: config.dragHandleId||'dragHandleId',
			minIcon 	: config.minIcon||'images/window/window_min.jpg',
			maxIcon 	: config.maxIcon||'images/window/window_max.jpg',
			closeIcon	: config.closeIcon||'images/window/window_close.jpg',
			resizeIcon  : config.resizeIcon||'images/window/window_resize.gif',
			onClose     : config.onClose,
			minWidth:  config.minWidth,
			minHeight:  config.minHeight,
			maxWidth:  config.maxWidth,
			maxHeight:  config.maxHeight,
			minTop:     config.minTop,
			minLeft:    config.minLeft
		};
		var dragHandle = "#top_"+this.config.dragHandleId;
		var resizeSe = "#se_"+this.config.dragHandleId;
		
		this.addClass("popWindow");
		var windowTop = jQuery("<div id=\"top_"+this.config.dragHandleId+"\"  class=\"popWindow_top\"></div>");
		var windowTopContent = jQuery("<div class=\"popWindow_top_content\">"+this.config.caption+"</div>");
		var windowBottom = jQuery("<div class=\"popWindow_bottom\"></div>");
		var windowBottomContent = jQuery("<div class=\"popWindow_bottom_content\"></div>");
		var windowContent = jQuery("<div class=\"popWindow_content\"></div>");
		//<img src="images/window_min.jpg" tppabs="http://interface.eyecon.ro/demos/images/window_min.jpg" id="windowMin" />
		//<img src="images/window_max.jpg" tppabs="http://interface.eyecon.ro/demos/images/window_max.jpg" id="windowMax" />
		//<img src="images/window_close.jpg" tppabs="http://interface.eyecon.ro/demos/images/window_close.jpg" id="windowClose" />
		var window_minImg = jQuery("<img src=\""+this.config.minIcon+"\" class=\"popWindow_img_min\"></img>");
		var window_maxImg = jQuery("<img src=\""+this.config.maxIcon+"\" class=\"popWindow_img_max\"></img>");
		var window_closeImg = jQuery("<img src=\""+this.config.closeIcon+"\" class=\"popWindow_img_close\"></img>");
		
		var windowResizeImg = jQuery("<img id=\"se_"+this.config.dragHandleId+"\" src=\""+this.config.resizeIcon+"\" class=\"popWindow_img_resize\"></img>");
		//this.Draggable();
		windowTop.append(windowTopContent);
		windowTop.append(window_minImg);
		windowTop.append(window_maxImg);
		windowTop.append(window_closeImg);
		
		windowBottom.append(windowBottomContent);
		windowContent.append(this.config.content);
		this.append(windowTop);
		this.append(windowBottom);
		this.append(windowContent);
		this.append(windowResizeImg);

		window_minImg.bind("click",function(){
			windowContent.SlideToggleUp(300);
			windowBottom.animate({height: 10}, 300);
			windowBottomContent.animate({height: 10}, 300);
			p.animate({height:40},300).get(0).isMinimized = true;
			$(this).hide();
			windowResizeImg.hide();
			window_maxImg.show();
		});
		
		window_maxImg.bind("click",function(){
			var windowSize = $.iUtil.getSize(windowContent.get(0));
			windowContent.SlideToggleUp(300);
			windowBottom.animate({height: windowSize.hb + 13}, 300);
			windowBottomContent.animate({height: windowSize.hb + 13}, 300);
			p.animate({height:windowSize.hb+43}, 300).get(0).isMinimized = false;
			$(this).hide();
			window_minImg.show();
			windowResizeImg.show();
		});
		
		window_closeImg.bind("click",function(){
			if(p.config.onClose){
				p.config.onClose.apply(p);
			}
			p.TransferTo({
				to:'windowOpen',
				className:'transferer2', 
				duration: 400
			}).hide();
		});

		this.Resizable({
			minWidth: p.config.minWidth||805,
			minHeight: p.config.minHeight||60,
			maxWidth: p.config.maxWidth||800,
			maxHeight: p.config.maxHeight||600,
			minTop:    p.config.minTop||1,
			minLeft:   p.config.minLeft||1,
			dragHandle: dragHandle,
			handlers: {
				se: resizeSe
			},
			onResize : function(size, position) {
				windowBottom.css('height', size.height-33 + 'px');
				windowBottomContent.css('height', size.height-33 + 'px');
				var windowContentEl = windowContent.css('width', size.width - 25 + 'px');
				if (!p.get(0).isMinimized) {
					windowContentEl.css('height', size.height - 48 + 'px');
				}
			}
		});
	}
}

jQuery.fn.extend(
	{
		PopWindow : jQuery.iPopWindow.create
	}
);