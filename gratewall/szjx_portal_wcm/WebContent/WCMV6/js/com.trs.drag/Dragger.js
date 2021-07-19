$package("com.trs.drag");

com.trs.drag.Dragger = Class.create('drag.Dragger');
com.trs.drag._Draggers = [];
com.trs.drag.Dragger.prototype={
	initialize: function(o, oRoot, minX, maxX, minY, maxY, bSwapHorzRef, bSwapVertRef, fXMapper, fYMapper){
		this.root = oRoot && oRoot != null ? oRoot : o ;
		this.hmode			= bSwapHorzRef ? false : true ;
		this.vmode			= bSwapVertRef ? false : true ;
		if (this.hmode  && isNaN(parseInt(this.root.style.left  ))) this.root.style.left   = "0px";
		if (this.vmode  && isNaN(parseInt(this.root.style.top   ))) this.root.style.top    = "0px";
		if (!this.hmode && isNaN(parseInt(this.root.style.right ))) this.root.style.right  = "0px";
		if (!this.vmode && isNaN(parseInt(this.root.style.bottom))) this.root.style.bottom = "0px";
		this.minX	= typeof minX != 'undefined' ? minX : null;
		this.minY	= typeof minY != 'undefined' ? minY : null;
		this.maxX	= typeof maxX != 'undefined' ? maxX : null;
		this.maxY	= typeof maxY != 'undefined' ? maxY : null;
		this.xMapper = fXMapper ? fXMapper : null;
		this.yMapper = fYMapper ? fYMapper : null;
		this.onDragStart=new Function();
		this.onDragEnd=new Function();
		this.onDrag=new Function();
		this.handler = o;
		o.onmousedown	= this.start.bindAsEventListener(this);//function(ev){caller.start(ev);return false;};
		o.onmousemove	= null;
//		Event.observe(window,'unload',this.destroy.bind(this));
		com.trs.drag._Draggers.push(this);
	},
	start:function(ev){
		ev=(window.event)?window.event:ev;
		if((ev.button||ev.which)!=1){
			Event.stop(ev);
			return false;
		}
		if(this.root.style.position!='absolute'){
			Position.absolutize(this.root);
		}
		var y = parseInt(this.vmode ? this.root.style.top  : this.root.style.bottom);
		var x = parseInt(this.hmode ? this.root.style.left : this.root.style.right );
		this.onDragStart(x, y);
		this.lastMouseX	= Event.pointerX(ev);
		this.lastMouseY	= Event.pointerY(ev);

		if (this.hmode) {
			if (this.minX != null)	this.minMouseX	= Event.pointerX(ev) - x + this.minX;
			if (this.maxX != null)	this.maxMouseX	= this.minMouseX + this.maxX - this.minX;
		} else {
			if (this.minX != null) this.maxMouseX = -this.minX + Event.pointerX(ev) + x;
			if (this.maxX != null) this.minMouseX = -this.maxX + Event.pointerX(ev) + x;
		}

		if (this.vmode) {
			if (this.minY != null)	this.minMouseY	= Event.pointerY(ev) - y + this.minY;
			if (this.maxY != null)	this.maxMouseY	= this.minMouseY + this.maxY - this.minY;
		} else {
			if (this.minY != null) this.maxMouseY = -this.minY + Event.pointerY(ev) + y;
			if (this.maxY != null) this.minMouseY = -this.maxY + Event.pointerY(ev) + y;
		}
		this.document_mousemove	= this.drag.bindAsEventListener(this);
		this.document_mouseup	= this.end.bindAsEventListener(this);
		Event.observe(document.body,'mousemove',this.document_mousemove);
		Event.observe(document.body,'mouseup',this.document_mouseup);
		Event.stop(ev);
		return false;
	},
	drag:function(ev){
		ev=(window.event)?window.event:ev;

		if((ev.button||ev.which)!=1){
			return this.end(ev);
		}
		var ey	= Event.pointerY(ev);
		var ex	= Event.pointerX(ev);
		var y = parseInt(this.vmode ? this.root.style.top  : this.root.style.bottom);
		var x = parseInt(this.hmode ? this.root.style.left : this.root.style.right );
		var nx, ny;
		if (this.minX != null) ex = this.hmode ? Math.max(ex, this.minMouseX) : Math.min(ex, this.maxMouseX);
		if (this.maxX != null) ex = this.hmode ? Math.min(ex, this.maxMouseX) : Math.max(ex, this.minMouseX);
		if (this.minY != null) ey = this.vmode ? Math.max(ey, this.minMouseY) : Math.min(ey, this.maxMouseY);
		if (this.maxY != null) ey = this.vmode ? Math.min(ey, this.maxMouseY) : Math.max(ey, this.minMouseY);

		nx = x + ((ex - this.lastMouseX) * (this.hmode ? 1 : -1));
		ny = y + ((ey - this.lastMouseY) * (this.vmode ? 1 : -1));
		if (this.xMapper)		nx = this.xMapper(y)
		else if (this.yMapper)	ny = this.yMapper(x)
		this.root.style[this.hmode ? "left" : "right"] = nx + "px";
		this.root.style[this.vmode ? "top" : "bottom"] = ny + "px";
		this.lastMouseX	= ex;
		this.lastMouseY	= ey;
		this.onDrag(nx, ny, [ex,ey] ,ev);
		Event.stop(ev);
		return false;
	},
	end:function(ev){
		Event.stopObserving(document.body,'mousemove',this.document_mousemove);
		Event.stopObserving(document.body,'mouseup',this.document_mouseup);
		this.onDragEnd(parseInt(this.root.style[this.hmode ? "left" : "right"]),parseInt(this.root.style[this.vmode ? "top" : "bottom"]),ev);
		Event.stop(ev);
		return false;
	},
	destroy:function(_bUnion){
		if(this.handler){
			this.handler.onmousedown = this.handler.onmousemove = null;
			this.handler = null;
		}
		this.document_mousemove = this.document_mouseup = null;
		this.onDragStart = this.onDrag = this.onDragEnd = null;
		this.root = null;
		if(!_bUnion&&com.trs.drag._Draggers)com.trs.drag._Draggers.without(this);
	}
}
Event.observe(window,'unload',function(){
	if(com.trs.drag._Draggers){
		for(var i=0,n=com.trs.drag._Draggers.length;i<n;i++){
			var oDragger = com.trs.drag._Draggers[i];
			oDragger.destroy(true);
		}
	}
	com.trs.drag._Draggers = null;
});
