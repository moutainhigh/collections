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

Event.observe(window, 'load', function(){
	var dd = new wcm.dd.BaseDragDrop({id:'widget-box'});

	dd.addListener('beforestartdrag', function(event){
		Event.stop(event.browserEvent);
		var dom = Event.element(event.browserEvent);
		var thumb = findElement(dom, null, 'thumb');
			
		if(!thumb){
			return false;
		}
		
		//先记录拖动的起始元素
		this.startDragEl = thumb;
		return true;
	});

	dd.addListener('startdrag', function(x, y, event){
		var browserEvent = event.browserEvent;
		var startDragEl = $(this.startDragEl);

		this.page = Position.getPageInTop(window.frameElement);

		var win = getTopWin();
		var dom, imgEl;

		if(!win.$('widget-drag-in-top-id')){
			dom = win.document.createElement("div");
			dom.id = 'widget-drag-in-top-id';
			dom.unselectable = 'on';
			dom.style.position = 'absolute';
			dom.style.fontSize = '12px';
			dom.style.whiteSpace = 'nowrap';

			imgEl = getTopWin().document.createElement('img');
			dom.appendChild(imgEl);
			win.document.body.appendChild(dom);
		}else{
			dom = win.$('widget-drag-in-top-id');
			imgEl = dom.getElementsByTagName('img')[0];
		}

		dom.style.left = (this.page[0] + browserEvent.clientX + 3) + 'px';
		dom.style.top = (this.page[1] + browserEvent.clientY + 3) + 'px';
		dom.style.width =  startDragEl.offsetWidth + 'px';
		dom.style.height = startDragEl.offsetHeight + 'px';


		var thumbImg = startDragEl.getElementsByTagName('img')[0];
		imgEl.src = thumbImg.src;
		imgEl.style.width = thumbImg.offsetWidth + 'px';
		imgEl.style.height = thumbImg.offsetHeight + 'px';


		//正在拖动的元素
		this.dragEl = dom;

		var info = startDragEl.getAttribute("itemId");
		this.dragInfo = {
			objectId : win.nHostId || 0,
			objectType : win.nHostType || 0,
			templateId : win.nTemplateId || 0,
			widgetId : info
		};

	});


	dd.addListener('drag', function(x, y, event){
		var browserEvent = event.browserEvent;
		var dom = this.dragEl;

		dom.style.left = (this.page[0] + browserEvent.clientX + 3) + 'px';
		dom.style.top = (this.page[1] + browserEvent.clientY + 3) + 'px';

		//top.status = 'widget_mini' + ",x:" + dom.style.left + ",y:" + dom.style.top + ",date:" + new Date().getTime();

	});


	dd.addListener('enddrag', function(x, y, event){
		
	});


	dd.addListener('dispose', function(event){
		var win = getTopWin();
		win.document.body.removeChild(this.dragEl);
		delete this.dragEl;
		delete this.startDragEl;
		delete this.dragInfo;
		delete this.page;

	});

	var crossdd = new wcm.dd.AccrossFrameDragDrop(dd);

	Ext.apply(crossdd, {
		getWinInfos : function(){
			if(!getTopWin().$('page'))return [];
			return [
				{
					win : getTopWin(),
					startDrag : function(event, target, opt){
						if(!this.dd) return;
						var browserEvent = event.browserEvent;
						this.pageWin = getTopWin().$('page').contentWindow;
						this.pageWin.wcm.util.AccessWidgetDragger.onDragStart();
					},
					drag : function(event, target, opt){
						var browserEvent = event.browserEvent;
						var dragEl = this.dd.dragEl;
						dragEl.style.left = browserEvent.clientX + 3 + 'px';
						dragEl.style.top = browserEvent.clientY + 3 + 'px';
						this.pageWin.wcm.util.AccessWidgetDragger.onDrag(browserEvent.clientX, browserEvent.clientY);
						//top.status = 'top' + ",x:" + dragEl.style.left + ",y:" + dragEl.style.top + ",date:" + new Date().getTime();
					},
					endDrag : function(event, target, opt){
						var browserEvent = event.browserEvent;
						this.pageWin.wcm.util.AccessWidgetDragger.onDragEnd(browserEvent.clientX, browserEvent.clientY, event, this.dd.dragInfo);
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

						this.pageWin = pageIframe.contentWindow;
						this.pageWin.wcm.util.AccessWidgetDragger.onDragStart();
					},
					drag : function(event, target, opt){
						var browserEvent = event.browserEvent;
						var dragEl = this.dd.dragEl;
						dragEl.style.left = this.page[0] + browserEvent.clientX + 3 + 'px';
						dragEl.style.top = this.page[1] + browserEvent.clientY + 3 + 'px';

						this.pageWin.wcm.util.AccessWidgetDragger.onDrag(browserEvent.clientX + this.page[0], browserEvent.clientY + this.page[1]);
						//top.status = 'main' + ",x:" + dragEl.style.left + ",y:" + dragEl.style.top + ",date:" + new Date().getTime();
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

});



function resizeIfNeed(_loadImg){
	if(!_loadImg) return;
	var maxWidth = 172;
	var maxHeight = 112;

	getFittableImageSize(_loadImg, maxWidth, maxHeight, function(width, height){
		_loadImg.width = width;
		_loadImg.height = height;
	});

}		

function getFittableImageSize(imgDom, maxWidth, maxHeight, fCallback){
	if(!imgDom) return;
	var img = new Image();
	img.onload = function(){
		var height = img.height, width = img.width;

		if(height > maxHeight || width > maxWidth){	
			if(height > width){
				width = maxHeight * width/height;
				height = maxHeight
			}else{
				height = maxWidth * height/width;
				width = maxWidth;
			}
		}
		img.onload = null;
		if(fCallback) fCallback(width, height);
	}
	img.src = imgDom.src;
}


(function(){
	var maxWidth = 900;
	var maxHeight = 600;
	var bigImageBoxId = 'trs-big-image-box';
	var bigImageId = 'trs-big-image';

	function initBigImageBox(){
		if(getTopWin().$(bigImageBoxId)) return;

		var tmpBigImageBox = getTopWin().document.createElement('div');
		tmpBigImageBox.id = bigImageBoxId;
		tmpBigImageBox.style.visibility = 'hidden';
		Element.addClassName(tmpBigImageBox, 'big-image-box');
		getTopWin().document.body.appendChild(tmpBigImageBox);

		var arrow = getTopWin().document.createElement('div');
		Element.addClassName(arrow, 'arrow');
		tmpBigImageBox.appendChild(arrow);

		var imgEl = getTopWin().document.createElement('img');
		imgEl.id = bigImageId;
		tmpBigImageBox.appendChild(imgEl);

		tmpBigImageBox.onmouseover = function(){
			this.style.visibility = 'hidden';
		};
	}

	window.imageMouseOver = function(event, thumb, index){
		//正在进行拖动
		if(getTopWin().$('widget-drag-in-top-id')) return;

		initBigImageBox();
		var imgDom = thumb.getElementsByTagName('img')[0];

		var position = Position.getPageInTop(thumb);
		getFittableImageSize(imgDom, maxWidth, maxHeight, function(width, height){
			var imgEl = getTopWin().$(bigImageId), tmpBigImageBox = getTopWin().$(bigImageBoxId);
			imgEl.src = imgDom.src;
			imgEl.style.width = width + "px";
			imgEl.style.height = height + "px";

			//tmpBigImageBox.style.width = width + "px";
			if(height < 114){
				tmpBigImageBox.style.height = "114px";
			}else{
				tmpBigImageBox.style.height = "auto";
			}

			var left = position[0] + thumb.offsetWidth + 10;
			var top = position[1] - (tmpBigImageBox.offsetHeight - thumb.offsetHeight) / 2;
			if(top < 0) top = 0;
			var topBodyHeight = getTopWin().document.body.offsetHeight;
			if((top + tmpBigImageBox.offsetHeight) > topBodyHeight) top = topBodyHeight - tmpBigImageBox.offsetHeight;

			tmpBigImageBox.style.left = left + 'px';
			tmpBigImageBox.style.top = top + 'px';
			tmpBigImageBox.style.visibility = 'visible';
			Element['removeClassName'](tmpBigImageBox, 'arrow-right');
		});
	}

	window.imageMouseOut = function(event, thumb){
		event = event || window.event;
		var toElement = event.toElement;
		if(thumb.contains(toElement)) return;
		var tmpBigImageBox = getTopWin().$(bigImageBoxId);
		if(tmpBigImageBox) tmpBigImageBox.style.visibility = 'hidden';
	}

	window.imageMouseDown = function(event, thumb){
		var tmpBigImageBox = getTopWin().$(bigImageBoxId);
		if(tmpBigImageBox) tmpBigImageBox.style.visibility = 'hidden';
	}
})();


Event.observe(window, 'load', function(){
	var doms = document.getElementsByClassName('WidgetCategory');

	for(var index = 0; index < doms.length; index++){
		var header = Element.first(doms[index]);
		Event.observe(header, 'click', function(){
			Element[Element.hasClassName(this.parentNode, 'hideBody') ? 'removeClassName' : 'addClassName'](this.parentNode, 'hideBody');
		}.bind(header));
	}
});