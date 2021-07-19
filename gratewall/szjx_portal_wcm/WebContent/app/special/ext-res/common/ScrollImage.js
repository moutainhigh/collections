/**
 * 本组件提供了图片滚动的功能。 可以根据用户传入的图片地址、图片描述信息，图片宽度、高度等信息 将图片列表生成到指定的容器上，并让图片列表中的图片进行滚动。
 */

Ext.ns("com.trs.ScrollImage");
Ext.apply(String, {
	parse : function(_sTemplate, _oParameters){
		return _sTemplate.replace(/\{(\w+)\}/g, function(m, i){
			return _oParameters[i.toUpperCase()];
		});
	}
	
});
(function() {
	//缓冲新产生的对象，以便可以执行销毁
	var cache = {};

	/*
	 * 每一个图片的html结构模板
	 */

		var sTemplate = [
			'<div class="img-item">',
				'<div>', 
					'<a target="_blank" href="{LINKURL}">',
					'<img alt="" src="{IMGURL}" style="width:{WIDTH}px;height:{HEIGHT}px;"/>', 
					'</a>', 
				'</div>',
				'<div style="display:{SHOWDESC};width:{WIDTH}px;" class="img-descs"><a target="_blank" href="{LINKURL}">{DESC}</a></div>', 
			'</div>'].join("");

	/**
	 *
	 * @this {Array} imgUrls :	需要显示的图片url地址的集合 <br>
	 * @this {String} container:	图片列表将渲染在id为domId的容器中 <br>
	 * @this {Array} descs :	图片描述
	 * @this {String}width:	渲染图片的容器宽度
	 * @this {String}height:	渲染图片的容器高度
	 * @this {String}speed:	图片滚动的速度（值越大滚动的越慢）
	 * @this {String}space:	图片每次滚动的像素（值越大图片滚动一次移动的距离越大）
	 * @this {int}hasDescs:	是否显示图片描述
	 * @this {String}direction:	图片滚动方向
	 * @this {String}canvasHeight:	画布高度
	 * @this {int}page:	翻多少张图片
	 * 
	 */
	com.trs.ScrollImage = function(cfg) {
		cache[cfg.container] = this;
		Ext.apply(this, cfg);
		this.speed = parseInt(this.speed, 10) || 60;
		this.space = parseInt(this.space, 10) || 2;
		this.hasDescs = !!parseInt(this.hasDescs, 10);
		var width = parseInt(this.width.substring(0,this.width.length-2),10);
		var page = parseInt(this.page, 10) || 0;
		this.pagesWidth = width * page;
	}

	/**
	*
	*/
	com.trs.ScrollImage.get = function(container){
		return cache[container];
	}

	com.trs.ScrollImage.prototype = {
		/**
		 * 根据传入的配置信息，将滚动图片列表渲染在指定的容器内
		 * 
		 */
		render : function() {
			var container = $(this.container);
			if (!container) {
				return;
			}

			// 产生一个比较长的容器，从而使内部的图片元素在一行显示，不出现折行
			var bigDiv = document.createElement("div");
			
			Element.addClassName(bigDiv, "ScrollImg"+(this.direction||"horizontal")+"Cls");
			bigDiv.style.zoom = 1;// 解决ie6下可能存在的不能正确显示的bug

			/*
			 * 创建两个相同的子div容器，每个div里面包含了所有的图片和描述信息
			 * 为什么创建两个相同的div，是为了解决图片列表滚动到最尾部的时候再切换 到最初位置时的闪动现象
			 */
			this.div1 = this.appendList2Node(bigDiv);
			Element.addClassName(this.div1, "div1Cls");
			this.div2 = this.appendList2Node(bigDiv);
			Element.addClassName(this.div2, "div2Cls");

			// 将创建的大的div添加到指定的容器内
			container.innerHTML = "";
			container.appendChild(bigDiv);

			if(this.direction =="vertical"){
				container.style.height = (this.rollingHeight||300)+"px";
			}


			this.autoPlay();

		},

		autoPlay : function(){
			var context = this;
			
			if(this.pagesWidth>0){
				this.playTheadId = -1;
				clearInterval(this.playTheadId);
				this.playTheadId = setInterval(function(){
					context.play2(true);
				}, 7000);
			}else{
				var container = $(context.container);
				container.onmouseover = function() {
					context.stop();
				};
				container.onmouseout = function() {
					context.play()
				};
				this.play();
			}

		},
		play2 : function(bLeft){
			var context = this;
			clearInterval(this.intervalHandler);
			this.moveStopFlag = false;
			this.scrollSpace = 0;
			if(bLeft){
				context.scrollNext();
			}else{
				context.scrollLast();
			}
			this.intervalHandler = setInterval(function() {
				if(bLeft){
					context.scrollNext();
				}else{
					context.scrollLast();
				}
			}, this.speed);
		},

		// private
		play : function() {
			var context = this;
			clearInterval(this.intervalHandler);
			this.intervalHandler = setInterval(function() {
				context.scrollNext();
			}, this.speed);
		},

		// private
		stop : function() {
			clearInterval(this.intervalHandler);
		},

		// private
		scrollNext : function() {
			var container = $(this.container);
			try {
				if(this.pagesWidth>0 && this.scrollSpace>this.pagesWidth){
					this.moveStopFlag=true;
					clearInterval(this.intervalHandler);
				}
				this.scrollSpace += this.space;
				if(this.direction == "vertical"){
					container.scrollTop += this.space;
					if (container.scrollTop > this.div1.scrollHeight) {
						container.scrollTop = container.scrollTop
								- this.div1.scrollHeight;
					};
				}else{
					container.scrollLeft += this.space;
					// 如果第一个图片列表已经滚到容器外面，说明需要重新初始化第一个图片列表的位置
					if (container.scrollLeft > this.div1.scrollWidth) {
						container.scrollLeft = container.scrollLeft
								- this.div1.scrollWidth;
					};
				}
			} catch (e) {
				this.stop();
				return;
			}


			
		},

		// private
		scrollLast : function() {
			var container = $(this.container);
			try {
				if(this.pagesWidth>0 && this.scrollSpace>this.pagesWidth){
					this.moveStopFlag=true;
					clearInterval(this.intervalHandler);
				}
				this.scrollSpace += this.space;
				container.scrollLeft -= this.space;
			} catch (e) {
				this.stop();
				return;
			}


			// 如果第一个图片列表已经滚到容器外面，说明需要重新初始化第一个图片列表的位置
			if (container.scrollLeft <= 0) {
				container.scrollLeft = this.div1.scrollWidth;
			};
		},

		/*
		 * 构造图片列表，添加到节点parent中
		 */
		// private
		appendList2Node : function(parent) {
			var dom = document.createElement("div");

			var aHtml = [];
			var imgUrls = this.imgUrls;
			var descs = this.descs;
			var linkUrls = this.linkUrls;
			for (var i = 0; i < imgUrls.length; i++) {
				var imgUrl = imgUrls[i];
				if(!imgUrl){
					continue;
				}
				if (imgUrl.toUpperCase().indexOf("HTTP") < 0) {
					imgUrl = wcm.Constant.ROOT_PATH + imgUrl;
				}
				aHtml.push(String.parse(sTemplate, {
								IMGURL : imgUrl, 
								LINKURL: linkUrls[i] || imgUrl,
								DESC : descs[i] || "",
								WIDTH : this.width,
								HEIGHT :this.height, 
								SHOWDESC : (this.hasDescs?"":"none")
							}));
			}
	
			dom.innerHTML = aHtml.join("");
			parent.appendChild(dom);
			return dom;
		},

		/**
		 * 清除掉滚动图片绑定的关联事件
		 */
		destroy : function() {
			this.stop();
			var container = $(this.container);
			container.onmouseover = null;
			container.onmouseout = null;
			delete this.div1;
			delete this.div2;
		}
	};
})();
