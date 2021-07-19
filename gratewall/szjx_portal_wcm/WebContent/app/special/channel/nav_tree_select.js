
(function(){
	function findElement(dom, sAttrName, sClassName, aIgnoreAttr){
		aIgnoreAttr = aIgnoreAttr || [];
		while(dom){
			if(!dom.tagName || dom.tagName == 'BODY') break;
			for (var i = 0; i < aIgnoreAttr.length; i++){
				if(dom.getAttribute(aIgnoreAttr[i]) != null) return 0;
			}
			if(sAttrName && dom.getAttribute(sAttrName) != null){
				return dom;
			}
			if(sClassName && Element.hasClassName(dom, sClassName)){
				return dom;
			}
			dom = dom.parentNode;
		}
		return null;
	}

	function getTopWin(){
		try{
			return window.top;
		}catch(error){
			return window;
		}
	}

	var dd = new wcm.dd.BaseDragDrop({id:'ChannelNav'});

	dd.addListener('beforestartdrag', function(event){
		Event.stop(event.browserEvent);
		var dom = Event.element(event.browserEvent);
		if(dom.tagName != 'LABEL') return false;

		var classPreEl = findElement(dom, 'classPre');
			
		if(!classPreEl || classPreEl.getAttribute('classPre') != 'channel'){
			return false;
		}
		
		//先记录拖动的起始元素
		this.startDragEl = dom;
		return true;
	});

	dd.addListener('startdrag', function(x, y, event){
		var browserEvent = event.browserEvent;
		var startDragEl = $(this.startDragEl);

		//remove linke href
		this.link = startDragEl.parentNode;
		this.link.removeAttribute('href');

		var doc = startDragEl.ownerDocument || document;
		var box = doc.documentElement || doc.body;
		var page = Position.getPageInTop(box);

		var offset = Position.cumulativeOffset(startDragEl);

		this.orgLeft = page[0] + browserEvent.clientX + 3;
		this.orgTop = page[1] + browserEvent.clientY + 3;

		var win = getTopWin();
		var dom = win.$('tree-nav-for-special-drag');
		if(!dom){
			dom = win.document.createElement("div");
			dom.id = 'tree-nav-for-special-drag';
			dom.unselectable = 'on';
			dom.style.position = 'absolute';
			dom.style.fontSize = '12px';
			dom.style.whiteSpace = 'nowrap';
			win.document.body.appendChild(dom);
		}
		dom.style.left = this.orgLeft + 'px';
		dom.style.top = this.orgTop + 'px';
		dom.style.width =  startDragEl.offsetWidth + 'px';
		dom.style.height = startDragEl.offsetHeight + 'px';

		dom.innerHTML = startDragEl.innerHTML;

		//正在拖动的元素
		this.dragEl = dom;

		var sLabelFor = startDragEl.getAttribute("for");

		if(!sLabelFor){//fix ie6
			var result = startDragEl.outerHTML.match(/<label[^>]*?for=['" ]?([^>]*)['" ]?[^>]*?>/i);
			if(result != null){
				sLabelFor = result[1];
			}
		}		

		if(!sLabelFor){
			this.dragInfo = {};
		}else{
			var info = sLabelFor.split("_");
			this.dragInfo = {
				objectId : win.nHostId || 0,
				objectType : win.nHostType || 0,
				templateId : win.nTemplateId || 0,
				channelId : info[1]
			};
		}
	});


	dd.addListener('drag', function(x, y, event){
		var dom = this.dragEl;

		dom.style.left = this.orgLeft + (x - this.lastPointer[0]) + 'px';
		dom.style.top = this.orgTop + (y - this.lastPointer[1]) + 'px';
	});


	dd.addListener('enddrag', function(x, y, event){
		
	});


	dd.addListener('dispose', function(event){
		var win = getTopWin();
		win.document.body.removeChild(this.dragEl);
		delete this.dragEl;
		delete this.startDragEl;
		delete this.dragInfo;

		var caller = this;
		setTimeout(function(){
			caller.link.setAttribute('href', '#');
			delete caller.link;
		}, 10);
	});

	var crossdd = new wcm.dd.AccrossFrameDragDrop(dd);

	Ext.apply(crossdd, {
		getWinInfos : function(){
			if(!getTopWin().$('page'))return [];
			return [
				{
					win : getTopWin(),
					startDrag : function(event, target, opt){
						var browserEvent = event.browserEvent;
						var pageIframe = getTopWin().$('page');

						//获取$('page')在顶层页面中的位置
						this.page = Position.getPageInTop(pageIframe);

						this.pageWin = getTopWin().$('page').contentWindow;
						var doc = this.pageWin.document;
						var box = doc.documentElement || doc.body;
						this.scroll = [box.scrollLeft, box.scrollTop];

						this.pageWin.wcm.util.AccessWidgetDragger.onDragStart();
					},
					drag : function(event, target, opt){
						var browserEvent = event.browserEvent;
						var dragEl = this.dd.dragEl;
						dragEl.style.left = browserEvent.clientX + 'px';
						dragEl.style.top = browserEvent.clientY + 'px';
						this.pageWin.wcm.util.AccessWidgetDragger.onDrag(browserEvent.clientX, browserEvent.clientY);
					},
					endDrag : function(event, target, opt){
						var browserEvent = event.browserEvent;
						this.pageWin.wcm.util.AccessWidgetDragger.onDragEnd(browserEvent.clientX, browserEvent.clientY, event, this.dd.dragInfo);
						delete this.page;
						delete this.pageWin;
					},
					destroy : function(){
						getTopWin().$('page').contentWindow.wcm.util.AccessWidgetDragger.destroy();
					}				
				}, 
				{
					win : getTopWin().$('page').contentWindow,
					startDrag : function(event, target, opt){
						var browserEvent = event.browserEvent;
						var pageIframe = getTopWin().$('page');
						
						//获取$('page')在顶层页面中的位置
						this.page = Position.getPageInTop(pageIframe);

						var doc = opt.win.document;
						var box = doc.documentElement || doc.body;
						this.scroll = [box.scrollLeft, box.scrollTop];

						this.pageWin = pageIframe.contentWindow;
						this.pageWin.wcm.util.AccessWidgetDragger.onDragStart();
					},
					drag : function(event, target, opt){
						var browserEvent = event.browserEvent;
						var dragEl = this.dd.dragEl;
						dragEl.style.left = this.page[0] + browserEvent.clientX + 'px';
						dragEl.style.top = this.page[1] + browserEvent.clientY + 'px';

						//this.pageWin.wcm.util.AccessWidgetDragger.onDrag(browserEvent.clientX + this.scroll[0], browserEvent.clientY + this.scroll[1]);
						this.pageWin.wcm.util.AccessWidgetDragger.onDrag(browserEvent.clientX + this.page[0], browserEvent.clientY + this.page[1]);
					},
					endDrag : function(event, target, opt){
						var browserEvent = event.browserEvent;
						this.pageWin.wcm.util.AccessWidgetDragger.onDragEnd(browserEvent.clientX, browserEvent.clientY, event, this.dd.dragInfo);
						delete this.page;
						delete this.pageWin;
						delete this.scroll;
					},
					destroy : function(){
						getTopWin().$('page').contentWindow.wcm.util.AccessWidgetDragger.destroy();
					}
			}];	
		}
	});

})();