Ext.ns('com.trs.ImageDragger');
/**
*本组件对页面中的图片提供了拖动的功能，该功能主要是为了实现类似地图的拖动效果，
*即：通过拖动图片，可以将区域外的图片显示在当前的区域内部，从而对用户可见。
*为了使页面中的图片支持拖动，需要进行以下步骤：
*	1)引入当前js依赖的基础js库；
*	2)引入当前的js；
*	3)对需要支持拖动的元素添加draggable-image样式，属性值可以任意；
*	4)对需要支持拖动的元素添加背景图片样式，该图片将作为拖动的图片；
*/
(function(){
	var 
	/**
	*页面上需要支持图片拖动的标识样式，如果存在该class，则该元素将支持拖动
	*/
	className = 'draggable-image',
	/**
	*计算出图片的原始宽度，并将该宽度设置到元素的属性上，imageWidth为属性的名称
	*/
	imageWidth = 'data-image-width', 
	/**
	*计算出图片的原始高度，并将该高度设置到元素的属性上，imageHeight为属性的名称
	*/
	imageHeight = 'data-image-height',
	/**
	*从拖动元素的backgroundImage样式内部反解出来图片地址的正则
	*/
	backgroundImageReg = /url\((["']*)([^)]*?)\1\)/i;

	com.trs.ImageDragger = function(cfg){
		Ext.apply(this, cfg);
		this.el = $(this.el);
		this.init();
	};

	com.trs.ImageDragger.prototype = {
		init : function(){
			var el = this.el;

			var backgroundImage = el.style.backgroundImage;
			if(!backgroundImage) return;

			//获取图片的原始大小，以便拖动时限制图片的拖动范围
			var img = new Image();
			img.onload = function(){				
				el.setAttribute(imageWidth, this.width);
				el.setAttribute(imageHeight, this.height);
			};
			img.src = backgroundImage.replace(backgroundImageReg, '$2');

			this.initEvents();
		},
		//private
		initEvents : function(){
			var context = this;
			Event.observe(this.el, 'mousedown', function(event){
				var el = context.el;
				//获取图片的初始位置信息
				var position = el.style.backgroundPosition.split(" ");
				var l = parseInt(position[0], 10), t = parseInt(position[1], 10);
				var w = parseInt(el.offsetWidth, 10), h = parseInt(el.offsetHeight, 10);
				var ox = event.pageX || event.clientX, oy = event.pageY || event.clientY;
				
				var drag = function(event){
					var x = event.pageX || event.clientX, y = event.pageY || event.clientY;
					var px=x-ox+l, py=y-oy+t;
					px = Math.min(Math.max(-(el.getAttribute(imageWidth)-w), px), 0);
					py = Math.min(Math.max(-(el.getAttribute(imageHeight)-h), py), 0);
					el.style.backgroundPosition = px+"px " + py + "px";
				}
				var dragStop=function(a){
					if(drag){
						Event.stopObserving(el, 'mousemove', drag, false);
						drag = null;
					}
					if(dragStop){
						Event.stopObserving(el, 'mouseup', dragStop, false);
						dragStop = null;
					}
				};
				Event.observe(el, 'mousemove', drag, false);
				Event.observe(el, 'mouseup', dragStop, false);
				Event.observe(el, 'mouseout', dragStop, false);
			});
		},

		destroy : function(){
			delete this.el;
		}
	};

	DOM.ready(function(){
		var doms = document.getElementsByClassName(className);
		for(var index = 0; index < doms.length; index++){
			new com.trs.ImageDragger({el : doms[index]});
		}
	});
})();