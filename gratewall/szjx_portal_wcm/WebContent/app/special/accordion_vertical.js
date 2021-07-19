Ext.ns('wcm.AccordionVertical');

(function(){
	var template = [
		'<div class="panel accor-panel-{0}" id="accor-panel-{0}">',
			'<div class="header" trigger="{2}">',
				'<div class="title">{1}</div>',
			'</div>',
			'<div class="body" id="accor-panel-body-{0}"></div>',
		'</div>'
	].join("");

	wcm.AccordionVertical = function(config){
		this.config = config;
		this.render();
	}
	
	wcm.AccordionVertical.prototype = {
		render : function(){
			Element.addClassName(this.config.container, 'accordion-vertical');

			var items = this.config.items;
			var selectedIndex = 0;

			//init the html
			var aHtml = [];
			for(var index = 0; index < items.length; index++){
				var item = items[index];
				if(item.selected){
					selectedIndex = index;
				}
				aHtml.push(String.format(template, item.key, item.title, index));
			}
			$(this.config.container).innerHTML = aHtml.join("");


			//init the action
			this.initAction();

			this.select(selectedIndex);
		},

		initAction : function(){
			Event.observe(this.config.container, 'click', function(event){
				event = event || window.event;

				//find trigger target
				var trigger;
				var dom = Event.element(event);
				while(dom && dom.nodeType == 1){
					trigger = dom.getAttribute("trigger");
					if(trigger){
						break;
					}
					dom = dom.parentNode;
				}
				if(!trigger) return;

				this.select(trigger);
			}.bind(this), false);

			Event.observe(window, 'resize', function(event){
				if(this.lastSelectedIndex == null) return;

				var item = this.config.items[this.lastSelectedIndex];
				var dom = $('accor-panel-' + item.key);
				var oldOverflow = $(this.config.container).style.overflow || 'auto';
				$(this.config.container).style.overflow = 'hidden';
				var header = Element.first(dom);
				var containerHeight = $(this.config.container).offsetHeight;
				var headerHeight = header.offsetHeight;
				var bodyHeight = containerHeight - this.config.items.length * headerHeight;
				Element.last(dom).style.height = Math.max(0, bodyHeight) + 'px';
				$(this.config.container).style.overflow = oldOverflow;

			}.bind(this), false);
		},

		redraw : function(selectedIndex){
			if(this.lastSelectedIndex == null) return;
			
			var item = this.config.items[this.lastSelectedIndex];

			//设置高度
			var dom = $('accor-panel-' + item.key);
			var header = Element.first(dom);
			var oldOverflow = $(this.config.container).style.overflow || 'auto';
			$(this.config.container).style.overflow = 'hidden';
			var containerHeight = $(this.config.container).offsetHeight || $(this.config.container).getAttribute("_initheight") || 0;
			var headerHeight = header.offsetHeight || 31;
			var bodyHeight = containerHeight - this.config.items.length * headerHeight;
			Element.last(dom).style.height = Math.max(0, bodyHeight) + 'px';
			$(this.config.container).style.overflow = oldOverflow;
		},

		select : function(selectedIndex){
			if(selectedIndex == this.lastSelectedIndex) return;

			if(this.lastSelectedIndex != null){
				var item = this.config.items[this.lastSelectedIndex];
				Element.removeClassName($('accor-panel-' + item.key), 'selected');
			}

			
			this.lastSelectedIndex = selectedIndex;
			var item = this.config.items[this.lastSelectedIndex];

			//设置高度
			var dom = $('accor-panel-' + item.key);
			var header = Element.first(dom);
			var containerHeight = $(this.config.container).offsetHeight || $(this.config.container).getAttribute("_initheight") || 0;
			var headerHeight = header.offsetHeight || 31;
			var bodyHeight = containerHeight - this.config.items.length * headerHeight;
			Element.last(dom).style.height = Math.max(0, bodyHeight) + 'px';

			//显示
			Element.addClassName(dom, 'selected');

			var cmd = this.config.items[selectedIndex].cmd;
			if(cmd) cmd(Element.last(dom));
		}
	}
})();