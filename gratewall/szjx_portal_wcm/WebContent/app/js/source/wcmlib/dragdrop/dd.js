//拖动
Ext.ns("wcm.dd.DragDrop");

wcm.dd.Caches = [];
Event.observe(window, 'unload', function(){
	var caches = wcm.dd.Caches;
	for (var i = 0; i < caches.length; i++){
		try{
			caches[i].destroy();
		}catch(error){
		}
		caches[i]= null;
	}
	wcm.dd.Caches = [];
});
wcm.dd.BaseDragDrop = function(config){
	this.init(config);
	wcm.dd.Caches.push(this);
};

Ext.extend(wcm.dd.BaseDragDrop, wcm.util.Observable, {
	id : null,

	dragElId : null,

	handleElId : null,

	deltaX : 0,

	deltaY : 0,
	lastPointer : [],

	//private
	setCapture : function(element, win){
		if(this.captureEnable == false) return;
		if(element.setCapture){
			element.setCapture();
		}else if(window.captureEvents){
			if(!win){
				var doc = element.ownerDocument || document;
				win = doc.parentWindow || doc.defaultView;
			}
			win.captureEvents(18);
		}
	},

	//private
	releaseCapture : function(element, win){
		if(this.captureEnable == false) return;
		if(element.releaseCapture){
			element.releaseCapture();
		}else if(window.releaseEvents){
			if(!win){
				var doc = element.ownerDocument || document;
				win = doc.parentWindow || doc.defaultView;
			}
			win.releaseEvents(18);
		}
	},

	init : function(config){
		this.id = this.dragElId = this.handleElId = config["id"];
        Ext.apply(this, config);
        Ext.EventManager.on(this.handleElId, "mousedown", this.handleMouseDown, this);
		this.addEvents('beforestartdrag', 'startdrag', 'drag', 'enddrag', 'dispose', 'destroy');
	},

	b4StartDrag : function(e){
		var dom = Event.element(e);
		if(this.isValidChild(dom)) return true;
		return false;
	},

	isValidChild : function(dom){
		while(dom && dom.tagName != "BODY"){
			if(dom.id == this.handleElId) return true;
			dom = dom.parentNode;
		}
		return false;
	},

	handleMouseDown : function(e){
		Event.stop(e.browserEvent);
		if(e.button != 0) return;
		if(!this.b4StartDrag(e)) return;
		if(this.fireEvent('beforestartdrag', e) === false) return;
		this.dragging = false;

		//bind events.
		var el = $(this.handleElId);
		var doc = el.ownerDocument || document;
		Ext.EventManager.on(doc, "mousemove", this.handleMouseMove, this);
		Ext.EventManager.on(doc, "mouseup", this.handleMouseUp, this);		
		this.setCapture(el);
		
		el = $(this.dragElId);
		var xy = this.getXY(e);
		this.lastPointer = xy;
		this.deltaX = xy[0] - parseInt(Element.getStyle(el, 'left'), 10);
		this.deltaY = xy[1] - parseInt(Element.getStyle(el, 'top'), 10);
	},

	getXY : function(e){
		return [parseInt(e.getPageX(), 10), parseInt(e.getPageY(), 10)];
	},

	startDrag : function(x, y, e){
		this.fireEvent('startdrag', x, y, e, this);
	},
	
	drag : function(x, y, e){
		this.fireEvent('drag', x, y, e, this);
	},

	handleMouseMove : function(e){
		var xy = this.getXY(e);
		if(Math.abs(xy[0] - this.lastPointer[0]) < 2 && Math.abs(xy[1] - this.lastPointer[1]) < 2){
			return;
		}
		if(!this.dragging){
			this.dragging = true;
			this.startDrag(xy[0], xy[1], e);
		}
		this.drag(xy[0], xy[1], e);
	},

	endDrag : function(x, y, e){
		this.fireEvent('enddrag', x, y, e, this);
	},

	release : function(e){},
	dispose : function(e){
		this.fireEvent('dispose');
		this.release(e);
		//unbind events.
		var el = $(this.handleElId);
		var doc = el.ownerDocument || document;
        Ext.EventManager.un(doc, "mousemove", this.handleMouseMove, this);
        Ext.EventManager.un(doc, "mouseup", this.handleMouseUp, this);
		this.releaseCapture(el);
	},
	handleMouseUp : function(e){
		if(this.dragging){
			var xy = this.getXY(e);
			this.endDrag(xy[0], xy[1], e);
		}
		this.dispose(e);
	},
	destroy : function(){
		this.fireEvent('destroy');
        Ext.EventManager.un(this.handleElId, "mousedown", this.handleMouseDown, this);
		this.purgeListeners();
		delete this.dragging;
		delete this.handleElId;
		delete this.dragElId;
		delete this.id;
	}
});

wcm.dd.DragDrop = Ext.extend(wcm.dd.BaseDragDrop, {
	drag : function(x, y, e){
		wcm.dd.DragDrop.superclass.drag.apply(this, arguments);
		var dom = $(this.dragElId);
		dom.style.left = (x - this.deltaX) + "px";
		dom.style.top = (y - this.deltaY) + "px";
	}
});