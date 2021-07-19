/**
 * 本组件提供了x新闻滚动的功能。 可以根据用户传入的滚动速度、滚动距离，等参数，将新闻列表生成到指定的容器上，并让新闻列表中的新闻进行滚动。
 */

Ext.ns("com.trs.ScrollNews");
(function() {
	//缓冲新产生的对象，以便可以执行销毁
	var cache = {};

	/**
	 *
	 * @this {String} container:	滚动新闻将渲染在id为domId的容器中 <br>
	 * @this {String}direction:	新闻滚动方向
	 * @this {String}speed:	新闻滚动的速度（值越大滚动的越慢）
	 * @this {String}space:	新闻每次滚动的像素（值越大新闻滚动一次移动的距离越大）
	 */
	com.trs.ScrollNews = function(cfg) {
		cache[cfg.container] = this;
		Ext.apply(this,cfg);
		this.speed = parseInt(this.speed, 10) || 50;
		this.space = parseInt(this.space, 10) || 2;
		this.content = $(this.container).innerHTML;
	}

	/**
	*
	*/
	com.trs.ScrollNews.get = function(container){
		return cache[container];
	}

	com.trs.ScrollNews.prototype = {
		/**
		 * 根据传入的配置信息，将滚动新闻列表渲染在指定的容器内
		 * 
		 */
		render : function() {
			var container = $(this.container);
			if (!container) {
				return;
			}

			// 产生一个比较长的容器，从而使内部的新闻在一行（或者同一列）显示，不出现折行
			var bigDiv = document.createElement("div");
			Element.addClassName(bigDiv,"ScrollNews"+this.direction+"Cls");
			bigDiv.style.zoom = 1;// 解决ie6下可能存在的不能正确显示的bug

			/*
			 * 创建两个相同的子div容器，每个div里面包含了所有的新闻
			 * 为什么创建两个相同的div，是为了解决新闻列表滚动到最尾部的时候再切换 到最初位置时的闪动现象
			 */
			this.div1 = document.createElement("div");
			this.div1.innerHTML = this.content;
			this.div2 = document.createElement("div");
			this.div2.innerHTML = this.div1.innerHTML;
			Element.addClassName(this.div1, "div1Cls");
			Element.addClassName(this.div2, "div2Cls");
			// 将创建的大的div添加到指定的容器内
			bigDiv.appendChild(this.div1);
			bigDiv.appendChild(this.div2);
			container.innerHTML = '';
			container.appendChild(bigDiv);
			if(this.direction =="vertical"){
				container.style.height = (this.rollingHeight||20)+"px";
			}
			var context = this;
			container.onmouseover = function() {
				context.stop();
			};
			container.onmouseout = function() {
				context.play();
			};

			this.play();
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
				if(this.direction == "vertical"){
					container.scrollTop += this.space;
				// 如果第一个新闻列表已经滚到容器外面，说明需要重新初始化第一个新闻列表的位置
					if (container.scrollTop > this.div1.scrollHeight) {
						container.scrollTop = container.scrollTop
								- this.div1.scrollHeight;
					}
				}
			
				else{
					container.scrollLeft += this.space;

					// 如果第一个新闻列表已经滚到容器外面，说明需要重新初始化第一个新闻列表的位置
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

		/**
		 * 清除掉滚动新闻绑定的关联事件
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
